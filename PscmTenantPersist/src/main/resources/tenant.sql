use tenant;

create table `tenantreal` (
  `RealCode` varchar(50) NOT NULL,
  `Name` varchar(50) DEFAULT NULL,
  `Address` varchar(50) DEFAULT NULL,
  `PostCode` varchar(20) DEFAULT NULL,
  `Contacter` varchar(20) DEFAULT NULL,
  `Telephone` varchar(20) DEFAULT NULL,
  `BizLicense` varbinary(100) DEFAULT NULL,
  `OtherScanner` varbinary(100) DEFAULT NULL,
  `RealType` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`RealCode`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='企业数据表';

create table `dynamicsource` (
  `SourceCode` varchar(50) NOT NULL,
  `SourceName` varchar(50) DEFAULT NULL,
  `DriveName` varchar(50) DEFAULT NULL,
  `LinkUrl` varchar(50) DEFAULT NULL,
  `UserName` varchar(50) DEFAULT NULL,
  `UserPassword` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`SourceCode`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='动态数据源表';

create table `tenant` (
  `TenantCode` varchar(50) NOT NULL,
  `Account` varchar(50) DEFAULT NULL,
  `Password` varchar(50) DEFAULT NULL,
  `Name` varchar(50) DEFAULT NULL,
  `TenantType` int(11) DEFAULT NULL COMMENT '租户类型:0项目部,1项目部所在的公司,2公司的上级集团公司。以此类推。100业主,101监理,102设计,102咨询',
  `CreateDate` date DEFAULT NULL,
  `StartDate` date DEFAULT NULL,
  `EndDate` date DEFAULT NULL,
  `Status` int(11) DEFAULT NULL COMMENT '租户的状态：1正常,2欠费,3过期,4没有使用',
  `ParentCode` varchar(50) NOT NULL COMMENT '任何一个租户都存在父租户。没有父租户的租户是不存在的。如果根租户，其父租户编码为0。',
  `RealCode` varchar(50) DEFAULT NULL,
  `SourceCode` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`TenantCode`) USING BTREE,
  KEY `FK_TenantReal` (`RealCode`) USING BTREE,
  KEY `FK_DynamicSource` (`SourceCode`) USING BTREE,
  CONSTRAINT `FK_DynamicSource` FOREIGN KEY (`SourceCode`) REFERENCES `dynamicsource` (`SourceCode`),
  CONSTRAINT `FK_TenantReal` FOREIGN KEY (`RealCode`) REFERENCES `tenantreal` (`RealCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='租户表';


create table `business` (
  `BizCode` varchar(50) NOT NULL,
  `BizEnglishName` varchar(50) DEFAULT NULL,
  `BizChineseName` varchar(50) DEFAULT NULL,
  `BizDesc` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`BizCode`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='功能模块数据表';

create table `tenbiz` (
  `TenantCode` varchar(50) NOT NULL,
  `BizCode` varchar(50) NOT NULL,
  `BizChineseName` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`TenantCode`,`BizCode`) USING BTREE,
  KEY `FK_BizCode` (`BizCode`) USING BTREE,
  CONSTRAINT `FK_BizCode` FOREIGN KEY (`BizCode`) REFERENCES `business` (`BizCode`),
  CONSTRAINT `FK_TenantCode` FOREIGN KEY (`TenantCode`) REFERENCES `tenant` (`TenantCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='租户分配功能数据表';

