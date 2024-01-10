<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="出库单号" prop="stockOutNum">
        <el-input
          v-model="queryParams.stockOutNum"
          placeholder="请输入出库单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="源单号" prop="sourceNo">
        <el-input
          v-model="queryParams.sourceNo"
          placeholder="请输入来源单据号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="源单据Id" prop="sourceId">
        <el-input
          v-model="queryParams.sourceId"
          placeholder="请输入来源单据Id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="打印时间" prop="printTime">
        <el-date-picker clearable
          v-model="queryParams.printTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择打印时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="创建日期" prop="createTime">
        <el-date-picker clearable
          v-model="queryParams.createTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择创建日期">
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
          v-hasPermi="['wms:stockOutEntry:add']"
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
          v-hasPermi="['wms:stockOutEntry:edit']"
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
          v-hasPermi="['wms:stockOutEntry:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['wms:stockOutEntry:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="stockOutEntryList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" />
      <el-table-column label="出库单号" align="center" prop="stockOutNum" />
      <el-table-column label="源单号" align="center" prop="sourceNo" />
      <el-table-column label="源单Id" align="center" prop="sourceId" />
      <el-table-column label="出库类型" align="center" prop="stockOutType" >
        <template slot-scope="scope">
          <el-tag size="small" v-if="scope.row.stockOutType === 1">订单拣货出库</el-tag>
          <el-tag size="small" v-if="scope.row.stockOutType === 2">采购退货出库</el-tag>
          <el-tag size="small" v-if="scope.row.stockOutType === 3">盘点出库</el-tag>
          <el-tag size="small" v-if="scope.row.stockOutType === 4">报损出库</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="状态：0待拣货1拣货中2拣货完成3已出库" align="center" prop="status" />
      <el-table-column label="打印状态：是否打印1已打印0未打印" align="center" prop="printStatus" />
      <el-table-column label="打印时间" align="center" prop="printTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.printTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建日期" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建人" align="center" prop="createBy" />
      <el-table-column label="更新时间" align="center" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新人" align="center" prop="updateBy" />
      <el-table-column label="完成出库时间" align="center" prop="completeTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.completeTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="出库操作人userid" align="center" prop="stockOutOperatorId" />
      <el-table-column label="出库操作人" align="center" prop="stockOutOperatorName" />
      <el-table-column label="出库时间" align="center" prop="stockOutTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.stockOutTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="是否删除0未删除1已删除" align="center" prop="isDelete" />
      <el-table-column label="商品数" align="center" prop="goodsUnit" />
      <el-table-column label="商品规格数" align="center" prop="specUnit" />
      <el-table-column label="总件数" align="center" prop="specUnitTotal" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['wms:stockOutEntry:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['wms:stockOutEntry:remove']"
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

    <!-- 添加或修改出库单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="出库单编号" prop="stockOutNum">
          <el-input v-model="form.stockOutNum" placeholder="请输入出库单编号" />
        </el-form-item>
        <el-form-item label="来源单据号" prop="sourceNo">
          <el-input v-model="form.sourceNo" placeholder="请输入来源单据号" />
        </el-form-item>
        <el-form-item label="来源单据Id" prop="sourceId">
          <el-input v-model="form.sourceId" placeholder="请输入来源单据Id" />
        </el-form-item>
        <el-form-item label="打印时间" prop="printTime">
          <el-date-picker clearable
            v-model="form.printTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择打印时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="完成出库时间" prop="completeTime">
          <el-date-picker clearable
            v-model="form.completeTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择完成出库时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="出库操作人userid" prop="stockOutOperatorId">
          <el-input v-model="form.stockOutOperatorId" placeholder="请输入出库操作人userid" />
        </el-form-item>
        <el-form-item label="出库操作人" prop="stockOutOperatorName">
          <el-input v-model="form.stockOutOperatorName" placeholder="请输入出库操作人" />
        </el-form-item>
        <el-form-item label="出库时间" prop="stockOutTime">
          <el-date-picker clearable
            v-model="form.stockOutTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择出库时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="是否删除0未删除1已删除" prop="isDelete">
          <el-input v-model="form.isDelete" placeholder="请输入是否删除0未删除1已删除" />
        </el-form-item>
        <el-form-item label="商品数" prop="goodsUnit">
          <el-input v-model="form.goodsUnit" placeholder="请输入商品数" />
        </el-form-item>
        <el-form-item label="商品规格数" prop="specUnit">
          <el-input v-model="form.specUnit" placeholder="请输入商品规格数" />
        </el-form-item>
        <el-form-item label="总件数" prop="specUnitTotal">
          <el-input v-model="form.specUnitTotal" placeholder="请输入总件数" />
        </el-form-item>
        <el-divider content-position="center">出库单明细信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddWmsStockOutEntryItem">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteWmsStockOutEntryItem">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="wmsStockOutEntryItemList" :row-class-name="rowWmsStockOutEntryItemIndex" @selection-change="handleWmsStockOutEntryItemSelectionChange" ref="wmsStockOutEntryItem">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="来源订单id" prop="sourceOrderId" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.sourceOrderId" placeholder="请输入来源订单id" />
            </template>
          </el-table-column>
          <el-table-column label="来源订单号" prop="sourceOrderNo" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.sourceOrderNo" placeholder="请输入来源订单号" />
            </template>
          </el-table-column>
          <el-table-column label="来源订单itemId出库对应的itemId，如：order_item表id、invoice_info表id" prop="sourceOrderItemId" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.sourceOrderItemId" placeholder="请输入来源订单itemId出库对应的itemId，如：order_item表id、invoice_info表id" />
            </template>
          </el-table-column>
          <el-table-column label="商品id" prop="goodsId" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.goodsId" placeholder="请输入商品id" />
            </template>
          </el-table-column>
          <el-table-column label="商品规格id" prop="specId" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.specId" placeholder="请输入商品规格id" />
            </template>
          </el-table-column>
          <el-table-column label="规格编码" prop="specNum" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.specNum" placeholder="请输入规格编码" />
            </template>
          </el-table-column>
          <el-table-column label="总数量" prop="originalQuantity" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.originalQuantity" placeholder="请输入总数量" />
            </template>
          </el-table-column>
          <el-table-column label="已出库数量" prop="outQuantity" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.outQuantity" placeholder="请输入已出库数量" />
            </template>
          </el-table-column>
          <el-table-column label="完成出库时间" prop="completeTime" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.completeTime" placeholder="请输入完成出库时间" />
            </template>
          </el-table-column>
          <el-table-column label="完成拣货时间" prop="pickedTime" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.pickedTime" placeholder="请输入完成拣货时间" />
            </template>
          </el-table-column>
          <el-table-column label="状态：0待拣货1拣货中2拣货完成3已出库" prop="status" width="150">
            <template slot-scope="scope">
              <el-select v-model="scope.row.status" placeholder="请选择状态：0待拣货1拣货中2拣货完成3已出库">
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
import { listStockOutEntry, getStockOutEntry, delStockOutEntry, addStockOutEntry, updateStockOutEntry } from "@/api/wms/stockOutEntry";

export default {
  name: "StockOutEntry",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedWmsStockOutEntryItem: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 出库单表格数据
      stockOutEntryList: [],
      // 出库单明细表格数据
      wmsStockOutEntryItemList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        stockOutNum: null,
        sourceNo: null,
        sourceId: null,
        stockOutType: null,
        status: null,
        printStatus: null,
        printTime: null,
        createTime: null,
        createBy: null,
        updateTime: null,
        updateBy: null,
        completeTime: null,
        stockOutOperatorId: null,
        stockOutOperatorName: null,
        stockOutTime: null,
        isDelete: null,
        goodsUnit: null,
        specUnit: null,
        specUnitTotal: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        stockOutNum: [
          { required: true, message: "出库单编号不能为空", trigger: "blur" }
        ],
        stockOutType: [
          { required: true, message: "出库类型1订单拣货出库2采购退货出库3盘点出库4报损出库不能为空", trigger: "change" }
        ],
        status: [
          { required: true, message: "状态：0待拣货1拣货中2拣货完成3已出库不能为空", trigger: "change" }
        ],
        printStatus: [
          { required: true, message: "打印状态：是否打印1已打印0未打印不能为空", trigger: "change" }
        ],
        createTime: [
          { required: true, message: "创建日期不能为空", trigger: "blur" }
        ],
        isDelete: [
          { required: true, message: "是否删除0未删除1已删除不能为空", trigger: "blur" }
        ],
        goodsUnit: [
          { required: true, message: "商品数不能为空", trigger: "blur" }
        ],
        specUnit: [
          { required: true, message: "商品规格数不能为空", trigger: "blur" }
        ],
        specUnitTotal: [
          { required: true, message: "总件数不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询出库单列表 */
    getList() {
      this.loading = true;
      listStockOutEntry(this.queryParams).then(response => {
        this.stockOutEntryList = response.rows;
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
        stockOutNum: null,
        sourceNo: null,
        sourceId: null,
        stockOutType: null,
        status: null,
        printStatus: null,
        printTime: null,
        createTime: null,
        createBy: null,
        updateTime: null,
        updateBy: null,
        completeTime: null,
        stockOutOperatorId: null,
        stockOutOperatorName: null,
        stockOutTime: null,
        remark: null,
        isDelete: null,
        goodsUnit: null,
        specUnit: null,
        specUnitTotal: null
      };
      this.wmsStockOutEntryItemList = [];
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
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getStockOutEntry(id).then(response => {
        this.form = response.data;
        this.wmsStockOutEntryItemList = response.data.wmsStockOutEntryItemList;
        this.open = true;
        this.title = "修改出库单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.wmsStockOutEntryItemList = this.wmsStockOutEntryItemList;
          if (this.form.id != null) {
            updateStockOutEntry(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addStockOutEntry(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除出库单编号为"' + ids + '"的数据项？').then(function() {
        return delStockOutEntry(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
	/** 出库单明细序号 */
    rowWmsStockOutEntryItemIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** 出库单明细添加按钮操作 */
    handleAddWmsStockOutEntryItem() {
      let obj = {};
      obj.sourceOrderId = "";
      obj.sourceOrderNo = "";
      obj.sourceOrderItemId = "";
      obj.goodsId = "";
      obj.specId = "";
      obj.specNum = "";
      obj.originalQuantity = "";
      obj.outQuantity = "";
      obj.completeTime = "";
      obj.pickedTime = "";
      obj.status = "";
      this.wmsStockOutEntryItemList.push(obj);
    },
    /** 出库单明细删除按钮操作 */
    handleDeleteWmsStockOutEntryItem() {
      if (this.checkedWmsStockOutEntryItem.length == 0) {
        this.$modal.msgError("请先选择要删除的出库单明细数据");
      } else {
        const wmsStockOutEntryItemList = this.wmsStockOutEntryItemList;
        const checkedWmsStockOutEntryItem = this.checkedWmsStockOutEntryItem;
        this.wmsStockOutEntryItemList = wmsStockOutEntryItemList.filter(function(item) {
          return checkedWmsStockOutEntryItem.indexOf(item.index) == -1
        });
      }
    },
    /** 复选框选中数据 */
    handleWmsStockOutEntryItemSelectionChange(selection) {
      this.checkedWmsStockOutEntryItem = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('wms/stockOutEntry/export', {
        ...this.queryParams
      }, `stockOutEntry_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
