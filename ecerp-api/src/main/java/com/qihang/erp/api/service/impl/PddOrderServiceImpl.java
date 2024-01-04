package com.qihang.erp.api.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.zhijian.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.qihang.erp.api.domain.PddOrderItem;
import com.qihang.erp.api.mapper.PddOrderMapper;
import com.qihang.erp.api.domain.PddOrder;
import com.qihang.erp.api.service.IPddOrderService;

/**
 * 拼多多订单Service业务层处理
 * 
 * @author qihang
 * @date 2024-01-02
 */
@Service
public class PddOrderServiceImpl implements IPddOrderService 
{
    @Autowired
    private PddOrderMapper pddOrderMapper;

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
        return pddOrderMapper.selectPddOrderList(pddOrder);
    }

    /**
     * 新增拼多多订单
     * 
     * @param pddOrder 拼多多订单
     * @return 结果
     */
    @Transactional
    @Override
    public int insertPddOrder(PddOrder pddOrder)
    {
        pddOrder.setTradeType(1L);
        pddOrder.setConfirmStatus(1L);
        pddOrder.setGroupStatus(1L);
        pddOrder.setRefundStatus(1L);
        pddOrder.setOrderStatus(1L);
        double discountAmount = pddOrder.getPlatformDiscount() + pddOrder.getSellerDiscount()+ pddOrder.getCapitalFreeDiscount();
        pddOrder.setDiscountAmount(discountAmount);
        double payAmount = pddOrder.getGoodsAmount() - pddOrder.getDiscountAmount() + pddOrder.getPostage();
        pddOrder.setPayAmount(payAmount);
        pddOrder.setAfterSalesStatus(0L);
        pddOrder.setOrderConfirmTime(0L);
        pddOrder.setAuditStatus(0L);
        pddOrder.setSettlementStatus(0L);
        pddOrder.setShipStatus(0L);
        int rows = pddOrderMapper.insertPddOrder(pddOrder);
        insertPddOrderItem(pddOrder);
        return rows;
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
