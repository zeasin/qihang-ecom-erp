package com.qihang.erp.api.service.impl;

import java.util.List;
import com.zhijian.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.zhijian.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.qihang.erp.api.domain.WmsStockOutEntryItem;
import com.qihang.erp.api.mapper.WmsStockOutEntryMapper;
import com.qihang.erp.api.domain.WmsStockOutEntry;
import com.qihang.erp.api.service.IWmsStockOutEntryService;

/**
 * 出库单Service业务层处理
 * 
 * @author qihang
 * @date 2024-01-10
 */
@Service
public class WmsStockOutEntryServiceImpl implements IWmsStockOutEntryService 
{
    @Autowired
    private WmsStockOutEntryMapper wmsStockOutEntryMapper;

    /**
     * 查询出库单
     * 
     * @param id 出库单主键
     * @return 出库单
     */
    @Override
    public WmsStockOutEntry selectWmsStockOutEntryById(Long id)
    {
        return wmsStockOutEntryMapper.selectWmsStockOutEntryById(id);
    }

    /**
     * 查询出库单列表
     * 
     * @param wmsStockOutEntry 出库单
     * @return 出库单
     */
    @Override
    public List<WmsStockOutEntry> selectWmsStockOutEntryList(WmsStockOutEntry wmsStockOutEntry)
    {
        return wmsStockOutEntryMapper.selectWmsStockOutEntryList(wmsStockOutEntry);
    }

    /**
     * 新增出库单
     * 
     * @param wmsStockOutEntry 出库单
     * @return 结果
     */
    @Transactional
    @Override
    public int insertWmsStockOutEntry(WmsStockOutEntry wmsStockOutEntry)
    {
        wmsStockOutEntry.setCreateTime(DateUtils.getNowDate());
        int rows = wmsStockOutEntryMapper.insertWmsStockOutEntry(wmsStockOutEntry);
        insertWmsStockOutEntryItem(wmsStockOutEntry);
        return rows;
    }

    /**
     * 修改出库单
     * 
     * @param wmsStockOutEntry 出库单
     * @return 结果
     */
    @Transactional
    @Override
    public int updateWmsStockOutEntry(WmsStockOutEntry wmsStockOutEntry)
    {
        wmsStockOutEntry.setUpdateTime(DateUtils.getNowDate());
        wmsStockOutEntryMapper.deleteWmsStockOutEntryItemByEntryId(wmsStockOutEntry.getId());
        insertWmsStockOutEntryItem(wmsStockOutEntry);
        return wmsStockOutEntryMapper.updateWmsStockOutEntry(wmsStockOutEntry);
    }

    /**
     * 批量删除出库单
     * 
     * @param ids 需要删除的出库单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteWmsStockOutEntryByIds(Long[] ids)
    {
        wmsStockOutEntryMapper.deleteWmsStockOutEntryItemByEntryIds(ids);
        return wmsStockOutEntryMapper.deleteWmsStockOutEntryByIds(ids);
    }

    /**
     * 删除出库单信息
     * 
     * @param id 出库单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteWmsStockOutEntryById(Long id)
    {
        wmsStockOutEntryMapper.deleteWmsStockOutEntryItemByEntryId(id);
        return wmsStockOutEntryMapper.deleteWmsStockOutEntryById(id);
    }

    /**
     * 新增出库单明细信息
     * 
     * @param wmsStockOutEntry 出库单对象
     */
    public void insertWmsStockOutEntryItem(WmsStockOutEntry wmsStockOutEntry)
    {
        List<WmsStockOutEntryItem> wmsStockOutEntryItemList = wmsStockOutEntry.getWmsStockOutEntryItemList();
        Long id = wmsStockOutEntry.getId();
        if (StringUtils.isNotNull(wmsStockOutEntryItemList))
        {
            List<WmsStockOutEntryItem> list = new ArrayList<WmsStockOutEntryItem>();
            for (WmsStockOutEntryItem wmsStockOutEntryItem : wmsStockOutEntryItemList)
            {
                wmsStockOutEntryItem.setEntryId(id);
                list.add(wmsStockOutEntryItem);
            }
            if (list.size() > 0)
            {
                wmsStockOutEntryMapper.batchWmsStockOutEntryItem(list);
            }
        }
    }
}
