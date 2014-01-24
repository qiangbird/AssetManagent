<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Error Page</title>
<link rel="stylesheet" type="text/css" href="css/base/base.css">
<link rel="stylesheet" type="text/css" href="css/base/resetCss.css">
<link rel="stylesheet" type="text/css"
	href="jquery.poshytip/css/tip-green.css">
<link rel="stylesheet" type="text/css"
	href="searchList/css/dataList.css">
<link rel="stylesheet" type="text/css" href="css/common/commonList.css">
<link rel="stylesheet" type="text/css"
	href="filterBox/css/filterBox.css">
<link rel="stylesheet" type="text/css"
	href="css/search/searchCommon.css">
<link rel="stylesheet" type="text/css"
	href="css/common/jquery-ui-1.8.18.custom.css">
<link rel="stylesheet" type="text/css"
	href="datepicker/css/datepicker.css">
<link rel="stylesheet" type="text/css"
	href="css/asset/locationManagement.css">
<script type="text/javascript" src="js/common/jquery-1.7.1.min.js"></script>
<script type="text/javascript"
	src="js/common/jquery-ui-1.8.18.custom.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/error/404.css"
	media="all" />
</head>
<body id="noPermit">
	<jsp:include page="/WEB-INF/page/common/footer.jsp"></jsp:include>

</body>
<script type="text/javascript" src="js/common/common.js"></script>
<script type="text/javascript" src="js/search/searchCommon.js"></script>
<script type="text/javascript" src="searchList/js/DataList.js"></script>
<script type="text/javascript" src="filterBox/js/filterBox.js"></script>
<script type="text/javascript" src="js/common/selfDefineDialog.js"></script>
<script type="text/javascript" src="js/location/location.js"></script>
<script type="text/javascript"
	src="js/common/jquery.i18n.properties-1.0.9.js"></script>
<script type="text/javascript" src="dropDownList/dropDownList.js"></script>
<link rel="stylesheet" type="text/css"
	href="dropDownList/themes/dropDownList.css" />
<script type="text/javascript" src="js/common/selfDefineDialog.js"></script>
</html>