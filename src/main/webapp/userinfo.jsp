<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
				<div class="page__hd xb-orderBg">
					<div class="xb-order">
						<a class="xb-order-btn" href="index.jsp"><i class="weui-icon-back"></i></a>
						<h2>编辑资料</h2>
					</div>
				</div>
				<div class="page__bd page__bd_spacing">
					<ul>
						<li>
							<div class="weui-gallery" id="gallery" style="opacity: 0;display: none;">
								<span class="weui-gallery__img" id="galleryImg"  ></span>
								<div class="weui-gallery__opr">
									<a href="javascript:;" class="weui-gallery__del" id="del_student_card">
										<i class="weui-icon-delete weui-icon_gallery-delete"></i>
									</a>
								</div>
							</div>
							<div class="weui-cells weui-cells_form">
								<div class="weui-cell">
									<div class="wui-cell__hd">
										<label class="weui-label">用户名</label>
									</div>
									<div class="weui-cell__bd">
										<input type="text" name="nickname"   class="weui-input" value="${result.obj.name}"/>
									</div>
								</div>
								<div class="weui-cell weui-cell_select">
									<div class="weui-cell__hd">
										<label for="" class="weui-label">学校</label>
									</div>
									<div class="weui-cell__bd">
										<select class="weui-select" id="select1">
											<option selected="" value="华东交通大学">华东交通大学</option>
										</select>
									</div>
								</div>
								<div class="weui-cell">
									<div class="wui-cell__hd">
										<label class="weui-label">手机号</label>
									</div>
									<c:choose>
									<c:when  test="${result.obj.phoneNum ==null}">
									<div class="weui-cell__bd">
										<input type="text" name="tel" class="weui-input" pattern="[0-9]*"/>
									</div>
									</c:when>
									<c:otherwise>
										<div class="weui-cell__bd">
										<input type="text" name="tel" class="weui-input" value="${result.obj.phoneNum}" readonly="readonly" />
									</div>
									</c:otherwise>
									</c:choose>
									<c:if test="${result.obj.phoneNum ==null}">
									<div class="weui-cell__ft" id="code">
										<button id="getCode" data-flag="first" class="weui-vcode-btn" dsiabled="disabled" >获取验证码</button>
									</div>
									</c:if>
								</div>
								<c:if test="${result.obj.phoneNum ==null}">
								<div class="weui-cell">
									<div class="wui-cell__hd">
										<label class="weui-label">验证码</label>
									</div>
									<div class="weui-cell__bd">
										<input type="text" class="weui-input" name="code" pattern="[0-9]*" />
									</div>
								</div>
								</c:if>
								<div class="weui-cell weui-cell_select">
									<div class="wui-cell__hd">
										<label class="weui-label">学院</label>
									</div>
									<div class="weui-cell__bd">
										<select class="weui-select" name="college"   value="${result.obj.college}">
											<option selected=""  value="软件学院">软件学院</option>
											<option  value="土木建筑学院">土木建筑学院</option>
											<option  value="电气与自动化工程学院">电气与自动化工程学院</option>
											<option  value="机电与车辆工程学院">机电与车辆工程学院</option>
											<option  value="经济管理学院">经济管理学院</option>
											<option  value="体育学院">体育学院</option>
											<option  value="信息学院">信息学院</option>
											<option  value="人文学院">人文学院</option>
											<option  value="理学院">理学院</option>
											<option  value="外语学院">外语学院</option>
											<option  value="艺术学院">艺术学院</option>
											<option  value="国际学院">国际学院</option>
											<option  value="交通运输与物流学院">交通运输与物流学院</option>
											<option  value="马克思主义学院">马克思主义学院</option>
											<option  value="材料科学与工程学院">材料科学与工程学院</option>
											<option  value="国防生学院">国防生学院</option>
											<option  value="轨道交通职业技术学院">轨道交通职业技术学院</option>
											<option  value="继续教育与职业技术培训学院">继续教育与职业技术培训学院</option>
										</select>
									</div>
								</div>
								<div class="weui-cell">
									<div class="wui-cell__hd">
										<label class="weui-label">学号</label>
									</div>
									<div class="weui-cell__bd">
										<input type="text" name="student_card_num" value="${result.obj.studentCardNum}" class="weui-input"  pattern="[0-9]*" />
									</div>
								</div>
								<div class="weui-cell">
									<div class="weui-cell__hd">
										<label for="" class="weui-label">宿舍地址</label>
									</div>
									<div class="weui-cell__bd">
										<input type="text" name="location" class="weui-input" value="${result.obj.location}" />
									</div>
								</div>
								<div class="weui-cell">
									<div class="wui-cell__hd">
										<label class="weui-label">学生证</label>
									</div>
									<div class="weui-cell__bd">
										<div class="weui-uploader">
											<div class="weui-uploader__bd">
												<ul class="weui-uploader__file" id="uploaderFiles">
 													    <li class="weui-uploader__file" style="background-image:url(assert/images/pic_160.png)" id="imgli" >
 													    	<c:choose>
 													    		<c:when test="${result.obj.studentCard !=null}" >
 													    			<img data-flag="unchange"  style="width:100%;height:100%; z-index:999;" id="stu_img" src="/WxPost/images/${result.obj.studentCard}"/>
 													    		</c:when>
 													    		<c:otherwise>
 													    			<img data-flag="unchange"  style="width:100%;height:100%; z-index:999;" id="stu_img" src="assert/images/pic_160.png"/>
 													    		</c:otherwise>
 													    	</c:choose>
														</li>
												</ul>
												<div class="weui-uploader__input-box">
													<input type="hidden" name="studentCard" value="${result.obj.studentCard}" />
													<input type="button" class="weui-uploader__input" id="uploaderInput"  />
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</li>
					</ul>
				</div>
				<div class="page__ft  xb-detail-fixed">
					<a href="javascript:;" class="weui-btn weui-btn_primary" id="showToast">保存</a>
				</div>
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
				$(function() {

var oHeight = $(document).height(); 
   $(window).resize(function(){
        if($(document).height() < oHeight){
        $(".xb-detail-fixed").css("position","static");
    }else{
        $(".xb-detail-fixed").css("position","absolute");
    }
   });
					
					$("select[name='college'] option[value='${result.obj.college}'] ").attr("selected",true);
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
								jsApiList : [ 'chooseImage','uploadImage']
							}); 
						}
					});
					var wait = 60;
					var timeout;
					function time(){
						if(wait == 1){
							wait=60;
							$("#getCode").text("获取验证码");
							$("#getCode").data("flag","first");
							clearTimeout(timeout);
						}else{
							wait --;
							$("#getCode").text(wait);
							$("#getCode").data("flag","");
							timeout =setTimeout(function(){
								time();
							},1000);
						}
					}
                    Bmob.initialize("297342c49eceb9acee09a8f679cbe358", "764c4df56531ceb676144109958e3578");
                    $("#getCode").on('click',function(){
                    	var phoneNum = $("input[name='tel']").val();
						var flag = $(this).data("flag");
						if(flag == "first"){
							$(this).text(60);
							time();
							Bmob.Sms.requestSmsCode({"mobilePhoneNumber": phoneNum, "template":"模板一"} ).then(function(obj) {
                         	toast("发送成功!");
                         }, function(err){
                         	loadingToast("发送失败!");
                         });
						}
                     });
                    $gallery = $('#gallery'),
                    $galleryImg = $('#galleryImg'),
                    $uploaderInput = $('#uploaderInput'),
                    $uploaderFiles = $('#uploaderFiles');
                    var $toast = $('#toast');
                    var $loadingToast = $('#loadingToast');
                    $uploaderInput.on("click", function(e) {
                    	wx.chooseImage({
                    		count: 1, 
                    		sizeType: ['compressed'],   
                    		sourceType: ['album', 'camera'], 
                    		success: function (res) {
                    			var localIds = res.localIds;
                    			$("#stu_img").attr("src",localIds);
                    			$galleryImg.attr("style", "background-image:url("+localIds[0]+")");
                    			wx.uploadImage({
                    				localId: localIds[0], 
                    				isShowProgressTips: 1,
                    				success: function (res) {
                    					var serverId = res.serverId; 
                    					$("input[name='studentCard']").val(serverId);
                    					$("#stu_img").data("flag","change");
                    				}
                    			});
                    		}
                    	});
                    });
                   $('#showToast').on('click', function() {
                       var openid = getCookie('openid');
					   var nickname = $("input[name='nickname']").val();
                       var select1 = $("#select1").val();
                       var telNum = $("input[name='tel']").val();
                       var collegeNam = $("select[name='college']").val();
                       var student_card_num = $("input[name='student_card_num']").val();
                       var locationA = $("input[name='location']").val();
                       var student_card = $("input[name='studentCard']").val();
					   var flag =  $("#stu_img").data("flag");
                       var code = $("input[name='code']").val();
                       if(code=="" || nickname==""||telNum==""||collegeNam==""||student_card_num==""||locationA==""||student_card==""){
                       		loadingToast("请填写完整相关信息!");
                       }else{
                       	var user={
                       		"id":openid,
                       		"name":nickname,
                       		"school":select1,
                       		"phoneNum":telNum,
                       		"college":collegeNam,
                       		"studentCardNum":student_card_num,
                       		"studentCard":student_card,
                       		"location":locationA
                       	}
                       	var userResult = JSON.stringify(user);
                       	if(${result.msg == "noexist"}){
                       	Bmob.Sms.verifySmsCode(telNum, code).then(function(obj) {
                       			if(obj.msg == "ok"){
                       				$.ajax({
                       					url:"/WxPost/choose",
                       					type:'get',
                       					dataType:'text',
                       					data:{'user':userResult,'flag':flag},
                       					success:function(data){
                       						var obj = JSON.parse(data);
                       						toast(obj.msg)
											window.location.href="/WxPost/panduan?openid="+openid;
                       					}
                       				});
                       			}
                       		}, function(err){
									loadingToast("验证码输入错误!");
                       		});
                       	}else{
                       		$.ajax({
                       			url:"/WxPost/choose",
                       			type:'get',
                       			dataType:'text',
                       			data:{'user':userResult,'flag':flag},
                       			success:function(data){
                       				var obj = JSON.parse(data);
                       				toast(obj.msg);
									//window.location.href="/WxPost/panduan?openid="+openid;
                       			}
                       		});
                       	}		
                      } 
					});

					$uploaderFiles.on("click", "li", function() {
						if(${result.obj.studentCard !=null}){
							$galleryImg.attr("style", "background-image:url(/WxPost/images/${result.obj.studentCard})");
						}else{
							$galleryImg.attr("style", "background-image:url(assert/images/pic_160.png)");
						}
						$gallery.fadeIn(100);
					});
					$gallery.on("click", function() {
						$gallery.fadeOut(100);
					});
					$("#del_student_card").on('click',function(){
						$("#stu_img").attr("src","");
						$galleryImg.attr("style", "background-image:url(assert/images/pic_160.png)")
						$("#stu_img").val("");
					});
				});
			</script>
		</script>
		<script src="assert/cookieutil.js"></script>
        <script src="assert/bmob-min.js"></script>
		<script src="./assert/zepto.min.js"></script>
		<script src="./assert/example.js"></script>
		<script src="https://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
	</body>

</html>