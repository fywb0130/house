package com.zeng.house.dao;

import com.zeng.house.bean.LianjiaHouse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by zengqiang on 2017/8/15.
 */
@Mapper
public interface LianjiaDao {
    List<LianjiaHouse> select(@Param("house") LianjiaHouse house, @Param("limit") Integer limit);

    Long count(LianjiaHouse house);

    void insert(LianjiaHouse house);

	@Select("select url from house_lj order by updateTime desc limit 10")
	List<String> getSeed();
}
