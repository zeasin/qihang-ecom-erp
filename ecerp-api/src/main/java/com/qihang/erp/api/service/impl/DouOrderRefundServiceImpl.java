package com.qihang.erp.api.service.impl;

import java.util.List;
import com.zhijian.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qihang.erp.api.mapper.DouOrderRefundMapper;
import com.qihang.erp.api.domain.DouOrderRefund;
import com.qihang.erp.api.service.IDouOrderRefundService;

/**
 * 抖店订单退款Service业务层处理
 * 
 * @author qihang
 * @date 2024-01-02
 */
@Service
public class DouOrderRefundServiceImpl implements IDouOrderRefundService 
{
    @Autowired
    private DouOrderRefundMapper douOrderRefundMapper;

    /**
     * 查询抖店订单退款
     * 
     * @param id 抖店订单退款主键
     * @return 抖店订单退款
     */
    @Override
    public DouOrderRefund selectDouOrderRefundById(Long id)
    {
        return douOrderRefundMapper.selectDouOrderRefundById(id);
    }

    /**
     * 查询抖店订单退款列表
     * 
     * @param douOrderRefund 抖店订单退款
     * @return 抖店订单退款
     */
    @Override
    public List<DouOrderRefund> selectDouOrderRefundList(DouOrderRefund douOrderRefund)
    {
        return douOrderRefundMapper.selectDouOrderRefundList(douOrderRefund);
    }

    /**
     * 新增抖店订单退款
     * 
     * @param douOrderRefund 抖店订单退款
     * @return 结果
     */
    @Override
    public int insertDouOrderRefund(DouOrderRefund douOrderRefund)
    {
        douOrderRefund.setCreateTime(DateUtils.getNowDate());
        return douOrderRefundMapper.insertDouOrderRefund(douOrderRefund);
    }

    /**
     * 修改抖店订单退款
     * 
     * @param douOrderRefund 抖店订单退款
     * @return 结果
     */
    @Override
    public int updateDouOrderRefund(DouOrderRefund douOrderRefund)
    {
        douOrderRefund.setUpdateTime(DateUtils.getNowDate());
        return douOrderRefundMapper.updateDouOrderRefund(douOrderRefund);
    }

    /**
     * 批量删除抖店订单退款
     * 
     * @param ids 需要删除的抖店订单退款主键
     * @return 结果
     */
    @Override
    public int deleteDouOrderRefundByIds(Long[] ids)
    {
        return douOrderRefundMapper.deleteDouOrderRefundByIds(ids);
    }

    /**
     * 删除抖店订单退款信息
     * 
     * @param id 抖店订单退款主键
     * @return 结果
     */
    @Override
    public int deleteDouOrderRefundById(Long id)
    {
        return douOrderRefundMapper.deleteDouOrderRefundById(id);
    }
}
