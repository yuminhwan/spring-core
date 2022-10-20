package com.example.springdi;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.springdi.book.Book;
import com.example.springdi.book.MyBook;
import com.example.springdi.book.MyRuntimeAnnotation;

class AnnotationTest {

    /**
     * 어노테이션은 주석과 마찬가지이다.
     * 아무런 설정을 하지 않으면 런타임시에서는 무시한다.
     * 즉, 바이트코드까지는 있다. (기본값이 CLASS이기 때문에)
     */
    @DisplayName("어노테이션에 아무런 설정을 하지 않으면 런타임시에는 확인할 수 없다.")
    @Test
    void getAnnotations() {
        Class<Book> bookClass = Book.class;
        Annotation[] annotations = bookClass.getAnnotations();
        boolean result = Arrays.stream(annotations)
            .map(Annotation::annotationType)
            .anyMatch((annotation) -> annotation.getSimpleName().equals("MyAnnotation"));

        assertThat(result).isFalse();
    }

    @DisplayName("Retention설정을 런타임으로 하면 메모리 상에서도 확인이 가능하다.")
    @Test
    void runTimeAnnotation() {
        Class<Book> bookClass = Book.class;
        Annotation[] annotations = bookClass.getAnnotations();
        boolean result = Arrays.stream(annotations)
            .map(Annotation::annotationType)
            .anyMatch((annotation) -> annotation.getSimpleName().equals("MyRuntimeAnnotation"));

        assertThat(result).isTrue();
    }
    
    @DisplayName("상위 클래스의 어노테이션 까지 가져온다.")
    @Test
    void inherited() {
        Annotation[] annoations = MyBook.class.getAnnotations();

        assertThat(annoations).hasSize(1);
    }

    @DisplayName("클래스 자신에게 붙은 어노테이션만 가져온다.")
    @Test
    void getDeclaredAnnotations() {
        Annotation[] declaredAnnotations = MyBook.class.getDeclaredAnnotations();

        assertThat(declaredAnnotations).isEmpty();
    }

    @DisplayName("필드에 붙은 어노테이션에 접근한다.")
    @Test
    void fieldGetAnnotations() throws NoSuchFieldException {
        Field field = Book.class.getField("d");

        assertThat(field.getAnnotations()).hasSize(1);
    }

    @DisplayName("어노테이션에 값을 넣을 수 있다.")
    @Test
    void annotationValue() throws NoSuchFieldException {
        Field field = Book.class.getField("d");
        MyRuntimeAnnotation annotation = field.getAnnotation(MyRuntimeAnnotation.class);

        assertAll(
            () -> assertThat(annotation.name()).isEqualTo("hwan"),
            () -> assertThat(annotation.value()).isEqualTo("default")
        );
    }
}
