USE house;
CREATE TABLE IF NOT EXISTS house_lj(
id int unsigned PRIMARY KEY auto_increment,
evaluatePoint int comment '评分',
price VARCHAR(32) comment '单价描述',
priceF FLOAT comment '单价',
) engine=InnoDB DEFAULT charset=utf8mb4 comment='链家数据表';
