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
          v-hasPermi="['shop:order:edit']"
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
          v-hasPermi="['shop:order:remove']"
        >删除</el-button>
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
      <el-table-column label="折扣金额，单位：元" align="center" prop="discountAmount" />
      <el-table-column label="商品金额，单位：元" align="center" prop="goodsAmount" />
      <el-table-column label="支付金额，单位：元" align="center" prop="payAmount" />
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
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['shop:order:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['shop:order:remove']"
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

    <!-- 添加或修改店铺订单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="订单编号" prop="orderNum">
          <el-input v-model="form.orderNum" placeholder="请输入订单编号" />
        </el-form-item>
        <el-form-item label="内部店铺ID" prop="shopId">
          <el-input v-model="form.shopId" placeholder="请输入内部店铺ID" />
        </el-form-item>
        <el-form-item label="订单备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="买家留言信息" prop="buyerMemo">
          <el-input v-model="form.buyerMemo" placeholder="请输入买家留言信息" />
        </el-form-item>
        <el-form-item label="标签" prop="tag">
          <el-input v-model="form.tag" placeholder="请输入标签" />
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
        <el-form-item label="支付时间" prop="payTime">
          <el-input v-model="form.payTime" placeholder="请输入支付时间" />
        </el-form-item>
        <el-form-item label="${comment}" prop="receiverName">
          <el-input v-model="form.receiverName" placeholder="请输入${comment}" />
        </el-form-item>
        <el-form-item label="${comment}" prop="receiverPhone">
          <el-input v-model="form.receiverPhone" placeholder="请输入${comment}" />
        </el-form-item>
        <el-form-item label="${comment}" prop="address">
          <el-input v-model="form.address" placeholder="请输入${comment}" />
        </el-form-item>
        <el-form-item label="${comment}" prop="town">
          <el-input v-model="form.town" placeholder="请输入${comment}" />
        </el-form-item>
        <el-form-item label="${comment}" prop="city">
          <el-input v-model="form.city" placeholder="请输入${comment}" />
        </el-form-item>
        <el-form-item label="${comment}" prop="province">
          <el-input v-model="form.province" placeholder="请输入${comment}" />
        </el-form-item>
        <el-form-item label="${comment}" prop="country">
          <el-input v-model="form.country" placeholder="请输入${comment}" />
        </el-form-item>
        <el-form-item label="订单审核时间" prop="auditTime">
          <el-input v-model="form.auditTime" placeholder="请输入订单审核时间" />
        </el-form-item>
        <el-form-item label="发货时间" prop="shippingTime">
          <el-input v-model="form.shippingTime" placeholder="请输入发货时间" />
        </el-form-item>
        <el-form-item label="快递单号" prop="shippingNumber">
          <el-input v-model="form.shippingNumber" placeholder="请输入快递单号" />
        </el-form-item>
        <el-form-item label="物流公司" prop="shippingCompany">
          <el-input v-model="form.shippingCompany" placeholder="请输入物流公司" />
        </el-form-item>
        <el-divider content-position="center">${subTable.functionName}信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddSShopOrderItem">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteSShopOrderItem">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="sShopOrderItemList" :row-class-name="rowSShopOrderItemIndex" @selection-change="handleSShopOrderItemSelectionChange" ref="sShopOrderItem">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="erp系统商品id" prop="goodsId" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.goodsId" placeholder="请输入erp系统商品id" />
            </template>
          </el-table-column>
          <el-table-column label="erp系统商品规格id" prop="specId" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.specId" placeholder="请输入erp系统商品规格id" />
            </template>
          </el-table-column>
          <el-table-column label="商品标题" prop="goodsTitle" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.goodsTitle" placeholder="请输入商品标题" />
            </template>
          </el-table-column>
          <el-table-column label="商品图片" prop="goodsImg" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.goodsImg" placeholder="请输入商品图片" />
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
          <el-table-column label="是否赠品0否1是" prop="isGift" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.isGift" placeholder="请输入是否赠品0否1是" />
            </template>
          </el-table-column>
          <el-table-column label="已退货数量" prop="refundCount" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.refundCount" placeholder="请输入已退货数量" />
            </template>
          </el-table-column>
          <el-table-column label="售后状态 1：无售后或售后关闭，2：售后处理中，3：退款中，4： 退款成功 " prop="refundStatus" width="150">
            <template slot-scope="scope">
              <el-select v-model="scope.row.refundStatus" placeholder="请选择售后状态 1：无售后或售后关闭，2：售后处理中，3：退款中，4： 退款成功 ">
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
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        orderNum: null,
        shopId: null,
        remark: null,
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
        createTime: null,
        createBy: null,
        updateBy: null,
        updateTime: null
      };
      this.sShopOrderItemList = [];
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
      this.title = "添加店铺订单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getOrder(id).then(response => {
        this.form = response.data;
        this.sShopOrderItemList = response.data.sShopOrderItemList;
        this.open = true;
        this.title = "修改店铺订单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.sShopOrderItemList = this.sShopOrderItemList;
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
      this.$modal.confirm('是否确认删除店铺订单编号为"' + ids + '"的数据项？').then(function() {
        return delOrder(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
	/** ${subTable.functionName}序号 */
    rowSShopOrderItemIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** ${subTable.functionName}添加按钮操作 */
    handleAddSShopOrderItem() {
      let obj = {};
      obj.goodsId = "";
      obj.specId = "";
      obj.goodsTitle = "";
      obj.goodsImg = "";
      obj.goodsNum = "";
      obj.goodsSpec = "";
      obj.specNum = "";
      obj.goodsPrice = "";
      obj.itemAmount = "";
      obj.quantity = "";
      obj.remark = "";
      obj.isGift = "";
      obj.refundCount = "";
      obj.refundStatus = "";
      this.sShopOrderItemList.push(obj);
    },
    /** ${subTable.functionName}删除按钮操作 */
    handleDeleteSShopOrderItem() {
      if (this.checkedSShopOrderItem.length == 0) {
        this.$modal.msgError("请先选择要删除的${subTable.functionName}数据");
      } else {
        const sShopOrderItemList = this.sShopOrderItemList;
        const checkedSShopOrderItem = this.checkedSShopOrderItem;
        this.sShopOrderItemList = sShopOrderItemList.filter(function(item) {
          return checkedSShopOrderItem.indexOf(item.index) == -1
        });
      }
    },
    /** 复选框选中数据 */
    handleSShopOrderItemSelectionChange(selection) {
      this.checkedSShopOrderItem = selection.map(item => item.index)
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
