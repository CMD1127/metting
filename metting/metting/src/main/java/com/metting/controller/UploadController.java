package com.metting.controller;


import com.metting.result.R;
import com.metting.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/upload")
@CrossOrigin
public class UploadController {

    @Autowired
    private UploadService uploadService;

    /***
     * 头像上传
     * @param files
     * @return
     */
    @PostMapping(value = "/avatar")
    public R uploadAvatar(@RequestParam(value = "file") List<MultipartFile> files){
        System.out.println(files);
        List<String> imagePath = uploadService.uploadAvatar(files);
        return R.ok().data("imagePath",imagePath);
    }

}
