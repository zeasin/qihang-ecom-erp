package cn.qihangerp.open.tao.service.impl;

import cn.qihangerp.common.ResultVo;
import cn.qihangerp.common.ResultVoEnum;
import cn.qihangerp.open.tao.domain.OmsTaoOrderItem;
import cn.qihangerp.open.tao.mapper.OmsTaoOrderItemMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.qihangerp.open.tao.domain.OmsTaoOrder;
import cn.qihangerp.open.tao.service.OmsTaoOrderService;
import cn.qihangerp.open.tao.mapper.OmsTaoOrderMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Date;
import java.util.List;

/**
* @author TW
* @description 针对表【oms_tao_order(淘宝订单表)】的数据库操作Service实现
* @createDate 2024-04-30 13:52:20
*/
@AllArgsConstructor
@Service
public class OmsTaoOrderServiceImpl extends ServiceImpl<OmsTaoOrderMapper, OmsTaoOrder>
    implements OmsTaoOrderService{
    private final OmsTaoOrderMapper mapper;
    private final OmsTaoOrderItemMapper itemMapper;
    @Transactional
    @Override
    public ResultVo<Integer> saveOrder(Long shopId, OmsTaoOrder order) {
        if(order == null ) return ResultVo.error(ResultVoEnum.SystemException);
        try {
            List<OmsTaoOrder> taoOrders = mapper.selectList(new LambdaQueryWrapper<OmsTaoOrder>().eq(OmsTaoOrder::getTid, order.getTid()));
            if (taoOrders != null && taoOrders.size() > 0) {
                // 存在，修改
                OmsTaoOrder update = new OmsTaoOrder();
                update.setId(taoOrders.get(0).getId());
                update.setReceiverName(order.getReceiverName());
                update.setReceiverMobile(order.getReceiverMobile());
                update.setReceiverAddress(order.getReceiverAddress());
                update.setSid(order.getSid());
                update.setSellerRate(order.getSellerRate());
                update.setBuyerRate(order.getBuyerRate());
                update.setStatus(order.getStatus());
                update.setModified(order.getModified());
                update.setEndTime(order.getEndTime());
                update.setConsignTime(order.getConsignTime());

                update.setUpdateTime(new Date());
                update.setReceivedPayment(order.getReceivedPayment());
                update.setAvailableConfirmFee(order.getAvailableConfirmFee());
                mapper.updateById(update);
                // 更新item
                for (var item : order.getItems()) {
                    List<OmsTaoOrderItem> taoOrderItems = itemMapper.selectList(new LambdaQueryWrapper<OmsTaoOrderItem>().eq(OmsTaoOrderItem::getOid, item.getOid()));
                    if (taoOrderItems != null && taoOrderItems.size() > 0) {
                        // 更新
                        OmsTaoOrderItem itemUpdate = new OmsTaoOrderItem();
                        itemUpdate.setId(taoOrderItems.get(0).getId());
                        itemUpdate.setRefundId(item.getRefundId());
                        itemUpdate.setRefundStatus(item.getRefundStatus());
                        itemUpdate.setStatus(item.getStatus());
                        itemUpdate.setBuyerRate(item.getBuyerRate());
                        itemUpdate.setSellerRate(item.getSellerRate());
                        itemUpdate.setEndTime(item.getEndTime());
                        itemUpdate.setConsignTime(item.getConsignTime());
                        itemUpdate.setShippingType(item.getShippingType());
                        itemUpdate.setLogisticsCompany(item.getLogisticsCompany());
                        itemUpdate.setInvoiceNo(item.getInvoiceNo());
                        itemMapper.updateById(itemUpdate);
                    } else {
                        // 新增
                        itemMapper.insert(item);
                    }
                }
                return ResultVo.error(ResultVoEnum.DataExist, "订单已经存在，更新成功");
            } else {
                // 不存在，新增
                order.setShopId(shopId);
                order.setCreateTime(new Date());
                mapper.insert(order);
                // 添加item
                for (var item : order.getItems()) {
                    itemMapper.insert(item);
                }
                return ResultVo.success();
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            System.out.println("保存订单数据错误："+e.getMessage());
            return ResultVo.error(ResultVoEnum.SystemException, "系统异常：" + e.getMessage());
        }
    }
}




