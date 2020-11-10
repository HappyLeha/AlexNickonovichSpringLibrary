var href = $(location).attr('href');
var array = href.toString().split("/");
var path = "http:/";
for (var i = 1; i < array.length - 1; i++) {
    path += "/" + array[i];
}
function toRentpage(id) {
    window.location = path+'/rentpage/'+id;
}
function toRentcreate(id) {
    window.location = path+'/rentcreate/'+id;
}
$( document ).ready(function(){
    $.ajax({
        url: "book/free",
        method: "GET",
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            var books=JSON.parse(JSON.stringify(data));
            var flag=true;
            $.each(books, function( index, book ) {
                if (book.rentId!=null) flag=false;
            });
            $.each(books, function( index, book ) {
                var id=book.id;
                var title=book.title==null?"":book.title;
                var date=book.date==null?"":book.date;
                var publishing=book.publishing==null?"":book.publishing;
                var button="<button class=\"button-return\" onclick=\"toRentpage("+book.rentId+")\">\n" +
                    "                Вернуть книгу\n" +
                    "            </button>";
                if (book.rentId==null) {
                    button="<button class=\"button-give\" onclick=\"toRentcreate("+id+")\">\n" +
                        "                Взять книгу\n" +
                        "            </button>";
                }
                if (book.rentId==null&&!flag) {
                    button="";
                }
                $("tbody").append("<tr>\n" +
                    "            <td><a href='/bookpage/"+id+"'>"+title+"</a></td>\n" +
                    "            <td>"+date+"</td>\n" +
                    "            <td>"+publishing+"</td>\n" +
                    "            <td>"+button+"</td>\n" +
                    "        </tr>");
            });
        }
    });
});