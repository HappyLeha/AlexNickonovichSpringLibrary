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
    for (var i = 1; i < array.length - 2; i++) {
        path += "/" + array[i];
    }
    $.ajax({
        url: path + "/book/" + id,
        method: "GET",
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            var book = JSON.parse(JSON.stringify(data));
            var title = book.title;
            var count = book.count;
            var date = book.date;
            var publishing = book.publishing == null ? "" : book.publishing;
            var authors = book.authors == null ? "" : book.authors;
            $("#title").val(title);
            $("#count").val(count);
            $("#date").val(date);
            $("#publishing").val(publishing);
            $("#authors").val(authors);
            },
        statusCode: {
            404: function (response) {
                window.location = path+'/booklist';
            },
            409: function (response) {
                $("#error").html("Количество экземпляров книги меньше количества выданных книг!");
            }
        }
    });
    $("#form-put").submit(function () {
        var formData = {
            id: id,
            title: $("#title").val(),
            date: $("#date").val(),
            count: $("#count").val(),
            publishing: $("#publishing").val().toString() === "" ? null : $("#publishing").val(),
            authors: $("#authors").val().toString() === "" ? null : $("#authors").val()
        };
        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: path + "/book",
            data: JSON.stringify(formData),
            dataType: 'text',
            success: [function (data) {
                window.location = path+'/bookpage/' + id;
            }],
            statusCode: {
                406: function (response) {
                    $("#error").html("Введённые пароли не совпадают!");
                },
                409: function (response) {
                    $("#error").html("Количество экземпляров книги меньше количества выданых книг!");
                }
            }
        });
        return false;
    });
    $('#button-yellow').click(function () {
        window.location = path+'/bookpage/' + id;
    });
    $("#form-delete").submit(function () {
        $.ajax({
            url: path + '/book/'+id,
            contentType : "application/json",
            type: 'DELETE',
            dataType:'text',
            success: [function (result) {
                window.location = path+'/booklist';
            }],
            statusCode: {
                409: function (response) {
                    $( "#error" ).html("Перед удалением книги нужно вернуть все её экземпляры!");
                }
            }
        });
        return false;
    });
});