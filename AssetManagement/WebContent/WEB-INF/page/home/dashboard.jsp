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
				<th><spring:message code="navigator.machine"/></th>
				<th><spring:message code="navigator.monitor"/></th>
				<th><spring:message code="navigator.software"/></th>
				<th><spring:message code="navigator.device"/></th>
				<th><spring:message code="other"/></th>
				<th><spring:message code="total"/></th>
			</tr>
			<shiro:hasRole name="IT">
			<tr class="tr-all-asset">
				<td class="tr-header" content="allAssets"><spring:message code="navigator.all.assets"/></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td class="tr-header" content="allAvailable"><spring:message code="asset.available"/></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td class="tr-header" content="allInuse"><spring:message code="asset.inUse"/></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td class="tr-header" content="allIdle"><spring:message code="asset.idle"/></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td class="tr-header" content="returned"><spring:message code="asset.returned"/></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td class="tr-header" content="borrowed"><spring:message code="asset.borrowed"/></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td class="tr-header" content="broken"><spring:message code="asset.broken"/></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td class="tr-header" content="writeOff"><spring:message code="asset.writeOff"/></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td class="tr-header" content="returningToIT"><spring:message code="asset.returningToIT"/></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td class="tr-header" content="assigning"><spring:message code="asset.assigning"/></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<!--  
			<tr class="tr-my-asset">
				<td class="tr-header"><spring:message code="navigator.fixed.assets"/></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			-->
			</shiro:hasRole>
			<shiro:hasAnyRoles name="MANAGER,SPECIAL_ROLE">
			<tr class="tr-customer-asset">
				<td class="tr-header-customer" style="cursor: pointer; font-weight: bold;" content="Customer Asset">
					<div class="tree_icons tree_icon_close">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
					<spring:message code="navigator.customser.assets"/>
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
					<div class="tree_icons tree_icon_close">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
					<spring:message code="asset.available"/>
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
					<div class="tree_icons tree_icon_close">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
					<spring:message code="asset.inUse"/>
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
					<div class="tree_icons tree_icon_close">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
					<spring:message code="asset.idle"/>
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
				<td class="tr-header" content="myAssets"><spring:message code="navigator.my.assets"/></td>
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
		<div class="warrantyExpiredPanel">
			<div class="panel_title">
				<span><spring:message code="panel.warrantyExpiredAssets"/></span>
				<span id="viewMore_warrantyExpired" class="viewMore">
					<span class="viewMoreIcon">&nbsp;&nbsp;&nbsp;&nbsp;</span>
					<span class="viewMoreContent"><spring:message code="panel.more"/></span>
				</span>
			</div>
			<div class="leftPanel_content">
				<table>
					<tr>
						<th><spring:message code="asset.id"/></th>
						<th><spring:message code="asset.name"/></th>
						<th><spring:message code="Customer"/></th>
						<th><spring:message code="asset.warranty"/></th>
					</tr>
				</table>
			</div>
		</div>
		
	</shiro:hasRole>
	
</div>

<div id="right">
	<shiro:hasRole name="IT">
	<div class="newlyPurchaseItemsPanel">
		<div class="panel_title">
			<span><spring:message code="panel.newlyPurchaseItems"/></span>
			<span id="" class="viewMore">
				<span class="viewMoreIcon">&nbsp;&nbsp;&nbsp;&nbsp;</span>
				<span class="viewMoreContent"><spring:message code="panel.more"/></span>
			</span>
		</div>
		<div class="todo_content">
			<table>
				<tr>
					<th style="width: 185px;"><spring:message code="asset.name"/></th>
					<th style="width: 130px;"><spring:message code="Date"/></th>
					<th style="width: 115px;"><spring:message code="delete"/></th>
				</tr>
			</table>
		</div>
	</div>
	
	<div class="returnedAssetPanel">
		<div class="panel_title">
			<span><spring:message code="panel.returnedAssets"/></span>
			<span id="viewMore_returnedAsset" class="viewMore">
				<span class="viewMoreIcon">&nbsp;&nbsp;&nbsp;&nbsp;</span>
				<span class="viewMoreContent"><spring:message code="panel.more"/></span>
			</span>
		</div>
		<div class="todo_content">
			<table>
				<tr>
					<th class="tr_checkbox"><div class="div_checkbox_all"></div></th>
					<th style="width: 100px;"><spring:message code="asset.name"/></th>
					<th style="width: 130px;"><spring:message code="Customer"/></th>
					<th style="width: 115px;"><spring:message code="todo.returnedTime"/></th>
				</tr>
			</table>
			<input class="confirmButton" id="returnedAsset" type="button" value="<spring:message code='todo.return'/>"/>
		</div>
	</div>
	</shiro:hasRole>
	
	<shiro:hasAnyRoles name="MANAGER,SPECIAL_ROLE">
	<div class="receivedAssetPanel">
		<div class="panel_title">
			<span><spring:message code="panel.receivedAssets"/></span>
			<span id="viewMore_receivedAsset" class="viewMore">
				<span class="viewMoreIcon">&nbsp;&nbsp;&nbsp;&nbsp;</span>
				<span class="viewMoreContent"><spring:message code="panel.more"/></span>
			</span>
		</div>
		<div class="todo_content">
			<table>
				<tr>
					<th class="tr_checkbox"><div class="div_checkbox_all"></div></th>
					<th style="width: 100px;"><spring:message code="asset.name"/></th>
					<th style="width: 130px;"><spring:message code="Customer"/></th>
					<th style="width: 115px;"><spring:message code="todo.receivedTime"/></th>
				</tr>
			</table>
			<input class="confirmButton" id="receivedAsset" type="button" value="<spring:message code='todo.receive'/>"/>
		</div>
	</div>
	</shiro:hasAnyRoles>
	
	<shiro:hasAnyRoles name="MANAGER,SPECIAL_ROLE">
		<div class="idleAssetPanel">
			<div class="panel_title">
				<span><spring:message code="panel.idleAssets"/></span>
				<span id="viewMore_idleAsset" class="viewMore">
					<span class="viewMoreIcon">&nbsp;&nbsp;&nbsp;&nbsp;</span>
					<span class="viewMoreContent"><spring:message code="panel.more"/></span>
				</span>
			</div>
			<div class="todo_content">
				<table>
					<tr>
						<th class="tr_checkbox"><div class="div_checkbox_all"></div></th>
						<th style="width: 100px;"><spring:message code="asset.name"/></th>
						<th style="width: 130px;"><spring:message code="Customer"/></th>
						<th style="width: 115px;"><spring:message code="User"/></th>
					</tr>
				</table>
				<input class="confirmButton" id="idleAsset" type="button" value="<spring:message code="returnToIT"/>"/>
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