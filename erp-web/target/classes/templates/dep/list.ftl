<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="/easyui/themes/icon.css">
<script type="text/javascript" src="/easyui/jquery.min.js"></script>
<script type="text/javascript" src="/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="/js/form.js"></script>
<script type="text/javascript">
	$(function(){ //页面加载完后执行function函数
		//初始化easyui表格
		$("#grid").datagrid({ 
			url: '/dep/getData.do', //请求路径
			columns: [[ //指定表格的列
				{field: 'uuid', title: '部门编号', width:100},
				{field: 'name', title: '部门名称', width:100},
				{field: 'tele', title: '部门电话', width:100}
			]],
			singleSelect: true //是否单选
		});
	});
	
	//查询
	function search() {
		//获取表单参数
		var data = getFormData("searchForm"); //获取表单数据，并返回json对象
		//让表格重新加载数据
		$("#grid").datagrid('load', data);
	}
</script>
</head>
<body>
	<!-- 构造查询表单 -->	
	<form id="searchForm">
		部门名称：<input name="name"/>
		部门电话：<input name="tele"/>
		<input type="button" value="搜索" onclick="search()"/>	
	</form>
	<table id="grid"></table>
</body>
</html>