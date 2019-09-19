/**
 * Created by Administrator on 2019/2/22.
 */
var busiList = [];
if (App.isAngularJsApp() === false) {
    jQuery(document).ready(function() {
        BusiTable.init();
        //新增和编辑
        BusiEdit.init();
        //业务删除初始化
        BusiDelete.init();
        //选中所属业务
        $('#busitree').on('select_node.jstree', function(e,data) {
            var ref = $(this).jstree(true);
            var nodes = ref.get_checked();  //使用get_checked方法
            $.each(nodes, function(i, nd) {
                if (nd != data.node.id)
                    ref.uncheck_node(nd);
            });
            $(this).siblings("input").val(data.node.text);
            $(this).hide();
        });

        //取消选中所属业务
        $('#busitree').on('deselect_node.jstree', function(e,data) {
            $(this).siblings("input").val("");
            $(this).hide();
        });

        //按下input之外的地方，所属业务输入框不显示
        $(document).click(function(e){
            if ($(e.target)[0] != $("#busi")[0]){
                $("#busitree").hide();
            }
        });

        //查询按钮按下
        $("#busi_inquiry").on("click", function(){
            $("#busi_table").bootstrapTable('destroy');
            BusiTable.init();
        });
    });
}

var BusiTable = function () {
    var initTable = function () {

        var table = $('#busi_table');
        table.bootstrapTable({
            striped : true, //是否显示行间隔色
            pageNumber : 1, //初始化加载第一页
            pagination : false,//是否分页
            sidePagination : 'client',//server:服务器端分页|client：前端分页
            pageSize : 10,//单页记录数
            showRefresh : false,//刷新按钮
            idField: 'id',
            checkboxHeader: false,
            ajax :function (e) {
                //因为需要做成业务选择的树形业务，所以一次获取所有数据，前端分页
                var data = e.data;
                var callback = e.success;
                var formData = $(".inquiry-form").getFormData();
                var da = {
                    //id: formData.id,
                    //uname: formData.uname,
                    currentpage: "",
                    pagesize: "",
                    startindex: "0",
                    draw: 1
                };
                busiDataGet(da, callback);
            },
            columns : [
                {
                    field: 'xuhao',
                    width: 36,
                    title : '序号',
                    formatter: function (value, row, index) {
                        return index + 1;
                    }
                },{
                    field: 'check',  checkbox: true, formatter: function (value, row, index) {
                        if (row.check == true) {
                            //设置选中
                            return {  checked: true };
                        }
                    }
                }, {
                    title : '业务ID',
                    field : 'id',
                    visible: false
                }, {
                    title : '业务名称',
                    field : 'uname'
                }, {
                    title : '业务内容',
                    formatter: function (value, row, index) {
                        return '<a href="javascript:;" id="op_content" busiid="' + row.id + '">查看业务内容</a>'
                    }
                }, {
                    title : '操作',
                    formatter: function (value, row, index) {
                        return '<a href="javascript:;" id="op_edit" busiid="' + row.id + '">编辑</a>'
                    }
                }
            ],
            //在哪一列展开树形
            treeShowField: 'uname',
            //指定父id列
            parentIdField: 'parentid',
            onResetView: function(data) {
                //console.log('load');
                table.treegrid({
                    initialState: 'expanded',// 所有节点都折叠
                    // initialState: 'expanded',// 所有节点都展开，默认展开
                    treeColumn: 2,
                    expanderExpandedClass: 'fa fa-folder-open icon-state-warning icon-lg',  //图标样式
                    expanderCollapsedClass: 'fa fa-folder icon-state-warning icon-lg',
                    expanderLeafClass:'fa fa-file-text-o icon-state-warning icon-lg',
                    onChange: function() {
                        //$table.bootstrapTable('resetWidth');
                    }
                });

                //只展开树形的第一级节点
                //table.treegrid('getRootNodes').treegrid('expand');
            },
            onCheck:function(row){
                var datas = table.bootstrapTable('getData');
                // 勾选子类
                //selectChilds(datas,row,"busiid","parentid",true);

                // 勾选父类
                //selectParentChecked(datas,row,"busiid","parentid");
                //限制单选
                singleSelect(datas, row, "id");
                // 刷新数据
                table.bootstrapTable('load', datas);
            },

            onUncheck:function(row){
                //var datas = table.bootstrapTable('getData');
                //selectChilds(datas,row,"busiid","parentid",false);
                //table.bootstrapTable('load', datas);
            }
        });
        table.on('change', 'tbody tr .checkboxes', function () {
            $(this).parents('tr').toggleClass("active");
        });
    };
    return {
        init: function () {
            initTable();
        }
    };

}();

var BusiEdit = function() {
    var handleRegister = function() {
        var validator = $('.register-form').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",
            rules: {
                uname: {
                    required: true
                },
                sort: {
                    required: true
                }
            },

            messages: {
                uname: {
                    required: "业务名称必须输入"
                },
                sort: {
                    required: "排序号必须输入"
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

        //所属业务验证，选择所属业务的时候，不能选择自身，也不能选择自身的子业务
        jQuery.validator.addMethod("fjid", function(value, element) {
            var busi = $('.register-form').getFormData();
            var ref = $('#busitree').jstree(true);
            var nodes = ref.get_selected();
            var same = parentOrSelf(nodes, busi.id);
            return this.optional(element) || !same;
        }, "不能选择自身和自身的子业务作为所属业务");

        //确定按钮按下
        $('#register-btn').click(function() {
            if ($('.register-form').validate().form()) {
                var busi = $('.register-form').getFormData();
                busi.fjid = "";
                try{
                    var select = $('#busitree').jstree(true).get_selected(true);
                    if( select.length > 0){
                        busi.fjid = select[0].id;
                    }
                }catch (e) {

                }
                if($("input[name=edittype]").val() == BUSIADD){
                    busiAdd(busi);
                }else{
                    busiEdit(busi);
                }
            }
        });
        //增加业务
        $('#op_add').click(function() {
            validator.resetForm();
            $(".register-form").find(".has-error").removeClass("has-error");
            $(".modal-title").text("新增业务");
            $(":input",".register-form").not(":button,:reset,:submit,:radio").val("")
                .removeAttr("checked")
                .removeAttr("selected");
            //清空业务输入框
            clearSelect($("#busitree"));
            //操作类型
            $("input[name=edittype]").val(BUSIADD);
            $('#edit_busi').modal('show');
        });
        //编辑业务
        $("#busi_table").on('click', '#op_edit', function (e) {
            e.preventDefault();
            validator.resetForm();
            $(".register-form").find(".has-error").removeClass("has-error");
            $(".modal-title").text("编辑业务");
            var exclude = ["fjid"];
            var id = $(this).attr("busiid");
            var busilist = $("#busi_table").bootstrapTable('getData');
            var busi = new Object();
            for(var i=0; i < busilist.length; i++){
                if(id == busilist[i].id){
                    busi = busilist[i];
                }
            }
            var options = { jsonValue: busi, exclude:exclude,isDebug: false};
            $(".register-form").initForm(options);
            //所属业务初始化
            clearSelect($("#busitree"));
            if(busi.parentid != 0){
                $('#busitree').jstree(true).select_node(busi.parentid);
            }
            //操作类型
            $("input[name=edittype]").val(BUSIEDIT);
            $('#edit_busi').modal('show');
        });
        //查看业务详情
        $("#busi_table").on('click', '#op_content', function (e) {
            var id = $(this).attr("busiid");
            var busilist = $("#busi_table").bootstrapTable('getData');
            var busi = new Object();
            for(var i=0; i < busilist.length; i++){
                if(id == busilist[i].id){
                    busi = busilist[i];
                }
            }
            alertDialog(busi.content);
        })
    };

    return {
        //main function to initiate the module
        init: function() {
            handleRegister();
        }
    };
}();

var BusiDelete = function() {
    var handle = function(){
        $('#op_del').click(function() {
            var len = $("input[name=btSelectItem]:checked").length;
            if(len < 1){
                alertDialog("至少选中一项！");
            }else{
                confirmDialog("数据删除后将不可恢复，您确定要删除吗？", BusiDelete.deleteBusi)
            }
        });
    }
    return{
        deleteBusi: function(){
            var busilist = new Object();
            var select = $("#busi_table").bootstrapTable('getSelections');
            for(var i=0; i<select.length;i++) {
                busilist.id = select[i].id;
            }
            busiDelete(busilist);
        },
        init: function () {
            handle();
        }
    }
}();

function getBusiDataEnd(flg, result, callback){
    App.unblockUI('#lay-out');
    if(flg){
        if (result && result.retcode == SUCCESS) {
            busiList = result.busilist;
            //做成新增或者删除业务的树形结构
            busiNameSelectBuild(busiList, $("#busitree"));
            //给页面上的table赋值
            bootstrapTreeTableDataSet(result.cnt, result.busilist, "busilist", "id", callback);
        }else{
            //给页面上的table赋值
            bootstrapTreeTableDataSet(0, [], "busilist", "id", callback);
            alertDialog("业务信息获取失败！");
        }
    }else{
        //给页面上的table赋值
        bootstrapTreeTableDataSet(0, [], "busilist", "id", callback);
        alertDialog("业务信息获取失败！");
    }
}

function busiInfoEditEnd(flg, result, type){
    var res = "失败";
    var text = "";
    var alert = "";
    switch (type){
        case BUSIADD:
            text = "新增";
            break;
        case BUSIEDIT:
            text = "编辑";
            break;
        case BUSIDELETE:
            text = "删除";
            break;
    }
    if(flg){
        if(result && result.retcode != SUCCESS){
            alert = result.retmsg;
        }
        if (result && result.retcode == SUCCESS) {
            res = "成功";
            $("#busi_table").bootstrapTable('destroy');
            BusiTable.init();
            $('#edit_busi').modal('hide');
        }
    }
    if(alert == "") alert = text + "业务" + res + "！";
    App.unblockUI('#lay-out');
    alertDialog(alert);
}

function parentOrSelf(node, checkId){
    var ref = $("#busitree").jstree(true);
    if(node == checkId){
        return true
    }else{
        var pnode = ref.get_parent(node);
        if(pnode){
            return parentOrSelf(pnode, checkId);
        }else{
            return false;
        }
    }
}