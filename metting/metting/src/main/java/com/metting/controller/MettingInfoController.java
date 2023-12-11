package com.metting.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.metting.entity.EmployeeInfo;
import com.metting.entity.MettingInfo;
import com.metting.entity.vo.EmployeeVo;
import com.metting.entity.vo.MettingVo;
import com.metting.result.R;
import com.metting.service.EmployeeInfoService;
import com.metting.service.MettingInfoService;
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
@RequestMapping("/metting")
@CrossOrigin
public class MettingInfoController {
    @Autowired
    private MettingInfoService mettingInfoService;
    /***
     *  条件查询带分页
     */
    @ApiOperation(value = "条件查询带分页")
    @PostMapping("pageMettingCondition/{current}/{limit}")
    public R pageMettingCondition(@PathVariable Long current, @PathVariable Long limit, @RequestBody(required = false) MettingVo mettingVo){
        //创建page对象
        Page<MettingInfo> page=new Page<>(current,limit);
        // 构建条件
        QueryWrapper<MettingInfo> wrapper=new QueryWrapper<>();
        String name = mettingVo.getName();
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        wrapper.orderByDesc("create_time");
        // 调用方法实现条件查询分页
        IPage<MettingInfo> page1 = mettingInfoService.page(page, wrapper);

        long total = page1.getTotal();
        List<MettingInfo> records = page1.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }

    /***
     * 添加
     */
    @PostMapping("add")
    public R save(@RequestBody MettingInfo mettingInfo){
        mettingInfo.setIsFlag(0);
        boolean flag = mettingInfoService.save(mettingInfo);
        if (flag){
            return R.ok();
        }else{
            return R.error();
        }
    }


    @ApiOperation(value = "根据ID查询")
    @GetMapping("getMetting/{id}")
    public R getById(@PathVariable String id){
        MettingInfo mettingInfo = mettingInfoService.getById(id);
        return R.ok().data("item",mettingInfo);
    }
    /***
     * 修改功能
     */
    @ApiOperation(value = "根据ID修改")
    @PostMapping("update")
    public R updateById(@RequestBody MettingInfo mettingInfo) {
        boolean flag = mettingInfoService.updateById(mettingInfo);
        if (flag){
            return R.ok();
        }else{
            return R.error();
        }
    }

    /**
     *  逻辑删除
     */
    @ApiOperation(value = "根据ID删除")
    @DeleteMapping("delete/{id}")
    public R delete(@PathVariable String id) {
        boolean flag = mettingInfoService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}

