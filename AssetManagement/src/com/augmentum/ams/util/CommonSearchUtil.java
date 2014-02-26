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
import com.augmentum.ams.model.operationLog.OperationLog;
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
    public static BooleanQuery searchByAssetStatus(String status) {

        BooleanQuery assetStatusQuery = new BooleanQuery();

        if (StringUtils.isBlank(status)) {
            status = SearchFieldConstants.ASSET_STATUS;
        }

        String[] statusConditions = FormatUtil.splitString(status,
                SystemConstants.SPLIT_COMMA);

        if (null != statusConditions && 0 < statusConditions.length) {

            for (int i = 0; i < statusConditions.length; i++) {
                assetStatusQuery.add(new TermQuery(new Term("status",
                        statusConditions[i])), Occur.SHOULD);
            }
        }

        return assetStatusQuery;
    }

    /**
     * @author Geoffrey.Zhao
     * @param type
     * @return
     */
    public static BooleanQuery searchByAssetType(String type) {

        BooleanQuery assetTypeQuery = new BooleanQuery();

        if (StringUtils.isBlank(type)) {
            type = SearchFieldConstants.ASSET_TYPE;
        }

        String[] typeConditions = FormatUtil.splitString(type,
                SystemConstants.SPLIT_COMMA);

        if (null != typeConditions && 0 < typeConditions.length) {

            for (int i = 0; i < typeConditions.length; i++) {
                assetTypeQuery.add(new TermQuery(new Term("type",
                        typeConditions[i])), Occur.SHOULD);
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
            toTime = UTCTimeUtil.formatFilterTime(toTime);
            return new TermRangeQuery(fieldName, fromTime, toTime, true, true);
        } else if (isNullToTime && !isNullFromTime) {

            toTime = SystemConstants.SEARCH_MAX_DATE;
            fromTime = UTCTimeUtil.formatFilterTime(fromTime);
            return new TermRangeQuery(fieldName, fromTime, toTime, true, true);
        } else if (!isNullFromTime && !isNullToTime) {

            fromTime = UTCTimeUtil.formatFilterTime(fromTime);
            toTime = UTCTimeUtil.formatFilterTime(toTime);
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
}
