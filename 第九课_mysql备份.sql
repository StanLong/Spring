最原始的数据备份：
tar备份数据库：备份期间，数据不可用

备份过程：【完全物理备份】
1、停止数据库
2、tar备份数据库
3、启动数据库

[root@node01 ~]# systemctl stop mysqld
[root@node01 ~]# make dir /var/mysqlbackup
[root@node01 ~]# tar -cf /var/mysqlbackup/`date +%F`-mysql-all.tar /var/lib/mysql
tar: Removing leading `/' from member names
[root@node01 ~]# cd /var/mysqlbackup/
[root@node01 mysqlbackup]# ll
total 123960
-rw-r--r-- 1 root root 126935040 Jul 28 09:05 2019-07-28-mysql-all.tar


还原的过程
1、停止数据库
2、清理环境
3、导入备份数据
4、启动数据库
5、binlog恢复


