package com.berhan.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class JwtTokenManager {

    // sistem tarafında yaptığımız değişiklikler proje açıkken yapıldığı için sistem değişkenlerini güncellediğimizde
    // projeyi kapatıp açmamız lazım
    @Value("${jwt.secret-key}")
    String secretKey;
    @Value("${jwt.issuer}")
    String issuer;// bu tokeni kim üretiyor
    @Value("${jwt.audience}")
    String audience;// bu token kimin için üretiliyor
    Long expiration = System.currentTimeMillis()+(1000*60*5);

//    public Optional<String> createToken(Long id){
//        String token = null;
//        Date date = new Date(expiration);
//        try{
//            token= JWT.create()
//                    .withAudience(audience)
//                    .withIssuer(issuer)
//                    .withIssuedAt(new Date())
//                    .withExpiresAt(date)
//                    .withClaim("id",id)// id ye göre secret key vereceimizi belirtiyoruz
//                    .sign(Algorithm.HMAC512(secretKey));
//            return Optional.of(token);
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//            return Optional.empty();
//        }
//    }


//    public Optional<String> createToken(Long id,ERole role){
//        String token = null;
//        Date date = new Date(System.currentTimeMillis()+(1000*60*5));
//        try{
//            token= JWT.create()
//                    .withAudience(audience)
//                    .withIssuer(issuer)
//                    .withClaim("rol",role.toString())
//                    .withIssuedAt(new Date())
//                    .withExpiresAt(date)
//                    .withClaim("id",id)// id ye göre secret key vereceimizi belirtiyoruz
//                    .sign(Algorithm.HMAC512(secretKey));
//            return Optional.of(token);
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//            return Optional.empty();
//        }
//    }

//    public Boolean validateToken(String token){
//
//        try{
//            Algorithm algorithm = Algorithm.HMAC512(secretKey);
//            JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).withAudience(audience).build();
//            DecodedJWT decodedJWT = verifier.verify(token);
//            if (decodedJWT == null){
//                return false;
//            }
//            return true;
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//            throw new AuthManagerException(ErrorType.INVALID_TOKEN);
//        }
//    }

    public Optional<Long> getIdFromToken(String token){

        try{
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).withAudience(audience).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            if (decodedJWT == null){
                throw new RuntimeException();
            }
            Long id = decodedJWT.getClaim("id").asLong();// jason türünde döner id isimli parametrenin değerini aldık.
            return Optional.of(id);
        }catch (Exception e){
            e.getMessage();
            throw new RuntimeException();
        }

    }
//    public Optional<String> getRoleFromToken(String token){
//
//        try{
//            Algorithm algorithm = Algorithm.HMAC512(secretKey);
//            JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).withAudience(audience).build();
//            DecodedJWT decodedJWT = verifier.verify(token);
//            if (decodedJWT == null){
//                throw new AuthManagerException(ErrorType.INVALID_TOKEN);
//            }
//            String role = decodedJWT.getClaim("rol").asString();// jason türünde döner id isimli parametrenin değerini aldık.
//            return Optional.of(role);
//        }catch (Exception e){
//            e.getMessage();
//            throw new AuthManagerException(ErrorType.INVALID_TOKEN);
//        }
//
//    }
}
