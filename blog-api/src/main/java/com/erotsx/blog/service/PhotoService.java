package com.erotsx.blog.service;

import com.erotsx.blog.entity.Photo;
import com.erotsx.blog.vo.PageVo;
import com.erotsx.blog.vo.PhotoVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PhotoService {

    /**
     * 上传单个图片，图片不属于任何相册
     *
     * @param file 图片
     * @return 图片地址
     * @throws IOException IOException
     */
    String upload(MultipartFile file) throws IOException;

    /**
     * 批量删除图片
     *
     * @param photoIdList 图片Id列表
     */
    void delete(List<Long> photoIdList);

    /**
     * 搜索图片，返回分页列表
     *
     * @param albumId  相册Id
     * @param page     page
     * @param pageSize pageSize
     * @return 分页列表
     */
    PageVo<PhotoVo> search(Long albumId, int page, int pageSize);

    /**
     * 批量移动图片到相册
     *
     * @param albumId     相册Id
     * @param photoIdList 图片列表
     */
    void move(Long albumId, List<Long> photoIdList);

    /**
     * 修改照片信息
     *
     * @param photoVo photoVo
     */
    void update(PhotoVo photoVo);

    /**
     * 批量上传图片到相册
     *
     * @param files 图片列表
     * @param id    相册id
     * @return 图片url列表
     * @throws IOException IOException
     */
    List<String> uploadPhotos(MultipartFile[] files, Long id) throws IOException;

    /**
     * 插入图片到相册
     *
     * @param photo photo
     */
    void insert(Photo photo);

    /**
     * 搜索系统相册，返回分页列表
     *
     * @param keyword  关键词
     * @param page     page
     * @param pageSize pageSize
     * @return 分页列表
     */
    PageVo<PhotoVo> sysSearch(String keyword, int page, int pageSize);
}
