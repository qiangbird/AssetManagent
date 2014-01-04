/**
 * 
 */
package com.augmentum.ams.util;

import java.util.Date;

import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * @author Grylls.Xu
 * @time Sep 26, 2013 12:38:40 PM
 */
public class HibernateSearchUtil {

    public static BooleanQuery queryAnd(String keyword, BooleanQuery booleanQuery, String field) {
        QueryParser queryParser = new QueryParser(
                Version.LUCENE_30,
                field,
                new IKAnalyzer());

        queryParser.setAllowLeadingWildcard(true);
        Query query = null;
        try {
            query = queryParser.parse(keyword);
            booleanQuery.add(query, Occur.MUST);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return booleanQuery;
    }

    public static BooleanQuery queryAnd(String keyword, BooleanQuery booleanQuery, String[] fields) {
        MultiFieldQueryParser queryParser = new MultiFieldQueryParser(
                Version.LUCENE_30,
                fields,
                new IKAnalyzer());

        queryParser.setAllowLeadingWildcard(true);
        Query query = null;
        try {
            query = queryParser.parse(keyword);
            booleanQuery.add(query, Occur.MUST);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return booleanQuery;
    }

    public static BooleanQuery queryTime(Date beginDate, Date endDate, BooleanQuery booleanQuery, String field) {
        if (null != beginDate || null != endDate) {
            String strBeginDate = UTCTimeUtil.formatDateToString(beginDate, "yyyyMMdd");
            String strEndDate = UTCTimeUtil.formatDateToString(endDate, "yyyyMMdd");
            TermRangeQuery termRangeQuery = new TermRangeQuery(field, strBeginDate, strEndDate, true, true);
            booleanQuery.add(termRangeQuery, Occur.MUST);
        }
        return booleanQuery;
    }



    //    public static BooleanQuery queryOr(String keyword, BooleanQuery booleanQuery, String field[]) {
    //        QueryParser queryParser = new QueryParser(
    //                Version.LUCENE_30,
    //                field[],
    //                new IKAnalyzer());
    //
    //        queryParser.setAllowLeadingWildcard(true);
    //        Query query = null;
    //        try {
    //            query = queryParser.parse(keyword);
    //            booleanQuery.add(query, Occur.SHOULD);
    //        } catch (ParseException e) {
    //            // TODO Auto-generated catch block
    //            e.printStackTrace();
    //        }
    //
    //        return booleanQuery;
    //    }

}
