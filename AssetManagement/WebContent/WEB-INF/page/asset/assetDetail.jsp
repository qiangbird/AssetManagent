<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>  
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
<link rel="stylesheet" href="autocomplete/css/autocomplete.css" type="text/css">
<link rel="stylesheet" href="css/asset/assetCommon.css" type="text/css">
<link rel="stylesheet" href="css/asset/viewAsset.css" type="text/css">
</head>
<body>
    <jsp:include page="../common/header.jsp"></jsp:include>
    <div id="blank">
       <a href="home"><spring:message code="navigator.home"></spring:message></a>
       <b>&gt;</b>
       <span><spring:message code="view.asset"></spring:message></span>
    </div>
    <div id="bodyMinHight">
    <div id="main">
        <div id="operation">
        <shiro:hasAnyRoles name="SYSTEM_ADMIN,IT,MANAGER,SPECIAL_ROLE">
        <c:if test="${uuid==null }">
            <%-- <a id="editBtn"><spring:message code="edit" /></a> --%>
            <button id="editBtn" value=<spring:message code="edit" />><spring:message code="edit" /></button>
            <shiro:hasAnyRoles name="SYSTEM_ADMIN,IT">
            <button id="copyBtn" value=<spring:message code="copy" />><spring:message code="copy" /></button>
            <button id="deleteBtn" value=<spring:message code="delete" />><spring:message code="delete" /></button>
            </shiro:hasAnyRoles>
        </c:if>
        </shiro:hasAnyRoles>
            <button id="cancelBtn" value=<spring:message code="cancel" />><spring:message code="cancel" /></button>
        </div>

        <div id="createAssetContent">
            <div class="commons ng-binding">
            <spring:message code="asset.common.property" />
            </div>

            <form:form action="*" method="post" id="assetFrom" modelAttribute="asset">
                <div class="common-requirement-property">
                    <div class="asset-input-left asset-input-panel">
                        <form:hidden path="id" />
                        <p>
                            <label><spring:message code="asset.id" /></label>
                            <form:input path="assetId" readonly="readonly" />
                        </p>
                        <p>
                            <label><spring:message code="asset.name" /></label>
                            <form:input path="assetName" readonly="readonly" />
                        </p>
                        <p>

                            <label><spring:message code="asset.type" /></label>
                            <form:input path="type" id="assetType" readonly="readonly" />

                        </p>
                        <p>
                            <label><spring:message code="asset.bar.code" /></label>
                            <form:input path="barCode" readonly="readonly" />
                        </p>
                        <p>
                            <label><spring:message code="asset.series.no" /></label>
                            <form:input path="seriesNo" readonly="readonly" />
                        </p>
                        <p>
                            <label><spring:message code="asset.po.no" /></label>
                            <form:input path="poNo" readonly="readonly" />

                        </p>
                        <p>
                            <label><spring:message code="asset.ownership" /></label>
                            <form:input path="ownerShip" readonly="readonly" />
                        </p>
                        <p>
                            <label><spring:message code="asset.used.by" /></label>
                            <form:input path="customer.customerName" readonly="readonly" />
                        </p>
                        <p>
                            <label><spring:message code="project" /></label>
                            <form:input path="project.projectName" readonly="readonly" />
                        </p>
                        <p>
                            <label><spring:message code="asset.status" /></label>
                            <form:input path="status" readonly="readonly" />
                        </p>
                        <p>
                            <label><spring:message code="asset.check.in.date" /></label>
                            <form:input path="checkInTime" readonly="readonly" />
                        </p>
                        <p>
                            <label><spring:message code="asset.check.out.date" /></label>
                            <form:input path="checkOutTime" readonly="readonly" />
                        </p>
                        <p>
                            <label><spring:message code="keeper" /></label>
                            <form:input path="keeper" readonly="readonly" />
                        </p>
                        <shiro:hasRole name="IT">
                        <p id="fixBlock">
                            <label><spring:message code="navigator.fixed.assets" /></label>
                            <form:input path="fixed" id="fixed" type="hidden" />
                            <span id="isFixed">Yes</span>
                            <span id="nonFixed">No</span>
                            
                        </p>
                        </shiro:hasRole>

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
                            <form:hidden path="photoPath" readonly="readonly"
                                placeholder="Please select a photo" class="truePhotoName" />

                        </p>

                        <p>
                            <label> <spring:message code="asset.entity" /></label>
                            <form:input path="entity" readonly="readonly" />
                        </p>
                         <p>
                            <label><spring:message code="asset.site" /></label>
                            <form:input path="site" readonly="readonly" />
                        </p>
                        <p>
                            <label><spring:message code="asset.location" /></label>
                            <form:input path="location" readonly="readonly" />
                        </p>
                        <p>
                            <label><spring:message code="asset.user" /></label>
                            <form:input path="user.userName" readonly="readonly" />
                        </p>
                        <p>
                            <label><spring:message code="asset.manufacture" /></label>
                            <form:input path="manufacturer" readonly="readonly" />
                        </p>

                        <p>
                            <label><spring:message code="asset.warranty" /></label>
                            <form:input path="warrantyTime" readonly="readonly" />
                        </p>

                        <p>
                            <label><spring:message code="asset.vendor" /></label>
                            <form:input path="vendor" readonly="readonly" />
                        </p>

                        <p>
                            <label><spring:message code="audit.trail" /></label>
                           <span id="viewAuditTrail"> <a href="transferLog/list?id=${id }"> <spring:message code="view" /> </a></span>
                        </p>
                        
                        <p>
                            <label><spring:message code="asset.memo" /></label>
                            <%-- <form:input path="memo" readonly="readonly" /> --%>
                            <form:textarea path="memo" rows="1" cols="15" readonly="readonly" />
                        </p>
                    </div>
                </div>
                    <div class="clear"></div>


                <%-- <%@include file="showAsSelect.txt"%>  --%>
                <div class="showAsSelect">

                    <div id="machineDetails" class="type-details" style="display: none">
                        <div class="commons">
                            <strong><spring:message code="asset.machine.details" /></strong>
                        </div>
                        <div class="detail-as-select-left">
                            <div class="asset-input-left asset-input-panel">
                                <p>
                                    <label><spring:message code="asset.machine.subtype" /></label>
                                    <form:input path="machine.subtype" readonly="readonly" />
                                </p>
                                <p>
                                    <label><spring:message code="asset.machine.speification" /></label>
                                    <form:input path="machine.specification" readonly="readonly" />
                                </p>
                            </div>
                        </div>

                        <div class="detail-as-select-right">
                            <div class="asset-input-left asset-input-panel">
                                <p>
                                    <label><spring:message code="asset.machine.addtional.config" /></label>
                                    <form:input path="machine.configuration" readonly="readonly" />

                                </p>
                                <p>
                                    <label><spring:message code="asset.machine.mac.address" /></label>
                                    <form:input path="machine.address" readonly="readonly" />
                                </p>
                            </div>
                        </div>
                    </div>
                        <div class="clear"></div>


                    <div id="monitorDetails" class="type-details" style="display: none">
                        <div class="commons">
                            <strong><spring:message code="asset.monitor.details" /></strong>
                        </div>
                        <div class="asset-input-left asset-input-panel">
                            <form:hidden path="monitor.id" />
                            <p>
                                <label><spring:message code="asset.monitor.size" /></label>
                                <form:input path="monitor.size" id="size" class="l-text"  readonly="readonly" />
                            </p>
                        </div>
                        <div class="asset-input-right asset-input-panel">
                            <p>
                                <label><spring:message code="asset.monitor.detail" /></label>
                                <form:input path="monitor.detail" class="l-text"  readonly="readonly" />
                            </p>
                        </div>
                    </div>
                        <div class="clear"></div>

                    <div id="deviceDetails" class="type-details" style="display: none">
                        <div class="commons">
                            <strong><spring:message code="asset.device.details" /></strong>
                        </div>
                        <div class="asset-input-left asset-input-panel">
                            <form:hidden path="device.id" />
                            <p>
                                <label><spring:message code="asset.device.subtype" /></label>
                                <form:input path="device.deviceSubtype.subtypeName"
                                    id="deviceSubtypeSelect" class="form-control l-text"  readonly="readonly" />

                                <span class="image-span"></span>
                            </p>
                        </div>
                        <div class="asset-input-right asset-input-panel">
                            <p>
                                <label><spring:message code="asset.device.configuration" /></label>
                                <form:input path="device.configuration" id="configuration"
                                    class="l-text"  readonly="readonly" />
                            </p>
                        </div>
                    </div>
                        <div class="clear"></div>

                    <div id="softwareDetails" class="type-details"
                        style="display: none">
                        <div class="commons">
                            <strong><spring:message code="asset.software.details" /></strong>
                        </div>
                        <div class="asset-input-left asset-input-panel">
                            <form:hidden path="software.id" />
                            <p>
                                <label><spring:message code="asset.software.version" /></label>
                                <form:input path="software.version" id="version" class="l-text"  readonly="readonly" />
                            </p>
                    <%--      <c:choose>
                            <c:when test="${asset.software.managerVisible }">
                            <p>
                                <label><spring:message code="asset.software.version" /></label>
                                <form:input path="software.version" id="version" class="l-text"  readonly="readonly" />
                            </p>
                            </c:when>
                            <c:otherwise>
                            <p>
                            <label><spring:message code="asset.software.version" /></label>
                             <shiro:hasAnyRoles name="EMPLOYEE,MANAGER,SPECIAL_ROLE">
                               <input type="text" value="**********" class="l-text"  readonly="readonly" >
                             </shiro:hasAnyRoles>
                             <shiro:hasAnyRoles name="SYSTEM_ADMIN,IT">
                               <form:input path="software.version" id="version" class="l-text"  readonly="readonly" />
                             </shiro:hasAnyRoles>
                             </p>
                            </c:otherwise>
                         </c:choose>  --%>
                            <c:choose>
                            <c:when test="${asset.software.managerVisible }">
                             <p>
                                <label><spring:message code="asset.software.additional.info" /></label>
                                <form:input path="software.additionalInfo" id="additionalInfo" class="l-text"  readonly="readonly" />
                            </p> 
                            </c:when>
                            <c:otherwise>
                             <shiro:hasAnyRoles name="SYSTEM_ADMIN,IT">
                             <p>
                                <label><spring:message code="asset.software.additional.info" /></label>
                                <form:input path="software.additionalInfo" id="additionalInfo" class="l-text"  readonly="readonly" />
                            </p> 
                             </shiro:hasAnyRoles>
                            </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="asset-input-right asset-input-panel">
                          <c:choose>
                            <c:when test="${asset.software.managerVisible }">
                            <p>
                                <label><spring:message code="asset.software.license.key" /></label>
                                 <form:input path="software.licenseKey" id="licenseKey"
                                    class="l-text"  readonly="readonly" />
                            </p>
                            </c:when>
                            <c:otherwise>
                            <p>
                            <label><spring:message code="asset.software.license.key" /></label>
                             <shiro:hasAnyRoles name="EMPLOYEE,MANAGER,SPECIAL_ROLE">
                               <input type="text" value="**********" class="l-text"  readonly="readonly" >
                             </shiro:hasAnyRoles>
                             <shiro:hasAnyRoles name="SYSTEM_ADMIN,IT">
                             <form:input path="software.licenseKey" id="licenseKey"
                                    class="l-text"  readonly="readonly" />
                             </shiro:hasAnyRoles>
                             </p>
                            </c:otherwise>
                         </c:choose> 

                        </div>
                    </div>
                        <div class="clear"></div>

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
                                        path="otherAssets.detail" id="details" class="l-textarea"  readonly="readonly" />
                                </span>
                            </p>
                        </div>

                        <div class="asset-input-right asset-input-panel"></div>
                    </div>
                        <div class="clear"></div>

                </div>
                <div class="clear"></div>

                <div class="showAsSelfDefine">
                    <c:if test="${selfPropertyCount!='0' }">
                        <div class="commons">
                            <strong><spring:message code="asset.self.define" /></strong>
                        </div>
                    </c:if>
                    <div class="detail-as-select-left">
                        <div class="asset-input-left asset-input-panel" id="selfLeft">
                            <c:forEach var="selfProperty" items="${showSelfProperties }">
                                <c:if test="${selfProperty.position == 'sortableLeft'}">
                                    <c:if test="${selfProperty.propertyType == 'inputType'}">
                                        <p>
                                            <label class="selfPropertyName">${selfProperty.enName} </label>
                                            <input value="${selfProperty.value }" readonly="readonly">
                                        </p>
                                    </c:if>
                                    <c:if test="${selfProperty.propertyType == 'selectType'}">
                                        <p>
                                            <label class="selfPropertyName">${selfProperty.enName} </label>
                                            <input type="text" readonly="readonly" value="${selfProperty.value }">
                                        </p>
                                    </c:if>
                                    <c:if test="${selfProperty.propertyType == 'dateType'}">
                                        <p>
                                            <label class="selfPropertyName">${selfProperty.enName} </label>
                                            <input readonly="readonly" value="${selfProperty.value }">
                                        </p>
                                    </c:if>

                                    <c:if test="${selfProperty.propertyType == 'textAreaType'}">
                                        <p class="p-textarea">
                                            <label class="selfPropertyName">${selfProperty.enName}</label> <input readonly="readonly"
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
                                            <label class="selfPropertyName">${selfProperty.enName} </label> 
                                            <input readonly="readonly" value="${selfProperty.value }">
                                        </p>
                                    </c:if>

                                    <c:if test="${selfProperty.propertyType == 'selectType'}">
                                        <p>
                                            <label class="selfPropertyName">${selfProperty.enName} </label> 
                                            <input type="text"  readonly="readonly" value="${selfProperty.value }">
                                        </p>
                                    </c:if>

                                    <c:if test="${selfProperty.propertyType == 'dateType'}">
                                        <p>
                                            <label class="selfPropertyName">${selfProperty.enName} </label>
                                            <input readonly="readonly" value="${selfProperty.value }">
                                        </p>
                                    </c:if>

                                    <c:if test="${selfProperty.propertyType == 'textAreaType'}">
                                        <p>
                                            <label class="selfPropertyName">${selfProperty.enName} </label>
                                            <input type="text" value="${selfProperty.value }" readonly="readonly" />
                                        </p>
                                    </c:if>
                                </c:if>
                            </c:forEach>
                        </div>
                    </div>
                </div>

                <div class="clear"></div>
                <input type="hidden" id="localeLanguage" value=${sessionScope.localeLanguage }>
        </div>
    </div>
    </div>
    </form:form>
	<jsp:include page="/WEB-INF/page/common/footer.jsp"></jsp:include>
    <script type="text/javascript" src="js/asset/viewAsset.js"></script>
    <script type="text/javascript" src="dropDownList/dropDownList.js"></script>
        <script type="text/javascript" src="js/asset/assetCommon.js"></script>
        
</body>
</html>