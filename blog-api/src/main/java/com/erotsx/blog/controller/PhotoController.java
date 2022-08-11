package com.erotsx.blog.controller;

import com.erotsx.blog.common.api.Result;
import com.erotsx.blog.entity.Photo;
import com.erotsx.blog.service.PhotoService;
import com.erotsx.blog.vo.PageVo;
import com.erotsx.blog.vo.PhotoVo;
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
     * 批量上传图片到相册
     *
     * @param files 图片列表
     * @param id    相册id
     * @return 图片url列表
     * @throws IOException IOException
     */
    @PostMapping("upload/{id}")
    public Result<List<String>> uploadPhotos(@RequestBody MultipartFile[] files, @PathVariable("id") Long id) throws IOException {
        return Result.success(photoService.uploadPhotos(files, id), "上传成功");
    }

    /**
     * 插入图片到相册
     *
     * @param photo photo
     * @return String
     */
    @PostMapping("insert")
    public Result<?> insert(@RequestBody Photo photo) {
        photoService.insert(photo);
        return Result.success(null, "上传成功");
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

    /**
     * 搜索图片，返回分页列表
     *
     * @param albumId  相册Id
     * @param page     page
     * @param pageSize pageSize
     * @return 分页列表
     */
    @GetMapping("search")
    public Result<PageVo<PhotoVo>> search(@RequestParam(required = false) Long albumId,
                                          @RequestParam(required = false, defaultValue = "1") int page,
                                          @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return Result.success(photoService.search(albumId, page, pageSize));
    }

    /**
     * 搜索系统相册，返回分页列表
     *
     * @param keyword  关键词
     * @param page     page
     * @param pageSize pageSize
     * @return 分页列表
     */
    @GetMapping("sys/search")
    public Result<PageVo<PhotoVo>> sysSearch(@RequestParam(required = false) String keyword,
                                             @RequestParam(required = false, defaultValue = "1") int page,
                                             @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return Result.success(photoService.sysSearch(keyword, page, pageSize));
    }

    /**
     * 批量移动图片到相册
     *
     * @param albumId     相册Id
     * @param photoIdList 图片列表
     * @return String
     */
    @PostMapping("move")
    public Result<?> move(@RequestParam Long albumId, @RequestBody List<Long> photoIdList) {
        photoService.move(albumId, photoIdList);
        return Result.success(null, "移动成功");
    }

    /**
     * 修改照片信息
     *
     * @param photoVo photoVo
     * @return String
     */
    @PutMapping("update")
    public Result<?> update(@RequestBody PhotoVo photoVo) {
        photoService.update(photoVo);
        return Result.success(null, "修改成功");
    }

}
