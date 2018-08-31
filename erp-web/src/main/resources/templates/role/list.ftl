<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${request.contextPath}/easyui/themes/icon.css">
<script type="text/javascript" src="${request.contextPath}/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${request.contextPath}/js/form.js"></script>
<script type="text/javascript">
	var roleId;

	$(function(){ //页面加载完后执行function函数
		//初始化easyui表格
		$("#grid").datagrid({ 
			url: '${request.contextPath}/role/getData.do', //请求路径
			columns: [[ //指定表格的列
				{field: 'uuid', title: '角色编号', width:100},
				{field: 'name', title: '角色名称', width:100}
			]],
			onClickRow: function(rowIndex, rowData) {
				//初始化菜单树
				$("#tree").tree({
					url: '${request.contextPath}/menu/getRoleMenus.do?roleuuid=' + rowData.uuid, //请求的URL
					checkbox: true, //添加复选框 
					animate: true //动画效果
				});
				roleId = rowData.uuid;
			},
			singleSelect: true, //是否单选
		});
	});
	
	//保存角色菜单
	function save() {
		//把角色id以及选中菜单id发送给后台
		if (roleId == null) {
			$.messager.alert('提示', '请选择角色！');	
			return;
		}
		//得到选中的节点集合
		var nodes = $("#tree").tree('getChecked');
		var ids = '';
		for (var i = 0; i < nodes.length; i++) {
			ids += nodes[i].id + ',';
		}
		//获取没有全选状态下的父节点ID
		nodes = $("#tree").tree('getChecked', 'indeterminate');
		for (var i = 0; i < nodes.length; i++) {
			ids += nodes[i].id + ',';
		}
		//alert(ids);
		//提交异步请求执行保存
		$.post('${request.contextPath}/role/saveRoleMenu.do'
			, {roleId: roleId, menuIds: ids}, function(rt) {
			$.messager.alert('提示', rt.message);
		}, 'json');
		
	}
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'west', title:'选择角色', split:true" style="width:220px;">
		<table id="grid"></table>
	</div>
	<div data-options="region:'center', title:'权限设置'" style="padding:5px;background:#eee;">
		<ul id="tree"></ul>
		<input type="button" value="保存" onclick="save()"/>
	</div>
</body>
</html>