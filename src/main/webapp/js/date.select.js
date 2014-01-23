$(function() {
		var timedate = $( "#timefrom, #timeto" ).datepicker({
			defaultDate: "+1w",
			changeMonth: true,
			changeYear: true,
			numberOfMonths: 1,
			onSelect: function( selectedDate ) {
				var option = this.id == "timefrom" ? "minDate" : "maxDate",
					instance = $( this ).data( "datepicker" ),
					date = $.datepicker.parseDate(
						instance.settings.dateFormat ||
						$.datepicker._defaults.dateFormat,
						selectedDate, instance.settings );
				timedate.not( this ).datepicker( "option", option, date );
			},
			onClose: function(){
				if(this.id=="timefrom" && $("#timeto").val()=="" && $(this).val()!==""){
					$("#timeto").focus();
				}
					}
		});
	});
