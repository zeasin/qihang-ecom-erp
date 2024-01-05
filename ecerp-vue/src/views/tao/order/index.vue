<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="订单号" prop="id">
        <el-input
          v-model="queryParams.id"
          placeholder="请输入订单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="店铺" prop="shopId">
        <el-input
          v-model="queryParams.shopId"
          placeholder="请输入店铺"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="订单创建时间" prop="orderCreateTime">
        <el-date-picker clearable
          v-model="queryParams.orderCreateTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择订单创建时间">
        </el-date-picker>
      </el-form-item>

      <el-form-item label="订单状态" prop="statusStr">
        <el-input
          v-model="queryParams.statusStr"
          placeholder="请输入订单状态"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="快递单号" prop="logisticsCode">
        <el-input
          v-model="queryParams.logisticsCode"
          placeholder="请输入快递单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>


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
          v-hasPermi="['tao:order:add']"
        >手动添加</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-upload"
          size="mini"

          @click="handleUpdate"
          v-hasPermi="['tao:order:edit']"
        >Execl导入</el-button>
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['tao:order:remove']"
        >删除</el-button>
      </el-col> -->
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['tao:order:export']"
        >导出订单</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="orderList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="订单号" align="center" prop="id" />
      <el-table-column label="店铺" align="center" prop="shopId" />
      <el-table-column label="商品" width="350">
          <template slot-scope="scope">
            <el-row v-for="item in scope.row.taoOrderItemList" :key="item.id" :gutter="20">
              
            <div style="float: left;display: flex;align-items: center;" >
              <el-image  style="width: 70px; height: 70px;" :src="item.productImgUrl"></el-image>
              <div style="margin-left:10px">
              <p>{{item.goodsTitle}}</p>
              <p>{{item.skuInfo}}&nbsp;
                <el-tag size="small">x {{item.quantity}}</el-tag>
                </p>
              </div>
            </div>
            </el-row>
          </template>
      </el-table-column>
      <el-table-column label="总金额" align="center" prop="totalAmount" />
      <el-table-column label="运费" align="center" prop="shippingFee" />
      <!-- <el-table-column label="优惠金额" align="center" prop="discountAmount" /> -->
      <!-- <el-table-column label="实际支付金额" align="center" prop="payAmount" /> -->
      <!-- <el-table-column label="优惠描述" align="center" prop="discountRemark" /> -->
      <el-table-column label="订单创建时间" align="center" prop="orderCreateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.orderCreateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="订单修改时间" align="center" prop="orderModifyTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.orderModifyTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
 -->
     <!--  <el-table-column label="发货时间" align="center" prop="deliveredTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.deliveredTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="完成时间" align="center" prop="completeTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.completeTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column> -->
      <el-table-column label="卖家备注" align="center" prop="sellerMemo" />
      <el-table-column label="买家留言" align="center" prop="buyerFeedback" />
<!--      <el-table-column label="关闭原因。buyerCancel:买家取消订单，sellerGoodsLack:卖家库存不足，other:其它" align="center" prop="closeReason" />-->

      <el-table-column label="订单状态" align="center" prop="statusStr" />
      <el-table-column label="交易状态" align="center" prop="status" />
      <!-- <el-table-column label="快递公司" align="center" prop="logisticsCompany" /> -->
<!--      <el-table-column label="快递公司编码" align="center" prop="logisticsCompanyCode" />-->
      <el-table-column label="快递单号" align="center" prop="logisticsCode" />
<!--      <el-table-column label="退款单ID" align="center" prop="refundId" />-->
<!--      <el-table-column label="退款金额，单位为元" align="center" prop="refundAmount" />-->
<!--      <el-table-column label="订单的售中退款状态，等待卖家同意：waitselleragree ，待买家修改：waitbuyermodify，等待买家退货：waitbuyersend，等待卖家确认收货：waitsellerreceive，退款成功：refundsuccess，退款失败：refundclose" align="center" prop="refundStatus" />-->
<!--      <el-table-column label="订单审核状态" align="center" prop="auditStatus" />-->
<!--      <el-table-column label="订单审核时间" align="center" prop="auditTime" width="180">-->
<!--        <template slot-scope="scope">-->
<!--          <span>{{ parseTime(scope.row.auditTime, '{y}-{m}-{d}') }}</span>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--      <el-table-column label="发货状态" align="center" prop="sendStatus" />-->
<!--      <el-table-column label="仓库发货时间" align="center" prop="sendTime" width="180">-->
<!--        <template slot-scope="scope">-->
<!--          <span>{{ parseTime(scope.row.sendTime, '{y}-{m}-{d}') }}</span>-->
<!--        </template>-->
<!--      </el-table-column>-->
      <!-- <el-table-column label="标签(1：实售2：淘宝客3：刷单4：返现)" align="center" prop="tag" /> -->
      <el-table-column label="备注" align="center" prop="remark" />
      <!-- <el-table-column label="是否评价" align="center" prop="isComment" /> -->
      <!-- <el-table-column label="是否合并发货(0:否1:是)" align="center" prop="isMerge" /> -->
<!--      <el-table-column label="订单创建时间" align="center" prop="createTime" width="180">-->
<!--        <template slot-scope="scope">-->
<!--          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--      <el-table-column label="创建人" align="center" prop="createBy" />-->
<!--      <el-table-column label="更新时间" align="center" prop="updateTime" width="180">-->
<!--        <template slot-scope="scope">-->
<!--          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--      <el-table-column label="更新人" align="center" prop="updateBy" />-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['tao:order:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['tao:order:remove']"
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

    <!-- 添加或修改淘宝订单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="店铺id" prop="shopId">
          <el-input v-model="form.shopId" placeholder="请输入店铺id" />
        </el-form-item>
        <el-form-item label="订单来源0天猫1淘宝" prop="orderSource">
          <el-input v-model="form.orderSource" placeholder="请输入订单来源0天猫1淘宝" />
        </el-form-item>
        <el-form-item label="买家昵称" prop="buyerName">
          <el-input v-model="form.buyerName" placeholder="请输入买家昵称" />
        </el-form-item>
        <el-form-item label="应付款总金额，totalAmount = ∑itemAmount + shippingFee，单位为元" prop="totalAmount">
          <el-input v-model="form.totalAmount" placeholder="请输入应付款总金额，totalAmount = ∑itemAmount + shippingFee，单位为元" />
        </el-form-item>
        <el-form-item label="运费" prop="shippingFee">
          <el-input v-model="form.shippingFee" placeholder="请输入运费" />
        </el-form-item>
        <el-form-item label="优惠金额" prop="discountAmount">
          <el-input v-model="form.discountAmount" placeholder="请输入优惠金额" />
        </el-form-item>
        <el-form-item label="实际支付金额" prop="payAmount">
          <el-input v-model="form.payAmount" placeholder="请输入实际支付金额" />
        </el-form-item>
        <el-form-item label="优惠描述" prop="discountRemark">
          <el-input v-model="form.discountRemark" placeholder="请输入优惠描述" />
        </el-form-item>
        <el-form-item label="订单创建时间" prop="orderCreateTime">
          <el-date-picker clearable
            v-model="form.orderCreateTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择订单创建时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="订单修改时间" prop="orderModifyTime">
          <el-date-picker clearable
            v-model="form.orderModifyTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择订单修改时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="付款时间，如果有多次付款，这里返回的是首次付款时间" prop="payTime">
          <el-date-picker clearable
            v-model="form.payTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择付款时间，如果有多次付款，这里返回的是首次付款时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="确认时间" prop="confirmedTime">
          <el-date-picker clearable
            v-model="form.confirmedTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择确认时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="发货时间" prop="deliveredTime">
          <el-date-picker clearable
            v-model="form.deliveredTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择发货时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="完成时间" prop="completeTime">
          <el-date-picker clearable
            v-model="form.completeTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择完成时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="卖家备忘信息" prop="sellerMemo">
          <el-input v-model="form.sellerMemo" placeholder="请输入卖家备忘信息" />
        </el-form-item>
        <el-form-item label="买家留言，不超过500字" prop="buyerFeedback">
          <el-input v-model="form.buyerFeedback" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="关闭原因。buyerCancel:买家取消订单，sellerGoodsLack:卖家库存不足，other:其它" prop="closeReason">
          <el-input v-model="form.closeReason" placeholder="请输入关闭原因。buyerCancel:买家取消订单，sellerGoodsLack:卖家库存不足，other:其它" />
        </el-form-item>
        <el-form-item label="收货时间，这里返回的是完全收货时间" prop="receivingTime">
          <el-date-picker clearable
            v-model="form.receivingTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择收货时间，这里返回的是完全收货时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="订单状态" prop="statusStr">
          <el-input v-model="form.statusStr" placeholder="请输入订单状态" />
        </el-form-item>
        <el-form-item label="快递公司" prop="logisticsCompany">
          <el-input v-model="form.logisticsCompany" placeholder="请输入快递公司" />
        </el-form-item>
        <el-form-item label="快递公司编码" prop="logisticsCompanyCode">
          <el-input v-model="form.logisticsCompanyCode" placeholder="请输入快递公司编码" />
        </el-form-item>
        <el-form-item label="快递单号" prop="logisticsCode">
          <el-input v-model="form.logisticsCode" placeholder="请输入快递单号" />
        </el-form-item>
        <el-form-item label="退款单ID" prop="refundId">
          <el-input v-model="form.refundId" placeholder="请输入退款单ID" />
        </el-form-item>
        <el-form-item label="退款金额，单位为元" prop="refundAmount">
          <el-input v-model="form.refundAmount" placeholder="请输入退款金额，单位为元" />
        </el-form-item>
        <el-form-item label="订单审核时间" prop="auditTime">
          <el-date-picker clearable
            v-model="form.auditTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择订单审核时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="仓库发货时间" prop="sendTime">
          <el-date-picker clearable
            v-model="form.sendTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择仓库发货时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="标签(1：实售2：淘宝客3：刷单4：返现)" prop="tag">
          <el-input v-model="form.tag" placeholder="请输入标签(1：实售2：淘宝客3：刷单4：返现)" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="是否评价" prop="isComment">
          <el-input v-model="form.isComment" placeholder="请输入是否评价" />
        </el-form-item>
        <el-form-item label="是否合并发货(0:否1:是)" prop="isMerge">
          <el-input v-model="form.isMerge" placeholder="请输入是否合并发货(0:否1:是)" />
        </el-form-item>
        <el-divider content-position="center">淘宝订单明细信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddTaoOrderItem">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteTaoOrderItem">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="taoOrderItemList" :row-class-name="rowTaoOrderItemIndex" @selection-change="handleTaoOrderItemSelectionChange" ref="taoOrderItem">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="天猫子订单id" prop="subItemId" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.subItemId" placeholder="请输入天猫子订单id" />
            </template>
          </el-table-column>
          <el-table-column label="明细总金额" prop="itemAmount" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.itemAmount" placeholder="请输入明细总金额" />
            </template>
          </el-table-column>
          <el-table-column label="优惠金额" prop="discountFee" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.discountFee" placeholder="请输入优惠金额" />
            </template>
          </el-table-column>
          <el-table-column label="手工调整金额" prop="adjustFee" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.adjustFee" placeholder="请输入手工调整金额" />
            </template>
          </el-table-column>
          <el-table-column label="商品标题" prop="goodsTitle" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.goodsTitle" placeholder="请输入商品标题" />
            </template>
          </el-table-column>
          <el-table-column label="商品货号，对应系统商品编码" prop="goodsNumber" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.goodsNumber" placeholder="请输入商品货号，对应系统商品编码" />
            </template>
          </el-table-column>
          <el-table-column label="商品主图" prop="productImgUrl" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.productImgUrl" placeholder="请输入商品主图" />
            </template>
          </el-table-column>
          <el-table-column label="商品链接" prop="productUrl" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.productUrl" placeholder="请输入商品链接" />
            </template>
          </el-table-column>
          <el-table-column label="天猫的商品Id" prop="productId" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.productId" placeholder="请输入天猫的商品Id" />
            </template>
          </el-table-column>
          <el-table-column label="天猫的SKUID" prop="skuId" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.skuId" placeholder="请输入天猫的SKUID" />
            </template>
          </el-table-column>
          <el-table-column label="单品货号，对应系统sku编码" prop="specNumber" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.specNumber" placeholder="请输入单品货号，对应系统sku编码" />
            </template>
          </el-table-column>
          <el-table-column label="SKU字符串" prop="skuInfo" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.skuInfo" placeholder="请输入SKU字符串" />
            </template>
          </el-table-column>
          <el-table-column label="实际单价" prop="price" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.price" placeholder="请输入实际单价" />
            </template>
          </el-table-column>
          <el-table-column label="数量" prop="quantity" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.quantity" placeholder="请输入数量" />
            </template>
          </el-table-column>
          <el-table-column label="子订单状态" prop="status" width="150">
            <template slot-scope="scope">
              <el-select v-model="scope.row.status" placeholder="请选择子订单状态">
                <el-option label="请选择字典生成" value="" />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="子订单状态" prop="statusStr" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.statusStr" placeholder="请输入子订单状态" />
            </template>
          </el-table-column>
          <el-table-column label="退款状态0无售后1售后中" prop="refundStatus" width="150">
            <template slot-scope="scope">
              <el-select v-model="scope.row.refundStatus" placeholder="请选择退款状态0无售后1售后中">
                <el-option label="请选择字典生成" value="" />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="退款状态" prop="refundStatusStr" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.refundStatusStr" placeholder="请输入退款状态" />
            </template>
          </el-table-column>
          <el-table-column label="退款金额" prop="refundAmount" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.refundAmount" placeholder="请输入退款金额" />
            </template>
          </el-table-column>
          <el-table-column label="退款单id" prop="refundId" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.refundId" placeholder="请输入退款单id" />
            </template>
          </el-table-column>
          <el-table-column label="1 未发货 2 已发货 3 已收货 4 已经退货 5 部分发货 8 还未创建物流订单" prop="logisticsStatus" width="150">
            <template slot-scope="scope">
              <el-select v-model="scope.row.logisticsStatus" placeholder="请选择1 未发货 2 已发货 3 已收货 4 已经退货 5 部分发货 8 还未创建物流订单">
                <el-option label="请选择字典生成" value="" />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="确认订单最新规格id" prop="newSpecId" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.newSpecId" placeholder="请输入确认订单最新规格id" />
            </template>
          </el-table-column>
          <el-table-column label="确认订单最新规格编码" prop="newSpecNumber" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.newSpecNumber" placeholder="请输入确认订单最新规格编码" />
            </template>
          </el-table-column>
          <el-table-column label="售后状态0未申请售后1售后申请中(退款待审核)2同意退货(退款待收货)3买家已发货，待收货(待收货)4已收货" prop="afterSaleState" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.afterSaleState" placeholder="请输入售后状态0未申请售后1售后申请中(退款待审核)2同意退货(退款待收货)3买家已发货，待收货(待收货)4已收货" />
            </template>
          </el-table-column>
          <el-table-column label="erp系统商品id" prop="erpGoodsId" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.erpGoodsId" placeholder="请输入erp系统商品id" />
            </template>
          </el-table-column>
          <el-table-column label="erp系统商品规格id" prop="erpGoodsSpecId" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.erpGoodsSpecId" placeholder="请输入erp系统商品规格id" />
            </template>
          </el-table-column>
          <el-table-column label="是否礼品0否1是" prop="isGift" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.isGift" placeholder="请输入是否礼品0否1是" />
            </template>
          </el-table-column>
          <el-table-column label="是否换货(0:否1:是)" prop="isSwap" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.isSwap" placeholder="请输入是否换货(0:否1:是)" />
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
import { listOrder, getOrder, delOrder, addOrder, updateOrder } from "@/api/tao/order";

export default {
  name: "Order",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedTaoOrderItem: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 淘宝订单表格数据
      orderList: [],
      // 淘宝订单明细表格数据
      taoOrderItemList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        shopId: null,
        orderSource: null,
        buyerName: null,
        totalAmount: null,
        shippingFee: null,
        discountAmount: null,
        payAmount: null,
        discountRemark: null,
        orderCreateTime: null,
        orderModifyTime: null,
        payTime: null,
        confirmedTime: null,
        deliveredTime: null,
        completeTime: null,
        sellerMemo: null,
        buyerFeedback: null,
        closeReason: null,
        receivingTime: null,
        statusStr: null,
        status: null,
        logisticsCompany: null,
        logisticsCompanyCode: null,
        logisticsCode: null,
        refundId: null,
        refundAmount: null,
        refundStatus: null,
        auditStatus: null,
        auditTime: null,
        sendStatus: null,
        sendTime: null,
        tag: null,
        isComment: null,
        isMerge: null,
        createTime: null,
        createBy: null,
        updateTime: null,
        updateBy: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        shopId: [
          { required: true, message: "店铺id不能为空", trigger: "blur" }
        ],
        buyerName: [
          { required: true, message: "买家昵称不能为空", trigger: "blur" }
        ],
        totalAmount: [
          { required: true, message: "应付款总金额，totalAmount = ∑itemAmount + shippingFee，单位为元不能为空", trigger: "blur" }
        ],
        shippingFee: [
          { required: true, message: "运费不能为空", trigger: "blur" }
        ],
        discountAmount: [
          { required: true, message: "优惠金额不能为空", trigger: "blur" }
        ],
        payAmount: [
          { required: true, message: "实际支付金额不能为空", trigger: "blur" }
        ],
        orderCreateTime: [
          { required: true, message: "订单创建时间不能为空", trigger: "blur" }
        ],
        status: [
          { required: true, message: "交易状态，waitbuyerpay:等待买家付款;waitsellersend:等待卖家发货;waitlogisticstakein:等待物流公司揽件;waitbuyerreceive:等待买家收货;waitbuyersign:等待买家签收;signinsuccess:买家已签收;confirm_goods:已收货;success:交易成功;cancel:交易取消;terminated:交易终止;未枚举:其他状态不能为空", trigger: "change" }
        ],
        auditStatus: [
          { required: true, message: "订单审核状态不能为空", trigger: "change" }
        ],
        createTime: [
          { required: true, message: "订单创建时间不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询淘宝订单列表 */
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
        shopId: null,
        orderSource: null,
        buyerName: null,
        totalAmount: null,
        shippingFee: null,
        discountAmount: null,
        payAmount: null,
        discountRemark: null,
        orderCreateTime: null,
        orderModifyTime: null,
        payTime: null,
        confirmedTime: null,
        deliveredTime: null,
        completeTime: null,
        sellerMemo: null,
        buyerFeedback: null,
        closeReason: null,
        receivingTime: null,
        statusStr: null,
        status: null,
        logisticsCompany: null,
        logisticsCompanyCode: null,
        logisticsCode: null,
        refundId: null,
        refundAmount: null,
        refundStatus: null,
        auditStatus: null,
        auditTime: null,
        sendStatus: null,
        sendTime: null,
        tag: null,
        remark: null,
        isComment: null,
        isMerge: null,
        createTime: null,
        createBy: null,
        updateTime: null,
        updateBy: null
      };
      this.taoOrderItemList = [];
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
      this.title = "添加淘宝订单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getOrder(id).then(response => {
        this.form = response.data;
        this.taoOrderItemList = response.data.taoOrderItemList;
        this.open = true;
        this.title = "修改淘宝订单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.taoOrderItemList = this.taoOrderItemList;
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
      this.$modal.confirm('是否确认删除淘宝订单编号为"' + ids + '"的数据项？').then(function() {
        return delOrder(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
	/** 淘宝订单明细序号 */
    rowTaoOrderItemIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** 淘宝订单明细添加按钮操作 */
    handleAddTaoOrderItem() {
      let obj = {};
      obj.subItemId = "";
      obj.itemAmount = "";
      obj.discountFee = "";
      obj.adjustFee = "";
      obj.goodsTitle = "";
      obj.goodsNumber = "";
      obj.productImgUrl = "";
      obj.productUrl = "";
      obj.productId = "";
      obj.skuId = "";
      obj.specNumber = "";
      obj.skuInfo = "";
      obj.price = "";
      obj.quantity = "";
      obj.status = "";
      obj.statusStr = "";
      obj.refundStatus = "";
      obj.refundStatusStr = "";
      obj.refundAmount = "";
      obj.refundId = "";
      obj.logisticsStatus = "";
      obj.newSpecId = "";
      obj.newSpecNumber = "";
      obj.afterSaleState = "";
      obj.erpGoodsId = "";
      obj.erpGoodsSpecId = "";
      obj.remark = "";
      obj.isGift = "";
      obj.isSwap = "";
      this.taoOrderItemList.push(obj);
    },
    /** 淘宝订单明细删除按钮操作 */
    handleDeleteTaoOrderItem() {
      if (this.checkedTaoOrderItem.length == 0) {
        this.$modal.msgError("请先选择要删除的淘宝订单明细数据");
      } else {
        const taoOrderItemList = this.taoOrderItemList;
        const checkedTaoOrderItem = this.checkedTaoOrderItem;
        this.taoOrderItemList = taoOrderItemList.filter(function(item) {
          return checkedTaoOrderItem.indexOf(item.index) == -1
        });
      }
    },
    /** 复选框选中数据 */
    handleTaoOrderItemSelectionChange(selection) {
      this.checkedTaoOrderItem = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('tao/order/export', {
        ...this.queryParams
      }, `order_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
