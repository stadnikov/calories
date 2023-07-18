const mealAjaxUrl = "profile/meals/";

function processSerializedData(data) {
    for (key in data) {
        if (key < 2) { //process Dates - 0,1 indexes
            let value = data[key].value;
            if (value.length == 10) { //no or invalid Date
                const dateParams = value.split(".");
                data[key].value = dateParams[2] + "-" + dateParams[1] + "-" + dateParams[0];
            }
        }
    }
    return data;
}

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: mealAjaxUrl,
    updateTable: function () {
        $.ajax({
            type: "GET",
            url: mealAjaxUrl + "filter",
            data: processSerializedData($("#filter").serializeArray())
        }).done(updateTableByData);
    }
}

function showDateTimePicker(id) {
    jQuery(id).datetimepicker({
        format: 'd.m.Y H:i',
        lang: 'ru'
    });
    jQuery(id).datetimepicker('show');
}

let minimumDate;
let maximumDate;

function showDatePicker(id) {
    jQuery(id).datetimepicker({
        onShow: function (ct) {
            if (id.startsWith('#end')) {
                if ((minimumDate = $('#startDate').val()).trim() != '') {
                    this.setOptions({
                        minDate: minimumDate,
                        formatDate: 'd.m.Y'
                    });
                }
            }

            if (id.startsWith('#start')) {
                if ((maximumDate = $('#endDate').val()).trim() != '') {
                    this.setOptions({
                        maxDate: maximumDate,
                        formatDate: 'd.m.Y'
                    })
                }
            }
        },
        timepicker: false,
        format: 'd.m.Y',
        lang: 'ru'
    });
    jQuery(id).datetimepicker('show');
}

let minimumTime;
let maximumTime;

function showTimePicker(id) {
    jQuery(id).datetimepicker({
        onShow: function (ct) {
            if (id.startsWith('#end')) {
                if ((minimumTime = $('#startTime').val()).trim() != '') {
                    this.setOptions({
                        minTime: minimumTime
                    });
                }
            }

            if (id.startsWith('#start')) {
                if ((maximumTime = $('#endTime').val()).trim() != '') {
                    this.setOptions({
                        maxTime: maximumTime
                    })
                }
            }
        },
        datepicker: false,
        format: 'H:i',
        lang: 'ru'
    });
    jQuery(id).datetimepicker('show');
}


function clearFilter() {
    $("#filter")[0].reset();
    $.get(mealAjaxUrl, updateTableByData);
}

$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "ajax": {
                "url": mealAjaxUrl,
                "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime",
                    "render": function (date, type, row) {
                        if (type === "display") {
                            return date.replace("T", " ").substring(0, 16);
                        }
                        return date;
                    }

                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderEditBtn
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderDeleteBtn
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ],
            "createdRow": function (row, data, dataIndex) {
                $(row).attr("data-meal-excess", data.excess);
            }

        })
    );
});