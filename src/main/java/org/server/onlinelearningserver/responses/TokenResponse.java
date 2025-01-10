package org.server.onlinelearningserver.responses;

public class TokenResponse extends BasicResponse{
    private boolean isValid;

    public TokenResponse(boolean success, String error, boolean isValid) {
        super(success, error);
        this.isValid = isValid;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
