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
<link rel="stylesheet" type="text/css" href="css/search/searchCommon.css">
<link rel="stylesheet" type="text/css" href="css/asset/assetCommon.css">
<link rel="stylesheet" type="text/css" href="css/asset/assignAssetsDialog.css">
</head>
<body>
	<jsp:include page="../common/header.jsp"></jsp:include>
	<div id="bodyMinHight">
	<c:choose>
		<c:when test="${todoFlag == 'returned' }">
			<div class="content">
				<div class="dataList">
					<input type="button" value="<spring:message code='todo.return'/>" class="confirm-button" id="confirmReturnedButton"/>
				</div>
			</div>
		</c:when>
		<c:when test="${todoFlag == 'received' }">
			<div class="content">
				<div class="dataList">
					<input type="button" value="<spring:message code='todo.receive'/>" class="confirm-button" id="confirmReceivedButton"/>
				</div>
			</div>
		</c:when>
	</c:choose>
	</div>
	<input type="hidden" id="locale" value="${sessionScope.localeLanguage }">
	<input type="hidden" id="todoFlag" value="${todoFlag }">
	
    <jsp:include page="/WEB-INF/page/common/footer.jsp"></jsp:include>
	
<script type="text/javascript" src="js/common/common.js"></script>
<script type="text/javascript" src="js/search/searchCommon.js"></script>
<script type="text/javascript" src="searchList/js/DataList.js"></script>
<script type="text/javascript" src="js/asset/returnedAssetList.js"></script>
<script type="text/javascript" src="js/common/selfDefineDialog.js"></script>
</body>
</html>