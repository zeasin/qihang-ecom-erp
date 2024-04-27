package cn.qihangerp.api.mapper;

import java.util.List;
import cn.qihangerp.api.domain.Shop;
import org.apache.ibatis.annotations.Mapper;

/**
 * 店铺Mapper接口
 * 
 * @author qihang
 * @date 2023-12-31
 */
@Mapper
public interface ShopMapper 
{
    /**
     * 查询店铺
     * 
     * @param id 店铺主键
     * @return 店铺
     */
    public Shop selectShopById(Long id);

    /**
     * 查询店铺列表
     * 
     * @param shop 店铺
     * @return 店铺集合
     */
    public List<Shop> selectShopList(Shop shop);

    /**
     * 新增店铺
     * 
     * @param shop 店铺
     * @return 结果
     */
    public int insertShop(Shop shop);

    /**
     * 修改店铺
     * 
     * @param shop 店铺
     * @return 结果
     */
    public int updateShop(Shop shop);

    /**
     * 删除店铺
     * 
     * @param id 店铺主键
     * @return 结果
     */
    public int deleteShopById(Long id);

    /**
     * 批量删除店铺
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteShopByIds(Long[] ids);
}
