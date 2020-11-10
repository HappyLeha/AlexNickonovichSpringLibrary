var href = $(location).attr('href');
var array = href.toString().split("/");
var id = array[array.length - 1];
var base64=null;
var login=null;
var path = "http:/";
for (var i = 1; i < array.length - 2; i++) {
    path += "/" + array[i];
}
$( document ).ready(function() {
    $("#login").prop( "disabled", true );
    $("#firstname").prop( "disabled", true );
    $("#lastname").prop( "disabled", true );
    $("#year").prop( "disabled", true );
    $("#email").prop( "disabled", true );
    $("#phone").prop( "disabled", true );
        $.ajax({
            url: path+"/user/"+id,
            method: "GET",
            contentType: "application/json",
            dataType: "json",
            success: function (data) {
                var user = JSON.parse(JSON.stringify(data));
                id=user.id;
                login=user.login;
                var firstname=user.firstName;
                var lastname=user.lastName;
                var year=user.year==null?"":user.year;
                var email=user.email;
                var phone=user.phone==null?"":user.phone;
                base64=user.photo==null?null:user.photo;
                $("#login").val(login);
                $("#firstname").val(firstname);
                $("#lastname").val(lastname);
                $("#year").val(year);
                $("#email").val(email);
                $("#phone").val(phone);
                if (base64!=null) $('#image').attr('src', base64);
            },
            statusCode: {
                404: function (response) {
                    window.location = path+'/booklist';
                }
            }
        });
        $("#form-delete").submit(function () {
            $.ajax({
                url: path+'/user/'+id,
                contentType : "application/json",
                type: 'DELETE',
                dataType:'text',
                success: [function (result) {
                    window.location = path+'/booklist';
                }],
                    statusCode: {
                    409: function (response) {
                        $( "#error" ).html("Перед удалением читателя необходимо вернуть книги!");
                    }
                }
            });
                return false;
            });
    }
);