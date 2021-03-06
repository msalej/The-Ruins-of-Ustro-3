package view;

import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.MenuButton;
import javafx.scene.text.Font;
import controller.SaveController;
import model.Creature;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class ViewManager {

    private final String FONT_PATH1 = "src/view/resources/brotherDeluxe1350____2011.ttf";
    private final String FONT_PATH2 = "src/view/resources/Kenney High Square.ttf";
    private static final String BUTTON_RELEASED_STYLE = "-fx-background-color: transparent; -fx-background-image: url('view/resources/buttonLong_brown.png');";
    private static final String BUTTON_PRESSED_STYLE = "-fx-background-color: transparent; -fx-background-image: url('view/resources/buttonLong_brown_pressed.png');";
    //private static final String BUTTON_RELEASED_STYLE2 = "-fx-background-color: transparent; -fx-background-image: url('view/resources/buttonSquare_brown.png');";
    //private static final String BUTTON_PRESSED_STYLE2 = "-fx-background-color: transparent; -fx-background-image: url('view/resources/buttonSquare_brown_pressed.png');";
    private static final int STAGE_WIDTH = 800;
    private static final int STAGE_HEIGHT = 600;

    private AnchorPane mainPane;
    private Stage mainStage;

    private MenuButton newGame;
    private MenuButton loadGame;
    private MenuButton helpButton;

    private SaveController saveController;
    private Creature loadedPlayer;
    private boolean loaded = false;

    private Text skillPoints;

    public ViewManager() throws FileNotFoundException {
        this.loadedPlayer = new Creature();
        this.saveController = new SaveController();
        mainStage = new Stage();
        loadMenu();
    }

    private void loadMenu() throws FileNotFoundException {
        mainPane = new AnchorPane();
        Scene mainScene = new Scene(mainPane, STAGE_WIDTH, STAGE_HEIGHT);
        mainStage.setScene(mainScene);
        mainStage.setTitle("The Ruins of Ustro 3");
        mainStage.setResizable(false);
        createButtons();
        createBackground();
        createLogo();
    }

    public Stage getMainStage() {
        return mainStage;
    }

    private void createButtons() throws FileNotFoundException {
        createNewGameButton();
        createLoadGameButton();
        createHelpButton();
        createQuitButton();
    }

    private void createNewGameButton() throws FileNotFoundException {
        newGame = new MenuButton("New Game", BUTTON_RELEASED_STYLE, BUTTON_PRESSED_STYLE, FONT_PATH2);
        newGame.setLayoutX(100);
        newGame.setLayoutY(170);
        mainPane.getChildren().add(newGame);

        newGame.setOnAction(newGame -> {
            loaded = false;
            loadedPlayer.setAttack(1);
            loadedPlayer.setMaxSpeed(8);
            loadedPlayer.setMaxHitPoints(10);
            loadedPlayer.setSkillPoints(0);

            try {
                setCharacterScreen();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private void createLoadGameButton() throws FileNotFoundException {
        loadGame = new MenuButton("Load Game", BUTTON_RELEASED_STYLE, BUTTON_PRESSED_STYLE, FONT_PATH2);
        loadGame.setLayoutX(100);
        loadGame.setLayoutY(newGame.getLayoutY() + 100);
        mainPane.getChildren().add(loadGame);

        loadGame.setOnAction(newGame -> {
            loaded = true;

            try {
                saveController.loadPlayer(loadedPlayer);
                setCharacterScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void createHelpButton() throws FileNotFoundException {
        helpButton = new MenuButton("Help", BUTTON_RELEASED_STYLE, BUTTON_PRESSED_STYLE, FONT_PATH2);
        helpButton.setLayoutX(100);
        helpButton.setLayoutY(loadGame.getLayoutY() + 100);
        mainPane.getChildren().add(helpButton);

        helpButton.setOnAction(actionEvent -> {
            try {
                setHelpScreen();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private void createQuitButton() throws FileNotFoundException {
        MenuButton quitButton = new MenuButton("Quit", BUTTON_RELEASED_STYLE, BUTTON_PRESSED_STYLE, FONT_PATH2);
        quitButton.setLayoutX(100);
        quitButton.setLayoutY(helpButton.getLayoutY() + 100);
        mainPane.getChildren().add(quitButton);

        quitButton.setOnAction(actionEvent -> mainStage.close());
    }

    private void createBackButton(AnchorPane pane) throws FileNotFoundException {
        MenuButton backButton = new MenuButton("Back", BUTTON_RELEASED_STYLE, BUTTON_PRESSED_STYLE, FONT_PATH2);
        backButton.setLayoutX(10);
        backButton.setLayoutY(540);
        pane.getChildren().add(backButton);

        backButton.setOnAction(actionEvent -> {
            try {
                loadMenu();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private MenuButton createPlayButton(AnchorPane pane, int X, int Y) throws FileNotFoundException {
        MenuButton playButton = new MenuButton("Play", BUTTON_RELEASED_STYLE, BUTTON_PRESSED_STYLE, FONT_PATH2);
        playButton.setLayoutX(X);
        playButton.setLayoutY(Y);
        pane.getChildren().add(playButton);

        return playButton;
    }

    private MenuButton createSaveButton(AnchorPane pane) throws FileNotFoundException {
        MenuButton saveButton = new MenuButton("Save", BUTTON_RELEASED_STYLE, BUTTON_PRESSED_STYLE, FONT_PATH2);
        saveButton.setLayoutX(210);
        saveButton.setLayoutY(540);
        pane.getChildren().add(saveButton);

        return saveButton;
    }

    private void setStatButtons(AnchorPane pane, ImageView imageView, int X, int Y) {
        imageView.setLayoutX(X);
        imageView.setLayoutY(Y);
        pane.getChildren().add(imageView);

        imageView.setOnMouseEntered(mouseEvent -> imageView.setEffect(new DropShadow()));
        imageView.setOnMouseExited(mouseEvent -> imageView.setEffect(null));
    }

    private void increaseStat(Creature loadedPlayer, ImageView addAttack, ImageView addSpeed, ImageView addHp) {
        addAttack.setOnMouseClicked(mouseEvent -> {
            if(loadedPlayer.getSkillPoints() > 0) {
                loadedPlayer.setAttack(loadedPlayer.getAttack() + 1);
                loadedPlayer.setSkillPoints(loadedPlayer.getSkillPoints() - 1);
                try {
                    setCharacterScreen();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        addSpeed.setOnMouseClicked(mouseEvent -> {
            if(loadedPlayer.getSkillPoints() > 0) {
                loadedPlayer.setMaxSpeed(loadedPlayer.getMaxSpeed() + 1);
                loadedPlayer.setSkillPoints(loadedPlayer.getSkillPoints() - 1);
                try {
                    setCharacterScreen();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        addHp.setOnMouseClicked(mouseEvent -> {
            if(loadedPlayer.getSkillPoints() > 0) {
                loadedPlayer.setMaxHitPoints(loadedPlayer.getMaxHitPoints() + 1);
                loadedPlayer.setHitPoints(loadedPlayer.getMaxHitPoints());
                loadedPlayer.setSkillPoints(loadedPlayer.getSkillPoints() - 1);
                try {
                    setCharacterScreen();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void setCharacterScreen() throws FileNotFoundException {
        mainPane = new AnchorPane();
        Text title1 = new Text();
        Text title2 = new Text();
        title1.setText("Your character");
        title2.setText("Choose level");
        title1.setFont(Font.loadFont(new FileInputStream(FONT_PATH1), 50));
        title2.setFont(Font.loadFont(new FileInputStream(FONT_PATH1), 40));

        title1.setLayoutX(210);
        title1.setLayoutY(70);
        title2.setLayoutX(460);
        title2.setLayoutY(150);

        mainPane.getChildren().add(title1);
        mainPane.getChildren().add(title2);

        createBackButton(mainPane);
        MenuButton level1 = createPlayButton(mainPane, 600, 200);
        MenuButton level2 = createPlayButton(mainPane, 600, 330);
        MenuButton level3 = createPlayButton(mainPane, 600, 460);

        MenuButton saveButton = createSaveButton(mainPane);

        Text L1 = new Text();
        Text L2 = new Text();
        Text L3 = new Text();
        L1.setText("Level 1");
        L2.setText("Level 2");
        L3.setText("Level 3");
        L1.setFont(Font.loadFont(new FileInputStream(FONT_PATH1), 30));
        L2.setFont(Font.loadFont(new FileInputStream(FONT_PATH1), 30));
        L3.setFont(Font.loadFont(new FileInputStream(FONT_PATH1), 30));

        L1.setLayoutX(470);
        L1.setLayoutY(235);
        L2.setLayoutX(470);
        L2.setLayoutY(365);
        L3.setLayoutX(470);
        L3.setLayoutY(495);

        mainPane.getChildren().add(L1);
        mainPane.getChildren().add(L2);
        mainPane.getChildren().add(L3);

        ImageView character = new ImageView("view/resources/player_03.png");
        character.setLayoutX(20);
        character.setLayoutY(250);

        mainPane.getChildren().add(character);

        double attack;
        double speed;
        double hp;

        attack = loadedPlayer.getAttack();
        speed = loadedPlayer.getMaxSpeed();
        hp = loadedPlayer.getMaxHitPoints();

        skillPoints = new Text();
        Text attackStat = new Text();
        Text speedStat = new Text();
        Text hitPoints = new Text();
        setSkillPoints();
        attackStat.setText("Attack:   " + attack);
        speedStat.setText("Speed:    " + speed);
        hitPoints.setText("HP:        " + hp);
        skillPoints.setFont(Font.loadFont(new FileInputStream(FONT_PATH1), 30));
        attackStat.setFont(Font.loadFont(new FileInputStream(FONT_PATH1), 25));
        speedStat.setFont(Font.loadFont(new FileInputStream(FONT_PATH1), 25));
        hitPoints.setFont(Font.loadFont(new FileInputStream(FONT_PATH1), 25));

        skillPoints.setLayoutX(50);
        skillPoints.setLayoutY(200);
        attackStat.setLayoutX(150);
        attackStat.setLayoutY(280);
        speedStat.setLayoutX(150);
        speedStat.setLayoutY(350);
        hitPoints.setLayoutX(150);
        hitPoints.setLayoutY(420);

        ImageView addAttack = new ImageView("view/resources/cursorSword_silver.png");
        setStatButtons(mainPane, addAttack, 320, 255);
        ImageView addSpeed = new ImageView("view/resources/cursorSword_silver.png");
        setStatButtons(mainPane, addSpeed, 320, 325);
        ImageView addHp = new ImageView("view/resources/cursorSword_silver.png");
        setStatButtons(mainPane, addHp, 320, 395);

        mainPane.getChildren().add(skillPoints);
        mainPane.getChildren().add(attackStat);
        mainPane.getChildren().add(speedStat);
        mainPane.getChildren().add(hitPoints);

        Scene scene = new Scene(mainPane, STAGE_WIDTH, STAGE_HEIGHT);
        mainStage.setScene(scene);

        createBackground();

        increaseStat(loadedPlayer, addAttack, addSpeed, addHp);

        saveButton.setOnAction(actionEvent -> {
            try {
                saveController.savePlayer(loadedPlayer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        level1.setOnAction(actionEvent -> {
            try{
                Creature clone = new Creature(loadedPlayer.getPositionX(), loadedPlayer.getPositionY(),
                        loadedPlayer.getHitPoints(), loadedPlayer.getMaxSpeed(), loadedPlayer.getAcceleration(),
                        loadedPlayer.getBulletCoolDown(), loadedPlayer.getSizeX(), loadedPlayer.getSizeY(),
                        loadedPlayer.getRadius(), loadedPlayer.getAttack());
                GameViewManager gameManager = new GameViewManager(clone, this, 1);

            } catch(Exception e) {
                e.printStackTrace();
            } });

        level2.setOnAction(ActionEvent -> {
            try{
                Creature clone = new Creature(loadedPlayer.getPositionX(), loadedPlayer.getPositionY(),
                        loadedPlayer.getHitPoints(), loadedPlayer.getMaxSpeed(), loadedPlayer.getAcceleration(),
                        loadedPlayer.getBulletCoolDown(), loadedPlayer.getSizeX(), loadedPlayer.getSizeY(),
                        loadedPlayer.getRadius(), loadedPlayer.getAttack());
                GameViewManager gameManager = new GameViewManager(clone, this, 2);

            } catch(Exception e) {
                e.printStackTrace();
            } });

        level3.setOnAction(ActionEvent -> {
            try{
                Creature clone = new Creature(loadedPlayer.getPositionX(), loadedPlayer.getPositionY(),
                        loadedPlayer.getHitPoints(), loadedPlayer.getMaxSpeed(), loadedPlayer.getAcceleration(),
                        loadedPlayer.getBulletCoolDown(), loadedPlayer.getSizeX(), loadedPlayer.getSizeY(),
                        loadedPlayer.getRadius(), loadedPlayer.getAttack());
                GameViewManager gameManager = new GameViewManager(clone, this, 3);

            } catch(Exception e) {
                e.printStackTrace();
        } });
    }

    public void setSkillPoints() {
        skillPoints.setText("Skill Points:   " + loadedPlayer.getSkillPoints());
    }

    private void setHelpScreen() throws FileNotFoundException {
        mainPane = new AnchorPane();
        Text title = new Text();
        Text movement = new Text();
        Text shooting = new Text();
        Text controls = new Text();
        title.setText("Controls");
        movement.setText("Movement");
        shooting.setText("Shooting");
        controls.setText("Arrow keys                                        A S D F");
        title.setFont(Font.loadFont(new FileInputStream(FONT_PATH1), 50));
        movement.setFont(Font.loadFont(new FileInputStream(FONT_PATH1), 40));
        shooting.setFont(Font.loadFont(new FileInputStream(FONT_PATH1), 40));
        controls.setFont(Font.loadFont(new FileInputStream(FONT_PATH2), 40));

        title.setLayoutX(280);
        title.setLayoutY(100);

        movement.setLayoutX(150);
        movement.setLayoutY(250);

        shooting.setLayoutX(450);
        shooting.setLayoutY(250);

        controls.setLayoutX(170);
        controls.setLayoutY(350);

        mainPane.getChildren().add(title);
        mainPane.getChildren().add(movement);
        mainPane.getChildren().add(shooting);
        mainPane.getChildren().add(controls);

        createBackButton(mainPane);

        Scene scene = new Scene(mainPane, STAGE_WIDTH, STAGE_HEIGHT);
        mainStage.setScene(scene);

        createBackground();
    }

    private void createBackground() {
        Image backgroundImage = new Image("view/resources/ground_06.png");
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        mainPane.setBackground(new Background(background));
    }

    private void createLogo() throws FileNotFoundException {
        Text logo = new Text();
        logo.setText("The Ruins of Ustro 3");
        logo.setFont(Font.loadFont(new FileInputStream(FONT_PATH1), 50));
        logo.setEffect(new DropShadow());
        logo.setLayoutX(140);
        logo.setLayoutY(100);
        mainPane.getChildren().add(logo);
    }

    public Creature getLoadedPlayer() {
        return loadedPlayer;
    }
}
