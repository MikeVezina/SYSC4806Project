generateButtonClicked = function()
{
    $.get("/admin/generate", function (data) {
        alert(data.replace("<br>", "\n"));
    });

    return false;
};

pageLoaded = function () {
    // Hide the collapsable write review element on DOM load
    var elem = $("#generateDataButton");

    if(elem) {
        elem.click(generateButtonClicked);
    }
};

$(pageLoaded);