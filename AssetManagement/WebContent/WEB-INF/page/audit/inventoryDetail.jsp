<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%> 
<%@ page language="java" contentType="text/html; charset=utf-8" 
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd"> -->
<html>
<head>
	<base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" type="text/css" href="css/common/jquery-ui-1.8.18.custom.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/css/common/tip-green/tip-green.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/jquery.dataTables.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/audit/inventoryDetails.css" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/asset/assignAssetsDialog.css" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>datepicker/css/datepicker.css" />
    <link rel="stylesheet" type="text/css" href="css/base/base.css">
	<link rel="stylesheet" type="text/css" href="css/base/resetCss.css">
	<link rel="stylesheet" type="text/css" href="jquery.poshytip/css/tip-green.css">
	<link rel="stylesheet" type="text/css" href="searchList/css/dataList.css">
	<link rel="stylesheet" type="text/css" href="css/common/commonList.css">
	<link rel="stylesheet" type="text/css" href="filterBox/css/filterBox.css">
	<link rel="stylesheet" type="text/css" href="css/search/searchCommon.css">
    <title></title>
</head>

<body>
 <jsp:include page="../common/header.jsp" />
<div id="inventoryDetails">
<input type="hidden" name="fileName" id="fileName" value="${fileName}"/>
<input type="hidden" name="percentage" id="percentage" value="${percentNum}"/>
<input type="hidden" name="operator" id="operator" value="${operator}"/>
<input type="hidden" name="operationTime" id="operationTime" value="${operationTime}"/>

<!--         <div id="blank">
            <a href="dashBoard">home</a>
            <b id="father">&gt;</b>
            <a href="checkInventory">checkInventory</a><b>&gt;</b><span>fileName</span>
        </div> -->
        <div id="hen" style="height: 20px;width:100px;display: none; border-color:red"><spring:message code="audit.loading..."/>Loading...</div>
        <div id="dataTables_scrollBody">
	     
        </div>
    <div class="dialogBody">
    	    <div class="shady"></div>
        <div class="process-panel">
            <label id="fielLabel">${fileName}</label>
            <div class="process-content">
                <div class="process-bar">
                    <div class="percentResult"></div>
                </div>
                
                <div class="process-operation-button">
                <c:if test="${operator == null}">
                	<form action="auditFile/checkInventory" enctype="multipart/form-data" method="post">
	                	<input id="fileUpload" type="file" name="file" id="file" onchange="checkInventory(this, 'inventoryDetails');"></input>
	                	<input type="hidden" name="auditFileName" value="${fileName}"></input>
                		<input id="uploadBtn" type="button" value=<spring:message code="audit.upload" /> Onclick="file.click();"></input>
                	</form>
                	<div class="hidden-tool">
                		<input type="button" value=<spring:message code="audit.done" /> onclick="upToDone(this);"></input>
                		<input type="button" value=<spring:message code="audit.delete" /> onclick="removeAuditFile(this);" ></input>
                	</div>
                </c:if>
                <c:if test="${operator != null}">
                <span>${fileName}</span>
                <strong>${operator}</strong>
                </c:if> 
                </div>
            </div>    
        </div>
		<div class="dialog-blank">
            <span>
		        <spring:message code="audit.checkResults"/>
            </span>
            <div> <a id="exportIconAll" ></a></div>
        </div>
        
        <table  id="tableTitleTemplate" >
	        <thead> 
	        	<tr>
	        		<th style='text-indent:-50px; text-align:right'><spring:message code='audit.id' /></th>
					<th ext-indent:-5px><spring:message code='audit.barcode' /></th> 
					<th><spring:message code='audit.name' /></th>
					<th><spring:message code='audit.type' /></th>
				</tr>
			</thead>
		</table>
     
        <div class="dialog-content">
            <div id="auditFilePanel" class="content-panel-inventory-left">
                <div class="dialog-panel-title">
                    <label><spring:message code="audit.auditFile"/></label>
                    <span>
                        <label class="a">
                            <a id="aa" onclick="showAudited(this);"><spring:message code="audit.audited" /></a><strong>(0)</strong>
                        </label>
                        <label class="u">
                            <a onclick="showUnAudited(this);"><spring:message code="audit.unaudited" /></a><strong>(0)</strong>
                        </label>
                    </span>    
                </div>
                <div class="dialog-panel-content" id="tableContent"> </div>
                <div class="dialog-panel-shadow"></div>
                <div class="dialog-panel-link">
                    <a  id="auditView"><spring:message code="audit.viewMore" /></a>
                </div>
            </div>
            
            <div id="inventoryFilePanel" class="content-panel-inventory-right">
              <div class="dialog-panel-title">
                    <label><spring:message code="audit.inventoryFile" /></label>
                    <span><spring:message code="audit.inconsistent" /><strong id="iconsSize">(0)</strong></span>
                </div>
                <div id="inconsTableContent"></div>
                <div class="dialog-panel-shadow"></div>  
                <div class="dialog-panel-link">
                    <a href="#" id="iconsView"><spring:message code="audit.viewMore" /></a>
                </div>
            </div>
      </div>
        </div>
       <!--  <div id="viewMoreDetails" style="min-height: 540px">
   		</div> -->
        </div>
         <div id="dialog-confirm" title="Operation confirm">
		   <p id="confirm-message-body"></p>
		 </div>
		
		 <div id="dialog-warning" title="Warning">
		  <p id="warning-message-body"></p>
		 </div>
	<div class="viewInventoryAsset">
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
						<div class="condition_title"><label><spring:message code="searchBy" /></label></div>
						<div class="condition_optional" id="searchFields">
							<p><input type="checkBox" name="field" class="checked_all" value="all"/><label><spring:message code="checkAll"/></label></p>
							<p><input type="checkBox" name="field" value="assetId" /><label><spring:message code="asset.assetId"/></label></p>
							<p><input type="checkBox" name="field" value="assetName" /><label><spring:message code="asset.name"/></label></p>
			                <p><input type="checkBox" name="field" value="user.userName"/><label><spring:message code="asset.user"/></label></p>
			                <p><input type="checkBox" name="field" value="project.projectName"/><label><spring:message code="asset.project"/></label></p>
			                <p><input type="checkBox" name="field" value="customer.customerName"/><label><spring:message code="asset.customer"/></label></p>
			                <p><input type="checkBox" name="field" value="poNo"/><label><spring:message code="asset.poNo" /></label></p>
			                <p><input type="checkBox" name="field" value="barCode"/><label><spring:message code="asset.barcode"/></label></p>
			        	</div>
					</div>
					<div class="single_condition">
						<div class="condition_title"><label><spring:message code="asset.type"/></label></div>
						<div class="condition_optional" id="assetType">
							<p><input type="checkBox" name="field" class="checked_all" value="all"/><label><spring:message code="checkAll"/></label></p>
							<p><input type="checkBox" name="field" value="MACHINE" /><label><spring:message code="asset.machine"/></label></p>
							<p><input type="checkBox" name="field" value="MONITOR" /><label><spring:message code="asset.monitor"/></label></p>
							<p><input type="checkBox" name="field" value="DEVICE" /><label><spring:message code="asset.device"/></label></p>
							<p><input type="checkBox" name="field" value="SOFTWARE" /><label><spring:message code="asset.software"/></label></p>
							<p><input type="checkBox" name="field" value="OTHERASSETS" /><label><spring:message code="asset.otherAssets"/></label></p>
			            </div>
			        </div>
			        <div class="single_condition">
						<div class="condition_title"><label><spring:message code="asset.status"/></label></div>
						<div class="condition_optional" id="assetStatus">
							<p><input type="checkBox" name="field" class="checked_all" value="all"/><label><spring:message code="checkAll"/></label></p>
							<p><input type="checkBox" name="field" value="AVAILABLE" /><label><spring:message code="asset.available"/></label></p>
							<p><input type="checkBox" name="field" value="IN_USE" /><label><spring:message code="asset.inUse"/></label></p>
							<p><input type="checkBox" name="field" value="IDLE" /><label><spring:message code="asset.idle"/></label></p>
							<p><input type="checkBox" name="field" value="RETURNED" /><label><spring:message code="asset.returned"/></label></p>
			            </div>
			        </div>
			        <div class="single_condition">
						<div class="condition_title"><spring:message code="asset.checkInDate"/></div>
						<div class="condition_optional" id="checkInTime">
							<p class="dateP"><input id="fromTime" class="dateInput" type="text" name="field" /></p>
                            <span class="dateLine">-</span>
                            <p class="dateP"><input id="toTime" class="dateInput" type="text" name="field" /></p>
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
	        <div id="customizedViewButton">
		        <a id="customizedView" ><spring:message code="customizedView"/></a>
		        <ul id="viewUlTemplate">
	                <li><a id="" class="existCustomizedView" ></a></li>
	            </ul>
		        <ul id="viewUl">
	                <li><a href="customizedView/goToNewCustomizedView"><spring:message code="createView"/></a></li>
	                <li id="viewLine"><a href="customizedView/findCustomizedViewByUserForManagement"><spring:message code="manageView"/></a></li>
	            </ul>
            </div>
	    </div>
    </div>
    </div>
    <input type="hidden" id="locale" value="${sessionScope.localeLanguage }">
    
    <jsp:include page="inventoryProcessbar.jsp"></jsp:include>
    <jsp:include page="/WEB-INF/page/common/footer.jsp"></jsp:include>
    
    <script type="text/javascript" src="<%=basePath%>/js/common/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/common/jquery-ui-1.8.18.custom.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/audit/inventoryDetails.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/audit/inventoryCommonOperation.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/common/jquery.form.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/common/jquery.dataTables.js"></script>
	<script type="text/javascript" src="filterBox/js/filterBox.js" ></script>
	<script type="text/javascript" src="jquery.poshytip/js/jquery.poshytip.js" ></script>
<!-- all assets js files  ---------------------------------------- -->    
    <script type="text/javascript" src="js/common/common.js"></script>
<script type="text/javascript" src="js/search/searchCommon.js"></script>
<script type="text/javascript" src="searchList/js/DataList.js"></script>
<script type="text/javascript" src="js/audit/inventoryAsset.js" ></script>
<script type="text/javascript" src="js/search/searchCustomizedView.js" ></script>
</body>
</html>
