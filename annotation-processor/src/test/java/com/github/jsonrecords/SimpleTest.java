package com.github.jsonrecords;

import static io.toolisticon.cute.Cute.blackBoxTest;
import static io.toolisticon.cute.JavaFileObjectUtils.readFromString;

import com.github.jsonrecords.ap.AnnotationProcessor;
import io.toolisticon.cute.CuteApi;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Example test.
 *
 * @author noavarice
 * @since 1.0.0
 */
@DisplayName("Example test")
class SimpleTest {

  @DisplayName("Example black-box test using CUTE")
  @Test
  void test() {
    // @formatter:off
    // language=Java
    final String expectedJava = """
        package com.github.jsonrecords.test;
        
        public class SimpleMapperImpl implements SimpleMapper {
        }
        """;
    // @formatter:on
    blackBoxTest()
        .given()
        .processor(AnnotationProcessor.class)
        .andSourceFiles("/JsonRecordsMapper.java", "/SimpleMapper.java")
        .whenCompiled()
        .thenExpectThat()
        .compilationSucceeds()
        .andThat()
        .generatedSourceFile("com.github.jsonrecords.test.SimpleMapperImpl")
        .matches(
            CuteApi.ExpectedFileObjectMatcherKind.TEXT_IGNORE_LINE_ENDINGS,
            readFromString(expectedJava)
        )
        .executeTest();
  }
}
