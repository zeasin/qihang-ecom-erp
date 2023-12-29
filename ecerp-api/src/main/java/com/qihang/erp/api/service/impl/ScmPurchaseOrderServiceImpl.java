package com.qihang.erp.api.service.impl;

import java.util.List;
import com.zhijian.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qihang.erp.api.mapper.ScmPurchaseOrderMapper;
import com.qihang.erp.api.domain.ScmPurchaseOrder;
import com.qihang.erp.api.service.IScmPurchaseOrderService;

/**
 * 采购订单Service业务层处理
 * 
 * @author qihang
 * @date 2023-12-29
 */
@Service
public class ScmPurchaseOrderServiceImpl implements IScmPurchaseOrderService 
{
    @Autowired
    private ScmPurchaseOrderMapper scmPurchaseOrderMapper;

    /**
     * 查询采购订单
     * 
     * @param id 采购订单主键
     * @return 采购订单
     */
    @Override
    public ScmPurchaseOrder selectScmPurchaseOrderById(Long id)
    {
        return scmPurchaseOrderMapper.selectScmPurchaseOrderById(id);
    }

    /**
     * 查询采购订单列表
     * 
     * @param scmPurchaseOrder 采购订单
     * @return 采购订单
     */
    @Override
    public List<ScmPurchaseOrder> selectScmPurchaseOrderList(ScmPurchaseOrder scmPurchaseOrder)
    {
        return scmPurchaseOrderMapper.selectScmPurchaseOrderList(scmPurchaseOrder);
    }

    /**
     * 新增采购订单
     * 
     * @param scmPurchaseOrder 采购订单
     * @return 结果
     */
    @Override
    public int insertScmPurchaseOrder(ScmPurchaseOrder scmPurchaseOrder)
    {
        scmPurchaseOrder.setCreateTime(DateUtils.getNowDate());
        return scmPurchaseOrderMapper.insertScmPurchaseOrder(scmPurchaseOrder);
    }

    /**
     * 修改采购订单
     * 
     * @param scmPurchaseOrder 采购订单
     * @return 结果
     */
    @Override
    public int updateScmPurchaseOrder(ScmPurchaseOrder scmPurchaseOrder)
    {
        scmPurchaseOrder.setUpdateTime(DateUtils.getNowDate());
        return scmPurchaseOrderMapper.updateScmPurchaseOrder(scmPurchaseOrder);
    }

    /**
     * 批量删除采购订单
     * 
     * @param ids 需要删除的采购订单主键
     * @return 结果
     */
    @Override
    public int deleteScmPurchaseOrderByIds(Long[] ids)
    {
        return scmPurchaseOrderMapper.deleteScmPurchaseOrderByIds(ids);
    }

    /**
     * 删除采购订单信息
     * 
     * @param id 采购订单主键
     * @return 结果
     */
    @Override
    public int deleteScmPurchaseOrderById(Long id)
    {
        return scmPurchaseOrderMapper.deleteScmPurchaseOrderById(id);
    }
}
