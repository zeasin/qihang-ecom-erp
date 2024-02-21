//package com.qihang.erp.api.controller.xhs;
//
//import com.alibaba.fastjson.JSONArray;
//import com.b2c.entity.DataRow;
//import com.b2c.entity.api.ApiResult;
//import com.b2c.entity.result.EnumResultVo;
//import com.b2c.entity.xhs.XhsGoodsEntity;
//import com.b2c.interfaces.ShopService;
//import com.b2c.interfaces.xhs.XhsGoodsService;
//import com.xiaohongshu.fls.opensdk.client.ProductClient;
//import com.xiaohongshu.fls.opensdk.entity.BaseResponse;
//import com.xiaohongshu.fls.opensdk.entity.product.request.GetDetailItemRequest;
//import com.xiaohongshu.fls.opensdk.entity.product.request.GetSpuRequest;
//import com.xiaohongshu.fls.opensdk.entity.product.response.GetDetailItemResponse;
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
//public class AjaxGoodsXHSController {
//    @Autowired
//    private ShopService shopService;
//    private static Logger log = LoggerFactory.getLogger(AjaxGoodsXHSController.class);
//    @Autowired
//    private XhsGoodsService goodsService;
//
//    /**
//     * 拉取小红书店铺订单
//     *
//     * @param req
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping(value = "/pull_goods_ajx", method = RequestMethod.POST)
//    public ApiResult<String> pullGoods(HttpServletRequest req, @RequestBody DataRow reqData) throws Exception {
//        Integer shopId = reqData.getInt("shopId");//抖音shopId
//
//        var shop = shopService.getShop(shopId);
//
//        log.info("拉取小红书店铺订单......................");
//
//        String url = "https://ark.xiaohongshu.com/ark/open_api/v3/common_controller";
//
//        ProductClient client = new ProductClient(url, shop.getAppkey(), "2.0", shop.getAppSercet());
//
//        GetDetailItemRequest request = new GetDetailItemRequest();
//
//        //调取接口
//        BaseResponse<GetDetailItemResponse> response = client.execute(request, shop.getSessionKey());
//        if (response.success) {
//
//            long total = 0;//拉取到的总订单数
//            long addCount = 0;//新增的订单数
//            long updCount = 0;//更新的订单数
//            long errCount = 0;//错误的订单数
//
//            //拉取订单成功
////            log.info("拉取小红书店铺订单成功。总记录数："+response.getData());
//            GetSpuRequest request1 = new GetSpuRequest();
//            for (var g : response.getData().getData()) {
////                String pid = goods.spuData.getId();
//                XhsGoodsEntity entity = new XhsGoodsEntity();
//                entity.setShopId(shopId);
//                entity.setName(g.getSpuData().getName());
//                entity.setGoodsNum(g.getSpuData().getArticleNo());
//                entity.setImageUrl(g.getSpuData().getImageUrls().get(0));
//                entity.setShortName(g.getSpuData().getShortName());
//                entity.setSpuId(g.getSpuData().getId());
//                entity.setVideoUrl(g.getSpuData().getVideoUrl());
//                entity.setDesc(g.getSpuData().getDesc());
//                entity.setDescImages(JSONArray.toJSONString(g.getSpuData().getImageDescUrls()));
//                entity.setPrice(g.getItemData().getPrice());
//                entity.setStock(g.getItemData().getStock());
//                entity.setErpCode(g.getItemData().getErpCode());
//                entity.setSkuId(g.getItemData().getId());
//                String spec = "";
//                for (var i : g.getItemData().getVariants()) {
//                    spec += i.getValue() + " ";
//                }
//                entity.setSpec(spec);
//
//                var res = goodsService.pullGoods(entity);
//
//                if (res.getCode() == EnumResultVo.SUCCESS.getIndex()) {
//                    log.info("拉取新商品，新增成功，ID：" + res.getData());
//                    addCount++;
//                } else if (res.getCode() == EnumResultVo.DataExist.getIndex()) {
//                    log.info("拉取新商品，更新成功，ID：" + res.getData());
//                    updCount++;
//                } else {
//                    log.info("拉取新商品，更新异常，" + res.getMsg() + "。订单号：" + entity.getSpuId());
//                    errCount++;
//                }
//            }
//
//
//            String msg = "拉取小红书订单（ShopId：" + shopId + "）成功：总数：" + response.getData().getTotal() + ",新增：" + addCount + ",更新：" + updCount + ",错误：" + errCount;
//            return new ApiResult<>(EnumResultVo.SUCCESS.getIndex(), msg);
//        } else {
//            log.error("拉取小红书店铺订单失败：" + response.getMsg() + "。错误代码：" + response.getCode());
//
//            return new ApiResult<>(EnumResultVo.Fail.getIndex(), "拉取小红书店铺订单失败：" + response.getMsg() + "。错误代码：" + response.getCode());
//        }
//
//    }
//
//    /**
//     * 关联erp系统商品规格
//     * @param req
//     * @param reqData
//     * @return
//     */
//    @RequestMapping(value = "/link_erp_goods_spec_ajax", method = RequestMethod.POST)
//    public ApiResult<Integer> getOrderList(HttpServletRequest req, @RequestBody DataRow reqData){
//        Long xhsGoodsSpecId = reqData.getLong("id");
//        String erpCode = reqData.getString("code");
//        if(xhsGoodsSpecId==null || xhsGoodsSpecId <= 0 )
//            return new ApiResult<>(EnumResultVo.ParamsError.getIndex(), "id不能为空");
//        if(StringUtils.hasText(erpCode) == false)
//            return new ApiResult<>(EnumResultVo.ParamsError.getIndex(), "请输入SKU编码");
//        log.info("ID:"+xhsGoodsSpecId+",Code:"+erpCode);
//
//        var res = goodsService.linkErpGoodsSpec(xhsGoodsSpecId,erpCode);
//        if(res.getCode()  == EnumResultVo.SUCCESS.getIndex())
//            return new ApiResult<>(EnumResultVo.SUCCESS.getIndex(), "SUCCESS",res.getData());
//        else
//            return new ApiResult<>(EnumResultVo.Fail.getIndex(), res.getMsg());
//    }
//}
