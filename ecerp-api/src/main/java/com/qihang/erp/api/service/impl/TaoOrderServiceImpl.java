package com.qihang.erp.api.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.qihang.erp.api.domain.*;
import com.qihang.erp.api.mapper.*;
import com.zhijian.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.zhijian.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.qihang.erp.api.service.ITaoOrderService;

/**
 * 淘宝订单Service业务层处理
 * 
 * @author qihang
 * @date 2024-01-03
 */
@Service
public class TaoOrderServiceImpl implements ITaoOrderService 
{
    @Autowired
    private TaoOrderMapper taoOrderMapper;
    @Autowired
    private TaoOrderAddressMapper addressMapper;
    @Autowired
    private ErpOrderMapper erpOrderMapper;

    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private ScmSupplierAgentShippingMapper agentShippingMapper;

    @Autowired
    private WmsOrderShippingMapper orderShippingMapper;

    /**
     * 查询淘宝订单
     * 
     * @param id 淘宝订单主键
     * @return 淘宝订单
     */
    @Override
    public TaoOrder selectTaoOrderById(String id)
    {
        TaoOrder taoOrder = taoOrderMapper.selectTaoOrderById(id);
        TaoOrderAddress addr = addressMapper.selectTaoOrderAddressByOrderId(taoOrder.getId());
        taoOrder.setReceiver(addr.getContactPerson());
        taoOrder.setPhone(addr.getMobile());
        taoOrder.setProvince(addr.getProvince());
        taoOrder.setCity(addr.getCity());
        taoOrder.setTown(addr.getTown());
        taoOrder.setAddress(addr.getAddress());
        return taoOrder;
    }

    /**
     * 查询淘宝订单列表
     * 
     * @param taoOrder 淘宝订单
     * @return 淘宝订单
     */
    @Override
    public List<TaoOrder> selectTaoOrderList(TaoOrder taoOrder)
    {
        var orderList = taoOrderMapper.selectTaoOrderList(taoOrder);
        for (var o:orderList) {
            List<TaoOrderItem> items = taoOrderMapper.selectOrderItemByOrderId(o.getId());
            o.setTaoOrderItemList(items);
        }
        return orderList;
    }

    /**
     * 新增淘宝订单
     * 
     * @param taoOrder 淘宝订单
     * @return 结果
     */
    @Transactional
    @Override
    public int insertTaoOrder(TaoOrder taoOrder)
    {
        if(StringUtils.isNull(taoOrder.getShippingFee())) taoOrder.setShippingFee(BigDecimal.ZERO);
        taoOrder.setTotalAmount(taoOrder.getTotalAmount().add(taoOrder.getShippingFee()));
        taoOrder.setPayAmount(taoOrder.getTotalAmount());
        taoOrder.setStatus(2L);
        taoOrder.setStatusStr("等待发货");
        taoOrder.setRefundStatus("0");
        taoOrder.setAuditStatus(0L);
        taoOrder.setSendStatus(0L);
        taoOrder.setIsComment(0);

        taoOrder.setCreateTime(DateUtils.getNowDate());
        int rows = taoOrderMapper.insertTaoOrder(taoOrder);
        insertTaoOrderItem(taoOrder);
        // 添加地址
        TaoOrderAddress address = new TaoOrderAddress();
        address.setOrderId(taoOrder.getId());
        address.setContactPerson(taoOrder.getReceiver());
        address.setMobile(taoOrder.getPhone());
        address.setProvince(taoOrder.getProvince());
        address.setCity(taoOrder.getCity());
        address.setArea(taoOrder.getTown());
        address.setAddress(taoOrder.getAddress());
        addressMapper.insertTaoOrderAddress(address);
        return rows;
    }

    @Transactional
    @Override
    public int confirmOrder(TaoOrder taoOrder) {
        if(StringUtils.isNull(taoOrder.getShipType())){
            return -3;
        }
        if(taoOrder.getShipType() != 0 && taoOrder.getShipType() != 1){
            // 1 供应商发货 0 仓库发货
            return -4;
        }

        TaoOrder original = taoOrderMapper.selectTaoOrderById(taoOrder.getId());
        if(original.getAuditStatus() != 0) return -1;
        ErpOrder erpOrder = erpOrderMapper.selectErpOrderByNum(taoOrder.getId());
        if(erpOrder!=null) return -2;

        // 新增ErpOrder
        // 确认订单（操作：插入数据到s_shop_order、s_shop_order_item）
        ErpOrder so = new ErpOrder();
        so.setOrderNum(original.getId());
        so.setShopId(original.getShopId().intValue());
        so.setShopType(4);
        so.setRemark(original.getRemark());
        so.setBuyerMemo(original.getBuyerFeedback());
        so.setTag(original.getTag());
        so.setRefundStatus(1);
        so.setOrderStatus(1);
        so.setShipType(taoOrder.getShipType());

        so.setGoodsAmount(original.getTotalAmount().subtract(original.getShippingFee()));
        so.setDiscountAmount(original.getDiscountAmount());
        so.setAmount(original.getTotalAmount());
        so.setPostage(original.getShippingFee());

        so.setPayTime(original.getPayTime());
        so.setConfirmTime(new Date());
        so.setCreateTime(new Date());
        so.setCreateBy(taoOrder.getUpdateBy());

        TaoOrderAddress addr = addressMapper.selectTaoOrderAddressByOrderId(taoOrder.getId());
        so.setReceiverName(addr.getContactPerson());
        so.setReceiverPhone(addr.getMobile());
        so.setAddress(addr.getAddress());
        so.setCountry("中国");
        so.setProvince(addr.getProvince());
        so.setCity(addr.getCity());
        so.setTown(addr.getTown());

        erpOrderMapper.insertErpOrder(so);
        // 新增ErpOrderItem
        List<TaoOrderItem> taoOrderItems = taoOrderMapper.selectOrderItemByOrderId(taoOrder.getId());
        List<ErpOrderItem> items = new ArrayList<>();
        for (var i:taoOrderItems) {
            Goods goods = goodsMapper.selectGoodsById(i.getErpGoodsId());
            ErpOrderItem item = new ErpOrderItem();
            item.setOrderId(so.getId());
            item.setOrderItemNum(i.getSubItemId());
            item.setSupplierId(goods.getSupplierId().intValue());
            item.setGoodsId(i.getErpGoodsId());
            item.setSpecId(i.getErpGoodsSpecId());
            item.setGoodsTitle(i.getGoodsTitle());
            item.setGoodsImg(i.getProductImgUrl());
            item.setGoodsNum(i.getGoodsNumber());
            item.setSpecNum(i.getSpecNumber());
            item.setGoodsSpec(i.getSkuInfo());
            item.setGoodsPrice(i.getPrice());
            item.setItemAmount(i.getItemAmount());
            item.setQuantity(i.getQuantity().intValue());
            item.setIsGift(i.getIsGift().intValue());
            item.setRefundCount(0);
            item.setRefundStatus(1);
            item.setCreateBy(taoOrder.getUpdateBy());
            item.setCreateTime(new Date());
            items.add(item);
        }
        // 添加了赠品
        if(taoOrder.getTaoOrderItemList()!=null && !taoOrder.getTaoOrderItemList().isEmpty()){
            for (var g:taoOrder.getTaoOrderItemList()) {
                Goods goods = goodsMapper.selectGoodsById(g.getErpGoodsId());
                ErpOrderItem item = new ErpOrderItem();
                item.setOrderId(so.getId());
                item.setOrderItemNum(original.getId()+"_");
                item.setSupplierId(goods.getSupplierId().intValue());
                item.setGoodsId(g.getErpGoodsId());
                item.setSpecId(g.getErpGoodsSpecId());
                item.setGoodsTitle(g.getGoodsTitle());
                item.setGoodsImg(g.getProductImgUrl());
                item.setGoodsNum(g.getGoodsNumber());
                item.setSpecNum(g.getSpecNumber());
                item.setGoodsSpec(g.getSkuInfo());
                item.setGoodsPrice(g.getPrice());
                item.setItemAmount(g.getItemAmount());
                item.setQuantity(g.getQuantity().intValue());
                item.setIsGift(1);
                item.setRefundCount(0);
                item.setRefundStatus(1);
                item.setCreateBy(taoOrder.getUpdateBy());
                item.setCreateTime(new Date());
                items.add(item);
            }
        }
//        erpOrderMapper.batchErpOrderItem(items);

        // 新增代发表
        if(taoOrder.getShipType() == 1){
            for (ErpOrderItem it: items) {
                // 添加Erp_order_item
                erpOrderMapper.insertErpOrderItem(it);
                ScmSupplierAgentShipping agentShipping = new ScmSupplierAgentShipping();
                agentShipping.setShopId(original.getShopId());
                agentShipping.setShopType(4L);
                agentShipping.setSupplierId(it.getSupplierId().longValue());
                agentShipping.setOrderNum(original.getId());
//                agentShipping.setOrderItemId(it.getOrderItemNum());
                agentShipping.setOrderItemId(it.getId().toString());
                agentShipping.setOrderDate(original.getOrderCreateTime());
                agentShipping.setGoodsId(it.getGoodsId());
                agentShipping.setSpecId(it.getSpecId());
                agentShipping.setGoodsTitle(it.getGoodsTitle());
                agentShipping.setGoodsImg(it.getGoodsImg());
                agentShipping.setGoodsNum(it.getGoodsNum());
                agentShipping.setGoodsSpec(it.getGoodsSpec());
                agentShipping.setSpecNum(it.getSpecNum());
                agentShipping.setGoodsPrice(it.getGoodsPrice());
                agentShipping.setQuantity(it.getQuantity().longValue());
                agentShipping.setItemAmount(it.getItemAmount());
                agentShipping.setStatus(0L);
                agentShipping.setCreateTime(new Date());
                agentShipping.setCreateBy(taoOrder.getUpdateBy());

                agentShippingMapper.insertScmSupplierAgentShipping(agentShipping);
            }
        }else {
            // 仓库发货
            for (ErpOrderItem it: items) {
                erpOrderMapper.insertErpOrderItem(it);

                WmsOrderShipping shipping = new WmsOrderShipping();
                shipping.setShopId(original.getShopId());
                shipping.setShopType(4L);
                shipping.setOrderNum(original.getId());
//                shipping.setOrderItemId(it.getOrderItemNum());
                shipping.setOrderItemId(it.getId().toString());
                shipping.setOrderDate(original.getOrderCreateTime());
                shipping.setGoodsId(it.getGoodsId());
                shipping.setSpecId(it.getSpecId());
                shipping.setGoodsTitle(it.getGoodsTitle());
                shipping.setGoodsImg(it.getGoodsImg());
                shipping.setGoodsNum(it.getGoodsNum());
                shipping.setGoodsSpec(it.getGoodsSpec());
                shipping.setSpecNum(it.getSpecNum());
                shipping.setQuantity(it.getQuantity().longValue());
                shipping.setStatus(0L);
                shipping.setCreateTime(new Date());
                shipping.setCreateBy(taoOrder.getUpdateBy());
                orderShippingMapper.insertWmsOrderShipping(shipping);
            }
        }
        //更新自己
        TaoOrder update = new TaoOrder();
        update.setId(original.getId());
        update.setAuditStatus(1L);
        update.setAuditTime(new Date());
        update.setUpdateBy(taoOrder.getUpdateBy());
        update.setUpdateTime(new Date());
        taoOrderMapper.updateTaoOrder(update);
        return 1;
    }


    /**
     * 批量删除淘宝订单
     * 
     * @param ids 需要删除的淘宝订单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteTaoOrderByIds(Long[] ids)
    {
        taoOrderMapper.deleteTaoOrderItemByOrderIds(ids);
        return taoOrderMapper.deleteTaoOrderByIds(ids);
    }

    /**
     * 删除淘宝订单信息
     * 
     * @param id 淘宝订单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteTaoOrderById(Long id)
    {
        taoOrderMapper.deleteTaoOrderItemByOrderId(id);
        return taoOrderMapper.deleteTaoOrderById(id);
    }

    /**
     * 新增淘宝订单明细信息
     * 
     * @param taoOrder 淘宝订单对象
     */
    public void insertTaoOrderItem(TaoOrder taoOrder)
    {
        List<TaoOrderItem> taoOrderItemList = taoOrder.getTaoOrderItemList();
        String id = taoOrder.getId();
        if (StringUtils.isNotNull(taoOrderItemList))
        {
            List<TaoOrderItem> list = new ArrayList<TaoOrderItem>();
            for (TaoOrderItem taoOrderItem : taoOrderItemList)
            {
                taoOrderItem.setSubItemId(taoOrder.getId());
                taoOrderItem.setOrderId(id);
                taoOrderItem.setDiscountFee(BigDecimal.ZERO);
                taoOrderItem.setAdjustFee(BigDecimal.ZERO);
                taoOrderItem.setStatus("2");
                taoOrderItem.setRefundStatus(0L);
                taoOrderItem.setLogisticsStatus(0L);
                taoOrderItem.setNewSpecId(0L);
                taoOrderItem.setAfterSaleState(0L);
                taoOrderItem.setIsGift(0);
                taoOrderItem.setIsSwap(0);
                list.add(taoOrderItem);
            }
            if (list.size() > 0)
            {
                taoOrderMapper.batchTaoOrderItem(list);
            }
        }
    }
}
