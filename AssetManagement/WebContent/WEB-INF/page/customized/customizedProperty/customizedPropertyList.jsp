<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<html>
<head>
<base href="<%=basePath%>">
<link rel="stylesheet" href="css/customize/customizedProperty/selfProperty.css" type="text/css">
<link rel="stylesheet" href="css/common/jquery-ui.css" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<jsp:include page="../../common/header.jsp" />
<!-- <input type="hidden" id="properties" value="" /> -->
<div id="propertyContent">
	<form id="propertiesForm" action="" method="post">
	<input name="selfProperties" id="selfProperties" type="hidden" value="" />
    <div class="customer-assetType">
        <div class="customerName">
            <label class="shortLeftText">
                <span class="propertyRequired">*</span>
                 <span class="propertyText">Customer</span>
            </label>
            <input id="customerName" class="inputSelect" type="text" value=""  />
            <input name="customerCode" id="customerCode" class="inputSelect" type="hidden" value=""  />
        </div>
        <div class="assetType">
            <label class="shortLeftText">
                <span class="propertyRequired">*</span>
                 <span class="propertyText">AssetType</span>
            </label>
            <input name="assetType" id="assetType" class="inputSelect" type="text" value="" />
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
                     <span class="propertyText">property.enName</span>
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
        <button id="cancle" value="cancel"  class="operateButton">Cancel</button>
        <button id="save" value="save" class="operateButton">Save</button>
        <img id="addProperty" alt="add" src="image/self/ICN_Add_16x16.png"></img>
    </div>
    <div id="definePropertyContent" class="showProperty">
    	<input type="hidden" id="editId" value="" />
        <div class="addPropertyTitle"><span id="addPropertyTitle"></span></div>
        <div class="leftCass">
            <div class="rowDiv">
                <span class="spanClass">PropertyType</span>
                <input type="hidden" id="selectedType" value="">
                <ul class="propertyType">
                    <li id="inputType" class="propertyTypeLi" >
                        <img class="propertyTypeImg" src="image/self/input.png">
                        <div class="propertyTypeInput">Input</div>
                    </li>
                    <li id="selectType" class="propertyTypeLi" >
                        <img class="propertyTypeImg" src="image/self/select.png">
                        <div class="propertyTypeSelect">Select</div>
                    </li>
                    <li id="dateType" class="propertyTypeLi" >
                        <img class="propertyTypeImg" src="image/self/date.png">
                        <div class="propertyTypeDate">Date</div>
                    </li>
                    <li id="textAreaType" class="propertyTypeLi">
                        <img class="propertyTypeImg" src="image/self/textarea.png">
                        <div class="propertyTypeTextArea">TextArea</div>
                    </li>
                </ul>
            </div>
            <div class="rowDiv">
                <span class="spanClass">ChineseName
                    <span class="propertyRequired">*</span>
                </span> 
                <input type="text" id="propertyZhName" class="zhName propertyName" maxlength="36">
            </div>
            <div class="rowDiv">
                <span class="spanClass">EnglishName
                    <span class="propertyRequired">*</span>
                </span> 
                <input type="text" id="propertyEnName" class="enName propertyName" maxlength="36">
            </div>
            <div class="rowDiv test">
                <span class="spanClass radioSpan">Required
                    <span class="propertyRequired">*</span>
                </span> 
                <input type="hidden" id="propertyRequired" value="true" /> 
                <div class="radioBoxes">
	    			<a class="radioCheckOn" id="true"></a><span class="requiredTrue">True</span>
	                <a class="radioCheckOff" id="false"></a><span class="requiredFalse">False</span>
    			</div>
            </div>
            <div class="rowDiv">
                <button  id="submitProperty" value="submit" class="operateButton">Submit</button>
                <button  id="cancleProperty" value="cancel" class="operateButton">Cancel</button>
            </div>
        </div>
        <div class="rightClass">
            <div class="description">
                <div>Description</div>
                <textarea  id="propertyDescription" class="propertyDescription" rows="3" maxlength="1024"></textarea>
            </div>
            <div class="rowDiv inputType" >
                <div>DefaultValue</div>
                <div>
                    <input type="text" id="propertyInputValue" class="propertyInput" maxlength="36">
                </div>
            </div>
            <div class="clear"></div>
            <div class="rowDiv selectType" >
                <div class="selectText">
                    AddItemToSelect<span class="propertyRequired">*</span>
                </div>
                <div>
                    <input type="text" id="propertyPropertyItem" class="propertySelect" maxlength="36">
                    <button id="addItem" value="add">Add</button>
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
            	<div>DefaultValue</div>
            	<div>
            		<input id="propertyDateTypeValue" class="l-date createDate" readonly="readonly" placeholder="Please time" />
            	</div>
            </div>
            <div class="clear"></div>
            <div class="rowDiv textAreaType" >
                <div class="textAreaText">DefaultValue</div>
                <div>
                    <textarea id="propertyTextAreaTypevalue" rows="4" cols="" class="propertyTextArea" maxlength="1024"></textarea>
                </div>
            </div>
            <div class="clear"></div>
        </div>
    </div>
</div>                              
<jsp:include page="/WEB-INF/page/common/footer.jsp"></jsp:include>
<script type="text/javascript" src="js/common/json2.js"></script>
<script type="text/javascript" src="js/customize/customizedProperty/selfProperty.js"></script>
<script type="text/javascript" src="js/common/commonFunction.js"></script>
</body>
</html>