package ro.kudostech.springreactsocialloginblueprint.tools;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class JwtKeyGenerator {

  public static void main(String[] args) {
    SecretKey signingKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    String base64EncodedSecretKey = Encoders.BASE64.encode(signingKey.getEncoded());
    System.out.println("Secret key: " + base64EncodedSecretKey);
  }
}
