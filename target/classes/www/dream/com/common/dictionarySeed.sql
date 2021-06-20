/**  truncate 처치하고 있던 공간까지 함께 삭제됩니다.  */
truncate table s_id_seed;
drop table s_id_seed;


create table s_id_seed(
	seq_id		number(19)		primary key,
	seed		char(5)
);


CREATE OR REPLACE FUNCTION get_id(intSeed number) RETURN char
IS
	ret char(5);
BEGIN
	select seed into ret from s_id_seed where seq_id = intSeed;
	RETURN ret;
END;

select get_id(15) from dual;
