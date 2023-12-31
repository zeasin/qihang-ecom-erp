package com.qihang.erp.api.service.impl;

import java.util.List;
import com.zhijian.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.zhijian.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.qihang.erp.api.domain.WmsStockInEntryItem;
import com.qihang.erp.api.mapper.WmsStockInEntryMapper;
import com.qihang.erp.api.domain.WmsStockInEntry;
import com.qihang.erp.api.service.IWmsStockInEntryService;

/**
 * 入库单Service业务层处理
 * 
 * @author qihang
 * @date 2023-12-31
 */
@Service
public class WmsStockInEntryServiceImpl implements IWmsStockInEntryService 
{
    @Autowired
    private WmsStockInEntryMapper wmsStockInEntryMapper;

    /**
     * 查询入库单
     * 
     * @param id 入库单主键
     * @return 入库单
     */
    @Override
    public WmsStockInEntry selectWmsStockInEntryById(Long id)
    {
        return wmsStockInEntryMapper.selectWmsStockInEntryById(id);
    }

    /**
     * 查询入库单列表
     * 
     * @param wmsStockInEntry 入库单
     * @return 入库单
     */
    @Override
    public List<WmsStockInEntry> selectWmsStockInEntryList(WmsStockInEntry wmsStockInEntry)
    {
        return wmsStockInEntryMapper.selectWmsStockInEntryList(wmsStockInEntry);
    }

    /**
     * 新增入库单
     * 
     * @param wmsStockInEntry 入库单
     * @return 结果
     */
    @Transactional
    @Override
    public int insertWmsStockInEntry(WmsStockInEntry wmsStockInEntry)
    {
        wmsStockInEntry.setCreateTime(DateUtils.getNowDate());
        int rows = wmsStockInEntryMapper.insertWmsStockInEntry(wmsStockInEntry);
        insertWmsStockInEntryItem(wmsStockInEntry);
        return rows;
    }

    /**
     * 修改入库单
     * 
     * @param wmsStockInEntry 入库单
     * @return 结果
     */
    @Transactional
    @Override
    public int updateWmsStockInEntry(WmsStockInEntry wmsStockInEntry)
    {
        wmsStockInEntry.setUpdateTime(DateUtils.getNowDate());
        wmsStockInEntryMapper.deleteWmsStockInEntryItemByEntryId(wmsStockInEntry.getId());
        insertWmsStockInEntryItem(wmsStockInEntry);
        return wmsStockInEntryMapper.updateWmsStockInEntry(wmsStockInEntry);
    }

    /**
     * 批量删除入库单
     * 
     * @param ids 需要删除的入库单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteWmsStockInEntryByIds(Long[] ids)
    {
        wmsStockInEntryMapper.deleteWmsStockInEntryItemByEntryIds(ids);
        return wmsStockInEntryMapper.deleteWmsStockInEntryByIds(ids);
    }

    /**
     * 删除入库单信息
     * 
     * @param id 入库单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteWmsStockInEntryById(Long id)
    {
        wmsStockInEntryMapper.deleteWmsStockInEntryItemByEntryId(id);
        return wmsStockInEntryMapper.deleteWmsStockInEntryById(id);
    }

    /**
     * 新增入库单明细信息
     * 
     * @param wmsStockInEntry 入库单对象
     */
    public void insertWmsStockInEntryItem(WmsStockInEntry wmsStockInEntry)
    {
        List<WmsStockInEntryItem> wmsStockInEntryItemList = wmsStockInEntry.getWmsStockInEntryItemList();
        Long id = wmsStockInEntry.getId();
        if (StringUtils.isNotNull(wmsStockInEntryItemList))
        {
            List<WmsStockInEntryItem> list = new ArrayList<WmsStockInEntryItem>();
            for (WmsStockInEntryItem wmsStockInEntryItem : wmsStockInEntryItemList)
            {
                wmsStockInEntryItem.setEntryId(id);
                list.add(wmsStockInEntryItem);
            }
            if (list.size() > 0)
            {
                wmsStockInEntryMapper.batchWmsStockInEntryItem(list);
            }
        }
    }
}
