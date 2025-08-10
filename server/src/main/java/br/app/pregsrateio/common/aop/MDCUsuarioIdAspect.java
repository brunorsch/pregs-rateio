package br.app.pregsrateio.common.aop;

import static java.lang.String.valueOf;
import static org.slf4j.MDC.putCloseable;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.MDC;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;

import br.app.pregsrateio.common.security.UsuarioPrincipal;

@Aspect
@Component
public class MDCUsuarioIdAspect {
    @Around("@annotation(br.app.pregsrateio.common.aop.annotations.MDCUsuarioId) || @within(br.app.pregsrateio.common.aop.annotations.MDCUsuarioId)")
    public Object mdcUsuarioIdAspect(ProceedingJoinPoint joinPoint) throws Throwable {
        var aopUtils = new AopUtils(joinPoint);
        var principal = aopUtils.getValorParametroAnotadoCom(
            AuthenticationPrincipal.class, UsuarioPrincipal.class);

        if (principal == null) {
            return joinPoint.proceed();
        }

        try (var ignored = putCloseable("idUsuario", valueOf(principal.getIdUsuario()))) {
            return joinPoint.proceed();
        } finally {
            MDC.remove("idUsuario");
        }
    }
}
