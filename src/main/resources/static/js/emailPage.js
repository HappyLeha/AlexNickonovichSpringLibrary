let adresses=[];
$( document ).ready(function(){
    var href=$(location).attr('href');
    var array=href.toString().split("/");
    var path="http:/";
    for (var i=1;i<array.length-1;i++) {
        path+="/"+array[i];
    }
    var params=window.location.search.toString().split("=")[1];
    var id=params.split(";");
    var formData={
        id: id
    };
    $.ajax({
        url: path+"/user/email",
        method: "POST",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify(formData),
        success: function (data) {
            var emails = JSON.parse(JSON.stringify(data));
            if (emails.length>0) {
                $("#readers").css("height",20*(emails.length+2));
                var value="\n";
                $(emails).each(function (index,element) {
                        value+=element.login+"\n";
                        adresses.push(element.email);
                });
                $("#readers").val(value);
            }
        }
    });
    $("#form").submit(function () {
        var formData = {
            emails: adresses,
            subject: $("#subject").val(),
            text: $("#text").val()
        };
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: path + "/email",
            data: JSON.stringify(formData),
            dataType: 'text',
            success: [function (data) {
                window.location = path+'/readerlist';

            }],
            statusCode: {
                503: function (response) {
                    $( "#error" ).html("Почтовый сервер недоступен!");
                },
                404: function (response) {
                    window.location = path+'/readerlist';
                }
            }
        });
        return false;
    });
});