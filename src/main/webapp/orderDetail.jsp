<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
		<title>快递镖局</title>
		<link rel="stylesheet" href="<%=basePath%>./assert/style/weui.css" />
		<link rel="stylesheet" href="<%=basePath%>./assert/example.css" />
	</head>

	<body ontouchstart>
		<div class="weui-toptips weui-toptips_warn js_tooltips">错误提示</div>

		<div class="container" id="container"></div>

		<script type="text/html" id="tpl_home">
			<div class="page">
				<div class="page__hd xb-orderBg">
					<div class="xb-order">
						<a class="xb-order-btn" id="goback"><i class="weui-icon-back"></i></a>
						<h2>订单详情</h2>
						<a class="xb-order-btn-right" id="updateOrder">保存</a>
					</div>
				</div>
				<div class="page__bd page__bd_spacing">
					<ul>
						<li>
						  <form>
							<input type="hidden" value="${result.obj.id}" />
							<div class="weui-cells weui-cells_form">
								<div class="weui-cell" style="margin-top:10px;">
									<div class="wui-cell__hd">
										<label class="weui-label">快递取货码</label>
									</div>
									<div class="weui-cell__bd">
										<input type="number" name="receiveNum" value="${result.obj.receiveNum}" class="weui-input" pattern="[0-9]*" placeholder="6位取货码" />
									</div>
								</div>
								<div class="weui-cell">
									<div class="wui-cell__hd">
										<label class="weui-label">订单名称</label>
									</div>
									<div class="weui-cell__bd">
										<input type="text" value="${result.obj.name}" name="name" class="weui-input" placeholder="衣服、鞋子、其他" />
									</div>
								</div>
								<div class="weui-cell">
									<div class="wui-cell__hd">
										<label class="weui-label">收货地址</label>
									</div>
									<div class="weui-cell__bd">
										<input type="text" value="${result.obj.receiveAddress}" name="receiveAddress" class="weui-input" placeholder="尽可能详细" />
									</div>
								</div>
								<div class="weui-cell weui-cell_select">
									<div class="weui-cell__hd">
										<label for="" class="weui-label">取货地址</label>
									</div>
									<div class="weui-cell__bd">
										<select class="weui-select" value="${result.obj.getAddress}" name="getAddress">
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
										<select class="weui-select" value="${result.obj.size}" name="size" disabled>
											<option  value="3">中(3元)</option>
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
										<select class="weui-select" value="${result.obj.hurry}" name="hurry" disabled>
											<option value="true">是(1元)</option>
											<option value="false">否</option>
										</select>
									</div>
								</div>
								<div class="weui-cell">
									<div class="weui-cell__hd"  style="width:15%;">
										<label for="" class="weui-label">备注</label>
									</div>
									<div class="weui-cell__bd">
										<textarea type="text"  value="${result.obj.descration}" data-desc="${result.obj.descration}" name ="descration" class="weui-textarea" rows="3" placeholder="根据您的快递类型提出相应的要求，例如易损坏等，该项为选填!"></textarea>
									</div>
								</div>
							</div>
							</form>
						</li>
					</ul>
				</div>
				<div class="page__ft  xb-detail">
					<a  id="deleteOrder" class="weui-btn weui-btn_warn">取消发布并退款</a>
				</div>
			</div>
			<div class="js_dialog" id="iosDialog1" style="display: none;">
            <div class="weui-mask"></div>
            <div class="weui-dialog">
                <div class="weui-dialog__hd"><strong class="weui-dialog__title">取消订单</strong></div>
                <div class="weui-dialog__bd">你是否要取消订单？</div>
                <div class="weui-dialog__ft">
                    <a href="javascript:;" class="weui-dialog__btn weui-dialog__btn_default">返回</a>
                    <a href="javascript:;" class="weui-dialog__btn weui-dialog__btn_primary">确认</a>
                </div>
            </div>
        </div>
			<div id="loadingToast" style="display:none;">
				<div class="weui-mask_transparent"></div>
				<div class="weui-toast">
					<i class="weui-loading weui-icon_toast"></i>
					<p class="weui-toast__content" id="check"></p>
				</div>
			</div>
			<script type="text/javascript">
				$(function(){
					var openid  = getCookie('openid');
					$('textarea').html($('textarea').data('desc'));
					$("select[name='getAddress'] option[value='${result.obj.getAddress}']").attr("selected",true); 
					$("select[name='size'] option[value='${result.obj.size}']").attr("selected",true); 
					$("select[name='hurry'] option[value='${result.obj.hurry}']").attr("selected",true);			
					$('#goback').attr('href','/WxPost/order/selfpage/1?openid='+openid);
					function loadingToast(msg){
						$loadingToast = $("#loadingToast");
						$("#check").text(msg)
						if($loadingToast.css('display') != 'none') return;
						$loadingToast.fadeIn(100);
							setTimeout(function() {
							$loadingToast.fadeOut(200);
						}, 1000);
					}
					var $iosDialog1 = $('#iosDialog1');
					$('#deleteOrder').on('click',function(){$iosDialog1.fadeIn(200);}); 
					$('.weui-dialog__btn_default').click(function(){ $(this).parents('.js_dialog').fadeOut(200);});
					$('.weui-dialog__btn_primary').click(function(){
						$(this).parents('.js_dialog').fadeOut(200);
						$.ajax({
							url:"/WxPost/pay/refund/${result.obj.id}",							
							type:"post",
							dataType:"json",
							success:function(res){
								var data = JSON.parse(res);
								if(data.return_msg =="OK"){
									console.log("退款成功!");
									$.ajax({
										url:"/WxPost/order/delete/${result.obj.id}",							
										type:"post",
										dataType:"json",
										success:function(res){
										console.log(res);
										var data = JSON.parse(res);
											if(data.msg == "ok"){
											window.location.href=window.location.href="/WxPost/order/selfpage/1?openid="+openid;
											loadingToast("退款成功,删除订单成功!");
											}else{
											loadingToast("退款成功,删除订单失败!");
										}
										}
									});
								}else{
									loadingToast("未知错误,退款未成功,请咨询客服!");
								}				
							}
						});
					});

					$("#updateOrder").on('click',function(){
						if($("input[name='receiveNum']").val() == ""){
							loadingToast("取货码不能为空")
						}else if($("input[name='receiveAddress']").val() == ""){
							loadingToast("收货地址不能为空!")
						}else if(($("input[name='receiveNum']").val().length !=6) && ($("input[name='receiveNum']").val().length !=4) ){
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
						data["id"] = ${result.obj.id};
						var order = JSON.stringify(data);
						console.log(data);
						$.ajax({
  									type: 'POST',
  									url: '/WxPost/order/update',
									contentType: 'application/x-www-form-urlencoded',
									dataType:'json',
  									data:data,
									success:function(res){
										console.log(res)
										var data = JSON.parse(res);
										if(data.msg=="ok"){
											loadingToast("修改订单成功！")
											window.location.href="/WxPost/order/selfpage/1?openid="+openid;
										}else{
											loadingToast("修改订单失败！");
										}
									}
							});
						}
					});
				});
			</script>
		</script>
		
		<script src="<%=basePath%>./assert/zepto.min.js"></script>
		<script src="<%=basePath%>./assert/example.js"></script>
		<script src="<%=basePath %>assert/cookieutil.js"></script>
	</body>

</html>