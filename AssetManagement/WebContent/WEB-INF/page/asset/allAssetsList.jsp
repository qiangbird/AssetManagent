<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
    <%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>  
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Asset List</title>
<link rel="stylesheet" type="text/css" href="css/base/base.css">
<link rel="stylesheet" type="text/css" href="css/base/resetCss.css">
<link rel="stylesheet" type="text/css" href="searchList/css/dataList.css">
<link rel="stylesheet" type="text/css" href="css/common/commonList.css">
<link rel="stylesheet" type="text/css" href="filterBox/css/filterBox.css">
<link rel="stylesheet" type="text/css" href="css/search/searchCommon.css">
<link rel="stylesheet" type="text/css" href="css/asset/assignAssetsDialog.css">
<link rel="stylesheet" type="text/css" href="messageBar/css/messagebar.css">
</head>
<body>
	<jsp:include page="../common/header.jsp"></jsp:include>
	<input id="categoryType" type="hidden" value="asset">
	<input id="currentPage" type="hidden" value="allAssetPage">
	<div id="blank">
   		<a href="home"><spring:message code="navigator.home"/></a>
   		<b>&gt;</b>
   		<c:choose>
   			<c:when test="${isFixedAsset}">
   				<a href="asset/allAssets"><spring:message code="navigator.all.assets"/></a>
   				<b>&gt;</b>
		   		<span><spring:message code="navigator.fixed.assets"/></span>
   			</c:when>
   			<c:when test="${isWarrantyExpired}">
   				<a href="asset/allAssets"><spring:message code="navigator.all.assets"/></a>
   				<b>&gt;</b>
		   		<span><spring:message code="panel.warrantyExpiredAssets"/></span>
   			</c:when>
   			<c:when test="${requestScope.userUuid != null }">
   				<span><spring:message code="navigator.my.assets"/></span>
   			</c:when>
   			<c:otherwise>
   				<c:choose>
   					<c:when test="${type == '' }">
			   			<span><spring:message code="navigator.all.assets"/></span>
   					</c:when>
   					<c:otherwise>
   						<a href="asset/allAssets"><spring:message code="navigator.all.assets"/></a>
   					</c:otherwise>
   				</c:choose>
				<c:if test="${type == 'machine' }">
					<b>&gt;</b>
					<span><spring:message code="navigator.machine"/></span>
				</c:if>
				<c:if test="${type == 'device' }">
					<b>&gt;</b>
					<span><spring:message code="navigator.device"/></span>
				</c:if>
				<c:if test="${type == 'monitor' }">
					<b>&gt;</b>
					<span><spring:message code="navigator.monitor"/></span>
				</c:if>
				<c:if test="${type == 'software' }">
					<b>&gt;</b>
					<span><spring:message code="navigator.software"/></span>
				</c:if>
				<c:if test="${type == 'otherassets' }">
					<b>&gt;</b>
					<span><spring:message code="navigator.other.assets"/></span>
				</c:if>
   			</c:otherwise>
   		</c:choose>
	</div>

	<div id="bodyMinHight">
	<input type="hidden" id="tips" value="${requestScope.tips }">
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
					<c:if test="${type == null }">
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
					</c:if>
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
	        	<span class="middle" ><spring:message code="search" /></span>
	        	<span class="right"></span>
	        </a>
	        <c:if test="${requestScope.userUuid==null }">
	        <div id="customizedViewButton">
		        <a id="customizedView" ><spring:message code="customizedView"/></a>
		        <ul id="viewUlTemplate">
	                <li><a id="" class="existCustomizedView" ></a></li>
	            </ul>
		        <ul id="viewUl">
	                <li><a id="createView" ><spring:message code="createView"/></a></li>
	                <li id="viewLine"><a id="manageView" >
	                   <spring:message code="manageView"/></a></li>
	            </ul>
            </div>
            
            <shiro:hasRole name="IT">
	        <div class="operation_assets_list">
		        <a class="a_operations_assets_list"><spring:message code="operation" /></a>
	        	<ul>
	        		<li><label id="assignAssetsButton"><a><spring:message code="assign"/></a></label></li>
	        		<li><label id="returnAssetsToCustomer"><a><spring:message code="returnToCustomer"/></a></label></li>
	        		<li><label id="changeToFixed"><a><spring:message code="changeToFixedAsset"/></a></label></li>
	        		<li><label id="changeToNonFixed"><a><spring:message code="changeToNonFixedAsset"/></a></label></li>
	        		<li><label id="addToAudit"><a><spring:message code="addToAudit"/></a></label></li>
	        	</ul>
	        </div>
	        </shiro:hasRole>
	        </c:if>
	        
	        <shiro:hasRole name="IT">
	        <form name="exportForm" id="exportForm" action="asset/export">
	        	<input type="hidden" name="assetIds" id="assetIds" value=""/>
	        	
	        	<input type="hidden" name="assetStatus" id="condition_assetStatus" value="" />
	        	<input type="hidden" name="assetType" id="condition_assetType" value="" />
	        	<input type="hidden" name="keyWord" id="condition_keyWord" value="" />
	        	<input type="hidden" name="searchFields" id="condition_searchFields" value="" />
	        	<input type="hidden" name="fromTime" id="condition_fromTime" value="" />
	        	<input type="hidden" name="toTime" id="condition_toTime" value="" />
	        	
	        	<input type="hidden" name="isFixedAsset" value="${isFixedAsset }" />
	        	<input type="hidden" name="isWarrantyExpired" value="${isWarrantyExpired }" />
	        </form>
	        <a id="exportIcon" ></a>
	        </shiro:hasRole>
	    </div>
    	</div>
    </div>
    </div>
    <div id="dialog_assign" title="<spring:message code='itAssign.dialog.title'/>">
	      <div>
	          <span id="not_null_flag">*</span>
	          <label id="label_CustomerName"><spring:message code="customer"/></label>
	          <input id="customerCode"  type="text" value="" />
              <input id="customerName" type="hidden" value="" />
	      </div>
	      <div>
	          <label id="label_ProjectName"><spring:message code="project"/></label>
	          <input id="projectCode" type="text" value=""/>
	          <input type="hidden" id="projectName" value=""/>
	          
	      </div>
	      <div>
	          <label id="label_UserName"><spring:message code="asset.user"/></label>
	          <input id="userId" type="text" value=""/>
	          <input type="hidden" id="userName" value="">
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
 	<div id="dialog-warning" title="Warning">
  		<p id="warning-message-body"></p>
 	</div>
 	
    <input type="hidden" id="categoryFlag" value="1"/>
    <input type="hidden" id="language" value="${sessionScope.localeLanguage }">
    <input type="hidden" id="userUuid" value="${requestScope.userUuid }">
    <input type="hidden" id="status" value="${status }" />
    <input type="hidden" id="type" value="${type }" />
    <input type="hidden" id="isFixedAsset" value="${isFixedAsset }" />
    <input type="hidden" id="isWarrantyExpired" value="${isWarrantyExpired }" />

    <input type="hidden" id="locale" value="${sessionScope.localeLanguage }">
    <jsp:include page="/WEB-INF/page/common/footer.jsp"></jsp:include>
</body>
<script type="text/javascript" src="js/common/common.js"></script>
<script type="text/javascript" src="js/search/searchCommon.js"></script>
<script type="text/javascript" src="searchList/js/DataList.js"></script>
<script type="text/javascript" src="js/asset/searchAssetList.js" ></script>
<script type="text/javascript" src="filterBox/js/filterBox.js" ></script>
<script type="text/javascript" src="js/common/selfDefineDialog.js"></script>
<script type="text/javascript" src="js/common/messageBarCommon.js"></script>
<script type="text/javascript" src="js/common/jquery.form.js"></script>
</html>