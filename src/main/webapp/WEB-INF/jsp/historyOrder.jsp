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
                <a class="xb-order-btn " href="index.jsp"><i class="weui-icon-back"></i></a>
                <h2>历史订单</h2>
            </div>
        </div>
        <div class="page__bd page__bd_spacing">
            <ul>
                <li>
                    <div class="weui-cells">
                        <a class="weui-cell weui-cell_access" id="historyOrder">
                            <div class="weui-cell__bd">我发布的订单</div>
                            <div class="weui-cell__ft"></div>
                        </a>
                    </div>
                </li>
                <li>
                    <div class="weui-cells">
                        <a class="weui-cell weui-cell_access" id="historyReceiveOrder">
                            <div class="weui-cell__bd">我接收的订单</div>
                            <div class="weui-cell__ft"></div>
                        </a>
                    </div>
                </li>
            </ul>
        </div>
        <div class="page__ft">

        </div>
    </div>
    <script type="text/javascript">

        $(function () {
            var openid = getCookie('openid');
            $('#historyOrder').attr('href', '/WxPost/history/order/1?openid=' + openid);
            $('#historyReceiveOrder').attr('href', '/WxPost/history/receiveorder/1?openid=' + openid);
        });
</script>
</script>
<
script
src = "assert/cookieutil.js" ></script>
<script src="assert/zepto.min.js"></script>
<script src="assert/example.js"></script>
</body>

</html>