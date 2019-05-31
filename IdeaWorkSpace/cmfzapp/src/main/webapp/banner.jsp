<%@page pageEncoding="UTF-8" contentType="text/html; UTF-8" isELIgnored="false" %>
<script>
    $(function () {
        $("#table").jqGrid({
            url: "${pageContext.request.contextPath}/banner/queryall",
            editurl: "${pageContext.request.contextPath}/banner/edit",//所有增删改操作
            datatype: "json",
            colNames: ["id", "标题", "状态", "创建时间", "图片", "描述"],
            colModel: [
                {name: "id"},
                {name: "title", editable: true},
                {
                    name: "status", editable: true, edittype: "select",
                    editoptions: {value: "展示:展示;不展示:不展示"}
                },
                {name: "createDate"},
                {
                    name: "imgPic", editable: true, edittype: "file",
                    formatter: function (cellvalue, options, rowObject) {
                        return "<img style='height:100px;width:200px' src='${pageContext.request.contextPath}/img/" + cellvalue + "' />"
                    }
                },
                {name: "description", editable: true}
            ],
            styleUI: "Bootstrap",//bs风格
            pager: "#bannerPager",//开启分页
            rowNum: 3,//每页展示
            rowList: [3, 6, 9, 12],//选择分页
            autowidth: true,//自适应宽度
            height: '500px',
            multiselect: true,//支持多选
            viewrecords: true,//显示总条数
            rownumbers: true//显示行号
        }).jqGrid("navGrid", "#bannerPager",
            {addtext: "添加", edittext: "修改", deltext: "删除", search: false},
            {//修改
                closeAfterEdit: true,

                afterSubmit: function (response) {

                    var bannerId = response.responseJSON.banner.id;
                    var message = response.responseJSON.edit
                    $.ajaxFileUpload({
                        url: "${pageContext.request.contextPath}/banner/upload",
                        fileElementId: "imgPic",
                        type: "post",
                        data: {bannerId: bannerId},
                        success: function (data) {
                            $("#table").trigger("reloadGrid")
                            $("#showmessage").show().html(message).attr("class", "alert alert-info")
                            setTimeout(function () {
                                $("#showmessage").hide()
                            }, 3000)
                        }
                    })


                    return response;
                }
            },
            {//添加
                closeAfterAdd: true,
                afterSubmit: function (response) {
                    var bannerId = response.responseJSON.banner.id;
                    var message = response.responseJSON.add
                    $.ajaxFileUpload({
                        url: "${pageContext.request.contextPath}/banner/upload",
                        fileElementId: "imgPic",
                        type: "post",
                        data: {bannerId: bannerId},
                        success: function (data) {
                            $("#table").trigger("reloadGrid")
                            $("#showmessage").show().html(message)
                            setTimeout(function () {
                                $("#showmessage").hide().attr("class", "alert alert-success")
                            }, 3000)
                        }
                    })
                    return response;
                }
            },
            {//删除
                afterComplete: function (response) {

                    var message = response.responseJSON.delete
                    $("#showmessage").show().html(message)
                    setTimeout(function () {
                        $("#showmessage").hide()
                    }, 3000)
                }
            }
        );
    })
</script>
<div id="showmessage" class="alert alert-danger" role="alert" style="display: none"></div>

<table id="table" class="table table-striped"></table>

<div id="bannerPager" style="height: 50px"></div>


