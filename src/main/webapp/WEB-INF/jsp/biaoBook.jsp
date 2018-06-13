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
                <a class="xb-order-btn " href="https://www.huahuayu.com.cn/WxPost/usercenter"><i
                        class="weui-icon-back"></i></a>
                <h2>使用帮助</h2>
            </div>
        </div>
        <style>
            .xb-aboutUs-img {
                position: relative;
                top: 0;
                left: 0;
                right: 0;
                max-width: 100%;
                height: auto;
                padding-top: 10px;
            }

            .xb-aboutUs-img img {
                width: 100% !important;
                height: auto !important;
                border-radius: 5px;
            }
        </style>
        <div class="page__bd page__bd_spacing">
            <div class="xb-aboutUs-img">
                <img src="assert/biaoBook.jpg"/>
            </div>
        </div>

    </div>
    <script type="text/javascript">
        $(function () {
        });
</script>
</script>

<
script
src = "./assert/zepto.min.js" ></script>
<script src="assert/example.js"></script>
</body>

</html>