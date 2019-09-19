/**
 * Created by Administrator on 2019/2/22.
 */
if (App.isAngularJsApp() === false) {
    jQuery(document).ready(function() {
        Password.init();
    });
}

var Password = function() {
    var handlePassword = function() {
        $('.password-form').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",
            rules: {
                oldpasswd: {
                    required: true,
                    minlength: 6,
                    maxlength: 12
                },
                newpasswd1: {
                    required: true,
                    simple: true,
                    same: true,
                    minlength: 6,
                    maxlength: 12
                },
                newpasswd2: {
                    equalTo: "#newpasswd1"
                }
            },

            messages: {
                oldpasswd: {
                    required: "原密码必须输入"
                },
                newpasswd1: {
                    required: "新密码必须输入"
                },
                newpasswd2: {
                    equalTo: "确认密码必须与新密码一致"
                }
            },

            invalidHandler: function(event, validator) { //display error alert on form submit

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
                form.submit();
            }
        });

        jQuery.validator.addMethod("simple", function(value, element) {
            return passwordCheck(value);
        }, "新密码过于简单");

        jQuery.validator.addMethod("same", function(value, element) {
            return value != $("#oldpasswd").val();
        }, "新密码不能与旧密码相同");

        $('#password_modify').click(function() {
            if ($('.password-form').validate().form()) {
                var data = $('.password-form').getFormData();
                var user = {
                    uid: localStorage.getItem("uid"),
                    oldpasswd: hex_md5(data.oldpasswd),
                    newpasswd1: hex_md5(data.newpasswd1),
                    newpasswd2: hex_md5(data.newpasswd2)
                };
                passwordModify(user);
            }
        });
    };

    return {
        //main function to initiate the module
        init: function() {
            handlePassword();
        }
    };
}();

function passwordModifyEnd(flg, result){
    var res = "失败！";
    var alert = "";
    if(flg){
        if(result && result.retcode != SUCCESS){
            alert = result.retmsg;
        }
        if (result && result.retcode == SUCCESS) {
            res = "成功！";
        }
    }
    if(alert == "") alert = "密码修改" + res;
    App.unblockUI('#lay-out');
    alertDialog(alert);
}