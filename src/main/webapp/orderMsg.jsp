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
			<div class="page">
				<div class="weui-msg">
					<div class="weui-msg__icon-area"><i class="weui-icon-success weui-icon_msg"></i></div>
					<div class="weui-msg__text-area">
						<h2 class="weui-msg__title">订单取消成功</h2>
					</div>
					<div class="weui-msg__opr-area">
						<p class="weui-btn-area">
							<a href="javascript:;" class="weui-btn weui-btn_primary" id="showIOSDialog1">重新发布</a>
							<a href="javascript:;" class="weui-btn weui-btn_warn" id="showIOSDialog2">删除订单</a>
							<a href="orderList.jsp" class="weui-btn weui-btn_default">我发布的订单</a>
						</p>
					</div>
					<div class="weui-msg__extra-area">
						<div class="weui-footer">
							<p class="weui-footer__links">
								<!--技术支持<a href="http://ecjtu.net/" class="weui-footer__link">日新工作室</a>-->
							</p>
							<p class="weui-footer__text">Copyright &copy; 2017</p>
						</div>
					</div>
					<div id="dialogs">
						<div class="js_dialog" id="iosDialog1" style="opacity: 0;display: none;">
							<div class="weui-mask"></div>
							<div class="weui-dialog">
								<div class="weui-dialog__hd">
									<div class="weui-dialog__title">重新发布</div>
								</div>
								<div class="weui-dialog__bd">刚刚一定是脑袋短路了，不然怎么会搞这个</div>
								<div class="weui-dialog__ft">
									<a href="javascript:;" class="weui-dialog__btn weui-dialog__btn_default">取消</a>
									<a href="orderList.jsp" class="weui-dialog__btn weui-dialog__btn_primary">确认</a>
								</div>
							</div>
						</div>
						<div class="js_dialog" id="iosDialog2" style="opacity: 0;display: none;">
							<div class="weui-mask"></div>
							<div class="weui-dialog">
								<div class="weui-dialog__hd">
									<div class="weui-dialog__title">删除订单</div>
								</div>
								<div class="weui-dialog__bd">点击确定你就把我删了我，这么残忍嘛</div>
								<div class="weui-dialog__ft">
									<a href="javascript:;" class="weui-dialog__btn weui-dialog__btn_default">取消</a>
									<a href="orderList.jsp" class="weui-dialog__btn weui-dialog__btn_primary" >确认</a>
								</div>
							</div>
						</div>
						<!--暂时没做这个-->
						<!--<div id="toast" style="opacity: 0;display: none;">
							<div class="weui-mask_transparent"></div>
							<div class="weui-toast">
								<i class="weui-icon-success-no-circle weui-icon_toast"></i>
								<p class="weui-toast__content">已完成</p>
							</div>
						</div>-->
					</div>
				</div>
			</div>
			<script type="text/javascript">
				$(function(){
					var $iosDialog1=$('#iosDialog1'),
						$iosDialog2=$('#iosDialog2');
					var $toast=$('#toast');
					
					$('#dialogs').on('click','.weui-dialog__btn',function(){
						$(this).parents('.js_dialog').fadeOut(200);
					});
					
					$('#showIOSDialog1').on('click',function(){
						$iosDialog1.fadeIn(200);
					});
					
					$('#showIOSDialog2').on('click',function(){
						$iosDialog2.fadeIn(200);
					});
					
					$('.js_item').on('click', function() {
						var id = $(this).data('id');
						window.pageManager.go(id);
					});
				});
			</script>
		</script>
		
		<script src="./assert/zepto.min.js"></script>
		<script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		<script src="https://res.wx.qq.com/open/libs/weuijs/1.0.0/weui.min.js"></script>
		<script src="./assert/example.js"></script>
	</body>

</html>