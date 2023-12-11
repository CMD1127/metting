<template>
    <div class="app-container">
      文章添加
      <br>
      <br>
      <el-form label-width="120px">
        <el-form-item label="范文库名称">
          <el-input v-model="impArticle.impArticleName" />
        </el-form-item>
    
    
        <el-form-item label="文章内容">
         <mavon-editor ref="md" v-model="impArticle.content" :ishljs="true"  />
 </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="saveOrUpdate">保存</el-button>
        </el-form-item>
      </el-form>
    </div>
  </template>
  <script>
  import impArticle from '@/api/edu/impArticle'
  export default {
    data() {
      return {
        impArticle: {}
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
      if (this.$route.params && this.$route.params.impArticleId) {
        const impArticleId = this.$route.params.impArticleId
        this.getImpArticle(impArticleId)
      }else{
            this.impArticle={}
      }
      },
      //根据id查询
      getImpArticle(impArticleId) {
        impArticle.getById(impArticleId)
          .then(response => {
            this.impArticle = response.data.item
          })
      },
      // 修改
      update() {
        impArticle.updateImpArticle(this.impArticle)
          .then(reponse => {
            //提示
            this.$message({
              type: 'success',
              message: '修改成功!'
            })
            //跳转列表页面，使用路由跳转方式实现
            this.$router.push({ path: '/impArticle/list' })
          })
      },
      //添加
      saveOrUpdate() {
          this.update()
      }
    }
  }
  </script>