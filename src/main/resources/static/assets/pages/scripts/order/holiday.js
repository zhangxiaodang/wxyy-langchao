/**
 * Created by Jianggy on 2019/6/22.
 */
var holidayList = [];
if (App.isAngularJsApp() === false) {
    jQuery(document).ready(function() {
        //日期框初始化
        ComponentsDatePickers.init();
        //获取节假日列表
        HolidayTable.init();
        //节假日编辑初始化
        HolidayEdit.init();
        //节假日删除初始化
        HolidayDelete.init();
        //查询按钮
        $("#holiday_inquiry").click(function(){
            HolidayTable.init();
        });
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
        }
    };

    return {
        init: function () {
            handleDatePickers();
        }
    };
}();

var HolidayTable = function () {
    var initTable = function (data) {
        var $table = $('#holiday_table');
        // begin first table
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
            "ajax":function (data, callback, settings) {
                var formData = $(".inquiry-form").getFormData();
                var da = {
                    rq: formData.rq.replace(/-/g, ""),
                    currentpage: (data.start / data.length) + 1,
                    pagesize: data.length == -1 ? "": data.length,
                    startindex: data.start,
                    draw: data.draw
                };
                holidayDataGet(da, callback);
            },
            columns: [//返回的json数据在这里填充，注意一定要与上面的<th>数量对应，否则排版出现扭曲
                { "data": null},
                { "data": null},
                { "data": "id", visible: false },
                { "data": "rq" },
                { "data": "bz" }
            ],
            columnDefs: [
                {
                    "targets": [0],
                    "render": function (data, type, row, meta) {
                        return '<input type="checkbox" class="checkboxes" value="1" />';
                    }
                },{
                    //序号
                    "targets": [1],
                    "render": function (data, type, row, meta) {
                        return meta.row + 1;
                    }
                },{
                    //日期
                    "targets": [3],
                    "render": function (data, type, row, meta) {
                        return data.substr(0, 4) + "-" + data.substr(4, 2) + "-" + data.substr(6, 2);
                    }
                }
            ],
            fnRowCallback: function( nRow, aData, iDisplayIndex, iDisplayIndexFull ) {
                $('th:eq(1)', nRow).attr('style', 'width:36px; text-align:center');
                $('td:eq(1)', nRow).attr('style', 'width:36px; text-align:center');
            }
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

var HolidayEdit = function() {
    var handleRegister = function() {
        var validator = $('.register-form').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",
            rules: {
                rq: {
                    required: true
                }
            },

            messages: {
                rq: {
                    required: "日期必须输入"
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

        $('#register-btn').click(function() {
            if ($('.register-form').validate().form()) {
                var holiday = $('.register-form').getFormData();
                holiday.rq = holiday.rq.replace(/-/g, "");
                if($("input[name=edittype]").val() == HOLIDAYADD){
                    holidayAdd(holiday);
                }else{
                    holidayEdit(holiday);
                }
            }
        });
        //新增节假日
        $('#op_add').click(function() {
            //清除校验错误信息
            validator.resetForm();
            $(".register-form").find(".has-error").removeClass("has-error");
            $(".modal-title").text("新增节假日");
            $(":input",".register-form").not(":button,:reset,:submit,:radio").val("")
                .removeAttr("checked")
                .removeAttr("selected");
            $("input[name=edittype]").val(HOLIDAYADD);
            $('#edit_holiday').modal('show');
        });

        //编辑节假日
        $("#holiday_table").on('click', '#op_edit', function (e) {
            e.preventDefault();
            //清除校验错误信息
            validator.resetForm();
            $(".register-form").find(".has-error").removeClass("has-error");
            $(".modal-title").text("编辑节假日");
            var exclude = ["rq"];
            var holiday = new Object();
            var row = $(this).parents('tr')[0];     //通过获取该td所在的tr，即td的父级元素，取出第一列序号元素
            var id = $("#holiday_table").dataTable().fnGetData(row).id;
            for(var i=0; i < holidayList.length; i++){
                if(id == holidayList[i].id){
                    holiday = holidayList[i];
                }
            }
            var options = { jsonValue: holiday, exclude:exclude,isDebug: false};
            $(".register-form").initForm(options);
            //日期赋值
            if(holiday.rq != undefined && holiday.rq != ""){
                $(".register-form input[name=rq]").datepicker("setDate",dateFormat(holiday.rq, "-"));
            }
            $("input[name=edittype]").val(HOLIDAYEDIT);
            $('#edit_holiday').modal('show');
        });
    };

    return {
        //main function to initiate the module
        init: function() {
            handleRegister();
        }
    };
}();

var HolidayDelete = function() {
    var handle = function(){
        $('#op_del').click(function() {
            var len = $(".checkboxes:checked").length;
            if(len < 1){
                alertDialog("至少选中一项！");
            }else{
                confirmDialog("数据删除后将不可恢复，您确定要删除吗？", HolidayDelete.deleteHoliday)
            }
        });
    }
    return{
        deleteHoliday: function(){
            var holidaylist = new Object();
            $(".checkboxes:checked").parents("td").each(function () {
                var row = $(this).parents('tr')[0];     //通过获取该td所在的tr，即td的父级元素，取出第一列序号元素
                holidaylist.id = $("#holiday_table").dataTable().fnGetData(row).id;
            });
            holidayDelete(holidaylist);
        },
        init: function(){
            handle();
        }
    }
}();

//节假日获取结束
function getHolidayDataEnd(flg, result, callback){
    App.unblockUI('#lay-out');
    if(flg){
        if (result && result.retcode == SUCCESS) {
            holidayList = result.holidays;
            tableDataSet(result.draw, result.cnt, result.cnt, result.holidays, callback);
        }else{
            tableDataSet(0, 0, 0, [], callback);
            alertDialog("节假日信息获取失败！");
        }
    }else{
        tableDataSet(0, 0, 0, [], callback);
        alertDialog("节假日信息获取失败！");
    }
}

function holidayInfoEditEnd(flg, result, type){
    var res = "失败";
    var text = "";
    var alert = "";
    switch (type){
        case HOLIDAYADD:
            text = "新增";
            break;
        case HOLIDAYEDIT:
            text = "编辑";
            break;
        case HOLIDAYDELETE:
            text = "删除";
            break;
    }
    if(flg){
        if(result && result.retcode != SUCCESS){
            alert = result.retmsg;
        }
        if (result && result.retcode == SUCCESS) {
            res = "成功";
            HolidayTable.init();
            $('#edit_holiday').modal('hide');
        }
    }
    if(alert == "") alert = text + "节假日" + res + "！";
    App.unblockUI('#lay-out');
    alertDialog(alert);
}