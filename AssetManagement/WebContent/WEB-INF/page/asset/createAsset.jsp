<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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
<title>Create Assets</title>
<link rel="stylesheet" href="css/asset/createAsset.css" type="text/css">
<link rel="stylesheet" href="css/asset/assetCommon.css" type="text/css">
<link rel="stylesheet" href="css/common/jquery-ui.css" type="text/css">
<link rel="stylesheet" href="autocomplete/css/autocomplete.css"
	type="text/css">
<link rel="stylesheet" href="jquery.poshytip/css/tip-green.css" type="text/css">
</head>
<body>
	<jsp:include page="../common/header.jsp"></jsp:include>
	<div id="main">
		<div class="home">
			<span class="root-back"><a href="#"><spring:message code="navigator.home" /></a></span>
			<span class="catelog-in-line">></span> 
			<span class="breadCrum"><spring:message code="create.asset" /></span>
		</div>
		<div id="createAssetContent">
			<div class="commons ng-binding">
			<spring:message code="asset.common.property" />
			</div>

			<div id="mainTop">
				<input type="button" />
				<form id="uploadForm" method="post" enctype="multipart/form-data">
					<input type="file" name="file" class="file" id="file"
						onchange="document.getElementById('photoPath').value=this.value" />
				</form>
			</div>

			<form:form action="asset/saveAsset" method="post" id="assetFrom"
				modelAttribute="assetVo">
				<div class="common-requirement-property">
					<div class="asset-input-left asset-input-panel">
						<form:hidden path="assetId" />
						<p>
							<span>*</span> <label><spring:message code="asset.name" /></label>
							<form:input path="assetName" maxlength="36" cssClass="l-text"
								data-required="true" placeholder="Please select a asset name" />
						</p>
						<p>
							<span>*</span><label><spring:message code="asset.type" /></label>
							<form:select path="type" id="assetType">
								<c:forEach var="assetTypeItems" items="${assetType}">
									<option value="${assetTypeItems}">${assetTypeItems}</option>
								</c:forEach>
							</form:select>
						</p>
						<p>
							<label><spring:message code="asset.bar.code" /></label>
							<form:input path="barCode" maxlength="36" cssClass="l-text"
								placeholder="Please input the barCode" />
						</p>
						<p>
							<label><spring:message code="asset.series.no" /></label>
							<form:input path="seriesNo" maxlength="36" cssClass="l-text"
								placeholder="Please input SeriesNo" />
						</p>
						<p>
							<label><spring:message code="asset.po.no" /></label>
							<form:input path="poNo" maxlength="36" cssClass="l-text"
								placeholder="Please input poNo" />

						</p>
						<p>
							<span>*</span> <label><spring:message code="asset.ownership" /></label>
							<form:input path="ownerShip" id="ownership" maxlength="36"
								cssClass="l-select" cssErrorClass="l-select-error"
								placeholder="Please select OwnerShip" />
						</p>
						<p>
							<span>*</span> <label><spring:message code="asset.used.by" /></label>
							<form:input path="customer.customerName" id="customerName"
								maxlength="36" cssClass="l-select"
								cssErrorClass="l-select-error"
								placeholder="Please select Custumer" />
							<form:hidden path="customer.customerCode" id="customerCode" />


						</p>
						<p>
							<label><spring:message code="project" /></label>
							<form:input path="project.projectName" id="project"
								class="l-select" readonly="readonly"
								placeholder="Please select Project" />
							<form:hidden path="project.projectCode" id="projectCode" />
						</p>
						<div class="showProject"></div>
						<p>
							<span>*</span> <label><spring:message code="asset.status" /></label>
							<form:select path="status" cssClass="l-select"
								id="selectedStatus" items="${assetStatus}">
								<!-- <option value="AVAILABLE">Available</option>
								<option value="IN_USE">In Use</option>
								<option value="IDLE">Idle</option>
								<option value="BORROWED">Borrowed</option>
								<option value="RETURNED">Returned</option>
								<option value="BROKEN">Broken</option>
								<option value="WRITE_OFF">Write Off</option> -->
							</form:select>
						</p>
						<p>
							<label><spring:message code="asset.check.in.date" /></label>
							<form:input path="checkInTime" id="checkedInTime"
								cssClass="l-date" readonly="true"
								placeholder="Please select checkInTime" />
						</p>
						<p>
							<label><spring:message code="asset.check.out.date" /></label>
							<form:input path="checkOutTime" id="checkedOutTime"
								cssClass="l-date" readonly="true"
								placeholder="Please select checkOutTime" />
						</p>

						<p>
							<label><spring:message code="keeper" /></label>
							<form:input path="keeper" id="keeperSelect" class="l-text"
								readonly="readonly" />
						</p>
					</div>

					<div id="picDiv">
						<c:choose>
							<c:when test="${assetVo.photoPath==''||assetVo.photoPath==null }">
								<img src="image/asset/create/NoPic.jpg" id="aphoto" />
							</c:when>
							<c:otherwise>
								<img src="${assetVo.photoPath }" id="aphoto" />
							</c:otherwise>

						</c:choose>

					</div>
					<div class="common-asset-input-right asset-input-panel">
						<p>
							<!-- <label><label id="label_AssetPhoto"></label></label> -->
							<form:hidden path="photoPath" readonly="true"
								placeholder="Please select a photo" class="truePhotoName" />

						</p>

						<p>
							<span>*</span> <label> <spring:message code="asset.entity" /></label>
							<form:select path="entity" id="selectedEntity">
								<c:forEach var="allEntityItems" items="${allEntity}">
									<option class="showEntityItems" value="${allEntityItems}">${allEntityItems}</option>
								</c:forEach>
							</form:select>

						</p>

						<p>
							<span>*</span> <label><spring:message code="asset.site" /></label>
							<form:select path="site" id="selectedSite" class="select" items="${siteList}">
								<%-- <c:forEach var="siteListItems" items="${siteList}">
									<option value='${siteListItems.replace(" ","_") }'>${siteListItems}</option>
								</c:forEach> --%>
							</form:select>
						</p>
						<p>
                            <span>*</span> <label><spring:message code="asset.location" /></label>
                                <form:input path="location" id="selectedLocation" maxlength="36"
                                cssClass="l-text" placeholder="Please input a user" />
                            <%-- <form:select path="location" id="selectedLocation" class="select">
                                <c:forEach var="locationListItems" items="${locationList }">
                                    <option>${locationListItems.room}</option>
                                </c:forEach>
                            </form:select> --%>
                        </p>
						<p>
							<label><spring:message code="asset.user" /></label>
							<form:input path="user.userName" id="assetUser" maxlength="36"
								cssClass="l-text" placeholder="Please input a user" />
							<form:hidden path="user.userId" id="userId" />
						</p>
						<p>
							<label><spring:message code="asset.manufacture" /></label>
							<form:input path="manufacturer" maxlength="36" cssClass="l-text"
								placeholder="Please input manufacturer" />
						</p>

						<p>
							<label><spring:message code="asset.warranty" /></label>
							<form:input path="warrantyTime" id="assetWarranty"
								cssClass="l-date" readonly="true"
								placeholder="Please select assetWarranty" />
						</p>

						<p>
							<label><spring:message code="asset.vendor" /></label>
							<form:input path="vendor" id="monitorVendor" cssClass="l-text"
								placeholder="Please input vendor" />
						</p>

						<p class="p-textarea">
							<label><spring:message code="asset.memo" /></label> <span
								class="p-textarea-span"> <form:textarea path="memo"
									cssClass="l-textarea" />
							</span>
						</p>
					</div>
				</div>
				<%--  <%@include file="showAsSelect.txt"%>  --%>
				<div class="showAsSelect">

					<div id="machineDetails" class="type-details" style="display: none">
						<div class="commons">
							<strong><spring:message code="asset.machine.details" /></strong>
						</div>
						<div class="detail-as-select-left">
							<form:hidden path="machine.id" />
							<div class="asset-input-left asset-input-panel">
								<p>
									<span>*</span>
									<label><spring:message code="asset.machine.subtype" /></label>
									<form:select path="machine.subtype" id="machineType"
										items="${machineTypes}"></form:select>
								</p>
								<p class="p-textarea">
									<label><spring:message code="asset.machine.speification" /></label>
									<span class="p-textarea-span"> <form:textarea
											path="machine.specification" id="specification"
											class="form-control l-textarea" />
									</span>
								</p>
							</div>
						</div>

						<div class="detail-as-select-right">
							<div class="asset-input-left asset-input-panel">
								<p class="p-textarea">
									<label><spring:message code="asset.machine.addtional.config" /></label>
									<span class="p-textarea-span"> <form:textarea
											path="machine.configuration" class="l-textarea" />
									</span>
								</p>
								<p>
									<label><spring:message code="asset.machine.mac.address" /></label>
									<form:input path="machine.address" class="l-text" />
								</p>
							</div>
						</div>

					</div>


					<div id="monitorDetails" class="type-details" style="display: none">
						<div class="commons">
							<strong><spring:message code="asset.monitor.details" /></strong>
						</div>
						<div class="asset-input-left asset-input-panel">
							<form:hidden path="monitor.id" />
							<p>
								<label><spring:message code="asset.monitor.size" /></label>
								<form:input path="monitor.size" id="size" class="l-text" />
							</p>
						</div>
						<div class="asset-input-right asset-input-panel">
							<p class="p-textarea">
								<label><spring:message code="asset.monitor.detail" /></label> <span
									class="p-textarea-span"> <form:textarea
										path="monitor.detail" id="details"
										class="form-control l-textarea" />

								</span>
							</p>
						</div>
					</div>

					<div id="deviceDetails" class="type-details" style="display: none">
						<div class="commons">
							<strong><spring:message code="asset.device.details" /></strong>
						</div>
						<div class="asset-input-left asset-input-panel">
							<form:hidden path="device.id" />
							<p>
								<label><spring:message code="asset.device.subtype" /></label>
								<form:input path="device.deviceSubtype.subtypeName"
									id="deviceSubtypeSelect" class="form-control l-text" />

								<span class="image-span"></span>
							</p>
						</div>
						<div class="asset-input-right asset-input-panel">
							<p>
								<label><spring:message code="asset.device.configuration" /></label>
								<form:input path="device.configuration" id="configuration"
									class="l-text" />
							</p>
						</div>
					</div>

					<div id="softwareDetails" class="type-details"
						style="display: none">
						<div class="commons">
							<strong><spring:message code="asset.software.details" /></strong>
						</div>
						<div class="asset-input-left asset-input-panel">
							<form:hidden path="software.id" />
							<p>
								<label><spring:message code="asset.software.version" /></label>
								<form:input path="software.version" id="version" class="l-text" />
							</p>
							<p>
								<label><spring:message code="asset.software.license.key" /></label>
								<form:input path="software.licenseKey" id="licenseKey"
									class="l-text" />
							</p>
						</div>
						<div class="asset-input-right asset-input-panel">
							<p>
								<span>*</span>
								<label><spring:message code="asset.software.max.user.num" /></label>
								<form:input path="software.maxUseNum" id="maxUseNum"
									class="l-text" />
							</p>
							<p>
                                <label><spring:message code="asset.software.expired.time" /></label>

                                <form:input path="softwareExpiredTime" id="softwareExpiredTime"
                                    class="l-date" />
                            </p>
							<p>
								<label><spring:message code="asset.software.additional.info" /></label>

								<form:input path="software.additionalInfo" id="additionalInfo"
									class="l-text" />
							</p>

						</div>
					</div>

					<div id="otherAssetsDetails" class="type-details"
						style="display: none">
						<div class="commons">
							<strong><spring:message code="asset.non.it.asset.details" /></strong>
						</div>
						<div class="asset-input-left asset-input-panel">
							<form:hidden path="otherAssets.id" />
							<p class="p-textarea">
								<label><spring:message code="asset.non.it.asset.detail" /></label>
								<span class="p-textarea-span"> <form:textarea
										path="otherAssets.detail" id="details" class="l-textarea" />
								</span>
							</p>
						</div>

						<div class="asset-input-right asset-input-panel"></div>
					</div>

				</div>

				<div class="asset-batch-create">
					<span id="batchCreatCkBox"> 
					<input type="checkbox" id="batchCreate" name="batchCreate">
					</span> 
					<label class="bath-create-asset"><spring:message code="batch.create" /></label>
					<label class="form-control l-text"> 
					<input id="showBatch" type="text" name="batchCount" 
					class="showBatchNormal" value="1" required></label>
				</div>

				<div id="bodyShadow"></div>
				<div class="operation">
					<div class="operation_location">
						<input type="button" value='<spring:message code="submit" />' class="submit-button" id="submitForm" />
						<input type="button" value='<spring:message code="cancel" />' class="cancel-button" onclick="window.history.back();" />
					</div>
					<div id="showError"></div>
				</div>
			</form:form>
			<input type="hidden" id="localeCode" name="localeCode"
				value="${sessionScope.i18n }">
		</div>
		<jsp:include page="/WEB-INF/page/common/footer.jsp"></jsp:include>
		<script type="text/javascript" src="js/common/jquery-1.7.1.min.js"></script>
		<script type="text/javascript"
			src="js/common/jquery-ui-1.8.18.custom.min.js"></script>
		<script type="text/javascript"
			src="js/common/jquery.i18n.properties-1.0.9.js"></script>
		<script type="text/javascript" src="js/common/jquery-validate.min.js"></script>
		<script type="text/javascript" src="js/common/jquery.form.js"></script>
		<script type="text/javascript" src="js/asset/createAsset.js"></script>
		<script type="text/javascript" src="dropDownList/dropDownList.js"></script>
		<link rel="stylesheet" type="text/css" href="dropDownList/themes/dropDownList.css" />
		<script type="text/javascript" src="js/asset/assetCommon.js"></script>
		
		<!-- add front page validation -->
		<script type="text/javascript" src="js/common/validation.js"></script>
		<script type="text/javascript" src="jquery.poshytip/js/jquery.poshytip.js"></script>
		<script type="text/javascript" src="js/asset/assetValidation.js"></script>
</body>
</html>