<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="供应商" prop="contactId">
        <el-select
          v-model="queryParams.contactId"
          filterable
          remote
          reserve-keyword
          placeholder="请输入供应商名称"
          :remote-method="searchSupplier"
          :loading="supplierLoading">
          <el-option
            v-for="item in supplierList"
            :key="item.id"
            :label="item.name"
            :value="item.id">
          </el-option>  
        </el-select>
        <!-- <el-input
          v-model="queryParams.contactId"
          placeholder="请输入供应商id"
          clearable
          @keyup.enter.native="handleQuery"
        /> -->
      </el-form-item>
      <el-form-item label="订单编号" prop="orderNo">
        <el-input
          v-model="queryParams.orderNo"
          placeholder="请输入订单编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="订单日期" prop="orderDate">
        <el-date-picker clearable
          v-model="queryParams.orderDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择订单日期">
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
          v-hasPermi="['purchase:purchaseOrder:add']"
        >创建采购订单</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['purchase:purchaseOrder:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="purchaseOrderList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" />
      <el-table-column label="供应商" align="center" prop="contactId" />
      <el-table-column label="订单编号" align="center" prop="orderNo" />
      <el-table-column label="订单日期" align="center" prop="orderDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.orderDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>

      <el-table-column label="订单总金额" align="center" prop="orderAmount" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="审核人" align="center" prop="auditUser" />
      <el-table-column label="审核时间" align="center" prop="auditTime" />
      <el-table-column label="供应商确认时间" align="center" prop="supplierConfirmTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.supplierConfirmTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="供应商发货时间" align="center" prop="supplierDeliveryTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.supplierDeliveryTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="收货时间" align="center" prop="receivedTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.receivedTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="入库时间" align="center" prop="stockInTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.stockInTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-col :span="24">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-circle-check"
            @click="handleUpdateStatus(scope.row)"
            v-hasPermi="['purchase:purchaseOrder:edit']"
          >审核</el-button>
        </el-col>
        <el-col :span="24">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-finished"
            @click="handleUpdateStatus(scope.row)"
            v-hasPermi="['purchase:purchaseOrder:edit']"
          >生成合同</el-button>
        </el-col>
        <el-col :span="24">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-finished"
            @click="handleUpdateStatus(scope.row)"
            v-hasPermi="['purchase:purchaseOrder:edit']"
          >供应商确认</el-button>
        </el-col>
        <el-col :span="24">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-finished"
            @click="handleUpdateStatus(scope.row)"
            v-hasPermi="['purchase:purchaseOrder:edit']"
          >供应商发货</el-button>
        </el-col>
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

    <!-- 添加或修改采购订单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">

        <el-form-item label="订单编号" prop="orderNo">
          <el-input v-model="form.orderNo" placeholder="请输入订单编号" />
        </el-form-item>
        <el-form-item label="订单日期" prop="orderDate">
          <el-date-picker clearable
            v-model="form.orderDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择订单日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="订单创建时间" prop="orderTime">
          <el-input v-model="form.orderTime" placeholder="请输入订单创建时间" />
        </el-form-item>
        <el-form-item label="订单总金额" prop="orderAmount">
          <el-input v-model="form.orderAmount" placeholder="请输入订单总金额" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" />
        </el-form-item>
        <el-form-item label="采购单审核人" prop="auditUser">
          <el-input v-model="form.auditUser" placeholder="请输入采购单审核人" />
        </el-form-item>
        <el-form-item label="审核时间" prop="auditTime">
          <el-input v-model="form.auditTime" placeholder="请输入审核时间" />
        </el-form-item>
        <el-form-item label="供应商确认时间" prop="supplierConfirmTime">
          <el-date-picker clearable
            v-model="form.supplierConfirmTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择供应商确认时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="供应商发货时间" prop="supplierDeliveryTime">
          <el-date-picker clearable
            v-model="form.supplierDeliveryTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择供应商发货时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="收货时间" prop="receivedTime">
          <el-date-picker clearable
            v-model="form.receivedTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择收货时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="入库时间" prop="stockInTime">
          <el-date-picker clearable
            v-model="form.stockInTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择入库时间">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listPurchaseOrder, getPurchaseOrder, delPurchaseOrder, addPurchaseOrder, updatePurchaseOrder } from "@/api/purchase/purchaseOrder";
import { listSupplier} from "@/api/scm/supplier";
export default {
  name: "PurchaseOrder",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 采购订单表格数据
      purchaseOrderList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        contactId: null,
        orderNo: null,
        orderDate: null,
        orderTime: null,
        orderAmount: null,
        status: null,
        auditUser: null,
        auditTime: null,
        supplierConfirmTime: null,
        supplierDeliveryTime: null,
        receivedTime: null,
        stockInTime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      supplierLoading:false,
      supplierList:[]
    };
  },
  created() {
    this.getList();
  },
  methods: {
    searchSupplier(query){
      this.supplierLoading = true;
      const qw = {
        name:query
      }
      listSupplier(qw).then(response => {
        this.supplierList = response.rows;
        this.supplierLoading = false;
      });
    },
    /** 查询采购订单列表 */
    getList() {
      this.loading = true;
      listPurchaseOrder(this.queryParams).then(response => {
        this.purchaseOrderList = response.rows;
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
        contactId: null,
        orderNo: null,
        orderDate: null,
        orderTime: null,
        orderAmount: null,
        remark: null,
        status: null,
        auditUser: null,
        auditTime: null,
        supplierConfirmTime: null,
        supplierDeliveryTime: null,
        receivedTime: null,
        stockInTime: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      };
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
      this.$router.push('/scm/purchase/order/create');
    },
    handleUpdateStatus(){
      this.open = true;
      this.title = "操作采购订单";
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updatePurchaseOrder(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPurchaseOrder(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('purchase/purchaseOrder/export', {
        ...this.queryParams
      }, `purchaseOrder_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
