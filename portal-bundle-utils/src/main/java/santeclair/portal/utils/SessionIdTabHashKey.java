package santeclair.portal.utils;


public class SessionIdTabHashKey {

    private final String sessionId;
    private final Integer tabHash;

    public SessionIdTabHashKey(String sessionId, Integer tabHash) {
        this.sessionId = sessionId;
        this.tabHash = tabHash;
    }

    public String getSessionId() {
        return sessionId;
    }

    public Integer getTabHash() {
        return tabHash;
    }

    public boolean isSessionIdEquals(String sessionId) {
        return this.sessionId == null ? sessionId == null : this.sessionId.equals(sessionId);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((sessionId == null) ? 0 : sessionId.hashCode());
        result = prime * result + ((tabHash == null) ? 0 : tabHash.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SessionIdTabHashKey other = (SessionIdTabHashKey) obj;
        if (sessionId == null) {
            if (other.sessionId != null)
                return false;
        } else if (!sessionId.equals(other.sessionId))
            return false;
        if (tabHash == null) {
            if (other.tabHash != null)
                return false;
        } else if (!tabHash.equals(other.tabHash))
            return false;
        return true;
    }

}
