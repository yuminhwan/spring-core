package com.example.springdi;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReflectionTest {

    private Class<Book> bookClass;

    @BeforeEach
    void setUp() {
        bookClass = Book.class;
    }

    @DisplayName("클래스 타입 찾는 3가지 방법")
    @Test
    void classType() throws ClassNotFoundException {
        // 1. .class (클래스 타입을 통해)
        Class<Book> bookClass = Book.class;

        // 2. .getClass() (인스턴스를 통해)
        Book book = new Book();
        Class<? extends Book> bookClass2 = book.getClass();

        // 3. Class.forName() / FQCN -> com.example.springdi.Book
        Class<?> bookClass3 = Class.forName("com.example.springdi.Book");

        assertAll(
                () -> assertThat(bookClass).isNotNull(),
                () -> assertThat(bookClass2).isNotNull(),
                () -> assertThat(bookClass3).isNotNull()
        );
    }

    /**
     * public한 필드만 가져온다.
     */
    @DisplayName("getFields는 public 필드 접근한다.")
    @Test
    void getFields() {
        Field[] fields = bookClass.getFields();

        assertThat(fields).hasSize(1)
            .extracting("name").containsExactly("d");
    }
    
    @DisplayName("getDeclaredFields메서드는 모든 필드에 접근한다.")
    @Test
    void getDeclaredFields() {
        Field[] fields = bookClass.getDeclaredFields();

        assertThat(fields).hasSize(5);
    }
    
    @DisplayName("접근이 불가능한 필드일 경우 예외가 발생한다. ")
    @Test
    void notAccessField() throws NoSuchFieldException {
        Field field = bookClass.getDeclaredField("B");

        assertThatThrownBy(() -> field.get(new Book()))
            .isInstanceOf(IllegalAccessException.class);
    }

    @DisplayName("setAccessible 메서드를 통해 접근이 가능하다록 변경한다.")
    @Test
    void makeAccess() throws NoSuchFieldException, IllegalAccessException {
        Field field = bookClass.getDeclaredField("B");
        field.setAccessible(true);

        assertThat(field.get(new Book())).isEqualTo("BOOK");
    }

    @DisplayName("메서드 정보를 가져온다.")
    @Test
    void getMethods() {
        Method[] methods = bookClass.getDeclaredMethods();

        assertThat(methods).extracting("name")
            .contains("f", "g", "h");
    }

    @DisplayName("생성자 정보를 가져온다.")
    @Test
    void getConstructors() {
        Constructor<?>[] constructors = bookClass.getConstructors();

        assertThat(constructors).hasSize(2);
    }
    
    @DisplayName("상위 클래스 정보를 가져온다.")
    @Test
    void getSuperclass() {
        Class<? super MyBook> superclass = MyBook.class.getSuperclass();

        assertThat(superclass.getSimpleName()).isEqualTo("Book");
    }

    @DisplayName("인터페이스 정보를 가져온다.")
    @Test
    void getInterfaces() {
        Class<?>[] interfaces = MyBook.class.getInterfaces();

        assertThat(interfaces).extracting("simpleName")
            .containsExactly("MyInterface");
    }

    /**
     * 메서드 정보도 가능하다.
     */
    @DisplayName("필드의 정보에 접근한다.")
    @Test
    void getModifiers() throws NoSuchFieldException {
        Field field = bookClass.getField("d");
        int modifiers = field.getModifiers();

        assertAll(
                () -> assertThat(Modifier.isPublic(modifiers)).isTrue(),
                () -> assertThat(Modifier.isPrivate(modifiers)).isFalse(),
                () -> assertThat(Modifier.isStatic(modifiers)).isFalse()
        );
    }
}
