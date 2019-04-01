SELECT ID FROM sys_user ORDER BY RAND() LIMIT 10;
SELECT ID FROM sys_user t1 join ( SELECT RAND() * (SELECT MAX(ID) from sys_user) AS nid ) t2 ON t1.ID > t2.nid LIMIT 10;