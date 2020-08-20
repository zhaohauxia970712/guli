package org.phoneix.Teacher.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.phoneix.Teacher.entity.EduTeacher;
import org.phoneix.Teacher.entity.query.TeacherQuery;
import org.phoneix.Teacher.service.EduTeacherService;
import org.phoneix.guli.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author Phoneix
 * @since 2020-08-12
 */
@Api(description="讲师管理")
@RestController
@RequestMapping("/teacher")
@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @ApiOperation(value = "所有讲师列表")
    @GetMapping
    private Result getTeacherList(){
        List<EduTeacher> list = eduTeacherService.list(null);
        return Result.ok().data("list",list);
    }

    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("/{id}")
    public Result removeById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id){
        boolean b = eduTeacherService.removeById(id);
        if(b){
            return Result.ok();
        }
        return Result.error();
    }

    @ApiOperation(value = "讲师分页列表")
    @GetMapping("/{page}/{limit}")
    public Result selectTeacherByPage(
            @ApiParam(name="page",value = "当前页", required = true)
            @PathVariable(value = "page") Integer page,
            @ApiParam(name="limit",value = "每页显示记录数", required = true)
            @PathVariable Integer limit){
        try {
            Page<EduTeacher> teacherPage = new Page<>(page, limit);
            eduTeacherService.page(teacherPage,null);
            return Result.ok().data("total",teacherPage.getTotal()).data("rows",teacherPage.getRecords());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

    //条件查询的分页
    // 1、 有分页
    // 2、条件 ： 根据讲师名称、讲师等级、创建时间、修改时间
    @ApiOperation(value = "根据讲师条件分页查询")
    @PostMapping("/{page}/{limit}")
    public Result selectTeacherByPageAndWrapper(
            @ApiParam(name="page",value = "当前页", required = true)
            @PathVariable(value = "page") Integer page,
            @ApiParam(name="limit",value = "每页显示记录数", required = true)
            @PathVariable Integer limit,
            @RequestBody  TeacherQuery query){
        try {
            Page<EduTeacher> teacherPage = new Page<>(page, limit);
            //Wrapper<TeacherQuery> queryWrapper = new QueryWrapper<TeacherQuery>();
            eduTeacherService.pageQuery(teacherPage, query);
            return Result.ok().data("total",teacherPage.getTotal()).data("rows",teacherPage.getRecords());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

    @ApiOperation(value = "新增讲师")
    @PostMapping("/save")
    public Result save(
            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody EduTeacher teacher){

        eduTeacherService.save(teacher);
        return Result.ok();
    }

    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("/{id}")
    public Result getById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id){

        EduTeacher teacher = eduTeacherService.getById(id);
        return Result.ok().data("teacher", teacher);
    }

    @ApiOperation(value = "根据ID修改讲师")
    @PutMapping("update")
    public Result updateById(
            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody EduTeacher teacher){
        eduTeacherService.updateById(teacher);
        return Result.ok();
    }





}


