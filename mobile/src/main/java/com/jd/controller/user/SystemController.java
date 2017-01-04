package com.jd.controller.user;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.jd.common.mybatis.Pager;
import com.jd.core.ensure.Ensure;
import com.jd.dtos.TagDto;
import com.jd.entity.user.Tag;
import com.jd.face.JsonResult;
import com.jd.face.Result;
import com.jd.request.CommonRequest;
import com.jd.request.TagRequest;
import com.jd.response.MenusResponse;
import com.jd.response.TagListResponse;
import com.jd.response.TagResponse;
import com.jd.response.UserResponse;
import com.jd.service.account.AccountService;
import com.jd.service.shop.PictureService;
import com.jd.service.shop.ShopService;
import com.jd.utils.CollectionUtils;
import com.jd.utils.DozerUtils;
import com.jd.utils.StringUtil;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
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
    public JsonResult getPage(@RequestBody CommonRequest<TagRequest> request) {
        TagRequest tagRequest = request.getParam(TagRequest.class);
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
    public JsonResult getTagList(CommonRequest<TagRequest> request) {
        TagRequest tagRequest = request.getParam(TagRequest.class);
        TagDto dto = null;
        if (tagRequest != null) {
            dto = mapper.map(tagRequest, TagDto.class);
            dto.setKey(StringUtil.trimToNull(dto.getKey()));
        }
        List<Tag> sysTagList = shopService.queryTagList(null, dto);
        List<Tag> userTagList = null;
        if (getLoginUserId() != null)
            userTagList = shopService.queryUserTagList(dto, getLoginUserId());
        TagListResponse tagListResponse = new TagListResponse();
        if (CollectionUtils.isNotEmpty(sysTagList)) {
            if (CollectionUtils.isNotEmpty(userTagList)) {
                sysTagList = (List<Tag>) CollectionUtils.removeAll(sysTagList, userTagList);
                tagListResponse.setUserTags(DozerUtils.maps(userTagList, TagResponse.class));
            }
            tagListResponse.setSysTags(DozerUtils.maps(sysTagList, TagResponse.class));
        }
        return new JsonResult(tagListResponse);
    }

    /**
     * 新增标签 修改標簽
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResult saveOrUpdateTag(@RequestBody CommonRequest<TagRequest> request) {
        TagRequest tag = request.getParam(TagRequest.class);
        Ensure.that(tag).isNotNull("10000");
        Boolean res = shopService.delShopTagsById(tag.getBusinessId(), Arrays.asList(tag.getId()));
        Ensure.that(res).isTrue("20001");
        return new JsonResult();
    }

    /**
     * 删除标签
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult delTag(@RequestBody CommonRequest<TagRequest> request) {
        TagRequest tag = request.getParam(TagRequest.class);
        Ensure.that(tag).isNotNull("10000");
        Ensure.that(shopService.saveShopTags(tag.getId(), Arrays.asList(tag.getId()))).isTrue("20001");
        return new JsonResult();
    }

}
