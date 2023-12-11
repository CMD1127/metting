import request from '@/utils/request'

export default{
        //current当前页 limit每页记录书 fanwenkuQuery条件对象
        getMettingListPage(current,limit,mettingQuery){
            return request({
                url: `/metting/pageMettingCondition/${current}/${limit}`,
                method: 'post',
                //fanwenkuQuery条件对象 后端使用RequestBody获取数据
                //data表示把对象转换城json进行传递到接口里面
                data: mettingQuery
              })
        },
        // 添加功能
        save(metting){
          return request({
            url: `/metting/add`,
            method:'post',
            data:metting
          })
        },
        // 根据id查询
        getById(id){
          return request({
            url: `/metting/getMetting/${id}`,
            method:'get'
          })
        },
        // 修改功能
        update(metting){
          return request({
            url: `/metting/update`,
            method:'post',
            data:metting
          })
        },
          // 删除功能
          delete(id){
            return request({
              url: `/metting/delete/${id}`,
              method:'delete'
            })
        },
}