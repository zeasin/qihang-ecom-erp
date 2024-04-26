package com.qihang.erp.api.controller;

import com.qihang.common.PageQuery;
import com.qihang.common.PageResult;
import com.qihang.core.controller.BaseController;
import com.qihang.core.domain.AjaxResult;
import com.qihang.core.page.TableDataInfo;
import com.qihang.erp.api.domain.ErpOrderItem;
import com.qihang.erp.api.domain.WmsStockOutEntry;
import com.qihang.erp.api.domain.WmsStockOutEntryItem;
import com.qihang.erp.api.domain.bo.StockOutEntryGenerateBo;
import com.qihang.erp.api.service.ErpOrderItemService;
import com.qihang.erp.api.service.IErpOrderService;

import com.qihang.erp.api.service.WmsStockOutEntryItemService;
import com.qihang.erp.api.service.WmsStockOutEntryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/shipping")
public class ShippingController extends BaseController {
    private final ErpOrderItemService erpOrderItemService;
    private final WmsStockOutEntryService stockOutEntryService;
    private final WmsStockOutEntryItemService stockOutEntryItemService;
    /**
     * 备货中-供应商代发
     * @param bo
     * @param pageQuery
     * @return
     */
    @GetMapping("/supplier_ship_list")
    public TableDataInfo supplierShipList(ErpOrderItem bo, PageQuery pageQuery)
    {
        PageResult<ErpOrderItem> list = erpOrderItemService.queryPageList(1,0,bo, pageQuery);
        return getDataTable(list);
    }

    /**
     * 备货中-仓库发货
     * @param bo
     * @param pageQuery
     * @return
     */
    @GetMapping("/stock_ship_list")
    public TableDataInfo stockShipList(ErpOrderItem bo, PageQuery pageQuery)
    {
        PageResult<ErpOrderItem> list = erpOrderItemService.queryPageList(0,0,bo, pageQuery);
        return getDataTable(list);
    }

    /**
     * 新增出库单
     */
    @PostMapping("/generate_stock_out_entry")
    public AjaxResult add(@RequestBody StockOutEntryGenerateBo bo)
    {
        int result = stockOutEntryService.generateStockOutEntryForOrderItem(bo);
        if(result == -1) return AjaxResult.error("参数错误：orderItemIds为空");
        if(result == -2) return AjaxResult.error("参数错误：没有要添加的");
        else if(result == -1001) return AjaxResult.error("存在错误的orderItemId：状态不对不能生成出库单");
        //wmsStockOutEntryService.insertWmsStockOutEntry(wmsStockOutEntry)
        return toAjax(1);
    }

    /**
     * 订单待出库出库单list
     * @param bo
     * @param pageQuery
     * @return
     */
    @GetMapping("/order_stock_out_entry_list")
    public TableDataInfo stockOutEntryList(WmsStockOutEntry bo, PageQuery pageQuery)
    {
        bo.setStockOutType(1);
        bo.setStatus(0);
        PageResult<WmsStockOutEntry> list = stockOutEntryService.queryPageList(bo, pageQuery);
        return getDataTable(list);
    }

    /**
     * 订单待出库明细list
     * @param bo
     * @param pageQuery
     * @return
     */
    @GetMapping("/order_stock_out_entry_item_list")
    public TableDataInfo stockOutEntryItemList(WmsStockOutEntryItem bo, PageQuery pageQuery)
    {
        bo.setStockOutType(1);
        bo.setStatus(0);
        PageResult<WmsStockOutEntryItem> list = stockOutEntryItemService.queryPageList(bo, pageQuery);
        return getDataTable(list);
    }
}
