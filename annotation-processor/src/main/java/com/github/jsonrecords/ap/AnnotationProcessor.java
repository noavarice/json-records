package com.github.jsonrecords.ap;

import com.google.auto.service.AutoService;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UncheckedIOException;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * Main annotation processor.
 *
 * @author noavarice
 * @since 1.0.0
 */
@SupportedAnnotationTypes("com.github.jsonrecords.JsonRecordsMapper")
@SupportedSourceVersion(SourceVersion.RELEASE_21)
@AutoService(Processor.class)
public final class AnnotationProcessor extends AbstractProcessor {

  @Override
  public boolean process(
      final Set<? extends TypeElement> annotations,
      final RoundEnvironment roundEnv
  ) {
    for (final TypeElement annotation : annotations) {
      final Set<? extends Element> annotated = roundEnv.getElementsAnnotatedWith(annotation);
      if (annotated.isEmpty()) {
        continue;
      }

      for (final Element element : annotated) {
        if (!element.getKind().isInterface()) {
          reportOnlyInterfaceAllowed(element);
          continue;
        }

        tryGenerateClass((TypeElement) element);
      }
    }

    return true;
  }

  private void reportOnlyInterfaceAllowed(final Element element) {
    processingEnv.getMessager().printMessage(
        Diagnostic.Kind.ERROR,
        "@JsonRecordsMapper can only be applied to interfaces",
        element
    );
  }

  private void tryGenerateClass(final TypeElement element) {
    final String interfaceFqcn = "" + element.getQualifiedName();
    final String classFqcn = interfaceFqcn + "Impl";

    final JavaFileObject fileObject;
    try {
      fileObject = processingEnv.getFiler().createSourceFile(classFqcn);
    } catch (final IOException e) {
      throw new UncheckedIOException(e);
    }

    try (final var writer = new PrintWriter(fileObject.openWriter())) {
      writePackage(classFqcn, writer);
      writeClassName(interfaceFqcn, classFqcn, writer);
      writer.println('}');
    } catch (final IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  private void writePackage(final String className, final PrintWriter writer) {
    if (!className.contains(".")) {
      return;
    }

    final String packageName = className.substring(0, className.lastIndexOf('.'));
    writer.println("package " + packageName + ";");
    writer.println();
  }

  private void writeClassName(
      final String interfaceFqcn,
      final String classFqcn,
      final PrintWriter writer
  ) {
    writer.println(
        "public class " + simpleName(classFqcn) + " implements " + simpleName(interfaceFqcn) + " {"
    );
  }

  private static String simpleName(final String fqcn) {
    return fqcn.contains(".") ? fqcn.substring(fqcn.lastIndexOf(".") + 1) : fqcn;
  }
}
