//package com.qihang.erp.api.controller.xhs;
//
//import com.b2c.common.utils.DateUtil;
//import com.b2c.entity.api.ApiResult;
//import com.b2c.entity.result.EnumResultVo;
//import com.b2c.entity.xhs.XhsOrderSettleEntity;
//import com.b2c.erp.DataConfigObject;
//import com.b2c.interfaces.ShopService;
//import com.b2c.interfaces.xhs.XhsOrderSettleService;
//import com.fasterxml.jackson.databind.exc.InvalidFormatException;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.servlet.http.HttpServletRequest;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.math.BigDecimal;
//import java.util.Iterator;
//
//@RequestMapping("/xhs")
//@Controller
//public class XHSOrderSettleController {
//    private static Logger log = LoggerFactory.getLogger(XHSOrderSettleController.class);
//    @Autowired
//    private ShopService shopService;
//    @Autowired
//    private XhsOrderSettleService orderSettleService;
//
//    @RequestMapping("/order_settle_list")
//    public String order_settle_list(Model model,HttpServletRequest request,@RequestParam Integer shopId){
//        //查询店铺信息
//        var shop = shopService.getShop(shopId);
//        model.addAttribute("shop", shop);
//        model.addAttribute("shopId",shop.getId());
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
//        Integer pageIndex = 1, pageSize = DataConfigObject.getInstance().getPageSize();
//        if (!StringUtils.isEmpty(request.getParameter("page"))) {
//            pageIndex = Integer.parseInt(request.getParameter("page"));
//        }
//
//        var result = orderSettleService.getList(1,50,null,null,null,null);
//        model.addAttribute("pageIndex", pageIndex);
//        model.addAttribute("pageSize", pageSize);
//        model.addAttribute("totalSize", result.getTotalSize());
//        model.addAttribute("lists", result.getList());
//
//        model.addAttribute("view", "xhsshop");
//        model.addAttribute("pView", "sale");
//        return "order/xhs/order_settle_list_xhs";
//    }
//
//    @RequestMapping(value = "/order_settle_import", method = RequestMethod.GET)
//    public String orderDetailTmall(Model model, @RequestParam Integer shopId, HttpServletRequest request) {
//
//        //查询店铺信息
//        var shop = shopService.getShop(shopId);
//        model.addAttribute("shop", shop);
//        model.addAttribute("shopId", shop.getId());
//
//        model.addAttribute("view", "xhsshop");
//        model.addAttribute("pView", "sale");
//        return "order/xhs/order_settle_import";
//    }
//
//
//    @ResponseBody
//    @RequestMapping(value = "/order_settle_excel_upload", method = RequestMethod.POST)
//    public ApiResult<String> live_replay_excel_upload(@RequestParam("excel") MultipartFile file, HttpServletRequest req, @RequestParam Long shopId) throws IOException, InvalidFormatException {
//
//        String fileName = file.getOriginalFilename();
//        String path = this.getClass().getResource("/").getPath() + File.separator;
//
////        String dir = System.getProperty("user.dir");
////        log.info("根目录"+path);
//        String destFileName = "file"+ File.separator+"order_settle"+ File.separator + DateUtil.getCurrentDate()+"_"+ fileName;
////        System.out.println(destFileName);
//        File destFile = new File(path+destFileName);
//        file.transferTo(destFile);
//
//        log.info("/保存文件成功，文件路径" + path+destFileName + "***********/");
//        Workbook workbook = null;
//        InputStream fis = null;
//
////        var shelfList = new ArrayList<ErpStockLocationBatchAddVo>();
//
//        int isExist = 0;//存在数
//        int success = 0;//成功数
//        int exCount = 0;//异常数
//
//        try {
//            fis = new FileInputStream(path+destFileName);
//
//            if (fileName.toLowerCase().endsWith("xlsx")) {
//                workbook = new XSSFWorkbook(fis);
//            } else if (fileName.toLowerCase().endsWith("xls")) {
//                workbook = new HSSFWorkbook(fis);
//            }
//
//            Sheet sheet = workbook.getSheetAt(0);
//
//            //得到行的迭代器
//            Iterator<Row> rowIterator = sheet.iterator();
//                int rowCount = 0;
//            //循环每一行
//            while (rowIterator.hasNext()) {
//                Row row = rowIterator.next();
//                //从第二行开始
//                if (rowCount > 0) {
//                    System.out.print("第" + (rowCount++) + "行  ");
//                    XhsOrderSettleEntity settleEntity = new XhsOrderSettleEntity();
//                    String orderNo = row.getCell(0).getStringCellValue();
//                    settleEntity.setOrderNo(orderNo);
//                    String afterSaleNo = row.getCell(1).getStringCellValue();
//                    settleEntity.setAfterSaleNo(afterSaleNo);
//                    String orderCreateTime = row.getCell(2).getStringCellValue();
//                    settleEntity.setOrderCreateTime(orderCreateTime);
//                    String orderSettleTime = row.getCell(3).getStringCellValue();
//                    settleEntity.setOrderSettleTime(orderSettleTime);
//                    String transactionType = row.getCell(4).getStringCellValue();
//                    settleEntity.setTransactionType(transactionType);
//                    String settleAccount = row.getCell(5).getStringCellValue();
//                    settleEntity.setSettleAccount(settleAccount);
//                    double amount = row.getCell(6).getNumericCellValue();
//                    settleEntity.setAmount(new BigDecimal(amount));
//                    double settleAmount = row.getCell(7).getNumericCellValue();
//                    settleEntity.setSettleAmount(new BigDecimal(settleAmount));
//                    double goodsAmount = row.getCell(8).getNumericCellValue();
//                    settleEntity.setGoodsAmount(new BigDecimal(goodsAmount));
//                    double freightAmount = row.getCell(9).getNumericCellValue();
//                    settleEntity.setFreightAmount(new BigDecimal(freightAmount));
//                    double platformDiscount = row.getCell(10).getNumericCellValue();
//                    settleEntity.setPlatformDiscount(new BigDecimal(platformDiscount));
//                    double goodsTax = row.getCell(12).getNumericCellValue();
//                    settleEntity.setGoodsTax(new BigDecimal(goodsTax));
//                    double freightTax = row.getCell(13).getNumericCellValue();
//                    settleEntity.setFreightTax(new BigDecimal(freightTax));
//                    double commission = row.getCell(14).getNumericCellValue();
//                    settleEntity.setCommission(new BigDecimal(commission));
//                    double paymentChannelFee = row.getCell(15).getNumericCellValue();
//                    settleEntity.setPaymentChannelFee(new BigDecimal(paymentChannelFee));
//                    double distributionCommission = row.getCell(16).getNumericCellValue();
//                    settleEntity.setDistributionCommission(new BigDecimal(distributionCommission));
//                    double huabeiFee = row.getCell(17).getNumericCellValue();
//                    settleEntity.setHuabeiFee(new BigDecimal(huabeiFee));
//                    String remark = row.getCell(18).getStringCellValue();
//                    settleEntity.setRemark(remark);
//
//                    var res = orderSettleService.addOrderSettle(settleEntity);
//                    if(res.getCode() == EnumResultVo.DataExist.getIndex()){
//                        isExist += 1;
//                    }else if(res.getCode() == EnumResultVo.SUCCESS.getIndex()){
//                        success += 1;
//                    }else exCount += 1;
//                }
//                rowCount++;
//            }
//
//
//        } catch (Exception ex) {
////            workbook = new HSSFWorkbook(fis);
//            return new ApiResult<>(500, ex.getMessage());
//        }
////
//////        if (shelfList == null || shelfList.size() == 0) return new ApiResult<>(404, "没有数据");
//
//
//        return new ApiResult<>(EnumResultVo.SUCCESS.getIndex(),"新增成功："+success+"，已存在："+isExist+"，异常："+exCount);
//    }
//
//}
