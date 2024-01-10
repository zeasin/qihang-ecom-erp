package com.qihang.erp.api.domain.bo;

import java.util.Date;

public class StockingAddVo {
    private String stockOutNum;
    private Date createTime;
    private Long[] ids;

    public String getStockOutNum() {
        return stockOutNum;
    }

    public void setStockOutNum(String stockOutNum) {
        this.stockOutNum = stockOutNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }
}
