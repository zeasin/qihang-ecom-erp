<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="订单编号" prop="orderNum">
        <el-input
          v-model="queryParams.orderNum"
          placeholder="请输入订单编号"
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
      
      <el-form-item label="标签" prop="tag">
        <el-input
          v-model="queryParams.tag"
          placeholder="请输入标签"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
     
      <el-form-item label="收件人" prop="receiverName">
        <el-input
          v-model="queryParams.receiverName"
          placeholder="请输入收件人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="手机号" prop="receiverPhone">
        <el-input
          v-model="queryParams.receiverPhone"
          placeholder="请输入手机号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
     
      <el-form-item label="城市" prop="city">
        <el-input
          v-model="queryParams.city"
          placeholder="请输入城市"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="省份" prop="province">
        <el-input
          v-model="queryParams.province"
          placeholder="请输入省份"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      
      <el-form-item label="快递单号" prop="shippingNumber">
        <el-input
          v-model="queryParams.shippingNumber"
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
          v-hasPermi="['shop:order:add']"
        >手动创建订单</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['shop:order:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="orderList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="订单ID" align="center" prop="id" />
      <el-table-column label="订单编号" align="center" prop="orderNum" />
      <el-table-column label="店铺" align="center" prop="shopId" />
      <el-table-column label="订单备注" align="center" prop="remark" />
      <el-table-column label="买家留言信息" align="center" prop="buyerMemo" />
      <el-table-column label="标签" align="center" prop="tag" />
      <el-table-column label="售后状态" align="center" prop="refundStatus" />
      <el-table-column label="订单状态" align="center" prop="orderStatus" />
      <!-- <el-table-column label="邮费，单位：元" align="center" prop="postage" /> -->
      <el-table-column label="折扣金额(元)" align="center" prop="discountAmount" />
      <el-table-column label="商品金额(元)" align="center" prop="goodsAmount" />
      <el-table-column label="支付金额" align="center" prop="payAmount" />
      <!-- <el-table-column label="支付时间" align="center" prop="payTime" /> -->
      <el-table-column label="收件人" align="center" prop="receiverName" />
      <el-table-column label="手机号" align="center" prop="receiverPhone" />
      <!-- <el-table-column label="${comment}" align="center" prop="address" /> -->
      <!-- <el-table-column label="${comment}" align="center" prop="town" /> -->
      <el-table-column label="省" align="center" prop="province" />
      <el-table-column label="市" align="center" prop="city" />
      
    
      <el-table-column label="订单审核时间" align="center" prop="auditTime" />
      <el-table-column label="订单审核状态" align="center" prop="auditStatus" />
      <el-table-column label="发货时间" align="center" prop="shippingTime" />
      <el-table-column label="快递单号" align="center" prop="shippingNumber" />
      <!-- <el-table-column label="物流公司" align="center" prop="shippingCompany" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleDetail(scope.row)"
            v-hasPermi="['shop:order:edit']"
          >详情</el-button>
          
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

    
  </div>
</template>

<script>
import { listOrder, getOrder, delOrder, addOrder, updateOrder } from "@/api/shop/order";

export default {
  name: "Order",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedSShopOrderItem: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 店铺订单表格数据
      orderList: [],
      // ${subTable.functionName}表格数据
      sShopOrderItemList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderNum: null,
        shopId: null,
        buyerMemo: null,
        tag: null,
        refundStatus: null,
        orderStatus: null,
        postage: null,
        discountAmount: null,
        goodsAmount: null,
        payAmount: null,
        payTime: null,
        receiverName: null,
        receiverPhone: null,
        address: null,
        town: null,
        city: null,
        province: null,
        country: null,
        auditTime: null,
        auditStatus: null,
        shippingTime: null,
        shippingNumber: null,
        shippingCompany: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        orderNum: [
          { required: true, message: "订单编号不能为空", trigger: "blur" }
        ],
        shopId: [
          { required: true, message: "内部店铺ID不能为空", trigger: "blur" }
        ],
        refundStatus: [
          { required: true, message: "售后状态 1：无售后或售后关闭，2：售后处理中，3：退款中，4： 退款成功 5：全部不能为空", trigger: "change" }
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
        auditStatus: [
          { required: true, message: "订单审核状态：0待确认，1已确认2已拦截-9未拉取不能为空", trigger: "change" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询店铺订单列表 */
    getList() {
      this.loading = true;
      listOrder(this.queryParams).then(response => {
        this.orderList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
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
      this.$router.push('/shop/order/create');
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('shop/order/export', {
        ...this.queryParams
      }, `order_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
