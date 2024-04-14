package com.github.jsonrecords.ap;

import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

/**
 * Main annotation processor.
 *
 * @author noavarice
 * @since 1.0.0
 */
@SupportedAnnotationTypes("com.github.jsonrecords.JsonRecordsMapper")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public final class AnnotationProcessor extends AbstractProcessor {

  @Override
  public boolean process(
      final Set<? extends TypeElement> annotations,
      final RoundEnvironment roundEnv
  ) {
    throw new UnsupportedOperationException();
  }
}
