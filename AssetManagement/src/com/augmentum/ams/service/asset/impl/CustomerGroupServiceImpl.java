package com.augmentum.ams.service.asset.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.QueryWrapperFilter;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.augmentum.ams.dao.asset.CustomerGroupDao;
import com.augmentum.ams.dao.base.BaseHibernateDao;
import com.augmentum.ams.model.asset.Customer;
import com.augmentum.ams.model.asset.CustomerGroup;
import com.augmentum.ams.service.asset.CustomerGroupService;
import com.augmentum.ams.service.asset.CustomerService;
import com.augmentum.ams.util.CommonSearchUtil;
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
        if (null != list) {
            for (Customer customer : list) {
                customer.setCustomerGroup(customerGroup);
                customerService.updateCustomer(customer);
            }
        }
    }

    @Override
    public List<CustomerGroup> findAllGroups() {
        return customerGroupDao.findAll(CustomerGroup.class);
    }

    @Override
    public CustomerGroup getCustomerGroupById(String customerGroupId) {
        String hql = "from CustomerGroup where id = ? and isExpired = false";
        return customerGroupDao.getUnique(hql, customerGroupId);
    }

    @Override
    public void deleteCustomerGroupById(String customerGroupId) {

        CustomerGroup customerGroup = getCustomerGroupById(customerGroupId);
        List<Customer> customers = customerService
                .getCustomerByGroup(customerGroupId);
        customerGroupDao.delete(customerGroup);

        for (Customer customer : customers) {
            customer.setCustomerGroup(null);
        }
    }

    @Override
    public void updateCustomerGroup(CustomerGroup customerGroup) {

        // delete
        List<Customer> list = customerService.getCustomerByGroup(customerGroup
                .getId());
        List<Customer> list1 = customerGroup.getCustomers();

        for (int i = 0; i < list.size(); i++) {
            if (list1.contains(list.get(i))) {
                list.remove(list.get(i));
            }
        }

        for (Customer customer : list) {
            customer.setCustomerGroup(null);
            customerService.updateCustomer(customer);
        }
        for (Customer customer1 : list1) {
            Customer customer = customerService.getCustomerByCode(customer1
                    .getCustomerCode());
            if (null == customer) {
                customer1.setCustomerGroup(customerGroup);
                customerService.saveCustomer(customer1);
            } else {
                customer.setCustomerGroup(customerGroup);
                customerService.updateCustomer(customer);
            }
        }
        customerGroupDao.update(customerGroup);

    }

    @Override
    public Page<CustomerGroup> findCustomerGroupBySearchCondition(
            SearchCondition searchCondition) {

        Session session = sessionFactory.openSession();
        FullTextSession fullTextSession = Search.getFullTextSession(session);
        QueryBuilder qb = fullTextSession.getSearchFactory()
                .buildQueryBuilder().forEntity(CustomerGroup.class).get();

        // create ordinary query, it contains search by keyword
        BooleanQuery keyWordQuery = CommonSearchUtil.searchByKeyWord(
                CustomerGroup.class, qb, searchCondition.getKeyWord(),
                searchCondition.getSearchFields());

        QueryWrapperFilter filter = null;

        // add entity associate
        Criteria criteria = session.createCriteria(CustomerGroup.class);
        criteria.setFetchMode("customers", FetchMode.JOIN);

        Page<CustomerGroup> page = new Page<CustomerGroup>();

        // set page parameters, sort column, sort sign, page size, current page
        // num
        page.setPageSize(searchCondition.getPageSize());
        page.setCurrentPage(searchCondition.getPageNum());
        page.setSortOrder(searchCondition.getSortSign());
        page.setSortColumn(searchCondition.getSortName());

        FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(
                keyWordQuery, CustomerGroup.class).setCriteriaQuery(criteria);
        page = baseHibernateDao.findByIndex(fullTextQuery, filter, page);
        fullTextSession.close();
        return page;
    }
}
