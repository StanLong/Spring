error log   错误日志    排错   /var/log/mysqld.log(默认开启)
bin log     二进程日志  备份   增量备份 
relay log   中继日志    复制   接收 replication master
slow log    慢查询日志  调优   查询时间超过指定值


按ctrl+z退出mysql，按fg返回mysql


修改mysql日志配置文件
vim /etc/my.cnf

log-error=/var/log/mysqld.log                         --错误日志
pid-file=/var/run/mysqld/mysqld.pid
log-bin=/var/log/mysqlbinlog/master                     --二进制日志   （配置失败，mysql无法重启）
server-id=11
slow_query_log=1                                      --开启慢查询日志
slow_query_log_file=/var/log/mysql/slow.log
long_query_time=3


配置完成之后重启 mysql
systemctl restart mysqld

测试慢查询
select benchmark(50000000, 2*3);