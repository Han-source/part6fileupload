<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="../includes/header.jsp"%>

<!-- Begin Page Content -->
<div class="container-fluid">

	<!-- DataTales Example -->
	<div class="card shadow mb-4">
		<div class="card-body">
			<%@ include file="./includes/postCommon.jsp"%>

			<button data-oper='modify' class="btn-primary">수정</button>
			<button data-oper='list' class="btn-secondary">목록</button>

			<form id="frmOper" action="/post/modifyPost" method="get">
				<input type="hidden" name="boardId" value="${boardId}">
				 <input type="hidden" id="postId" name="postId" value="${post.id}">
				<input type="hidden" name="pageNumber" value="${pagenation.pageNumber}">
				<input type="hidden" name="amount" value="${pagenation.amount}">
				 <input type="hidden" name="searching" value="${pagenation.searching}" />
			</form>
		</div>
		<%@ include file="./includes/replyManagement.jsp"%>		

</div>
</div>
<!-- /.container-fluid -->




<!-- End of 댓글 입력 모달창 -->
</div>
<!-- End of Main Content -->
<%@ include file="../includes/footer.jsp"%>

<!--  수정과 목록버튼 눌렀을떄의 이벤트처리 -->
<script type="text/javascript">
	$(document).ready(function() {
		var frmOper = $("#frmOper");
		$("button[data-oper='modify']").on("click", function() {
			$("#frmOper").submit();
		});

		$("button[data-oper='list']").on("click", function() {
			$("#frmOper").find("#postId").remove();
			$("#frmOper").attr("action", "/post/listBySearch").submit();
		});

	});
</script>