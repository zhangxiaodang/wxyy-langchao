
if (App.isAngularJsApp() === false) {
    jQuery(document).ready(function() {
        //获取当前通知
        cancelNoticeGet({});
        //日期初始化
        ComponentsDatePickers.init()
        //节假日编辑初始化
        CancelEdit.init();
    });
}


var ComponentsDatePickers = function () {
    var handleDatePickers = function () {
        if (jQuery().datepicker) {
            var date = getNowFormatDate().substr(0, 4) + "-12-31";
            $('.date-picker').datepicker({
                rtl: App.isRTL(),
                orientation: "auto",
                autoclose: true,
                language:"zh-CN",
                todayBtn:true,
                format:"yyyy-mm-dd",
                todayHighlight: true,
                endDate: date,
                clearBtn: true
            });
            var currentDate = getNowFormatDate();
            $('.date-picker').datepicker("setDate", currentDate);
        }
    };

    return {
        init: function () {
            handleDatePickers();
        }
    };
}();


var CancelEdit = function() {
    var handleRegister = function() {
        var validator = $('.cancel-form').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",
            rules: {
                notice: {
                    required: true
                },
                rq: {
                    required: true
                }
            },

            messages: {
                notice: {
                    required: "通知内容必须输入"
                },
                rq: {
                    required: "取消预约日期必须输入"
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
                if (element.attr("name") == "tnc") { // insert checkbox errors after the container
                    error.insertAfter($('#register_tnc_error'));
                } else if (element.closest('.input-icon').size() === 1) {
                    error.insertAfter(element.closest('.input-icon'));
                } else {
                    error.insertAfter(element);
                }
            },

            submitHandler: function(form) {
                form.submit();
            }
        });

        $('#cancel').click(function() {
            if ($('.cancel-form').validate().form()) {
                var cancel = $('.cancel-form').getFormData();
                cancelNoticeEdit(cancel);
            }
        });
    };

    return {
        //main function to initiate the module
        init: function() {
            handleRegister();
        }
    };
}();

function getNoticeDataEnd(flg, result, callback){
    App.unblockUI('#lay-out');
    if(flg){
        if (result && result.retcode == SUCCESS) {
            $('.date-picker').datepicker("setDate", result.rq);
            $("#notice").text(result.notice);
        }else{
            var currentDate = getNowFormatDate();
            $('.date-picker').datepicker("setDate", currentDate);
            $("#notice").text("");
            alertDialog("取消通知获取失败！");
        }
    }else{
        var currentDate = getNowFormatDate();
        $('.date-picker').datepicker("setDate", currentDate);
        $("#notice").text("");
        alertDialog("取消通知获取失败！");
    }
}

function noticeInfoEditEnd(flg, result, type){
    var res = "失败";
    var text = "保存";
    var alert = "";
    if(flg){
        if(result && result.retcode != SUCCESS){
            alert = result.retmsg;
        }
        if (result && result.retcode == SUCCESS) {
            res = "成功";
        }
    }
    if(alert == "") alert = text + "通知" + res + "！";
    App.unblockUI('#lay-out');
    alertDialog(alert);
}