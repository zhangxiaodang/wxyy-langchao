
function getDateStr(AddDayCount, type) {
    var dd = new Date();
    dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期
    var y = dd.getFullYear();
    var m = (dd.getMonth()+1);//获取当前月份的日期，不足10补0
    var d = dd.getDate();//获取当前几号，不足10补0;
    if(type == 0){
        return m + "月" + d + "日";
    }else{
        m < 10 ? "0"+ m : m;//获取当前月份的日期，不足10补0
        d < 10 ? "0"+ d : d;//获取当前几号，不足10补0;
        return "" + y  + m  + d;
    }
}

function timeFormat(time){
    if(time == undefined || time == null) return "";
    if(time.length < 4) return time;
    return time.substring(0, 2) + ":" + time.substring(2, 4)
}