package com.qihang.erp.api.service.impl;

import java.util.List;
import com.zhijian.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qihang.erp.api.mapper.ScmPurchaseOrderShipMapper;
import com.qihang.erp.api.domain.ScmPurchaseOrderShip;
import com.qihang.erp.api.service.IScmPurchaseOrderShipService;

/**
 * 采购订单物流Service业务层处理
 * 
 * @author qihang
 * @date 2023-12-30
 */
@Service
public class ScmPurchaseOrderShipServiceImpl implements IScmPurchaseOrderShipService 
{
    @Autowired
    private ScmPurchaseOrderShipMapper scmPurchaseOrderShipMapper;

    /**
     * 查询采购订单物流
     * 
     * @param id 采购订单物流主键
     * @return 采购订单物流
     */
    @Override
    public ScmPurchaseOrderShip selectScmPurchaseOrderShipById(Long id)
    {
        return scmPurchaseOrderShipMapper.selectScmPurchaseOrderShipById(id);
    }

    /**
     * 查询采购订单物流列表
     * 
     * @param scmPurchaseOrderShip 采购订单物流
     * @return 采购订单物流
     */
    @Override
    public List<ScmPurchaseOrderShip> selectScmPurchaseOrderShipList(ScmPurchaseOrderShip scmPurchaseOrderShip)
    {
        return scmPurchaseOrderShipMapper.selectScmPurchaseOrderShipList(scmPurchaseOrderShip);
    }

    /**
     * 新增采购订单物流
     * 
     * @param scmPurchaseOrderShip 采购订单物流
     * @return 结果
     */
    @Override
    public int insertScmPurchaseOrderShip(ScmPurchaseOrderShip scmPurchaseOrderShip)
    {
        scmPurchaseOrderShip.setCreateTime(DateUtils.getNowDate());
        return scmPurchaseOrderShipMapper.insertScmPurchaseOrderShip(scmPurchaseOrderShip);
    }

    /**
     * 修改采购订单物流
     * 
     * @param scmPurchaseOrderShip 采购订单物流
     * @return 结果
     */
    @Override
    public int updateScmPurchaseOrderShip(ScmPurchaseOrderShip scmPurchaseOrderShip)
    {
        scmPurchaseOrderShip.setUpdateTime(DateUtils.getNowDate());
        return scmPurchaseOrderShipMapper.updateScmPurchaseOrderShip(scmPurchaseOrderShip);
    }

    /**
     * 批量删除采购订单物流
     * 
     * @param ids 需要删除的采购订单物流主键
     * @return 结果
     */
    @Override
    public int deleteScmPurchaseOrderShipByIds(Long[] ids)
    {
        return scmPurchaseOrderShipMapper.deleteScmPurchaseOrderShipByIds(ids);
    }

    /**
     * 删除采购订单物流信息
     * 
     * @param id 采购订单物流主键
     * @return 结果
     */
    @Override
    public int deleteScmPurchaseOrderShipById(Long id)
    {
        return scmPurchaseOrderShipMapper.deleteScmPurchaseOrderShipById(id);
    }
}
