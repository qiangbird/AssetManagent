<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%> 
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<html>
<head>
<base href="<%=basePath%>">
<link rel="stylesheet" href="css/customize/customizedProperty/selfProperty.css" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<jsp:include page="../../common/header.jsp" />
<div id="blank">
   <a href="home"><spring:message code="navigator.home"></spring:message></a>
   <b>&gt;</b>
   <span><spring:message code="navigator.self.property"></spring:message></span>
</div>
<div id="bodyMinHight">
<div id="propertyContent">
	<form id="propertiesForm" action="" method="post">
	<input name="selfProperties" id="selfProperties" type="hidden" value="" />
    <div class="customer-assetType">
        <div class="customerName">
            <label class="shortLeftText">
                <span class="propertyRequired">*</span>
                 <span class="propertyText"><spring:message code="customer"></spring:message></span>
            </label>
            <input id="customerCode" class="" type="text" value=""  />
            <input id="customerName"  type="hidden" value=""  />
        </div>
        <div class="assetType">
            <label class="shortLeftText">
                <span class="propertyRequired">*</span>
                 <span class="propertyText"><spring:message code="customized.property.assetType"></spring:message></span>
            </label>
            <input id="assetType" class="" type="text" value="" />
        </div>
    </div>
    </form>
    <div class="clear"></div>
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
                     <span class="propertyText"></span>
                 </label>
                 <input type="text" value="" class="inputText" maxlength="36"/>
                 <div>
                     <input type="text" value="" id="inputSelectType" class="inputSelectType" maxlength="36"/>
                 </div>
                 <input id="propertyTime" class="inputDate" readonly="readonly" placeholder="Please input time" />
                 <textarea  rows="3" value="" cols="" class="inputTextarea" maxlength="1024"></textarea>
            </li>
        </ul>
    </div>
    <div class="properties">
	    <ul id="sortableLeft" class="sortable" >
        </ul>
	    <ul id="sortableRight" class="sortable" ></ul>
    </div>
    <div class="clear"></div>
    <div id="showError"></div>
    <div class="operateClass">
        <button id="cancel" value="cancel"  class="operateButton"><spring:message code="cancel" /></button>
        <button id="save" value="save" class="operateButton"><spring:message code="save" /></button>
        <img id="addProperty" alt="add" src="image/self/ICN_Add_16x16.png"></img>
    </div>
    <div id="definePropertyContent" class="showProperty">
    	<input type="hidden" id="editId" value="" />
        <div class="addPropertyTitle"><span id="addPropertyTitle"></span></div>
        <div class="leftCass">
            <div class="rowDiv">
                <span class="spanClass"><spring:message code="customized.property.type" /></span>
                <input type="hidden" id="selectedType" value="">
                <ul class="propertyType">
                    <li id="inputType" class="propertyTypeLi" >
                        <img class="propertyTypeImg" src="image/self/input.png">
                        <div class="propertyTypeInput"><spring:message code="customized.property.input" /></div>
                    </li>
                    <li id="selectType" class="propertyTypeLi" >
                        <img class="propertyTypeImg" src="image/self/select.png">
                        <div class="propertyTypeSelect"><spring:message code="customized.property.select" /></div>
                    </li>
                    <li id="dateType" class="propertyTypeLi" >
                        <img class="propertyTypeImg" src="image/self/date.png">
                        <div class="propertyTypeDate"><spring:message code="customized.property.date" /></div>
                    </li>
                    <li id="textAreaType" class="propertyTypeLi">
                        <img class="propertyTypeImg" src="image/self/textarea.png">
                        <div class="propertyTypeTextArea"><spring:message code="customized.property.textArea" /></div>
                    </li>
                </ul>
            </div>
            <div class="rowDiv">
                <span class="spanClass"><spring:message code="customized.property.Chinese.name" ></spring:message>
                    <span class="propertyRequired">*</span>
                </span> 
                <input type="text" id="propertyZhName" class="zhName propertyName" maxlength="36">
            </div>
            <div class="rowDiv">
                <span class="spanClass"><spring:message code="customized.property.English.name" ></spring:message>
                    <span class="propertyRequired">*</span>
                </span> 
                <input type="text" id="propertyEnName" class="enName propertyName" maxlength="36">
            </div>
            <div class="rowDiv test">
                <span class="spanClass radioSpan"><spring:message code="customized.property.required" />
                    <span class="propertyRequired">*</span>
                </span> 
                <input type="hidden" id="propertyRequired" value="true" /> 
                <div class="radioBoxes">
	    			<a class="radioCheckOn" id="true"></a><span class="requiredTrue"><spring:message code="customized.property.true" /></span>
	                <a class="radioCheckOff" id="false"></a><span class="requiredFalse"><spring:message code="customized.property.false" /></span>
    			</div>
            </div>
            <div class="rowDiv">
                <button  id="submitProperty" value="add" class="operateButton"><spring:message code="add" /></button>
                <button  id="editProperty" value="edit" class="operateButton"><spring:message code="update" /></button>
                <button  id="cancleProperty" value="cancel" class="operateButton"><spring:message code="cancel" /></button>
            </div>
        </div>
        <div class="rightClass">
            <div class="description">
                <div><spring:message code="customized.property.description" /></div>
                <textarea  id="propertyDescription" class="propertyDescription" rows="3" maxlength="1024"></textarea>
            </div>
            <div class="rowDiv inputType" >
                <div><spring:message code="customized.property.defaultValue" /></div>
                <div>
                    <input type="text" id="propertyInputValue" class="propertyInput" maxlength="36">
                </div>
            </div>
            <div class="clear"></div>
            <div class="rowDiv selectType" >
                <div class="selectText">
                    <spring:message code="customized.property.addItemToSelect" /><span class="propertyRequired">*</span>
                </div>
                <div>
                    <input type="text" id="propertyPropertyItem" class="propertySelect" maxlength="36">
                    <button id="addItem" value="add"><spring:message code="add" /></button>
                </div>
                <div id="itemTemplate">
                	<div class="itemContent">
                        <input type="text" value="" id="itemValue" class="addItem" disabled="disabled" maxlength="36">
                        <div class="deleteItem" ></div>
                    </div>
                </div>
                <div class="addSelectItem"></div>
            </div>
            <div class="clear"></div>
            <div class="rowDiv dateType">
            	<div><spring:message code="customized.property.defaultValue" /></div>
            	<div>
            		<input id="propertyDateTypeValue" class="l-date createDate" readonly="readonly" placeholder="Please time" />
            	</div>
            </div>
            <div class="clear"></div>
            <div class="rowDiv textAreaType" >
                <div class="textAreaText"><spring:message code="customized.property.defaultValue" /></div>
                <div>
                    <textarea id="propertyTextAreaTypevalue" rows="4" cols="" class="propertyTextArea" maxlength="1024"></textarea>
                </div>
            </div>
            <div class="clear"></div>
        </div>
    </div>
</div>                     
</div>         
<jsp:include page="/WEB-INF/page/common/footer.jsp"></jsp:include>
<script type="text/javascript" src="js/common/json2.js"></script>
<script type="text/javascript" src="js/common/commonFunction.js"></script>
<script type="text/javascript" src="js/common/validation.js"></script>
<script type="text/javascript" src="js/asset/assetValidation.js"></script>
<script type="text/javascript" src="js/customized/customizedProperty/customizedProperty.js"></script>
</body>
</html>