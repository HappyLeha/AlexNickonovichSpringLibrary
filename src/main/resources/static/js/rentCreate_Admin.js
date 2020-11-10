$( document ).ready(function(){
    var users=null;
    var href=$(location).attr('href');
    var array=href.toString().split("/");
    var id=array[array.length-1];
    var path="http:/";
    for (var i=1;i<array.length-2;i++) {
        path+="/"+array[i];
    }
    var date = new Date();
    var month = date.getMonth() + 1;
    month = month < 10 ? "0" + month : month;
    var day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
    $("#start-date").val(date.getFullYear() + "-" + month + "-" + day);
    $("#book").prop( "disabled", true );
    $("#start-date").prop( "disabled", true );
    $("#finish-date").prop("min", date.getFullYear() + "-" + month + "-" + day);
    $.ajax({
        url: path + "/book/" + id,
        method: "GET",
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            var book = JSON.parse(JSON.stringify(data));
            $("#book").val(book.title);
        },
        statusCode: {
            404: function (response) {
                window.location = path+'/booklist';
            }
        }
    });
    $.ajax({
        url: path +"/user/free",
        method: "GET",
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            users = JSON.parse(JSON.stringify(data));
            if (users.length===0) window.location = path+'/booklist';
            $.each(users, function( index, user ) {
                $("#reader").append("<option class='option' id='"+user.id+"'>"+user.login+"</option>");
            });
            }
    });
    $( "#form" ).submit(function(){
        var selected=null;
        $(".option:selected").each(function (index,element) {
            selected=element.id;
        });
        var formData={
            user: selected,
            book: id,
            startDate: $( "#start-date" ).val(),
            endDate: $( "#finish-date" ).val(),
        };
        $.ajax({
            type: "POST",
            contentType : "application/json",
            url: path +"/rent",
            data: JSON.stringify(formData),
            dataType:'text',
            success: [function(data){
                window.location = path+'/booklist';
            }],
            statusCode: {
                409: function (response) {
                    window.location = path+'/booklist';
                },
                404: function (response) {
                    window.location = path+'/booklist';
                }
            }
        });
        return false;
    });
});