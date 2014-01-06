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
		        <spring:message code="audit.check.results"/>
            </span>
            <div> <a id="exportIconAll" ></a></div>
        </div>
        
        <table  id="tableTitleTemplate" >
	        <thead> 
	        	<tr>
	        		<th style='text-indent:-50px; text-align:right'><spring:message code='audit.id' /></th>
					<th ext-indent:-5px><spring:message code='audit.bar-code' /></th> 
					<th><spring:message code='audit.name' /></th>
					<th><spring:message code='audit.type' /></th>
				</tr>
			</thead>
		</table>
     
        <div class="dialog-content">
            <div id="auditFilePanel" class="content-panel-inventory-left">
                <div class="dialog-panel-title">
                    <label><spring:message code="audit.audit.file"/></label>
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
                    <a  id="auditView"><spring:message code="audit.view.more" /></a>
                </div>
            </div>
            
            <div id="inventoryFilePanel" class="content-panel-inventory-right">
              <div class="dialog-panel-title">
                    <label><spring:message code="audit.inventory.file" /></label>
                    <span><spring:message code="audit.inconsistent" /><strong id="iconsSize">(0)</strong></span>
                </div>
                <div id="inconsTableContent"></div>
                <div class="dialog-panel-shadow"></div>  
                <div class="dialog-panel-link">
                    <a href="#" id="iconsView"><spring:message code="audit.view.more" /></a>
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
        
    <jsp:include page="inventoryProcessbar.jsp"></jsp:include>
    <jsp:include page="/WEB-INF/page/common/footer.jsp"></jsp:include>
    <script type="text/javascript" src="<%=basePath%>/js/common/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/common/jquery-ui-1.8.18.custom.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/audit/inventoryDetails.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/audit/inventoryCommonOperation.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/common/jquery.form.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/common/jquery.dataTables.js"></script>
    <script type="text/javascript" src="js/common/selfDefineDialog.js"></script>
    
</body>
</html>
