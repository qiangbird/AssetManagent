<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
    <%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>  
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>DashBoard</title>


</head>
<body>
	<jsp:include page="../common/header.jsp"></jsp:include>
	<div id="main">
		<div id="left"></div>
		<div id="right"></div>
	</div>
	<shiro:hasRole name="IT">
	<div class="returnedAssetPanel">
		<div class="panel_title">
			<span>ReturnedAsset</span>
			<span><a href="todo/redirectReturnedAssetList">View More</a></span>
		</div>
		<table border="1">
			<tr>
				<td><input class="checkall" type="checkbox"/></td>
				<td>AssetName</td>
				<td>ProjectName</td>
				<td>ReturnedTime</td>
			</tr>
		</table>
		<input id="returnedAsset" type="button" value="Return"/>
	</div>
	</shiro:hasRole>
	<div class="mainPanel">
		<table border="1">
			<tr>
				<td></td>
				<td>Device</td>
				<td>Machine</td>
				<td>Monitor</td>
				<td>Other</td>
				<td>Software</td>
				<td>Total</td>
			</tr>
			<shiro:hasRole name="IT">
			<tr>
				<td>All Asset</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			</shiro:hasRole>
			<tr>
				<td>My Asset</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<shiro:hasAnyRoles name="MANAGER,EMPLOYEE,SPECIAL_ROLE">
			<tr>
				<td>Customer Asset</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>Available Customer Asset</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>In Use Customer Asset</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			</shiro:hasAnyRoles>
			<shiro:hasRole name="IT">
			<tr>
				<td>Fixed</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>Available</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>In Use</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>Returned</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>Borrowed</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>Broken</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>Write Off</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			</shiro:hasRole>
		</table>
	</div>

	<input type="hidden" id="locale" value="${sessionScope.localeLanguage }">
    <jsp:include page="/WEB-INF/page/common/footer.jsp"></jsp:include>
    
<script type="text/javascript" src="js/home/dashboard.js"></script>
</body>
</html>