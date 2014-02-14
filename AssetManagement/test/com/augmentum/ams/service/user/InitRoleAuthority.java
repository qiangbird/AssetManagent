/**
 * 
 */
package com.augmentum.ams.service.user;

import java.io.InputStream;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.augmentum.ams.exception.BusinessException;

/**
 * @author Grylls.Xu
 * @time Oct 11, 2013 3:30:13 PM
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@TransactionConfiguration(transactionManager = "txManager")
public class InitRoleAuthority {

    private static final String AUTHORITY = "authority.xml";

    @Autowired
    private AuthorityService authorityService;
    @Autowired
    private RoleService roleService;

    @SuppressWarnings("unchecked")
    @Test
    public void testInitRoleAuthorities() throws DocumentException, BusinessException {
        SAXReader saxReader = new SAXReader();
        Document document = null;
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(AUTHORITY);
        document = saxReader.read(inputStream);
        Element root  = document.getRootElement();
        Iterator<Element> roles = root.elementIterator();

        authorityService.deleteAllAuthority();

        while (roles.hasNext()) {
            Element role = roles.next();
            String roleName = role.attributeValue("name");
            Iterator<Element> permissions = role.elementIterator();
            while (permissions.hasNext()) {
                Element authority = permissions.next();
                String authorityName = authority.getText();
                System.out.println(roleName + ":  " + authority);

                authorityService.saveAuthority(authorityName);
                roleService.saveRoleAuthorities(roleName, authorityName);
            }
        }
    }
    
    @Test
    public void testDeleteAllAuthority() {
        authorityService.deleteAllAuthority();
    }
}
