package com.example.springdb.connection;

import static com.example.springdb.connection.ConnectionConst.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class ConnectionTest {

    @DisplayName("드라이버 매니저 테스트")
    @Test
    void driverManger() throws SQLException {
        Connection con1 = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        Connection con2 = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        log.info("connection={}, class={}", con1, con1.getClass());
        log.info("connection={}, class={}", con2, con2.getClass());
    }

    @DisplayName("데이터소스 매니저 테스트")
    @Test
    void dataSourceDriverManger() throws SQLException {
        //DriverMangerDataSource - 항상 새로운 커넥션을 획득
        DataSource driverManagerDataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        useDataSource(driverManagerDataSource);
    }

    /*
    커넥션을 채우는 작업은 별도의 스레드에서 진행하게 된다.
    로그에서 MyPool connection adder로 스레드가 돌아간다.
    왜? 커넥션을 얻는 것 즉, 커넥션 풀에 커넥션을 채우는 것은 시간이 오래걸린다.
    만약 하나의 스레드에서 실행 시 애플리케이션 실행 시간이 늦어질 것이다.
    고로, 영향을 주지 않기 위해서 별도의 스레드에서 실행하도록 하는 것이다.
     */
    @DisplayName("커넥션 풀 테스트")
    @Test
    void dataSourceConnectionPool() throws SQLException, InterruptedException {
        // 커넥션 풀링
        HikariDataSource datasource = new HikariDataSource();
        datasource.setJdbcUrl(URL);
        datasource.setUsername(USERNAME);
        datasource.setPassword(PASSWORD);
        datasource.setMaximumPoolSize(1);
        datasource.setPoolName("MyPool");

        useDataSource(datasource);
        Thread.sleep(1000);
    }

    private void useDataSource(DataSource dataSource) throws SQLException {
        Connection con1 = dataSource.getConnection();
        Connection con2 = dataSource.getConnection();
        log.info("connection={}, class={}", con1, con1.getClass());
        log.info("connection={}, class={}", con2, con2.getClass());
    }
}
