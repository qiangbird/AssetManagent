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
<title>Asset List</title>
<link rel="stylesheet" type="text/css" href="css/base/base.css">
<link rel="stylesheet" type="text/css" href="css/base/resetCss.css">
<link rel="stylesheet" type="text/css" href="jquery.poshytip/css/tip-green.css">
<link rel="stylesheet" type="text/css" href="searchList/css/dataList.css">
<link rel="stylesheet" type="text/css" href="css/common/commonList.css">
<link rel="stylesheet" type="text/css" href="filterBox/css/filterBox.css">
<link rel="stylesheet" type="text/css" href="css/search/searchCommon.css">
<link rel="stylesheet" type="text/css" href="css/common/jquery-ui-1.8.18.custom.css">
<link rel="stylesheet" type="text/css" href="datepicker/css/datepicker.css">
<link rel="stylesheet" type="text/css" href="css/asset/locationManagement.css">
<script type="text/javascript" src="js/common/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="js/common/jquery-ui-1.8.18.custom.min.js"></script>
</head>
<body>
	<jsp:include page="../common/header.jsp"></jsp:include>
	<div id ="messageBar"></div>
    <div class="content">
    <!-- 核心入口 -->
    	<div class="dataList">
    		<div id="searchCondition">
			<input type="hidden" id="keyword_content" value="">
			<div id="searchInputTipDiv" class="inputTipDiv"><span id="label_KeywordPlaceholder"></span></div>
			<input id="keyword" class="input_txt" name="" type="text" value="">
			<a id="searchButton" class="a_common_button green_button_thirty">
	        	<span class="left"></span>
	        	<span class="middle" ><spring:message code="SearchButton" /></span>
	        	<span class="right"></span>
	        </a>
	        <a id="addButton"><spring:message code="add" /></a>
	    </div>
    	</div>
    </div>
    
    <div id="dialog_location">
     <table>
            <tr>
            
            <td valign="top">
               <form action="location/save" method="post" id="dialog">
               <table  id="location_table">
        <div class="create-table">
            <div>
            <input type="hidden" name="id" id="location_id" value="${location.id }"/>
            </div>
            <div>
                <tr><td><spring:message code="location.site" /></td><td><input type="text" name="site" id="site" value="${location.site }"/><td/></tr>
            </div>
            <div>
                <tr><td><spring:message code="location.room" /></td><td><input type="text" name="room" id="room" value="${location.room }" /></td></tr>
            </div>
        </div>
        
        <div class="submit-div">
            <%-- <c:if test="${!isCreatePage }">
                <input type="hidden" name="_method" value="put" />
            </c:if> --%>
<%--             <input class="input-80-30 submit-button" type="submit" value='<spring:message code="submit" />' />
            <input class="input-80-30 reset-button" type="reset" value=<spring:message code="reset" /> /> --%>
          <%--  <c:if test='${location.id==null }'>sssss</c:if>
           <c:if test='${location.id=="" }'><c:out value="${location.id }"></c:out> </c:if> --%>
           <c:out value="${location.id }"></c:out>
            <input id="submitLocation" class="input-80-30 submit-button" type="button" value='<spring:message code="submit" />' />
            <input id="resetLocation" class="input-80-30 reset-button" type="reset" value=<spring:message code="reset" /> />
            </tr>
        </div>
        </table>
    </form>
            </td>
            
            </tr>
        </table>
    </div>
    
     <div id="dialog-confirm" title="Operation confirm">
   		<p id="confirm-message-body"></p>
 	</div>
 	<div id="dialog-warning" title="Warning">
  		<p id="warning-message-body"></p>
 	</div>
 	
    <input type="hidden" id="categoryFlag" value="1"/>
    <input type="hidden" id="language" value="${sessionScope.localeLanguage }">
    
    <%-- <div id="dialog-warning" title="<label id='label_Operation_Warning'></label>">
        <p id="warning-message-body"></p>
    </div> --%>
    
    <jsp:include page="/WEB-INF/page/common/footer.jsp"></jsp:include>
    
</body>
<script type="text/javascript" src="js/common/common.js"></script>
<script type="text/javascript" src="js/search/searchCommon.js"></script>
<script type="text/javascript" src="searchList/js/DataList.js"></script>
<script type="text/javascript" src="filterBox/js/filterBox.js" ></script>
<script type="text/javascript" src="js/common/selfDefineDialog.js"></script>
<script type="text/javascript" src="js/location/location.js"></script>
<script type="text/javascript" src="js/common/jquery.i18n.properties-1.0.9.js"></script>
<script type="text/javascript" src="dropDownList/dropDownList.js"></script>
<link rel="stylesheet" type="text/css" href="dropDownList/themes/dropDownList.css" />
<script type="text/javascript" src="js/common/selfDefineDialog.js"></script>
</html>