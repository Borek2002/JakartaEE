package pl.edu.pg.eti.kask.authentication.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import jakarta.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import jakarta.security.enterprise.authentication.mechanism.http.LoginToContinue;
import jakarta.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;

@ApplicationScoped
//@BasicAuthenticationMechanismDefinition(realmName = "Simple RPG")
//@FormAuthenticationMechanismDefinition(
//        loginToContinue = @LoginToContinue(
//                loginPage = "/authentication/form/login.xhtml",
//                errorPage = "/authentication/form/login_error.xhtml"
//        )
//)
@CustomFormAuthenticationMechanismDefinition(
        loginToContinue = @LoginToContinue(
                loginPage = "/authentication/custom/login.xhtml",
                errorPage = "/authentication/custom/login_error.xhtml"
        )
)
@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "jdbc/Hotels",
        callerQuery = "select password from users where email = ?",
        groupsQuery = "select role from users__roles where id = (select id from users where email = ?)",
        hashAlgorithm = Pbkdf2PasswordHash.class
)
public class AuthenticationConfig {
}