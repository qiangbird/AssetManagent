<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%> 
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<html>
<head>
<base href="<%=basePath%>">
<link rel="stylesheet" href="css/common/autocomplete.css" type="text/css">
<link rel="stylesheet" href="css/common/jquery-ui.css" type="text/css">
<link rel="stylesheet" href="jquery.poshytip/css/tip-green.css" type="text/css">
<link rel="stylesheet" href="css/user/roleList.css" type="text/css">
<link rel="stylesheet" type="text/css" href="css/base/base.css">
<link rel="stylesheet" type="text/css" href="css/base/resetCss.css">
<link rel="stylesheet" type="text/css" href="searchList/css/dataList.css">
<link rel="stylesheet" type="text/css" href="css/common/commonList.css">
<link rel="stylesheet" type="text/css" href="filterBox/css/filterBox.css">
<link rel="stylesheet" type="text/css" href="css/search/searchCommon.css">

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<jsp:include page="../common/header.jsp" />
<div id="blank">
   <a href="home"><spring:message code="navigator.home"></spring:message></a>
   <b>&gt;</b>
   <a href="user/roleList"><spring:message code="navigator.system"></spring:message></a>
   <b>&gt;</b>
   <span><spring:message code="navigator.roleList"></spring:message></span>
 </div>
<div id="bodyMinHight">
    <div class="content">
	    <div class="roleAddContent">
	        <div id="autoText">
	            <input id="employeeName" name="employeeName" type="text" value="" />
	        </div>
	        <div id="checkboxOperate">
	            <input type="hidden" id="itValue" value="false" >
	            <input type="hidden" id="adminValue" value="false" />
	            <a id="it" class="roleCheckBoxOff" ><span><spring:message code="user.IT" /></span></a>
	            <a id="admin" class="roleCheckBoxOff" ><span><spring:message code="user.system.admin" /></span></a>
	        </div>
	        <div class="addOperateButton">
	            <input type="button" value=<spring:message code="add" />  id="addButton"></input>
	        </div>
	      </div>
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
                            <div class="condition_title"><label><spring:message code="user.role"/></label></div>
                            <div class="condition_optional" id="userRole">
                                <p><input type="checkBox" name="field" class="checked_all" value="all"/><label><spring:message code="checkAll"/></label></p>
                                <p><input id="role_it" type="checkBox" name="field" value="it" /><label><spring:message code="user.IT"/></label></p>
                                <p><input id="role_systemAdmin" type="checkBox" name="field" value="system_admin" /><label><spring:message code="user.system.admin"/></label></p>
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
           </div>
        </div>
    <div class="saveOperateButton">
        <input type="button" value=<spring:message code="save" />  id="saveButton"></input>
        <input type="button" value=<spring:message code="cancel" />  id="cancelButton"></input> 
    </div>
    </div>
</div>
<jsp:include page="/WEB-INF/page/common/footer.jsp"></jsp:include>

<script type="text/javascript" src="js/common/validation.js"></script>
<script type="text/javascript" src="jquery.poshytip/js/jquery.poshytip.js"></script>
<script type="text/javascript" src="js/common/common.js"></script>
<script type="text/javascript" src="js/user/userInfoTips.js"></script>
<script type="text/javascript" src="js/search/searchCommon.js"></script>
<script type="text/javascript" src="searchList/js/DataList.js"></script>
<script type="text/javascript" src="js/user/roleList.js"></script>
<script type="text/javascript" src="js/common/autocomplete.js"></script>
<script type="text/javascript" src="filterBox/js/filterBox.js" ></script>

</body>
</html>