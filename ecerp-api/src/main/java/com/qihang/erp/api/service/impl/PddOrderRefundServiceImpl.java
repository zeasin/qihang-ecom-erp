package com.qihang.erp.api.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qihang.erp.api.mapper.PddOrderRefundMapper;
import com.qihang.erp.api.domain.PddOrderRefund;
import com.qihang.erp.api.service.IPddOrderRefundService;

/**
 * 拼多多订单退款Service业务层处理
 * 
 * @author qihang
 * @date 2024-01-13
 */
@Service
public class PddOrderRefundServiceImpl implements IPddOrderRefundService 
{
    @Autowired
    private PddOrderRefundMapper pddOrderRefundMapper;

    /**
     * 查询拼多多订单退款
     * 
     * @param id 拼多多订单退款主键
     * @return 拼多多订单退款
     */
    @Override
    public PddOrderRefund selectPddOrderRefundById(Long id)
    {
        return pddOrderRefundMapper.selectPddOrderRefundById(id);
    }

    /**
     * 查询拼多多订单退款列表
     * 
     * @param pddOrderRefund 拼多多订单退款
     * @return 拼多多订单退款
     */
    @Override
    public List<PddOrderRefund> selectPddOrderRefundList(PddOrderRefund pddOrderRefund)
    {
        return pddOrderRefundMapper.selectPddOrderRefundList(pddOrderRefund);
    }

    /**
     * 新增拼多多订单退款
     * 
     * @param pddOrderRefund 拼多多订单退款
     * @return 结果
     */
    @Override
    public int insertPddOrderRefund(PddOrderRefund pddOrderRefund)
    {
        return pddOrderRefundMapper.insertPddOrderRefund(pddOrderRefund);
    }

    /**
     * 修改拼多多订单退款
     * 
     * @param pddOrderRefund 拼多多订单退款
     * @return 结果
     */
    @Override
    public int updatePddOrderRefund(PddOrderRefund pddOrderRefund)
    {
        return pddOrderRefundMapper.updatePddOrderRefund(pddOrderRefund);
    }

    /**
     * 批量删除拼多多订单退款
     * 
     * @param ids 需要删除的拼多多订单退款主键
     * @return 结果
     */
    @Override
    public int deletePddOrderRefundByIds(Long[] ids)
    {
        return pddOrderRefundMapper.deletePddOrderRefundByIds(ids);
    }

    /**
     * 删除拼多多订单退款信息
     * 
     * @param id 拼多多订单退款主键
     * @return 结果
     */
    @Override
    public int deletePddOrderRefundById(Long id)
    {
        return pddOrderRefundMapper.deletePddOrderRefundById(id);
    }
}
