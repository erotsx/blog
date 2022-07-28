package com.erotsx.blog.controller;

import com.erotsx.blog.common.api.Result;
import com.erotsx.blog.service.PhotoAlbumService;
import com.erotsx.blog.vo.PageParams;
import com.erotsx.blog.vo.PageVo;
import com.erotsx.blog.vo.PhotoAlbumVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("photo/album")
public class PhotoAlbumController {

    @Resource
    private PhotoAlbumService photoAlbumService;

    /**
     * 根据相册名称关键词搜索相册并分页
     *
     * @param keyword  相册名称关键词
     * @param page     page
     * @param pageSize pageSize
     * @return 分页后的相册列表
     */
    @GetMapping("search")
    public Result<PageVo<PhotoAlbumVo>> search(@RequestParam(required = false) String keyword,
                                               @RequestParam(required = false, defaultValue = "1") int page,
                                               @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return Result.success(photoAlbumService.search(keyword, page, pageSize));
    }

    /**
     * 新增相册
     *
     * @param photoAlbumVo PhotoAlbumVo
     * @return String
     */
    @PostMapping("add")
    public Result<?> add(@RequestBody PhotoAlbumVo photoAlbumVo) {
        photoAlbumService.add(photoAlbumVo);
        return Result.success(null, "添加成功");
    }
}
