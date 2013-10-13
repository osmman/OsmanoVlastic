/*
 * Authentication method provide Sign in and generating HTTP Basic
 * 
 */

Authentication = {
	signInSubmit : function(e) {
		if ($('#authenticationName').val().length == 0 || $('#authenticationName').val().length == 0) {
			return;
		}
		Authentication.signIn($('#authenticationName').val(), $(
				'#authenticationPassword').val());
		$('#authenticationName').val("");
		$('#authenticationPassword').val("");
		$('#signIn').hide();
		$('#signOut').show();
		$('#authenticationSignetUser').text(Authentication.name);
		$('#body').show();
		$('#body-unautorized').hide();
		e.preventDefault();
	},
	signOutSubmit : function() {
		Authentication.signOut();
		$('#signIn').show();
		$('#signOut').hide();
		$('#authenticationSignetUser').text('');
		$('#body').hide();
		$('#body-unautorized').show();
	},
	signIn : function(name, password) {
		Authentication.name = name;
		Authentication.token = window.btoa(Authentication.name+":"+password);
	},
	signOut : function() {
		Authentication.name = "";
		Authentication.token = "";
		$('#destinationPart').hide();
		Destination.cleanDestinations();
	},
	name : "",
	token : ""
}