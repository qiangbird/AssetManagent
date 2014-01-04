package com.augmentum.ams.service.customized.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.WildcardQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.augmentum.ams.dao.customized.CustomizedViewItemDao;
import com.augmentum.ams.exception.BaseException;
import com.augmentum.ams.model.customized.CustomizedView;
import com.augmentum.ams.model.customized.CustomizedViewItem;
import com.augmentum.ams.model.enumeration.ColumnTypeEnum;
import com.augmentum.ams.service.customized.CustomizedViewItemService;
import com.augmentum.ams.service.customized.CustomizedViewService;
import com.augmentum.ams.util.Constant;
import com.augmentum.ams.util.LogHelper;
import com.augmentum.ams.util.UTCTimeUtil;
import com.augmentum.ams.web.vo.customized.CustomizedViewVo;

@Service("customizedViewItemService")
public class CustomizedViewItemServiceImpl implements CustomizedViewItemService {

    private Logger logger = Logger.getLogger(CustomizedViewItemServiceImpl.class);

    private static String rangSearchGroup[] = { Constant.LESS_THAN, Constant.LESS_OR_EQUAL,
            Constant.GREATER_THAN, Constant.GREATER_OR_EQUAL };

    private static String termSearchGroup[] = { Constant.IS, Constant.IS_NOT, Constant.TRUE,
            Constant.FALSE };

    private static String containsSearchGroup[] = { Constant.CONTAINS, Constant.NOT_CONTAINS,
            Constant.START_WITH };

    @Autowired
    private CustomizedViewItemDao customizedViewItemDao;

    @Autowired
    private CustomizedViewService customizedViewService;

    /*
     * (non-Javadoc)
     * 
     * @see com.augmentum.ams.service.customized.CustomizedViewItemService#
     * saveCusomzieViewItem
     * (com.augmentum.ams.model.customized.CustomizedViewItem)
     */
    @Override
    public void saveCustomizedViewItem(CustomizedViewVo customizedViewVo) {

        logger.info(LogHelper.getLogInfo("Save customized view item start"));

        CustomizedView customizedView = customizedViewService.saveCustomizedView(customizedViewVo);

        int length = customizedViewVo.getColumns().length;
        Date time = UTCTimeUtil.localDateToUTC();
        if (0 < length) {
            List<CustomizedViewItem> customizedViewItems = new ArrayList<CustomizedViewItem>();

            for (int i = 0; i < length; i++) {
                CustomizedViewItem customizedViewItem = new CustomizedViewItem();

                customizedViewItem.setColumnName(customizedViewVo.getColumns()[i]);
                customizedViewItem.setSearchCondition(customizedViewVo.getSearchConditions()[i]);
                customizedViewItem.setValue(customizedViewVo.getValues()[i]);
                customizedViewItem.setColumnType(customizedViewVo.getColumnTypes()[i]);
                customizedViewItem.setSearchColumn(customizedViewVo.getSearchColumns()[i]);
                customizedViewItem.setRealTable(customizedViewVo.getRealTables()[i]);
                customizedViewItem.setCreatedTime(time);
                customizedViewItem.setCustomizedView(customizedView);
                customizedViewItem.setUpdatedTime(time);

                customizedViewItems.add(customizedViewItem);
            }
            customizedViewItemDao.saveCustomizedViewItem(customizedViewItems);
        }
        logger.info(LogHelper.getLogInfo("Save customized view item end"));
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.augmentum.ams.service.customized.CustomizedViewItemService#
     * deleteCusomzieViewItem
     * (com.augmentum.ams.model.customized.CustomizedViewItem)
     */
    @Override
    public void deleteCustomizedViewItem(CustomizedViewVo customizedViewVo) {

        logger.info(LogHelper.getLogInfo("Delete customized view item start"));

        List<CustomizedViewItem> customizedViewItems = this.findByCustomizedViewId(customizedViewVo
                .getCustomizedViewId());

        for (CustomizedViewItem customizedViewItem : customizedViewItems) {
            customizedViewItemDao.delete(customizedViewItem);
        }
        logger.info(LogHelper.getLogInfo("Delete customized view item end"));
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.augmentum.ams.service.customized.CustomizedViewItemService#
     * updateCusomzieViewItem
     * (com.augmentum.ams.model.customized.CustomizedViewItem)
     */
    @Override
    public void updateCustomizedViewItem(CustomizedViewVo customizedViewVo) throws BaseException {

        logger.info(LogHelper.getLogInfo("Update customized view item start"));

        CustomizedView customizedView = customizedViewService
                .updateCustomizedView(customizedViewVo);

        int length = customizedViewVo.getColumns().length;
        Date time = UTCTimeUtil.localDateToUTC();
        String id = ""; // init customizedViewItemId

        if (0 < length) {
            List<CustomizedViewItem> customizedViewItems = new ArrayList<CustomizedViewItem>();
            for (int i = 0; i < length; i++) {
                CustomizedViewItem customizedViewItem = new CustomizedViewItem();
                id = customizedViewVo.getCustomizedViewItemIds()[i];

                if (!"".equals(id)) { // already exist item
                    customizedViewItem = this.getCustomizedViewItemById(id);
                    // TODO hard code here
                    if ("yes".equals(customizedViewVo.getIsDeletes()[i])) {
                        // TODO updatedTime need change to fixed ExpiredTime
                        customizedViewItem.setExpired(Boolean.TRUE);
                    }
                } else { // new added item
                    customizedViewItem.setCreatedTime(time);
                    customizedViewItem.setCustomizedView(customizedView);
                    customizedViewItem.setColumnName(customizedViewVo.getColumns()[i]);
                    customizedViewItem.setRealTable(customizedViewVo.getRealTables()[i]);
                    customizedViewItem.setColumnType(customizedViewVo.getColumnTypes()[i]);
                    customizedViewItem.setSearchColumn(customizedViewVo.getSearchColumns()[i]);
                }
                // update
                customizedViewItem.setUpdatedTime(time);
                customizedViewItems.add(customizedViewItem);
                customizedViewItem.setValue(customizedViewVo.getValues()[i]);
                customizedViewItem.setSearchCondition(customizedViewVo.getSearchConditions()[i]);
            }
            customizedViewItemDao.saveCustomizedViewItem(customizedViewItems);
        }
        logger.info(LogHelper.getLogInfo("Update customized view item end"));
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.augmentum.ams.service.customized.CustomizedViewItemService#
     * getCustomizedViewItemById(java.lang.String)
     */
    @Override
    public CustomizedViewItem getCustomizedViewItemById(String customizedViewItemId) {

        logger.info(LogHelper
                .getLogInfo("Get customized view item by id start, customizedViewItem id",
                        customizedViewItemId));

        CustomizedViewItem customizedViewItem = customizedViewItemDao.get(CustomizedViewItem.class,
                customizedViewItemId);

        logger.info(LogHelper.getLogInfo("Get customized view item by id end"));

        return customizedViewItem;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.augmentum.ams.service.customized.CustomizedViewItemService#
     * getAllByCustomizedViewId(java.lang.String)
     */
    @Override
    public List<CustomizedViewItem> findByCustomizedViewId(String customizedViewId) {

        logger.info(LogHelper.getLogInfo("Find by customized view id start, customizedView id",
                customizedViewId));

        List<CustomizedViewItem> customizedViewItems = new ArrayList<CustomizedViewItem>();
        try {
            customizedViewItems = customizedViewItemDao.findByCustomizedViewId(customizedViewId);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        logger.info(LogHelper.getLogInfo("Find by customized view id end"));

        return customizedViewItems;
    }

    @Override
    public BooleanQuery getCustomizedViewItemQuery(String customizedViewId) throws BaseException {

        logger.info(LogHelper.getLogInfo("Get CustomizedViewItem query start, customizedView id",
                customizedViewId));

        BooleanQuery booleanQuery = new BooleanQuery();
        List<CustomizedViewItem> customizedViewItem = findByCustomizedViewId(customizedViewId);

        // judge the size of customizedViewItem of the customizedView
        if (0 < customizedViewItem.size()) {
            // more than one customizedViewItems
            booleanQuery = checkCustomizedViewOperator(booleanQuery, customizedViewId,
                    customizedViewItem);
        }
        logger.info(LogHelper.getLogInfo("Get CustomizedViewItem query end"));

        return booleanQuery;
    }

    private BooleanQuery checkCustomizedViewOperator(BooleanQuery booleanQuery,
            String customizedViewId, List<CustomizedViewItem> customizedViewItem)
            throws BaseException {

        logger.info(LogHelper
                .getLogInfo(
                        "Check customizedView operator start, customizedViewItem id, customizedViewItem size",
                        customizedViewId, customizedViewItem.size()));

        CustomizedView customizedView = customizedViewService
                .getCustomizedViewById(customizedViewId);

        // operator is 'and'
        if (Constant.OPERATOR_AND.equals(customizedView.getOperators())) {
            booleanQuery = searchWithOperator(customizedViewItem, Occur.MUST);
            return booleanQuery;
        }
        // operator is 'or'
        if (Constant.OPERATOR_OR.equals(customizedView.getOperators())) {
            booleanQuery = searchWithOperator(customizedViewItem, Occur.SHOULD);
            return booleanQuery;
        }
        logger.info(LogHelper.getLogInfo("Check customizedView operator end"));

        return booleanQuery;
    }

    private BooleanQuery searchWithOperator(List<CustomizedViewItem> customizedViewItem, Occur occur) {

        logger.info(LogHelper.getLogInfo(
                "Search with operator start, customizedViewItem size, occur",
                customizedViewItem.size(), occur));

        BooleanQuery booleanQuery = new BooleanQuery();

        for (int i = 0; i < customizedViewItem.size(); i++) {
            String searchCondition = customizedViewItem.get(i).getSearchCondition();
            String columnType = customizedViewItem.get(i).getColumnType();
            String value = customizedViewItem.get(i).getValue();
            String column = customizedViewItem.get(i).getSearchColumn();

            // boolean type
            if ("".equals(searchCondition)) {
                booleanQuery.add(new TermQuery(new Term(column, value)), occur);
                continue;
            }

            // range search
            if (isInRangSearchGroup(searchCondition)) {
                // date type
                if (columnType.equals(ColumnTypeEnum.DATE_TYPE.getColumnType())) {
                    String realValue = UTCTimeUtil.formatFilterTime(value);
                    booleanQuery.add(
                            generateTermRangeQuery(column, searchCondition, value, realValue),
                            occur);
                    continue;
                }
                // int type
                if (columnType.equals(ColumnTypeEnum.INT_TYPE.getColumnType())) {
                    booleanQuery.add(generateTermRangeQuery(column, searchCondition, value, value),
                            occur);
                    continue;
                }
            }

            // term search
            if (isInTermSearchGroup(searchCondition)) {
                if (columnType.equals(ColumnTypeEnum.DATE_TYPE.getColumnType())) {
                    value = UTCTimeUtil.formatFilterTime(value);
                }
                generateTermQuery(booleanQuery, column, searchCondition, value, occur);
                continue;
            }

            // contains search
            if (isInContainsSearchGroup(searchCondition)) {
                generateWildcardQuery(booleanQuery, column, searchCondition, value, occur);
                continue;
            }
        }
        logger.info(LogHelper.getLogInfo("Search with operator end"));

        return booleanQuery;
    }

    private void generateTermQuery(BooleanQuery booleanQuery, String column,
            String searchCondition, String value, Occur occur) {

        logger.info(LogHelper.getLogInfo("Generate term query start, column, value, occur", column,
                value, occur));

        if (Constant.IS_NOT.equals(searchCondition)) {
            BooleanQuery booleanQueryIn = new BooleanQuery();
            booleanQueryIn.add(new WildcardQuery(new Term(column, "*")), Occur.MUST);
            booleanQueryIn.add(new TermQuery(new Term(column, value)), Occur.MUST_NOT);
            booleanQuery.add(booleanQueryIn, occur);
        } else {
            booleanQuery.add(new TermQuery(new Term(column, value)), occur);
        }
        logger.info(LogHelper.getLogInfo("Generate term query end"));
    }

    private void generateWildcardQuery(BooleanQuery booleanQuery, String column,
            String searchCondition, String value, Occur occur) {

        logger.info(LogHelper.getLogInfo("Generate wildcard query start, column, value, occur",
                column, value, occur));

        if (Constant.CONTAINS.equals(searchCondition)) {
            booleanQuery.add(new WildcardQuery(new Term(column, "*" + value + "*")), occur);
            return;
        }
        if (Constant.NOT_CONTAINS.equals(searchCondition)) {
            BooleanQuery booleanQueryIn = new BooleanQuery();
            booleanQueryIn.add(new WildcardQuery(new Term(column, "*")), Occur.MUST);
            booleanQueryIn.add(new WildcardQuery(new Term(column, "*" + value + "*")),
                    Occur.MUST_NOT);
            booleanQuery.add(booleanQueryIn, occur);
            return;
        }
        if (Constant.START_WITH.equals(searchCondition)) {
            booleanQuery.add(new WildcardQuery(new Term(column, value + "*")), occur);
            return;
        }
        logger.info(LogHelper.getLogInfo("Generate wildcard query end"));

    }

    private TermRangeQuery generateTermRangeQuery(String column, String searchCondition,
            String value, String realValue) {

        logger.info(LogHelper.getLogInfo(
                "Generate term range query start, column, value, realValue", column, value,
                realValue));

        TermRangeQuery termRangeQuery = null;

        if (Constant.LESS_THAN.equals(searchCondition)) {
            termRangeQuery = new TermRangeQuery(column, null, realValue, false, false);
            return termRangeQuery;
        }
        if (Constant.LESS_OR_EQUAL.equals(searchCondition)) {
            termRangeQuery = new TermRangeQuery(column, null, realValue, false, true);
            return termRangeQuery;
        }
        if (Constant.GREATER_THAN.equals(searchCondition)) {
            termRangeQuery = new TermRangeQuery(column, realValue, null, false, false);
            return termRangeQuery;
        }
        if (Constant.GREATER_OR_EQUAL.equals(searchCondition)) {
            termRangeQuery = new TermRangeQuery(column, realValue, null, true, true);
            return termRangeQuery;
        }
        logger.info(LogHelper.getLogInfo("Generate term range query end"));

        return termRangeQuery;
    }

    private boolean isInContainsSearchGroup(String searchCondition) {
        return checkGroup(searchCondition, containsSearchGroup);
    }

    private boolean isInTermSearchGroup(String searchCondition) {
        return checkGroup(searchCondition, termSearchGroup);
    }

    private boolean isInRangSearchGroup(String searchCondition) {
        return checkGroup(searchCondition, rangSearchGroup);
    }

    private boolean checkGroup(String searchCondition, String[] arrayGroup) {

        logger.info(LogHelper.getLogInfo("Check group start, searchCondition, arrayGroup.length",
                searchCondition, arrayGroup.length));

        boolean isExist = false;
        for (int i = 0; i < arrayGroup.length; i++) {
            if (searchCondition.equals(arrayGroup[i])) {
                isExist = true;
                break;
            } else {
                isExist = false;
            }
        }
        logger.info(LogHelper.getLogInfo("Check group end"));

        return isExist;
    }
}
