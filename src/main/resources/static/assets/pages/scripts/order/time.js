/**
 * Created by Jianggy on 2019/6/24.
 */
if (App.isAngularJsApp() === false) {
    jQuery(document).ready(function() {
        //时间输入框初始化
        $('.timepicker-no-seconds').timepicker({
            autoclose: true,
            minuteStep: 1,
            showMeridian: false
        });
        //查询预约时间段
        $("#time_inquiry").click(function(){
            TimeTable.init();
        });
        //获取预约时间列表
        TimeTable.init();
        //预约时间删除初始化
        TimeDelete.init();
        //保存预约时间
        TimeEdit.init();
    });
}

var TimeTable = function () {
    var initTable = function (data) {
        var $table = $('#time_table');
        $table.dataTable({
            "language": TableLanguage,
            "bStateSave": false,
            "lengthMenu": TableLengthMenu,
            "destroy": true,
            "pageLength": 10,
            "pagingType": "bootstrap_extended",
            "serverSide": true,
            "processing": true,
            "searching": false,
            "ordering": false,
            "paginate": false,
            "ajax":function (data, callback, settings) {
                var formData = $(".inquiry-form").getFormData();
                var da = {
                    currentpage: (data.start / data.length) + 1,
                    pagesize: data.length == -1 ? "": data.length,
                    startindex: data.start,
                    draw: data.draw
                };
                timeDataGet(da, callback);
            },
            columns: [//返回的json数据在这里填充，注意一定要与上面的<th>数量对应，否则排版出现扭曲
                { "data": null},
                { "data": "id", visible: false },
                { "data": "kssj" },
                { "data": "jssj" }
            ],
            columnDefs: [
                {
                    "targets": [0],
                    "render": function (data, type, row, meta) {
                        return '<input type="checkbox" class="checkboxes" value="1" />';
                    }
                },
                {
                    "targets": [2],
                    "render": function (data, type, row, meta) {
                        return timeFormat(data);
                    }
                },{
                    "targets": [3],
                    "render": function (data, type, row, meta) {
                        return timeFormat(data);
                    }
                }
            ]
        });

        /*$table.find('.group-checkable').change(function () {
            var set = jQuery(this).attr("data-set");
            var checked = jQuery(this).is(":checked");
            jQuery(set).each(function () {
                if (checked) {
                    $(this).prop("checked", true);
                    $(this).parents('tr').addClass("active");
                } else {
                    $(this).prop("checked", false);
                    $(this).parents('tr').removeClass("active");
                }
            });
        });*/

        $table.on('change', 'tbody tr .checkboxes', function () {
            //单选
            var that = this;
            var set = jQuery('.group-checkable').attr("data-set");
            jQuery(set).each(function () {
                if(this == that )return;
                $(this).prop("checked", false);
                $(this).parents('tr').removeClass("active");
            });
        });
    };
    return {
        init: function (data) {
            initTable(data);
        }
    };

}();

var TimeEdit = function() {
    var handleRegister = function() {
        var validator = $('.inquiry-form').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",
            rules: {
                a_kssj: {
                    required: true
                },
                a_jssj: {
                    required: true,
                    maxTo: "#a_kssj"
                },
                p_kssj: {
                    required: true
                },
                p_jssj: {
                    required: true,
                    maxTo: "#p_kssj"
                },
                jgsj: {
                    required: true,
                    digits: true
                },
                kyyrs: {
                    required: true,
                    digits: true
                }
            },

            messages: {
                a_kssj: {
                    required: "上午工作开始必须输入"
                },

                a_jssj: {
                    required: "上午结束开始必须输入",
                    maxTo: "结束时间必须大于开始时间"
                },
                p_kssj: {
                    required: "下午工作开始必须输入"
                },

                p_jssj: {
                    required: "下午结束开始必须输入",
                    maxTo: "#结束时间必须大于开始时间"
                },
                jgsj: {
                    required: "时间间隔必须输入"
                },
                kyyrs: {
                    required: "可预约人数必须输入"
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
        // 结束时间必须大于开始时间
        jQuery.validator.addMethod("maxTo", function(value, element, para) {
            var date = getNowFormatDate();
            var endTime = date + " " + $(para).val() + ":00";
            var startTime = date + " " + value + ":00";
            return this.optional(element) || !(new Date(endTime) > new Date(startTime));
        }, "结束工作时间必须大于开始工作时间");

        $('#time_save').click(function(e) {
            e.preventDefault();
            if ($('.inquiry-form').validate().form()) {
                var data = $('.inquiry-form').getFormData();
                timeEdit(data);
            }
        });
    };
    return {
        init: function() {
            handleRegister();
        }
    };
}();

var TimeDelete = function() {
    var handle = function(){
        $('#op_del').click(function() {
            var len = $(".checkboxes:checked").length;
            if(len < 1){
                alertDialog("至少选中一项！");
            }else{
                confirmDialog("数据删除后将不可恢复，您确定要删除吗？", TimeDelete.deleteTime)
            }
        });
    };
    return{
        deleteTime: function(){
            var timelist = {id:[]};
            $(".checkboxes:checked").parents("td").each(function () {
                var row = $(this).parents('tr')[0];     //通过获取该td所在的tr，即td的父级元素，取出第一列序号元素
                var rowData = $("#time_table").dataTable().fnGetData(row);
                timelist.id = rowData.id;
                timelist.kssj = rowData.kssj;
                timelist.jssj = rowData.jssj;
            });
            timeDelete(timelist);
        },
        init: function(){
            handle();
        }
    }
}();

function getTimeDataEnd(flg, result, callback){
    App.unblockUI('#lay-out');
    if(flg){
        if (result && result.retcode == SUCCESS) {
            //设定开始，结束工作时间，时间间隔的值
            $("input[name='a_kssj']").timepicker("setTime",timeFormat(result.gzsj.a_kssj));
            $("input[name='a_jssj']").timepicker("setTime",timeFormat(result.gzsj.a_jssj));
            $("input[name='p_kssj']").timepicker("setTime",timeFormat(result.gzsj.p_kssj));
            $("input[name='p_jssj']").timepicker("setTime",timeFormat(result.gzsj.p_jssj));
            $("input[name='jgsj']").val(result.gzsj.jgsj);
            $("input[name='kyyrs']").val(result.gzsj.kyyrs);
            tableDataSet(result.draw, result.cnt, result.cnt, result.yysj, callback);
        }else{
            tableDataSet(0, 0, 0, [], callback);
            alertDialog("预约时间获取失败！");
        }
    }else{
        tableDataSet(0, 0, 0, [], callback);
        alertDialog("预约时间获取失败！");
    }
}

function timeInfoEditEnd(flg, result, type){
    var res = "失败";
    var text = "";
    var alert = "";
    switch (type){
        case TIMEEDIT:
            text = "编辑";
            break;
        case TIMEDELETE:
            text = "删除";
            break;
    }
    if(flg){
        if(result && result.retcode != SUCCESS){
            alert = result.retmsg;
        }
        if (result && result.retcode == SUCCESS) {
            res = "成功";
            TimeTable.init();
        }
    }
    if(alert == "") alert = text + "预约时间" + res + "！";
    App.unblockUI('#lay-out');
    alertDialog(alert);
}

function timeFormat(time){
    if(time.length < 4) return time;
    return time.substring(0, 2) + ":" + time.substring(2, 4);
}