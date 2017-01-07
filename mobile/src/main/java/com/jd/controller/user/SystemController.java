package com.jd.controller.user;

import com.github.pagehelper.PageInfo;
import com.jd.core.ensure.Ensure;
import com.jd.dtos.TagDto;
import com.jd.entity.user.Tag;
import com.jd.face.JsonResult;
import com.jd.request.TagRequest;
import com.jd.response.TagListResponse;
import com.jd.response.TagResponse;
import com.jd.service.account.AccountService;
import com.jd.service.shop.PictureService;
import com.jd.service.shop.ShopService;
import com.jd.utils.CollectionUtils;
import com.jd.utils.DozerUtils;
import com.jd.utils.StringUtil;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

/**
 * @author Ellen.
 * @date 2016/12/22.
 * @since 1.0.
 * com.jd.controller .by jingdun.tech.
 */
@Controller
@RequestMapping("/wap/tag")
public class SystemController extends BaseController {

    @Autowired
    AccountService accountService;

    @Autowired
    DozerBeanMapper mapper;

    @Autowired
    PictureService pictureService;

    @Autowired
    ShopService shopService;


    /**
     * 我的標簽列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/mine-list", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult getPage(TagRequest request) {
        TagRequest tagRequest = request;
        TagDto dto = null;
        if (tagRequest != null)
            dto = mapper.map(tagRequest, TagDto.class);
        List<Tag> tagList = shopService.queryUserTagList(dto, getLoginUserId());
        return new JsonResult(DozerUtils.maps(tagList, TagResponse.class));
    }


    /**
     * 標簽列表 不分頁
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult getTagList(TagRequest request) {
        TagRequest tagRequest = request;
        TagDto dto = null;
        if (tagRequest != null) {
            dto = mapper.map(tagRequest, TagDto.class);
            dto.setKey(StringUtil.trimToNull(dto.getKey()));
        }
        PageInfo<Tag> pageInfo = shopService.queryTagList(null, dto);
        List<Tag> sysTagList = pageInfo.getList();
        List<Tag> userTagList = null;
        if (getLoginUserId() != null)
            userTagList = shopService.queryUserTagList(dto, getLoginUserId());
        TagListResponse tagListResponse = new TagListResponse();
        if (CollectionUtils.isNotEmpty(pageInfo.getList())) {
            if (CollectionUtils.isNotEmpty(userTagList)) {
                sysTagList = (List<Tag>) CollectionUtils.removeAll(sysTagList, userTagList);
                tagListResponse.setUserTags(DozerUtils.maps(userTagList, TagResponse.class));
            }
            tagListResponse.setSysTags(DozerUtils.maps(sysTagList, TagResponse.class));
        }
        return new JsonResult(tagListResponse);
    }

    /**
     * 删除标签
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResult saveOrUpdateTag(TagRequest request) {
        TagRequest tag = request;
        Ensure.that(tag).isNotNull("10000");
        Boolean res = shopService.delShopTagsById(tag.getBusinessId(), Arrays.asList(tag.getId()));
        Ensure.that(res).isTrue("20001");
        return new JsonResult();
    }

    /**
     * 新增标签
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult delTag(TagRequest request) {
        TagRequest tag = request;
        Ensure.that(tag).isNotNull("10000");
        Ensure.that(shopService.saveShopTags(tag.getId(), Arrays.asList(tag.getId()))).isTrue("20001");
        return new JsonResult();
    }

    /**
     * 标签排序
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/sort", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResult sortTag(TagRequest request) {
        TagRequest tag = request;
        Ensure.that(tag).isNotNull("10000");
        Ensure.that(request.getTags()).isNotNull("10001");
        Ensure.that(shopService.updateTagSort(request.getTags())).isTrue("20001");
        return new JsonResult();
    }

}
