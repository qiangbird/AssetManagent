package com.augmentum.ams.service.search.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.augmentum.ams.dao.search.UserPageSizeDao;
import com.augmentum.ams.dao.user.UserDao;
import com.augmentum.ams.model.base.PageSize;
import com.augmentum.ams.model.user.User;
import com.augmentum.ams.service.search.UserPageSizeService;

@Service("userPageSizeService")
public class UserPageSizeServiceImpl implements UserPageSizeService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserPageSizeDao userPageSizeDao;

    private static Logger logger = Logger.getLogger(UserPageSizeServiceImpl.class);

    @Override
    public PageSize getUserPageSize(String userId, int categoryFlag) {

        logger.info("get userPageSize start, [userId], [categoryFlag]: " + userId + "," + categoryFlag);
        User user = userDao.findUserByUserId(userId);
        logger.info("judge user is null when updateUserPageSize, user is null: " + (null == user));
        
        if (null == user) {

            logger.info("init user page size start, userId: " + userId);

            user = userDao.getUserByUserId(userId);
            user.setPageSizeList(userPageSizeDao.findPageSizes());
            userDao.update(user);

            logger.info("init user page size end, userId: " + userId);
        }

        List<PageSize> list = user.getPageSizeList();
        logger.info("userPageSize list, list size: " + list.size());

        for (PageSize pageSize : list) {

            if (categoryFlag == pageSize.getCategoryFlag()) {
                return pageSize;
            }
        }

        return null;
    }

    @Override
    public PageSize updateUserPageSize(String userId, int categoryFlag, int pageSizeValue) {

        logger.info("update user page size start, [userId], [categoryFlag], [pageSizeVlalue]"
        		+ userId + "," + categoryFlag + "," + pageSizeValue);

        User user = userDao.findUserByUserId(userId);
        logger.info("judge user is null when updateUserPageSize, user is null: " + (null == user));

        if (null != user) {
            List<PageSize> pageSizes = new ArrayList<PageSize>();
            List<PageSize> list = user.getPageSizeList();
            logger.info("userPageSize list, list size: " + list.size());

            for (PageSize pageSize : list) {

                if (categoryFlag != pageSize.getCategoryFlag()) {
                    pageSizes.add(pageSize);
                } else {
                    PageSize ps = userPageSizeDao.getPageSize(categoryFlag, pageSizeValue);
                    ps.setPageSizeValue(pageSizeValue);
                    pageSizes.add(ps);
                }
            }
            user.setPageSizeList(pageSizes);
            userDao.update(user);
        }

        logger.info("update user page size end");
        return getUserPageSize(userId, categoryFlag);
    }

}
