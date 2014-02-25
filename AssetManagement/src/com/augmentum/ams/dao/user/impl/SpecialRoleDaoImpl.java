package com.augmentum.ams.dao.user.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.augmentum.ams.dao.base.impl.BaseDaoImpl;
import com.augmentum.ams.dao.user.SpecialRoleDao;
import com.augmentum.ams.model.user.SpecialRole;
import com.augmentum.ams.util.LogHelper;

@Repository("specialRoleDao")
public class SpecialRoleDaoImpl extends BaseDaoImpl<SpecialRole> implements SpecialRoleDao {

    private Logger logger = Logger.getLogger(SpecialRoleDaoImpl.class);

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.augmentum.ams.dao.user.SpecialRoleDao#getSpecialRoleByUserId(java
     * .lang.String)
     */
    @Override
    public SpecialRole getSpecialRoleByUserId(String userId) {

        logger.info(LogHelper.getLogInfo("Get special role by user id start", userId));

        String hql = "FROM SpecialRole WHERE isExpired = ? AND userId = ?";
        SpecialRole specialRole = super.getUnique(hql, Boolean.FALSE, userId);

        logger.info(LogHelper.getLogInfo("Get special role by user id end"));

        return specialRole;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.augmentum.ams.dao.user.SpecialRoleDao#getSpecialRoles(java.util.List)
     */
    @Override
    public List<SpecialRole> findSpecialRolesByCustomerCodes(List<String> customerCodes) {
        logger.info(LogHelper.getLogInfo("Get special role by user id start, customerCodes size",
                customerCodes.size()));

        DetachedCriteria criteria = DetachedCriteria.forClass(SpecialRole.class);

        criteria.add(Restrictions.eq("isExpired", Boolean.FALSE));
        criteria.add(Restrictions.in("customerCode", customerCodes));
        criteria.addOrder(Order.desc("updatedTime"));

        List<SpecialRole> specialRoles = findByCriteria(criteria);

        logger.info(LogHelper.getLogInfo("Get special role by user id end, specialRoles size",
                specialRoles.size()));

        return specialRoles;
    }

}
