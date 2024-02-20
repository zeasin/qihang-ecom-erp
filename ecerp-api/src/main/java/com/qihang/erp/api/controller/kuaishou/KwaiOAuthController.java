package com.qihang.erp.api.controller.kuaishou;//package com.b2c.oms.controller.kuaishou;
//import com.b2c.enums.EnumShopType;
//import com.b2c.service.mall.SystemService;
//import com.b2c.service.oms.SysThirdSettingService;
//import com.kuaishou.merchant.open.api.KsMerchantApiException;
//import com.kuaishou.merchant.open.api.client.oauth.OauthAccessTokenKsClient;
//import com.kuaishou.merchant.open.api.response.KsAccessTokenResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//
///**
// * 描述：
// * 阿里授权Controller
// *
// * @author qlp
// * @date 2019-09-12 14:13
// */
//@RequestMapping("/kwaixiaodian")
//@Controller
//public class KwaiOAuthController {
//    @Autowired
//    private SysThirdSettingService thirdSettingService;
//    private static  String clientId="ks701717119425407331";
//    private static  String clientSecret="0qBrIYOPrqGb8SM9ouVx9w";
//    private static Logger log = LoggerFactory.getLogger(KwaiOAuthController.class);
//
//
//    /**
//     * 调取授权页面
//     * @return
//     * @throws IOException
//     * @throws InterruptedException
//     */
//    @RequestMapping("/callback")
//    public String aliOAuth(HttpServletRequest req) throws IOException, InterruptedException {
//        Integer shopId =13;
//        String grantCode = req.getParameter("code");
//        OauthAccessTokenKsClient oauthAccessTokenKsClient = new OauthAccessTokenKsClient(clientId, clientSecret);
//        try {
//            KsAccessTokenResponse response = oauthAccessTokenKsClient.getAccessToken(grantCode);
//            Long expireIn=response.getExpiresIn();
//            thirdSettingService.updateEntity(shopId, response.getAccessToken(), response.getRefreshToken(),expireIn.intValue(),response.getRefreshToken());
//        } catch (KsMerchantApiException e) {
//            e.printStackTrace();
//        }
//        return "redirect:/";
//    }
//
//    @RequestMapping("/oauth")
//    public String callback(HttpServletRequest request) throws IOException, InterruptedException {
//        Integer shopTypeId = EnumShopType.Kwai.getIndex();
//        var entity = thirdSettingService.getEntity(shopTypeId);
//        String redirect_uri = "http://oms.huayiyungou.com:8080/kwaixiaodian/callback";
//
//
//        String url = "https://s.kwaixiaodian.com/oauth/authorize?response_type=code&app_id=ks701717119425407331&scope=user_info,merchant_item,merchant_order,merchant_refund&redirect_uri="+redirect_uri+"&state=ORDER_LIST";
//
//        return "redirect:" + url;
//    }
//}
