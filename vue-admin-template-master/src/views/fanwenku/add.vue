<template>
    <div class="app-container">
      人员添加
      <br>
      <br>
      <el-form label-width="120px">
        <el-form-item label="范文库名称">
          <el-input v-model="fanwenku.fanwenkuName" />
        </el-form-item>
    
     
        <!-- <el-form-item label="文章内容">
         <mavon-editor ref="md" v-model="content" :ishljs="true"  />
 </el-form-item> -->
        <el-form-item>
          <el-button type="primary" @click="saveOrUpdate">保存</el-button>
        </el-form-item>
      </el-form>
    </div>
  </template>
  <script>
   import fanwenku from '@/api/edu/fanwenku'
  export default {
    data() {
      return {
        fanwenku: {}
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
      if (this.$route.params && this.$route.params.fanwenkuId) {
        const fanwenkuId = this.$route.params.fanwenkuId
        this.getFanwenku(fanwenkuId)
      }else{
            this.fanwenku={}
      }
      },
      //根据id查询
      getFanwenku(fanwenkuId) {
        fanwenku.getById(fanwenkuId)
          .then(response => {
            this.fanwenku = response.data.item
          })
      },
      // 修改
      update() {
        fanwenku.updateFanwenku(this.fanwenku)
          .then(reponse => {
            //提示
            this.$message({
              type: 'success',
              message: '修改成功!'
            })
            //跳转列表页面，使用路由跳转方式实现
            this.$router.push({ path: '/fanwenku/list' })
          })
      },
      // 添加
      save() {
        fanwenku.save(this.fanwenku)
          .then(reponse => {
            //提示
            this.$message({
              type: 'success',
              message: '添加成功!'
            })
            //跳转列表页面，使用路由跳转方式实现
            this.$router.push({ path: '/fanwenku/list' })
          })
      },
      //添加
      saveOrUpdate() {
        //判断添加还是修改
        if (!this.fanwenku.fanwenkuId) { //没有id，做添加
          this.save();
        } else {//修改
          this.update()
        }
      }
    }
  }
  </script>