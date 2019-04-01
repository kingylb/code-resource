EXPLAIN SELECT SQL_NO_CACHE count(*) FROM case_info WHERE ID NOT in (SELECT ID from sys_user);
EXPLAIN SELECT SQL_NO_CACHE count(*) FROM case_info WHERE NOT EXISTS (SELECT * from sys_user WHERE case_info.ID = sys_user.ID);
EXPLAIN SELECT SQL_NO_CACHE count(*) FROM case_info LEFT JOIN sys_user ON case_info.ID = sys_user.ID WHERE sys_user.ID is NULL;