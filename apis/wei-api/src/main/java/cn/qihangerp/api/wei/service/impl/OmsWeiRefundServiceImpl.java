package cn.qihangerp.api.wei.service.impl;

import cn.qihangerp.common.PageQuery;
import cn.qihangerp.common.PageResult;
import cn.qihangerp.common.ResultVo;
import cn.qihangerp.common.ResultVoEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.qihangerp.api.wei.domain.OmsWeiRefund;
import cn.qihangerp.api.wei.service.OmsWeiRefundService;
import cn.qihangerp.api.wei.mapper.OmsWeiRefundMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import java.util.List;

/**
* @author TW
* @description 针对表【oms_wei_refund(视频号小店退款)】的数据库操作Service实现
* @createDate 2024-05-07 14:31:54
*/
@AllArgsConstructor
@Service
public class OmsWeiRefundServiceImpl extends ServiceImpl<OmsWeiRefundMapper, OmsWeiRefund>
    implements OmsWeiRefundService{
    private final OmsWeiRefundMapper mapper;


    @Override
    public PageResult<OmsWeiRefund> queryPageList(OmsWeiRefund bo, PageQuery pageQuery) {
        LambdaQueryWrapper<OmsWeiRefund> queryWrapper = new LambdaQueryWrapper<OmsWeiRefund>()
                .eq(bo.getShopId()!=null,OmsWeiRefund::getShopId,bo.getShopId())
                .eq(StringUtils.hasText(bo.getOrderId()),OmsWeiRefund::getOrderId,bo.getOrderId())
                ;

        Page<OmsWeiRefund> page = mapper.selectPage(pageQuery.build(), queryWrapper);

        return PageResult.build(page);
    }

    @Transactional
    @Override
    public ResultVo<Integer> saveRefund(Long shopId, OmsWeiRefund refund) {
        try {
            List<OmsWeiRefund> refunds = mapper.selectList(new LambdaQueryWrapper<OmsWeiRefund>().eq(OmsWeiRefund::getAfterSaleOrderId, refund.getAfterSaleOrderId()));
            if (refunds != null && refunds.size() > 0) {
                // 存在，修改
                OmsWeiRefund update = new OmsWeiRefund();
                update.setId(refunds.get(0).getId());
                update.setOrderId(refund.getOrderId());
                update.setStatus(refund.getStatus());
                update.setUpdateTime(refund.getUpdateTime());
                update.setReturnWaybillId(refund.getReturnWaybillId());
                update.setReturnDeliveryName(refund.getReturnDeliveryName());
                update.setReturnDeliveryId(refund.getReturnDeliveryId());
                update.setComplaintId(refund.getComplaintId());
                mapper.updateById(update);

                return ResultVo.error(ResultVoEnum.DataExist, "退款已经存在，更新成功");
            } else {
                // 不存在，新增
                refund.setShopId(shopId);
                mapper.insert(refund);
                return ResultVo.success();
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultVo.error(ResultVoEnum.SystemException, "系统异常：" + e.getMessage());
        }
    }
}




