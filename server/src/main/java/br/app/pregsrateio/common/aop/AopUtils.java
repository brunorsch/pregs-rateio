package br.app.pregsrateio.common.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AopUtils {
    private ProceedingJoinPoint joinPoint;

    @Nullable
    public <T, A> T getValorParametroAnotadoCom(Class<A> anotacao, Class<T> tipoRetorno) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        Object[] args = joinPoint.getArgs();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();

        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (Annotation annotation : parameterAnnotations[i]) {
                if (annotation.annotationType().equals(anotacao)) {
                    var result = args[i];

                    if(tipoRetorno.isInstance(result)) {
                        return tipoRetorno.cast(result);
                    }
                }
            }
        }

        return null;
    }
}
