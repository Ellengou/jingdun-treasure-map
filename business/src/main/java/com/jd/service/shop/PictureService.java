package com.jd.service.shop;

import com.jd.entity.user.Picture;

/**
 * Created by ellen on 2016/12/26.
 */
public interface PictureService {
    Picture savePicture(Picture picture);

    Picture updatePicture(Picture picture);
}
