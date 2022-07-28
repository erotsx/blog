package com.erotsx.blog.controller;

import com.erotsx.blog.common.api.Result;
import com.erotsx.blog.service.PhotoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("photo")
public class PhotoController {

    @Resource
    private PhotoService photoService;

    /**
     * 上传单个图片，图片不属于任何相册
     *
     * @param file 图片
     * @return 图片地址
     * @throws IOException IOException
     */
    @PostMapping("upload")
    public Result<String> upload(@RequestBody MultipartFile file) throws IOException {
        return Result.success(photoService.upload(file), "上传成功");
    }


    /**
     * 批量删除图片
     *
     * @param photoIdList 图片Id列表
     * @return msg
     */
    @DeleteMapping("delete")
    public Result<?> delete(@RequestBody List<Long> photoIdList) {
        photoService.delete(photoIdList);
        return Result.success(null, "删除成功");
    }

}
