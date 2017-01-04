package com.jd.controller.user;

import com.github.pagehelper.PageInfo;
import com.jd.common.mybatis.Pager;
import com.jd.core.ensure.Ensure;
import com.jd.dtos.EvaluationDto;
import com.jd.dtos.ShopDto;
import com.jd.face.JsonResult;
import com.jd.request.*;
import com.jd.response.EevaluationListResponse;
import com.jd.response.ShopResponse;
import com.jd.service.shop.CurioCityService;
import com.jd.service.shop.PictureService;
import com.jd.service.shop.ShopService;
import com.jd.utils.DozerUtils;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ellen on 2016/12/26.
 * 商户管理相关  保护店铺 标签 古玩城
 */
@Controller
@RequestMapping("/wap/shop")
public class ShopController extends BaseController {

    @Autowired
    DozerBeanMapper mapper;

    @Autowired
    ShopService shopService;

    @Autowired
    PictureService pictureService;

    @Autowired
    CurioCityService curioCityService;

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
     * 店铺列表 分頁
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
     * 店铺维度 评价列表 分頁
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
        EvaluationDto evaluationDto = mapper.map(evaluationListRequest,EvaluationDto.class);
        PageInfo<EvaluationDto> evaluationDtoPageInfo = curioCityService.queryEvaluationListByCurioId(pager, evaluationDto);
        if (pager != null)
            pager = new Pager(pager.getPageNum(), pager.getPageSize(), evaluationDtoPageInfo.getTotal());
        return new JsonResult(pager, DozerUtils.maps(evaluationDtoPageInfo.getList(), EevaluationListResponse.class));
    }


}
