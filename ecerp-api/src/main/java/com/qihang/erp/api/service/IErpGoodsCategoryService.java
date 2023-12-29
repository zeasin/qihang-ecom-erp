package com.qihang.erp.api.service;

import java.util.List;
import com.qihang.erp.api.domain.ErpGoodsCategory;

/**
 * 商品分类Service接口
 * 
 * @author qihang
 * @date 2023-12-29
 */
public interface IErpGoodsCategoryService 
{
    /**
     * 查询商品分类
     * 
     * @param id 商品分类主键
     * @return 商品分类
     */
    public ErpGoodsCategory selectErpGoodsCategoryById(Long id);

    /**
     * 查询商品分类列表
     * 
     * @param erpGoodsCategory 商品分类
     * @return 商品分类集合
     */
    public List<ErpGoodsCategory> selectErpGoodsCategoryList(ErpGoodsCategory erpGoodsCategory);

    /**
     * 新增商品分类
     * 
     * @param erpGoodsCategory 商品分类
     * @return 结果
     */
    public int insertErpGoodsCategory(ErpGoodsCategory erpGoodsCategory);

    /**
     * 修改商品分类
     * 
     * @param erpGoodsCategory 商品分类
     * @return 结果
     */
    public int updateErpGoodsCategory(ErpGoodsCategory erpGoodsCategory);

    /**
     * 批量删除商品分类
     * 
     * @param ids 需要删除的商品分类主键集合
     * @return 结果
     */
    public int deleteErpGoodsCategoryByIds(Long[] ids);

    /**
     * 删除商品分类信息
     * 
     * @param id 商品分类主键
     * @return 结果
     */
    public int deleteErpGoodsCategoryById(Long id);
}
