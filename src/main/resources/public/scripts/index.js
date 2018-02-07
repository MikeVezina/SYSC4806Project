$(window).on ('load', function () {

    // Send request to API for app information
    $.get( "/api/information", function( data ) {
        $("#authorsDiv").text(data.authors);
        $("#versionDiv").text(data.version);
    });

});