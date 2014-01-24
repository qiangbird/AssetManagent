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
<link rel="stylesheet" href="css/common/jquery-ui.css" type="text/css">
<link rel="stylesheet" href="css/common/tip-green/tip-green.css"
	type="text/css">
<link rel="stylesheet" href="css/user/roleList.css" type="text/css">
<link rel="stylesheet" href="css/asset/groupManagement.css"
	type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
	<jsp:include page="../common/header.jsp" />
	
	<div class="groupListContent">
		<div class="roleAddContent">
		 <span id="autoText">
            <input id="customerName" name="CustomerName" type="text"/>
        </span>
        
        <div id="groupDetail">
            <span>Group Name:${customerGroup.groupName }</span>
             <span>Process : ${customerGroup.processType }</span>
            
        </div>
        
			<div class="addOperateButton">
				<input type="button" value="Add" id="addButton"></input>
				<input type="button" value="Reset" id="resetButton"></input> 
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
				<div class="columnElement employeeInfoElement">CustomerName</div>
				<div class="columnElement employeeInfoElement">CustomerCode</div>
				<div class="columnElement operateElement">GroupName</div>
				<div class="columnElement operateElement">Edit</div>
				<div class="columnElement operateElement">Remove</div>
			</div>
			<div class="customerList">
				<c:forEach var="groupCustmer" items="${groupCustomerList }">
					<div class="groupCustomerItem" id="${groupCustmer.id }">
						<div class="columnData groupIndex">Sequence</div>
						<div class="columnData groupName">
							<c:out value="${groupCustmer.customerName }"></c:out>
						</div>
						<div class="columnData groupDescripton">
							<c:out value="${groupCustmer.customerCode }" />
						</div>
						<div class="columnData groupProcess">
							<c:out value="${group.groupName }" />
						</div>
						<div class="columnData groupEdit">
						<span class="editIcon" id="${groupCustmer.id }"></span>
							<%-- <a href="group/edit?id=${groupCustmer.id }"><span class="editIcon"></span></a> --%>
						</div>
						<div class="columnData groupRemove">
						<span class="removeIcon"></span>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>

		<div class="saveOperateButton">
			<input type="button" value="Save" id="saveButton"></input> 
			<input type="button" value="Cancel" id="cancelButton"></input>
		</div>

	</div>
<jsp:include page="/WEB-INF/page/common/footer.jsp"></jsp:include>
<script type="text/javascript" src="js/asset/groupCustomerManagement.js"></script>
<script type="text/javascript" src="js/common/autocomplete.js"></script>
<script type="text/javascript" src="jquery.poshytip/js/jquery.poshytip.js"></script>
</body>
</html>