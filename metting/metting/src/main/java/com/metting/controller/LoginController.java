package com.metting.controller;


import com.metting.config.MD5;
import com.metting.entity.User;
import com.metting.entity.vo.loginVo;
import com.metting.result.R;
import com.metting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@CrossOrigin
public class LoginController {
    @Autowired
    private UserService userService;

    // login
    @PostMapping("login")
    public R login(@RequestBody loginVo loginVo){
       User user= userService.getById(loginVo.getLoginName());
       if(user==null){
           return R.error().data("error","登录失败,账号未注册！");

       }
       if(!MD5.encrypt(loginVo.getPassword()).equals(user.getPassword())){
           return R.error().data("error","登录失败,密码错误！");
       }
        return R.ok().data("token",user.getLoginName());
    }


    // info
    @PostMapping("info")
    public R info( @RequestBody String token){
        String loginName=token.replace("=","");
        User user= userService.getById(loginName);
        if(user==null){
            return R.error().data("message","未查找到信息");
        }
        if(user.getRole().equals(1)){
            return R.ok().data("roles","管理员").data("name",user.getUserName()).data("avatar","https://www.baidu.com/link?url=os58oOCvqWhJzHwOlJhALDwqm4wJ7FYwcUHR5AmIs_WHhQz-8R3QUi1KP34k-jHuiPBiImmiML82Mi_Is5qz2RckiIqjyXzHRPHTUnrf3uwVin-8p-VhUUAy3eopSCvYcj-aDljVAfcw5S606p29cP6owtGW_L8M1ZpjYmcGSgT--jV2SWuSgWEogQQObH6BKlXriBOaQT_M8CP5UPKHwT-nULn_LteZVR0ef3EYxKswV9kCMp3DexrY9P1oawrBLdknwV1xj9xKK6aGy7y6fJmYtuIKfL35oyAOAyTGjCuvGzV7I0ZtHscah_ilxz2BXtSsxXtqXkarGCpYhqxmQBDafm-s51BUoCPbqlbBOqwsxiOMCrveZ9728pxAN8fbsEH4zLzqT4-nLkTb5q1f5t212mJM1dyLs0IlNwTTsxh72VBsc3XlPyqcfFORfTJHusB4ZrP8Oi748V-VRRn0_9sqQJSggWfHxVLCn63Pi1btfD4PaAhiieKLd3Q1gDQB7REbd24qVAIfbDTmKHioSZweHAvXnOBEWUlXT8HGITvttLN-nyHQXmlSsokPEwFnLzrmQDSs1jldsUvWgOH_xdDyVkfgn0d4B3J3Ph6kDyyJxAIlLAkFiFyFsH0YjKntLG80ueKuiZSeU03okIjeAXXETgGdLl_j2XDl1TcZARHeEgEsIrJ34DjNtjHKT3UNJhAilMXfB3mzfhZ0B1awBXUlYivkLrB9hb_U7bupOK5pZlw8EFSN1yAwd7aLRrFemavcZDPV_I04bqe5Cq0Qg5N31Vkwx2yEMtR3FCS9GG0V5QUQF2S0NapeDJWgUSIai9cUsp9TfNonzXhjPTxjWK&wd=&eqid=8dded4420000752a0000000564f001dc");

        }else{
            return R.ok().data("roles","普通用户").data("name",user.getUserName()).data("avatar","https://www.baidu.com/link?url=os58oOCvqWhJzHwOlJhALDwqm4wJ7FYwcUHR5AmIs_WHhQz-8R3QUi1KP34k-jHuiPBiImmiML82Mi_Is5qz2RckiIqjyXzHRPHTUnrf3uwVin-8p-VhUUAy3eopSCvYcj-aDljVAfcw5S606p29cP6owtGW_L8M1ZpjYmcGSgT--jV2SWuSgWEogQQObH6BKlXriBOaQT_M8CP5UPKHwT-nULn_LteZVR0ef3EYxKswV9kCMp3DexrY9P1oawrBLdknwV1xj9xKK6aGy7y6fJmYtuIKfL35oyAOAyTGjCuvGzV7I0ZtHscah_ilxz2BXtSsxXtqXkarGCpYhqxmQBDafm-s51BUoCPbqlbBOqwsxiOMCrveZ9728pxAN8fbsEH4zLzqT4-nLkTb5q1f5t212mJM1dyLs0IlNwTTsxh72VBsc3XlPyqcfFORfTJHusB4ZrP8Oi748V-VRRn0_9sqQJSggWfHxVLCn63Pi1btfD4PaAhiieKLd3Q1gDQB7REbd24qVAIfbDTmKHioSZweHAvXnOBEWUlXT8HGITvttLN-nyHQXmlSsokPEwFnLzrmQDSs1jldsUvWgOH_xdDyVkfgn0d4B3J3Ph6kDyyJxAIlLAkFiFyFsH0YjKntLG80ueKuiZSeU03okIjeAXXETgGdLl_j2XDl1TcZARHeEgEsIrJ34DjNtjHKT3UNJhAilMXfB3mzfhZ0B1awBXUlYivkLrB9hb_U7bupOK5pZlw8EFSN1yAwd7aLRrFemavcZDPV_I04bqe5Cq0Qg5N31Vkwx2yEMtR3FCS9GG0V5QUQF2S0NapeDJWgUSIai9cUsp9TfNonzXhjPTxjWK&wd=&eqid=8dded4420000752a0000000564f001dc");

        }
    }

    // 注册
    @PostMapping("register")
    public R register(@RequestBody User user){
        // 设置密码MD5加密
        String password=MD5.encrypt(user.getPassword());
        user.setPassword(password);
        boolean flag=userService.save(user);
        if (flag){
            return R.ok().message("注册成功！");
        }else{
            return R.error().message("注册失败！");
        }

    }
}
