<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">

	<head>
	<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
		<meta charset="UTF-8">
		<meta name="full-screen" content="yes">
<meta name="x5-fullscreen" content="true">
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
		<title>快递镖局</title>
		<link rel="stylesheet" href="<%=basePath %>./assert/style/weui.css" />
		<link rel="stylesheet" href="<%=basePath %>./assert/example.css" />
		<link rel="stylesheet" href="<%=basePath %>assert/dropload.css" />
	</head>
	

	<body ontouchstart>
		<div class="weui-toptips weui-toptips_warn js_tooltips">错误提示</div>
		<div class="container" id="container"></div>
		<script type="text/html" id="tpl_home">
			<div class="page">
				<div class="page__hd xb-orderBg">
					<div class="xb-order">
						<a class="xb-order-btn" href="<%=basePath %>/historyOrder.jsp"><i class="weui-icon-back"></i></a>
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
							</div>
						</li>
						</c:forEach>
					</ul>
				</div>
				<div class="page__ft">
				</div>
			</div>
			<script type="text/javascript">
				$(function(){
					
					// 页数
					var page = 1;
					var openid = getCookie("openid");
					$('.content').dropload({
						scrollArea : window,
						autoLoad:false,
						threshold : 50,
			loadDownFn : function(me){
			page++;
            var result = '';
            $.ajax({
            	type: 'GET',
            	url: '/WxPost/history/orderjson/'+page+'?openid='+openid,
            	dataType: 'json',
            	success: function(data){
					var list  = JSON.parse(data).obj.list;
            		var arrLen = list.length;
            		if(arrLen > 0){
            			for(var i=0; i<arrLen; i++){
            				result +=  '<li class="xb-pdTop">'
							+'<div class="weui-form-preview">'
							+'<div class="weui-form-preview__hd">'
							+'		<div class="weui-form-preview__item">'
							+'			<label class="weui-form-preview__label">订单名称</label>'
							+'			<em class="weui-form-preview__value">'+list[i].name+'</em>'
							+'		</div>'
							+'	</div>'
							+'	<div class="weui-form-preview__bd">'
							+'		<div class="weui-form-preview__item">'
							+'			<label class="weui-form-preview__label">发布时间</label>'
							+'			<span class="weui-form-preview__value">'+list[i].showTime+'</span>'
							+'		</div>'
							+'		<div class="weui-form-preview__item">'
							+'		    <label class="weui-form-preview__label">取货码</label>'
							+'			<span class="weui-form-preview__value">'+list[i].receiveNum+'</span>'
							+'		</div>'
							+'		<div class="weui-form-preview__item">'
							+'			<label class="weui-form-preview__label">取货地址</label>'
							+'			<span class="weui-form-preview__value">'+list[i].getAddress+'</span>'
							+'		</div>'
							+'		<div class="weui-form-preview__item">'
							+'			<label class="weui-form-preview__label">收货地址</label>'
							+'			<span class="weui-form-preview__value">'+list[i].receiveAddress+'</span>'
							+'		</div>'
							+'		<div class="weui-form-preview__item">'
							+'			<label class="weui-form-preview__label">订单价值</label>'
							+'			<span class="weui-form-preview__value">'+list[i].size+'元</span>'
							+'		</div>'
							+'		<div class="weui-form-preview__item">'
							+'			<label class="weui-form-preview__label">备注</label>'
							+'			<span class="weui-form-preview__value">'+list[i].descration+'</span>'
							+'		</div>'
							+'	</div>'
							+'</div>'
						+'</li>' 
            			}
                }else{
                        me.lock('up');
                        me.noData();
                    }
                    $('.lists').append(result);
                    me.resetload();
                },
                error: function(xhr, type){
                    me.resetload();
                }
            });
        }
    });
				});

			</script>
		</script>
		
		<script src="<%=basePath %>./assert/zepto.min.js"></script>
		<script src="<%=basePath %>./assert/example.js"></script>
		<script src="<%=basePath %>assert/dropload.min.js"></script>
		<script src="<%=basePath %>assert/cookieutil.js"></script>
	</body>

</html>