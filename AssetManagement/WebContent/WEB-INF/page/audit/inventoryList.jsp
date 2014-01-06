<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%> 
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Asset Audit List</title>
<link rel="stylesheet" type="text/css" href="css/audit/checkInventory.css" />
<link rel="stylesheet" type="text/css" href="css/base/base.css">
<link rel="stylesheet" type="text/css" href="css/base/resetCss.css">
<link rel="stylesheet" type="text/css" href="jquery.poshytip/css/tip-green.css">
<link rel="stylesheet" type="text/css" href="css/common/jquery-ui-1.8.18.custom.css">
<link rel="stylesheet" type="text/css" href="datepicker/css/datepicker.css">
</head>
<body>
<jsp:include page="../common/header.jsp"></jsp:include>
<div id="body" >
	<div id="main">
		<div class="up-shadow"></div>
		<div class="left-control-panel">
			<div id="processBtn" class="left-control">
				<div class="processBtn-control-blank"></div>
				<span>Processing
					<label class="process-count"></label>
				</span>
			</div>
			<div id="doneBtn" class="left-control">
				<div class="processBtn-control-blank"></div>
				<span>Done
					<label class="done-count"></label>
				</span>
			</div>
		</div>
		<div id="processFiles" class="right-content-panel">
	   		<div class="content-title">Audit File
	   			<label id="content-title-status"></label>
	   		</div>
	          <div class="operation-panel">
	          		<label class="audit-file-no-data"></label>
	            	<table id="dataTable" class="table">
	            		<tr class="audit-process-tr process-0" style="display: none;">
	            			<td>
	            				<div class="process-panel">
	            					<a></a>
	            					<div class="process-content">
	            						<div class="process-bar">
	            							<div class="percentResult"></div>
	            						</div>
	            						<div class="process-operation-button">
		            						<form action="auditFile/checkInventory" enctype="multipart/form-data" method="post">
							                	<input id="fileUpload" type="file" name="file" id="file" onchange="initUpload(this);"></input>
							                	<input type="hidden" name="auditFileName" id="auditFileName" value=""></input>
						                		<input id="uploadBtn" type="button" value="Upload" Onclick="file.click();"></input>
						                	</form>
	            							<!-- <input id="uploadBtn" type="button" value="Upload" /> -->
	            							<div class="hidden-tool">
		            							<input type="button" value="Done"  class="check-done-button"/>
		            							<input type="button" value="Delete"  class="check-delete-button"/>
	            							</div>
	            						</div>
	            					</div>
	            				</div>
	            			</td>
	            		</tr>
	            		<tr class="audit-done-tr done-0" style="display: none;">
	            			<td>
	            				<div class="process-panel">
	            					<a></a>
	            					<div class="process-content">
	            						<div class="process-bar">
	            							<div class="percentResult"></div>
	            						</div>
	            						<div class="done-operation-info">
	            							<label class="done-operation-time"></label>
	            							<label class="operator-title">Operator</label>
	            							<label class="done-operator"></label>
	            						</div>
	            					</div>
	            				</div>
	            			</td>
	            		</tr>
	            	</table>
	          </div>
	    </div>
	    <div class="down-shadow"></div>
	</div>
	
	<div id="dialog-confirm" title="Operation confirm">
		   <p id="confirm-message-body"></p>
		 </div>
		
		 <div id="dialog-warning" title="Warning">
		  <p id="warning-message-body"></p>
		 </div>
</div>

<jsp:include page="inventoryProcessbar.jsp"></jsp:include>
<jsp:include page="/WEB-INF/page/common/footer.jsp"></jsp:include>
<script type="text/javascript" src="js/audit/checkInventory.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/audit/inventoryCommonOperation.js"></script>
<script type="text/javascript" src="js/common/selfDefineDialog.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/common/jquery.form.js"></script>
</body>
</html>