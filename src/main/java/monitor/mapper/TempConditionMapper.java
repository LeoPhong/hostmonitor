package monitor.mapper;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import monitor.entity.TempConditionEntity;

public interface TempConditionMapper {
    @Select("SELECT * FROM temp_condition")
    @Results({
        @Result(property="curTime", column="time", javaType=Timestamp.class),
        @Result(property="id",column="id", javaType=Integer.class),
        @Result(property="temp", column="temp", javaType=Double.class)
    })
    List<TempConditionEntity>getAll();

    @Select("SELECT * FROM temp_condition WHERE id=#{id} ORDER BY time DESC LIMIT 1")
    @Results({
        @Result(property="curTime", column="time", javaType=Timestamp.class),
        @Result(property="id",column="id", javaType=Integer.class),
        @Result(property="temp", column="temp", javaType=Double.class)
    })
    TempConditionEntity getLastOneById(int id);

    @Insert("INSERT INTO temp_condition VALUES(#{curTime}, #{id}, #{temp})")
    void insert(TempConditionEntity TempConditionEntity);
}