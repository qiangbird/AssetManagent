<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<title>Edit Assets</title>
<link rel="stylesheet" href="css/asset/createAsset.css" type="text/css">
<link rel="stylesheet" href="css/asset/editAsset.css" type="text/css">
<link rel="stylesheet" href="css/common/jquery-ui.css" type="text/css">
<link rel="stylesheet" href="autocomplete/css/autocomplete.css" type="text/css">
<link rel="stylesheet" href="css/asset/assetCommon.css" type="text/css">
</head>
<body>
<jsp:include page="../home/head.jsp"></jsp:include>
<div id="main">
   <div class="home">
        <span class="root-back"><a href="#"><label id="label_Home"></a></span>
        <span class="catelog-in-line">></span> 
        <span class="breadCrum"><label id="label_EditAsset"></span>
    </div>
    <div id="createAssetContent">
        <div class="commons ng-binding">
        <label id="label_CommonProperty"></label>
        </div>
      <div id="mainTop">
       <input type="button" />
       <form id="uploadForm" method="post" enctype="multipart/form-data">
          <input type="file" name="file"
               class="file" id="file" onchange="document.getElementById('photoPath').value=this.value" />
       </form>  
       </div> 
        
        
    <form:form action="asset/update" method="post" id="assetFrom" modelAttribute="asset">
    <div class="common-requirement-property">
    <div class="asset-input-left asset-input-panel">
        <form:hidden path="id"/>
        <p>
            <span>*</span> <label><label id="label_AssetId"></label></label>
            <form:input path="assetId" maxlength="36" cssClass="l-text" data-required="true" readonly="true"/>
        </p>
        <p>
            <span>*</span> <label><label id="label_AssetName"></label></label>
            <form:input path="assetName" maxlength="36" cssClass="l-text" data-required="true" placeholder="Please select a asset name"/>
        </p>
        <p>
        
            <span>*</span> <label><label id="label_AssetType"></label></label>
                 <form:input path="type" id="assetType" readonly="true" />
                
        </p>
        <p>
            <label><label id="label_AssetBarCode"></label></label>
            <form:input path="barCode" maxlength="36" cssClass="l-text"
                placeholder="Please input the barCode" />
        </p>
        <p>
            <label><label id="label_AssetSeriesNo"></label></label>
            <form:input path="seriesNo" maxlength="36" cssClass="l-text" placeholder="Please input SeriesNo" />
        </p>
        <p>
            <label><label id="label_AssetPoNo"></label></label>
            <form:input path="poNo" maxlength="36" cssClass="l-text"
                placeholder="Please input poNo" />
    
        </p>
        <p>
        <span>*</span> <label><label id="label_AssetOwnerShip"></label></label>
            <form:input path="ownerShip" id="ownership" maxlength="36"
                                cssClass="l-select" cssErrorClass="l-select-error"
                                placeholder="Please select OwnerShip" />
        </p>
        <p>
            <span>*</span> <label><label id="label_AssetUsedBy"></label></label>
            <form:input path="customer.customerName" id="customerName" maxlength="36"
            cssClass="l-select" placeholder="Please select Custumer" readonly="true"/>
            <form:hidden path="customer.customerCode"  id="customerCode"/>
                
    
        </p>
        <div class="showElement" onblur="hideSelect()">
            <c:forEach var="myCustomersItems" items="${myCustomers}">
                <li class="showElementItems">
                <c:out value="${myCustomersItems.customerName}"></c:out>
                </li>
            </c:forEach>
        </div>
        
         <p>
             <label><label id="label_Project"></label></label>
               <form:input path="project.projectName" id="project" class="l-select" readonly="true"/>
                <form:hidden path="project.projectCode" id="projectCode" />
        </p>
        <div class="showProject">
        </div>
        
        <p>
            <span>*</span> <label><label id="label_AssetStatus"></label></label>
                <form:select path="status" id="selectedStatus" items="${assetStatus}"></form:select>
        </p>
         <p>
           <label><label id="label_AssetCheckInDate"></label></label>
           <form:input path="checkInTime" id="checkedInTime"
               cssClass="l-date" readonly="true"
               placeholder="Please select checkInTime" />
       </p>
       <p>
           <label><label id="label_AssetCheckOutDate"></label></label>
           <form:input path="checkOutTime" id="checkedOutTime"
               cssClass="l-date" readonly="true"
               placeholder="Please select checkOutTime" />
       </p>
       
         <p>
             <label><label id="label_Keeper"></label></label>
              <form:input path="keeper" id="keeperSelect" class="l-text" readonly="readonly"/>
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
               placeholder="Please select a photo" class="truePhotoName"/>
   
       </p> 
   
       <p>
           <span>*</span> <label> <label id="label_AssetEntity"></label></label>
           <form:select path="entity" id="selectedEntity" items="${allEntity}"></form:select>
       </p>
   
      <p>
            <span>*</span> <label><label id="label_AssetLocation"></label></label>
            <form:select path="location" id="selectedLocation" items="${siteList}"></form:select>
        </p>
        <p>
            <label><label id="label_AssetUser"></label></label>
            <form:input path="user.userName" id="assetUser" maxlength="36"
                cssClass="l-text" placeholder="Please input a user" />
                <form:hidden path="user.userId" id="userId"/>
        </p>
        <p>
            <label><label id="label_AssetManufacture"></label></label>
            <form:input path="manufacturer" maxlength="36" cssClass="l-text"
                placeholder="Please input manufacturer" />
        </p>
   
       <p>
           <label><label id="label_AssetWarranty"></label></label>
           <form:input path="warrantyTime" id="assetWarranty"
               cssClass="l-date" readonly="true"
               placeholder="Please select assetWarranty" />
       </p>
   
       <p>
           <label><label id="label_AssetVendor"></label></label>
           <form:input path="vendor" id="monitorVendor" cssClass="l-text"
               placeholder="Please input vendor" />
       </p>
   
       <p class="p-textarea">
           <label><label id="label_AssetMemo"></label></label>
           <span class="p-textarea-span"> <form:textarea path="memo"
                   cssClass="l-textarea" />
               </span>
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
                    <form:hidden path="machine.id"/>
                        <div class="asset-input-left asset-input-panel">
                            <p>
                                <span>*</span> <label><label id="label_AssetMachineSubtype"></label></label>
                                 <form:input path="machine.subtype" id="machineType" class="l-select" readonly="readonly" placeholder="Please select type"/>
                            </p>
                            <div class="machineType" onblur="hideSelect()">
                                <c:forEach var="machineTypeItems" items="${machineTypes}">
                                <li class="machineTypeItems"> <c:out value="${machineTypeItems}"></c:out></li>
                                </c:forEach> 
                            </div>
                            <p class="p-textarea">
                                <label><label id="label_AssetMachineSpeification"></label></label> <span class="p-textarea-span">
                                    <form:textarea path="machine.specification" id="specification" class="form-control l-textarea" />
                                </span>
                            </p>
                        </div>
                    </div>

                    <div class="detail-as-select-right">
                        <div class="asset-input-left asset-input-panel">
                            <p class="p-textarea">
                                <label><label id="label_AssetMachineAddtionalConfig"></label></label> <span class="p-textarea-span">
                                      <form:textarea path="machine.configuration" class="l-textarea" />
                                     
                                </span>
                            </p>
                            <p>
                                <label><label id="label_AssetMachineMacAddress"></label></label> 
                                <form:input path="machine.address" class="l-text"/>
                            </p>
                        </div>
                    </div>

                </div>


                <div id="monitorDetails" class="type-details" style="display: none">
                    <div class="commons">
                        <strong><label id="label_AssetMonitorDetails"></label></strong>
                    </div>
                    <div class="asset-input-left asset-input-panel">
                    <form:hidden path="monitor.id"/>
                        <p>
                            <label><label id="label_AssetMonitorSize"></label></label> 
                           <form:input path="monitor.size" id="size" class="l-text"/>
                        </p>
                    </div>
                    <div class="asset-input-right asset-input-panel">
                    <p class="p-textarea">
                            <label><label id="label_AssetMonitrDetails"></label></label> <span class="p-textarea-span"> 
                                    <form:textarea path="monitor.detail" id="details" class="form-control l-textarea"/>
                                    
                            </span>
                        </p>
                    </div>
                </div>

                <div id="deviceDetails" class="type-details" style="display: none">
                    <div class="commons">
                        <strong><label id="label_AssetDeviceDetails"></label></strong>
                    </div>
                    <div class="asset-input-left asset-input-panel">
                    <form:hidden path="device.id"/>
                        <p>
                            <label><label id="label_AssetDeviceSubtype"></label></label>
                                <form:input path="device.deviceSubtype.subtypeName" id="deviceSubtypeSelect" class="form-control l-text"/>
                                
                                 <span class="image-span"></span>
                        </p>
                    </div>
                    <div class="asset-input-right asset-input-panel">
                    <p>
                            <label><label id="label_AssetDeviceConfiguration"></label></label>
                                <form:input path="device.configuration" id="configuration" class="l-text"/> 
                        </p>
                    </div>
                </div> 
                
                <div id="softwareDetails" class="type-details" style="display: none">
                    <div class="commons">
                        <strong><label id="label_AssetSoftwareDetails"></label></strong>
                    </div>
                    <div class="asset-input-left asset-input-panel">
                    <form:hidden path="software.id"/>
                        <p>
                            <label><label id="label_AssetSoftwareVersion"></label></label>
                             <form:input path="software.version" id="version" class="l-text"/>
                        </p>
                        <p>
                            <label><label id="label_AssetSoftwareLicenseKey"></label></label> 
                                <form:input path="software.licenseKey" id="licenseKey" class="l-text"/>
                        </p>
                    </div>
                    <div class="asset-input-right asset-input-panel">
                        <p>
                            <span>*</span> <label><label id="label_AssetSoftwareMaxUserNum"></label></label> 
                                <form:input path="software.maxUseNum" id="maxUseNum" class="l-text"/>
                        </p>
                        <p>
                            <label><label id="label_AssetSoftwareAdditionalInfo"></label></label> 
                                
                                <form:input path="software.additionalInfo" id="additionalInfo" class="l-text"/>
                        </p> 
                        
                    </div>
                </div>

                <div id="otherAssetsDetails" class="type-details" style="display: none">
                    <div class="commons">
                        <strong><label id="label_AssetNonItAssetDetails"></label></strong>
                    </div>
                    <div class="asset-input-left asset-input-panel">
                     <form:hidden path="otherAssets.id"/>
                        <p class="p-textarea">
                            <label><label id="label_AssetNonItAssetDetail"></label></label> <span class="p-textarea-span"> 
                            <form:textarea path="otherAssets.detail" id="details" class="l-textarea"/>
                            </span>
                        </p>
                    </div>

                    <div class="asset-input-right asset-input-panel">
                    </div>
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
					 	<%-- <c:if test="${selfProperty.isRequired}">
						<span>*</span>					
						</c:if>  --%>
						<label class="selfPropertyName"><c:out value="${selfProperty.enName}"/> </label>
						<input type="hidden" class="selfId" value="${selfProperty.id}">
						<input type="text" class="l-text selfPropertyVlaue" name="${selfProperty.enName}" id="${selfProperty.id}" value="${selfProperty.value }">
                        </p>
                        </c:if>
                        <c:if test="${selfProperty.propertyType == 'selectType'}">
                        <p>
                        <label class="selfPropertyName"><c:out value="${selfProperty.enName}"/> </label>
                        <%-- <input type="text" class="l-select" name="${selfProperty.enName}" id="${selfProperty.id}" value="${selfProperty.value }"> --%>
                        
                        <input type="hidden" class="selfId" value="${selfProperty.id}">
                        <select id="DropList" class="l-select dropDownSelect selfPropertyVlaue">
                        <c:forTokens var="selectItems" delims="#" items="${selfProperty.value }">
                        <option value="${selectItems }">${selectItems }</option>
                        </c:forTokens>
                        </select>
                        </p>
                        </c:if>
                        <c:if test="${selfProperty.propertyType == 'dateType'}">
                        <p>
                        <label class="selfPropertyName"><c:out value="${selfProperty.enName}"/> </label>
                        <input type="hidden" class="selfId" value="${selfProperty.id}">
                        <input type="text" class="l-date selfPropertyVlaue" name="${selfProperty.enName}" id="${selfProperty.id}" value="${selfProperty.value }">
                        </p>
                        </c:if>
                        
                        <c:if test="${selfProperty.propertyType == 'textAreaType'}">
                        <p class="p-textarea">
                        <label class="selfPropertyName"><c:out value="${selfProperty.enName}"/> </label>
                        <input type="hidden" class="selfId" value="${selfProperty.id}">
                        <textarea class="l-textArea selfPropertyVlaue" name="${selfProperty.enName}" id="${selfProperty.id}"><c:out value="${selfProperty.value }"/> </textarea>
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
                    <%--    <c:if test="${selfProperty.isRequired}">
                        <span>*</span>                  
                        </c:if> --%>
                        <label class="selfPropertyName"><c:out value="${selfProperty.enName}"/> </label>
                        <input type="hidden" class="selfId" value="${selfProperty.id}">
                        <input type="text" class="l-text selfPropertyVlaue" name="${selfProperty.enName}" id="${selfProperty.id}" value="${selfProperty.value }">
                        </p>
                        </c:if>
                        
                        <c:if test="${selfProperty.propertyType == 'selectType'}">
                        <p>
                        <label class="selfPropertyName"><c:out value="${selfProperty.enName}"/> </label>
                        <input type="hidden" class="selfId" value="${selfProperty.id}">
                        <select id="DropList" class="l-select dropDownSelect selfPropertyVlaue">
                        <c:forTokens var="selectItems" delims="#" items="${selfProperty.value }">
                        <option value="${selectItems }">${selectItems }</option>
                        </c:forTokens>
                        </select>
                        
                        
                        </p>
                        </c:if>
                        
                        <c:if test="${selfProperty.propertyType == 'dateType'}">
                        <p>
                        <label class="selfPropertyName"><c:out value="${selfProperty.enName}"/> </label>
                        <input type="hidden" class="selfId" value="${selfProperty.id}">
                        <input type="text" class="l-date selfPropertyVlaue" name="${selfProperty.enName}" id="${selfProperty.id}" value="${selfProperty.value }">
                        </p>
                        </c:if>
                        
                        <c:if test="${selfProperty.propertyType == 'textAreaType'}">
                        <p class="p-textarea">
                        <label class="selfPropertyName"><c:out value="${selfProperty.enName}"/> </label>
                        <input type="hidden" class="selfId" value="${selfProperty.id}">
                        <textarea class="l-textArea selfPropertyVlaue" name="${selfProperty.enName}" id="${selfProperty.id}"><c:out value="${selfProperty.value }"/> </textarea>
                        </p>
                        </c:if>
                        </c:if>
                        </c:forEach>
						
						
						</div>
					</div>
				</div>  
				
				
				
				
  <div class="clear"></div>
 
<!-- <div class="showAsSelfDefine">
                    <div>
                        <strong><label id="label_AssetSelfDefine"></label></strong>
                    </div>
                    </div>
        
        <div id="sortableTemplate">
        <ul class="sortable" >
            <li class="dragDiv">
                <input type="hidden" id="id" value=""/>
                <input type="hidden" id="isNew" value="no"/>
                <div class="operateProperty">
                    <span class="editProperty" ></span> 
                    <span class="deleteProperty" ></span>
                 </div>
                 <label class="shortLeftText">
                     <span class="propertyRequired" >*</span>
                     <span class="propertyText">property.enName</span>
                 </label>
                 <input type="text" value="" class="inputText getValue" maxlength="36"/>
                 <div>
                     <input type="text" value="" id="inputSelectType" class="inputSelectType getValue" maxlength="36"/>
                 </div>
                 <input id="propertyTime" class="inputDate getValue" readonly="readonly" placeholder="Please input time" />
                 <textarea  rows="3" value="" cols="" class="inputTextarea getValue" maxlength="1024"></textarea>
            </li>
        </ul>
    </div>
    <div class="properties">
        <ul id="sortableLeft" class="sortable" ></ul>
        <ul id="sortableRight" class="sortable" ></ul>
    </div> -->




   <div class="asset-batch-create"></div>
   <div id="bodyShadow"></div>
   <div class="operation">
       <div class="operation_location">
           <input type="button" value="Submit" class="submit-button" id="submitForm"/> <input
               type="button" value="Cancel" class="cancel-button"
               onclick="window.history.back();" />
       </div>
       <div id="showError"></div>
   </div>
   </form:form>
<input type="hidden" id="localeCode" name="localeCode" value="${sessionScope.i18n }">
</div>

<script type="text/javascript" src="js/common/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="js/common/jquery-ui-1.8.18.custom.min.js"></script>
<script type="text/javascript" src="js/common/jquery.i18n.properties-1.0.9.js"></script>
<script type="text/javascript" src="js/common/jquery-validate.min.js"></script>
<script type="text/javascript" src="js/common/jquery.form.js"></script>
<script type="text/javascript" src="js/asset/editAsset.js"></script>
<script type="text/javascript" src="autocomplete/js/autocomplete.js"></script>
<script type="text/javascript" src="dropDownList/dropDownList.js"></script>
<link rel="stylesheet" type="text/css" href="dropDownList/themes/dropDownList.css" />
<script type="text/javascript" src="js/asset/assetCommon.js"></script>
</body>
</html>