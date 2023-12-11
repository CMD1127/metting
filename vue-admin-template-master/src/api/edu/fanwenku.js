import request from '@/utils/request'

export default{
  
        //current当前页 limit每页记录书 fanwenkuQuery条件对象
        getFanwenkuListPage(current,limit,fanwenkuQuery){
            return request({
                url: `/fanwenku/pageFanwenkuCondition/${current}/${limit}`,
                method: 'post',
                //fanwenkuQuery条件对象 后端使用RequestBody获取数据
                //data表示把对象转换城json进行传递到接口里面
                data: fanwenkuQuery
              })
        },
        // 添加功能
        save(fanwenku){
          return request({
            url: `/fanwenku/addFanwenku`,
            method:'post',
            data:fanwenku
          })
        },
        // 根据id查询
        getById(fanwenkuId){
          return request({
            url: `/fanwenku/getFanwenku/${fanwenkuId}`,
            method:'get'
          })
        },
        // 修改功能
        updateFanwenku(fanwenku){
          return request({
            url: `/fanwenku/updateFanwenku`,
            method:'post',
            data:fanwenku
          })
        },
          // 删除功能
          deleteFanwenku(fanwenkuId){
            return request({
              url: `/fanwenku/deleteFanwenku/${fanwenkuId}`,
              method:'delete'
            })
        },
}