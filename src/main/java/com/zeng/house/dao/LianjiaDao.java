package com.zeng.house.dao;

import com.zeng.house.bean.LianjiaHouse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by zengqiang on 2017/8/15.
 */
@Mapper
public interface LianjiaDao {
    List<LianjiaHouse> select(LianjiaHouse house, Integer limit);

@Select("select count(*) from lianjia")
    Long count();

    void insert(LianjiaHouse house);
}
