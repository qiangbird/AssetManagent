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
import com.augmentum.ams.util.LogHelper;

@Service("userPageSizeService")
public class UserPageSizeServiceImpl implements UserPageSizeService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserPageSizeDao userPageSizeDao;

    private static Logger logger = Logger.getLogger(UserPageSizeServiceImpl.class);

    @Override
    public PageSize getUserPageSize(String userId, int categoryFlag) {

        logger.info(LogHelper.getLogInfo("get user page size start", userId, categoryFlag));

        User user = userDao.findUserByUserId(userId);

        if (null == user) {

            logger.info(LogHelper.getLogInfo("init user page size start", userId));

            user = userDao.getUserByUserId(userId);
            user.setPageSizeList(userPageSizeDao.findPageSizes());
            userDao.update(user);

            logger.info(LogHelper.getLogInfo("init user page size end", userId));
        }

        List<PageSize> list = user.getPageSizeList();

        for (PageSize pageSize : list) {

            if (categoryFlag == pageSize.getCategoryFlag()) {
                return pageSize;
            }
        }

        return null;
    }

    @Override
    public PageSize updateUserPageSize(String userId, int categoryFlag, int pageSizeValue) {

        logger.info(LogHelper.getLogInfo("update user page size start", userId, categoryFlag,
                pageSizeValue));

        User user = userDao.findUserByUserId(userId);

        if (null != user) {
            List<PageSize> pageSizes = new ArrayList<PageSize>();
            List<PageSize> list = user.getPageSizeList();

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
