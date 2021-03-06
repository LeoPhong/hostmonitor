package monitor.mapper;

import java.sql.Timestamp;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import monitor.entity.NetConditionEntity;

public interface NetConditionMapper {
    @Select("SELECT * FROM net_condition")
    @Results({
        @Result(property="curTime", column="time", javaType=Timestamp.class),
        @Result(property="id",column="id", javaType=Integer.class),
        @Result(property="device", column="device", javaType=String.class),
        @Result(property="rxBytes", column="rxbytes", javaType=Long.class),
        @Result(property="txBytes", column="txbytes", javaType=Long.class),
    })
    List<NetConditionEntity>getAll();

    @Select("SELECT * FROM (SELECT * FROM net_condition WHERE id=#{id} and device=#{device} ORDER BY time DESC LIMIT 2) AS temp_table ORDER BY TIME")
    @Results({
        @Result(property="curTime", column="time", javaType=Timestamp.class),
        @Result(property="id",column="id", javaType=Integer.class),
        @Result(property="device", column="device", javaType=String.class),
        @Result(property="rxBytes", column="rxbytes", javaType=Long.class),
        @Result(property="txBytes", column="txbytes", javaType=Long.class),
    })
    List<NetConditionEntity>getLastTwoById(@Param("id")int id, @Param("device") String device);

    @Select("SELECT * FROM (SELECT * FROM  net_condition WHERE id=#{id} AND device=#{device} AND time>#{startTime} ORDER BY RAND() LIMIT 1000) AS temp_table ORDER BY time")
    @Results({
        @Result(property="curTime", column="time", javaType=Timestamp.class),
        @Result(property="id",column="id", javaType=Integer.class),
        @Result(property="device", column="device", javaType=String.class),
        @Result(property="rxBytes", column="rxbytes", javaType=Long.class),
        @Result(property="txBytes", column="txbytes", javaType=Long.class),
    })
    List<NetConditionEntity>getByDuringTime(@Param("startTime") Timestamp startTime, @Param("id") int id, @Param("device") String device);

    @Insert("INSERT INTO net_condition VALUES(#{curTime}, #{id}, #{device}, #{rxBytes}, #{txBytes})")
    void insert(NetConditionEntity netConditionEntity);
}