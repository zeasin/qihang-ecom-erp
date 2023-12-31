<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="店铺名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入店铺名" 
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
          v-hasPermi="['shop:shop:add']"
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
          v-hasPermi="['shop:shop:edit']"
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
          v-hasPermi="['shop:shop:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['shop:shop:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="shopList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键" align="center" prop="id" />
      <el-table-column label="店铺名" align="center" prop="name" />
      <el-table-column label="店铺别名" align="center" prop="nickName" />
      <el-table-column label="标识" align="center" prop="ename" />
      <el-table-column label="店铺主体" align="center" prop="company" />
      <el-table-column label="对应第三方平台Id" align="center" prop="type" />
      <el-table-column label="店铺url" align="center" prop="url" />
      <el-table-column label="排序" align="center" prop="orderNum" />
      <el-table-column label="是否删除0否1是" align="center" prop="isDelete" />
      <el-table-column label="是否显示(0：是1否）" align="center" prop="isShow" />
      <el-table-column label="更新时间" align="center" prop="modifyOn" />
      <el-table-column label="描述" align="center" prop="remark" />
      <el-table-column label="第三方平台店铺id，淘宝天猫开放平台使用" align="center" prop="sellerUserId" />
      <el-table-column label="卖家userId" align="center" prop="sellerUserIdStr" />
      <el-table-column label="第三方平台sessionKey" align="center" prop="sessionKey" />
      <el-table-column label="Appkey暂时抖音用" align="center" prop="appkey" />
      <el-table-column label="Appsercet暂时抖音用" align="center" prop="appSercet" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['shop:shop:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['shop:shop:remove']"
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

    <!-- 添加或修改店铺对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="店铺名" prop="name">
          <el-input v-model="form.name" placeholder="请输入店铺名" />
        </el-form-item>
        <el-form-item label="店铺别名" prop="nickName">
          <el-input v-model="form.nickName" placeholder="请输入店铺别名" />
        </el-form-item>
        <el-form-item label="标识" prop="ename">
          <el-input v-model="form.ename" placeholder="请输入标识" />
        </el-form-item>
        <el-form-item label="店铺主体" prop="company">
          <el-input v-model="form.company" placeholder="请输入店铺主体" />
        </el-form-item>
        <el-form-item label="店铺url" prop="url">
          <el-input v-model="form.url" placeholder="请输入店铺url" />
        </el-form-item>
        <el-form-item label="排序" prop="orderNum">
          <el-input v-model="form.orderNum" placeholder="请输入排序" />
        </el-form-item>
        <el-form-item label="是否删除0否1是" prop="isDelete">
          <el-input v-model="form.isDelete" placeholder="请输入是否删除0否1是" />
        </el-form-item>
        <el-form-item label="是否显示(0：是1否）" prop="isShow">
          <el-input v-model="form.isShow" placeholder="请输入是否显示(0：是1否）" />
        </el-form-item>
        <el-form-item label="更新时间" prop="modifyOn">
          <el-input v-model="form.modifyOn" placeholder="请输入更新时间" />
        </el-form-item>
        <el-form-item label="描述" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入描述" />
        </el-form-item>
        <el-form-item label="第三方平台店铺id，淘宝天猫开放平台使用" prop="sellerUserId">
          <el-input v-model="form.sellerUserId" placeholder="请输入第三方平台店铺id，淘宝天猫开放平台使用" />
        </el-form-item>
        <el-form-item label="卖家userId" prop="sellerUserIdStr">
          <el-input v-model="form.sellerUserIdStr" placeholder="请输入卖家userId" />
        </el-form-item>
        <el-form-item label="第三方平台sessionKey" prop="sessionKey">
          <el-input v-model="form.sessionKey" placeholder="请输入第三方平台sessionKey" />
        </el-form-item>
        <el-form-item label="Appkey暂时抖音用" prop="appkey">
          <el-input v-model="form.appkey" placeholder="请输入Appkey暂时抖音用" />
        </el-form-item>
        <el-form-item label="Appsercet暂时抖音用" prop="appSercet">
          <el-input v-model="form.appSercet" placeholder="请输入Appsercet暂时抖音用" />
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
import { listShop, getShop, delShop, addShop, updateShop } from "@/api/shop/shop";

export default {
  name: "Shop",
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
      // 店铺表格数据
      shopList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        nickName: null,
        ename: null,
        company: null,
        type: null,
        url: null,
        orderNum: null,
        isDelete: null,
        isShow: null,
        modifyOn: null,
        sellerUserId: null,
        sellerUserIdStr: null,
        sessionKey: null,
        appkey: null,
        appSercet: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: "店铺名不能为空", trigger: "blur" }
        ],
        type: [
          { required: true, message: "对应第三方平台Id不能为空", trigger: "change" }
        ],
        orderNum: [
          { required: true, message: "排序不能为空", trigger: "blur" }
        ],
        isDelete: [
          { required: true, message: "是否删除0否1是不能为空", trigger: "blur" }
        ],
        modifyOn: [
          { required: true, message: "更新时间不能为空", trigger: "blur" }
        ],
        sellerUserId: [
          { required: true, message: "第三方平台店铺id，淘宝天猫开放平台使用不能为空", trigger: "blur" }
        ],
        sellerUserIdStr: [
          { required: true, message: "卖家userId不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询店铺列表 */
    getList() {
      this.loading = true;
      listShop(this.queryParams).then(response => {
        this.shopList = response.rows;
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
        name: null,
        nickName: null,
        ename: null,
        company: null,
        type: null,
        url: null,
        orderNum: null,
        isDelete: null,
        isShow: null,
        modifyOn: null,
        remark: null,
        sellerUserId: null,
        sellerUserIdStr: null,
        sessionKey: null,
        appkey: null,
        appSercet: null
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
      this.title = "添加店铺";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getShop(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改店铺";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateShop(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addShop(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除店铺编号为"' + ids + '"的数据项？').then(function() {
        return delShop(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('shop/shop/export', {
        ...this.queryParams
      }, `shop_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
