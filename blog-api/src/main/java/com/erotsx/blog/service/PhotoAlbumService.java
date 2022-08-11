package com.erotsx.blog.service;

import com.erotsx.blog.vo.PageVo;
import com.erotsx.blog.vo.PhotoAlbumVo;

import java.util.List;

public interface PhotoAlbumService {

    /**
     * 根据相册名称关键词搜索相册并分页
     *
     * @param keyword  相册名称关键词
     * @param page     page
     * @param pageSize pageSize
     * @return 分页后的相册列表
     */
    PageVo<PhotoAlbumVo> search(String keyword, int page, int pageSize);

    /**
     * 新增相册
     *
     * @param photoAlbumVo PhotoAlbumVo
     */
    void add(PhotoAlbumVo photoAlbumVo);

    /**
     * 更新相册信息
     *
     * @param photoAlbumVo photoAlbumVo
     */
    void update(PhotoAlbumVo photoAlbumVo);

    /**
     * 根据id删除相册
     *
     * @param id 相册id
     */
    void delete(Long id);

    /**
     * 根据相册id查询相册信息
     *
     * @param id 相册id
     * @return 相册信息
     */
    PhotoAlbumVo info(Long id);

    /**
     * 获取所有相册信息
     *
     * @return 所有相册信息
     */
    List<PhotoAlbumVo> getAllAlbums();
}
