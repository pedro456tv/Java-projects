package PDP.task1;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

import PDP.task1.valueobjects.*;

/**
 * Entity representing an item in the backlog.
 * BacklogItems have identity and can change over time while maintaining their identity.
 */
public class BacklogItem {
    private final String id;
    private String status;
    private String story;
    private int storyPoints;
    private String summary;
    private String type;
    private ProductID productId;
    private ReleaseID releaseId;
    private SprintID sprintId;
    private final List<Task> tasks = new ArrayList<>();

    /**
     * Creates a new BacklogItem with generated ID
     * @param story User story description
     * @param storyPoints Estimated complexity points
     * @param summary Brief summary of the item
     * @param type Type of work item (e.g., Feature, Bug, etc.)
     */
    public BacklogItem(String story, int storyPoints, String summary, String type) {
        this.id = UUID.randomUUID().toString();
        this.story = story;
        this.storyPoints = storyPoints;
        this.summary = summary;
        this.type = type;
        this.status = "New";

        if (storyPoints < 0) {
            throw new IllegalArgumentException("Cannot have < 0 story points");
        }
    }

    /**
     * Adds a task to this backlog item
     * @param task The task to add
     */
    public void addTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }
        tasks.add(task);
    }

    /**
     * Moves this item to a sprint
     * @param sprintId The sprint to assign to
     */
    public void assignToSprint(SprintID sprintId) {
        this.sprintId = sprintId;
        this.status = "Scheduled";
    }

    public String getId() { return id; }
    public String getStatus() { return status; }
    public String getStory() { return story; }
    public int getStoryPoints() { return storyPoints; }
    public String getSummary() { return summary; }
    public String getType() { return type; }
    public ProductID getProductId() { return productId; }
    public ReleaseID getReleaseId() { return releaseId; }
    public SprintID getSprintId() { return sprintId; }
    public List<Task> getTasks() { return new ArrayList<>(tasks); }

    public void setStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status cannot be null or empty");
        }
        this.status = status;
    }

    public void setStory(String story) {
        if (story == null || story.trim().isEmpty()) {
            throw new IllegalArgumentException("Story cannot be null or empty");
        }
        this.story = story;
    }

    public void setStoryPoints(int storyPoints) {
        if (storyPoints < 0) {
            throw new IllegalArgumentException("Story points cannot be negative");
        }
        this.storyPoints = storyPoints;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setType(String type) {
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Type cannot be null or empty");
        }
        this.type = type;
    }

    public void setProductId(ProductID productId) {
        this.productId = productId;
    }

    public void setReleaseId(ReleaseID releaseId) {
        this.releaseId = releaseId;
    }
}
