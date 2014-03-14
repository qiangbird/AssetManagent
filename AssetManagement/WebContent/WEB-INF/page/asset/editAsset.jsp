<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<title>Edit Assets</title>
<link rel="stylesheet" href="css/asset/assetCommon.css" type="text/css">
<link rel="stylesheet" href="css/asset/createAsset.css" type="text/css">
<link rel="stylesheet" href="css/asset/editAsset.css" type="text/css">
<link rel="stylesheet" href="autocomplete/css/autocomplete.css"
    type="text/css">
</head>
<body>
    <jsp:include page="../common/header.jsp"></jsp:include>
    <div id="blank">
       <a href="home"><spring:message code="navigator.home"></spring:message></a>
       <b>&gt;</b>
       <span><spring:message code="edit.asset" /></span>
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
                        <input type='hidden' name="id" id="id" value="${asset.id }"/>
                        <div class="row">
                            <span>*</span> <label><spring:message code="asset.id" /></label>
                            <input name="assetId" id="assetId" value="${asset.assetId }" maxlength="36" class="l-text"
                                data-required="true" readonly="readonly" />
                        </div>
                        <div class="row">
                            <span>*</span> <label><spring:message code="asset.name" /></label>
                            <input name="assetName" id="assetName" value="${asset.assetName }" maxlength="36" class="l-text"
                                data-required="true" placeholder="Please select a asset name" />
                        </div>
                        <div class="row">
                            <span>*</span> <label><spring:message code="asset.type" /></label>
                            <input name="type" id="assetType" value="${asset.type }" readonly="readonly" />
                        </div>
                        <div class="row">
                            <label><spring:message code="asset.bar.code" /></label>
                            <input name="barCode" id="barCode" value="${asset.barCode }" maxlength="36" class="l-text"
                                placeholder="Please input the barCode" />
                        </div>
                        <div class="row">
                            <label><spring:message code="asset.series.no" /></label>
                            <input name="seriesNo" id="seriesNo" value="${asset.seriesNo }" maxlength="36" class="l-text"
                                placeholder="Please input SeriesNo" />
                        </div>
                        <div class="row">
                            <label><spring:message code="asset.po.no" /></label>
                            <input name="poNo" id="poNo" value="${asset.poNo }" maxlength="36" class="l-text"
                                placeholder="Please input poNo" />

                        </div>
                        <div class="row">
                            <span>*</span> <label><spring:message code="asset.ownership" /></label>
                            <input name="ownerShip" id="ownership" value="${asset.ownerShip }" maxlength="36"
                             readonly="readonly" />
                        </div>
                        <div class="row">
                            <span>*</span> <label><spring:message code="asset.used.by" /></label>
                            <input name="customer.customerName" id="customerName"  value="${asset.customer.customerName }"
                                maxlength="36" readonly="readonly" />
                            <input type='hidden' id="customerCode" value="${asset.customer.customerCode }"/>
                        </div>
<%--                        <div class="showElement row" onblur="hideSelect()">
                            <c:forEach var="myCustomersItems" items="${myCustomers}">
                                <li class="showElementItems"><c:out
                                        value="${myCustomersItems.customerName}"></c:out></li>
                            </c:forEach>
                        </div> --%>
                        <div class="row">
                            <label><spring:message code="project" /></label>
                            <input name="project.projectName" id="project" value="${asset.project.projectName }"
                                 readonly="readonly" />
                            <input type='hidden'  id="projectCode" value="${asset.project.projectCode }" />
                        </div>
                        <div class="showProject"></div>

                        <div class="row">
                            <span>*</span> <label><spring:message code="asset.status" /></label>
                            <%-- <form:select name="status" id="selectedStatus"
                                items="${assetStatus}"></form:select> --%>
                                <input name="status" id="selectedStatus" value="${asset.status }" />
                        </div>
                        <div class="row">
                            <label><spring:message code="asset.check.in.date" /></label>
                            <input name="checkInTime" id="checkedInTime" value="${asset.checkInTime }"
                                class="checkIn" readonly="readonly"
                                placeholder="Please select checkInTime" />
                        </div>
                        <div class="row">
                            <label><spring:message code="asset.check.out.date" /></label>
                            <input name="checkOutTime" id="checkedOutTime" value="${asset.checkOutTime }"
                                class="checkOut" readonly="readonly"
                                placeholder="Please select checkOutTime" />
                        </div>

                        <div class="row">
                            <label><spring:message code="keeper" /></label>
                            <input name="keeper" id="keeperSelect" class="l-text"
                                readonly="readonly" value="${asset.keeper }" />
                        </div>
                        <shiro:hasRole name="IT">
                        <div id="minHeight" class="row">
                            <label><spring:message code="navigator.fixed.assets" /></label>
                            <input name="fixed" id="fixed" type="hidden" value="${asset.fixed }"/>
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

                            <c:when test="${asset.photoPath==''||asset.photoPath==null }">
                                <img src="image/asset/create/NoPic.jpg" id="aphoto" />
                            </c:when>
                            <c:otherwise>
                                <img src="${asset.photoPath }" id="aphoto" />
                            </c:otherwise>

                        </c:choose>

                    </div>
                    <div class="common-asset-input-right asset-input-panel">
                        <div>
                            <input type='hidden' name="photoPath" id="photoPath" readonly="readonly"
                                placeholder="Please select a photo" class="truePhotoName" />

                        </div>

                        <div class="row">
                            <span>*</span> <label> <spring:message code="asset.entity" /></label>
                            <%-- <form:select name="entity" id="selectedEntity"
                                items="${allEntity}"></form:select> --%>
                                <input name="entity" id="selectedEntity" value="${asset.entity }"  />
                        </div>

                        <div class="row">
                            <span>*</span> <label><spring:message code="asset.site" /></label>
<%--                             <c:set var="aa">
                            </c:set> --%>
                            <%-- <form:select name="site" id="selectedSite" class="select" items='${siteList}'>
                               <c:forEach var="siteListItems" items="${siteList}">
                                    <option value='${siteListItems.replace(" ","_") }'>${siteListItems}</option>
                                </c:forEach>
                            </form:select> --%>
                            <input name="site" id="selectedSite"  value="${asset.site }"  class="l-text"  />
                        </div>
                        <div class="row">
                            <span>*</span><label><spring:message code="asset.location" /></label>
                            <input name="location" id="selectedLocation" maxlength="36" value="${asset.location }"
                                class="l-text" placeholder="Please input a room" />
                            <input id="room"  value="${asset.location }" type="hidden"/>
                        </div>
                        <div class="row">
                            <label><spring:message code="asset.user" /></label>
                            <input name="user.userId" id="userId"  maxlength="36"
                                class="l-text" placeholder="Please input a user" />
                            <input id="userIdDefaultValue" value="${asset.user.userId }" type="hidden" />
                            <input id="userName" value="${asset.user.userName }" type="hidden" />
                        </div>
                        <div class="row">
                            <label><spring:message code="asset.manufacture" /></label>
                            <input name="manufacturer" id="manufacturer" value="${asset.manufacturer }" maxlength="36" class="l-text"
                                placeholder="Please input manufacturer" />
                        </div>

                        <div class="row">
                            <label><spring:message code="asset.warranty" /></label>
                            <input name="warrantyTime" id="assetWarranty" value="${asset.warrantyTime }"
                                 readonly="readonly"
                                placeholder="Please select assetWarranty" />
                        </div>

                        <div class="row">
                            <label><spring:message code="asset.vendor" /></label>
                            <input name="vendor" id="monitorVendor" class="l-text" value="${asset.vendor }"
                                placeholder="Please input vendor" />
                        </div>

                        <div class="p-textarea row">
                            <label><spring:message code="asset.memo" /></label> <span
                                class="p-textarea-span"> <textarea name="memo"
                                    class="l-textarea" >${asset.vendor }</textarea>
                            </span>
                        </div>
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
                            <input type='hidden' name="machine.id" id="machineId" value="${asset.machine.id }"/>
                            <div class="asset-input-left asset-input-panel">
                                <div class="row">
                                    <span>*</span>
                                    <label><spring:message code="asset.machine.subtype" /></label>
                                    <%-- <form:select name="machine.subtype" id="machineType"
                                        items="${machineTypes}"></form:select> --%>
                                        <input name="machine.subtype" id="machineType" value="${asset.machine.subtype }" class=""/>
                                </div>
                                <div class="p-textarea row">
                                    <label><spring:message code="asset.machine.speification" /></label>
                                    <span class="p-textarea-span"> <textarea
                                            name="machine.specification" id="specification" value="${asset.machine.specification }"
                                            class="form-control l-textarea" > </textarea>
                                    </span>
                                </div>
                            </div>
                        </div>

                        <div class="detail-as-select-right">
                            <div class="asset-input-left asset-input-panel">
                                <div class="p-textarea row">
                                    <label><spring:message code="asset.machine.addtional.config" /></label>
                                    <span class="p-textarea-span"> <textarea
                                            name="machine.configuration" class="l-textarea" value="${asset.machine.configuration }"></textarea>
                                    </span>
                                </div>
                                <div class="row">
                                    <label><spring:message code="asset.machine.mac.address" /></label>
                                    <input name="machine.address" class="l-text" value="${asset.machine.address }"/>
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
                            <input type='hidden' name="monitor.id" id="monitorId" value="${asset.monitor.id }"/>
                            <div class="row">
                                <label><spring:message code="asset.monitor.size" /></label>
                                <input name="monitor.size" id="size" value="${asset.monitor.size }" class="l-text" />
                            </div>
                        </div>
                        <div class="asset-input-right asset-input-panel">
                            <div class="p-textarea row">
                                <label><spring:message code="asset.monitor.detail" /></label> <span
                                    class="p-textarea-span"> <textarea
                                        name="monitor.detail" id="monitorDetail"
                                        class="form-control l-textarea" >${asset.monitor.detail }</textarea>

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
                            <input type='hidden' name="device.id" id="deviceId" value="${asset.device.id }"/>
                            <div class="row">
                                <label><spring:message code="asset.device.subtype" /></label>
                                <input name="device.deviceSubtype.subtypeName"  value="${asset.device.deviceSubtype.subtypeName }"
                                    id="deviceSubtypeSelect" class="form-control l-text" />

                                <span class="image-span"></span>
                            </div>
                        </div>
                        <div class="asset-input-right asset-input-panel">
                            <div class="row">
                                <label><spring:message code="asset.device.configuration" /></label>
                                <input name="device.configuration" id="configuration" value="${asset.device.configuration }"
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
                            <input type='hidden' name="software.id" id="softwareId" value="${asset.software.id }"/>
                            <div class="row">
                               <label><spring:message code="asset.software.version" /></label>
                               <input name="software.version" id="version" value="${asset.software.version }" class="l-text" />
                           </div>
                           <c:choose>
                           <c:when test='${sessionScope.userRoleList.contains("IT") }'>
                       <%-- <div>
                           <label><spring:message code="asset.software.version" /></label>
                           <input name="software.version" id="version" class="l-text" />
                       </div> --%>
                           <div class="row">
                                <label><spring:message code="asset.software.additional.info" /></label>
                                <input name="software.additionalInfo" id="additionalInfo" value="${asset.software.additionalInfo }"
                                        class="l-text" />
                           </div>
                           </c:when>
                           <c:otherwise>
                           <c:choose>
                           <c:when test="${asset.software.managerVisible }">
                            <div class="row">
                               <label><spring:message code="asset.software.version" /></label>
                               <input name="software.version" id="version" value="${asset.software.version }" class="l-text" readonly="readonly"/>
                           </div>
                           <div class="row">
                                <label><spring:message code="asset.software.additional.info" /></label>
                                <input name="software.additionalInfo" id="additionalInfo" value="${asset.software.additionalInfo }"
                                        class="l-text" readonly="readonly"/>
                           </div>
                           </c:when>
                           
                           <c:otherwise>
                           <input type='hidden' name="software.version" id="version" value="${asset.software.version }" 
                                        class="l-text" readonly="readonly"/>
                           <input type='hidden' name="software.additionalInfo" id="additionalInfo" value="${asset.software.additionalInfo }"
                                        class="l-text" readonly="readonly"/>
                           <div class="row">
                               <label><spring:message code="asset.software.version" /></label>
                               <input type="text" value="**********" class="l-text"  readonly="readonly" >
                           </div>
                           </c:otherwise>
                           </c:choose>
                           </c:otherwise>
                           </c:choose>

                       </div>
                        <div class="asset-input-right asset-input-panel">
                           <c:choose>
                           <c:when test='${sessionScope.userRoleList.contains("IT") }'>
                           <div class="row">
                              <label><spring:message code="asset.software.license.key" /></label>
                               <input name="software.licenseKey" id="licenseKey" value="${asset.software.licenseKey }" class="l-text"/>
                           </div>             
                                    
                            <div id="software_manager_visible"  class="row">
                                <input id="visible" name="software.managerVisible" value="${asset.software.managerVisible }" style="display: none;"/>
                                <a class="visibleCheckBoxOff" ></a>
                                <label id="manager_visible"><spring:message code="asset.software.visible.for.manager" /></label>
                            </div>
                            </c:when>
                            <c:otherwise>
                           <div  class="row">
                              <label><spring:message code="asset.software.license.key" /></label>
                               <c:choose>
                               <c:when test="${asset.software.managerVisible }">
                                  <input name="software.licenseKey" id="licenseKey" value="${asset.software.licenseKey }"
                                            class="l-text"  readonly="readonly" />
                               </c:when>
                               <c:otherwise>
                                    <input type='hidden' name="software.licenseKey" id="licenseKey"
                                                class="l-text"  readonly="readonly" value="${asset.software.licenseKey }"/>
                                    <input type="text" value="**********" class="l-text"  readonly="readonly" >
                               </c:otherwise>
                               </c:choose>
                            </div>
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
                            <input type='hidden' name="otherAssets.id" id="otherAssetsId" value="${asset.otherAssets.id }" />
                            <div class="p-textarea row">
                                <label><spring:message code="asset.non.it.asset.detail" /></label>
                                <span class="p-textarea-span"> <textarea
                                        name="otherAssets.detail" id="otherAssetsDetails" class="l-textarea" >value="${asset.otherAssets.detail }"</textarea>
                                </span>
                            </div>
                        </div>

                        <div class="asset-input-right asset-input-panel"></div>
                    </div>
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
                                        <div class="row">
                                            <%-- <c:if test="${selfProperty.isRequired}">
                        <span>*</span>                  
                        </c:if>  --%>
                                            <label class="selfPropertyName">${selfProperty.enName}</label> <input type="hidden"
                                                class="selfId" value="${selfProperty.id}"> <input
                                                type="text" class="l-text selfPropertyVlaue"
                                                name="${selfProperty.enName}" id="${selfProperty.id}"
                                                value="${selfProperty.value }">
                                        </div>
                                    </c:if>
                                    <c:if test="${selfProperty.propertyType == 'selectType'}">
                                        <div class="row">
                                            <label class="selfPropertyName">${selfProperty.enName}</label>
                                            <%-- <input type="text" class="l-select" name="${selfProperty.enName}" id="${selfProperty.id}" value="${selfProperty.value }"> --%>

                                            <input type="hidden" class="selfId"
                                                value="${selfProperty.id}"> <select id="DropList"
                                                class="l-select dropDownSelect selfPropertyVlaue">
                                                <c:forTokens var="selectItems" delims="#"
                                                    items="${selfProperty.value }">
                                                    <option value="${selectItems }">${selectItems }</option>
                                                </c:forTokens>
                                            </select>
                                        </div>
                                    </c:if>
                                    <c:if test="${selfProperty.propertyType == 'dateType'}">
                                        <div class="row">
                                            <label class="selfPropertyName">${selfProperty.enName} </label> <input type="hidden"
                                                class="selfId" value="${selfProperty.id}"> <input
                                                type="text" readonly="readonly" class="l-date selfPropertyVlaue"
                                                name="${selfProperty.enName}" id="${selfProperty.id}"
                                                value="${selfProperty.value }">
                                        </div>
                                    </c:if>

                                    <c:if test="${selfProperty.propertyType == 'textAreaType'}">
                                        <div class="p-textarea row">
                                            <label class="selfPropertyName"><c:out
                                                    value="${selfProperty.enName}" /> </label> <input type="hidden"
                                                class="selfId" value="${selfProperty.id}">
                                            <textarea class="l-textArea selfPropertyVlaue"
                                                name="${selfProperty.enName}" id="${selfProperty.id}">${selfProperty.value }
                                                 </textarea>
                                        </div>
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
                                        <div class="row">
                                            <%--    <c:if test="${selfProperty.isRequired}">
                        <span>*</span>                  
                        </c:if> --%>
                                            <label class="selfPropertyName">${selfProperty.enName} </label> <input type="hidden"
                                                class="selfId" value="${selfProperty.id}"> <input
                                                type="text" class="l-text selfPropertyVlaue"
                                                name="${selfProperty.enName}" id="${selfProperty.id}"
                                                value="${selfProperty.value }">
                                        </div>
                                    </c:if>

                                    <c:if test="${selfProperty.propertyType == 'selectType'}">
                                        <div class="row">
                                            <label class="selfPropertyName">${selfProperty.enName} </label> <input type="hidden"
                                                class="selfId" value="${selfProperty.id}"> <select
                                                id="DropList"
                                                class="l-select dropDownSelect selfPropertyVlaue">
                                                <c:forTokens var="selectItems" delims="#"
                                                    items="${selfProperty.value }">
                                                    <option value="${selectItems }">${selectItems }</option>
                                                </c:forTokens>
                                            </select>


                                        </div>
                                    </c:if>

                                    <c:if test="${selfProperty.propertyType == 'dateType'}">
                                        <div class="row">
                                            <label class="selfPropertyName">${selfProperty.enName} </label> <input type="hidden"
                                                class="selfId" value="${selfProperty.id}"> <input
                                                type="text" readonly="readonly" class="l-date selfPropertyVlaue"
                                                name="${selfProperty.enName}" id="${selfProperty.id}"
                                                value="${selfProperty.value }">
                                        </div>
                                    </c:if>

                                    <c:if test="${selfProperty.propertyType == 'textAreaType'}">
                                        <div class="p-textarea row">
                                            <label class="selfPropertyName">${selfProperty.enName} </label> <input type="hidden"
                                                class="selfId" value="${selfProperty.id}">
                                            <textarea class="l-textArea selfPropertyVlaue"
                                                name="${selfProperty.enName}" id="${selfProperty.id}"  >${selfProperty.value }</textarea>
                                        </div>
                                    </c:if>
                                </c:if>
                            </c:forEach>


                        </div>
                    </div>
                </div>

                <div class="clear"></div>
                <div id="bodyShadow"></div>
                <div class="operation">
                    <div class="operation_location">
                        <input type="button" value="<spring:message code="save" />" 
                        class="submit-button" id="editSubmitForm" />
                        <input type="button" value='<spring:message code="cancel" />'
                            id="cancelCopy" onclick="window.history.back();" />
                    </div>
                    <div id="showError"></div>
                </div>
            <div class="clear"></div>
        </div>
        </div>
        </div>
        <jsp:include page="/WEB-INF/page/common/footer.jsp"></jsp:include>
        <script type="text/javascript" src="js/common/jquery-validate.min.js"></script>
        <script type="text/javascript" src="js/common/jquery.form.js"></script>
        <script type="text/javascript" src="autocomplete/js/autocomplete.js"></script>
        <script type="text/javascript" src="dropDownList/dropDownList.js"></script>
        <script type="text/javascript" src="js/asset/assetCommon.js"></script>
        <script type="text/javascript" src="js/common/messageBarCommon.js"></script>
        <script type="text/javascript" src="js/asset/editAsset.js"></script>
        
        <!-- add front page validation -->
        <script type="text/javascript" src="js/common/validation.js"></script>
        <script type="text/javascript" src="messageBar/js/messagebar.js"></script>
</body>
</html>