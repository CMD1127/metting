<template>
    <div class="app-container">
      用户列表
      <br>
      <br>
       <!--查询表单-->
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item label="账号" style="margin-right: 50px;">
          <el-input v-model="userQuery.loginName" placeholder="请输入账号" />
        </el-form-item>
        <el-form-item label="用户名称" style="margin-right: 50px;"> 
          <el-input v-model="userQuery.userName" placeholder="请输入用户名称"/>
        </el-form-item>
        <el-form-item label="用户类型" style="margin-right: 150px;">
          <el-select v-model="userQuery.role" clearable placeholder="请选择用户类型">
            <el-option :value="0" label="普通用户"/>
            <el-option :value="1" label="管理员"/>
          </el-select>
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
        <el-table-column prop="loginName" label="账号" width="200" />
        <el-table-column prop="userName" label="用户名称" width="200" />
        <el-table-column label="用户类型" width="250">
          <template slot-scope="scope">
            {{ scope.row.role===0?'普通用户':'管理员' }}
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="添加时间" width="300"/>
        <el-table-column label="操作" width="200" align="center">
          <template slot-scope="scope">
            <router-link :to="'/user/edit/'+scope.row.loginName">
              <el-button type="primary" size="mini" icon="el-icon-edit">修改</el-button>
            </router-link>
            <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.loginName)">删除</el-button>
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
  // 引入调用teacher.js文件
  import user from '@/api/edu/user'
  export default {
      // 写核心代码
        data(){
            return {
                    list:null, // 查询之后接口返回集合
                    page:1, // 当前页
                    limit:7, // 每页记录书
                    total:0, // 总记录数
                    userQuery:{
                      loginName:"",
                      userName:"",
                      role:"",
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
              user.getUserListPage(this.page,this.limit,this.userQuery)
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
              this.userQuery={};
              //查询表单
              this.getList()
            },  
//删除用户
        removeDataById(loginName) {
            this.$confirm('此操作将永久删除用户信息,是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => { //确定执行then方法
                //调用接口
                user.deleteUser(loginName)
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