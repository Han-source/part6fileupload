/**
* 모듈패턴
	이벤트, DOM, ajax 처리 등을 하나의 jsp에 넣기에는 복잡해짐, 유지보수성 저하
	이에 javascript를 별도 file로 만들어 분할 정복.
CallBack 함수 : 특정 이벤트시에 이를 불러준다.
*/
console.log("reply.js 연동 성공");

/** 함수 정의 영역 */

var dateFormatService = (function() {
	//책 기준 418쪽 스타일 포기. 유튜브 스타일 적용함.
	function convertToWhen(timeVal) {
		var now = new Date();
		var gapInMilliSec = now.getTime() - timeVal;
		//초로 먼저 먼저 환산하기
		var gapInSec = gapInMilliSec / 1000; 
		if(gapInSec < 60)
			return [Math.ceil(gapInSec), '초 전'].join('');
			
		var gapInMin = gapInSec / 60;
		if(gapInMin < 60)
			return [Math.ceil(gapInMin), '분 전'].join(''); 
	
		var gapInHour = gapInMin / 60;
		if(gapInHour < 24)
			return [Math.ceil(gapInHour), '시간 전'].join('');
		
		
		var gapInDay = gapInHour / 24;
		if(gapInDay < 7)
			return [Math.ceil(gapInDay), '일 전'].join('');
			
		var gapInWeek = gapInDay / 7;
		if(gapInWeek < 5)
			return [Math.ceil(gapInDay), '주 전'].join('');
			
		var gapInMon = gapInDay / 30.5;
		if(gapInMon < 12)
			return [Math.ceil(gapInMon), '달 전'].join('');
			
		var gapInYear = gapInDay / 365;
			return [Math.ceil(gapInYear), '년 전'].join('');
	
	}
	

	return {
		getWhenPosted:convertToWhen
	};
})();