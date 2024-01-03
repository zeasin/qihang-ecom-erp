package com.qihang.erp.api.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qihang.erp.api.mapper.TaoOrderRefundMapper;
import com.qihang.erp.api.domain.TaoOrderRefund;
import com.qihang.erp.api.service.ITaoOrderRefundService;

/**
 * 淘宝退款订单Service业务层处理
 * 
 * @author qihang
 * @date 2024-01-03
 */
@Service
public class TaoOrderRefundServiceImpl implements ITaoOrderRefundService 
{
    @Autowired
    private TaoOrderRefundMapper taoOrderRefundMapper;

    /**
     * 查询淘宝退款订单
     * 
     * @param id 淘宝退款订单主键
     * @return 淘宝退款订单
     */
    @Override
    public TaoOrderRefund selectTaoOrderRefundById(Long id)
    {
        return taoOrderRefundMapper.selectTaoOrderRefundById(id);
    }

    /**
     * 查询淘宝退款订单列表
     * 
     * @param taoOrderRefund 淘宝退款订单
     * @return 淘宝退款订单
     */
    @Override
    public List<TaoOrderRefund> selectTaoOrderRefundList(TaoOrderRefund taoOrderRefund)
    {
        return taoOrderRefundMapper.selectTaoOrderRefundList(taoOrderRefund);
    }

    /**
     * 新增淘宝退款订单
     * 
     * @param taoOrderRefund 淘宝退款订单
     * @return 结果
     */
    @Override
    public int insertTaoOrderRefund(TaoOrderRefund taoOrderRefund)
    {
        return taoOrderRefundMapper.insertTaoOrderRefund(taoOrderRefund);
    }

    /**
     * 修改淘宝退款订单
     * 
     * @param taoOrderRefund 淘宝退款订单
     * @return 结果
     */
    @Override
    public int updateTaoOrderRefund(TaoOrderRefund taoOrderRefund)
    {
        return taoOrderRefundMapper.updateTaoOrderRefund(taoOrderRefund);
    }

    /**
     * 批量删除淘宝退款订单
     * 
     * @param ids 需要删除的淘宝退款订单主键
     * @return 结果
     */
    @Override
    public int deleteTaoOrderRefundByIds(Long[] ids)
    {
        return taoOrderRefundMapper.deleteTaoOrderRefundByIds(ids);
    }

    /**
     * 删除淘宝退款订单信息
     * 
     * @param id 淘宝退款订单主键
     * @return 结果
     */
    @Override
    public int deleteTaoOrderRefundById(Long id)
    {
        return taoOrderRefundMapper.deleteTaoOrderRefundById(id);
    }
}
