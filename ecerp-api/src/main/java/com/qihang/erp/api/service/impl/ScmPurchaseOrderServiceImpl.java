package com.qihang.erp.api.service.impl;

import java.beans.Transient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.qihang.erp.api.domain.ScmPurchaseOrderItem;
import com.qihang.erp.api.domain.bo.PurchaseOrderAddBo;
import com.qihang.erp.api.mapper.ScmPurchaseOrderItemMapper;
import com.zhijian.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qihang.erp.api.mapper.ScmPurchaseOrderMapper;
import com.qihang.erp.api.domain.ScmPurchaseOrder;
import com.qihang.erp.api.service.IScmPurchaseOrderService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 采购订单Service业务层处理
 * 
 * @author qihang
 * @date 2023-12-29
 */
@Service
public class ScmPurchaseOrderServiceImpl implements IScmPurchaseOrderService 
{
    @Autowired
    private ScmPurchaseOrderMapper scmPurchaseOrderMapper;
    @Autowired
    private ScmPurchaseOrderItemMapper scmPurchaseOrderItemMapper;

    /**
     * 查询采购订单
     * 
     * @param id 采购订单主键
     * @return 采购订单
     */
    @Override
    public ScmPurchaseOrder selectScmPurchaseOrderById(Long id)
    {
        return scmPurchaseOrderMapper.selectScmPurchaseOrderById(id);
    }

    /**
     * 查询采购订单列表
     * 
     * @param scmPurchaseOrder 采购订单
     * @return 采购订单
     */
    @Override
    public List<ScmPurchaseOrder> selectScmPurchaseOrderList(ScmPurchaseOrder scmPurchaseOrder)
    {
        return scmPurchaseOrderMapper.selectScmPurchaseOrderList(scmPurchaseOrder);
    }

    /**
     * 新增采购订单
     * 
     * @param scmPurchaseOrder 采购订单
     * @return 结果
     */
    @Transactional
    @Override
    public int insertScmPurchaseOrder(PurchaseOrderAddBo addBo)
    {
        if(addBo.getGoodsList() == null || addBo.getGoodsList().isEmpty()) return -1;
        // 添加主表
        ScmPurchaseOrder scmPurchaseOrder = new ScmPurchaseOrder();
        scmPurchaseOrder.setOrderNo("PUR"+DateUtils.parseDateToStr("yyyyMMddHHmmss",new Date()));
        scmPurchaseOrder.setOrderAmount(addBo.getOrderAmount());
        scmPurchaseOrder.setCreateTime(DateUtils.getNowDate());
        scmPurchaseOrder.setOrderDate(addBo.getOrderDate());
        scmPurchaseOrder.setContactId(addBo.getContactId());
        scmPurchaseOrder.setOrderTime(System.currentTimeMillis()/1000);
        scmPurchaseOrder.setCreateBy(addBo.getCreateBy());
        scmPurchaseOrder.setStatus(0);
        scmPurchaseOrderMapper.insertScmPurchaseOrder(scmPurchaseOrder);

        // 添加子表
        for (var item:addBo.getGoodsList()) {
            ScmPurchaseOrderItem orderItem = new ScmPurchaseOrderItem();
            orderItem.setOrderDate(addBo.getOrderDate());
            orderItem.setOrderId(scmPurchaseOrder.getId());
            orderItem.setOrderNo(scmPurchaseOrder.getOrderNo());
            orderItem.setAmount(item.getAmount().doubleValue());
            orderItem.setGoodsId(item.getGoodsId());
            orderItem.setGoodsNum(item.getNumber());
            orderItem.setIsDelete(0);
            orderItem.setPrice(item.getPurPrice());
            orderItem.setQuantity(item.getQty());
            orderItem.setSpecId(item.getId());
            orderItem.setSpecNum(item.getSpecNum());
            orderItem.setStatus(0);
            orderItem.setTransType("Purchase");
            scmPurchaseOrderItemMapper.insertScmPurchaseOrderItem(orderItem);
        }
        return 1;
    }

    /**
     * 修改采购订单
     * 
     * @param scmPurchaseOrder 采购订单
     * @return 结果
     */
    @Override
    public int updateScmPurchaseOrder(ScmPurchaseOrder scmPurchaseOrder)
    {
        scmPurchaseOrder.setUpdateTime(DateUtils.getNowDate());
        return scmPurchaseOrderMapper.updateScmPurchaseOrder(scmPurchaseOrder);
    }

    /**
     * 批量删除采购订单
     * 
     * @param ids 需要删除的采购订单主键
     * @return 结果
     */
    @Override
    public int deleteScmPurchaseOrderByIds(Long[] ids)
    {
        return scmPurchaseOrderMapper.deleteScmPurchaseOrderByIds(ids);
    }

    /**
     * 删除采购订单信息
     * 
     * @param id 采购订单主键
     * @return 结果
     */
    @Override
    public int deleteScmPurchaseOrderById(Long id)
    {
        return scmPurchaseOrderMapper.deleteScmPurchaseOrderById(id);
    }
}
