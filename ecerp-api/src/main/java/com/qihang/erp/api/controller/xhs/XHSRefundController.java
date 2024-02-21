//package com.qihang.erp.api.controller.xhs;
//
//import com.b2c.common.utils.DateUtil;
//import com.b2c.interfaces.ShopService;
//import com.b2c.interfaces.xhs.XhsRefundService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import javax.servlet.http.HttpServletRequest;
//
///**
// * 描述：
// *
// * @author qlp
// * @date 2019-09-24 16:05
// */
//@RequestMapping("/xhs")
//@Controller
//public class XHSRefundController {
//    @Autowired
//    private ShopService shopService;
//    @Autowired
//    private XhsRefundService refundService;
//
//    private static Logger log = LoggerFactory.getLogger(XHSRefundController.class);
//
//    /**
//     * 退货订单
//     *
//     * @param model
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = "/refund_list", method = RequestMethod.GET)
//    public String list(Model model, @RequestParam Integer shopId, HttpServletRequest request) {
//        //查询店铺信息
//        var shop = shopService.getShop(shopId);
//        model.addAttribute("shop",shop);
//        model.addAttribute("shopId",shop.getId());
//        model.addAttribute("menuId", "refund_list");
//
//        String page = request.getParameter("page");
//        Integer pageIndex = 1;
//        Integer pageSize = 20;
//
//        String orderNum = "";
//        String logisticsCode = "";//退货物流单号
//
//        Integer startTime = null;
//        Integer endTime = null;
//
//        Integer status = null;
//        Integer aftersaleType = null;
//
//        if (!StringUtils.isEmpty(page)) {
//            pageIndex = Integer.parseInt(page);
//        }
//
//        if (!StringUtils.isEmpty(request.getParameter("orderNum"))) {
//            orderNum = request.getParameter("orderNum");
//            model.addAttribute("orderNum", orderNum);
//        }
//
//
//        if (!StringUtils.isEmpty(request.getParameter("logisticsCode"))) {
//            logisticsCode = request.getParameter("logisticsCode");
//            model.addAttribute("logisticsCode", logisticsCode);
//        }
//
//        if (!StringUtils.isEmpty(request.getParameter("status"))) {
//            status = Integer.parseInt(request.getParameter("status"));
//            model.addAttribute("status",status);
//        }
//        if (!StringUtils.isEmpty(request.getParameter("aftersaleType"))) {
//            aftersaleType = Integer.parseInt(request.getParameter("aftersaleType"));
//            model.addAttribute("aftersaleType",aftersaleType);
//        }
//
//
//        if (!StringUtils.isEmpty(request.getParameter("startTime"))) {
//            startTime = DateUtil.dateToStamp(request.getParameter("startTime"));
//        }
//        if (!StringUtils.isEmpty(request.getParameter("endTime"))) {
//            endTime = DateUtil.dateTimeToStamp(request.getParameter("endTime") + " 23:59:59");
//        }
//
//
//        model.addAttribute("pageIndex", pageIndex);
//        model.addAttribute("pageSize", pageSize);
//        var result = refundService.getRefundList(shop.getId(),pageIndex,pageSize);
//        model.addAttribute("totalSize", result.getTotalSize());
//        model.addAttribute("list", result.getList());
//
//        model.addAttribute("view", "xhsshop");
//        model.addAttribute("pView", "sale");
//
//        return "order/xhs/refund_list_xhs";
//    }
////
////    @RequestMapping(value = "/refund_detail", method = RequestMethod.GET)
////    public String refundDetail(Model model, @RequestParam Long refundId,@RequestParam Integer shopId,HttpServletRequest request) {
////        //查询店铺信息
////        var shop = shopService.getShop(shopId);
////        model.addAttribute("shop",shop);
////        model.addAttribute("menuId", "refund_list");
////        var orderVo = douyinOrderService.getDouYinRefundOrderDetail(refundId);
////        if(orderVo==null) log.info("没有找到退货订单数据");
////        model.addAttribute("orderVo",orderVo);
////
////        model.addAttribute("view", "dyshop");
////        model.addAttribute("pView", "goods");
////
////        return "order/douyin/refund_detail_douyin";
////    }
////
////    @RequestMapping(value = "/order_refund_confirm", method = RequestMethod.GET)
////    public String orderRefundConfirm(Model model, @RequestParam Long refundId,@RequestParam Integer shopId, HttpServletRequest request) {
////        //查询店铺信息
////        var shop = shopService.getShop(shopId);
////        model.addAttribute("shop",shop);
////        model.addAttribute("menuId", "refund_list");
////        var refundOrder = douyinOrderService.getDouYinRefundOrderDetail(refundId);
////        model.addAttribute("orderVo",refundOrder);
////        return "order/douyin/order_refund_confirm";
////    }
//
//
//}
