package io.github.patrickbg.planeja.dominio.usuario;

import io.github.patrickbg.planeja.common.exceptions.UnauthorizedException;
import io.github.patrickbg.planeja.common.exceptions.ValidationException;
import io.github.patrickbg.planeja.common.validation.CampoInvalido;
import io.github.patrickbg.planeja.dominio.usuario.dto.AuthResponse;
import io.github.patrickbg.planeja.dominio.usuario.dto.LoginForm;
import io.github.patrickbg.planeja.dominio.usuario.dto.UsuarioCadastroForm;
import io.github.patrickbg.planeja.dominio.usuario.model.UsuarioEntity;
import io.github.patrickbg.planeja.infra.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtService jwtService;

    public AuthResponse cadastrar(UsuarioCadastroForm form){

        if(usuarioRepository.existsByLogin(form.login())){
            throw new ValidationException(List.of(new CampoInvalido("login","Login já cadastrado.")));
        }

        String senhaCriptografada = encoder.encode(form.senha());

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNome(form.nome());
        usuario.setLogin(form.login());
        usuario.setSenha(senhaCriptografada);

        usuarioRepository.save(usuario);

        return toAuthResponse(usuario);
    }

    public AuthResponse autenticar (LoginForm form){
        final String mensagem = "Login ou senha inválidos";

        UsuarioEntity usuario = usuarioRepository.findByLogin(form.login())
                .orElseThrow(() -> new UnauthorizedException(mensagem));

        boolean senhasBatem = encoder.matches(form.senha(), usuario.getSenha());

        if(!senhasBatem){
            throw new UnauthorizedException(mensagem);
        }

        return toAuthResponse(usuario);
    }

    private AuthResponse toAuthResponse(UsuarioEntity usuario){
        var token = jwtService.generateToken(usuario);
        var expiraEm = jwtService.getExpirationSeconds();
        return new AuthResponse(
                token,
                "Bearer",
                expiraEm,
                usuario.getNome()
        );
    }


}
