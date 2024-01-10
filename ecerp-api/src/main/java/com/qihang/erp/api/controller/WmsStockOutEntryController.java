package com.qihang.erp.api.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zhijian.common.annotation.Log;
import com.zhijian.common.core.controller.BaseController;
import com.zhijian.common.core.domain.AjaxResult;
import com.zhijian.common.enums.BusinessType;
import com.qihang.erp.api.domain.WmsStockOutEntry;
import com.qihang.erp.api.service.IWmsStockOutEntryService;
import com.zhijian.common.utils.poi.ExcelUtil;
import com.zhijian.common.core.page.TableDataInfo;

/**
 * 出库单Controller
 * 
 * @author qihang
 * @date 2024-01-10
 */
@RestController
@RequestMapping("/wms/stockOutEntry")
public class WmsStockOutEntryController extends BaseController
{
    @Autowired
    private IWmsStockOutEntryService wmsStockOutEntryService;

    /**
     * 查询出库单列表
     */
    @PreAuthorize("@ss.hasPermi('wms:stockOutEntry:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmsStockOutEntry wmsStockOutEntry)
    {
        startPage();
        List<WmsStockOutEntry> list = wmsStockOutEntryService.selectWmsStockOutEntryList(wmsStockOutEntry);
        return getDataTable(list);
    }

    /**
     * 导出出库单列表
     */
    @PreAuthorize("@ss.hasPermi('wms:stockOutEntry:export')")
    @Log(title = "出库单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmsStockOutEntry wmsStockOutEntry)
    {
        List<WmsStockOutEntry> list = wmsStockOutEntryService.selectWmsStockOutEntryList(wmsStockOutEntry);
        ExcelUtil<WmsStockOutEntry> util = new ExcelUtil<WmsStockOutEntry>(WmsStockOutEntry.class);
        util.exportExcel(response, list, "出库单数据");
    }

    /**
     * 获取出库单详细信息
     */
    @PreAuthorize("@ss.hasPermi('wms:stockOutEntry:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(wmsStockOutEntryService.selectWmsStockOutEntryById(id));
    }

//    /**
//     * 新增出库单
//     */
//    @PreAuthorize("@ss.hasPermi('wms:stockOutEntry:add')")
//    @Log(title = "出库单", businessType = BusinessType.INSERT)
//    @PostMapping
//    public AjaxResult add(@RequestBody WmsStockOutEntry wmsStockOutEntry)
//    {
//        return toAjax(wmsStockOutEntryService.insertWmsStockOutEntry(wmsStockOutEntry));
//    }

    /**
     * 修改出库单
     */
    @PreAuthorize("@ss.hasPermi('wms:stockOutEntry:edit')")
    @Log(title = "出库单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsStockOutEntry wmsStockOutEntry)
    {
        return toAjax(wmsStockOutEntryService.updateWmsStockOutEntry(wmsStockOutEntry));
    }

//    /**
//     * 删除出库单
//     */
//    @PreAuthorize("@ss.hasPermi('wms:stockOutEntry:remove')")
//    @Log(title = "出库单", businessType = BusinessType.DELETE)
//	@DeleteMapping("/{ids}")
//    public AjaxResult remove(@PathVariable Long[] ids)
//    {
//        return toAjax(wmsStockOutEntryService.deleteWmsStockOutEntryByIds(ids));
//    }
}
