let form;

function makeEditable(datatableApi) {
    ctx.datatableApi = datatableApi;
    form = $('#detailsForm');

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(jqXHR);
    });

    // solve problem with cache in IE: https://stackoverflow.com/a/4303862/548473
    $.ajaxSetup({cache: false});
}

function add() {
    $("#modalTitle").html(i18n[$(location).attr('pathname') == "/topjava/users" ? "addTitle" : "addMealTitle"]);
    form.find(":input").val("");
    $("#editRow").modal();
}

function updateRow(id) {
    form.find(":input").val("");
    $("#modalTitle").html(i18n[$(location).attr('pathname') == "/topjava/users" ? "editTitle" : "editMealTitle"]);
    $.get(ctx.ajaxUrl + id, function (data) {
        $.each(data, function (key, value) {
            if (key == 'dateTime') {
                value = isoToDate(value);
            }
            form.find("input[name='" + key + "']").val(value);
        });
        $('#editRow').modal();
    });
}

function isoToDate(iso) {
    //https://stackoverflow.com/questions/61653453/convert-date-string-from-iso-8601-format-yyyy-mm-ddthhmmss-sssz-to-dd-mm-y
    const dateStr = iso, [yyyy, mm, dd, hh, mi] = dateStr.split(/[/:\-T]/);
    return `${dd}.${mm}.${yyyy} ${hh}:${mi}`;
}

function deleteRow(id) {
    if (confirm(i18n['common.confirm'])) {
        $.ajax({
            url: ctx.ajaxUrl + id,
            type: "DELETE"
        }).done(function () {
            ctx.updateTable();
            successNoty("common.deleted");
        });
    }
}

function updateTableByData(data) {
    ctx.datatableApi.clear().rows.add(data).draw();
}

function processModalSerializedData(data) {
    for (key in data) {
        if (key == 1) {
            let data1 = data[key].value;
            //len should be 16
            if (data1.length == 16) {
                let dateAndTime = data1.split(" "); // 0-date 1-time
                let dateParams = dateAndTime[0].split(".");
                data[key].value = dateParams[2] + "-" + dateParams[1] + "-" + dateParams[0] + "T" + dateAndTime[1];
            }
        }
    }
    return data;
}

function save() {
    $.ajax({
        type: "POST",
        url: ctx.ajaxUrl,
        data: processModalSerializedData(form.serializeArray())
    }).done(function () {
        $("#editRow").modal("hide");
        ctx.updateTable();
        successNoty("common.saved");
    });
}

let failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(key) {
    closeNoty();
    new Noty({
        text: "<span class='fa fa-lg fa-check'></span> &nbsp;" + i18n[key],
        type: 'success',
        layout: "bottomRight",
        timeout: 1000
    }).show();
}

function renderEditBtn(data, type, row) {
    if (type === "display") {
        return "<a onclick='updateRow(" + row.id + ");'><span class='fa fa-pencil'></span></a>";
    }
}

function renderDeleteBtn(data, type, row) {
    if (type === "display") {
        return "<a onclick='deleteRow(" + row.id + ");'><span class='fa fa-remove'></span></a>";
    }
}

function failNoty(jqXHR) {
    closeNoty();
    failedNote = new Noty({
        text: "<span class='fa fa-lg fa-exclamation-circle'></span> &nbsp;" + i18n["common.errorStatus"] + ": " + jqXHR.status + (jqXHR.responseJSON ? "<br>" + jqXHR.responseJSON : ""),
        type: "error",
        layout: "bottomRight"
    });
    failedNote.show()
}