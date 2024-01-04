package com.qihang.erp.api.domain;

import java.util.List;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhijian.common.annotation.Excel;
import com.zhijian.common.core.domain.BaseEntity;

/**
 * 订单对象 erp_order
 * 
 * @author qihang
 * @date 2024-01-04
 */
public class ErpOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 订单id，自增 */
    private Long id;

    /** 订单编号 */
    @Excel(name = "订单编号")
    private String orderNum;

    /** 店铺类型 */
    @Excel(name = "店铺类型")
    private Long shopType;

    /** 店铺ID */
    @Excel(name = "店铺ID")
    private Long shopId;

    /** 买家留言信息 */
    @Excel(name = "买家留言信息")
    private String buyerMemo;

    /** 标签 */
    @Excel(name = "标签")
    private String tag;

    /** 售后状态 1：无售后或售后关闭，2：售后处理中，3：退款中，4： 退款成功 5：全部 */
    @Excel(name = "售后状态 1：无售后或售后关闭，2：售后处理中，3：退款中，4： 退款成功 5：全部")
    private Long refundStatus;

    /** 订单状态1：待发货，2：已发货待签收，3：已签收 */
    @Excel(name = "订单状态1：待发货，2：已发货待签收，3：已签收")
    private Long orderStatus;

    /** 支付金额，单位：元，支付金额=商品金额-折扣金额+邮费 */
    @Excel(name = "支付金额，单位：元，支付金额=商品金额-折扣金额+邮费")
    private Double amount;

    /** 收件人姓名 */
    @Excel(name = "收件人姓名")
    private String receiverName;

    /** 收件人手机号 */
    @Excel(name = "收件人手机号")
    private String receiverPhone;

    /** 收件人地址 */
    @Excel(name = "收件人地址")
    private String address;

    /** 国家/地区 */
    @Excel(name = "国家/地区")
    private String country;

    /** 省 */
    @Excel(name = "省")
    private String province;

    /** 市 */
    @Excel(name = "市")
    private String city;

    /** 区 */
    @Excel(name = "区")
    private String town;

    /** 订单确认时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "订单确认时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date confirmTime;

//    /** 订单审核状态：0待确认，1已确认2已拦截-9未拉取 */
//    @Excel(name = "订单审核状态：0待确认，1已确认2已拦截-9未拉取")
//    private Long auditStatus;

    /** 发货时间 */
    @Excel(name = "发货时间")
    private Date shippingTime;

    /** 快递单号 */
    @Excel(name = "快递单号")
    private String shippingNumber;

    /** 物流公司 */
    @Excel(name = "物流公司")
    private String shippingCompany;

    /** 订单明细信息 */
    private List<ErpOrderItem> itemList;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setOrderNum(String orderNum) 
    {
        this.orderNum = orderNum;
    }

    public String getOrderNum() 
    {
        return orderNum;
    }
    public void setShopType(Long shopType) 
    {
        this.shopType = shopType;
    }

    public Long getShopType() 
    {
        return shopType;
    }
    public void setShopId(Long shopId) 
    {
        this.shopId = shopId;
    }

    public Long getShopId() 
    {
        return shopId;
    }
    public void setBuyerMemo(String buyerMemo) 
    {
        this.buyerMemo = buyerMemo;
    }

    public String getBuyerMemo() 
    {
        return buyerMemo;
    }
    public void setTag(String tag) 
    {
        this.tag = tag;
    }

    public String getTag() 
    {
        return tag;
    }
    public void setRefundStatus(Long refundStatus) 
    {
        this.refundStatus = refundStatus;
    }

    public Long getRefundStatus() 
    {
        return refundStatus;
    }
    public void setOrderStatus(Long orderStatus) 
    {
        this.orderStatus = orderStatus;
    }

    public Long getOrderStatus() 
    {
        return orderStatus;
    }
    public void setAmount(Double amount)
    {
        this.amount = amount;
    }

    public Double getAmount()
    {
        return amount;
    }
    public void setReceiverName(String receiverName) 
    {
        this.receiverName = receiverName;
    }

    public String getReceiverName() 
    {
        return receiverName;
    }
    public void setReceiverPhone(String receiverPhone) 
    {
        this.receiverPhone = receiverPhone;
    }

    public String getReceiverPhone() 
    {
        return receiverPhone;
    }
    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
    }
    public void setCountry(String country) 
    {
        this.country = country;
    }

    public String getCountry() 
    {
        return country;
    }
    public void setProvince(String province) 
    {
        this.province = province;
    }

    public String getProvince() 
    {
        return province;
    }
    public void setCity(String city) 
    {
        this.city = city;
    }

    public String getCity() 
    {
        return city;
    }
    public void setTown(String town) 
    {
        this.town = town;
    }

    public String getTown() 
    {
        return town;
    }
    public void setConfirmTime(Date confirmTime) 
    {
        this.confirmTime = confirmTime;
    }

    public Date getConfirmTime() 
    {
        return confirmTime;
    }
//    public void setAuditStatus(Long auditStatus)
//    {
//        this.auditStatus = auditStatus;
//    }
//
//    public Long getAuditStatus()
//    {
//        return auditStatus;
//    }
    public void setShippingTime(Date shippingTime)
    {
        this.shippingTime = shippingTime;
    }

    public Date getShippingTime()
    {
        return shippingTime;
    }
    public void setShippingNumber(String shippingNumber) 
    {
        this.shippingNumber = shippingNumber;
    }

    public String getShippingNumber() 
    {
        return shippingNumber;
    }
    public void setShippingCompany(String shippingCompany) 
    {
        this.shippingCompany = shippingCompany;
    }

    public String getShippingCompany() 
    {
        return shippingCompany;
    }

    public List<ErpOrderItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<ErpOrderItem> itemList) {
        this.itemList = itemList;
    }
}
