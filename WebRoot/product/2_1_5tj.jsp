<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	//List<Map<String , Object>> categoryList=(List<Map<String , Object>>)request.getAttribute("categoryList");
	//List<Map<String , Object>> sub_categoryList=(List<Map<String , Object>>)request.getAttribute("sub_categoryList");
	//List<Map<String , Object>> s_sub_categoryList=(List<Map<String , Object>>)request.getAttribute("s_sub_categoryList");
	String oneListJson = (String) request.getAttribute("oneListJson");
	String twoMapJson = (String) request.getAttribute("twoMapJson");
	String threeMapJson = (String) request.getAttribute("threeMapJson");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>后台管理系统</TITLE>
<META http-equiv=Content-Type content="text/html; charset=gb2312">
<META content="" name=keywords>
<META content=name=description>
<STYLE type=text/css>
.neon {
	FILTER: glow(color =       #002E60, strength =       3)
}

DIV {
	WIDTH: 70px
}

BODY {
	MARGIN: 0px
}

BODY {
	MARGIN-TOP: 0px;
	SCROLLBAR-FACE-COLOR: #005fc5;
	FONT-SIZE: 12px;
	BACKGROUND: #ffffff;
	SCROLLBAR-HIGHLIGHT-COLOR: #799ae1;
	SCROLLBAR-SHADOW-COLOR: #799ae1;
	SCROLLBAR-3DLIGHT-COLOR: #005fc5;
	SCROLLBAR-ARROW-COLOR: #ffffff;
	SCROLLBAR-TRACK-COLOR: #aabfec;
	SCROLLBAR-DARKSHADOW-COLOR: #799ae1
}
</STYLE>
<LINK href="<%=path%>/images/duan_1.css" type=text/css rel=stylesheet>
<META content="MSHTML 6.00.2800.1106" name=GENERATOR>
<style type="text/css">
<!--
.STYLE7 {
	COLOR: #003366;
	font-size: 12px;
}
-->
</style>
<script type="text/javascript">
function dosubmit(){
	var th=document.form1;
	if(th.proname.value==""){
		alert('商品名称不能空！');
		return;
	}
	if(th.new_price.value==""){
		alert('商品现价不能空！');
		return;
	}
	if(th.old_price.value==""){
		alert('商品原价不能空！');
		return;
	}
	if(th.goods_location.value==""){
		alert('商品库存数不能空！');
		return;
	}
	if(th.praise_scale.value==""){
		alert('商品折扣不能空！');
		return;
	}
	if(th.category.value=="8"&&th.sub_category.value=="8"&&th.s_sub_category.value=="8"){
		alert('商品分类不能全空！');
		return;
	}
	if(th.proimage.value==""){
		alert('商品图片不能空！');
		return;
	}
	th.action="<%=path%>/servlet/ProductAction_forAndroid?action_flag=add";
	th.submit();
	}
function oneSelect() {
	//第一个下拉列表
	var oneListJson =<%=oneListJson%>;
	//alert('Onclick事件发生'+oneListJson.json.length);
	//{"json":["其他","办公","图书","彩妆","数码","服饰","母婴","电器","美食","鞋包"]}
	var categorysel = document.getElementById("categorysel");
	//清空二级下拉列表
	for ( var i = categorysel.options.length - 1; i > 0; i--) {
		categorysel.options.remove(i);
	}
	//生成新的下拉选项
	for ( var i = 0; i < oneListJson.json.length; i++) {
		//alert(i);
		if (document.all) //IE 
		{
			//alert('IE'+i);
			var newoption = document.createElement('option');
			newoption.value = oneListJson.json[i];
			newoption.text = oneListJson.json[i];
			categorysel.options.add(newoption);

		} else { //其他浏览器 
			//alert('Chrome'+i);
			var newoption = document.createElement('option');
			newoption.value = oneListJson.json[i];
			newoption.text = oneListJson.json[i];
			categorysel.options.add(newoption);
			//oSelect.insertBefore(new Option(optionValue, optionText), oSelect.options[position]); 
			//categorysel.insert(new Option(optionValue, optionText));
		}

	}
}
//selvalue一级下拉列表的选中项
function onechange(selvalue) {
	//拿到Select控件
	var subcategorysel = document.getElementById("subcategorysel");
	//第一级下拉控件选项改变后，先清空二级下拉选项
	for ( var i = subcategorysel.options.length - 1; i > 0; i--) {
		subcategorysel.options.remove(i);
	}
	//拿到三级下拉列表项并清空
	var s_subcategorysel = document.getElementById("s_subcategorysel");
	for ( var i = s_subcategorysel.options.length - 1; i > 0; i--) {
		s_subcategorysel.options.remove(i);
	}
	//拿到二级下拉列表的json字符串
	//twoMapJson={"json":{"图书":["儿童图书"],"其他":["其他"],"彩妆":["化妆工具","唇膏","睫毛膏","眼影","指甲油"],
		//"鞋包":["鞋靴"],"美食":["水果","调味品"],"电器":["家用电器"],"服饰":["女装","男装"],
		//"数码":["手机通讯","摄影摄像","手机配件","电脑耗材"],"母婴":["婴儿食品"],"办公":["穿戴设备"]}}
	var twoMapJson=<%=twoMapJson%>;
	//根据键名得到json字符串中的键值
	//alert(selvalue + '---' + twoMapJson.json[selvalue]);
	//生成新的下拉列表选项，对于键值为数组的可以直接使用[角标]访问某个值
	for ( var j = 0; j < twoMapJson.json[selvalue].length; j++) {
		var newoption = document.createElement('option');
		newoption.value = twoMapJson.json[selvalue][j];
		newoption.text = twoMapJson.json[selvalue][j];
		subcategorysel.options.add(newoption);
	}

}
function twochange(selvalue){
	var s_subcategorysel = document.getElementById("s_subcategorysel");
	//threeMapJson={"json":{"鞋靴":["男鞋","女鞋","布鞋"],"水果":["苹果"],
		//"唇膏":["滋润唇膏","咬唇妆"],"睫毛膏":[],"手机通讯":["安卓手机","苹果手机","耳机"],"指甲油":[],
		//"手机配件":["手机保护壳","屏保"],"穿戴设备":["智能手表"],"女装":["T恤衫","休闲裤","打底裤","女士短裤"],
		//"眼影":[],"其他":[],"化妆工具":["画眉笔"],"家用电器":["彩电","厨房电器"],"电脑耗材":["音箱"],//
		//"男装":[],"摄影摄像":["数码相机"],"婴儿食品":[],"调味品":["果酱"],"儿童图书":["益智"]}}
	var threeMapJson=<%=threeMapJson%>;
	//alert(selvalue + '---' + threeMapJson.json[selvalue]);
	for ( var i = s_subcategorysel.options.length - 1; i > 0; i--) {
			s_subcategorysel.options.remove(i);
		}
	for ( var j = 0; j < threeMapJson.json[selvalue].length; j++) {
		var newoption = document.createElement('option');
		newoption.value = threeMapJson.json[selvalue][j];
		newoption.text = threeMapJson.json[selvalue][j];
		s_subcategorysel.options.add(newoption);
	}
	return;
}
</script>

</HEAD>
<BODY bgColor="#ffffff" onload="oneSelect()">
	<form name="form1" action="" method="post"
		enctype="multipart/form-data">
		<TABLE height=426 cellSpacing=0 cellPadding=0 width=580 align=center
			border=0>
			<TBODY>
				<TR>
					<TD colSpan=3 height=9></TD>
				</TR>
				<TR>
					<TD vAlign=top width=12 background="<%=path%>/images/dhpddw.gif"
						rowSpan=2>&nbsp;</TD>
					<TD width=739 background="<%=path%>/images/h-1.gif" height=9></TD>
					<TD width=9 height=9><IMG height=9
						src="<%=path%>/images/jiao.gif" width=9></TD>
				</TR>
				<TR>
					<TD vAlign=top align=right width=739 height=408><TABLE
							cellSpacing=0 cellPadding=0 width=556 border=0>
							<!--DWLayoutTable-->
							<TBODY>
								<TR>
									<TD vAlign=bottom width=548 height=27><IMG height=10
										src="<%=path%>/images/jt2.gif" width=10> <span
										class="lbt">产品信息管理&gt;&gt;产品信息_添加 </span></TD>
									<TD width=8 rowSpan=3>&nbsp;</TD>
								</TR>
								<TR>
									<TD background="<%=path%>/images/ht.gif" height=22></TD>
								</TR>
								<TR>
									<TD class=unnamed1 vAlign=top height=9><TABLE width="99%"
											border=0 cellPadding=4 cellSpacing=1 bgColor=#0867b3>
											<TBODY>
												<TR bgColor=#ffffff height=20>
													<TD width=13% align="center" noWrap class="STYLE7">产品名称</TD>
													<TD width=31% noWrap><span class="foot3"><INPUT
															class=text2 maxLength=48 size=32 name="proname"
															minLength="1"> </span></TD>
													<TD width=13% align="center" noWrap><span
														class="STYLE7">产品现价</span></TD>
													<TD width=15% noWrap><span class="STYLE7"> <INPUT
															class=text2 maxLength=20 size=18 name="new_price"
															minLength="1"> </span></TD>
													<TD width=13% align="center" noWrap class="STYLE7">产品原价</TD>
													<TD width=15% noWrap colspan="3"><span class="STYLE7">
															<INPUT class=text2 maxLength=20 size=18 name="old_price"
															minLength="1"> </span>
													</TD>
												</TR>

												<TR bgColor=#ffffff height=20>

													<TD width=13% align="center" noWrap class="STYLE7">产品库存</TD>
													<TD width=20% noWrap colspan="1"><span class="STYLE7">
															<INPUT class=text2 maxLength=20 size=18
															name="goods_location" minLength="1"> </span>
													</TD>
													<TD width=13% align="center" noWrap class="STYLE7">产品折扣</TD>
													<TD width=20% noWrap colspan="1"><span class="STYLE7">
															<INPUT class=text2 maxLength=20 size=18
															name="praise_scale" minLength="1"> </span>
													</TD>
													<TD width=13% align="center" noWrap class="STYLE7">产品评论数</TD>
													<TD width=21% noWrap colspan="1"><span class="STYLE7">
															<INPUT class=text2 maxLength=20 size=18
															name="scales_volume" minLength="1"> </span>
													</TD>
												</TR>
												

												<TR bgColor=#ffffff height=20>
													<TD width=13% align="center" noWrap class="STYLE7">促销信息</TD>
													<TD width=31% noWrap colspan="5"><span class="STYLE7">
															<INPUT class=text2 maxLength=72 size=64
															name="goods_promotion" minLength="1"> </span>
													</TD>

												</TR>
												<TR bgColor=#ffffff height=20>
													<TD height="0" align="center" noWrap><span
														class="STYLE7">分类：</span></TD>
													<TD height="0" colspan="5" noWrap>
													<select onchange="onechange(this.value)" style="WIDTH: 80px"
														id="categorysel" name="category" class="text2">
														<option value="8">-请选择-</option>
													</select> 
													<select name="sub_category" id="subcategorysel" 
														onchange="twochange(this.value)">
														<option value="8">-请选择-</option>
													</select> 
													<select name="s_sub_category" id="s_subcategorysel">
														<option value="8">-请选择-</option>
													</select>
													</TD>
												</TR>
												<TR bgColor=#ffffff height=20>
													<TD height="-1" align="center" noWrap><span
														class="STYLE7">产品图片</span></TD>
													<TD height="-1" colspan="5" valign="middle" noWrap><input
														name="proimage" type="file" class="text2" size="60">
													</TD>
												</TR>
											</TBODY>
										</TABLE> <br></TD>
								</TR>
								<TR>
									<TD height=27 colspan="2" align="center" vAlign=top><a
										href="javascript:dosubmit();"><img
											src="<%=path%>/images/ok_1.jpg" name="Image3" width="60"
											height="22" border="0"> </a> <a
										href="javascript:history.back();"><img
											src="<%=path%>/images/fh_1.jpg" name="Image4" width="60"
											height="22" border="0"> </a></TD>
								</TR>
								<TR>
									<TD height=87 vAlign=top>
										<!--DWLayoutEmptyCell-->&nbsp;</TD>
									<TD></TD>
								</TR>
								<TR>
									<TD height=20>&nbsp;</TD>
									<TD></TD>
								</TR>
							</TBODY>
						</TABLE></TD>
					<TD width=9 background="<%=path%>/images/s-1.gif"></TD>
				</TR>
			</TBODY>
		</TABLE>
	</form>
	<IFRAME name=top align=default src="<%=path%>/bottom.jsp" frameBorder=0
		width=100% scrolling=no height=88>
		<h1>&nbsp;</h1>
	</IFRAME>
</BODY>
</HTML>

