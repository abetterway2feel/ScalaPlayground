package com.abetterway.processor;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;
import java.lang.reflect.Modifier;
import java.util.Set;

@SupportedAnnotationTypes("com.abetterway.processor.Immutable")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class SimpleAnnotationProcessor extends AbstractProcessor {
    @Override
    public boolean process(final Set<? extends TypeElement> annotations,
                           final RoundEnvironment roundEnv) {

        roundEnv.getElementsAnnotatedWith(Immutable.class)
                .stream()
                .filter(element -> element instanceof TypeElement)
                .forEach(element -> {
                    final TypeElement typeElement = (TypeElement) element;

                    typeElement.getEnclosedElements()
                            .stream()
                            .filter(enclosedElement -> enclosedElement instanceof VariableElement)
                            .forEach(eclosedElement -> {
                                final VariableElement variableElement = (VariableElement) eclosedElement;

                                if (!variableElement.getModifiers().contains(Modifier.FINAL)) {
                                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                                            String.format("Class '%s' is annotated as @Immutable, " +
                                                            "but field '%s' is not declared as final",
                                                    typeElement.getSimpleName(), variableElement.getSimpleName()
                                            )
                                    );
                                }
                            });
                });

        // Claiming that annotations have been processed by this processor
        return true;
    }
}
