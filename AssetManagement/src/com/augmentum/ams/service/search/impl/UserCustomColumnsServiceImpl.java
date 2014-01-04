package com.augmentum.ams.service.search.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.augmentum.ams.dao.asset.CustomizedColumnDao;
import com.augmentum.ams.dao.search.UserCustomColumnDao;
import com.augmentum.ams.dao.user.UserDao;
import com.augmentum.ams.model.asset.CustomizedColumn;
import com.augmentum.ams.model.enumeration.CategoryTypeEnum;
import com.augmentum.ams.model.user.User;
import com.augmentum.ams.model.user.UserCustomColumn;
import com.augmentum.ams.service.search.UserCustomColumnsService;
import com.augmentum.ams.util.LogHelper;
import com.augmentum.ams.util.UTCTimeUtil;

@Service("userCustomColumnsService")
public class UserCustomColumnsServiceImpl implements UserCustomColumnsService {

    @Autowired
    private UserCustomColumnDao userCustomColumnDao;

    @Autowired
    private CustomizedColumnDao customizedColumnDao;

    @Autowired
    private UserDao userDao;

    private static Logger logger = Logger.getLogger(UserCustomColumnsServiceImpl.class);

    @Override
    public List<UserCustomColumn> findUserCustomColumns(String category, String userId) {

        logger.info(LogHelper.getLogInfo("get user customColumns start", category, userId));

        List<UserCustomColumn> userCustomColumns = userCustomColumnDao.getUserCustomColumns(
                category, userId);

        if (null == userCustomColumns || 0 == userCustomColumns.size()) {
            initUserCustomColumn(userId);
            userCustomColumns = userCustomColumnDao.getUserCustomColumns(category, userId);
        }
        logger.info(LogHelper.getLogInfo("get userCustomColumn end, column size", userCustomColumns
                .size()));
        return userCustomColumns;
    }

    @Override
    public void updateCustomizedColumns(Map<String, Boolean> customizedColumnIds, String userId) {

        logger.info(LogHelper.getLogInfo("update user customColumns start", customizedColumnIds,
                userId));

        // temp will be used to update columns sequence
        int temp = 1;
        Date currentTime = UTCTimeUtil.localDateToUTC();
        List<UserCustomColumn> userCustomColumns = new ArrayList<UserCustomColumn>();

        for (Map.Entry<String, Boolean> entry : customizedColumnIds.entrySet()) {
            String customizedColumnId = entry.getKey().toString();
            Boolean showDefault = entry.getValue().booleanValue();
            UserCustomColumn userCustomColumn = userCustomColumnDao
                    .getUserCustomColumn(customizedColumnId);

            if (null != userCustomColumn) {
                userCustomColumn.setSequence(temp++);
                userCustomColumn.setShowDefault(showDefault);
                userCustomColumn.setUpdatedTime(currentTime);
            }
            userCustomColumns.add(userCustomColumn);
        }
        userCustomColumnDao.saveOrUpdateAll(userCustomColumns);

        logger.info("update user custom columns end");
    }

    @Override
    public void initUserCustomColumn(String userId) {

        logger.info(LogHelper.getLogInfo("init user custom columns start", userId));

        List<UserCustomColumn> userCustomColumns = userCustomColumnDao.getUserCustomColumns(
                CategoryTypeEnum.ASSET.getCategoryType(), userId);

        if (!(null != userCustomColumns && userCustomColumns.size()>0)) {
            List<CustomizedColumn> customizedColumns = customizedColumnDao.findAllColumns();
            User user = userDao.getUserByUserId(userId);
            Date currentTime = UTCTimeUtil.localDateToUTC();

            for (CustomizedColumn customizedColumn : customizedColumns) {
                UserCustomColumn userCustomColumn = new UserCustomColumn();

                userCustomColumn.setCreatedTime(currentTime);
                userCustomColumn.setUpdatedTime(currentTime);
                userCustomColumn.setSequence(customizedColumn.getSequence());
                userCustomColumn.setShowDefault(customizedColumn.getIsMustShow());
                userCustomColumn.setCustomizedColumn(customizedColumn);
                userCustomColumn.setUser(user);

                userCustomColumns.add(userCustomColumn);
            }
            userCustomColumnDao.saveOrUpdateAll(userCustomColumns);

            logger.info(LogHelper.getLogInfo(
                    "init user custom columns end, user custom columns size"));
        }
    }

}
