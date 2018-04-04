pageLoaded = function () {
    // Hide the collapsable write review element on DOM load
    var elem = $("#filtersCollapse");
    elem.removeClass("show");
};

$(document).on("DOMContentLoaded", pageLoaded);