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
                <a class="xb-order-btn" href="<%=basePath%>/WEB-INF/jsp/index.jspsp/index.jsp"><i
                        class="weui-icon-back"></i></a>
                <h2>接取订单</h2>
            </div>
        </div>
        <div class="page__bd page__bd_spacing">
            <ul class="lists">
                <c:forEach items="${result.obj.list}" var="item">
                    <li class="xb-pdTop">
                        <div class="weui-form-preview">
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
                                        <label class="weui-form-preview__label">取货地址</label>
                                        <span class="weui-form-preview__value">${item.getAddress}</span>
                                    </div>
                                    <div class="weui-form-preview__item">
                                        <label class="weui-form-preview__label">收货地址</label>
                                        <span class="weui-form-preview__value">${item.receiveAddress}</span>
                                    </div>
                                    <div class="weui-form-preview__item">
                                        <label class="weui-form-preview__label">订单价值</label>
                                        <span class="weui-form-preview__value">${item.size}元</span>
                                    </div>
                                    <div class="weui-form-preview__item">
                                        <label class="weui-form-preview__label">是否加急</label>
                                        <span class="weui-form-preview__value"><font style="color:red;">
										<c:choose>
                                            <c:when test="${item.hurry == true}">是</c:when>
                                            <c:otherwise>否</c:otherwise>
                                        </c:choose>
										</font>
									</span>
                                    </div>
                                    <div class="weui-form-preview__item">
                                        <label class="weui-form-preview__label">备注</label>
                                        <span class="weui-form-preview__value">${item.descration}</span>
                                    </div>
                                </div>
                                <div class="weui-form-preview__ft">
                                    <a href="javascript:;" data-id="${item.id}" data-pnum=""
                                       class="weui-form-preview__btn weui-form-preview__btn_primary orderConfirm">接单</a>
                                </div>
                            </div>
                    </li>
                </c:forEach>
            </ul>
            <div id="toast" style="opacity: 0;display: none;">
                <div class="weui-mask_transparent"></div>
                <div class="weui-toast">
                    <i class="weui-icon-success-no-circle weui-icon_toast"></i>
                    <p class="weui-toast__content" id="result"></p>
                </div>
            </div>
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
                            <div class="weui-dialog__title">接单</div>
                        </div>
                        <div class="weui-dialog__bd">亲，你现在是确定要接单了嘛，想清楚哦</div>
                        <div class="weui-dialog__ft">
                            <a href="javascript:;" class="weui-dialog__btn weui-dialog__btn_default">取消</a>
                            <a class="weui-dialog__btn weui-dialog__btn_primary">确认</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        $(function () {
            //Bmob.initialize("297342c49eceb9acee09a8f679cbe358", "764c4df56531ceb676144109958e3578");
            var page = 1;
            var openid = getCookie("openid");

            function toast(msg) {
                $toast = $("#toast")
                $("#result").text(msg)
                if ($toast.css('display') != 'none') return;
                $toast.fadeIn(100);
                setTimeout(function () {
                    $toast.fadeOut(200);
                }, 1000);
            }

            function loadingToast(msg) {
                $loadingToast = $("#loadingToast");
                $("#check").text(msg)
                if ($loadingToast.css('display') != 'none') return;
                $loadingToast.fadeIn(100);
                setTimeout(function () {
                    $loadingToast.fadeOut(200);
                }, 1000);
            }

            var $confirmDialog = $('#confirmDialog');
            $('#dialogs').on('click', '.weui-dialog__btn', function () {
                $(this).parents('.js_dialog').fadeOut(200);
            });
            $('.page').dropload({
                scrollArea: window,
                autoLoad: true,
                threshold: 50,
                loadDownFn: function (me) {
                    page++;
                    var result = '';
                    $.ajax({
                        type: 'GET',
                        url: '/WxPost/order/pagejson/' + page,
                        dataType: 'json',
                        success: function (data) {
                            var list = JSON.parse(data).obj.list;
                            var arrLen = list.length;
                            if (arrLen > 0) {
                                for (var i = 0; i < arrLen; i++) {
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
                                        + '			<label class="weui-form-preview__label">取货地址</label>'
                                        + '			<span class="weui-form-preview__value">' + list[i].getAddress + '</span>'
                                        + '		</div>'
                                        + '		<div class="weui-form-preview__item">'
                                        + '			<label class="weui-form-preview__label">收货地址</label>'
                                        + '			<span class="weui-form-preview__value">' + list[i].receiveAddress + '</span>'
                                        + '		</div>'
                                        + '		<div class="weui-form-preview__item">'
                                        + '			<label class="weui-form-preview__label">订单价值</label>'
                                        + '			<span class="weui-form-preview__value">' + list[i].size + '元</span>'
                                        + '		</div>'
                                        + '		<div class="weui-form-preview__item">'
                                        + '			<label class="weui-form-preview__label">是否加急</label>'
                                        + '			<span class="weui-form-preview__value"><font style="color:red;">' + (list[i].hurry == true ? '是' : '否') + '</font></span>'
                                        + '		</div>'
                                        + '		<div class="weui-form-preview__item">'
                                        + '			<label class="weui-form-preview__label">备注</label>'
                                        + '			<span class="weui-form-preview__value">' + list[i].descration + '</span>'
                                        + '		</div>'
                                        + '	</div>'
                                        + '	<div class="weui-form-preview__ft">'
                                        + '		<a href="javascript:;" data-id="' + list[i].id + '" class="weui-form-preview__btn weui-form-preview__btn_primary orderConfirm">接单</a>'
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
                            alert('Ajax error!');
                            me.resetload();
                        }
                    });
                }
            });
            $('.lists').on('click', '.orderConfirm', function () {
                $confirmDialog.fadeIn(200);
                $('.weui-dialog__btn_primary').data("id", $(this).data("id"));
            });
            $('#dialogs').on('click', '.weui-dialog__btn_primary', function () {
                $(this).parents('.js_dialog').fadeOut(200);
                var id = $(this).data("id");
                var receiveOrder = {"orderId": id, "openid": openid};
                $.ajax({
                    type: 'POST',
                    url: '/WxPost/receiveorder/insert',
                    contentType: 'application/x-www-form-urlencoded',
                    dataType: 'json',
                    data: receiveOrder,
                    success: function (res) {
                        var data = JSON.parse(res);
                        if (data.msg == "ok") {

                            /*
                                                            $.ajax({
                                                                url:'/WxPost/order/phonenum',
                                                                type:'POST',
                                                                data:{'orderid':id},
                                                                dataType:'json',
                                                                success:function(res){
                                                                    var data = JSON.parse(res);
                                                                    console.log(data);
                                                                    window.location.href = "/WxPost/order/page/1";
                                                                Bmob.Sms.requestSmsCode({"mobilePhoneNumber": data.obj, "template":"模板三"} ).then(function(obj) {
                                                                         console.log("发送成功!");
                                                                        toast("接单成功!");
                                                                        window.location.href = "/WxPost/order/page/1"
                                                                     }, function(err){
                                                                         console.log("发送失败!");
                                                                        toast("接单成功!");
                                                            window.location.href = "/WxPost/order/page/1"
                                                                     });
                                                                }
                                                            });*/
                            window.location.href = "/WxPost/order/page/1";
                        } else if (data.msg == "2") {
                            loadingToast("用户未通过校验接单失败!");
                        } else if (data.msg == "3") {
                            loadingToast("信誉积分过低,接单失败!");
                        } else if (data.msg == "4") {
                            loadingToast("下手慢啦,接单失败!");
                        } else if (data.msg == "5") {
                            loadingToast("请完善用户信息,接单失败!");
                        } else if (data.msg == "err") {
                            loadingToast("未知错误,接单失败!");
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
<script src="assert/cookieutil.js"></script>
<script src="assert/dropload.min.js"></script>
<%-- <script src="<%=basePath %>assert/bmob-min.js"></script> --%>
</body>

</html>