package cn.qihangerp.api.mapper;

import java.util.List;
import cn.qihangerp.api.domain.ErpGoodsBrand;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品品牌Mapper接口
 * 
 * @author qihang
 * @date 2023-12-29
 */
@Mapper
public interface ErpGoodsBrandMapper 
{
    /**
     * 查询商品品牌
     * 
     * @param id 商品品牌主键
     * @return 商品品牌
     */
    public ErpGoodsBrand selectErpGoodsBrandById(Long id);

    /**
     * 查询商品品牌列表
     * 
     * @param erpGoodsBrand 商品品牌
     * @return 商品品牌集合
     */
    public List<ErpGoodsBrand> selectErpGoodsBrandList(ErpGoodsBrand erpGoodsBrand);

    /**
     * 新增商品品牌
     * 
     * @param erpGoodsBrand 商品品牌
     * @return 结果
     */
    public int insertErpGoodsBrand(ErpGoodsBrand erpGoodsBrand);

    /**
     * 修改商品品牌
     * 
     * @param erpGoodsBrand 商品品牌
     * @return 结果
     */
    public int updateErpGoodsBrand(ErpGoodsBrand erpGoodsBrand);

    /**
     * 删除商品品牌
     * 
     * @param id 商品品牌主键
     * @return 结果
     */
    public int deleteErpGoodsBrandById(Long id);

    /**
     * 批量删除商品品牌
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteErpGoodsBrandByIds(Long[] ids);
}
