package fit.se.kltn.implement;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtServiceImpl {


//    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";


    public String generateToken(UserDetails userDetails){
        return Jwts.builder().setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
                .signWith(getSignKey())
                .compact();
    }
    public  <T> T extractClaim(String token, Function<Claims,T> claimsResolvers){
        final Claims claims= extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }
    public Key getSignKey() {
        byte[] key= Decoders.BASE64.decode("984hg493gh0439rthr0429uruj2309yh937gc763fe87t3f89723gfrtrendthi43uuiti45ii438u434673467537456jh44nnjnj");
        return Keys.hmacShaKeyFor(key);
    }
    public String extractUserName(String token){
        return extractClaim(token,Claims::getSubject);
    }
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token.replace("{", "").replace("}",""))
                .getBody();
    }
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username= extractUserName(token);
        return (username.equals(userDetails.getUsername())&& !isTokenExprired(token));
    }

    private boolean isTokenExprired(String token) {
        return extractClaim(token,Claims::getExpiration).before(new Date());
    }

    public String generateRefreshToken(Map<String, Object> extractClaims, UserDetails user) {
        return Jwts.builder()
                .setClaims(extractClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 604800000))
                .signWith(getSignKey())
                .compact();
    }
}
