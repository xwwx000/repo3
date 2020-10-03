/*污水处理数据库*/

DROP DATABASE IF EXISTS mud;
CREATE DATABASE mud CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';
USE mud;
SET names 'utf8';
/***********************************
类型:表
表名:t_comm_user
功能:用户表
************************************/
DROP TABLE IF EXISTS t_comm_user;
CREATE TABLE t_comm_user(
	id 					INT(4) UNSIGNED NOT NULL AUTO_INCREMENT,				/*自增ID*/
 	userid 				VARCHAR(20) NOT NULL,									/*用户编码*/
    username			VARCHAR(20) NOT NULL DEFAULT '',  						/*用户名称*/
    deptcode 			VARCHAR(20) NOT NULL DEFAULT '',						/*发货单位*/
    addr 				VARCHAR(40),											/*用户地址*/
    tel 				VARCHAR(20),											/*用户电话*/ 
    sex 				TINYINT DEFAULT 1,										/*用户性别*/
    userpwd				VARCHAR(128) NOT NULL,									/*密码*/
    email 				VARCHAR(30),											/*邮箱*/
    idcard 				VARCHAR(30),											/*身份证*/
    birthday 			DATE,													/*生日*/
    degree 				TINYINT NOT NULL DEFAULT 3,								/*学历*/
 	groupid 			TINYINT NOT NULL DEFAULT 1,								/*岗位id*/
    msg 				VARCHAR(128),											/*备注*/
    pagenum 			TINYINT DEFAULT 15,										/*每页显示记录数*/
    status 				TINYINT DEFAULT 0,										/*状态 0:正常 1:停用*/
    regtime 			DATETIME DEFAULT CURRENT_TIMESTAMP,      				/*注册时间*/
    lastlogintime 		DATETIME,												/*最后登陆时间*/
    lastloginip 		VARCHAR(20),											/*最后登陆地址*/
    PRIMARY KEY (ID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO t_comm_user (DEPTCODE,USERID,USERNAME,USERPWD,GROUPID) VALUES (1,'admin','管理员','CF79AE6ADDBA60AD018347359BD144D2',1);
INSERT INTO t_comm_user (DEPTCODE,USERID,USERNAME,USERPWD,GROUPID) VALUES (11,'ssw','沈水湾操作员','CF79AE6ADDBA60AD018347359BD144D2',2);
INSERT INTO t_comm_user (DEPTCODE,USERID,USERNAME,USERPWD,GROUPID) VALUES (12,'xnh','仙女河操作员','CF79AE6ADDBA60AD018347359BD144D2',2);
INSERT INTO t_comm_user (DEPTCODE,USERID,USERNAME,USERPWD,GROUPID) VALUES (13,'zxwn','振兴污泥操作员','CF79AE6ADDBA60AD018347359BD144D2',2);
INSERT INTO t_comm_user (DEPTCODE,USERID,USERNAME,USERPWD,GROUPID) VALUES (14,'zxwn2','振兴污泥2操作员','CF79AE6ADDBA60AD018347359BD144D2',2);
INSERT INTO t_comm_user (DEPTCODE,USERID,USERNAME,USERPWD,GROUPID) VALUES (15,'zxwncw','振兴污泥2操作员','CF79AE6ADDBA60AD018347359BD144D2',2);
INSERT INTO t_comm_user (DEPTCODE,USERID,USERNAME,USERPWD,GROUPID) VALUES (16,'zxwncw2','振兴污泥场外2操作员','CF79AE6ADDBA60AD018347359BD144D2',2);
INSERT INTO t_comm_user (DEPTCODE,USERID,USERNAME,USERPWD,GROUPID) VALUES (1,'czy01','集团操作员','CF79AE6ADDBA60AD018347359BD144D2',2);

INSERT INTO t_comm_user (DEPTCODE,USERID,USERNAME,USERPWD,GROUPID) VALUES (11,'sswadmin','沈水湾管理员','CF79AE6ADDBA60AD018347359BD144D2',4);
INSERT INTO t_comm_user (DEPTCODE,USERID,USERNAME,USERPWD,GROUPID) VALUES (12,'xnhadmin','仙女河管理员','CF79AE6ADDBA60AD018347359BD144D2',4);
ALTER TABLE t_comm_user ADD unique(userid);

/***********************************
类型:表
表名:t_comm_module
功能:菜单表
************************************/
DROP TABLE IF EXISTS t_comm_module;
CREATE TABLE t_comm_module(
	id INT(4) 		UNSIGNED NOT NULL AUTO_INCREMENT,	/*自增ID*/
	moduleid 		VARCHAR(6)  NOT NULL,				/*模块编码*/
	moduledesc 	VARCHAR(255) DEFAULT NULL,				/*模块名称*/
	filename 		VARCHAR(255) DEFAULT NULL,			/*文件名称*/
	imagename 	VARCHAR(255) DEFAULT NULL,				/*模块图片*/
	target 			VARCHAR(255) DEFAULT NULL,			/*模块链接*/
	levelid 			TINYINT NOT NULL,				/*模块界别1,2,3*/
	toplevelid 		INT NOT NULL,						/*上级模块*/
	visible 			TINYINT NOT NULL DEFAULT 1,		/*是否显示 1显示*/
	status 			TINYINT NOT NULL DEFAULT 0,			/*状态*/
	PRIMARY KEY (ID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE t_comm_module ADD unique(MODULEID);
INSERT INTO t_comm_module (MODULEID,MODULEDESC,FILENAME,IMAGENAME,TARGET,LEVELID,TOPLEVELID,VISIBLE) VALUES (11,'运输记录查询','inf.jsp','inf.gif','http://inf.jsp',1,0,1);
INSERT INTO t_comm_module (MODULEID,MODULEDESC,FILENAME,IMAGENAME,TARGET,LEVELID,TOPLEVELID,VISIBLE) VALUES (1121,'运输记录查询','pinf.jsp','psw.gif','http://inf.jsp',2,11,1);
INSERT INTO t_comm_module (MODULEID,MODULEDESC,FILENAME,IMAGENAME,TARGET,LEVELID,TOPLEVELID,VISIBLE) VALUES (112131,'记录查询','记录查询','psw.gif','order/orderList',3,1121,1);
INSERT INTO t_comm_module (MODULEID,MODULEDESC,FILENAME,IMAGENAME,TARGET,LEVELID,TOPLEVELID,VISIBLE) VALUES (112132,'遗漏查询','遗漏查询','psw.gif','order/orderOmitList',3,1121,1);
INSERT INTO t_comm_module (MODULEID,MODULEDESC,FILENAME,IMAGENAME,TARGET,LEVELID,TOPLEVELID,VISIBLE) VALUES (112138,'封签数据统计','封签数据统计','psw.gif','order/orderrfidcount',3,1121,1);
INSERT INTO t_comm_module (MODULEID,MODULEDESC,FILENAME,IMAGENAME,TARGET,LEVELID,TOPLEVELID,VISIBLE) VALUES (1122,'运输数据维护','pinf.jsp','psw.gif','http://inf.jsp',2,11,1);
INSERT INTO t_comm_module (MODULEID,MODULEDESC,FILENAME,IMAGENAME,TARGET,LEVELID,TOPLEVELID,VISIBLE) VALUES (112231,'运输数据补录','运输数据补录','psw.gif','order/orderManualList',3,1122,1);
INSERT INTO t_comm_module (MODULEID,MODULEDESC,FILENAME,IMAGENAME,TARGET,LEVELID,TOPLEVELID,VISIBLE) VALUES (112232,'遗漏数据设置','遗漏数据设置','psw.gif','order/orderOmitSetList',3,1122,1);
INSERT INTO t_comm_module (MODULEID,MODULEDESC,FILENAME,IMAGENAME,TARGET,LEVELID,TOPLEVELID,VISIBLE) VALUES (1129,'系统维护','pinf.jsp','psw.gif','http://inf.jsp',2,11,1);
/*INSERT INTO t_comm_module (MODULEID,MODULEDESC,FILENAME,IMAGENAME,TARGET,LEVELID,TOPLEVELID,VISIBLE) VALUES (112931,'操作用户维护','操作用户维护','psw.gif','user/userList',3,1129,1);*/
INSERT INTO t_comm_module (MODULEID,MODULEDESC,FILENAME,IMAGENAME,TARGET,LEVELID,TOPLEVELID,VISIBLE) VALUES (112932,'PDA用户维护','PDA用户维护','psw.gif','pda/pdaUserList',3,1129,1);
INSERT INTO t_comm_module (MODULEID,MODULEDESC,FILENAME,IMAGENAME,TARGET,LEVELID,TOPLEVELID,VISIBLE) VALUES (112933,'MAC地址维护','MAC地址维护','psw.gif','pda/pdamacaddrlist',3,1129,1);
INSERT INTO t_comm_module (MODULEID,MODULEDESC,FILENAME,IMAGENAME,TARGET,LEVELID,TOPLEVELID,VISIBLE) VALUES (112934,'发货单位维护','发货单位维护','psw.gif','order/sgoodsdeptlist',3,1129,1);
INSERT INTO t_comm_module (MODULEID,MODULEDESC,FILENAME,IMAGENAME,TARGET,LEVELID,TOPLEVELID,VISIBLE) VALUES (112935,'收货单位维护','收货单位维护','psw.gif','order/rgoodsdeptlist',3,1129,1);
INSERT INTO t_comm_module (MODULEID,MODULEDESC,FILENAME,IMAGENAME,TARGET,LEVELID,TOPLEVELID,VISIBLE) VALUES (112936,'标签TID维护','标签TID维护','psw.gif','pda/tid',3,1129,1);
INSERT INTO t_comm_module (MODULEID,MODULEDESC,FILENAME,IMAGENAME,TARGET,LEVELID,TOPLEVELID,VISIBLE) VALUES (112960,'PDA软件下载','PDA软件下载','psw.gif','pda/download',3,1129,1);
INSERT INTO t_comm_module (MODULEID,MODULEDESC,FILENAME,IMAGENAME,TARGET,LEVELID,TOPLEVELID,VISIBLE) VALUES (112961,'修改订单','修改订单','psw.gif','order/orderUpdateList',3,1129,1);
INSERT INTO t_comm_module (MODULEID,MODULEDESC,FILENAME,IMAGENAME,TARGET,LEVELID,TOPLEVELID,VISIBLE) VALUES (112970,'日志查询','日志查询','psw.gif','log/loglist',3,1129,1);
/***********************************
类型:表
表名:t_comm_dept
功能:部门表
************************************/
DROP TABLE IF EXISTS t_comm_dept;
CREATE TABLE t_comm_dept(
		deptcode				VARCHAR(20)		NOT NULL,				/*部门代码(每层2位)*/
		deptname				VARCHAR(50)		NOT NULL,				/*部门名称*/
		deptdesc				VARCHAR(50),							/*部门描述*/
		upcode					VARCHAR(20)		NOT NULL,				/*上级部门代码*/
		scopecode				VARCHAR(20)		NOT NULL,				/*范围码*/
		manager					VARCHAR(20),							/*责任人*/
		seq						TINYINT DEFAULT 0,						/*序号*/
		cstatus					TINYINT DEFAULT 0,						/*状态 0:正常 1:停用*/
    PRIMARY KEY (DEPTCODE)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO t_comm_dept (SEQ,DEPTCODE,DEPTNAME,UPCODE,SCOPECODE,MANAGER) VALUES (0,'1','国电东北环保产业集团有限公司','-1','1','责任人');
INSERT INTO t_comm_dept (SEQ,DEPTCODE,DEPTNAME,UPCODE,SCOPECODE,MANAGER) VALUES (1,'11','沈水湾','1','11','责任人');
INSERT INTO t_comm_dept (SEQ,DEPTCODE,DEPTNAME,UPCODE,SCOPECODE,MANAGER) VALUES (2,'12','仙女河','1','12','责任人');
INSERT INTO t_comm_dept (SEQ,DEPTCODE,DEPTNAME,UPCODE,SCOPECODE,MANAGER) VALUES (3,'13','振兴污泥','1','13','责任人');
INSERT INTO t_comm_dept (SEQ,DEPTCODE,DEPTNAME,UPCODE,SCOPECODE,MANAGER) VALUES (4,'14','振兴污泥2号','1','13','责任人');
INSERT INTO t_comm_dept (SEQ,DEPTCODE,DEPTNAME,UPCODE,SCOPECODE,MANAGER) VALUES (5,'15','振兴污泥场外','1','15','责任人');
INSERT INTO t_comm_dept (SEQ,DEPTCODE,DEPTNAME,UPCODE,SCOPECODE,MANAGER) VALUES (6,'16','振兴污泥场外2','1','16','责任人');
/***********************************
类型:表
表名:t_comm_usergroup
功能:工作组表
************************************/
DROP TABLE IF EXISTS t_comm_usergroup;
CREATE TABLE t_comm_usergroup(
	groupid 		INT(4) UNSIGNED NOT NULL AUTO_INCREMENT,	/*自增ID*/
	groupdesc 	VARCHAR(30) DEFAULT NULL,						/*组名称*/
	PRIMARY KEY (GROUPID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO t_comm_usergroup (groupdesc) VALUES ('系统管理员');
INSERT INTO t_comm_usergroup (groupdesc) VALUES ('操作员');
INSERT INTO t_comm_usergroup (groupdesc) VALUES ('领导查询');
INSERT INTO t_comm_usergroup (groupdesc) VALUES ('操作管理员');
/***********************************
类型:表
表名:t_comm_useringroup
功能:工作组与用户对照表
************************************/
DROP TABLE IF EXISTS t_comm_useringroup;
CREATE TABLE t_comm_useringroup(
	userid 		VARCHAR(40) NOT NULL, 		/*用户编码*/
	groupid 	INT NOT NULL,				/*组编码*/
	PRIMARY KEY (userid,groupid)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO t_comm_useringroup (userid,groupid) VALUES ('admin',1);
INSERT INTO t_comm_useringroup (userid,groupid) VALUES ('ssw',2);
INSERT INTO t_comm_useringroup (userid,groupid) VALUES ('xnh',2);
INSERT INTO t_comm_useringroup (userid,groupid) VALUES ('zxwn',2);
INSERT INTO t_comm_useringroup (userid,groupid) VALUES ('zxwn2',2);
INSERT INTO t_comm_useringroup (userid,groupid) VALUES ('zxwncw',2);
INSERT INTO t_comm_useringroup (userid,groupid) VALUES ('zxwncw2',2);
INSERT INTO t_comm_useringroup (userid,groupid) VALUES ('czy01',3);

INSERT INTO t_comm_useringroup (userid,groupid) VALUES ('sswadmin',4);
INSERT INTO t_comm_useringroup (userid,groupid) VALUES ('xnhadmin',4);
/***********************************
类型:表
表名:t_comm_groupmodule
功能:工作组与菜单模块对照表
************************************/
DROP TABLE IF EXISTS t_comm_groupmodule;
CREATE TABLE t_comm_groupmodule(
	groupid 	INT NOT NULL,			/*组编码*/
	moduleid VARCHAR(6) NOT NULL,		/*模块编码*/
	PRIMARY KEY(groupid,moduleid)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO t_comm_groupmodule (GROUPID,MODULEID) VALUES (1,11);
INSERT INTO t_comm_groupmodule (GROUPID,MODULEID) VALUES (1,1121);
INSERT INTO t_comm_groupmodule (GROUPID,MODULEID) VALUES (1,112131);
INSERT INTO t_comm_groupmodule (GROUPID,MODULEID) VALUES (1,112132);
INSERT INTO t_comm_groupmodule (GROUPID,MODULEID) VALUES (1,112138);
INSERT INTO t_comm_groupmodule (GROUPID,MODULEID) VALUES (1,1122);
INSERT INTO t_comm_groupmodule (GROUPID,MODULEID) VALUES (1,112231);
INSERT INTO t_comm_groupmodule (GROUPID,MODULEID) VALUES (1,112232);
INSERT INTO t_comm_groupmodule (GROUPID,MODULEID) VALUES (1,1129);
/*INSERT INTO t_comm_groupmodule (GROUPID,MODULEID) VALUES (1,112931);*/
INSERT INTO t_comm_groupmodule (GROUPID,MODULEID) VALUES (1,112932);
INSERT INTO t_comm_groupmodule (GROUPID,MODULEID) VALUES (1,112933);
INSERT INTO t_comm_groupmodule (GROUPID,MODULEID) VALUES (1,112934);
INSERT INTO t_comm_groupmodule (GROUPID,MODULEID) VALUES (1,112935);
INSERT INTO t_comm_groupmodule (GROUPID,MODULEID) VALUES (1,112936);
INSERT INTO t_comm_groupmodule (GROUPID,MODULEID) VALUES (1,112960);
INSERT INTO t_comm_groupmodule (GROUPID,MODULEID) VALUES (1,112961);
INSERT INTO t_comm_groupmodule (GROUPID,MODULEID) VALUES (1,112970);
INSERT INTO t_comm_groupmodule (GROUPID,MODULEID) VALUES (2,11);
INSERT INTO t_comm_groupmodule (GROUPID,MODULEID) VALUES (2,1121);
INSERT INTO t_comm_groupmodule (GROUPID,MODULEID) VALUES (2,112131);
INSERT INTO t_comm_groupmodule (GROUPID,MODULEID) VALUES (2,112132);
INSERT INTO t_comm_groupmodule (GROUPID,MODULEID) VALUES (2,112960);
INSERT INTO t_comm_groupmodule (GROUPID,MODULEID) VALUES (2,112961);
INSERT INTO t_comm_groupmodule (GROUPID,MODULEID) VALUES (3,11);
INSERT INTO t_comm_groupmodule (GROUPID,MODULEID) VALUES (3,1121);
INSERT INTO t_comm_groupmodule (GROUPID,MODULEID) VALUES (3,112131);
INSERT INTO t_comm_groupmodule (GROUPID,MODULEID) VALUES (3,112132);
INSERT INTO t_comm_groupmodule (GROUPID,MODULEID) VALUES (3,112960);

INSERT INTO t_comm_groupmodule (GROUPID,MODULEID) VALUES (4,11);
INSERT INTO t_comm_groupmodule (GROUPID,MODULEID) VALUES (4,1121);
INSERT INTO t_comm_groupmodule (GROUPID,MODULEID) VALUES (4,112131);
INSERT INTO t_comm_groupmodule (GROUPID,MODULEID) VALUES (4,112132);
INSERT INTO t_comm_groupmodule (GROUPID,MODULEID) VALUES (4,1122);
INSERT INTO t_comm_groupmodule (GROUPID,MODULEID) VALUES (4,112231);
INSERT INTO t_comm_groupmodule (GROUPID,MODULEID) VALUES (4,112232);
INSERT INTO t_comm_groupmodule (GROUPID,MODULEID) VALUES (4,112960);
INSERT INTO t_comm_groupmodule (GROUPID,MODULEID) VALUES (4,112961);
/***********************************
类型:表
表名:pickid
功能:类型主表
************************************/
DROP TABLE IF EXISTS pickid;
CREATE TABLE pickid (
		pid		CHAR(4)			NOT NULL,		/*ID*/
		itemstr	VARCHAR(200)	NOT NULL, 		/*列表名称*/
	PRIMARY KEY (PID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO pickid (pid,itemstr) VALUES ('rzlx','日志类型');
INSERT INTO pickid (pid,itemstr) VALUES ('xblx','性别类型');
INSERT INTO pickid (pid,itemstr) VALUES ('xllx','学历类型');

/***********************************
类型:表
表名:picklist
功能:类型明细表
************************************/
DROP TABLE IF EXISTS picklist;
CREATE TABLE picklist (
		pid			CHAR(4)	NOT NULL,							/*ID*/
		itemno		SMALLINT DEFAULT 0	NOT NULL,				/*条目序号*/
		itemstr		VARCHAR(100)		NOT NULL,				/*条目名称*/
	PRIMARY KEY (pid,itemno)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*日志类型*/
INSERT INTO picklist (pid,itemno,itemstr) VALUES ('rzlx',0,'页面操作');
INSERT INTO picklist (pid,itemno,itemstr) VALUES ('rzlx',1,'数据库操作');	
/*性别类型*/
INSERT INTO picklist (pid,itemno,itemstr) VALUES ('xblx',1,'男');
INSERT INTO picklist (pid,itemno,itemstr) VALUES ('xblx',0,'女');	
/*学历类型*/
INSERT INTO picklist (pid,itemno,itemstr) VALUES ('xllx',3,'大专');
INSERT INTO picklist (pid,itemno,itemstr) VALUES ('xllx',0,'博士');	
INSERT INTO picklist (pid,itemno,itemstr) VALUES ('xllx',1,'硕士');
INSERT INTO picklist (pid,itemno,itemstr) VALUES ('xllx',2,'本科');
INSERT INTO picklist (pid,itemno,itemstr) VALUES ('xllx',4,'高中');
INSERT INTO picklist (pid,itemno,itemstr) VALUES ('xllx',5,'其它');
/*收货状态*/
INSERT INTO picklist (pid,itemno,itemstr) VALUES ('shzt',0,'未收货');	
INSERT INTO picklist (pid,itemno,itemstr) VALUES ('shzt',1,'已收货');
/***********************************
类型:表
表名:picklistex
功能:类型明细扩展表
************************************/
DROP TABLE IF EXISTS picklistex;
CREATE TABLE picklistex (
		pid				CHAR(4)				NOT NULL,			/*ID*/
		itemno			TINYINT				NOT NULL,			/*条目序号*/
		exitemno		TINYINT				NOT NULL,			/*扩展条目序号*/
		itemstr			VARCHAR(100)		NOT NULL,			/*条目名称*/
	PRIMARY KEY (pid,itemno,exitemno)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/***********************************
类型:表
表名:t_comm_log
功能:记录操作日志
************************************/
DROP TABLE IF EXISTS t_comm_log;
CREATE TABLE t_comm_log(
	id 					INT(4) UNSIGNED NOT NULL AUTO_INCREMENT,					/*自增ID*/
	userid 				VARCHAR(20) NULL,											/*用户id*/
	username 			VARCHAR(20) NULL,											/*用户名称*/
	processtime 		DATETIME default CURRENT_TIMESTAMP,							/*操作时间*/
	useripaddress 		VARCHAR(20) NULL,											/*ip地址*/
	logtype 			TINYINT NOT NULL,											/*日志类型 0:后台操作,1:称软件操作,2:pda操作*/
	logdesc 			VARCHAR(300) NULL,											/*日志描述*/
	PRIMARY KEY (ID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/***********************************
类型:表
表名:t_cs_order
功能:业务表
************************************/
DROP TABLE IF EXISTS t_cs_order;
CREATE TABLE t_cs_order(
	id 				INT(4) UNSIGNED NOT NULL AUTO_INCREMENT,	/*自增ID*/
 	snum 			VARCHAR(40) NOT NULL,						/*流水号*/
    carnum 			VARCHAR(20) NOT NULL DEFAULT '',  			/*车牌号*/
    goodstype 		VARCHAR(60) NOT NULL DEFAULT '',			/*货物类型*/
    goodsweight	 	NUMERIC(15,3),								/*货物重量*/
    sgoodsdept 		VARCHAR(100) NOT NULL DEFAULT '',			/*发货单位*/
    consignor		VARCHAR(60) NOT NULL DEFAULT '',			/*发货人*/
    sgoodstime 		DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,/*发货时间*/
    rgoodsdept 		VARCHAR(100) NOT NULL DEFAULT '',			/*收货单位*/
   	rgoodstime 		DATETIME,									/*收货时间*/
    consignee 		VARCHAR(60) NOT NULL DEFAULT '',			/*收货人*/
    rgoodsstatus 	TINYINT NOT NULL DEFAULT 0,					/*收货状态 0:未收货 1:已收货*/
    rgoodsostatus	TINYINT NOT NULL DEFAULT 0,					/*收货状态 0:正常收货 1:遗漏收货*/
    status			TINYINT NOT NULL DEFAULT 0,					/*状态 0:正常 1:遗漏*/
    lo				NUMERIC(18,6),								/*经度*/
    la				NUMERIC(18,6),								/*维度*/
    rfid			VARCHAR(100) NOT NULL DEFAULT '',			/*RFID码*/
    rfidstatus		TINYINT NOT NULL DEFAULT 0,					/*封签状态 0未封签 1已封签*/
    inserttime		DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,/*数据上传时间*/
    manualflag		TINYINT NOT NULL DEFAULT 0,					/*手工录入标志 0正常 1手工*/
    others1			VARCHAR(60) NOT NULL DEFAULT '',			/*备用字符串1*/
    others2			VARCHAR(60) NOT NULL DEFAULT '',			/*备用字符串2*/
    others3			VARCHAR(60) NOT NULL DEFAULT '',			/*备用字符串3*/
    otheri1			INT(4) NOT NULL DEFAULT 0,					/*备用整型1*/
    otheri2			INT(4) NOT NULL DEFAULT 0,					/*备用整型2*/
    otheri3			INT(4) NOT NULL DEFAULT 0,					/*备用整型3*/
    remark			VARCHAR(400),								/*备注*/
    PRIMARY KEY (ID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE t_cs_order ADD INDEX index_t_cs_order(rfid,sgoodstime);
/***********************************
类型:表
表名:t_cs_order_2019
功能:业务表 备份表
************************************/
DROP TABLE IF EXISTS t_cs_order_2019;
CREATE TABLE t_cs_order_2019(
	id 				INT(4) UNSIGNED NOT NULL,					/*ID*/
 	snum 			VARCHAR(40) NOT NULL,						/*流水号*/
    carnum 			VARCHAR(20) NOT NULL DEFAULT '',  			/*车牌号*/
    goodstype 		VARCHAR(60) NOT NULL DEFAULT '',			/*货物类型*/
    goodsweight	 	NUMERIC(15,3),								/*货物重量*/
    sgoodsdept 		VARCHAR(100) NOT NULL DEFAULT '',			/*发货单位*/
    consignor		VARCHAR(60) NOT NULL DEFAULT '',			/*发货人*/
    sgoodstime 		DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,/*发货时间*/
    rgoodsdept 		VARCHAR(100) NOT NULL DEFAULT '',			/*收货单位*/
   	rgoodstime 		DATETIME,									/*收货时间*/
    consignee 		VARCHAR(60) NOT NULL DEFAULT '',			/*收货人*/
    rgoodsstatus 	TINYINT NOT NULL DEFAULT 0,					/*收货状态 0:未收货 1:已收货*/
    rgoodsostatus	TINYINT NOT NULL DEFAULT 0,					/*收货状态 0:正常收货 1:遗漏收货*/
    status			TINYINT NOT NULL DEFAULT 0,					/*状态 0:正常 1:遗漏*/
    lo				NUMERIC(18,6),								/*经度*/
    la				NUMERIC(18,6),								/*维度*/
    rfid			VARCHAR(100) NOT NULL DEFAULT '',			/*RFID码*/
    rfidstatus		TINYINT NOT NULL DEFAULT 0,					/*封签状态 0未封签 1已封签*/
    inserttime		DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,/*数据上传时间*/
    manualflag		TINYINT NOT NULL DEFAULT 0,					/*手工录入标志 0正常 1手工*/
    others1			VARCHAR(60) NOT NULL DEFAULT '',			/*备用字符串1*/
    others2			VARCHAR(60) NOT NULL DEFAULT '',			/*备用字符串2*/
    others3			VARCHAR(60) NOT NULL DEFAULT '',			/*备用字符串3*/
    otheri1			INT(4) NOT NULL DEFAULT 0,					/*备用整型1*/
    otheri2			INT(4) NOT NULL DEFAULT 0,					/*备用整型2*/
    otheri3			INT(4) NOT NULL DEFAULT 0,					/*备用整型3*/
    remark			VARCHAR(400),								/*备注*/
    PRIMARY KEY (ID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE t_cs_order_2019 ADD INDEX index_t_cs_order_2019(sgoodstime);
/***********************************
类型:表
表名:t_cs_order_tmp
功能:业务临时表用于存储未封签的数据。
************************************/
DROP TABLE IF EXISTS t_cs_order_tmp;
CREATE TABLE t_cs_order_tmp(
 	snum 			VARCHAR(20) NOT NULL,						/*流水号*/
 	sgoodsdept 		VARCHAR(100) NOT NULL						/*发货单位*/
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
/***********************************
类型:表
表名:t_cs_doc_order
功能:业务归档表
************************************/
DROP TABLE IF EXISTS t_cs_doc_order;
CREATE TABLE t_cs_doc_order(
	id 				INT(4) UNSIGNED NOT NULL AUTO_INCREMENT,	/*自增ID*/
 	snum 			VARCHAR(40) NOT NULL,						/*流水号*/
    carnum 			VARCHAR(20) NOT NULL DEFAULT '',  			/*车牌号*/
    goodstype 		VARCHAR(60) NOT NULL DEFAULT '',			/*货物类型*/
    goodsweight	 	NUMERIC(15,3),								/*货物重量*/
    sgoodsdept 		VARCHAR(100) NOT NULL DEFAULT '',			/*发货单位*/
    consignor		VARCHAR(60) NOT NULL DEFAULT '',			/*发货人*/
    sgoodstime 		DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,/*发货时间*/
    rgoodsdept 		VARCHAR(100) NOT NULL DEFAULT '',			/*收货单位*/
   	rgoodstime 		DATETIME,									/*收货时间*/
    consignee 		VARCHAR(60) NOT NULL DEFAULT '',			/*收货人*/
    rgoodsstatus 	TINYINT NOT NULL DEFAULT 0,					/*收货状态 0:未收货 1:已收货*/
    rgoodsostatus	TINYINT NOT NULL DEFAULT 0,					/*收货状态 0:正常收货 1:遗漏收货*/
    status			TINYINT NOT NULL DEFAULT 0,					/*状态 0:正常 1:遗漏*/
    lo				NUMERIC(18,6),								/*经度*/
    la				NUMERIC(18,6),								/*维度*/
    rfid			VARCHAR(100) NOT NULL DEFAULT '',			/*RFID码*/
    rfidstatus		TINYINT NOT NULL DEFAULT 0,					/*封签状态 0未封签 1已封签*/
    inserttime		DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,/*数据上传时间*/
    manualflag		TINYINT NOT NULL DEFAULT 0,					/*手工录入标志 0正常 1手工*/
    others1			VARCHAR(60) NOT NULL DEFAULT '',			/*备用字符串1*/
    others2			VARCHAR(60) NOT NULL DEFAULT '',			/*备用字符串2*/
    others3			VARCHAR(60) NOT NULL DEFAULT '',			/*备用字符串3*/
    otheri1			INT(4) NOT NULL DEFAULT 0,					/*备用整型1*/
    otheri2			INT(4) NOT NULL DEFAULT 0,					/*备用整型2*/
    otheri3			INT(4) NOT NULL DEFAULT 0,					/*备用整型3*/
    remark			VARCHAR(400),								/*备注*/
    PRIMARY KEY (ID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE t_cs_doc_order ADD INDEX index_t_cs_doc_order(sgoodstime);
ALTER TABLE t_cs_doc_order ADD UNIQUE(snum);
/***********************************
类型:表
表名:t_cs_sgoodsdept
功能:发货单位表
************************************/
DROP TABLE IF EXISTS t_cs_sgoodsdept;
CREATE TABLE t_cs_sgoodsdept(
	id 					INT(4) UNSIGNED NOT NULL AUTO_INCREMENT,	/*自增ID*/
	sgoodsdeptcode 		VARCHAR(100),								/*单位编码*/
 	sgoodsdeptname 		VARCHAR(100) NOT NULL,						/*单位名称*/
 	sgoodsdeptmark		VARCHAR(100) NOT NULL,						/*封签货品 污泥或围产物*/
    status 				int NOT NULL DEFAULT 0,						/*状态 0:正常 1:无效*/
    PRIMARY KEY (ID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO t_cs_sgoodsdept (sgoodsdeptcode,sgoodsdeptname) VALUES ('11','沈水湾');
INSERT INTO t_cs_sgoodsdept (sgoodsdeptcode,sgoodsdeptname) VALUES ('12','仙女河');
INSERT INTO t_cs_sgoodsdept (sgoodsdeptcode,sgoodsdeptname) VALUES ('13','振兴污泥');
INSERT INTO t_cs_sgoodsdept (sgoodsdeptcode,sgoodsdeptname) VALUES ('14','振兴污泥2号');
ALTER TABLE t_cs_sgoodsdept ADD UNIQUE(sgoodsdeptname);
/***********************************
类型:表
表名:t_cs_rgoodsdept
功能:收货单位表
************************************/
DROP TABLE IF EXISTS t_cs_rgoodsdept;
CREATE TABLE t_cs_rgoodsdept(
	id 					int(4) UNSIGNED NOT NULL AUTO_INCREMENT,	/*自增ID*/
	rgoodsdeptcode 		VARCHAR(100),								/*单位编码*/
 	rgoodsdeptname 		VARCHAR(100) NOT NULL,						/*单位名称*/
    status 				int NOT NULL DEFAULT 0,						/*状态 0:正常 1:无效*/
    PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO t_cs_rgoodsdept (rgoodsdeptcode,rgoodsdeptname) VALUES ('11','收货单位一');
ALTER TABLE t_cs_rgoodsdept ADD UNIQUE(rgoodsdeptname);
/***********************************
类型:表
表名:t_cs_goodstype
功能:货物类型表
************************************/
DROP TABLE IF EXISTS t_cs_goodstype;
CREATE TABLE t_cs_goodstype(
	id 					INT(4) UNSIGNED NOT NULL AUTO_INCREMENT,	/*自增ID*/
	goodstypecode 		VARCHAR(100),								/*货物类型编码*/
 	goodstypename 		VARCHAR(100) NOT NULL,						/*货物类型名称*/
    status 				int NOT NULL DEFAULT 0,						/*状态 0:正常 1:无效*/
    PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO t_cs_goodstype (goodstypecode,goodstypename) VALUES ('11','污泥');
INSERT INTO t_cs_goodstype (goodstypecode,goodstypename) VALUES ('12','液铝');
/***********************************
类型:表
表名:t_cs_pdauser
功能:pda用户表
************************************/
DROP TABLE IF EXISTS t_cs_pdauser;
CREATE TABLE t_cs_pdauser(
	id 					INT(4) UNSIGNED NOT NULL AUTO_INCREMENT,			/*自增ID*/
	pdauserid 			VARCHAR(50) NOT NULL,								/*pda用户ID*/
 	pdausername 		VARCHAR(50) NOT NULL,								/*pda用户名称*/
 	pdapwd				VARCHAR(50) NOT NULL,								/*pda密码*/
 	pdatype				int NOT NULL DEFAULT 0,								/*pda类型 0:收发货都可 1:只发货 2:只收货*/
 	pdadept				VARCHAR(100) NOT NULL,								/*pda用户所在部门*/
    status 				int NOT NULL DEFAULT 0,								/*状态 0:正常 1:无效*/
    PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO t_cs_pdauser(pdauserid,pdausername,pdapwd,pdatype,pdadept)values('pda01','沈水湾_仙女河发货PDA','CF79AE6ADDBA60AD018347359BD144D2',1,'沈水湾###仙女河');
INSERT INTO t_cs_pdauser(pdauserid,pdausername,pdapwd,pdatype,pdadept)values('pda02','收货PDA','CF79AE6ADDBA60AD018347359BD144D2',2,'收货单位一');
ALTER TABLE t_cs_pdauser ADD UNIQUE(pdauserid);
/***********************************
类型:表
表名:t_cs_macaddr
功能:pdamac地址表 也可以存储特定字符串用于验证
************************************/
DROP TABLE IF EXISTS t_cs_macaddr;
CREATE TABLE t_cs_macaddr(
	id 						INT(4) UNSIGNED NOT NULL AUTO_INCREMENT,			/*自增ID*/
	macaddr 				VARCHAR(50) NOT NULL,								/*mac地址*/
    status 					int NOT NULL DEFAULT 0,								/*状态 0:正常 1:无效*/
    PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO t_cs_macaddr(macaddr)values("20:72:0d:39:2c:62");
INSERT INTO t_cs_macaddr(macaddr)values("1a:d5:e6:50:0d:8a");
INSERT INTO t_cs_macaddr(macaddr)values("1a:d5:e6:50:0a:12");
/***********************************
类型:表
表名:t_cs_tid
功能:rfid tid列表,用于验证签的合法性
************************************/
DROP TABLE IF EXISTS t_cs_tid;
CREATE TABLE t_cs_tid(
	tidcode VARCHAR(100) NOT NULL,			/*tid码*/
	inserttime		DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,/*插入时间*/
    PRIMARY KEY (tidcode)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
/***********************************
类型:视图
表名:v_users_all_module
功能:遍历用户拥有菜单
************************************/
DROP VIEW IF EXISTS v_users_all_module;
CREATE VIEW v_users_all_module AS
		SELECT MODULEID,USERID
			FROM t_comm_groupmodule gm RIGHT JOIN t_comm_useringroup ug
			ON gm.GROUPID=ug.GROUPID;

/***********************************
类型:视图
表名:v_order_history
功能:遍历历史数据
************************************/
DROP VIEW IF EXISTS v_order_history;
CREATE VIEW v_order_history AS
		SELECT *
			FROM t_cs_order_2019 
		UNION 
		SELECT *
			FROM t_cs_order_2020;
		UNION 
		SELECT *
			FROM t_cs_order_2021;
		UNION 
		SELECT *
			FROM t_cs_order_2022;
		UNION 
		SELECT *
			FROM t_cs_order_2023;
		UNION 
		SELECT *
			FROM t_cs_order_2024;
		UNION 
		SELECT *
			FROM t_cs_order_2025;			
/***********************************
类型:存储过程
表名:sel_menubyuser
功能:生成菜单树
************************************/
/*CALL sel_menubyuser('12','admin')*/
/*
DROP PROCEDURE IF EXISTS sel_menubyuser;
delimiter //
CREATE PROCEDURE sel_menubyuser(IN p_moduleid VARCHAR(50), IN p_userid VARCHAR(50))
BEGIN
DECLARE lev int;
SET lev=1;
DROP TABLE IF EXISTS tmp_sel_menubyuser;
CREATE temporary TABLE tmp_sel_menubyuser(MODULEID VARCHAR(6),MODULEDESC varchar(255),LEVELID INT,TOPLEVELID INT,TARGET varchar(255),FILENAME varchar(255),IMAGENAME varchar(255),VISIBLE INT,STATUS INT,levv INT) DEFAULT CHARSET=utf8;
INSERT tmp_sel_menubyuser SELECT moduleId,moduleDesc,levelid,toplevelid,target,FILENAME,IMAGENAME,VISIBLE,STATUS,1 FROM t_comm_module WHERE toplevelid=p_moduleid;
while  row_count()>0
do
   SET lev=lev+1;
   INSERT tmp_sel_menubyuser SELECT t.moduleId,t.moduleDesc,t.levelid,t.toplevelid,t.target,t.FILENAME,t.IMAGENAME,t.VISIBLE,t.STATUS,lev from t_comm_module t join tmp_sel_menubyuser a on t.toplevelid=a.moduleId AND levv=lev-1;
END while ;
SELECT moduleId,moduleDesc,levelid,toplevelid,target,FILENAME,IMAGENAME,VISIBLE,STATUS FROM tmp_sel_menubyuser WHERE moduleId in (SELECT MODULEID FROM t_comm_groupmodule gm RIGHT JOIN t_comm_useringroup ug
			ON gm.GROUPID=ug.GROUPID WHERE userid=p_userid) AND levelId>1 order by moduleId;

END;
*/
/*上面有可能出现错误,重复打开临时表*/
DROP PROCEDURE IF EXISTS sel_menubyuser;
delimiter //
CREATE PROCEDURE sel_menubyuser(IN p_moduleid VARCHAR(50), IN p_userid VARCHAR(50))
BEGIN
DECLARE lev int;
SET lev=1;
DROP temporary TABLE IF EXISTS tmp_sel_menubyuser1;
DROP temporary TABLE IF EXISTS tmp_sel_menubyuser2;
CREATE temporary TABLE tmp_sel_menubyuser1(MODULEID VARCHAR(6),MODULEDESC varchar(255),LEVELID INT,TOPLEVELID INT,TARGET varchar(255),FILENAME varchar(255),IMAGENAME varchar(255),VISIBLE INT,STATUS INT,levv INT) DEFAULT CHARSET=utf8;
CREATE temporary TABLE tmp_sel_menubyuser2(MODULEID VARCHAR(6),MODULEDESC varchar(255),LEVELID INT,TOPLEVELID INT,TARGET varchar(255),FILENAME varchar(255),IMAGENAME varchar(255),VISIBLE INT,STATUS INT,levv INT) DEFAULT CHARSET=utf8;
INSERT tmp_sel_menubyuser1 SELECT moduleId,moduleDesc,levelid,toplevelid,target,FILENAME,IMAGENAME,VISIBLE,STATUS,1 FROM t_comm_module WHERE toplevelid=p_moduleid;
INSERT tmp_sel_menubyuser2 SELECT moduleId,moduleDesc,levelid,toplevelid,target,FILENAME,IMAGENAME,VISIBLE,STATUS,1 FROM t_comm_module WHERE toplevelid=p_moduleid;
while  row_count()>0
do
   SET lev=lev+1;
   INSERT tmp_sel_menubyuser2 SELECT t.moduleId,t.moduleDesc,t.levelid,t.toplevelid,t.target,t.FILENAME,t.IMAGENAME,t.VISIBLE,t.STATUS,lev from t_comm_module t join tmp_sel_menubyuser1 a on t.toplevelid=a.moduleId AND levv=lev-1;
END while ;

SELECT moduleId,moduleDesc,levelid,toplevelid,target,FILENAME,IMAGENAME,VISIBLE,STATUS FROM tmp_sel_menubyuser2 WHERE moduleId in (SELECT MODULEID FROM t_comm_groupmodule gm RIGHT JOIN t_comm_useringroup ug
			ON gm.GROUPID=ug.GROUPID WHERE userid=p_userid) AND levelId>1 order by moduleId;		
END