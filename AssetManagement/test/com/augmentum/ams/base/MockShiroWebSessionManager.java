/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.augmentum.ams.base;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionContext;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionContext;
import org.apache.shiro.web.session.mgt.WebSessionManager;
import org.easymock.EasyMock;

/**
 * We use this test class to Mock a shiro session at junit test.
 */
public class MockShiroWebSessionManager implements WebSessionManager {

    @Override
    public Session getSession(SessionKey sessionkey) throws SessionException {
        DefaultWebSessionManager mgr = new DefaultWebSessionManager();
        Cookie cookie = EasyMock.createMock(Cookie.class);
        mgr.setSessionIdCookie(cookie);
        mgr.setSessionIdCookieEnabled(true);
        SimpleSession session = new SimpleSession();
        session.setId("123456");
        WebSessionContext wsc = new DefaultWebSessionContext();
        wsc.setServletRequest(EasyMock.createMock(HttpServletRequest.class));
        wsc.setServletResponse(EasyMock.createMock(HttpServletResponse.class));
        SecurityUtils.getSecurityManager().start(wsc);
        return session;
    }

    @Override
    public Session start(SessionContext sessioncontext) {
        SimpleSession session = new SimpleSession();
        session.setId("123456");
        return session;
    }

    @Override
    public boolean isServletContainerSessions() {
        return false;
    }
}
