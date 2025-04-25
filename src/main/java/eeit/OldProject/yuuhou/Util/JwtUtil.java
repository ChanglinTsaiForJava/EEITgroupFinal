package eeit.OldProject.yuuhou.Util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    // ✅ 安全長度密鑰
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long jwtExpirationMs = 3600000; // 1 小時

    // ✅ 建立 Token（加上 role）
    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .addClaims(Map.of("role", role))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key)
                .compact();
    }

    // ✅ 取得 Email（subject）
    public String getEmailFromToken(String token) {
        return parseClaims(token).getSubject();
    }

    // ✅ 取得 Role
    public String getRoleFromToken(String token) {
        return parseClaims(token).get("role", String.class);
    }

    // ✅ 驗證 Token
    public boolean validateToken(String token) {
        try {
            parseClaims(token); // 有解析成功就代表合法
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    // ✅ 共用方法：解析 Token
    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
