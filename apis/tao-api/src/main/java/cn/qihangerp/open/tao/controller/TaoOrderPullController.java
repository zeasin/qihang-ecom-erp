package cn.qihangerp.open.tao.controller;


import cn.qihangerp.common.ApiResult;
import cn.qihangerp.common.ResultVoEnum;
import cn.qihangerp.common.enums.EnumShopType;
import cn.qihangerp.core.config.ServerConfig;
import cn.qihangerp.open.tao.bo.TaoRequest;
import cn.qihangerp.open.tao.domain.TaoOrder;
import cn.qihangerp.open.tao.domain.TaoOrderRefund;
import cn.qihangerp.open.tao.service.ITaoOrderRefundService;
import cn.qihangerp.open.tao.service.ITaoOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 淘系订单更新
 */
@RestController
@RequestMapping("/tao-api")
public class TaoOrderPullController {
    private static Logger log = LoggerFactory.getLogger(TaoOrderPullController.class);
//    @Autowired
//    private IShopSettingService shopSettingService;
    @Autowired
    private ITaoOrderService tmallOrderService;
    @Autowired
    private ITaoOrderRefundService tmallOrderReturnService;
//    @Autowired
//    private IShopService shopService;
    @Autowired
    private ApiPullHelper apiPullHelper;
    /**
     * 更新前的检查
     *
     * @param shopId
     * @return
     * @throws
     */


    /**
     * 拉取天猫订单
     *
     * @param req
     * @return
     * @throws ApiException
     */
    @GetMapping("/order/pull_order")
    @ResponseBody
    public ApiResult<Object> orderPull(TaoRequest req) throws IOException {
        log.info("/**************主动更新tao订单****************/");
        if (req.getShopId() == null || req.getShopId() <= 0) {
            return new ApiResult<>(ResultVoEnum.ParamsError.getIndex(), "参数错误，没有店铺Id");
        }
        var checkResult = apiPullHelper.checkBefore(req.getShopId());
        if (checkResult.getCode() != ResultVoEnum.SUCCESS.getIndex()) {
            return new ApiResult<>(checkResult.getCode(), checkResult.getMsg(),checkResult.getData());
        }
        String sessionKey = checkResult.getData().getAccessToken();
        String url = checkResult.getData().getApiRequestUrl();
        String appKey = checkResult.getData().getAppKey();
        String appSecret = checkResult.getData().getAppSecret();


        log.info("/**************主动更新tao订单，条件判断完成，开始更新。。。。。。****************/");
        Long pageSize = 50l;
        Long pageIndex = 1l;

        //第一次获取
        TaoBaoOpenOrderUpdResult<TaoOrder> upResult = TaoBaoOpenOrderUpdHelper.updTmallOrder(pageIndex, pageSize, url, appKey, appSecret, sessionKey);

        if (upResult.getCode().intValue() != 0) {
            log.info("/**************主动更新tao订单：第一次获取结果失败：" + upResult.getMsg() + "****************/");
            if(upResult.getCode().intValue() == ResultVoEnum.TokenFail.getIndex()){
                return new ApiResult<>(ResultVoEnum.TokenFail.getIndex(), "Token已过期，请重新授权",checkResult.getData());
            }
            return new ApiResult<>(ResultVoEnum.SystemException.getIndex(), upResult.getMsg());
        }

        log.info("/**************主动更新tao订单：第一次获取结果：总记录数" + upResult.getTotalRecords() + "****************/");
        int insertSuccess = 0;//新增成功的订单
        int totalError = 0;
        int hasExistOrder = 0;//已存在的订单数

        //循环插入订单数据到数据库
        for (var order : upResult.getList()) {

            //插入订单数据
            var result = tmallOrderService.updateTmallOrderForOpenTaobao(req.getShopId(), order);
            if (result.getCode() == ResultVoEnum.DataExist.getIndex()) {
                //已经存在
                log.info("/**************主动更新tao订单：开始更新数据库：" + order.getId() + "存在、更新****************/");
                hasExistOrder++;
            } else if (result.getCode() == ResultVoEnum.SUCCESS.getIndex()) {
                log.info("/**************主动更新tao订单：开始更新数据库：" + order.getId() + "不存在、新增****************/");
                insertSuccess++;
            } else {
                log.info("/**************主动更新tao订单：开始更新数据库：" + order.getId() + "报错****************/");
                totalError++;
            }
        }

        //计算总页数
        int totalPage = (upResult.getTotalRecords() % pageSize == 0) ? upResult.getTotalRecords() / pageSize.intValue() : (upResult.getTotalRecords() / pageSize.intValue()) + 1;
        pageIndex++;

        while (pageIndex <= totalPage) {

            TaoBaoOpenOrderUpdResult<TaoOrder> upResult1 = TaoBaoOpenOrderUpdHelper.updTmallOrder(pageIndex, pageSize, url, appKey, appSecret, sessionKey);
            //循环插入订单数据到数据库
            for (var order : upResult1.getList()) {
                //插入订单数据
                var result = tmallOrderService.updateTmallOrderForOpenTaobao(req.getShopId(), order);
                if (result.getCode() == ResultVoEnum.DataExist.getIndex()) {
                    //已经存在
                    log.info("/**************主动更新tao订单：开始更新数据库：" + order.getId() + "存在、更新****************/");
                    hasExistOrder++;
                } else if (result.getCode() == ResultVoEnum.SUCCESS.getIndex()) {
                    log.info("/**************主动更新tao订单：开始更新数据库：" + order.getId() + "不存在、新增****************/");
                    insertSuccess++;
                } else {
                    log.info("/**************主动更新tao订单：开始更新数据库：" + order.getId() + "报错****************/");
                    totalError++;
                }
            }
            pageIndex++;
        }
        String msg = "成功，总共找到：" + upResult.getTotalRecords() + "条订单，新增：" + insertSuccess + "条，添加错误：" + totalError + "条，更新：" + hasExistOrder + "条";
        log.info("/**************主动更新tao订单：END：" + msg + "****************/");
        return new ApiResult<>(ResultVoEnum.SUCCESS.getIndex(), msg);

    }

    /**
     * 更新单个订单
     *
     * @param taoRequest
     * @param model
     * @param request
     * @return
     * @throws
     */
    @RequestMapping("/order/pull_order_by_num")
    @ResponseBody
    public ApiResult<String> getOrderPullByNum(@RequestBody TaoRequest taoRequest, Model model, HttpServletRequest request)  {
        log.info("/**************主动更新tao订单by number****************/");
        if (taoRequest.getShopId() == null || taoRequest.getShopId() <= 0) {
            return new ApiResult<>(ResultVoEnum.ParamsError.getIndex(), "参数错误，没有店铺Id");
        }
        if (taoRequest.getOrderId() == null || taoRequest.getOrderId() <= 0) {
            return new ApiResult<>(ResultVoEnum.ParamsError.getIndex(), "参数错误，缺少orderId");
        }

        Long shopId = taoRequest.getShopId();
        var checkResult = apiPullHelper.checkBefore(shopId);

        if (checkResult.getCode() != ResultVoEnum.SUCCESS.getIndex()) {
            return new ApiResult<>(checkResult.getCode(), checkResult.getMsg());
        }

        String sessionKey = checkResult.getData().getAccessToken();
        String url = checkResult.getData().getApiRequestUrl();
        String appKey = checkResult.getData().getAppKey();
        String appSecret = checkResult.getData().getAppSecret();

//        TaobaoClient client = new DefaultTaobaoClient(url, appKey, appSecret);
//
//        TradeFullinfoGetRequest req = new TradeFullinfoGetRequest();
////        req.setFields("tid,type,status,payment,orders,promotion_details,post_fee");
//        req.setFields("tid,post_fee,receiver_name,receiver_state,receiver_city,receiver_district,receiver_address,receiver_mobile,receiver_phone,received_payment,num," +
//                "type,status,payment,orders,rx_audit_status,sellerMemo,pay_time,created,buyer_nick");
//        req.setTid(taoRequest.getOrderId());
//        TradeFullinfoGetResponse rsp = client.execute(req, sessionKey);
//        System.out.println(rsp.getBody());
//
//        var trade = rsp.getTrade();
//        if (trade == null) {
//            //没有找到退款单
//            log.info("/**************主动更新tao订单：END： 没有找到退单****************/");
//            return new ApiResult<>(EnumResultVo.DataError.getIndex(), "没有找到退单" + taoRequest.getOrderId());
//        }
//
//        //TODO:开始更新
//        TaoOrder order = new TaoOrder();
//        order.setId(trade.getTid().toString());
//        order.setOrderCreateTime(trade.getCreated());
//        order.setOrderModifyTime(trade.getModified());
//        order.setPayTime(trade.getPayTime());
//        order.setTotalAmount(BigDecimal.valueOf(Double.parseDouble(trade.getPayment())));
//        order.setShippingFee(BigDecimal.valueOf(Double.parseDouble(trade.getPostFee())));
//        order.setPayAmount(BigDecimal.valueOf(Double.parseDouble(trade.getPayment())));
//        order.setBuyerName(trade.getBuyerNick());
//        order.setSellerMemo(trade.getSellerMemo());
//        order.setProvince(trade.getReceiverState());
//        order.setCity(trade.getReceiverCity());
//        order.setDistrict(trade.getReceiverDistrict());
//        order.setStatus(EnumTmallOrderStatus.getStatus(trade.getStatus()));
//        order.setStatusStr(trade.getStatus());
//        List<TaoOrderItem> items = new ArrayList<>();
//        for (var item : trade.getOrders()) {
//            TaoOrderItem orderItem = new TaoOrderItem();
//            orderItem.setSpecNumber(item.getOuterSkuId());
//            orderItem.setGoodsNumber(item.getOuterIid());
//            orderItem.setProductImgUrl(item.getPicPath());
//            orderItem.setGoodsTitle(item.getTitle());
//            orderItem.setPrice(BigDecimal.valueOf(Double.parseDouble(item.getPrice())));
//            orderItem.setQuantity(item.getNum());
//            orderItem.setSubItemId(item.getOid().toString());
//            orderItem.setSkuInfo(item.getSkuPropertiesName());
//            orderItem.setItemAmount(BigDecimal.valueOf(Double.parseDouble(item.getPayment())));
//            orderItem.setDiscountFee(new BigDecimal(item.getDiscountFee()));
//            orderItem.setAdjustFee(new BigDecimal(item.getAdjustFee()));
//
//            orderItem.setRefundStatusStr(item.getRefundStatus());
//            items.add(orderItem);
//        }
//        order.setTaoOrderItemList(items);
//
//        var result = tmallOrderService.updateTmallOrderForOpenTaobao(taoRequest.getShopId(), order);
//        if (result.getCode() == EnumResultVo.DataExist.getIndex()) {
//            //已经存在
//            log.info("/**************主动更新tao订单：开始更新数据库：" + order.getId() + "存在、更新****************/");
//        } else if (result.getCode() == EnumResultVo.SUCCESS.getIndex()) {
//            log.info("/**************主动更新tao订单：开始更新数据库：" + order.getId() + "不存在、新增****************/");
//        } else {
//            log.info("/**************主动更新tao订单：开始更新数据库：" + order.getId() + "报错****************/");
//        }
        String msg = "";

        log.info("/**************主动更新tao订单：END：" + msg + "****************/");
        return new ApiResult<>(ResultVoEnum.SUCCESS.getIndex(), msg);
    }

    /**
     * 更新退货订单
     *
     * @param model
     * @param request
     * @return
     * @throws
     */
    @RequestMapping("/refund/pull_refund_order")
    @ResponseBody
    public ApiResult<String> refundOrderPull(@RequestBody TaoRequest taoRequest, Model model, HttpServletRequest request)  {
        log.info("/**************主动更新tao退货订单****************/");
        if (taoRequest.getShopId() == null || taoRequest.getShopId() <= 0) {
            return new ApiResult<>(ResultVoEnum.ParamsError.getIndex(), "参数错误，没有店铺Id");
        }
        Long shopId = taoRequest.getShopId();
        var checkResult = apiPullHelper.checkBefore(shopId);

        if (checkResult.getCode() != ResultVoEnum.SUCCESS.getIndex()) {
            return new ApiResult<>(checkResult.getCode(), checkResult.getMsg());
        }

        String sessionKey = checkResult.getData().getAccessToken();
        String url = checkResult.getData().getApiRequestUrl();
        String appKey = checkResult.getData().getAppKey();
        String appSecret = checkResult.getData().getAppSecret();


        Long pageSize = 50l;
        Long pageIndex = 1l;

        //第一次获取
        TaoBaoOpenOrderUpdResult<TaoOrderRefund> upResult = TaoBaoOpenOrderUpdHelper.updTmallRefunOrder(pageIndex, pageSize, url, appKey, appSecret, sessionKey);

        if (upResult.getCode().intValue() != 0) {
            log.info("/**************主动更新tao退货订单：第一次获取结果失败：" + upResult.getMsg() + "****************/");
            return new ApiResult<>(ResultVoEnum.SystemException.getIndex(), upResult.getMsg());
        }

        log.info("/**************主动更新tao退货订单：第一次获取结果：总记录数" + upResult.getTotalRecords() + "****************/");
        int insertSuccess = 0;//新增成功的订单
        int totalError = 0;
        int hasExistOrder = 0;//已存在的订单数

        //循环插入订单数据到数据库
        for (var order : upResult.getList()) {

            //插入订单数据
            var result = tmallOrderReturnService.updOrderRefund(shopId, order);
            if (result == ResultVoEnum.DataExist.getIndex()) {
                //已经存在
                log.info("/**************主动更新tao退货订单：开始更新数据库：" + order.getRefundId() + "存在、更新****************/");
                hasExistOrder++;
            } else if (result == ResultVoEnum.SUCCESS.getIndex()) {
                log.info("/**************主动更新tao退货订单：开始更新数据库：" + order.getRefundId() + "不存在、新增****************/");
                insertSuccess++;
            } else {
                log.info("/**************主动更新tao退货订单：开始更新数据库：" + order.getRefundId() + "报错****************/");
                totalError++;
            }
        }


        //计算总页数
        int totalPage = (upResult.getTotalRecords() % pageSize == 0) ? upResult.getTotalRecords() / pageSize.intValue() : (upResult.getTotalRecords() / pageSize.intValue()) + 1;
        pageIndex++;

        while (pageIndex <= totalPage) {
            TaoBaoOpenOrderUpdResult<TaoOrderRefund> upResult1 = TaoBaoOpenOrderUpdHelper.updTmallRefunOrder(pageIndex, pageSize, url, appKey, appSecret, sessionKey);
            //循环插入订单数据到数据库
            for (var order : upResult1.getList()) {

                //插入订单数据
                var result1 = tmallOrderReturnService.updOrderRefund(shopId, order);
                if (result1 == ResultVoEnum.DataExist.getIndex()) {
                    //已经存在
                    log.info("/**************主动更新tao退货订单：开始更新数据库：" + order.getRefundId() + "存在、更新****************/");
                    hasExistOrder++;
                } else if (result1 == ResultVoEnum.SUCCESS.getIndex()) {
                    log.info("/**************主动更新tao退货订单：开始更新数据库：" + order.getRefundId() + "不存在、新增****************/");
                    insertSuccess++;
                } else {
                    log.info("/**************主动更新tao退货订单：开始更新数据库：" + order.getRefundId() + "报错****************/");
                    totalError++;
                }
            }
            pageIndex++;
        }

        String msg = "成功，总共找到：" + upResult.getTotalRecords() + "条订单，新增：" + insertSuccess + "条，添加错误：" + totalError + "条，更新：" + hasExistOrder + "条";
        log.info("/**************主动更新tao订单：END：" + msg + "****************/");
        return new ApiResult<>(ResultVoEnum.SUCCESS.getIndex(), msg);
    }

    /**
     * 更新单条退货单
     *
     * @param taoRequest
     * @param model
     * @param request
     * @return
     * @throws
     */
    @RequestMapping("/refund/pull_refund_order_by_num")
    @ResponseBody
    public ApiResult<String> refundOrderPullByNum(@RequestBody TaoRequest taoRequest, Model model, HttpServletRequest request)  {
        log.info("/**************主动更新tao退货订单by number****************/");
        if (taoRequest.getShopId() == null || taoRequest.getShopId() <= 0) {
            return new ApiResult<>(ResultVoEnum.ParamsError.getIndex(), "参数错误，没有店铺Id");
        }
        if (taoRequest.getOrderId() == null || taoRequest.getOrderId() <= 0) {
            return new ApiResult<>(ResultVoEnum.ParamsError.getIndex(), "参数错误，缺少退款单号");
        }

        Long shopId = taoRequest.getShopId();
        var checkResult = apiPullHelper.checkBefore(shopId);

        if (checkResult.getCode() != ResultVoEnum.SUCCESS.getIndex()) {
            return new ApiResult<>(checkResult.getCode(), checkResult.getMsg());
        }

        String sessionKey = checkResult.getData().getAccessToken();
        String url = checkResult.getData().getApiRequestUrl();
        String appKey = checkResult.getData().getAppKey();
        String appSecret = checkResult.getData().getAppSecret();

//        TaobaoClient client = new DefaultTaobaoClient(url, appKey, appSecret);
//
//        RefundGetRequest req1 = new RefundGetRequest();
//        req1.setFields("refund_id, alipay_no, tid, oid, buyer_nick, seller_nick, total_fee, status, created, refund_fee, good_status, " +
//                "has_good_return, payment, reason, desc,order_status, num_iid, title, price, num, good_return_time, company_name, sid, address, " +
//                "shipping_type, refund_remind_timeout, refund_phase, refund_version, operation_contraint, attribute, outer_id, sku");
//        req1.setRefundId(taoRequest.getOrderId());
//        RefundGetResponse rsp1 = client.execute(req1, sessionKey);
//        System.out.println(rsp1.getBody());
//
//        var item = rsp1.getRefund();
//        if (item == null) {
//            //没有找到退款单
//            log.info("/**************主动更新tao订单：END： 没有找到退单****************/");
//            return new ApiResult<>(EnumResultVo.DataError.getIndex(), "没有找到退单" + taoRequest.getOrderId());
//        }
//
//        if (item.getHasGoodReturn()) {
//            log.info("/**************主动更新tao退货订单：买家需要退货，处理快递公司和快递单号处理*********************/");
//            //买家需要退货，处理快递公司和快递单号处理
//            if (StringUtils.isEmpty(item.getCompanyName())) {
//                log.info("/**************主动更新tao退货订单：买家需要退货，处理快递公司和快递单号处理，主数据中没有快递公司和单号*********************/");
//                String companyName = "";
//                if (rsp1.getRefund().getAttribute().indexOf("logisticsCompanyName:") > -1) {
//                    Integer companyNameStart = rsp1.getRefund().getAttribute().indexOf("logisticsCompanyName:") + 21;
//                    companyName = rsp1.getRefund().getAttribute().substring(companyNameStart, companyNameStart + 4);
//                }
//                String logisticsCode = "";
//                if (rsp1.getRefund().getAttribute().indexOf("logisticsOrderCode:") > -1) {
//                    Integer logisticsCodeStart = rsp1.getRefund().getAttribute().indexOf("logisticsOrderCode:") + 19;
//                    logisticsCode = rsp1.getRefund().getAttribute().substring(logisticsCodeStart, logisticsCodeStart + 15);
//                }
//                if (StringUtils.isEmpty(companyName) == false) {
//                    item.setCompanyName(companyName);
//                }
//
//                if (StringUtils.isEmpty(logisticsCode) == false) {
//                    if (logisticsCode.indexOf(";") >= 0) {
//                        logisticsCode = logisticsCode.substring(0, logisticsCode.indexOf(";"));
//                    }
//                    item.setSid(logisticsCode);
//                }
//            }
//        }
//        TaoOrderRefund tmallOrderRefund = new TaoOrderRefund();
////        tmallOrderRefund.setBuyer_nick(item.getBuyerNick());
//        tmallOrderRefund.setCreated(DateUtil.dateToStamp(item.getCreated()));
//        tmallOrderRefund.setRemark(item.getDesc());
//        tmallOrderRefund.setGoodStatus(item.getGoodStatus());
//        tmallOrderRefund.setHasGoodReturn(item.getHasGoodReturn() == true ? 1 : 0);
//        tmallOrderRefund.setLogisticsCode(item.getSid());
//        tmallOrderRefund.setLogisticsCompany(item.getCompanyName());
//        tmallOrderRefund.setModified(DateUtil.dateToStamp(item.getModified()));
//        tmallOrderRefund.setOid(item.getOid());
////        tmallOrderRefund.setOrderStatus(item.getOrderStatus());
//        tmallOrderRefund.setReason(item.getReason());
//        tmallOrderRefund.setRefundFee(BigDecimal.valueOf(Double.parseDouble(item.getRefundFee())));
//        tmallOrderRefund.setRefundId(item.getRefundId());
//        tmallOrderRefund.setStatus(item.getStatus());
//        tmallOrderRefund.setTid(item.getTid());
////        tmallOrderRefund.setTotal_fee(item.getTotalFee());
//        tmallOrderRefund.setNum(item.getNum());
//        tmallOrderRefund.setRefundPhase(item.getRefundPhase());

        //插入订单数据
//        var result1 = tmallOrderReturnService.updOrderRefund(shopId, tmallOrderRefund);
        String msg = "SUCCESS";
//        if (result1 == ResultVoEnum.DataExist.getIndex()) {
//            //已经存在
//            log.info("/**************主动更新tao退货订单：开始更新数据库：" + tmallOrderRefund.getRefundId() + "存在、更新****************/");
//            msg = "更新成功";
//        } else if (result1 == ResultVoEnum.SUCCESS.getIndex()) {
//            log.info("/**************主动更新tao退货订单：开始更新数据库：" + tmallOrderRefund.getRefundId() + "不存在、新增****************/");
//            msg = "新增成功";
//        } else {
//            log.info("/**************主动更新tao退货订单：开始更新数据库：" + tmallOrderRefund.getRefundId() + "报错****************/");
//            msg = "更新报错";
//        }


        log.info("/**************主动更新tao订单：END：" + msg + "****************/");
        return new ApiResult<>(ResultVoEnum.SUCCESS.getIndex(), msg);
    }


}
