<template>
  <div class="app-container">
    <el-form ref="form" :model="form" :rules="rules" label-width="108px">

        <el-form-item label="商品分类" prop="categoryId">
          <treeselect :options="dataList" placeholder="请选择上级菜单" v-model="form.categoryId" style="width:220px"/>
        </el-form-item> 
        <el-form-item label="供应商id" prop="supplierId">
          <!-- <el-input v-model="form.supplierId" placeholder="请输入供应商id" /> -->
          <el-select v-model="form.supplierId" filterable  placeholder="请选择供应商名称">
            <el-option v-for="item in supplierList" :key="item.id" :label="item.name" :value="item.id">
          </el-option>
        </el-select>
        </el-form-item>
        <el-form-item label="商品名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入商品名称" />
        </el-form-item>
        <el-form-item label="商品图片" prop="image">
          <!-- <image-upload v-model="form.image"/> -->
           <el-input v-model="form.image" placeholder="请输入商品图片" />
        </el-form-item>
        <el-form-item label="商品编号" prop="number" >
          <el-input v-model="form.number" placeholder="请输入商品编号" style="width:220px"/>
        </el-form-item>
         <el-form-item label="预计采购价格" prop="purPrice">
          <el-input type="number" v-model.number="form.purPrice" placeholder="请输入预计采购价格" style="width:220px"/>
        </el-form-item>
        <!-- <el-form-item label="建议批发价" prop="wholePrice">
          <el-input type="number" v-model.number="form.wholePrice" placeholder="请输入建议批发价" style="width:220px"/>
        </el-form-item>
        <el-form-item label="建议零售价" prop="retailPrice">
          <el-input type="number" v-model.number="form.retailPrice" placeholder="请输入建议零售价" style="width:220px"/>
        </el-form-item> -->
        <el-form-item label="单位名称" prop="unitName">
          <el-input v-model="form.unitName" placeholder="请输入单位名称" style="width:220px" />
        </el-form-item>
        <el-form-item label="条码" prop="barCode">
          <el-input v-model="form.barCode" placeholder="请输入条码" style="width:220px"/>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <!-- <el-form-item label="衣长/裙长/裤长" prop="length">
          <el-input v-model="form.length" placeholder="请输入衣长/裙长/裤长" />
        </el-form-item>
        <el-form-item label="高度/袖长" prop="height">
          <el-input v-model="form.height" placeholder="请输入高度/袖长" />
        </el-form-item>
        <el-form-item label="宽度/胸阔(围)" prop="width">
          <el-input v-model="form.width" placeholder="请输入宽度/胸阔(围)" />
        </el-form-item>
        <el-form-item label="肩阔" prop="width1">
          <el-input v-model="form.width1" placeholder="请输入肩阔" />
        </el-form-item>
        <el-form-item label="腰阔" prop="width2">
          <el-input v-model="form.width2" placeholder="请输入腰阔" />
        </el-form-item>
        <el-form-item label="臀阔" prop="width3">
          <el-input v-model="form.width3" placeholder="请输入臀阔" />
        </el-form-item>
        <el-form-item label="重量" prop="weight">
          <el-input v-model="form.weight" placeholder="请输入重量" />
        </el-form-item>
        <el-form-item label="0启用   1禁用" prop="disable">
          <el-input v-model="form.disable" placeholder="请输入0启用   1禁用" />
        </el-form-item>
        <el-form-item label="保质期" prop="period">
          <el-input v-model="form.period" placeholder="请输入保质期" />
        </el-form-item> -->
       
        <!-- <el-form-item label="单位成本" prop="unitCost">
          <el-input v-model="form.unitCost" placeholder="请输入单位成本" />
        </el-form-item> -->
        
       <!--  <el-form-item label="品牌id" prop="brandId">
          <el-input v-model="form.brandId" placeholder="请输入品牌id" />
        </el-form-item> -->
        <!-- <el-form-item label="属性1：季节" prop="attr1">
          <el-input v-model="form.attr1" placeholder="请输入属性1：季节" />
        </el-form-item>
        <el-form-item label="属性2：分类" prop="attr2">
          <el-input v-model="form.attr2" placeholder="请输入属性2：分类" />
        </el-form-item>
        <el-form-item label="属性3：风格" prop="attr3">
          <el-input v-model="form.attr3" placeholder="请输入属性3：风格" />
        </el-form-item>
        <el-form-item label="属性4：年份" prop="attr4">
          <el-input v-model="form.attr4" placeholder="请输入属性4：年份" />
        </el-form-item>
        <el-form-item label="属性5：面料" prop="attr5">
          <el-input v-model="form.attr5" placeholder="请输入属性5：面料" />
        </el-form-item> -->
        <el-form-item label="外链url" prop="linkUrl">
          <el-input v-model="form.linkUrl" placeholder="请输入内容" />
        </el-form-item>
       <!--  <el-form-item label="最低库存" prop="lowQty">
          <el-input v-model="form.lowQty" placeholder="请输入最低库存" />
        </el-form-item>
        <el-form-item label="最高库存" prop="highQty">
          <el-input v-model="form.highQty" placeholder="请输入最高库存" />
        </el-form-item> -->
        <el-form-item label="发货地" prop="provinces">
          <el-cascader style="width:250px"
            size="large"
            :options="pcaTextArr"
            v-model="form.provinces">
          </el-cascader>
        </el-form-item>
        <el-form-item label="商品规格">
          <el-row :gutter="10" class="mb8" >
            <el-col :span="1.5">颜色：</el-col>
            <el-col :span="20">
              <treeselect :options="colorList" placeholder="颜色" v-model="form.colorValues" :normalizer="normalizer"  @input="onSpecChange" :multiple="true" />
            </el-col>
          </el-row>
          <el-row :gutter="10" class="mb8" >
            <el-col :span="1.5">尺码：</el-col>
            <el-col :span="20">
              <treeselect :options="sizeList" placeholder="尺码" v-model="form.sizeValues" :normalizer="normalizer" @input="onSpecChange" :multiple="true" />
            </el-col>
          </el-row>
          <el-row :gutter="10" class="mb8" >
            <el-col :span="1.5">款式：</el-col>
            <el-col :span="20">
              <treeselect :options="styleList" placeholder="款式" v-model="form.styleValues" :normalizer="normalizer" @input="onSpecChange" :multiple="true" />
            </el-col>
          </el-row>
          
        </el-form-item>
        <!-- <el-divider content-position="center" style="margin-left: 98px;">商品信息</el-divider> -->
        
        <el-table style="margin-left: 108px;" :data="form.specList" :row-class-name="rowSShopOrderItemIndex" ref="sShopOrderItem">
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="颜色" prop="color" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.colorValue" disabled placeholder="颜色" />
            </template>
          </el-table-column>
          <el-table-column label="尺码" prop="size" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.sizeValue" disabled placeholder="尺码" />
            </template>
          </el-table-column>
          <el-table-column label="款式" prop="style" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.styleValue" disabled placeholder="款式" />
            </template>
          </el-table-column>
          <el-table-column label="规格编码" prop="specNum" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.specNum" placeholder="规格编码" />
            </template>
          </el-table-column>
          <el-table-column label="预计采购价" prop="purPrice" width="150">
            <template slot-scope="scope">
              <el-input v-model.number="scope.row.purPrice" placeholder="预计采购价" />
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer" style="margin-left: 108px;margin-top:20px;margin-bottom: 50px;">
        <el-button type="primary" @click="submitForm">添加商品</el-button>
        <!-- <el-button @click="cancel">取 消</el-button> -->
      </div>
  </div>
</template>

<script>
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import { listCategory } from "@/api/goods/category";
import { listSupplier } from "@/api/scm/supplier";
import { listCategoryAttributeValue } from "@/api/goods/categoryAttributeValue";
import { addGoods } from "@/api/goods/goods";

import {
  provinceAndCityData,
  pcTextArr,
  regionData,
  pcaTextArr,
  codeToText,
} from "element-china-area-data";

export default {
  name: "OrderCreate",
  components: { Treeselect },
  data() {
    return {
      // 表单参数
      form: {
        colorValues:undefined,
        sizeValues:undefined,
        styleValues:undefined,
        number:'',
        specList:[],
        provinces: []
      },
      supplierList: [],
      pcaTextArr,
      dataList:[{
          id: 'fruits',
          label: 'Fruits',
          children: [],
      }],
      // 表单校验
      rules: {
        categoryId: [{ required: true, message: '请选择分类' }],
        supplierId: [{ required: true, message: '请选择供应商' }],
        name: [{ required: true, message: '商品名不能为空' }],
        image: [{ required: true, message: '商品图片不能为空' }],
        number: [{ required: true, message: '商品编码不能为空' }],
        purPrice: [{ required: true, message: '请填写预计采购价' }],
        
      },
      // 子表选中数据
      checkedSShopOrderItem: [],
      // 颜色
      colorList:[],
      // 尺码
      sizeList:[],
      //款式
      styleList:[],
    };
  },
  created() {
    this.getCategoryList()
    listSupplier({}).then(response => {
      this.supplierList = response.rows;
      // this.supplierLoading = false;
    });
    listCategoryAttributeValue({categoryAttributeId:114}).then(resp=>{
      this.colorList = resp.rows
    })
    listCategoryAttributeValue({categoryAttributeId:115}).then(resp=>{
      this.sizeList = resp.rows
    })
    listCategoryAttributeValue({categoryAttributeId:116}).then(resp=>{
      this.styleList = resp.rows
    })
  },
  methods: {
    normalizer(node) {
      return {
        id: node.id,
        label: node.value
      };
    },
    buildTree(list, parentId) {
      let tree = [];
      for (let i = 0; i < list.length; i++) {
        if (list[i].parentId === parentId) {
          let node = {
            id: list[i].id,
            label: list[i].name,
            children: this.buildTree(list, list[i].id)
          };
          tree.push(node);
        }
      }
      return tree;
    },
    /** 查询商品分类列表 */
    getCategoryList() {
      // this.loading = true;
      listCategory(this.queryParams).then(response => {
        this.dataList = this.buildTree(response.rows,0)
        // console.log("构建后的list",this.categoryList)
        // this.total = response.total;
        this.loading = false;
      });
    },
    onSpecChange(selected){
      // console.log('=====选择了=======',selected)
      // console.log('=======颜色：====',this.form.colorValues)
      // console.log('=======尺码：====',this.form.sizeValues)
      // console.log('=======款式：====',this.form.styleValues)

      //组合
      if(this.form.colorValues){
        this.form.specList = []
        // this.form.specList.push()
        if(this.form.sizeValues && this.form.styleValues){
          console.log('====颜色、尺码、款式===')
          this.form.colorValues.forEach(c=>{
              const color = this.colorList.find(x=>x.id === c)
              this.form.sizeValues.forEach(s=>{
                const size = this.sizeList.find(y=>y.id === s)
                this.form.styleValues.forEach(st=>{
                const style = this.styleList.find(z=>z.id === st)
                 const spec = {
                  colorId:c,
                  colorValue:color.value,
                  sizeId:s,
                  sizeValue:size.value,
                  styleId:st,
                  styleValue:style.value,
                  specNum:this.form.number+color.skuCode+size.skuCode+style.skuCode
                }
                this.form.specList.push(spec)
                })
              })
            })

        }else {
          // 有一个没有选择
          if(this.form.sizeValues){
            console.log('====颜色、尺码===')
            this.form.colorValues.forEach(c=>{
              const color = this.colorList.find(x=>x.id === c)
              this.form.sizeValues.forEach(s=>{
                const size = this.sizeList.find(y=>y.id === s)
                 const spec = {
                  colorId:c,
                  colorValue:color.value,
                  sizeId:s,
                  sizeValue:size.value,
                  styleId:null,
                  styleValue:'',
                  specNum:this.form.number+color.skuCode+size.skuCode
                }
                this.form.specList.push(spec)
              })
            })
          }else if(this.form.styleValues){
            // 选择了款式
            console.log('====颜色、款式===')

          }else{
            console.log('====颜色===')
            this.form.colorValues.forEach(x=>{
              const color = this.colorList.find(c=>c.id === x)
              const spec = {
                colorId:x,
                colorValue:color.value,
                sizeId:null,
                sizeValue:'',
                styleId:null,
                styleValue:'',
                specNum:this.form.number+color.skuCode
              }
              this.form.specList.push(spec)
            })
          }
        }
      }else{
        this.$modal.msgError("必须选择【颜色】")
        this.form.sizeValues = []
        this.form.styleValues = []
      }


      
    },
    /** ${subTable.functionName}序号 */
    rowSShopOrderItemIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** 提交按钮 */
    submitForm() {
      console.log('=====添加商品===',this.form)
      this.$refs["form"].validate(valid => {
        if (valid) {

          // 判断规格 specList
          if(!this.form.specList || this.form.specList.length === 0){
            this.$modal.msgError("请添加商品规格")
            return
          }else{
            for(let i=0;i<this.form.specList.length;i++){
              const sp = this.form.specList[i]
              if(!sp.specNum){
                this.$modal.msgError("商品规格编码不能为空")
                return
              }
            }
            
          } 

          addGoods(this.form).then(response => {
              this.$modal.msgSuccess("商品添加成功");
              // 调用全局挂载的方法,关闭当前标签页
              this.$store.dispatch("tagsView/delView", this.$route);
              this.$router.push('/goods/goods_list');
            });
        //   this.form.province = this.form.provinces[0]
        //   this.form.city = this.form.provinces[1]
        //   this.form.town = this.form.provinces[2]

        //   if(this.form.itemList && this.form.itemList.length >0){
        //     this.form.itemList.forEach(x=>{
        //       if(!x.goodsId || !x.quantity){
        //         this.$modal.msgError("请完善商品信息");
        //         return 
        //       }
        //     })
   
        //     console.log('======创建订单=====',this.form)
        //     addOrder(this.form).then(response => {
        //       this.$modal.msgSuccess("订单创建成功");
        //       // 调用全局挂载的方法,关闭当前标签页
        //       this.$store.dispatch("tagsView/delView", this.$route);
        //       this.$router.push('/sale/order/list');
        //     });
          
        // }else{
        //   this.$modal.msgError("请添加商品");
        // }
          
        }
      });
    }
  }
};

</script>