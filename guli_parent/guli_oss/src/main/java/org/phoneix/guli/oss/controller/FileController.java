package org.phoneix.guli.oss.controller;

import org.phoneix.guli.common.Result;
import org.phoneix.guli.oss.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("oss")
@CrossOrigin
public class FileController {
    @Autowired
    private FileService fileService;
    /*
    * 上传文件
    *
    * */
    @PostMapping("file/upload")
    public Result upload(MultipartFile file){
        String url = fileService.upload(file);
        return Result.ok().data("url",url);

    }
}
