package monitor.mapper;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

import monitor.entity.DiskConditionEntity;

public interface DiskConditionMapper {
    @Select("SELECT * FROM disk_condition")
    @Results({
        @Result(property="curTime", column="time", javaType=Timestamp.class),
        @Result(property="id", column="id", javaType=Integer.class),
        @Result(property="used", column="used", javaType=Long.class),
        @Result(property="sum", column="sum", javaType=Long.class)
    })
    List<DiskConditionEntity>getAll();

    @Select("SELECT * FROM disk_condition WHERE id=#{id} ORDER BY time DESC LIMIT 1")
    @Results({
        @Result(property="curTime", column="time", javaType=Timestamp.class),
        @Result(property="id", column="id", javaType=Integer.class),
        @Result(property="used", column="used", javaType=Long.class),
        @Result(property="sum", column="sum", javaType=Long.class)
    })
    DiskConditionEntity getLastOneById(int id);

    @Select("SELECT * FROM (SELECT * FROM  disk_condition WHERE id=#{id} AND time>#{startTime} ORDER BY RAND() LIMIT 1000) AS temp_table ORDeR BY time")
    @Results({
        @Result(property="curTime", column="time", javaType=Timestamp.class),
        @Result(property="id", column="id", javaType=Integer.class),
        @Result(property="used", column="used", javaType=Long.class),
        @Result(property="sum", column="sum", javaType=Long.class)
    })
    List<DiskConditionEntity>getDuringTime(@Param("id")int id, @Param("startTime")Timestamp startTime);

    @Insert("INSERT INTO disk_condition VALUES(#{curTime}, #{id}, #{used}, #{sum})")
    void insert(DiskConditionEntity diskConditionEntity);
}
