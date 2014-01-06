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
<link rel="stylesheet" type="text/css" href="css/common/jquery-ui-1.8.18.custom.css">
<link rel="stylesheet" type="text/css" href="datepicker/css/datepicker.css">
<link rel="stylesheet" type="text/css" href="css/asset/assignAssetsDialog.css">
</head>
<body>
	<jsp:include page="../common/header.jsp"></jsp:include>
	<div id ="messageBar"></div>
    <div class="content">
    	<div class="dataList">
    		<div id="searchCondition">
			<input type="hidden" id="keyword_content" value="">
			<div id="searchInputTipDiv" class="inputTipDiv"><span id="label_KeywordPlaceholder"></span></div>
			<input id="keyword" class="input_txt" name="" type="text" value="">
			<div class="filterDiv filterDiv_common">
				<button class="filterBtn filter_no_dropDown">
				</button>
				<span class="existedFlag"></span>
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
			        <a class="reset" href="javascript:void(0);"><label id="label_SearchConditionReset"></label></a>
				</div>
			</div>
			<a id="searchButton" class="a_common_button green_button_thirty">
	        	<span class="left"></span>
	        	<span class="middle" ><label id="label_SearchButton"></label> </span>
	        	<span class="right"></span>
	        </a>
	        <div id="customizedViewButton">
		        <a id="customizedView" >Customized View</a>
		        <ul id="viewUlTemplate">
	                <li><a id="" class="existCustomizedView" ></a></li>
	            </ul>
		        <ul id="viewUl">
	                <li><a href="customizedView/goToNewCustomizedView">Create View</a></li>
	                <li id="viewLine"><a href="customizedView/findCustomizedViewByUserForManagement">Management View </a></li>
	            </ul>
            </div>
	        <div class="operation_assets_list">
		        <a class="a_operations_assets_list">Operation</a>
	        	<ul>
	        		<li><label id="assignAssetsButton">Assign</label></li>
	        		<li><label id="returnAssetsToCustomer">ReturnToCustomer</label></li>
	        		<li><label id="changeToFixed">ChangeToFixedAsset</label></li>
	        		<li><label id="changeToNonFixed">ChangeToNonFixedAsset</label></li>
	        		<li><label id="addToAudit">AddToAudit</label></li>
	        	</ul>
	        </div>
	        
	    </div>
    	</div>
    </div>
    <div id="dialog_assign">
	      <div>
	          <span>*</span>
	          <label id="label_CustomerName">Customer</label>
	          <input id="customerName" type="text" name="customerName" placeholder="Please enter customer name"/>
	          <input type="hidden" id="customerCode" />
	      </div>
	      <div>
	          <label id="label_ProjectName">Project</label>
	          <input id="projectName" type="text"  name="projectName" placeholder="Please enter project name"/>
	          <input type="hidden" id="projectCode" />
	          
	      </div>
	      <div>
	          <label id="label_UserName">User</label>
	          <input id="userName" type="text" name="userName" placeholder="Please enter receiver name"/>
	          <input type="hidden" id="userId">
	      </div>
	          <a id="confirm_assign" class="a_common_button green_button_thirty">
	        	<span class="left"></span>
	        	<span class="middle" ><label id="label_ConfirmAssign">Confirm</label> </span>
	        	<span class="right"></span>
	          </a>
	          <p>
		          <input type="button" id="cancel_assign" value="Cancel">
	          <p>
    </div>
    
     <div id="dialog-confirm" title="Operation confirm">
   		<p id="confirm-message-body"></p>
 	</div>
 	<div id="dialog-warning" title="Warning">
  		<p id="warning-message-body"></p>
 	</div>
 	
    <input type="hidden" id="categoryFlag" value="1"/>
    <input type="hidden" id="language" value="${sessionScope.localeLanguage }">
    <jsp:include page="/WEB-INF/page/common/footer.jsp"></jsp:include>
</body>
<script type="text/javascript" src="js/common/common.js"></script>
<script type="text/javascript" src="js/search/searchCommon.js"></script>
<script type="text/javascript" src="searchList/js/DataList.js"></script>
<script type="text/javascript" src="js/asset/searchAssetList.js" ></script>
<script type="text/javascript" src="filterBox/js/filterBox.js" ></script>
<script type="text/javascript" src="js/search/searchCustomizedView.js" ></script>
<script type="text/javascript" src="js/common/selfDefineDialog.js"></script>
</html>