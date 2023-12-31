package com.qihang.erp.api.service;

import java.util.List;
import com.qihang.erp.api.domain.ShopOrder;

/**
 * 店铺订单Service接口
 * 
 * @author qihang
 * @date 2023-12-31
 */
public interface IShopOrderService 
{
    /**
     * 查询店铺订单
     * 
     * @param id 店铺订单主键
     * @return 店铺订单
     */
    public ShopOrder selectShopOrderById(Long id);

    /**
     * 查询店铺订单列表
     * 
     * @param shopOrder 店铺订单
     * @return 店铺订单集合
     */
    public List<ShopOrder> selectShopOrderList(ShopOrder shopOrder);

    /**
     * 新增店铺订单
     * 
     * @param shopOrder 店铺订单
     * @return 结果
     */
    public int insertShopOrder(ShopOrder shopOrder);

    /**
     * 修改店铺订单
     * 
     * @param shopOrder 店铺订单
     * @return 结果
     */
    public int updateShopOrder(ShopOrder shopOrder);

    /**
     * 批量删除店铺订单
     * 
     * @param ids 需要删除的店铺订单主键集合
     * @return 结果
     */
    public int deleteShopOrderByIds(Long[] ids);

    /**
     * 删除店铺订单信息
     * 
     * @param id 店铺订单主键
     * @return 结果
     */
    public int deleteShopOrderById(Long id);
}
