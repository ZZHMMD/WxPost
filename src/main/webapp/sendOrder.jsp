<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">

	<head>
			<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
		<title>快递镖局</title>
		<link rel="stylesheet" href="<%=basePath %>./assert/style/weui.css" />
		<link rel="stylesheet" href="<%=basePath %>./assert/example.css" />
	</head>

	<body ontouchstart>
		<div class="weui-toptips weui-toptips_warn js_tooltips">错误提示</div>
		<div class="container" id="container"></div>
		<script type="text/html" id="tpl_home">
			<div class="page">
				<div class="page__hd xb-orderBg">
					<div class="xb-order">
						<a class="xb-order-btn" href="index.jsp" id="sendBtn"><i class="weui-icon-back"></i></a>
						<h2>发布订单</h2>
					</div>
				</div>
				<div class="page__bd page__bd_spacing">
					<ul>
						<li>
						  <form>
							<div class="weui-cells weui-cells_form">
								<div class="weui-cell" style="margin-top:10px;">
									<div class="wui-cell__hd">
										<label class="weui-label">快递取货码</label>
									</div>
									<div class="weui-cell__bd">
										<input type="number" name="receiveNum" class="weui-input" pattern="[0-9]*" placeholder="4~6位取货码" />
									</div>
								</div>
								<div class="weui-cell">
									<div class="wui-cell__hd">
										<label class="weui-label">订单名称</label>
									</div>
									<div class="weui-cell__bd">
										<input type="text" name="name" class="weui-input" placeholder="衣服、鞋子、其他" />
									</div>
								</div>
								<div class="weui-cell">
									<div class="wui-cell__hd">
										<label class="weui-label">收货地址</label>
									</div>
									<div class="weui-cell__bd">
										<input type="text" name="receiveAddress" class="weui-input" placeholder="尽可能详细" />
									</div>
								</div>
								<div class="weui-cell weui-cell_select">
									<div class="weui-cell__hd">
										<label for="" class="weui-label">取货地址</label>
									</div>
									<div class="weui-cell__bd">
										<select class="weui-select" name="getAddress">
											<option selected="" value="6">北区菜鸟驿站</option>
											<option value="1">南区25栋</option>
											<option value="2">南区17栋</option>
											<option value="3">南区宅35栋</option>
											<option value="4">南区7栋对面书报亭</option>
											<option value="5">南区一食堂旁书报亭</option>
											<option value="7">北区董小龙便利店</option>
											<option value="8">南区校门口</option>
											<option value="9">南区后门邮政</option>
										</select>
									</div>
								</div>
								<div class="weui-cell weui-cell_select">
									<div class="weui-cell__hd">
										<label for="" class="weui-label">快递大小</label>
									</div>
									<div class="weui-cell__bd">
										<select class="weui-select" name="size">
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
										<select class="weui-select" name="hurry">
											<option selected="" value="true">是(1元)</option>
											<option value="false">否</option>
										</select>
									</div>
								</div>
								<div class="weui-cell">
									<div class="weui-cell__hd" style="width:15%;">
										<label for="" class="weui-label">备注</label>
									</div>
									<div class="weui-cell__bd">
										<textarea type="text" name ="descration" class="weui-textarea" rows="3" placeholder="根据您的快递类型提出相应的要求，例如易损坏等，该项为选填!"></textarea>
									</div>
								</div>
							</div>
							</form>
						</li>
					</ul>
				</div>
				<div class="page__ft  xb-detail-fixed">
					<a id="insertOrder" class="weui-btn weui-btn_primary">发布</a>
				</div>
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

			</div>
			<script type="text/javascript">
				$(function(){
					
					
var oHeight = $(document).height(); 
   $(window).resize(function(){
        if($(document).height() < oHeight){
        $(".xb-detail-fixed").css("position","static");
    }else{
        $(".xb-detail-fixed").css("position","absolute");
    }
   });
					var addr =  getCookie("receiveAddress");
					(addr != "")?$("input[name='receiveAddress']").val(addr):"";				
					$.ajax({
						url : "/WxPost/jssdkconfig",
						type : 'post',
						dataType : 'json',
						contentType : "application/x-www-form-urlencoded; charset=utf-8",
						data : { 'url' : location.href.split('#')[0]},
						success : function(res) {
							var data = JSON.parse(res);
							wx.config({
								debug : false,
								appId : data.appid,
								timestamp : data.timestamp,
								nonceStr : data.noncestr,
								signature : data.signature,
								jsApiList : [ 'chooseWXPay']
							}); 
						}
					});
					function toast( msg){
						$toast = $("#toast")
						$("#result").text(msg)
						if($toast.css('display') != 'none') return;
							$toast.fadeIn(100);
							setTimeout(function() {
								$toast.fadeOut(200);
							}, 1000);
					}
					function loadingToast(msg){
						$loadingToast = $("#loadingToast");
						$("#check").text(msg)
						if($loadingToast.css('display') != 'none') return;
						$loadingToast.fadeIn(100);
							setTimeout(function() {
							$loadingToast.fadeOut(200);
						}, 1000);
					}
					$("#insertOrder").on('click',function(){
						if($("input[name='receiveNum']").val() == ""){
							loadingToast("取货码不能为空")
						}else if($("input[name='receiveAddress']").val() == ""){
							loadingToast("收货地址不能为空!")
						}else if($("input[name='receiveNum']").val().length !=6 && $("input[name='receiveNum']").val().length !=4 && $("input[name='receiveNum']").val().length !=5 ){
							loadingToast("请输入正确的取货码!")
						}else{
						var pairs = $('form').serialize().split(/&/gi);
						var data = {};
						pairs.forEach(function(pair) {
							pair = pair.split('=');
							data[pair[0]] = decodeURIComponent(pair[1] || '');
						});
                        var openid = getCookie("openid");
						data["openid"] =openid ;
						var order = JSON.stringify(data);
						setCookie("receiveAddress",$("input[name='receiveAddress']").val(),30);
						
					var price = ($("select[name='hurry']").val() == "true")?100*(Number($("select[name='size']").val())+1):100*(Number($("select[name='size']").val()));
					$.ajax({
						url : "/WxPost/pay/sendorder",
						type : 'get',
						dataType : 'json',
						data : { 'price':price+"",'openid':openid },
						success : function(res) {
							var datar =  JSON.parse(res)
							if(typeof(datar.payid) != "undefined"){
									data["payid"]=datar.payid;
									wx.chooseWXPay({
    									timestamp: datar.timeStamp, 
    									nonceStr: datar.nonceStr,
    									package: 'prepay_id='+datar.package,
    									signType: 'MD5', 
   										paySign:datar.paySign, 
    									success: function (res) {
    										if(res.errMsg=="chooseWXPay:ok"){
    											$.ajax({
    												type: 'POST',
    												url: '/WxPost/order/insert',
    												contentType: 'application/x-www-form-urlencoded',
    												dataType:'json',
    												data:data,
    												success:function(res){
    													var data = JSON.parse(res);
    													if(data.msg=="ok"){
    														toast("发布订单成功！");
    														window.location.href="/WxPost/order/selfpage/1?openid="+openid;
    													}else if(data.msg =="2"){
    														loadingToast("信誉积分过低,发单失败!");
    													}else if(data.msg =="3"){
    														loadingToast("请完善信息,发单失败!");
    													}else{
    														loadingToast("未知错误,发布订单失败!");
    													}
    												}
    											});
											}else{
												loadingToast("付款未成功,发单失败!");
											}
    									}
								});
							}
						}
					});
						}
					}); 
				});
			</script>
		</script>
		<script src="<%=basePath %>assert/cookieutil.js"></script>
		<script src="<%=basePath %>./assert/zepto.min.js"></script>
		<script src="<%=basePath %>./assert/example.js"></script>
		<script src="https://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
	</body>

</html>