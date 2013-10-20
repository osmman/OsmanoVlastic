/*
 * Destination class enable to operate with diestinations.
 */

Destination = {
	open : function() {
		// hide other
		$('#destinationPart').show();
		$('#flightPart').hide();
		$('#reservationPart').hide();
		Destination.getDestinations();
	},
	orderDestinatios : function(e) {
		element = $(e);
		if (element.attr('orderValue').length == 0 || element.attr('orderValue') == 'asc') {
			$('#destinationPart th a').attr('orderValue','');
			$('#destinationPart th i').attr('class', '');
			element.attr('orderValue', 'desc');
			Destination.order = element.attr('name')+":desc";
			element.find('i').attr('class', 'icon-chevron-up');
		} else {
			$('#destinationPart th a').attr('orderValue','');
			$('#destinationPart th i').attr('class', '');
			element.attr('orderValue', 'asc');
			Destination.order = element.attr('name')+":asc";
			element.find('i').attr('class', 'icon-chevron-down');
		}
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
				request.setRequestHeader('X-Base', $('#destinationCount').val());
				request.setRequestHeader('X-Offset', $('#destinationOffset').val());
				request.setRequestHeader('X-Order', Destination.order);
			},
			success : function(data) {
				Destination.destinations = data;
				Destination.displayDestinations();
			}
		});
	},
	getDestinationsAndCallback : function(callback) {
		$.ajax({
			url : "rest/destination",
			type : "GET",
			beforeSend : function(request) {
				request.setRequestHeader('Authorization', 'Basic '
						+ Authentication.token);
				request.setRequestHeader('Accept', 'application/json');
				request.setRequestHeader('X-Base', $('#destinationCount').val());
				request.setRequestHeader('X-Offset', $('#destinationOffset').val());
				request.setRequestHeader('X-Order', Destination.order);
			},
			success : function(data) {
				Destination.destinations = data;
				callback();
			}
		});
	},
	displayDestinations : function() {
		Destination.cleanDestinations();
		for (i = 0; i < Destination.destinations.length; i++) {
			$(
					'<tr><td class="id">'
							+ Destination.destinations[i].id
							+ '</td><td><input type="text" name="name" class="name" value="'
							+ Destination.destinations[i].name
							+ '" /></td><td><button type="button" class="update btn btn-success"><i class="icon-edit icon-white" /></button> <button type="button" class="delete btn btn-danger"><i class="icon-remove icon-white" /></button></td></tr>')
					.appendTo($('#destinationPart tbody'));
		}
		$('#destinationPart tbody td .update').bind('click', function() {
			Destination.updateSubmit(this);
		});
		$('#destinationPart tbody td .delete').bind('click', function() {
			Destination.deleteSubmit(this);
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
			url : "rest/destination/" + id,
			type : "PUT",
			data : '{"name" : "' + name + '"}',
			beforeSend : function(request) {
				request.setRequestHeader('Authorization', 'Basic '
						+ Authentication.token);
				request.setRequestHeader('Content-Type', 'application/json');
			},
			success : function(data) {
				$('#destinationPart .message p').remove();
				$('<p class="text-success">Update success.</p>').appendTo(
						$('#destinationPart .message'));
			},
			error : function(data) {
				$('#destinationPart .message p').remove();
				$('<p class="text-error">Update faild.</p>').appendTo(
						$('#destinationPart .message'));
			}
		});
	},
	deleteSubmit : function(e) {
		line = $(e).parents('tr');
		id = line.find('.id').text();
		Destination.deleteDestination(id);
	},
	deleteDestination : function(id) {
		$.ajax({
			url : "rest/destination/" + id,
			type : "DELETE",
			beforeSend : function(request) {
				request.setRequestHeader('Authorization', 'Basic '
						+ Authentication.token);
				request.setRequestHeader('Content-Type', 'application/json');
			},
			success : function(data) {
				$('#destinationPart .message p').remove();
				$('<p class="text-success">Delete success.</p>').appendTo(
						$('#destinationPart .message'));
				Destination.getDestinations();
			},
			error : function(data) {
				$('#destinationPart .message p').remove();
				$('<p class="text-error">Delete faild.</p>').appendTo(
						$('#destinationPart .message'));
			}
		});
	},
	createSubmit : function(e) {
		name = $('#destinationName').val();
		Destination.createDestination(name);
		e.preventDefault();
	},
	createDestination : function(name) {
		$.ajax({
			url : "rest/destination/",
			type : "POST",
			data : '{"name" : "' + name + '"}',
			beforeSend : function(request) {
				request.setRequestHeader('Authorization', 'Basic '
						+ Authentication.token);
				request.setRequestHeader('Content-Type', 'application/json');
			},
			success : function(data) {
				$('#destinationPart .message p').remove();
				$('<p class="text-success">Create success.</p>').appendTo(
						$('#destinationPart .message'));
				Destination.getDestinations();
			},
			error : function(data) {
				$('#destinationPart .message p').remove();
				$('<p class="text-error">Create faild.</p>').appendTo(
						$('#destinationPart .message'));
			}
		});
	},
	cleanDestinations : function() {
		$('#destinationPart tbody tr').remove();
	},
	order : 'id:asc',
	destinations : []
}