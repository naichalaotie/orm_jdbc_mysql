DROP TABLE IF EXISTS `students`;

CREATE TABLE `students` (
  `id` int(10) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `sex` varchar(3) DEFAULT NULL,
  `math` int(5) DEFAULT NULL,
  `chinese` int(5) DEFAULT NULL,
  `english` int(5) DEFAULT NULL,
  `class` int(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `students` WRITE;

INSERT INTO `students` VALUES (1,'testPeople','boy',100,100,100,1903),(2,'shitou','boy',80,75,62,1903),(3,'zhuzi','boy',58,92,51,1903),(4,'xioafang','boy',86,82,75,1903),(5,'xiaoming','boy',79,94,84,1903),(6,'xiaohong','boy',85,92,83,1903),(7,'赵一一','boy',60,60,60,1701),(7,'name','sex',0,0,0,0),(7,'test','sex',NULL,NULL,NULL,NULL),(7,'??','sex',NULL,NULL,NULL,NULL),(7,'测试','sex',NULL,NULL,NULL,NULL),(7,'测试','sex',NULL,NULL,NULL,NULL),(7,'测试','sex',NULL,NULL,NULL,NULL),(7,'测试','sex',NULL,NULL,NULL,NULL),(7,'测试','sex',NULL,NULL,NULL,NULL),(15,'测试','男',NULL,NULL,NULL,NULL),(15,'测试','男',NULL,NULL,NULL,NULL),(15,'测试','男',NULL,NULL,NULL,NULL),(15,'测试2022.04.06','男',NULL,NULL,NULL,NULL),(7,'测试','sex',NULL,NULL,NULL,NULL),(7,'测试','sex',NULL,NULL,NULL,NULL),(7,'测试','sex',NULL,NULL,NULL,NULL),(7,'测试','sex',NULL,NULL,NULL,NULL),(7,'测试','sex',NULL,NULL,NULL,NULL),(7,'测试','sex',NULL,NULL,NULL,NULL),(7,'测试','sex',NULL,NULL,NULL,NULL),(7,'测试','sex',NULL,NULL,NULL,NULL),(0,NULL,NULL,NULL,NULL,NULL,NULL),(7,'测试','sex',NULL,NULL,NULL,NULL),(0,NULL,NULL,NULL,NULL,NULL,NULL),(7,'测试','sex',NULL,NULL,NULL,NULL),(0,NULL,NULL,NULL,NULL,NULL,NULL),(7,'测试','sex',NULL,NULL,NULL,NULL),(0,NULL,NULL,NULL,NULL,NULL,NULL),(7,'测试','sex',NULL,NULL,NULL,NULL),(0,NULL,NULL,NULL,NULL,NULL,NULL),(7,'测试','sex',NULL,NULL,NULL,NULL),(0,NULL,NULL,NULL,NULL,NULL,NULL),(7,'测试','sex',NULL,NULL,NULL,NULL),(0,NULL,NULL,NULL,NULL,NULL,NULL),(7,'测试','sex',NULL,NULL,NULL,NULL),(0,NULL,NULL,NULL,NULL,NULL,NULL),(7,'测试','sex',NULL,NULL,NULL,NULL),(0,NULL,NULL,NULL,NULL,NULL,NULL),(7,'测试','sex',NULL,NULL,NULL,NULL),(0,NULL,NULL,NULL,NULL,NULL,NULL),(7,'测试','sex',NULL,NULL,NULL,NULL),(0,NULL,NULL,NULL,NULL,NULL,NULL),(7,'测试','sex',NULL,NULL,NULL,NULL),(0,NULL,NULL,NULL,NULL,NULL,NULL),(7,'测试','sex',NULL,NULL,NULL,NULL),(0,NULL,NULL,NULL,NULL,NULL,NULL),(7,'测试','sex',NULL,NULL,NULL,NULL),(0,NULL,NULL,NULL,NULL,NULL,NULL),(7,'测试','sex',NULL,NULL,NULL,NULL),(0,NULL,NULL,NULL,NULL,NULL,NULL),(7,'测试','sex',NULL,NULL,NULL,NULL),(0,NULL,NULL,NULL,NULL,NULL,NULL),(7,'测试','sex',NULL,NULL,NULL,NULL),(0,NULL,NULL,NULL,NULL,NULL,NULL),(7,'测试','sex',NULL,NULL,NULL,NULL),(0,NULL,NULL,NULL,NULL,NULL,NULL),(7,'测试','sex',NULL,NULL,NULL,NULL),(0,NULL,NULL,NULL,NULL,NULL,NULL),(7,'测试','sex',NULL,NULL,NULL,NULL),(0,NULL,NULL,NULL,NULL,NULL,NULL),(7,'测试','sex',NULL,NULL,NULL,NULL),(0,NULL,NULL,NULL,NULL,NULL,NULL),(7,'测试','sex',NULL,NULL,NULL,NULL),(0,NULL,NULL,NULL,NULL,NULL,NULL),(7,'测试','sex',NULL,NULL,NULL,NULL),(0,NULL,NULL,NULL,NULL,NULL,NULL),(7,'测试','sex',NULL,NULL,NULL,NULL),(0,NULL,NULL,NULL,NULL,NULL,NULL);

UNLOCK TABLES;