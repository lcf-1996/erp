<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>销售统计报表</title>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${request.contextPath}/easyui/themes/icon.css">
<script type="text/javascript" src="${request.contextPath}/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${request.contextPath}/js/form.js"></script>
<script>
	$(function() {
	
		$("#grid").datagrid({
			url: '${request.contextPath}/orders/getOrderReportData.do',
			columns: [[
				{field: 'name', title: '商品类型', width: 200},
				{field: 'money', title: '销售额', width: 200}
			]],
			singleSelect: true
		});
		
		//绑定统计按钮的事件
		$("#countBtn").bind('click', function() {
			var data = getFormData('searchForm');
			$('#grid').datagrid('load', data);
			//更新图表数据
			$('#chart').attr('src', '${request.contextPath}/orders/chart.do?startDate=' + data['startDate']
				+ '&endDate = ' + data['endDate']);
		});
		
		//绑定导出按钮的事件
		$("#exportBtn").bind('click', function() {
			var data = getFormData('searchForm');
			window.open('${request.contextPath}/orders/exportExcel.do?startDate=' + data['startDate']
				+ '&endDate=' + data['endDate']);
		});
		
	});
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center', title:'销售统计表'" style="padding:5px;background:#eee;">
		<form id="searchForm">
			开始日期：<input name="startDate" class="easyui-datebox"/>
			截至日期：<input name="endDate" class="easyui-datebox"/>
			<input type="button" id="countBtn" value="统计" />
			<input type="button" id="exportBtn" value="导出" />
		</form>
		<table id="grid"></table>
	</div>
	<!-- 右边 -->
	<div data-options="region:'east', iconCls:'icon-reload', title:'销售统计图', split:true" style="width:407px">
		<img id="chart" src="${request.contextPath}/orders/chart.do"/>
	</div>
</body>
</html>
