const mealAjaxUrl = "meals/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: mealAjaxUrl
};

// $(document).ready(function () {
$(function () {
    makeEditable(
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
                    "desc"
                ]
            ]
        })
    );
});

let filterForm = $('#filterMealForm');
let filterParams;

function filterMeal() {
    let data = "filter?" + filterForm.serialize();
    filterParams = data;
    updateTable(data);
    successNoty("Filtering");
}

function filterMealReset() {
    filterParams = "";
    filterForm.trigger('reset');
    updateTable("all");
    successNoty("Filter reset");
}

function doUpdate() {
    if (filterParams != "" && filterParams !== undefined) {
        updateTable(filterParams);
    } else {
        updateTable("all");
    }
}