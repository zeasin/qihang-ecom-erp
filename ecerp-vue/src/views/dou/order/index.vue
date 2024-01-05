<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="订单id" prop="orderId">
        <el-input
          v-model="queryParams.orderId"
          placeholder="请输入订单id"
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

      <el-form-item label="收件人" prop="postReceiver">
        <el-input
          v-model="queryParams.postReceiver"
          placeholder="请输入收件人姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="手机号" prop="postTel">
        <el-input
          v-model="queryParams.postTel"
          placeholder="请输入收件人电话"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="物流单号" prop="logisticsCode">
        <el-input
          v-model="queryParams.logisticsCode"
          placeholder="请输入物流单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="物流公司" prop="logisticsCompany">
        <el-input
          v-model="queryParams.logisticsCompany"
          placeholder="请输入物流公司"
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
          v-hasPermi="['dou:order:add']"
        >手动添加</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-upload"
          size="mini"
          @click="handleUpdate"
          v-hasPermi="['dou:order:edit']"
        >Execl导入</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-download"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['dou:order:remove']"
        >API拉取订单</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['dou:order:export']"
        >导出订单</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="orderList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
<!--      <el-table-column label="订单id，自增" align="center" prop="id" />-->
      <el-table-column label="订单id" align="center" prop="orderId" />
      <el-table-column label="店铺" align="center" prop="shopId" >
        <template slot-scope="scope">
          <span v-if="scope.row.shopId==22">梦小妮牛仔裤</span>
        </template>
      </el-table-column>
      <el-table-column label="商品" width="350">
          <template slot-scope="scope">
            <el-row v-for="item in scope.row.douOrderItemList" :key="item.id" :gutter="20">
              
            <div style="float: left;display: flex;align-items: center;" >
              <el-image  style="width: 70px; height: 70px;" :src="item.productPic"></el-image>
              <div style="margin-left:10px">
              <p>{{item.productName}}</p>
              <p>{{item.goodsSpec}}</p>
              <p>
                <el-tag size="small">x {{item.comboNum}}</el-tag>
                </p>
              </div>
            </div>
            </el-row>
          </template>
      </el-table-column>
      <el-table-column label="订单金额" align="center" prop="orderTotalAmount" />
      <el-table-column label="运费" align="center" prop="postAmount" />
<!--      <el-table-column label="买家用户名" align="center" prop="userName" />-->
      <!-- <el-table-column label="收货地址" align="center" prop="postAddr" /> -->
<!--      <el-table-column label="邮政编码" align="center" prop="postCode" />-->
      <el-table-column label="收件人" align="center" prop="postReceiver" />
<!--      <el-table-column label="收件人电话" align="center" prop="postTel" />-->
      <el-table-column label="买家备注" align="center" prop="buyerWords" />
      <el-table-column label="卖家备注" align="center" prop="sellerWords" />
<!--      <el-table-column label="物流公司id" align="center" prop="logisticsId" />-->
      <el-table-column label="物流单号" align="center" prop="logisticsCode" />
      <!-- <el-table-column label="物流公司" align="center" prop="logisticsCompany" /> -->
      <!-- <el-table-column label="发货时间" align="center" prop="logisticsTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.logisticsTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="收货时间" align="center" prop="receiptTime" /> -->
      <el-table-column label="订单状态" align="center" prop="orderStatus" >
        <template slot-scope="scope">
          <!-- 订单状态1 待确认/待支付（订单创建完毕）105 已支付 2 备货中 101 部分发货 3 已发货（全部发货）4 已取消5 已完成（已收货） -->
          <el-tag size="small" v-if="scope.row.orderStatus === 1"> 待支付</el-tag>
          <el-tag size="small" v-if="scope.row.orderStatus === 2"> 备货中</el-tag>
          <el-tag size="small" v-if="scope.row.orderStatus === 3"> 已发货</el-tag>
          <el-tag size="small" v-if="scope.row.orderStatus === 4"> 已取消</el-tag>
          <el-tag size="small" v-if="scope.row.orderStatus === 5"> 已完成</el-tag>
          <span></span>

          <el-tag size="small" v-if="scope.row.auditStatus === 0" style="margin-top: 5px;"> 待确认</el-tag>
          <el-tag size="small" v-if="scope.row.auditStatus === 1" style="margin-top: 5px;"> 已确认</el-tag>
          <el-tag size="small" v-if="scope.row.auditStatus === 2" style="margin-top: 5px;"> 已拦截</el-tag>

        </template>
      </el-table-column>
      <!-- <el-table-column label="订单状态" align="center" prop="orderStatusStr" /> -->
      <el-table-column label="订单创建时间" align="center" prop="orderCreateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.orderCreateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="最晚发货时间" align="center" prop="expShipTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.expShipTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column> -->
<!--      <el-table-column label="订单取消原因" align="center" prop="cancelReason" />-->
<!--      <el-table-column label="【支付类型" align="center" prop="payType" />-->
      <!-- <el-table-column label="支付方式" align="center" prop="payTypeName" /> -->
<!--      <el-table-column label="支付时间 (pay_type为0货到付款时, 此字段为空)" align="center" prop="payTime" width="180">-->
<!--        <template slot-scope="scope">-->
<!--          <span>{{ parseTime(scope.row.payTime, '{y}-{m}-{d}') }}</span>-->
<!--        </template>-->
<!--      </el-table-column>-->
      
      <!-- <el-table-column label="平台优惠券金额 (单位: 分)" align="center" prop="couponAmount" /> -->
      <!-- <el-table-column label="商家优惠券金额 (单位: 分)" align="center" prop="shopCouponAmount" /> -->
<!--      <el-table-column label="优惠券详情 (type为优惠券类型, credit为优惠金额,单位分)" align="center" prop="couponInfo" />-->
      
<!--      <el-table-column label="运费险金额" align="center" prop="postInsuranceAmount" />-->
      <!-- <el-table-column label="是否评价 (1:已评价)" align="center" prop="isComment" /> -->
      <!-- <el-table-column label="订单佣金 (详情见附录)" align="center" prop="cType" /> -->
<!--      <el-table-column label="订单渠道 (站外0 火山1 抖音2 头条3 西瓜4 微信5 闪购6 头条lite版本7 懂车帝8 皮皮虾9)" align="center" prop="bType" />-->
<!--      <el-table-column label="app渠道" align="center" prop="appSource" />-->
<!--      <el-table-column label="流量来源" align="center" prop="trafficeSource" />-->
<!--      <el-table-column label="佣金率" align="center" prop="cosRatio" />-->
<!--      <el-table-column label="创建时间" align="center" prop="createdTime" width="180">-->
<!--        <template slot-scope="scope">-->
<!--          <span>{{ parseTime(scope.row.createdTime, '{y}-{m}-{d}') }}</span>-->
<!--        </template>-->
<!--      </el-table-column>-->
     <!--  <el-table-column label="发货状态" align="center" prop="sendStatus" />
      <el-table-column label="发货时间" align="center" prop="sendTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.sendTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column> -->
      <!-- <el-table-column label="订单审核状态" align="center" prop="auditStatus" /> -->
<!--      <el-table-column label="加密地址详情" align="center" prop="encryptDetail" />-->
      <!-- <el-table-column label="省" align="center" prop="province" /> -->
      <!-- <el-table-column label="市" align="center" prop="city" /> -->
      <!-- <el-table-column label="区" align="center" prop="town" /> -->
      <!-- <el-table-column label="街道" align="center" prop="street" /> -->
<!--      <el-table-column label="发货时间" align="center" prop="shipTime" />-->
<!--      <el-table-column label="0、普通 1、拼团 2、定金预售 3、订金找贷 4、拍卖 5、0元单 6、回收 7、寄卖" align="center" prop="tradeType" />-->
<!--      <el-table-column label="加密电话" align="center" prop="encryptPostTel" />-->
<!--      <el-table-column label="加密联系人" align="center" prop="encryptPostReceiver" />-->
<!--      <el-table-column label="打单结果" align="center" prop="result" />-->
<!--      <el-table-column label="打印状态" align="center" prop="printStatus" />-->
<!--      <el-table-column label="打印日期" align="center" prop="printTime" width="180">-->
<!--        <template slot-scope="scope">-->
<!--          <span>{{ parseTime(scope.row.printTime, '{y}-{m}-{d}') }}</span>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--      <el-table-column label="号码检索串" align="center" prop="phoneKey" />-->
<!--      <el-table-column label="地址检索串" align="center" prop="addressKey" />-->
<!--      <el-table-column label="达人id" align="center" prop="authorId" />-->
<!--      <el-table-column label="${comment}" align="center" prop="authorName" />-->
<!--      <el-table-column label="是否结算" align="center" prop="settlementStatus" />-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <!-- <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['dou:order:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['dou:order:remove']"
          >删除</el-button>
        </template> -->
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改抖店订单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="抖音订单id" prop="orderId">
          <el-input v-model="form.orderId" placeholder="请输入抖音订单id" />
        </el-form-item>
        <el-form-item label="订单所属商户id" prop="shopId">
          <el-input v-model="form.shopId" placeholder="请输入订单所属商户id" />
        </el-form-item>
        <el-form-item label="买家用户名" prop="userName">
          <el-input v-model="form.userName" placeholder="请输入买家用户名" />
        </el-form-item>
        <el-form-item label="邮寄地址 (展开为省市区json, 格式参考 订单-获取订单列表 返回示例)" prop="postAddr">
          <el-input v-model="form.postAddr" placeholder="请输入邮寄地址 (展开为省市区json, 格式参考 订单-获取订单列表 返回示例)" />
        </el-form-item>
        <el-form-item label="邮政编码" prop="postCode">
          <el-input v-model="form.postCode" placeholder="请输入邮政编码" />
        </el-form-item>
        <el-form-item label="收件人姓名" prop="postReceiver">
          <el-input v-model="form.postReceiver" placeholder="请输入收件人姓名" />
        </el-form-item>
        <el-form-item label="收件人电话" prop="postTel">
          <el-input v-model="form.postTel" placeholder="请输入收件人电话" />
        </el-form-item>
        <el-form-item label="买家备注" prop="buyerWords">
          <el-input v-model="form.buyerWords" placeholder="请输入买家备注" />
        </el-form-item>
        <el-form-item label="卖家备注" prop="sellerWords">
          <el-input v-model="form.sellerWords" placeholder="请输入卖家备注" />
        </el-form-item>
        <el-form-item label="物流公司id" prop="logisticsId">
          <el-input v-model="form.logisticsId" placeholder="请输入物流公司id" />
        </el-form-item>
        <el-form-item label="物流单号" prop="logisticsCode">
          <el-input v-model="form.logisticsCode" placeholder="请输入物流单号" />
        </el-form-item>
        <el-form-item label="物流公司" prop="logisticsCompany">
          <el-input v-model="form.logisticsCompany" placeholder="请输入物流公司" />
        </el-form-item>
        <el-form-item label="发货时间" prop="logisticsTime">
          <el-date-picker clearable
            v-model="form.logisticsTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择发货时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="收货时间" prop="receiptTime">
          <el-input v-model="form.receiptTime" placeholder="请输入收货时间" />
        </el-form-item>
        <el-form-item label="${comment}" prop="orderStatusStr">
          <el-input v-model="form.orderStatusStr" placeholder="请输入${comment}" />
        </el-form-item>
        <el-form-item label="订单创建时间" prop="orderCreateTime">
          <el-date-picker clearable
            v-model="form.orderCreateTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择订单创建时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="最晚发货时间" prop="expShipTime">
          <el-date-picker clearable
            v-model="form.expShipTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择最晚发货时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="订单取消原因" prop="cancelReason">
          <el-input v-model="form.cancelReason" placeholder="请输入订单取消原因" />
        </el-form-item>
        <el-form-item label="支付方式" prop="payTypeName">
          <el-input v-model="form.payTypeName" placeholder="请输入支付方式" />
        </el-form-item>
        <el-form-item label="支付时间 (pay_type为0货到付款时, 此字段为空)" prop="payTime">
          <el-date-picker clearable
            v-model="form.payTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择支付时间 (pay_type为0货到付款时, 此字段为空)">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="邮费金额 (单位: 分)" prop="postAmount">
          <el-input v-model="form.postAmount" placeholder="请输入邮费金额 (单位: 分)" />
        </el-form-item>
        <el-form-item label="平台优惠券金额 (单位: 分)" prop="couponAmount">
          <el-input v-model="form.couponAmount" placeholder="请输入平台优惠券金额 (单位: 分)" />
        </el-form-item>
        <el-form-item label="商家优惠券金额 (单位: 分)" prop="shopCouponAmount">
          <el-input v-model="form.shopCouponAmount" placeholder="请输入商家优惠券金额 (单位: 分)" />
        </el-form-item>
        <el-form-item label="优惠券详情 (type为优惠券类型, credit为优惠金额,单位分)" prop="couponInfo">
          <el-input v-model="form.couponInfo" placeholder="请输入优惠券详情 (type为优惠券类型, credit为优惠金额,单位分)" />
        </el-form-item>
        <el-form-item label="父订单总金额 (单位: 分) 即用户实际支付金额, 不包含运费" prop="orderTotalAmount">
          <el-input v-model="form.orderTotalAmount" placeholder="请输入父订单总金额 (单位: 分) 即用户实际支付金额, 不包含运费" />
        </el-form-item>
        <el-form-item label="运费险金额" prop="postInsuranceAmount">
          <el-input v-model="form.postInsuranceAmount" placeholder="请输入运费险金额" />
        </el-form-item>
        <el-form-item label="是否评价 (1:已评价)" prop="isComment">
          <el-input v-model="form.isComment" placeholder="请输入是否评价 (1:已评价)" />
        </el-form-item>
        <el-form-item label="app渠道" prop="appSource">
          <el-input v-model="form.appSource" placeholder="请输入app渠道" />
        </el-form-item>
        <el-form-item label="流量来源" prop="trafficeSource">
          <el-input v-model="form.trafficeSource" placeholder="请输入流量来源" />
        </el-form-item>
        <el-form-item label="佣金率" prop="cosRatio">
          <el-input v-model="form.cosRatio" placeholder="请输入佣金率" />
        </el-form-item>
        <el-form-item label="创建时间" prop="createdTime">
          <el-date-picker clearable
            v-model="form.createdTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择创建时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="发货时间" prop="sendTime">
          <el-date-picker clearable
            v-model="form.sendTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择发货时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="加密地址详情" prop="encryptDetail">
          <el-input v-model="form.encryptDetail" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="${comment}" prop="province">
          <el-input v-model="form.province" placeholder="请输入${comment}" />
        </el-form-item>
        <el-form-item label="${comment}" prop="city">
          <el-input v-model="form.city" placeholder="请输入${comment}" />
        </el-form-item>
        <el-form-item label="${comment}" prop="town">
          <el-input v-model="form.town" placeholder="请输入${comment}" />
        </el-form-item>
        <el-form-item label="${comment}" prop="street">
          <el-input v-model="form.street" placeholder="请输入${comment}" />
        </el-form-item>
        <el-form-item label="发货时间" prop="shipTime">
          <el-input v-model="form.shipTime" placeholder="请输入发货时间" />
        </el-form-item>
        <el-form-item label="加密电话" prop="encryptPostTel">
          <el-input v-model="form.encryptPostTel" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="加密联系人" prop="encryptPostReceiver">
          <el-input v-model="form.encryptPostReceiver" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="打单结果" prop="result">
          <el-input v-model="form.result" placeholder="请输入打单结果" />
        </el-form-item>
        <el-form-item label="打印日期" prop="printTime">
          <el-date-picker clearable
            v-model="form.printTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择打印日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="号码检索串" prop="phoneKey">
          <el-input v-model="form.phoneKey" placeholder="请输入号码检索串" />
        </el-form-item>
        <el-form-item label="地址检索串" prop="addressKey">
          <el-input v-model="form.addressKey" placeholder="请输入地址检索串" />
        </el-form-item>
        <el-form-item label="达人id" prop="authorId">
          <el-input v-model="form.authorId" placeholder="请输入达人id" />
        </el-form-item>
        <el-form-item label="${comment}" prop="authorName">
          <el-input v-model="form.authorName" placeholder="请输入${comment}" />
        </el-form-item>
        <el-divider content-position="center">抖店订单明细信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddDouOrderItem">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteDouOrderItem">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="douOrderItemList" :row-class-name="rowDouOrderItemIndex" @selection-change="handleDouOrderItemSelectionChange" ref="douOrderItem">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="抖音订单id" prop="orderId" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.orderId" placeholder="请输入抖音订单id" />
            </template>
          </el-table-column>
          <el-table-column label="子订单id" prop="subOrderId" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.subOrderId" placeholder="请输入子订单id" />
            </template>
          </el-table-column>
          <el-table-column label="订单所属商户id" prop="shopId" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.shopId" placeholder="请输入订单所属商户id" />
            </template>
          </el-table-column>
          <el-table-column label="该子订单购买的商品id" prop="productId" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.productId" placeholder="请输入该子订单购买的商品id" />
            </template>
          </el-table-column>
          <el-table-column label="商品名称" prop="productName" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.productName" placeholder="请输入商品名称" />
            </template>
          </el-table-column>
          <el-table-column label="该子订单购买的商品 sku_id" prop="comboId" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.comboId" placeholder="请输入该子订单购买的商品 sku_id" />
            </template>
          </el-table-column>
          <el-table-column label="商品编码" prop="goodsNum" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.goodsNum" placeholder="请输入商品编码" />
            </template>
          </el-table-column>
          <el-table-column label="该子订单购买的商品的编码 code" prop="specNum" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.specNum" placeholder="请输入该子订单购买的商品的编码 code" />
            </template>
          </el-table-column>
          <el-table-column label="该子订单所购买的sku的数量" prop="comboNum" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.comboNum" placeholder="请输入该子订单所购买的sku的数量" />
            </template>
          </el-table-column>
          <el-table-column label="邮费金额 (单位: 分)" prop="postAmount" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.postAmount" placeholder="请输入邮费金额 (单位: 分)" />
            </template>
          </el-table-column>
          <el-table-column label="平台优惠券金额 (单位: 分)" prop="couponAmount" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.couponAmount" placeholder="请输入平台优惠券金额 (单位: 分)" />
            </template>
          </el-table-column>
          <el-table-column label="优惠券id" prop="couponMetaId" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.couponMetaId" placeholder="请输入优惠券id" />
            </template>
          </el-table-column>
          <el-table-column label="优惠券详情 (type为优惠券类型, credit为优惠金额,单位分)" prop="couponInfo" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.couponInfo" placeholder="请输入优惠券详情 (type为优惠券类型, credit为优惠金额,单位分)" />
            </template>
          </el-table-column>
          <el-table-column label="活动细则 (活动可能会导致商品成交价combo_amount变成活动sku价格 ,活动campaign_info字段中的title为活动标题)" prop="campaignInfo" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.campaignInfo" placeholder="请输入活动细则 (活动可能会导致商品成交价combo_amount变成活动sku价格 ,活动campaign_info字段中的title为活动标题)" />
            </template>
          </el-table-column>
          <el-table-column label="该子订单总金额 (单位: 分)" prop="totalAmount" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.totalAmount" placeholder="请输入该子订单总金额 (单位: 分)" />
            </template>
          </el-table-column>
          <el-table-column label="是否评价 (1:已评价)" prop="isComment" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.isComment" placeholder="请输入是否评价 (1:已评价)" />
            </template>
          </el-table-column>
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
          <el-table-column label="商品单价" prop="price" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.price" placeholder="请输入商品单价" />
            </template>
          </el-table-column>
          <el-table-column label="是否赠品0否1是" prop="isGift" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.isGift" placeholder="请输入是否赠品0否1是" />
            </template>
          </el-table-column>
          <el-table-column label="子订单状态4已取消2已退货" prop="itemStatus" width="150">
            <template slot-scope="scope">
              <el-select v-model="scope.row.itemStatus" placeholder="请选择子订单状态4已取消2已退货">
                <el-option label="请选择字典生成" value="" />
              </el-select>
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
import { listOrder, getOrder, delOrder, addOrder, updateOrder } from "@/api/dou/order";

export default {
  name: "Order",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedDouOrderItem: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 抖店订单表格数据
      orderList: [],
      // 抖店订单明细表格数据
      douOrderItemList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderId: null,
        shopId: null,
        userName: null,
        postAddr: null,
        postCode: null,
        postReceiver: null,
        postTel: null,
        buyerWords: null,
        sellerWords: null,
        logisticsId: null,
        logisticsCode: null,
        logisticsCompany: null,
        logisticsTime: null,
        receiptTime: null,
        orderStatus: null,
        orderStatusStr: null,
        orderCreateTime: null,
        expShipTime: null,
        cancelReason: null,
        payType: null,
        payTypeName: null,
        payTime: null,
        postAmount: null,
        couponAmount: null,
        shopCouponAmount: null,
        couponInfo: null,
        orderTotalAmount: null,
        postInsuranceAmount: null,
        isComment: null,
        cType: null,
        bType: null,
        appSource: null,
        trafficeSource: null,
        cosRatio: null,
        createdTime: null,
        sendStatus: null,
        sendTime: null,
        auditStatus: null,
        encryptDetail: null,
        province: null,
        city: null,
        town: null,
        street: null,
        shipTime: null,
        tradeType: null,
        encryptPostTel: null,
        encryptPostReceiver: null,
        result: null,
        printStatus: null,
        printTime: null,
        phoneKey: null,
        addressKey: null,
        authorId: null,
        authorName: null,
        settlementStatus: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        orderId: [
          { required: true, message: "抖音订单id不能为空", trigger: "blur" }
        ],
        shopId: [
          { required: true, message: "订单所属商户id不能为空", trigger: "blur" }
        ],
        receiptTime: [
          { required: true, message: "收货时间不能为空", trigger: "blur" }
        ],
        orderStatus: [
          { required: true, message: "订单状态1 待确认/待支付不能为空", trigger: "change" }
        ],
        orderStatusStr: [
          { required: true, message: "$comment不能为空", trigger: "blur" }
        ],
        orderCreateTime: [
          { required: true, message: "订单创建时间不能为空", trigger: "blur" }
        ],
        payTypeName: [
          { required: true, message: "支付方式不能为空", trigger: "blur" }
        ],
        postAmount: [
          { required: true, message: "邮费金额 (单位: 分)不能为空", trigger: "blur" }
        ],
        couponAmount: [
          { required: true, message: "平台优惠券金额 (单位: 分)不能为空", trigger: "blur" }
        ],
        orderTotalAmount: [
          { required: true, message: "父订单总金额 (单位: 分) 即用户实际支付金额, 不包含运费不能为空", trigger: "blur" }
        ],
        postInsuranceAmount: [
          { required: true, message: "运费险金额不能为空", trigger: "blur" }
        ],
        createdTime: [
          { required: true, message: "创建时间不能为空", trigger: "blur" }
        ],
        updateTime: [
          { required: true, message: "更新时间不能为空", trigger: "blur" }
        ],
        sendStatus: [
          { required: true, message: "发货状态不能为空", trigger: "change" }
        ],
        auditStatus: [
          { required: true, message: "订单审核状态不能为空", trigger: "change" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询抖店订单列表 */
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
        orderId: null,
        shopId: null,
        userName: null,
        postAddr: null,
        postCode: null,
        postReceiver: null,
        postTel: null,
        buyerWords: null,
        sellerWords: null,
        logisticsId: null,
        logisticsCode: null,
        logisticsCompany: null,
        logisticsTime: null,
        receiptTime: null,
        orderStatus: null,
        orderStatusStr: null,
        orderCreateTime: null,
        expShipTime: null,
        cancelReason: null,
        payType: null,
        payTypeName: null,
        payTime: null,
        postAmount: null,
        couponAmount: null,
        shopCouponAmount: null,
        couponInfo: null,
        orderTotalAmount: null,
        postInsuranceAmount: null,
        isComment: null,
        cType: null,
        bType: null,
        appSource: null,
        trafficeSource: null,
        cosRatio: null,
        createdTime: null,
        updateTime: null,
        sendStatus: null,
        sendTime: null,
        auditStatus: null,
        encryptDetail: null,
        province: null,
        city: null,
        town: null,
        street: null,
        shipTime: null,
        tradeType: null,
        encryptPostTel: null,
        encryptPostReceiver: null,
        result: null,
        printStatus: null,
        printTime: null,
        phoneKey: null,
        addressKey: null,
        authorId: null,
        authorName: null,
        settlementStatus: null
      };
      this.douOrderItemList = [];
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
      this.title = "添加抖店订单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getOrder(id).then(response => {
        this.form = response.data;
        this.douOrderItemList = response.data.douOrderItemList;
        this.open = true;
        this.title = "修改抖店订单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.douOrderItemList = this.douOrderItemList;
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
      this.$modal.confirm('是否确认删除抖店订单编号为"' + ids + '"的数据项？').then(function() {
        return delOrder(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
	/** 抖店订单明细序号 */
    rowDouOrderItemIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** 抖店订单明细添加按钮操作 */
    handleAddDouOrderItem() {
      let obj = {};
      obj.orderId = "";
      obj.subOrderId = "";
      obj.shopId = "";
      obj.productId = "";
      obj.productName = "";
      obj.productPic = "";
      obj.comboId = "";
      obj.goodsNum = "";
      obj.specNum = "";
      obj.goodsSpec = "";
      obj.comboNum = "";
      obj.postAmount = "";
      obj.couponAmount = "";
      obj.couponMetaId = "";
      obj.couponInfo = "";
      obj.campaignInfo = "";
      obj.totalAmount = "";
      obj.isComment = "";
      obj.erpGoodsId = "";
      obj.erpSpecId = "";
      obj.remark = "";
      obj.price = "";
      obj.isGift = "";
      obj.itemStatus = "";
      this.douOrderItemList.push(obj);
    },
    /** 抖店订单明细删除按钮操作 */
    handleDeleteDouOrderItem() {
      if (this.checkedDouOrderItem.length == 0) {
        this.$modal.msgError("请先选择要删除的抖店订单明细数据");
      } else {
        const douOrderItemList = this.douOrderItemList;
        const checkedDouOrderItem = this.checkedDouOrderItem;
        this.douOrderItemList = douOrderItemList.filter(function(item) {
          return checkedDouOrderItem.indexOf(item.index) == -1
        });
      }
    },
    /** 复选框选中数据 */
    handleDouOrderItemSelectionChange(selection) {
      this.checkedDouOrderItem = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('dou/order/export', {
        ...this.queryParams
      }, `order_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
