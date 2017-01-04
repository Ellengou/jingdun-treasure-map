package com.jd.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.jd.entity.user.CityArea;
import com.jd.face.JsonResult;
import com.jd.response.CityAreaResponse;
import com.jd.service.basic.CityAreaService;
import com.jd.utils.DozerUtils;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Ellen.
 * @date 2016/12/26.
 * @since 1.0.
 * com.jd.controller .by jingdun.tech.
 */
@Controller
public class CityAreaController {

    @Autowired
    CityAreaService cityAreaService;

    @Autowired
    DozerBeanMapper mapper;

    @RequestMapping(value = "/city",method = RequestMethod.GET)
    @ResponseBody
    public JsonResult getCityInfoList() {
        CityArea cityArea = new CityArea();
        cityArea.setLevel(1);
        PageInfo<CityArea> page = cityAreaService.queryByAny(new Page(), cityArea);
        return new JsonResult(DozerUtils.maps(page.getList(), CityAreaResponse.class));
    }

    @RequestMapping(value = "/city/{code}",method = RequestMethod.GET)
    @ResponseBody
    public JsonResult getCityInfoListByCode(@PathVariable("code") String code) {
        return new JsonResult(DozerUtils.maps(cityAreaService.selectByCode(code), CityAreaResponse.class));
    }

    @RequestMapping(value = "/city/{code}/level/{level}",method = RequestMethod.GET)
    @ResponseBody
    public JsonResult getCityInfoListByCode(@PathVariable("code") String code, @PathVariable("level") String level) {
        return new JsonResult(DozerUtils.maps(cityAreaService.selectByCodeAndLevel(code, Integer.valueOf(level)), CityAreaResponse.class));
    }
}
