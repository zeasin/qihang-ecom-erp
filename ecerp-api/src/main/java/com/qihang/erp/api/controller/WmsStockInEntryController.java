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
import com.qihang.erp.api.domain.WmsStockInEntry;
import com.qihang.erp.api.service.IWmsStockInEntryService;
import com.zhijian.common.utils.poi.ExcelUtil;
import com.zhijian.common.core.page.TableDataInfo;

/**
 * 入库单Controller
 * 
 * @author qihang
 * @date 2023-12-31
 */
@RestController
@RequestMapping("/wms/WmsStockInEntry")
public class WmsStockInEntryController extends BaseController
{
    @Autowired
    private IWmsStockInEntryService wmsStockInEntryService;

    /**
     * 查询入库单列表
     */
    @PreAuthorize("@ss.hasPermi('wms:WmsStockInEntry:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmsStockInEntry wmsStockInEntry)
    {
        startPage();
        List<WmsStockInEntry> list = wmsStockInEntryService.selectWmsStockInEntryList(wmsStockInEntry);
        return getDataTable(list);
    }

    /**
     * 导出入库单列表
     */
    @PreAuthorize("@ss.hasPermi('wms:WmsStockInEntry:export')")
    @Log(title = "入库单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmsStockInEntry wmsStockInEntry)
    {
        List<WmsStockInEntry> list = wmsStockInEntryService.selectWmsStockInEntryList(wmsStockInEntry);
        ExcelUtil<WmsStockInEntry> util = new ExcelUtil<WmsStockInEntry>(WmsStockInEntry.class);
        util.exportExcel(response, list, "入库单数据");
    }

    /**
     * 获取入库单详细信息
     */
    @PreAuthorize("@ss.hasPermi('wms:WmsStockInEntry:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(wmsStockInEntryService.selectWmsStockInEntryById(id));
    }

    /**
     * 新增入库单
     */
    @PreAuthorize("@ss.hasPermi('wms:WmsStockInEntry:add')")
    @Log(title = "入库单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsStockInEntry wmsStockInEntry)
    {
        return toAjax(wmsStockInEntryService.insertWmsStockInEntry(wmsStockInEntry));
    }

    /**
     * 修改入库单
     */
    @PreAuthorize("@ss.hasPermi('wms:WmsStockInEntry:edit')")
    @Log(title = "入库单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsStockInEntry wmsStockInEntry)
    {
        return toAjax(wmsStockInEntryService.updateWmsStockInEntry(wmsStockInEntry));
    }

    /**
     * 删除入库单
     */
    @PreAuthorize("@ss.hasPermi('wms:WmsStockInEntry:remove')")
    @Log(title = "入库单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(wmsStockInEntryService.deleteWmsStockInEntryByIds(ids));
    }
}
