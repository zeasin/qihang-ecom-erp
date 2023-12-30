package com.qihang.erp.api.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhijian.common.annotation.Excel;
import com.zhijian.common.core.domain.BaseEntity;

/**
 * 采购订单物流对象 scm_purchase_order_ship
 * 
 * @author qihang
 * @date 2023-12-30
 */
public class ScmPurchaseOrderShip extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 采购单ID（主键） */
    private Long id;

    /** 物流公司 */
    @Excel(name = "物流公司")
    private String shipCompany;

    /** 物流单号 */
    @Excel(name = "物流单号")
    private String shipNo;

    /** 运费 */
    @Excel(name = "运费")
    private BigDecimal freight;

    /** 运送时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "运送时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date shipTime;

    /** 状态（0未收货1已收货2已入库） */
    @Excel(name = "状态", readConverterExp = "0=未收货1已收货2已入库")
    private Long status;

    /** 退回数量 */
    @Excel(name = "退回数量")
    private Long backCount;

    /** 入库数量 */
    @Excel(name = "入库数量")
    private Long stockInCount;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setShipCompany(String shipCompany) 
    {
        this.shipCompany = shipCompany;
    }

    public String getShipCompany() 
    {
        return shipCompany;
    }
    public void setShipNo(String shipNo) 
    {
        this.shipNo = shipNo;
    }

    public String getShipNo() 
    {
        return shipNo;
    }
    public void setFreight(BigDecimal freight)
    {
        this.freight = freight;
    }

    public BigDecimal getFreight()
    {
        return freight;
    }
    public void setShipTime(Date shipTime) 
    {
        this.shipTime = shipTime;
    }

    public Date getShipTime() 
    {
        return shipTime;
    }
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }
    public void setBackCount(Long backCount) 
    {
        this.backCount = backCount;
    }

    public Long getBackCount() 
    {
        return backCount;
    }
    public void setStockInCount(Long stockInCount) 
    {
        this.stockInCount = stockInCount;
    }

    public Long getStockInCount() 
    {
        return stockInCount;
    }

}
