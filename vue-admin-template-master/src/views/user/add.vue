<template>
    <div class="app-container">
      医院设置添加
      <br>
      <br>
      <el-form label-width="120px">
        <el-form-item label="账号">
          <el-input v-model="user.loginName" />
        </el-form-item>
        <el-form-item label="用户名称">
          <el-input v-model="user.userName" />
        </el-form-item>
     
        <!-- <el-form-item label="文章内容">
         <mavon-editor ref="md" v-model="content" :ishljs="true"  />
 </el-form-item> -->
        <el-form-item label="密码">
          <el-input type="password" v-model="user.password" />
        </el-form-item>
        <el-form-item label="用户类型" >
          <el-select v-model="user.role" clearable placeholder="请选择用户类型">
            <el-option :value="0" label="普通用户"/>
            <el-option :value="1" label="管理员"/>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="saveOrUpdate">保存</el-button>
        </el-form-item>
      </el-form>
    </div>
  </template>
  <script>
  import user from '@/api/edu/user'
  export default {
    data() {
      return {
        user: {}
      }
    },
    watch: {
    $route(to, from) {
      this.init()
    }
  },
    created() {
      this.init();
    },
    methods: {
      init(){
//页面渲染之前执行
      //获取路由id值 调用接口得到医院设置信息
      if (this.$route.params && this.$route.params.loginName) {
        const loginName = this.$route.params.loginName
        this.getUser(loginName)
      }else{
            this.user={}
      }
      },
      //根据id查询
      getUser(loginName) {
        user.getById(loginName)
          .then(response => {
            this.user = response.data.item
          })
      },
      // 修改
      update() {
        user.updateUser(this.user)
          .then(reponse => {
            //提示
            this.$message({
              type: 'success',
              message: '修改成功!'
            })
            //跳转列表页面，使用路由跳转方式实现
            this.$router.push({ path: '/user/list' })
          })
      },
      // 添加
      save() {
        user.save(this.user)
          .then(reponse => {
            //提示
            this.$message({
              type: 'success',
              message: '添加成功!'
            })
            //跳转列表页面，使用路由跳转方式实现
            this.$router.push({ path: '/user/list' })
          })
      },
      //添加
      saveOrUpdate() {
        //判断添加还是修改
        if (!this.user.createTime) { //没有id，做添加
          this.save();
        } else {//修改
          this.update()
        }
      }
    }
  }
  </script>