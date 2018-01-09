package monitor.mapper;

import java.sql.Timestamp;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
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

    @Select("SELECT * FROM net_condition WHERE id=#{id}")
    @Results({
        @Result(property="curTime", column="time", javaType=Timestamp.class),
        @Result(property="id",column="id", javaType=Integer.class),
        @Result(property="device", column="device", javaType=String.class),
        @Result(property="rxBytes", column="rxbytes", javaType=Long.class),
        @Result(property="txBytes", column="txbytes", javaType=Long.class),
    })
    NetConditionEntity getOneById(int id);

    @Select("SELECT * FROM net_condition WHERE time=#{curTime}")
    @Results({
        @Result(property="curTime", column="time", javaType=Timestamp.class),
        @Result(property="id",column="id", javaType=Integer.class),
        @Result(property="device", column="device", javaType=String.class),
        @Result(property="rxBytes", column="rxbytes", javaType=Long.class),
        @Result(property="txBytes", column="txbytes", javaType=Long.class),
    })
    NetConditionEntity getOneByTime(Timestamp curTime);

    @Insert("INSERT INTO net_condition VALUES(#{curTime}, #{id}, #{device}, #{rxBytes}, #{txBytes})")
    void insert(NetConditionEntity netConditionEntity);
}