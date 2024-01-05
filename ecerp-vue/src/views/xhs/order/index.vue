<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="订单号" prop="orderId">
        <el-input
          v-model="queryParams.orderId"
          placeholder="请输入订单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="店铺" prop="shopId">
        <!-- <el-input
          v-model="queryParams.shopId"
          placeholder="请输入店铺ID"
          clearable
          @keyup.enter.native="handleQuery"
        /> -->
        <el-select v-model="queryParams.shopId" placeholder="请选择店铺" clearable @change="handleQuery">
         <el-option
            v-for="item in shopList"
            :key="item.id"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="下单日期" prop="orderCreatedTime">
        <el-date-picker clearable
                        v-model="queryParams.orderCreatedTime"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择下单日期">
        </el-date-picker>
      </el-form-item>

      <el-form-item label="优先级" prop="sellerRemarkFlag">
        <el-input
          v-model="queryParams.sellerRemarkFlag"
          placeholder="请输入商家标记优先级，ark订单列表展示旗子颜色 1灰旗 2红旗 3黄旗 4绿旗 5蓝旗 6紫旗"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>


      <el-form-item label="快递单号" prop="expressTrackingNo">
        <el-input
          v-model="queryParams.expressTrackingNo"
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
          v-hasPermi="['xhs:order:add']"
        >手动添加</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-upload"
          size="mini"
          @click="handleUpdate"
          v-hasPermi="['xhs:order:edit']"
        >Execl导入</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-download"
          size="mini"

          @click="handleDelete"
          v-hasPermi="['xhs:order:remove']"
        >API拉取订单</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['xhs:order:export']"
        >导出订单</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="orderList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
<!--      <el-table-column label="主键id" align="center" prop="id" />-->
      <el-table-column label="订单号" align="center" prop="orderId" />
      <el-table-column label="店铺" align="center" prop="shopId" >
       <template slot-scope="scope">
          <span v-if="scope.row.shopId==21">珍姐姐de衣柜的店</span>
        </template>
      </el-table-column>
      <el-table-column label="商品" width="350">
          <template slot-scope="scope">
            <el-row v-for="item in scope.row.xhsOrderItemList" :key="item.id" :gutter="20">
              
            <div style="float: left;display: flex;align-items: center;" >
              <el-image  style="width: 70px; height: 70px;" :src="item.itemImage"></el-image>
              <div style="margin-left:10px">
              <p>{{item.itemName}}</p>
              <p>{{item.itemSpec}}&nbsp;
                <el-tag size="small">x {{item.quantity}}</el-tag>
                </p>
              </div>
            </div>
            </el-row>
          </template>
      </el-table-column>
<!--      <el-table-column label="订单来源" align="center" prop="shopType" />-->
      <!-- <el-table-column label="店铺" align="center" prop="shopId" /> -->
<!--      <el-table-column label="订单类型" align="center" prop="orderType" />-->
<el-table-column label="实付金额" align="center" prop="totalPayAmount" />
      <el-table-column label="运费" align="center" prop="totalShippingFree" />
      <el-table-column label="小红书订单状态" align="center" prop="orderStatus" >
        <!-- 小红书订单状态，1已下单待付款 2已支付处理中 3清关中 4待发货 5部分发货 6待收货 7已完成 8已关闭 9已取消 10换货申请中 -->
        <template slot-scope="scope">
          
          <el-tag size="small" v-if="scope.row.orderStatus === 1"> 待支付</el-tag>
          <el-tag size="small" v-if="scope.row.orderStatus === 2"> 已支付</el-tag>
          <el-tag size="small" v-if="scope.row.orderStatus === 3"> 清关中</el-tag>
          <el-tag size="small" v-if="scope.row.orderStatus === 4"> 待发货</el-tag>
          <el-tag size="small" v-if="scope.row.orderStatus === 5"> 部分发货</el-tag>
           <el-tag size="small" v-if="scope.row.orderStatus === 6"> 待收货</el-tag>
            <el-tag size="small" v-if="scope.row.orderStatus === 7"> 已完成</el-tag>
             <el-tag size="small" v-if="scope.row.orderStatus === 8"> 已关闭</el-tag>
              <el-tag size="small" v-if="scope.row.orderStatus === 9"> 已取消</el-tag>
               <el-tag size="small" v-if="scope.row.orderStatus === 10"> 换货申请中</el-tag>
          <span></span>

          <el-tag size="small" v-if="scope.row.auditStatus === 0" style="margin-top: 5px;"> 待确认</el-tag>
          <el-tag size="small" v-if="scope.row.auditStatus === 1" style="margin-top: 5px;"> 已确认</el-tag>
          <el-tag size="small" v-if="scope.row.auditStatus === 2" style="margin-top: 5px;"> 已拦截</el-tag>

        </template>
      </el-table-column>
      <!-- <el-table-column label="小红书售后状态" align="center" prop="afterSalesStatus" /> -->
      <!-- <el-table-column label="申请取消状态" align="center" prop="cancelStatus" /> -->
      <el-table-column label="下单时间" align="center" prop="orderCreatedTime" >
        <template slot-scope="scope">
          <span>{{ dateToString(scope.row.orderCreatedTime) }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="订单支付时间 单位ms" align="center" prop="orderPaidTime" />
      <el-table-column label="订单更新时间 单位ms" align="center" prop="orderUpdateTime" />
      <el-table-column label="订单发货时间 单位ms" align="center" prop="orderDeliveryTime" />
      <el-table-column label="订单取消时间 单位ms" align="center" prop="orderCancelTime" />
      <el-table-column label="订单完成时间 单位ms" align="center" prop="orderFinishTime" />
      <el-table-column label="承诺最晚发货时间 单位ms" align="center" prop="promiseLastDeliveryTime" /> -->
      <el-table-column label="用户备注" align="center" prop="customerRemark" />
      <el-table-column label="商家标记备注" align="center" prop="sellerRemark" />
      <el-table-column label="商家标记优先级" align="center" prop="sellerRemarkFlag" />
      <!-- <el-table-column label="预售最早发货时间 单位ms" align="center" prop="presaleDeliveryStartTime" />
      <el-table-column label="预售最晚发货时间 单位ms" align="center" prop="presaleDeliveryEndTime" /> -->
<!--      <el-table-column label="原始关联订单号(退换订单的原订单)" align="center" prop="originalPackageId" />-->
      
      <el-table-column label="快递单号" align="center" prop="expressTrackingNo" />
<!--      <el-table-column label="快递公司编码" align="center" prop="expressCompanyCode" />-->
<!--      <el-table-column label="收件人姓名+手机+地址等计算得出，用来查询收件人详情" align="center" prop="openAddressId" />-->
      <!-- <el-table-column label="省" align="center" prop="province" /> -->
      <!-- <el-table-column label="市" align="center" prop="city" /> -->
<!--      <el-table-column label="区县" align="center" prop="district" />-->
<!--      <el-table-column label="订单审核状态" align="center" prop="auditStatus" />-->
<!--      <el-table-column label="订单审核时间" align="center" prop="auditTime" width="180">-->
<!--        <template slot-scope="scope">-->
<!--          <span>{{ // parseTime(scope.row.auditTime, '{y}-{m}-{d}') }}</span>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--      <el-table-column label="结算状态0未结算1已结算" align="center" prop="settleStatus" />-->
<!--      <el-table-column label="结算金额" align="center" prop="settleAmount" />-->
<!--      <el-table-column label="ERP发货状态0待处理1出库中2已出库3已发货" align="center" prop="sendStatus" />-->
<!--      <el-table-column label="发货时间" align="center" prop="sendTime" width="180">-->
<!--        <template slot-scope="scope">-->
<!--          <span>{{ parseTime(scope.row.sendTime, '{y}-{m}-{d}') }}</span>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--      <el-table-column label="创建时间" align="center" prop="createTime" width="180">-->
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
            v-hasPermi="['xhs:order:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['xhs:order:remove']"
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

    <!-- 添加或修改小红书订单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1100px" append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="180px" inline>
        <el-form-item label="订单号" prop="orderId">
          <el-input v-model="form.orderId" placeholder="请输入订单号"  style="width:250px" :disabled="isAudit"/>
        </el-form-item>
        <el-form-item label="店铺" prop="shopId">
          <!-- <el-input v-model="form.shopId" placeholder="请输入店铺ID" /> -->
          <el-select v-model="form.shopId" placeholder="请选择店铺" style="width:250px" :disabled="isAudit">
           <el-option style="width:250px"
              v-for="item in shopList"
              :key="item.id"
              :label="item.name"
              :value="item.id" >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="订单创建时间" prop="orderCreatedTime">
          <!-- <el-input v-model="form.orderCreatedTime" placeholder="请输入订单创建时间 单位ms" /> -->
           <el-date-picker clearable style="width:250px"
            v-model="form.orderCreatedTime"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="请选择订单创建时间">
          </el-date-picker>
        </el-form-item>
        
        <el-form-item label="用户备注" prop="customerRemark">
          <el-input v-model="form.customerRemark" placeholder="请输入用户备注" />
        </el-form-item>
        <el-form-item label="商家标记备注" prop="sellerRemark">
          <el-input v-model="form.sellerRemark" placeholder="请输入商家标记备注" />
        </el-form-item>
        
        <el-form-item label="订单实付金额(包含运费) 单位分" prop="totalPayAmount">
          <el-input v-model="form.totalPayAmount" placeholder="请输入订单实付金额(包含运费) 单位分" />
        </el-form-item>
        <el-form-item label="订单运费 单位分" prop="totalShippingFree">
          <el-input v-model="form.totalShippingFree" placeholder="请输入订单运费 单位分" />
        </el-form-item>
        
        <!-- <el-form-item label="收件人姓名+手机+地址等计算得出，用来查询收件人详情" prop="openAddressId">
          <el-input v-model="form.openAddressId" placeholder="请输入收件人姓名+手机+地址等计算得出，用来查询收件人详情" />
        </el-form-item>
        <el-form-item label="省" prop="province">
          <el-input v-model="form.province" placeholder="请输入省" />
        </el-form-item>
        <el-form-item label="市" prop="city">
          <el-input v-model="form.city" placeholder="请输入市" />
        </el-form-item>
        <el-form-item label="区县" prop="district">
          <el-input v-model="form.district" placeholder="请输入区县" />
        </el-form-item>
     -->
        <el-form-item label="收件人姓名" prop="receiver">
          <el-input v-model="form.receiver" placeholder="请输入收件人姓名" style="width:250px"/>
        </el-form-item>
        <el-form-item label="收件人电话" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入收件人电话" style="width:250px"/>
        </el-form-item>
        <el-form-item label="省市区" prop="provinces">
          <el-cascader style="width:250px"
            size="large"
            :options="pcaTextArr"
            v-model="form.provinces">
          </el-cascader>
        </el-form-item>
        <el-form-item label="详细地址" prop="postAddr">
          <el-input v-model="form.address" placeholder="请输入详细地址" style="width:250px" />
        </el-form-item>

        <el-divider content-position="center">小红书订单明细信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddXhsOrderItem">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteXhsOrderItem">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="xhsOrderItemList" :row-class-name="rowXhsOrderItemIndex" @selection-change="handleXhsOrderItemSelectionChange" ref="xhsOrderItem">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="商品" prop="erpGoodsId" width="350" v-if="!isAudit" >
            <template slot-scope="scope">
              <el-select v-model="scope.row.erpGoodsSpecId" filterable remote reserve-keyword placeholder="搜索商品" style="width: 330px;"
                :remote-method="searchSku" :loading="skuListLoading" @change="skuChanage(scope.row)">
                <el-option v-for="item in skuList" :key="item.id"
                  :label="item.name + ' - ' + item.colorValue + ' ' + item.sizeValue + ' ' + item.styleValue"
                  :value="item.id">
                </el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="商品图片" prop="productImgUrl" >
            <template slot-scope="scope">
              <el-image style="width: 70px; height: 70px" :src="scope.row.productImgUrl"></el-image>
            </template>
          </el-table-column>
          <el-table-column label="商品id" prop="itemId" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.itemId" placeholder="请输入商品id" />
            </template>
          </el-table-column>
          <el-table-column label="商品名称" prop="itemName" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.itemName" placeholder="请输入商品名称" />
            </template>
          </el-table-column>
          <el-table-column label="商家编码(若为组合品，暂不支持组合品的商家编码，但skulist会返回子商品商家编码)" prop="erpcode" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.erpcode" placeholder="请输入商家编码(若为组合品，暂不支持组合品的商家编码，但skulist会返回子商品商家编码)" />
            </template>
          </el-table-column>
          <el-table-column label="规格" prop="itemSpec" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.itemSpec" placeholder="请输入规格" />
            </template>
          </el-table-column>
          <el-table-column label="数量" prop="quantity" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.quantity" placeholder="请输入数量" />
            </template>
          </el-table-column>
          <el-table-column label="总支付金额" prop="totalPaidAmount" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.totalPaidAmount" placeholder="请输入总支付金额" />
            </template>
          </el-table-column>
          <el-table-column label="商家承担总优惠" prop="totalMerchantDiscount" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.totalMerchantDiscount" placeholder="请输入商家承担总优惠" />
            </template>
          </el-table-column>
          <el-table-column label="平台承担总优惠" prop="totalRedDiscount" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.totalRedDiscount" placeholder="请输入平台承担总优惠" />
            </template>
          </el-table-column>
          <el-table-column label="是否赠品，1 赠品 0 普通商品" prop="itemTag" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.itemTag" placeholder="请输入是否赠品，1 赠品 0 普通商品" />
            </template>
          </el-table-column>
          <el-table-column label="ERP发货状态0待处理1出库中2已出库3已发货" prop="erpSendStatus" width="150">
            <template slot-scope="scope">
              <el-select v-model="scope.row.erpSendStatus" placeholder="请选择ERP发货状态0待处理1出库中2已出库3已发货">
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
import { listOrder, getOrder, delOrder, addOrder, updateOrder } from "@/api/xhs/order";
import { listShop } from "@/api/shop/shop";
import { searchSku } from "@/api/goods/goods";
import {
  provinceAndCityData,
  pcTextArr,
  regionData,
  pcaTextArr,
  codeToText,
} from "element-china-area-data";
export default {
  name: "Order",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedXhsOrderItem: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 小红书订单表格数据
      orderList: [],
      // 小红书订单明细表格数据
      xhsOrderItemList: [],
      shopList:[],
      isAudit:false,
      skuListLoading:false,
      skuList:[],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderId: null,
        shopType: null,
        shopId: null,
        orderType: null,
        orderStatus: null,
        afterSalesStatus: null,
        cancelStatus: null,
        orderCreatedTime: null,
        orderPaidTime: null,
        orderUpdateTime: null,
        orderDeliveryTime: null,
        orderCancelTime: null,
        orderFinishTime: null,
        promiseLastDeliveryTime: null,
        customerRemark: null,
        sellerRemark: null,
        sellerRemarkFlag: null,
        presaleDeliveryStartTime: null,
        presaleDeliveryEndTime: null,
        originalPackageId: null,
        totalPayAmount: null,
        totalShippingFree: null,
        expressTrackingNo: null,
        expressCompanyCode: null,
        openAddressId: null,
        province: null,
        city: null,
        district: null,
        auditStatus: null,
        auditTime: null,
        settleStatus: null,
        settleAmount: null,
        sendStatus: null,
        sendTime: null,
        createTime: null,
        createBy: null,
        updateTime: null,
        updateBy: null
      },
      // 表单参数
      // 表单参数
      form: {
        provinces: [],
        address:null,
        receiver:null,
        phone:null,
        province:null,
        city:null,
        town:null
      },
      pcaTextArr,
      // 表单校验
      rules: {
        orderId: [
          { required: true, message: "订单号不能为空", trigger: "blur" }
        ],
        shopType: [
          { required: true, message: "订单来源不能为空", trigger: "change" }
        ],
        shopId: [
          { required: true, message: "店铺ID不能为空", trigger: "blur" }
        ],
        orderType: [
          { required: true, message: "订单类型：不能为空", trigger: "change" }
        ],
        orderStatus: [
          { required: true, message: "小红书订单状态，1已下单待付款 2已支付处理中 3清关中 4待发货 5部分发货 6待收货 7已完成 8已关闭 9已取消 10换货申请中不能为空", trigger: "change" }
        ],
        afterSalesStatus: [
          { required: true, message: "小红书售后状态，1无售后 2售后处理中 3售后完成(含取消)不能为空", trigger: "change" }
        ],
        cancelStatus: [
          { required: true, message: "申请取消状态，0未申请取消 1取消处理中不能为空", trigger: "change" }
        ],
        orderCreatedTime: [
          { required: true, message: "订单创建时间 单位ms不能为空", trigger: "blur" }
        ],
        orderPaidTime: [
          { required: true, message: "订单支付时间 单位ms不能为空", trigger: "blur" }
        ],
        orderUpdateTime: [
          { required: true, message: "订单更新时间 单位ms不能为空", trigger: "blur" }
        ],
        orderDeliveryTime: [
          { required: true, message: "订单发货时间 单位ms不能为空", trigger: "blur" }
        ],
        orderCancelTime: [
          { required: true, message: "订单取消时间 单位ms不能为空", trigger: "blur" }
        ],
        orderFinishTime: [
          { required: true, message: "订单完成时间 单位ms不能为空", trigger: "blur" }
        ],
        promiseLastDeliveryTime: [
          { required: true, message: "承诺最晚发货时间 单位ms不能为空", trigger: "blur" }
        ],
        sellerRemarkFlag: [
          { required: true, message: "商家标记优先级，ark订单列表展示旗子颜色 1灰旗 2红旗 3黄旗 4绿旗 5蓝旗 6紫旗不能为空", trigger: "blur" }
        ],
        presaleDeliveryStartTime: [
          { required: true, message: "预售最早发货时间 单位ms不能为空", trigger: "blur" }
        ],
        presaleDeliveryEndTime: [
          { required: true, message: "预售最晚发货时间 单位ms不能为空", trigger: "blur" }
        ],
        totalPayAmount: [
          { required: true, message: "订单实付金额(包含运费) 单位分不能为空", trigger: "blur" }
        ],
        totalShippingFree: [
          { required: true, message: "订单运费 单位分不能为空", trigger: "blur" }
        ],
        openAddressId: [
          { required: true, message: "收件人姓名+手机+地址等计算得出，用来查询收件人详情不能为空", trigger: "blur" }
        ],
        province: [
          { required: true, message: "省不能为空", trigger: "blur" }
        ],
        city: [
          { required: true, message: "市不能为空", trigger: "blur" }
        ],
        district: [
          { required: true, message: "区县不能为空", trigger: "blur" }
        ],
        settleStatus: [
          { required: true, message: "结算状态0未结算1已结算不能为空", trigger: "change" }
        ],
        settleAmount: [
          { required: true, message: "结算金额不能为空", trigger: "blur" }
        ],
        sendStatus: [
          { required: true, message: "ERP发货状态0待处理1出库中2已出库3已发货不能为空", trigger: "change" }
        ],
        createTime: [
          { required: true, message: "创建时间不能为空", trigger: "blur" }
        ],
        updateTime: [
          { required: true, message: "更新时间不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    listShop({type:7}).then(response => {
        this.shopList = response.rows;
      });
    this.getList();
  },
  methods: {
    dateToString(timespan){
            var date = new Date(timespan);
            return date.toLocaleString();
        },
    /** 查询小红书订单列表 */
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
        shopType: null,
        shopId: null,
        orderType: null,
        orderStatus: null,
        afterSalesStatus: null,
        cancelStatus: null,
        orderCreatedTime: null,
        orderPaidTime: null,
        orderUpdateTime: null,
        orderDeliveryTime: null,
        orderCancelTime: null,
        orderFinishTime: null,
        promiseLastDeliveryTime: null,
        customerRemark: null,
        sellerRemark: null,
        sellerRemarkFlag: null,
        presaleDeliveryStartTime: null,
        presaleDeliveryEndTime: null,
        originalPackageId: null,
        totalPayAmount: null,
        totalShippingFree: null,
        expressTrackingNo: null,
        expressCompanyCode: null,
        openAddressId: null,
        province: null,
        city: null,
        district: null,
        auditStatus: null,
        auditTime: null,
        settleStatus: null,
        settleAmount: null,
        sendStatus: null,
        sendTime: null,
        createTime: null,
        createBy: null,
        updateTime: null,
        updateBy: null
      };
      this.xhsOrderItemList = [];
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
      this.title = "添加小红书订单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getOrder(id).then(response => {
        this.form = response.data;
        this.xhsOrderItemList = response.data.xhsOrderItemList;
        this.open = true;
        this.title = "修改小红书订单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.xhsOrderItemList = this.xhsOrderItemList;
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
      this.$modal.confirm('是否确认删除小红书订单编号为"' + ids + '"的数据项？').then(function() {
        return delOrder(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
	/** 小红书订单明细序号 */
    rowXhsOrderItemIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** 小红书订单明细添加按钮操作 */
    handleAddXhsOrderItem() {
      let obj = {};
      obj.itemId = "";
      obj.itemName = "";
      obj.erpcode = "";
      obj.itemSpec = "";
      obj.itemImage = "";
      obj.quantity = "";
      obj.totalPaidAmount = "";
      obj.totalMerchantDiscount = "";
      obj.totalRedDiscount = "";
      obj.itemTag = "";
      obj.erpSendStatus = "";
      this.xhsOrderItemList.push(obj);
    },
    /** 小红书订单明细删除按钮操作 */
    handleDeleteXhsOrderItem() {
      if (this.checkedXhsOrderItem.length == 0) {
        this.$modal.msgError("请先选择要删除的小红书订单明细数据");
      } else {
        const xhsOrderItemList = this.xhsOrderItemList;
        const checkedXhsOrderItem = this.checkedXhsOrderItem;
        this.xhsOrderItemList = xhsOrderItemList.filter(function(item) {
          return checkedXhsOrderItem.indexOf(item.index) == -1
        });
      }
    },
    /** 复选框选中数据 */
    handleXhsOrderItemSelectionChange(selection) {
      this.checkedXhsOrderItem = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('xhs/order/export', {
        ...this.queryParams
      }, `order_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
