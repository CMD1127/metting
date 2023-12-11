import request from '@/utils/request'

export default{
        //current当前页 limit每页记录书 userQuery条件对象
        getUserListPage(current,limit,userQuery){
            return request({
                url: `/user/pageUserCondition/${current}/${limit}`,
                method: 'post',
                //tuserQuery条件对象 后端使用RequestBody获取数据
                //data表示把对象转换城json进行传递到接口里面
                data: userQuery
              })
        },
        // 添加功能
        save(user){
          return request({
            url: `/user/addUser`,
            method:'post',
            data:user
          })
        },
        // 根据id查询
        getById(loginName){
          return request({
            url: `/user/getUser/${loginName}`,
            method:'get'
          })
        },
        // 修改功能
        updateUser(user){
          return request({
            url: `/user/updateUser`,
            method:'post',
            data:user
          })
        },
          // 删除功能
          deleteUser(loginName){
            return request({
              url: `/user/deleteUser/${loginName}`,
              method:'delete'
            })
        },
}