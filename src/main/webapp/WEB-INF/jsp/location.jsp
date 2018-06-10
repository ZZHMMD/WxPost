<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<base href="<%=basePath %>"/>
<html lang="zh-cmn-Hans">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>快递镖局</title>
    <link rel="stylesheet" href="assert/style/weui.css"/>
    <link rel="stylesheet" href="assert/example.css"/>
</head>

<body ontouchstart>
<div class="weui-toptips weui-toptips_warn js_tooltips">错误提示</div>
<div class="container" id="container"></div>
<script type="text/html" id="tpl_home">
    <div class="page">
        <div class="page__hd xb-orderBg">
            <div class="xb-order">
                <a class="xb-order-btn" href="index.jsp" id="sendBtn"><i class="weui-icon-back"></i></a>
                <h2>编辑收货地址</h2>
            </div>
        </div>
        <div class="page__bd page__bd_spacing">
            <ul>
                <li>
                    <div class="weui-cells weui-cells_form">
                        <div class="weui-cell" style="margin-top:10px;">
                            <div class="wui-cell__hd">
                                <label class="weui-label">快递取货码</label>
                            </div>
                            <div class="weui-cell__bd">
                                <input type="number" class="weui-input" pattern="[0-9]*"/>
                            </div>
                        </div>
                        <div class="weui-cell">
                            <div class="wui-cell__hd">
                                <label class="weui-label">电话号码</label>
                            </div>
                            <div class="weui-cell__bd">
                                <input type="number" class="weui-input" pattern="[0-9]*"/>
                            </div>
                        </div>
                        <div class="weui-cell weui-cell_select">
                            <div class="weui-cell__hd">
                                <label for="" class="weui-label">取货地址</label>
                            </div>
                            <div class="weui-cell__bd">
                                <select class="weui-select" name="select1">
                                    <option selected="" value="1">北区菜鸟驿站</option>
                                    <option value="2">南区25栋</option>
                                    <option value="3">南区17栋</option>
                                </select>
                            </div>
                        </div>
                        <div class="weui-cell weui-cell_select">
                            <div class="weui-cell__hd">
                                <label for="" class="weui-label">收货地址</label>
                            </div>
                            <div class="weui-cell__bd">
                                <select class="weui-select" name="select1">
                                    <option selected="" value="1">32栋</option>
                                    <option value="2">33栋</option>
                                    <option value="3">34栋</option>
                                </select>
                            </div>
                            <div class="weui-cell__bd">
                                <select class="weui-select" name="select1">
                                    <option selected="" value="1">101</option>
                                    <option value="2">102</option>
                                    <option value="3">201</option>
                                </select>
                            </div>
                        </div>
                        <div class="weui-cell weui-cell_select">
                            <div class="weui-cell__hd">
                                <label for="" class="weui-label">快递大小</label>
                            </div>
                            <div class="weui-cell__bd">
                                <select class="weui-select" name="select1">
                                    <option selected="" value="3">中(3元)</option>
                                    <option value="2">小(2元)</option>
                                    <option value="5">大(5元)</option>
                                </select>
                            </div>
                        </div>
                        <div class="weui-cell weui-cell_select">
                            <div class="weui-cell__hd">
                                <label for="" class="weui-label">是否加急</label>
                            </div>
                            <div class="weui-cell__bd">
                                <select class="weui-select" name="select1">
                                    <option selected="" value="1">是</option>
                                    <option value="0">否</option>
                                </select>
                            </div>
                        </div>
                        <div class="weui-cell">
                            <div class="weui-cell__hd" style="width:15%;">
                                <label for="" class="weui-label">备注</label>
                            </div>
                            <div class="weui-cell__bd">
                                <textarea type="text" class="weui-textarea" rows="3"
                                          placeholder="提出你的需求,没有也行！"></textarea>
                                <div class="weui-textarea-counter">
                                    <span>0</span>/200
                                </div>
                            </div>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
        <div class="page__ft  xb-detail">
            <a href="orderMsg.jsp" class="weui-btn weui-btn_primary">发布</a>
        </div>
    </div>
    <script type="text/javascript">
        $(function () {
            $('.js_item').on('click', function () {
                var id = $(this).data('id');
                window.pageManager.go(id);
            });

//					$('#sendBtn').on('click',function(){
//						window.localStorage.setItem("tabbar","home");
//					});
        });
</script>
</script>

<
script
src = "assert/zepto.min.js" ></script>
<script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="https://res.wx.qq.com/open/libs/weuijs/1.0.0/weui.min.js"></script>
<script src="assert/example.js"></script>
</body>

</html>