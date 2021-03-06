<%@page import="java.awt.Window"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"                                                                                                                                                        
    pageEncoding="UTF-8"%>                                                                                                                                                                                             
<%@include file="/include/commons.jsp" %>                                                                                                                                                                              
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">                                                                                                                 
<html>                                                                                                                                                                                                                 
<head>                                                                                                                                                                                                                 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>                                                                                                                                                    
<meta http-equiv="X-UA-Compatible" content = "IE=edge,chrome=1" />
<title>系统名称</title>                                                                                                                                                                                                    
<link href="${ctx}/resources/platform/css/style.css" rel="stylesheet" type="text/css" />   
<link rel="stylesheet" type="text/css" href="${ctx}/resources/chuchur/css/base.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/resources/chuchur/css/formui.css" />
 <link rel="stylesheet" type="text/css" href="${ctx}/resources/chuchur/css/chur.css" />                                                                                                                            
<style type="text/css">                                                                                                                                                                                                
	tr.check {                                                                                                                                                                                                         
		cursor: pointer;   
	}                                                                                                                                                                                                                  
	.checked {                                                                                                                                                                                                         
		background: #bbb;                                                                                                                                                                                              
	}
    .courseinput {
	width: 400px;
	height: 32px;
	line-height: 32px;
	border-top: solid 1px #a7b5bc;
	border-left: solid 1px #a7b5bc;
	border-right: solid 1px #ced9df;
	border-bottom: solid 1px #ced9df;
	text-indent: 10px;
}    
</style>                                                                                                                                                                                                               
<script src="${ctx}/resources/js/jquery.min.js"></script>                                                                                                                                                              
<script type="text/javascript" src="${ctx}/resources/js/org.zh.plugins.js"></script>   
<script type="text/javascript" src="${ctx}/resources/js/org.cp.common.js"></script>   
<script type="text/javascript" src="${ctx}/resources/js/org.zh.common.js"></script>      
<script type="text/javascript" src="${ctx}/resources/chuchur/js/ChurAlert.min.js?skin=blue"></script>
<script type="text/javascript" src="${ctx}/resources/chuchur/js/chur-alert.1.0.js"></script>        
<script type="text/javascript" src="${ctx}/resources/chuchur/js/churCommon.js"></script>                                                                                                                                                     
<script type="text/javascript">                                                                                                                                                                                        
   //数据操作
   function systemNameUpdate(){
	   var systemtName = $("#systemtName").val();
	   if(systemtName != "" && systemtName != null && systemtName != undefined){
		   $.ajax({
			   type:"POST",
			   url:"${ctx}/dataCenterController/updateSystemName",
			   data:{"systemName":systemtName},
			   success:function(data){
				   if(data.status == 200){
					   operaAlert("success","成功提示",data.msg);
				   }else{
					   operaAlert("warning","警告提示",data.msg);
				   }
			   },
			   error:function(data){
				   operaAlert("error","错误提示","数据操作失败");
			   }
		   });
	   }
   }

</script>                                                                                                                                                                                                              
</head>                                                                                                                                                                                                                
<body>                        
	 <div style="width:50%;margin-top: 18%;margin-left:20%;">
	   <ul class="forminfo" style="float: left;">
			<li style="margin-top: 30px">
				<input id="systemtName" type="text" class="courseinput" placeholder="系统名称" value="${systemName}"/>&nbsp;&nbsp;
				<input type="button" class="btn2"  value="更&nbsp;&nbsp;新" onclick="systemNameUpdate();"/>
		   </li>
		 </ul>
	 </div>                                                                                                                                                                                     
</body>                                                                                                                                                                                                                
</html>                                                                                                                                                                                           