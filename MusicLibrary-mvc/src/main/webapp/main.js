
function prepareDialog(name) {
    $("#dialog-" + name).dialog({autoOpen: false});
    $("#opener-" + name).click(function () {
        $("#dialog-" + name).dialog("open");
    });
}


