<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
Map<String,Object> map=(Map<String,Object>)request.getAttribute("map");//
Map<String,Object> spick_info=(Map<String,Object>)request.getAttribute("spick_info");

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML><HEAD><TITLE>后台管理系统</TITLE>
<META http-equiv=Content-Type content="text/html; charset=gb2312">
<META 
content="" 
name=keywords>
<META content= name=description>
<STYLE type=text/css>
.neon {
	FILTER: glow(color=#002E60,strength=3)
}
DIV {
	WIDTH: 70px
}
BODY {
	MARGIN: 0px
}
BODY {
	MARGIN-TOP: 0px; SCROLLBAR-FACE-COLOR: #005fc5; FONT-SIZE: 12px; BACKGROUND: #ffffff; SCROLLBAR-HIGHLIGHT-COLOR: #799ae1; SCROLLBAR-SHADOW-COLOR: #799ae1; SCROLLBAR-3DLIGHT-COLOR: #005fc5; SCROLLBAR-ARROW-COLOR: #ffffff; SCROLLBAR-TRACK-COLOR: #aabfec; SCROLLBAR-DARKSHADOW-COLOR: #799ae1
}
</STYLE>
<LINK href="<%=path %>/images/duan_1.css" type=text/css rel=stylesheet>
<META content="MSHTML 6.00.2800.1106" name=GENERATOR>
<style type="text/css">
<!--
.STYLE7 {COLOR: #003366; font-size: 12px;}
-->
</style>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">

function miaosha(){
	var th=document.form1;
	var goods_id=<%=map.get("goods_id")%>;
	var new_price=<%=map.get("new_price")%>;
	var goods_location=<%=map.get("goods_location")%>;
	
	var ms_price=th.miaosha_price.value;
	var ms_num=th.miaosha_num.value;
	
	
	Date.prototype.format = function(format){ 
		var o = { 
		"M+" : this.getMonth()+1, //month 
		"d+" : this.getDate(), //day 
		"h+" : this.getHours(), //hour 
		"m+" : this.getMinutes(), //minute 
		"s+" : this.getSeconds(), //second 
		"q+" : Math.floor((this.getMonth()+3)/3), //quarter 
		"S" : this.getMilliseconds() //millisecond 
		}; 

		if(/(y+)/.test(format)) { 
		format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
		} 

		for(var k in o) { 
		if(new RegExp("("+ k +")").test(format)) { 
		format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length)); 
		} 
		} 
		return format; 
		} ;
		var myDate = parseInt(new Date().valueOf()/1000);//当前时间的秒值
		//var nowStr = new Date().format("yyyy-MM-dd hh:mm:ss"); 
	 
	if(ms_price==""){
		alert("价格不能为空！");
		return;
	}
	var reg1 = /^\d+(\.\d+)?$/;
	if(!reg1.test(ms_price)==true){
	    alert("价格必须是数字！");
	    return;
	}
	if(ms_price>=new_price){
		alert("秒杀价要低于原价！");
		return;
	}
	
	if(ms_num==""){
		alert("数量不能为空！");
		return;
	}
	var reg2=/^[1-9]\d*$|^0$/;
	if(!reg2.test(ms_num)==true){
	    alert("数量必须是数字！");
	    return;
	}
	if(ms_num>goods_location){
		alert("已经没有那么多库存！");
		return;
	}
	if(th.birthday.value==""||th.birthday.value==null){
		alert("时间不能为空！");
		return;
	}
	var time=th.birthday.value;
	time = time.replace(new RegExp("-","gm"),"/");
	time=new Date(time).getTime()/1000;//转化时间为秒
	if(time-myDate<0){
		alert("只能选择未来的时间！");
		return;
	}
	th.action="<%=path%>/servlet/AddMiaoshaProduct?goods_id="+goods_id
			+"&"+"ms_price="+ms_price+"&"+"ms_num="+ms_num
			+"&"+"time="+th.birthday.value;
	th.submit();
	
	
}
</script>
</HEAD>
<BODY bgColor="#ffffff">
<form name="form1" action="" method="post" enctype="multipart/form-data">
<TABLE height=426 cellSpacing=0 cellPadding=0 width=580 align=center border=0 id="main_table">
  <TBODY>
  <TR>
    <TD colSpan=3 height=9></TD></TR>
  <TR>
    <TD vAlign=top width=12 background="<%=path %>/images/dhpddw.gif" rowSpan=2>&nbsp;</TD>
    <TD width=739 background="<%=path %>/images/h-1.gif" height=9></TD>
    <TD width=9 height=9><IMG height=9 src="<%=path %>/images/jiao.gif" width=9></TD></TR>
  <TR>
    <TD vAlign=top align=right width=739 height=408><TABLE cellSpacing=0 cellPadding=0 width=556 border=0>
      <!--DWLayoutTable-->
      <TBODY>
        <TR>
          <TD vAlign=bottom width=548 height=27><IMG height=10 
            src="<%=path %>/images/jt2.gif" width=10> <span class="lbt">产品管理&gt;&gt;产品信息_查看 </span></TD>
          <TD width=8 rowSpan=3>&nbsp;</TD>
        </TR>
        <TR>
          <TD background="<%=path %>/images/ht.gif" height=22></TD>
        </TR>
        <TR>
          <TD class=unnamed1 vAlign=top 
            height=9><TABLE width="99%" 
        border=0 cellPadding=4 cellSpacing=1 bgColor=#0867b3>
              <TBODY>
                <TR bgColor=#ffffff height=20>
													<TD width=13% align="center" noWrap class="STYLE7">产品名称</TD>
													<TD width=31% noWrap><span class="foot3"><%=map.get("goods_name")%> </span></TD>
													<TD width=13% align="center" noWrap><span
														class="STYLE7">产品现价</span></TD>
													<TD width=15% noWrap><span class="STYLE7"><%=map.get("new_price")%> </span></TD>
													<TD width=13% align="center" noWrap class="STYLE7">产品原价</TD>
													<TD width=15% noWrap colspan="3"><span class="STYLE7">
													<%=map.get("old_price")%> </span>
													</TD>
												</TR>

												<TR bgColor=#ffffff height=20>

													<TD width=13% align="center" noWrap class="STYLE7">产品库存</TD>
													<TD width=20% noWrap colspan="1"><span class="STYLE7">
													<%=map.get("goods_location")%> </span>
													</TD>
													<TD width=13% align="center" noWrap class="STYLE7">产品折扣</TD>
													<TD width=20% noWrap colspan="1"><span class="STYLE7">
															<%=map.get("praise_scale")%> </span>
													</TD>
													<TD width=13% align="center" noWrap class="STYLE7">产品评论数</TD>
													<TD width=21% noWrap colspan="1"><span class="STYLE7">
															<%=map.get("scales_volume")%> </span>
													</TD>
												</TR>
												

												<TR bgColor=#ffffff height=20>
													<TD width=13% align="center" noWrap class="STYLE7">促销信息</TD>
													<TD width=31% noWrap colspan="5"><span class="STYLE7">
															<%=map.get("goods_promotion")%> </span>
													</TD>

												</TR>
												<TR bgColor=#ffffff height=20>
													<TD width=13% align="center" noWrap class="STYLE7">商品分类：</TD>
													<TD width=31% noWrap colspan="5"><span class="STYLE7">
															<%=map.get("category")%>--<%=map.get("sub_category")%>--<%=map.get("s_sub_category")%> </span>
													</TD>

												</TR>
                <TR bgColor=#ffffff height=20>
                  <TD height="-1" align="center" noWrap><span class="STYLE7">产品图片</span></TD>
                  <TD height="-1" colspan="5" valign="middle" noWrap><img src="<%=request.getContextPath()+"\\upload\\"+map.get("goods_image")%>" ></img></TD>
                  </TR>
              </TBODY>
            </TABLE>
              <br></TD>
        </TR>
        <TR>
        <TD height=27 colspan="2" align="center" vAlign=top>  <a href="javascript:history.back();" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image4','','<%=path %>/images/fh_2.jpg',1)"><img src="<%=path %>/images/fh_1.jpg" name="Image4" width="60" height="22" border="0"></a><a href="javascript:miaosha();" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image4','','<%=path %>/images/fh_2.jpg',1)"><img src="<%=path %>/images/fh_1_2.jpg" name="Image5" width="60" height="22" border="0"></a></TD>
          
        </TR>
        <TR>
        <td align="center" vAlign=top class="STYLE7">秒杀价格：<input  style="color:gray"  onpropertychange="this.style.color='black'" oninput="this.style.color='black'" name="miaosha_price" id="miaosha_price" type="text" value=<%=spick_info.get("spick_price") %> /></td>
        </TR>
        <TR>
        <td align="center" vAlign=top class="STYLE7">秒杀数量：<input style="color:gray"  onpropertychange="this.style.color='black'" oninput="this.style.color='black'" name="miaosha_num" id="miaosha_num" type="text" value=<%=spick_info.get("spick_num") %> /></td>
        </TR>
         <TR>
        <td align="center" vAlign=top class="STYLE7">秒杀时间：	<input style="color:gray"  onpropertychange="this.style.color='black'" oninput="this.style.color='black'" id="birthday" class="Wdate" type="text" value=<%=spick_info.get("spike_time").toString() %> 
							name="birthday" readonly="readonly" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"
							></td>
        </TR>
        <TR>
          <TD height=87 vAlign=top><!--DWLayoutEmptyCell-->&nbsp;</TD>
          <TD></TD>
        </TR>
        <TR>
          <TD height=20>&nbsp;</TD>
          <TD></TD>
        </TR>
      </TBODY>
    </TABLE></TD>
    <TD width=9 background="<%=path %>/images/s-1.gif"></TD></TR></TBODY></TABLE>
    </form>
<IFRAME name=top align=default src="<%=path %>/bottom.jsp" 
      frameBorder=0 width=100% scrolling=no 
      height=88>
<h1>&nbsp;</h1>
</IFRAME>
</BODY></HTML>

