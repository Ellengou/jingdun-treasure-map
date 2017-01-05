package com.jd.controller.user;

import com.github.pagehelper.PageInfo;
import com.jd.common.mybatis.Pager;
import com.jd.constant.BusinessType;
import com.jd.core.ensure.Ensure;
import com.jd.dtos.EvaluationDto;
import com.jd.dtos.ItemDto;
import com.jd.entity.user.Evaluation;
import com.jd.entity.user.Picture;
import com.jd.face.JsonResult;
import com.jd.request.CommonRequest;
import com.jd.request.EvaluationListRequest;
import com.jd.request.EvaluationRequest;
import com.jd.request.ItemListRequest;
import com.jd.response.EevaluationListResponse;
import com.jd.response.ItemListResponse;
import com.jd.service.item.ItemService;
import com.jd.service.shop.CurioCityService;
import com.jd.service.shop.PictureService;
import com.jd.utils.ArrayUtils;
import com.jd.utils.DozerUtils;
import com.jd.utils.FileUtil;
import com.jd.utils.StringUtil;
import com.jd.webkits.filter.WordFilter;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
     * 商品维度 评价列表 分頁
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/eval/page", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult findShopEvaluationPage(@RequestBody CommonRequest<EvaluationListRequest> request) {
        Pager pager = request.getPager();
        EvaluationListRequest evaluationListRequest = request.getParam(EvaluationListRequest.class);
        Ensure.that(evaluationListRequest).isNotNull("");
        Ensure.that(evaluationListRequest.getCurioCityId()).isNotNull("");
        EvaluationDto evaluationDto = mapper.map(evaluationListRequest, EvaluationDto.class);
        PageInfo<EvaluationDto> evaluationDtoPageInfo = curioCityService.queryEvaluationListByCurioId(pager, evaluationDto);
        if (pager != null)
            pager = new Pager(pager.getPageNum(), pager.getPageSize(), evaluationDtoPageInfo.getTotal());
        return new JsonResult(pager, DozerUtils.maps(evaluationDtoPageInfo.getList(), EevaluationListResponse.class));
    }

}
