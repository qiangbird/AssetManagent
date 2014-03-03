<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ToDo Asset</title>

<link rel="stylesheet" type="text/css" href="css/base/base.css">
<link rel="stylesheet" type="text/css" href="css/base/resetCss.css">
<link rel="stylesheet" type="text/css" href="jquery.poshytip/css/tip-green.css">
<link rel="stylesheet" type="text/css" href="searchList/css/dataList.css">
<link rel="stylesheet" type="text/css" href="css/common/commonList.css">
<link rel="stylesheet" type="text/css" href="filterBox/css/filterBox.css">
<link rel="stylesheet" type="text/css" href="css/search/searchCommon.css">
<link rel="stylesheet" type="text/css" href="css/asset/assetCommon.css">
<link rel="stylesheet" type="text/css" href="css/asset/assignAssetsDialog.css">
</head>
<body>
	<jsp:include page="../common/header.jsp"></jsp:include>
	<div id="blank">
   		<a href="home"><spring:message code="navigator.home"></spring:message></a>
   		<b>&gt;</b>
   		<c:choose>
   			<c:when test="${todoFlag == 'returned'}">
		   		<span><spring:message code="panel.returnedAssets"></spring:message></span>
   			</c:when>
   			<c:when test="${todoFlag == 'received'}">
   				<span><spring:message code="panel.receivedAssets"></spring:message></span>
   			</c:when>
   		</c:choose>
	</div>
	
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
							<p><input type="checkBox" name="field" value="asset.assetId"/><label><spring:message code="asset.id"/></label></p>
							<p><input type="checkBox" name="field" value="asset.assetName"/><label><spring:message code="asset.name"/></label></p>
			                <p><input type="checkBox" name="field" value="asset.user.userName"/><label><spring:message code="asset.user"/></label></p>
			                <p><input type="checkBox" name="field" value="asset.project.projectName"/><label><spring:message code="project"/></label></p>
			                <p><input type="checkBox" name="field" value="asset.customer.customerName"/><label><spring:message code="customer"/></label></p>
			                <c:choose>
								<c:when test="${todoFlag == 'received' }">
									<p><input type="checkBox" name="field" value="assigner.userName"/><label><spring:message code="todo.assigner" /></label></p>
								</c:when>
								<c:when test="${todoFlag == 'returned' }">
									<p><input type="checkBox" name="field" value="returner.userName"/><label><spring:message code="todo.returner" /></label></p>
								</c:when>
							</c:choose>
			        	</div>
			        	<div class="single_condition">
						<c:choose>
							<c:when test="${todoFlag == 'returned' }">
								<div class="condition_title"><spring:message code="todo.returnedTime"/></div>
							</c:when>
							<c:when test="${todoFlag == 'received' }">
								<div class="condition_title"><spring:message code="todo.receivedTime"/></div>
							</c:when>
						</c:choose>
						<div class="condition_optional" id="receivedTime">
							<p class="dateP"><input id="fromTime" class="dateInput" type="text" name="field" /></p>
                            <span class="dateLine">-</span>
                            <p class="dateP"><input id="toTime" class="dateInput" type="text" name="field" /></p>
			            </div>
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
	        <c:choose>
				<c:when test="${todoFlag == 'returned' }">
					<input type="button" value="<spring:message code='confirm'/>" class="confirm-button" id="confirmReturnedButton"/>
				</c:when>
				<c:when test="${todoFlag == 'received' }">
					<input type="button" value="<spring:message code='todo.receive'/>" class="confirm-button" id="confirmReceivedButton"/>
				</c:when>
			</c:choose>
		</div>
		</div>
	</div>
	</div>
	<input type="hidden" id="locale" value="${sessionScope.localeLanguage }">
	<input type="hidden" id="todoFlag" value="${todoFlag }">
	
    <jsp:include page="/WEB-INF/page/common/footer.jsp"></jsp:include>
	
<script type="text/javascript" src="js/common/common.js"></script>
<script type="text/javascript" src="js/search/searchCommon.js"></script>
<script type="text/javascript" src="searchList/js/DataList.js"></script>
<script type="text/javascript" src="js/todo/todoList.js"></script>
<script type="text/javascript" src="filterBox/js/filterBox.js" ></script>
<script type="text/javascript" src="js/common/selfDefineDialog.js"></script>
</body>
</html>