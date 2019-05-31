<%@page pageEncoding="UTF-8" contentType="text/html; UTF-8" isELIgnored="false" %>
<script>
    $(function () {
        $("#table").jqGrid({
            url: "${pageContext.request.contextPath}/user/queryall",
            editurl: "${pageContext.request.contextPath}/user/edit",//所有增删改操作
            datatype: "json",
            colNames: ["id", "电话", "密码", "头像", "名字", "法号", "性别", "省", "市", "签名", "状态", "时间"],
            colModel: [
                {name: "id", },
                {name: "phone", },
                {name: "password", },
                {
                    name: "headPic", edittype: "file",
                    formatter: function (cellvalue, options, rowObject) {
                        return "<img style='height:50px;width:100px' src='${pageContext.request.contextPath}/img/" + cellvalue + "' />"
                    }

                },
                {name: "name", },
                {name: "dharma", },
                {name: "sex", },
                {name: "province", },
                {name: "city", },
                {name: "sign", },
                {
                    name: "status", editable: true, edittype: "select",
                    editoptions: {value: "展示:展示;不展示:不展示"}
                },
                {name: "createDate"},

            ],
            styleUI: "Bootstrap",//bs风格
            pager: "#Pager",//开启分页
            rowNum: 3,//每页展示
            rowList: [3, 6, 9, 12],//选择分页
            autowidth: true,//自适应宽度
            height: '460px',
            multiselect: true,//支持多选
            viewrecords: true,//显示总条数
            rownumbers: true//显示行号
        }).jqGrid("navGrid", "#Pager",
            {add: false, edittext: "修改", del: false, search: false},
            {//修改
                closeAfterEdit: true,
                afterComplete: function (response) {

                    var message = response.responseJSON.update
                    $("#showmessage").show().html(message)
                    setTimeout(function () {
                        $("#showmessage").hide()
                    }, 3000)
                }
            },
            {//添加
            },
            {//删除
            }
        );
    })

    function outputmassage() {
        location.href="${pageContext.request.contextPath}/user/easyPoiOut"
    }

    function showuserregist() {
        $("#mymodel").modal("show")//模态框展示
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));
        var data=null
        // 指定图表的配置项和数据
        var option = {
            title: {
                text: '七日用户注册走势图'
            },
            tooltip: {},
            legend: {
                data:['当日注册用户数量']
            },
            xAxis: {
                data:[]
            },
            yAxis: {},
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
        $.ajax({
            url:"${pageContext.request.contextPath}/user/getSevenDay",
            type: "get",
            datatype:"json",
            success:function (map) {
                myChart.setOption({
                    xAxis: {
                        data: [map.x.a,map.x.b,map.x.c,map.x.d,map.x.e,map.x.f,map.x.g]
                    },
                    series: [{
                        name: '当日注册用户数量',
                        type: 'line',
                        data: [map.s.a,map.s.b,map.s.c,map.s.d,map.s.e,map.s.f,map.s.g]
                    }]

                })
            }
        })
    }
    
    function showmap() {
        $("#mymodel1").modal("show")//模态框展示
        var myChart = echarts.init(document.getElementById('map'));
        // 指定图表的配置项和数据
        option = {
            title : {
                text: '用户地区分布',
                subtext: '纯属虚构',
                left: 'center'
            },
            tooltip : {
                trigger: 'item'
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data:['注册人数']
            },
            visualMap: {
                min: 0,
                max: 5,
                left: 'left',
                top: 'bottom',
                text:['高','低'],           // 文本，默认为数值文本
                calculable : true
            },
            toolbox: {
                show: true,
                orient : 'vertical',
                left: 'right',
                top: 'center',
                feature : {
                    mark : {show: true},
                    dataView : {show: true, readOnly: false},
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            series : [
                {
                    name: '注册人数',
                    type: 'map',
                    mapType: 'china',
                    roam: false,
                    label: {
                        normal: {
                            show: false
                        },
                        emphasis: {
                            show: true
                        }
                    }
                }
            ]
        };


        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
        $.ajax({
            url:"${pageContext.request.contextPath}/user/getUser",
            type: "get",
            datatype:"json",
            success:function (data) {
                myChart.setOption({
                    series : [
                        {
                            data:data
                        }
                    ]
                })
            }
        })

        var goEasy = new GoEasy({
            appkey: "BS-fc8bfbc77ebf47199876061cef12b256"
        });
        goEasy.subscribe({
            channel: "test_channel",
            onMessage: function (message) {
                var data = JSON.parse(message.content);
                myChart.setOption({
                    series: [{
                        data: data
                    }]

                })
            }
        });



    }
</script>

<div>
    <!-- Nav tabs -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active">
            <a href="#home" aria-controls="home" role="tab" data-toggle="tab"><b>用户信息</b></a></li>
        <li><a href="javascript:void(0)" onclick="outputmassage()"><b>导出信息</b></a></li>
        <li><a href="javascript:void(0)" onclick="showuserregist()"><b>七日用户注册走势图</b></a></li>
        <li><a href="javascript:void(0)" onclick="showmap()"><b>用户地区分布图</b></a></li>
    </ul>
</div>
<div id="showmessage" class="alert alert-danger" role="alert" style="display: none"></div>
<table id="table" class="table table-striped"></table>

<div id="Pager" style="height: 50px"></div>

<div class="modal fade" id ="mymodel" tabindex="-1" role="dialog" >
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            </div>
            <div id="main" style="width: 600px;height:400px;" class="modal-body">

            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<div class="modal fade" id ="mymodel1" tabindex="-1" role="dialog" >
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            </div>
            <div id="map" style="width: 600px;height:400px;" class="modal-body">

            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
