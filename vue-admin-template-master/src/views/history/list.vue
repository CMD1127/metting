<template>
    <div class="app-container">
      文章最近操作列表
      <br>
      <br>
       <!--查询表单-->
      <el-form :inline="true" class="demo-form-inline">
      
        <el-form-item label="范文库名称" style="margin-right: 50px;">
          <el-select v-model="articleQuery.fanwenkuId" placeholder="请选择范文名称">
    <el-option
      v-for="item in fanwenkuList"
      :key="item.fanwenkuId"
      :label="item.fanwenkuName"
      :value="item.fanwenkuId">
    </el-option>
  </el-select>
        </el-form-item>
        <el-form-item label="文章内容" style="margin-right: 50px;">
          <el-input v-model="articleQuery.content" placeholder="请输入文章内容"  />
        </el-form-item>
  
        <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
        <el-button type="default" @click="resetData()">清空</el-button>

      </el-form>
      <!-- 表格 -->
      <el-table
        v-loading="listLoading"
        :data="list"
        element-loading-text="数据加载中"
        border
        fit
        highlight-current-row>
        <el-table-column
          label="序号"
          width="70"
          align="center">
          <template slot-scope="scope">
            {{ (page - 1) * limit + scope.$index + 1 }}
          </template>
        </el-table-column>
        <el-table-column prop="fanwenkuName" label="范文库名称" width="400" />
        <el-table-column prop="content" label="文章内容" width="400" :show-overflow-tooltip="true">
          <template slot-scope="scope">
             <a
               href="javascript:void(0);"
               @click="openDialog(scope.row)"
               class="abAccount"
               title="点击文章内容"
             >{{scope.row.content}}</a>
           </template>
        </el-table-column>
        <el-table-column prop="updateTime" label="最近操作时间" width="400"/>
      </el-table>
  <!-- 分页 -->
     <el-pagination
        :current-page="page"
        :page-size="limit"
        :total="total"
        style="padding: 30px 0; text-align: center;"
        layout="total, prev, pager, next, jumper"
        @current-change="getList"
      /> 
  <!-- Form -->

<el-dialog title="文章内容详情" :visible.sync="dialogFormVisible">
  <el-form :model="form">
    <el-form-item label="范文库名称:">
      <span>{{form.fanwenkuName}}</span>
    </el-form-item>
    <el-form-item label="文章内容">
    <el-input type="textarea" :rows="10" v-model="form.content" readonly="readonly" style="resize: none; border: none;"></el-input>
</el-form-item>
  </el-form>
</el-dialog>
    </div>
  </template>
  <script>
  
  import article from '@/api/edu/article'
  export default {
      // 写核心代码
        data(){
            return {
              uploadForm:{
                fanwenkuId:null,
                fileList: []
              },
              dialogFormVisible: false,
              dialogUploadFormVisible:false,
              fanwenkuId:'',
              form:{
               
                fanwenkuName:'',
                content:''
              },
              fanwenkuList:null,
                    list:null, // 查询之后接口返回集合
                    page:1, // 当前页
                    limit:7, // 每页记录书
                    total:0, // 总记录数
                    articleQuery:{
                      fanwenkuId:'',
                      content:''
                    } // 条件封装对象
  
            }
        },
        created(){  //页面渲染之前执行 一般调用mehods的方法
           this.getList()
           this.resetData()
          this.getFanwenkuList();
        },
        methods:{ 
          uploadFlag(){
            this.uploadForm.fileList =[]
            this.fileList=[]
            this.uploadForm.fanwenkuId=''
            this.dialogUploadFormVisible = true
          },


          openDialog(row){
            debugger
            this.dialogFormVisible=true
            this.form.content=row.content
            this.form.fanwenkuName=row.fanwenkuName
          } ,
           // 查询全部范文库名称
           getFanwenkuList(){
            article.fanwenkuFindAll()
              .then(response=>{//请求成功
                this.fanwenkuList=response.data.items
              }) 
              .catch(error=>{
                console.log(error);
              }) //请求失败
           },
            getList(page=1){
              this.page=page
              article.pageHistoryArticleCondition(this.page,this.limit,this.articleQuery)
              .then(response=>{//请求成功
             
                this.list=response.data.rows
                this.total=response.data.total
                console.log(this.list)
                console.log(this.total)
              }) 
              .catch(error=>{
                console.log(error);
              }) //请求失败
            },
            resetData(){
              // 清空表单
              this.articleQuery={};
              //查询表单
              this.getList()
            },  
        }
  }
  </script>
  <style>
.el-tooltip__popper{
    max-width:30%;
 
  }
 
</style>
