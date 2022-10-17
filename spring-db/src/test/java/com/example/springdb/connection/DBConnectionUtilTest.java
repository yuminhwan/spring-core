package com.example.springdb.connection;

import static org.assertj.core.api.Assertions.*;

import java.sql.Connection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class DBConnectionUtilTest {

    @DisplayName("데이터베이스 커넥션 획득 성공")
    @Test
    void connection_success() {
        // given
        // when
        Connection connection = DBConnectionUtil.getConnection();

        // then
        assertThat(connection).isNotNull();
    }
}
