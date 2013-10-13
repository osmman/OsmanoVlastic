/*
 * Destination class enable to operate with diestinations.
 */

Destination = {
	open : function() {
		// hide other
		// $('#otherPart').hide();
		$('#destinationPart').show();
		Destination.getDestinations();
	},
	getDestinations : function() {
		$.ajax({
			url : "rest/destination",
			type : "GET",
			beforeSend : function(request) {
				request.setRequestHeader('Authorization', 'Basic '
						+ Authentication.token);
				request.setRequestHeader('Accept', 'application/json');
			},
			success : function(data) {
				Destination.destinations = data;
				Destination.displayDestinations();
			}
		});
	},
	displayDestinations : function() {
		Destination.cleanDestinations();
		for (i = 0; i < Destination.destinations.length; i++) {
			$(
					'<tr><td class="id">'
							+ Destination.destinations[i].destination.id
							+ '</td><td><input type="text" name="name" class="name" value="'
							+ Destination.destinations[i].destination.name
							+ '" /></td><td><button type="button" class="update">Update</button></td></tr>')
					.appendTo($('#destinationPart tbody'));
		}
		$('#destinationPart tbody td .update').bind('click', function() {
			Destination.updateSubmit(this);
		});
	},
	updateSubmit : function(e) {
		line = $(e).parents('tr');
		id = line.find('.id').text();
		name = line.find('.name').val();
		Destination.updateDestination(id, name);
	},
	updateDestination : function(id, name) {
		$.ajax({
			url : "rest/destination/"+id,
			type : "PUT",
			data : "{'destination': {'name' : '"+name+"'}}",
			beforeSend : function(request) {
				request.setRequestHeader('Authorization', 'Basic '
						+ Authentication.token);
				request.setRequestHeader('Content-Type', 'application/json');
			},
			success : function(data) {
				
			}
		});
	},
	cleanDestinations : function() {
		$('#destinationPart tbody tr').remove();
	},
	destinations : []
}