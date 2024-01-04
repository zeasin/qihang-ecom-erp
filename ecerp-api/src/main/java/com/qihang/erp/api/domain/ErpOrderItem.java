package com.qihang.erp.api.domain;

import com.zhijian.common.annotation.Excel;
import com.zhijian.common.core.domain.BaseEntity;

/**
 * 订单明细对象 erp_order_item
 * 
 * @author qihang
 * @date 2024-01-04
 */
public class ErpOrderItem extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id，自增 */
    private Long id;

    /** 订单ID */
    @Excel(name = "订单ID")
    private Long orderId;

    /** erp系统商品id */
    @Excel(name = "erp系统商品id")
    private Long goodsId;

    /** erp系统商品规格id */
    @Excel(name = "erp系统商品规格id")
    private Long specId;

    /** 商品标题 */
    @Excel(name = "商品标题")
    private String goodsTitle;

    /** 商品图片 */
    @Excel(name = "商品图片")
    private String goodsImg;

    /** 商品编码 */
    @Excel(name = "商品编码")
    private String goodsNum;

    /** 商品规格 */
    @Excel(name = "商品规格")
    private String goodsSpec;

    /** 商品规格编码 */
    @Excel(name = "商品规格编码")
    private String specNum;

    /** 商品单价 */
    @Excel(name = "商品单价")
    private Long goodsPrice;

    /** 子订单金额 */
    @Excel(name = "子订单金额")
    private Long itemAmount;

    /** 商品数量 */
    @Excel(name = "商品数量")
    private Long quantity;

    /** 是否赠品0否1是 */
    @Excel(name = "是否赠品0否1是")
    private Long isGift;

    /** 已退货数量 */
    @Excel(name = "已退货数量")
    private Long refundCount;

    /** 售后状态 1：无售后或售后关闭，2：售后处理中，3：退款中，4： 退款成功  */
    @Excel(name = "售后状态 1：无售后或售后关闭，2：售后处理中，3：退款中，4： 退款成功 ")
    private Long refundStatus;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setOrderId(Long orderId) 
    {
        this.orderId = orderId;
    }

    public Long getOrderId() 
    {
        return orderId;
    }
    public void setGoodsId(Long goodsId) 
    {
        this.goodsId = goodsId;
    }

    public Long getGoodsId() 
    {
        return goodsId;
    }
    public void setSpecId(Long specId) 
    {
        this.specId = specId;
    }

    public Long getSpecId() 
    {
        return specId;
    }
    public void setGoodsTitle(String goodsTitle) 
    {
        this.goodsTitle = goodsTitle;
    }

    public String getGoodsTitle() 
    {
        return goodsTitle;
    }
    public void setGoodsImg(String goodsImg) 
    {
        this.goodsImg = goodsImg;
    }

    public String getGoodsImg() 
    {
        return goodsImg;
    }
    public void setGoodsNum(String goodsNum) 
    {
        this.goodsNum = goodsNum;
    }

    public String getGoodsNum() 
    {
        return goodsNum;
    }
    public void setGoodsSpec(String goodsSpec) 
    {
        this.goodsSpec = goodsSpec;
    }

    public String getGoodsSpec() 
    {
        return goodsSpec;
    }
    public void setSpecNum(String specNum) 
    {
        this.specNum = specNum;
    }

    public String getSpecNum() 
    {
        return specNum;
    }
    public void setGoodsPrice(Long goodsPrice) 
    {
        this.goodsPrice = goodsPrice;
    }

    public Long getGoodsPrice() 
    {
        return goodsPrice;
    }
    public void setItemAmount(Long itemAmount) 
    {
        this.itemAmount = itemAmount;
    }

    public Long getItemAmount() 
    {
        return itemAmount;
    }
    public void setQuantity(Long quantity) 
    {
        this.quantity = quantity;
    }

    public Long getQuantity() 
    {
        return quantity;
    }
    public void setIsGift(Long isGift) 
    {
        this.isGift = isGift;
    }

    public Long getIsGift() 
    {
        return isGift;
    }
    public void setRefundCount(Long refundCount) 
    {
        this.refundCount = refundCount;
    }

    public Long getRefundCount() 
    {
        return refundCount;
    }
    public void setRefundStatus(Long refundStatus) 
    {
        this.refundStatus = refundStatus;
    }

    public Long getRefundStatus() 
    {
        return refundStatus;
    }


}
