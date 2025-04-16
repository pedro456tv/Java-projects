package com.example.reflextest;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * @author Peter Francek
 * Game which tests user reflexes (static,dynamic,time), and shows the stats.
 * The control is mainly with the mouse. There are several tests to choose from.
 */
public class ReflexTest extends Application {
    private static final int SCREEN_WIDTH = 700, SCREEN_HEIGHT = 400;
    private static final int STATIC_TEST_COUNT = 5; //number of static tests
    private static final int DYNAMIC_TEST_COUNT = 10;//num of second for dynamic test
    private static final int TIME_TEST_REPETITIONS = 5;//number of time tests
    private static final int FONT_SIZE = 20;
    private static final int StartingInterval = 5; //min for time test interval
    private static final int randomIntervalStart = 3; //where from is minimally shown ?
    private static final int randomIntervalEnd = 7; //where from is maximally shown ?
    private static final String STATIC_TEXT = "At certain time intervals a red screen will appear, then you need to click on it as quickly as possible. " +
            "The game will measure the time from display to the user's mouse click. " +
            "This is repeated "+STATIC_TEST_COUNT+"-times, averaging the result and displaying it at the end.";
    private static final String DYNAMIC_TEXT = "Within " + DYNAMIC_TEST_COUNT + " seconds, red circles will appear" +
            " on the screen. Try to shoot down as many of them as possible and as fast as possible in this time. At the end you will see the number of" +
            " accurate hits and the average firing speed.";
    private static final String TIME_TEXT = "Between " + StartingInterval + " to "+ (StartingInterval +randomIntervalEnd)+ " seconds the timer will be displayed" +
            " on the screen. Sometimes at random time it will no longer display numbers just ? and your job is to click when "+
            "you think the timer has just stood still at zero. This is repeated "+TIME_TEST_REPETITIONS+"-times, averaging the result and displaying "+
            "how close you averaged from 0 in ms.";
    private static final double minRadius = 10; // min radius of circle
    private static final double maxRadius = 30; // max radius of circle
    private static final int randomStaticStart = 1000;//min interval for random rectangle
    private static final int randomStaticEnd = 5000;//max interval for random rectangle
    private static final int CircleDelayCreationTimer = 700; //time between circles creation
    private List<Long> reactionTimes;
    private final Map<Circle, Long> mapCircles = new HashMap<>();

    private int clicks;
    private int goodClicks;
    private int staticTestIndex;
    private int timeTestIndex;
    private long timeTestStartTime;

    private BorderPane root;

    /**
     * Launch function
     * @param args - input args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starting function - overridden from Application
     * @param primaryStage - Stage screen
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Reflex Tests");

        reactionTimes = new ArrayList<>();

        staticTestIndex = 0;
        timeTestIndex = 0;

        root = new BorderPane();
        root.setPadding(new Insets(10));

        showMainMenu();

        primaryStage.setScene(new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT));
        primaryStage.show();
    }

    private void showMainMenu() {
        root.getChildren().clear();
        root.setCenter(null);

        Text text = new Text("Reflex tests");
        text.setFont(Font.font(FONT_SIZE));
        TextFlow flow = new TextFlow(text);


        Button staticSpeedButton = new Button("Static Speed Test");
        staticSpeedButton.setOnAction(event -> showInstructions("static"));

        Button dynamicSpeedButton = new Button("Dynamic Speed Test");
        dynamicSpeedButton.setOnAction(event -> showInstructions("dynamic"));

        Button timeAccuracyButton = new Button("Time Accuracy Test");
        timeAccuracyButton.setOnAction(event -> showInstructions("time"));

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(event -> System.exit(0));

        VBox buttonBox = new VBox(10);
        buttonBox.setPadding(new Insets(10));
        buttonBox.getChildren().addAll(staticSpeedButton, dynamicSpeedButton, timeAccuracyButton, exitButton);
        buttonBox.setAlignment(Pos.CENTER);

        BorderPane topPane = new BorderPane();
        topPane.setTop(flow);
        root.setTop(topPane);
        root.setCenter(buttonBox);
    }

    /**
     *
     * @param what-what test instructions to show
     */
    private void showInstructions(String what) {
        root.getChildren().clear();
        reactionTimes.clear();

        Button staticStartTest = new Button("Start Test");
        Text instruction = new Text();
        instruction.setStroke(Color.BLACK);
        instruction.setFont(Font.font(FONT_SIZE));

        TextFlow flow = new TextFlow(instruction);
        if (what.equals("static")) {
            staticStartTest.setOnAction(event -> runStaticSpeedTest());
            staticTestIndex = 0;
            instruction.setText(STATIC_TEXT);
        } else if (what.equals("dynamic")) {
            staticStartTest.setOnAction(event -> runDynamicSpeedTest());
            instruction.setText(DYNAMIC_TEXT);
        } else {
            staticStartTest.setOnAction(event -> runTimeAccuracyTest());
            timeTestIndex = 0;
            instruction.setText(TIME_TEXT);
        }
        BorderPane topPane = new BorderPane();
        topPane.setPadding(new Insets(10));
        topPane.setCenter(staticStartTest);
        BorderPane.setAlignment(staticStartTest, Pos.CENTER);
        topPane.setBottom(flow);
        root.setCenter(topPane);
    }

    /**
     * function for static test, it creates RED rectangle at some random time, after we click on it, we measure time from
     * appearance of the rectangle until it was clicked by user
     */
    private void runStaticSpeedTest() {
        if (staticTestIndex < STATIC_TEST_COUNT) {
            root.getChildren().clear();
            Rectangle screen = new Rectangle(SCREEN_WIDTH, SCREEN_HEIGHT, Color.RED);

            screen.setOnMouseClicked(event -> handleStaticSpeedTestClick());
            Timeline showRectangle = new Timeline(new KeyFrame(Duration.millis(randomStaticStart + new Random().nextInt(randomStaticEnd)), e -> {
                root.setCenter(screen);
                timeTestStartTime = System.currentTimeMillis();
            }));
            showRectangle.play();

        } else {
            showResults("static");
        }
    }

    private void handleStaticSpeedTestClick() {
        long reactionTime = System.currentTimeMillis() - timeTestStartTime;
        System.out.println(reactionTime);
        reactionTimes.add(reactionTime);

        staticTestIndex++;
        runStaticSpeedTest();
    }

    /**
     * function for dynamic test, it is creating circles that need to be clicked as fast as possible during some time,
     * during this we are calculating precision, as well as time from appearance of Circle until it was clicked.
     */
    private void runDynamicSpeedTest() {
        clicks = 0;
        goodClicks = 0;
        root.getChildren().clear();

        Timeline countdownTimer = new Timeline();
        for (int i = DYNAMIC_TEST_COUNT; i >= 0; i--) {
            int finalI = i;
            Duration duration = Duration.seconds(DYNAMIC_TEST_COUNT - i);
            KeyFrame keyFrame = new KeyFrame(duration, e -> showCountdown(Integer.toString(finalI)));
            countdownTimer.getKeyFrames().add(keyFrame);
        }
        countdownTimer.play();

        Timeline circleCreationTimer = new Timeline();
        circleCreationTimer.setCycleCount(Timeline.INDEFINITE);
        circleCreationTimer.getKeyFrames().add(
                new KeyFrame(
                        Duration.millis(CircleDelayCreationTimer + new Random().nextDouble() * 500),
                        e -> {
                            Circle spot = createRandomSpot();
                            mapCircles.put(spot, System.currentTimeMillis());
                            spot.setOnMouseClicked(event -> handleCircleClick(spot));
                            root.setOnMouseClicked(mouseEvent -> clicks++);
                            root.getChildren().add(spot);
                        }
                )
        );
        circleCreationTimer.play();
        Timeline endTimer = new Timeline(new KeyFrame(Duration.seconds(DYNAMIC_TEST_COUNT), e -> {
            circleCreationTimer.stop();
            showResults("dynamic");
        }));
        endTimer.play();
    }

    /**
     *
     * @param time-time until the end of test
     */
    private void showCountdown(String time) {
        Text countdownText = new Text(time);
        countdownText.setFont(Font.font("Arial", FontWeight.BOLD, FONT_SIZE));
        BorderPane topPane = new BorderPane();
        topPane.setTop(countdownText);
        BorderPane.setAlignment(countdownText, Pos.TOP_RIGHT);
        root.setTop(topPane);
    }

    /**
     *
     * @return Circle object
     */
    private Circle createRandomSpot() {

        double radius = minRadius + new Random().nextDouble() * (maxRadius - minRadius);

        Point2D xy = getRandomCoordinate(radius);

        Circle spot = new Circle(xy.getX(), xy.getY(), radius);
        spot.setFill(Color.RED);

        return spot;
    }

    /**
     *
     * @param radius - radius of circle
     * @return x,y coordinate as Point2D object
     */
    private Point2D getRandomCoordinate(double radius) {
        double x;
        double y;
        boolean collides;

        do {
            x = radius + new Random().nextDouble() * (SCREEN_WIDTH - 2 * radius);
            y = radius + new Random().nextDouble() * (SCREEN_HEIGHT - 2 * radius);
            collides = checkCollision(x, y, radius);
        } while (collides);

        return new Point2D(x, y);
    }

    /**
     *
     * @param x-coordinate x
     * @param y-coordinate y
     * @param radius-radius of circle
     * @return true if collision detected else false
     */
    private boolean checkCollision(double x, double y, double radius) {
        for (Node node : root.getChildren()) {
            if (node instanceof Circle existingSpot) {
                double existingX = existingSpot.getCenterX();
                double existingY = existingSpot.getCenterY();
                double existingRadius = existingSpot.getRadius();

                double distance = Math.sqrt(Math.pow(existingX - x, 2) + Math.pow(existingY - y, 2));

                if (distance <= existingRadius + radius) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     *
     * @param circle-circle that was clicked
     */
    private void handleCircleClick(Circle circle) {
        long reactionTime = System.currentTimeMillis() - mapCircles.get(circle);
        reactionTimes.add(reactionTime);
        mapCircles.remove(circle);
        goodClicks++;
        root.getChildren().remove(circle);
    }

    /**
     * function for time test, where a number is randomly picked from interval, and countdown is started.
     * Countdown is shown on screen and during this at random time it begins to show only ?, user has to click when he
     * thinks the countdown ended, then we calculate how far in milliseconds was he from actual ending of the countdown
     */
    private void runTimeAccuracyTest() {
        if (timeTestIndex < TIME_TEST_REPETITIONS) {
            root.getChildren().clear();

            int delaySeconds = StartingInterval + new Random().nextInt(randomIntervalEnd + 1);

            int randomIntervalNumber = Integer.min(randomIntervalEnd-1, randomIntervalStart + new Random().nextInt(delaySeconds - 1));


            Timeline countdownTimer = new Timeline();
            for (int i = delaySeconds; i >= 0; i--) {
                int finalI = i;
                Duration duration = Duration.seconds(delaySeconds - i);
                KeyFrame keyFrame = new KeyFrame(duration, e -> {
                    if (finalI < randomIntervalNumber) {
                        showCountdownText("?");
                    } else {
                        showCountdownText(Integer.toString(finalI));
                    }
                });
                countdownTimer.getKeyFrames().add(keyFrame);
            }

            timeTestStartTime = System.currentTimeMillis();
            countdownTimer.play();

            root.setOnMouseClicked(mouseEvent -> {
                long responseTime = System.currentTimeMillis() - timeTestStartTime;
                countdownTimer.stop();
                if (responseTime < (delaySeconds * 1000L)) {

                    reactionTimes.add(delaySeconds * 1000L - responseTime);
                    showResultsTime("Early");
                } else if (responseTime > (delaySeconds * 1000L)) {

                    reactionTimes.add(responseTime - delaySeconds * 1000L);
                    showResultsTime("Late");
                } else {
                    reactionTimes.add(0L);
                    showResultsTime("Perfect");
                }
                root.setOnMouseClicked(mouseEvent1 -> {});
                PauseTransition delay = new PauseTransition(Duration.millis(1500));
                delay.setOnFinished(event -> {
                    runTimeAccuracyTest();
                    timeTestIndex++;
                });
                delay.play();
            });
        } else {
            showResults("time");
        }
    }

    /**
     *
     * @param text - show numbers or ? on screen countdown(for time test)
     */
    private void showCountdownText(String text) {
        root.getChildren().clear();
        Text countdownText = new Text(text);
        countdownText.setFont(Font.font("Arial", FontWeight.BOLD, SCREEN_HEIGHT - 50));
        BorderPane topPane = new BorderPane();
        topPane.setTop(countdownText);
        BorderPane.setAlignment(countdownText, Pos.CENTER);
        root.setTop(topPane);
    }

    /**
     *
     * @param result - result to show
     */
    private void showResultsTime(String result) {
        root.getChildren().clear();
        Text resultText = new Text("Result: " + reactionTimes.get(reactionTimes.size() - 1) + "ms from 0");
        Text lateSoon = new Text(result + "!");
        resultText.setFont(Font.font("Arial", FontWeight.BOLD, FONT_SIZE));
        lateSoon.setFont(Font.font("Arial", FontWeight.BOLD, FONT_SIZE));
        BorderPane topPane = new BorderPane();
        topPane.setTop(resultText);
        topPane.setCenter(lateSoon);
        BorderPane.setAlignment(resultText, Pos.CENTER);
        root.setTop(topPane);
    }

    /**
     *
     * @param what-what to display in results
     */
    private void showResults(String what) {
        root.getChildren().clear();

        double averageReactionTime = calculateAverageReactionTime();

        VBox resultBox = new VBox(10);
        resultBox.setAlignment(Pos.CENTER);

        Text averageTimeText = new Text("Average Reaction Time: " + averageReactionTime + " ms");
        if (reactionTimes.isEmpty()) {
            averageTimeText.setText("Average Reaction Time: Infinity ms");
        }
        averageTimeText.setFont(Font.font(FONT_SIZE));
        resultBox.getChildren().add(averageTimeText);

        if (what.equals("dynamic")) {
            System.out.println("Num of hits: " + goodClicks + "/" + clicks);
            Text clickText = new Text("Number of hits: " + goodClicks + "/" + clicks + " in " + DYNAMIC_TEST_COUNT + " seconds");
            clickText.setFont(Font.font(FONT_SIZE));
            resultBox.getChildren().add(clickText);
        }

        Button backButton = new Button("Back to Menu");
        backButton.setOnAction(event -> showMainMenu());

        Button saveButton = new Button("Save Stat");
        switch (what) {
            case "static" -> saveButton.setOnAction(event -> save("static"));
            case "dynamic" -> saveButton.setOnAction(event -> save("dynamic"));
            case "time" -> saveButton.setOnAction(event -> save("time"));
        }

        VBox buttonBox = new VBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(backButton, saveButton);

        BorderPane topPane = new BorderPane();
        topPane.setTop(buttonBox);
        topPane.setCenter(resultBox);

        BorderPane.setAlignment(backButton, Pos.CENTER);

        root.setTop(topPane);
    }


    private double calculateAverageReactionTime() {
        long sum = 0;
        for (long time : reactionTimes) {
            sum += time;
        }
        return Math.round((double) sum / reactionTimes.size());
    }

    /**
     *
     * @param what - what to save whether(static,dynamic,time)
     */
    private void save(String what) {
        try {
            FileWriter fileWriter = new FileWriter("game_stats.txt");

            String gameStats = "average time: " + calculateAverageReactionTime() + "\n";
            if (what.equals("dynamic")) {
                gameStats += "number of hits: " + goodClicks + "/" + clicks + "\n";
            }
            fileWriter.write(gameStats);
            fileWriter.close();
            System.out.println("Game stats saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving game stats: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

