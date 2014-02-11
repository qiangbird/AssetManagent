function recordFailInfo(validations) {

	var validation = "success";

	for ( var i = 0; i < validations.length; i++) {

		if ("failed" == validations[i]) {
			validation = "failed";
			break;
		}
	}

	return validation;
}

jQuery.fn.extend({

	validateNull : function(validationAttribute, errorMessage) {
		if (errorMessage == undefined) {
			errorMessage = "Can not be null";
		}
		var validation = "success";

		if (null == validationAttribute || "" == validationAttribute
				|| $(this).hasClass("input-text-error")
				|| $(this).hasClass("l-select-error")
				|| $(this).hasClass("l-text-error")) {
			$(this).addClass("validation_fail");
			$(this).poshytip({
				className : 'tip-red',
				bgImageFrameSize : 9,
				content : function(updateCallback) {
					window.setTimeout(function() {
						updateCallback(errorMessage);
					}, 100);
					return 'Loading...';
				},
				allowTipHover : false
			});
			validation = "failed";

		} else {
			$(this).removeClass("validation_fail");
			$(this).poshytip('destroy');
		}

		return validation;
	},
	validateLength : function(validationAttribute, maxLength, errorMessage) {
		if (errorMessage == undefined) {
			errorMessage = "The length is too long";
		}

		var validation = "success";

		if (validationAttribute.length > maxLength) {

			$(this).addClass("validation_fail");
			$(this).poshytip({
				className : 'tip-red',
				bgImageFrameSize : 9,
				content : function(updateCallback) {
					window.setTimeout(function() {
						updateCallback(errorMessage);
					}, 100);
					return 'Loading...';
				},
				allowTipHover : false
			});
			validation = "failed";

		} else {
			$(this).removeClass("validation_fail");
			$(this).poshytip('destroy');
		}

		return validation;
	},
	validateNum : function(validationAttribute, errorMessage) {
		if (errorMessage == undefined) {
			errorMessage = "Must be a number !";
		}
		var validation = "success";

		if (!numberCheck(validationAttribute) || null == validationAttribute
				|| "" == validationAttribute) {
			$(this).addClass("validation_fail");
			$(this).poshytip({
				className : 'tip-red',
				bgImageFrameSize : 9,
				content : function(updateCallback) {
					window.setTimeout(function() {
						updateCallback(errorMessage);
					}, 100);
					return 'Loading...';
				},
				allowTipHover : false
			});
			validation = "failed";

		} else {
			$(this).removeClass("validation_fail");
			$(this).poshytip('destroy');
		}

		return validation;
	},
	validateDate : function(validationAttribute1, validationAttribute2,
			errorMessage) {
		if (errorMessage == undefined) {
			errorMessage = "Input date error !";
		}
		var validation = "success";
		if (!dateCompare(validationAttribute1, validationAttribute2)) {
			$(this).addClass("validation_fail");
			$(this).poshytip({
				className : 'tip-red',
				bgImageFrameSize : 9,
				content : function(updateCallback) {
					window.setTimeout(function() {
						updateCallback(errorMessage);
					}, 100);
					return 'Loading...';
				},
				allowTipHover : false
			});
			validation = "failed";

		} else {
			$(this).removeClass("validation_fail");
			$(this).poshytip('destroy');
		}

		return validation;
	},
	checkErrorMessage : function(jqueryObject) {

		var errorMessage = jqueryObject.val();

		if (errorMessage != "") {
			$(this).addClass("validation_fail");
			$(this).poshytip({
				className : 'tip-red',
				bgImageFrameSize : 9,
				content : function(updateCallback) {
					window.setTimeout(function() {
						updateCallback(errorMessage);
					}, 100);
					return 'Loading...';
				},
				allowTipHover : false
			});
			validation = "failed";
		} else {
			$(this).removeClass("validation_fail");
			$(this).poshytip('destroy');
		}
	},
	clearValidationMessage : function() {
		$(this).removeClass("validation_fail");
		$(this).poshytip('destroy');
	}
});
