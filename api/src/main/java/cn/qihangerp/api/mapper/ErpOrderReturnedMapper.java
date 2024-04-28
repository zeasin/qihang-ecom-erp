package cn.qihangerp.api.mapper;

import java.util.List;
import cn.qihangerp.domain.ErpOrderReturned;
import org.apache.ibatis.annotations.Mapper;

/**
 * 退换货Mapper接口
 * 
 * @author qihang
 * @date 2024-01-13
 */
@Mapper
public interface ErpOrderReturnedMapper 
{
    /**
     * 查询退换货
     * 
     * @param id 退换货主键
     * @return 退换货
     */
    public ErpOrderReturned selectErpOrderReturnedById(Long id);

    /**
     * 查询退换货列表
     * 
     * @param erpOrderReturned 退换货
     * @return 退换货集合
     */
    public List<ErpOrderReturned> selectErpOrderReturnedList(ErpOrderReturned erpOrderReturned);

    /**
     * 新增退换货
     * 
     * @param erpOrderReturned 退换货
     * @return 结果
     */
    public int insertErpOrderReturned(ErpOrderReturned erpOrderReturned);

    /**
     * 修改退换货
     * 
     * @param erpOrderReturned 退换货
     * @return 结果
     */
    public int updateErpOrderReturned(ErpOrderReturned erpOrderReturned);

    /**
     * 删除退换货
     * 
     * @param id 退换货主键
     * @return 结果
     */
    public int deleteErpOrderReturnedById(Long id);

    /**
     * 批量删除退换货
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteErpOrderReturnedByIds(Long[] ids);
}
