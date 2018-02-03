package monitor.mapper;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;

import monitor.entity.MemConditionEntity;

public interface MemConditionMapper {
    @Select("SELECT * FROM mem_condition")
    @Results({
        @Result(property="curTime", column="time", javaType=Timestamp.class),
        @Result(property="id", column="id", javaType=Integer.class),
        @Result(property="memSum", column="memsum", javaType=Long.class),
        @Result(property="memUsed", column="memused", javaType=Long.class),
        @Result(property="swapSum", column="swapsum", javaType=Long.class),
        @Result(property="swapUsed", column="swapused", javaType=Long.class),
    })
    List<MemConditionEntity>getAll();

    @Select("SELECT * FROM mem_condition WHERE id=#{id} ORDER BY time DESC LIMIT 1")
    @Results({
        @Result(property="curTime", column="time", javaType=Timestamp.class),
        @Result(property="id", column="id", javaType=Integer.class),
        @Result(property="memSum", column="memsum", javaType=Long.class),
        @Result(property="memUsed", column="memused", javaType=Long.class),
        @Result(property="swapSum", column="swapsum", javaType=Long.class),
        @Result(property="swapUsed", column="swapused", javaType=Long.class),
    })
    MemConditionEntity getLastOneById(int id);

    @Select("SELECT * FROM (SELECT * FROM  mem_condition WHERE id=#{id} AND time>#{startTime} ORDER BY RAND() LIMIT 1000) AS temp_table ORDER BY time")
    @Results({
        @Result(property="curTime", column="time", javaType=Timestamp.class),
        @Result(property="id", column="id", javaType=Integer.class),
        @Result(property="memSum", column="memsum", javaType=Long.class),
        @Result(property="memUsed", column="memused", javaType=Long.class),
        @Result(property="swapSum", column="swapsum", javaType=Long.class),
        @Result(property="swapUsed", column="swapused", javaType=Long.class),
    })
    List<MemConditionEntity>getByDuringTime(@Param("id")int id, @Param("startTime")Timestamp startTime);

    @Insert("INSERT INTO mem_condition VALUES(#{curTime}, #{id}, #{memUsed}, #{memSum}, #{swapUsed}, #{swapSum})")
    void insert(MemConditionEntity diskConditionEntity);
}
