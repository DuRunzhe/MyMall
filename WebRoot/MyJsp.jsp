<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = (String) request.getContextPath();
	String basePath = (String) request.getScheme() + "://"
			+ (String) request.getServerName() + ":"
			+ request.getServerPort() + path + "/";
	String oneListJson = (String) request.getAttribute("oneListJson");
	String twoMapJson = (String) request.getAttribute("twoMapJson");
	String threeMapJson = (String) request.getAttribute("threeMapJson");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'MyJsp.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta http-equiv="content-type" content="text/html;charset=utf-8">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript">
	function change(t) {
		var th = document.form1;
		alert(t);
	}
	function oneSelect() {
		var oneListJson =<%=oneListJson%>;
		//alert('Onclick事件发生'+oneListJson.json.length);
		//{"json":["其他","办公","图书","彩妆","数码","服饰","母婴","电器","美食","鞋包"]}
		var categorysel = document.getElementById("categorysel");
		for ( var i = categorysel.options.length - 1; i > 0; i--) {
			categorysel.options.remove(i);
		}
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
	function onechange(selvalue) {

		var subcategorysel = document.getElementById("subcategorysel");
		var twoMapJson=<%=twoMapJson%>;
		//alert(selvalue + '---' + twoMapJson.json[selvalue]);
		for ( var i = subcategorysel.options.length - 1; i > 0; i--) {
			subcategorysel.options.remove(i);
		}
		var s_subcategorysel = document.getElementById("s_subcategorysel");
		for ( var i = s_subcategorysel.options.length - 1; i > 0; i--) {
			s_subcategorysel.options.remove(i);
		}
		//alert(key+':'+twoMapJson.json[key]);
		for ( var j = 0; j < twoMapJson.json[selvalue].length; j++) {
			var newoption = document.createElement('option');
			newoption.value = twoMapJson.json[selvalue][j];
			newoption.text = twoMapJson.json[selvalue][j];
			subcategorysel.options.add(newoption);
		}
		return;
		var temp = "";
		for ( var i in twoMapJson.json) {//用javascript的for/in循环遍历对象的属性 
			temp += i + ":" + twoMapJson.json[i] + "\n";
		}
		alert(temp);//结果：cid:C0 \n ctext:区县 

		var subcategorysel = document.getElementById('subcategorysel');
		for ( var i = 0; i < twoMapJson.json.selvalue.length; i++) {
			alert(i);
		}

	}
	function twochange(selvalue){
		var s_subcategorysel = document.getElementById("s_subcategorysel");
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
</head>

<body onload="oneSelect()">
	This is my JSP page.
	<br>
	<form name="form1" method="post">
		<select onchange="onechange(this.value)" style="WIDTH: 80px"
			id="categorysel" name="category" class="text2">
			<option value="8">-请选择-</option>
		</select> <select name="subcategorysel" id="subcategorysel" onchange="twochange(this.value)">
			<option value="8">-请选择-</option>
		</select> <select name="s_subcategorysel" id="s_subcategorysel" onchange="">
			<option value="8">-请选择-</option>
		</select>
	</form>

</body>
</html>
