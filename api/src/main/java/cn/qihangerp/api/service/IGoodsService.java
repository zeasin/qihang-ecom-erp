package cn.qihangerp.api.service;

import java.util.List;
import cn.qihangerp.api.domain.Goods;
import cn.qihangerp.api.domain.vo.GoodsSpecListVo;

/**
 * 商品管理Service接口
 * 
 * @author qihang
 * @date 2023-12-29
 */
public interface IGoodsService 
{
    /**
     * 查询商品管理
     * 
     * @param id 商品管理主键
     * @return 商品管理
     */
    public Goods selectGoodsById(Long id);

    /**
     * 查询商品管理列表
     * 
     * @param goods 商品管理
     * @return 商品管理集合
     */
    public List<Goods> selectGoodsList(Goods goods);

    /**
     * 新增商品管理
     * 
     * @param goods 商品管理
     * @return 结果
     */
    public int insertGoods(Goods goods);

    /**
     * 修改商品管理
     * 
     * @param goods 商品管理
     * @return 结果
     */
    public int updateGoods(Goods goods);

    /**
     * 批量删除商品管理
     * 
     * @param ids 需要删除的商品管理主键集合
     * @return 结果
     */
    public int deleteGoodsByIds(Long[] ids);

    /**
     * 删除商品管理信息
     * 
     * @param id 商品管理主键
     * @return 结果
     */
    public int deleteGoodsById(Long id);

    List<GoodsSpecListVo> searchGoodsSpec(String keyword);
}
