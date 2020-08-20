package org.phoneix.Teacher.controller;


import org.phoneix.Teacher.entity.EduCourse;
import org.phoneix.Teacher.entity.vo.CourseDesc;
import org.phoneix.Teacher.service.EduCourseService;
import org.phoneix.guli.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author Phoneix
 * @since 2020-08-14
 */
@RestController
@RequestMapping("/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    @PostMapping("/save")
    public Result saveCourse(@RequestBody CourseDesc courseDesc) { //接收课程和描述对象

        try {
            //接收课程和描述对象
            String courseId = eduCourseService.saveCourseDesc(courseDesc);
            return Result.ok().data("courseId", courseId);

        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

}

