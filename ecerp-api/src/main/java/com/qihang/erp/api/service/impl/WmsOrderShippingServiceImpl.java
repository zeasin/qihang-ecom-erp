package com.qihang.erp.api.service.impl;

import java.util.List;
import com.zhijian.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qihang.erp.api.mapper.WmsOrderShippingMapper;
import com.qihang.erp.api.domain.WmsOrderShipping;
import com.qihang.erp.api.service.IWmsOrderShippingService;

/**
 * 仓库订单发货Service业务层处理
 * 
 * @author qihang
 * @date 2024-01-07
 */
@Service
public class WmsOrderShippingServiceImpl implements IWmsOrderShippingService 
{
    @Autowired
    private WmsOrderShippingMapper wmsOrderShippingMapper;

    /**
     * 查询仓库订单发货
     * 
     * @param id 仓库订单发货主键
     * @return 仓库订单发货
     */
    @Override
    public WmsOrderShipping selectWmsOrderShippingById(Long id)
    {
        return wmsOrderShippingMapper.selectWmsOrderShippingById(id);
    }

    /**
     * 查询仓库订单发货列表
     * 
     * @param wmsOrderShipping 仓库订单发货
     * @return 仓库订单发货
     */
    @Override
    public List<WmsOrderShipping> selectWmsOrderShippingList(WmsOrderShipping wmsOrderShipping)
    {
        return wmsOrderShippingMapper.selectWmsOrderShippingList(wmsOrderShipping);
    }

    /**
     * 新增仓库订单发货
     * 
     * @param wmsOrderShipping 仓库订单发货
     * @return 结果
     */
    @Override
    public int insertWmsOrderShipping(WmsOrderShipping wmsOrderShipping)
    {
        wmsOrderShipping.setCreateTime(DateUtils.getNowDate());
        return wmsOrderShippingMapper.insertWmsOrderShipping(wmsOrderShipping);
    }

    /**
     * 修改仓库订单发货
     * 
     * @param wmsOrderShipping 仓库订单发货
     * @return 结果
     */
    @Override
    public int updateWmsOrderShipping(WmsOrderShipping wmsOrderShipping)
    {
        wmsOrderShipping.setUpdateTime(DateUtils.getNowDate());
        return wmsOrderShippingMapper.updateWmsOrderShipping(wmsOrderShipping);
    }

    /**
     * 批量删除仓库订单发货
     * 
     * @param ids 需要删除的仓库订单发货主键
     * @return 结果
     */
    @Override
    public int deleteWmsOrderShippingByIds(Long[] ids)
    {
        return wmsOrderShippingMapper.deleteWmsOrderShippingByIds(ids);
    }

    /**
     * 删除仓库订单发货信息
     * 
     * @param id 仓库订单发货主键
     * @return 结果
     */
    @Override
    public int deleteWmsOrderShippingById(Long id)
    {
        return wmsOrderShippingMapper.deleteWmsOrderShippingById(id);
    }
}
