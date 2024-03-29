package com.erotsx.blog.controller;

import com.erotsx.blog.common.api.Result;
import com.erotsx.blog.entity.Tag;
import com.erotsx.blog.service.TagService;
import com.erotsx.blog.vo.PageVo;
import com.erotsx.blog.vo.TagVo;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("tag")
public class TagController {

    @Resource
    private TagService tagService;


    /**
     * 获取热门标签
     *
     * @param limit 设置返回数目
     * @return 根据带有此标签的数量降序返回标签
     */
    @GetMapping("getHotTags")
    public Result<List<TagVo>> getTags(@RequestParam(defaultValue = "10") int limit) {
        return Result.success(tagService.getTags(limit));
    }

    /**
     * 获取所有标签
     *
     * @return 所有标签及数量
     */
    @GetMapping("getAllTags")
    public Result<PageVo<TagVo>> getAllTags() {
        return Result.success(tagService.getAllTags());
    }

    /**
     * 搜索标签
     *
     * @param keyword  关键词
     * @param page     page
     * @param pageSize pageSize
     * @return 分页后的 TagVo
     */
    @GetMapping("search")
    public Result<PageVo<TagVo>> search(@RequestParam(required = false) String keyword,
                                        @RequestParam(required = false, defaultValue = "1") int page,
                                        @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return Result.success(tagService.search(keyword, page, pageSize));
    }

    /**
     * 删除标签
     *
     * @param id 标签id
     * @return 删除成功
     */
    @DeleteMapping("delete/{id}")
    public Result<?> delete(@PathVariable Long id) {
        tagService.deleteById(id);
        return Result.success(null, "删除成功");
    }

    /**
     * 添加标签
     *
     * @param tag Tag 类
     * @return msg
     */
    @PostMapping("add")
    public Result<?> add(@RequestBody Tag tag) {
        tagService.add(tag);
        return Result.success(null, "添加成功");
    }

    /**
     * 修改标签信息
     *
     * @param tag 标签信息
     * @return msg
     */
    @PutMapping("update")
    public Result<?> update(@RequestBody Tag tag) {
        tagService.update(tag);
        return Result.success(null, "修改成功");
    }

    /**
     * 查询标签信息
     *
     * @param id 根据Id查询标签信息
     * @return tagVo
     */
    @GetMapping("info/{id}")
    public Result<TagVo> info(@PathVariable Long id) {
        return Result.success(tagService.getInfo(id));
    }

}
