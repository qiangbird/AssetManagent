<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%> 
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<html>
<head>
<base href="<%=basePath%>">
<link rel="stylesheet" href="<%=basePath%>css/home/menu.css" type="text/css">

<script type="text/javascript" src="<%=basePath%>/js/common/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/common/jquery-ui-1.8.18.custom.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/common/autocomplete.js"></script>
<script type="text/javascript" src="<%=basePath%>/jquery.poshytip/js/jquery.poshytip.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/common/jquery.i18n.properties-1.0.9.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/common/uuid.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/home/menu.js"></script>

<title>Asset Page</title>
</head>
<body>
	<input type="hidden" id="language" value="${sessionScope.i18n }"/>
    <div id="navigator">
        <div class="headerNavigationStart"></div>
        <div class="menuCotent">
            <ul class="firstMenuUl">
                <li class="menuLi"><a href="#">Home</a></li>
                <li class="menuLi"><a>AssetList</a>
                    <ul class="sencndMenuUl">
                        <li><a href="asset/redirectSearchAsset">AllAssets</a></li>
                        <li><a href="#">FixedAssets</a></li>
                        <li><a href="#">Machine</a></li>
                        <li><a href="#">Monitor</a></li>
                        <li><a href="#/asset/software">Software</a></li>
                        <li><a href="#">Device</a></li>
                        <li><a href="#">OtherAssets</a></li>
                    </ul></li>
                <li class="menuLi"><a>System</a>
                    <ul class="sencndMenuUl">
                        <li><a href="user/roleList">RoleList</a></li>
                        <li><a href="specialRole/findSpecialRoles">Assign Special Role</a></li>
                        <li><a href="#">SharedCustomers</a></li>
                        <li><a href="#">SchedulerTask</a></li>
                        <li><a href="location/list">LocationList</a></li>
                        <li><a href="group/list">GroupList</a></li>
                    </ul></li>
                <li class="menuLi"><a>Customer Assets</a>
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
                <li class="menuLi"><a href="customizedView/goToNewCustomizedView">MyAssets</a>
					<ul class="sencndMenuUl">
                        <li><a href="self/selfProperty">SelfProperty</a></li>
                    </ul></li>
                <li class="menuLi"><a href="#">TransferLog</a></li>
                <li class="menuLi"><a href="#">OperationLog</a></li>
            </ul>
        </div>

        <div class="headerNavigationEnd">
            <a>Operation</a>
            <ul class="operationUl">
                <li><a href="auditFile/redirectAuditList">CheckInventory</a></li>
                <li><a href="asset/createAsset">CreateAsset</a></li>
                <li><a href="#">ImportAsset</a></li>
            </ul>
        </div>
    </div>
    <div class="header">
        <div style="padding-top: 5px; float: right;" ng-controller="i18nCtl">
          <!--   <a id="goChinese" onclick="ChangeLanguage('zh')">Chinese</a> <a id="goEnglish" onclick="ChangeLanguage('en')">English</a> -->
            <a id="goChinese">中文</a> <a id="goEnglish">English</a>
        </div>
        <div id="headerCrumbs" ng-controller="breadCrumbCtl">
        </div>
    </div>
</body>
</html>