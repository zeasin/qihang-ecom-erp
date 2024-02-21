//package com.qihang.erp.api.controller.xhs;
//
//import com.alibaba.fastjson.JSONObject;
//import com.b2c.common.utils.DateUtil;
//import com.b2c.entity.DataRow;
//import com.b2c.entity.StockDataVo;
//import com.b2c.entity.api.ApiResult;
//import com.b2c.entity.enums.EnumErpOrderSendStatus;
//import com.b2c.entity.enums.EnumXhsOrderStatus;
//import com.b2c.entity.result.EnumResultVo;
//import com.b2c.entity.result.ResultVo;
//import com.b2c.entity.xhs.XhsOrderEntity;
//import com.b2c.entity.xhs.XhsOrderItemEntity;
//import com.b2c.erp.DataConfigObject;
//import com.b2c.interfaces.ExpressCompanyService;
//import com.b2c.interfaces.ShopService;
//import com.b2c.interfaces.erp.ErpGoodsService;
//import com.b2c.interfaces.wms.ErpStockOutFormService;
//import com.b2c.interfaces.xhs.XhsGoodsService;
//import com.b2c.interfaces.xhs.XhsOrderService;
//import com.xiaohongshu.fls.opensdk.client.PackageClient;
//import com.xiaohongshu.fls.opensdk.entity.BaseResponse;
//import com.xiaohongshu.fls.opensdk.entity.packages.request.GetPackageDetailRequest;
//import com.xiaohongshu.fls.opensdk.entity.packages.request.GetPackageListRequest;
//import com.xiaohongshu.fls.opensdk.entity.packages.request.GetReceiverInfoRequest;
//import com.xiaohongshu.fls.opensdk.entity.packages.request.GetReceiverInfoRequest.*;
//import com.xiaohongshu.fls.opensdk.entity.packages.request.PackageDeliverRequest;
//import com.xiaohongshu.fls.opensdk.entity.packages.response.GetPackageDetailResponse;
//import com.xiaohongshu.fls.opensdk.entity.packages.response.GetPackagesListResponse;
//import com.xiaohongshu.fls.opensdk.entity.packages.response.GetReceiveInfoResponse;
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
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//@RequestMapping("/xhs")
//@RestController
//public class AjaxOrderXHSController {
//    @Autowired
//    private ShopService shopService;
//    private static Logger log = LoggerFactory.getLogger(AjaxOrderXHSController.class);
//    @Autowired
//    private XhsOrderService orderService;
//    @Autowired
//    private XhsGoodsService goodsService;
//    @Autowired
//    private ErpGoodsService erpGoodsService;
//    @Autowired
//    private ErpStockOutFormService stockOutFormService;
//    @Autowired
//    private ExpressCompanyService expressCompanyService;
//
//    /**
//     * 拉取小红书店铺订单
//     * @param req
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping(value = "/pull_order_ajx", method = RequestMethod.POST)
//    public ApiResult<String> getOrderList(HttpServletRequest req,@RequestBody DataRow reqData) throws Exception {
//        Integer shopId = reqData.getInt("shopId");//抖音shopId
//
//        String startDate = reqData.getString("startTime");
//        String endDate = reqData.getString("endTime");
//        var shop = shopService.getShop(shopId);
//
//        log.info("拉取小红书店铺订单......................");
//
//        String url = "https://ark.xiaohongshu.com/ark/open_api/v3/common_controller";
//
//        PackageClient client = new PackageClient(url,shop.getAppkey(),"2.0",shop.getAppSercet());
//
//        Long endTime = System.currentTimeMillis() / 1000;//订单更新结束时间
//        Long startTime = endTime-(60 * 60 * 24 * 1);//订单更新开始时间
//
//        if(StringUtils.hasText(startDate)){
//            try {
//                startTime = DateUtil.dateTimeToStamp(startDate).longValue();
//                if(StringUtils.hasText(endDate)){
//                    endTime = DateUtil.dateTimeToStamp(endDate).longValue();
//                }else{
//                    endTime = startTime+ (60 * 60 * 24 * 1);
//                }
//            }catch (Exception e){}
//        }
//        log.info("开始拉取小红书退货数据，开始时间:"+DateUtil.unixTimeStampToDate(startTime)+"结束时间："+DateUtil.unixTimeStampToDate(endTime));
//
//
//        GetPackageListRequest request =  new GetPackageListRequest();
//        request.setStartTime(startTime);
//        request.setEndTime(endTime-1);
//        request.setTimeType(1);
//
//        //调取接口
//        BaseResponse<GetPackagesListResponse> response = client.execute(request,shop.getSessionKey());
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
//            for(var psd:response.getData().getPackageList()){
//                log.info(JSONObject.toJSONString(psd));
//                GetPackageDetailRequest detailRequest = new GetPackageDetailRequest();
//                detailRequest.setPackageId(psd.getPackageId());
//                BaseResponse<GetPackageDetailResponse> detailResponse = client.execute(detailRequest,shop.getSessionKey());
//                GetPackageDetailResponse d = detailResponse.getData();
//
//                log.info(JSONObject.toJSONString(d));
//
//                XhsOrderEntity order = new XhsOrderEntity();
//                order.setOrderId(d.getPackageId());
//                order.setShopType(shop.getType());
//                order.setShopId(shopId);
//                order.setOrderType(d.getPackageType());
//                order.setOrderStatus(d.getPackageStatus());
//                order.setAfterSalesStatus(d.getPackageAfterSalesStatus());
//                order.setCancelStatus(d.getCancelStatus());
//                order.setCreatedTime(d.getCreatedTime());
//                order.setPaidTime(d.getPaidTime());
//                order.setUpdateTime(d.getUpdateTime());
//                order.setDeliveryTime(d.getDeliveryTime());
//                order.setCancelTime(d.getCancelTime());
//                order.setFinishTime(d.getFinishTime());
//                order.setPromiseLastDeliveryTime(d.getPromiseLastDeliveryTime());
//                order.setCustomerRemark(d.getCustomerRemark());
//                order.setSellerRemark(d.getSellerRemark());
//                order.setSellerRemarkFlag(d.getSellerRemarkFlag());
//                order.setPresaleDeliveryStartTime(d.getPresaleDeliveryStartTime());
//                order.setPresaleDeliveryEndTime(d.getPresaleDeliveryEndTime());
//                order.setOriginalPackageId(d.getOriginalPackageId());
//                order.setTotalPayAmount(d.getTotalPayAmount());
//                order.setTotalShippingFree(d.getTotalShippingFree());
//                order.setExpressCompanyCode(d.getExpressCompanyCode());
//                order.setExpressTrackingNo(d.getExpressTrackingNo());
//                order.setOpenAddressId(d.getOpenAddressId());
//                order.setProvince(d.getReceiverProvinceName());
//                order.setCity(d.getReceiverCityName());
//                order.setDistrict(d.getReceiverDistrictName());
//
//                List<XhsOrderItemEntity> items = new ArrayList<>();
//                for (var i:d.getItemList()) {
//                    XhsOrderItemEntity item = new XhsOrderItemEntity();
//                    item.setItemId(i.getItemId());
//                    item.setItemImage(i.getItemImage());
//                    item.setItemName(i.getItemName());
//                    item.setErpCode(i.getErpcode());
//                    item.setItemSpec(i.getItemSpec());
//                    item.setQuantity(i.getItemQuantity());
//                    item.setTotalPaidAmount(i.getTotalPaidAmount());
//                    item.setTotalMerchantDiscount(i.getTotalMerchantDiscount());
//                    item.setTotalRedDiscount(i.getTotalRedDiscount());
//                    item.setItemTag(i.getItemTag());
//                    items.add(item);
//                }
//                order.setItems(items);
//                var res = orderService.pullOrder(order);
//
//                if(res.getCode() == EnumResultVo.SUCCESS.getIndex()) {
//                    log.info("拉取新订单，新增成功，ID："+res.getData() );
//                    addCount++;
//                }
//                else if(res.getCode() == EnumResultVo.DataExist.getIndex()){
//                    log.info("拉取新订单，更新成功，ID："+ res.getData());
//                    updCount++;
//                }else {
//                    log.info("拉取新订单，更新异常，" + res.getMsg()+"。订单号："+order.getOrderId());
//                    errCount++;
//                }
//
//            }
//
//
//            String msg = "拉取小红书订单（ShopId："+shopId+"）成功：总数："+response.getData().total+",新增："+addCount+",更新："+updCount+",错误："+errCount;
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
//     * 拉取小红书店铺订单收货地址
//     * @param req
//     * @param reqData
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping(value = "/pull_order_address_ajx", method = RequestMethod.POST)
//    public ApiResult<String> getOrderAddress(HttpServletRequest req,@RequestBody DataRow reqData) throws Exception {
//        Integer shopId = reqData.getInt("shopId");//抖音shopId
//        String orderId = reqData.getString("orderId");
//        Long id = reqData.getLong("id");
//        String openAddressId = reqData.getString("openAddressId");
//        var shop = shopService.getShop(shopId);
//
//        log.info("拉取小红书店铺订单收货地址......................");
//
//        String url = "https://ark.xiaohongshu.com/ark/open_api/v3/common_controller";
//        PackageClient client = new PackageClient(url,shop.getAppkey(),"2.0",shop.getAppSercet());
////更新快递信息
////        CommonClient client1 = new CommonClient(url,shop.getAppkey(),"2.0",shop.getAppSercet());
////        GetExpressCompanyListRequest r1 = new GetExpressCompanyListRequest();
////
////        BaseResponse<GetExpressCompanyListResponse> resp = client1.execute(r1,shop.getSessionKey());
////        if(resp.success) {
////            for (var item: resp.getData().getExpressCompanyInfos()) {
////                expressCompanyService.update(item.expressCompanyCode,item.getExpressCompanyName());
////                log.info("更新完成");
////            }
////
////        }
//        //收货信息参数
//        GetReceiverInfoRequest request = new GetReceiverInfoRequest();
//
//        List<GetReceiverInfoRequest.ReceiverQuery> queryList = new ArrayList<>();
//
//        ReceiverQuery query =  request.new ReceiverQuery();
//        query.setOpenAddressId(openAddressId);
//        query.setPackageId(orderId);
//        queryList.add(query);
//        request.setReceiverQueries(queryList);
//
//        BaseResponse<GetReceiveInfoResponse> response = client.execute(request,shop.getSessionKey());
//        log.info(response.msg);
//        if(response.success){
//            GetReceiveInfoResponse.ReceiverInfo ri = response.getData().receiverInfos.get(0);
//
//            orderService.pullOrderAddress(id,ri.getReceiverProvinceName(),ri.getReceiverCityName(),ri.getReceiverDistrictName(),ri.getReceiverAddress(),ri.getReceiverName(),ri.getReceiverPhone());
//
//            return new ApiResult<>(EnumResultVo.SUCCESS.getIndex(), "拉取小红书店铺订单收货地址成功：","<span>"+ri.getReceiverName()+"</span><span>"+ri.getReceiverPhone()+"</span><br /><span>"+ri.getReceiverAddress()+"</span>" );
//        }else{
//            return new ApiResult<>(EnumResultVo.Fail.getIndex(), "拉取小红书店铺订单收货地址失败："+response.getMsg() );
//        }
//
//
//    }
//
//
//    /**
//     * 更新订单备注
//     * @param req
//     * @param reqData
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping(value = "/order_remark_ajax", method = RequestMethod.POST)
//    public ApiResult<String> orderRemark(HttpServletRequest req,@RequestBody DataRow reqData) throws Exception {
//        Long id = reqData.getLong("id");
//        String remark = reqData.getString("remark");
//
//        log.info("保存订单备注......................"+remark);
//
//        orderService.updOrderRemark(id,remark);
//
//        return new ApiResult<>(EnumResultVo.SUCCESS.getIndex(), "SUCCESS" );
//    }
//
//
//    /***
//     * 订单手动发货(调用小红书接口)
//     * @return
//     */
//    @RequestMapping(value = "/order_confirm", method = RequestMethod.POST)
//    public ApiResult<Integer> orderConfirm(@RequestBody DataRow data , HttpServletRequest request) throws IOException {
//        Long orderId = data.getLong("orderId");
//        /**********************查询订单数据（check）*************************/
//        var order = orderService.getOrderDetailById(orderId);
//        if (order == null) return new ApiResult<>(EnumResultVo.ParamsError.getIndex(), "没有找到订单数据，请检查参数是否正确");
//
//        if(order.getOrderStatus() != EnumXhsOrderStatus.STATUS_4.getIndex()){
//            return new ApiResult<>(EnumResultVo.ParamsError.getIndex(), "订单状态为："+EnumXhsOrderStatus.getStatusName(order.getOrderStatus())+"，无法发货");
//        }
//        if(order.getAfterSalesStatus().intValue()!=1)
//            return new ApiResult<>(EnumResultVo.ParamsError.getIndex(), "订单有售后，无法发货");
//        if(order.getErpSendStatus().intValue()!=0)
//            return new ApiResult<>(EnumResultVo.ParamsError.getIndex(), "仓库状态为"+ EnumErpOrderSendStatus.getName(order.getErpSendStatus())+"。不能操作");
//
//        //加入拣货队列的数据
//        List<StockDataVo> stockDataVos = new ArrayList<>();
//
//        /**********************检查订单明细中的商品*************************/
//        for (var item : order.getItems()) {
//            var spec = goodsService.getGoodsSpecByXhsSkuId(item.getItemId());
//
//            if (spec == null) return new ApiResult<>(EnumResultVo.ParamsError.getIndex(), "订单中的商品不存在，请更新小红书商品数据");
//            else if (spec.getErpSkuId() == null || spec.getErpSkuId().intValue() == 0) {
//                return new ApiResult<>(EnumResultVo.ParamsError.getIndex(), "订单中的商品" + item.getItemId() + "在ERP系统中没有找到，请将小红书商品与ERP系统商品关联");
//            }
//
//            //把ERP系统中goods_spec id设置到订单明细去
//            item.setErpSkuId(spec.getErpSkuId());
//            /*************检查商品库存信息*************/
//            int stock = erpGoodsService.getGoodsSpecStockById(spec.getErpSkuId());
//            if (stock < item.getQuantity().intValue()) {
//                return new ApiResult<>(EnumResultVo.ParamsError.getIndex(), "商品" + item.getItemId() + "可用库存不足，库存为" + stock);
//            }
//
//            StockDataVo vo = new StockDataVo();
//            vo.setSpecId(spec.getErpSkuId());
//            vo.setQuantity(item.getQuantity());
//            vo.setSpecNum(spec.getErpCode());
//            vo.setOrderItemId(item.getId());
//            stockDataVos.add(vo);
//        }
//
//        /****************一、添加到仓库出库队列，(添加到待捡货、锁定库存）*******************/
//        //public ResultVo<Long> joinStockOutQueue(List<Long> orderIdList)
//
//        ResultVo<Long> resultVo = stockOutFormService.joinStockOutQueueForXHS(order.getOrderId(), orderId, DataConfigObject.getInstance().getLoginUserName(), stockDataVos, "", "");
//        return new ApiResult<>(resultVo.getCode(), resultVo.getMsg());
//    }
//
//    /***
//     * 订单手动发货(调用小红书接口)
//     * @return
//     */
//    @RequestMapping(value = "/order_hand_send_ajax", method = RequestMethod.POST)
//    public ApiResult<Integer> erderSend(@RequestBody DataRow data , HttpServletRequest request) throws IOException {
//        Long orderId = data.getLong("id");
//        String companyCode = data.getString("companyCode");
//        String expressNo = data.getString("code");
//
//        if (StringUtils.isEmpty(companyCode))
//            return new ApiResult<>(EnumResultVo.ParamsError.getIndex(), "请选择快递公司");
//        if (StringUtils.isEmpty(expressNo))
//            return new ApiResult<>(EnumResultVo.ParamsError.getIndex(), "请输入快递单号");
//
//
//        /**********************查询订单数据（check）*************************/
//        var order = orderService.getOrderDetailById(orderId);
//        if (order == null) return new ApiResult<>(EnumResultVo.ParamsError.getIndex(), "没有找到订单数据，请检查参数是否正确");
//        if (order.getOrderStatus() != EnumXhsOrderStatus.STATUS_4.getIndex()) {
//            return new ApiResult<>(EnumResultVo.ParamsError.getIndex(), "订单状态为：" + EnumXhsOrderStatus.getStatusName(order.getOrderStatus()) + "，无法发货");
//        }
//        if (order.getAfterSalesStatus().intValue() != 1)
//            return new ApiResult<>(EnumResultVo.ParamsError.getIndex(), "订单有售后，无法发货");
//
//        if (order.getErpSendStatus().intValue() != 2)
//            return new ApiResult<>(EnumResultVo.ParamsError.getIndex(), "仓库状态为" + EnumErpOrderSendStatus.getName(order.getErpSendStatus()) + "。不能操作");
//
//
//        /***********推送发货信息到小红书************/
//        var shop = shopService.getShop(order.getShopId());
//        log.info("ERP系统发货处理成功，开始推送发货信息到小红书");
//        String url = "https://ark.xiaohongshu.com/ark/open_api/v3/common_controller";
//        PackageClient client = new PackageClient(url, shop.getAppkey(), "2.0", shop.getAppSercet());
//        PackageDeliverRequest request1 = new PackageDeliverRequest();
//        request1.setExpressCompanyCode(companyCode);
//        request1.setPackageId(order.getOrderId());
//        request1.setExpressNo(expressNo);
//
//        BaseResponse<String> response = client.execute(request1, shop.getSessionKey());
//        log.info(response.msg);
//        if (response.success) {
//            ResultVo<Long> resultVo = stockOutFormService.sendOrderXHS(orderId, companyCode, expressNo);
//            return new ApiResult<>(resultVo.getCode(), resultVo.getMsg());
//        } else {
//            return new ApiResult<>(EnumResultVo.Fail.getIndex(), response.msg);
//        }
//    }
//}
