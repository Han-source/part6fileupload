drop table sm_ht2post
drop table s_hashtag;
drop SEQUENCE seq_hashtag_id;

truncate table sm_ht2post;

CREATE SEQUENCE seq_hashtag_id;
--단어 저장 DB
-- id, super_id, hashtag, description
create table s_hashtag(
   id			number(9) primary key,
   --추상 개념으로 통합 검색에서 활용하기 위하여
   super_id		number(9) references s_hashtag(id),
   hashtag		varchar2(100), --단어
   description	varchar2(1000)
);
create unique index uidx_hashtag on s_hashtag(hashtag);	 
--단어 : 사랑, 우정
--행복

--sm : 관계테이블
--hashtag_id, post_id, occur_cnt
create table sm_ht2post(
	hashtag_id		number(9),
	post_id			varchar2(4000),
	occur_cnt		number(9),
	primary key(hashtag_id, post_id)
);

--개인화서비스. Personalization
create table sm_ht2party(
	hashtag_id		number(9),
	user_id			varchar2(10),
	occur_cnt		number(9),
	latest_use_time	timestamp		default sysdate not null,
	primary key(user_id, hashtag_id)
);

--sequence를 활용하여 원하는 개수만큼 숫자형 id 만들어 내기

--되는거
CREATE OR REPLACE FUNCTION getMultiId(cnt number) RETURN varchar2
IS
   seq_val number(9);
   strRet varchar2(4000) := '';
BEGIN
   FOR i in 1..cnt LOOP
      select seq_hashtag_id.nextval into seq_val from dual;
      strRet := strRet || ',' || seq_val;
   END LOOP;
   return ltrim(strRet, ',');   
END; 
select getMultiId(5) from dual; 