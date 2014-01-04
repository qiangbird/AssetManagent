<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%> 
<%@ page language="java" contentType="text/html; charset=utf-8" 
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd"> -->
<html>
<head>
	<base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" type="text/css" href="css/common/jquery-ui-1.8.18.custom.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/css/common/tip-green/tip-green.css">
    <link rel="stylesheet" href="<%=basePath%>/css/common/dialog.css" type="text/css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/jquery.dataTables.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/audit/inventoryDetails.css" />
    <title></title>
</head>

<body>
 <jsp:include page="../home/head.jsp" />
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
        <div id="hen" style="height: 20px;width:100px;display: none; border-color:red">Loading...</div>
        <div id="dataTables_scrollBody">
	     
        </div>
    <div class="dialogBody">
    	    <div class="shady"></div>
        <div class="process-panel">
            <label id="fielLabel">${fileName}</label>
            <div class="process-content">
                <!-- <div class="process-bar">
                    <div class="percentResult"></div>
                    <div class="process-status-left"></div>
                    <div class="process-status-center"></div>
                    <div class="process-status-right"></div>
                </div> -->
                <div class="process-bar">
                    <div class="percentResult"></div>
                </div>
                
                <div class="process-operation-button">
                <c:if test="${operator == null}">
                	<form action="auditFile/checkInventory" enctype="multipart/form-data" method="post">
	                	<input id="fileUpload" type="file" name="file" id="file" onchange="checkInventory(this, 'inventoryDetails');"></input>
	                	<input type="hidden" name="auditFileName" value="${fileName}"></input>
                		<input id="uploadBtn" type="button" value="Upload" Onclick="file.click();"></input>
                	</form>
                	<div class="hidden-tool">
                		<input type="button" value="Done" onclick="upToDone(this);"></input>
                		<input type="button" value="Delete" onclick="removeAuditFile(this);" ></input>
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
		        checkResults
            </span>
            <div> <a id="exportIconAll" ></a></div>
        </div>
     
        <div class="dialog-content">
            <div id="auditFilePanel" class="content-panel-inventory-left">
                <div class="dialog-panel-title">
                    <h6>AuditFile</h6>
                    <span>
                        <label class="a">
                            <a id="aa" onclick="showAudited(this);">audited</a><strong>(0)</strong>
                        </label>
                        <label class="u">
                            <a onclick="showUnAudited(this);">unaudited</a><strong>(0)</strong>
                        </label>
                    </span>    
                </div>
                <div class="dialog-panel-content" id="tableContent"> </div>
                <div class="dialog-panel-shadow"></div>
                <div class="dialog-panel-link">
                    <a  id="auditView">viewMore</a>
                </div>
            </div>
            
            <div id="inventoryFilePanel" class="content-panel-inventory-right">
              <div class="dialog-panel-title">
                    <h6>InventoryFile</h6>
                    <span>inconsistent<strong id="iconsSize">(0)</strong></span>
                </div>
                <div id="inconsTableContent"></div>
                <div class="dialog-panel-shadow"></div>  
                <div class="dialog-panel-link">
                    <a href="#" id="iconsView">viewMore</a>
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
        
    <jsp:include page="inventoryChecking.jsp"></jsp:include>
    
    <script type="text/javascript" src="<%=basePath%>/js/common/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/common/jquery-ui-1.8.18.custom.min.js"></script>
    <%-- <script type="text/javascript" src="<%=basePath%>js/common/dialog.js"></script> --%>
    <script type="text/javascript" src="<%=basePath%>/js/audit/inventoryDetails.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/audit/inventoryCommonOperation.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/common/jquery.form.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/common/jquery.dataTables.js"></script>
    <script type="text/javascript" src="js/common/selfDefineDialog.js"></script>
    
</body>
</html>
