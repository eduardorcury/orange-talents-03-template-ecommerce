package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.compartilhado.security;

import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.usuario.Usuario;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.usuario.UsuarioRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class AutenticacaoFilter extends OncePerRequestFilter {

    private final UsuarioRepository repository;
    private final AutenticacaoService service;

    public AutenticacaoFilter(UsuarioRepository repository, AutenticacaoService service) {
        this.repository = repository;
        this.service = service;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {


        String token = recuperarToken(request);
        boolean isValid = service.isTokenValid(token);
        if (isValid) {
            autenticarCliente(token);
        }

        filterChain.doFilter(request, response);

    }

    private void autenticarCliente(String token) {

        String login = service.getUsuarioLogin(token);
        Optional<Usuario> usuarioOptional = repository.findByLogin(login);

        if (usuarioOptional.isPresent()) {
            UsernamePasswordAuthenticationToken usuarioLogado = new UsernamePasswordAuthenticationToken(
                            usuarioOptional.get().getUsername(), null, usuarioOptional.get().getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usuarioLogado);
        } else throw new UsernameNotFoundException("Usuário não encontrado");

    }

    private String recuperarToken(HttpServletRequest request) {

        String token = request.getHeader("Authorization");
        if (token == null || token.isBlank() || !token.startsWith("Bearer ")) {
            return null;
        }
        return token.substring(7);

    }
}
