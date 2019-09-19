/**
 * Created by Administrator on 2019/2/28.
 */
function loginCheck(data){
    $.ajax({
        type: "post",
        contentType: "application/json",
        async: true,           //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
        url: "/web/portal/login",    //请求发送到TestServlet处
        data: sendMessageEdit(LOGIN, data),
        dataType: "json",        //返回数据形式为json
        success: function (result) {
            console.info("loginCheck:" + JSON.stringify(result));
            loginCheckEnd(true, result);

        },
        error: function (errorMsg) {
            console.info("loginCheck-error:" + JSON.stringify(errorMsg));
            loginCheckEnd(false, "");
        }
    });
}

function userDataGet(data, callback){
    App.blockUI({target: '#lay-out',boxed: true});
    if(data == null){
        data = {userid: "", username: "", currentpage: "", pagesize: "", startindex: "0", draw: 1}
    }
    $.ajax({
        type: "post",
        contentType: "application/json",
        async: true,           //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
        url: "/web/user/query",    //请求发送到TestServlet处
        data: sendMessageEdit(DEFAULT, data),
        dataType: "json",        //返回数据形式为json
        success: function (result) {
            console.info("userDataGet:" + JSON.stringify(result));
            getUserDataEnd(true, result, callback);
        },
        error: function (errorMsg) {
            console.info("userDataGet-error:" + JSON.stringify(errorMsg));
            getUserDataEnd(false, "", callback);
        }
    });
}

function userAdd(data){
    App.blockUI({target:'#lay-out',boxed: true});
    $.ajax({
        type: "post",
        contentType: "application/json",
        async: true,           //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
        url: "/web/user/add",    //请求发送到TestServlet处
        data: sendMessageEdit(DEFAULT, data),
        dataType: "json",        //返回数据形式为json
        success: function (result) {
            console.info("userAdd:" + JSON.stringify(result));
            userInfoEditEnd(true, result, USERADD);
        },
        error: function (errorMsg) {
            console.info("userAdd-error:" + JSON.stringify(errorMsg));
            userInfoEditEnd(false, "", USERADD);
        }
    });
}

function userDelete(data){
    App.blockUI({target:'#lay-out',boxed: true});
    $.ajax({
        type: "post",
        contentType: "application/json",
        async: true,           //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
        url: "/web/user/del",    //请求发送到TestServlet处
        data: sendMessageEdit(DEFAULT, data),
        dataType: "json",        //返回数据形式为json
        success: function (result) {
            console.info("userDelete:" + JSON.stringify(result));
            userInfoEditEnd(true, result, USERDELETE);
        },
        error: function (errorMsg) {
            console.info("userDelete-error:" + JSON.stringify(errorMsg));
            userInfoEditEnd(false, "", USERDELETE);
        }
    });
}

function userEdit(data){
    App.blockUI({target:'#lay-out',boxed: true});
    $.ajax({
        type: "post",
        contentType: "application/json",
        async: true,           //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
        url: "/web/user/upd",    //请求发送到TestServlet处
        data: sendMessageEdit(DEFAULT, data),
        dataType: "json",        //返回数据形式为json
        success: function (result) {
            console.info("userEdit:" + JSON.stringify(result));
            userInfoEditEnd(true, result, USEREDIT);
        },
        error: function (errorMsg) {
            console.info("userEdit-error:" + JSON.stringify(errorMsg));
            userInfoEditEnd(false, "", USEREDIT);
        }
    });
}

function passwordReset(data){
    App.blockUI({target:'#lay-out',boxed: true});
    $.ajax({
        type: "post",
        contentType: "application/json",
        async: true,           //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
        url: hostUrl + "passwordreset",    //请求发送到TestServlet处
        data: sendMessageEdit(DEFAULT, data),
        dataType: "json",        //返回数据形式为json
        success: function (result) {
            console.info("passwordReset:" + JSON.stringify(result));
            passwordResetEnd(true, result);
        },
        error: function (errorMsg) {
            console.info("passwordReset-error:" + JSON.stringify(errorMsg));
            passwordResetEnd(false, "");
        }
    });
}

function passwordModify(data){
    App.blockUI({target:'#lay-out',boxed: true});
    $.ajax({
        type: "post",
        contentType: "application/json",
        async: true,           //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
        url: "/web/portal/chgpasswd",    //请求发送到TestServlet处
        data: sendMessageEdit(DEFAULT, data),
        dataType: "json",        //返回数据形式为json
        success: function (result) {
            console.info("passwordModify:" + JSON.stringify(result));
            passwordModifyEnd(true, result);
        },
        error: function (errorMsg) {
            console.info("passwordModify-error:" + JSON.stringify(errorMsg));
            passwordModifyEnd(false, "");
        }
    });
}

function busiDataGet(data, callback){
    App.blockUI({target:'#lay-out',boxed: true});
    if(data == null){
        data = {id: "", uname: "", currentpage: "", pagesize: "", startindex: "0", draw: 1}
    }
    $.ajax({
        type: "post",
        contentType: "application/json",
        async: true,           //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
        url: "/web/busi/query",    //请求发送到TestServlet处
        data: sendMessageEdit(DEFAULT, data),
        dataType: "json",        //返回数据形式为json
        success: function (result) {
            console.info("busiDataGet:" + JSON.stringify(result));
            getBusiDataEnd(true, result, callback);
        },
        error: function (errorMsg) {
            console.info("busiDataGet-error:" + JSON.stringify(errorMsg));
            getBusiDataEnd(false, "", callback);
        }
    });
}

function busiAdd(data){
    App.blockUI({target:'#lay-out',boxed: true});
    $.ajax({
        type: "post",
        contentType: "application/json",
        async: true,           //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
        url: "/web/busi/add",    //请求发送到TestServlet处
        data: sendMessageEdit(DEFAULT, data),
        dataType: "json",        //返回数据形式为json
        success: function (result) {
            console.info("busiAdd:" + JSON.stringify(result));
            busiInfoEditEnd(true, result, BUSIADD);
        },
        error: function (errorMsg) {
            console.info("busiAdd-error:" + JSON.stringify(errorMsg));
            busiInfoEditEnd(false, "", BUSIADD);
        }
    });
}

function busiDelete(data){
    App.blockUI({target:'#lay-out',boxed: true});
    $.ajax({
        type: "post",
        contentType: "application/json",
        async: true,           //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
        url: "/web/busi/del",    //请求发送到TestServlet处
        data: sendMessageEdit(DEFAULT, data),
        dataType: "json",        //返回数据形式为json
        success: function (result) {
            console.info("busiDelete:" + JSON.stringify(result));
            busiInfoEditEnd(true, result, BUSIDELETE);
        },
        error: function (errorMsg) {
            console.info("busiDelete-error:" + JSON.stringify(errorMsg));
            busiInfoEditEnd(false, "", BUSIDELETE);
        }
    });
}

function busiEdit(data){
    App.blockUI({target:'#lay-out',boxed: true});
    $.ajax({
        type: "post",
        contentType: "application/json",
        async: true,           //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
        url: "/web/busi/upd",    //请求发送到TestServlet处
        data: sendMessageEdit(DEFAULT, data),
        dataType: "json",        //返回数据形式为json
        success: function (result) {
            console.info("busiEdit:" + JSON.stringify(result));
            busiInfoEditEnd(true, result, BUSIEDIT);
        },
        error: function (errorMsg) {
            console.info("busiEdit-error:" + JSON.stringify(errorMsg));
            busiInfoEditEnd(false, "", BUSIEDIT);
        }
    });
}

function timeDataGet(data, callback){
    App.blockUI({target:'#lay-out',boxed: true});
    if(data == null){
        data = {currentpage: "", pagesize: "", startindex: "0", draw: 1}
    }
    $.ajax({
        type: "post",
        contentType: "application/json",
        async: true,           //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
        url: "/web/time/query",    //请求发送到TestServlet处
        data: sendMessageEdit(DEFAULT, data),
        dataType: "json",        //返回数据形式为json
        success: function (result) {
            console.info("timeDataGet:" + JSON.stringify(result));
            getTimeDataEnd(true, result, callback);
        },
        error: function (errorMsg) {
            console.info("timeDataGet-error:" + JSON.stringify(errorMsg));
            getTimeDataEnd(false, "", callback);
        }
    });
}

function timeEdit(data){
    App.blockUI({target:'#lay-out',boxed: true});
    $.ajax({
        type: "post",
        contentType: "application/json",
        async: true,           //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
        url: "/web/time/upd",    //请求发送到TestServlet处
        data: sendMessageEdit(DEFAULT, data),
        dataType: "json",        //返回数据形式为json
        success: function (result) {
            console.info("timeAdd:" + JSON.stringify(result));
            timeInfoEditEnd(true, result, TIMEEDIT);
        },
        error: function (errorMsg) {
            console.info("timeAdd-error:" + JSON.stringify(errorMsg));
            timeInfoEditEnd(false, "", TIMEEDIT);
        }
    });
}

function timeDelete(data){
    App.blockUI({target:'#lay-out',boxed: true});
    $.ajax({
        type: "post",
        contentType: "application/json",
        async: true,           //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
        url: "/web/time/del",    //请求发送到TestServlet处
        data: sendMessageEdit(DEFAULT, data),
        dataType: "json",        //返回数据形式为json
        success: function (result) {
            console.info("timeDelete:" + JSON.stringify(result));
            timeInfoEditEnd(true, result, TIMEDELETE);
        },
        error: function (errorMsg) {
            console.info("timeDelete-error:" + JSON.stringify(errorMsg));
            timeInfoEditEnd(false, "", TIMEDELETE);
        }
    });
}

function holidayDataGet(data, callback){
    App.blockUI({target:'#lay-out',boxed: true});
    if(data == null){
        data = {rq: "", currentpage: "", pagesize: "", startindex: "0", draw: 1}
    }
    $.ajax({
        type: "post",
        contentType: "application/json",
        async: true,           //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
        url: "/web/holiday/query",    //请求发送到TestServlet处
        data: sendMessageEdit(DEFAULT, data),
        dataType: "json",        //返回数据形式为json
        success: function (result) {
            console.info("holidayDataGet:" + JSON.stringify(result));
            getHolidayDataEnd(true, result, callback);
        },
        error: function (errorMsg) {
            console.info("holidayDataGet-error:" + JSON.stringify(errorMsg));
            getHolidayDataEnd(false, "", callback);
        }
    });
}

function holidayAdd(data){
    App.blockUI({target:'#lay-out',boxed: true});
    $.ajax({
        type: "post",
        contentType: "application/json",
        async: true,           //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
        url: "/web/holiday/add",    //请求发送到TestServlet处
        data: sendMessageEdit(DEFAULT, data),
        dataType: "json",        //返回数据形式为json
        success: function (result) {
            console.info("holidayAdd:" + JSON.stringify(result));
            holidayInfoEditEnd(true, result, HOLIDAYADD);
        },
        error: function (errorMsg) {
            console.info("holidayAdd-error:" + JSON.stringify(errorMsg));
            holidayInfoEditEnd(false, "", HOLIDAYADD);
        }
    });
}

function holidayDelete(data){
    App.blockUI({target:'#lay-out',boxed: true});
    $.ajax({
        type: "post",
        contentType: "application/json",
        async: true,           //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
        url: "/web/holiday/del",    //请求发送到TestServlet处
        data: sendMessageEdit(DEFAULT, data),
        dataType: "json",        //返回数据形式为json
        success: function (result) {
            console.info("holidayDelete:" + JSON.stringify(result));
            holidayInfoEditEnd(true, result, HOLIDAYDELETE);
        },
        error: function (errorMsg) {
            console.info("holidayDelete-error:" + JSON.stringify(errorMsg));
            holidayInfoEditEnd(false, "", HOLIDAYDELETE);
        }
    });
}

function holidayEdit(data){
    App.blockUI({target:'#lay-out',boxed: true});
    $.ajax({
        type: "post",
        contentType: "application/json",
        async: true,           //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
        url: "/web/holiday/upd",    //请求发送到TestServlet处
        data: sendMessageEdit(DEFAULT, data),
        dataType: "json",        //返回数据形式为json
        success: function (result) {
            console.info("holidayEdit:" + JSON.stringify(result));
            holidayInfoEditEnd(true, result, HOLIDAYEDIT);
        },
        error: function (errorMsg) {
            console.info("holidayEdit-error:" + JSON.stringify(errorMsg));
            holidayInfoEditEnd(false, "", HOLIDAYEDIT);
        }
    });
}

function orderDataGet(data, callback){
    App.blockUI({target:'#lay-out',boxed: true});
    $.ajax({
        type: "post",
        contentType: "application/json",
        async: true,           //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
        url: "/web/order/query",    //请求发送到TestServlet处
        data: sendMessageEdit(DEFAULT, data),
        dataType: "json",        //返回数据形式为json
        success: function (result) {
            console.info("orderDataGet:" + JSON.stringify(result));
            getOrderDataEnd(true, result, callback);
        },
        error: function (errorMsg) {
            console.info("orderDataGet-error:" + JSON.stringify(errorMsg));
            getOrderDataEnd(false, "", callback);
        }
    });
}

function cancelNoticeGet(data){
    App.blockUI({target:'#lay-out',boxed: true});
    $.ajax({
        type: "post",
        contentType: "application/json",
        async: true,           //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
        url: "/web/notice/query",    //请求发送到TestServlet处
        data: sendMessageEdit(DEFAULT, data),
        dataType: "json",        //返回数据形式为json
        success: function (result) {
            console.info("cancelNoticeGet:" + JSON.stringify(result));
            getNoticeDataEnd(true, result, null);
        },
        error: function (errorMsg) {
            console.info("cancelNoticeGet-error:" + JSON.stringify(errorMsg));
            getNoticeDataEnd(false, "", null);
        }
    });
}

function cancelNoticeEdit(data){
    App.blockUI({target:'#lay-out',boxed: true});
    $.ajax({
        type: "post",
        contentType: "application/json",
        async: true,           //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
        url: "/web/notice/edit",    //请求发送到TestServlet处
        data: sendMessageEdit(DEFAULT, data),
        dataType: "json",        //返回数据形式为json
        success: function (result) {
            console.info("cancelNoticeEdit:" + JSON.stringify(result));
            noticeInfoEditEnd(true, result, null);
        },
        error: function (errorMsg) {
            console.info("cancelNoticeEdit-error:" + JSON.stringify(errorMsg));
            noticeInfoEditEnd(false, "", null);
        }
    });
}