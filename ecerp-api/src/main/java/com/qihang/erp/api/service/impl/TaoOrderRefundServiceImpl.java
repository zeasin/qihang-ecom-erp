package com.qihang.erp.api.service.impl;

import java.util.Date;
import java.util.List;

import com.qihang.erp.api.domain.*;
import com.qihang.erp.api.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    @Autowired
    private GoodsSpecMapper goodsSpecMapper;
    @Autowired
    private ErpOrderReturnedMapper erpOrderReturnedMapper;

    @Autowired
    private ErpOrderMapper erpOrderMapper;

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
        else if(taoOrderItem.getRefundStatus()!=0) return -2;
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
    public int confirmRefund(TaoOrderRefund taoOrderRefund)
    {
        TaoOrderRefund refund = taoOrderRefundMapper.selectTaoOrderRefundById(taoOrderRefund.getId());
        if (refund == null) return -1;
        else if(refund.getAuditStatus() != 0) return -2;

        // 查询erp_order_item
        ErpOrderItem select = new ErpOrderItem();
        select.setOrderItemNum(refund.getOid().toString());
        select.setShopId(refund.getShopId().intValue());
        ErpOrderItem erpOrderItem = erpOrderMapper.selectOrderItemByOrderItemNum(select);
        if(erpOrderItem == null) return -21;

//        // 查询erp_goods_spec
//        GoodsSpec goodsSpec = new GoodsSpec();
//        goodsSpec.setSpecNum(taoOrderRefund.getSpecNumber());
//        List<GoodsSpec> goodsSpecs = goodsSpecMapper.selectGoodsSpecList(goodsSpec);
//        if(goodsSpecs==null || goodsSpecs.size() ==0) return -11;

        // 插入到erp_order_returned
        ErpOrderReturned returned = new ErpOrderReturned();
        returned.setReturnedNum(refund.getRefundId());
        if(refund.getAfterSalesType() == 1){
            returned.setReturnedType(1L);
        }else if(refund.getAfterSalesType() == 3){
            returned.setReturnedType(2L);
        }
        returned.setOrderNum(refund.getTid()+"");
        returned.setOrderId(erpOrderItem.getOrderId());
        returned.setOrderItemId(erpOrderItem.getId());
        returned.setShopId(refund.getShopId());
        returned.setShopType(4L);
        returned.setGoodsId(erpOrderItem.getGoodsId());
        returned.setSpecId(erpOrderItem.getSpecId());
        returned.setGoodsNum(refund.getGoodsNumber());
        returned.setSpecNum(refund.getSpecNumber());
        returned.setGoodsName(refund.getGoodsTitle());
        returned.setGoodsSpec(refund.getSkuInfo());
        returned.setGoodsImage(refund.getProductImgUrl());
        returned.setQuantity(refund.getNum());
        returned.setLogisticsCompany(taoOrderRefund.getLogisticsCompany());
        returned.setLogisticsCode(taoOrderRefund.getLogisticsCode());
        returned.setRemark(taoOrderRefund.getRemark());
        returned.setStatus(1L);
        returned.setCreateBy(taoOrderRefund.getUpdateBy());
        returned.setCreateTime(new Date());
        erpOrderReturnedMapper.insertErpOrderReturned(returned);

        // 更新自己
        TaoOrderRefund up = new TaoOrderRefund();
        up.setId(taoOrderRefund.getId());
        up.setAuditStatus(1L);
        up.setAuditTime(new Date());
        up.setUpdateBy(taoOrderRefund.getUpdateBy());
        up.setUpdateTime(new Date());
        up.setLogisticsCompany(taoOrderRefund.getLogisticsCompany());
        up.setLogisticsCode(taoOrderRefund.getLogisticsCode());
        up.setSendTime(taoOrderRefund.getSendTime());
        taoOrderRefundMapper.updateTaoOrderRefund(up);

        return 1;
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
