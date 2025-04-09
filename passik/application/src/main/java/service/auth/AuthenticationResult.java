package service.auth;

/**
 * TODO: instead of String type for message, provide an enum with each possible outcome. 
 * And in general for each Result record.
 * 
 * Helper record to track the isSuccessful or failure of user's authentication.
 *
 * @param success if the authentication was successful or not
 * @param message output of executed login.
 */
public record AuthenticationResult(boolean success, String message) { }
