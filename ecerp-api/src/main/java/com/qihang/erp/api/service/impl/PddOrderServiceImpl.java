package com.qihang.erp.api.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.qihang.erp.api.domain.ErpOrder;
import com.qihang.erp.api.domain.ErpOrderItem;
import com.qihang.erp.api.mapper.ErpOrderMapper;
import com.zhijian.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.zhijian.common.utils.StringUtils;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.qihang.erp.api.domain.PddOrderItem;
import com.qihang.erp.api.mapper.PddOrderMapper;
import com.qihang.erp.api.domain.PddOrder;
import com.qihang.erp.api.service.IPddOrderService;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * 拼多多订单Service业务层处理
 * 
 * @author qihang
 * @date 2024-01-02
 */
@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
@Service
public class PddOrderServiceImpl implements IPddOrderService 
{
    @Autowired
    private PddOrderMapper pddOrderMapper;
    @Autowired
    private ErpOrderMapper erpOrderMapper;

    /**
     * 查询拼多多订单
     * 
     * @param id 拼多多订单主键
     * @return 拼多多订单
     */
    @Override
    public PddOrder selectPddOrderById(Long id)
    {
        return pddOrderMapper.selectPddOrderById(id);
    }

    /**
     * 查询拼多多订单列表
     * 
     * @param pddOrder 拼多多订单
     * @return 拼多多订单
     */
    @Override
    public List<PddOrder> selectPddOrderList(PddOrder pddOrder)
    {
        List<PddOrder> orderList = pddOrderMapper.selectPddOrderList(pddOrder);
        for (var o:orderList) {
            List<PddOrderItem> items = pddOrderMapper.selectOrderItemByOrderId(o.getId());
            o.setPddOrderItemList(items);
        }
        return orderList;
    }

    /**
     * 新增拼多多订单
     * 
     * @param pddOrder 拼多多订单
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertPddOrder(PddOrder pddOrder) {
        PddOrder order = pddOrderMapper.selectByOrderSn(pddOrder.getOrderSn());
        if (order != null) return -1;

//        if (StringUtils.isNotNull(pddOrder.getPddOrderItemList())) {
//            for (PddOrderItem pddOrderItem : pddOrder.getPddOrderItemList()) {
//                if (StringUtils.isNull(pddOrderItem.getErpSpecId())) {
//                    return -3;
//                }
//            }
//        } else return -2;

        pddOrder.setTradeType(0L);
        pddOrder.setConfirmStatus(1L);
        pddOrder.setGroupStatus(1L);
        pddOrder.setRefundStatus(1L);
        pddOrder.setOrderStatus(1L);
        double discountAmount = pddOrder.getPlatformDiscount() + pddOrder.getSellerDiscount() + pddOrder.getCapitalFreeDiscount();
        pddOrder.setDiscountAmount(discountAmount);
        double payAmount = pddOrder.getGoodsAmount() - pddOrder.getDiscountAmount() + pddOrder.getPostage();
        pddOrder.setPayAmount(payAmount);
        pddOrder.setAfterSalesStatus(0L);
        pddOrder.setOrderConfirmTime(0L);
        pddOrder.setAuditStatus(0L);
        pddOrder.setSettlementStatus(0L);
        pddOrder.setShipStatus(0L);
        int rows = pddOrderMapper.insertPddOrder(pddOrder);
//        insertPddOrderItem(pddOrder);
        Long id = pddOrder.getId();
        List<PddOrderItem> list = new ArrayList<PddOrderItem>();
        for (PddOrderItem pddOrderItem : pddOrder.getPddOrderItemList()) {
            pddOrderItem.setOrderId(id);
            list.add(pddOrderItem);
        }

        if (list.size() > 0) {
            try {
                pddOrderMapper.batchPddOrderItem(list);
            } catch (Exception e) {
                //手工回滚异常
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return -3;
            }
        }

        return rows;
    }

    /**
     * 确认订单
     * @param orderId
     * @return
     */
    @Transactional
    @Override
    public int confirmOrder(Long orderId,String remark,String createBy) {
        PddOrder order = pddOrderMapper.selectPddOrderById(orderId);
        if(order == null) return -1;
        else if(order.getAuditStatus() != 0) return -2;
        else if(order.getRefundStatus() != 1) return -3;

        // 判断是否存在
        ErpOrder erpo = erpOrderMapper.selectErpOrderByNum(order.getOrderSn());
        if(erpo !=null ) return -4;

        // 确认订单（操作：插入数据到s_shop_order、s_shop_order_item）
        ErpOrder so = new ErpOrder();
        so.setOrderNum(order.getOrderSn());
        so.setShopId(order.getShopId().intValue());
        so.setShopType(5);
        so.setRemark(remark);
        so.setBuyerMemo(order.getBuyerMemo());
        so.setTag(order.getTag());
        so.setRefundStatus(1);
        so.setOrderStatus(1);
        so.setGoodsAmount(BigDecimal.valueOf(order.getGoodsAmount()));
        so.setDiscountAmount(BigDecimal.valueOf(order.getDiscountAmount()));
        so.setAmount(BigDecimal.valueOf(order.getPayAmount()));
        so.setPostage(BigDecimal.valueOf(order.getPostage()));
        try {
            //2022-07-17 17:10:57
            Date payDate = DateUtils.dateTime("yyyy-MM-dd HH:mm:ss", order.getPayTime());
            so.setPayTime(payDate);
        }catch (Exception e){}

        so.setReceiverName(order.getReceiverName());
        so.setReceiverPhone(order.getReceiverPhone());
        so.setAddress(order.getAddress());
        so.setCountry("中国");
        so.setProvince(order.getProvince());
        so.setCity(order.getCity());
        so.setTown(order.getTown());
        so.setConfirmTime(new Date());
        so.setCreateTime(new Date());
        so.setCreateBy(createBy);
        erpOrderMapper.insertErpOrder(so);

        List<ErpOrderItem> items = new ArrayList<>();
        for (var i:order.getPddOrderItemList()) {
            ErpOrderItem item = new ErpOrderItem();
            item.setOrderId(so.getId());
            item.setGoodsId(i.getErpGoodsId());
            item.setSpecId(i.getErpSpecId());
            item.setGoodsTitle(i.getGoodsName());
            item.setGoodsImg(i.getGoodsImage());
            item.setGoodsNum(i.getGoodsNum());
            item.setSpecNum(i.getSpecNum());
            item.setGoodsSpec(i.getGoodsSpec());
            item.setGoodsPrice(BigDecimal.valueOf(i.getGoodsPrice()));
            item.setItemAmount(BigDecimal.valueOf(i.getItemAmount()));
            item.setQuantity(i.getQuantity().intValue());
            item.setIsGift(i.getIsGift().intValue());
            item.setRefundCount(0);
            item.setRefundStatus(1);
            item.setCreateBy(createBy);
            item.setCreateTime(new Date());
            items.add(item);
        }
        erpOrderMapper.batchErpOrderItem(items);

        //更新自己
        PddOrder po =new PddOrder();
        po.setId(order.getId());
        po.setAuditStatus(1L);
        pddOrderMapper.updatePddOrder(po);
        return 1;
    }

    /**
     * 修改拼多多订单
     * 
     * @param pddOrder 拼多多订单
     * @return 结果
     */
    @Transactional
    @Override
    public int updatePddOrder(PddOrder pddOrder)
    {
        pddOrderMapper.deletePddOrderItemByOrderId(pddOrder.getId());
        insertPddOrderItem(pddOrder);
        return pddOrderMapper.updatePddOrder(pddOrder);
    }

    /**
     * 批量删除拼多多订单
     * 
     * @param ids 需要删除的拼多多订单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deletePddOrderByIds(Long[] ids)
    {
        pddOrderMapper.deletePddOrderItemByOrderIds(ids);
        return pddOrderMapper.deletePddOrderByIds(ids);
    }

    /**
     * 删除拼多多订单信息
     * 
     * @param id 拼多多订单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deletePddOrderById(Long id)
    {
        pddOrderMapper.deletePddOrderItemByOrderId(id);
        return pddOrderMapper.deletePddOrderById(id);
    }

    /**
     * 新增拼多多订单明细信息
     * 
     * @param pddOrder 拼多多订单对象
     */
    public void insertPddOrderItem(PddOrder pddOrder)
    {
        List<PddOrderItem> pddOrderItemList = pddOrder.getPddOrderItemList();
        Long id = pddOrder.getId();
        if (StringUtils.isNotNull(pddOrderItemList))
        {
            List<PddOrderItem> list = new ArrayList<PddOrderItem>();
            for (PddOrderItem pddOrderItem : pddOrderItemList)
            {
                pddOrderItem.setOrderId(id);
                list.add(pddOrderItem);
            }
            if (list.size() > 0)
            {
                pddOrderMapper.batchPddOrderItem(list);
            }
        }
    }
}
