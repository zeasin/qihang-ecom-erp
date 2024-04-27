package cn.qihangerp.api.controller.kuaishou;//package com.b2c.oms.controller.kuaishou;
//import com.b2c.common.api.ApiResult;
//import com.b2c.common.api.ApiResultEnum;
//import com.b2c.common.utils.DateUtil;
//import com.b2c.common.utils.JsonUtil;
//import com.b2c.entity.DataRow;
//import com.b2c.entity.kwai.DcKwaiAddressVo;
//import com.b2c.entity.kwai.DcKwaiOrderEntity;
//import com.b2c.entity.kwai.DcKwaiOrdersItemEntity;
//import com.b2c.entity.kwai.EnumKwaiExpressCodeVo;
//import com.b2c.entity.result.EnumResultVo;
//import com.b2c.oms.request.OrderConfirmReq;
//import com.b2c.service.oms.DcKwaiOrderService;
//import com.b2c.service.oms.ShopService;
//import com.b2c.service.oms.SysThirdSettingService;
//import com.kuaishou.merchant.open.api.KsMerchantApiException;
//import com.kuaishou.merchant.open.api.client.AccessTokenKsMerchantClient;
//import com.kuaishou.merchant.open.api.request.KsMerchantOrderListRequest;
//import com.kuaishou.merchant.open.api.request.KsMerchantOrderLogisticsUpdateRequest;
//import com.kuaishou.merchant.open.api.response.KsMerchantOrderListResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//import javax.servlet.http.HttpServletRequest;
//import java.math.BigDecimal;
//import java.util.*;
//
//@RequestMapping("/ajax_kwai")
//@RestController
//public class AjaxOrderKwaiController {
//    private static Logger log = LoggerFactory.getLogger(AjaxOrderKwaiController.class);
//    @Autowired
//    private SysThirdSettingService thirdSettingService;
//    @Autowired
//    private DcKwaiOrderService kwaiOrderService;
//    @Autowired
//    private ShopService shopService;
//    private static String appKey="ks701717119425407331";
//    // 对应授权商家快手账号
//    private static long  sellerId = 1372638315L;
//
//    @RequestMapping(value = "/pull_order", method = RequestMethod.POST)
//    public ApiResult<Long> getOrderList(@RequestBody DataRow reqData, HttpServletRequest req){
//        Integer shopId = 13;
//        var shop = shopService.getShop(shopId);
//        var settingEntity = thirdSettingService.getEntity(shop.getType());
//        String accessToken = settingEntity.getAccess_token();
//        String startDate = reqData.getString("startTime");
//        String endDate = reqData.getString("endTime");
//
//        Long endTime = System.currentTimeMillis() / 1000;//订单更新结束时间
//        Long startTime = endTime-(60 * 60 * 24 * 1);//订单更新开始时间
//
//        if(!StringUtils.isEmpty(startDate))startTime = DateUtil.dateToStamp(startDate).longValue();
//
//        if (!StringUtils.isEmpty(endDate)) endTime = DateUtil.dateTimeToStamp(endDate + " 23:59:00").longValue();
//
//
//        long kaishidaojiesu = endTime - startTime;
//        long forSize = (kaishidaojiesu % (60 * 60 * 24) == 0) ? kaishidaojiesu / (60 * 60 * 24) : kaishidaojiesu / (60 * 60 * 24) + 1;//计算需要循环的次数
//
//        log.info("开始循环更新快手订单。开始时间：" + DateUtil.unixTimeStampToDate(startTime) + "结束时间：" + DateUtil.unixTimeStampToDate(endTime) + "总共循环" + forSize);
//        int pageIndex = 1;
//        int pageSize = 50;
//
//        ApiResult<Long> result=null;
//        for (int i = 0; i < forSize; i++) {
//            Long startTime1 = startTime + i * 60 * 60 * 24;
//            Long endTime1 = startTime1 + 60 * 60 * 24;
//            result = this.editKwaiOrder(pageIndex,pageSize,accessToken, startTime1, endTime1);
//            if(result.getCode()>0) return new ApiResult<>(result.getCode(), result.getMsg());
//            //计算总页数
//            int totalPage = (result.getData().intValue() % pageSize == 0) ? result.getData().intValue() / pageSize : (result.getData().intValue() / pageSize) + 1;
//
//            while (pageIndex < totalPage) {
//                pageIndex++;
//                result = this.editKwaiOrder(pageIndex,pageSize,accessToken, startTime1, endTime1);
//            }
//            pageIndex=1;
//        }
//        return result;
//    }
//
//    public ApiResult<Long> editKwaiOrder(Integer pageIndex,Integer pageSize,String token,Long startTime,Long endTime) {
//
//        AccessTokenKsMerchantClient tokenKsMerchantClient = new AccessTokenKsMerchantClient(appKey);
//        KsMerchantOrderListRequest ksMerchantOrderListRequest = new KsMerchantOrderListRequest();
//
//        // common param
//        ksMerchantOrderListRequest.setAccessToken(token);
//        ksMerchantOrderListRequest.setUid(sellerId);
//        ksMerchantOrderListRequest.setApiMethodVersion(1);
//        // business param
//        ksMerchantOrderListRequest.setType(1);
//        ksMerchantOrderListRequest.setQueryType(2);
//        ksMerchantOrderListRequest.setSellerId(sellerId);
//        ksMerchantOrderListRequest.setCurrentPage(pageIndex);
//        ksMerchantOrderListRequest.setPageSize(pageSize);
//        ksMerchantOrderListRequest.setBeginTime(startTime*1000);//时间范围只能24小时
//        ksMerchantOrderListRequest.setEndTime(endTime*1000);
//        ksMerchantOrderListRequest.setPcursor("");
//
//        // api invoke
//        try {
//            KsMerchantOrderListResponse response = tokenKsMerchantClient.execute(ksMerchantOrderListRequest);
//            if(!StringUtils.isEmpty(response.getErrorMsg()) && response.getResult()==24)return new ApiResult<>(ApiResultEnum.TokenFail.getIndex(), "异常："+response.getErrorMsg());
//            if(!StringUtils.isEmpty(response.getErrorMsg()) && response.getResult()!=24)return new ApiResult<>(ApiResultEnum.SystemException.getIndex(), "异常："+response.getErrorMsg());
//            var list= response.getMerchantOrderListData().getOrderInfoList();
//            for(var obj :list){
//                DcKwaiOrderEntity order= new DcKwaiOrderEntity();
//                var address = JsonUtil.strToObject(obj.getAddress(),DcKwaiAddressVo.class);
//                order.setOid(obj.getOid());
//                Long discountFee=obj.getDiscountFee();
//                order.setDiscountfee(obj.getDiscountFee()>0 ? new BigDecimal(discountFee/100) : new BigDecimal(0));
//                Long expressFee=obj.getExpressFee();
//                order.setExpressfee(obj.getExpressFee()>0 ? new BigDecimal(expressFee.doubleValue() /100) : new BigDecimal(0));
//                order.setNum(obj.getNum());
//                order.setConsignee(address.getConsignee());
//                order.setMobile(address.getMobile());
//                order.setPaytime(obj.getPayTime()>0 ? obj.getPayTime()/1000 : 0L);
//                order.setBuyerRemark(obj.getRemark());
//                order.setSellerRemark(obj.getSellerNoteList().toString());
//                order.setStatus(obj.getStatus());
//                order.setRefund(obj.getRefund());
//                Long totalFee=obj.getTotalFee();
//                order.setTotalfee(totalFee>0 ? new BigDecimal(totalFee.doubleValue()/100) : new BigDecimal(0));
//                order.setProvince(address.getProvince());
//                order.setCity(address.getCity());
//                order.setDistrict(address.getDistrict());
//                StringBuilder sb= new StringBuilder(address.getProvince()).append(address.getCity()).append(address.getDistrict()).append(address.getAddress());
//                order.setAddress(sb.toString());
//                order.setCreatetime(obj.getCreateTime()>0 ? obj.getCreateTime()/1000 :0L);
//                List<DcKwaiOrdersItemEntity> items = new ArrayList<>();
//                for(var kwaiItem:obj.getOrderProductInfoList()){
//                    DcKwaiOrdersItemEntity item=new DcKwaiOrdersItemEntity();
//                    item.setItemid(kwaiItem.getItemId());
//                    item.setItempicurl(kwaiItem.getItemPicUrl());
//                    Long price=kwaiItem.getPrice();
//                    item.setPrice(price>0 ? new BigDecimal(price.doubleValue()/100) : new BigDecimal(0));
//                    item.setRefundId(kwaiItem.getRefundId());
//                    item.setRefundStatus(kwaiItem.getRefundStatus());
//                    item.setSkunick(StringUtils.isEmpty(kwaiItem.getSkuNick()) ? "" : kwaiItem.getSkuNick());
//                    item.setItemtitle(kwaiItem.getItemTitle());
//                    item.setNum(kwaiItem.getNum());
//                    item.setGoodsspec(kwaiItem.getSkuDesc());
//                    items.add(item);
//                }
//                order.setItems(items);
//                var result= kwaiOrderService.editKwaiOrder(order);
//                log.info(order.getOid()+"更新:"+result.getMsg());
//            }
//            return new ApiResult<>(ApiResultEnum.SUCCESS.getIndex(), "成功",response.getMerchantOrderListData().getTotalSize());
//        } catch (KsMerchantApiException e) {
//            return new ApiResult<>(ApiResultEnum.Fail.getIndex(), "异常："+e.getErrorMsg());
//        }
//    }
//    /**
//     * 订单确认
//     * @return
//     */
//    @RequestMapping(value = "/affirm_order", method = RequestMethod.POST)
//    public ApiResult<Integer> orderAffirm(@RequestBody OrderConfirmReq req){
//        if (req.getOrderId() == null || req.getOrderId() <= 0)
//            return new ApiResult<>(ApiResultEnum.ParamsError.getIndex(), "参数错误，缺少orderId");
//
//        if (StringUtils.isEmpty(req.getClientId()))req.setClientId(0);
//
//        if (StringUtils.isEmpty(req.getReceiver()))
//            return new ApiResult<>(ApiResultEnum.ParamsError.getIndex(), "参数错误，缺少receiver");
//        if (StringUtils.isEmpty(req.getMobile()))
//            return new ApiResult<>(ApiResultEnum.ParamsError.getIndex(), "参数错误，缺少mobile");
//        if (StringUtils.isEmpty(req.getAddress()))
//            return new ApiResult<>(ApiResultEnum.ParamsError.getIndex(), "参数错误，缺少address");
//        var result = kwaiOrderService.kwaiOrderAffirm(req.getOrderId(),req.getClientId(),req.getReceiver(), req.getMobile(), req.getAddress(), req.getSellerMemo());
//        return new ApiResult<>(result.getCode(), result.getMsg());
//    }
//    /**
//     * 订单发货
//     * @param req
//     * @return
//     */
//    @RequestMapping(value = "/send_order", method = RequestMethod.POST)
//    public ApiResult<Integer> orderSend(@RequestBody DataRow req){
//        if(StringUtils.isEmpty(req.get("orderId")))return new ApiResult<>(EnumResultVo.ParamsError.getIndex(),"参数错误，订单id不能为空");
//        if(StringUtils.isEmpty(req.get("code")))return new ApiResult<>(EnumResultVo.ParamsError.getIndex(),"参数错误，快递单号不能为空");
//        Integer shopId = 13;
//        var shop = shopService.getShop(shopId);
//        var settingEntity = thirdSettingService.getEntity(shop.getType());
//        AccessTokenKsMerchantClient tokenKsMerchantClient = new AccessTokenKsMerchantClient(appKey);
//        KsMerchantOrderLogisticsUpdateRequest ksMerchantLogisticsRequest=new KsMerchantOrderLogisticsUpdateRequest();
//        try {
//            ksMerchantLogisticsRequest.setAccessToken(settingEntity.getAccess_token());
//            ksMerchantLogisticsRequest.setUid(sellerId);
//            ksMerchantLogisticsRequest.setApiMethodVersion(1);
//            ksMerchantLogisticsRequest.setOrderId(req.getLong("orderId"));
//            ksMerchantLogisticsRequest.setExpressCode(EnumKwaiExpressCodeVo.getIndex(req.getString("name")));
//            ksMerchantLogisticsRequest.setExpressNo(req.getString("code"));
//            ksMerchantLogisticsRequest.setSellerId(sellerId);
//            var ksResponse = tokenKsMerchantClient.execute(ksMerchantLogisticsRequest);
//            if(ksResponse.getResult()==1){
//                kwaiOrderService.updKwaiOrderStatus(req.getLong("orderId"));
//                return new ApiResult<>(ApiResultEnum.SUCCESS.getIndex(), "成功");
//            }else return new ApiResult<>(ApiResultEnum.Fail.getIndex(), ksResponse.getErrorMsg());
//        }catch (KsMerchantApiException e) {
//             return new ApiResult<>(ApiResultEnum.Fail.getIndex(), "异常："+e.getErrorMsg());
//        }
//    }
//}
