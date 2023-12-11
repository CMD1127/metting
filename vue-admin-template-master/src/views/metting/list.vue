<template>
    <div class="app-container">
      会议室列表
      <br>
      <br>
       <!--查询表单-->
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item label="会议室名称" style="margin-right: 50px;">
          <el-input v-model="mettingQuery.name" placeholder="请输入会议室名称" />
        </el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
        <el-button type="default" @click="resetData()">清空</el-button>
      </el-form>
      <!-- 表格 -->
      <el-table
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
        <el-table-column prop="name" label="会议室名称" width="300" />
        <el-table-column prop="mettingNumber" label="会议室号" width="300" />
        <el-table-column
                    prop="isFlag"
                    label="是否分配"
                    show-overflow-tooltip
 
            >
            <template slot-scope="scope">
                <span v-if="scope.row.isFlag === 1">已分配</span>
                <span  v-else-if="scope.row.isFlag === 0">未分配</span>
            </template>
                
            </el-table-column>
        <el-table-column prop="createTime" label="添加时间" width="300"/>
        <el-table-column label="操作" width="300" align="center">
          <template slot-scope="scope">
            <router-link :to="'/metting/edit/'+scope.row.id">
              <el-button type="primary" size="mini" icon="el-icon-edit">修改</el-button>
            </router-link>
            <el-button type="primary" size="mini" icon="el-icon-delete" @click="dialogFormV(scope.row)">分配</el-button>
    
            <el-button v-if="scope.row.employeeId==null || scope.row.employeeId==''" type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-dialog title="会议室分配" :visible.sync="dialogFormVisible" >
  <el-form label-width="120px">
    <el-form-item label="活动区域" :label-width="formLabelWidth">
      <el-select v-model="employeeId" clearable placeholder="请选择">
    <el-option
      v-for="item in employeeList"
      :key="item.id"
      :label="item.name"
      :value="item.id">
    </el-option>
  </el-select>
    </el-form-item>
  </el-form>
  <div slot="footer" class="dialog-footer">
    <el-button @click="dialogFormVisible = false">取 消</el-button>
    <el-button type="primary" @click="update()">确 定</el-button>
  </div>
</el-dialog>

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
  
  import metting from '@/api/edu/metting'
    import emoployee from '@/api/edu/employee'
  export default {
      // 写核心代码
        data(){
            return {
              employeeList:[],
              employeeId:'',
              mettingRow:{},
              formLabelWidth: '120px',
              employeeQuery:{},
              dialogFormVisible: false,
                    list:null, // 查询之后接口返回集合
                    page:1, // 当前页
                    limit:7, // 每页记录书
                    total:0, // 总记录数
                    mettingQuery:{
                      name:''
                    } // 条件封装对象
  
            }
        },
        created(){  //页面渲染之前执行 一般调用mehods的方法
          this.getList()
          this.resetData()
          this.getEmployeeList()
        },
        methods:{   
           // 修改
      update() {
        debugger
        this.mettingRow.employeeId=this.employeeId
        if(this.employeeId==null || this.employeeId==''){
          this.mettingRow.isFlag=0
        }else{
          this.mettingRow.isFlag=1
        }
        metting.update(this.mettingRow)
          .then(reponse => {
            //提示
            this.$message({
              type: 'success',
              message: '分配成功!'
            })
            //跳转列表页面，使用路由跳转方式实现
            this.getList()
            this.dialogFormVisible = false
          })
      },
          dialogFormV(row){
            debugger
            this.employeeId=row.employeeId
            this.dialogFormVisible = true
            this.mettingRow=row
          },
          getEmployeeList(page=1){
              this.page=page
              emoployee.getEmployeeListPage(this.page,this.limit,this.employeeQuery)
              .then(response=>{//请求成功
                this.employeeList=response.data.rows
                this.total=response.data.total
                console.log(this.list)
                console.log(this.total)
              }) 
              .catch(error=>{
                console.log(error);
              }) //请求失败
            },
            getList(page=1){
              this.page=page
              metting.getMettingListPage(this.page,this.limit,this.mettingQuery)
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
              this.mettingQuery={};
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
                metting.delete(id)
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