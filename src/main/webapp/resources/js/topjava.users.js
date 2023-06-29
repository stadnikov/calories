const userAjaxUrl = "admin/users/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: userAjaxUrl
};

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
    $.ajax({
        type: "PATCH",
        url: ctx.ajaxUrl + userId + "?enabled=" + checkbox.checked
    })
        .done(function () {
            successNoty(checkbox.checked ? "Enabling user" : "Disabling user");
            //repaint element
            let element = document.getElementById(userId);
            element.setAttribute('enabled-user', checkbox.checked);
        })
        .fail(function (jqXHR) {
            checkbox.checked = !checkbox.checked;
        });
}

function doUpdate() {
    updateTable("");
}