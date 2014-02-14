<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%-- <link rel="icon" href="<%=basePath %>resources/images/headIMG/favicon.ico" type="image/x-icon"> --%>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/error/error.css" />

<title>Insert title here</title>
</head>
<body>
<div class="html_body_div"> 
    <div class="error_background">
      <div class="message_background">
        <div class="error_image_access"></div>
        <div class="error_message_access">
          <div class="error_text_access">You have no authority to enter the system</div>
        </div>
      </div>
    </div>
</div>
</body>
<script type="text/javascript" src="<%=basePath%>js/common/jquery-1.10.2.js" ></script>
<script type="text/javascript" src="<%=basePath%>js/error/error.js" ></script>
</html>