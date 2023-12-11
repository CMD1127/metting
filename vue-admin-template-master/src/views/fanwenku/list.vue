<template>
    <div class="app-container">
      范文库列表
      <br>
      <br>
       <!--查询表单-->
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item label="范文库名称" style="margin-right: 50px;">
          <el-input v-model="fawnenkuQuery.fanwenkuName" placeholder="请输入范文库名称" />
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
        <el-table-column prop="fanwenkuId" label="范文库编码" width="300" />
        <el-table-column prop="fanwenkuName" label="范文库名称" width="300" />
        <el-table-column prop="createTime" label="添加时间" width="300"/>
        <el-table-column label="操作" width="300" align="center">
          <template slot-scope="scope">
            <router-link :to="'/fanwenku/edit/'+scope.row.fanwenkuId">
              <el-button type="primary" size="mini" icon="el-icon-edit">修改</el-button>
            </router-link>
            <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.fanwenkuId)">删除</el-button>
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
  
    </div>
  </template>
  
  
  
  
  <script>
  
  import fanwenku from '@/api/edu/fanwenku'
  export default {
      // 写核心代码
        data(){
            return {
                    list:null, // 查询之后接口返回集合
                    page:1, // 当前页
                    limit:7, // 每页记录书
                    total:0, // 总记录数
                    fawnenkuQuery:{
                      fanwenkuName:''
                    } // 条件封装对象
  
            }
        },
        created(){  //页面渲染之前执行 一般调用mehods的方法
          this.getList()
          this.resetData()
          
        },
        methods:{    //创建具体的方法 调用teacher.js的方法
            getList(page=1){
              this.page=page
              fanwenku.getFanwenkuListPage(this.page,this.limit,this.fawnenkuQuery)
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
              this.fawnenkuQuery={};
              //查询表单
              this.getList()
            },  
//删除用户
        removeDataById(fanwenkuId) {
            this.$confirm('此操作将永久删除范文库信息,是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => { //确定执行then方法
                //调用接口
                fanwenku.deleteFanwenku(fanwenkuId)
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