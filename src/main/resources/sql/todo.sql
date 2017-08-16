USE house;
CREATE TABLE IF NOT EXISTS house_lj(
id int unsigned PRIMARY KEY auto_increment,
evaluatePoint int comment '评分',
title VARCHAR(64) comment '标题',
price VARCHAR(32) comment '单价描述',
priceF FLOAT comment '单价',
total VARCHAR(32) comment '总价描述',
totalF FLOAT  comment '总价',
size VARCHAR(32) comment '面积描述',
sizeF FLOAT  comment '面积',
floor VARCHAR(32) comment '楼层',
buildTime VARCHAR(32) comment '年代描述',
buildTimeF FLOAT comment '年代',
shape VARCHAR(32) comment '户型',
priceAvg VARCHAR(32) comment '均价描述',
priceAvgF FLOAT comment '均价',
lastSale VARCHAR(32) comment '上次交易时间描述',
lastSaleF FLOAT comment '上次交易时间',
position VARCHAR(32) comment '位置',
putOut VARCHAR(32) comment '挂出时间描述',
putOutF FLOAT comment '挂出时间',
direction VARCHAR(32) comment '朝向',
decorate VARCHAR(32) comment '装修',
url VARCHAR(128) comment '链接地址'
updateTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) engine=InnoDB DEFAULT charset=utf8mb4 comment='链家数据表';
