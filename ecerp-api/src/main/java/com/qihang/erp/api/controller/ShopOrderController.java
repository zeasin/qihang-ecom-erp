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
import com.qihang.erp.api.domain.ShopOrder;
import com.qihang.erp.api.service.IShopOrderService;
import com.zhijian.common.utils.poi.ExcelUtil;
import com.zhijian.common.core.page.TableDataInfo;

/**
 * 店铺订单Controller
 * 
 * @author qihang
 * @date 2023-12-31
 */
@RestController
@RequestMapping("/shop/order")
public class ShopOrderController extends BaseController
{
    @Autowired
    private IShopOrderService shopOrderService;

    /**
     * 查询店铺订单列表
     */
    @PreAuthorize("@ss.hasPermi('shop:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(ShopOrder shopOrder)
    {
        startPage();
        List<ShopOrder> list = shopOrderService.selectShopOrderList(shopOrder);
        return getDataTable(list);
    }

    /**
     * 导出店铺订单列表
     */
    @PreAuthorize("@ss.hasPermi('shop:order:export')")
    @Log(title = "店铺订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ShopOrder shopOrder)
    {
        List<ShopOrder> list = shopOrderService.selectShopOrderList(shopOrder);
        ExcelUtil<ShopOrder> util = new ExcelUtil<ShopOrder>(ShopOrder.class);
        util.exportExcel(response, list, "店铺订单数据");
    }

    /**
     * 获取店铺订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('shop:order:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(shopOrderService.selectShopOrderById(id));
    }

    /**
     * 新增店铺订单
     */
    @PreAuthorize("@ss.hasPermi('shop:order:add')")
    @Log(title = "店铺订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ShopOrder shopOrder)
    {
        shopOrder.setCreateBy(getUsername());
        return toAjax(shopOrderService.insertShopOrder(shopOrder));
    }


}
