<template>
    <div class="app-container">
      文章添加
      <br>
      <br>
      <el-form label-width="120px">
        <el-form-item label="范文库名称">
          <el-select v-model="article.fanwenkuId" placeholder="选择范文名称">
                <el-option
                  v-for="item in fanwenkuList"
                  :key="item.fanwenkuId"
                  :label="item.fanwenkuName"
                  :value="item.fanwenkuId">
                </el-option>
              </el-select>
        </el-form-item>
    
    
        <el-form-item label="文章内容">
         <mavon-editor ref="md" v-model="article.content" :ishljs="true"  />
 </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="saveOrUpdate">保存</el-button>
        </el-form-item>
      </el-form>
    </div>
  </template>
  <script>
   import article from '@/api/edu/article'
  export default {
    data() {
      return {
        fanwenkuList:null,
        article: {}
      }
    },
    watch: {
    $route(to, from) {
      this.init()
    }
  },
    created() {
      this.init();
      this.getFanwenkuList();
    },
    methods: {
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
      init(){
//页面渲染之前执行
      //获取路由id值 调用接口得到医院设置信息
      if (this.$route.params && this.$route.params.articleId) {
        const articleId = this.$route.params.articleId
        this.getArticle(articleId)
      }else{
            this.fanwenku={}
      }
      },
      //根据id查询
      getArticle(articleId) {
        article.getById(articleId)
          .then(response => {
            this.article = response.data.item
          })
      },
      // 修改
      update() {
        article.updateArticle(this.article)
          .then(reponse => {
            //提示
            this.$message({
              type: 'success',
              message: '修改成功!'
            })
            //跳转列表页面，使用路由跳转方式实现
            this.$router.push({ path: '/article/list' })
          })
      },
      // 添加
      save() {
        article.save(this.article)
          .then(reponse => {
            //提示
            this.$message({
              type: 'success',
              message: '添加成功!'
            })
            //跳转列表页面，使用路由跳转方式实现
            this.$router.push({ path: '/article/list' })
          })
      },
      //添加
      saveOrUpdate() {
        //判断添加还是修改
        if (!this.article.articleId) { //没有id，做添加
          this.save();
        } else {//修改
          this.update()
        }
      }
    }
  }
  </script>