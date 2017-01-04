package com.jd.service.shop.impl;

import com.jd.dao.mapper.user.PictureMapperExt;
import com.jd.entity.user.Picture;
import com.jd.service.shop.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ellen on 2016/12/26.
 */
@Service
public class PictureServiceImpl implements PictureService{

    @Autowired
    PictureMapperExt pictureMapperExt;

    @Override
    public Picture savePicture(Picture picture) {
       Picture pic =  pictureMapperExt.selectByBusinessIdAndPicType(picture.getForeignId(),picture.getPictureType());
       if (pic!=null) {
           pic.setPath(picture.getPath());
           pictureMapperExt.updateByPrimaryKey(pic);
           return pic;
       }else {
           int id = pictureMapperExt.insertSelective(picture);
           return id > 0 ? pictureMapperExt.selectByPrimaryKey(Long.valueOf(id)) : null;
       }
    }

    @Override
    public Picture updatePicture(Picture picture) {
        return pictureMapperExt.updateByPrimaryKeySelective(picture)>0?pictureMapperExt.selectByPrimaryKey(picture.getId()):null;
    }
}
