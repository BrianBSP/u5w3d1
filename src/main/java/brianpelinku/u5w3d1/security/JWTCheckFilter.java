package brianpelinku.u5w3d1.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.UnavailableException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTCheckFilter extends OncePerRequestFilter {

    @Autowired
    private JWTTools jwtTools;

    // per filtrare ogni richiesta
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // prima verifico se c'è l'Authorization Header
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            throw new UnavailableException("Inserire correttamente il Token.");

        // lo estrapoliamo
        String accessToken = authHeader.substring(7);
        System.out.println("access Token: " + accessToken);
        // controlliamo che sia un token "valido"
        jwtTools.verificaToken(accessToken);
        // passo allo step successivo
        filterChain.doFilter(request, response);
    }

    // per non filtrare la richiesta
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }
}
