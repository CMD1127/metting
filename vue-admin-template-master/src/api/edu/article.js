import request from '@/utils/request'

export default{
       //current当前页 limit每页记录书 articleQuery条件对象
       getArticleListPage(current,limit,articleQuery){
        return request({
            url: `/article/pageArticleCondition/${current}/${limit}`,
            method: 'post',
            //articleQuery 后端使用RequestBody获取数据
            //data表示把对象转换城json进行传递到接口里面
            data: articleQuery
          })
    },
        
        fanwenkuFindAll(){
          return request({
            url: `/article/findAll`,
            method:'get'
          })
        },
         // 添加功能
         save(article){
          return request({
            url: `/article/addArticle`,
            method:'post',
            data:article
          })
        },
        // 根据id查询
        getById(articleId){
          return request({
            url: `/article/getArticle/${articleId}`,
            method:'get'
          })
        },
        // 修改功能
        updateArticle(article){
          return request({
            url: `/article/updateArticle`,
            method:'post',
            data:article
          })
        },
          // 删除功能
          deleteArticle(articleId){
            return request({
              url: `/article/deleteArticle/${articleId}`,
              method:'delete'
            })
        },

         // 上传功能
         uploadefile(file,fanwenkuId){
          return request({
            url: `/article/uploadefile/${fanwenkuId}`,
            method:'post',
            data:file
          })
      },
      //current当前页 limit每页记录书 articleQuery条件对象
      pageHistoryArticleCondition(current,limit,articleQuery){
        return request({
            url: `/article/pageHistoryArticleCondition/${current}/${limit}`,
            method: 'post',
            data: articleQuery
          })
    },
     // 修改功能
     updateHistoryById(articleId){
      return request({
        url: `/article/updateHistoryArticle`,
        method:'post',
        data:articleId
      })
    },
}