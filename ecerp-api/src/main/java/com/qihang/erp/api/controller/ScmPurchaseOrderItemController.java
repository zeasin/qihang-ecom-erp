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
import com.qihang.erp.api.domain.ScmPurchaseOrderItem;
import com.qihang.erp.api.service.IScmPurchaseOrderItemService;
import com.zhijian.common.utils.poi.ExcelUtil;
import com.zhijian.common.core.page.TableDataInfo;

/**
 * 采购订单明细Controller
 * 
 * @author qihang
 * @date 2023-12-29
 */
@RestController
@RequestMapping("/purchase/purchaseOrderItem")
public class ScmPurchaseOrderItemController extends BaseController
{
    @Autowired
    private IScmPurchaseOrderItemService scmPurchaseOrderItemService;

    /**
     * 查询采购订单明细列表
     */
    @PreAuthorize("@ss.hasPermi('purchase:purchaseOrderItem:list')")
    @GetMapping("/list")
    public TableDataInfo list(ScmPurchaseOrderItem scmPurchaseOrderItem)
    {
        startPage();
        List<ScmPurchaseOrderItem> list = scmPurchaseOrderItemService.selectScmPurchaseOrderItemList(scmPurchaseOrderItem);
        return getDataTable(list);
    }

    /**
     * 导出采购订单明细列表
     */
    @PreAuthorize("@ss.hasPermi('purchase:purchaseOrderItem:export')")
    @Log(title = "采购订单明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ScmPurchaseOrderItem scmPurchaseOrderItem)
    {
        List<ScmPurchaseOrderItem> list = scmPurchaseOrderItemService.selectScmPurchaseOrderItemList(scmPurchaseOrderItem);
        ExcelUtil<ScmPurchaseOrderItem> util = new ExcelUtil<ScmPurchaseOrderItem>(ScmPurchaseOrderItem.class);
        util.exportExcel(response, list, "采购订单明细数据");
    }

    /**
     * 获取采购订单明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('purchase:purchaseOrderItem:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(scmPurchaseOrderItemService.selectScmPurchaseOrderItemById(id));
    }

    /**
     * 新增采购订单明细
     */
    @PreAuthorize("@ss.hasPermi('purchase:purchaseOrderItem:add')")
    @Log(title = "采购订单明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ScmPurchaseOrderItem scmPurchaseOrderItem)
    {
        return toAjax(scmPurchaseOrderItemService.insertScmPurchaseOrderItem(scmPurchaseOrderItem));
    }

    /**
     * 修改采购订单明细
     */
    @PreAuthorize("@ss.hasPermi('purchase:purchaseOrderItem:edit')")
    @Log(title = "采购订单明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ScmPurchaseOrderItem scmPurchaseOrderItem)
    {
        return toAjax(scmPurchaseOrderItemService.updateScmPurchaseOrderItem(scmPurchaseOrderItem));
    }

    /**
     * 删除采购订单明细
     */
    @PreAuthorize("@ss.hasPermi('purchase:purchaseOrderItem:remove')")
    @Log(title = "采购订单明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(scmPurchaseOrderItemService.deleteScmPurchaseOrderItemByIds(ids));
    }
}
