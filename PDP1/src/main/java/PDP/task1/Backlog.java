package PDP.task1;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Aggregate Root representing a Backlog.
 * A Backlog contains BacklogItems and serves as the entry point for modifications.
 */
public class Backlog {
    private final UUID id;
    private String name;
    private String description;
    private final List<BacklogItem> backlogItems = new ArrayList<>();

    /**
     * Creates a new Backlog with generated ID
     * @param name The name of the backlog
     * @param description Description of what this backlog represents
     */
    public Backlog(String name, String description) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
    }

    /**
     * Adds a new item to the backlog
     * @param item The backlog item to add
     * @throws IllegalArgumentException if item is null
     */
    public void addBacklogItem(BacklogItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Backlog item cannot be null");
        }
        backlogItems.add(item);
    }

    /**
     * Removes an item from the backlog
     * @param item The backlog item to remove
     * @return true if item was found and removed
     */
    public boolean removeBacklogItem(BacklogItem item) {
        return backlogItems.remove(item);
    }
    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public List<BacklogItem> getBacklogItems() { return new ArrayList<>(backlogItems); }
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
