package com.qihang.erp.api.mapper;

import java.util.List;
import com.qihang.erp.api.domain.ShopOrder;
import com.qihang.erp.api.domain.SShopOrderItem;
import org.apache.ibatis.annotations.Mapper;

/**
 * 店铺订单Mapper接口
 * 
 * @author qihang
 * @date 2023-12-31
 */
@Mapper
public interface ShopOrderMapper 
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
     * 删除店铺订单
     * 
     * @param id 店铺订单主键
     * @return 结果
     */
    public int deleteShopOrderById(Long id);

    /**
     * 批量删除店铺订单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteShopOrderByIds(Long[] ids);

    /**
     * 批量删除${subTable.functionName}
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSShopOrderItemByOrderIds(Long[] ids);
    
    /**
     * 批量新增${subTable.functionName}
     * 
     * @param sShopOrderItemList ${subTable.functionName}列表
     * @return 结果
     */
    public int batchSShopOrderItem(List<SShopOrderItem> sShopOrderItemList);
    

    /**
     * 通过店铺订单主键删除${subTable.functionName}信息
     * 
     * @param id 店铺订单ID
     * @return 结果
     */
    public int deleteSShopOrderItemByOrderId(Long id);
}
