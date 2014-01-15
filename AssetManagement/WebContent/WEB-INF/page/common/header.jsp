<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%> 
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<html>
<head>
<base href="<%=basePath%>">
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/header.css" >
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/jquery-ui-1.8.18.custom.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>datepicker/css/datepicker.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/tip-green/tip-green.css">
<title>Asset Page</title>
</head>
<body>
	<input type="hidden" id="basePath" value="<%=basePath %>">
	<input type="hidden" id="locale" value=${sessionScope.localeLanguage }>
	<div id="header_top">
            <div id="header_logo"></div>
            <div id="header_text">
                <a><spring:message code="head.title"/></a>
            </div>
            <div id="header_top_right">
                <div id="header_login">
                    <a id="header_login_name"><spring:message code="head.welcome" /> <span id="loginUser">${sessionScope.userVo.employeeName}</span></a>
                    <%-- <a class="decoration_hover cursor_hand" id="header_login_control" href="<%=path %>/logout">Login out</a> --%>
                </div>
                <div id="header_i18n">
                    <c:if test="${'en_US' == localeLanguage || null == localeLanguage}">
                        <input id="i18n" type="hidden" value="EN"/>
		                <a href="javascript:void(0)" class="i18n-set-step" name="zh" id="i18n-set-ZH">Chinese (中文)</a>
		            </c:if>
		            <c:if test="${localeLanguage=='zh_CN'}">
                        <input id="i18n" type="hidden" value="ZH"/>
		                <a href="javascript:void(0)" class="i18n-set-step" name="en" id="i18n-set-EN">英文 (English)</a>
		            </c:if>
                </div>
            </div>
     </div>
    <div id="navigator">
        <div class="headerNavigationStart"></div>
        <div class="menuCotent">
            <ul class="firstMenuUl">
                <li class="menuLi"><a href="#"><spring:message code="navigator.home" /></a></li>
                <li class="menuLi"><a><spring:message code="navigator.asset.list" /></a>
                    <ul class="sencndMenuUl">
                        <li><a href="asset/allAssets"><spring:message code="navigator.all.assets" /></a></li>
                        <li><a href="#"><spring:message code="navigator.fixed.assets" /></a></li>
                        <li><a href="#"><spring:message code="navigator.machine" /></a></li>
                        <li><a href="#"><spring:message code="navigator.monitor" /></a></li>
                        <li><a href="#/asset/software"><spring:message code="navigator.software" /></a></li>
                        <li><a href="#"><spring:message code="navigator.device" /></a></li>
                        <li><a href="#"><spring:message code="navigator.other.assets" /></a></li>
                    </ul></li>
                <li class="menuLi"><a><spring:message code="navigator.system" /></a>
                    <ul class="sencndMenuUl">
                        <li><a href="user/roleList"><spring:message code="navigator.roleList" /></a></li>
                        <li><a href="specialRole/findSpecialRoles"><spring:message code="navigator.assign.special.role" /></a></li>
                        <li><a href="#"><spring:message code="navigator.shared.customers" /></a></li>
                        <li><a href="#"><spring:message code="navigator.scheduler.task" /></a></li>
                        <li><a href="location/listLocation"><spring:message code="navigator.location.list" /></a></li>
                        <li><a href="group/list"><spring:message code="navigator.group.list" /></a></li>
                    </ul></li>
                <li class="menuLi"><a><spring:message code="navigator.customser.assets" /></a>
                    <ul class="sencndMenuUl">
                    <c:if test="${sessionScope.customerList !=null }">
                       <c:forEach var="customer" items="${sessionScope.customerList }">
				  <c:choose>
				    <c:when test="${customer.customerGroup.processType == 'SHARED' }">
				    <li>
				    <c:if test="${sessionScope.userRole=='Manager' }">
				    <span>*</span>
				    </c:if>
				    <a href="customerAsset/listCustomerAsset?customerCode=${customer.customerCode}"> <c:out value="${customer.customerName }"></c:out></a> 
				    </li>
				    </c:when>
				   <c:otherwise>  
				    <li>
				     <a href="customerAsset/listCustomerAsset?customerCode=${customer.customerCode}"> <c:out value="${customer.customerName }"></c:out></a> 
				    </li>
				   </c:otherwise>
				  </c:choose>
                       </c:forEach>
                       </c:if>
                    </ul></li>
                <li class="menuLi"><a href="customizedView/goToNewCustomizedView"><spring:message code="navigator.my.assets" /></a>
					<ul class="sencndMenuUl">
                        <li><a href="self/selfProperty"><spring:message code="navigator.self.property" /></a></li>
                        <li><a href="asset/listMyAssets"><spring:message code="navigator.my.assets" /></a></li>
                    </ul></li>
                <li class="menuLi"><a href="#"><spring:message code="navigator.transfer.log" /></a></li>
                <li class="menuLi"><a href="#"><spring:message code="navigator.operation.log" /></a></li>
            </ul>
        </div>

        <div class="headerNavigationEnd">
            <a><spring:message code="navigator.operation" /></a>
            <ul class="operationUl">
                <li><a href="auditFile/inventoryList"><spring:message code="navigator.check.inventory" /></a></li>
                <li><a href="asset/createAsset"><spring:message code="navigator.create.asset" /></a></li>
                <li><a href="#"><spring:message code="navigator.import.asset" /></a></li>
            </ul>
        </div>
    </div>
     <div id="dialog-confirm" title="Operation confirm">
   		<p id="confirm-message-body"></p>
 	</div>
 	<div id="dialog-warning" title="Warning">
  		<p id="warning-message-body"></p>
 	</div>
    <script type="text/javascript" src="<%=basePath%>js/common/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/jquery-ui-1.8.18.custom.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/autocomplete.js"></script>
	<script type="text/javascript" src="<%=basePath%>jquery.poshytip/js/jquery.poshytip.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/jquery.i18n.properties-1.0.9.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/uuid.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/header.js"></script>
    <script type="text/javascript" src="js/common/selfDefineDialog.js"></script>
</body>
</html>