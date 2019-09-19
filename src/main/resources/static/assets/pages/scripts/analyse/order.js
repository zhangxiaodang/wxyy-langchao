/**
 * Created by Jianggy on 2019/6/22.
 */
var orderList = [];
if (App.isAngularJsApp() === false) {
    jQuery(document).ready(function() {
        //日期框初始化
        ComponentsDatePickers.init();
        //获取预约列表
        OrderTable.init();
        //查询按钮
        $("#order_inquiry").click(function(){
            OrderTable.init();
        });
    });
}

var ComponentsDatePickers = function () {
    var handleDatePickers = function () {
        if (jQuery().datepicker) {
            $('.date-picker').datepicker({
                rtl: App.isRTL(),
                orientation: "auto",
                autoclose: true,
                language:"zh-CN",
                todayBtn:true,
                format:"yyyy-mm-dd",
                todayHighlight: true,
                clearBtn: true
            });
        }
        //时间输入框初始化
        $('.timepicker-no-seconds').timepicker({
            autoclose: true,
            minuteStep: 1,
            showMeridian: false,
            defaultTime: ""
        });
    };

    return {
        init: function () {
            handleDatePickers();
        }
    };
}();

var OrderTable = function () {
    var initTable = function (data) {
        var $table = $('#order_table');
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
                    phone: formData.phone,
                    date: formData.date,
                    starttime: formData.kssj.replace(/-/g, ""),
                    endtime: formData.jssj.replace(/-/g, ""),
                    currentpage: (data.start / data.length) + 1,
                    pagesize: data.length == -1 ? "": data.length,
                    startindex: data.start,
                    draw: data.draw
                };
                orderDataGet(da, callback);
            },
            columns: [//返回的json数据在这里填充，注意一定要与上面的<th>数量对应，否则排版出现扭曲
                { "data": "id", visible: false },
                { "data": null },
                { "data": "uname" },
                { "data": "phone" },
                { "data": "uid" },
                //{ "data": "type" },
                { "data": "businame" },
                { "data": "date" },
                { "data": "starttime" },
                { "data": "endtime" },
                { "data": "status" }
            ],
            columnDefs: [
                {
                    "targets": [1],
                    "render": function (data, type, row, meta) {
                        return meta.row + 1;
                    }
                },{
                    //身份证号
                    "targets": [4],
                    "render": function (data, type, row, meta) {
                        return data.substr(0, 6) + fillString("", "*", data.length-10) + data.substr(data.length - 4, 4);
                    }
                },{
                    //状态
                    "targets": [9],
                    "render": function (data, type, row, meta) {
                        var zt = "预约中";
                        switch (data) {
                            case "1":
                                zt = "已取消";
                                break;
                            case "2":
                                zt = "已取号";
                                break;
                            case "3":
                                zt = "已过期";
                                break;
                        }
                        return zt;
                    }
                }
            ],
            fnRowCallback: function( nRow, aData, iDisplayIndex, iDisplayIndexFull ) {
                $('td:eq(2)', nRow).attr('style', 'text-align: center;');
                $('td:eq(3)', nRow).attr('style', 'text-align: center;');
                $('td:eq(5)', nRow).attr('style', 'text-align: center;');
                $('td:eq(6)', nRow).attr('style', 'text-align: center;');
                $('td:eq(7)', nRow).attr('style', 'text-align: center;');
                $('td:eq(8)', nRow).attr('style', 'text-align: center;');
            }
        });
    };
    return {
        init: function (data) {
            initTable(data);
        }
    };

}();

//预约获取结束
function getOrderDataEnd(flg, result, callback){
    App.unblockUI('#lay-out');
    if(flg){
        if (result && result.retcode == SUCCESS) {
            orderList = result.orderlist;
            tableDataSet(result.draw, result.cnt, result.cnt, result.orderinfo, callback);
        }else{
            tableDataSet(0, 0, 0, [], callback);
            alertDialog("预约信息获取失败！");
        }
    }else{
        tableDataSet(0, 0, 0, [], callback);
        alertDialog("预约信息获取失败！");
    }
}