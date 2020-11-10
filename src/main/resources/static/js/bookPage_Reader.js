$( document ).ready(function() {
var href=$(location).attr('href');
var array=href.toString().split("/");
var id=array[array.length-1];
var path="http:/";
for (var i=1;i<array.length-2;i++) {
    path+="/"+array[i];
}
$( "#title" ).prop( "disabled", true );
$( "#count" ).prop( "disabled", true );
$( "#date" ).prop( "disabled", true );
$( "#publishing" ).prop( "disabled", true );
$( "#authors" ).prop( "disabled", true );
        $.ajax({
            url: path+"/book/"+id,
            method: "GET",
            contentType: "application/json",
            dataType: "json",
            success: function (data) {
                var book = JSON.parse(JSON.stringify(data));
                var title=book.title;
                var count=book.count;
                var date=book.date;
                var publishing=book.publishing==null?"":book.publishing;
                var authors=book.authors==null?"":book.authors;
                $("#title").val(title);
                $("#count").val(count);
                $("#date").val(date);
                $("#publishing").val(publishing);
                $("#authors").val(authors);
            },
            statusCode:  {
                404: function (response) {
                       window.location = path+'/booklist';
                }
            }
        });
});