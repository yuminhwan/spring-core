package com.example.springdi.comicbook;

import static org.assertj.core.api.Assertions.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ComicBookTest {

    private Class<?> comicBookClass;

    @BeforeEach
    void setUp() throws ClassNotFoundException {
        comicBookClass = Class.forName("com.example.springdi.comicbook.ComicBook");
    }

    @DisplayName("기본 생성자로 인스턴스를 만들 수 있다.")
    @Test
    void getConstructor() throws Exception {
        Constructor<?> constructor = comicBookClass.getConstructor(null);
        ComicBook comicBook = (ComicBook)constructor.newInstance();

        assertThat(comicBook).isNotNull();
    }

    @DisplayName("기본 생성자 이외의 생성자로도 인스턴스를 만들 수 있다.")
    @Test
    void getConstructor_notDefault() throws Exception{
        Constructor<?> constructor = comicBookClass.getConstructor(String.class);
        ComicBook comicBook = (ComicBook)constructor.newInstance("test"); // 파라미터를 넘겨줘야 한다.

        assertThat(comicBook).extracting("B")
            .isEqualTo("test");
    }

    @DisplayName("클래스 변수는 클래스끼리 공유하므로 인스턴스를 안줘도 접근 가능하다.")
    @Test
    void getStaticValue() throws Exception{
        Field field = comicBookClass.getDeclaredField("A");

        assertThat(field.get(null)).isEqualTo("A");
    }
    
    @DisplayName("인스턴스 변수는 인스턴스가 있어야 접근이 가능하다.")
    @Test
    void getInstanceValue() throws Exception{
        Field field = comicBookClass.getDeclaredField("B");
        field.setAccessible(true);

        assertThat(field.get(new ComicBook())).isEqualTo("B");
    }

    @DisplayName("변수 변경도 가능하다.")
    @Test
    void changeStaticValue() throws Exception{
        Field field = comicBookClass.getDeclaredField("A");
        field.set(null, "test");

        assertThat(field.get(null)).isEqualTo("test");
    }

    @DisplayName("메서드 실행도 가능하다.")
    @Test
    void invoke() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = comicBookClass.getDeclaredMethod("c");
        method.setAccessible(true);
        method.invoke(new ComicBook());
    }

    @DisplayName("파라미터가 있는 메서드도 실행 가능하다.")
    @Test
    void invoke_withParameter() throws Exception{
        Method method = comicBookClass.getDeclaredMethod("sum", int.class, int.class);
        int result = (int)method.invoke(new ComicBook(), 1, 2);

        assertThat(result).isEqualTo(3);
    }
}
