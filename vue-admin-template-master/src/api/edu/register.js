import request from '@/utils/request'

export default{
     
        // 注册
        register(user){
          return request({
            url: `/login/register`,
            method:'post',
            data:user
          })
        }
}