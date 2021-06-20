drop SEQUENCE seq_master;
drop table s_detail;
drop table s_master;

CREATE SEQUENCE seq_master;

create table s_master(
        id     		  number(9)	       primary key ,
        name          varchar2(100)    not null,
        reg_dt			timestamp		default sysdate not null
);

create table s_detail (
   m_id         number(9),
   id     		number(9)	       primary key,
   info         varchar2(50)
 
);