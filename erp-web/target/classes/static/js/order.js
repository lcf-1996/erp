var supplierName;
var stats =  new Array();
if (Request['type'] == 1) {
	stats[0] = '未审核';
	stats[1] = '已审核';
	stats[2] = '已确认';
	stats[3] = '已入库';
	
	supplierName = '供应商';
}
if (Request['type'] == 2) {
	stats[0] = '未出库';
	stats[3] = '已出库';
	
	supplierName = '客户';
}

var g_index;   //主表格的行索引
var g_index2;  //子表格的行索引

$(function() {
	//绑定查询按钮的事件
	$("#searchBtn").bind('click', function() {
		//获取搜索表单的参数
		var data = getFormData("searchForm");
		if (data['state'] == '') {
			delete data.state; //删除对象属性
		}
		//重新加载表格，并把表单的参数发送给后台
		$("#grid").datagrid('reload', data);
	});
	
	$("#grid").datagrid({
		title: '订单列表', //设置表格的标题
		url: url, //获取数据的URL地址
		columns: [[
			{field: 'uuid', title: '订单编号', width: 80},
			{field: 'createtime', title: '生成日期', width: 80, formatter: function(value) {
				return to_date(value);
			}},
			{field: 'checktime', title: '检查日期', width: 80, formatter: function(value) {
				return to_date(value);
			}},
			{field: 'starttime', title: '开始日期', width: 80, formatter: function(value) {
				return to_date(value);
			}},
			{field: 'endtime', title: '结束日期', width: 80, formatter: function(value) {
				return to_date(value);
			}},
			{field: 'type', title: '订单类型', width: 80, formatter: function(value) {
				return value == 1 ? '采购订单' : (value == 2 ? '销售订单' : '');
			}},
			{field: 'creater', title: '下单员', width: 80, formatter: function(value, row, rowIndex) {
				if (value != null) {
					$.post(basePath + '/emp/getName.do', {uuid: value}, function(rt) {
						$("#creater_" + rowIndex).html(rt.name);
					}, 'json');
				}
				return "<span id='creater_" + rowIndex + "'></span>";
				
			}},
			{field: 'checker', title: '审查员', width: 80, formatter: function(value, row, rowIndex) {
				if (value != null) {
					$.post(basePath + '/emp/getName.do', {uuid: value}, function(rt) {
						$("#checker_" + rowIndex).html(rt.name);
					}, 'json');
				}
				return "<span id='checker_" + rowIndex + "'></span>";
				
			}},
			{field: 'starter', title: '采购员', width: 80, formatter: function(value, row, rowIndex) {
				if (value != null) {
					$.post(basePath + '/emp/getName.do', {uuid: value}, function(rt) {
						$("#starter_" + rowIndex).html(rt.name);
					}, 'json');
				}
				return "<span id='starter_" + rowIndex + "'></span>";
				
			}},
			{field: 'ender', title: '库管员', width: 80, formatter: function(value, row, rowIndex) {
				if (value != null) {
					$.post(basePath + '/emp/getName.do', {uuid: value}, function(rt) {
						$("#ender_" + rowIndex).html(rt.name);
					}, 'json');
				}
				return "<span id='ender_" + rowIndex + "'></span>";
				
			}},
			{field: 'supplieruuid', title: supplierName, width: 80},
			{field: 'totalmoney', title: '总金额', width: 80},
			{field: 'state', title: '订单状态', formatter: function(value) {
				return stats[value];
			}},
			oper
			/*{field: '-', title: '操作', formatter: function(value, row, rowIndex) {
				return "<a href='javascript:doStart(" + row.uuid + ")'>确认</a>"
			}}*/
		]],
		singleSelect: true,
		pagination: true, //显示分页栏
		pageSize: '5', //每页的记录数
		pageList: [5, 10, 15, 20],
		height: 400, //表格高度
		view: detailview, //指定datailView组件
		detailFormatter: function(index, row) { //点击行的时候显示的内容
			return "<table id='ddv_" + index + "'></table>";
		},
		onExpandRow: function(index, row) { //点击行的时候触发的事件
			$('#ddv_' + index).datagrid({
				url: basePath + '/ordersdetail/getData.do?ordersuuid=' + row.uuid,
				columns: [[
					{field: 'uuid', title: '编号', width: 100},
					{field: 'goodsuuid', title: '商品编号', width: 100},
					{field: 'goodsname', title: '商品名称', width: 100},
					{field: 'price', title: '价格', width: 100},
					{field: 'num', title: '数量', width: 100},
					{field: 'money', title: '金额', width: 100},
					{field: 'endtime', title: '结束日期', width: 100, formatter: function(value) {
						return to_date(value);
					}},
					{field: 'ender', title: '仓管员', width: 100, formatter: function(value, row, rowIndex) {
						if (value != null) {
							$.post(basePath + '/emp/getName.do', {uuid: value}, function(rt) {
								$("#starter_" + rowIndex).html(rt.name);
							}, 'json');
						}
						return "<span id='starter_" + rowIndex + "'></span>";
					}},
					{field: 'storeuuid', title: '仓库编号', width: 100},
					{field: 'state', title: '状态', width: 100, formatter: function(value) {
						if (Request['type'] == 1) {
							return value == 0 ? '未入库' : ((value == 1) ? '已入库' : '');
						}
						if (Request['type'] == 2) {
							return value == 0 ? '未出库' : ((value == 1) ? '已出库' : '');
						}
						return '';
					}}
				]],
				singleSelect: true,
				onDblClickRow: function(rowIndex, rowData) {
					g_index = index;
					g_index2 = rowIndex;
					//打开窗口
					$('#orderWindow').window('open');
					//初始化表单的数据
					$('#goodsuuid').html(rowData.goodsuuid);
					$('#goodsname').html(rowData.goodsname);
					$('#num').html(rowData.num);
					$('#id').val(rowData.uuid);
				},
				loadFilter: function(data) {
					//子表格的数据
					var value = {total: 0, rows: []};
					//循环每一行
					for (var i = 0; i < data.length; i++) {
						//如果状态为“未入库”, 那么就把该行数据保存在rows数组中
						if ((isFilter && data[i].state == '0') || !isFilter) {
							value.rows.push(data[i]);
						}
					}
					return value;
				}
			});
			
		}
	});
	
	//根据订单状态显示列(如果是销售订单，把检查时间、确认时间、检查人、确认人列隐藏)
	if (Request['type'] == 2) {
		$('#grid').datagrid('hideColumn', 'checktime');
		$('#grid').datagrid('hideColumn', 'starttime');
		$('#grid').datagrid('hideColumn', 'checker');
		$('#grid').datagrid('hideColumn', 'starter');
	}

});

//订单审核
function doCheck(orderuuid) {
	$.messager.confirm('提示', '确定要更改此订单为已审核吗？', function(flag) {
		if (flag) {
			$.post(basePath + '/orders/doCheck.do', {uuid: orderuuid}, function(rt) {
				if (rt.status) {
					$('#grid').datagrid('reload'); //刷新表格
				}
				$.messager.alert('提示', rt.message);
			});
		}
	});
}

//订单确认
function doStart(orderuuid) {
	$.messager.confirm('提示', '确定要更改此订单为已确认吗？', function(flag) {
		if (flag) {
			$.post(basePath + '/orders/doStart.do', {uuid: orderuuid}, function(rt) {
				if (rt.status) {
					$('#grid').datagrid('reload'); //刷新表格
				}
				$.messager.alert('提示', rt.message);
			});
		}
	});
}

//入库操作
function doInStore() {
	//获取表单数据
	var data = getFormData("orderForm");
	//异步提交数据
	$.post(basePath + '/orders/doInStore.do', data, function(rt) {
		if (rt.status) {
			//$('#grid').datagrid('reload'); 
			//关闭窗口
			$("#orderWindow").window('close');
			//移出子表格当前操作行
			$('#ddv_' + g_index).datagrid('deleteRow', g_index2);
			//判断子表格是否有记录，如果没有，则删除主表格的行
			if ($('#ddv_' + g_index).datagrid('getRows').length == 0) {
				$('#grid').datagrid('deleteRow', g_index);
			} 

			
		}
		$.messager.alert('提示', rt.message);
		
	}, 'json');
}

//出库操作
function doOutStore() {
	//获取表单数据
	var data = getFormData("orderForm");
	//异步提交数据
	$.post(basePath + '/orders/doOutStore.do', data, function(rt) {
		if (rt.status) {
			//关闭窗口
			$("#orderWindow").window('close');
			//移出子表格当前操作行
			$('#ddv_' + g_index).datagrid('deleteRow', g_index2);
			//判断子表格是否有记录，如果没有，则删除主表格的行
			if ($('#ddv_' + g_index).datagrid('getRows').length == 0) {
				$('#grid').datagrid('deleteRow', g_index);
			} 
		}
		$.messager.alert('提示', rt.message);
		
	}, 'json');
}




