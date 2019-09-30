package com.cmiov.tsop.oscar.mall.dbutool.utils;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ZhengKai
 * @data 2019-09-28 18:51
 */
@Slf4j
public class DBUtil {
    private static String driver="com.mysql.jdbc.Driver";

    private DBUtil(){

    }

    static {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(String url, String userName, String password) throws SQLException {
        return DriverManager.getConnection(url, userName, password);
    }

    public static void closeResource(Connection connection, Statement st, ResultSet resultSet){
        closeResultSet(resultSet);
        closeConnection(connection);
        closeStatement(st);
    }

    public static void closeConnection(Connection connection){
        if (connection!=null){
            try {
                connection.close();
            } catch (SQLException e) {
                log.error("DBUtils closeConnection exception = [{}]", e.getErrorCode());
            }
        }
        connection = null;
    }

    public static void closeStatement(Statement st){
        if (st!=null){
            try {
                st.close();
            } catch (SQLException e) {
                log.error("DBUtils closeStatement exception =[{}]", e.getErrorCode());
            }
        }
        st=null;
    }
    private static void closeResultSet(ResultSet rs) {
        if (rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                log.error("DBUtils closeResultSet exception =[{}]",e.getErrorCode());
            }
        }
        rs = null;
    }

    public static List query(String sql, String url, String userName, String password) throws Exception{
        Connection conn=getConnection(url,userName,password);
        PreparedStatement st = conn.prepareStatement(sql);
        ResultSet rs=st.executeQuery();

        List list = new ArrayList();

        ResultSetMetaData md = rs.getMetaData();
        int columnCount = md.getColumnCount();

        while (rs.next()){
            Map rowData=new LinkedHashMap<>();
            for (int i = 1;i <= columnCount; i++) {
                rowData.put(md.getColumnName(i), rs.getObject(i) == null ? "null" : rs.getObject(i));
            }
            list.add(rowData);
        }
        closeResource(conn, st, rs);
        return list;
    }


    public static Integer update(String sql, String url, String userName, String password) throws Exception{
        Connection conn=getConnection(url, userName, password);
        PreparedStatement pst = conn.prepareStatement(sql);
        int rows = pst.executeUpdate();
        closeResource(conn, pst, null);
        return rows;
    }
}
