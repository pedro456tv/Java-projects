package PDP.task1.valueobjects;

/**
 * Value Object representing a Sprint identifier.
 * Immutable and validated on creation.
 */
public class SprintID {
    private final String id;

    public SprintID(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Sprint ID cannot be null or empty");
        }
        this.id = id;
    }

    public String getId() { return id; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SprintID sprintID = (SprintID) o;
        return id.equals(sprintID.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
