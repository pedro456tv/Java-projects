package PDP.task1;

import PDP.task1.valueobjects.EstimationLogEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Entity representing a task that needs to be completed as part of a BacklogItem.
 * Tasks have identity and can be assigned to team members.
 */
public class Task {
    private final String id;
    private String name;
    private String description;
    private int hoursRemaining;
    private String volunteer;
    private final List<EstimationLogEntry> estimationLogEntries = new ArrayList<>();

    /**
     * Creates a new Task
     * @param name Name of the task
     * @param description Detailed description of the task
     */
    public Task(String name, String description) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.hoursRemaining = 0;

        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
    }

    /**
     * Updates the remaining hours estimate
     * @param hours New estimate (must be >= 0)
     */
    public void updateHoursRemaining(int hours) {
        if (hours < 0) {
            throw new IllegalArgumentException("Hours remaining cannot be negative");
        }
        this.hoursRemaining = hours;
    }

    /**
     * Assigns this task to a team member
     * @param volunteer Name or identifier of the team member
     */
    public void assignTo(String volunteer) {
        if (volunteer == null || volunteer.trim().isEmpty()) {
            throw new IllegalArgumentException("Volunteer cannot be null or empty");
        }
        this.volunteer = volunteer;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getHoursRemaining() { return hoursRemaining; }
    public String getVolunteer() { return volunteer; }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Adds log entry to estimate task
     * @param entry
     */
    public void addEstimationLogEntry(EstimationLogEntry entry) {
        if (entry == null) {
            throw new IllegalArgumentException("Log entry cannot be null");
        }
        estimationLogEntries.add(entry);
        this.hoursRemaining = entry.getHoursRemaining(); // Update current estimate
    }

    public List<EstimationLogEntry> getEstimationLogEntries() {
        return new ArrayList<>(estimationLogEntries);
    }
}
