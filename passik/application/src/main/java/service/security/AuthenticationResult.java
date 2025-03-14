package service.security;

/**
 * Helper record to track the success or failure of user's authentication.
 *
 * @param success if the authentication was successful or not
 * @param message output of executed login.
 */
public record AuthenticationResult(boolean success, String message) { }
