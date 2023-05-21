package com.lsc.freemarker.db;


import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lsc.freemarker.entity.Column;
import com.lsc.freemarker.entity.Table;
import com.lsc.freemarker.utils.StringBuildUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * @author lisc
 * @Description: com.lsc.freemarker.db
 * @date 2022/11/8 15:30
 */
@Slf4j

public class DataBaseConnection {

    public static void main(String[] args) {
        Connection conn = getConnection();
        String sql = "select * from ibep_form_data_records";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery(sql);
            ResultSetMetaData data = rs.getMetaData();
            while (rs.next()) {
                for (int i = 1; i <= data.getColumnCount(); i++) {
                    //获得所有列的数目及实际列数
                    int columnCount = data.getColumnCount();
                    //获得指定列的列名
                    String columnName = data.getColumnName(i);
                    //获得指定列的列值
                    String columnValue = rs.getString(i);
                    //获得指定列的数据类型
                    int columnType = data.getColumnType(i);
                    //获得指定列的数据类型名
                    String columnTypeName = data.getColumnTypeName(i);
                    //所在的Catalog名字
                    String catalogName = data.getCatalogName(i);
                    //对应数据类型的类
                    String columnClassName = data.getColumnClassName(i);
                    //在数据库中类型的最大字符个数
                    int columnDisplaySize = data.getColumnDisplaySize(i);
                    //默认的列的标题
                    String columnLabel = data.getColumnLabel(i);
                    //获得列的模式
                    String schemaName = data.getSchemaName(i);
                    //某列类型的精确度(类型的长度)
                    int precision = data.getPrecision(i);
                    //小数点后的位数
                    int scale = data.getScale(i);
                    //获取某列对应的表名
                    String tableName = data.getTableName(i);
                    // 是否自动递增
                    boolean isAutoInctement = data.isAutoIncrement(i);
                    //在数据库中是否为货币型
                    boolean isCurrency = data.isCurrency(i);
                    //是否为空
                    int isNullable = data.isNullable(i);
                    //是否为只读
                    boolean isReadOnly = data.isReadOnly(i);
                    //能否出现在where中
                    boolean isSearchable = data.isSearchable(i);
                    System.out.println(columnCount);
                    System.out.println("获得列" + i + "的字段名称:" + columnName);
                    System.out.println("获得列" + i + "的字段值:" + columnValue);
                    System.out.println("获得列" + i + "的类型,返回SqlType中的编号:" + columnType);
                    System.out.println("获得列" + i + "的数据类型名:" + columnTypeName);
                    System.out.println("获得列" + i + "所在的Catalog名字:" + catalogName);
                    System.out.println("获得列" + i + "对应数据类型的类:" + columnClassName);
                    System.out.println("获得列" + i + "在数据库中类型的最大字符个数:" + columnDisplaySize);
                    System.out.println("获得列" + i + "的默认的列的标题:" + columnLabel);
                    System.out.println("获得列" + i + "的模式:" + schemaName);
                    System.out.println("获得列" + i + "类型的精确度(类型的长度):" + precision);
                    System.out.println("获得列" + i + "小数点后的位数:" + scale);
                    System.out.println("获得列" + i + "对应的表名:" + tableName);
                    System.out.println("获得列" + i + "是否自动递增:" + isAutoInctement);
                    System.out.println("获得列" + i + "在数据库中是否为货币型:" + isCurrency);
                    System.out.println("获得列" + i + "是否为空:" + isNullable);
                    System.out.println("获得列" + i + "是否为只读:" + isReadOnly);
                    System.out.println("获得列" + i + "能否出现在where中:" + isSearchable);

                }
            }
        } catch (SQLException e) {
            System.out.println("数据库连接失败");
            close(conn, stmt, rs);
        }
    }


    public static Table getDataBaseTableData(String tableName) {
        Connection conn = getConnection();
        String sql = String.format("select * from %s", tableName);
        PreparedStatement stmt = null;
        ResultSet rs = null;

        Table table = new Table();
        ArrayList<Column> columnArrayList = new ArrayList<>();
        try {
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery(sql);
            ResultSetMetaData data = rs.getMetaData();
            //   while (rs.next()) {
            for (int i = 1; i <= data.getColumnCount(); i++) {

                Column column = new Column();
                // 获取某列对应的表名
                String dataTableName = data.getTableName(i);
                table.setDatabaseTableName(dataTableName);
                // 下划线命名转为驼峰命名
                String javaTableName = StringBuildUtils.underlineToHump(dataTableName);
                table.setJavaTableName(javaTableName);


                //获得指定列的列名
                String dataColumnName = data.getColumnName(i);
                column.setColumnName(dataColumnName);

                // 下划线命名转为驼峰命名
                String javaColumnName = StringBuildUtils.underlineToHump(dataColumnName);
                column.setJavaColumnName(javaColumnName);

                //对应数据类型的类
                String columnClassName = data.getColumnClassName(i);
                column.setColumnType(columnClassName.substring(columnClassName.lastIndexOf(".")));

                //获得指定列的数据类型名
                String columnTypeName = data.getColumnTypeName(i);
                column.setColumnDbType(columnTypeName);

                columnArrayList.add(column);
            }
            //     }
            table.setColumns(columnArrayList);
        } catch (SQLException e) {
            log.info("数据库连接失败", e);
        } finally {
            close(conn, stmt, rs);
        }
        return table;
    }

    /**
     * 获取数据库连接
     *
     * @return
     */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Properties prop = new Properties();
            InputStream in = DataBaseConnection.class.getClassLoader().getResourceAsStream("spring-config/db.properties");
            prop.load(in);
            DataSource dataSource = DruidDataSourceFactory.createDataSource(prop);
            //5.获取数据库联链接
            connection = dataSource.getConnection();
        } catch (Exception e) {
            log.info("DateBaseConnection:init() 创建连接时 发生异常:", e);
        }
        return connection;
    }


    /**
     * 关闭数据库连接
     *
     * @param connection
     * @param statement
     * @param resultSet
     */
    public static void close(Connection connection, Statement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                connection.close();
            }
        } catch (SQLException throwables) {
            log.info("DateBaseConnection:close() 关闭连接时,发生异常:", throwables);
        }
    }


    @Test
    public void test() throws Exception {
        Table ibep_form_data_records = getDataBaseTableData("ibep_form_data_records");
        System.out.println("JSONObject.toJSONString(ibep_form_data_records, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue) = " + JSONObject.toJSONString(ibep_form_data_records, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue));


    }


}
