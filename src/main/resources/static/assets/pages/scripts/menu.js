/**
 * Created by Administrator on 2019/2/19.
 */
var menuMake = '<li class="sidebar-toggler-wrapper hide">' +
'<div class="sidebar-toggler">' +
    '</div>' +
    '</li>' +
    '<li class="sidebar-search-wrapper">' +
    '<div style="color:white; padding: 10px 20px">' +
    '<i class="icon-grid"></i>' +
    '<span class="title"> 功能导航 </span>' +
    '</div>' +
    '</li>';

function makeMenu(data){
    for(var i=0; i<data.length; i++){
        if(data[i].power == 0) {
            continue;
        }
        if(data[i].menutype == 0){
            menuMake += '<li class="nav-item">' +
                '<a href="javascript:;" class="nav-link nav-toggle" name="' + data[i].menuid + '">' +
                    '<i class="'+ data[i].menuicon +'"></i>' +
                    '<span class="title"> '+ data[i].menuname +' </span>' +
                '</a>' +
                    '<ul class="sub-menu">'
        }else{
            menuMake += '<li class="nav-item">' +
                '<a href="' + data[i].url + '" class="nav-link nav-toggle" data-id="' + data[i].menuid + '" onclick="setMenu(this)">' +
                    '<i class="'+ data[i].menuicon +'"></i>' +
                    '<span class="title"> '+ data[i].menuname +' </span>' +
                '</a>'
        }
        if(data[i].hasOwnProperty("menulist") && data[i].menulist != undefined && data[i].menulist != null ){
            makeMenu(data[i].menulist);
        }else{
            menuMake += "</li>";
            if( i == data.length - 1) menuMake += "</ul>"
        }
    }
    menuMake += "</li>";
}

function setMenu(obj){
    var menu = $(obj).attr("href");
    localStorage.setItem("menu", menu)
}