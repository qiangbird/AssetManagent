<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<title>View Asset</title>
<link rel="stylesheet" href="css/asset/viewAsset.css" type="text/css">
<link rel="stylesheet" href="css/common/jquery-ui.css" type="text/css">
<link rel="stylesheet" href="autocomplete/css/autocomplete.css"
    type="text/css">
</head>
<body>
    <jsp:include page="../common/header.jsp"></jsp:include>
    <div id="main">
        <div class="home">
            <span class="root-back"><a href="#"><label id="label_Home"></a></span>
            <span class="catelog-in-line">></span>
            <span class="breadCrum"><label id="label_ViewAsset"></span>
        </div>
        <div id="operation">
            <a id="editBtn"><label id="label_Edit"></label></a> <a id="copyBtn">
            <label id="label_Copy"></label></a> <a id="deleteBtn">
            <label id="label_Delete"></label></a> <a id="cancelBtn">
            <label id="label_Cancel"></label></a>
        </div>

        <div id="createAssetContent">
            <div class="commons ng-binding">
                <label id="label_CommonProperty"></label>
            </div>

            <form:form action="asset/update" method="post" id="assetFrom"
                modelAttribute="asset">
                <div class="common-requirement-property">
                    <div class="asset-input-left asset-input-panel">
                        <form:hidden path="id" />
                        <p>
                            <label><label id="label_AssetId"></label></label>
                            <form:input path="assetId" readonly="true" />
                        </p>
                        <p>
                            <label><label id="label_AssetName"></label></label>
                            <form:input path="assetName" readonly="true" />
                        </p>
                        <p>

                            <label><label id="label_AssetType"></label></label>
                            <form:input path="type" id="assetType" readonly="true" />

                        </p>
                        <p>
                            <label><label id="label_AssetBarCode"></label></label>
                            <form:input path="barCode" readonly="true" />
                        </p>
                        <p>
                            <label><label id="label_AssetSeriesNo"></label></label>
                            <form:input path="seriesNo" readonly="true" />
                        </p>
                        <p>
                            <label><label id="label_AssetPoNo"></label></label>
                            <form:input path="poNo" readonly="true" />

                        </p>
                        <p>
                            <label><label id="label_AssetOwnerShip"></label></label>
                            <form:input path="ownerShip" readonly="true" />
                        </p>
                        <p>
                            <label><label id="label_AssetUsedBy"></label></label>
                            <form:input path="customer.customerName" readonly="true" />
                        </p>
                        <p>
                            <label><label id="label_Project"></label></label>
                            <form:input path="project.projectName" readonly="true" />
                        </p>
                        <p>
                            <label><label id="label_AssetStatus"></label></label>
                            <form:input path="status" readonly="true" />
                        </p>
                        <p>
                            <label><label id="label_AssetCheckInDate"></label></label>
                            <form:input path="checkInTime" readonly="true" />
                        </p>
                        <p>
                            <label><label id="label_AssetCheckOutDate"></label></label>
                            <form:input path="checkOutTime" readonly="true" />
                        </p>

                        <p>
                            <label><label id="label_Keeper"></label></label>
                            <form:input path="keeper" readonly="true" />
                        </p>
                    </div>

                    <div id="picDiv">
                        <c:choose>
                            <c:when test="${asset.photoPath==''||asset.photoPath==null }">
                                <img src="image/asset/create/NoPic.jpg" id="aphoto" />
                            </c:when>
                            <c:otherwise>
                                <img src="${asset.photoPath }" id="aphoto" />
                            </c:otherwise>
                        </c:choose>

                    </div>
                    <div class="common-asset-input-right asset-input-panel">
                        <p>
                            <form:hidden path="photoPath" readonly="true"
                                placeholder="Please select a photo" class="truePhotoName" />

                        </p>

                        <p>
                            <label> <label id="label_AssetEntity"></label></label>
                            <form:input path="entity" readonly="true" />
                        </p>
                        <p>
                            <label><label id="label_AssetLocation"></label></label>
                            <form:input path="location" readonly="true" />
                        </p>
                        <p>
                            <label><label id="label_AssetUser"></label></label>
                            <form:input path="user.userName" readonly="true" />
                        </p>
                        <p>
                            <label><label id="label_AssetManufacture"></label></label>
                            <form:input path="manufacturer" readonly="true" />
                        </p>

                        <p>
                            <label><label id="label_AssetWarranty"></label></label>
                            <form:input path="warrantyTime" readonly="true" />
                        </p>

                        <p>
                            <label><label id="label_AssetVendor"></label></label>
                            <form:input path="vendor" readonly="true" />
                        </p>

                        <p class="p-textarea">
                            <label><label id="label_AssetMemo"></label></label>
                            <form:input path="memo" readonly="true" />
                        </p>
                    </div>
                </div>


                <%-- <%@include file="showAsSelect.txt"%>  --%>
                <div class="showAsSelect">

                    <div id="machineDetails" class="type-details" style="display: none">
                        <div class="commons">
                            <strong><label id="label_AssetMachineDetails"></label></strong>
                        </div>
                        <div class="detail-as-select-left">
                            <div class="asset-input-left asset-input-panel">
                                <p>
                                    <label><label id="label_AssetMachineSubtype"></label></label>
                                    <form:input path="machine.subtype" readonly="true" />
                                </p>
                                <p>
                                    <label><label id="label_AssetMachineSpeification"></label></label>
                                    <form:input path="machine.specification" readonly="true" />
                                </p>
                            </div>
                        </div>

                        <div class="detail-as-select-right">
                            <div class="asset-input-left asset-input-panel">
                                <p>
                                    <label><label id="label_AssetMachineAddtionalConfig"></label></label>
                                    <form:input path="machine.configuration" readonly="true" />

                                </p>
                                <p>
                                    <label><label id="label_AssetMachineMacAddress"></label></label>
                                    <form:input path="machine.address" readonly="true" />
                                </p>
                            </div>
                        </div>

                    </div>


                    <div id="monitorDetails" class="type-details" style="display: none">
                        <div class="commons">
                            <strong><label id="label_AssetMonitorDetails"></label></strong>
                        </div>
                        <div class="asset-input-left asset-input-panel">
                            <form:hidden path="monitor.id" />
                            <p>
                                <label><label id="label_AssetMonitorSize"></label></label>
                                <form:input path="monitor.size" id="size" class="l-text" />
                            </p>
                        </div>
                        <div class="asset-input-right asset-input-panel">
                            <p>
                                <label><label id="label_AssetMonitrDetails"></label></label>
                                <form:input path="monitor.detail" class="l-text" />
                            </p>
                        </div>
                    </div>

                    <div id="deviceDetails" class="type-details" style="display: none">
                        <div class="commons">
                            <strong><label id="label_AssetDeviceDetails"></label></strong>
                        </div>
                        <div class="asset-input-left asset-input-panel">
                            <form:hidden path="device.id" />
                            <p>
                                <label><label id="label_AssetDeviceSubtype"></label></label>
                                <form:input path="device.deviceSubtype.subtypeName"
                                    id="deviceSubtypeSelect" class="form-control l-text" />

                                <span class="image-span"></span>
                            </p>
                        </div>
                        <div class="asset-input-right asset-input-panel">
                            <p>
                                <label><label id="label_AssetDeviceConfiguration"></label></label>
                                <form:input path="device.configuration" id="configuration"
                                    class="l-text" />
                            </p>
                        </div>
                    </div>

                    <div id="softwareDetails" class="type-details"
                        style="display: none">
                        <div class="commons">
                            <strong><label id="label_AssetSoftwareDetails"></label></strong>
                        </div>
                        <div class="asset-input-left asset-input-panel">
                            <form:hidden path="software.id" />
                            <p>
                                <label><label id="label_AssetSoftwareVersion"></label></label>
                                <form:input path="software.version" id="version" class="l-text" />
                            </p>
                            <p>
                                <label><label id="label_AssetSoftwareLicenseKey"></label></label>
                                <form:input path="software.licenseKey" id="licenseKey"
                                    class="l-text" />
                            </p>
                        </div>
                        <div class="asset-input-right asset-input-panel">
                            <p>
                                <label><label id="label_AssetSoftwareMaxUserNum"></label></label>
                                <form:input path="software.maxUseNum" id="maxUseNum"
                                    class="l-text" />
                            </p>
                            <p>
                                <label><label id="label_AssetSoftwareAdditionalInfo"></label></label>
                                <form:input path="software.additionalInfo" id="additionalInfo"
                                    class="l-text" />
                            </p>

                        </div>
                    </div>

                    <div id="otherAssetsDetails" class="type-details"
                        style="display: none">
                        <div class="commons">
                            <strong><label id="label_AssetNonItAssetDetails"></label></strong>
                        </div>
                        <div class="asset-input-left asset-input-panel">
                            <form:hidden path="otherAssets.id" />
                            <p class="p-textarea">
                                <label><label id="label_AssetNonItAssetDetail"></label></label>
                                <span class="p-textarea-span"> <form:textarea
                                        path="otherAssets.detail" id="details" class="l-textarea" />
                                </span>
                            </p>
                        </div>

                        <div class="asset-input-right asset-input-panel"></div>
                    </div>

                </div>

                <div class="showAsSelfDefine">
                    <c:if test="${selfPropertyCount!='0' }">
                        <div class="commons">
                            <strong><label id="label_AssetSelfDefine"></label></strong>
                        </div>
                    </c:if>
                    <div class="detail-as-select-left">
                        <div class="asset-input-left asset-input-panel" id="selfLeft">
                            <c:forEach var="selfProperty" items="${showSelfProperties }">
                                <c:if test="${selfProperty.position == 'sortableLeft'}">
                                    <c:if test="${selfProperty.propertyType == 'inputType'}">
                                        <p>
                                            <label class="selfPropertyName">
                                            <c:out value="${selfProperty.enName}" /> </label>
                                            <input value="${selfProperty.value }" readonly="true">
                                        </p>
                                    </c:if>
                                    <c:if test="${selfProperty.propertyType == 'selectType'}">
                                        <p>
                                            <label class="selfPropertyName">
                                            <c:out value="${selfProperty.enName}" /> </label>
                                            <input type="text" readonly="true" value="${selfProperty.value }">
                                        </p>
                                    </c:if>
                                    <c:if test="${selfProperty.propertyType == 'dateType'}">
                                        <p>
                                            <label class="selfPropertyName">
                                            <c:out value="${selfProperty.enName}" /> </label>
                                            <input readonly="true" value="${selfProperty.value }">
                                        </p>
                                    </c:if>

                                    <c:if test="${selfProperty.propertyType == 'textAreaType'}">
                                        <p class="p-textarea">
                                            <label class="selfPropertyName"><c:out
                                                    value="${selfProperty.enName}" /> </label> <input readonly="true"
                                                value="${selfProperty.value }" />
                                        </p>
                                    </c:if>
                                </c:if>
                            </c:forEach>
                        </div>
                    </div>

                    <div class="detail-as-select-right">
                        <div class="asset-input-left asset-input-panel" id="selfRight">


                            <c:forEach var="selfProperty" items="${showSelfProperties }">
                                <c:if test="${selfProperty.position == 'sortableRight'}">
                                    <c:if test="${selfProperty.propertyType == 'inputType'}">
                                        <p>
                                            <label class="selfPropertyName">
                                            <c:out  value="${selfProperty.enName}" /> </label> 
                                            <input readonly="true" value="${selfProperty.value }">
                                        </p>
                                    </c:if>

                                    <c:if test="${selfProperty.propertyType == 'selectType'}">
                                        <p>
                                            <label class="selfPropertyName">
                                            <c:out value="${selfProperty.enName}" /> </label> 
                                            <input type="text"  readonly="true" value="${selfProperty.value }">
                                        </p>
                                    </c:if>

                                    <c:if test="${selfProperty.propertyType == 'dateType'}">
                                        <p>
                                            <label class="selfPropertyName">
                                            <c:out value="${selfProperty.enName}" /> </label>
                                            <input readonly ="true" value="${selfProperty.value }">
                                        </p>
                                    </c:if>

                                    <c:if test="${selfProperty.propertyType == 'textAreaType'}">
                                        <p>
                                            <label class="selfPropertyName">
                                            <c:out value="${selfProperty.enName}" /> </label>
                                            <input type="text" value="${selfProperty.value }" readonly="true" />
                                        </p>
                                    </c:if>
                                </c:if>
                            </c:forEach>
                        </div>
                    </div>
                </div>

                <div class="clear"></div>
                <input type="hidden" id="localeCode" name="localeCode"  value="${sessionScope.i18n }">
        </div>
    </div>
    </form:form>
	<jsp:include page="/WEB-INF/page/common/footer.jsp"></jsp:include>
    <script type="text/javascript" src="js/asset/viewAsset.js"></script>
    <script type="text/javascript" src="js/common/jquery-1.7.1.min.js"></script>
    <script type="text/javascript"
        src="js/common/jquery.i18n.properties-1.0.9.js"></script>
    <script type="text/javascript" src="dropDownList/dropDownList.js"></script>
    <link rel="stylesheet" type="text/css" href="dropDownList/themes/dropDownList.css" />
    <script type="text/javascript" src="js/common/jquery-ui-1.8.18.custom.min.js"></script>
        <script type="text/javascript" src="js/asset/assetCommon.js"></script>
        
</body>
</html>