//页面加载
var interval = 0;
var timeout = 0;
$(document).ready(function(){
    //根据业务类型，显示不同的输入框
    var busiType = "01"
    if(busiType == "01"){
        $("#pywlx").show();
        $("#pcphm").hide();
        $(".top img").attr("src", "../img/cgtop.jpg");
        $("#weituo").show();
    }else{
        $("#pywlx").hide();
        $("#pcphm").show();
        $(".top img").attr("src", "../img/wftop.jpg");
        $("#weituo").hide();
    }
    //根据localstorage结果，初始化各个input框
    $(":input","#infoform").not(":button,:reset,:submit,:radio").val("")
        .removeAttr("checked")
        .removeAttr("selected");
    var orderInfo = localStorage.getItem("orderinfo");
    if(orderInfo != "" && orderInfo != null && orderInfo != undefined){
        orderInfo = JSON.parse(orderInfo);
    }else{
        orderInfo = new Object();
    }
    if(business != "" && business != null){
        orderInfo.busiid = business.busiid;
        orderInfo.businame = business.businame;
        localStorage.setItem("busitips", business.busitips);
        // mui.alert("<div style='text-align: left; max-height: 5rem; overflow: auto'>" + business.busitips + "</div>", "提示", function (e) {
        // });
    }
    if(time != "" && time != null){
        orderInfo.date = time.date;
        orderInfo.timerang = timeFormat(time.starttime) + "-" + timeFormat(time.endtime);
    }

    if(yyxm != "" && yyxm != null) {
        orderInfo.username = yyxm;
        localStorage.setItem("username", yyxm);
    }
    if(yysjh != "" && yysjh != null) {
        orderInfo.phone = yysjh;
        localStorage.setItem("phone", yysjh);
    }
    if(yyid != "" && yyid != null) {
        orderInfo.userid = yyid;
        localStorage.setItem("userid", yyid);
    }

    var options = { jsonValue: orderInfo, exclude:[],isDebug: false};
    $("#infoform").initForm(options);
});

//业务类型输入框按下
$("#ywlx").on("click", function(){
    //获取formdata,保存到localstorage
    var orderInfo = $("#infoform").getFormData();
    localStorage.setItem("orderinfo", JSON.stringify(orderInfo));
    var form = $('<form />', {action : "/wx/busiselect", method:"post", style:"display:none;"}).appendTo('body');
    form.submit();
});

//预约时间输入框按下
$("#yysj").on("click", function(){
    //获取formdata,保存到localstorage
    var orderInfo = $("#infoform").getFormData();
    localStorage.setItem("orderinfo", JSON.stringify(orderInfo));
    var busiType = localStorage.getItem("busitype");
    var form = $('<form />', {action : "/wx/timeselect", method:"post", style:"display:none;"}).appendTo('body');
    form.append('<input type="hidden" name="busitype" value="' + busiType + '"/>' );
    form.submit();
});

//获取验证码按钮按下
$("#getverify").on("click", function(e){
    e.preventDefault();
    var info = $("#infoform").getFormData();
    if(!checkPhone(info.phone)){
        return;
    }
    var data = {
        phone: info.phone,
        openid: localStorage.getItem("openid")
    };
    //验证码120S才能重新发送
    var that = this;
    mui(this).button("loading");
    var i = 120;
    $(that).text( i + "S后重新发送");
    var interval = setInterval(function(){
        i--;
        $(that).text( i + "S后重新发送");
    }, 1000);
    var timeout = setTimeout(function() {
        clearInterval(interval);
        mui(that).button('reset');
    }.bind(this), 120000);
    $.ajax({
        type: "post",
        contentType: "application/json",
        async: true,
        url: "/wx/getverify",
        data: JSON.stringify(data),
        dataType:"json",      //返回数据形式为json
        success:function(result){
            console.info("getverify:"+JSON.stringify(result));
            if(result.retcode == "0000"){
                //$(that).text("验证码已发送");
            }else{
                clearInterval(interval);
                clearTimeout(timeout);
                mui(that).button('reset');
                mui.alert("", result.retmsg, function(e){});
            }
        },
        error:function(errorMsg){
            clearInterval(interval);
            clearTimeout(timeout);
            mui(that).button('reset');
            console.info("getverify-error:"+ JSON.stringify(errorMsg));
            mui.alert("请重试", "获取验证码失败", function(e){});
        }
    })
});

//提交按钮按下
$("#tj").on("click", function(e){
    e.preventDefault();
    //检查是否输入正确，输入完全
    var info = $("#infoform").getFormData();
    var busiType = localStorage.getItem("busitype");
    //校验手机号码
    if(!checkPhone(info.phone)){
        return;
    }

    //校验验证码
    //if(!checkVerify(info.verifycode)){
    //    return;
    //}
    //校验身份证号
    if(!checkCardId(info.userid)){
        return;
    }
    //校验车牌号码
    if(busiType == "1" && !checkCarNumber(info.carnumber)){
        return;
    }
    //校验姓名
    if(!checkNull(info.username, "姓名")){
        return;
    }
    //校验业务类型
    if(busiType == "01" && !checkNull(info.businame, "业务类型")){
        return;
    }
    //校验时间
    if(!checkNull(info.timerang, "预约时间")){
        return;
    }

    //增加openid
    info.openid = localStorage.getItem("openid");
    info.starttime = info.timerang.substr(0, 5).replace(/:/g, "");
    info.endtime = info.timerang.substr(6, 5).replace(/:/g, "");
    info.busitype = busiType;
    if(busiType == "0"){
        //弹框显示本业务的流程
        var busitips = localStorage.getItem("busitips")
        mui.alert(busitips, "提示", function(e){
            //提交
            orderInfoSubmit(info);
        })
    }else{
        orderInfoSubmit(info);
    }
});

function orderInfoSubmit(data){
    mui.showLoading("正在加载中...", "div");
    //ajax获取该日期下的预约时间
    $.ajax({
        type: "post",
        contentType: "application/json",
        async: true,
        url: "/wx/orderconfirm",
        data: JSON.stringify(data),
        dataType:"json",      //返回数据形式为json
        success:function(result){
            mui.hideLoading();
            console.info("orderInfoSubmit:"+JSON.stringify(result));
            if(result.retcode == "0000"){
                //预约成功
                var tips = "请在完成预约之后，在预约时间携带相关资料前往办事大厅办理相关业务，" +
                    "否则视为违约，违约两次之后，将限制其继续在微信办理预约任务。"
                mui.alert(tips, "提示", function(e){
                    //显示预约成功画面
                    var form = $('<form />', {action : "/wx/orderresult", method:"post", style:"display:none;"}).appendTo('body');
                    form.submit();
                });
            }else{
                mui.alert("请重试", result.retmsg, function(e){});
            }
        },
        error:function(errorMsg){
            mui.hideLoading();
            console.info("orderInfoSubmit-error:"+ JSON.stringify(errorMsg));
            mui.alert("请重试", "提交预约信息失败", function(e){});
        }
    });
}

function checkPhone(phone){
    if(phone == ""){
        mui.alert("", "手机号码必须输入", function(e){});
        return false;
    }
    var tel = /^1[3456789]\d{9}$/;
    if(!tel.test(phone)){
        mui.alert("请重新输入", "手机号码不正确", function(e){});
        return false;
    }else{
        return true;
    }
}

function checkVerify(verify){
    if(verify == ""){
        mui.alert("", "验证码必须输入", function(e){});
        return false;
    }
    var ver = /\d{4}$/;
    if(!ver.test(verify)){
        mui.alert("请重新输入", "验证码不正确", function(e){});
        return false;
    }
    return true;
}

function checkCardId(num){
    if(num == ""){
        mui.alert("", "身份证号码必须输入", function(e){});
        return false;
    }
    num = num.toUpperCase();
    //身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X。
    if (!(/(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(num))) {
        mui.alert("请重新输入", "身份证号码不正确", function(e){});
        return false;
    }
    //验证前2位，城市符合
    var aCity={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江 ",
        31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",
        43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",
        61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"};
    if(aCity[parseInt(num.substr(0,2))]==null){
        mui.alert("请重新输入", "身份证号码不正确", function(e){});
        return false;
    }

    //下面分别分析出生日期和校验位
    var len, re; len = num.length;
    if (len == 15) {
        re = new RegExp(/^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/);
        var arrSplit = num.match(re);  //检查生日日期是否正确
        var dtmBirth = new Date('19' + arrSplit[2] + '/' + arrSplit[3] + '/' + arrSplit[4]);
        var bGoodDay; bGoodDay = (dtmBirth.getYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4]));
        if (!bGoodDay) {
            mui.alert("请重新输入", "身份证号码不正确", function(e){});
            return false;
        } else {
            //将15位身份证转成18位 //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
            var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
            var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
            var nTemp = 0, i;
            num = num.substr(0, 6) + '19' + num.substr(6, num.length - 6);
            for(i = 0; i < 17; i ++) {
                nTemp += num.substr(i, 1) * arrInt[i];
            }
            num += arrCh[nTemp % 11];
            return true;
        }
    }
    if (len == 18) {
        re = new RegExp(/^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/);
        var arrSplit = num.match(re);  //检查生日日期是否正确
        var dtmBirth = new Date(arrSplit[2] + "/" + arrSplit[3] + "/" + arrSplit[4]);
        var bGoodDay; bGoodDay = (dtmBirth.getFullYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4]));
        if (!bGoodDay) {
            mui.alert("请重新输入", "身份证号码不正确", function(e){});
            return false;
        }
        else {
            //检验18位身份证的校验码是否正确。 //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
            var valnum;
            var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
            var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
            var nTemp = 0, i;
            for(i = 0; i < 17; i ++) {
                nTemp += num.substr(i, 1) * arrInt[i];
            }
            valnum = arrCh[nTemp % 11];
            if (valnum != num.substr(17, 1)) {
                mui.alert("请重新输入", "身份证号码不正确", function(e){});
                return false;
            }
            return true;
        }
    }
    mui.alert("请重新输入", "身份证号码不正确", function(e){});
    return false;
}

function checkCarNumber(pNumber){
    if(pNumber == ""){
        mui.alert("", "车牌号必须输入", function(e){});
        return false;
    }
    var pattPlateNumber =  /^(([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-Z](([0-9]{5}[DF])|([DF]([A-HJ-NP-Z0-9])[0-9]{4})))|([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-Z][A-HJ-NP-Z0-9]{4}[A-HJ-NP-Z0-9挂学警港澳使领]))$/;
    if(!pattPlateNumber.test(pNumber)){
        mui.alert("请重新输入", "车牌号不正确", function(e){});
    }else{
        return true;
    }
}

function checkNull(value, name){
    if(value == ""){
        mui.alert("", name + "必须输入", function(e){});
        return false;
    }
    return true;
}

function downPDF(obj, id){
    var href = "";
    if(id == 1){
        href = "../img/danwei.pdf"
    }else{
        href = "../img/geren.pdf"
    }
    var form = document.createElement('form');
    form.action = href;
    document.getElementsByTagName('body')[0].appendChild(form);
    form.submit();
}