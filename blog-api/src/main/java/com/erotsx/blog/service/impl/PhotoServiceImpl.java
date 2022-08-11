package com.erotsx.blog.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.erotsx.blog.dao.PhotoAlbumMapper;
import com.erotsx.blog.dao.PhotoMapper;
import com.erotsx.blog.entity.Photo;
import com.erotsx.blog.entity.PhotoAlbum;
import com.erotsx.blog.service.PhotoService;
import com.erotsx.blog.utils.ImgBedUtils;
import com.erotsx.blog.vo.PageVo;
import com.erotsx.blog.vo.PhotoVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PhotoServiceImpl implements PhotoService {

    @Resource
    private PhotoMapper photoMapper;

    @Resource
    private PhotoAlbumMapper photoAlbumMapper;

    /**
     * 上传单个图片，图片不属于任何相册
     *
     * @param file 图片
     * @return 图片地址
     * @throws IOException IOException
     */
    @Override
    public String upload(MultipartFile file) throws IOException {
        JSONObject data = ImgBedUtils.upload(file);
        Photo photo = new Photo();
        String url = data.getString("url");
        photo.setName(data.getString("relative_path"));
        photo.setUrl(url);
        photo.setDeleteUrl(data.getString("delete"));
        photoMapper.insert(photo);
        return url;
    }

    /**
     * 批量删除图片
     *
     * @param photoIdList 图片Id列表
     */
    @Override
    public void delete(List<Long> photoIdList) {
        LambdaUpdateWrapper<Photo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.in(Photo::getId, photoIdList).set(Photo::getAlbumId, null);
        photoMapper.update(null, updateWrapper);
    }

    /**
     * 搜索图片，返回分页列表
     *
     * @param albumId  相册Id
     * @param page     page
     * @param pageSize pageSize
     * @return 分页列表
     */
    @Override
    public PageVo<PhotoVo> search(Long albumId, int page, int pageSize) {
        LambdaQueryWrapper<Photo> queryWrapper = new LambdaQueryWrapper<>();
        if (albumId != null) {
            queryWrapper.eq(Photo::getAlbumId, albumId);
        }
        return getPhotoVoPageVo(page, pageSize, queryWrapper);
    }

    /**
     * 批量移动图片到相册
     *
     * @param albumId     相册Id
     * @param photoIdList 图片列表
     */
    @Override
    public void move(Long albumId, List<Long> photoIdList) {
        LambdaUpdateWrapper<Photo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.in(Photo::getId, photoIdList).set(Photo::getAlbumId, albumId);
        photoMapper.update(null, updateWrapper);
    }

    /**
     * 修改照片信息
     *
     * @param photoVo photoVo
     */
    @Override
    public void update(PhotoVo photoVo) {
        Photo photo = new Photo();
        BeanUtils.copyProperties(photoVo, photo);
        photoMapper.updateById(photo);
    }

    /**
     * 批量上传图片到相册
     *
     * @param files 图片列表
     * @param id    相册id
     * @return 图片url列表
     * @throws IOException IOException
     */
    @Override
    public List<String> uploadPhotos(MultipartFile[] files, Long id) throws IOException {
        List<String> urlList = new ArrayList<>();
        for (MultipartFile file : files) {
            JSONObject data = ImgBedUtils.upload(file);
            Photo photo = new Photo();
            photo.setAlbumId(id);
            String url = data.getString("url");
            photo.setName(data.getString("relative_path"));
            photo.setUrl(url);
            photo.setDeleteUrl(data.getString("delete"));
            photoMapper.insert(photo);
            urlList.add(url);
        }
        return urlList;
    }

    /**
     * 插入图片到相册
     *
     * @param photo photo
     */
    @Override
    public void insert(Photo photo) {
        photoMapper.insert(photo);
    }

    /**
     * 搜索系统相册，返回分页列表
     *
     * @param keyword  关键词
     * @param page     page
     * @param pageSize pageSize
     * @return 分页列表
     */
    @Override
    public PageVo<PhotoVo> sysSearch(String keyword, int page, int pageSize) {
        LambdaQueryWrapper<Photo> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isBlank(keyword)) {
            queryWrapper.like(Photo::getName, keyword);
        }
        LambdaQueryWrapper<PhotoAlbum> albumLambdaQueryWrapper = new LambdaQueryWrapper<>();
        albumLambdaQueryWrapper.eq(PhotoAlbum::getStatus, 2);
        PhotoAlbum photoAlbum = photoAlbumMapper.selectOne(albumLambdaQueryWrapper);
        queryWrapper.eq(Photo::getAlbumId, photoAlbum.getId());
        return getPhotoVoPageVo(page, pageSize, queryWrapper);
    }

    private PageVo<PhotoVo> getPhotoVoPageVo(int page, int pageSize, LambdaQueryWrapper<Photo> queryWrapper) {
        Page<Photo> photoPage = photoMapper.selectPage(new Page<>(page, pageSize), queryWrapper);
        List<PhotoVo> photoVoList = new ArrayList<>();
        for (Photo photo : photoPage.getRecords()) {
            photoVoList.add(getPhotoVo(photo));
        }
        return new PageVo<>(photoVoList, photoPage.getTotal());
    }

    private PhotoVo getPhotoVo(Photo photo) {
        PhotoVo photoVo = new PhotoVo();
        BeanUtils.copyProperties(photo, photoVo);
        return photoVo;
    }


}
