var href = $(location).attr('href');
var array = href.toString().split("/");
var path = "http:/";
for (var i = 1; i < array.length - 1; i++) {
    path += "/" + array[i];
}
function toRentcreate(id) {
    window.location = path+'/rentcreate/'+id;
}
function toRentpage(id) {
    window.location = path+'/rentpage/'+id;
}
$( document ).ready(function(){
    $.ajax({
        url: "book",
        method: "GET",
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            var books=JSON.parse(JSON.stringify(data));
            $.each(books, function( index, book ) {
                var id=book.id;
                var userId=book.userId==null?"":book.userId;
                var rentId=book.rentId==null?"":book.rentId;
                var checkbox="";
                var button="<button class=\"button-check\" onclick=\"toRentpage("+rentId+")\">\n" +
                    "                Информация о брони\n" +
                    "            </button>";
                var title=book.title==null?"":book.title;
                var date=book.date==null?"":book.date;
                var publishing=book.publishing==null?"":book.publishing;
                var name=book.name==null?"":"<a href='/readerpage/"+userId+"'>"+book.name+"</a>";
                if (rentId==="") {
                    checkbox = "<input type=\"checkbox\" class=\"checkbox\" " +
                        "id=\""+index+"-"+id+"\">";

                    button="<button class=\"button-give\" onclick=\"toRentcreate("+id+")\">\n" +
                        "                Выдать книгу\n" +
                        "            </button>";
                }
                $("tbody").append("<tr>\n" +
                    "            <td>"
                    +checkbox+"</td>\n" +
                    "            <td><a href='/bookpage/"+id+"'>"+title+"</a></td>\n" +
                    "            <td>"+date+"</td>\n" +
                    "            <td>"+publishing+"</td>\n" +
                    "            <td>"+name+"</td>\n" +
                    "            <td>" +
                    button +
                    "            </td>\n" +
                    "        </tr>");
            });
        }
    });
    $("#button-red").click(function () {
        let books = [];
        $(".checkbox:checked").each(function (index,element) {
            let split=String(element.id).split("-");
            var book={
                id: split[1]
            };
            books.push(book);
        });
        if (books.length===0) return;
        $.ajax({
            url: 'book',
            contentType : "application/json",
            type: 'DELETE',
            data: JSON.stringify(books),
            success: function(result) {
                window.location = path+'/booklist';
            }
        });
    });
    $("#button-green").click(function () {
        window.location = path+'/bookcreate';
    });
    $("#button-blue").click(function () {
        window.location = path+'/readerlist';
    });
});