package com.boot_demo.demo1.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class JsonTypeHandler implements TypeHandler<Object> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void setParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.toString());
    }

    @Override
    public Object getResult(ResultSet rs, String columnName) throws SQLException {
        try {
            String s = rs.getString(columnName);
            if (s != null) {
                return objectMapper.readTree(s);
            }
            return null;

        } catch (JsonProcessingException e) {
            log.error("error", e);
        }
        return null;
    }

    @Override
    public Object getResult(ResultSet rs, int columnIndex) throws SQLException {
        try {
            String s = rs.getString(columnIndex);
            if (s != null) {
                return objectMapper.readTree(s);
            }
            return null;
        } catch (JsonProcessingException e) {
            log.error("error", e);
        }
        return null;
    }

    @Override
    public Object getResult(CallableStatement cs, int columnIndex) throws SQLException {
        try {
            String s = cs.getString(columnIndex);
            if (s != null) {
                return objectMapper.readTree(s);
            }
            return null;
        } catch (JsonProcessingException e) {
            log.error("error", e);
        }
        return null;
    }
}
