package com.erotsx.blog.controller;

import com.erotsx.blog.service.TagService;
import com.erotsx.blog.vo.Result;
import com.erotsx.blog.vo.TagVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("tag")
public class TagController {

    @Resource
    private TagService tagService;

    /**
     * @param limit 设置返回数目
     * @return 根据带有此标签的数量降序返回标签
     */
    @GetMapping("getHotTags")
    public Result<List<TagVo>> getTags(@RequestParam(defaultValue = "10") int limit) {
        return Result.success(tagService.getTags(limit));
    }

    /**
     * @return 获取所有标签
     */
    @GetMapping("getAllTags")
    public Result<List<TagVo>> getAllTags() {
        return Result.success(tagService.getAllTags());
    }
}
