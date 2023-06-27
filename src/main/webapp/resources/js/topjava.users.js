const userAjaxUrl = "admin/users/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: userAjaxUrl
};

doUpdate = doUserUpdate;

// $(document).ready(function () {
$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "name"
                },
                {
                    "data": "email"
                },
                {
                    "data": "roles"
                },
                {
                    "data": "enabled"
                },
                {
                    "data": "registered"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "asc"
                ]
            ]
        })
    );
});

function userActive(userId, checkbox) {
    updateEnabled(userId, checkbox.checked);
}

function updateEnabled(userId, value) {
    $.ajax({
        type: "PATCH",
        url: ctx.ajaxUrl + userId + "?enabled=" + value
    })
        .done(function () {
            successNoty(value ? "Enabling user" : "Disabling user");
            //repaint element
            let element = document.getElementById(userId);
            element.setAttribute('enabled-user', value);
        })
        .fail(function (jqXHR) {
            if (jqXHR.status == 500 || jqXHR.status == 0) {
                // internal server error or internet connection broke
                failNoty(jqXHR);
            }
        });
}

function doUserUpdate() {
    updateTable("");
}