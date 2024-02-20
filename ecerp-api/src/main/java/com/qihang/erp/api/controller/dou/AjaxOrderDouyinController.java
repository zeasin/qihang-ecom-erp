package com.qihang.erp.api.controller.dou;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.doudian.open.api.order_searchList.OrderSearchListRequest;
import com.doudian.open.api.order_searchList.OrderSearchListResponse;
import com.doudian.open.api.order_searchList.param.OrderSearchListParam;
import com.doudian.open.core.AccessToken;
import com.doudian.open.core.AccessTokenBuilder;
import com.doudian.open.core.GlobalConfig;
import com.qihang.erp.api.common.ApiResult;
import com.qihang.erp.api.common.EnumResultVo;
import com.qihang.erp.api.service.IShopService;
import com.qihang.erp.api.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpResponse;
import java.util.*;

@RequestMapping("/douapi")
@RestController
public class AjaxOrderDouyinController {
    private static Logger log = LoggerFactory.getLogger(AjaxOrderDouyinController.class);
    @Autowired
    private IShopService shopService;
//    @Autowired
//    private DouyinOrderService douyinOrderService;
//
//    @Autowired
//    private ErpSalesOrderService salesOrderService;

    @RequestMapping(value = "/order/pull_order", method = RequestMethod.GET)
    public ApiResult<ErpSalesPullCountResp> getOrderList(DouRequest reqData, HttpServletRequest req) throws Exception {
        Integer updType = reqData.getUpdType();//更新类型0拉取新订单1更新订单
        String startDate = reqData.getStartDate();//reqData.getString("startTime");
        String endDate = reqData.getEndDate();//reqData.getString("endTime");

        ApiResult<ErpSalesPullCountResp> result=null;//返回结果


        Long endTime = System.currentTimeMillis() / 1000;//订单更新结束时间
        Long startTime = endTime-(60 * 60 * 24 * 7);//订单更新开始时间

//        if(updType.intValue()==0){

        if (StringUtils.isEmpty(startDate)) {
//            var pullOrderLog=salesOrderService.getErpOrderPullLogByShopId(shopId,updType);
//            if(pullOrderLog!=null) {
//                startTime = pullOrderLog.getEndTime() - 60 * 10;
//            }
        } else {
            //选择了开始日期,从开始时间直接循环更新到结束时间
            startTime = DateUtil.dateToStamp(startDate).longValue();

            if (StringUtils.isEmpty(endDate)) endTime = System.currentTimeMillis() / 1000;
            else endTime = DateUtil.dateTimeToStamp(endDate + " 23:59:00").longValue();
//        startTime = 1587966628L;endTime = startTime + 60 * 30;
        }

         result=this.editDouyinOrder(startTime,endTime, reqData.getShopId());

        return result;
    }

    private ApiResult<ErpSalesPullCountResp> editDouyinOrder(Long startTime,Long endTime,Long shopId) throws Exception{
        var shop = shopService.selectShopById(shopId);
        if(shop == null) return new ApiResult<>(EnumResultVo.Fail.getIndex(), "店铺不存在！");
        String appKey = shop.getAppkey();
        String appSercet = shop.getAppSercet();
        if(!StringUtils.hasText(appKey) || !StringUtils.hasText(appSercet)) return new ApiResult<>(EnumResultVo.Fail.getIndex(), "参数错误：请设置appkey和serecet");
//        appKey = "7005157746437834253";
//        appSercet="8104c8b8-9085-4a80-9248-629759b4f1a3";
//        String method = "order.list";
//         method = "order.searchList";
        //设置appKey和appSecret，全局设置一次
        GlobalConfig.initAppKey(appKey);
        GlobalConfig.initAppSecret(appSercet);
        //入参为shopId
        AccessToken accessToken = AccessTokenBuilder.build(4463798L); //123456是shopId
        if(accessToken.getCode().equals("30002")){
            return new ApiResult<>(EnumResultVo.Fail.getIndex(),accessToken.getMsg());
        }
        if(!accessToken.getCode().equals("10000")){
            return new ApiResult<>(EnumResultVo.Fail.getIndex(),accessToken.getMsg());
        }
        int updCount =0;
        int addCount =0;
        int failCount = 0;

        OrderSearchListRequest orderReq = new OrderSearchListRequest();
        OrderSearchListParam orderParam = new OrderSearchListParam();
        orderParam.setPage(1L);
        orderParam.setSize(20L);
        orderParam.setOrderAsc(false);
        orderParam.setCreateTimeStart(startTime);
        orderReq.setParam(orderParam);
        OrderSearchListResponse orderRes = null;
        try {
            orderRes = orderReq.execute(accessToken);
            if(orderRes.getCode().equals("10000")){
                if(orderRes.getData() == null || orderRes.getData().getTotal()  == 0) return new ApiResult<>(EnumResultVo.DataError.getIndex(),"无订单可以更新");

                // 循环处理订单
                for(var order:orderRes.getData().getShopOrderList()){
//                    DcDouyinOrdersEntity douYinOrder= JsonUtil.strToObject(JSON.toJSONString(json),DcDouyinOrdersEntity.class);
//                    var address = JsonUtil.strToObject(douYinOrder.getPostAddr(),DcDouyinAddressVo.class);
//                    String postAddr=new StringBuilder(address.getProvince().getName()).append(address.getCity().getName()).append(address.getTown().getName()).append(address.getDetail()).toString();
//                    douYinOrder.setPostAddr(postAddr);
//
//                    var result =  douyinOrderService.editDouYinOrder(douYinOrder);
//                    if(result.getCode() == EnumResultVo.DataExist.getIndex()) updCount++;
//                    else if(result.getCode() == EnumResultVo.Fail.getIndex()) failCount++;
//                    else if(result.getCode() == EnumResultVo.SUCCESS.getIndex()) addCount++;
                }
            }else{
                return new ApiResult<>(EnumResultVo.Fail.getIndex(),orderRes.getSubMsg());
            }
        }catch (Exception e){
            return new ApiResult<>(EnumResultVo.Fail.getIndex(),e.getMessage());
        }

        /*LinkedHashMap<String, Object> jsonMap =new LinkedHashMap<>();
//        jsonMap.put("end_time",DateUtil.dateToString(new Date(),"yyyy/MM/dd HH:mm:ss"));//截至时间
        jsonMap.put("end_time",DateUtil.unixTimeStampToDate2(endTime,"yyyy/MM/dd HH:mm:ss"));//截至时间
        jsonMap.put("is_desc","1");//设置了此字段即为desc (最近的在前), 不设置默认asc
        jsonMap.put("page","0");//查询的第几页,默认值为0, 第一页下标从0开始
        jsonMap.put("size","50");//每页大小 默认为10, 最大100
        jsonMap.put("start_time",DateUtil.unixTimeStampToDate2(startTime,"yyyy/MM/dd HH:mm:ss"));//开始时间
//        jsonMap.put("start_time","2020/05/03 00:00:01");//开始时间

        //jsonParam.put("order_by","create_time");//支持按照订单生成时间和最后修改时间进行搜索 create_time 或 update_time (默认创建时间)
        //jsonParam.put("order_status","1");//订单状态

        JSONObject jsonObject = new JSONObject(true);
        jsonObject.putAll(jsonMap);

        String paramJson =jsonObject.toJSONString();//"{\"end_time\":\""+endTime1+"\",\"is_desc\":\"1\",\"page\":\"0\",\"size\":\"50\",\"start_time\":\""+startTime1+"\"}";
        String timestamp = DateUtil.dateToString(new Date(),"yyyy/MM/dd HH:mm:ss");
        String signStr = "app_key"+appKey+"method"+method+"param_json"+paramJson+"timestamp"+timestamp+"v"+"1";
        signStr = appSercet+signStr+appSercet;

        String sign = MD5Utils.MD5Encode(signStr);

        String  sendUrl= "https://openapi.jinritemai.com/order/list";

        Map<String, String> params = new HashMap<>();
        params.put("app_key", appKey);
        params.put("method", method);
        params.put("param_json",paramJson);
        params.put("timestamp", timestamp);
        params.put("v", "1");
        params.put("sign", sign);
        int updCount =0;
        int addCount =0;
        int failCount = 0;
        try {
            HttpResponse<String> response = ExpressClient.doPost(sendUrl, HttpUtil.map2Url(params));
            if (response.statusCode() == 200) {
                String resultStr = response.body();
                JSONObject resultObj = JSONObject.parseObject(resultStr);
                if(resultObj.getInteger("code") == 70000){
                   return new ApiResult<>(EnumResultVo.Fail.getIndex(), "请求API错误："+resultObj.getString("sub_msg"));
                }
                JSONObject obj = JSONObject.parseObject(response.body()).getJSONObject("data");
                if(obj.getLong("count").intValue()==0)return new ApiResult<>(EnumResultVo.DataError.getIndex(),"无订单可以更新");
                var jsonArray= obj.getJSONArray("list");
                for(var json:jsonArray){
//                    DcDouyinOrdersEntity douYinOrder= JsonUtil.strToObject(JSON.toJSONString(json),DcDouyinOrdersEntity.class);
//                    var address = JsonUtil.strToObject(douYinOrder.getPostAddr(),DcDouyinAddressVo.class);
//                    String postAddr=new StringBuilder(address.getProvince().getName()).append(address.getCity().getName()).append(address.getTown().getName()).append(address.getDetail()).toString();
//                    douYinOrder.setPostAddr(postAddr);
//
//                    var result =  douyinOrderService.editDouYinOrder(douYinOrder);
//                    if(result.getCode() == EnumResultVo.DataExist.getIndex()) updCount++;
//                    else if(result.getCode() == EnumResultVo.Fail.getIndex()) failCount++;
//                    else if(result.getCode() == EnumResultVo.SUCCESS.getIndex()) addCount++;
                }
            }
        } catch (Exception e) {
            return new ApiResult<>(EnumResultVo.Fail.getIndex(), "系统异常："+e.getMessage());
        }*/

        ErpSalesPullCountResp resp = new ErpSalesPullCountResp();//返回结果
        resp.setStartTime(DateUtil.unixTimeStampToDate(startTime));
        resp.setEndTime(DateUtil.unixTimeStampToDate(endTime));
        resp.setAddCount(addCount);
        resp.setFailCount(failCount);
        resp.setUpdCount(updCount);

        return new ApiResult<>(EnumResultVo.SUCCESS.getIndex(), "SUCCESS",resp);
    }

    /**
     * 订单发货
     * @param req
     * @return
     */
//    @RequestMapping(value = "/send_order", method = RequestMethod.POST)
//    public ApiResult<Integer> orderSend(@RequestBody DataRow req){
//        if(StringUtils.isEmpty(req.get("orderId")))return new ApiResult<>(EnumResultVo.ParamsError.getIndex(),"参数错误，订单id不能为空");
//        if(StringUtils.isEmpty(req.get("code")))return new ApiResult<>(EnumResultVo.ParamsError.getIndex(),"参数错误，快递单号不能为空");
//        String orderId=req.getString("orderId");
//        Integer shopId=8;
//        var shop = shopService.getShop(shopId);
//        String appKey = shop.getAppkey();
//        String appSercet = shop.getAppSercet();
//        String method = "order.logisticsAdd";
//        LinkedHashMap<String, Object> jsonMap =new LinkedHashMap<>();
//        jsonMap.put("company","韵达快递");//快递单号
//        jsonMap.put("logistics_code",req.getString("code"));//快递单号
//        jsonMap.put("logistics_id","9");//快递公司id
//        jsonMap.put("order_id",orderId);//订单id
//
//        JSONObject jsonObject = new JSONObject(true);
//        jsonObject.putAll(jsonMap);
//
//        String paramJson =jsonObject.toJSONString();
//        System.out.println(paramJson);
//        String timestamp = DateUtil.dateToString(new Date(),"yyyy/MM/dd HH:mm:ss");
//
//        String signStr = "app_key"+appKey+"method"+method+"param_json"+paramJson+"timestamp"+timestamp+"v"+"1";
//        signStr = appSercet+signStr+appSercet;
//
//        String sign = MD5Utils.MD5Encode(signStr);
//
//        String  sendUrl= "https://openapi.jinritemai.com/order/logisticsAdd";
//
//        Map<String, String> params = new HashMap<>();
//        params.put("app_key", appKey);
//        params.put("method", method);
//        params.put("param_json",paramJson);
//        params.put("timestamp", timestamp);
//        params.put("v", "1");
//        params.put("sign", sign);
//
//        try {
//            HttpResponse<String> response = ExpressClient.doPost(sendUrl, map2Url(params));
//            if (response.statusCode() == 200) {
//                JSONObject obj = JSONObject.parseObject(response.body());
//                if(obj.getString("message").equals("success")){
//                    douyinOrderService.sendDouyinOrder(orderId);
//                }
//            }
//        } catch (Exception e) {
//            return new ApiResult<>(EnumResultVo.Fail.getIndex(), "系统异常："+e.getMessage());
//        }
//        return new ApiResult<>(EnumResultVo.SUCCESS.getIndex(), "SUCCESS");
//
//    }
    /**
     * 更新退货订单
     * @param req
     * @return
     */
//    @RequestMapping(value = "/pull_refund_order", method = RequestMethod.POST)
//    public ApiResult<Integer> pullRefundOrder(@RequestBody DataRow req){
//        Integer shopId=8;
//        var shop = shopService.getShop(shopId);
//        String appKey = shop.getAppkey();
//        String appSercet = shop.getAppSercet();
//        String method = "afterSale.orderList";
//        String refundMethod="refund.orderList";
//        Long endTime=System.currentTimeMillis() / 1000;//订单更新结束时间
//        Long startTime=endTime-(60 * 60 * 24 * 7);//订单更新开始时间
//        if(!StringUtils.isEmpty(req.get("startTime"))) startTime=DateUtil.dateTimeToStamp(req.getString("startTime")+" 00:00:00").longValue();
//        if(!StringUtils.isEmpty(req.get("endTime")))endTime=DateUtil.dateTimeToStamp(req.getString("endTime")+" 23:59:00").longValue();
//        LinkedHashMap<String, Object> jsonMap =new LinkedHashMap<>();
//        jsonMap.put("end_time",DateUtil.unixTimeStampToDate2(endTime,"yyyy/MM/dd HH:mm:ss"));//截至时间
//        jsonMap.put("order_by","create_time");
//        jsonMap.put("page","0");
//        jsonMap.put("size","20");
//        jsonMap.put("start_time",DateUtil.unixTimeStampToDate2(startTime,"yyyy/MM/dd HH:mm:ss"));//开始时间
//
//        JSONObject jsonObject = new JSONObject(true);
//        jsonObject.putAll(jsonMap);
//
//        String paramJson =jsonObject.toJSONString();
//        String timestamp = DateUtil.dateToString(new Date(),"yyyy/MM/dd HH:mm:ss");
//
//        String signStr = "app_key"+appKey+"method"+method+"param_json"+paramJson+"timestamp"+timestamp+"v"+"1";
//        signStr = appSercet+signStr+appSercet;
//
//        String sign = MD5Utils.MD5Encode(signStr);
//
//        String  sendUrl= "https://openapi.jinritemai.com";
//
//        Map<String, String> params = new HashMap<>();
//        params.put("app_key", appKey);
//        params.put("method", method);
//        params.put("param_json",paramJson);
//        params.put("timestamp", timestamp);
//        params.put("v", "1");
//        params.put("sign", sign);
//
//        try {
//            HttpResponse<String> response = ExpressClient.doPost(sendUrl+"/afterSale/orderList", HttpUtil.map2Url(params));
//            if (response.statusCode() == 200) {
//                JSONObject obj = JSONObject.parseObject(response.body()).getJSONObject("data");
//                if(obj.getLong("count").intValue()==0)return new ApiResult<>(EnumResultVo.DataError.getIndex(),"无售后订单可以更新");
//                var jsonArray= obj.getJSONArray("list");
//                for(var json:jsonArray){
//                    DcDouyinOrdersRefundEntity refundOrder= JsonUtil.strToObject(JSON.toJSONString(json),DcDouyinOrdersRefundEntity.class);
//                    refundOrder.setLogisticsCode("");
//                    var detail = afterSaleOrderDetail(refundOrder.getOrderId());
//                    if(detail!=null){
//                        refundOrder.setQuestionDesc(detail.getApplyInfo().getQuestionDesc());
//                        refundOrder.setApplyTime(DateUtil.dateTimeToStamp(detail.getProcessBar().getApplyTime()));
//                        refundOrder.setLogisticsCompany(detail.getStageInfo().getLogisticsInfo().getLogisticsName());
//                        refundOrder.setLogisticsCode(detail.getStageInfo().getLogisticsInfo().getLogisticsCode());
//                    }
//                    refundOrder.setAfterType(0);
//                    douyinOrderService.editDouYinRefundOrder(refundOrder);
//                }
//            }
//            //更新退款订单
//            String refundSignStr = "app_key"+appKey+"method"+refundMethod+"param_json"+paramJson+"timestamp"+timestamp+"v"+"1";
//            refundSignStr = appSercet+refundSignStr+appSercet;
//            String refundSign = MD5Utils.MD5Encode(refundSignStr);
//            params.put("method",refundMethod);
//            params.put("param_json",paramJson);
//            params.put("sign", refundSign);
//            HttpResponse<String> refundResponse = ExpressClient.doPost(sendUrl+"/refund/orderList", HttpUtil.map2Url(params));
//            if (refundResponse.statusCode() == 200) {
//                JSONObject obj = JSONObject.parseObject(refundResponse.body()).getJSONObject("data");
//                if(obj.getLong("count").intValue()==0)return new ApiResult<>(EnumResultVo.DataError.getIndex(),"无售后订单可以更新");
//                var jsonArray= obj.getJSONArray("list");
//                for(var json:jsonArray){
//                    JSONObject jsonObj=(JSONObject)json;
//                    DcDouyinOrdersRefundEntity refundOrder= JsonUtil.strToObject(JSON.toJSONString(json),DcDouyinOrdersRefundEntity.class);
//                    JSONObject goodObj = jsonObj.getJSONArray("child").getJSONObject(0);
//                    refundOrder.setAfterType(1);
//                    refundOrder.setTotalAmount(jsonObj.getDouble("order_total_amount"));
//                    refundOrder.setProductName(goodObj.getString("product_name"));
//                    refundOrder.setProductPic(goodObj.getString("product_pic"));
//                    refundOrder.setPid(goodObj.getString("pid"));
//                    refundOrder.setComboNum(jsonObj.getJSONArray("child").size());
//                    refundOrder.setCode(jsonObj.getString("code"));
//                    refundOrder.setQuestionDesc(refundOrder.getSellerWords());
//                    refundOrder.setApplyTime(refundOrder.getUpdateTime());
//                    douyinOrderService.editDouYinRefundOrder(refundOrder);
//                }
//            }
//        } catch (Exception e) {
//            return new ApiResult<>(EnumResultVo.Fail.getIndex(), "系统异常："+e.getMessage());
//        }
//        return new ApiResult<>(EnumResultVo.SUCCESS.getIndex(), "SUCCESS");
//
//    }
//
//    //售后订单详情
//    public DcDouyinOrdersRefundDetail afterSaleOrderDetail(String refundOrderId){
//        Integer shopId=8;
//        var shop = shopService.getShop(shopId);
//        String appKey = shop.getAppkey();
//        String appSercet = shop.getAppSercet();
//        String method = "afterSale.orderDetail";
//        String paramJson ="{\"order_id\":\""+refundOrderId+"\"}";
//        String timestamp = DateUtil.dateToString(new Date(),"yyyy/MM/dd HH:mm:ss");
//
//        String signStr = "app_key"+appKey+"method"+method+"param_json"+paramJson+"timestamp"+timestamp+"v"+"1";
//        signStr = appSercet+signStr+appSercet;
//
//        String sign = MD5Utils.MD5Encode(signStr);
//
//        Map<String, String> params = new HashMap<>();
//        params.put("app_key", appKey);
//        params.put("method", method);
//        params.put("param_json",paramJson);
//        params.put("timestamp", timestamp);
//        params.put("v", "1");
//        params.put("sign", sign);
//        try {
//            String  sendUrl= "https://openapi.jinritemai.com";
//            HttpResponse<String> detailResponse = ExpressClient.doPost(sendUrl+"/afterSale/orderDetail", HttpUtil.map2Url(params));
//            JSONObject detailJson = JSONObject.parseObject(detailResponse.body());
//            //System.out.println(detailJson.toJSONString());
//            if(detailJson.getString("message").equals("success")){
//                JSONObject datailObj = detailJson.getJSONObject("data").getJSONObject("data");
//                return JsonUtil.strToObject(JSON.toJSONString(datailObj),DcDouyinOrdersRefundDetail.class);
//            }
//        }catch (Exception ex){
//            return null;
//        }
//        return null;
//    }


}
