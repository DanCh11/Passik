package utils;

import de.daycu.passik.model.auth.MasterId;
import de.daycu.passik.model.auth.MasterLogin;
import de.daycu.passik.model.auth.MasterPassword;
import de.daycu.passik.model.vault.*;

import java.util.UUID;

/**
 * Class that provides mock data for testing purposes for different use cases.
 */
public final class Fixtures {
    public final static MasterId masterid = new MasterId(UUID.randomUUID());
    public final static MasterLogin basicMasterLogin = new MasterLogin("masterLogin");
    public final static MasterLogin unknownMasterLogin = new MasterLogin("unknownLogin");
    public final static MasterPassword basicMasterPassword = new MasterPassword("masterPassword");
    public final static MasterPassword wrongMasterPassword = new MasterPassword("1234");
    public final static MasterPassword unknownMasterPassword = new MasterPassword("unknownPassword");
    public final static MasterPassword encodedMasterPassword = new MasterPassword("encodedPassword");

    public final static MasterPassword canonicalMasterPassword = new MasterPassword("MasterPassword1!");
    public final static MasterPassword missingUpperCaseMasterPassword = new MasterPassword("masterpassword1!");
    public final static MasterPassword missingDigitMasterPassword = new MasterPassword("MasterPassword!");
    public final static MasterPassword missingSpecialCharacterMasterPassword = new MasterPassword("MasterPassword1");

    public final static CredentialLogin credentialLogin = new CredentialLogin("login");
    public final static CredentialLogin updatedCredentialLogin = new CredentialLogin("updatedLogin");
    public final static CredentialPassword credentialPassword = new CredentialPassword("password");
    public final static Credentials credentials = new Credentials(credentialLogin, credentialPassword);
    public final static DigitalServiceName digitalServiceName = new DigitalServiceName("digital service");
    public final static DigitalAccount digitalAccount = new DigitalAccount(masterid, digitalServiceName, credentials);
    public final static CredentialPassword encryptedCredentialPassword = new CredentialPassword("encryptedPassword");
    public final static Credentials encryptedCredentials = new Credentials(credentialLogin, encryptedCredentialPassword);
    public final static Credentials updateCredentials = new Credentials(updatedCredentialLogin, encryptedCredentialPassword);
    public final static DigitalAccount digitalAccountWithEncryptedPassword = new DigitalAccount(masterid, digitalServiceName, encryptedCredentials);
    public final static DigitalAccount updatedDigitalAccount = new DigitalAccount(masterid, digitalServiceName, updateCredentials);
}
