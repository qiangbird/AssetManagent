<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    %>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Asset List</title>
<link rel="stylesheet" type="text/css" href="css/base/base.css">
<link rel="stylesheet" type="text/css" href="css/base/resetCss.css">
<link rel="stylesheet" type="text/css" href="jquery.poshytip/css/tip-green.css">
<link rel="stylesheet" type="text/css" href="searchList/css/dataList.css">
<link rel="stylesheet" type="text/css" href="css/common/commonList.css">
<link rel="stylesheet" type="text/css" href="filterBox/css/filterBox.css">
<link rel="stylesheet" type="text/css" href="css/search/searchCommon.css">
<link rel="stylesheet" type="text/css" href="css/asset/transferLog.css">
<link rel="stylesheet" type="text/css" href="messageBar/css/messagebar.css">
</head>
<body>
	<jsp:include page="../common/header.jsp"></jsp:include>
	<div id="blank">
       <a href="home"><spring:message code="navigator.home"></spring:message></a>
       <b>&gt;</b>
       <span><spring:message code="navigator.operation.log"></spring:message></span>
    </div>
	<div id="bodyMinHight">
    <div class="content">
    	<div class="dataList">
    		<div id="searchCondition">
			<input type="hidden" id="keyword_content" value="">
			<div id="searchInputTipDiv" class="inputTipDiv"><span id="label_KeywordPlaceholder"></span></div>
			<input id="keyword" class="input_txt" name="" type="text" value="">
			<div class="filterDiv filterDiv_common">
				<button class="filterBtn filter_no_dropDown">
				</button>
				<span class="existedFlag"></span>
				<div class="filterBox">
					<div class="single_condition">
						<div class="condition_title"><spring:message code="search" /></div>
						<div class="condition_optional" id="searchFields">
							<p><input type="checkBox" name="field" class="checked_all" value="all"/><label><spring:message code="CheckedAllFields" /></label></p>
							<p><input type="checkBox" name="field" value="operatorName"/><label><spring:message code="operationLog.operatorName" /></label></p>
							<p><input type="checkBox" name="field" value="operation"/><label><spring:message code="operationLog.operation" /></label></p>
			                <p><input type="checkBox" name="field" value="operationObject"/><label><spring:message code="operationLog.operationObject" /></label></p>
			        	</div>
					</div>
			        <div class="single_condition">
						<div class="condition_title"><spring:message code="searchAsTime" /></div>
						<div class="condition_optional" id="updatedTime">
							<p class="dateP"><input id="fromTime" class="dateInput" type="text" name="field" /></p>
                            <span class="dateLine">-</span>
                            <p class="dateP"><input id="toTime" class="dateInput" type="text" name="field" /></p>
			            </div>
			        </div> 
			        <a class="reset" href="javascript:void(0);"><spring:message code="reset" /></a>
				</div>
			</div>
			<a id="searchButton" class="a_common_button green_button_thirty">
	        	<span class="left"></span>
	        	<span class="middle" ><spring:message code="search" /> </span>
	        	<span class="right"></span>
	        </a>
	    </div>
    	</div>
    </div>
    </div>
 	
    <input type="hidden" id="language" value="${sessionScope.localeLanguage }">
    <input type="hidden" id="assetUuId" value="${assetUuId }">
    <jsp:include page="/WEB-INF/page/common/footer.jsp"></jsp:include>
</body>
<script type="text/javascript" src="js/common/common.js"></script>
<script type="text/javascript" src="js/search/searchCommon.js"></script>
<script type="text/javascript" src="searchList/js/DataList.js"></script>
<script type="text/javascript" src="js/asset/searchOperationLogList.js" ></script>
<script type="text/javascript" src="filterBox/js/filterBox.js" ></script>
<script type="text/javascript" src="js/common/messageBarCommon.js"></script>
</html>