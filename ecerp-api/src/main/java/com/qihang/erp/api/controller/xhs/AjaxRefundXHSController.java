//package com.qihang.erp.api.controller.xhs;
//
//import com.b2c.common.utils.DateUtil;
//import com.b2c.entity.DataRow;
//import com.b2c.entity.api.ApiResult;
//import com.b2c.entity.enums.EnumXhsRefundStatus;
//import com.b2c.entity.result.EnumResultVo;
//import com.b2c.entity.xhs.XhsRefundEntity;
//import com.b2c.interfaces.ShopService;
//import com.b2c.interfaces.xhs.XhsRefundService;
//import com.xiaohongshu.fls.opensdk.client.AfterSaleClient;
//import com.xiaohongshu.fls.opensdk.entity.BaseResponse;
//import com.xiaohongshu.fls.opensdk.entity.afterSale.request.AuditReturnsRequest;
//import com.xiaohongshu.fls.opensdk.entity.afterSale.request.GetAfterSaleListRequest;
//import com.xiaohongshu.fls.opensdk.entity.afterSale.response.GetAfterSaleListResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//
//@RequestMapping("/xhs")
//@RestController
//public class AjaxRefundXHSController {
//    @Autowired
//    private ShopService shopService;
//    private static Logger log = LoggerFactory.getLogger(AjaxRefundXHSController.class);
//    @Autowired
//    private XhsRefundService refundService;
//
//
//    /**
//     * 拉取小红书店铺订单
//     * @param req
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping(value = "/pull_refund_ajx", method = RequestMethod.POST)
//    public ApiResult<String> getOrderList(HttpServletRequest req, @RequestBody DataRow reqData) throws Exception {
//        Integer shopId = reqData.getInt("shopId");//抖音shopId
//        String startDate = reqData.getString("startTime");
//        String endDate = reqData.getString("endTime");
//
//        var shop = shopService.getShop(shopId);
//
//        log.info("拉取小红书店铺订单......................");
//
//        String url = "https://ark.xiaohongshu.com/ark/open_api/v3/common_controller";
//
//        AfterSaleClient client = new AfterSaleClient(url,shop.getAppkey(),"2.0",shop.getAppSercet());
//
//        Long endTime = System.currentTimeMillis() / 1000;//订单更新结束时间
//        Long startTime = endTime-(60 * 60 * 24 * 1);//订单更新开始时间
//        if(StringUtils.hasText(startDate)){
//            try {
//                startDate = startDate+" 00:00:00";
//                startTime = DateUtil.dateTimeToStamp(startDate).longValue();
//                if(StringUtils.hasText(endDate)){
//                    endDate = endTime+" 23:59:59";
//                    endTime = DateUtil.dateTimeToStamp(endDate).longValue();
//                }else{
//                    endTime = startTime+ (60 * 60 * 24 * 1) -1 ;
//                }
//            }catch (Exception e){}
//        }
//        log.info("开始拉取小红书退货数据，开始时间:"+DateUtil.unixTimeStampToDate(startTime)+"结束时间："+DateUtil.unixTimeStampToDate(endTime));
//
//        GetAfterSaleListRequest request =  new GetAfterSaleListRequest();
//        request.setStartTime(startTime);
//        request.setEndTime(endTime);
//        request.setTimeType(1);
//
//        //调取接口
//        BaseResponse<GetAfterSaleListResponse> response = client.execute(request,shop.getSessionKey());
//        if(response.success){
//
//            long totalOrder=0;//拉取到的总订单数
//            long addCount =0;//新增的订单数
//            long updCount = 0;//更新的订单数
//            long errCount = 0;//错误的订单数
//
//
//            //拉取订单成功
//            log.info("拉取小红书店铺订单成功。总记录数："+response.getData().total);
//            for (var i:response.getData().getSimpleAfterSaleList()) {
//                XhsRefundEntity r=new XhsRefundEntity();
//                r.setShopId(shopId);
//                r.setReturnsId(i.getReturnsId());
//                r.setReturnType(i.getReturnType());
//                r.setReasonId(i.getReasonId());
//                r.setReason(i.getReason());
//                r.setStatus(i.getStatus());
//                r.setSubStatus(i.getSubStatus());
//                r.setReceiveAbnormalType(i.getReceiveAbnormalType());
//                r.setPackageId(i.getPackageId());
//                r.setExchangePackageId(i.getExchangePackageId());
//                r.setCreatedTime(i.getCreatedTime());
//                r.setReturnExpressNo(i.getReturnExpressNo());
//                r.setReturnExpressCompanyCode(i.getReturnExpressCompanyCode());
//                r.setReturnExpressCompany(i.getReturnExpressCompany());
//                r.setReturnAddress(i.getReturnAddress());
//                r.setShipNeeded(i.getShipNeeded());
//                r.setRefunded(i.refunded ?1:0);
//                r.setRefundStatus(i.getRefundStatus());
//                r.setAutoReceiveDeadline(i.getAutoReceiveDeadline());
//                r.setUpdateTime(i.getUpdateTime());
//                var res = refundService.pullRefund(r);
//                if(res.getCode() == EnumResultVo.SUCCESS.getIndex()) {
//                    log.info("拉取新售后，新增成功，ID："+res.getData() );
//                    addCount++;
//                }
//                else if(res.getCode() == EnumResultVo.DataExist.getIndex()){
//                    log.info("拉取新售后，更新成功，ID："+ res.getData());
//                    updCount++;
//                }else {
//                    log.info("拉取新售后，更新异常，" + res.getMsg()+"。订单号："+r.getReturnsId());
//                    errCount++;
//                }
//            }
//
//
//            String msg = "拉取小红书售后（ShopId："+shopId+"）成功：总数："+response.getData().total+",新增："+addCount+",更新："+updCount+",错误："+errCount;
//            return new ApiResult<>(EnumResultVo.SUCCESS.getIndex(), msg);
//        }else{
//            log.error("拉取小红书店铺订单失败："+response.getMsg()+"。错误代码："+response.getCode());
//
//            return new ApiResult<>(EnumResultVo.Fail.getIndex(),"拉取小红书店铺订单失败："+response.getMsg()+"。错误代码："+response.getCode());
//        }
//
//    }
//
//    /**
//     * 同意仅退款
//     * @param req
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping(value = "/agree_refundOnly", method = RequestMethod.POST)
//    public ApiResult<Integer> returnGoodsToWareHouseSuccess(@RequestBody DataRow req) throws Exception{
//        Integer shopId = req.getInt("shopId");//抖音shopId
//        Long id = req.getLong("id");//dc_xhs_refund表主键id
//        var refund = refundService.getRefundDetailById(id);
//        if(refund == null ) return new ApiResult<>(EnumResultVo.ParamsError.getIndex(),"找不到退款数据");
//        else if(refund.getReturnType()!=5) return new ApiResult<>(EnumResultVo.DataError.getIndex(),"这里仅处理“未发货仅退款”的退款单");
//        else if(refund.getStatus()!=1) return new ApiResult<>(EnumResultVo.DataError.getIndex(),"退款单状态为“"+ EnumXhsRefundStatus.getStatusName(refund.getStatus())+"”,不能处理");
//
//        var shop = shopService.getShop(shopId);
//
//        log.info("推送退款同意。。。。。。小红书店铺售后......................");
//
//        String url = "https://ark.xiaohongshu.com/ark/open_api/v3/common_controller";
//        AfterSaleClient client = new AfterSaleClient(url,shop.getAppkey(),"2.0",shop.getAppSercet());
//        AuditReturnsRequest request = new AuditReturnsRequest();
//        request.setReturnsId(refund.getReturnsId());
//        request.setAction(1);
//        BaseResponse<String> response = client.execute(request,shop.getSessionKey());
//        if(response.success){
//            //同意退款，更新状态
//            refundService.agreeRefundOnly(id);
//        }
//
//        return new ApiResult<>(EnumResultVo.SUCCESS.getIndex(),"SUCCESS");
//    }
//
//}
