pageLoaded = function () {
    // Hide the collapsable write review element on DOM load
    var elem = $("#writeReviewCollapse");
    elem.removeClass("show");
};

$(document).on("DOMContentLoaded", pageLoaded);