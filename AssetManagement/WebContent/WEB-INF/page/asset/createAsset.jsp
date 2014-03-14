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
String basename = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + name + "/";
%>
<html>
<head>
<base href="<%=basename%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Create Assets</title>
<link rel="stylesheet" href="autocomplete/css/autocomplete.css" type="text/css">
<link rel="stylesheet" type="text/css" href="dropDownList/themes/dropDownList.css" />
<link rel="stylesheet" href="css/asset/assetCommon.css" type="text/css">
<link rel="stylesheet" href="css/asset/createAsset.css" type="text/css">
</head>
<body>
    <jsp:include page="../common/header.jsp"></jsp:include>
    <div id="blank">
	   <a href="home"><spring:message code="navigator.home"></spring:message></a>
	   <b>&gt;</b>
	   <span><spring:message code="create.asset"></spring:message></span>
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
                        onchange="document.getElementById('photoname').value=this.value" />
                </form>
            </div>

            <%-- <form action="asset/saveAsset" method="post" id="assetFrom"> --%>
                <!-- <input type="hidden" id="action" value="asset/saveAsset"> -->
                <div class="common-requirement-property">
                    <div class="asset-input-left asset-input-panel">
                        <input type="hidden" id="assetId"/>
                        <div class="row">
                            <span>*</span> <label><spring:message code="asset.name" /></label>
                            <input id="assetName" maxlength="36" class="l-text"
                                data-required="true" placeholder="Please select a asset name" />
                        </div>
                        <div class="row">
                            <span>*</span><label><spring:message code="asset.type" /></label>
<%--                             <form:select name="type" id="assetType">
                                <c:forEach var="assetTypeItems" items="${assetType}">
                                    <option value="${assetTypeItems}">${assetTypeItems}</option>
                                </c:forEach>
                            </form:select> --%>
                            <input id="assetType" maxlength="36" class="l-text" />
                        </div>
                        <div class="row">
                            <label><spring:message code="asset.bar.code" /></label>
                            <input id="barCode" maxlength="36" class="l-text"
                                placeholder="Please input the barCode" />
                        </div>
                        <div class="row">
                            <label><spring:message code="asset.series.no" /></label>
                            <input id="seriesNo" maxlength="36" class="l-text"
                                placeholder="Please input SeriesNo" />
                        </div>
                        <div class="row">
                            <label><spring:message code="asset.po.no" /></label>
                            <input id="poNo" maxlength="36" class="l-text"
                                placeholder="Please input poNo" />

                        </div>
                        <div class="row">
                            <span>*</span> <label><spring:message code="asset.ownership" /></label>
                            <input id="ownership" maxlength="36" placeholder="Please select OwnerShip" />
                           
                        </div>
                        <div class="row">
                            <span>*</span> <label><spring:message code="asset.used.by" /></label>
                            <input id="customerCode" maxlength="36" placeholder="Please select Custumer" />
                            <input type='hidden' id="customerName" value=""/> 
                        </div>
                        <div class="row">
                            <label><spring:message code="project" /></label>
                            <input id="projectCode"  maxlength="36" placeholder="Please select Project" />
                            <input type='hidden' id="projectName" value=""/> 
                        </div>
                        <div class="showProject"></div>
                        <div class="row">
                            <span>*</span> <label><spring:message code="asset.status" /></label>
<%--                             <form:select name="status" class="l-select"
                                id="selectedStatus" items="${assetStatus}">
                            </form:select> --%>
                            <input id="selectedStatus" />
                        </div>
                        <div class="row">
                            <label><spring:message code="asset.check.in.date" /></label>
                            <input id="checkedInTime"
                                class="checkIn" readonly="readonly"
                                placeholder="Please select checkInTime" />
                        </div>
                        <div class="row">
                            <label><spring:message code="asset.check.out.date" /></label>
                            <input id="checkedOutTime"
                                class="checkOut" readonly="readonly"
                                placeholder="Please select checkOutTime" />
                        </div>

                        <div class="row">
                            <label><spring:message code="keeper" /></label>
                            <input id="keeperSelect" class="l-text"
                                readonly="readonly" />
                        </div>
                        <shiro:hasRole name="IT">
                        <div id="minHeight"  class="row">
                            <label><spring:message code="navigator.fixed.assets" /></label>
                            <input id="fixed" type="hidden" value="false"/>
                            <div class="radioBoxes">
			                    <div class="fixedCheckBox"><a class="radioCheckOn" id="false"></a><span class="requiredFalse">
			                    <spring:message code="customized.property.false" /></span></div>
			                    <div class="fixedCheckBox"><a class="radioCheckOff" id="true"></a><span class="requiredTrue">
			                    <spring:message code="customized.property.true" /></span></div>
			                </div>
                        </div>
                        </shiro:hasRole>
                    </div>

                    <div id="picDiv" >
                        <%-- <c:choose>
                            <c:when test="${assetVo.photoPath==''||assetVo.photoPath==null }"> --%>
                                <img src="image/asset/create/NoPic.jpg" id="aphoto" />
                            <%-- </c:when>
                            <c:otherwise>
                                <img src="${assetVo.photoPath }" id="aphoto" />
                            </c:otherwise>

                        </c:choose> --%>

                    </div>
                    <div class="common-asset-input-right asset-input-panel ">
                        <div>
                            <!-- <label><label id="label_AssetPhoto"></label></label> -->
                            <input type='hidden' id="photoPath" readonly="readonly"
                                placeholder="Please select a photo" class="truePhotoName" />

                        </div>

                        <div class="row">
                            <span>*</span> <label> <spring:message code="asset.entity" /></label>
                            <%-- <form:select name="entity" id="selectedEntity">
                                <c:forEach var="allEntityItems" items="${allEntity}">
                                    <option class="showEntityItems" value="${allEntityItems}">${allEntityItems}</option>
                                </c:forEach>
                            </form:select> --%>
                            <input id="selectedEntity" class="l-text"  />

                        </div>

                        <div class="row">
                            <span>*</span> <label><spring:message code="asset.site" /></label>
                            <%-- <form:select name="site" id="selectedSite" class="select" items="${siteList}">
                            </form:select> --%>
                            <input id="selectedSite" class="l-text"  />
                        </div>
                        <div class="row">
                            <span>*</span><label><spring:message code="asset.location" /></label>
                                <input id="selectedLocation" maxlength="36"
                                class="l-text" placeholder="Please input a room" />
                                <input id="room"  value="${asset.location }" type="hidden"/>
                        </div>
                        <div class="row">
                            <label><spring:message code="asset.user" /></label>
                            <input id="userId" maxlength="36"
                                class="l-text" placeholder="Please input a user" />
                            <input  id="userName"  value="" type="hidden" />
                            <%-- <input type='hidden' name="user.userId" id="userId" /> --%>
                        </div>
                        <div class="row">
                            <label><spring:message code="asset.manufacture" /></label>
                            <input id="manufacturer" maxlength="36" class="l-text"
                                placeholder="Please input manufacturer" />
                        </div>

                        <div class="row">
                            <label><spring:message code="asset.warranty" /></label>
                            <input id="assetWarranty"
                                class="l-date" readonly="readonly"
                                placeholder="Please select assetWarranty" />
                        </div>

                        <div class="row">
                            <label><spring:message code="asset.vendor" /></label>
                            <input id="monitorVendor" class="l-text"
                                placeholder="Please input vendor" />
                        </div>

                        <div class="p-textarea row">
                            <label><spring:message code="asset.memo" /></label> <span
                                class="p-textarea-span"> <textarea id="memo"
                                    class="l-textarea" ></textarea>
                            </span>
                        </div>
                    </div>
                </div>
                    <div class="clear"></div>
                <%--  <%@include file="showAsSelect.txt"%>  --%>
                <div class="showAsSelect">

                    <div id="machineDetails" class="type-details" style="display: none">
                        <div class="commons">
                            <strong><spring:message code="asset.machine.details" /></strong>
                        </div>
                        <div class="detail-as-select-left">
                            <input type='hidden' id="machineId"/>
                            <div class="asset-input-left asset-input-panel">
                                <div class="row">
                                    <span>*</span>
                                    <label><spring:message code="asset.machine.subtype" /></label>
                                    <%-- <form:select name="machine.subtype" id="machineType"
                                        items="${machineTypes}"></form:select> --%>
                                        <input id="machineType" class=""/>
                                </div>
                                <div class="p-textarea row">
                                    <label><spring:message code="asset.machine.speification" /></label>
                                    <span class="p-textarea-span"> <textarea id="specification"
                                            class="form-control l-textarea" ></textarea>
                                    </span>
                                </div>
                            </div>
                        </div>

                        <div class="detail-as-select-right">
                            <div class="asset-input-left asset-input-panel">
                                <div class="p-textarea row">
                                    <label><spring:message code="asset.machine.addtional.config" /></label>
                                    <span class="p-textarea-span"> <textarea
                                         id="machineConfiguration" class="l-textarea" ></textarea>
                                    </span>
                                </div>
                                <div class="row">
                                    <label><spring:message code="asset.machine.mac.address" /></label>
                                    <input id="machineAddress" class="l-text" />
                                </div>
                            </div>
                        </div>
                    <div class="clear"></div>
                    </div>


                    <div id="monitorDetails" class="type-details" style="display: none">
                        <div class="commons">
                            <strong><spring:message code="asset.monitor.details" /></strong>
                        </div>
                        <div class="asset-input-left asset-input-panel">
                            <input type='hidden'id="monitorId"/>
                            <div class="row">
                                <label><spring:message code="asset.monitor.size" /></label>
                                <input id="size" class="l-text" />
                            </div>
                        </div>
                        <div class="asset-input-right asset-input-panel">
                            <div class="p-textarea row">
                                <label><spring:message code="asset.monitor.detail" /></label> <span
                                    class="p-textarea-span"> <textarea id="monitorDetails"
                                        class="form-control l-textarea" ></textarea>
                                </span>
                            </div>
                        </div>
                        <div class="clear"></div>
                    </div>

                    <div id="deviceDetails" class="type-details" style="display: none">
                        <div class="commons">
                            <strong><spring:message code="asset.device.details" /></strong>
                        </div>
                        <div class="asset-input-left asset-input-panel">
                            <input type='hidden' id="deviceId" />
                            <div class="row">
                                <label><spring:message code="asset.device.subtype" /></label>
                                <input id="deviceSubtypeSelect" class="form-control l-text" />

                                <span class="image-span"></span>
                            </div>
                        </div>
                        <div class="asset-input-right asset-input-panel">
                            <div class="row">
                                <label><spring:message code="asset.device.configuration" /></label>
                                <input id="configuration" class="l-text" />
                            </div>
                        </div>
                        <div class="clear"></div>
                    </div>

                    <div id="softwareDetails" class="type-details"
                        style="display: none">
                        <div class="commons">
                            <strong><spring:message code="asset.software.details" /></strong>
                        </div>
                        <div class="asset-input-left asset-input-panel">
                            <input type='hidden' id="softwareId"/>
                            <div class="row">
                                <label><spring:message code="asset.software.version" /></label>
                                <input id="version" class="l-text" />
                            </div>
                            <div class="row">
                                <label><spring:message code="asset.software.additional.info" /></label>
                                <input id="additionalInfo" class="l-text" />
                            </div>
                        </div>
                        <div class="asset-input-right asset-input-panel">
                            <div class="row">
                                <label><spring:message code="asset.software.license.key" /></label>
                                <input id="licenseKey"  class="l-text" />
                            </div>
                             <div id="software_manager_visible"  class="row">
                             <input id="visible"  style="display: none;"/>
                                <a class="visibleCheckBoxOff" ></a>
                                <label id="manager_visible"><spring:message code="asset.software.visible.for.manager" /></label>
                            </div>
                        </div>
                        <div class="clear"></div>
                    </div>

                    <div id="otherAssetsDetails" class="type-details"
                        style="display: none">
                        <div class="commons">
                            <strong><spring:message code="asset.non.it.asset.details" /></strong>
                        </div>
                        <div class="asset-input-left asset-input-panel">
                            <input type='hidden' id="otherAssetsId" />
                            <div class="p-textarea row">
                                <label><spring:message code="asset.non.it.asset.detail" /></label>
                                <span class="p-textarea-span"> <textarea id="otherAssetsDetails" class="l-textarea" ></textarea>
                                </span>
                            </div>
                        </div>
                        <div class="asset-input-right asset-input-panel"></div>
                        <div class="clear"></div>
                    </div>
                </div>
                <div class="clear"></div>
                <div class="asset-batch-create">
                    <a class="batchCheckBoxOff" id="batchCreate"></a>
                    <input type="hidden" id="isBatchCreate"  value="false" />
                    <label class="bath-create-asset"><spring:message code="batch.create" /></label>
                    <label class="form-control l-text"> 
                    <input id="showBatch" type="text"  
                    class="showBatchNormal" value="1" required></label>
                </div>

                <div id="bodyShadow"></div>
                <div class="operation">
                    <div class="operation_location">
                        <button type="button" class="submit-button" id="submitForm" ><spring:message code="save" /></button>
                        <button type="button" id="cancelCopy" onclick="window.history.back();" ><spring:message code="cancel" /></button>
                    </div>
                    <div id="showError"></div>
                </div>
            <%-- </form> --%>
<%--             <input type="hidden" id="localeCode" name="localeCode"
                value="${sessionScope.i18n }"> --%>
        </div>
        </div>
        </div>
        <jsp:include page="/WEB-INF/page/common/footer.jsp"></jsp:include>
        <script type="text/javascript" src="js/common/jquery-validate.min.js"></script>
        <script type="text/javascript" src="js/common/jquery.form.js"></script>
        <script type="text/javascript" src="js/asset/assetCommon.js"></script>
        <script type="text/javascript" src="js/asset/createAsset.js"></script>
        <script type="text/javascript" src="dropDownList/dropDownList.js"></script>
        
        <!-- add front page validation -->
        <script type="text/javascript" src="js/common/validation.js"></script>
        
</body>
</html>