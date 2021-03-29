package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.compartilhado.security;

import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.usuario.Usuario;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.usuario.UsuarioRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.Optional;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private String expiration;

    @Autowired
    // 1
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        // 2
        Optional<Usuario> usuarioOptional = usuarioRepository.findByLogin(login);

        // 3
        if (usuarioOptional.isPresent()) {
            return usuarioOptional.get();
        }

        throw new UsernameNotFoundException("Usuário de login " + login + " não encontrado");

    }

    public String gerarToken(Authentication authentication) {

        Assert.isTrue(authentication.isAuthenticated(), "Parece que a autenticação não deu certo");

        Usuario usuarioLogado = (Usuario) authentication.getPrincipal();
        Date dataDeExpiracao = new Date(new Date().getTime() + Long.parseLong(expiration));

        return Jwts.builder()
                .setIssuer("API Mercado Livre")
                .setSubject(usuarioLogado.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(dataDeExpiracao)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

    }

    public boolean isTokenValid(String token) {

        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (Exception exception) {
            return false;
        }

    }

    public String getUsuarioLogin(String token) {

        Assert.isTrue(isTokenValid(token), "Token inválido");
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return claims.getSubject();

    }
}
