package PDP.task1.valueobjects;

/**
 * Value Object representing an estimation log entry.
 * Immutable and validated on creation.
 */
public class EstimationLogEntry {
    private final String name;
    private final String description;
    private final int hoursRemaining;
    private final String volunteer;

    public EstimationLogEntry(String name, String description, int hoursRemaining, String volunteer) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (hoursRemaining < 0) {
            throw new IllegalArgumentException("Hours remaining cannot be negative");
        }
        this.name = name;
        this.description = description;
        this.hoursRemaining = hoursRemaining;
        this.volunteer = volunteer;
    }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getHoursRemaining() { return hoursRemaining; }
    public String getVolunteer() { return volunteer; }
}
