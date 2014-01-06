<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
request.setCharacterEncoding("UTF-8");
response.setCharacterEncoding("UTF-8");
String path = request.getContextPath();
String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/";
%>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Location Management</title>
<link rel="stylesheet" type="text/css" href="css/asset/locationList.css">
<link rel="stylesheet" type="text/css" href="css/common/filterBox.css">
<!-- <link rel="stylesheet" type="text/css" href="css/common/dataList.css"> -->
<link rel="stylesheet" type="text/css" href="css/common/search.css">
<link rel="stylesheet" href="css/asset/locationManagement.css" type="text/css">
<script type="text/javascript" src="js/common/jquery-1.7.1.min.js"></script>
</head>
<body>
<jsp:include page="../common/header.jsp"></jsp:include>

	<%-- <form action="location/update/${location.id }" method="put"> --%>
	<form:form action="location/update/${location.id }" method="put">
		<div class="create-table">
			<div>
				<span>Site</span><input type="text" name="site" value="${location.site }"/>
			</div>
			<div>
				<span>Room</span><input type="text"	name="room" value="${location.room }" />
			</div>
		</div>
		<div class="submit-div">
			<%-- <c:if test="${!isCreatePage }">
				<input type="hidden" name="_method" value="put" />
			</c:if> --%>
			<input class="input-80-30" type="submit" value="submit" />
		</div>
		</form:form>
	<%-- </form> --%>
<jsp:include page="/WEB-INF/page/common/footer.jsp"></jsp:include>
</body>
</html>