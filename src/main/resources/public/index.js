$(window).on ('load', function () {

    // Send request to API for app authors
    $.get( "/api/authors", function( data ) {
        $("#authorsDiv").text(data);
    });

    // Send request to API for app version
    $.get( "/api/version", function( data ) {
        $("#versionDiv").text(data);
    });

});