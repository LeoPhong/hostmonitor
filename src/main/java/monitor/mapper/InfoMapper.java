package monitor.mapper;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

import monitor.entity.InfoEntity;

public interface InfoMapper {
    @Select("SELECT * FROM info")
    @Results({
        @Result(property="id", column="id", javaType=Integer.class),
        @Result(property="name", column="name", javaType=String.class),
        @Result(property="username", column="username", javaType=String.class),
        @Result(property="password", column="password", javaType=String.class),
        @Result(property="ipAddress", column="ipaddress", javaType=String.class),
        @Result(property="port", column="port",javaType=Integer.class),
        @Result(property="online", column="online", javaType=Boolean.class),
        @Result(property="enableTemp", column="enabletemp", javaType=Boolean.class),
    })
    List<InfoEntity> getAll();

    @Select("SELECT * FROM info WHERE id=#{id}")
    @Results({
        @Result(property="id", column="id", javaType=Integer.class),
        @Result(property="name", column="name", javaType=String.class),
        @Result(property="username", column="username", javaType=String.class),
        @Result(property="password", column="password", javaType=String.class),
        @Result(property="ipAddress", column="ipaddress", javaType=String.class),
        @Result(property="port", column="port",javaType=Integer.class),
        @Result(property="online", column="online", javaType=Boolean.class),
        @Result(property="enableTemp", column="enabletemp", javaType=Boolean.class),
    })
    InfoEntity getOne(int id);

    @Select("SELECT * FROM info WHERE online=#{online}")
    @Results({
        @Result(property="id", column="id", javaType=Integer.class),
        @Result(property="name", column="name", javaType=String.class),
        @Result(property="username", column="username", javaType=String.class),
        @Result(property="password", column="password", javaType=String.class),
        @Result(property="ipAddress", column="ipaddress", javaType=String.class),
        @Result(property="port", column="port",javaType=Integer.class),
        @Result(property="online", column="online", javaType=Boolean.class),
        @Result(property="enableTemp", column="enabletemp", javaType=Boolean.class),
    })
    List<InfoEntity>getByOnline(boolean online);

    @Select("SELECT * FROM info WHERE enabletemp=#{enableTemp}")
    @Results({
        @Result(property="id", column="id", javaType=Integer.class),
        @Result(property="name", column="name", javaType=String.class),
        @Result(property="username", column="username", javaType=String.class),
        @Result(property="password", column="password", javaType=String.class),
        @Result(property="ipAddress", column="ipaddress", javaType=String.class),
        @Result(property="port", column="port",javaType=Integer.class),
        @Result(property="online", column="online", javaType=Boolean.class),
        @Result(property="enableTemp", column="enabletemp", javaType=Boolean.class),
    })
    List<InfoEntity>getByEnableTemp(boolean enableTemp);

    @Select("SELECT * FROM info WHERE name=#{name}")
    @Results({
        @Result(property="id", column="id", javaType=Integer.class),
        @Result(property="name", column="name", javaType=String.class),
        @Result(property="username", column="username", javaType=String.class),
        @Result(property="password", column="password", javaType=String.class),
        @Result(property="ipAddress", column="ipaddress", javaType=String.class),
        @Result(property="port", column="port",javaType=Integer.class),
        @Result(property="online", column="online", javaType=Boolean.class),
        @Result(property="enableTemp", column="enabletemp", javaType=Boolean.class),
    })
    InfoEntity getByName(String name);


    @Update("UPDATE info SET password=#{password}")
    void update(String password);
}