package br.com.pedroloureco.prodsjava.security.filter;

import br.com.pedroloureco.prodsjava.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserIdFilter extends OncePerRequestFilter {

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        UUID idPathVariable = getIdPathVariable(request);

        if(userRepository.existsByEmailAndId(userEmail, idPathVariable)){
            filterChain.doFilter(request, response);
        }

        response.sendError(403);
    }

    private UUID getIdPathVariable(HttpServletRequest request){
        String uri = request.getRequestURI().substring(request.getContextPath().length());
        String pattern = "/user/{id}";

        if (pathMatcher.match(pattern, uri)) {
            Map<String, String> variables = pathMatcher.extractUriTemplateVariables(pattern, uri);
            String id = variables.get("id");

            return UUID.fromString(id);
        }

        return null;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !new AntPathRequestMatcher("/user/{id}").matches(request);
    }
}
