<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="退款id" prop="refundId">
        <el-input
          v-model="queryParams.refundId"
          placeholder="请输入退款id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="淘宝交易单号" prop="tid">
        <el-input
          v-model="queryParams.tid"
          placeholder="请输入淘宝交易单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="子订单号。如果是单笔交易oid会等于tid" prop="oid">
        <el-input
          v-model="queryParams.oid"
          placeholder="请输入子订单号。如果是单笔交易oid会等于tid"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="买家昵称" prop="buyerNick">
        <el-input
          v-model="queryParams.buyerNick"
          placeholder="请输入买家昵称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="交易总金额。精确到2位小数;单位:元。如:200.07，表示:200元7分" prop="totalFee">
        <el-input
          v-model="queryParams.totalFee"
          placeholder="请输入交易总金额。精确到2位小数;单位:元。如:200.07，表示:200元7分"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="支付给卖家的金额(交易总金额-退还给买家的金额)。精确到2位小数;单位:元。如:200.07，表示:200元7分" prop="payment">
        <el-input
          v-model="queryParams.payment"
          placeholder="请输入支付给卖家的金额(交易总金额-退还给买家的金额)。精确到2位小数;单位:元。如:200.07，表示:200元7分"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="退还金额(退还给买家的金额)。精确到2位小数;单位:元。如:200.07，表示:200元7分" prop="refundFee">
        <el-input
          v-model="queryParams.refundFee"
          placeholder="请输入退还金额(退还给买家的金额)。精确到2位小数;单位:元。如:200.07，表示:200元7分"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="退款申请时间。" prop="created">
        <el-input
          v-model="queryParams.created"
          placeholder="请输入退款申请时间。"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="更新时间。" prop="modified">
        <el-input
          v-model="queryParams.modified"
          placeholder="请输入更新时间。"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="退货数量" prop="num">
        <el-input
          v-model="queryParams.num"
          placeholder="请输入退货数量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="买家是否需要退货。可选值:true(是),false(否)" prop="hasGoodReturn">
        <el-input
          v-model="queryParams.hasGoodReturn"
          placeholder="请输入买家是否需要退货。可选值:true(是),false(否)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="退款原因" prop="reason">
        <el-input
          v-model="queryParams.reason"
          placeholder="请输入退款原因"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="退款说明" prop="desc">
        <el-input
          v-model="queryParams.desc"
          placeholder="请输入退款说明"
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
      <el-form-item label="物流单号" prop="logisticsCode">
        <el-input
          v-model="queryParams.logisticsCode"
          placeholder="请输入物流单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="${comment}" prop="sendTime">
        <el-input
          v-model="queryParams.sendTime"
          placeholder="请输入${comment}"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="${comment}" prop="auditTime">
        <el-date-picker clearable
          v-model="queryParams.auditTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择${comment}">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="收货时间" prop="receivedTime">
        <el-date-picker clearable
          v-model="queryParams.receivedTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择收货时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="${comment}" prop="address">
        <el-input
          v-model="queryParams.address"
          placeholder="请输入${comment}"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="系统创建时间" prop="createOn">
        <el-input
          v-model="queryParams.createOn"
          placeholder="请输入系统创建时间"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="店铺id" prop="shopId">
        <el-input
          v-model="queryParams.shopId"
          placeholder="请输入店铺id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="${comment}" prop="erpGoodsId">
        <el-input
          v-model="queryParams.erpGoodsId"
          placeholder="请输入${comment}"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="${comment}" prop="erpGoodsSpecId">
        <el-input
          v-model="queryParams.erpGoodsSpecId"
          placeholder="请输入${comment}"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="sku编号" prop="specNumber">
        <el-input
          v-model="queryParams.specNumber"
          placeholder="请输入sku编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="退款阶段，可选值：onsale/aftersale" prop="refundPhase">
        <el-input
          v-model="queryParams.refundPhase"
          placeholder="请输入退款阶段，可选值：onsale/aftersale"
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
          v-hasPermi="['tao:refund:add']"
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
          v-hasPermi="['tao:refund:edit']"
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
          v-hasPermi="['tao:refund:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['tao:refund:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="refundList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="${comment}" align="center" prop="id" />
      <el-table-column label="退款id" align="center" prop="refundId" />
      <el-table-column label="类型" align="center" prop="afterSalesType" />
      <el-table-column label="淘宝交易单号" align="center" prop="tid" />
      <el-table-column label="子订单号。如果是单笔交易oid会等于tid" align="center" prop="oid" />
      <el-table-column label="买家昵称" align="center" prop="buyerNick" />
      <el-table-column label="交易总金额。精确到2位小数;单位:元。如:200.07，表示:200元7分" align="center" prop="totalFee" />
      <el-table-column label="支付给卖家的金额(交易总金额-退还给买家的金额)。精确到2位小数;单位:元。如:200.07，表示:200元7分" align="center" prop="payment" />
      <el-table-column label="退还金额(退还给买家的金额)。精确到2位小数;单位:元。如:200.07，表示:200元7分" align="center" prop="refundFee" />
      <el-table-column label="退款申请时间。" align="center" prop="created" />
      <el-table-column label="更新时间。" align="center" prop="modified" />
      <el-table-column label="退款对应的订单交易状态。" align="center" prop="orderStatus" />
      <el-table-column label="退款状态。可选值WAIT_SELLER_AGREE(买家已经申请退款，等待卖家同意) WAIT_BUYER_RETURN_GOODS(卖家已经同意退款，等待买家退货) WAIT_SELLER_CONFIRM_GOODS(买家已经退货，等待卖家确认收货) SELLER_REFUSE_BUYER(卖家拒绝退款) CLOSED(退款关闭) SUCCESS(退款成功)" align="center" prop="status" />
      <el-table-column label="货物状态。可选值BUYER_NOT_RECEIVED (买家未收到货) BUYER_RECEIVED (买家已收到货) BUYER_RETURNED_GOODS (买家已退货)" align="center" prop="goodStatus" />
      <el-table-column label="退货数量" align="center" prop="num" />
      <el-table-column label="买家是否需要退货。可选值:true(是),false(否)" align="center" prop="hasGoodReturn" />
      <el-table-column label="退款原因" align="center" prop="reason" />
      <el-table-column label="退款说明" align="center" prop="desc" />
      <el-table-column label="物流公司" align="center" prop="logisticsCompany" />
      <el-table-column label="物流单号" align="center" prop="logisticsCode" />
      <el-table-column label="${comment}" align="center" prop="sendTime" />
      <el-table-column label="2已签收9供应商已退款" align="center" prop="auditStatus" />
      <el-table-column label="${comment}" align="center" prop="auditTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.auditTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="收货时间" align="center" prop="receivedTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.receivedTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="${comment}" align="center" prop="address" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="系统创建时间" align="center" prop="createOn" />
      <el-table-column label="店铺id" align="center" prop="shopId" />
      <el-table-column label="${comment}" align="center" prop="erpGoodsId" />
      <el-table-column label="${comment}" align="center" prop="erpGoodsSpecId" />
      <el-table-column label="sku编号" align="center" prop="specNumber" />
      <el-table-column label="退款阶段，可选值：onsale/aftersale" align="center" prop="refundPhase" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['tao:refund:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['tao:refund:remove']"
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

    <!-- 添加或修改淘宝退款订单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="退款id" prop="refundId">
          <el-input v-model="form.refundId" placeholder="请输入退款id" />
        </el-form-item>
        <el-form-item label="淘宝交易单号" prop="tid">
          <el-input v-model="form.tid" placeholder="请输入淘宝交易单号" />
        </el-form-item>
        <el-form-item label="子订单号。如果是单笔交易oid会等于tid" prop="oid">
          <el-input v-model="form.oid" placeholder="请输入子订单号。如果是单笔交易oid会等于tid" />
        </el-form-item>
        <el-form-item label="买家昵称" prop="buyerNick">
          <el-input v-model="form.buyerNick" placeholder="请输入买家昵称" />
        </el-form-item>
        <el-form-item label="交易总金额。精确到2位小数;单位:元。如:200.07，表示:200元7分" prop="totalFee">
          <el-input v-model="form.totalFee" placeholder="请输入交易总金额。精确到2位小数;单位:元。如:200.07，表示:200元7分" />
        </el-form-item>
        <el-form-item label="支付给卖家的金额(交易总金额-退还给买家的金额)。精确到2位小数;单位:元。如:200.07，表示:200元7分" prop="payment">
          <el-input v-model="form.payment" placeholder="请输入支付给卖家的金额(交易总金额-退还给买家的金额)。精确到2位小数;单位:元。如:200.07，表示:200元7分" />
        </el-form-item>
        <el-form-item label="退还金额(退还给买家的金额)。精确到2位小数;单位:元。如:200.07，表示:200元7分" prop="refundFee">
          <el-input v-model="form.refundFee" placeholder="请输入退还金额(退还给买家的金额)。精确到2位小数;单位:元。如:200.07，表示:200元7分" />
        </el-form-item>
        <el-form-item label="退款申请时间。" prop="created">
          <el-input v-model="form.created" placeholder="请输入退款申请时间。" />
        </el-form-item>
        <el-form-item label="更新时间。" prop="modified">
          <el-input v-model="form.modified" placeholder="请输入更新时间。" />
        </el-form-item>
        <el-form-item label="退货数量" prop="num">
          <el-input v-model="form.num" placeholder="请输入退货数量" />
        </el-form-item>
        <el-form-item label="买家是否需要退货。可选值:true(是),false(否)" prop="hasGoodReturn">
          <el-input v-model="form.hasGoodReturn" placeholder="请输入买家是否需要退货。可选值:true(是),false(否)" />
        </el-form-item>
        <el-form-item label="退款原因" prop="reason">
          <el-input v-model="form.reason" placeholder="请输入退款原因" />
        </el-form-item>
        <el-form-item label="退款说明" prop="desc">
          <el-input v-model="form.desc" placeholder="请输入退款说明" />
        </el-form-item>
        <el-form-item label="物流公司" prop="logisticsCompany">
          <el-input v-model="form.logisticsCompany" placeholder="请输入物流公司" />
        </el-form-item>
        <el-form-item label="物流单号" prop="logisticsCode">
          <el-input v-model="form.logisticsCode" placeholder="请输入物流单号" />
        </el-form-item>
        <el-form-item label="${comment}" prop="sendTime">
          <el-input v-model="form.sendTime" placeholder="请输入${comment}" />
        </el-form-item>
        <el-form-item label="${comment}" prop="auditTime">
          <el-date-picker clearable
            v-model="form.auditTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择${comment}">
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
        <el-form-item label="${comment}" prop="address">
          <el-input v-model="form.address" placeholder="请输入${comment}" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="系统创建时间" prop="createOn">
          <el-input v-model="form.createOn" placeholder="请输入系统创建时间" />
        </el-form-item>
        <el-form-item label="店铺id" prop="shopId">
          <el-input v-model="form.shopId" placeholder="请输入店铺id" />
        </el-form-item>
        <el-form-item label="${comment}" prop="erpGoodsId">
          <el-input v-model="form.erpGoodsId" placeholder="请输入${comment}" />
        </el-form-item>
        <el-form-item label="${comment}" prop="erpGoodsSpecId">
          <el-input v-model="form.erpGoodsSpecId" placeholder="请输入${comment}" />
        </el-form-item>
        <el-form-item label="sku编号" prop="specNumber">
          <el-input v-model="form.specNumber" placeholder="请输入sku编号" />
        </el-form-item>
        <el-form-item label="退款阶段，可选值：onsale/aftersale" prop="refundPhase">
          <el-input v-model="form.refundPhase" placeholder="请输入退款阶段，可选值：onsale/aftersale" />
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
import { listRefund, getRefund, delRefund, addRefund, updateRefund } from "@/api/tao/refund";

export default {
  name: "Refund",
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
      // 淘宝退款订单表格数据
      refundList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        refundId: null,
        afterSalesType: null,
        tid: null,
        oid: null,
        buyerNick: null,
        totalFee: null,
        payment: null,
        refundFee: null,
        created: null,
        modified: null,
        orderStatus: null,
        status: null,
        goodStatus: null,
        num: null,
        hasGoodReturn: null,
        reason: null,
        desc: null,
        logisticsCompany: null,
        logisticsCode: null,
        sendTime: null,
        auditStatus: null,
        auditTime: null,
        receivedTime: null,
        address: null,
        createOn: null,
        shopId: null,
        erpGoodsId: null,
        erpGoodsSpecId: null,
        specNumber: null,
        refundPhase: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        afterSalesType: [
          { required: true, message: "类型不能为空", trigger: "change" }
        ],
        num: [
          { required: true, message: "退货数量不能为空", trigger: "blur" }
        ],
        auditStatus: [
          { required: true, message: "2已签收9供应商已退款不能为空", trigger: "change" }
        ],
        createOn: [
          { required: true, message: "系统创建时间不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询淘宝退款订单列表 */
    getList() {
      this.loading = true;
      listRefund(this.queryParams).then(response => {
        this.refundList = response.rows;
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
        refundId: null,
        afterSalesType: null,
        tid: null,
        oid: null,
        buyerNick: null,
        totalFee: null,
        payment: null,
        refundFee: null,
        created: null,
        modified: null,
        orderStatus: null,
        status: null,
        goodStatus: null,
        num: null,
        hasGoodReturn: null,
        reason: null,
        desc: null,
        logisticsCompany: null,
        logisticsCode: null,
        sendTime: null,
        auditStatus: null,
        auditTime: null,
        receivedTime: null,
        address: null,
        remark: null,
        createOn: null,
        shopId: null,
        erpGoodsId: null,
        erpGoodsSpecId: null,
        specNumber: null,
        refundPhase: null
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
      this.title = "添加淘宝退款订单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getRefund(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改淘宝退款订单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateRefund(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addRefund(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除淘宝退款订单编号为"' + ids + '"的数据项？').then(function() {
        return delRefund(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('tao/refund/export', {
        ...this.queryParams
      }, `refund_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
