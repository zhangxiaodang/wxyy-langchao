/**
 * Created by Administrator on 2019/2/19.
 */
var hostIP = "60.216.52.194";
var hostUrl = "http://172.18.1.156:8888/wldp/web/front/";
var loginUrl = "http://172.18.1.156:8888/wldp/web/";
//var hostUrl = "/web/test/front/";
var SUCCESS = "0000";

var DEFAULT = 0;
var USERADD = 1;
var USEREDIT = 2;
var USERDELETE = 3;
var LOGIN = 4;
var BUSIADD = 5;
var BUSIEDIT = 6;
var BUSIDELETE = 7;
var TIMEEDIT = 8;
var TIMEDELETE = 9;
var HOLIDAYADD = 10;
var HOLIDAYEDIT = 11;
var HOLIDAYDELETE = 12;

var TableLanguage = {
        "aria": {
            "sortAscending": ": 以升序排列此列",
                "sortDescending": ": 以降序排列此列"
        },
        "emptyTable": "暂无数据",
        "info": "当前显示第 _START_ 到 _END_ 项，共 _TOTAL_项",
        "infoEmpty": "当前显示第 0 至 0 项，共 0 项",
        "infoFiltered": "(由 _MAX_ 项结果过滤)",
        "lengthMenu": "每页 _MENU_ 条",
        "search": "搜索:",
        "zeroRecords": "没有匹配的数据",
        "paginate": {
            "previous":"上一页",
            "next": "下一页",
            "last": "首页",
            "first": "末页",
            "page": "第",
            "pageOf": "共"
        },
        "processing": "正在加载中......"
    };
var TableLengthMenu = [
        [10, 20, 30, -1],
        [10, 20, 30, "所有"] // change per page values here
    ];

var loginSuccess = {
    "userid": "1",
    "token": "2222222222",
    "username": "张三",
    "userimage":""
};

//测试数据
var userMenuList = {
    menulist:[
        {"menuid":"usermanager","menutype":0,sort:"0", power:"1", "menuname":"用户管理","url":"", menuicon:"icon-users",
            "menulist":[
                {"menuid":"user","menutype":1,sort:"0", power:"1", "menuname":"用户管理","url":"/web/user/index", menuicon:"icon-user"},
                {"menuid":"password","menutype":1,sort:"1", power:"1", "menuname":"修改密码","url":"/web/portal/password", menuicon:"icon-lock"}
            ]
        },
        {"menuid":"ordermanager","menutype":0,sort:"0", power:"1", "menuname":"预约管理","url":"", menuicon:"icon-diamond",
            "menulist":[
                {"menuid":"busi","menutype":1,sort:"0", power:"1", "menuname":"预约业务管理","url":"/web/busi/index", menuicon:"icon-home"},
                {"menuid":"time","menutype":1,sort:"0", power:"1", "menuname":"预约时间管理","url":"/web/time/index", menuicon:"icon-clock"},
                {"menuid":"holiday","menutype":1,sort:"1", power:"1", "menuname":"节假日管理","url":"/web/holiday/index", menuicon:"icon-calendar"},
                {"menuid":"cancel","menutype":1,sort:"1", power:"1", "menuname":"预约取消通知","url":"/web/notice/cancel", menuicon:"icon-bubble"}
            ]
        },
        {"menuid":"orderanalyse","menutype":0,sort:"0", power:"1", "menuname":"预约查询","url":"", menuicon:"icon-badge",
            "menulist":[
                {"menuid":"query","menutype":1,sort:"0", power:"1", "menuname":"预约查询","url":"/web/order/index", menuicon:"icon-home"},
                {"menuid":"analyse","menutype":1,sort:"1", power:"0", "menuname":"预约分析","url":"/web/order/analyse", menuicon:"icon-user-following"}
            ]
        }
    ]
};