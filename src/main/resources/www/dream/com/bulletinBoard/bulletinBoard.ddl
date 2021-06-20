--offset : 몇개를 버릴것인가. fetch : 몇개를 읽을것인가
select *
from s_post
order by id desc
offset 20 row fetch first 10 rows only;


-------------------------------


drop SEQUENCE seq_reply_id;
DROP SEQUENCE seq_post_id; 
drop table s_post;
drop table s_board;

--id, name, description
create table s_board(
   id			number(9) primary key,
   name			varchar2(100),
   description	varchar2(1000),
   reg_dt		timestamp		default sysdate not null,
   upt_dt		timestamp		default sysdate not null
);

insert into s_board(id, name, description)
	values(1, '공지사항', '드림 회사에서 드리는 공지사항');

insert into s_board(id, name, description)
	values(2, 'FAQ', '자주 문의되는 사항에 대한 답변들');

insert into s_board(id, name, description)
	values(3, '자유게시판', '회원이면 누구나 자유롭게 자신의 의견을 밝힐 수 있다');

	
CREATE SEQUENCE seq_reply_id;
	
-- id, writer_id, content, reg_dt, upt_dt, descrim, board_id, title, read_cnt, like_cnt, dislike_cnt
create table s_reply(
   id				varchar2(4000)	primary key,	
   writer_id		varchar2(10)	REFERENCES s_party(user_id),
   content			varchar2(4000),
   reg_dt			timestamp		default sysdate not null,
   upt_dt			timestamp		default sysdate not null,
   descrim        varchar2(10)     	  not null, --reply, post
   --descrim이 post일 경우 사용되는 정보들
   board_id			number(9)		REFERENCES s_board(id),
   title			varchar2(100),
   read_cnt			number(9)		default 0,
   like_cnt			number(9)		default 0,
   dislike_cnt		number(9)		default 0
);
create index idx_reply_board_id on s_reply(board_id, id);
drop index idx_reply_board_id;

--Oracle 자료형 선택 시 
--int, long -> number(9), 19
--date -> 년 월 일(date), 일시(timestamp)
--boolean ->char(1)


DROP TABLE s_contact_point;
DROP TABLE s_party;
DROP TABLE s_contact_point_type;

--Sequence
DROP SEQUENCE seq_party_id; 
CREATE SEQUENCE seq_party_id START WITH -990000000 MINVALUE -990000000 ; 

--party_type, description
create table s_party_type(
   party_type         char(10),
   description        varchar2(100)
);

insert into s_party_type(party_type, description)
   values('Admin', '관리자');
insert into s_party_type(party_type, description)
   values('Manager', '운영자');
insert into s_party_type(party_type, description)
   values('User', '사용자');   
--user_id, user_pwd, name, birth_dt, sex, enabled, reg_dt, upt_dt, descrim
create table s_party(
        user_id       varchar2(10)     primary key ,
  	    user_pwd      varchar2(100)    not null,   --100 : 암호화된 결과물까지 고려
        name          varchar2(100)    not null,   --100 : 전지구적인 사람의 이름 길이까지 고려
        birth_dt       Date,                  -- 생일 년월일
        sex        	   char(1)      	  default 1 not null,   --true male, false female
        enabled        char(1)   	      default 1,
        reg_dt         timestamp     	  default sysdate not null,   --등록시점
        upt_dt         timestamp          default sysdate not null,   --수정시점
        descrim        varchar2(10)     	  not null
      --Admin용 속성 정의함
      --Manager용 속성 정의함
      --User용 속성 정의함
);

insert into s_party(user_id, user_pwd, name, birth_dt, sex, enabled, descrim)
   values('admin', '1234', '김이박', '1999-01-30', '1', '1', 'Admin');
insert into s_party(user_id, user_pwd, name, birth_dt, sex, enabled, descrim)
   values('mana1', '1234', '김이홍', '2010-01-30', '0', '1', 'Manager');
insert into s_party(user_id, user_pwd, name, birth_dt, sex, enabled, descrim)
   values('mana2', '1234', '김이정', '2005-01-30', '1', '1', 'Manager');   
insert into s_party(user_id, user_pwd, name, birth_dt, sex, enabled, descrim)
   values('hong', '1234', '홍길동', '2005-01-30', '1', '1', 'User');
insert into s_party(user_id, user_pwd, name, birth_dt, sex, enabled, descrim)
   values('lee', '1234', '이순신', '2005-01-30', '0', '1', 'User');
insert into s_party(user_id, user_pwd, name, birth_dt, sex, enabled, descrim)
   values('ghost', '1234', '고스트', '2005-01-30', '0', '0', 'User');

-- 각 행위자별 여러 연락처 정보 관리
--party_type, description
create table s_contact_point_type(
   contact_point_type   char(10),
   description         varchar2(100)
);

insert into s_contact_point_type(contact_point_type, description)
   values('address', '주소지');   
insert into s_contact_point_type(contact_point_type, description)
   values('phoneNum', '주소지에 있는 전화번호');   
insert into s_contact_point_type(contact_point_type, description)
   values('mobileNum', '핸드폰 번호');   
   
--연락처. 회원 탈퇴시 정보는 실제 삭제 처리.
--user_id, contact_point_type, info, reg_dt, upt_dt
create table s_contact_point (
   user_id         varchar2(10),
   contact_point_type   char(10),
   info            varchar2(50),-- 연락처 정보
   reg_dt         timestamp      default sysdate not null,   --등록시점
   upt_dt         timestamp      default sysdate not null,   --수정시점
   primary key (user_id, contact_point_type),
   CONSTRAINT fk_cp_party FOREIGN KEY (user_id) REFERENCES s_party(user_id)
);

admin...address 313호 2020.01.01 2025.01.01
admin...address 2025.01.01 9999.12.31

 	from_dt         timestamp      default sysdate not null,   --등록시점
	thru_dt         timestamp      default sysdate not null,   --수정시점
  

insert into s_contact_point(user_id, contact_point_type, info)
   values('admin', 'address', '서울시 금천구 가산동 312호');
insert into s_contact_point(user_id, contact_point_type, info)
   values('admin', 'phoneNum', '02-2685-8654');
insert into s_contact_point(user_id, contact_point_type, info)
   values('admin', 'mobileNum', '010-9007-6293');   
insert into s_contact_point(user_id, contact_point_type, info)
   values('mana1', 'address', '서울시 금천구 가산동 314호');
insert into s_contact_point(user_id, contact_point_type, info)
   values('mana1', 'phoneNum', '02-111-1111');
insert into s_contact_point(user_id, contact_point_type, info)
   values('mana1', 'mobileNum', '010-222-1165');   
insert into s_contact_point(user_id, contact_point_type, info)
   values('mana2', 'address', '세종시 금천구 가산동 316호');
insert into s_contact_point(user_id, contact_point_type, info)
   values('mana2', 'phoneNum', '02-654-2341');
insert into s_contact_point(user_id, contact_point_type, info)
   values('mana2', 'mobileNum', '010-2355-6870');      
insert into s_contact_point(user_id, contact_point_type, info)
   values('hong', 'address', '한양 남산동 333번지');
insert into s_contact_point(user_id, contact_point_type, info)
   values('lee', 'address', '조선 충청도 아산');

--Code Table. 