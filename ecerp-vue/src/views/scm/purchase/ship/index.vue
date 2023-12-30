<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="物流公司" prop="shipCompany">
        <el-input
          v-model="queryParams.shipCompany"
          placeholder="请输入物流公司" 
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="物流单号" prop="shipNo">
        <el-input
          v-model="queryParams.shipNo"
          placeholder="请输入物流单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="运费" prop="freight">
        <el-input
          v-model="queryParams.freight"
          placeholder="请输入运费"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="运送时间" prop="shipTime">
        <el-date-picker clearable
          v-model="queryParams.shipTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择运送时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="退回数量" prop="backCount">
        <el-input
          v-model="queryParams.backCount"
          placeholder="请输入退回数量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="入库数量" prop="stockInCount">
        <el-input
          v-model="queryParams.stockInCount"
          placeholder="请输入入库数量"
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
          v-hasPermi="['purchase:PurchaseOrderShip:add']"
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
          v-hasPermi="['purchase:PurchaseOrderShip:edit']"
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
          v-hasPermi="['purchase:PurchaseOrderShip:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['purchase:PurchaseOrderShip:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="PurchaseOrderShipList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="采购单ID" align="center" prop="id" />
      <el-table-column label="物流公司" align="center" prop="shipCompany" />
      <el-table-column label="物流单号" align="center" prop="shipNo" />
      <el-table-column label="运费" align="center" prop="freight" />
      <el-table-column label="运送时间" align="center" prop="shipTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.shipTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="说明" align="center" prop="remark" />
      <el-table-column label="退回数量" align="center" prop="backCount" />
      <el-table-column label="入库数量" align="center" prop="stockInCount" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['purchase:PurchaseOrderShip:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['purchase:PurchaseOrderShip:remove']"
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

    <!-- 添加或修改采购订单物流对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="物流公司" prop="shipCompany">
          <el-input v-model="form.shipCompany" placeholder="请输入物流公司" />
        </el-form-item>
        <el-form-item label="物流单号" prop="shipNo">
          <el-input v-model="form.shipNo" placeholder="请输入物流单号" />
        </el-form-item>
        <el-form-item label="运费" prop="freight">
          <el-input v-model="form.freight" placeholder="请输入运费" />
        </el-form-item>
        <el-form-item label="运送时间" prop="shipTime">
          <el-date-picker clearable
            v-model="form.shipTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择运送时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="说明" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入说明" />
        </el-form-item>
        <el-form-item label="退回数量" prop="backCount">
          <el-input v-model="form.backCount" placeholder="请输入退回数量" />
        </el-form-item>
        <el-form-item label="入库数量" prop="stockInCount">
          <el-input v-model="form.stockInCount" placeholder="请输入入库数量" />
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
import { listPurchaseOrderShip, getPurchaseOrderShip, delPurchaseOrderShip, addPurchaseOrderShip, updatePurchaseOrderShip } from "@/api/scm/PurchaseOrderShip";

export default {
  name: "PurchaseOrderShip",
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
      // 采购订单物流表格数据
      PurchaseOrderShipList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        shipCompany: null,
        shipNo: null,
        freight: null,
        shipTime: null,
        status: null,
        backCount: null,
        stockInCount: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询采购订单物流列表 */
    getList() {
      this.loading = true;
      listPurchaseOrderShip(this.queryParams).then(response => {
        this.PurchaseOrderShipList = response.rows;
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
        shipCompany: null,
        shipNo: null,
        freight: null,
        shipTime: null,
        createBy: null,
        createTime: null,
        status: null,
        remark: null,
        backCount: null,
        stockInCount: null,
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
      this.reset();
      this.open = true;
      this.title = "添加采购订单物流";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getPurchaseOrderShip(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改采购订单物流";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updatePurchaseOrderShip(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPurchaseOrderShip(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除采购订单物流编号为"' + ids + '"的数据项？').then(function() {
        return delPurchaseOrderShip(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('purchase/PurchaseOrderShip/export', {
        ...this.queryParams
      }, `PurchaseOrderShip_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
