package com.jd.controller;

import com.github.pagehelper.PageInfo;
import com.jd.common.mybatis.Pager;
import com.jd.constant.BusinessType;
import com.jd.core.ensure.Ensure;
import com.jd.core.utils.CollectionUtils;
import com.jd.core.utils.StringUtils;
import com.jd.dtos.ItemDto;
import com.jd.dtos.ItemTagDto;
import com.jd.entity.user.Item;
import com.jd.entity.user.ItemTag;
import com.jd.entity.user.Picture;
import com.jd.face.JsonResult;
import com.jd.request.CommonRequest;
import com.jd.request.ItemListRequest;
import com.jd.request.ItemRequest;
import com.jd.request.ItemTagRequest;
import com.jd.response.ItemListResponse;
import com.jd.response.TagResponse;
import com.jd.service.item.ItemService;
import com.jd.service.shop.PictureService;
import com.jd.utils.DozerUtils;
import com.jd.utils.FileUtil;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

/**
 * Created by ellen on 2016/12/28.
 */
@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    ItemService itemService;

    @Autowired
    DozerBeanMapper mapper;

    @Autowired
    PictureService pictureService;

    /**
     * 商品列表 分頁
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult findItemList(@RequestBody CommonRequest<ItemListRequest> request) {
        Pager pager = request.getPager();
        ItemListRequest listRequest = request.getParam(ItemListRequest.class);
        ItemDto itemDto = null;
        if (listRequest != null)
            itemDto = mapper.map(listRequest, ItemDto.class);
        PageInfo<ItemDto> info = itemService.queryItemList(pager, itemDto);
        if (pager != null)
            pager = new Pager(pager.getPageNum(), pager.getPageSize(), info.getTotal());
        return new JsonResult(pager, DozerUtils.maps(info.getList(), ItemListResponse.class));
    }

    /**
     * 商品新增 商品修改
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/add-update", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult addOrUpdateItem(@RequestBody CommonRequest<ItemRequest> request) {
        ItemRequest itemRequest = request.getParam(ItemRequest.class);
        Ensure.that(itemRequest).isNotNull("10000");
        String cid = itemRequest.getCid();
        List<String> itemViews = Arrays.asList(itemRequest.getItemViews());

        Item item = mapper.map(itemRequest, Item.class);
        if (item.getId()==null)
            item = itemService.saveItem(item);
        else
            item = itemService.updateItem(item);
        Ensure.that(item).isNotNull("40001");

        Picture picture = new Picture();
        picture.setName(FileUtil.getFilename(cid));
        picture.setForeignId(item.getId());
        picture.setPath(cid);
        picture.setPictureType(BusinessType.PictureType.ITEM_ID.name());
        Ensure.that(pictureService.savePicture(picture)).isNotNull("40010");
       if (CollectionUtils.isNotEmpty(itemViews))
           for (String url : itemViews){
               if (StringUtils.trimToNull(url)==null)
                   break;
                   picture = new Picture();
                   picture.setName(FileUtil.getFilename(url));
                   picture.setForeignId(item.getId());
                   picture.setPath(url);
                   picture.setPictureType(BusinessType.PictureType.ITEM.name());
                   Ensure.that(pictureService.savePicture(picture)).isNotNull("40008");
           }
        return new JsonResult();
    }

    /**
     * 商品上架 下架
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/market", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResult itemMarketOrNot(@RequestBody CommonRequest<ItemRequest> request) {
        ItemRequest itemRequest = request.getParam(ItemRequest.class);
        Ensure.that(itemRequest).isNotNull("10000");
        Ensure.that(itemRequest.getIds()).isNotNull("10001");
        if (itemRequest.getMarketAble())
            Ensure.that(itemService.marketAble(itemRequest.getMarketAble(),itemRequest.getIds())).isTrue("40006");
        else
            Ensure.that(itemService.marketAble(itemRequest.getMarketAble(),itemRequest.getIds())).isTrue("40007");
        return new JsonResult();
    }


    /**
     * 商品删除
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResult delItem(@RequestBody CommonRequest<ItemRequest> request) {
        ItemRequest itemRequest = request.getParam(ItemRequest.class);
        Ensure.that(itemRequest).isNotNull("10000");
        Ensure.that(itemRequest.getIds()).isNotNull("10001");
        Ensure.that(itemService.delItem(itemRequest.getIds())).isTrue("40005");
        return new JsonResult();
    }


    /**
     * 商品标签列表
     *
     * @return
     */
    @RequestMapping(value = "/tag/list", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult findItemDetail(ItemTagRequest request) {
        ItemTagDto dto = null;
        if (request!=null)
            dto = mapper.map(request,ItemTagDto.class);
        List<ItemTag> list = itemService.queryTagList(dto);
        if (com.jd.utils.CollectionUtils.isNotEmpty(list))
            return new JsonResult(DozerUtils.maps(list, TagResponse.class));
        else
            return new JsonResult(list);
    }

}
