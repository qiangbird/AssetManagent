<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
    <%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    %>
    <%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>  
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Customer Asset List</title>
<link rel="stylesheet" type="text/css" href="css/base/base.css">
<link rel="stylesheet" type="text/css" href="css/base/resetCss.css">
<link rel="stylesheet" type="text/css" href="jquery.poshytip/css/tip-green.css">
<link rel="stylesheet" type="text/css" href="searchList/css/dataList.css">
<link rel="stylesheet" type="text/css" href="css/common/commonList.css">
<link rel="stylesheet" type="text/css" href="filterBox/css/filterBox.css">
<link rel="stylesheet" type="text/css" href="css/search/searchCommon.css">
<link rel="stylesheet" type="text/css" href="css/asset/customerAsset.css">
<link rel="stylesheet" type="text/css" href="css/common/jquery-ui-1.8.18.custom.css">
<link rel="stylesheet" type="text/css" href="datepicker/css/datepicker.css">
<link rel="stylesheet" type="text/css" href="css/asset/isAssign.css" />
</head>
<body>
    <jsp:include page="../common/header.jsp"></jsp:include>
    <input id="categoryType" type="hidden" value="asset">
    <div id="blank">
   		<a href="home"><spring:message code="navigator.home"></spring:message></a>
   		<b>&gt;</b>
   		<c:choose>
   			<c:when test="${customer.customerCode == null}">
		   		<span><spring:message code="navigator.customser.assets"></spring:message></span>
   			</c:when>
   			<c:otherwise>
   				<a href="customerAsset/listAllCustomerAssets"><spring:message code="navigator.customser.assets"></spring:message></a>
   			</c:otherwise>
   		</c:choose>
   		<c:if test="${customer.customerCode != null}">
	   		<b>&gt;</b>
   			<span>${customer.customerName }</span>
   		</c:if>
	</div>
    
    <input type="hidden" id="customerGroupType" value="${customer.customerGroup.processType }">
    <input type="hidden" id="customerCode" value="${customer.customerCode }">
    <input type="hidden" id="userRole" value="${sessionScope.userRole }">
    <input type="hidden" id="userCode" value="${sessionScope.userVo.employeeId }">
    <div id="bodyMinHight">
    <div class="content">
        <div class="dataList">
            <div id="searchCondition">
            <input type="hidden" id="keyword_content" value="">
            <div id="searchInputTipDiv" class="inputTipDiv"><spring:message code="keyword" /></div>
            <input id="keyword" class="input_txt" name="" type="text" value="">
            
            <div class="filterDiv filterDiv_common">
				<button class="filterBtn filter_no_dropDown">
				</button>
				<span class="existedFlag"></span>
			     <div class="filterBox">
					<div class="single_condition">
						<div class="condition_title"><spring:message code="searchBy" /></div>
						<div class="condition_optional" id="searchFields">
							<p><input type="checkBox" name="field" class="checked_all" value="all"/><label><spring:message code="checkAll"/></label></p>
							<p><input type="checkBox" name="field" value="assetId"/><label><spring:message code="asset.id"/></label></p>
							<p><input type="checkBox" name="field" value="assetName"/><label><spring:message code="asset.name"/></label></p>
			                <p><input type="checkBox" name="field" value="user.userName"/><label><spring:message code="asset.user"/></label></p>
			                <p><input type="checkBox" name="field" value="project.projectName"/><label><spring:message code="project"/></label></p>
			                <p><input type="checkBox" name="field" value="customer.customerName"/><label><spring:message code="customer"/></label></p>
			                <p><input type="checkBox" name="field" value="poNo"/><label><spring:message code="asset.po.no" /></label></p>
			                <p><input type="checkBox" name="field" value="barCode"/><label><spring:message code="asset.bar.code"/></label></p>
			        	</div>
					</div>
					<div class="single_condition">
						<div class="condition_title"><label><spring:message code="asset.type"/></label></div>
						<div class="condition_optional" id="assetType">
							<p><input type="checkBox" name="field" class="checked_all" value="all"/><label><spring:message code="checkAll"/></label></p>
							<p><input type="checkBox" name="field" value="machine" /><label><spring:message code="asset.machine"/></label></p>
							<p><input type="checkBox" name="field" value="monitor" /><label><spring:message code="asset.monitor"/></label></p>
							<p><input type="checkBox" name="field" value="device" /><label><spring:message code="asset.device"/></label></p>
							<p><input type="checkBox" name="field" value="software" /><label><spring:message code="asset.software"/></label></p>
							<p><input type="checkBox" name="field" value="otherassets" /><label><spring:message code="asset.otherAssets"/></label></p>
			            </div>
			        </div>
			        <div class="single_condition">
						<div class="condition_title"><label><spring:message code="asset.status"/></label></div>
						<div class="condition_optional" id="assetStatus">
							<p><input type="checkBox" name="field" class="checked_all" value="all"/><label><spring:message code="checkAll"/></label></p>
							<p><input type="checkBox" name="field" value="available" /><label><spring:message code="asset.available"/></label></p>
							<p><input type="checkBox" name="field" value="in_use" /><label><spring:message code="asset.inUse"/></label></p>
							<p><input type="checkBox" name="field" value="idle" /><label><spring:message code="asset.idle"/></label></p>
							<p><input type="checkBox" name="field" value="returned" /><label><spring:message code="asset.returned"/></label></p>
			            </div>
			        </div>
			        <div class="single_condition">
						<div class="condition_title"><spring:message code="asset.check.in.date"/></div>
						<div class="condition_optional" id="checkInTime">
							<p class="dateP"><input id="fromTime" class="dateInput" type="text" name="field" /></p>
                            <span class="dateLine">-</span>
                            <p class="dateP"><input id="toTime" class="dateInput" type="text" name="field" /></p>
			            </div>
			        </div>
			        <a class="reset" href="javascript:void(0);"><spring:message code="reset"/></a>
				</div>
			</div>
            
            <a id="searchButton" class="a_common_button green_button_thirty">
                <span class="left"></span>
                <span class="middle" ><spring:message code="SearchButton" /> </span>
                <span class="right"></span>
            </a>
            <div id="customizedViewButton">
                <a id="customizedView" ><spring:message code="customizedView"/></a>
                <ul id="viewUlTemplate">
                    <li><a id="" class="existCustomizedView" ></a></li>
                </ul>
                <ul id="viewUl">
                    <li><a id="createView" ><spring:message code="createView"/></a></li>
                    <li id="viewLine"><a href="customizedView/findCustomizedViewByUserForManagement"><spring:message code="manageView"/></a></li>
                </ul>
            </div>
             <div class="operation_assets_list">
                <a class="a_operations_assets_list"><spring:message code="customer.asset.operation" /></a>
                <ul>
                	<shiro:hasAnyRoles name="MANAGER,SPECIAL_ROLE">
                   		<li id="takeOver" value=""><a><spring:message code="customer.asset.take.over" /></a></li>
                    	<li id="assgin"><a><spring:message code="customer.asset.assign" /></a></li>
                   		<li id="returnToProject" value="AVAILABLE"><a><spring:message code="customer.asset.return.to.project" /></a></li>
                    	<li id="returnToIT" value="RETURNING_TO_IT"><a><spring:message code="customer.asset.return.to.it" /></a></li>
                    	<li id="returnToCustomer" value="RETURNED"><a><spring:message code="returnToCustomer" /></a></li>
                 	</shiro:hasAnyRoles>
                	<shiro:hasRole name="EMPLOYEE">
                    	<li id="takeOver"><a><spring:message code="customer.asset.take.over" /></a></li>
                    	<li id="returnToProject" value="AVAILABLE"><a><spring:message code="customer.asset.return.to.project" /></a></li>
                	</shiro:hasRole>
                </ul>
            </div> 
            
            <shiro:hasRole name="MANAGER">
	        <form name="exportForm" id="exportForm" action="asset/export">
	        	<input type="hidden" name="assetIds" id="assetIds" value=""/>
	        	
	        	<input type="hidden" name="assetStatus" id="condition_assetStatus" value="" />
	        	<input type="hidden" name="assetType" id="condition_assetType" value="" />
	        	<input type="hidden" name="keyWord" id="condition_keyWord" value="" />
	        	<input type="hidden" name="searchFields" id="condition_searchFields" value="" />
	        	<input type="hidden" name="fromTime" id="condition_fromTime" value="" />
	        	<input type="hidden" name="toTime" id="condition_toTime" value="" />
	        	
	        </form>
	        <a id="exportIcon" ></a>
	        </shiro:hasRole>
        </div>
        </div>
    </div>
    <div class="assetOperation">
    <span class="customerAssetOperation"></span>
    </div>
    <input type="hidden" id="categoryFlag" value="1"/>
    <input type="hidden" id="language" value="${sessionScope.localeLanguage }">
    <input type="hidden" id="status" value="${status }" />
    <input type="hidden" id="type" value="${type }" />
    
    <div id="dialog" title="<spring:message code='managerAssign.dialog.title'/>">
	      <div>
	          <label id="label_CustomerName"><spring:message code="customer"/></label>
	          <select id="customer" class="dropDownSelect" name="assignCustomerCode">
                	<c:forEach var="customer" items="${sessionScope.customerList }">
                		<option value="${customer.customerCode}">${customer.customerName }</option>
                	</c:forEach>
              </select>
	      </div>
	      <div>
	          <label id="label_ProjectName"><spring:message code="project"/></label>
	          <input id="projectName" type="text"  name="projectName" placeholder="<spring:message code='dialog.placeholder.project'/>"/>
	          <input type="hidden" id="projectCode" name="projectCode"/>
	          
	      </div>
	      <div>
	          <label id="label_UserName"><spring:message code="asset.user"/></label>
	          <input id="userName" type="text" name="userName" placeholder="<spring:message code='dialog.placeholder.user'/>"/>
	          <input type="hidden" id="userId" name="userId">
	      </div>
          <a id="confirm_assign" class="a_common_button green_button_thirty">
        	<span class="left"></span>
        	<span class="middle" ><label id="label_ConfirmAssign"><spring:message code="confirm"/></label> </span>
        	<span class="right"></span>
          </a>
          <p>
	          <input type="button" id="cancel_assign" value="<spring:message code='cancel'/>">
          </p>
    </div>
    
    
	<div id="dialog-confirm" title="Operation confirm">
	  <p id="confirm-message-body"></p>
	</div>
	<div id="dialog-warning" title="<label id='label_Operation_Warning'></label>">
		<p id="warning-message-body"></p>
	</div>
	</div>
	<jsp:include page="/WEB-INF/page/common/footer.jsp"></jsp:include>
</body>
<script type="text/javascript" src="js/common/jquery.i18n.properties-1.0.9.js"></script>
<script type="text/javascript" src="js/asset/assetCommon.js"></script>
<script type="text/javascript" src="js/common/common.js"></script>
<script type="text/javascript" src="js/search/searchCommon.js"></script>
<script type="text/javascript" src="searchList/js/DataList.js"></script>
<script type="text/javascript" src="js/asset/searchCustomerAssetList.js" ></script>
<script type="text/javascript" src="filterBox/js/filterBox.js" ></script>
<script type="text/javascript" src="dropDownList/dropDownList.js"></script>
<script type="text/javascript" src="js/common/selfDefineDialog.js"></script>
<script type="text/javascript" src="js/common/json2.js"></script>
<link rel="stylesheet" type="text/css" href="dropDownList/themes/dropDownList.css" />
</html>