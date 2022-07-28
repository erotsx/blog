package com.erotsx.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.erotsx.blog.dao.PhotoAlbumMapper;
import com.erotsx.blog.dao.PhotoMapper;
import com.erotsx.blog.entity.Photo;
import com.erotsx.blog.entity.PhotoAlbum;
import com.erotsx.blog.service.PhotoAlbumService;
import com.erotsx.blog.vo.PageVo;
import com.erotsx.blog.vo.PhotoAlbumVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class PhotoAlbumServiceImpl implements PhotoAlbumService {

    @Resource
    private PhotoAlbumMapper photoAlbumMapper;

    @Resource
    private PhotoMapper photoMapper;

    /**
     * 根据相册名称关键词搜索相册并分页
     *
     * @param keyword  相册名称关键词
     * @param page     page
     * @param pageSize pageSize
     * @return 分页后的相册列表
     */
    @Override
    public PageVo<PhotoAlbumVo> search(String keyword, int page, int pageSize) {
        System.out.println(keyword);
        LambdaQueryWrapper<PhotoAlbum> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(PhotoAlbum::getStatus, 1, 0);
        if (!StringUtils.isBlank(keyword)) {
            queryWrapper.like(PhotoAlbum::getName, keyword);
        }
        Page<PhotoAlbum> photoAlbumPage = photoAlbumMapper.selectPage(new Page<>(page, pageSize), queryWrapper);
        List<PhotoAlbumVo> photoAlbumVoList = new ArrayList<>();
        for (PhotoAlbum photoAlbum : photoAlbumPage.getRecords()) {
            photoAlbumVoList.add(getPhotoAlbumVo(photoAlbum));
        }
        return new PageVo<>(photoAlbumVoList, photoAlbumPage.getTotal());
    }

    /**
     * 新增相册
     *
     * @param photoAlbumVo PhotoAlbumVo
     */
    @Override
    public void add(PhotoAlbumVo photoAlbumVo) {
        PhotoAlbum photoAlbum = new PhotoAlbum();
        BeanUtils.copyProperties(photoAlbumVo, photoAlbum);
        photoAlbumMapper.insert(photoAlbum);
    }

    /**
     * 更新相册信息
     *
     * @param photoAlbumVo photoAlbumVo
     */
    @Override
    public void update(PhotoAlbumVo photoAlbumVo) {
        PhotoAlbum photoAlbum = new PhotoAlbum();
        BeanUtils.copyProperties(photoAlbumVo, photoAlbum);
        photoAlbumMapper.updateById(photoAlbum);
    }

    /**
     * 根据id删除相册
     *
     * @param id 相册id
     */
    @Override
    public void delete(Long id) {
        photoAlbumMapper.deleteById(id);
    }

    /**
     * 根据相册id查询相册信息
     *
     * @param id 相册id
     * @return 相册信息
     */
    @Override
    public PhotoAlbumVo info(Long id) {
        return getPhotoAlbumVo(photoAlbumMapper.selectById(id));
    }

    /**
     * 获取所有相册信息
     *
     * @return 所有相册信息
     */
    @Override
    public List<PhotoAlbumVo> getAllAlbums() {
        LambdaQueryWrapper<PhotoAlbum> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(PhotoAlbum::getStatus, 1, 0);
        List<PhotoAlbum> photoAlbumList = photoAlbumMapper.selectList(queryWrapper);
        List<PhotoAlbumVo> photoAlbumVoList = new ArrayList<>();
        for (PhotoAlbum photoAlbum : photoAlbumList) {
            photoAlbumVoList.add(getPhotoAlbumVo(photoAlbum));
        }
        return photoAlbumVoList;
    }

    private PhotoAlbumVo getPhotoAlbumVo(PhotoAlbum photoAlbum) {
        PhotoAlbumVo photoAlbumVo = new PhotoAlbumVo();
        photoAlbumVo.setPhotoCount(getPhotoCount(photoAlbum.getId()));
        BeanUtils.copyProperties(photoAlbum, photoAlbumVo);
        return photoAlbumVo;
    }

    private Integer getPhotoCount(Long albumId) {
        LambdaQueryWrapper<Photo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Photo::getAlbumId, albumId);
        return photoMapper.selectCount(queryWrapper);
    }
}
