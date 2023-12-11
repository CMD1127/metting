import request from '@/utils/request'

export default{
        //current当前页 limit每页记录书 fanwenkuQuery条件对象
        getEmployeeListPage(current,limit,employeeQuery){
            return request({
                url: `/employee/pageEmployeeCondition/${current}/${limit}`,
                method: 'post',
                //fanwenkuQuery条件对象 后端使用RequestBody获取数据
                //data表示把对象转换城json进行传递到接口里面
                data: employeeQuery
              })
        },
        // 添加功能
        save(emoployee){
          return request({
            url: `/employee/add`,
            method:'post',
            data:emoployee
          })
        },
        // 根据id查询
        getById(id){
          return request({
            url: `/employee/getEmployee/${id}`,
            method:'get'
          })
        },
        // 修改功能
        update(employee){
          return request({
            url: `/employee/updateEmployee`,
            method:'post',
            data:employee
          })
        },
          // 删除功能
          delete(id){
            return request({
              url: `/employee/deleteEmployee/${id}`,
              method:'delete'
            })
        },
}