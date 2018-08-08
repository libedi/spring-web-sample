// form 객체를 JSON Object로 변환 : $("form").serializeObject();
$.fn.serializeObject = function(){
	"use strict";
	
	var o = {};
	var a = this.serializeArray();
	$.each(a, function() {
		if (o[this.name]) {
			if (!o[this.name].push) {
				o[this.name] = [o[this.name]];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
};

$(function(){
	"use strict";
	
	$.ajaxPrefilter(function(options, originalOptions, jqXHR) {
		// 인증 헤더 추가
		const jwt = sessionStorage.getItem("jwt");
		if(jwt !== null) {
			jqXHR.setRequestHeader("Authentication", jwt);
		}
		// REST 요청을 위한 Http Method Override
		if(options.type === "put" || options.type === "patch" || options.type === "delete") {
			var headerValue = options.type;
			jqXHR.setRequestHeader("X-HTTP-Method-Override", headerValue);
			options.type = "post";
		}
	});
});