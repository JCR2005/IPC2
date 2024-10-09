package token;

import JPA.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Date;

public class TokenService {

    private static final String SECRET = "your-256-bit-secret"; // Cambia esto por una clave secreta robusta

    public String generateToken(Usuario usuario) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        String token = JWT.create()
                .withSubject(usuario.getUsuario().toString()) // Aquí puedes agregar más datos del usuario
                .withClaim("tipoCuenta", usuario.getTipoCuenta().toString()) // Cambia .withSubject por .withClaim para agregar otro dato
                .withIssuedAt(new Date())
                // .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Se elimina la expiración
                .sign(algorithm);
        return token;
    }

    public DecodedJWT verifyToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        return JWT.require(algorithm)
                .build()
                .verify(token);
    }
}
