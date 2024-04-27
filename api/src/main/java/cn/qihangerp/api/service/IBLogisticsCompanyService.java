package cn.qihangerp.api.service;

import java.util.List;
import cn.qihangerp.api.domain.BLogisticsCompany;

/**
 * 物流公司Service接口
 * 
 * @author qihang
 * @date 2024-01-12
 */
public interface IBLogisticsCompanyService 
{
    /**
     * 查询物流公司
     * 
     * @param id 物流公司主键
     * @return 物流公司
     */
    public BLogisticsCompany selectBLogisticsCompanyById(Long id);

    /**
     * 查询物流公司列表
     * 
     * @param bLogisticsCompany 物流公司
     * @return 物流公司集合
     */
    public List<BLogisticsCompany> selectBLogisticsCompanyList(BLogisticsCompany bLogisticsCompany);

    /**
     * 新增物流公司
     * 
     * @param bLogisticsCompany 物流公司
     * @return 结果
     */
    public int insertBLogisticsCompany(BLogisticsCompany bLogisticsCompany);

    /**
     * 修改物流公司
     * 
     * @param bLogisticsCompany 物流公司
     * @return 结果
     */
    public int updateBLogisticsCompany(BLogisticsCompany bLogisticsCompany);

    /**
     * 批量删除物流公司
     * 
     * @param ids 需要删除的物流公司主键集合
     * @return 结果
     */
    public int deleteBLogisticsCompanyByIds(Long[] ids);

    /**
     * 删除物流公司信息
     * 
     * @param id 物流公司主键
     * @return 结果
     */
    public int deleteBLogisticsCompanyById(Long id);
}
