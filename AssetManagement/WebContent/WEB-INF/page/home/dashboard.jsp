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
<title>DashBoard</title>
<link rel="stylesheet" type="text/css" href="css/base/base.css">
<link rel="stylesheet" type="text/css" href="css/base/resetCss.css">
<link rel="stylesheet" type="text/css" href="jquery.poshytip/css/tip-green.css">
<link rel="stylesheet" type="text/css" href="css/asset/assignAssetsDialog.css">
<link rel="stylesheet" type="text/css" href="messageBar/css/messagebar.css">
<link rel="stylesheet" type="text/css" href="css/home/dashboard.css">

</head>
<body>
	<jsp:include page="../common/header.jsp"></jsp:include>
	
<div id="main">

<div id="left">
	<div class="mainPanel">
		<table border="1">
			<tr class="td-header">
				<th  class="tr-header"></th>
				<th>Machine</th>
				<th>Monitor</th>
				<th>Software</th>
				<th>Device</th>
				<th>Other</th>
				<th>Total</th>
			</tr>
			<shiro:hasRole name="IT">
			<tr class="tr-all-asset">
				<td class="tr-header">All Asset</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td class="tr-header">Fixed</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td class="tr-header">Available</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td class="tr-header">In Use</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td class="tr-header">Returned</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td class="tr-header">Borrowed</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td class="tr-header">Broken</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td class="tr-header">Write Off</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			</shiro:hasRole>
			<shiro:hasAnyRoles name="MANAGER,EMPLOYEE,SPECIAL_ROLE">
			<tr class="tr-customer-asset">
				<td class="tr-header-customer" style="cursor: pointer; font-weight: bold;" content="Customer Asset">
					<div class="tree_icons tree_icon_close">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>Customer Asset
				</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td class="tr-header-customer" style="cursor: pointer;" content="Available Customer Asset">
					<div class="tree_icons tree_icon_close">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>Available
				</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td class="tr-header-customer" style="cursor: pointer;" content="In Use Customer Asset">
					<div class="tree_icons tree_icon_close">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>In Use
				</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td class="tr-header-customer" style="cursor: pointer;" content="Idle Customer Asset">
					<div class="tree_icons tree_icon_close">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>Idle
				</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			</shiro:hasAnyRoles>
			<tr class="tr-my-asset">
				<td class="tr-header">My Asset</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
		</table>
	</div>
	
	<shiro:hasRole name="IT">
		<div class="warrantyTimeExpired">
			<div class="panel_title">
				<span>Asset Warranty Time Will Be Expired</span>
				<span><a href="todo/redirectReturnedAssetList?todoFlag=returned">View More</a></span>
			</div>
			<div class="leftPanel_content">
				<table>
					<tr>
						<th>AssetId</th>
						<th>AssetName</th>
						<th>ProjectName</th>
						<th>WarrantyTime</th>
					</tr>
				</table>
			</div>
		</div>
		
		<div class="licenseTimeExpired">
			<div class="panel_title">
				<span>Asset License Time Will Be Expired</span>
				<span><a href="todo/redirectReturnedAssetList?todoFlag=returned">View More</a></span>
			</div>
			<div class="leftPanel_content">
				<table>
					<tr>
						<th>AssetId</th>
						<th>AssetName</th>
						<th>ProjectName</th>
						<th>LicensesExpiredTime</th>
					</tr>
				</table>
			</div>
		</div>
	</shiro:hasRole>
	
</div>

<div id="right">
	<shiro:hasRole name="IT">
	<div class="newlyComingAssetPanel">
		<div class="panel_title">
			<span>Newly Coming Assets</span>
			<span><a href="todo/redirectReturnedAssetList?todoFlag=returned">View More</a></span>
		</div>
		<div class="todo_content">
			<table>
				<tr>
					<th style="width: 100px;">AssetName</th>
					<th style="width: 130px;">ProjectName</th>
					<th style="width: 115px;">CheckInTime</th>
				</tr>
			</table>
		</div>
	</div>
	
	<div class="returnedAssetPanel">
		<div class="panel_title">
			<span>Returned Assets</span>
			<span><a href="todo/redirectReturnedAssetList?todoFlag=returned">View More</a></span>
		</div>
		<div class="todo_content">
			<table>
				<tr>
					<th class="tr_checkbox"><input class="checkall" type="checkbox"/></th>
					<th style="width: 100px;">AssetName</th>
					<th style="width: 130px;">ProjectName</th>
					<th style="width: 115px;">ReturnedTime</th>
				</tr>
			</table>
			<input class="confirmButton" id="returnedAsset" type="button" value="Return"/>
		</div>
	</div>
	</shiro:hasRole>
	
	<shiro:hasAnyRoles name="MANAGER,SPECIAL_ROLE">
	<div class="receivedAssetPanel">
		<div class="panel_title">
			<span>Received Assets</span>
			<span><a href="todo/redirectReturnedAssetList?todoFlag=received">View More</a></span>
		</div>
		<div class="todo_content">
			<table>
				<tr>
					<th class="tr_checkbox"><input class="checkall" type="checkbox"/></th>
					<th style="width: 100px;">AssetName</th>
					<th style="width: 130px;">ProjectName</th>
					<th style="width: 115px;">ReceivedTime</th>
				</tr>
			</table>
			<input class="confirmButton" id="receivedAsset" type="button" value="Received"/>
		</div>
	</div>
	</shiro:hasAnyRoles>
	
	<shiro:hasAnyRoles name="MANAGER,SPECIAL_ROLE">
		<div class="idleAssetPanel">
			<div class="panel_title">
				<span>Idle Assets</span>
				<span><a href="todo/redirectReturnedAssetList?todoFlag=received">View More</a></span>
			</div>
			<div class="todo_content">
				<table>
					<tr>
						<th class="tr_checkbox"><input class="checkall" type="checkbox"/></th>
						<th style="width: 100px;">AssetName</th>
						<th style="width: 130px;">ProjectName</th>
						<th style="width: 115px;">User</th>
					</tr>
				</table>
				<input class="confirmButton" id="receivedAsset" type="button" value="ReturnToIT"/>
			</div>
		</div>
	</shiro:hasAnyRoles>
</div>

</div>

	<input type="hidden" id="locale" value="${sessionScope.localeLanguage }">
    <jsp:include page="/WEB-INF/page/common/footer.jsp"></jsp:include>
    
<script type="text/javascript" src="js/home/dashboard.js"></script>
</body>
</html>