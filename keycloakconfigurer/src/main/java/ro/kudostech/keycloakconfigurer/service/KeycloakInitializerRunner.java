package ro.kudostech.keycloakconfigurer.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KeycloakInitializerRunner implements CommandLineRunner {

  @Value("${keycloak.base-url}")
  public String keycloakBaseUrl;

  @Value("${keycloak.client-id}")
  public String keycloakClientId;

  @Value("${keycloak.client-secret}")
  public String keycloakClientSecret;

  private static final String REALM_NAME = "kudconnect";
  private static final List<String> REDIRECT_URL_LIST = List.of("http://localhost:8080/*");

  private final Keycloak keycloakAdmin;

  private static final List<UserPass> USER_LIST =
      Arrays.asList(new UserPass("admin@test.com", "admin"), new UserPass("user@test.com", "user"));

  record UserPass(String id, String password, String email) {
    public UserPass(String email, String password) {
      this(null, password, email);
    }
  }

  @Override
  public void run(String... args) {
    log.info("Initializing '{}' realm in Keycloak ...", REALM_NAME);

    cleanUpRealm();

    RealmRepresentation realmRepresentation = new RealmRepresentation();
    realmRepresentation.setRealm(REALM_NAME);
    realmRepresentation.setEnabled(true);
    realmRepresentation.setRegistrationAllowed(true);
    realmRepresentation.setLoginWithEmailAllowed(true);
    realmRepresentation.setRegistrationEmailAsUsername(true);

    configureClient(realmRepresentation);

    configureDefaultUsers(realmRepresentation);

    keycloakAdmin.realms().create(realmRepresentation);
  }

  private void configureClient(RealmRepresentation realmRepresentation) {
    ClientRepresentation client = new ClientRepresentation();
    client.setClientId(keycloakClientId);
    client.setPublicClient(false);
    client.setRedirectUris(REDIRECT_URL_LIST);
    client.setSecret(keycloakClientSecret);
    client.setEnabled(true);
    client.setAuthorizationServicesEnabled(true);
    client.setStandardFlowEnabled(true);
    client.setDirectAccessGrantsEnabled(true);
    realmRepresentation.setClients(Collections.singletonList(client));
  }

  private void configureDefaultUsers(RealmRepresentation realmRepresentation) {
    List<UserRepresentation> userRepresentations =
        USER_LIST.stream()
            .map(
                userPass -> {
                  // User Credentials
                  CredentialRepresentation credentialRepresentation =
                      new CredentialRepresentation();
                  credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
                  credentialRepresentation.setValue(userPass.password());

                  UserRepresentation userRepresentation = new UserRepresentation();
                  userRepresentation.setUsername(userPass.email());
                  userRepresentation.setEmail(userPass.email());
                  userRepresentation.setFirstName(userPass.email());
                  userRepresentation.setLastName(userPass.email());
                  userRepresentation.setEnabled(true);
                  userRepresentation.setCredentials(List.of(credentialRepresentation));
                  return userRepresentation;
                })
            .toList();
    realmRepresentation.setUsers(userRepresentations);
  }

  private void cleanUpRealm() {
    Optional<RealmRepresentation> representationOptional =
        keycloakAdmin.realms().findAll().stream()
            .filter(r -> r.getRealm().equals(REALM_NAME))
            .findAny();
    if (representationOptional.isPresent()) {
      log.info("Removing already pre-configured '{}' realm", REALM_NAME);
      keycloakAdmin.realm(REALM_NAME).remove();
    }
  }
}
