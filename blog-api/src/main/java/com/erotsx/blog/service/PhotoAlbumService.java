package com.erotsx.blog.service;

import com.erotsx.blog.vo.PageVo;
import com.erotsx.blog.vo.PhotoAlbumVo;

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
}
