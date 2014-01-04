package com.augmentum.ams.service.user.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.augmentum.ams.dao.user.SpecialRoleDao;
import com.augmentum.ams.model.asset.Customer;
import com.augmentum.ams.model.user.SpecialRole;
import com.augmentum.ams.service.asset.CustomerService;
import com.augmentum.ams.service.user.SpecialRoleService;
import com.augmentum.ams.util.LogHelper;
import com.augmentum.ams.util.UTCTimeUtil;
import com.augmentum.ams.web.vo.user.SpecialRoleVo;

@Service("specialRoleService")
public class SpecialRoleServiceImpl implements SpecialRoleService{

    private static Logger logger = Logger.getLogger(SpecialRoleServiceImpl.class);
    
    @Autowired
    private SpecialRoleDao specialRoleDao;
    
    @Autowired
    private CustomerService customerService;

    @Override
    public void saveOrUpdateSpecialRole(SpecialRoleVo specialRoleVo) {
        
        logger.info(LogHelper.getLogInfo("Save or update specialRole at service start", specialRoleVo.getSpecialRoles()));
        
        List<SpecialRole> specialRoles = new ArrayList<SpecialRole>();
        Date date = UTCTimeUtil.localDateToUTC();

        for (SpecialRoleVo srVo : shrinkWrapSpecialRoles(specialRoleVo)) {
            SpecialRole specialRole = null;
            SpecialRole existSpecialRole = specialRoleDao
                    .getSpecialRoleByUserId(srVo.getEmployeeId());

            if (existSpecialRole != null) {
                specialRole = existSpecialRole;
                if(Boolean.TRUE == srVo.isDelete()){
                    specialRole.setExpired(Boolean.TRUE);
                }
            } else {
                specialRole = new SpecialRole();
                specialRole.setCustomerCode(srVo.getCustomerCode());
                specialRole.setUserId(srVo.getEmployeeId());
                specialRole.setUserName(srVo.getEmployeeName());
                specialRole.setCreatedTime(date);
            }
            specialRole.setUpdatedTime(date);
            specialRoles.add(specialRole);
        }
        specialRoleDao.saveOrUpdateAll(specialRoles);
        
        logger.info(LogHelper.getLogInfo("Save or update specialRole  at service end"));
    }
    
    // shrink-wrap the specialRoleVos from string to object in the specialRoleVo
    private List<SpecialRoleVo> shrinkWrapSpecialRoles(SpecialRoleVo specialRoleVo){
        
        List<SpecialRoleVo> specialRoleVos = new ArrayList<SpecialRoleVo>();
        JSONArray specialRolesArray = JSONArray.fromObject(specialRoleVo.getSpecialRoles());
        JSONObject specialRoleObject = null;
        
        for(int i = 0; i < specialRolesArray.size(); i++){
            SpecialRoleVo newSpecialRoleVo = new SpecialRoleVo();
            specialRoleObject = JSONObject.fromObject(specialRolesArray.get(i));
            newSpecialRoleVo.setCustomerCode(specialRoleObject.getString("customerCode"));
            newSpecialRoleVo.setEmployeeId(specialRoleObject.getString("employeeId"));
            newSpecialRoleVo.setEmployeeName(specialRoleObject.getString("employeeName"));
            newSpecialRoleVo.setDelete(specialRoleObject.getBoolean("isDelete"));
            specialRoleVos.add(newSpecialRoleVo);
        }
        
        return specialRoleVos;
    }

    @Override
    public List<SpecialRoleVo> findSpecialRolesByCustomerCodes(List<String> customerCodes) {
        
        logger.info(LogHelper.getLogInfo("Find pecial roles by customerCodes  at service start, customerCodes size is", customerCodes.size()));
        
        List<SpecialRole> specialRoles = specialRoleDao.findSpecialRolesByCustomerCodes(customerCodes);
        List<SpecialRoleVo> specialRoleVos = new ArrayList<SpecialRoleVo>();
        
        if(0 < specialRoles.size()){
            for(SpecialRole specialRole : specialRoles){
                SpecialRoleVo specialRoleVo = new SpecialRoleVo();
                specialRoleVo.setCustomerCode(specialRole.getCustomerCode());
                specialRoleVo.setCustomerName(getCustomerName(specialRole.getCustomerCode()));
                specialRoleVo.setDelete(Boolean.FALSE);
                specialRoleVo.setEmployeeId(specialRole.getUserId());
                specialRoleVo.setEmployeeName(specialRole.getUserName());
                specialRoleVos.add(specialRoleVo);
            }
        }
        logger.info(LogHelper.getLogInfo("Find pecial roles by customerCodes  at service end"));
        
        return specialRoleVos;
    }
    
    @Override
    public JSONArray changeVOToJSON(List<SpecialRoleVo> specialRoleVos){
        
        JSONArray specialRoles = new JSONArray();
        
        for(SpecialRoleVo specialRoleVo : specialRoleVos) {
            JSONObject specialRoleObject = new JSONObject();
            specialRoleObject.put("customerCode", specialRoleVo.getCustomerCode());
            specialRoleObject.put("customerName", specialRoleVo.getCustomerName());
            specialRoleObject.put("employeeId", specialRoleVo.getEmployeeId());
            specialRoleObject.put("employeeName", specialRoleVo.getEmployeeName());
            specialRoles.add(specialRoleObject);
        }
        return specialRoles;
    }
    
    private String getCustomerName(String customerCode){
        
        Customer customer = customerService.getCustomerByCode(customerCode);
        
        return customer.getCustomerName();
    }
    
    @Override
    public boolean findSpecialRoleByUserId(String userId) {
        String hql="from SpecialRole where userId = ?";
        List<SpecialRole> list = specialRoleDao.find(hql,userId);
        return list.size()>0?true:false;
    }
}
