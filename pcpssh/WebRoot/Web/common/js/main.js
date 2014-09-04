$(function() {
	
	$('#centerTab').tabs({
		tools : [ {
			iconCls : 'icon-back',
			handler : function() {
				$.messager.confirm('娉ㄩ攢鎻愮ず', '浣犵‘瀹氭敞閿�悧?', function(r) {
					if (r) {
						window.location = root + '/logout.action';
					}
				});
			}
		} ]
	});
});

/**
 * 鍒涘缓鏂伴�椤瑰崱
 * 
 * @param tabId
 *            閫夐」鍗d
 * @param title
 *            閫夐」鍗℃爣棰�
 * @param url
 *            閫夐」鍗¤繙绋嬭皟鐢ㄨ矾寰�
 */
function addTab(tabId, title, url) {
	// 濡傛灉褰撳墠id鐨則ab涓嶅瓨鍦ㄥ垯鍒涘缓涓�釜tab,瀛樺湪鍒欓�涓椤甸潰
	if ($("#centerTab").tabs('exists', title)) {
		$("#centerTab").tabs('select', title); 
	} else {
		var name = 'iframe_' + tabId;
		$('#centerTab')
				.tabs(
						'add',
						{
							title : title,
							closable : true,
							cache : false,
							// 娉細浣跨敤iframe鍗冲彲闃叉鍚屼竴涓〉闈㈠嚭鐜癹s鍜宑ss鍐茬獊鐨勯棶棰�
							content : '<iframe name="'
									+ name
									+ '"id="'
									+ tabId
									+ '"src="'
									+ url
									+ '" width="100%" height="100%" frameborder="0" scrolling="auto" ></iframe>'
						});
	}
}


$(function() {
	/*if(user == '' || user == null || user == undefined){
		$.messager.alert('鎻愮ず', '杩樻湭鐧诲綍锛岀偣鍑荤‘璁ら噸鏂扮櫥褰曘�', "warning",function() {
			window.location = root + '/login/toLoginPage.action';
		});
	}*/
});
