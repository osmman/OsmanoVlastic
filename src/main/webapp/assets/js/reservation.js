/*
 * Destination class enable to operate with diestinations.
 */

Reservation = {
	open : function(e) {
		// hide other
		line = $(e).parents('tr');
		Reservation.flightId = line.find('.id').text();
		$('#destinationPart').hide();
		$('#flightPart').hide();
		$('#reservationPart').show();
		Reservation.getReservations();
	},
	orderReservations : function(e) {
		element = $(e);
		if (element.attr('orderValue').length == 0
				|| element.attr('orderValue') == 'asc') {
			$('#reservationPart th a').attr('orderValue', '');
			$('#reservationPart th i').attr('class', '');
			element.attr('orderValue', 'desc');
			Reservation.order = element.attr('name') + ":desc";
			element.find('i').attr('class', 'icon-chevron-up');
		} else {
			$('#reservationPart th a').attr('orderValue', '');
			$('#reservationPart th i').attr('class', '');
			element.attr('orderValue', 'asc');
			Reservation.order = element.attr('name') + ":asc";
			element.find('i').attr('class', 'icon-chevron-down');
		}
		Reservation.getReservations();
	},
	getReservations : function() {
		$.ajax({
			url : "rest/flight/" + Reservation.flightId + "/reservation",
			type : "GET",
			beforeSend : function(request) {
				request.setRequestHeader('Authorization', 'Basic '
						+ Authentication.token);
				request.setRequestHeader('Accept', 'application/json');
				request
						.setRequestHeader('X-Base', $('#reservationCount')
								.val());
				request.setRequestHeader('X-Offset', $('#reservationOffset')
						.val());
				request.setRequestHeader('X-Order', Reservation.order);
			},
			success : function(data) {
				Reservation.reservations = data;
				Reservation.displayReservations();
			}
		});
	},
	displayReservations : function() {
		Reservation.cleanReservations();
		for (i = 0; i < Reservation.reservations.length; i++) {
			$(
					'<tr><td class="id">'
							+ Reservation.reservations[i].id
							+ '</td><td>'
							+ new Date(Reservation.reservations[i].created).format("yyyy-MM-dd hh:mm:S")
							+ '</td><td>'
							+ Reservation.reservations[i].password
							+ '</td><td><input type="text" name="seats" class="seats" value="'
							+ Reservation.reservations[i].seats
							+ '" /></td><td>'
							+ Reservation.reservations[i].state
							+ '</td><td><input type="text" name="password" class="password" value="" /></td><td><button type="button" class="update btn btn-success"><i class="icon-edit icon-white" /></button> <button type="button" class="delete btn btn-danger"><i class="icon-remove icon-white" /></button></td></tr>')
					.appendTo($('#reservationPart tbody'));
		}
		$('#reservationPart tbody td .update').bind('click', function() {
			Reservation.updateSubmit(this);
		});
		$('#reservationPart tbody td .delete').bind('click', function() {
			Reservation.deleteSubmit(this);
		});
	},
	updateSubmit : function(e) {
		line = $(e).parents('tr');
		id = line.find('.id').text();
		seats = line.find('.seats').val();
		password = line.find('.password').val();
		Reservation.updateReservation(id, seats);
	},
	updateReservation : function(id, seats, password) {
		$.ajax({
			url : "rest/flight/" + Reservation.flightId + "/reservation/" + id,
			type : "PUT",
			data : '{"seats" : "' + seats + '"}',
			beforeSend : function(request) {
				request.setRequestHeader('Authorization', 'Basic '
						+ Authentication.token);
				request.setRequestHeader('X-Password', password);
				request.setRequestHeader('Content-Type', 'application/json');
			},
			success : function(data) {
				$('#reservationPart .message p').remove();
				$('<p class="text-success">Update success.</p>').appendTo(
						$('#reservationPart .message'));
			},
			error : function(data) {
				$('#reservationPart .message p').remove();
				$('<p class="text-error">Update faild.</p>').appendTo(
						$('#reservationPart .message'));
			}
		});
	},
	deleteSubmit : function(e) {
		line = $(e).parents('tr');
		id = line.find('.id').text();
		password = line.find('.password').val();
		Reservation.deleteReservation(id, password);
	},
	deleteReservation : function(id, password) {
		$.ajax({
			url : "rest/flight/" + Reservation.flightId + "/reservation/"+id,
			type : "DELETE",
			beforeSend : function(request) {
				request.setRequestHeader('Authorization', 'Basic '
						+ Authentication.token);
				request.setRequestHeader('X-Password', password);
				request.setRequestHeader('Content-Type', 'application/json');
			},
			success : function(data) {
				$('#reservationPart .message p').remove();
				$('<p class="text-success">Delete success.</p>').appendTo(
						$('#reservationPart .message'));
				Reservation.getReservations();
			},
			error : function(data) {
				$('#reservationPart .message p').remove();
				$('<p class="text-error">Delete faild.</p>').appendTo(
						$('#reservationPart .message'));
			}
		});
	},
	createSubmit : function(e) {
		seats = $('#reservationSeats').val();
		Reservation.createReservation(seats);
		e.preventDefault();
	},
	createReservation : function(seats) {
		$.ajax({
			url : "rest/flight/" + Reservation.flightId + "/reservation/",
			type : "POST",
			data : '{"seats" : "' + seats + '"}',
			beforeSend : function(request) {
				request.setRequestHeader('Authorization', 'Basic '
						+ Authentication.token);
				request.setRequestHeader('Content-Type', 'application/json');
			},
			success : function(data, textStatus, request) {
				$('#reservationPart .message p').remove();
				$('<p class="text-success">Create success. Password is: <strong>'+request.getResponseHeader('X-Password')+'</strong></p>').appendTo(
						$('#reservationPart .message'));
				Reservation.getReservations();
			},
			error : function(data) {
				$('#reservationPart .message p').remove();
				$('<p class="text-error">Create faild.</p>').appendTo(
						$('#reservationPart .message'));
			}
		});
	},
	cleanReservations : function() {
		$('#reservationPart tbody tr').remove();
	},
	order : 'id:asc',
	flightId : '',
	reservations : []
}