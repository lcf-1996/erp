<script type="text/javascript">
	$(function() {
		
		$("#grid").datagrid({
			url: '${request.contextPath}/storealert/getData.do', 
			columns: [[
		       {field:'uuid', title:'商品编号', width:100}, 
		       {field:'name', title:'商品名称', width:100}, 
		       {field:'storenum', title:'库存数量', width:100}, 
		       {field:'outnum', title:'待发货数量', width:100} 
			]],
			singleSelect: true,
			toolbar: [{ //定义一个工具栏
				iconCls: 'icon-add',
				text: '发送报警邮件',
				handler: function() {
					$.post('${request.contextPath}/storealert/send.do', function(rt) {
						$.messager.alert('提示', rt.message);
					}, 'json');
				}
			}]
		});
		
	});
	
</script>
<table id="grid"></table>