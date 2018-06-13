<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<base href="<%=basePath %>"/>
<html lang="zh-cmn-Hans">
<head>
    <meta charset="UTF-8">
    <meta name="full-screen" content="yes">
    <meta name="x5-fullscreen" content="true">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>快递镖局</title>
    <link rel="stylesheet" href="assert/style/weui.css"/>
    <link rel="stylesheet" href="assert/example.css"/>
    <link rel="stylesheet" href="assert/dropload.css"/>
</head>
<body ontouchstart>
<div class="weui-toptips weui-toptips_warn js_tooltips">错误提示</div>
<div class="container" id="container"></div>
<script type="text/html" id="tpl_home">
    <div class="page">
        <div class="page__hd xb-orderBg">
            <div class="xb-order">
                <a class="xb-order-btn" href="<%=basePath %>index.jsp"><i class="weui-icon-back"></i></a>
                <h2>我发布的订单</h2>
            </div>
        </div>
        <div class="page__bd page__bd_spacing content">
            <ul class="lists">
                <c:forEach items="${result.obj.list}" var="item">
                    <li class="xb-pdTop">
                        <div class="weui-form-preview">
                            <div class="weui-form-preview__hd">
                                <div class="weui-form-preview__item">
                                    <label class="weui-form-preview__label">订单名称</label>
                                    <em class="weui-form-preview__value">${item.name}</em>
                                </div>
                            </div>
                            <div class="weui-form-preview__bd">
                                <div class="weui-form-preview__item">
                                    <label class="weui-form-preview__label">发布时间</label>
                                    <span class="weui-form-preview__value">${item.showTime}</span>
                                </div>
                                <div class="weui-form-preview__item">
                                    <label class="weui-form-preview__label">取货码</label>
                                    <span class="weui-form-preview__value">${item.receiveNum}</span>
                                </div>
                                <div class="weui-form-preview__item">
                                    <label class="weui-form-preview__label">取货地址</label>
                                    <span class="weui-form-preview__value">${item.getAddress}</span>
                                </div>
                                <c:if test="${item.status ==2}">
                                    <div class="weui-form-preview__item">
                                        <label class="weui-form-preview__label">取货人</label>
                                        <span class="weui-form-preview__value">${item.username}</span>
                                    </div>
                                    <div class="weui-form-preview__item">
                                        <label class="weui-form-preview__label">联系方式</label>
                                        <span class="weui-form-preview__value"><a
                                                href="tel:${item.phoneNum}">${item.phoneNum}</a></span>
                                    </div>
                                </c:if>
                                <div class="weui-form-preview__item">
                                    <label class="weui-form-preview__label">收货地址</label>
                                    <span class="weui-form-preview__value">${item.receiveAddress}</span>
                                </div>
                                <div class="weui-form-preview__item">
                                    <label class="weui-form-preview__label">订单价值</label>
                                    <span class="weui-form-preview__value">${item.size}元</span>
                                </div>
                                <div class="weui-form-preview__item">
                                    <label class="weui-form-preview__label">备注</label>
                                    <span class="weui-form-preview__value">${item.descration}</span>
                                </div>
                            </div>
                            <div class="weui-form-preview__ft">
                                <c:choose>
                                    <c:when test="${item.status == 1}">
                                        <a href="javascript:;" data-id="${item.id}"
                                           class="editOrder weui-form-preview__btn weui-form-preview__btn_default">修改订单</a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="javascript:;" data-id="${item.id}"
                                           class="weui-form-preview__btn weui-form-preview__btn_primary orderConfirm">确认收货</a>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </li>
                </c:forEach>
            </ul>
            <div id="loadingToast" style="display:none;">
                <div class="weui-mask_transparent"></div>
                <div class="weui-toast">
                    <i class="weui-loading weui-icon_toast"></i>
                    <p class="weui-toast__content" id="check"></p>
                </div>
            </div>
            <div id="dialogs">
                <div class="js_dialog" id="confirmDialog" style="display: none;">
                    <div class="weui-mask"></div>
                    <div class="weui-dialog">
                        <div class="weui-dialog__hd">
                            <div class="weui-dialog__title">确认收货</div>
                        </div>
                        <div class="weui-dialog__bd">是否要确认收货？</div>
                        <div class="weui-dialog__ft">
                            <a href="javascript:;" class="weui-dialog__btn weui-dialog__btn_default">取消</a>
                            <a href="javascript:;" class="weui-dialog__btn weui-dialog__btn_primary"
                               id="confirmBtn">确认</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>


    </div>
    <script type="text/javascript">
        $(function () {
            function loadingToast(msg) {
                $loadingToast = $("#loadingToast");
                $("#check").text(msg)
                if ($loadingToast.css('display') != 'none') return;
                $loadingToast.fadeIn(100);
                setTimeout(function () {
                    $loadingToast.fadeOut(200);
                }, 1000);
            }

            // 页数
            var page = 1;
            var openid = getCookie("openid");
            $('.page').dropload({
                scrollArea: window,
                autoLoad: false,
                threshold: 50,
                loadDownFn: function (me) {
                    page++;
                    var result = '';
                    $.ajax({
                        type: 'GET',
                        url: '/WxPost/order/selfpagejson/' + page + '?openid=' + openid,
                        dataType: 'json',
                        success: function (data) {
                            var list = JSON.parse(data).obj.list;
                            var arrLen = list.length;
                            if (arrLen > 0) {
                                for (var i = 0; i < arrLen; i++) {
                                    var choose;
                                    var userd = '';
                                    if (list[i].status == 1) {
                                        choose = '<a href="javascript:;" data-id="' + list[i].id + '" class="editOrder weui-form-preview__btn weui-form-preview__btn_default">修改订单</a>';
                                    } else {
                                        choose = '<a href="javascript:;" data-id="' + list[i].id + '" class="weui-form-preview__btn weui-form-preview__btn_primary orderConfirm">确认收货</a>';
                                        userd += '<div class="weui-form-preview__item">'
                                            + '	<label class="weui-form-preview__label">取货人</label>'
                                            + '	<span class="weui-form-preview__value">' + list[i].username + '</span>'
                                            + '</div>'
                                            + '<div class="weui-form-preview__item">'
                                            + '	<label class="weui-form-preview__label">联系方式</label>'
                                            + '	<span class="weui-form-preview__value"><a href="tel:' + list[i].phoneNum + '">' + list[i].phoneNum + '</a></span>'
                                            + '</div> '
                                    }
                                    result += '<li class="xb-pdTop">'
                                        + '<div class="weui-form-preview">'
                                        + '<div class="weui-form-preview__hd">'
                                        + '		<div class="weui-form-preview__item">'
                                        + '			<label class="weui-form-preview__label">订单名称</label>'
                                        + '			<em class="weui-form-preview__value">' + list[i].name + '</em>'
                                        + '		</div>'
                                        + '	</div>'
                                        + '	<div class="weui-form-preview__bd">'
                                        + '		<div class="weui-form-preview__item">'
                                        + '			<label class="weui-form-preview__label">发布时间</label>'
                                        + '			<span class="weui-form-preview__value">' + list[i].showTime + '</span>'
                                        + '		</div>'
                                        + '		<div class="weui-form-preview__item">'
                                        + '		    <label class="weui-form-preview__label">取货码</label>'
                                        + '			<span class="weui-form-preview__value">' + list[i].receiveNum + '</span>'
                                        + '		</div>'
                                        + '		<div class="weui-form-preview__item">'
                                        + '			<label class="weui-form-preview__label">取货地址</label>'
                                        + '			<span class="weui-form-preview__value">' + list[i].getAddress + '</span>'
                                        + '		</div>'
                                        + userd
                                        + '		<div class="weui-form-preview__item">'
                                        + '			<label class="weui-form-preview__label">收货地址</label>'
                                        + '			<span class="weui-form-preview__value">' + list[i].receiveAddress + '</span>'
                                        + '		</div>'
                                        + '		<div class="weui-form-preview__item">'
                                        + '			<label class="weui-form-preview__label">订单价值</label>'
                                        + '			<span class="weui-form-preview__value">' + list[i].size + '元</span>'
                                        + '		</div>'
                                        + '		<div class="weui-form-preview__item">'
                                        + '			<label class="weui-form-preview__label">备注</label>'
                                        + '			<span class="weui-form-preview__value">' + list[i].descration + '</span>'
                                        + '		</div>'
                                        + '	</div>'
                                        + '	<div class="weui-form-preview__ft">'
                                        + choose
                                        + '	</div>'
                                        + '</div>'
                                        + '</li>'
                                }
                            } else {
                                me.lock('up');
                                me.noData();
                            }
                            $('.lists').append(result);
                            me.resetload();
                        },
                        error: function (xhr, type) {
                            me.resetload();
                        }
                    });
                }
            });
            var $confirmDialog = $('#confirmDialog');
            $('#dialogs').on('click', '.weui-dialog__btn', function () {
                $(this).parents('.js_dialog').fadeOut(200);
            });
            $('.content').on('click', '.orderConfirm', function () {
                $confirmDialog.fadeIn(200);
                $('#confirmBtn').data("id", $(this).data("id"));
            });
            $('#confirmDialog').on('click', '#confirmBtn', function () {
                $(this).parents('.js_dialog').fadeOut(200);
                var id = $(this).data("id");
                $.ajax({
                    type: 'post',
                    url: '/WxPost/pay/payfor/' + id,
                    dataType: 'json',
                    success: function (res) {
                        console.log(res);
                        var data = JSON.parse(res);
                        if (data.result_code == "SUCCESS") {
                            $.ajax({
                                type: 'post',
                                url: '/WxPost/order/receivething',
                                dataType: 'json',
                                data: {'id': id},
                                success: function (res) {
                                    var data = JSON.parse(res);
                                    if (data.msg == "ok") {
                                        loadingToast("收货成功!");
                                        window.location.href = "/WxPost/order/selfpage/1?openid=" + openid;
                                    } else {
                                        loadingToast("收货失败!");
                                    }
                                }
                            });
                        } else {
                            loadingToast("收货失败!");
                        }
                    }
                });
            });
            $('.lists').on("click", '.editOrder', function () {
                var id = $(this).data("id");
                $.ajax({
                    url: '/WxPost/order/get',
                    type: 'POST',
                    dataType: 'json',
                    data: {"id": id},
                    success: function (res) {
                        var order = JSON.parse(res).obj;
                        if (order.status == 1) {
                            window.location.href = "/WxPost/order/get/" + id;
                        } else if (order.status == 2) {
                            loadingToast("订单已被接收，无法修改！");
                        } else {
                            loadingToast("订单已过期，无法修改！");
                        }
                    }
                });
            });

        });

</script>
</script>

<
script
src = "assert/zepto.min.js" ></script>
<script src="assert/example.js"></script>
<script src="assert/dropload.min.js"></script>
<script src="assert/cookieutil.js"></script>
</body>

</html>