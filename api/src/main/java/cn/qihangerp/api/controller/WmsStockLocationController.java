package cn.qihangerp.api.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.qihangerp.common.annotation.Log;
import cn.qihangerp.core.controller.BaseController;
import cn.qihangerp.domain.AjaxResult;
import cn.qihangerp.common.enums.BusinessType;
import cn.qihangerp.api.domain.WmsStockLocation;
import cn.qihangerp.api.service.IWmsStockLocationService;
import cn.qihangerp.common.utils.poi.ExcelUtil;
import cn.qihangerp.core.page.TableDataInfo;

/**
 * 仓库货架Controller
 * 
 * @author qihang
 * @date 2024-01-09
 */
@RestController
@RequestMapping("/wms/location")
public class WmsStockLocationController extends BaseController
{
    @Autowired
    private IWmsStockLocationService wmsStockLocationService;

    /**
     * 查询仓库货架列表
     */
    @PreAuthorize("@ss.hasPermi('wms:location:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmsStockLocation wmsStockLocation)
    {
        startPage();
        List<WmsStockLocation> list = wmsStockLocationService.selectWmsStockLocationList(wmsStockLocation);
        return getDataTable(list);
    }

    /**
     * 导出仓库货架列表
     */
    @PreAuthorize("@ss.hasPermi('wms:location:export')")
    @Log(title = "仓库货架", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmsStockLocation wmsStockLocation)
    {
        List<WmsStockLocation> list = wmsStockLocationService.selectWmsStockLocationList(wmsStockLocation);
        ExcelUtil<WmsStockLocation> util = new ExcelUtil<WmsStockLocation>(WmsStockLocation.class);
        util.exportExcel(response, list, "仓库货架数据");
    }

    /**
     * 获取仓库货架详细信息
     */
    @PreAuthorize("@ss.hasPermi('wms:location:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(wmsStockLocationService.selectWmsStockLocationById(id));
    }

    /**
     * 新增仓库货架
     */
    @PreAuthorize("@ss.hasPermi('wms:location:add')")
    @Log(title = "仓库货架", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsStockLocation wmsStockLocation)
    {
        return toAjax(wmsStockLocationService.insertWmsStockLocation(wmsStockLocation));
    }

    /**
     * 修改仓库货架
     */
    @PreAuthorize("@ss.hasPermi('wms:location:edit')")
    @Log(title = "仓库货架", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsStockLocation wmsStockLocation)
    {
        return toAjax(wmsStockLocationService.updateWmsStockLocation(wmsStockLocation));
    }

    @GetMapping("/search")
    public TableDataInfo searchBy(String number)
    {
        startPage();
        List<WmsStockLocation> list = wmsStockLocationService.search(number);
        return getDataTable(list);
    }
    /**
     * 删除仓库货架
     */
//    @PreAuthorize("@ss.hasPermi('wms:location:remove')")
//    @Log(title = "仓库货架", businessType = BusinessType.DELETE)
//	@DeleteMapping("/{ids}")
//    public AjaxResult remove(@PathVariable Long[] ids)
//    {
//        return toAjax(wmsStockLocationService.deleteWmsStockLocationByIds(ids));
//    }
}
