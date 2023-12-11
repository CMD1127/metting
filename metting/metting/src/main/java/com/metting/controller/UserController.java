package com.metting.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.metting.config.MD5;
import com.metting.entity.User;
import com.metting.entity.vo.userVo;
import com.metting.result.R;
import com.metting.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wcc
 * @since 2023-08-30
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

        @ApiOperation(value = "所有用户")
        @GetMapping("findAll")
        public R list(){
            List<User> list = userService.list(null);
            return R.ok().data("items",list);
        }

    /***
     *  条件查询带分页
     */
    @ApiOperation(value = "条件查询带分页")
    @PostMapping("pageUserCondition/{current}/{limit}")
    public R pageUserCondition(@PathVariable  Long current, @PathVariable Long limit,@RequestBody(required = false) userVo userVo){
        //创建page对象
        Page<User> page=new Page<>(current,limit);
        // 构建条件
        QueryWrapper<User> wrapper=new QueryWrapper<>();
        String loginName = userVo.getLoginName();
        String userName = userVo.getUserName();
        Integer role = userVo.getRole();

        if (!StringUtils.isEmpty(userName)) {
            wrapper.like("user_name", userName);
        }

        if (!StringUtils.isEmpty(loginName) ) {
            wrapper.eq("login_name", loginName);
        }

        if (!StringUtils.isEmpty(role) ) {
            wrapper.eq("role", role);
        }
        wrapper.orderByDesc("create_time");
        // 调用方法实现条件查询分页
        IPage<User> page1 = userService.page(page, wrapper);

        long total = page1.getTotal();
        List<User> records = page1.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }

    /***
     * 添加用户
     */
    @ApiOperation(value = "添加用户")
    @PostMapping("addUser")
    public R save(@RequestBody User user){
        // 设置密码MD5加密
        String password=MD5.encrypt(user.getPassword());
        user.setPassword(password);
        userService.save(user);
        return R.ok();
    }

    /***
     * 根据id查找
     */

    @ApiOperation(value = "根据ID查询用户")
    @GetMapping("getUser/{loginName}")
    public R getById(@PathVariable String loginName){
        User user = userService.getById(loginName);
        return R.ok().data("item",user);
    }

    /***
     * 修改功能
     */
    @ApiOperation(value = "根据ID修改用户")
    @PostMapping("updateUser")
    public R updateById(@RequestBody User user){
        User oldUser = userService.getById(user.getLoginName());
        boolean flag=true;
        if (oldUser.getPassword().equals(user.getPassword())) {
             flag = userService.updateById(user);
        }else{
            String password=MD5.encrypt(user.getPassword());
            user.setPassword(password);
            flag = userService.updateById(user);
        }
        if(flag){
            return R.ok();
        }else{
            return R.error();
        }

    }

    /**
     *  逻辑删除
     */
    @ApiOperation(value = "根据ID删除用户")
    @DeleteMapping("deleteUser/{loginName}")
    public R delete(@PathVariable String loginName){
        boolean flag = userService.removeById(loginName);
        if(flag){
            return R.ok();
        }else{
            return  R.error();
        }
    }

}

