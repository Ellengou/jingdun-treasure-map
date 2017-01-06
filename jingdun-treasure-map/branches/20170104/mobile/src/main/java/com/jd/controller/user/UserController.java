package com.jd.controller.user;

import com.github.pagehelper.PageInfo;
import com.jd.common.mybatis.Pager;
import com.jd.constant.BusinessType;
import com.jd.core.ensure.Ensure;
import com.jd.dtos.EvaluationDto;
import com.jd.dtos.ItemTagDto;
import com.jd.entity.user.Evaluation;
import com.jd.entity.user.Favorites;
import com.jd.entity.user.ItemTag;
import com.jd.entity.user.Picture;
import com.jd.face.JsonResult;
import com.jd.request.*;
import com.jd.response.EevaluationListResponse;
import com.jd.response.TagResponse;
import com.jd.service.item.ItemService;
import com.jd.service.shop.CurioCityService;
import com.jd.service.shop.PictureService;
import com.jd.utils.*;
import com.jd.webkits.filter.WordFilter;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by renchao on 2017/1/5.
 */
@Controller
@RequestMapping("/wap/user")
public class UserController extends BaseController{


    @Autowired
    ItemService itemService;

    @Autowired
    DozerBeanMapper mapper;

    @Autowired
    PictureService pictureService;

    @Autowired
    CurioCityService curioCityService;

    /**
     * 商品评价
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/eval", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult itemEvaluation(EvaluationRequest request) {
        EvaluationRequest evaluationRequest = request;
        Ensure.that(evaluationRequest).isNotNull("50002");
        Ensure.that(evaluationRequest.getContent()).isNotNull("50002");
        Evaluation evaluation = mapper.map(evaluationRequest, Evaluation.class);
        evaluation.setContent(WordFilter.check(evaluationRequest.getContent()));
        Evaluation result = itemService.saveEvaluation(evaluation);
        Ensure.that(result).isNotNull("50001");
        Picture picture = new Picture();
        picture.setForeignId(result.getId());
        String[] views = evaluationRequest.getViews();
        if (ArrayUtils.isNotEmpty(views))
            for (String path : views) {
                if (StringUtil.trimToNull(path) == null)
                    break;
                picture.setPictureType(BusinessType.PictureType.EVAL.name());
                picture.setName(FileUtil.getFilename(path));
                picture.setPath(path);
                Ensure.that(pictureService.savePicture(picture)).isNotNull("50000");
            }
        return new JsonResult();
    }

    /**
     * 用户评价列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/eval/page", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult findUserEvaluationPage(Pager pager, EvaluationListRequest request) {
        EvaluationListRequest evaluationListRequest = request;
        Ensure.that(evaluationListRequest).isNotNull("10000");
        Ensure.that(evaluationListRequest.getUserId()).isNotNull("10001");
        EvaluationDto evaluationDto = mapper.map(evaluationListRequest, EvaluationDto.class);
        PageInfo<EvaluationDto> evaluationDtoPageInfo = itemService.queryEvaluationListByItemIdOrUserId(pager, evaluationDto);
        if (pager != null)
            pager = new Pager(pager.getPageNum(), pager.getPageSize(), evaluationDtoPageInfo.getTotal());
        return new JsonResult(pager, DozerUtils.maps(evaluationDtoPageInfo.getList(), EevaluationListResponse.class));
    }


    /**
     * 商品收藏
     *
     * @return
     */
    @RequestMapping(value = "/favorite/{itemId}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult itemFavorite(@PathVariable("itemId") Long itemId) {
        Favorites favorites = new Favorites();
        favorites.setBusinessId(itemId);
        favorites.setType(BusinessType.FavoritesType.ITEM.name());
        favorites.setUserId(getLoginUserId());
        Favorites res = itemService.saveFavorites(favorites);
        Ensure.that(res).isNotNull("USER_1022");
        return new JsonResult();
    }
}
