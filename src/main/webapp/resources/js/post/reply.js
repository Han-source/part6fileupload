/**
* 모듈패턴
	이벤트, DOM, ajax 처리 등을 하나의 jsp에 넣기에는 복잡해짐, 유지보수성 저하
	이에 javascript를 별도 file로 만들어 분할 정복.
CallBack 함수 : 특정 이벤트시에 이를 불러준다.
*/
console.log("reply.js 연동 성공");

/** 함수 정의 영역 */

var replyService = (function() {

	
	function getCountOfReply(replyId, successCallBack, errorCallBack){
		$.getJSON(
			"/replies/count/" + replyId,
			function(cnt) {
				if (successCallBack) {
					successCallBack(cnt);
				}
			}
		).fail(
			function(xhr, status, errMsg) {
				if (errorCallBack) {
					errorCallBack(errMsg);
				}
			}
		);	
	}
	// 특정게시판에 달린 모든 댓글들
	function getList(orgIdAndPage, successCallBack, errorCallBack) {
		//https://api.jquery.com/jquery.getjson/
		var page = orgIdAndPage.page || 1;

		$.getJSON(
			"/replies/pages/" + orgIdAndPage.orgId + "/" + page,
			function(listReplyWithCount) {
				if (successCallBack) {
					successCallBack(listReplyWithCount);
				}
			}
		).fail(
			function(xhr, status, errMsg) {
				if (errorCallBack) {
					errorCallBack(errMsg);
				}
			}
		);
	}
	
	// 특정 댓글의 댓글들 출력
	function getListOfReply(replyId, successCallBack, errorCallBack) {
		$.getJSON(
			"/replies/pages/" + replyId,
			function(listAllReplyOfReply) {
				if (successCallBack) {
					successCallBack(listAllReplyOfReply);
				}
			}
		).fail(
			function(xhr, status, errMsg) {
				if (errorCallBack) {
					errorCallBack(errMsg);
				}
			}
		);
	}
	
	
	
	function get(replyId, successCallBack, errorCallBack){
		$.getJSON(
			"/replies/" + replyId + ".json",
			function(replyObj) {
				if (successCallBack) {
					successCallBack(replyObj);
				}
			}
		).fail(
			function(xhr, status, errMsg) {
				if (errorCallBack) {
					errorCallBack(errMsg);
				}
			}
		);
	}
	function add(originalId, reply, successCallBack, errorCallBack) {
		//https://api.jquery.com/jquery.ajax/
		$.ajax({
			type: 'post',	//Method alias
			url: '/replies/new/' + originalId,
			data: JSON.stringify(reply),	//객체를 json문자열로 출력
			contentType: 'application/json; charset=UTF-8',
			success: function(resObj, status, xhr) {
				if (successCallBack) {
					successCallBack(resObj);
				}
			},
			error: function(xhr, status, errMsg) {
				if (errorCallBack) {
					errorCallBack(errMsg);
				}
			}
		});
	}

	function update(reply, successCallBack, errorCallBack){
		$.ajax({
			type: 'put',	//Method alias
			url: '/replies/',
			data: JSON.stringify(reply),	//객체를 json문자열로 출력
			contentType: 'application/json; charset=UTF-8',
			success: function(resultMsg, status, xhr) {
				if (successCallBack) {
					successCallBack(resultMsg);
				}
			},
			error: function(xhr, status, errMsg) {
				if (errorCallBack) {
					errorCallBack(errMsg);
				}
			}
		});
	}

	function remove(replyId, successCallBack, errorCallBack){
		$.ajax({
			type: 'delete',	//Method alias
			url: '/replies/' + replyId,
			//XMLHttpRequest (XHR)은 AJAX 요청을 생성하는 JavaScript API입니다. XHR의 메서드로 브라우저와 서버간의 네트워크 요청을 전송할 수 있습니다.
			success: function(delResult, status, xhr) {
				if (successCallBack) {
					successCallBack(delResult);
				}
			},
			error: function(xhr, status, errMsg) {
				if (errorCallBack) {
					errorCallBack(errMsg);
				}
			}
		});
	}

	return {
		getAllReplyCountOfReply : getCountOfReply, 
		getReplyList : getList,
		getReplyListOfReply : getListOfReply,
		getReply : get,
		addReply : add,
		updateReply : update,
		removeReply : remove
	};
})();