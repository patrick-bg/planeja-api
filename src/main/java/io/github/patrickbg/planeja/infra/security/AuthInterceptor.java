package io.github.patrickbg.planeja.infra.security;

import io.github.patrickbg.planeja.dominio.usuario.model.UsuarioEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtService jwtService;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {

        if("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        String token = authorizationHeader.substring(7);
        if(!jwtService.isTokenValido(token)){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        UsuarioEntity usuario = jwtService.getUsuario(token);
        request.setAttribute("usuarioAutenticado", usuario);
        return true;
    }
}
