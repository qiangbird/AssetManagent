package com.augmentum.ams.util;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.util.Version;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.augmentum.ams.constants.SearchFieldConstants;
import com.augmentum.ams.constants.SystemConstants;
import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.model.asset.CustomerGroup;
import com.augmentum.ams.model.asset.Location;
import com.augmentum.ams.model.asset.TransferLog;
import com.augmentum.ams.model.audit.Inconsistent;
import com.augmentum.ams.model.operationLog.OperationLog;
import com.augmentum.ams.model.todo.ToDo;
import com.augmentum.ams.web.vo.system.SearchCondition;
import common.Logger;

public class CommonSearchUtil {

    static Logger logger = Logger.getLogger(CommonSearchUtil.class);

    /**
     * @author Geoffrey.Zhao
     * @param Object
     * @param QueryBuilder
     * @param keyWord
     * @param searchFields
     * @param sentenceSearchFields
     * @return
     */
    public static BooleanQuery searchByKeyWord(Class<?> clazz, QueryBuilder qb,
            String keyWord, String fields) {

        String[] sentenceSearchFields = null;
        String[] searchFields = FormatUtil.splitString(fields,
                SystemConstants.SPLIT_COMMA);

        if (null == searchFields || 0 == searchFields.length) {

            // judge which one is the search entity: asset or location or group
            // or transfer_log etc.
            if (clazz == Asset.class) {

                searchFields = SearchFieldConstants.ALL_ASSET_FIELDS;
                sentenceSearchFields = SearchFieldConstants.ASSET_SENTENCE_FIELDS;
            } else if (clazz == TransferLog.class) {

                searchFields = SearchFieldConstants.TRANSFER_LOG_FIELDS;
                sentenceSearchFields = SearchFieldConstants.TRANSFER_LOG_FIELDS;
            } else if (clazz == OperationLog.class) {

                searchFields = SearchFieldConstants.OPERATION_LOG_FIELDS;
                sentenceSearchFields = SearchFieldConstants.OPERATION_LOG_FIELDS;
            } else if (clazz == CustomerGroup.class) {

                searchFields = SearchFieldConstants.CUSTOMER_GROUP_FIELDS;
                sentenceSearchFields = SearchFieldConstants.CUSTOMER_GROUP_FIELDS;
            } else if (clazz == Location.class) {

                searchFields = SearchFieldConstants.LOCATION_FIELDS;
                sentenceSearchFields = SearchFieldConstants.LOCATION_FIELDS;
            } else if (clazz == ToDo.class) {

                searchFields = SearchFieldConstants.TODO_FIELDS;
                sentenceSearchFields = SearchFieldConstants.TODO_FIELDS;
            } else if (clazz == Inconsistent.class) {

                searchFields = SearchFieldConstants.INCONSISTENT_FIELDS;
                sentenceSearchFields = SearchFieldConstants.INCONSISTENT_SENTENCE_FIELDS;
            }
        } else {
            sentenceSearchFields = searchFields;
        }

        Occur[] clauses = new Occur[searchFields.length];

        for (int i = 0; i < searchFields.length; i++) {
            clauses[i] = Occur.SHOULD;
        }

        BooleanQuery keyWordQuery = new BooleanQuery();

        Query defaultQuery = new TermQuery(new Term("isExpired",
                Boolean.FALSE.toString()));
        keyWordQuery.add(defaultQuery, Occur.MUST);

        // if keyword is null or "", search condition is "*", it will search all
        // the value based on some one field
        if (StringUtils.isNotBlank(keyWord)) {

            // keyWord = FormatUtil.formatKeyword(keyWord);
            keyWord = keyWord.trim().toLowerCase();

            // judge if keyword contains space, if yes, search keyword as a
            // sentence
            if (-1 != keyWord.indexOf(" ")) {

                BooleanQuery sentenceQuery = new BooleanQuery();

                for (int i = 0; i < sentenceSearchFields.length; i++) {
                    Query query = qb.phrase().onField(sentenceSearchFields[i])
                            .sentence(keyWord).createQuery();
                    sentenceQuery.add(query, Occur.SHOULD);
                }

                keyWordQuery.add(sentenceQuery, Occur.MUST);

            }

            BooleanQuery booleanQuery = new BooleanQuery();

            // add normal multi fields query as searhFields
            Query parseQuery = null;

            try {
                parseQuery = MultiFieldQueryParser.parse(Version.LUCENE_30,
                        keyWord, searchFields, clauses, new IKAnalyzer());
            } catch (ParseException e) {
                logger.error("parse keyword error", e);
            }
            booleanQuery.add(parseQuery, Occur.SHOULD);

            // add prefix query as serchFields
            for (int i = 0; i < searchFields.length; i++) {

                Query keyWordPrefixQuery = new PrefixQuery(new Term(
                        searchFields[i], keyWord));
                booleanQuery.add(keyWordPrefixQuery, Occur.SHOULD);
            }
            keyWordQuery.add(booleanQuery, Occur.MUST);
        }

        return keyWordQuery;
    }

    /**
     * @author Geoffrey.Zhao
     * @param status
     * @return
     */
    public static BooleanQuery searchByAssetStatus(String status, Class<?> clazz) {

        BooleanQuery assetStatusQuery = new BooleanQuery();

        if (StringUtils.isBlank(status)) {
            return null;
        }

        String[] statusConditions = FormatUtil.splitString(status,
                SystemConstants.SPLIT_COMMA);

        if (null != statusConditions && 0 < statusConditions.length) {

            for (int i = 0; i < statusConditions.length; i++) {

                if (clazz == Asset.class) {
                    assetStatusQuery.add(new TermQuery(new Term("status",
                            statusConditions[i])), Occur.SHOULD);
                } else {
                    assetStatusQuery.add(new TermQuery(new Term("asset.status",
                            statusConditions[i])), Occur.SHOULD);
                }
            }
        }

        return assetStatusQuery;
    }

    /**
     * @author Geoffrey.Zhao
     * @param type
     * @return
     */
    public static BooleanQuery searchByAssetType(String type, Class<?> clazz) {

        BooleanQuery assetTypeQuery = new BooleanQuery();

        if (StringUtils.isBlank(type)) {
            return null;
        }

        String[] typeConditions = FormatUtil.splitString(type,
                SystemConstants.SPLIT_COMMA);

        if (null != typeConditions && 0 < typeConditions.length) {

            for (int i = 0; i < typeConditions.length; i++) {

                if (clazz == Asset.class) {
                    assetTypeQuery.add(new TermQuery(new Term("type",
                            typeConditions[i])), Occur.SHOULD);
                } else {
                    assetTypeQuery.add(new TermQuery(new Term("asset.type",
                            typeConditions[i])), Occur.SHOULD);
                }
            }
        }
        return assetTypeQuery;
    }

    /**
     * @author Geoffrey.Zhao
     * @param fromTime
     * @param toTime
     * @return
     */
    public static Query searchByTimeRangeQuery(String fieldName,
            String fromTime, String toTime) {

        boolean isNullFromTime = StringUtils.isBlank(fromTime);
        boolean isNullToTime = StringUtils.isBlank(toTime);

        if (isNullFromTime && !isNullToTime) {

            fromTime = SystemConstants.SEARCH_MIN_DATE;
            toTime = UTCTimeUtil.formatToFilterTime(toTime);
            return new TermRangeQuery(fieldName, fromTime, toTime, true, true);
        } else if (isNullToTime && !isNullFromTime) {

            toTime = SystemConstants.SEARCH_MAX_DATE;
            fromTime = UTCTimeUtil.formatFromFilterTime(fromTime);
            return new TermRangeQuery(fieldName, fromTime, toTime, true, true);
        } else if (!isNullFromTime && !isNullToTime) {

            fromTime = UTCTimeUtil.formatFromFilterTime(fromTime);
            toTime = UTCTimeUtil.formatToFilterTime(toTime);
            return new TermRangeQuery(fieldName, fromTime, toTime, true, true);
        } else {

            return null;
        }
    }

    /**
     * @author Geoffrey.Zhao
     * @param sortName
     * @return
     */
    public static String transferSortName(String sortName) {

        if ("userName".equals(sortName) || "user.userName".equals(sortName)) {
            sortName = "user.userName_forSort";
        }
        return sortName;
    }

    /**
     * @author Geoffrey.Zhao
     * @param condition
     */
    public static BooleanQuery addFilterQueryForAsset(SearchCondition searchCondition, String fileName, Class<?> clazz) {
        
        BooleanQuery booleanQuery = null;
        
        BooleanQuery statusQuery = CommonSearchUtil.searchByAssetStatus(
                searchCondition.getAssetStatus(), clazz);
        BooleanQuery typeQuery = CommonSearchUtil.searchByAssetType(
                searchCondition.getAssetType(), clazz);
        Query checkInTimeQuery = CommonSearchUtil.searchByTimeRangeQuery(
                fileName, searchCondition.getFromTime(),
                searchCondition.getToTime());

        if (null != statusQuery) {
            
            if (null == booleanQuery) {
                booleanQuery = new BooleanQuery();
            }
            booleanQuery.add(statusQuery, Occur.MUST);
        }

        if (null != typeQuery) {
            
            if (null == booleanQuery) {
                booleanQuery = new BooleanQuery();
            }
            booleanQuery.add(typeQuery, Occur.MUST);
        }

        if (null != checkInTimeQuery) {
            
            if (null == booleanQuery) {
                booleanQuery = new BooleanQuery();
            }
            booleanQuery.add(checkInTimeQuery, Occur.MUST);
        }
        return booleanQuery;
    }
}
