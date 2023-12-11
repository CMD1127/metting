<template>
    <div class="app-container">
      人员列表
      <br>
      <br>
       <!--查询表单-->
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item label="姓名" style="margin-right: 50px;">
          <el-input v-model="employeeQuery.name" placeholder="请输入姓名" />
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
        <el-table-column prop="name" label="姓名" width="300" />
   <el-table-column
                    prop="sex"
                    label="性别"
                    show-overflow-tooltip
 
            >
            <template slot-scope="scope">
                <span v-if="scope.row.sex === 1">男</span>
                <span  v-else-if="scope.row.sex === 0">女</span>
            </template>
                
            </el-table-column>
        <el-table-column prop="age" label="年龄" width="300" />
        <el-table-column prop="createTime" label="创建时间" width="300"/>
        <el-table-column label="操作" width="300" align="center">
          <template slot-scope="scope">
            <router-link :to="'/employee/edit/'+scope.row.id">
              <el-button type="primary" size="mini" icon="el-icon-edit">修改</el-button>
            </router-link>
            <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除</el-button>
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
、
  <script>
  import emoployee from '@/api/edu/employee'
  export default {
      // 写核心代码
        data(){
            return {
                    list:null, // 查询之后接口返回集合
                    page:1, // 当前页
                    limit:7, // 每页记录书
                    total:0, // 总记录数
                    employeeQuery:{
                      name:''
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
              emoployee.getEmployeeListPage(this.page,this.limit,this.employeeQuery)
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
              this.employeeQuery={};
              //查询表单
              this.getList()
            },  
//删除用户
        removeDataById(id) {
            this.$confirm('此操作将永久删除范文库信息,是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => { //确定执行then方法
                //调用接口
                emoployee.delete(id)
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