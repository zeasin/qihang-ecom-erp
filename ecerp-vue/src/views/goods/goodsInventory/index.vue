<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="商品id" prop="goodsId">
        <el-input
          v-model="queryParams.goodsId"
          placeholder="请输入商品id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="商品编码" prop="goodsNumber">
        <el-input
          v-model="queryParams.goodsNumber"
          placeholder="请输入商品编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="商品规格id" prop="specId">
        <el-input
          v-model="queryParams.specId"
          placeholder="请输入商品规格id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="规格编码" prop="specNumber">
        <el-input
          v-model="queryParams.specNumber"
          placeholder="请输入规格编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="当前库存" prop="currentQty">
        <el-input
          v-model="queryParams.currentQty"
          placeholder="请输入当前库存"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="锁定库存" prop="lockedQty">
        <el-input
          v-model="queryParams.lockedQty"
          placeholder="请输入锁定库存"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="0正常  1删除" prop="isDelete">
        <el-input
          v-model="queryParams.isDelete"
          placeholder="请输入0正常  1删除"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime">
        <el-date-picker clearable
          v-model="queryParams.createTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择创建时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="创建人" prop="createBy">
        <el-input
          v-model="queryParams.createBy"
          placeholder="请输入创建人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="更新时间" prop="updateTime">
        <el-date-picker clearable
          v-model="queryParams.updateTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择更新时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="更新人" prop="updateBy">
        <el-input
          v-model="queryParams.updateBy"
          placeholder="请输入更新人"
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
          v-hasPermi="['api:goodsInventory:add']"
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
          v-hasPermi="['api:goodsInventory:edit']"
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
          v-hasPermi="['api:goodsInventory:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['api:goodsInventory:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="goodsInventoryList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键ID" align="center" prop="id" />
      <el-table-column label="商品id" align="center" prop="goodsId" />
      <el-table-column label="商品编码" align="center" prop="goodsNumber" />
      <el-table-column label="商品规格id" align="center" prop="specId" />
      <el-table-column label="规格编码" align="center" prop="specNumber" />
      <el-table-column label="当前库存" align="center" prop="currentQty" />
      <el-table-column label="锁定库存" align="center" prop="lockedQty" />
      <el-table-column label="0正常  1删除" align="center" prop="isDelete" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
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
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['api:goodsInventory:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['api:goodsInventory:remove']"
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

    <!-- 添加或修改商品库存对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="商品id" prop="goodsId">
          <el-input v-model="form.goodsId" placeholder="请输入商品id" />
        </el-form-item>
        <el-form-item label="商品编码" prop="goodsNumber">
          <el-input v-model="form.goodsNumber" placeholder="请输入商品编码" />
        </el-form-item>
        <el-form-item label="商品规格id" prop="specId">
          <el-input v-model="form.specId" placeholder="请输入商品规格id" />
        </el-form-item>
        <el-form-item label="规格编码" prop="specNumber">
          <el-input v-model="form.specNumber" placeholder="请输入规格编码" />
        </el-form-item>
        <el-form-item label="当前库存" prop="currentQty">
          <el-input v-model="form.currentQty" placeholder="请输入当前库存" />
        </el-form-item>
        <el-form-item label="锁定库存" prop="lockedQty">
          <el-input v-model="form.lockedQty" placeholder="请输入锁定库存" />
        </el-form-item>
        <el-form-item label="0正常  1删除" prop="isDelete">
          <el-input v-model="form.isDelete" placeholder="请输入0正常  1删除" />
        </el-form-item>
        <el-divider content-position="center">商品库存明细信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddErpGoodsInventoryDetail">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteErpGoodsInventoryDetail">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="erpGoodsInventoryDetailList" :row-class-name="rowErpGoodsInventoryDetailIndex" @selection-change="handleErpGoodsInventoryDetailSelectionChange" ref="erpGoodsInventoryDetail">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="入库数量" prop="inQty" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.inQty" placeholder="请输入入库数量" />
            </template>
          </el-table-column>
          <el-table-column label="入库前数量" prop="originQty" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.originQty" placeholder="请输入入库前数量" />
            </template>
          </el-table-column>
          <el-table-column label="当前库存数量" prop="currentQty" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.currentQty" placeholder="请输入当前库存数量" />
            </template>
          </el-table-column>
          <el-table-column label="采购价" prop="purPrice" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.purPrice" placeholder="请输入采购价" />
            </template>
          </el-table-column>
          <el-table-column label="入库单id" prop="entryId" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.entryId" placeholder="请输入入库单id" />
            </template>
          </el-table-column>
          <el-table-column label="入库单itemId" prop="entryItemId" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.entryItemId" placeholder="请输入入库单itemId" />
            </template>
          </el-table-column>
          <el-table-column label="备注" prop="remark" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.remark" placeholder="请输入备注" />
            </template>
          </el-table-column>
          <el-table-column label="规格id" prop="specId" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.specId" placeholder="请输入规格id" />
            </template>
          </el-table-column>
          <el-table-column label="商品id" prop="goodsId" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.goodsId" placeholder="请输入商品id" />
            </template>
          </el-table-column>
          <el-table-column label="入库仓位id" prop="inLocation" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.inLocation" placeholder="请输入入库仓位id" />
            </template>
          </el-table-column>
          <el-table-column label="创建时间" prop="createTime" width="240">
            <template slot-scope="scope">
              <el-date-picker clearable v-model="scope.row.createTime" type="date" value-format="yyyy-MM-dd" placeholder="请选择创建时间" />
            </template>
          </el-table-column>
          <el-table-column label="创建人" prop="createBy" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.createBy" placeholder="请输入创建人" />
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
import { listGoodsInventory, getGoodsInventory, delGoodsInventory, addGoodsInventory, updateGoodsInventory } from "@/api/goods/goodsInventory";

export default {
  name: "GoodsInventory",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedErpGoodsInventoryDetail: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 商品库存表格数据
      goodsInventoryList: [],
      // 商品库存明细表格数据
      erpGoodsInventoryDetailList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        goodsId: null,
        goodsNumber: null,
        specId: null,
        specNumber: null,
        currentQty: null,
        lockedQty: null,
        isDelete: null,
        createTime: null,
        createBy: null,
        updateTime: null,
        updateBy: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        goodsId: [
          { required: true, message: "商品id不能为空", trigger: "blur" }
        ],
        specId: [
          { required: true, message: "商品规格id不能为空", trigger: "blur" }
        ],
        specNumber: [
          { required: true, message: "规格编码不能为空", trigger: "blur" }
        ],
        currentQty: [
          { required: true, message: "当前库存不能为空", trigger: "blur" }
        ],
        lockedQty: [
          { required: true, message: "锁定库存不能为空", trigger: "blur" }
        ],
        isDelete: [
          { required: true, message: "0正常  1删除不能为空", trigger: "blur" }
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
    this.getList();
  },
  methods: {
    /** 查询商品库存列表 */
    getList() {
      this.loading = true;
      listGoodsInventory(this.queryParams).then(response => {
        this.goodsInventoryList = response.rows;
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
        goodsId: null,
        goodsNumber: null,
        specId: null,
        specNumber: null,
        currentQty: null,
        lockedQty: null,
        isDelete: null,
        createTime: null,
        createBy: null,
        updateTime: null,
        updateBy: null
      };
      this.erpGoodsInventoryDetailList = [];
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
      this.title = "添加商品库存";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getGoodsInventory(id).then(response => {
        this.form = response.data;
        this.erpGoodsInventoryDetailList = response.data.erpGoodsInventoryDetailList;
        this.open = true;
        this.title = "修改商品库存";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.erpGoodsInventoryDetailList = this.erpGoodsInventoryDetailList;
          if (this.form.id != null) {
            updateGoodsInventory(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addGoodsInventory(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除商品库存编号为"' + ids + '"的数据项？').then(function() {
        return delGoodsInventory(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
	/** 商品库存明细序号 */
    rowErpGoodsInventoryDetailIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** 商品库存明细添加按钮操作 */
    handleAddErpGoodsInventoryDetail() {
      let obj = {};
      obj.inQty = "";
      obj.originQty = "";
      obj.currentQty = "";
      obj.purPrice = "";
      obj.entryId = "";
      obj.entryItemId = "";
      obj.remark = "";
      obj.specId = "";
      obj.goodsId = "";
      obj.inLocation = "";
      obj.createTime = "";
      obj.createBy = "";
      this.erpGoodsInventoryDetailList.push(obj);
    },
    /** 商品库存明细删除按钮操作 */
    handleDeleteErpGoodsInventoryDetail() {
      if (this.checkedErpGoodsInventoryDetail.length == 0) {
        this.$modal.msgError("请先选择要删除的商品库存明细数据");
      } else {
        const erpGoodsInventoryDetailList = this.erpGoodsInventoryDetailList;
        const checkedErpGoodsInventoryDetail = this.checkedErpGoodsInventoryDetail;
        this.erpGoodsInventoryDetailList = erpGoodsInventoryDetailList.filter(function(item) {
          return checkedErpGoodsInventoryDetail.indexOf(item.index) == -1
        });
      }
    },
    /** 复选框选中数据 */
    handleErpGoodsInventoryDetailSelectionChange(selection) {
      this.checkedErpGoodsInventoryDetail = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('api/goodsInventory/export', {
        ...this.queryParams
      }, `goodsInventory_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
