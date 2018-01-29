package monitor.mapper;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import monitor.entity.CpuConditionEntity;

public interface CpuConditionMapper {
    @Select("SELECT * FROM cpu_condition")
    @Results({
        @Result(property="curTime", column="time", javaType=Timestamp.class),
        @Result(property="id",column="id", javaType=Integer.class),
        @Result(property="load", column="load", javaType=Double.class)
    })
    List<CpuConditionEntity>getAll();

    @Select("SELECT * FROM cpu_condition WHERE id=#{id} ORDER BY time DESC LIMIT 1")
    @Results({
        @Result(property="curTime", column="time", javaType=Timestamp.class),
        @Result(property="id",column="id", javaType=Integer.class),
        @Result(property="load", column="load", javaType=Double.class)
    })
    CpuConditionEntity getLastOneById(int id);

    @Select("SELECT * FROM (SELECT * FROM  cpu_condition WHERE id=#{id} AND time>#{startTime} ORDER BY RAND() LIMIT 1000) AS temp_table ORDER BY time")
    @Results({
        @Result(property="curTime", column="time", javaType=Timestamp.class),
        @Result(property="id",column="id", javaType=Integer.class),
        @Result(property="load", column="load", javaType=Double.class)
    })
    List<CpuConditionEntity>getByDuringTime(@Param("id")int id, @Param("startTime")Timestamp startTime);

    @Insert("INSERT INTO cpu_condition VALUES(#{curTime}, #{id}, #{load})")
    void insert(CpuConditionEntity cpuConditionEntity);
}