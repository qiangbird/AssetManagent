package com.augmentum.ams.service.asset.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import org.apache.lucene.search.WildcardQuery;
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

import com.augmentum.ams.dao.asset.CustomerGroupDao;
import com.augmentum.ams.dao.base.BaseHibernateDao;
import com.augmentum.ams.model.asset.Customer;
import com.augmentum.ams.model.asset.CustomerGroup;
import com.augmentum.ams.service.asset.CustomerGroupService;
import com.augmentum.ams.service.asset.CustomerService;
import com.augmentum.ams.util.Constant;
import com.augmentum.ams.util.FormatUtil;
import com.augmentum.ams.util.SearchFieldHelper;
import com.augmentum.ams.web.vo.system.Page;
import com.augmentum.ams.web.vo.system.SearchCondition;

@Service("customerGroupService")
public class CustomerGroupServiceImpl implements CustomerGroupService {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerGroupDao customerGroupDao;
    @Autowired
    private BaseHibernateDao<CustomerGroup> baseHibernateDao;
    @Autowired
    protected SessionFactory sessionFactory;

    Logger logger = Logger.getLogger(CustomerGroupServiceImpl.class);

    @Override
    public void saveCustomerGroup(CustomerGroup customerGroup) {
        List<Customer> list = customerGroup.getCustomers();
        customerGroup = customerGroupDao.save(customerGroup);
        for (Customer customer : list) {
            customer.setCustomerGroup(customerGroup);
            // customerService.saveCustomer(customer);
            customerService.updateCustomer(customer);
        }
        // customerGroupDao.save(customerGroup);
    }

    @Override
    public List<CustomerGroup> findAllGroups() {
        return customerGroupDao.findAll(CustomerGroup.class);
    }

    @Override
    public CustomerGroup getCustomerGroupById(String customerGroupId) {
        String hql = "from CustomerGroup where id = ?";
        return customerGroupDao.getUnique(hql, customerGroupId);
    }

    @Override
    public void deleteCustomerGroupById(String customerGroupId) {
        CustomerGroup customerGroup = getCustomerGroupById(customerGroupId);
        customerGroupDao.delete(customerGroup);
    }

    @Override
    public void updateCustomerGroup(CustomerGroup customerGroup) {
        //delete 
        List<Customer> list = customerService.getCustomerByGroup(customerGroup.getId());
        List<Customer> list1 = customerGroup.getCustomers();
        
        for(int i=0;i<list.size();i++){
            if(list1.contains(list.get(i))){
                list.remove(list.get(i));
            }

        }
        
        for(Customer customer : list){
            customer.setCustomerGroup(null);
            customerService.updateCustomer(customer);
        }
        for(Customer customer1 : list1){
            Customer customer = customerService.getCustomerByCode(customer1.getCustomerCode());
            if(null == customer){
                customer1.setCustomerGroup(customerGroup);
                customerService.saveCustomer(customer1);
            }else{
                customer.setCustomerGroup(customerGroup);
            customerService.updateCustomer(customer);
            }
        }
        customerGroupDao.update(customerGroup);
    }

    @Override
    public Page<CustomerGroup> findCustomerGroupBySearchCondition(SearchCondition searchCondition) {
        // init base search columns and associate way
        String[] fieldNames = getSearchFieldNames(searchCondition.getSearchFields());
        Occur[] clauses = new Occur[fieldNames.length];

        for (int i = 0; i < fieldNames.length; i++) {
            clauses[i] = Occur.SHOULD;
        }

        Session session = sessionFactory.openSession();
        FullTextSession fullTextSession = Search.getFullTextSession(session);
        QueryBuilder qb = fullTextSession.getSearchFactory().buildQueryBuilder()
                .forEntity(CustomerGroup.class).get();

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
        Criteria criteria = session.createCriteria(CustomerGroup.class);
        criteria.setFetchMode("customers", FetchMode.JOIN);
        criteria.add(Restrictions.eq("isExpired", Boolean.FALSE));

        Page<CustomerGroup> page = new Page<CustomerGroup>();

        // set page parameters, sort column, sort sign, page size, current page
        // num
        page.setPageSize(searchCondition.getPageSize());
        page.setCurrentPage(searchCondition.getPageNum());
        page.setSortOrder(searchCondition.getSortSign());

        FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(query,
                CustomerGroup.class).setCriteriaQuery(criteria);
        page = baseHibernateDao.findByIndex(fullTextQuery, filter, page, CustomerGroup.class);
        fullTextSession.close();
        return page;
    }

    private String[] getSearchFieldNames(String searchConditions) {
        String[] fieldNames = FormatUtil.splitString(searchConditions, Constant.SPLIT_COMMA);

        if (null == fieldNames || 0 == fieldNames.length) {
            fieldNames = SearchFieldHelper.getCustomerGroupFields();
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
}
