package org.phoneix.Teacher.controller;


import com.sun.prism.shader.Mask_TextureSuper_AlphaTest_Loader;
import io.swagger.annotations.ApiParam;
import org.phoneix.Teacher.entity.EduSubject;
import org.phoneix.Teacher.entity.vo.OneSubject;
import org.phoneix.Teacher.service.EduSubjectService;
import org.phoneix.guli.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.security.auth.Subject;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author Phoneix
 * @since 2020-08-13
 */
@RestController
@RequestMapping("/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;


    @PostMapping("import")
    public Result importSubject(MultipartFile file){
        List<String> mesList = eduSubjectService.importExcel(file);

        if(mesList.size() == 0){
            return Result.ok();
        }else{
            return Result.error().data("messageList",mesList);
        }

    }

    @GetMapping("tree")
    public Result TreeSubject(){
        List<OneSubject> subjectList = eduSubjectService.getTree();

        return Result.ok().data("subjectList",subjectList);
    }

    @DeleteMapping("/{id}")
    public Result deleteSubjectById(@PathVariable String id){
        boolean b = eduSubjectService.deleteById(id);
        if(b){
            return Result.ok();
        }else{
            return Result.error();
        }
    }
    @PostMapping("saveLevelOne")
    public Result saveLevelOne(
            @ApiParam(name = "subject", value = "课程分类对象", required = true)
            @RequestBody EduSubject subject){
        boolean result = eduSubjectService.saveLevelOne(subject);
        if(result){
            return Result.ok();
        }else {
            return Result.error().message("删除失败");
        }
    }

    @PostMapping("saveLevelTwo")
    public Result saveTwoLevel(@RequestBody EduSubject subjcet){
       boolean result =  eduSubjectService.selectTwoLevel(subjcet);
       if(result){
           return Result.ok();
       }else{
           return Result.error().message("保存失败");
       }
    }
}


