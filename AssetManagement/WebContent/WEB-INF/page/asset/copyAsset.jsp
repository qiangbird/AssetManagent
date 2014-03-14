<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<%
request.setCharacterEncoding("UTF-8");
response.setCharacterEncoding("UTF-8");
String name = request.getContextPath();
String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + name + "/";
%>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Copy Assets</title>
<link rel="stylesheet" href="css/asset/createAsset.css" type="text/css">
<link rel="stylesheet" href="css/asset/copyAsset.css" type="text/css">
<link rel="stylesheet" href="autocomplete/css/autocomplete.css" type="text/css">
<link rel="stylesheet" href="css/asset/assetCommon.css" type="text/css">
</head>
<body>
	<jsp:include page="../common/header.jsp"></jsp:include>
	<div id="blank">
       <a href="home"><spring:message code="navigator.home"></spring:message></a>
       <b>&gt;</b>
       <span>
        <c:choose>
            <c:when test="${flag==''||flag==null}">
            <spring:message code="copy.asset" />
            </c:when>
            <c:when test="${flag!=''}">
            <spring:message code="create.asset" />
            </c:when>
            </c:choose>
       </span>
    </div>
	<div id="bodyMinHight">
	<div id="main">
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

				<div class="common-requirement-property">
					<div class="asset-input-left asset-input-panel">
						<input type='hidden' name="assetId" id="assetId" value="${assetVo.assetId }"/>
						<input type='hidden' name="purchaseItemId" id="purchaseItemId" value="${assetVo.purchaseItemId }"/>
						<div class="row">
							<span>*</span> <label><spring:message code="asset.name" /></label>
							<input name="assetName" id="assetName" value="${assetVo.assetName }" maxlength="36" class="l-text"
								data-required="true" placeholder="Please select a asset name" />
						</div>
						<div class="row">
							<span>*</span> <label><spring:message code="asset.type" /></label>
							<%-- <form:select name="type" id="assetType" items="${assetType}"></form:select> --%>
                            <input name="type" id="assetType"  value="${assetVo.type }" readonly="readonly" />
						</div>
						<div class="row">
							<label><spring:message code="asset.bar.code" /></label>
							<input name="barCode" id="barCode"  value="${assetVo.barCode }" maxlength="36" class="l-text"
								placeholder="Please input the barCode" />
						</div>
						<div class="row">
							<label><spring:message code="asset.series.no" /></label>
							<input name="seriesNo" id="seriesNo"  value="${assetVo.seriesNo }" maxlength="36" class="l-text"
								placeholder="Please input SeriesNo" />
						</div>
						<div class="row">
							<label><spring:message code="asset.po.no" /></label>
							<input name="poNo" id="poNo"  value="${assetVo.poNo }" maxlength="36" class="l-text"
								placeholder="Please input poNo" />

						</div>
						<div class="row">
							<span>*</span> <label><spring:message code="asset.ownership" /></label>
							<input name="ownerShip" id="ownership" value="${assetVo.ownerShip }" maxlength="36"
								 placeholder="Please select OwnerShip" />

						</div>
						<div class="row">
							<span>*</span> <label><spring:message code="asset.used.by" /></label>
							<input name="customer.customerCode" id="customerCode"
								maxlength="36" placeholder="Please select Custumer" />
					        <input id="customerCodeDefaultValue" value="${assetVo.customer.customerCode }" type="hidden" />
					        <input id="customerName" value="${assetVo.customer.customerName }" type="hidden" />
						</div>
<%-- 						<div class="showElement" onblur="hideSelect()">
							<c:forEach var="myCustomersItems" items="${myCustomers}">
								<li class="showElementItems"><c:out
										value="${myCustomersItems.customerName}"></c:out></li>
							</c:forEach>
						</div> --%>

						<div class="row">
							<label><spring:message code="project" /></label>
							<input name="project.projectCode" id="projectCode"
							  readonly="readonly" />
							<input id="projectCodeDefaultValue" value="${assetVo.project.projectCode }" readonly="readonly" />
							<input id="projectName" value="${assetVo.project.projectName }" type="hidden" />
						</div>
						<div class="showProject"></div>
						<div class="row">
							<span>*</span> <label><spring:message code="asset.status" /></label>
							<%-- <form:select name="status" id="selectedStatus"
								items="${assetStatus}">
							</form:select> --%>
							<input name="status" id="selectedStatus" value="${assetVo.status }" class="l-select" />
						</div>
						<div class="row">
							<label><spring:message code="asset.check.in.date" /></label>
							<input name="checkInTime" id="checkedInTime" value="${assetVo.checkInTime }"
								class="l-date" readonly="readonly"
								placeholder="Please select checkInTime" />
						</div>
						<div class="row">
							<label><spring:message code="asset.check.out.date" /></label>
							<input name="checkOutTime" id="checkedOutTime" value="${assetVo.checkOutTime }"
								class="l-date" readonly="readonly"
								placeholder="Please select checkOutTime" />
						</div>

						<div class="row">
							<label><spring:message code="keeper" /></label>
							<input name="keeper" id="keeperSelect" class="l-text" value="${assetVo.keeper }"
								readonly="readonly" />
						</div>
						<shiro:hasRole name="IT">
                        <div id="minHeight" class="row">
                            <label><spring:message code="navigator.fixed.assets" /></label>
                            <input name="fixed" id="fixed" type="hidden" value="${assetVo.fixed }"/>
                            <div class="radioBoxes">
                                <div class="fixedCheckBox"><a class="radioCheckOn" id="false"></a><span class="requiredFalse">
                                <spring:message code="customized.property.false" /></span></div>
                                <div class="fixedCheckBox"><a class="radioCheckOff" id="true"></a><span class="requiredTrue">
                                <spring:message code="customized.property.true" /></span></div>
                            </div>
                        </div>
                        </shiro:hasRole>

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
						<div>
							<!-- <label><label id="label_AssetPhoto"></label></label> -->
							<input type='hidden' name="photoPath" id="photoPath" readonly="readonly"
								placeholder="Please select a photo" class="truePhotoName" />

						</div>

						<div class="row">
							<span>*</span> <label> <spring:message code="asset.entity" /></label>
							<%-- <form:select name="entity" id="selectedEntity"
								items="${allEntity}"></form:select> --%>
							<input name="entity" id="selectedEntity" value="${assetVo.entity }" class="l-text"  />
						</div>
						<div class="row">
                            <span>*</span> <label><spring:message code="asset.site" /></label>
                            <%-- <form:select name="site" id="selectedSite" class="select" items='${siteList}'>
                            </form:select> --%>
                            <input name="site" id="selectedSite"  value="${assetVo.site }"  class="l-text"  />
                        </div>
						<div class="row">
							<span>*</span><label><spring:message code="asset.location" /></label>
							<input name="location" id="selectedLocation" maxlength="36" value="${assetVo.location }"
                                class="l-text" placeholder="Please input a room" />
                                <input id="room"  value="${assetVo.location }" type="hidden"/>
						</div>
						<div class="row">
							<label><spring:message code="asset.user" /></label>
							<input name="user.userId" id="userId" maxlength="36"
								class="l-text" placeholder="Please input a user" />
							<input id="userIdDefaultValue" value="${assetVo.user.userId }" type="hidden" />
							<input id="userName" value="${assetVo.user.userName }" type="hidden" />
						</div>
						<div class="row">
							<label><spring:message code="asset.manufacture" /></label>
							<input name="manufacturer" id="manufacturer" maxlength="36" class="l-text" value="${assetVo.manufacturer }"
								placeholder="Please input manufacturer" />
						</div>

						<div class="row">
							<label><spring:message code="asset.warranty" /></label>
							<input name="warrantyTime" id="assetWarranty"  value="${assetVo.warrantyTime }"
								class="l-date" readonly="readonly"
								placeholder="Please select assetWarranty" />
						</div>

						<div class="row">
							<label><spring:message code="asset.vendor" /></label>
							<input name="vendor" id="monitorVendor" class="l-text" value="${assetVo.vendor }"
								placeholder="Please input vendor" />
						</div>

						<div class="p-textarea row">
							<label><spring:message code="asset.memo" /></label> <span
								class="p-textarea-span"> <textarea name="memo" id="memo"
									class="l-textarea" >${assetVo.vendor }</textarea>
							</span>
						</div>
					</div>
				</div>
				<div class="clear"></div>
				<div class="showAsSelect">

					<div id="machineDetails" class="type-details" style="display: none">
						<div class="commons">
							<strong><spring:message code="asset.machine.details" /></strong>
						</div>
						<div class="detail-as-select-left">
							<input type='hidden' name="machine.id" id="machineId" value="${assetVo.machine.id }"/>
							<div class="asset-input-left asset-input-panel">
								<div class="row">
									<span>*</span>
									<label>
									<spring:message code="asset.machine.subtype" />
									</label>
									<%-- <form:select name="machine.subtype" id="machineType"
										items="${machineTypes}"></form:select> --%>
										<input name="machine.subtype" id="machineType" value="${assetVo.machine.subtype }" class=""/>
								</div>
								<div class="p-textarea row">
									<label><spring:message code="asset.machine.speification" /></label>
									<span class="p-textarea-span"> <textarea
											name="machine.specification" id="specification"
											class="form-control l-textarea" >${assetVo.machine.specification }</textarea>
									</span>
								</div>
							</div>
						</div>

						<div class="detail-as-select-right">
							<div class="asset-input-left asset-input-panel">
								<div class="p-textarea row">
									<label><spring:message code="asset.machine.addtional.config" /></label>
									<span class="p-textarea-span"> <textarea
											name="machine.configuration" class="l-textarea" >${assetVo.machine.configuration }</textarea>
									</span>
								</div>
								<div class="row">
									<label><spring:message code="asset.machine.mac.address" /></label>
									<input name="machine.address" class="l-text" value="${assetVo.machine.address }"/>
								</div>
							</div>
						</div>

					</div>
                    <div class="clear"></div>

					<div id="monitorDetails" class="type-details" style="display: none">
						<div class="commons">
							<strong><spring:message code="asset.monitor.details" /></strong>
						</div>
						<div class="asset-input-left asset-input-panel">
							<input type='hidden' name="monitor.id" id="monitorId" value="${assetVo.monitor.id }"/>
							<div class="row">
								<label><spring:message code="asset.monitor.size" /></label>
								<input name="monitor.size" id="size" class="l-text" value="${assetVo.monitor.size }" />
							</div>
						</div>
						<div class="asset-input-right asset-input-panel">
							<div class="p-textarea row">
								<label><spring:message code="asset.monitor.detail" /></label> <span
									class="p-textarea-span"> <textarea
										name="monitor.detail" id="monitorDetails"
										class="form-control l-textarea" >${assetVo.monitor.detail }</textarea>

								</span>
							</div>
						</div>
					</div>
                    <div class="clear"></div>
					<div id="deviceDetails" class="type-details" style="display: none">
						<div class="commons">
							<strong><spring:message code="asset.device.details" /></strong>
						</div>
						<div class="asset-input-left asset-input-panel">
							<input type='hidden' name="device.id" id="deviceId" value="${assetVo.device.id }"/>
							<div class="row">
								<label><spring:message code="asset.device.subtype" /></label>
								<input name="device.deviceSubtype.subtypeName" value="${assetVo.device.deviceSubtype.subtypeName }" 
									id="deviceSubtypeSelect" class="form-control l-text" />

								<span class="image-span"></span>
							</div>
						</div>
						<div class="asset-input-right asset-input-panel">
							<div class="row">
								<label><spring:message code="asset.device.configuration" /></label>
								<input name="device.configuration" id="configuration" value="${assetVo.device.configuration }"
									class="l-text" />
							</div>
						</div>
					</div>
                    <div class="clear"></div>
                  <div id="softwareDetails" class="type-details"
                        style="display: none">
                        <div class="commons">
                            <strong><spring:message code="asset.software.details" /></strong>
                        </div>
                        <div class="asset-input-left asset-input-panel">
                            <input type='hidden' name="software.id" id="softwareId" value="${assetVo.software.id }"/>
                            
                       <div class="row">
                           <label><spring:message code="asset.software.version" /></label>
                           <input name="software.version" id="version" class="l-text" value="${assetVo.software.version }" />
                       </div>
                       <div class="row">
                            <label><spring:message code="asset.software.additional.info" /></label>
                            <input name="software.additionalInfo" id="additionalInfo" value="${assetVo.software.additionalInfo }"
                                    class="l-text" />
                       </div>
                       </div>
                       
                        <div class="asset-input-right asset-input-panel">
                       <div>
                          <label><spring:message code="asset.software.license.key" /></label>
                       <input name="software.licenseKey" id="licenseKey"
                                    class="l-text"/>
                       </div>             
                                    
                       <div id="software_manager_visible"  class="row">
                                <input id="visible" name="software.managerVisible" value="${asset.software.managerVisible }" style="display: none;"/>
                                <a class="visibleCheckBoxOff" ></a>
                                <label id="manager_visible"><spring:message code="asset.software.visible.for.manager" /></label>
                            </div>
                        </div>
                    </div>
                    <div class="clear"></div>
					<div id="otherAssetsDetails" class="type-details"
						style="display: none">
						<div class="commons">
							<strong><spring:message code="asset.non.it.asset.details" /></strong>
						</div>
						<div class="asset-input-left asset-input-panel">
							<input type='hidden' name="otherAssets.id" id="otherAssetsId" value="${assetVo.otherAssets.id }"/>
							<div class="p-textarea row">
								<label><spring:message code="asset.non.it.asset.detail" /></label>
								<span class="p-textarea-span"> <textarea
										name="otherAssets.detail" id="otherAssetsDetails" class="l-textarea" >${assetVo.otherAssets.detail }</textarea>
								</span>
							</div>
						</div>

						<div class="asset-input-right asset-input-panel"></div>
					</div>
                    <div class="clear"></div>
				</div>
                    <div class="clear"></div>
				<div class="asset-batch-create">
                    <a class="batchCheckBoxOff" id="batchCreate"></a>
                    <input type="hidden" id="batchNumber"/>
                    <label class="bath-create-asset"><spring:message code="batch.create" /></label>
                    <label class="form-control l-text"> 
                    <input id="showBatch" type="text" name="batchCount" 
                    class="showBatchNormal" value="1" required></label>
                </div>

				<div id="bodyShadow"></div>
				<div class="operation">
					<div class="operation_location">
						<button value='<spring:message code="save" />'
						 class="submit-button" id="submitForm" ><spring:message code="save" /></button>
						<button value='<spring:message code="cancel" />'
						 id="cancelCopy" ><spring:message code="cancel" /></button>
					</div>
					<div id="showError"></div>
				</div>
		</div>
		</div>
		</div>
		<jsp:include page="/WEB-INF/page/common/footer.jsp"></jsp:include>
		<script type="text/javascript" src="js/common/jquery-validate.min.js"></script>
		<script type="text/javascript" src="js/common/jquery.form.js"></script>
		<script type="text/javascript" src="autocomplete/js/autocomplete.js"></script>
		<script type="text/javascript" src="dropDownList/dropDownList.js"></script>
		<script type="text/javascript" src="js/asset/assetCommon.js"></script>
		<!-- add front page validation -->
        <script type="text/javascript" src="js/common/validation.js"></script>
		<script type="text/javascript" src="js/asset/copyAsset.js"></script>
</body>
</html>