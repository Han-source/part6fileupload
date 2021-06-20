<!-- 게시글 상세 화면(readPost.jsp)에서 코드가 복잡해져 분할 정복을 통해 복잡도를 관리
	유지보수성 향상
  -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!--댓글 목록 출력 관련 부분 -->
<div class="card-footer">
	<div calss="card-header">
		댓글
		<button id = "btnOpenReplyModalForNew" class="btn-primary btn=xs pull-right">댓글달기</button>
	</div>
	<div class="card-body">
		<!-- 원글에 달린 댓글 목록 페이징으로 출력하기 -->
		<ul id="ulReply">
		</ul>
	</div>
         <!-- 페이징 처리 -->
         <div id="replyPaging" class='fa-pull-right'>
         </div>
</div>


	<!-- 댓글 입력 모달창 -->
	<div id="modalReply" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">REPLY MODAL</h4>
				</div>
				<!-- End of modal-header -->
				<div class="modal-body">
					<div class="form-group">
						<label>Reply</label>
						<input class="form-control" name='replyContent' value='New Reply!!!!'>
					</div>
<!-- 					<div class="form-group">
						<label>작성자</label>
						<input class="form-control" name='replyer' value=''>
					</div>
-->
					<div class="form-group">
						<label>Reply Date</label>
						<input class="form-control" name='replyDate' value=''>
					</div>
				</div>
				<!-- End of modal-body -->
				<div class="modal-footer">
					<button id='btnModifyReply' type="button" class="btn btn-warning">Modify</button>
					<button id='btnRemoveReply' type="button" class="btn btn-danger">Remove</button>
					<button id='btnRegisterReply' type="button" class="btn btn-primary">Register</button>
					<button id='btnCloseModal' type="button" class="btn btn-default">Close</button>
				</div>
				<!-- End of modal-footer -->
			</div>
			<!-- End of modal-content -->
		</div>
		<!-- End of modal-dialog -->
	</div>

<!-- End of 댓글 입력 모달창 -->
<script type="text/javascript" src="\resources\js\post\reply.js">
</script>
<script type="text/javascript" src="\resources\js\util\dateFormat.js">
</script>

<script type="text/javascript">
	var ulReply = $("#ulReply");
	var postId = "${post.id}"; //원글 identify
	var currentPageNum = 1;
	var replyPaging = $("#replyPaging");
	showReplyList(1); // 아래 함수 호출

	/** 댓글을 보여주는 함수 */
	function showReplyList(pageNum) {
		replyService.getReplyList(
			{
				orgId : postId,
				page : pageNum || 1
			},
			function(listReplyWithCount) { //서버로부터 받은 정보를 li로 출력
				var criteria = listReplyWithCount.first; //total Count 등이 들어있음.
				var listReply = listReplyWithCount.second;
				if (listReply == null || listReply.length == 0) {
					//정보가 없을 시 UL에 담긴 내용 청소.	
					ulReply.html("");
					return;
				}
				// 댓글정보가 있으면 li로 만들어서 ul에 담을 것.
				strLiTags = printReplyOfReplyByRecursion(listReply, false);

				ulReply.html(strLiTags);
				replyPaging.html(criteria.pagingDiv);

			}, function(errMsg) {
					alert("댓글 등록 오류  : " + errMsg);
				}
			);
		}
	//생성
	//replyService 모듈
	//모달창을 이용하기 위해 만든 것
	var modalReply = $("#modalReply");
	var inputReplyContent = modalReply.find("input[name='replyContent']");
	var inputReplyer = modalReply.find("input[name='replyer']");
	var inputReplyDate = modalReply.find("input[name='replyDate']");

	var btnRegisterReply = $("#btnRegisterReply");
	var btnModifyReply = $("#btnModifyReply");
	var btnRemoveReply = $("#btnRemoveReply");

	// var ulReply = $("#ulReply");
	//댓글에서 대댓글 전체 조회
	ulReply.on("click", "li i", function(e) {
		e.preventDefault();
		//this 위에 붙어있는 li 부분.
		var clickedLi = $(this).closest("li");
		//댓글id
		var reply_id = clickedLi.data("reply_id");
		
	replyService.getReplyListOfReply(
			reply_id,
			//listReplyWithHierahcy : 댓글들이 담겨있는 계층 구조
			function(listReplyWithHierahcy) { //서버로부터 받은 정보를 li로 출력
				//먼저번에 조회한 결과인 Ul은 청소 하고 댓글 내용만 기억 시키자
				var ul = clickedLi.find("ul");
				ul.remove();
				// 첫 출발점 0
				strLiTags = clickedLi.html();
				strLiTags += printReplyOfReplyByRecursion(listReplyWithHierahcy, true);
				clickedLi.html(strLiTags);
			  }, function(errMsg){
				  alert("댓글 등록 오류 : " +errMsg);
			  }		
			);
	});
	
	/** 대댓글을 달기위한 recursion 함수*/
	/** 반복문을 사용할 경우 ul전에 depth를 +1 해준다. */
	function printReplyOfReplyByRecursion(listReplyWithHierahcy, wrapWithUl) {
		var strRet = "";
		//두번쨰 파라미터가 true 인경우 호출
		if (wrapWithUl) {
			strRet = "<ul>";
		}
		for (var i = 0, len = listReplyWithHierahcy.length || 0; i < len; i++) {
				strRet += '<li class="clearfix" data-reply_id = "' + listReplyWithHierahcy[i].id + '">';
				strRet += '	<div>';
				strRet += '		<div>';
				if(listReplyWithHierahcy[i].replyCnt > 0){							
					strRet += '		<i>[' + listReplyWithHierahcy[i].replyCnt + ']</i>';
				}
				strRet += '			<strong>' + listReplyWithHierahcy[i].writer.name + '</strong>';
				strRet += '			<small>' + dateFormatService.getWhenPosted(listReplyWithHierahcy[i].updateDate) + '</small>';
				strRet += '		<button class="btn btn-primary btn=xs pull-right">대댓글달기</button>';
				strRet += '		</div>';
				strRet += '		<p>' + listReplyWithHierahcy[i].content + '</p>';
				strRet += '	</div>';
				if (listReplyWithHierahcy[i].listReply.length != 0) {
					strRet += printReplyOfReplyByRecursion(listReplyWithHierahcy[i].listReply, true);		
				}
				strRet += '</li>';
			}
		if (wrapWithUl) {
			strRet += "</ul>";	
		}
		return strRet;
	}
	
	//대댓글 신규 용도의 모달 창 열기
	$("#btnOpenReplyModalForNew").on("click", function(e) {
		modalReply.data("original_id", postId);
		modalReply.data("display_target", null);
		showModalForCreate();
	});
	
	// 대댓글 신규 용도 모달 창 열기. 자손 결합자
	ulReply.on("click", "li button", function(e) {
		
		var clickedLi = $(this).closest("li");
		modalReply.data("original_id", clickedLi.data("reply_id"));

		//추가 버튼을 누른 대대댓이 포함된 댓글
		var grandFather = clickedLi.parents("#ulReply li").last();
		if(grandFather.length == 0) {
			modalReply.data("display_target", clickedLi);
			
		}else {
			modalReply.data("display_target", grandFather);
		}
		
 		showModalForCreate();
	});
	
	
	function showModalForCreate(){
		// 모달에 들어 있는 모든 내용 청소	
		modalReply.find("input").val("");
		// 신규 댓글 달기 시에는 등록일자는 Daefault 처리. 따라서 보여줄 필요가 없어요.
		inputReplyDate.closest("div").hide();
		
		btnModifyReply.hide();
		btnRemoveReply.hide();
		btnRegisterReply.show();
		modalReply.modal("show");
	}
	
	$("#btnCloseModal").on("click", function(e) {
		// 모달에 들어 있는 모든 내용 청소	
		modalReply.find("input").val("");
		modalReply.modal("hide");
	});
	
	// 목록에서 특정 댓글을 클릭하면 해당 댓글의 상세 정보를 Ajax-rest로 읽고 이를 모달창에 보여준다.
	// 특정 댓글은 동적으로 추가된 것이기에 이벤트 위임 방식을 활용해야함.
	// "li"를 추가한 것이 이벤트 위임 방식이다.
	ulReply.on("click", "li p", function(e) {
		var clickedLi = $(this).closest("li");
		//추가 버튼을 누른 대대댓이 포함된 댓글
		modalReply.data("display_target", clickedLi.parents("#ulReply li").last());
		
		replyService.getReply(
			clickedLi.data("reply_id"),
			function(replyObj) {
				// 수정 또는 삭제 시 댓글의 아이디가 필요합니다.
				modalReply.data("reply_id",replyObj.id);
				inputReplyContent.val(replyObj.content);
				inputReplyer.val(replyObj.writer.name);
				inputReplyDate.val(dateFormatService.getWhenPosted(replyObj.updateDate));
				inputReplyer.attr("readonly","readonly");
				inputReplyDate.attr("readonly","readonly");
				
				btnModifyReply.show();
				btnRemoveReply.show();
				btnRegisterReply.hide();

				modalReply.modal("show");
			},
			function(errMsg) {
				alert("댓글 조회 오류  : " + errMsg);
		    }
		);
	});
	
	//모달창에서 내용을 사용자가 입력한 다음에 등록 버튼을 누르면 댓글로 등록하고 목록을 1쪽부터 다시 보여줌.
	btnRegisterReply.on("click", function(e) {
		var reply = {
			content : inputReplyContent.val() //사용자가 입력한 정보의 값을 호출
		};
		replyService.addReply(
			modalReply.data("original_id"),
			reply,
			function(resObj){	
				modalReply.find("input").val("");   //모달 내부에 있는 모든 input 요소의 값을 빈 값으로 채움.
				modalReply.modal("hide"); 
				displayUpdatedContents(1);
			},
			function(errMsg) {
				alert("댓글 등록 오류  : " + errMsg);
			}
		);
	});
	
	// 댓글 상세 내용이 모달창에 나타났으며 작성자가 그 내용을 수정하고 수정 버튼을 누르면 DB에 내용을 반영하고
	// 목록으로 돌아온다.
	btnModifyReply.on("click", function(e) {
		replyService.updateReply(
			{
				id : modalReply.data("reply_id"),
				content : inputReplyContent.val()
			},
			function(resultMsg) {
				modalReply.modal("hide"); 
				//댓글을 수정한 경우와 대댓을 수정한 경우로 나누어 반응 시켜야 한다
				displayUpdatedContents(currentPageNum);
			},
			function(errMsg) {
				alert("댓글수정 오류  : " + errMsg);
			}
		);
	});

	// 댓글 상세 내용이 모달창에 나타났으며 작성자가 삭제 버튼을 누르면 DB에 내용을 반영하고
	// 목록으로 돌아온다.
	btnRemoveReply.on("click", function(e) {
	 replyService.removeReply(
			 modalReply.data("reply_id"),
			 function(delResult) {
				 modalReply.modal("hide"); 
				//댓글을 삭제한 경우와 대댓을 삭제한 경우로 나누어 반응 시켜야 한다
				 displayUpdatedContents(currentPageNum);
			 }, 
			 function(errMsg) {
			 alert("댓글 삭제 오류  : " + errMsg);
			 }
		);
	});
    
	/** 수정, 삭제 조회 시에 그 창을 그대로 유지해 줄 것인지 아니면 처음 화면으로 보여줄 것인지를 결정해주는 함수 */
	function displayUpdatedContents(targetPage) {
	displayTargetLi = modalReply.data("display_target");
	//댓글 추가한 경우와 대댓글 추가한 경우로 나누어 반응 시켜야 한다.
	// 등록 성공 시 목록을 다시 보여준다.
	if (displayTargetLi == null){
		showReplyList(targetPage);	
	} else {
		//modalReply.data("display_target", clickedLi.parents("#ulReply li").last());
		//댓글 id확보
		var reply_id = $(displayTargetLi).data("reply_id");
		
		replyService.getReplyListOfReply(
			reply_id,
			//listReplyWithHierahcy : 댓글들이 담겨있는 계층 구조
			function(listReplyWithHierahcy) { //서버로부터 받은 정보를 li로 출력
				// 댓글목록을 클릭시 똑같이 출력이 되는 현상이 발생한다.
				// 이러한 현상을 막기위해 먼저 조회한 결과 UL을 청소하고 댓글 내용만 기억 시키기.
				var ul = $(displayTargetLi).find("ul");
				ul.remove();
				strLiTags = displayTargetLi.html();
			
				strLiTags += printReplyOfReplyByRecursion(listReplyWithHierahcy, true);
				displayTargetLi.html(strLiTags);
			  }, function(errMsg){
				  alert("댓글 조회 오류 : " +errMsg);
			  }		
			);
			//댓글에 댓글이 몇개 달려있는지 count
			replyService.getAllReplyCountOfReply(
				reply_id,
				//listReplyWithHierahcy : 댓글들이 담겨있는 계층 구조
				function(cnt) {
					var iTagForCntDisplay = $(displayTargetLi).find("i");
					var strongTagForCntDisplay = $(displayTargetLi).find("strong");
					if (cnt == 0) {
						if (iTagForCntDisplay.length != 0) {
							$(iTagForCntDisplay).remove();
						}
					} else {
						if (iTagForCntDisplay.length == 0) {
							strongTagForCntDisplay.before("<i>[" + cnt + "]</i>");							
						} else {
							iTagForCntDisplay.html("[" + cnt + "]");
						}
					}
				  }, function(errMsg){
					  alert("댓글 개수 조회 오류 : " +errMsg);
				  }
			);
		}
	}	
	
	//페이징 중 하나를 선택하면 해당 쪽의 댓글 목록을 조회
	replyPaging.on("click", "li a", function(e) {
		e.preventDefault();
		
		var clickedAnchor = $(this);
		currentPageNum = clickedAnchor.attr("href");
		showReplyList(currentPageNum);
	});
 
	 
	 
</script>