/** the priority of button  */
var buttonPriorities = {
 'editButton': {'priority': 1, 'background': 'green'},
 'publishButton': {'priority': 2, 'background': 'green'},
 'saveAsPlanBtn': {'priority': 3, 'background': 'green'},
 'cancelButton': {'priority': 4, 'background': 'white'},
 'deleteButton': {'priority': 5, 'background': 'white'},
 'viewOnlineCourse': {'priority': 6, 'background': 'green'},
 'trainerAssessPlan': {'priority': 7, 'background': 'white'},
 'traineeAssessPlan': {'priority': 7, 'background': 'white'},
 'masterAssessTrainer': {'priority': 8, 'background': 'white'},
 'trainerAssessTrainee': {'priority': 9, 'background': 'white'},
 'traineeAssessCourse': {'priority': 10, 'background': 'white'},
 'applyLeaveButton': {'priority': 11, 'background': 'white'},
 'backToCourse': {'priority': 11, 'background': 'white'},
 'joinButton': {'priority': 12, 'background': 'white'},
 'quitButton': {'priority': 12, 'background': 'white'},
 'viewAllAssessment': {'priority': 13, 'background': 'white'},
 'traineeEditPlanAssessment': {'priority': 14, 'background': 'white'},
 'traineeViewPlanAssessment': {'priority': 14, 'background': 'white'},
 'trainerEditPlanAssessment': {'priority': 14, 'background': 'white'},
 'trainerViewPlanAssessment': {'priority': 14, 'background': 'white'},
 'masterEditTrainerAssessment': {'priority': 14, 'background': 'white'},
 'masterViewTrainerAssessment': {'priority': 14, 'background': 'white'},
 'traineeEditCourseAssessment': {'priority': 14, 'background': 'white'},
 'traineeViewCourseAssessment': {'priority': 14, 'background': 'white'},
 'trainerEditTraineeAssessment': {'priority': 14, 'background': 'white'},
 'trainerViewTraineeAssessment': {'priority': 14, 'background': 'white'},
 'masterDelegatePlan': {'priority': 15, 'background': 'white'},
 'masterChangeDelegate': {'priority': 16, 'background': 'white'},
 'masterCancelDelegate': {'priority': 17, 'background': 'white'}
};

/** 
 * Load JS
 * @param url
 *             the JS url
 * @param callback
 *             Load success, and execute the callback function
 * @param charset
 * @return
 */
function loadJS(url,callback,charset){
    var script = document.createElement('script');
    script.onload = script.onreadystatechange = function ()
    {
        if (script && script.readyState && /^(?!(?:loaded|complete)$)/.test(script.readyState)) return;
        script.onload = script.onreadystatechange = null;
        script.src = '';
        script.parentNode.removeChild(script);
        script = null;
        if(callback) callback();
    };
    script.charset=charset || document.charset || document.characterSet;
    script.src = url;
    try {document.getElementsByTagName("head")[0].appendChild(script);} catch (e) {}
}

/**
 * message bar
 * @param message
 * @param itemId
 * @return
 */
function showMessageBar(message, itemId) {
    $("#messageBar").messageBar({
        isPrepositionId: true,
        responseMessage: message + " ",
        itemId: itemId,
        top: 100
    });
}
var MESSAGE_BAR_CREATE = 'create';
var MESSAGE_BAR_EDIT = 'edit';
var MESSAGE_BAR_IGNORE = 'ignore';

/**
 * 
 * @return
 */
function initXhEditor_setLanguange(xHEditor,language){
    if(!xHEditor || xHEditor == ' ') return;
    if (!language) {
        var defaultLan = getLanguageByBrowser();
        if (defaultLan.indexOf("zh") !== -1) {
            loadJS('../xheditor/xheditor-1.1.12-zh-cn.js',xHEditor);
        }else {
            loadJS('../xheditor/xheditor-1.1.12-en.js',xHEditor);
        }
    }else if (language.indexOf("zh") !== -1) {
        loadJS('../xheditor/xheditor-1.1.12-zh-cn.js',xHEditor);
    } else {
        loadJS('../xheditor/xheditor-1.1.12-en.js',xHEditor);
    }
}

/**
 * add a prototype in javaScript function of String 
 * @return {} function(). used string.trim()
 * @author Tanner.Cai
 */
String.prototype.trim = function(){
    return this.replace(/(^\s*)|(\s*$)/g, "");
};

/**
 * check the two string if the have some is same.
 * @param {} subStr
 * @param {} separator
 * @return {} If have same string in two string.
 * @author Tanner.Cai
 */
function checkTheSameForStr(subStr,separator){
    var flag = false;
    var array = subStr.trim().split(separator);
    if(array.length >= 2){
        for(var i = 0 ; i < array.length ; i++){
            if(countInstances(subStr,array[i])>=2){
                flag=true;
                break;
            }
        }
    }
    return flag;
}

/**
 * find the count for two string if one include other.
 * @param {} mainStr
 * @param {} subStr
 * @return {} the count of the same string
 * @author Tanner.Cai
 */
function countInstances(mainStr, subStr){
    var count = 0;
    var offset = 0;
    do {
        offset = mainStr.indexOf(subStr, offset);
        if(offset != -1){
            count++;
            offset += subStr.length;
        }
    }while(offset != -1);
        return count;
}

function loadAllEmployees(url, callback, $node) {
    $.ajax({
        type:"POST",
        url: url,
        datatype:"html",
        success:function(text){
            if(handleException(text)) {
               if (!text || !text.names) {
                    return false;
                }
                var array = text.names;
                if ($node) {
                    $node.data("employeeNamesData", array);
                }
                if(callback) {
                    callback(array);
                }
            }
        }
    });
}

function ArrayIsEqual(array1, array2) {
    var result = array1.sort().toString() === array2.sort().toString();
    return result;
}

function commonCheckBox(obj) {
    if($(obj).attr("check") === "checked") {
        $(obj).attr("check", "unchecked");
        $(obj).addClass("common_checkbox_unchecked").removeClass("common_checkbox_checked");
    }else{
        $(obj).attr("check", "checked");
        $(obj).addClass("common_checkbox_checked").removeClass("common_checkbox_unchecked");
    }
}

function commonRadio(obj) {
    var name = $(obj).attr('radioName');
    $("span[radioName='"+name+"']:not(check='checked')").attr("check", "unchecked")
        .addClass("common_radio_unchecked").removeClass("common_radio_checked");
    $(obj).attr("check", "checked");
    $(obj).addClass("common_radio_checked").removeClass("common_radio_unchecked");
}

/**
 * Handle ajax exception 
 * @param data
 * @return
 */
function handleException(data, customUrl, finalHandleFunc, finalHandleFuncParam) {
    if (data && data.errorCodeId) {
        // Don't jump page
        if (data.flag == "0") {
            initialErrorMsgBar(data.errorMessage, function() {
                if(finalHandleFunc) {
                    finalHandleFunc(finalHandleFuncParam);
                }
            });
        }
        // Jump to Server Error or custom page
        if (data.flag == "1") {
            if (data.errorCodeId == "E0001") {
                window.onbeforeunload = null;
                window.location.href = $("#basePath").val() + "serverError";
            } else if (data.errorCodeId == "E0002") {
                window.onbeforeunload = null;
                window.location.href = $("#basePath").val() + "serverValidationError";
            } else if (data.errorCodeId == "E0003") {
                window.onbeforeunload = null;
                window.location.href = $("#basePath").val() + "iapError";
            } else if (data.errorCodeId == "E0004") {
                window.onbeforeunload = null;
                window.location.href = $("#basePath").val() + "error";
            } else if (data.errorCodeId == "E0005") {
                window.onbeforeunload = null;
                window.location.href = $("#basePath").val() + "noAccess";
            } else if (data.errorCodeId == "E0006") {
                window.onbeforeunload = null;
                window.location.href = window.location.href;
            } else if (data.errorCodeId == "E0007") {
                window.onbeforeunload = null;
                window.location.href = $("#basePath").val() + "serverError";
            } else if(customUrl){
                initialErrorMsgBar(data.errorMessage, function() {
                    window.onbeforeunload = null;
                    window.location = customUrl;
                });
            } else if(finalHandleFunc){
                initialErrorMsgBar(data.errorMessage, function() {
                    finalHandleFunc(finalHandleFuncParam);
                });
            } else {
                // if custom page not defined. jump to dash-board
                initialErrorMsgBar(data.errorMessage, function() {
                    window.onbeforeunload = null;
                    window.location = $('#basePath').val() + 'dashboard/dashboard_dashboard';
                });
            }
        }
        return false;
    }
    return true;
}

$.ajaxSetup({
    global: false,
    cache: false,
    error: function(XMLHttpRequest, textStatus){
//        ajaxErrorHandle(XMLHttpRequest, textStatus);
//        removeLoaderImage();
    },
    data: {isAjax : true}
});
/**
 * ajax error handle
 */
function ajaxErrorHandle(XMLHttpRequest, textStatus) {
    if(XMLHttpRequest){
        if(XMLHttpRequest.readyState == 0) {
            window.onbeforeunload = null;
            if(getServerCurrentTime()){
                window.location = window.location;
            } else {
                initialErrorMsgBar(getSessionTimeOutMsg, function(){
                    window.location = window.location;
                }, getRefreshI18n);
            }
        }
    }
} 

/**
 * Data warning pop-up
 * @param errorMsg
 * @param finalHandleFunc
 * @return
 */
function initialErrorMsgBar(errorMsg, finalHandleFunc, btnText) {
    if(!($("#erroMsgDialog").length > 0)){
        var confirmHtml = '<div id="erroMsgDialog">'+
                              '<div class="confirmContent">'+
                              '</div>'+
                          '</div>';
        $('body').append(confirmHtml);
    }
    if(btnText) {
        
    } else {
        btnText = getBTNClose();
    }
    $("#erroMsgDialog").find('.confirmContent').html(errorMsg);
    $('#erroMsgDialog').dialog({
         autoOpen: false,
        width: 350, 
         modal: true, 
         resizable: false,
         draggable: false,
         title: getBtnNotice(),
         buttons: [
           {
               text: btnText,
               id : "greenButton",
               click: function(event) {
                   event.stopPropagation();
                   $(this).dialog("close");
                   if (finalHandleFunc) {
                       finalHandleFunc.call();
                   }
               }
           } 
         ]
    });
    $("#erroMsgDialog").dialog("open");
}

function checkAutoComplete($node) {
    var validateResult = true;
    $node.find("ul li").each(function(index, node){
        if ($(node).attr("error")) {
            validateResult = false;
            return;
        }
    });
    return validateResult;
}

/**
 * Get server current time.
 * @return
 */
function getServerCurrentTime() {
    var serverCurrentTime='';
    $.ajax ({
        type: "POST",
        async: false,
        global: false,
        error: null,
        url: $('#basePath').val()+"getCurrentServerTime",
        success: function(currentTime) {
            serverCurrentTime = currentTime;
        }
    });
    return serverCurrentTime;
}

function addLoaderImage(){
    if(document.getElementById("loader-background") == undefined){
        $("body").append("<div id='loader-background'></div>");
        $("#loader-background").height($(document).height());
        $("body").append("<div id='loader-image'></div>");
        var top = (window.screen.availHeight  - $("#loader-image").height())/2 + document.body.scrollTop-60;
        $("#loader-image").css({
            "position":"absolute",
            "top": top +"px",
            "left":($(document).width() -$("#loader-image").width())/2 +"px"
        });
        $(window).bind("scroll", function(){
            $("#loader-background").height($(document).height());
            top = (window.screen.availHeight  - $("#loader-image").height())/2 + document.body.scrollTop-60;
            $("#loader-image").css("top", top +"px");
        });
    }
}

function removeLoaderImage(){
    if(document.getElementById("loader-background") != undefined){
        $(window).unbind( "scroll" );
        $("#loader-background").remove();
        $("#loader-image").remove();
    }
}

function addBlankAfterCharacter(selector, character) {
    var categoryTag = $(selector).html();
    var regx = "/" + character + "/g";
    var replaceStr = character + " ";
    var newCategoryTag = categoryTag.replace(eval(regx), replaceStr);
    $(selector).html(newCategoryTag);
}

function loadEmployeeHasNoTrainerRole(){
    $.ajax({
        type:"POST",
        url: $('#basePath').val()+"plan/findEmployeeHasNoTrainerRole",
        datatype:"html",
        success:function(text){
            if(handleException(text)) {
                var array = text.trainerWithNoTrainerRole.split(",");
                $(document).find("body").data("noTrainerRoleEmployee", array);
            }
        }
    });
}

