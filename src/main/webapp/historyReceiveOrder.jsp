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
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
		<title>快递镖局</title>
		<link rel="stylesheet" href="<%=basePath%>./assert/style/weui.css" />
		<link rel="stylesheet" href="<%=basePath%>./assert/example.css" />
		<link rel="stylesheet" href="<%=basePath %>assert/dropload.css" />
	</head>

	<body ontouchstart>
		<div class="weui-toptips weui-toptips_warn js_tooltips">错误提示</div>

		<div class="container" id="container"></div>

		<script type="text/html" id="tpl_home">
			<div class="page">
				<div class="page__hd xb-orderBg">
					<div class="xb-order">
						<a class="xb-order-btn" href="<%=basePath%>index.jsp" ><i class="weui-icon-back"></i></a>
						<h2>我接取的订单</h2>
					</div>
				</div>
				<div class="page__bd page__bd_spacing content">
					<ul class="lists">
						<div class="weui-gallery" id="gallery" style="opacity: 0;display: none;">
								<span class="weui-gallery__img" id="galleryImg"  ></span>
						</div>
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
										<label class="weui-form-preview__label">发单时间</label>
										<span class="weui-form-preview__value">${item.showOrderTime}</span>
									</div>
									<div class="weui-form-preview__item">
										<label class="weui-form-preview__label">接单时间</label>
										<span class="weui-form-preview__value">${item.showReceiveOrderTime}</span>
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
										<label class="weui-form-preview__label">联系电话</label>
										<span class="weui-form-preview__value"><a href="tel:${item.phoneNum}">${item.phoneNum}</a></span>
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
										</font></span>
									</div>
									<div class="weui-form-preview__item">
										<label class="weui-form-preview__label">学生证</label>
										<span class="weui-form-preview__value"><img src="/WxPost/images/${item.studentCard}" data-url="${item.studentCard}" class ="studentCard"/></span>
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
			</div>
			<script type="text/javascript">
				$(function(){

					var $galleryImg = $('#galleryImg');
	 				var $gallery = $('#gallery');
                     $('.lists').on('click','.studentCard',function(){
                     	var url = $(this).data("url");
                     	$galleryImg.attr("style", "background-image:url(<%=basePath%>/images/"+url+")");
                     	$gallery.fadeIn(100);
                     });
                    $gallery.on("click",function() {
						$gallery.fadeOut(100);
	 				});
					// 页数
					var page = 1;
					var openid = getCookie("openid");
					$('.page').dropload({
						scrollArea : window,
						autoLoad:false,
						threshold : 50,
			loadDownFn : function(me){
			page++;
            var result = '';
            $.ajax({
            	type: 'GET',
            	url: '/WxPost/history/receiveorderjson/'+page+'?openid='+openid,
            	dataType: 'json',
            	success: function(data){
					var list  = JSON.parse(data).obj.list;
            		var arrLen = list.length;
            		if(arrLen > 0){
            			for(var i=0; i<arrLen; i++){
							var choose = (list[i].hurry ==true)?'是':'否';
							result += '<li class="xb-pdTop">'
							+'<div class="weui-form-preview">'
							+'	<div class="weui-form-preview__hd">'
							+'		<div class="weui-form-preview__item">'
							+'			<label class="weui-form-preview__label">订单名称</label>'
							+'			<em class="weui-form-preview__value">'+list[i].name+'</em>'
							+'		</div>'
							+'	</div>'
							+'	<div class="weui-form-preview__bd">'
							+'		<div class="weui-form-preview__item">'
							+'			<label class="weui-form-preview__label">发单时间</label>'
							+'			<span class="weui-form-preview__value">'+list[i].showOrderTime+'</span>'
							+'		</div>'
							+'		<div class="weui-form-preview__item">'
							+'			<label class="weui-form-preview__label">接单时间</label>'
							+'			<span class="weui-form-preview__value">'+list[i].showReceiveOrderTime+'</span>'
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
							+'			<label class="weui-form-preview__label">联系电话</label>'
							+'			<span class="weui-form-preview__value"><a href="tel:'+list[i].phoneNum+'">'+list[i].phoneNum+'</a></span>'
							+'		</div>'
							+'		<div class="weui-form-preview__item">'
							+'			<label class="weui-form-preview__label">订单价值</label>'
							+'			<span class="weui-form-preview__value">'+list[i].size+'元</span>'
							+'		</div>'
							+'		<div class="weui-form-preview__item">'
							+'			<label class="weui-form-preview__label">是否加急</label>'
							+'			<span class="weui-form-preview__value"><font style="color:red;">'
							+choose
							+'			</font></span>'
							+'		</div>'
							+'		<div class="weui-form-preview__item">'
							+'			<label class="weui-form-preview__label">学生证</label>'
							+'			<span class="weui-form-preview__value"><img src="/WxPost/images/'+list[i].studentCard+'" data-url="'+list[i].studentCard+'" class ="studentCard"/></span>'
							+'		</div>'
							+'		<div class="weui-form-preview__item">'
							+'			<label class="weui-form-preview__label">备注</label>'
							+'			<span class="weui-form-preview__value">'+list[i].descration+'</span>'
							+'		</div>'
							+'	</div>'
							+'</div>'
						+'</li> '
            			}
                }else{
                        me.lock('up');
                        me.noData();
                    }
                    $('.lists').append(result);
                    me.resetload();
                },
                error: function(xhr, type){
                	alert('Ajax error!');
                    me.resetload();
                }
            });
        }
    });
			
				});
			</script>
		</script>
		<script src="<%=basePath%>./assert/zepto.min.js"></script>
		<script src="<%=basePath%>./assert/example.js"></script>
		<script src="<%=basePath %>assert/dropload.min.js"></script>
 		<script src="<%=basePath%>assert/cookieutil.js"></script>
	</body>

</html>