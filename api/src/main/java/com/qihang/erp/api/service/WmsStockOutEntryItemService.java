package com.qihang.erp.api.service;

import com.qihang.common.PageQuery;
import com.qihang.common.PageResult;
import com.qihang.erp.api.domain.WmsStockOutEntry;
import com.qihang.erp.api.domain.WmsStockOutEntryItem;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author TW
* @description 针对表【wms_stock_out_entry_item(出库单明细)】的数据库操作Service
* @createDate 2024-04-26 13:57:35
*/
public interface WmsStockOutEntryItemService extends IService<WmsStockOutEntryItem> {
    PageResult<WmsStockOutEntryItem> queryPageList(WmsStockOutEntryItem bo, PageQuery pageQuery);
}
