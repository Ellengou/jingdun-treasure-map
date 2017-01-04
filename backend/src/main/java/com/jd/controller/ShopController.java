package com.jd.controller;

import com.github.pagehelper.PageInfo;
import com.jd.common.mybatis.Pager;
import com.jd.constant.BusinessType;
import com.jd.core.ensure.Ensure;
import com.jd.dtos.ShopDto;
import com.jd.dtos.TagDto;
import com.jd.entity.user.Picture;
import com.jd.entity.user.Shop;
import com.jd.entity.user.Tag;
import com.jd.face.JsonResult;
import com.jd.request.CommonRequest;
import com.jd.request.ShopListRequest;
import com.jd.request.ShopRequest;
import com.jd.request.TagRequest;
import com.jd.response.ShopResponse;
import com.jd.response.TagResponse;
import com.jd.service.shop.PictureService;
import com.jd.service.shop.ShopService;
import com.jd.utils.CollectionUtils;
import com.jd.utils.DozerUtils;
import com.jd.utils.FileUtil;
import com.jd.utils.StringUtil;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * Created by ellen on 2016/12/26.
 * 商户管理相关  保护店铺 标签 古玩城
 */
@Controller
@RequestMapping("/shop")
public class ShopController extends BaseController {

    @Autowired
    DozerBeanMapper mapper;

    @Autowired
    ShopService shopService;

    @Autowired
    PictureService pictureService;

    /**
     * 店铺列表 分頁
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult findShopPage(@RequestBody CommonRequest<ShopListRequest> request) {
        Pager pager = request.getPager();
        ShopListRequest shopListRequest = request.getParam(ShopListRequest.class);
        ShopDto shopDto = null;
        if (shopListRequest != null)
            shopDto = mapper.map(shopListRequest, ShopDto.class);
        PageInfo<ShopDto> info = shopService.queryShopList(pager, shopDto);
        if (pager != null)
            pager = new Pager(pager.getPageNum(), pager.getPageSize(), info.getTotal());
        return new JsonResult(pager, DozerUtils.maps(info.getList(), ShopResponse.class));
    }


    /**
     * 店铺列表 不分頁
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult findShopList(@RequestBody CommonRequest<ShopListRequest> request) {
        ShopListRequest shopListRequest = request.getParam(ShopListRequest.class);
        ShopDto shopDto = null;
        if (shopListRequest != null)
            shopDto = mapper.map(shopListRequest, ShopDto.class);
        PageInfo<ShopDto> info = shopService.queryShopList(null, shopDto);
        return new JsonResult(DozerUtils.maps(info.getList(), ShopResponse.class));
    }

    /**
     * 店铺詳情
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult verifyShop(@PathVariable("id") Long id) {
        ShopDto shop = shopService.findShopInfo(id);
        Ensure.that(shop).isNotNull("30001");
        return new JsonResult(mapper.map(shop, ShopResponse.class));
    }


    /**
     * 店铺删除
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResult delShop(@PathVariable("id") Long id) {
        Shop shop = shopService.findShopById(id);
        Ensure.that(shop).isNotNull("30001");
        shop = new Shop();
        shop.setId(id);
        shop.setDeleted(Boolean.TRUE);
        shop = shopService.updateShop(shop);
        Ensure.that(shop).isNotNull("30003");
        return new JsonResult();
    }

    /**
     * 新增店铺 修改店铺
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/add-update", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult saveOrUpdateShop(@RequestBody CommonRequest<ShopRequest> request) {
        ShopRequest shopRequest = request.getParam(ShopRequest.class);
        Ensure.that(shopRequest).isNotNull("10000");
        String banner = shopRequest.getBanner();
        String cid = shopRequest.getCid();
        List<String> shopViews = Arrays.asList(shopRequest.getShopViews());

        Shop shop = mapper.map(shopRequest, Shop.class);
        Shop result;
        if (shopRequest.getId() != null)
            result = shopService.updateShop(shop);
        else
            result = shopService.saveShop(shop);
        Ensure.that(result).isNotNull("20001");
        Picture picture;
        picture = new Picture();
        picture.setName(FileUtil.getFilename(banner));
        picture.setForeignId(shop.getId());
        picture.setPath(banner);
        picture.setPictureType(BusinessType.PictureType.SHOP_BANNER.name());
        pictureService.savePicture(picture);
        picture.setPath(cid);
        picture.setPictureType(BusinessType.PictureType.SHOP_ID.name());
        picture.setName(FileUtil.getFilename(cid));
        pictureService.savePicture(picture);
        if (CollectionUtils.isNotEmpty(shopViews))
            for (String url : shopViews) {
                if (StringUtil.trimToNull(url) == null)
                    break;
                picture.setPictureType(BusinessType.PictureType.SHOP_ID.name());
                picture.setName(FileUtil.getFilename(url));
                picture.setPath(url);
                pictureService.savePicture(picture);
            }
        return new JsonResult();
    }

    /**
     * 標簽列表 分頁
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/tag/page", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult getTagPage(@RequestBody CommonRequest<TagRequest> request) {
        Pager pager = request.getPager();
        TagRequest tagRequest = request.getParam(TagRequest.class);
        TagDto dto = null;
        if (tagRequest != null)
            dto = mapper.map(tagRequest, TagDto.class);
        return new JsonResult(pager, DozerUtils.maps(shopService.queryTagList(pager, dto), TagResponse.class));
    }

    /**
     * 商户標簽列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/tag/list", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult getTagList(@RequestBody CommonRequest<ShopRequest> request) {
        ShopRequest shopRequest = request.getParam(ShopRequest.class);
        Ensure.that(shopRequest).isNotNull("10000");
        Ensure.that(shopRequest.getId()).isNotNull("10001");
        List<Tag> tags = shopService.findTagListByShopId(shopRequest.getId());
        return new JsonResult(DozerUtils.maps(tags, TagResponse.class));
    }

    /**
     * 新增商户--标签关联
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/tag/add", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult saveTag(@RequestBody CommonRequest<TagRequest> request) {
        TagRequest tagRequest = request.getParam(TagRequest.class);
        Ensure.that(tagRequest).isNotNull("10000");
        Ensure.that(tagRequest.getTagIds()).isNotNull("10001");
        Ensure.that(shopService.saveShopTags(getLoginUserId(), Arrays.asList(tagRequest.getTagIds()))).isTrue("30004");
        return new JsonResult();
    }

    /**
     * 删除标签--商户关联
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/tag/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResult delTags(@RequestBody CommonRequest<TagRequest> request) {
        TagRequest tagRequest = request.getParam(TagRequest.class);
        Ensure.that(tagRequest).isNotNull("10000");
        Ensure.that(tagRequest.getTagIds()).isNotNull("10001");
        Ensure.that(shopService.delShopTagsById(getLoginUserId(), Arrays.asList(tagRequest.getTagIds()))).isTrue("30004");
        return new JsonResult();
    }
}
