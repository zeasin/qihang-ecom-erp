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
import com.qihang.erp.api.domain.XhsOrder;
import com.qihang.erp.api.service.IXhsOrderService;
import com.zhijian.common.utils.poi.ExcelUtil;
import com.zhijian.common.core.page.TableDataInfo;

/**
 * 小红书订单Controller
 * 
 * @author qihang
 * @date 2024-01-03
 */
@RestController
@RequestMapping("/xhs/order")
public class XhsOrderController extends BaseController
{
    @Autowired
    private IXhsOrderService xhsOrderService;

    /**
     * 查询小红书订单列表
     */
    @PreAuthorize("@ss.hasPermi('xhs:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(XhsOrder xhsOrder)
    {
        startPage();
        List<XhsOrder> list = xhsOrderService.selectXhsOrderList(xhsOrder);
        return getDataTable(list);
    }

    /**
     * 导出小红书订单列表
     */
    @PreAuthorize("@ss.hasPermi('xhs:order:export')")
    @Log(title = "小红书订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, XhsOrder xhsOrder)
    {
        List<XhsOrder> list = xhsOrderService.selectXhsOrderList(xhsOrder);
        ExcelUtil<XhsOrder> util = new ExcelUtil<XhsOrder>(XhsOrder.class);
        util.exportExcel(response, list, "小红书订单数据");
    }

    /**
     * 获取小红书订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('xhs:order:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(xhsOrderService.selectXhsOrderById(id));
    }

    /**
     * 新增小红书订单
     */
    @PreAuthorize("@ss.hasPermi('xhs:order:add')")
    @Log(title = "小红书订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody XhsOrder xhsOrder)
    {
        return toAjax(xhsOrderService.insertXhsOrder(xhsOrder));
    }

    /**
     * 修改小红书订单
     */
    @PreAuthorize("@ss.hasPermi('xhs:order:edit')")
    @Log(title = "小红书订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody XhsOrder xhsOrder)
    {
        return toAjax(xhsOrderService.updateXhsOrder(xhsOrder));
    }

    /**
     * 删除小红书订单
     */
    @PreAuthorize("@ss.hasPermi('xhs:order:remove')")
    @Log(title = "小红书订单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(xhsOrderService.deleteXhsOrderByIds(ids));
    }
}
