<template>
  <div class="app-container">
    <avue-crud :data="data" @row-save="save" :page="page" :option="option0" @on-load="onLoad" @search-change="search" @row-del="deleteData" @row-update="updateData"></avue-crud>
  </div>
</template>

<script>
export default {
  name: "supplierIndex",
  data() {
    return {
      page: {
        pageSize: 20,
        total:0
      },
      queryParams:{
        currentPage:1,
        pageSize:20
      },
      data: [],
      showBorder:true,
      showStripe:true,
      showPage:true
    }
  },
  computed: {
    option0() {
      return {
        searchMenuSpan: 8,
        border: this.showBorder,
        stripe: this.showStripe,
        showHeader: this.showHeader,
        index: true,
        indexLabel:'序号',
        size: this.sizeValue,
        selection: this.showCheckbox,
        page: this.showPage,
        align: "center",
        menuAlign: "center",
        column: [

          {
            label: "来源",
            prop: "source",
            search: true,
            type:'select',
            dicData:[{
              label:'拼多多PC',
              value:'PDD-PIFA'
            },{
              label:'拼多多',
              value:'PDD-YKD'
            },{
              label:'淘宝',
              value:'TAOBAO'
            }],
            // props: {
            //       label: 'dictLabel',
            //       value: 'dictValue'
            // },
            // dicUrl:'/system/dict/data/list?pageNum=1&pageSize=10&dictType=keyword_source',
            rules: [{
              required: true,
              message: "请选择来源",
              trigger: "blur"
            }]
          },
          {
            label: "关键词",
            prop: "word",
            search: true,
            rules: [{
              required: true,
              message: "请输入关键词",
              trigger: "blur"
            }]
          },
          {
            label: "备注",
            prop: "remark"
          },
          {
            label: "日期",
            prop: "date",
            type:'date',
            format:'yyyy-MM-dd',
            search: true,
            rules: [{
              required: true,
              message: "请选择日期",
              trigger: "blur"
            }]
          },
          {
            label: "创建日期",
            prop: "createTime",
            type:'date',
            format:'yyyy-MM-dd HH:mm:ss',
            display:false
          }
        ]
      };
    }
  },
}
</script>

<style scoped>

</style>
