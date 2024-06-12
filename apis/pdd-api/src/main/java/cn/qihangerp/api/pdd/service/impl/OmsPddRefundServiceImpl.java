package cn.qihangerp.api.pdd.service.impl;

import cn.qihangerp.api.pdd.bo.PddAfterSaleBo;
import cn.qihangerp.api.pdd.mapper.OmsPddOrderItemMapper;
import cn.qihangerp.api.pdd.mapper.OmsPddOrderMapper;
import cn.qihangerp.common.PageQuery;
import cn.qihangerp.common.PageResult;
import cn.qihangerp.common.ResultVo;
import cn.qihangerp.common.ResultVoEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.qihangerp.api.pdd.domain.OmsPddRefund;
import cn.qihangerp.api.pdd.service.OmsPddRefundService;
import cn.qihangerp.api.pdd.mapper.OmsPddRefundMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
* @author TW
* @description 针对表【oms_pdd_refund(拼多多订单退款表)】的数据库操作Service实现
* @createDate 2024-06-12 13:50:40
*/
@AllArgsConstructor
@Service
public class OmsPddRefundServiceImpl extends ServiceImpl<OmsPddRefundMapper, OmsPddRefund>
    implements OmsPddRefundService{
    private final OmsPddRefundMapper mapper;
    private final OmsPddOrderMapper orderMapper;
    private final OmsPddOrderItemMapper orderItemMapper;

    @Override
    public PageResult<OmsPddRefund> queryPageList(PddAfterSaleBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<OmsPddRefund> queryWrapper = new LambdaQueryWrapper<OmsPddRefund>()
                .eq(bo.getShopId()!=null,OmsPddRefund::getShopId,bo.getShopId())
//                .eq(bo.getCustomerExpect()!=null,OmsJdAfterSale::getCustomerExpect,bo.getCustomerExpect())
//                .eq(bo.getOrderId()!=null,OmsJdAfterSale::getOrderId,bo.getOrderId())
//                .eq(StringUtils.hasText(bo.getServiceId()),OmsJdAfterSale::getServiceId,bo.getServiceId())
                ;

        Page<OmsPddRefund> page = mapper.selectPage(pageQuery.build(), queryWrapper);

        return PageResult.build(page);
    }

    @Transactional
    @Override
    public ResultVo<Integer> saveAfter(Long shopId, OmsPddRefund after) {
//        try {
////            if(after.getCustomerExpect().intValue() == 1) {
////                after.setServiceId(after.getRefundId());
////            }
//            List<OmsJdAfterSale> afterSales = mapper.selectList(new LambdaQueryWrapper<OmsJdAfterSale>()
//                    .eq(OmsJdAfterSale::getShopId, shopId)
//                    .eq( OmsJdAfterSale::getServiceId, after.getServiceId()));
////            if(after.getCustomerExpect().intValue() == 1) {
////                // 退款
////                afterSales = mapper.selectList(new LambdaQueryWrapper<JdAfterSale>()
////                        .eq(JdAfterSale::getShopId, after.getShopId())
////                        .eq(after.getRefundId() != null && after.getRefundId() > 0, JdAfterSale::getRefundId, after.getRefundId())
////                );
////            }else {
////                afterSales = mapper.selectList(new LambdaQueryWrapper<JdAfterSale>()
////                        .eq(JdAfterSale::getShopId, after.getShopId())
////                        .eq(after.getApplyId() != null && after.getApplyId() > 0, JdAfterSale::getApplyId, after.getApplyId())
////                );
////            }
//            if (afterSales != null && afterSales.size() > 0) {
//                // 存在，修改
//                OmsJdAfterSale update = new OmsJdAfterSale();
//                update.setId(afterSales.get(0).getId());
//                if(after.getCustomerExpect().intValue() == 1) {
//                    update.setCustomerExpectName("售前退款");
//                    // refund
//                    update.setRefundCheckTime(after.getRefundCheckTime());
//                    update.setRefundStatus(after.getRefundStatus());
//                    update.setRefundCheckUsername(after.getRefundCheckUsername());
//                    update.setRefundCheckRemark(after.getRefundCheckRemark());
//                    List<OmsJdOrder> jdOrders = orderMapper.selectList(new LambdaQueryWrapper<OmsJdOrder>().eq(OmsJdOrder::getOrderId, after.getOrderId()));
//
//                    if(jdOrders!=null && jdOrders.size()>0){
//                        List<OmsJdOrderItem> jdOrderItems = orderItemMapper.selectList(
//                                new LambdaQueryWrapper<OmsJdOrderItem>()
//                                        .eq(OmsJdOrderItem::getOrderId, jdOrders.get(0).getId())
//                        );
//                        if(jdOrderItems!=null && jdOrderItems.size()>0){
//                            update.setSkuId(StringUtils.hasText(jdOrderItems.get(0).getSkuId())?Long.parseLong(jdOrderItems.get(0).getSkuId()):0L);
//                            update.setWareType(10);
//                            update.setWareTypeName("主商品");
//                            update.setWareName(jdOrderItems.get(0).getSkuName());
//                            update.setServiceCount(StringUtils.hasText(jdOrderItems.get(0).getItemTotal()) ?Integer.parseInt(jdOrderItems.get(0).getItemTotal()):0);
//                        }else {
//                            update.setWareName("没有找到订单明细");
//                            update.setServiceCount(0);
//                        }
//                    }else {
//                        update.setWareName("没有找到订单");
//                        update.setServiceCount(0);
//                    }
//                }else {
//                    update.setServiceStatus(after.getServiceStatus());
//                    update.setServiceStatusName(after.getServiceStatusName());
//                    update.setApprovePin(after.getApprovePin());
//                    update.setApproveName(after.getApproveName());
//                    update.setApproveTime(after.getApproveTime());
//                    update.setApproveResult(after.getApproveResult());
//                    update.setApproveResultName(after.getApproveResultName());
//                    update.setProcessPin(after.getProcessPin());
//                    update.setProcessName(after.getProcessName());
//                    update.setProcessTime(after.getProcessTime());
//                    update.setProcessResult(after.getProcessResult());
//                    update.setProcessResultName(after.getProcessResultName());
//                }
//
//
//                update.setUpdateTime(new Date());
//                update.setUpdateBy("拉取更新");
//
//                mapper.updateById(update);
//                return ResultVo.error(ResultVoEnum.DataExist, "退款已经存在，更新成功");
//            } else {
//                // 新增
//                after.setShopId(shopId);
//                after.setCreateTime(new Date());
//                after.setCreateBy("拉取插入");
//                if(after.getCustomerExpect().intValue() == 1) {
//                    after.setCustomerExpectName("售前退款");
//
////                    after.setServiceId(after.getRefundId());
//                    // refund ,补充商品信息
//                    List<OmsJdOrder> jdOrders = orderMapper.selectList(new LambdaQueryWrapper<OmsJdOrder>().eq(OmsJdOrder::getOrderId, after.getOrderId()));
//
//                    if(jdOrders!=null && jdOrders.size()>0){
//                        List<OmsJdOrderItem> jdOrderItems = orderItemMapper.selectList(
//                                new LambdaQueryWrapper<OmsJdOrderItem>().eq(OmsJdOrderItem::getOrderId, jdOrders.get(0).getId()));
//
//                        if(jdOrderItems!=null && jdOrderItems.size()>0){
//                            after.setSkuId(StringUtils.hasText(jdOrderItems.get(0).getSkuId())?Long.parseLong(jdOrderItems.get(0).getSkuId()):0L);
//                            after.setWareType(10);
//                            after.setWareTypeName("主商品");
//                            after.setWareName(jdOrderItems.get(0).getSkuName());
//                            after.setServiceCount(StringUtils.hasText(jdOrderItems.get(0).getItemTotal()) ?Integer.parseInt(jdOrderItems.get(0).getItemTotal()):0);
//                        }else {
//                            after.setWareName("没有找到订单明细");
//                            after.setServiceCount(0);
//                        }
//                    }else {
//                        after.setWareName("没有找到订单");
//                        after.setServiceCount(0);
//                    }
//                }
//                mapper.insert(after);
//                return ResultVo.success();
//            }
//
//        } catch (Exception e) {
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//            return ResultVo.error(ResultVoEnum.SystemException, "系统异常：" + e.getMessage());
//        }
        return null;
    }
}




