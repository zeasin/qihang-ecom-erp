<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="单据编号" prop="no">
        <el-input
          v-model="queryParams.no"
          placeholder="请输入单据编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="来源单号" prop="sourceNo">
        <el-input
          v-model="queryParams.sourceNo"
          placeholder="请输入来源单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      
      <el-form-item label="入库人" prop="stockInOperator">
        <el-input
          v-model="queryParams.stockInOperator"
          placeholder="请输入操作入库人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="入库时间" prop="stockInTime">
        <el-date-picker clearable
          v-model="queryParams.stockInTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择入库时间">
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
          v-hasPermi="['wms:WmsStockInEntry:add']"
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
          v-hasPermi="['wms:WmsStockInEntry:edit']"
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
          v-hasPermi="['wms:WmsStockInEntry:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['wms:WmsStockInEntry:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="WmsStockInEntryList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键ID" align="center" prop="id" />
      <el-table-column label="单据编号" align="center" prop="no" />
      <el-table-column label="来源单号" align="center" prop="sourceNo" />
      <el-table-column label="来源单id" align="center" prop="sourceId" />
      <el-table-column label="来源类型" align="center" prop="sourceType" />
      <el-table-column label="采购订单商品数" align="center" prop="sourceGoodsUnit" />
      <el-table-column label="采购订单总件数" align="center" prop="sourceSpecUnitTotal" />
      <el-table-column label="采购订单商品规格数" align="center" prop="sourceSpecUnit" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作入库人id" align="center" prop="stockInOperatorId" />
      <el-table-column label="操作入库人" align="center" prop="stockInOperator" />
      <el-table-column label="入库时间" align="center" prop="stockInTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.stockInTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['wms:WmsStockInEntry:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['wms:WmsStockInEntry:remove']"
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

    <!-- 添加或修改入库单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="单据编号" prop="no">
          <el-input v-model="form.no" placeholder="请输入单据编号" />
        </el-form-item>
        <el-form-item label="来源单号" prop="sourceNo">
          <el-input v-model="form.sourceNo" placeholder="请输入来源单号" />
        </el-form-item>
        <el-form-item label="来源单id" prop="sourceId">
          <el-input v-model="form.sourceId" placeholder="请输入来源单id" />
        </el-form-item>
        <el-form-item label="采购订单商品数" prop="sourceGoodsUnit">
          <el-input v-model="form.sourceGoodsUnit" placeholder="请输入采购订单商品数" />
        </el-form-item>
        <el-form-item label="采购订单总件数" prop="sourceSpecUnitTotal">
          <el-input v-model="form.sourceSpecUnitTotal" placeholder="请输入采购订单总件数" />
        </el-form-item>
        <el-form-item label="采购订单商品规格数" prop="sourceSpecUnit">
          <el-input v-model="form.sourceSpecUnit" placeholder="请输入采购订单商品规格数" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" />
        </el-form-item>
        <el-form-item label="操作入库人id" prop="stockInOperatorId">
          <el-input v-model="form.stockInOperatorId" placeholder="请输入操作入库人id" />
        </el-form-item>
        <el-form-item label="操作入库人" prop="stockInOperator">
          <el-input v-model="form.stockInOperator" placeholder="请输入操作入库人" />
        </el-form-item>
        <el-form-item label="入库时间" prop="stockInTime">
          <el-date-picker clearable
            v-model="form.stockInTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择入库时间">
          </el-date-picker>
        </el-form-item>
        <el-divider content-position="center">入库单明细信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddWmsStockInEntryItem">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteWmsStockInEntryItem">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="wmsStockInEntryItemList" :row-class-name="rowWmsStockInEntryItemIndex" @selection-change="handleWmsStockInEntryItemSelectionChange" ref="wmsStockInEntryItem">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="来源类型" prop="sourceType" width="150">
            <template slot-scope="scope">
              <el-select v-model="scope.row.sourceType" placeholder="请选择来源类型">
                <el-option label="请选择字典生成" value="" />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="来源单id" prop="sourceId" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.sourceId" placeholder="请输入来源单id" />
            </template>
          </el-table-column>
          <el-table-column label="来源单itemId" prop="sourceItemId" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.sourceItemId" placeholder="请输入来源单itemId" />
            </template>
          </el-table-column>
          <el-table-column label="商品id" prop="goodsId" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.goodsId" placeholder="请输入商品id" />
            </template>
          </el-table-column>
          <el-table-column label="商品编码" prop="goodsNum" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.goodsNum" placeholder="请输入商品编码" />
            </template>
          </el-table-column>
          <el-table-column label="商品规格id" prop="specId" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.specId" placeholder="请输入商品规格id" />
            </template>
          </el-table-column>
          <el-table-column label="商品规格编码" prop="specNum" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.specNum" placeholder="请输入商品规格编码" />
            </template>
          </el-table-column>
          <el-table-column label="原始数量" prop="originalQuantity" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.originalQuantity" placeholder="请输入原始数量" />
            </template>
          </el-table-column>
          <el-table-column label="入库数量" prop="inQuantity" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.inQuantity" placeholder="请输入入库数量" />
            </template>
          </el-table-column>
          <el-table-column label="入库仓位" prop="locationId" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.locationId" placeholder="请输入入库仓位" />
            </template>
          </el-table-column>
          <el-table-column label="入库仓位编码" prop="locationNum" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.locationNum" placeholder="请输入入库仓位编码" />
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
import { listWmsStockInEntry, getWmsStockInEntry, delWmsStockInEntry, addWmsStockInEntry, updateWmsStockInEntry } from "@/api/wms/WmsStockInEntry";

export default {
  name: "WmsStockInEntry",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedWmsStockInEntryItem: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 入库单表格数据
      WmsStockInEntryList: [],
      // 入库单明细表格数据
      wmsStockInEntryItemList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        no: null,
        sourceNo: null,
        sourceId: null,
        sourceType: null,
        sourceGoodsUnit: null,
        sourceSpecUnitTotal: null,
        sourceSpecUnit: null,
        stockInOperatorId: null,
        stockInOperator: null,
        stockInTime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        no: [
          { required: true, message: "单据编号不能为空", trigger: "blur" }
        ],
        sourceType: [
          { required: true, message: "来源类型不能为空", trigger: "change" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询入库单列表 */
    getList() {
      this.loading = true;
      listWmsStockInEntry(this.queryParams).then(response => {
        this.WmsStockInEntryList = response.rows;
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
        no: null,
        sourceNo: null,
        sourceId: null,
        sourceType: null,
        sourceGoodsUnit: null,
        sourceSpecUnitTotal: null,
        sourceSpecUnit: null,
        remark: null,
        stockInOperatorId: null,
        stockInOperator: null,
        stockInTime: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      };
      this.wmsStockInEntryItemList = [];
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
      this.title = "添加入库单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getWmsStockInEntry(id).then(response => {
        this.form = response.data;
        this.wmsStockInEntryItemList = response.data.wmsStockInEntryItemList;
        this.open = true;
        this.title = "修改入库单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.wmsStockInEntryItemList = this.wmsStockInEntryItemList;
          if (this.form.id != null) {
            updateWmsStockInEntry(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addWmsStockInEntry(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除入库单编号为"' + ids + '"的数据项？').then(function() {
        return delWmsStockInEntry(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
	/** 入库单明细序号 */
    rowWmsStockInEntryItemIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** 入库单明细添加按钮操作 */
    handleAddWmsStockInEntryItem() {
      let obj = {};
      obj.sourceType = "";
      obj.sourceId = "";
      obj.sourceItemId = "";
      obj.goodsId = "";
      obj.goodsNum = "";
      obj.specId = "";
      obj.specNum = "";
      obj.originalQuantity = "";
      obj.inQuantity = "";
      obj.remark = "";
      obj.locationId = "";
      obj.locationNum = "";
      this.wmsStockInEntryItemList.push(obj);
    },
    /** 入库单明细删除按钮操作 */
    handleDeleteWmsStockInEntryItem() {
      if (this.checkedWmsStockInEntryItem.length == 0) {
        this.$modal.msgError("请先选择要删除的入库单明细数据");
      } else {
        const wmsStockInEntryItemList = this.wmsStockInEntryItemList;
        const checkedWmsStockInEntryItem = this.checkedWmsStockInEntryItem;
        this.wmsStockInEntryItemList = wmsStockInEntryItemList.filter(function(item) {
          return checkedWmsStockInEntryItem.indexOf(item.index) == -1
        });
      }
    },
    /** 复选框选中数据 */
    handleWmsStockInEntryItemSelectionChange(selection) {
      this.checkedWmsStockInEntryItem = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('wms/WmsStockInEntry/export', {
        ...this.queryParams
      }, `WmsStockInEntry_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
