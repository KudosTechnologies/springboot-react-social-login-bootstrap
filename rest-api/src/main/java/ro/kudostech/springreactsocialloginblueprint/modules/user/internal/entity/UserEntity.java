package ro.kudostech.springreactsocialloginblueprint.modules.user.internal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.kudostech.springreactsocialloginblueprint.modules.user.api.model.OAuth2Provider;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String password;
  private String name;

  @Column(unique = true)
  private String email;

  private String role;
  private String imageUrl;

  @Enumerated(EnumType.STRING)
  private OAuth2Provider provider;

  private String providerId;
}
