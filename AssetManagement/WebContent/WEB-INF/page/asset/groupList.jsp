
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<base href="<%=basePath%>">
<link rel="stylesheet" href="css/common/autocomplete.css"
	type="text/css">
<link rel="stylesheet" type="text/css" href="css/common/jquery-ui-1.8.18.custom.css">
<link rel="stylesheet" href="css/common/jquery-ui.css" type="text/css">
<link rel="stylesheet" href="css/common/tip-green/tip-green.css" type="text/css">
<link rel="stylesheet" href="css/user/roleList.css" type="text/css">
<link rel="stylesheet" href="css/asset/groupManagement.css"type="text/css">
<link rel="stylesheet" href="css/asset/locationManagement.css"type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
	<jsp:include page="../common/header.jsp" />
	<div class="groupListContent">
		<div class="roleAddContent">
			<div class="addOperateButton">
				<input type="button" value="Add" id="addButton"></input>
			</div>
		</div>
		<div class="employeeRoleInfoTemplate">
			<div class="employeeRoleInfo">
				<input type="hidden" id="isNew" value="" />
				<div class="columnData sequenceElement">
					<span id="sequence"></span>
				</div>
				<div class="columnData employeeInfoElement">
					<span class="employeeIdInRow"></span>
				</div>
				<div class="columnData employeeInfoElement">
					<span class="employeeNameInRow"></span>
				</div>
				<div class="columnData operateCheckbox itInRow">
					<a id="itInRow" class="roleCheckBoxInRowOff"></a> <input
						type="hidden" id="itInRowValue" value="">
				</div>
				<div class="columnData operateCheckbox adminInRow">
					<a id="adminInRow" class="roleCheckBoxInRowOff"></a> <input
						type="hidden" id="adminInRowValue" value="">
				</div>
				<div class="columnData removeElement">
					<span class="deleteLink"></span>
				</div>
			</div>
		</div>
		<div class="groupDispaly">
			<div class="rowHead">
				<div id="showError"></div>
				<div class="columnElement sequenceElement">Sequence</div>
				<div class="columnElement employeeInfoElement">GroupName</div>
				<div class="columnElement employeeInfoElement">GroupDescription</div>
				<div class="columnElement operateElement">GroupProcessType</div>
				<div class="columnElement operateElement">Eidt</div>
				<div class="columnElement operateElement">Remove</div>
			</div>
			<div class="groupList">
				<c:forEach var="group" items="${groupList }" varStatus="status">
					<div class="groupItem" id="${group.id }">
						<div class="columnData groupIndex">${status.index+1}</div>
						<div class="columnData groupName">
							<c:out value="${group.groupName }"></c:out>
						</div>
						<div class="columnData groupDescripton">
							<c:out value="${group.description }" />
						</div>
						<div class="columnData groupProcess">
							<c:out value="${group.processType }" />
						</div>
						<div class="columnData groupEdit">
						<span class="editIcon" id="${group.id }"></span>
							<%-- <a href="group/edit?id=${group.id }"><span class="editIcon"></span></a> --%>
						</div>
						<div class="columnData groupRemove">
						<span class="removeIcon" id="${group.id }"></span>
							<%-- <a href="group/delete?id=${group.id }"><span class="removeIcon"></span></a> --%>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>

		<!-- <div class="saveOperateButton">
			<input type="button" value="Save" id="saveButton"></input> <input
				type="button" value="Cancel" id="cancelButton"></input>
		</div> -->

	</div>

	<table class="addGroup">
        <tr>
        <td valign="top">
         <form action="group/update" method="post" id="dialog">
        <table id="group_table">
            <div class="create-table">
                <div>
                    <tr>
                        <td>Group Name</td>
                        <td>
                        <input id="groupId" type="hidden" name="id" value="${customerGroup.id }"/>
                        <input id="groupName" type="text" name="groupName" value="${customerGroup.groupName }"/>
                    <td />
                    </tr>
                </div>
                <div>
                    <tr>
                        <td>Description</td>
                        <td><input id="description" type="text" name="description" value="${customerGroup.description }"/></td>
                    </tr>
                </div>
                <div>
                    <tr>
                        <td>Process Type</td>
                        <td>
                        <select name="processType" id="processType">
                        <c:forEach var="processType" items="${processTypeList }">
                        
                       <c:if test="${customerGroup.processType !='SHARED'}">
                            <option value="${processType }" selected="selected">${processType }</option>
                        </c:if>
                         <c:if test="${customerGroup.processType =='SHARED'}">
                            <option value="${processType }">${processType }</option>
                        </c:if>
                        </c:forEach>
                        </select>
                    </tr>
                </div>
            </div>
			<div class="submit-div">
				<input class="input-80-30 submit-button" type="submit" value="Add" /> 
				<input class="input-80-30 reset-button" type="reset" value="Cancel" />
		    </div>
		</table>
		</form>
		</td>
		</tr>
	</table>
<jsp:include page="/WEB-INF/page/common/footer.jsp"></jsp:include>
<script type="text/javascript" src="js/user/userInfoTips.js"></script>
<script type="text/javascript" src="js/asset/groupManagement.js"></script> 
<script type="text/javascript" src="js/common/autocomplete.js"></script>
<script type="text/javascript" src="jquery.poshytip/js/jquery.poshytip.js"></script>
</body>
</html>