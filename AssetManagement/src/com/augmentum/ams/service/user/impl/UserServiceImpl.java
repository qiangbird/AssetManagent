package com.augmentum.ams.service.user.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.QueryWrapperFilter;
import org.apache.lucene.search.TermQuery;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.augmentum.ams.dao.base.BaseHibernateDao;
import com.augmentum.ams.dao.user.UserDao;
import com.augmentum.ams.exception.BusinessException;
import com.augmentum.ams.model.enumeration.RoleEnum;
import com.augmentum.ams.model.user.Role;
import com.augmentum.ams.model.user.User;
import com.augmentum.ams.service.user.RoleService;
import com.augmentum.ams.service.user.SpecialRoleService;
import com.augmentum.ams.service.user.UserService;
import com.augmentum.ams.util.CommonSearchUtil;
import com.augmentum.ams.util.RoleLevelUtil;
import com.augmentum.ams.util.UTCTimeUtil;
import com.augmentum.ams.web.vo.system.Page;
import com.augmentum.ams.web.vo.system.SearchCondition;
import com.augmentum.ams.web.vo.user.UserVo;

@Service("userService")
public class UserServiceImpl implements UserService {

    private static Logger logger = Logger.getLogger(UserServiceImpl.class);

    protected HttpServletRequest servletRequest;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleService roleService;

    @Autowired
    private SpecialRoleService specialRoleService;
    
    @Autowired
    protected SessionFactory sessionFactory;
    
    @Autowired
    private BaseHibernateDao<User> baseHibernateDao;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.augmentum.ams.service.user.UserService#saveUserRole(java.util.List)
     */
    @Override
    public void saveUserRole(List<UserVo> userVos) throws BusinessException {

        // Before save user and role info in database, clear user_role table
        // records.
        // this.deleteUserRole();
        List<User> users = new ArrayList<User>();
        Date date = UTCTimeUtil.localDateToUTC();

        for (UserVo userVo : userVos) {
            User user = null;
            User existUser = userDao.getUserByUserId(userVo.getEmployeeId());

            // If user is saved in database already in database, then assign it
            // to user
            // If user is not save in database, then initialize user base info
            // from userVo
            if (existUser != null) {
                user = existUser;
                if (Boolean.TRUE == userVo.isDelete()) {
                    userDao.deleteUserRoleById(user.getId());
                    /*
                     * user.setExpired(Boolean.TRUE); users.add(user);
                     */
                    continue;
                }
            } else {
                user = new User();
                user.setUserId(userVo.getEmployeeId());
                user.setUserName(userVo.getEmployeeName());
                user.setCreatedTime(date);
            }

            user.setUpdatedTime(date);
            List<Role> roles = new ArrayList<Role>();

            // If user is selected ITRole, then save user ITRole in user_role
            // table
            if (userVo.isITRole()) {
                Role itRole = roleService.getRoleByName(RoleEnum.IT.name());
                roles.add(itRole);
                logger.info("Assign ITRole for user, user name is:" + user.getUserName());
            }

            // If user is selected SystemAdminRole, then save user
            // SystemAdminRole in user_role table
            if (userVo.isSystemAdminRole()) {
                Role systemAdminRole = roleService.getRoleByName(RoleEnum.SYSTEM_ADMIN.name());
                roles.add(systemAdminRole);
                logger.info("Assign SystemAdminRole for user, user name is:" + user.getUserName());
            }
            user.setRoles(roles);
            users.add(user);
        }
        userDao.saveOrUpdateAll(users);
        logger.info("Bulk save users with roles in datbase, users are:" + users);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.augmentum.ams.service.user.UserService#findUserRole()
     */
    @Override
    public List<UserVo> findUserRole() {

        // Select users and their roles, if user has no role, then he/she will
        // be filtered
        String hql = "select distinct user from User user inner join fetch user.roles role where user.isExpired = ? order by user.userId";
        List<User> users = null;
        users = userDao.find(hql, Boolean.FALSE);
        List<UserVo> userVos = new ArrayList<UserVo>();

        // For each user, encapsulate them info and role info in UserVo
        for (User user : users) {
            UserVo userVo = new UserVo();
            List<Role> roles = user.getRoles();

            // If user has ITRole then setting ITRole flag is true, if user has
            // SystemAdminRole then setting SystemAdminRole flag is true
            for (Role role : roles) {
                if (role.getRoleName().equals(RoleEnum.IT.name())) {
                    userVo.setITRole(true);
                } else if (role.getRoleName().equals(RoleEnum.SYSTEM_ADMIN.name())) {
                    userVo.setSystemAdminRole(true);
                }
            }

            userVo.setId(user.getId());
            userVo.setEmployeeId(user.getUserId());
            userVo.setEmployeeName(user.getUserName());
            userVos.add(userVo);
        }
        logger.info("Select all users which have ITRole or SystemAdminRole");
        return userVos;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.augmentum.ams.service.user.UserService#getUserByUserId(java.lang.
     *      String)
     **/

    @Override
    public User getUserByUserId(String userId) {
        String hql = "FROM User user WHERE user.userId=? AND isExpired = ?";
        User user = null;
        user = userDao.getUnique(hql, userId, Boolean.FALSE);
        logger.info("Get user info by userId:" + userId);
        return user;
    }

    public User getUserById(String userId) {
        DetachedCriteria criteria = DetachedCriteria.forClass(User.class).setFetchMode("roles",
                FetchMode.JOIN);
        criteria.add(Restrictions.eq("userId", userId));
        criteria.add(Restrictions.eq("isExpired", Boolean.FALSE));

        List<User> users = userDao.findByCriteria(criteria);
        if (users.size() > 0) {
            User user = users.get(0);
            return user;
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.augmentum.ams.service.user.UserService#validateEmployee(com.augmentum
     * .ams.web.vo.user.UserVo)
     */
    @Override
    public User validateEmployee(UserVo userVo) {
        if (null == userVo) {
            logger.error("userVo is null when validate employee");
            return null;
        } else {

            User user = this.getUserById(userVo.getEmployeeId());
            if (null == user) {
                user = new User();
                user.setUserId(userVo.getEmployeeId());
                user.setUserName(userVo.getEmployeeName());
                Date date = UTCTimeUtil.localDateToUTC();
                user.setCreatedTime(date);
                user.setUpdatedTime(date);
                userDao.save(user);
            }
            List<Role> roles = new ArrayList<Role>();
            String companyRole = RoleLevelUtil.getRoleByUserVo(userVo);
            roles.addAll(user.getRoles());
            Role role = new Role();
            role.setRoleName(companyRole);
            roles.add(role);
            user.setRoles(roles);
            return user;
        }
    }

    @Override
    public User getUserByName(String username) {
        return userDao.getUserByName(username);
    }

    @Override
    public void saveUser(User user) {
        userDao.save(user);
    }

    @Override
    public void saveUserAsUserVo(UserVo userVo) {
        User user;
        user = new User();
        user.setUserName(userVo.getEmployeeName());
        user.setUserId(userVo.getEmployeeId());
        saveUser(user);
    }

    @Override
    public User getUserByUserIdForUserRoles(String userId) {

        DetachedCriteria criteria = DetachedCriteria.forClass(User.class).setFetchMode("roles",
                FetchMode.JOIN);
        criteria.add(Restrictions.eq("userId", userId));
        criteria.add(Restrictions.eq("isExpired", Boolean.FALSE));
        List<User> list = userDao.findByCriteria(criteria);

        if (null == list || 0 == list.size()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public String getUserRole(UserVo userVo) {
        if (userVo.getEmployeeLevel().equals("Manager")
                || userVo.getEmployeeLevel().equals("Director")
                || specialRoleService.findSpecialRoleByUserId(userVo.getEmployeeId())) {
            return "Manager";
        } else {
            return "Employee";
        }
    }

    @Override
    public Map<String, User> findAllUsersFromLocal() {

        List<User> users = userDao.findAll(User.class);
        Map<String, User> employees = new HashMap<String, User>();

        for (User user : users) {
            employees.put(user.getUserName(), user);
        }
        return employees;
    }

    @Override
    public Page<User> findUserBySearchCondition(SearchCondition searchCondition) {
        
        Session session = sessionFactory.openSession();
        FullTextSession fullTextSession = Search.getFullTextSession(session);
        QueryBuilder qb = fullTextSession.getSearchFactory()
                .buildQueryBuilder().forEntity(User.class).get();

        // create ordinary query, it contains search by keyword
        BooleanQuery keyWordQuery = CommonSearchUtil.searchByKeyWord(
                User.class, qb, searchCondition.getKeyWord(),
                searchCondition.getSearchFields());
        
        BooleanQuery filterQuery = new BooleanQuery();;
        
        if (!searchCondition.getIsITRole() && !searchCondition.getIsSystemAdminRole()) {
            
            BooleanQuery defaultFilterQuery = new BooleanQuery();
            defaultFilterQuery.add(new TermQuery(new Term("roles.role_name", "it")), Occur.SHOULD);
            defaultFilterQuery.add(new TermQuery(new Term("roles.role_name", "system_admin")), Occur.SHOULD);
            
            filterQuery.add(defaultFilterQuery, Occur.MUST);
        }

        if (searchCondition.getIsITRole()) {
            
            filterQuery.add(new TermQuery(new Term("roles.role_name", "it")), Occur.MUST);
        }
        
        if (searchCondition.getIsSystemAdminRole()) {
            filterQuery.add(new TermQuery(new Term("roles.role_name", "system_admin")), Occur.MUST);
        }
        
        QueryWrapperFilter filter = new QueryWrapperFilter(filterQuery);

        // add entity associate
        Criteria criteria = session.createCriteria(User.class)
                .setFetchMode("roles", FetchMode.JOIN);

        Page<User> page = new Page<User>();

        // set page parameters, sort column, sort sign, page size, current page
        // num
        page.setPageSize(searchCondition.getPageSize());
        page.setCurrentPage(searchCondition.getPageNum());
        page.setSortOrder(searchCondition.getSortSign());
        page.setSortColumn(searchCondition.getSortName());

        FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(
                keyWordQuery, User.class).setCriteriaQuery(criteria);

        page = baseHibernateDao.findByIndex(fullTextQuery, filter, page);
        fullTextSession.close();
        return page;
    }
}
