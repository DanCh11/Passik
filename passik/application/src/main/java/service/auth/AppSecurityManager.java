package service.auth;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import port.out.persistance.MasterRepository;
import service.encryption.EncryptionService;

/**
 * Security Initializations on startup to create master's realm.
 */
public class AppSecurityManager {

    private static boolean initialized = false;

    /**
     * Prevents multiple creation of the same instance. If the instance runs multiple times, then shiro is reconfigured
     * which can potentially break the thread state or user sessions.
     */
    public static synchronized void initialize(MasterRepository masterRepository, EncryptionService encryptionService) {
        if (initialized) return;

        MasterRealm masterRealm = new MasterRealm(masterRepository, encryptionService);
        DefaultSecurityManager securityManager = new DefaultSecurityManager(masterRealm);
        SecurityUtils.setSecurityManager(securityManager);

        initialized = true;
    }

    /**
     * Resets the initialized flag. This is useful for testing.
     */
    public static synchronized void reset() {
        initialized = false;
    }

}
