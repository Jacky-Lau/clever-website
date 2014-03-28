$(document).ready(function() {
	$("div").each(function(index, value) { 
		if ($(this).attr('id') == "descriptionDiv") {
			var strDescription = $(this).html();
			var strDescriptionReplaced = strDescription.trim().replace(/\n/g, "<br/>").replace(/\s/g, "&nbsp;");
			$(this).html(strDescriptionReplaced);			
		}
	});
	$('#searchTopDiv').on('click','#searchButton',function(){
		var valueCondition=$('#searchConditionInput').val();
		window.location.href="/clever-website/archetypeSearch?condition="+valueCondition;		
	});
});