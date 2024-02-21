//package com.qihang.erp.api.controller.xhs;
//
//
//import com.b2c.interfaces.ShopService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import javax.servlet.http.HttpServletRequest;
//import java.net.URLEncoder;
//
//@RequestMapping("/xhs")
//@Controller
//public class XHSOAuthController {
//
//    @Autowired
//    private ShopService shopService;
//    private static Logger log = LoggerFactory.getLogger(XHSOAuthController.class);
//
//    /**
//     * 小红书店铺授权
//     * @param req
//     * @param shopId
//     * @return
//     */
//    @RequestMapping("/oauth")
//    public String oauth(HttpServletRequest req,@RequestParam Integer shopId) {
//        //查询店铺信息
//        var shop = shopService.getShop(shopId);
//        String returnUrl =  "http://localhost:8080/oauth/xhs";
//
//        String url = "https://ark.xiaohongshu.com/ark/authorization?appId="+shop.getAppkey()+"&redirectUri="+ URLEncoder.encode(returnUrl)+"&state="+shopId;
//
//        return "redirect:" + url;
//    }
//
////    @RequestMapping("/getToken")
////    public String getToken(HttpServletRequest req) throws IOException, InterruptedException {
////        log.info("/**********获取拼多多授权token*********/");
////        String code = req.getParameter("code");
////        String clientId = DataConfigObject.getInstance().getPddClientId();
////        String clientSecret = DataConfigObject.getInstance().getPddClientSecret();
////        Integer shopId =Integer.parseInt(req.getParameter("state"));
////
////        PopAccessTokenClient accessTokenClient = new PopAccessTokenClient(clientId, clientSecret);
////
////        // 生成AccessToken
////        try {
////            AccessTokenResponse response = accessTokenClient.generate(code);
////            if(response.getErrorResponse()!=null){
////                log.info("/***************获取拼多多授权token错误："+response.getErrorResponse().getErrorMsg()+"**************/");
////            }else{
////                //保存accessToken
////                System.out.println(shopId +"--token:" + response.getAccessToken()+",thirdId:"+response.getOwnerId()+",shopId:"+shopId);
////
////                shopService.updateSessionKey(shopId,Long.parseLong(response.getOwnerId()),response.getAccessToken());
////
//////                thirdSettingService.updateEntity(shopId, response.getAccessToken(), response.getRefreshToken(), response.getExpiresIn(),response.getOwnerId());
////                return "redirect:/pdd/getTokenSuccess?mallId="+response.getOwnerId();
/////*                String state = req.getParameter("state");
////                if (state.equalsIgnoreCase("GETORDERLIST")) {
////                    //获取订单list
////                    return "redirect:/shop/shop_list";
////                } else if (state.equalsIgnoreCase("DCGOODSLIST")) {
////                    //商品list
////                    return "redirect:/goods/pdd_list";
////                }*/
////            }
////        } catch (Exception e) {
////
////            e.printStackTrace();
////
////        }
////        return "redirect:/";
////    }
////
////    /**
////     * 获取授权成功
////     * @param req
////     * @param model
////     * @return
////     */
////    @RequestMapping("/getTokenSuccess")
////    public String getTokeSuccess(HttpServletRequest req, @RequestParam Long mallId, Model model){
////        var shop = shopService.getShopByMallId(mallId);
////        model.addAttribute("shop",shop);
////        model.addAttribute("shopId",shop.getId());
////        return "get_token_success";
////    }
//
//
//
//}
