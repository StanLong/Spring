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
注：备份的数据应该存储到其他的服务器或存储介质中


还原的过程
1、停止数据库
2、清理环境
3、导入备份数据
4、启动数据库
5、binlog恢复

[root@node01 mysqlbackup]# systemctl stop mysqld
[root@node01 mysqlbackup]# rm -rf /var/lib/mysql/*
[root@node01 mysql]# tar -xf /var/mysqlbackup/2019-07-28-mysql-all.tar -C /
[root@node01 mysql]# systemctl start mysqld




LVM备份
如果之前没有准备逻辑卷，需要先进行数据迁移

----------------------------------------------------------------------------------------------
数据迁移，数据迁移之前要先把mysql服务停掉，以保证所有的数据都已经保存到硬盘里
1、准备LVM及文件系统

在虚拟机上额外多准备一块硬盘

[root@node01 ~]# clear
[root@node01 ~]# lsblk
NAME            MAJ:MIN RM  SIZE RO TYPE MOUNTPOINT
sda               8:0    0  200G  0 disk 
├─sda1            8:1    0    1G  0 part /boot
└─sda2            8:2    0  199G  0 part 
  ├─centos-root 253:0    0   50G  0 lvm  /
  ├─centos-swap 253:1    0    2G  0 lvm  [SWAP]
  └─centos-home 253:2    0  147G  0 lvm  /home
sdb               8:16   0    1G  0 disk                      --从虚拟机上添加的硬盘
sr0              11:0    1  4.2G  0 rom  

2、把这块新硬盘做成VG
[root@node01 ~]# vgcreate datavg /dev/sdb 
  Physical volume "/dev/sdb" successfully created.
  Volume group "datavg" successfully created
[root@node01 ~]# 

3、创建逻辑卷
[root@node01 ~]# lvcreate -L 500M -n mysql datavg
  Logical volume "mysql" created.
[root@node01 ~]#

[root@node01 ~]# lvscan
  ACTIVE            '/dev/centos/root' [50.00 GiB] inherit
  ACTIVE            '/dev/centos/home' [146.99 GiB] inherit
  ACTIVE            '/dev/centos/swap' [2.00 GiB] inherit
  ACTIVE            '/dev/datavg/mysql' [500.00 MiB] inherit       --上一步创建的
[root@node01 ~]# 

[root@node01 ~]# vgs
  VG     #PV #LV #SN Attr   VSize    VFree  
  centos   1   3   0 wz--n- <199.00g   4.00m
  datavg   1   1   0 wz--n- 1020.00m 520.00m
[root@node01 ~]# 

4、格式化逻辑卷
[root@node01 ~]# mkfs.xfs /dev/datavg/mysql 
meta-data=/dev/datavg/mysql      isize=512    agcount=4, agsize=32000 blks
         =                       sectsz=512   attr=2, projid32bit=1
         =                       crc=1        finobt=0, sparse=0
data     =                       bsize=4096   blocks=128000, imaxpct=25
         =                       sunit=0      swidth=0 blks
naming   =version 2              bsize=4096   ascii-ci=0 ftype=1
log      =internal log           bsize=4096   blocks=855, version=2
         =                       sectsz=512   sunit=0 blks, lazy-count=1
realtime =none                   extsz=4096   blocks=0, rtextents=0
[root@node01 ~]# 

5、将逻辑卷挂在到临时目录
[root@node01 ~]# mount /dev/datavg/mysql /mnt/

6、备份数据
[root@node01 ~]# cp -a /var/lib/mysql/* /mnt/

7、卸载逻辑卷
[root@node01 ~]# umount /mnt/


8、编辑 fstab，加上这么一句
/dev/datavg/mysql       /var/lib/mysql          xfs     defaults        0 0
开机将 /dev/datavg/mysql 挂载到 /var/lib/mysql 不备份 不检测

9、查看
[root@node01 ~]# mount -a
[root@node01 ~]# df
Filesystem               1K-blocks    Used Available Use% Mounted on
/dev/mapper/centos-root   52403200 2381852  50021348   5% /
devtmpfs                    488784       0    488784   0% /dev
tmpfs                       499848       0    499848   0% /dev/shm
tmpfs                       499848    6836    493012   2% /run
tmpfs                       499848       0    499848   0% /sys/fs/cgroup
/dev/mapper/centos-home  154057220   32944 154024276   1% /home
/dev/sda1                  1038336  127468    910868  13% /boot
tmpfs                        99972       0     99972   0% /run/user/0
/dev/mapper/datavg-mysql    508580  162720    345860  32% /var/lib/mysql


10、改权限，重启mysql服务
[root@node01 mapper]# chown -R mysql.mysql /var/lib/mysql
[root@node01 mapper]# systemctl restart mysqld

--不改权限可能会报这个错
2019-07-30T15:33:09.552248Z 0 [ERROR] MYSQL_BIN_LOG::open_purge_index_file failed to open register  file.
2019-07-30T15:33:09.552259Z 0 [ERROR] MYSQL_BIN_LOG::open_index_file failed to sync the index file.
2019-07-30T15:33:09.552267Z 0 [ERROR] Aborting

到这一步，数据迁移结束 保存到 /var/lib/mysql 都已迁移到 /dev/mapper/datavg-mysql

------------------------------------------------------------------------------------------------------------------------------------


LVM 快照被封流程
1、加全局读锁
mysql> flush tables with read lock;
Query OK, 0 rows affected (0.00 sec)

2、创建快照
--学习 lvcreate
