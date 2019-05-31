<%@page pageEncoding="UTF-8" contentType="text/html; UTF-8" isELIgnored="false" %>
<script>
    $(function () {
        $("#albumList").jqGrid({
            url: "${pageContext.request.contextPath}/album/queryall",
            editurl: "${pageContext.request.contextPath}/album/edit",//所有增删改操作
            datatype: "json",//json串的型式返回
            //表格内容
            colNames: ["标题", "评分", "作者", "播音员", "章节数量", "简介", "创建时间", "插图"],
            colModel: [
                {name: "title", editable: true},
                {name: "score", editable: true},
                {name: "author", editable: true},
                {name: "broadcast", editable: true},
                {name: "count", editable: true},
                {name: "brief", editable: true},
                {name: "createDate"},
                {
                    name: "coverPic", editable: true, edittype: "file",
                    formatter: function (cellvalue, options, rowObject) {//显示图片
                        return "<img style='height:50px;width:100px' src='${pageContext.request.contextPath}/img/" + cellvalue + "' />"
                    }
                },
            ],
            pager: "#albumPager",//分页
            rowNum: 3,//每页五个
            rowList: [3, 6, 9],//分页选择
            viewrecords: true,//显示总条数
            height: '500px',//高度
            multiselect: true,//支持多选
            rownumbers: true,//显示行号
            styleUI: "Bootstrap",//bs
            autowidth: true,//自使用宽度
            subGrid: true,//展示下拉标头
            subGridRowExpanded: function (subGidId, albumId) {
                addSubGrid(subGidId, albumId);//添加子标签，并给到id,封装到subGidId中
            }

        }).jqGrid("navGrid", "#albumPager",
            {addtext: "添加", edittext: "修改", deltext: "删除", search: false},
            {//修改
                closeAfterEdit: true,
                afterSubmit: function (response) {

                    var albumId = response.responseJSON.album.id;
                    var message = response.responseJSON.edit
                    $.ajaxFileUpload({
                        url: "${pageContext.request.contextPath}/album/upload",
                        fileElementId: "coverPic",
                        type: "post",
                        data: {albumId: albumId},
                        success: function (data) {
                            $("#albumList").trigger("reloadGrid")
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
                    var albumId = response.responseJSON.album.id;
                    var message = response.responseJSON.add
                    $.ajaxFileUpload({
                        url: "${pageContext.request.contextPath}/album/upload",
                        fileElementId: "coverPic",
                        type: "post",
                        data: {albumId: albumId},
                        success: function (data) {
                            $("#albumList").trigger("reloadGrid")
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
        )
    })
    //************************************************************************************************
    // ************************************************************************************************
    //************************************************************************************************
    function addSubGrid(subGidId, albumId) {
        var subGridTableId = subGidId + "table";//创建表id
        var subGridPagerId = subGidId + "pager";//创建增删改id
        $("#" + subGidId).html(//给子表格标签添加内容
            "<table id='" + subGridTableId + "' class=\"table table-striped\"></table>" +
            "<div id='" + subGridPagerId + "' style=\"height: 50px\"></div>")

        $("#" + subGridTableId).jqGrid({
            url: "${pageContext.request.contextPath}/chapter/queryall?albumid=" + albumId,
            editurl: "${pageContext.request.contextPath}/chapter/edit?albumid=" + albumId,//所有增删改操作
            datatype: "json",
            colNames: ["标题", "大小", "时长", "章节操作"],
            colModel: [
                {name: "title", editable: true},
                {name: "size"},//大小
                {name: "duration"},//时长
                {
                    name: "audio", editable: true, edittype: "file",
                    formatter: function (cellValue, options, value) {
                        return "<a href='#' onclick=\"playAudio('" + cellValue + "')\"><span class='glyphicon glyphicon-play-circle'></span></a>&nbsp;&nbsp;&nbsp;" +
                            "<a href='#' onclick=\"downLoadAudio('" + cellValue + "')\"><span class='glyphicon glyphicon-download'></span></a> "
                    }
                }
            ],
            pager: "#" + subGridPagerId,
            rowNum: 5,
            rowList: [3, 4, 7],
            viewrecords: true,
            height: '60%',
            multiselect: true,
            styleUI: "Bootstrap",
            autowidth: true
        }).jqGrid("navGrid", "#" + subGridPagerId
            ,
            {addtext: "添加", edittext: "修改", deltext: "删除", search: false},
            {//修改
                closeAfterEdit: true,
                afterSubmit: function (response) {
                    var chapterId = response.responseJSON.chapter.id;
                    var message = response.responseJSON.edit
                    $.ajaxFileUpload({
                        url: "${pageContext.request.contextPath}/chapter/upload",
                        fileElementId: "audio",
                        type: "post",
                        data: {chapterId: chapterId},
                        success: function (data) {
                            $("#" + subGridTableId).trigger("reloadGrid")
                            $("#showmessage").show().html(message)
                            setTimeout(function () {
                                $("#showmessage").hide().attr("class", "alert alert-success")
                            }, 3000)
                        }
                    })


                    return response;
                }
            },
            {//添加
                closeAfterAdd: true,
                afterSubmit: function (response) {
                    var chapterId = response.responseJSON.chapter.id;
                    var message = response.responseJSON.add
                    $.ajaxFileUpload({
                        url: "${pageContext.request.contextPath}/chapter/upload",
                        fileElementId: "audio",
                        type: "post",
                        data: {chapterId: chapterId},
                        success: function (data) {
                            $("#" + subGridTableId).trigger("reloadGrid")
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
        )
    }

    function playAudio(cellValue) {
        $("#playAudio").modal("show")
        $("#audioId").attr("src", "${pageContext.request.contextPath}/upload/audio/" + cellValue);
    }

    function downLoadAudio(cellValue) {
        location.href = "${pageContext.request.contextPath}/chapter/downLoadAudio?audioName=" + cellValue;
    }

</script>

<div id="playAudio" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <audio id="audioId" src="" controls></audio>
    </div><!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<div id="showmessage" class="alert alert-danger" role="alert" style="display: none"></div>
<table id="albumList" class="table table-striped"></table>

<div id="albumPager" style="height: 50px"></div>