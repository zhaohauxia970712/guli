package org.phoneix.Teacher.entity.vo;

import lombok.Data;
import org.phoneix.Teacher.entity.EduCourse;
import org.phoneix.Teacher.entity.EduCourseDescription;

@Data
public class CourseDesc {
    private EduCourse eduCourse;
    private EduCourseDescription eduCourseDescription;
}
