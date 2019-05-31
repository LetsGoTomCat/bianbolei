<%@page pageEncoding="UTF-8" contentType="text/html; UTF-8" isELIgnored="false" %>


<script>
    $(function () {
        $("#articleList").jqGrid({
            url: "${pageContext.request.contextPath}/article/queryall",
            editurl: "${pageContext.request.contextPath}/article/edit",//所有增删改操作
            datatype: "json",
            colNames: ["标题", "状态", "创建时间", "内容", "操作"],
            colModel: [
                {name: "title", editable: true},
                {
                    name: "status", editable: true, edittype: "select",
                    editoptions: {value: "展示:展示;不展示:不展示"}
                },
                {name: "createDate"},
                {name: "content", editable: true},
                {
                    name: "",
                    formatter: function (cellValue, options, value) {
                        return "<a href='#' class='glyphicon glyphicon-list'  onclick=\"lookModal('" + value.id + "')\">查看详情</a>" +
                            "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' class='glyphicon glyphicon-pencil'  onclick=\"updateModal('" + value.id + "')\">修改</a>" +
                            "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' class='glyphicon glyphicon-remove'  onclick=\"deleteModel('" + value.id + "')\">删除</a>"
                            ;

                    }
                }
            ],
            styleUI: "Bootstrap",//bs风格
            pager: "#articlePager",//开启分页
            rowNum: 3,//每页展示
            rowList: [3, 6, 9, 12],//选择分页
            autowidth: true,//自适应宽度
            height: '500px',
            multiselect: true,//支持多选
            viewrecords: true,//显示总条数
            rownumbers: true//显示行号
        }).jqGrid("navGrid", "#articlePager",
            {add: false, edit: false, del: false, search: false},
            {//修改
            },
            {//添加
            },
            {//删除
            }
        );
    })

    function showModal() {
        $("#myModal").modal("show")//模态框展示
        $("#addArticleFrom")[0].reset();
        KindEditor.html("#editor", "");//再次访问清空
        $("#modal_footer").html(
            "<button type=\"button\" class=\"btn btn-primary\" onclick=\"addModal()\">保存</button>\n" +
            "<button type=\"button\" class=\"btn btn-danger\" data-dismiss=\"modal\">取消</button>")
        KindEditor.create('#editor', {
            uploadJson: "${pageContext.request.contextPath}/kindeditor/uploadImg",//上传图片图片
            filePostName: "img",//默认是imgFile
            fileManagerJson: "${pageContext.request.contextPath}/kindeditor/getAll",//显示曾用过的图片
            allowFileManager: true,//允许图片上传
            resizeType: 1,//不让文本框移动
            afterBlur: function () {//解决无法传递
                this.sync()
            }
        });

    }


    function addModal() {
        $.ajax({
            url: "${pageContext.request.contextPath}/article/edit",
            datatype: "json",
            type: "post",
            data: $("#addArticleFrom").serialize(),
            success: function () {
                $("#articleList").trigger("reloadGrid")
                $("#modal_footer").html(
                    "<div class=\"alert alert-success\" role=\"alert\"><b>添加成功</b></div>"
                )
            }
        })
    }

    function lookModal(id) {//查看
        // alert(value.title)
        var value = $("#articleList").jqGrid("getRowData", id);
        $("#myModal").modal("show");
        $("#title").val(value.title)
        if (value.status == '展示') {
            $("#status").val('展示')
        } else {
            $("#status").val('不展示')
        }
        $("#showmassage").text("查看用户信息")
        $("#modal_footer").html(
            // "<button type=\"button\" class=\"btn btn-primary\" onclick=\"saveModal()\">确认修改</button>\n" +
            "<button type=\"button\" class=\"btn btn-danger\" data-dismiss=\"modal\" onclick=\"changemassage()\">退出</button>")
        KindEditor.create('#editor', {
            afterBlur: function () {//解决无法传递
                this.sync()
            }
        });
        $("#editor").val(value.content)
        KindEditor.html("#editor", value.content);

    }

    function changemassage() {
        $("#showmassage").text("编辑用户信息")
    }


    function deleteModel(id) {
        $.ajax({
            url: "${pageContext.request.contextPath}/article/delete",
            datatype: "json",
            type: "post",
            data: "id=" + id,
            success: function () {
                $("#articleList").trigger("reloadGrid")
            }

        })
    }

    function updateModal(id) {//修改
        var value = $("#articleList").jqGrid("getRowData", id);
        $("#myModal").modal("show");
        $("#title").val(value.title)
        if (value.status == '展示') {
            $("#status").val('展示')
        } else {
            $("#status").val('不展示')
        }
        $("#modal_footer").html(
            "<button type=\"button\" class=\"btn btn-primary\" onclick=\"saveModal('" + id + "')\">修改</button>\n" +
            "<button type=\"button\" class=\"btn btn-danger\" data-dismiss=\"modal\">取消</button>")
        KindEditor.create('#editor', {
            uploadJson: "${pageContext.request.contextPath}/kindeditor/uploadImg",//上传图片图片
            filePostName: "img",//默认是imgFile
            fileManagerJson: "${pageContext.request.contextPath}/kindeditor/getAll",//显示曾用过的图片
            allowFileManager: true,//允许图片上传
            resizeType: 1,//不让文本框移动
            afterBlur: function () {//解决无法传递
                this.sync()
            }
        });
        $("#editor").val(value.content)
        KindEditor.html("#editor", value.content);

    }

    function saveModal(id) {
        $.ajax({
            url: "${pageContext.request.contextPath}/article/update?id=" + id,
            datatype: "json",
            type: "post",
            data: $("#addArticleFrom").serialize(),
            success: function () {
                $("#articleList").trigger("reloadGrid")
                $("#modal_footer").html(
                    "<div class=\"alert alert-success\" role=\"alert\"><b>修改成功</b></div>"
                )
            }
        })
    }

</script>


<div>
    <!-- Nav tabs -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active">
            <a href="#home" aria-controls="home" role="tab" data-toggle="tab"><b>文章信息</b></a></li>
        <li><a href="javascript:void(0)" onclick="showModal()"><b>添加文章</b></a></li>
    </ul>
</div>


<table id="articleList" class="table table-striped"></table>
<div style="height: 50px" id="articlePager"></div>


<div class="modal fade" id="myModal" tabindex="">
    <div class="modal-dialog">
        <div class="modal-content" style="width:750px">
            <!--模态框标题-->
            <div class="modal-header">
                <!--
                    用来关闭模态框的属性:data-dismiss="modal"
                -->
                <button type="button" class="close" data-dismiss="modal"><span>&times;</span></button>
                <h4 id="showmassage" class="modal-title">编辑用户信息</h4>
            </div>

            <!--模态框内容体-->
            <div class="modal-body">
                <form action="" class="form-horizontal" id="addArticleFrom">
                    <div class="form-group">
                        <label class="col-sm-1 control-label">标题</label>
                        <div class="col-sm-5">
                            <input type="text" name="title" id="title" placeholder="请输入标题" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-1 control-label">状态</label>
                        <div class="col-sm-5">
                            <select class="form-control" name="status" id="status">
                                <option value="展示">展示</option>
                                <option value="不展示">不展示</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <textarea id="editor" name="content" style="width:700px;height:300px;"></textarea>
                        </div>
                    </div>
                    <input id="addInsertImg" name="insertImg" hidden>
                </form>
            </div>
            <!--模态页脚-->
            <div class="modal-footer" id="modal_footer">

            </div>
        </div>
    </div>
</div>





