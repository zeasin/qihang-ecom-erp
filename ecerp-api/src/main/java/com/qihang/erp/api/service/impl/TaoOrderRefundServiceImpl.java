package com.qihang.erp.api.service.impl;

import java.util.Date;
import java.util.List;

import com.qihang.erp.api.domain.TaoOrder;
import com.qihang.erp.api.domain.TaoOrderItem;
import com.qihang.erp.api.mapper.TaoOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qihang.erp.api.mapper.TaoOrderRefundMapper;
import com.qihang.erp.api.domain.TaoOrderRefund;
import com.qihang.erp.api.service.ITaoOrderRefundService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 淘宝退款订单Service业务层处理
 * 
 * @author qihang
 * @date 2024-01-13
 */
@Service
public class TaoOrderRefundServiceImpl implements ITaoOrderRefundService 
{
    @Autowired
    private TaoOrderRefundMapper taoOrderRefundMapper;
    @Autowired
    private TaoOrderMapper taoOrderMapper;

    /**
     * 查询淘宝退款订单
     * 
     * @param id 淘宝退款订单主键
     * @return 淘宝退款订单
     */
    @Override
    public TaoOrderRefund selectTaoOrderRefundById(Long id)
    {
        return taoOrderRefundMapper.selectTaoOrderRefundById(id);
    }

    /**
     * 查询淘宝退款订单列表
     * 
     * @param taoOrderRefund 淘宝退款订单
     * @return 淘宝退款订单
     */
    @Override
    public List<TaoOrderRefund> selectTaoOrderRefundList(TaoOrderRefund taoOrderRefund)
    {
        return taoOrderRefundMapper.selectTaoOrderRefundList(taoOrderRefund);
    }

    /**
     * 新增淘宝退款订单
     * 
     * @param taoOrderRefund 淘宝退款订单
     * @return 结果
     */
    @Transactional
    @Override
    public int insertTaoOrderRefund(TaoOrderRefund taoOrderRefund)
    {
        // 查询
        TaoOrderItem taoOrderItem = taoOrderMapper.selectOrderItemBySubItemIdId(taoOrderRefund.getOid());
        if(taoOrderItem == null) return -1;
//        else if(taoOrderItem.getRefundStatus()!=0) return -2;
        TaoOrder taoOrder = taoOrderMapper.selectTaoOrderById(taoOrderItem.getOrderId());
        // 插入数据
        TaoOrderRefund refund = new TaoOrderRefund();
        refund.setRefundId(taoOrderRefund.getRefundId());
        refund.setAfterSalesType(taoOrderRefund.getAfterSalesType());
        refund.setShopId(taoOrder.getShopId());
        refund.setTid(taoOrderRefund.getTid());
        refund.setOid(taoOrderRefund.getOid());
        refund.setRefundFee(taoOrderRefund.getRefundFee());
        refund.setCreated(System.currentTimeMillis() / 1000);
        refund.setStatus(1L);
        refund.setNum(taoOrderRefund.getNum());
        refund.setAuditStatus(0L);
        refund.setProductId(taoOrderItem.getProductId());
        refund.setSkuId(taoOrderItem.getSkuId());
        refund.setGoodsTitle(taoOrderItem.getGoodsTitle());
        refund.setGoodsNumber(taoOrderItem.getGoodsNumber());
        refund.setSpecNumber(taoOrderItem.getSpecNumber());
        refund.setProductImgUrl(taoOrderItem.getProductImgUrl());
        refund.setSkuInfo(taoOrderItem.getSkuInfo());
        refund.setCreateTime(new Date());
        refund.setCreateBy(taoOrderRefund.getCreateBy());

        taoOrderRefundMapper.insertTaoOrderRefund(refund);

        // 更新 order_item 状态
        TaoOrderItem up = new TaoOrderItem();
        up.setId(taoOrderItem.getId());
        up.setRefundStatus(1L);
        up.setUpdateBy(taoOrderRefund.getCreateBy());
        up.setUpdateTime(new Date());
        taoOrderMapper.updateTaoOrderItem(up);

        return 1;
    }

    /**
     * 修改淘宝退款订单
     * 
     * @param taoOrderRefund 淘宝退款订单
     * @return 结果
     */
    @Override
    public int updateTaoOrderRefund(TaoOrderRefund taoOrderRefund)
    {
        return taoOrderRefundMapper.updateTaoOrderRefund(taoOrderRefund);
    }

    /**
     * 批量删除淘宝退款订单
     * 
     * @param ids 需要删除的淘宝退款订单主键
     * @return 结果
     */
    @Override
    public int deleteTaoOrderRefundByIds(Long[] ids)
    {
        return taoOrderRefundMapper.deleteTaoOrderRefundByIds(ids);
    }

    /**
     * 删除淘宝退款订单信息
     * 
     * @param id 淘宝退款订单主键
     * @return 结果
     */
    @Override
    public int deleteTaoOrderRefundById(Long id)
    {
        return taoOrderRefundMapper.deleteTaoOrderRefundById(id);
    }
}
