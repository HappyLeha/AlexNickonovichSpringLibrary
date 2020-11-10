$( document ).ready(function(){
    var href=$(location).attr('href');
    var array=href.toString().split("/");
    var path="http:/";
    for (var i=1;i<array.length-1;i++) {
        path+="/"+array[i];
    }
    $.ajax({
        url: path+"/user",
        method: "GET",
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            var users=JSON.parse(JSON.stringify(data));
            $.each(users, function( index, user ) {
                var id=user.id;
                var login=user.login;
                var book=user.book==null?"":user.book;
                var bookId=user.bookId==null?"":user.bookId;
                var checkbox="<input type=\"checkbox\" class=\"checkbox\" " +
                    "id=\""+id+"\">";
                $("tbody").append("<tr>\n" +
                    "            <td>"
                    +checkbox+"</td>\n" +
                    "            <td><a href='/userpage/"+id+"'>"+login+"</a></td>\n" +
                    "            <td><a href='/bookpage/"+bookId+"'>"+book+"</a></td>\n" +
                    "        </tr>");
            });
        }
    });
    $("#button-email").click(function () {
        var readers="readers=";
        $(".checkbox:checked").each(function (index,element) {
            if (index===0) readers+=element.id;
            else readers+=";"+element.id;
        });
        if (readers!=="readers=") window.location = path+'/emailpage?'+readers;
    });
    $("#button-list").click(function () {
        window.location = path+'/booklist';
    });
});