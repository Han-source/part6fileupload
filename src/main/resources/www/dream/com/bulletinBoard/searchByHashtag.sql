create table s_hashtag(
   id			number(9) primary key,
   --추상 개념으로 통합 검색에서 활용하기 위하여
   super_id		number(9) references s_hashtag(id),
   hashtag		varchar2(100), --단어
   description	varchar2(1000)
);

create table sm_ht2post(
	hashtag_id		number(9),
	post_id			varchar2(4000),
	occur_cnt		number(9),
	primary key(hashtag_id, post_id)
);

create table s_post(
   id				varchar2(4000)	primary key,	
   board_id			number(9)		REFERENCES s_board(id),
   writer_id		varchar2(10)	REFERENCES s_party(user_id),
   title			varchar2(100),
   content			varchar2(4000),
   read_cnt			number(9)		default 0,
   like_cnt			number(9)		default 0,
   dislike_cnt		number(9)		default 0,
   reg_dt			timestamp		default sysdate not null,
   upt_dt			timestamp		default sysdate not null
);

--검색어 기반 게시글 조회
--검색어 예시 : 피자, 객체
select id
  from s_hashtag
 where hashtag in ('피자', '객체') 

--검색어 피자, 객체 로 이 단어가 적용된 게시글 id를 찾아라 JOIN
 select distinct post_id
  from s_hashtag m, sm_ht2post w
 where hashtag in ('피자', '객체')
 	and w.hashtag_id = m.id;
 
--검색어 피자, 객체 로 이 단어가 적용된 게시글 id를 찾아라 IN  
select distinct post_id
  from sm_ht2post
 where hashtag_id in (
 select id
  from s_hashtag
 where hashtag in ('피자', '객체') 
 )

 --검색어 피자, 객체 로 이 단어가 적용된 게시글과 작성자 정보를 찾아라
select distinct p.*, w.*
  from s_hashtag h, sm_ht2post m, s_post p, s_party w
 where h.hashtag in ('지향', '강아지')
   and m.hashtag_id = h.id
   and p.id = m.post_id
   and p.writer_id = w.user_id

--검색어 피자, 객체 로 이 단어가 적용된 게시글과 작성자 정보를 찾아서 
--해당 페이지의 내용을 최신 작성글 순서로 출력하시오
select distinct p.*, w.*
  from s_hashtag h, sm_ht2post m, s_post p, s_party w
 where h.hashtag in ('피자', '객체')
   and m.hashtag_id = h.id
   and p.id = m.post_id
   and p.writer_id = w.user_id
   order by p.id desc
	  	offset  (0) * 10 row fetch first 10 rows only
	  	
--단어 사이의 상속 구조를 이용한 강력 검색 기능
insert into s_hashtag(id, hashtag)
	values(-1001, 'element');	  	
insert into s_hashtag(id, hashtag)
	values(-1000, 'feature');
	
update s_hashtag 
   set super_id = -1000
 where id in(15, 19);

update s_hashtag 
   set super_id = -1001
 where id = -1000;
 
--단어 'element'를 s_hashtag에서 검색하면 그 결과는 {'element', 'feature', '변수', '메소드'}이다
select hashtag, id
  from s_hashtag
  start with hashtag ='element'
  connect by prior id = super_id;
  
--검색어 피자, 객체 로 이 단어가 계층적으로 적용된 게시글과 작성자 정보를 찾아서 
--해당 페이지의 내용을 최신 작성글 순서로 출력하시오
  
select distinct p.*, w.*
  from s_hashtag h, sm_ht2post m, s_post p, s_party w
 where h.id in (
 		SELECT id
  		 FROM s_hashtag
  		 start with hashtag in('element','피자', '객체')
   		CONNECT BY PRIOR id = super_id)
   and m.hashtag_id = h.id
   and p.id = m.post_id
   and p.writer_id = w.user_id
   order by p.id desc
	 OFFSET 0 ROWS FETCH FIRST 10 ROW ONLY
	  	
	  	
	  	
select count(distinct p.id)
		from s_hashtag h, sm_ht2post m, s_post p
	  where h.id in (
			SELECT id
			  FROM s_hashtag
			start with hashtag in('element','피자', '객체')
		CONNECT BY PRIOR id = super_id)
		and m.hashtag_id = h.id
		and p.id = m.post_id
		and p.board_id = 3
		 	  	