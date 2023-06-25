const mealAjaxUrl = "meals/";

// https://stackoverflow.com/a/5064235/548473
const mealctx = {
    ajaxUrl: mealAjaxUrl
};

// $(document).ready(function () {
$(function () {
    makeMealEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
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


let filterForm = $('#filterMealForm');
let filterParams;

function filterMeal() {
    let data = "filter?" + filterForm.serialize();
    $.ajax({
        type: "GET",
        url: mealctx.ajaxUrl + "filter",
        data: filterForm.serialize()
    }).done(function () {
        filterParams = data;
        updateMealTable(data);
        successNoty("send filter form");
    })
}

function filterMealReset() {
    filterParams = "";
    filterForm.trigger('reset');
    updateMealTable("all");
    successNoty("filter reset");
}

function saveMeal() {
    $.ajax({
        type: "POST",
        url: mealctx.ajaxUrl,
        data: form.serialize()
    }).done(function () {
        $("#editRow").modal("hide");
        if (filterParams != "" && filterParams !== undefined) {
            updateMealTable(filterParams);
        } else {
            updateMealTable("all");
        }
        successNoty("Saved");
    })
}

function updateMealTable(text) {
    $.get(mealctx.ajaxUrl + text, function (data) {
        mealctx.datatableApi.clear().rows.add(data).draw();
    });
}

function deleteMealRow(id) {
    $.ajax({
        url: mealctx.ajaxUrl + id,
        type: "DELETE"
    }).done(function () {
        if (filterParams != "" && filterParams !== undefined) {
            updateMealTable(filterParams);
        } else {
            updateMealTable("all");
        }
        successNoty("Deleted");
    });
}

function makeMealEditable(datatableApi) {
    mealctx.datatableApi = datatableApi;
    form = $('#detailsMealForm');
    $(".delete").click(function () {
        if (confirm('Are you sure?')) {
            deleteMealRow($(this).closest('tr').attr("id"));
        }
    });

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(jqXHR);
    });

    // solve problem with cache in IE: https://stackoverflow.com/a/4303862/548473
    $.ajaxSetup({cache: false});
}