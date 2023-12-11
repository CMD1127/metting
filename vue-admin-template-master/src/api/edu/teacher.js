import request from '@/utils/request'

export default{
        // 讲师列表(条件查询分页)
        //current当前页 limit每页记录书 teacherQuery条件对象
        getTeacherListPage(current,limit,teacherQuery){
            return request({
                url: `/eduservice/teacher/pageTeacherCondition/${current}/${limit}`,
                method: 'post',
                //teacherQuery条件对象 后端使用RequestBody获取数据
                //data表示把对象转换城json进行传递到接口里面
                data: teacherQuery
              })
        },
        // 删除功能
        deleteTeacher(id){
            return request({
              url: `/eduservice/teacher/deleteTeacher/${id}`,
              method:'delete'
            })
        },
        // 添加功能
        save(teacher){
          return request({
            url: `/eduservice/teacher/addTeacher`,
            method:'post',
            data:teacher
          })
        },
        // 根据id查询
        getById(id){
          return request({
            url: `/eduservice/teacher/getTeacher/${id}`,
            method:'get'
          })
        },
        // 修改功能
        updateTeacher(teacher){
          return request({
            url: `/eduservice/teacher/updateTeacher`,
            method:'post',
            data:teacher
          })
        }

}