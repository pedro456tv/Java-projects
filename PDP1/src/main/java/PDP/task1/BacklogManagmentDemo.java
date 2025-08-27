package PDP.task1;

import PDP.task1.valueobjects.*;

public class BacklogManagmentDemo {
    public static void main(String[] args) {
        System.out.println("=== BACKLOG MANAGEMENT SYSTEM DEMO ===");

        // -------------------------------------------------------------------
        // 1. Backlog Creation Tests
        // -------------------------------------------------------------------
        System.out.println("\n1. Testing Backlog Creation:");

        // Valid backlog creation
        Backlog productBacklog = new Backlog("Product Backlog", "Main backlog for the product");
        System.out.println("✓ Created backlog: " + productBacklog.getName());

        // -------------------------------------------------------------------
        // 2. Backlog Item Tests
        // -------------------------------------------------------------------
        System.out.println("\n2. Testing Backlog Items:");
        // Valid backlog item
        BacklogItem loginFeature = new BacklogItem(
                "As a user, I want to login",
                5,
                "Auth feature",
                "Feature"
        );
        System.out.println("✓ Created backlog item: " + loginFeature.getSummary());

        // Invalid story points test
        try {
            new BacklogItem("Invalid story points", -1, "Should fail", "Bug");
            System.out.println("✖ Failed to catch negative story points");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Properly caught negative story points: " + e.getMessage());
        }

        // -------------------------------------------------------------------
        // 3. Task Management Tests
        // -------------------------------------------------------------------
        System.out.println("\n3. Testing Task Management:");

        Task designTask = new Task("Design UI", "Create mockups");
        designTask.updateHoursRemaining(8);
        designTask.assignTo("UI Designer");

        // Test estimation logging
        designTask.addEstimationLogEntry(new EstimationLogEntry(
                "Initial estimate",
                "Team consensus",
                8,
                "Scrum Master"
        ));
        designTask.addEstimationLogEntry(new EstimationLogEntry(
                "Revised estimate",
                "Found complexity",
                12,
                "Lead Designer"
        ));

        System.out.println("✓ Task has " + designTask.getEstimationLogEntries().size() + " log entries");
        System.out.println("  Current estimate: " + designTask.getHoursRemaining() + "h");

        // Test task validation
        try {
            new Task("", "Should fail");
            System.out.println("✖ Failed to catch null task name");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Properly caught null task name: " + e.getMessage());
        }

        // -------------------------------------------------------------------
        // 4. Sprint Assignment Tests
        // -------------------------------------------------------------------
        System.out.println("\n4. Testing Sprint Assignment:");

        loginFeature.assignToSprint(new SprintID("sprint-5"));
        System.out.println("✓ Assigned to sprint. New status: " + loginFeature.getStatus());

        // -------------------------------------------------------------------
        // 5. Value Object Tests
        // -------------------------------------------------------------------
        System.out.println("\n5. Testing Value Objects:");

        ProductID product1 = new ProductID("prod-123");
        ProductID product2 = new ProductID("prod-123");
        System.out.println("✓ Product IDs equal: " + product1.equals(product2));

        try {
            new ReleaseID(null);
            System.out.println("✖ Failed to catch null ReleaseID");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Properly caught null ReleaseID: " + e.getMessage());
        }

        // -------------------------------------------------------------------
        // 6. Full Workflow Demonstration
        // -------------------------------------------------------------------
        System.out.println("\n6. Full Workflow Demonstration:");

        // Create and populate backlog
        Backlog sprintBacklog = new Backlog("Sprint 5", "Current sprint work");

        BacklogItem reportingFeature = new BacklogItem(
                "As a manager, I want reports",
                8,
                "Reporting dashboard",
                "Feature"
        );

        Task dbTask = new Task("Design schema", "Create reporting tables");
        dbTask.updateHoursRemaining(16);
        dbTask.assignTo("DB Admin");
        dbTask.addEstimationLogEntry(new EstimationLogEntry(
                "Initial",
                "Basic estimate",
                16,
                "Architect"
        ));

        Task uiTask = new Task("Build dashboard", "React components");
        uiTask.updateHoursRemaining(24);
        uiTask.assignTo("Frontend Dev");

        reportingFeature.addTask(dbTask);
        reportingFeature.addTask(uiTask);
        reportingFeature.setStatus("Scheduled");
        sprintBacklog.addBacklogItem(reportingFeature);

        BacklogItem loggingFeature = new BacklogItem(
                "Logging for users",
                10,
                null,
                "Epic"
        );
        Task loggingTask = new Task("Logging",null);
        loggingTask.addEstimationLogEntry(new EstimationLogEntry(
                "Logging task estimate",
                null,
                10,
                "Intern"
        ));
        loggingTask.assignTo("Intern");
        loggingTask.updateHoursRemaining(8);
        loggingFeature.addTask(loggingTask);
        loggingFeature.setProductId(new ProductID("Prod-123"));
        sprintBacklog.addBacklogItem(loggingFeature);

        // Show final state
        System.out.println("\nFinal Backlog State:");
        System.out.println("Backlog: " + sprintBacklog.getName());
        for (BacklogItem item : sprintBacklog.getBacklogItems()) {
            System.out.println("- " + item.getSummary() +
                    " (Points: " + item.getStoryPoints() +
                    ", Status: " + item.getStatus() + ")");

            for (Task task : item.getTasks()) {
                System.out.println("  • " + task.getName() +
                        " (" + task.getHoursRemaining() + "h)" +
                        " - Assigned to: " + task.getVolunteer());

                for (EstimationLogEntry log : task.getEstimationLogEntries()) {
                    System.out.println("    ↳ " + log.getName() +
                            ": " + log.getHoursRemaining() + "h" +
                            " (" + log.getVolunteer() + ")");
                }
            }
        }

        System.out.println("\n=== DEMO COMPLETE ===");
    }
}