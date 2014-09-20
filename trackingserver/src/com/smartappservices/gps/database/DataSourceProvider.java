package com.smartappservices.gps.database;

/**
 *
 * @author Atul
 */

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

public class DataSourceProvider {

    public static DataSource getDataSource()
    {
        DataSource datasource = null;
        try {
            PoolProperties p = new PoolProperties();
            p.setUrl("jdbc:mysql://localhost:3306/trackman");
            p.setDriverClassName("com.mysql.jdbc.Driver");
        //    p.setUsername("root");
        //    p.setPassword("root");
            p.setUsername("trackman");
            p.setPassword("m!ntr!ck");
            p.setJmxEnabled(true);
            p.setTestWhileIdle(false);
            p.setTestOnBorrow(true);
            p.setValidationQuery("SELECT 1");
            p.setTestOnReturn(false);
            p.setValidationInterval(30000);
            p.setTimeBetweenEvictionRunsMillis(30000);
            p.setMaxActive(100);
            p.setInitialSize(3);
            p.setMaxWait(10000);
            p.setRemoveAbandonedTimeout(60);
            p.setMinEvictableIdleTimeMillis(30000);
            p.setMinIdle(3);
            p.setLogAbandoned(true);
            p.setRemoveAbandoned(true);
            p.setJdbcInterceptors(
                    "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"
                    + "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
            datasource = new DataSource();
            datasource.setPoolProperties(p);
        } catch (Exception e) {
            System.out.println("Exception : " + e);
        }
        return datasource;
    }

}
