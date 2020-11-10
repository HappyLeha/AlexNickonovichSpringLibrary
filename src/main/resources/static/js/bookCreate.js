$( document ).ready(function() {
    var href = $(location).attr('href');
    var array = href.toString().split("/");
    var id = array[array.length - 1];
    var path = "http:/";
    var date = new Date();
    var month = date.getMonth() + 1;
    month = month < 10 ? "0" + month : month;
    var day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
    $("#date").prop("max", date.getFullYear() + "-" + month + "-" + day);
    for (var i = 1; i < array.length - 1; i++) {
        path += "/" + array[i];
    }
    $('#button-yellow').click(function () {
        window.location = path+'/bookcreate';
    });
    $("#form").submit(function () {
        var formData = {
            title: $("#title").val(),
            date: $("#date").val(),
            count: $("#count").val(),
            publishing: $("#publishing").val().toString() === "" ? null : $("#publishing").val(),
            authors: $("#authors").val().toString() === "" ? null : $("#authors").val()
        };
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: path + "/book",
            data: JSON.stringify(formData),
            dataType: 'text',
            success: [function (data) {
                window.location = path+'/booklist';
            }],
        });
        return false;
    });
});