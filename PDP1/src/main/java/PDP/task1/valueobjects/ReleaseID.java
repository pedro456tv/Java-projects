package PDP.task1.valueobjects;

/**
 * Value Object representing a Release identifier.
 * Immutable and validated on creation.
 */
public class ReleaseID {
    private final String id;

    public ReleaseID(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Release ID cannot be null or empty");
        }
        this.id = id;
    }

    public String getId() { return id; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReleaseID releaseID = (ReleaseID) o;
        return id.equals(releaseID.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
