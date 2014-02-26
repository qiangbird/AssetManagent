package com.augmentum.ams.service.audit.impl;

import java.util.List;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.QueryWrapperFilter;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.WildcardQuery;
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

import com.augmentum.ams.constants.SystemConstants;
import com.augmentum.ams.dao.audit.AuditFileDao;
import com.augmentum.ams.dao.audit.InconsistentDao;
import com.augmentum.ams.dao.base.BaseHibernateDao;
import com.augmentum.ams.exception.BaseException;
import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.model.audit.AuditFile;
import com.augmentum.ams.model.audit.Inconsistent;
import com.augmentum.ams.service.audit.InconsistentService;
import com.augmentum.ams.service.customized.CustomizedViewItemService;
import com.augmentum.ams.util.FormatUtil;
import com.augmentum.ams.util.UTCTimeUtil;
import com.augmentum.ams.web.vo.system.Page;
import com.augmentum.ams.web.vo.system.SearchCondition;

@Service("inconsistentService")
public class InconsistentServciceImpl implements InconsistentService {

	@Autowired
	private InconsistentDao inconsistentDao;

	@Autowired
	private AuditFileDao auditFileDao;

	@Autowired
	protected SessionFactory sessionFactory;

	@Autowired
	private CustomizedViewItemService customizedViewItemService;

	@Autowired
	private BaseHibernateDao<Asset> baseHibernateDao;

	@Autowired
	private BaseHibernateDao<Inconsistent> baseHibernateDaoIncons;

	private Logger logger = Logger.getLogger(InconsistentServciceImpl.class);

	@Override
	public int getInconsistentAssetsCount(String auditFileName) {

		AuditFile auditFile = auditFileDao
				.getAuditFileByFileName(auditFileName);

		if (null == auditFile) {
			return 0;
		}

		return inconsistentDao
				.findInconsistentAssetsByFileId(auditFile.getId()).size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.augmentum.ams.service.audit.InconsistentService#findInconsistentAssets
	 * (java.lang.String, int, int)
	 */
	@Override
	public JSONArray findInconsistentAssets(String auditFileName,
			int iDisplayStart, int iDisplayLength) {

		JSONArray arrays = new JSONArray();

		List<Inconsistent> inconsistentList = inconsistentDao
				.findInconsistentList(auditFileName, iDisplayStart,
						iDisplayLength);

		for (int i = 0; i < inconsistentList.size(); i++) {
			JSONArray array = new JSONArray();
			Inconsistent incons = inconsistentList.get(i);

			if (null != incons.getAsset()) {
				array.add(i + 1 + iDisplayStart);
				if (null == incons.getAsset().getBarCode()) {
					array.add("");
				} else {
					array.add(incons.getAsset().getBarCode());
				}
				array.add(incons.getAsset().getAssetName());
				array.add(incons.getAsset().getType());
			} else {
				array.add(i + 1 + iDisplayStart);
				array.add(incons.getBarCode());
				array.add("");
				array.add("");
			}
			arrays.add(array);
		}
		return arrays;
	}

	// TODO refine search code
	// ------------------------------------------------------------------------
	@Override
	public Page<Asset> findAssetForInconsistent(SearchCondition searchCondition)
			throws BaseException {
	    
	    Page<Asset> page = new Page<Asset>();
/*
		List<String> assetIdList = inconsistentDao.findInconsistentAssetByFileName(searchCondition
				.getAuditFileName());

		String[] fieldNames = getSearchFieldNames(searchCondition
				.getSearchFields());
		Occur[] clauses = new Occur[fieldNames.length];

		for (int i = 0; i < fieldNames.length; i++) {
			clauses[i] = Occur.SHOULD;
		}

		Session session = sessionFactory.openSession();
		FullTextSession fullTextSession = Search.getFullTextSession(session);
		QueryBuilder qb = fullTextSession.getSearchFactory()
				.buildQueryBuilder().forEntity(Asset.class).get();

		// create ordinary query, it contains search by keyword and field names
		BooleanQuery query = new BooleanQuery();

		String keyWord = searchCondition.getKeyWord();

		// if keyword is null or "", search condition is "*", it will search all
		// the value based on some one field
		if (StringUtils.isBlank(keyWord)) {
			Query defaultQuery = new TermQuery(new Term("isExpired",
					Boolean.FALSE.toString()));
			query.add(defaultQuery, Occur.MUST);
		} else {

			keyWord = FormatUtil.formatKeyword(keyWord);

			// judge if keyword contains space, if yes, search keyword as a
			// sentence
			if (-1 != keyWord.indexOf(" ")) {
				query = getSentenceQuery(qb, fieldNames, keyWord);
			}
			BooleanQuery bq = new BooleanQuery();

			Query parseQuery = null;

			try {
				parseQuery = MultiFieldQueryParser.parse(Version.LUCENE_30,
						keyWord, fieldNames, clauses, new IKAnalyzer());
			} catch (ParseException e) {
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

		// create filter based on advanced search condition, it used for further
		// filtering query result
		BooleanQuery booleanQuery = new BooleanQuery();

		BooleanQuery bq = new BooleanQuery();
		for (String s : assetIdList) {
			bq.add(new TermQuery(new Term("id", s)), Occur.SHOULD);
		}

		booleanQuery.add(bq, Occur.MUST);

		// If customizedViewId is not empty, only use the
		// customizedViewItemQuery
		if (null != searchCondition.getCustomizedViewId()
				&& !"".equals(searchCondition.getCustomizedViewId())) {
			BooleanQuery customizedViewItemQuery = customizedViewItemService
					.getCustomizedViewItemQuery(searchCondition
							.getCustomizedViewId());

			booleanQuery.add(customizedViewItemQuery, Occur.MUST);
		} else {
			BooleanQuery statusQuery = getStatusQuery(searchCondition
					.getAssetStatus());
			BooleanQuery typeQuery = getTypeQuery(searchCondition
					.getAssetType());
			Query trq = getTimeRangeQuery(searchCondition.getFromTime(),
					searchCondition.getToTime());

			booleanQuery.add(
					new TermQuery(new Term("isExpired", Boolean.FALSE
							.toString())), Occur.MUST);
			booleanQuery.add(statusQuery, Occur.MUST);
			booleanQuery.add(typeQuery, Occur.MUST);
			booleanQuery.add(trq, Occur.MUST);
		}
		QueryWrapperFilter filter = new QueryWrapperFilter(booleanQuery);

		// add entity associate
		Criteria criteria = session.createCriteria(Asset.class)
				.setFetchMode("user", FetchMode.JOIN)
				.setFetchMode("customer", FetchMode.JOIN)
				.setFetchMode("project", FetchMode.JOIN)
				.setFetchMode("location", FetchMode.JOIN);

		criteria.add(Restrictions.eq("isExpired", Boolean.FALSE));
		criteria.add(Restrictions.in("id", assetIdList));

		Page<Asset> page = new Page<Asset>();

		// set page parameters, sort column, sort sign, page size, current page
		// num
		page.setPageSize(searchCondition.getPageSize());
		page.setCurrentPage(searchCondition.getPageNum());
		page.setSortOrder(searchCondition.getSortSign());
		page.setSortColumn(transferSortName(searchCondition.getSortName()));

		FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(
				query, Asset.class).setCriteriaQuery(criteria);
		page = baseHibernateDao.findByIndex(fullTextQuery, filter, page);
		fullTextSession.close();
		return page;

	}

	// TODO search inconsistent barcode
	@Override
	public Page<Inconsistent> findInconsistentBarcode(
			SearchCondition searchCondition) {

		List<String> barcodeList = inconsistentDao
				.findInconsistentBarcode(searchCondition.getAuditFileName());

		String[] fieldNames = { "barcode" };
		Occur[] clauses = new Occur[fieldNames.length];

		for (int i = 0; i < fieldNames.length; i++) {
			clauses[i] = Occur.SHOULD;
		}

		Session session = sessionFactory.openSession();
		FullTextSession fullTextSession = Search.getFullTextSession(session);
		QueryBuilder qb = fullTextSession.getSearchFactory()
				.buildQueryBuilder().forEntity(Inconsistent.class).get();

		// create ordinary query, it contains search by keyword and field names
		BooleanQuery query = new BooleanQuery();

		String keyWord = searchCondition.getKeyWord();

		// if keyword is null or "", search condition is "*", it will search all
		// the value based on some one field
		if (StringUtils.isBlank(keyWord)) {
			query.add(getDefaultQuery(barcodeList), Occur.MUST);
		} else {

			keyWord = FormatUtil.formatKeyword(keyWord);

			// judge if keyword contains space, if yes, search keyword as a
			// sentence
			if (-1 != keyWord.indexOf(" ")) {
				query = getSentenceQuery(qb, fieldNames, keyWord);
			}
			BooleanQuery bq = new BooleanQuery();

			Query parseQuery = null;

			try {
				parseQuery = MultiFieldQueryParser.parse(Version.LUCENE_30,
						keyWord, fieldNames, clauses, new IKAnalyzer());
			} catch (ParseException e) {
				logger.error("parse keyword error", e);
			}
			bq.add(parseQuery, Occur.SHOULD);

			for (int i = 0; i < fieldNames.length; i++) {

				Query keyWordPrefixQuery = new PrefixQuery(new Term(
						fieldNames[i], keyWord));
				bq.add(keyWordPrefixQuery, Occur.SHOULD);
			}

			query.add(getDefaultQuery(barcodeList), Occur.MUST);
			query.add(bq, Occur.MUST);
		}

		QueryWrapperFilter filter = null;

		// add entity associate
		Criteria criteria = session.createCriteria(Inconsistent.class);
		criteria.add(Restrictions.in("barCode", barcodeList));

		Page<Inconsistent> page = new Page<Inconsistent>();

		// set page parameters, sort column, sort sign, page size, current page
		// num
		page.setPageSize(searchCondition.getPageSize());
		page.setCurrentPage(searchCondition.getPageNum());
		page.setSortColumn(searchCondition.getSortName());
		page.setSortOrder(searchCondition.getSortSign());

		FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(
				query, Inconsistent.class).setCriteriaQuery(criteria);
		page = baseHibernateDaoIncons.findByIndex(fullTextQuery, filter, page,
				Inconsistent.class);
		fullTextSession.close();
		*/
	    return page;
	}

	private BooleanQuery getDefaultQuery(List<String> barcodeList) {
		BooleanQuery bq = new BooleanQuery();

		for (String barcode : barcodeList) {
			Query wildcardQuery = new WildcardQuery(
					new Term("barcode", barcode));
			bq.add(wildcardQuery, Occur.SHOULD);
		}
		return bq;
	}

    @Override
    public Page<Inconsistent> findInconsistentBarcode(SearchCondition condition)
            throws BaseException {
        // TODO Auto-generated method stub
        return null;
    }

}
