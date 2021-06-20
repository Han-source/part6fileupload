select *
  from s_party;
  
 --cartition product. 정보 건수의 곱하기로 건수가 만들어져 가치가 없다.
 select *
  from s_party p, s_contact_point cp;
 
 --join
  select *
  from s_party p, s_contact_point cp
 where p.user_id = cp.user_id;
 
 --outer join(eclipse version)
  select *
  from s_party p, s_contact_point cp
 where p.user_id = cp.user_id(+);

  --outer join(standard version)
  select *
  from s_party p left outer join  s_contact_point cp on p.user_id = cp.user_id; 
  
 
  --회장님의 정보와 연락처 정보를 조회
 select  name, birth_dt, sex, descrim, contact_point_type, info
  from s_party p left outer join  s_contact_point cp on p.user_id = cp.user_id
 where p.user_id='admin';