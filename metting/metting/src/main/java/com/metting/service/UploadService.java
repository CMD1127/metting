package com.metting.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UploadService {

    List<String> uploadAvatar(List<MultipartFile> files); //头像上传


}
