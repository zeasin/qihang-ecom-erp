package cn.qihangerp.api.pdd.service;



import cn.qihangerp.api.pdd.domain.PddOrderRefund;

import java.util.List;

/**
 * 拼多多订单退款Service接口
 * 
 * @author qihang
 * @date 2024-01-13
 */
public interface IPddOrderRefundService 
{
    /**
     * 查询拼多多订单退款
     * 
     * @param id 拼多多订单退款主键
     * @return 拼多多订单退款
     */
    public PddOrderRefund selectPddOrderRefundById(Long id);

    /**
     * 查询拼多多订单退款列表
     * 
     * @param pddOrderRefund 拼多多订单退款
     * @return 拼多多订单退款集合
     */
    public List<PddOrderRefund> selectPddOrderRefundList(PddOrderRefund pddOrderRefund);

    /**
     * 新增拼多多订单退款
     * 
     * @param pddOrderRefund 拼多多订单退款
     * @return 结果
     */
    public int insertPddOrderRefund(PddOrderRefund pddOrderRefund);

    /**
     * 修改拼多多订单退款
     * 
     * @param pddOrderRefund 拼多多订单退款
     * @return 结果
     */
    public int updatePddOrderRefund(PddOrderRefund pddOrderRefund);

    /**
     * 批量删除拼多多订单退款
     * 
     * @param ids 需要删除的拼多多订单退款主键集合
     * @return 结果
     */
    public int deletePddOrderRefundByIds(Long[] ids);

    /**
     * 删除拼多多订单退款信息
     * 
     * @param id 拼多多订单退款主键
     * @return 结果
     */
    public int deletePddOrderRefundById(Long id);
}
