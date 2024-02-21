//package com.qihang.erp.api.controller.xhs;
//
//import com.b2c.erp.DataConfigObject;
//import com.b2c.interfaces.ShopService;
//import com.b2c.interfaces.xhs.XhsGoodsService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import javax.servlet.http.HttpServletRequest;
//
//@RequestMapping("/xhs")
//@Controller
//public class XHSGoodsController {
//    private static Logger log = LoggerFactory.getLogger(XHSGoodsController.class);
//    @Autowired
//    private ShopService shopService;
//    @Autowired
//    private XhsGoodsService goodsService;
//
//    /**
//     * 商品列表
//     * @param model
//     * @param request
//     * @param shopId
//     * @return
//     */
//    @RequestMapping("/goods_list")
//    public String goodsList(Model model, HttpServletRequest request, @RequestParam Integer shopId){
//        //查询店铺信息
//        var shop = shopService.getShop(shopId);
//        model.addAttribute("shop",shop);
//
//        model.addAttribute("menuId","order_list");
//
//        String goodsNum="";
//        if (!StringUtils.isEmpty(request.getParameter("goodsNum"))) {
//            goodsNum = request.getParameter("goodsNum");
//            model.addAttribute("goodsNum", goodsNum);
//        }
//
//
//        Integer pageIndex = 1, pageSize = DataConfigObject.getInstance().getPageSize();
//        if (!StringUtils.isEmpty(request.getParameter("page"))) {
//            pageIndex = Integer.parseInt(request.getParameter("page"));
//        }
//        var result = goodsService.getGoodsList(shopId,pageIndex,pageSize,goodsNum);
//
//        model.addAttribute("pageIndex", pageIndex);
//        model.addAttribute("pageSize", pageSize);
//        model.addAttribute("totalSize", result.getTotalSize());
//        model.addAttribute("lists", result.getList());
//        model.addAttribute("shopId",shop.getId());
//
//        model.addAttribute("view", "xhsshop");
//        model.addAttribute("pView", "sale");
//
//        return "order/xhs/goods_list_xhs";
//    }
//}
