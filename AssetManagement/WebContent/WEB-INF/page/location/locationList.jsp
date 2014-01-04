<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
request.setCharacterEncoding("UTF-8");
response.setCharacterEncoding("UTF-8");
String path = request.getContextPath();
String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; 
%>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Location Management</title>
<link rel="stylesheet" type="text/css" href="css/asset/locationList.css">
<link rel="stylesheet" type="text/css" href="css/common/filterBox.css">
<!-- <link rel="stylesheet" type="text/css" href="css/common/dataList.css"> -->
<link rel="stylesheet" type="text/css" href="css/common/search.css">
<link rel="stylesheet" type="text/css" href="css/common/jquery-ui.css">
<link rel="stylesheet" href="css/asset/locationManagement.css" type="text/css">
<script type="text/javascript" src="js/common/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="js/common/jquery-ui-1.8.18.custom.min.js"></script>



<script type="text/javascript">
            $(document).ready(function(){
                    
                $("#dialog").dialog({
                    autoOpen:false,//是否自动打开
                    
                   // buttons: {"确定":function(){$(this).dialog("close");},"取消":function(){$(this).dialog("close");}},//添加按钮
                    closeOnEscape: true,//按ESC键时，是否可关闭dialog  true为可关闭，false为不可关闭
                    draggable: true,//是否可拖动
                    height: 300,
                    width: 400,
                    show: "scale",//打开dialog的效果
                    hide: "scale",//关闭dialog的效果
                    modal: true,//true表示为模式dialog
                    position: "center",//显示dialog的位置
                    resizable: true,//是否可调整大小
                    title: "Add new locatoin",
                    bgiframe: true
                });
                $("#addButton").click(function(){
                    $("#dialog").dialog("open");
                });
                
            })
            
        </script>


</head>
<body>
<jsp:include page="../home/head.jsp"></jsp:include>
<%-- <div id="listAll">
<table>
<tr>
<th>Room</th>
<th>Site</th>
<th>Operation</th>
</tr>
<c:forEach items="${locationList }" var="location">
<tr>
<td>${location.site }</td>
<td>${location.room }</td>
<td>
<span href="location/list/${location.id }/delete" class="aa" onclick="deleteRow('${location.id }', 'location/list/${location.id }/delete', this)">Delete</span>/
<a href="location/${location.id }/delete">Delete</a>/
<!-- <a id="aa">ddddd</a> -->
<a href="location/${location.id }/editLocation">Edit</a>
</td>
</tr>
</c:forEach>
</table>
<a href="location/create">create</a>
</div> --%>


 <div class="locationListContent">
     <div class="roleAddContent">
        <span id="autoText">
            <input id="employeeName" name="employeeName" type="text"/>
        </span>
        <span id="operateButton">
            <input type="button" value="Add" id="addButton"></input>
            <input type="button" value="Save" ng-click="saveEmployeeRole()" id="saveButton"></input>
            <input type="button" value="Reset" ng-click="resetEmployeeRole()" id="resetButton"></input> 
        </span>
      </div>
      <div class="rowHead">
          <div id="showError"></div>
          <span class="columnElement sequenceElement">Sequence</span>
          <span class="columnElement employeeInfoElement">Site</span>
          <span class="columnElement employeeInfoElement">Room</span>
          <span class="columnElement operateElement">Operation</span>
          <!-- <span class="columnElement operateElement">Update</span>
          <span class="columnElement operateElement">Remove</span>    --> 
     </div>
     
     <div>
         <div class="employeeRoleInfo">
     <c:forEach items="${locationList }" var="location" >
     <div class="items">
            <span class="columnData employeeInfoElement">111</span>
            <span class="columnData employeeInfoElement">${location.site }</span>
            <span class="columnData employeeInfoElement">${location.room }</span>
          <!-- <span class="columnData sequenceElement">{{$index+1}}</span>
          <span class="columnData employeeInfoElement">{{employeeRole.employeeId}}</span>
          <span class="columnData employeeInfoElement">{{employeeRole.employeeName}}</span> -->
          <span class="columnData employeeInfoElement">
          <a href="location/edit/${location.id }"><!-- <div id="editLocation"></div> -->Edit</a>/
          <div id="delelteLocation" onclick="deleteRow('${location.id }', 'location/delete/', this)"></div>
          </span>
          <!-- <span class="columnData operateCheckbox">
              <label ng-class="{roleChecked:employeeRole.systemAdminRole,roleUnChecked:!employeeRole.systemAdminRole}" ng-click="changeSystemAdminRole($index)"></label>
          </span>
          <span class="columnData removeElement">
              <span ng-click="removeEmployeeRole($index)"></span>
          </span>     -->
          </div>
          </c:forEach>
        </div>
   </div>
</div>




       <table>
            <tr>
            
            <td valign="top">
               <form action="location/save" method="post" id="dialog">
               <table>
        <div class="create-table">
            <div>
                <tr><td>Site</td><td><input type="text" name="site" value="${location.site }"/><td/></tr>
            </div>
            <div>
                <tr><td>Room</td><td><input type="text" name="room" value="${location.room }" /></td></tr>
            </div>
        </div>
        
        <div class="submit-div">
            <%-- <c:if test="${!isCreatePage }">
                <input type="hidden" name="_method" value="put" />
            </c:if> --%>
            <input class="input-80-30" type="submit" value="submit" />
            <input class="input-80-30" type="reset" value="Reset" />
            </tr>
        </div>
        </table>
    </form>
            </td>
            
            </tr>
        </table>



<script type="text/javascript" src="js/location/location.js"></script>
</body>
</html>