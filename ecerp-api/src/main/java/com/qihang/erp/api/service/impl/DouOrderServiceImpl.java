package com.qihang.erp.api.service.impl;

import java.util.List;
import com.zhijian.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.zhijian.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.qihang.erp.api.domain.DouOrderItem;
import com.qihang.erp.api.mapper.DouOrderMapper;
import com.qihang.erp.api.domain.DouOrder;
import com.qihang.erp.api.service.IDouOrderService;

/**
 * 抖店订单Service业务层处理
 * 
 * @author qihang
 * @date 2024-01-02
 */
@Service
public class DouOrderServiceImpl implements IDouOrderService 
{
    @Autowired
    private DouOrderMapper douOrderMapper;

    /**
     * 查询抖店订单
     * 
     * @param id 抖店订单主键
     * @return 抖店订单
     */
    @Override
    public DouOrder selectDouOrderById(Long id)
    {
        return douOrderMapper.selectDouOrderById(id);
    }

    /**
     * 查询抖店订单列表
     * 
     * @param douOrder 抖店订单
     * @return 抖店订单
     */
    @Override
    public List<DouOrder> selectDouOrderList(DouOrder douOrder)
    {
        List<DouOrder> douOrders = douOrderMapper.selectDouOrderList(douOrder);
        for (var o:douOrders) {
            List<DouOrderItem> items = douOrderMapper.selectOrderItemByOrderId(o.getId());
            o.setDouOrderItemList(items);
        }
        return douOrders;
    }

    /**
     * 新增抖店订单
     * 
     * @param douOrder 抖店订单
     * @return 结果
     */
    @Transactional
    @Override
    public int insertDouOrder(DouOrder douOrder)
    {
        int rows = douOrderMapper.insertDouOrder(douOrder);
        insertDouOrderItem(douOrder);
        return rows;
    }

    /**
     * 修改抖店订单
     * 
     * @param douOrder 抖店订单
     * @return 结果
     */
    @Transactional
    @Override
    public int updateDouOrder(DouOrder douOrder)
    {
        douOrder.setUpdateTime(DateUtils.getNowDate());
        douOrderMapper.deleteDouOrderItemByDouyinOrderId(douOrder.getId());
        insertDouOrderItem(douOrder);
        return douOrderMapper.updateDouOrder(douOrder);
    }

    /**
     * 批量删除抖店订单
     * 
     * @param ids 需要删除的抖店订单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteDouOrderByIds(Long[] ids)
    {
        douOrderMapper.deleteDouOrderItemByDouyinOrderIds(ids);
        return douOrderMapper.deleteDouOrderByIds(ids);
    }

    /**
     * 删除抖店订单信息
     * 
     * @param id 抖店订单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteDouOrderById(Long id)
    {
        douOrderMapper.deleteDouOrderItemByDouyinOrderId(id);
        return douOrderMapper.deleteDouOrderById(id);
    }

    /**
     * 新增抖店订单明细信息
     * 
     * @param douOrder 抖店订单对象
     */
    public void insertDouOrderItem(DouOrder douOrder)
    {
        List<DouOrderItem> douOrderItemList = douOrder.getDouOrderItemList();
        Long id = douOrder.getId();
        if (StringUtils.isNotNull(douOrderItemList))
        {
            List<DouOrderItem> list = new ArrayList<DouOrderItem>();
            for (DouOrderItem douOrderItem : douOrderItemList)
            {
                douOrderItem.setDouyinOrderId(id);
                list.add(douOrderItem);
            }
            if (list.size() > 0)
            {
                douOrderMapper.batchDouOrderItem(list);
            }
        }
    }
}
