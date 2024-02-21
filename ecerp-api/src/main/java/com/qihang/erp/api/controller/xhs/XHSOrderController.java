//package com.qihang.erp.api.controller.xhs;
//
//import com.b2c.common.utils.DateUtil;
//import com.b2c.entity.xhs.XhsOrderEntity;
//import com.b2c.erp.DataConfigObject;
//import com.b2c.interfaces.ExpressCompanyService;
//import com.b2c.interfaces.ShopService;
//import com.b2c.interfaces.xhs.XhsOrderService;
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
//@RequestMapping("/xhs")
//@Controller
//public class XHSOrderController {
//    private static Logger log = LoggerFactory.getLogger(XHSOrderController.class);
//    @Autowired
//    private ShopService shopService;
//    @Autowired
//    private XhsOrderService orderService;
//    @Autowired
//    private ExpressCompanyService expressCompanyService;
//    /**
//     * 订单列表
//     * @param model
//     * @param request
//     * @return
//     */
//    @RequestMapping("/order_list")
//    public String orderList(Model model, HttpServletRequest request, @RequestParam Integer shopId){
//        //查询店铺信息
//        var shop = shopService.getShop(shopId);
//        model.addAttribute("shop",shop);
//
//        model.addAttribute("menuId","order_list");
//
//        String orderNum="";
//        if (!StringUtils.isEmpty(request.getParameter("orderNum"))) {
//            orderNum = request.getParameter("orderNum");
//            model.addAttribute("orderNum", orderNum);
//        }
//        Integer status= null;
//        if (!StringUtils.isEmpty(request.getParameter("status"))) {
//            status = Integer.parseInt(request.getParameter("status"));
//            model.addAttribute("status", status);
//        }
//
//        String logisticsCode="";
//        if (!StringUtils.isEmpty(request.getParameter("logisticsCode"))){
//            logisticsCode = request.getParameter("logisticsCode");
//            model.addAttribute("logisticsCode",logisticsCode);
//        }
//        Integer startTime=null;
//        if (!StringUtils.isEmpty(request.getParameter("startTime"))){
//            startTime = DateUtil.dateTimeToStamp(request.getParameter("startTime"));
//            model.addAttribute("startTime",request.getParameter("startTime"));
//        }
//        Integer endTime=null;
//        if (!StringUtils.isEmpty(request.getParameter("endTime"))){
//            String endTimes=request.getParameter("endTime");
//            if(endTimes.indexOf("00:00:00")>0){
//                endTimes=endTimes.replace("00:00:00","23:59:59");
//            }
//            endTime = DateUtil.dateTimeToStamp(endTimes);
//            model.addAttribute("endTime",endTimes);
//        }
//
//        Integer pageIndex = 1, pageSize = DataConfigObject.getInstance().getPageSize();
//        if (!StringUtils.isEmpty(request.getParameter("page"))) {
//            pageIndex = Integer.parseInt(request.getParameter("page"));
//        }
//        var result = orderService.getOrderList(shopId,pageIndex,pageSize,orderNum,startTime,endTime,status,logisticsCode);
//
//        model.addAttribute("pageIndex", pageIndex);
//        model.addAttribute("pageSize", pageSize);
//        model.addAttribute("totalSize", result.getTotalSize());
//        model.addAttribute("lists", result.getList());
//        model.addAttribute("shopId",shop.getId());
//
//        model.addAttribute("company", expressCompanyService.getExpressCompany());
//
//        model.addAttribute("view", "xhsshop");
//        model.addAttribute("pView", "sale");
//
//        return "order/xhs/order_list_xhs";
//    }
//
//    /**
//     * 订单详情
//     * @param model
//     * @param id
//     * @param shopId
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = "/order_detail", method = RequestMethod.GET)
//    public String orderDetailTmall(Model model, @RequestParam Long id, @RequestParam Integer shopId, HttpServletRequest request) {
//
////        DcDouyinOrdersListVo orderDetail = douyinOrderService.getOderDetailByOrderId(id);
//        XhsOrderEntity order = orderService.getOrderDetailById(id);
//
//        model.addAttribute("orderVo", order);
//
//        //查询店铺信息
//        var shop = shopService.getShop(shopId);
//        model.addAttribute("shop", shop);
//
//
//        model.addAttribute("view", "xhsshop");
//        model.addAttribute("pView", "sale");
//        return "order/xhs/order_detail_xhs";
//    }
//
//
//}
