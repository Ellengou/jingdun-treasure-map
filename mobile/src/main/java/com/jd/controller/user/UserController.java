package com.jd.controller.user;

import com.github.pagehelper.PageInfo;
import com.jd.common.mybatis.Pager;
import com.jd.constant.BusinessType;
import com.jd.core.ensure.Ensure;
import com.jd.dtos.EvaluationDto;
import com.jd.entity.user.Evaluation;
import com.jd.entity.user.Picture;
import com.jd.face.JsonResult;
import com.jd.request.CommonRequest;
import com.jd.request.EvaluationListRequest;
import com.jd.request.EvaluationRequest;
import com.jd.response.EevaluationListResponse;
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
 * Created by renchao on 2017/1/5.
 */
@Controller
@RequestMapping("/wap/user")
public class UserController {


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
    public JsonResult itemEvaluation(@RequestBody CommonRequest<EvaluationRequest> request) {
        EvaluationRequest evaluationRequest = request.getParam(EvaluationRequest.class);
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
}
