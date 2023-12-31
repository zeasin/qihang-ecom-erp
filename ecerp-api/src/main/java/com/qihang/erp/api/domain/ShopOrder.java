package com.qihang.erp.api.domain;

import java.util.List;
import com.zhijian.common.annotation.Excel;
import com.zhijian.common.core.domain.BaseEntity;

/**
 * 店铺订单对象 s_shop_order
 * 
 * @author qihang
 * @date 2023-12-31
 */
public class ShopOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 订单id，自增 */
    private Long id;

    /** 订单编号 */
    @Excel(name = "订单编号")
    private String orderNum;

    /** 内部店铺ID */
    @Excel(name = "内部店铺ID")
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

    /** 邮费，单位：元 */
    @Excel(name = "邮费，单位：元")
    private Long postage;

    /** 折扣金额，单位：元，折扣金额=平台优惠+商家优惠+团长免单优惠金额 */
    @Excel(name = "折扣金额，单位：元，折扣金额=平台优惠+商家优惠+团长免单优惠金额")
    private Long discountAmount;

    /** 商品金额，单位：元，商品金额=商品销售价格*商品数量-改价金额（接口暂无该字段） */
    @Excel(name = "商品金额，单位：元，商品金额=商品销售价格*商品数量-改价金额", readConverterExp = "接=口暂无该字段")
    private Long goodsAmount;

    /** 支付金额，单位：元，支付金额=商品金额-折扣金额+邮费 */
    @Excel(name = "支付金额，单位：元，支付金额=商品金额-折扣金额+邮费")
    private Long payAmount;

    /** 支付时间 */
    @Excel(name = "支付时间")
    private String payTime;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String receiverName;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String receiverPhone;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String address;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String town;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String city;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String province;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String country;

    /** 订单审核时间 */
    @Excel(name = "订单审核时间")
    private String auditTime;

    /** 订单审核状态：0待确认，1已确认2已拦截-9未拉取 */
    @Excel(name = "订单审核状态：0待确认，1已确认2已拦截-9未拉取")
    private Long auditStatus;

    /** 发货时间 */
    @Excel(name = "发货时间")
    private String shippingTime;

    /** 快递单号 */
    @Excel(name = "快递单号")
    private String shippingNumber;

    /** 物流公司 */
    @Excel(name = "物流公司")
    private String shippingCompany;

    /** $table.subTable.functionName信息 */
    private List<SShopOrderItem> sShopOrderItemList;

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
    public void setPostage(Long postage) 
    {
        this.postage = postage;
    }

    public Long getPostage() 
    {
        return postage;
    }
    public void setDiscountAmount(Long discountAmount) 
    {
        this.discountAmount = discountAmount;
    }

    public Long getDiscountAmount() 
    {
        return discountAmount;
    }
    public void setGoodsAmount(Long goodsAmount) 
    {
        this.goodsAmount = goodsAmount;
    }

    public Long getGoodsAmount() 
    {
        return goodsAmount;
    }
    public void setPayAmount(Long payAmount) 
    {
        this.payAmount = payAmount;
    }

    public Long getPayAmount() 
    {
        return payAmount;
    }
    public void setPayTime(String payTime) 
    {
        this.payTime = payTime;
    }

    public String getPayTime() 
    {
        return payTime;
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
    public void setTown(String town) 
    {
        this.town = town;
    }

    public String getTown() 
    {
        return town;
    }
    public void setCity(String city) 
    {
        this.city = city;
    }

    public String getCity() 
    {
        return city;
    }
    public void setProvince(String province) 
    {
        this.province = province;
    }

    public String getProvince() 
    {
        return province;
    }
    public void setCountry(String country) 
    {
        this.country = country;
    }

    public String getCountry() 
    {
        return country;
    }
    public void setAuditTime(String auditTime) 
    {
        this.auditTime = auditTime;
    }

    public String getAuditTime() 
    {
        return auditTime;
    }
    public void setAuditStatus(Long auditStatus) 
    {
        this.auditStatus = auditStatus;
    }

    public Long getAuditStatus() 
    {
        return auditStatus;
    }
    public void setShippingTime(String shippingTime) 
    {
        this.shippingTime = shippingTime;
    }

    public String getShippingTime() 
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

    public List<SShopOrderItem> getSShopOrderItemList()
    {
        return sShopOrderItemList;
    }

    public void setSShopOrderItemList(List<SShopOrderItem> sShopOrderItemList)
    {
        this.sShopOrderItemList = sShopOrderItemList;
    }

}
