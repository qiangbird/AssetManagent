<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base/base.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base/resetCss.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>jquery.poshytip/css/tip-green.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>searchList/css/dataList.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/commonList.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>filterBox/css/filterBox.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/search/searchCommon.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>datepicker/css/datepicker.css">
	
	<script type="text/javascript" src="<%=basePath%>js/common/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/jquery-ui-1.8.18.custom.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/jquery.i18n.properties-1.0.9.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/search/searchCustomizedView.js" ></script>
	<script type="text/javascript" src="<%=basePath%>filterBox/js/filterBox.js" ></script>
	<script type="text/javascript" src="<%=basePath%>jquery.poshytip/js/jquery.poshytip.js" ></script>
	<script type="text/javascript" src="<%=basePath%>searchList/js/DataList.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/audit/inventoryAsset.js" ></script>
	
	<div id="viewMoreDetailDialog">
   	 	<input id="flag" type="hidden" value="${flag }"></input>
   	 	<input id="auditFileName" type="hidden" value="${auditFileName }"></input>
      	<div id="checkResult">
            <a id="auditLink">Audited</a>
            <a id="unAuditLink">Unaudited</a>
            <a id="inconsistentLink">Inconsistent</a>
    	</div>
		<div class="dataList dataList-width">
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
						<div class="condition_title"><label><spring:message code="searchBy" /></label></div>
						<div class="condition_optional" id="searchFields">
							<p><input type="checkBox" name="field" class="checked_all" value="all"/><label><spring:message code="checkAll"/></label></p>
							<p><input type="checkBox" name="field" value="assetId" /><label><spring:message code="asset.assetId"/></label></p>
							<p><input type="checkBox" name="field" value="assetName" /><label><spring:message code="asset.name"/></label></p>
			                <p><input type="checkBox" name="field" value="user.userName"/><label><spring:message code="asset.user"/></label></p>
			                <p><input type="checkBox" name="field" value="project.projectName"/><label><spring:message code="asset.project"/></label></p>
			                <p><input type="checkBox" name="field" value="customer.customerName"/><label><spring:message code="asset.customer"/></label></p>
			                <p><input type="checkBox" name="field" value="poNo"/><label><spring:message code="asset.poNo" /></label></p>
			                <p><input type="checkBox" name="field" value="barCode"/><label><spring:message code="asset.barcode"/></label></p>
			        	</div>
					</div>
					<div class="single_condition">
						<div class="condition_title"><label><spring:message code="asset.type"/></label></div>
						<div class="condition_optional" id="assetType">
							<p><input type="checkBox" name="field" class="checked_all" value="all"/><label><spring:message code="checkAll"/></label></p>
							<p><input type="checkBox" name="field" value="MACHINE" /><label><spring:message code="asset.machine"/></label></p>
							<p><input type="checkBox" name="field" value="MONITOR" /><label><spring:message code="asset.monitor"/></label></p>
							<p><input type="checkBox" name="field" value="DEVICE" /><label><spring:message code="asset.device"/></label></p>
							<p><input type="checkBox" name="field" value="SOFTWARE" /><label><spring:message code="asset.software"/></label></p>
							<p><input type="checkBox" name="field" value="OTHERASSETS" /><label><spring:message code="asset.otherAssets"/></label></p>
			            </div>
			        </div>
			        <div class="single_condition">
						<div class="condition_title"><label><spring:message code="asset.status"/></label></div>
						<div class="condition_optional" id="assetStatus">
							<p><input type="checkBox" name="field" class="checked_all" value="all"/><label><spring:message code="checkAll"/></label></p>
							<p><input type="checkBox" name="field" value="AVAILABLE" /><label><spring:message code="asset.available"/></label></p>
							<p><input type="checkBox" name="field" value="IN_USE" /><label><spring:message code="asset.inUse"/></label></p>
							<p><input type="checkBox" name="field" value="IDLE" /><label><spring:message code="asset.idle"/></label></p>
							<p><input type="checkBox" name="field" value="RETURNED" /><label><spring:message code="asset.returned"/></label></p>
			            </div>
			        </div>
			        <div class="single_condition">
						<div class="condition_title"><spring:message code="asset.checkInDate"/></div>
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
	        	<span class="middle" ><spring:message code="search" /></span>
	        	<span class="right"></span>
	        </a>
	        <div id="customizedViewButton">
		        <a id="customizedView" ><spring:message code="customizedView"/></a>
		        <ul id="viewUlTemplate">
	                <li><a id="" class="existCustomizedView" ></a></li>
	            </ul>
		        <ul id="viewUl">
	                <li><a href="<%=basePath%>customizedView/goToNewCustomizedView"><spring:message code="createView"/></a></li>
	                <li id="viewLine"><a href="<%=basePath%>customizedView/findCustomizedViewByUserForManagement"><spring:message code="manageView"/></a></li>
	            </ul>
            </div>
	    </div>
	    </div>
    </div>
