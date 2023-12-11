<template>
    <div class="app-container">
      会议室添加
      <br>
      <br>
      <el-form label-width="120px">
        <el-form-item label="范文库名称">
          <el-input v-model="metting.name" />
        </el-form-item>
        <el-form-item label="范文库名称">
          <el-input v-model="metting.mettingNumber" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="saveOrUpdate">保存</el-button>
        </el-form-item>
      </el-form>
    </div>
  </template>
  <script>
   import metting from '@/api/edu/metting'
  export default {
    data() {
      return {
        metting: {}
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
      if (this.$route.params && this.$route.params.id) {
        const id = this.$route.params.id
        this.getMetting(id)
      }else{
            this.metting={}
      }
      },
      //根据id查询
      getMetting(id) {
        metting.getById(id)
          .then(response => {
            this.metting = response.data.item
          })
      },
      // 修改
      update() {
        metting.update(this.metting)
          .then(reponse => {
            //提示
            this.$message({
              type: 'success',
              message: '修改成功!'
            })
            //跳转列表页面，使用路由跳转方式实现
            this.$router.push({ path: '/metting/list' })
          })
      },
      // 添加
      save() {
        metting.save(this.metting)
          .then(reponse => {
            //提示
            this.$message({
              type: 'success',
              message: '添加成功!'
            })
            //跳转列表页面，使用路由跳转方式实现
            this.$router.push({ path: '/metting/list' })
          })
      },
      //添加
      saveOrUpdate() {
        //判断添加还是修改
        if (!this.metting.id) { //没有id，做添加
          this.save();
        } else {//修改
          this.update()
        }
      }
    }
  }
  </script>