$(function(){
	$('#tt').datagrid(
			{
				title:'上传历史',
				iconCls:'icon-ok',
				width:'100%',
				fit:true,
				fitcolumns:true,
				toolbar:[{
					text:'删除',
					iconCls:'icon-remove',
					handler:function(){
						//删除
						delDoc();
					}
				}],
				pageSize:10,
				pageList:[5,10,15,20],
				nowrap:false,
				striped:true,
				collapsible:true,
				url:'uploadHistory.action',
				loadMsg:'数据装载中......',
				sortName:'id',
				sortOrder:'asc',
				remoteSort:false,
				
				frozenColumns:[[{
					field:'ck',
					checkbox:true
				}]],
				columns:[[
				       {
				    	   title:'编号',
				    	   field:'id',
				    	   width:'90',
				    	   rowspan:2,
				    	   align:'center'
				       },
				       {
				    	   title:'文档名',
				    	   field:'docName',
				    	   width:'300',
				    	   rowspan:2,
				    	   align:'center'
				       },
				       {
				    	   title:'文档大小（M）',
				    	   field:'docSize',
				    	   width:'90',
				    	   rowspan:2,
				    	   align:'center'
				       },
				       {
				    	   title:'上传时间',
				    	   field:'docTime',
				    	   width:'150',
				    	   rowspan:2,
				    	   align:'center'
				       },
				       {
				    	   title:'下载次数',
				    	   field:'count',
				    	   width:'90',
				    	   rowspan:2,
				    	   align:'center'
				       }
			    ]],
			    pagination : true,
				rownumbers : true
			});
	
	
	$('#tt').datagrid('getPager').pagination({
		displayMsg : '当前显示从{from}到{to}共{total}记录',
		onBeforeRefresh : function(pageNumber, pageSize) {
			$(this).pagination('loading');
			$(this).pagination('loaded');
		},
	});
});
//刷新表格
function reloadTable(){
	$('#tt').datagrid('reload');
}

//删除
function delDoc(){
	if ($('#tt').datagrid('getSelected')) {
		// 首先如果用户选择了数据，则获取选择的数据集合
		var names = [];
		var selectedRow = $('#tt').datagrid('getSelections');
		for (var i = 0; i < selectedRow.length; i++) {
			names.push(selectedRow[i].docName);
		}
		var name = names.join(',');
		var sqlName = "";
		for (var i = 0; i < names.length; i++) {
			sqlName += "'";
			sqlName += names[i];
			sqlName += "',";
		}
		sqlName = sqlName.substring(0, (sqlName.length - 1));
		// 删除操作
		$.messager.confirm('删除提示', '你确定删除下列文档吗?<br/>' + name, function(r) {
			if (r) {
				$.ajax({
					async : false,
					cache : false,
					type : 'POST',
					dataType : "json",
					data : {
						"doc.docName" : sqlName,
					},
					url : root + '/delDoc_owner.action',// 请求的action路径
					error : function() {
						$.messager.confirm('删除提示','请求失败',function(r){});
					},
					success : function(data) {
						if (data.result == 'success') {
							// 刷新列表
							reloadTable();
						}else {// 返回异常信息
							alert('删除失败');
						}
					}
				});
			}
		});
	} else {
		$.messager.confirm('提示', '请选择文档！', function(r) {});
	}
}

