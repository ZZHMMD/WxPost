<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
		<title>快递镖局</title>
		<link rel="stylesheet" href="./assert/style/weui.css" />
		<link rel="stylesheet" href="./assert/example.css" />
	</head>

	<body ontouchstart>
		<div class="weui-toptips weui-toptips_warn js_tooltips">错误提示</div>
		<div class="container" id="container"></div>
		<script type="text/html" id="tpl_home">
            <input type="hidden" id="detail" data-noncestr="${nonceStr}" data-appid="${appid}" data-timestamp="${timestamp}" data-signature="${signature}" />
			<div class="page">
				<div class="page__hd xb-bgColor">
					<div class="xb-user"  data-id="${user.openid}">
			        	<div class="xb-user-img">
			            	<img src="${user.headimgurl}" alt="">
			            </div>
			            <h3>${user.nickname}</h3>
			        </div>
				</div>
				<div class="page__bd page__bd_spacing xb-pdTop">
					<ul>
						<li>
							<div class="weui-cells">
								<a class="weui-cell weui-cell_access" id="receiveOrderList">
									<div class="weui-cell__bd">我接取的订单</div>
									<div class="weui-cell__ft"></div>
								</a>
								<a class="weui-cell weui-cell_access" id="orderList">
									<div class="weui-cell__bd">我发布的订单</div>
									<div class="weui-cell__ft"></div>
								</a>
								<a class="weui-cell weui-cell_access" id="historyOrder" href="historyOrder.jsp">
									<div class="weui-cell__bd">已完成订单</div>
									<div class="weui-cell__ft"></div>
								</a>
							</div>
						</li>
						<li>
							<div class="weui-cells">
								<a class="weui-cell weui-cell_access" href="set.jsp">
									<div class="weui-cell__bd">设置</div>
								</a>
							</div>
						</li>
					</ul>
				</div>
				<div class="page__ft j_bottom">
					<div class="weui-tabbar">
						<a  id="getOrder" class="weui-tabbar__item xb-tabbar-item">
							<p class="weui-tabbar__label xb-tabbar-text">接单</p>
						</a>
						<a id="sendOrder" class="weui-tabbar__item xb-tabbar-item" >
							<p class="weui-tabbar__label xb-tabbar-text" >发单</p>
						</a>
						<a href="index.jsp" class="weui-tabbar__item xb-tabbar-item weui-bar__item_on">
							<p class="weui-tabbar__label xb-tabbar-text">个人中心</p>
						</a>
					</div>
				</div>
			</div>
			<script type="text/javascript" class="tabbar js_show">
				$(function() {
					var flag = getCookie('openid')
					if(flag == ""){
						setCookie('openid',$('.xb-user').data("id"),30);
						setCookie('headimgurl',$('.xb-user img').attr("src"),30);
						setCookie('nickname',$('.xb-user h3').html(),30);
					}else{
						$('.xb-user img').attr("src",getCookie('headimgurl'));
						$('.xb-user h3').html(getCookie('nickname'));
					}
					var openid = getCookie("openid");
					$.ajax({
						url:"/WxPost/getuser/"+openid,
						dataType:"json",
						type:"GET",
						success:function(res){
							var data = JSON.parse(res);
							if(data.msg =="ok"){
								$("#getOrder").attr("href","/WxPost/order/page/1");
								$("#sendOrder").attr("href","sendOrder.jsp");
							}else{
								$("#getOrder").attr('href',"/WxPost/panduan?openid="+openid);
								$("#sendOrder").attr('href',"/WxPost/panduan?openid="+openid);
							}
						}
					});

					$("#orderList").attr("href","/WxPost/order/selfpage/1?openid="+openid);
					$("#receiveOrderList").attr("href","/WxPost/receiveorder/page/1?openid="+openid);
					var winH = $(window).height();
					var categorySpace = 10;
					$('.js_item').on('click', function() {
						var id = $(this).data('id');
						window.pageManager.go(id);
					});

					$('.js_category').on('click', function() {

						var $this = $(this),
							$inner = $this.next('.js_categoryInner'),
							$page = $this.parents('.page'),
							$parent = $(this).parent('li');
						var innerH = $inner.data('height');
						bear = $page;
						if(!innerH) {
							$inner.css('height', 'auto');
							innerH = $inner.height();
							$inner.removeAttr('style');
							$inner.data('height', innerH);
						}
						if($parent.hasClass('js_show')) {
							$parent.removeClass('js_show');
						} else {
							$parent.siblings().removeClass('js_show');
							$parent.addClass('js_show');
							if(this.offsetTop + this.offsetHeight + innerH > $page.scrollTop() + winH) {
								var scrollTop = this.offsetTop + this.offsetHeight + innerH - winH + categorySpace;
								if(scrollTop > this.offsetTop) {
									scrollTop = this.offsetTop - categorySpace;
								}
								$page.scrollTop(scrollTop);
							}
						}
					});
				});
			</script>
		</script>
		<script src="assert/cookieutil.js"></script>
		<script src="./assert/zepto.min.js"></script>
		<script src="./assert/example.js"></script>
	</body>

</html>