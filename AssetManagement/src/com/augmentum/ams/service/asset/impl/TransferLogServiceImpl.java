package com.augmentum.ams.service.asset.impl;


import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.QueryWrapperFilter;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.util.Version;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.augmentum.ams.dao.asset.TransferLogDao;
import com.augmentum.ams.dao.base.BaseHibernateDao;
import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.model.asset.TransferLog;
import com.augmentum.ams.model.user.User;
import com.augmentum.ams.service.asset.AssetService;
import com.augmentum.ams.service.asset.TransferLogService;
import com.augmentum.ams.util.Constant;
import com.augmentum.ams.util.FormatUtil;
import com.augmentum.ams.util.SearchFieldHelper;
import com.augmentum.ams.util.UTCTimeUtil;
import com.augmentum.ams.web.vo.system.Page;
import com.augmentum.ams.web.vo.system.SearchCondition;
import com.augmentum.ams.web.vo.user.UserVo;

@Service("transferLogService")
public class TransferLogServiceImpl implements TransferLogService {

	Logger logger = Logger.getLogger(TransferLogServiceImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
    private BaseHibernateDao<TransferLog> baseHibernateDao;
	@Autowired
	private TransferLogDao transferLogDao;
	@Autowired
	private AssetService assetService;
	
	@Override
	public Page<TransferLog> findTransferLogBySearchCondition(
			SearchCondition searchCondition) {
        // init base search columns and associate way
        String[] fieldNames = getSearchFieldNames(searchCondition.getSearchFields());
        Occur[] clauses = new Occur[fieldNames.length];

        for (int i = 0; i < fieldNames.length; i++) {
            clauses[i] = Occur.SHOULD;
        }

        Session session = sessionFactory.openSession();
        FullTextSession fullTextSession = Search.getFullTextSession(session);
        
        QueryBuilder qb =null;
		try {
			qb = fullTextSession.getSearchFactory().buildQueryBuilder()
			        .forEntity(TransferLog.class).get();
		} catch (Exception e1) {
			logger.error(e1);
		}

        // create ordinary query, it contains search by keyword and field names
        BooleanQuery query = new BooleanQuery();

        String keyWord = searchCondition.getKeyWord();

        // if keyword is null or "", search condition is "*", it will search all
        // the value based on some one field
        if (null == keyWord || "".equals(keyWord) || "*".equals(keyWord)) {
            Query defaultQuery = new TermQuery(new Term("isExpired", Boolean.FALSE.toString()));
            query.add(defaultQuery, Occur.MUST);
        } else {

            keyWord = FormatUtil.formatKeyword(keyWord);

            // judge if keyword contains space, if yes, search keyword as a
            // sentence
            if (-1 != keyWord.indexOf(" ")) {
                String[] sentenceFields = SearchFieldHelper.getSentenceFields();
                query = getSentenceQuery(qb, sentenceFields, keyWord);
            } else {

                // if keyword doesn't contain space, search keyword as tokenized
                // index string

                BooleanQuery bq = new BooleanQuery();

                Query parseQuery = null;

                try {
                    parseQuery = MultiFieldQueryParser.parse(Version.LUCENE_30, keyWord,
                            fieldNames, clauses, new IKAnalyzer());
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    logger.error("parse keyword error", e);
                }
                bq.add(parseQuery, Occur.SHOULD);

                for (int i = 0; i < fieldNames.length; i++) {
                    Query keyWordPrefixQuery = new PrefixQuery(new Term(fieldNames[i], keyWord));
                    bq.add(keyWordPrefixQuery, Occur.SHOULD);
                }

                query.add(bq, Occur.MUST);
            }
        }

        // create filter based on advanced search condition, it used for further
        // filtering query result
        BooleanQuery booleanQuery = new BooleanQuery();

        booleanQuery
                .add(new TermQuery(new Term("isExpired", Boolean.FALSE.toString())), Occur.MUST);

        QueryWrapperFilter filter = new QueryWrapperFilter(booleanQuery);

        // add entity associate
        Criteria criteria = session.createCriteria(TransferLog.class);
        criteria.setFetchMode("user", FetchMode.JOIN).setFetchMode("asset", FetchMode.JOIN);
        criteria.add(Restrictions.eq("isExpired", Boolean.FALSE));

        Page<TransferLog> page = new Page<TransferLog>();

        // set page parameters, sort column, sort sign, page size, current page
        // num
        page.setPageSize(searchCondition.getPageSize());
        page.setCurrentPage(searchCondition.getPageNum());
        page.setSortOrder(searchCondition.getSortSign());
        page.setSortColumn(transferSortName(searchCondition.getSortName()));

        FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(query, TransferLog.class)
                .setCriteriaQuery(criteria);
        page = baseHibernateDao.findByIndex(fullTextQuery, filter, page, TransferLog.class);
        fullTextSession.close();
        return page;
	}

	 private String[] getSearchFieldNames(String searchConditions) {
	        String[] fieldNames = FormatUtil.splitString(searchConditions, Constant.SPLIT_COMMA);

	        if (null == fieldNames || 0 == fieldNames.length) {
	            fieldNames = SearchFieldHelper.getTransferLogFields();
	        }
	        return fieldNames;
	    }
	 
	 private BooleanQuery getSentenceQuery(QueryBuilder qb, String[] sentenceFields, String keyWord) {
	        BooleanQuery sentenceQuery = new BooleanQuery();

	        for (int i = 0; i < sentenceFields.length; i++) {
	            Query query = qb.phrase().onField(sentenceFields[i]).sentence(keyWord).createQuery();
	            sentenceQuery.add(query, Occur.SHOULD);
	        }
	        return sentenceQuery;
	    }
	 
	    private String transferSortName(String sortName) {

	        if ("userName".equals(sortName)) {
	            sortName = "user.userName";
	        }
	        return sortName;
	    }

		@Override
		public void saveTransferLog(String assetIds, String action) {
			Subject subject = SecurityUtils.getSubject();
			User user = (User) subject.getSession().getAttribute("currentUser");
			String ids[] = assetIds.split(",");
			Date date = UTCTimeUtil.localDateToUTC();
			for(String id : ids){
			Asset asset = assetService.getAsset(id);
			TransferLog transferLog = new TransferLog();
			transferLog.setAsset(asset);
			transferLog.setEmployee(user);
			transferLog.setAction(action);
			transferLog.setTime(date);
			transferLogDao.save(transferLog);
			}
			
		}
}
