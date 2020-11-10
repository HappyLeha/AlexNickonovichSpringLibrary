$( document ).ready(function(){
    var user=null;
    var href=$(location).attr('href');
    var array=href.toString().split("/");
    var id=array[array.length-1];
    var path="http:/";
    for (var i=1;i<array.length-2;i++) {
        path+="/"+array[i];
    }
    $("#book").prop( "disabled", true );
    $("#reader").prop( "disabled", true );
    $("#start-date").prop( "disabled", true );
    $("#finish-date").prop( "disabled", true );
    $.ajax({
        url: path + "/rent/" + id,
        method: "GET",
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            var rent = JSON.parse(JSON.stringify(data));
            $("#book").val(rent.book);
            $("#reader").append("<option>"+rent.user+"</option>");
            $("#start-date").val(rent.startDate);
            $("#finish-date").val(rent.endDate);
        },
        statusCode: {
            404: function (response) {
                window.location = path+'/booklist';
            }
        }
    });
    $("#form").submit(function () {
        $.ajax({
            url: path + '/rent',
            contentType : "application/json",
            type: 'DELETE',
            dataType:'text',
            success: [function (result) {
                window.location = path+'/booklist';
            }],
        });
        return false;
    });
});