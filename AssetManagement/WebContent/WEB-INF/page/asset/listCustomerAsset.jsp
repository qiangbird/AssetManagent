<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<link rel="stylesheet" type="text/css" href="css/asset/customerAsset.css">
<link rel="stylesheet" type="text/css" href="css/common/jquery-ui-1.8.18.custom.css">
<link rel="stylesheet" type="text/css" href="datepicker/css/datepicker.css">
<link rel="stylesheet" type="text/css" href="css/asset/isAssign.css" />
</head>
<body>
    <jsp:include page="../home/head.jsp"></jsp:include>
    
    
    <input type="hidden" id="customerCode" value="${customer.customerCode }">
    <input type="hidden" id="userRole" value="${sessionScope.userRole }">
    <input type="hidden" id="userCode" value="${sessionScope.userVo.employeeId }">
    <div class="content">
        <div class="dataList">
            <div id="searchCondition">
            <input type="hidden" id="keyword_content" value="">
            <div id="searchInputTipDiv" class="inputTipDiv"><span></span></div>
            <input id="keyword" class="input_txt" name="" type="text" value="">
            <div class="filterDiv filterDiv_common">
                <button class="filterBtn filter_no_dropDown">
                </button>
                <div class="filterBox">
                    <div class="single_condition">
                        <div class="condition_title"><label id="label_SearchBy"></label></div>
                        <div class="condition_optional" id="searchFields">
                            <p><input type="checkBox" name="field" class="checked_all" value="all"/><label id="label_CheckedAllFields"></label></p>
                            <p><input type="checkBox" name="field" value="assetId"/><label id="label_AssetId"></label></p>
                            <p><input type="checkBox" name="field" value="assetName"/><label id="label_AssetName"></label></p>
                            <p><input type="checkBox" name="field" value="user.userName"/><label id="label_User"></label></p>
                            <p><input type="checkBox" name="field" value="project.projectName"/><label id="label_Project"></label></p>
                            <p><input type="checkBox" name="field" value="customer.customerName"/><label id="label_Customer"></label></p>
                            <p><input type="checkBox" name="field" value="poNo"/><label id="label_PoNo"></label></p>
                            <p><input type="checkBox" name="field" value="barCode"/><label id="label_BarCode"></label></p>
                        </div>
                    </div>
                    <div class="single_condition">
                        <div class="condition_title"><label id="label_AssetType"></label></div>
                        <div class="condition_optional" id="assetType">
                            <p><input type="checkBox" name="field" class="checked_all" value="all"/><label id="label_CheckedAllTypes"></label></p>
                            <p><input type="checkBox" name="field" value="MACHINE" /><label id="label_Machine"></label></p>
                            <p><input type="checkBox" name="field" value="MONITOR" /><label id="label_Monitor"></label></p>
                            <p><input type="checkBox" name="field" value="DEVICE" /><label id="label_Device"></label></p>
                            <p><input type="checkBox" name="field" value="SOFTWARE" /><label id="label_Software"></label></p>
                            <p><input type="checkBox" name="field" value="OTHERASSETS" /><label id="label_OtherAssets"></label></p>
                        </div>
                    </div>
                    <div class="single_condition">
                        <div class="condition_title"><label id="label_AssetStatus"></label></div>
                        <div class="condition_optional" id="assetStatus">
                            <p><input type="checkBox" name="field" class="checked_all" value="all"/><label id="label_CheckedAllStatus"></label></p>
                            <p><input type="checkBox" name="field" value="AVAILABLE" /><label id="label_Available"></label></p>
                            <p><input type="checkBox" name="field" value="IN_USE" /><label id="label_InUse"></label></p>
                            <p><input type="checkBox" name="field" value="IDLE" /><label id="label_Idle"></label></p>
                            <p><input type="checkBox" name="field" value="RETURNED" /><label id="label_Returned"></label></p>
                        </div>
                    </div>
                    <div class="single_condition">
                        <div class="condition_title"><label id="label_CheckInTime"></label></div>
                        <div class="condition_optional" id="checkInTime">
                            <p class="dateP"><input id="fromTime" class="dateInput" type="text" name="field" /></p>
                            <span class="dateLine">-</span>
                            <p class="dateP"><input id="toTime" class="dateInput" type="text" name="field" /></p>
                        </div>
                    </div>
                    <a class="reset" href="javascript:void(0);">Reset</a>
                </div>
            </div>
            <a id="searchButton" class="a_common_button green_button_thirty">
                <span class="left"></span>
                <span class="middle" ><label id="label_SearchButton"></label> </span>
                <span class="right"></span>
            </a>
            
            <div class="operation_assets_list">
                <a class="a_operations_assets_list">Operation</a>
                <ul>
                <c:choose>
                <c:when test="${sessionScope.userRole == 'Manager'}">
                    <c:if test="${requestScope.customer.customerGroup.processType == 'SHARED' }">
                    <li id="takeOver" value=""><a>Take over</a></li>
                    <li id="returnToProject" value="AVAILABLE"><a>Return To Project</a></li>
                    </c:if>
                    <li id="assgin"><a>Assign</a></li>
                    <li id="returnToIT" value="RETURNING_TO_IT"><a>Return to IT</a></li>
                </c:when>
                <c:otherwise>
                    <li id="takeOver"><a>Take over</a></li>
                    <li id="returnToProject" value="RETURNING_TO_IT"><a>Return To Project</a></li>
                </c:otherwise>
                </c:choose>
                </ul>
            </div>
            
            
        </div>
        </div>
    </div>
    <div class="assetOperation">
    <span class="customerAssetOperation"></span>
    </div>
    <input type="hidden" id="categoryFlag" value="1"/>
    <input type="hidden" id="localeCode" name="localeCode" value="${sessionScope.i18n }">
    
     <table>
            <tr>
            
            <td valign="top">
      <form action="customerAsset/assginAssts" method="post" id="dialog">
         <table  id="assginTable">
        <div class="create-table">
            <input type="hidden" name="_method" value="put">
            <input type="hidden" name="customerCode" value="${customer.customerCode }"/>
            <input type="hidden" name="ids" id="ids"/>
            <div>
                <tr><td>Project</td>
                <td>
                <select id="DropList" class="dropDownSelect" name="projectCode">
                <c:forEach var="project" items="${projectList }">
                <option value="${project.projectCode}">${project.projectName }</option>
                </c:forEach>
                </select>
                </td>
                </tr>
            </div>
            <div>
                <tr><td>User</td><td><input type="text" name="userName" id="user"/>
                <input type="hidden" name="assetUserCode" id="assetUserCode">
                </td></tr>
            </div>
        
        <div class="submit-div">
            <input class="input-80-30 submit-button" type="submit" value="submit" />
            <input class="input-80-30 reset-button" type="reset" value="Reset" />
            </tr>
        </div>
        </div>
        </table>
    </form>
            </td>
            </tr>
        </table>
        
	<div id="dialog-confirm" title="Operation confirm">
	  <p id="confirm-message-body"></p>
	</div>
	<div id="dialog-warning" title="Warning">
		<p id="warning-message-body"></p>
	</div>
	
</body>
<script type="text/javascript" src="js/common/common.js"></script>
<script type="text/javascript" src="js/search/searchCommon.js"></script>
<script type="text/javascript" src="searchList/js/DataList.js"></script>
<script type="text/javascript" src="js/asset/searchCustomerAssetList.js" ></script>
<script type="text/javascript" src="filterBox/js/filterBox.js" ></script>
<script type="text/javascript" src="dropDownList/dropDownList.js"></script>
<script type="text/javascript" src="js/common/selfDefineDialog.js"></script>
<script type="text/javascript" src="js/common/json2.js"></script>
<link rel="stylesheet" type="text/css" href="dropDownList/themes/dropDownList.css" />
</html>