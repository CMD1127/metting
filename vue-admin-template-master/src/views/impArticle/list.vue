<template>
    <div class="app-container">
      待改进文章列表
      <br>
      <br>
       <!--查询表单-->
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item label="待文章名称" style="margin-right: 50px;">
          <el-input v-model="impArticleQuery.impArticleName" placeholder="请输入文章内容"  />
        </el-form-item>
        <el-form-item label="待文章内容" style="margin-right: 50px;">
          <el-input v-model="impArticleQuery.content" placeholder="请输入文章内容"  />
        </el-form-item>
  
        <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
        <el-button type="default" @click="resetData()">清空</el-button>
        <el-button type="default" @click="uploadFlag">上传</el-button>

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
        <el-table-column prop="impArticleId" label="待改进文章编码" width="200" />
        <el-table-column prop="impArticleName" label="待改进文章名称" width="200" />
        <el-table-column prop="content" label="待文章内容" width="250" :show-overflow-tooltip="true">
          <template slot-scope="scope">
             <a
               href="javascript:void(0);"
               @click="openDialog(scope.row)"
               class="abAccount"
               title="点击文章内容"
             >{{scope.row.content}}</a>
           </template>
        </el-table-column>
        <el-table-column prop="createTime" label="最近更改时间" width="170"/>
        <el-table-column label="操作" width="300" align="center">
          <template slot-scope="scope">
           <router-link :to="'/impArticle/edit/'+scope.row.impArticleId">
              <el-button type="primary" size="mini" icon="el-icon-edit">修改</el-button>
            </router-link>
            <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.impArticleId)">删除</el-button>
          </template>
        </el-table-column>
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
    <el-form-item label="待改进文章名称:">
      <span>{{form.impArticleName}}</span>
    </el-form-item>
    <el-form-item label="文章内容">
    <el-input type="textarea" :rows="10" v-model="form.content" readonly="readonly" style="resize: none; border: none;"></el-input>
</el-form-item>
  </el-form>
</el-dialog>

<el-dialog title="文章上传" :visible.sync="dialogUploadFormVisible">
  <el-form :model="uploadForm">
   
        <el-form-item label="待改进文章名称" :label-width="formLabelWidth">
      <el-input v-model="uploadForm.impArticleName" autocomplete="off" style="width: 350px"></el-input>
    </el-form-item>
       
      
    <el-form-item label="文章上传" style="margin-left: 50px">
      <el-upload
          class="upload-demo"
          ref="upload"
          action=""
          :on-change="handleChange"
          :on-remove="handleRemove"
          :file-list="fileList"
          :limit="3"
          :on-exceed="handleExceed"
          multiple
          :auto-upload="false">
        <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
</el-upload>


    </el-form-item>

  </el-form>
  <div slot="footer" class="dialog-footer">
    <el-button @click="dialogUploadFormVisible = false">取 消</el-button>
    <el-button type="primary" @click="upload">确 定</el-button>
  </div>
</el-dialog>
    </div>
  </template>
  <script>
  
  import impArticle from '@/api/edu/impArticle'
  export default {
      // 写核心代码
        data(){
            return {
              formLabelWidth: '120px',
              uploadForm:{
                impArticleName:null,
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
                    impArticleQuery:{
                      impArticleName:'',
                      content:''
                    } // 条件封装对象
  
            }
        },
        created(){  //页面渲染之前执行 一般调用mehods的方法
           this.getList()
           this.resetData()
        },
        methods:{ 
          uploadFlag(){
            this.uploadForm.fileList =[]
            this.fileList=[]
            this.uploadForm.impArticleName=''
            this.dialogUploadFormVisible = true
          },
          upload(){
            debugger
            let list=this.uploadForm.fileList
            let impArticleName=this.uploadForm.impArticleName
            if (this.uploadForm.fileList.length !== 0) {
              debugger
        const formData = new FormData()
        this.uploadForm.fileList.forEach((item, index) => {
          formData.append('file', item.raw)
         
          debugger
          console.log(item.raw, item)
        })
        impArticle.uploadefile(formData,impArticleName)
              .then(response=>{//请求成功
             
                //提示
                this.$message({
                            type: 'success',
                            message: '上传成功!'
                        })
                        
                this.dialogUploadFormVisible = false
                //刷新页面
                        this.getList(1)
              }) 
              .catch(error=>{
                console.log(error);
                //提示
                this.$message({
                            type: 'success',
                            message: '上传失败!'
                        })
              }) //请求失败
      } 
           
          },
          //上传
       // 文件状态改变时的钩子，添加文件、上传成功和上传失败时都会被调用
    handleChange(file, fileList) {
      // 对选中的文件做判断
      // if (file.raw.type !== 'text/plain') {
      //   this.$refs.upload.handleRemove(file)
      //   return
      // }
      this.uploadForm.fileList = fileList
    },
    // 文件列表移除文件时的钩子
    handleRemove(file, fileList) {
      console.log('移除', file, fileList)
      this.uploadForm.fileList = fileList
    },
    // 文件超出个数限制时的钩子
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);

    },

          openDialog(row){
            debugger
            this.dialogFormVisible=true
            this.form.content=row.content
            this.form.impArticleName=row.impArticleName
          },

            getList(page=1){
              this.page=page
              impArticle.getImpArticleListPage(this.page,this.limit,this.impArticleQuery)
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
              this.impArticleQuery={};
              //查询表单
              this.getList()
            },  
//删除用户
        removeDataById(impArticleId) {
            this.$confirm('此操作将永久删除文章信息,是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => { //确定执行then方法
                //调用接口
                impArticle.deleteImpArticle(impArticleId)
                    .then(response => {
                        //提示
                        this.$message({
                            type: 'success',
                            message: '删除成功!'
                        })
                        //刷新页面
                        this.getList(1)
                    })
            })
        }
        }
  }
  </script>
  <style>
.el-tooltip__popper{
    max-width:30%;
 
  }
 
</style>
