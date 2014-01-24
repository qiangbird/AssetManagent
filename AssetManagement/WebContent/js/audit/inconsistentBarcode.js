$(document).ready(function(){
	var dataList;
	var criteria;
	
	$("#barcodeLink").click(function(){
		$(".filterDiv").hide();
		$(".customizedViewButton").hide();
		
		
		criteria.pageNum = 1;
		criteria.sortName = 'barcode';
		criteria.sortSign = 'asc';
		criteria.auditFileName = $("#fileName").val();
	    
		dataList = $(".dataList").DataList({
			columns : [{ EN : 'Barcode', ZH : '条形码', sortName : 'barCode', width : 200, headerId: 1, isMustShow : true }],
		    criteria : criteria,
		    minHeight : 150,
		    pageSizes : [10, 20, 30, 50],
		    language : $("#locale").val().substring(0, 2).toUpperCase(),
		    hasCheckbox : false,
		    pageItemSize : 5,
		    url : 'inconsistent/viewInconsistentBarcode',
		    updateShowField : {},
		    updateShowSize : {
		        url : 'searchCommon/pageSize/updatePageSize',
		        callback : function() {
		        }
		    },
		    contentHandler : function(str) {
		        return resultContentHandle(str);
		    }
		});
		
		dataList.setShow("Barcode");
	});
});
