/*
 * Destination class enable to operate with diestinations.
 */

Flight = {
	open : function() {
		// hide other
		// $('#otherPart').hide();
		Destination.getDestinationsAndCallback(function (){
			res = '';
			for (i = 0; i < Destination.destinations.length; i++) {
				res += '<option value="' + Destination.destinations[i].id + '">'
						+ Destination.destinations[i].name + '</option>';
			}
			$('select[name=flightFrom]').append(res);
			$('select[name=flightTo]').append(res);
			$('#destinationPart').hide();
			$('#reservationPart').hide();
			$('#flightPart').show();
			Flight.getFlights();
		});
	},
	orderFlights : function(e) {
		element = $(e);
		if (element.attr('orderValue').length == 0
				|| element.attr('orderValue') == 'asc') {
			$('#flightPart th a').attr('orderValue', '');
			$('#flightPart th i').attr('class', '');
			element.attr('orderValue', 'desc');
			Flight.order = element.attr('name') + ":desc";
			element.find('i').attr('class', 'icon-chevron-up');
		} else {
			$('#flightPart th a').attr('orderValue', '');
			$('#flightPart th i').attr('class', '');
			element.attr('orderValue', 'asc');
			Flight.order = element.attr('name') + ":asc";
			element.find('i').attr('class', 'icon-chevron-down');
		}
		Flight.getFlights();
	},
	getFlights : function() {
		$.ajax({
			url : "rest/flight",
			type : "GET",
			beforeSend : function(request) {
				request.setRequestHeader('Authorization', 'Basic '
						+ Authentication.token);
				from = $('#flightFilterFrom').val();
				to = $('#flightFilterTo').val();
				filter = "";
				if (from != "") {
					filter += "dateOfDepartureFrom="+from;
				}
				if (to != "") {
					if (filter != "") {
						filter += ",";
					}
					filter += "dateOfDepartureTo="+to;
				}
				request.setRequestHeader('Accept', 'application/json');
				request.setRequestHeader('X-Base', $('#flightCount').val());
				request.setRequestHeader('X-Offset', $('#flightOffset').val());
				request.setRequestHeader('X-Order', Flight.order);
				request.setRequestHeader('X-Filter', filter);
			},
			success : function(data) {
				Flight.flights = data;
				Destination.getDestinationsAndCallback(Flight.displayFlights);
			}
		});
	},
	displayFlights : function() {
		Flight.cleanFlights();
		res = '';
		for (i = 0; i < Destination.destinations.length; i++) {
			res += '<option value="' + Destination.destinations[i].id + '">'
					+ Destination.destinations[i].name + '</option>';
		}
		for (i = 0; i < Flight.flights.length; i++) {
			$(
					'<tr><td class="id"><a href="#" class="open">'
							+ Flight.flights[i].id
							+ '</a></td><td><input type="text" name="dateOfDeparture" class="dateOfDeparture input-small" value="'
							+ new Date(Flight.flights[i].dateOfDeparture).format("yyyy-MM-dd hh:mm:S")
							+ '" /></td><td><input type="text" name="name" class="name input-small" value="'
							+ Flight.flights[i].name
							+ '"</td><td><select name="from" class="from"><option value="'
							+ Flight.flights[i].from.id
							+ '" selected>'
							+ Flight.flights[i].from.name
							+ '</option>'
							+ res
							+ '</select></td><td><select name="to" class="to"><option value="'
							+ Flight.flights[i].to.id
							+ '" selected>'
							+ Flight.flights[i].to.name
							+ '</option>'
							+ res
							+ '</select></td><td><input type="text" name="seats" class="seats input-small" value="'
							+ Flight.flights[i].seats
							+ '"</td></td><td><button type="button" class="update btn btn-success"><i class="icon-edit icon-white"/></button> <button type="button" class="delete btn btn-danger"><i class="icon-remove icon-white"/></button></td></tr>')
					.appendTo($('#flightPart tbody'));
		}
		$('#flightPart tbody td .update').bind('click', function() {
			Flight.updateSubmit(this);
		});
		$('#flightPart tbody td .delete').bind('click', function() {
			Flight.deleteSubmit(this);
		});
		$('#flightPart tbody td .open').bind('click', function() {
			Reservation.open(this);
		});
	},
	updateSubmit : function(e) {
		line = $(e).parents('tr');
		id = line.find('.id').text();
		dateOfDeparture = line.find('.dateOfDeparture').val();
		name = line.find('.name').val();
		from = line.find('.from').val();
		to = line.find('.to').val();
		seats = line.find('.seats').val();

		Flight.updateFlight(id, dateOfDeparture, name, from, to, seats);
	},
	updateFlight : function(id, dateOfDeparture, name, from, to, seats) {
		$.ajax({
			url : "rest/flight/" + id,
			type : "PUT",
			data : '{"dateOfDeparture" : "' + dateOfDeparture + '", "name" : "'
					+ name + '", "from" : "' + from + '", "to" : "' + to
					+ '", "seats" : "' + seats + '"}',
			beforeSend : function(request) {
				request.setRequestHeader('Authorization', 'Basic '
						+ Authentication.token);
				request.setRequestHeader('Content-Type', 'application/json');
			},
			success : function(data) {
				$('#flightPart .message p').remove();
				$('<p class="text-success">Update success.</p>').appendTo(
						$('#flightPart .message'));
			},
			error : function(data) {
				$('#flightPart .message p').remove();
				$('<p class="text-error">Update faild.</p>').appendTo(
						$('#flightPart .message'));
			}
		});
	},
	deleteSubmit : function(e) {
		line = $(e).parents('tr');
		id = line.find('.id').text();
		Flight.deleteFlight(id);
	},
	deleteFlight : function(id) {
		$.ajax({
			url : "rest/flight/" + id,
			type : "DELETE",
			beforeSend : function(request) {
				request.setRequestHeader('Authorization', 'Basic '
						+ Authentication.token);
				request.setRequestHeader('Content-Type', 'application/json');
			},
			success : function(data) {
				$('#flightPart .message p').remove();
				$('<p class="text-success">Delete success.</p>').appendTo(
						$('#flightPart .message'));
				Flight.getFlights();
			},
			error : function(data) {
				$('#flightPart .message p').remove();
				$('<p class="text-error">Delete faild.</p>').appendTo(
						$('#flightPart .message'));
			}
		});
	},
	createSubmit : function(e) {
		dateOfDeparture = $('#flightDateOfDeparture').val();
		name = $('#flightName').val();
		from = $('#flightFrom').val();
		to = $('#flightTo').val();
		seats = $('#flightSeats').val();
		Flight.createFlight(name, dateOfDeparture, name, from, to);
		e.preventDefault();
	},
	createFlight : function(name) {
		$.ajax({
			url : "rest/flight/",
			type : "POST",
			data : '{"dateOfDeparture" : "' + dateOfDeparture + '", "name" : "'
			+ name + '", "from" : "' + from + '", "to" : "' + to
			+ '", "seats" : "' + seats + '"}',
			beforeSend : function(request) {
				request.setRequestHeader('Authorization', 'Basic '
						+ Authentication.token);
				request.setRequestHeader('Content-Type', 'application/json');
			},
			success : function(data) {
				$('#flightPart .message p').remove();
				$('<p class="text-success">Create success.</p>').appendTo(
						$('#flightPart .message'));
				Flight.getFlights();
			},
			error : function(data) {
				$('#flightPart .message p').remove();
				$('<p class="text-error">Create faild.</p>').appendTo(
						$('#flightPart .message'));
			}
		});
	},
	cleanFlights : function() {
		$('#flightPart tbody tr').remove();
	},
	order : 'id:asc',
	flights : []
}