'use strict';

/**
 * Ugly java script code for simple tests of shareit's REST interface.
 *  @author Axel Böttcher <axel.boettcher@hm.edu>
 */

 var state = {

 };

var authenticate = function (username, password) {
    var credentialsJSON = JSON.stringify({
         username : username,
         password : password
     });

    $.ajax({
        url : '/shareit/sso/authenticate',
        type : 'POST',
        contentType : 'application/json',
        data : credentialsJSON
    })
    .done((response) => {
        console.log("Hello " + username);

        state.jwt = response.accessToken;
        showError();
    })
    .fail((error) => {

         showError(error.status);

    });
}

/**
 * This function is used for transfer of new book info.
 */
var submitNewBook = function() {
	var json = JSON.stringify({
			title: $("input[name=title]").val(),
			author: $("input[name=author]").val(),
			isbn: $("input[name=isbn]").val()
	});
    $.ajax({
        url: '/shareit/media/books/',
        type:'POST',
        contentType: 'application/json; charset=UTF-8',
        headers : {
            "Authorization" : "Bearer " + state.jwt
        },
        data: json
        })
        .done(() => {
			$("input[name=title]").val("");
			$("input[name=author]").val("");
			$("input[name=isbn]").val("");
        	
        	showError();
        })
        .fail((error) => {
        	showError(error.responseJSON.detail);
        });
}

/**
 * Creates a list of all books using a Mustache-template.
 */
var listBooks = function() {
	$.ajax({
        url: '/shareit/media/books',
        type:'GET'
	})
	.done((data) => {
		var template = "<table class='u-full-width'><tbody>{{#data}}<tr><td>{{title}}</td><td>{{author}}</td><td>{{isbn}}</td></tr>{{/data}}</tbody></table>";
		Mustache.parse(template);
		var output = Mustache.render(template, {data: data});
		$("#content").html(output);
	});// no error handling
}

/**
 * Call backer for "navigational buttons" in left column. Used to set content in main part.
 */
var changeContent = function(content) {
	$.ajax({
        url: content,
        type:'GET'
	})
	.done((data) => {
		$("#content").html(data);
	});// no error handling
}

function showError(message) {
    var errorText = $("#errormessage");

    if (message) {

        errorText.addClass("visible");
        errorText.text(message);
        errorText.removeClass("hidden");

    } else {

        // hide view
        errorText.removeClass('visible');
        errorText.addClass('hidden');

    }
}

authenticate("admin", "root!Lord123");
