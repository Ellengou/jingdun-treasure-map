package com.jd.controller.user;

import com.github.pagehelper.PageInfo;
import com.jd.common.mybatis.Pager;
import com.jd.core.ensure.Ensure;
import com.jd.dtos.EvaluationDto;
import com.jd.dtos.ItemDto;
import com.jd.dtos.ItemTagDto;
import com.jd.entity.user.ItemTag;
import com.jd.face.JsonResult;
import com.jd.request.EvaluationListRequest;
import com.jd.request.ItemListRequest;
import com.jd.request.ItemTagRequest;
import com.jd.response.EevaluationListResponse;
import com.jd.response.ItemListResponse;
import com.jd.response.ItemResponse;
import com.jd.response.TagResponse;
import com.jd.service.item.ItemService;
import com.jd.service.shop.CurioCityService;
import com.jd.service.shop.PictureService;
import com.jd.utils.CollectionUtils;
import com.jd.utils.DozerUtils;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by ellen on 2016/12/28.
 */
@Controller
@RequestMapping("/wap/item")
public class ItemController {

    @Autowired
    ItemService itemService;

    @Autowired
    DozerBeanMapper mapper;

    @Autowired
    PictureService pictureService;

    @Autowired
    CurioCityService curioCityService;

    /**
     * 商品列表 分頁
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult findItemList(ItemListRequest request,Pager pager) {
        ItemListRequest listRequest = request;
        ItemDto itemDto = null;
        if (listRequest != null)
            itemDto = mapper.map(listRequest, ItemDto.class);
        PageInfo<ItemDto> info = itemService.queryItemList(pager, itemDto);
        if (pager != null)
            pager = new Pager(pager.getPageNum(), pager.getPageSize(), info.getTotal());
        return new JsonResult(pager, DozerUtils.maps(info.getList(), ItemListResponse.class));
    }

    /**
     * 商品维度 评价列表 分頁
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/eval/page", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult findShopEvaluationPage(EvaluationListRequest request,Pager pager) {
        EvaluationListRequest evaluationListRequest = request;
        Ensure.that(evaluationListRequest).isNotNull("");
        Ensure.that(evaluationListRequest.getCurioCityId()).isNotNull("");
        EvaluationDto evaluationDto = mapper.map(evaluationListRequest, EvaluationDto.class);
        PageInfo<EvaluationDto> evaluationDtoPageInfo = curioCityService.queryEvaluationListByCurioId(pager, evaluationDto);
        if (pager != null)
            pager = new Pager(pager.getPageNum(), pager.getPageSize(), evaluationDtoPageInfo.getTotal());
        return new JsonResult(pager, DozerUtils.maps(evaluationDtoPageInfo.getList(), EevaluationListResponse.class));
    }


    /**
     * 商品详情
     *
     * @param itemId
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult findItemDetail(@PathVariable("id") Long itemId) {
        ItemDto dto = itemService.findDetailById(itemId);
        if (dto != null) {
            ItemResponse itemResponse = mapper.map(dto, ItemResponse.class);
            EvaluationDto dto1 = new EvaluationDto();
            List<EevaluationListResponse> listResponses;
            dto1.setItemId(dto.getId());
            //此方法后期需要分离调用 否则影响性能
            PageInfo<EvaluationDto> evaluationDtoPageInfo = itemService.queryEvaluationListByItemIdOrUserId(null, dto1);
            if (evaluationDtoPageInfo != null && CollectionUtils.isNotEmpty(evaluationDtoPageInfo.getList())) {
                listResponses = DozerUtils.maps(evaluationDtoPageInfo.getList(), EevaluationListResponse.class);
                itemResponse.setEevaluationList(listResponses);
            }
            return new JsonResult(itemResponse);
        }
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
        if (CollectionUtils.isNotEmpty(list))
            return new JsonResult(DozerUtils.maps(list, TagResponse.class));
        return new JsonResult();
    }

}
