package com.qihang.erp.api.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhijian.common.annotation.Excel;
import com.zhijian.common.core.domain.BaseEntity;

/**
 * 淘宝退款订单对象 s_tao_order_refund
 * 
 * @author qihang
 * @date 2024-01-03
 */
public class TaoOrderRefund extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 退款id */
    @Excel(name = "退款id")
    private String refundId;

    /** 类型（1退货3换货） */
    @Excel(name = "类型", readConverterExp = "1=退货3换货")
    private Long afterSalesType;

    /** 淘宝交易单号（订单号） */
    @Excel(name = "淘宝交易单号", readConverterExp = "订=单号")
    private Long tid;

    /** 子订单号。如果是单笔交易oid会等于tid */
    @Excel(name = "子订单号。如果是单笔交易oid会等于tid")
    private Long oid;

    /** 买家昵称 */
    @Excel(name = "买家昵称")
    private String buyerNick;

    /** 交易总金额。精确到2位小数;单位:元。如:200.07，表示:200元7分 */
    @Excel(name = "交易总金额。精确到2位小数;单位:元。如:200.07，表示:200元7分")
    private String totalFee;

    /** 支付给卖家的金额(交易总金额-退还给买家的金额)。精确到2位小数;单位:元。如:200.07，表示:200元7分 */
    @Excel(name = "支付给卖家的金额(交易总金额-退还给买家的金额)。精确到2位小数;单位:元。如:200.07，表示:200元7分")
    private String payment;

    /** 退还金额(退还给买家的金额)。精确到2位小数;单位:元。如:200.07，表示:200元7分 */
    @Excel(name = "退还金额(退还给买家的金额)。精确到2位小数;单位:元。如:200.07，表示:200元7分")
    private BigDecimal refundFee;

    /** 退款申请时间。 */
    @Excel(name = "退款申请时间。")
    private Long created;

    /** 更新时间。 */
    @Excel(name = "更新时间。")
    private Long modified;

    /** 退款对应的订单交易状态。 */
    @Excel(name = "退款对应的订单交易状态。")
    private String orderStatus;

    /** 退款状态。可选值WAIT_SELLER_AGREE(买家已经申请退款，等待卖家同意) WAIT_BUYER_RETURN_GOODS(卖家已经同意退款，等待买家退货) WAIT_SELLER_CONFIRM_GOODS(买家已经退货，等待卖家确认收货) SELLER_REFUSE_BUYER(卖家拒绝退款) CLOSED(退款关闭) SUCCESS(退款成功) */
    @Excel(name = "退款状态。可选值WAIT_SELLER_AGREE(买家已经申请退款，等待卖家同意) WAIT_BUYER_RETURN_GOODS(卖家已经同意退款，等待买家退货) WAIT_SELLER_CONFIRM_GOODS(买家已经退货，等待卖家确认收货) SELLER_REFUSE_BUYER(卖家拒绝退款) CLOSED(退款关闭) SUCCESS(退款成功)")
    private Long status;

    /** 货物状态。可选值BUYER_NOT_RECEIVED (买家未收到货) BUYER_RECEIVED (买家已收到货) BUYER_RETURNED_GOODS (买家已退货) */
    @Excel(name = "货物状态。可选值BUYER_NOT_RECEIVED (买家未收到货) BUYER_RECEIVED (买家已收到货) BUYER_RETURNED_GOODS (买家已退货)")
    private String goodStatus;

    /** 退货数量 */
    @Excel(name = "退货数量")
    private Long num;

    /** 买家是否需要退货。可选值:true(是),false(否) */
    @Excel(name = "买家是否需要退货。可选值:true(是),false(否)")
    private Long hasGoodReturn;

    /** 退款原因 */
    @Excel(name = "退款原因")
    private String reason;

    /** 退款说明 */
    @Excel(name = "退款说明")
    private String desc;

    /** 物流公司 */
    @Excel(name = "物流公司")
    private String logisticsCompany;

    /** 物流单号 */
    @Excel(name = "物流单号")
    private String logisticsCode;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String sendTime;

    /** 2已签收9供应商已退款 */
    @Excel(name = "2已签收9供应商已退款")
    private Long auditStatus;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date auditTime;

    /** 收货时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "收货时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date receivedTime;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String address;

    /** 系统创建时间 */
    @Excel(name = "系统创建时间")
    private Long createOn;

    /** 店铺id */
    @Excel(name = "店铺id")
    private Long shopId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long erpGoodsId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long erpGoodsSpecId;

    /** sku编号 */
    @Excel(name = "sku编号")
    private String specNumber;

    /** 退款阶段，可选值：onsale/aftersale */
    @Excel(name = "退款阶段，可选值：onsale/aftersale")
    private String refundPhase;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setRefundId(String refundId) 
    {
        this.refundId = refundId;
    }

    public String getRefundId() 
    {
        return refundId;
    }
    public void setAfterSalesType(Long afterSalesType) 
    {
        this.afterSalesType = afterSalesType;
    }

    public Long getAfterSalesType() 
    {
        return afterSalesType;
    }
    public void setTid(Long tid) 
    {
        this.tid = tid;
    }

    public Long getTid() 
    {
        return tid;
    }
    public void setOid(Long oid) 
    {
        this.oid = oid;
    }

    public Long getOid() 
    {
        return oid;
    }
    public void setBuyerNick(String buyerNick) 
    {
        this.buyerNick = buyerNick;
    }

    public String getBuyerNick() 
    {
        return buyerNick;
    }
    public void setTotalFee(String totalFee) 
    {
        this.totalFee = totalFee;
    }

    public String getTotalFee() 
    {
        return totalFee;
    }
    public void setPayment(String payment) 
    {
        this.payment = payment;
    }

    public String getPayment() 
    {
        return payment;
    }
    public void setRefundFee(BigDecimal refundFee) 
    {
        this.refundFee = refundFee;
    }

    public BigDecimal getRefundFee() 
    {
        return refundFee;
    }
    public void setCreated(Long created) 
    {
        this.created = created;
    }

    public Long getCreated() 
    {
        return created;
    }
    public void setModified(Long modified) 
    {
        this.modified = modified;
    }

    public Long getModified() 
    {
        return modified;
    }
    public void setOrderStatus(String orderStatus) 
    {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatus() 
    {
        return orderStatus;
    }
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }
    public void setGoodStatus(String goodStatus) 
    {
        this.goodStatus = goodStatus;
    }

    public String getGoodStatus() 
    {
        return goodStatus;
    }
    public void setNum(Long num) 
    {
        this.num = num;
    }

    public Long getNum() 
    {
        return num;
    }
    public void setHasGoodReturn(Long hasGoodReturn) 
    {
        this.hasGoodReturn = hasGoodReturn;
    }

    public Long getHasGoodReturn() 
    {
        return hasGoodReturn;
    }
    public void setReason(String reason) 
    {
        this.reason = reason;
    }

    public String getReason() 
    {
        return reason;
    }
    public void setDesc(String desc) 
    {
        this.desc = desc;
    }

    public String getDesc() 
    {
        return desc;
    }
    public void setLogisticsCompany(String logisticsCompany) 
    {
        this.logisticsCompany = logisticsCompany;
    }

    public String getLogisticsCompany() 
    {
        return logisticsCompany;
    }
    public void setLogisticsCode(String logisticsCode) 
    {
        this.logisticsCode = logisticsCode;
    }

    public String getLogisticsCode() 
    {
        return logisticsCode;
    }
    public void setSendTime(String sendTime) 
    {
        this.sendTime = sendTime;
    }

    public String getSendTime() 
    {
        return sendTime;
    }
    public void setAuditStatus(Long auditStatus) 
    {
        this.auditStatus = auditStatus;
    }

    public Long getAuditStatus() 
    {
        return auditStatus;
    }
    public void setAuditTime(Date auditTime) 
    {
        this.auditTime = auditTime;
    }

    public Date getAuditTime() 
    {
        return auditTime;
    }
    public void setReceivedTime(Date receivedTime) 
    {
        this.receivedTime = receivedTime;
    }

    public Date getReceivedTime() 
    {
        return receivedTime;
    }
    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
    }
    public void setCreateOn(Long createOn) 
    {
        this.createOn = createOn;
    }

    public Long getCreateOn() 
    {
        return createOn;
    }
    public void setShopId(Long shopId) 
    {
        this.shopId = shopId;
    }

    public Long getShopId() 
    {
        return shopId;
    }
    public void setErpGoodsId(Long erpGoodsId) 
    {
        this.erpGoodsId = erpGoodsId;
    }

    public Long getErpGoodsId() 
    {
        return erpGoodsId;
    }
    public void setErpGoodsSpecId(Long erpGoodsSpecId) 
    {
        this.erpGoodsSpecId = erpGoodsSpecId;
    }

    public Long getErpGoodsSpecId() 
    {
        return erpGoodsSpecId;
    }
    public void setSpecNumber(String specNumber) 
    {
        this.specNumber = specNumber;
    }

    public String getSpecNumber() 
    {
        return specNumber;
    }
    public void setRefundPhase(String refundPhase) 
    {
        this.refundPhase = refundPhase;
    }

    public String getRefundPhase() 
    {
        return refundPhase;
    }

}
