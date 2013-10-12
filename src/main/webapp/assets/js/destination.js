/*
 * Destination class enable to operate with diestinations.
 */

Destination = {
	open : function() {
		// hide other
		$('#otherPart').hide();
		$('#destinationPart').show();
		Destination.getDestinations();
	},
	getDestinations : function() {
		$.ajax({
			url: "http://10.99.0.10:8080/osmanvlastic/rest/destination",
            type:"GET",
            beforeSend: function (request) {
                request.setRequestHeader('Authorization', 'Basic '+Authentication.token);
                request.setRequestHeader('Accept', 'application/json');
            },
            success: function(data) {
                Destination.destinations = data;
                Destination.displayDestinations();
            }
		});
	},
	displayDestinations : function(){
		$('#destinationPart tbody tr').remove();
		for (i = 0; i < Destination.destinations.length; i++) {
			$('<tr><td>'+Destination.destinations[i].id+'</td><td>'+Destination.destinations[i].name+'</td></tr>').appendTo($('#destinationPart tbody'));
		}
	},
	destinations: []
}