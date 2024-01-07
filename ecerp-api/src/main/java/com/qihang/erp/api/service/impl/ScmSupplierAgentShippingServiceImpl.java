package com.qihang.erp.api.service.impl;

import java.util.List;
import com.zhijian.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qihang.erp.api.mapper.ScmSupplierAgentShippingMapper;
import com.qihang.erp.api.domain.ScmSupplierAgentShipping;
import com.qihang.erp.api.service.IScmSupplierAgentShippingService;

/**
 * 供应商代发货Service业务层处理
 * 
 * @author qihang
 * @date 2024-01-07
 */
@Service
public class ScmSupplierAgentShippingServiceImpl implements IScmSupplierAgentShippingService 
{
    @Autowired
    private ScmSupplierAgentShippingMapper scmSupplierAgentShippingMapper;

    /**
     * 查询供应商代发货
     * 
     * @param id 供应商代发货主键
     * @return 供应商代发货
     */
    @Override
    public ScmSupplierAgentShipping selectScmSupplierAgentShippingById(Long id)
    {
        return scmSupplierAgentShippingMapper.selectScmSupplierAgentShippingById(id);
    }

    /**
     * 查询供应商代发货列表
     * 
     * @param scmSupplierAgentShipping 供应商代发货
     * @return 供应商代发货
     */
    @Override
    public List<ScmSupplierAgentShipping> selectScmSupplierAgentShippingList(ScmSupplierAgentShipping scmSupplierAgentShipping)
    {
        return scmSupplierAgentShippingMapper.selectScmSupplierAgentShippingList(scmSupplierAgentShipping);
    }

    /**
     * 新增供应商代发货
     * 
     * @param scmSupplierAgentShipping 供应商代发货
     * @return 结果
     */
    @Override
    public int insertScmSupplierAgentShipping(ScmSupplierAgentShipping scmSupplierAgentShipping)
    {
        scmSupplierAgentShipping.setCreateTime(DateUtils.getNowDate());
        return scmSupplierAgentShippingMapper.insertScmSupplierAgentShipping(scmSupplierAgentShipping);
    }

    /**
     * 修改供应商代发货
     * 
     * @param scmSupplierAgentShipping 供应商代发货
     * @return 结果
     */
    @Override
    public int updateScmSupplierAgentShipping(ScmSupplierAgentShipping scmSupplierAgentShipping)
    {
        scmSupplierAgentShipping.setUpdateTime(DateUtils.getNowDate());
        return scmSupplierAgentShippingMapper.updateScmSupplierAgentShipping(scmSupplierAgentShipping);
    }

    /**
     * 批量删除供应商代发货
     * 
     * @param ids 需要删除的供应商代发货主键
     * @return 结果
     */
    @Override
    public int deleteScmSupplierAgentShippingByIds(Long[] ids)
    {
        return scmSupplierAgentShippingMapper.deleteScmSupplierAgentShippingByIds(ids);
    }

    /**
     * 删除供应商代发货信息
     * 
     * @param id 供应商代发货主键
     * @return 结果
     */
    @Override
    public int deleteScmSupplierAgentShippingById(Long id)
    {
        return scmSupplierAgentShippingMapper.deleteScmSupplierAgentShippingById(id);
    }
}
