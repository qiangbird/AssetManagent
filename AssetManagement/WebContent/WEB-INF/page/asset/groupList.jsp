<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<base href="<%=basePath%>">
<link rel="stylesheet" href="css/common/autocomplete.css"
	type="text/css">
<link rel="stylesheet" type="text/css" href="css/common/jquery-ui-1.8.18.custom.css">
<link rel="stylesheet" href="css/common/jquery-ui.css" type="text/css">
<link rel="stylesheet" href="css/common/tip-green/tip-green.css" type="text/css">
<link rel="stylesheet" type="text/css" href="css/base/base.css">
<link rel="stylesheet" type="text/css" href="css/base/resetCss.css">
<link rel="stylesheet" type="text/css" href="jquery.poshytip/css/tip-green.css">
<link rel="stylesheet" type="text/css" href="searchList/css/dataList.css">
<link rel="stylesheet" type="text/css" href="css/common/commonList.css">
<link rel="stylesheet" type="text/css" href="filterBox/css/filterBox.css">
<link rel="stylesheet" type="text/css" href="css/search/searchCommon.css">
<link rel="stylesheet" type="text/css" href="datepicker/css/datepicker.css">
<link rel="stylesheet" href="css/asset/groupManagement.css"type="text/css">
<script type="text/javascript" src="js/common/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="js/common/jquery-ui-1.8.18.custom.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
	<jsp:include page="../common/header.jsp" />
	<div id="bodyMinHight">
    <div class="content">
    	<div class="dataList">
    		<div id="searchCondition">
			<input type="hidden" id="keyword_content" value="">
			<div id="searchInputTipDiv" class="inputTipDiv"><span id="label_KeywordPlaceholder"></span></div>
			<input id="keyword" class="input_txt" name="" type="text" value="">
			<a id="searchButton" class="a_common_button green_button_thirty">
	        	<span class="left"></span>
	        	<span class="middle" ><spring:message code="search" /></span>
	        	<span class="right"></span>
	        </a>
	        <a id="addButton"><spring:message code="add" /></a>
	    </div>
    	</div>
</div>
</div>

	 <div class="addGroup"> 
       <tr>
        <td valign="top">
         <form action="group/update" method="post" id="dialog">
        <table id="group_table" cellspacing="20">
        
            <div class="create-table">
                <div>
                    <tr>
                        <td><spring:message code="group.name" /></td>
                        <td>
                        <input id="groupId" type="hidden" name="id" value="${customerGroup.id }"/>
                        <input id="groupName" type="text" name="groupName" value="${customerGroup.groupName }"/>
                    <td />
                    </tr>
                </div>
                <div>
                    <tr>
                        <td><spring:message code="group.description" /></td>
                        <td><input id="description" type="text" name="description" value="${customerGroup.description }"/></td>
                    </tr>
                </div>
                <div>
                    <tr>
                        <td><spring:message code="group.process.type" /></td>
                        <td>
                       <select name="processType" id="processType">
                        <c:if test="${customerGroup==null}">
                        <c:forEach var="processType" items="${processTypeList }">
                         <option value="${processType }">${processType }</option>
                         </c:forEach>
                        </c:if>
                        <c:if test="${customerGroup!=null}">
                        <c:forEach var="processType" items="${processTypeList }">
                        <c:if test="${customerGroup.processType !='SHARED'}">
                            <option value="${processType }" selected="selected">${processType }</option>
                        </c:if>
                          </c:forEach>
                          </c:if>
                        </select>
                    </tr>
                </div>
                <div>
                    <tr>
                        <td><spring:message code="customer" /></td>
                        <td>
                          <input id="customers" type="text" name="customerCodes"/>
                          <input type="hidden" id="customerName"/>
                          <input type="hidden" id="customerCode"/>
                        </td>
                    </tr>
                </div>
            </div>
		</table>
		<div class="submit-div">
                <input id="submitGroup" class="input-80-30 submit-button" type="button" value="<spring:message code='save' />" /> 
                <input id="resetGroup" class="input-80-30 reset-button" type="button" value="<spring:message code='reset' />" />
        </div>
		</form>
		</td>
		</tr> 
	 </div>
	 
	 <input type="hidden" id="language" value="${sessionScope.localeLanguage }">
	 <div id="dialog-confirm" title="Operation confirm">
   		<p id="confirm-message-body"></p>
 	</div>
 	<div id="dialog-warning" title="Warning">
  		<p id="warning-message-body"></p>
 	</div>
 	
<jsp:include page="/WEB-INF/page/common/footer.jsp"></jsp:include>
<script type="text/javascript" src="js/user/userInfoTips.js"></script>
<script type="text/javascript" src="js/asset/groupManagement.js"></script> 
<script type="text/javascript" src="js/common/autocomplete.js"></script>
<script type="text/javascript" src="jquery.poshytip/js/jquery.poshytip.js"></script>
<script type="text/javascript" src="js/common/common.js"></script>
<script type="text/javascript" src="js/search/searchCommon.js"></script>
<script type="text/javascript" src="searchList/js/DataList.js"></script>
<script type="text/javascript" src="filterBox/js/filterBox.js" ></script>
<script type="text/javascript" src="js/common/jquery.i18n.properties-1.0.9.js"></script>
<script type="text/javascript" src="dropDownList/dropDownList.js"></script>
<link rel="stylesheet" type="text/css" href="dropDownList/themes/dropDownList.css" />
<script type="text/javascript" src="js/common/autocomplete.js"></script>
<script type="text/javascript" src="js/common/selfDefineDialog.js"></script>
</body>
</html>