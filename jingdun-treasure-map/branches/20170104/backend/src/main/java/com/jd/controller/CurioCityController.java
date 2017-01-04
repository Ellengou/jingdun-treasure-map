package com.jd.controller;

import com.github.pagehelper.PageInfo;
import com.jd.common.mybatis.Pager;
import com.jd.constant.BusinessType;
import com.jd.core.ensure.Ensure;
import com.jd.dtos.CurioCityDto;
import com.jd.entity.user.CurioCity;
import com.jd.entity.user.Picture;
import com.jd.face.JsonResult;
import com.jd.request.CommonRequest;
import com.jd.request.CurioCityListRequest;
import com.jd.request.CurioCityRequest;
import com.jd.response.CurioCityListResponse;
import com.jd.response.CurioCityResponse;
import com.jd.service.shop.CurioCityService;
import com.jd.service.shop.PictureService;
import com.jd.utils.DozerUtils;
import com.jd.utils.FileUtil;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ellen.
 * @date 2016/12/29.
 * @since 1.0.
 * com.jd.controller .by jingdun.tech.
 * 古玩城相关
 */
@Controller
@RequestMapping("/curio")
public class CurioCityController {

    @Autowired
    DozerBeanMapper mapper;

    @Autowired
    CurioCityService curioCityService;

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
        return new JsonResult(pager, DozerUtils.maps(info.getList(), CurioCityListResponse.class));
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
        return new JsonResult(DozerUtils.maps(info.getList(), CurioCityListResponse.class));
    }

    /**
     * 古玩城詳情
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult verifyCurioCity(@PathVariable("id") Long id) {
        CurioCityDto CurioCity = curioCityService.findCurioCityInfo(id);
        Ensure.that(CurioCity).isNotNull("30001");
        return new JsonResult(mapper.map(CurioCity,CurioCityResponse.class));
    }

    /**
     * 古玩城删除
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResult delCurioCity(@PathVariable("id") Long id) {
        CurioCity curioCity = curioCityService.findCurioCityById(id);
        Ensure.that(curioCity).isNotNull("30001");
        curioCity = new CurioCity();
        curioCity.setId(id);
        curioCity.setDeleted(Boolean.TRUE);
        curioCity = curioCityService.updateCurioCity(curioCity);
        Ensure.that(curioCity).isNotNull("30003");
        return new JsonResult();
    }

    /**
     * 新增古玩城 修改古玩城
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/add-update", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult saveOrUpdateCurioCity(@RequestBody CommonRequest<CurioCityRequest> request) {
        CurioCityRequest curioCityRequest = request.getParam(CurioCityRequest.class);
        Ensure.that(curioCityRequest).isNotNull("10000");
        String banner = curioCityRequest.getBanner();
        CurioCity curioCity = mapper.map(curioCityRequest, CurioCity.class);
        curioCity.setBannerId(banner);
        CurioCity result;
        if (curioCityRequest.getId() != null)
            result = curioCityService.updateCurioCity(curioCity);
        else
            result = curioCityService.saveCurioCity(curioCity);
        Ensure.that(result).isNotNull("20001");
        Picture picture = new Picture();
        picture.setName(FileUtil.getFilename(banner));
        picture.setForeignId(curioCity.getId());
        picture.setPath(banner);
        picture.setPictureType(BusinessType.PictureType.CURIO_BANNER.name());
        pictureService.savePicture(picture);
        return new JsonResult();
    }

   
}
