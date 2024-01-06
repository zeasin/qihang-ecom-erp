package com.qihang.erp.api.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.qihang.erp.api.domain.TaoOrderItem;
import com.qihang.erp.api.domain.XhsOrderReceiver;
import com.qihang.erp.api.mapper.XhsOrderReceiverMapper;
import com.zhijian.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.zhijian.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.qihang.erp.api.domain.XhsOrderItem;
import com.qihang.erp.api.mapper.XhsOrderMapper;
import com.qihang.erp.api.domain.XhsOrder;
import com.qihang.erp.api.service.IXhsOrderService;

/**
 * 小红书订单Service业务层处理
 * 
 * @author qihang
 * @date 2024-01-03
 */
@Service
public class XhsOrderServiceImpl implements IXhsOrderService 
{
    @Autowired
    private XhsOrderMapper xhsOrderMapper;
    @Autowired
    private XhsOrderReceiverMapper receiverMapper;

    /**
     * 查询小红书订单
     * 
     * @param id 小红书订单主键
     * @return 小红书订单
     */
    @Override
    public XhsOrder selectXhsOrderById(Long id)
    {
        return xhsOrderMapper.selectXhsOrderById(id);
    }

    /**
     * 查询小红书订单列表
     * 
     * @param xhsOrder 小红书订单
     * @return 小红书订单
     */
    @Override
    public List<XhsOrder> selectXhsOrderList(XhsOrder xhsOrder)
    {
        var orderList = xhsOrderMapper.selectXhsOrderList(xhsOrder);
        for (var o:orderList) {
            List<XhsOrderItem> items = xhsOrderMapper.selectOrderItemByOrderId(o.getId());
            o.setXhsOrderItemList(items);
        }
        return orderList;
    }

    /**
     * 新增小红书订单
     * 
     * @param xhsOrder 小红书订单
     * @return 结果
     */
    @Transactional
    @Override
    public int insertXhsOrder(XhsOrder xhsOrder)
    {
        xhsOrder.setShopType(7L);
        xhsOrder.setOrderType(1L);
        xhsOrder.setOrderStatus(2L);
        xhsOrder.setAfterSalesStatus(1L);
        xhsOrder.setCancelStatus(0L);
        xhsOrder.setOrderPaidTime(0L);
        xhsOrder.setOrderUpdateTime(0L);
        xhsOrder.setOrderDeliveryTime(0L);
        xhsOrder.setOrderCancelTime(0L);
        xhsOrder.setOrderFinishTime(0L);
        xhsOrder.setPromiseLastDeliveryTime(0L);
        xhsOrder.setOrderCreatedTime(xhsOrder.getCreateTime().getTime());
        xhsOrder.setPresaleDeliveryStartTime(0L);
        xhsOrder.setPresaleDeliveryEndTime(0L);
        Double totalAmount = (xhsOrder.getGoodsAmount() + xhsOrder.getShippingFree()) *100;
        xhsOrder.setTotalPayAmount(totalAmount.longValue());
        xhsOrder.setTotalShippingFree(xhsOrder.getShippingFree().longValue());
        xhsOrder.setOpenAddressId("0");
        xhsOrder.setAuditStatus(0L);
        xhsOrder.setSettleStatus(0L);
        xhsOrder.setSettleAmount(BigDecimal.ZERO);
        xhsOrder.setSendStatus(0L);
        xhsOrder.setCreateTime(DateUtils.getNowDate());

        int rows = xhsOrderMapper.insertXhsOrder(xhsOrder);
        insertXhsOrderItem(xhsOrder);

        // 地址
        XhsOrderReceiver receiver = new XhsOrderReceiver();
        receiver.setOrderId(xhsOrder.getId());
        receiver.setReceiver(xhsOrder.getReceiver());
        receiver.setPhone(xhsOrder.getPhone());
        receiver.setCountry("中国");
        receiver.setProvince(xhsOrder.getProvince());
        receiver.setCity(xhsOrder.getCity());
        receiver.setDistrict(xhsOrder.getDistrict());
        receiver.setAddress(xhsOrder.getAddress());
        receiverMapper.insertXhsOrderReceiver(receiver);

        return rows;
    }

    /**
     * 修改小红书订单
     * 
     * @param xhsOrder 小红书订单
     * @return 结果
     */
    @Transactional
    @Override
    public int updateXhsOrder(XhsOrder xhsOrder)
    {
        xhsOrder.setUpdateTime(DateUtils.getNowDate());
        xhsOrderMapper.deleteXhsOrderItemByOrderId(xhsOrder.getId());
        insertXhsOrderItem(xhsOrder);
        return xhsOrderMapper.updateXhsOrder(xhsOrder);
    }

    /**
     * 批量删除小红书订单
     * 
     * @param ids 需要删除的小红书订单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteXhsOrderByIds(Long[] ids)
    {
        xhsOrderMapper.deleteXhsOrderItemByOrderIds(ids);
        return xhsOrderMapper.deleteXhsOrderByIds(ids);
    }

    /**
     * 删除小红书订单信息
     * 
     * @param id 小红书订单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteXhsOrderById(Long id)
    {
        xhsOrderMapper.deleteXhsOrderItemByOrderId(id);
        return xhsOrderMapper.deleteXhsOrderById(id);
    }

    /**
     * 新增小红书订单明细信息
     * 
     * @param xhsOrder 小红书订单对象
     */
    public void insertXhsOrderItem(XhsOrder xhsOrder)
    {
        List<XhsOrderItem> xhsOrderItemList = xhsOrder.getXhsOrderItemList();
        Long id = xhsOrder.getId();
        if (StringUtils.isNotNull(xhsOrderItemList))
        {
            List<XhsOrderItem> list = new ArrayList<XhsOrderItem>();
            for (XhsOrderItem xhsOrderItem : xhsOrderItemList)
            {
                Double t = xhsOrderItem.getItemAmount()*100;
                xhsOrderItem.setTotalPaidAmount(t.longValue());
                xhsOrderItem.setTotalMerchantDiscount(0L);
                xhsOrderItem.setTotalRedDiscount(0L);
                xhsOrderItem.setItemTag(0L);
                xhsOrderItem.setErpSendStatus(0L);
                xhsOrderItem.setOrderId(id);
                list.add(xhsOrderItem);
            }
            if (list.size() > 0)
            {
                xhsOrderMapper.batchXhsOrderItem(list);
            }
        }
    }
}
