var base64=null;
$('#photo-clear').click(function () {
    base64=null;
    $('#image').src="../static/images/avatar.png";
});
$( document ).ready(function(){
    var href = $(location).attr('href');
    var array = href.toString().split("/");
    var path = "http:/";
    for (var i = 1; i < array.length - 1; i++) {
        path += "/" + array[i];
    }
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
    $( "#form" ).submit(function(){
        var formData={
            login: $( "#login" ).val(),
            password: $( "#password" ).val(),
            repeatPassword: $( "#password-repeat" ).val(),
            firstName: $( "#firstname" ).val(),
            lastName: $( "#lastname" ).val(),
            year: $( "#year" ).val().toString()===""?null:$( "#year" ).val(),
            email: $( "#email" ).val(),
            phone: $( "#phone" ).val().toString()===""?null:$( "#phone" ).val(),
            photo: base64==null?null:base64.toString(),

        };
        $.ajax({
            type: "POST",
            contentType : "application/json",
            url: "user",
            data: JSON.stringify(formData),
            dataType:'text',
            success: [function(data){
                window.location = path+'/booklist';
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
});