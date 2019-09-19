var Login = function() {

    var handleLogin = function() {

        $('.login-form').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            rules: {
                username: {
                    required: true
                },
                password: {
                    required: true
                },
                remember: {
                    required: false
                }
            },

            messages: {
                username: {
                    required: "用户名必须输入."
                },
                password: {
                    required: "密码必须输入."
                }
            },

            invalidHandler: function(event, validator) { //display error alert on form submit   
                //$('.alert-danger', $('.login-form')).show();
            },

            highlight: function(element) { // hightlight error inputs
                $(element)
                    .closest('.form-group').addClass('has-error'); // set error class to the control group
            },

            success: function(label) {
                label.closest('.form-group').removeClass('has-error');
                label.remove();
            },

            errorPlacement: function(error, element) {
                if (element.closest('.input-icon').size() === 1) {
                    error.insertAfter(element.closest('.input-icon'));
                } else {
                    error.insertAfter(element);
                }
            },

            submitHandler: function(form) {
                form.submit(); // form validation success, call ajax form submit
            }
        });

        $('.login-form input').keypress(function(e) {
            if (e.which == 13) {
                if ($('.login-form').validate().form()) {
                    //校验用户名和密码
                    var logData = $('.login-form').getFormData();
                    loginCheck([logData.username, hex_md5(logData.password)]);
                }
                return false;
            }
        });

        $('#login-btn').click(function(){
            if ($('.login-form').validate().form()) {
                //校验用户名和密码
                var logData = $('.login-form').getFormData();
                loginCheck([logData.username, hex_md5(logData.password)]);
            }
        });
    };

    return {
        init: function() {
            handleLogin();
            $('.login-bg').backstretch([
                    "../../public/manager/assets/pages/img/2.jpg",
                    "../../public/manager/assets/pages/img/3.jpg",
                    "../../public/manager/assets/pages/img/4.jpeg"
                ], {
                    fade: 1000,
                    duration: 8000
                }
            );
        }
    };
}();

function loginCheckEnd(flg, result){
    if(flg){
        if (result && result.retcode == SUCCESS){
            //进入主页面
            localStorage.setItem("uid", result.uid);
            localStorage.setItem("uname", result.uname);
            loginSubmit();
        }else{
            $('.alert-danger', $('.login-form')).show();
            $('.alert-danger span', $('.login-form')).text(result.retmsg);
        }
    }else{
        $('.alert-danger', $('.login-form')).show();
        $('.alert-danger span', $('.login-form')).text("登录失败");
    }
}

function loginSubmit(data){
    var form = $(".login-form");
    form[0].action = "/web/portal/main";
    form.submit();
}

jQuery(document).ready(function() {
    Login.init();
    localStorage.clear();
});