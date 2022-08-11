package com.erotsx.blog.controller;

import com.erotsx.blog.common.api.Result;
import com.erotsx.blog.service.PhotoAlbumService;
import com.erotsx.blog.vo.PageVo;
import com.erotsx.blog.vo.PhotoAlbumVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("album")
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

    /**
     * 更新相册信息
     *
     * @param photoAlbumVo photoAlbumVo
     * @return String
     */
    @PutMapping("update")
    public Result<?> update(@RequestBody PhotoAlbumVo photoAlbumVo) {
        photoAlbumService.update(photoAlbumVo);
        return Result.success(null, "更新成功");
    }

    /**
     * 根据id删除相册
     *
     * @param id 相册id
     * @return String
     */
    @DeleteMapping("delete/{id}")
    public Result<?> delete(@PathVariable("id") Long id) {
        photoAlbumService.delete(id);
        return Result.success(null, "删除成功");
    }

    /**
     * 根据相册id查询相册信息
     *
     * @param id 相册id
     * @return 相册信息
     */
    @GetMapping("info/{id}")
    public Result<PhotoAlbumVo> info(@PathVariable("id") Long id) {
        return Result.success(photoAlbumService.info(id), "success");
    }

    /**
     * 获取所有相册信息
     * @return 所有相册信息
     */
    @GetMapping("albums")
    public Result<List<PhotoAlbumVo>> getAllAlbums() {
        return Result.success(photoAlbumService.getAllAlbums());
    }

}
