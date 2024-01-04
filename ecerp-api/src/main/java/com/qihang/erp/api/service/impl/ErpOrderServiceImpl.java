package com.qihang.erp.api.service.impl;

import java.util.Date;
import java.util.List;
import com.zhijian.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.zhijian.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.qihang.erp.api.domain.ErpOrderItem;
import com.qihang.erp.api.mapper.ErpOrderMapper;
import com.qihang.erp.api.domain.ErpOrder;
import com.qihang.erp.api.service.IErpOrderService;

/**
 * 订单Service业务层处理
 * 
 * @author qihang
 * @date 2024-01-04
 */
@Service
public class ErpOrderServiceImpl implements IErpOrderService 
{
    @Autowired
    private ErpOrderMapper erpOrderMapper;

    /**
     * 查询订单
     * 
     * @param id 订单主键
     * @return 订单
     */
    @Override
    public ErpOrder selectErpOrderById(Long id)
    {
        return erpOrderMapper.selectErpOrderById(id);
    }

    /**
     * 查询订单列表
     * 
     * @param erpOrder 订单
     * @return 订单
     */
    @Override
    public List<ErpOrder> selectErpOrderList(ErpOrder erpOrder)
    {
        return erpOrderMapper.selectErpOrderList(erpOrder);
    }

    /**
     * 新增订单
     * 
     * @param erpOrder 订单
     * @return 结果
     */
    @Transactional
    @Override
    public int insertErpOrder(ErpOrder erpOrder)
    {
//        erpOrder.setCreateTime(DateUtils.getNowDate());
//        int rows = erpOrderMapper.insertErpOrder(erpOrder);
//        insertErpOrderItem(erpOrder);
//        return rows;
        if(erpOrder.getItemList() == null || erpOrder.getItemList().size() == 0) return -1;
        erpOrder.setOrderStatus(1L);
        erpOrder.setRefundStatus(1L);
//        if(erpOrder.getPostage() == null)shopOrder.setPostage(0L);
//        if(erpOrder.getDiscountAmount() == null)shopOrder.setDiscountAmount(0L);
//        if(erpOrder.getPayAmount() == null)shopOrder.setPayAmount(0L);
//        if(erpOrder.getAuditStatus() == null) shopOrder.setAuditStatus(1L);

        erpOrder.setCreateBy(erpOrder.getCreateBy());
        erpOrder.setCreateTime(DateUtils.getNowDate());
        int rows = erpOrderMapper.insertErpOrder(erpOrder);
        insertErpOrderItem(erpOrder);
//        insertSShopOrderItem(shopOrder);
        return rows;
    }

    /**
     * 修改订单
     * 
     * @param erpOrder 订单
     * @return 结果
     */
    @Transactional
    @Override
    public int updateErpOrder(ErpOrder erpOrder)
    {
        erpOrder.setUpdateTime(DateUtils.getNowDate());
        erpOrderMapper.deleteErpOrderItemByOrderId(erpOrder.getId());
        insertErpOrderItem(erpOrder);
        return erpOrderMapper.updateErpOrder(erpOrder);
    }

    /**
     * 批量删除订单
     * 
     * @param ids 需要删除的订单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteErpOrderByIds(Long[] ids)
    {
        erpOrderMapper.deleteErpOrderItemByOrderIds(ids);
        return erpOrderMapper.deleteErpOrderByIds(ids);
    }

    /**
     * 删除订单信息
     * 
     * @param id 订单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteErpOrderById(Long id)
    {
        erpOrderMapper.deleteErpOrderItemByOrderId(id);
        return erpOrderMapper.deleteErpOrderById(id);
    }

    /**
     * 新增订单明细信息
     * 
     * @param erpOrder 订单对象
     */
    public void insertErpOrderItem(ErpOrder erpOrder)
    {
        List<ErpOrderItem> erpOrderItemList = erpOrder.getItemList();
        Long id = erpOrder.getId();
        if (StringUtils.isNotNull(erpOrderItemList))
        {
            List<ErpOrderItem> list = new ArrayList<ErpOrderItem>();
            for (ErpOrderItem erpOrderItem : erpOrderItemList)
            {


                erpOrderItem.setOrderId(id);
                erpOrderItem.setRefundCount(0L);
                erpOrderItem.setRefundStatus(1L);
                erpOrderItem.setCreateBy(erpOrder.getCreateBy());
                erpOrderItem.setCreateTime(new Date());
                list.add(erpOrderItem);
            }
            if (list.size() > 0)
            {
                erpOrderMapper.batchErpOrderItem(list);
            }
        }
    }
}
