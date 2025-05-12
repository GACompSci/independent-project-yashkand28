import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class App extends Application {
    private GameLogic game = new GameLogic();
    private Button[][] buttons = new Button[4][4];
    private Card[][] deck = game.getDeck();

    private Card firstCard = null;
    private Button firstButton = null;
    private int firstRow, firstCol;

    private Label timerLabel = new Label("Time Left: 2:00");
    private int timeLeft = 120;
    private Timeline timer;

    private Label scoreLabel = new Label("Score: 0");
    private Label statusLabel = new Label("Find all matches!");
    private Button restartButton = new Button("Restart");

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();

        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                Button btn = new Button("X");
                btn.setMinSize(60, 60);
                int row = r;
                int col = c;

                btn.setOnAction(e -> handleClick(btn, row, col));

                buttons[r][c] = btn;
                grid.add(btn, c, r);
            }
        }

        restartButton.setOnAction(e -> resetGame());

        VBox root = new VBox(10, timerLabel, scoreLabel, grid, statusLabel, restartButton);
        Scene scene = new Scene(root, 300, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Memory Match Game");
        primaryStage.show();

        startTimer(); // Start the countdown
    }

    private void startTimer() {
        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timeLeft--;
            int minutes = timeLeft / 60;
            int seconds = timeLeft % 60;
            timerLabel.setText(String.format("Time Left: %d:%02d", minutes, seconds));

            if (timeLeft <= 0) {
                timer.stop();
                disableAll(true);
                statusLabel.setText("⏰ Time's up! Final Score: " + game.getScore());
            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    private void handleClick(Button btn, int row, int col) {
        Card clickedCard = deck[row][col];

        if (clickedCard._isFaceUp() || btn == firstButton || timeLeft <= 0) {
            return;
        }

        clickedCard.flip();
        btn.setText(String.valueOf(clickedCard.getCardNumber()));

        if (firstCard == null) {
            firstCard = clickedCard;
            firstButton = btn;
            firstRow = row;
            firstCol = col;
        } else {
            disableAll(true);

            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(e -> {
                boolean match = game.test(firstRow, firstCol, row, col);

                if (!match) {
                    firstCard.flip();
                    clickedCard.flip();
                    firstButton.setText("X");
                    btn.setText("X");
                    statusLabel.setText("Not a match. Try again!");
                } else {
                    scoreLabel.setText("Score: " + game.getScore());
                    statusLabel.setText("Nice! You found a match.");
                }

                firstCard = null;
                firstButton = null;
                disableAll(false);
            });
            pause.play();
        }
    }

    private void resetGame() {
        game = new GameLogic();
        deck = game.getDeck();
        timeLeft = 120;
        timerLabel.setText("Time Left: 2:00");
        scoreLabel.setText("Score: 0");
        statusLabel.setText("Find all matches!");
        firstCard = null;
        firstButton = null;

        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                buttons[r][c].setText("X");
                buttons[r][c].setDisable(false);
            }
        }

        if (timer != null) {
            timer.stop();
        }
        startTimer();
    }

    private void disableAll(boolean disable) {
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                buttons[r][c].setDisable(disable);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}