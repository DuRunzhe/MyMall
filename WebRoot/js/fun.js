/**
 * 
 */
function textClear(){
	//alert("Done !!");
	var x=document.getElementsByTagName("INPUT"); 
	for (var i=0;i<x.length;)
	  { 
	  
		if(x[i].type=="text"||x[i].type=="password"){
			x[i].value="";
			 
		}
		i=i+1;
	  }
}
function changeImage(img){
	//alert("Just do it!");
	img.src=img.src+"?"+new Date().getTime();//传递参数使地址发生变化，从而不再度取缓存
}