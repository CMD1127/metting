package com.metting.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.metting.entity.EmployeeInfo;
import com.metting.entity.vo.EmployeeVo;
import com.metting.result.R;
import com.metting.service.EmployeeInfoService;
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
 * @since 2023-12-09
 */
@RestController
@RequestMapping("/employee")
@CrossOrigin
public class EmployeeInfoController {


     @Autowired
     private EmployeeInfoService employeeInfoService;
    /***
     *  条件查询带分页
     */
    @ApiOperation(value = "条件查询带分页")
    @PostMapping("pageEmployeeCondition/{current}/{limit}")
    public R pageEmployeeCondition(@PathVariable Long current, @PathVariable Long limit, @RequestBody(required = false) EmployeeVo employeeVo){
        //创建page对象
        Page<EmployeeInfo> page=new Page<>(current,limit);
        // 构建条件
        QueryWrapper<EmployeeInfo> wrapper=new QueryWrapper<>();
        String name = employeeVo.getName();
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        wrapper.orderByDesc("create_time");
        // 调用方法实现条件查询分页
        IPage<EmployeeInfo> page1 = employeeInfoService.page(page, wrapper);

        long total = page1.getTotal();
        List<EmployeeInfo> records = page1.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }

    /***
     * 添加
     */
    @PostMapping("add")
    public R save(@RequestBody EmployeeInfo employeeInfo){
        boolean flag = employeeInfoService.save(employeeInfo);
        if (flag){
            return R.ok();
        }else{
           return R.error();
        }
    }


    @ApiOperation(value = "根据ID查询员工")
    @GetMapping("getEmployee/{id}")
    public R getById(@PathVariable String id){
        EmployeeInfo employeeInfo = employeeInfoService.getById(id);
        return R.ok().data("item",employeeInfo);
    }
    /***
     * 修改功能
     */
    @ApiOperation(value = "根据ID修改员工")
    @PostMapping("updateEmployee")
    public R updateById(@RequestBody EmployeeInfo employeeInfo) {
        boolean flag = employeeInfoService.updateById(employeeInfo);
        if (flag){
            return R.ok();
        }else{
            return R.error();
        }
    }

    /**
     *  逻辑删除
     */
    @ApiOperation(value = "根据ID删除员工")
    @DeleteMapping("deleteEmployee/{id}")
    public R delete(@PathVariable String id) {
        boolean flag = employeeInfoService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}

