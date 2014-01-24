package com.augmentum.ams.service.operationLog.impl;

import org.apache.commons.lang.StringUtils;
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

import com.augmentum.ams.dao.base.BaseHibernateDao;
import com.augmentum.ams.dao.operationLog.OperationLogDao;
import com.augmentum.ams.model.asset.TransferLog;
import com.augmentum.ams.model.operationLog.OperationLog;
import com.augmentum.ams.service.asset.impl.TransferLogServiceImpl;
import com.augmentum.ams.service.operationLog.OperationLogService;
import com.augmentum.ams.util.Constant;
import com.augmentum.ams.util.FormatUtil;
import com.augmentum.ams.util.SearchFieldHelper;
import com.augmentum.ams.web.vo.system.Page;
import com.augmentum.ams.web.vo.system.SearchCondition;

@Service("operationLogService")
public class OperationLogServiceImpl implements OperationLogService{
	
	Logger logger = Logger.getLogger(OperationLogServiceImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private OperationLogDao operationLogDao;
	@Autowired
	private BaseHibernateDao<OperationLog> baseHibernateDao;

	@Override
    public void save(OperationLog operationLog) {
		operationLogDao.save(operationLog);
	    
    }

	@Override
	public Page<OperationLog> findOperationLogBySearchCondition(
			SearchCondition searchCondition) {
		// init base search columns and associate way
				String[] fieldNames = getSearchFieldNames(searchCondition
						.getSearchFields());
				Occur[] clauses = new Occur[fieldNames.length];

				for (int i = 0; i < fieldNames.length; i++) {
					clauses[i] = Occur.SHOULD;
				}

				Session session = sessionFactory.openSession();
				FullTextSession fullTextSession = Search.getFullTextSession(session);

				QueryBuilder qb = null;
				try {
					qb = fullTextSession.getSearchFactory().buildQueryBuilder()
							.forEntity(OperationLog.class).get();
				} catch (Exception e1) {
					logger.error(e1);
				}

				// create ordinary query, it contains search by keyword and field names
				BooleanQuery query = new BooleanQuery();

				String keyWord = searchCondition.getKeyWord();

				// if keyword is null or "", search condition is "*", it will search all
				// the value based on some one field
				if (null == keyWord || "".equals(keyWord) || "*".equals(keyWord)) {
					Query defaultQuery = new TermQuery(new Term("isExpired",
							Boolean.FALSE.toString()));
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
							parseQuery = MultiFieldQueryParser.parse(Version.LUCENE_30,
									keyWord, fieldNames, clauses, new IKAnalyzer());
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							logger.error("parse keyword error", e);
						}
						bq.add(parseQuery, Occur.SHOULD);

						for (int i = 0; i < fieldNames.length; i++) {
							Query keyWordPrefixQuery = new PrefixQuery(new Term(
									fieldNames[i], keyWord));
							bq.add(keyWordPrefixQuery, Occur.SHOULD);
						}

						query.add(bq, Occur.MUST);
					}
				}

				//TODO handle fieldBox
				
				// create filter based on advanced search condition, it used for further
				// filtering query result
				BooleanQuery booleanQuery = new BooleanQuery();

				booleanQuery.add(
						new TermQuery(new Term("isExpired", Boolean.FALSE.toString())),
						Occur.MUST);

				QueryWrapperFilter filter = new QueryWrapperFilter(booleanQuery);

				// add entity associate
				Criteria criteria = session.createCriteria(OperationLog.class);
				criteria.setFetchMode("user", FetchMode.JOIN).setFetchMode("asset",
						FetchMode.JOIN);
				criteria.add(Restrictions.eq("isExpired", Boolean.FALSE));

				Page<OperationLog> page = new Page<OperationLog>();

				// set page parameters, sort column, sort sign, page size, current page
				// num
				page.setPageSize(searchCondition.getPageSize());
				page.setCurrentPage(searchCondition.getPageNum());
				page.setSortOrder(searchCondition.getSortSign());
				page.setSortColumn(operationLogSortName(searchCondition.getSortName()));

				FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(
						query, OperationLog.class).setCriteriaQuery(criteria);
				page = baseHibernateDao.findByIndex(fullTextQuery, filter, page,
						OperationLog.class);
				fullTextSession.close();
				return page;
	}
	
	private String[] getSearchFieldNames(String searchConditions) {
		String[] fieldNames = FormatUtil.splitString(searchConditions,
				Constant.SPLIT_COMMA);

		if (null == fieldNames || 0 == fieldNames.length) {
			fieldNames = SearchFieldHelper.getOperationLogFields();
		}
		return fieldNames;
	}

	private BooleanQuery getSentenceQuery(QueryBuilder qb,
			String[] sentenceFields, String keyWord) {
		BooleanQuery sentenceQuery = new BooleanQuery();

		for (int i = 0; i < sentenceFields.length; i++) {
			Query query = qb.phrase().onField(sentenceFields[i])
					.sentence(keyWord).createQuery();
			sentenceQuery.add(query, Occur.SHOULD);
		}
		return sentenceQuery;
	}

	private String operationLogSortName(String sortName) {

		if ("userName".equals(sortName)) {
			sortName = "user.userName";
		}
		return sortName;
	}

}
