<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="订单编号" prop="orderSn">
        <el-input
          v-model="queryParams.orderSn"
          placeholder="请输入订单编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="店铺" prop="shopId">
        <el-input
          v-model="queryParams.shopId"
          placeholder="请选择店铺"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>


      <el-form-item label="收件人" prop="receiverName1">
        <el-input
          v-model="queryParams.receiverName1"
          placeholder="请输入收件人姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="手机号" prop="receiverPhone1">
        <el-input
          v-model="queryParams.receiverPhone1"
          placeholder="请输入手机号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="快递单号" prop="trackingNumber">
        <el-input
          v-model="queryParams.trackingNumber"
          placeholder="请输入快递单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发货时间" prop="shippingTime">
        <el-date-picker clearable
                        v-model="queryParams.shippingTime"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择发货时间">
        </el-date-picker>
      </el-form-item>
<!--      <el-form-item label="省" prop="province">-->
<!--        <el-input-->
<!--          v-model="queryParams.province"-->
<!--          placeholder="请输入省"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="市" prop="city">-->
<!--        <el-input-->
<!--          v-model="queryParams.city"-->
<!--          placeholder="请输入市"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->


      <el-form-item label="创建时间" prop="createdTime">
        <el-date-picker clearable
          v-model="queryParams.createdTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择订单创建时间">
        </el-date-picker>
      </el-form-item>

<!--       <el-form-item label="标签" prop="tag">
        <el-input
          v-model="queryParams.tag"
          placeholder="请输入标签"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
<!--     -->
<!--      <el-form-item label="API拉取时间" prop="pullTime">-->
<!--        <el-date-picker clearable-->
<!--          v-model="queryParams.pullTime"-->
<!--          type="date"-->
<!--          value-format="yyyy-MM-dd"-->
<!--          placeholder="请选择API拉取时间">-->
<!--        </el-date-picker>-->
<!--      </el-form-item>-->
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['pdd:order:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['pdd:order:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['pdd:order:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['pdd:order:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="orderList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="订单id" align="center" prop="id" />
      <el-table-column label="订单编号" align="center" prop="orderSn" />
      <el-table-column label="店铺" align="center" prop="shopId" >
        <template slot-scope="scope">
          <span v-if="scope.row.shopId==5">梦小妮潮流女装</span>
        </template>
      </el-table-column>
      <el-table-column label="订单类型" align="center" prop="tradeType" />
      <el-table-column label="成交状态" align="center" prop="confirmStatus" />
<!--      <el-table-column label="是否顺丰包邮，1-是 0-否" align="center" prop="freeSf" />-->
<!--      <el-table-column label="成团状态：0：拼团中、1：已成团、2：团失败" align="center" prop="groupStatus" />-->
      <el-table-column label="团长免单金额，单位：元" align="center" prop="capitalFreeDiscount" />
      <el-table-column label="商家优惠金额，单位：元" align="center" prop="sellerDiscount" />
      <el-table-column label="平台优惠金额，单位：元" align="center" prop="platformDiscount" />
      <el-table-column label="订单备注" align="center" prop="remark" />
      <el-table-column label="更新时间" align="center" prop="updatedAt" />
      <el-table-column label="售后状态" align="center" prop="refundStatus" />
<!--      <el-table-column label="是否是抽奖订单，1-非抽奖订单，2-抽奖订单" align="center" prop="isLuckyFlag" />-->
      <el-table-column label="订单状态" align="center" prop="orderStatus" />
      <el-table-column label="发货时间" align="center" prop="shippingTime" />
      <el-table-column label="快递单号" align="center" prop="trackingNumber" />
      <el-table-column label="物流公司" align="center" prop="trackingCompany" />
      <el-table-column label="支付方式" align="center" prop="payType" />
<!--      <el-table-column label="支付单号" align="center" prop="payNo" />-->
      <el-table-column label="邮费" align="center" prop="postage" />
      <el-table-column label="折扣金额" align="center" prop="discountAmount" />
      <el-table-column label="商品金额" align="center" prop="goodsAmount" />
      <el-table-column label="支付金额" align="center" prop="payAmount" />
<!--      <el-table-column label="收件人电话" align="center" prop="receiverPhone" />-->
<!--      <el-table-column label="收件人姓名" align="center" prop="receiverName" />-->
      <el-table-column label="收件人" align="center" prop="receiverName1" />
<!--      <el-table-column label="手机号" align="center" prop="receiverPhone1" />-->
<!--      <el-table-column label="${comment}" align="center" prop="receiverAddress1" />-->
<!--      <el-table-column label="详细地址" align="center" prop="address" />-->
      <el-table-column label="区县" align="center" prop="town" />
      <el-table-column label="市" align="center" prop="city" />
      <el-table-column label="省" align="center" prop="province" />
<!--      <el-table-column label="国家地区" align="center" prop="country" />-->
      <el-table-column label="订单创建时间" align="center" prop="createdTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createdTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
<!--      <el-table-column label="支付时间" align="center" prop="payTime" />-->
<!--      <el-table-column label="成交时间" align="center" prop="confirmTime" />-->
<!--      <el-table-column label="确认收货时间" align="center" prop="receiveTime" />-->
      <el-table-column label="买家留言信息" align="center" prop="buyerMemo" />
      <el-table-column label="售后状态" align="center" prop="afterSalesStatus" />
<!--      <el-table-column label="订单成交时间" align="center" prop="orderConfirmTime" />-->
      <el-table-column label="订单承诺发货时间" align="center" prop="lastShipTime" />
      <el-table-column label="审核状态" align="center" prop="auditStatus" />
<!--      <el-table-column label="结算状态" align="center" prop="settlementStatus" />-->
      <el-table-column label="发货状态" align="center" prop="shipStatus" />
<!--      <el-table-column label="发货时间" align="center" prop="shipTime" />-->
      <el-table-column label="标签" align="center" prop="tag" />
<!--      <el-table-column label="导入文件id" align="center" prop="excelLogId" />-->
<!--      <el-table-column label="导入结果" align="center" prop="excelMsg" />-->
<!--      <el-table-column label="打印密文" align="center" prop="encryptedData" />-->
<!--      <el-table-column label="打印签名" align="center" prop="signature" />-->
<!--      <el-table-column label="打印状态" align="center" prop="printStatus" />-->
<!--      <el-table-column label="打印时间" align="center" prop="printTime" />-->
<!--      <el-table-column label="收件人检索" align="center" prop="nameKey" />-->
<!--      <el-table-column label="手机号检索" align="center" prop="phoneKey" />-->
<!--      <el-table-column label="地址检索" align="center" prop="addressKey" />-->
<!--      <el-table-column label="订单处理结果" align="center" prop="result" />-->
<!--      <el-table-column label="API拉取时间" align="center" prop="pullTime" width="180">-->
<!--        <template slot-scope="scope">-->
<!--          <span>{{ parseTime(scope.row.pullTime, '{y}-{m}-{d}') }}</span>-->
<!--        </template>-->
<!--      </el-table-column>-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['pdd:order:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['pdd:order:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改拼多多订单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="订单编号" prop="orderSn">
          <el-input v-model="form.orderSn" placeholder="请输入订单编号" />
        </el-form-item>
        <el-form-item label="内部店铺ID" prop="shopId">
          <el-input v-model="form.shopId" placeholder="请输入内部店铺ID" />
        </el-form-item>
        <el-form-item label="是否顺丰包邮，1-是 0-否" prop="freeSf">
          <el-input v-model="form.freeSf" placeholder="请输入是否顺丰包邮，1-是 0-否" />
        </el-form-item>
        <el-form-item label="团长免单金额，单位：元" prop="capitalFreeDiscount">
          <el-input v-model="form.capitalFreeDiscount" placeholder="请输入团长免单金额，单位：元" />
        </el-form-item>
        <el-form-item label="商家优惠金额，单位：元" prop="sellerDiscount">
          <el-input v-model="form.sellerDiscount" placeholder="请输入商家优惠金额，单位：元" />
        </el-form-item>
        <el-form-item label="平台优惠金额，单位：元" prop="platformDiscount">
          <el-input v-model="form.platformDiscount" placeholder="请输入平台优惠金额，单位：元" />
        </el-form-item>
        <el-form-item label="订单备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="订单的更新时间" prop="updatedAt">
          <el-input v-model="form.updatedAt" placeholder="请输入订单的更新时间" />
        </el-form-item>
        <el-form-item label="是否是抽奖订单，1-非抽奖订单，2-抽奖订单" prop="isLuckyFlag">
          <el-input v-model="form.isLuckyFlag" placeholder="请输入是否是抽奖订单，1-非抽奖订单，2-抽奖订单" />
        </el-form-item>
        <el-form-item label="发货时间" prop="shippingTime">
          <el-input v-model="form.shippingTime" placeholder="请输入发货时间" />
        </el-form-item>
        <el-form-item label="快递单号" prop="trackingNumber">
          <el-input v-model="form.trackingNumber" placeholder="请输入快递单号" />
        </el-form-item>
        <el-form-item label="物流公司" prop="trackingCompany">
          <el-input v-model="form.trackingCompany" placeholder="请输入物流公司" />
        </el-form-item>
        <el-form-item label="支付单号" prop="payNo">
          <el-input v-model="form.payNo" placeholder="请输入支付单号" />
        </el-form-item>
        <el-form-item label="邮费，单位：元" prop="postage">
          <el-input v-model="form.postage" placeholder="请输入邮费，单位：元" />
        </el-form-item>
        <el-form-item label="折扣金额，单位：元，折扣金额=平台优惠+商家优惠+团长免单优惠金额" prop="discountAmount">
          <el-input v-model="form.discountAmount" placeholder="请输入折扣金额，单位：元，折扣金额=平台优惠+商家优惠+团长免单优惠金额" />
        </el-form-item>
        <el-form-item label="商品金额，单位：元，商品金额=商品销售价格*商品数量-改价金额" prop="goodsAmount">
          <el-input v-model="form.goodsAmount" placeholder="请输入商品金额，单位：元，商品金额=商品销售价格*商品数量-改价金额" />
        </el-form-item>
        <el-form-item label="支付金额，单位：元，支付金额=商品金额-折扣金额+邮费" prop="payAmount">
          <el-input v-model="form.payAmount" placeholder="请输入支付金额，单位：元，支付金额=商品金额-折扣金额+邮费" />
        </el-form-item>
        <el-form-item label="收件人电话" prop="receiverPhone">
          <el-input v-model="form.receiverPhone" placeholder="请输入收件人电话" />
        </el-form-item>
        <el-form-item label="收件人姓名" prop="receiverName">
          <el-input v-model="form.receiverName" placeholder="请输入收件人姓名" />
        </el-form-item>
        <el-form-item label="${comment}" prop="receiverName1">
          <el-input v-model="form.receiverName1" placeholder="请输入${comment}" />
        </el-form-item>
        <el-form-item label="${comment}" prop="receiverPhone1">
          <el-input v-model="form.receiverPhone1" placeholder="请输入${comment}" />
        </el-form-item>
        <el-form-item label="${comment}" prop="receiverAddress1">
          <el-input v-model="form.receiverAddress1" placeholder="请输入${comment}" />
        </el-form-item>
        <el-form-item label="详细地址" prop="address">
          <el-input v-model="form.address" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="区县" prop="town">
          <el-input v-model="form.town" placeholder="请输入区县" />
        </el-form-item>
        <el-form-item label="市" prop="city">
          <el-input v-model="form.city" placeholder="请输入市" />
        </el-form-item>
        <el-form-item label="省" prop="province">
          <el-input v-model="form.province" placeholder="请输入省" />
        </el-form-item>
        <el-form-item label="国家地区" prop="country">
          <el-input v-model="form.country" placeholder="请输入国家地区" />
        </el-form-item>
        <el-form-item label="订单创建时间" prop="createdTime">
          <el-date-picker clearable
            v-model="form.createdTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择订单创建时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="支付时间" prop="payTime">
          <el-input v-model="form.payTime" placeholder="请输入支付时间" />
        </el-form-item>
        <el-form-item label="成交时间" prop="confirmTime">
          <el-input v-model="form.confirmTime" placeholder="请输入成交时间" />
        </el-form-item>
        <el-form-item label="确认收货时间" prop="receiveTime">
          <el-input v-model="form.receiveTime" placeholder="请输入确认收货时间" />
        </el-form-item>
        <el-form-item label="买家留言信息" prop="buyerMemo">
          <el-input v-model="form.buyerMemo" placeholder="请输入买家留言信息" />
        </el-form-item>
        <el-form-item label="订单成交时间" prop="orderConfirmTime">
          <el-input v-model="form.orderConfirmTime" placeholder="请输入订单成交时间" />
        </el-form-item>
        <el-form-item label="订单承诺发货时间" prop="lastShipTime">
          <el-input v-model="form.lastShipTime" placeholder="请输入订单承诺发货时间" />
        </el-form-item>
        <el-form-item label="发货时间" prop="shipTime">
          <el-input v-model="form.shipTime" placeholder="请输入发货时间" />
        </el-form-item>
        <el-form-item label="标签" prop="tag">
          <el-input v-model="form.tag" placeholder="请输入标签" />
        </el-form-item>
        <el-form-item label="导入文件id" prop="excelLogId">
          <el-input v-model="form.excelLogId" placeholder="请输入导入文件id" />
        </el-form-item>
        <el-form-item label="导入结果" prop="excelMsg">
          <el-input v-model="form.excelMsg" placeholder="请输入导入结果" />
        </el-form-item>
        <el-form-item label="打印密文" prop="encryptedData">
          <el-input v-model="form.encryptedData" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="打印签名" prop="signature">
          <el-input v-model="form.signature" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="打印时间" prop="printTime">
          <el-input v-model="form.printTime" placeholder="请输入打印时间" />
        </el-form-item>
        <el-form-item label="收件人检索" prop="nameKey">
          <el-input v-model="form.nameKey" placeholder="请输入收件人检索" />
        </el-form-item>
        <el-form-item label="手机号检索" prop="phoneKey">
          <el-input v-model="form.phoneKey" placeholder="请输入手机号检索" />
        </el-form-item>
        <el-form-item label="地址检索" prop="addressKey">
          <el-input v-model="form.addressKey" placeholder="请输入地址检索" />
        </el-form-item>
        <el-form-item label="订单处理结果" prop="result">
          <el-input v-model="form.result" placeholder="请输入订单处理结果" />
        </el-form-item>
        <el-form-item label="API拉取时间" prop="pullTime">
          <el-date-picker clearable
            v-model="form.pullTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择API拉取时间">
          </el-date-picker>
        </el-form-item>
        <el-divider content-position="center">拼多多订单明细信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddPddOrderItem">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeletePddOrderItem">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="pddOrderItemList" :row-class-name="rowPddOrderItemIndex" @selection-change="handlePddOrderItemSelectionChange" ref="pddOrderItem">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="erp系统商品id" prop="erpGoodsId" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.erpGoodsId" placeholder="请输入erp系统商品id" />
            </template>
          </el-table-column>
          <el-table-column label="erp系统商品规格id" prop="erpSpecId" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.erpSpecId" placeholder="请输入erp系统商品规格id" />
            </template>
          </el-table-column>
          <el-table-column label="商品名称" prop="goodsName" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.goodsName" placeholder="请输入商品名称" />
            </template>
          </el-table-column>
          <el-table-column label="商品编码" prop="goodsNum" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.goodsNum" placeholder="请输入商品编码" />
            </template>
          </el-table-column>
          <el-table-column label="商品规格" prop="goodsSpec" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.goodsSpec" placeholder="请输入商品规格" />
            </template>
          </el-table-column>
          <el-table-column label="商品规格编码" prop="specNum" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.specNum" placeholder="请输入商品规格编码" />
            </template>
          </el-table-column>
          <el-table-column label="商品单价" prop="goodsPrice" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.goodsPrice" placeholder="请输入商品单价" />
            </template>
          </el-table-column>
          <el-table-column label="子订单金额" prop="itemAmount" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.itemAmount" placeholder="请输入子订单金额" />
            </template>
          </el-table-column>
          <el-table-column label="商品数量" prop="quantity" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.quantity" placeholder="请输入商品数量" />
            </template>
          </el-table-column>
          <el-table-column label="是否礼品0否1是" prop="isGift" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.isGift" placeholder="请输入是否礼品0否1是" />
            </template>
          </el-table-column>
          <el-table-column label="拼多多商品id" prop="goodId" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.goodId" placeholder="请输入拼多多商品id" />
            </template>
          </el-table-column>
          <el-table-column label="拼多多商品skuid" prop="skuId" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.skuId" placeholder="请输入拼多多商品skuid" />
            </template>
          </el-table-column>
          <el-table-column label="已退货数量" prop="refundCount" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.refundCount" placeholder="请输入已退货数量" />
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listOrder, getOrder, delOrder, addOrder, updateOrder } from "@/api/pdd/order";

export default {
  name: "Order",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedPddOrderItem: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 拼多多订单表格数据
      orderList: [],
      // 拼多多订单明细表格数据
      pddOrderItemList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderSn: null,
        shopId: null,
        tradeType: null,
        confirmStatus: null,
        freeSf: null,
        groupStatus: null,
        capitalFreeDiscount: null,
        sellerDiscount: null,
        platformDiscount: null,
        updatedAt: null,
        refundStatus: null,
        isLuckyFlag: null,
        orderStatus: null,
        shippingTime: null,
        trackingNumber: null,
        trackingCompany: null,
        payType: null,
        payNo: null,
        postage: null,
        discountAmount: null,
        goodsAmount: null,
        payAmount: null,
        receiverPhone: null,
        receiverName: null,
        receiverName1: null,
        receiverPhone1: null,
        receiverAddress1: null,
        address: null,
        town: null,
        city: null,
        province: null,
        country: null,
        createdTime: null,
        payTime: null,
        confirmTime: null,
        receiveTime: null,
        buyerMemo: null,
        afterSalesStatus: null,
        orderConfirmTime: null,
        lastShipTime: null,
        auditStatus: null,
        settlementStatus: null,
        shipStatus: null,
        shipTime: null,
        tag: null,
        excelLogId: null,
        excelMsg: null,
        encryptedData: null,
        signature: null,
        printStatus: null,
        printTime: null,
        nameKey: null,
        phoneKey: null,
        addressKey: null,
        result: null,
        pullTime: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        orderSn: [
          { required: true, message: "订单编号不能为空", trigger: "blur" }
        ],
        shopId: [
          { required: true, message: "内部店铺ID不能为空", trigger: "blur" }
        ],
        tradeType: [
          { required: true, message: "订单类型 0-普通订单 ，1- 定金订单不能为空", trigger: "change" }
        ],
        confirmStatus: [
          { required: true, message: "成交状态：0：未成交、1：已成交、2：已取消、不能为空", trigger: "change" }
        ],
        freeSf: [
          { required: true, message: "是否顺丰包邮，1-是 0-否不能为空", trigger: "blur" }
        ],
        groupStatus: [
          { required: true, message: "成团状态：0：拼团中、1：已成团、2：团失败不能为空", trigger: "change" }
        ],
        capitalFreeDiscount: [
          { required: true, message: "团长免单金额，单位：元不能为空", trigger: "blur" }
        ],
        sellerDiscount: [
          { required: true, message: "商家优惠金额，单位：元不能为空", trigger: "blur" }
        ],
        platformDiscount: [
          { required: true, message: "平台优惠金额，单位：元不能为空", trigger: "blur" }
        ],
        refundStatus: [
          { required: true, message: "售后状态 1：无售后或售后关闭，2：售后处理中，3：退款中，4： 退款成功 5：全部不能为空", trigger: "change" }
        ],
        isLuckyFlag: [
          { required: true, message: "是否是抽奖订单，1-非抽奖订单，2-抽奖订单不能为空", trigger: "blur" }
        ],
        orderStatus: [
          { required: true, message: "订单状态1：待发货，2：已发货待签收，3：已签收不能为空", trigger: "change" }
        ],
        postage: [
          { required: true, message: "邮费，单位：元不能为空", trigger: "blur" }
        ],
        discountAmount: [
          { required: true, message: "折扣金额，单位：元，折扣金额=平台优惠+商家优惠+团长免单优惠金额不能为空", trigger: "blur" }
        ],
        goodsAmount: [
          { required: true, message: "商品金额，单位：元，商品金额=商品销售价格*商品数量-改价金额不能为空", trigger: "blur" }
        ],
        payAmount: [
          { required: true, message: "支付金额，单位：元，支付金额=商品金额-折扣金额+邮费不能为空", trigger: "blur" }
        ],
        afterSalesStatus: [
          { required: true, message: "售后状态 0：无售后 2：买家申请退款，待商家处理 3：退货退款，待商家处理 4：商家同意退款，退款中 5：平台同意退款，退款中 6：驳回退款， 待买家处理 7：已同意退货退款,待用户发货 8：平台处理中 9：平台拒 绝退款，退款关闭 10：退款成功 11：买家撤销 12：买家逾期未处 理，退款失败 13：买家逾期，超过有效期 14 : 换货补寄待商家处理 15:换货补寄待用户处理 16:换货补寄成功 17:换货补寄失败 18:换货补寄待用户确认完成不能为空", trigger: "change" }
        ],
        orderConfirmTime: [
          { required: true, message: "订单成交时间不能为空", trigger: "blur" }
        ],
        auditStatus: [
          { required: true, message: "0待确认，1已确认2已拦截-9未拉取不能为空", trigger: "change" }
        ],
        settlementStatus: [
          { required: true, message: "结算状态不能为空", trigger: "change" }
        ],
        shipStatus: [
          { required: true, message: "发货状态不能为空", trigger: "change" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询拼多多订单列表 */
    getList() {
      this.loading = true;
      listOrder(this.queryParams).then(response => {
        this.orderList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        orderSn: null,
        shopId: null,
        tradeType: null,
        confirmStatus: null,
        freeSf: null,
        groupStatus: null,
        capitalFreeDiscount: null,
        sellerDiscount: null,
        platformDiscount: null,
        remark: null,
        updatedAt: null,
        refundStatus: null,
        isLuckyFlag: null,
        orderStatus: null,
        shippingTime: null,
        trackingNumber: null,
        trackingCompany: null,
        payType: null,
        payNo: null,
        postage: null,
        discountAmount: null,
        goodsAmount: null,
        payAmount: null,
        receiverPhone: null,
        receiverName: null,
        receiverName1: null,
        receiverPhone1: null,
        receiverAddress1: null,
        address: null,
        town: null,
        city: null,
        province: null,
        country: null,
        createdTime: null,
        payTime: null,
        confirmTime: null,
        receiveTime: null,
        buyerMemo: null,
        afterSalesStatus: null,
        orderConfirmTime: null,
        lastShipTime: null,
        auditStatus: null,
        settlementStatus: null,
        shipStatus: null,
        shipTime: null,
        tag: null,
        excelLogId: null,
        excelMsg: null,
        encryptedData: null,
        signature: null,
        printStatus: null,
        printTime: null,
        nameKey: null,
        phoneKey: null,
        addressKey: null,
        result: null,
        pullTime: null
      };
      this.pddOrderItemList = [];
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加拼多多订单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getOrder(id).then(response => {
        this.form = response.data;
        this.pddOrderItemList = response.data.pddOrderItemList;
        this.open = true;
        this.title = "修改拼多多订单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.pddOrderItemList = this.pddOrderItemList;
          if (this.form.id != null) {
            updateOrder(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOrder(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除拼多多订单编号为"' + ids + '"的数据项？').then(function() {
        return delOrder(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
	/** 拼多多订单明细序号 */
    rowPddOrderItemIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** 拼多多订单明细添加按钮操作 */
    handleAddPddOrderItem() {
      let obj = {};
      obj.erpGoodsId = "";
      obj.erpSpecId = "";
      obj.goodsName = "";
      obj.goodsImage = "";
      obj.goodsNum = "";
      obj.goodsSpec = "";
      obj.specNum = "";
      obj.goodsPrice = "";
      obj.itemAmount = "";
      obj.quantity = "";
      obj.remark = "";
      obj.isGift = "";
      obj.goodId = "";
      obj.skuId = "";
      obj.refundCount = "";
      this.pddOrderItemList.push(obj);
    },
    /** 拼多多订单明细删除按钮操作 */
    handleDeletePddOrderItem() {
      if (this.checkedPddOrderItem.length == 0) {
        this.$modal.msgError("请先选择要删除的拼多多订单明细数据");
      } else {
        const pddOrderItemList = this.pddOrderItemList;
        const checkedPddOrderItem = this.checkedPddOrderItem;
        this.pddOrderItemList = pddOrderItemList.filter(function(item) {
          return checkedPddOrderItem.indexOf(item.index) == -1
        });
      }
    },
    /** 复选框选中数据 */
    handlePddOrderItemSelectionChange(selection) {
      this.checkedPddOrderItem = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('pdd/order/export', {
        ...this.queryParams
      }, `order_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
