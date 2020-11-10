var base64=null;
var login=null;
var id=null;
var href = $(location).attr('href');
var array = href.toString().split("/");
var path = "http:/";
for (var i = 1; i < array.length - 1; i++) {
    path += "/" + array[i];
}
$('#button-yellow').click(function () {
    window.location = path+'/profile';
});
$( document ).ready(function() {
        $('#image').click(function () {
            $( "#input" ).click();
        });
        $( "#input" ).change(function (event) {
            var input = $(this)[0];
            var reader = new FileReader();
            reader.onload = function (e) {
                $('#image').attr('src', e.target.result);
                base64=e.target.result;
            };
            reader.readAsDataURL(input.files[0]);
        });
        $.ajax({
            url: "user/profile",
            method: "GET",
            contentType: "application/json",
            dataType: "json",
            success: function (data) {
                var user = JSON.parse(JSON.stringify(data));
                id=user.id;
                login=user.login;
                var email=user.email;
                var phone=user.phone==null?"":user.phone;
                base64=user.photo==null?null:user.photo;
                $("#login").val(login);
                $("#email").val(email);
                $("#phone").val(phone);
                if (base64!=null) $('#image').attr('src', base64);
                else {
                    $("#div-checkbox").css('visibility',"collapse");
                }
            }
        });
        $( "#form-put" ).submit(function(){
            if ($("#checkbox").prop("checked")) {
                base64=null;
            }
            var formData={
                login: $( "#login" ).val(),
                password: $( "#password" ).val().toString()===""?null:$( "#password" ).val(),
                repeatPassword: $( "#password-repeat" ).val().toString()===""?null:$( "#password-repeat" ).val(),
                email: $( "#email" ).val(),
                phone: $( "#phone" ).val().toString()===""?null:$( "#phone" ).val(),
                photo: base64==null?null:base64.toString(),
            };
            $.ajax({
                type: "PUT",
                contentType : "application/json",
                url: "user",
                data: JSON.stringify(formData),
                dataType:'text',
                success: [function(data){
                    if (login===$( "#login" ).val().toString()) window.location = path+'/profile';
                    else window.location = path+'/logout';
                }],
                statusCode: {
                    406: function (response) {
                        $( "#error" ).html("Введённые пароли не совпадают!");
                    },
                    409: function (response) {
                        $( "#error" ).html("Пользователь с таким логином уже существует!");
                    }
                }
            });
            return false;
        });
        $("#form-delete").submit(function () {
            $.ajax({
                url: 'user',
                contentType : "application/json",
                type: 'DELETE',
                dataType:'text',
                success: [function (result) {
                    window.location = path+'/logout';
                }],
                statusCode: {
                    409: function (response) {
                        $( "#error" ).html("Перед удалением профиля необходимо вернуть книги!");
                    }
                }
            });
            return false;
        });
    }
);