EXPLAIN SELECT * FROM sys_user WHERE `USER_NAME` LIKE '%高%';
#EXPLAIN SELECT * FROM sys_user WHERE `USER_NAME` LIKE '高%';
EXPLAIN SELECT ID FROM sys_user WHERE `USER_NAME` LIKE '%高%';