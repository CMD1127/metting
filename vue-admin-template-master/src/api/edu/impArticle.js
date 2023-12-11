import request from '@/utils/request'

export default{
       //current当前页 limit每页记录书 articleQuery条件对象
       getImpArticleListPage(current,limit,impArticleQuery){
        return request({
            url: `/impArticle/pageImpArticleCondition/${current}/${limit}`,
            method: 'post',
            //articleQuery 后端使用RequestBody获取数据
            //data表示把对象转换城json进行传递到接口里面
            data: impArticleQuery
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
         uploadefile(file,impArticleName){
          return request({
            url: `/impArticle/uploadefile/${impArticleName}`,
            method:'post',
            data:file
          })
      },
      // 根据id查询
      getById(impArticleId){
        return request({
          url: `/impArticle/getImpArticle/${impArticleId}`,
          method:'get'
        })
      },
      // 修改功能
      updateImpArticle(impArticle){
        return request({
          url: `/impArticle/updateImpArticle`,
          method:'post',
          data:impArticle
        })
      },
        // 删除功能
        deleteImpArticle(impArticleId){
          return request({
            url: `/impArticle/deleteImpArticle/${impArticleId}`,
            method:'delete'
          })
      },
}