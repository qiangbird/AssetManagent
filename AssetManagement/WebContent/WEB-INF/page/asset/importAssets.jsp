<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%> 
<%@ page language="java" contentType="text/html; charset=utf-8" 
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<html>
<head>
	<base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title></title>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/asset/importAssets.css" />
    
</head>
<body>
        <jsp:include page="/WEB-INF/page/common/header.jsp" />
<div class="shady"></div>
    <div id="page">
        <!-- <div id="blank">
            <a href="dashBoard">home</a>
            <b>&gt;</b>
            <span>importAssets</span>
        </div> -->
        <div id="body">
           <div id="main">
               <div class="panel-shadow"></div>
               <div class="operate-panel">
                   <div class="tips">
                       <p>
		                   importNote
                       </p>
                   </div>
                   <div class="process-panel pro-first">
                       <strong>importType: </strong>
                       <p class="create-p">
		                   <span id="create"></span>
		                   <label>importCreate</label>
                       </p>
                       <p class="update-p">
		                   <span id="update"></span>
		                   <label>importUpdate</label>
	                   </p>
                   </div>
                   <div class="process-panel pro-second">
                       <strong>importFile: </strong>
                       <form id="importForm" action="asset/upload" enctype="multipart/form-data" method="post">
                           <input type="hidden" id="flag" name="flag" value="true" />
	                       <input type="file"  class="import-file" name="file" onchange="uploadFile(this);" />
	                   </form>    
                       <input type="text" class="import-text" readonly="readonly" />
                       <!--<div class="blank-span"></div>
                       --><input type="button" class="import-button upload-button" value="upload"/>
                       <input type="button" class="import-button start-button" value="start" disabled="disabled" />
                       <!--<span id="tips" onmouseover="showHelpTips()" onmouseout="hideHelpTips()"></span>
                       <div id="helpTips">
                        importNote"/>
                       </div>-->
                   </div>
	               <div class="process-panel pro-third">
	                   <strong>importResult:  </strong>
	                   <p>
	                       <label>all: </label> 
	                       &nbsp;<span id="allSize" class="result-size">0</span>  
	                   </p>
	                   <p>    
	                       <label class="mess-label">success:</label> 
	                       &nbsp;<span id="sucSize" class="result-size">0</span> 
	                   </p>    
	                   <p>
	                       <label class="mess-label">failure:</label> 
	                       <a id="exportError" class="export-icon"></a>
	                       <span id="fiaSize" class="result-size">0</span> 
	                   </p>    
	               </div>
               </div>
               <div class="panel-shadow"></div>
           </div>
        </div>
        <jsp:include page="../audit/inventoryProcessbar.jsp"></jsp:include>
    </div>
    <jsp:include page="/WEB-INF/page/common/footer.jsp" />
    <script type="text/javascript" src="<%=basePath%>js/common/jquery.form.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/asset/importAssets.js"></script>
</body>
</html>