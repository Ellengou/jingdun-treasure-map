package com.jd.controller.user;

import com.github.pagehelper.PageInfo;
import com.jd.common.mybatis.Pager;
import com.jd.core.ensure.Ensure;
import com.jd.dtos.CurioCityDto;
import com.jd.dtos.EvaluationDto;
import com.jd.dtos.ShopDto;
import com.jd.face.JsonResult;
import com.jd.request.CommonRequest;
import com.jd.request.CurioCityListRequest;
import com.jd.request.EvaluationListRequest;
import com.jd.request.ShopListRequest;
import com.jd.response.*;
import com.jd.service.shop.CurioCityService;
import com.jd.service.shop.PictureService;
import com.jd.service.shop.ShopService;
import com.jd.utils.CollectionUtils;
import com.jd.utils.DozerUtils;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ellen.
 * @date 2016/12/29.
 * @since 1.0.
 * com.jd.controller .by jingdun.tech.
 * 古玩城相关
 */
@Controller
@RequestMapping("/wap/curio")
public class CurioCityController {

    @Autowired
    DozerBeanMapper mapper;

    @Autowired
    CurioCityService curioCityService;

    @Autowired
    ShopService shopService;

    @Autowired
    PictureService pictureService;

    /**
     * 古玩城列表 分頁
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult findCurioCityPage(@RequestBody CommonRequest<CurioCityListRequest> request) {
        Pager pager = request.getPager();
        CurioCityListRequest CurioCityListRequest = request.getParam(CurioCityListRequest.class);
        CurioCityDto curioCityDto = null;
        if (CurioCityListRequest != null)
            curioCityDto = mapper.map(CurioCityListRequest, CurioCityDto.class);
        PageInfo<CurioCityDto> info = curioCityService.queryCurioCityList(pager, curioCityDto);
        if (pager != null)
            pager = new Pager(pager.getPageNum(), pager.getPageSize(), info.getTotal());
        return new JsonResult(pager, DozerUtils.maps(info.getList(), CurioCityResponse.class));
    }


    /**
     * 古玩城列表 不分頁
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult findCurioCityList(@RequestBody CommonRequest<CurioCityListRequest> request) {
        CurioCityListRequest CurioCityListRequest = request.getParam(CurioCityListRequest.class);
        CurioCityDto curioCityDto = null;
        if (CurioCityListRequest != null)
            curioCityDto = mapper.map(CurioCityListRequest, CurioCityDto.class);
        PageInfo<CurioCityDto> info = curioCityService.queryCurioCityList(null, curioCityDto);
        return new JsonResult(DozerUtils.maps(info.getList(), CurioCityResponse.class));
    }

    /**
     * 古玩城詳情
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult verifyCurioCity(@PathVariable("id") Long id) {
        CurioCityDto CurioCity = curioCityService.findCurioCityInfo(id);
        Ensure.that(CurioCity).isNotNull("30001");
        return new JsonResult(mapper.map(CurioCity, CurioCityResponse.class));
    }

    /**
     * 古玩城 店铺联合展示
     * ok
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult queryCurioCityAndShop(CurioCityListRequest request, String key, String cityCode) {
        CurioCityListRequest CurioCityListRequest = request;
        CurioCityDto curioCityDto = null;
        List<ShopDto> shopDtoList;
        ShopAndCurioResponse shopAndCurioResponse = new ShopAndCurioResponse();
        ShopDto shopDto = null;
        if (CurioCityListRequest != null)
            curioCityDto = mapper.map(CurioCityListRequest, CurioCityDto.class);
        if (CurioCityListRequest != null)
            shopDto = mapper.map(CurioCityListRequest, ShopDto.class);
        List<CurioCityDto> cityDtoList = curioCityService.queryList(curioCityDto);
        if (CollectionUtils.isNotEmpty(cityDtoList)) {
            shopAndCurioResponse.setCurios(DozerUtils.maps(cityDtoList, CurioCityResponse.class));
        }
        shopDtoList = shopService.queryList(shopDto);
        if (CollectionUtils.isNotEmpty(shopDtoList))
            shopAndCurioResponse.setShops(DozerUtils.maps(shopDtoList, ShopResponse.class));
        return new JsonResult(shopAndCurioResponse);
    }


    /**
     * 古玩城店铺列表 分頁
     * ok
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/shop/page", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult findCurioCityShopPage(Pager pager, ShopListRequest request) {
        ShopListRequest shopListRequest = request;
        ShopDto shopDto = null;
        if (shopListRequest != null) {
            shopDto = mapper.map(shopListRequest, ShopDto.class);
        }
        PageInfo<ShopDto> shopDtoPageInfo = shopService.queryShopList(pager, shopDto);
        if (pager != null)
            pager = new Pager(pager.getPageNum(), pager.getPageSize(), shopDtoPageInfo.getTotal());
        return new JsonResult(pager, DozerUtils.maps(shopDtoPageInfo.getList(), ShopListResponse.class));
    }

    /**
     * 古玩城维度 评价列表 分頁
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/eval/page", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult findCurioCityEvaluationPage(Pager pager, EvaluationListRequest request) {
        EvaluationListRequest evaluationListRequest = request;
        Ensure.that(evaluationListRequest).isNotNull("10000");
        Ensure.that(evaluationListRequest.getCurioCityId()).isNotNull("10001");
        EvaluationDto evaluationDto = mapper.map(evaluationListRequest, EvaluationDto.class);
        PageInfo<EvaluationDto> evaluationDtoPageInfo = curioCityService.queryEvaluationListByCurioId(pager, evaluationDto);
        if (pager != null)
            pager = new Pager(pager.getPageNum(), pager.getPageSize(), evaluationDtoPageInfo.getTotal());
        return new JsonResult(pager, DozerUtils.maps(evaluationDtoPageInfo.getList(), EevaluationListResponse.class));
    }

}
