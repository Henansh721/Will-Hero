package com.example.project_will_hero;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Menu implements Initializable {
    @FXML
    private AnchorPane About_page;

    @FXML
    private Group Buttons_group;

    @FXML
    private AnchorPane Exit_page;

    @FXML
    private ImageView Hero;

    @FXML
    private AnchorPane Load_page;

    @FXML
    private AnchorPane Login_page;

    @FXML
    private ImageView Orc01;

    @FXML
    private AnchorPane Stats_page;

    @FXML
    private AnchorPane Black_pane;

    @FXML
    private TextField email_field;

    @FXML
    private TextField name_field;

    private Game game;
    private Boolean is_keypressed = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        jump_animation(Hero,600,100,15).play();
        jump_animation(Orc01,450,50,-20).play();
    }

    public SequentialTransition jump_animation(Node obj, int time, int y, int x) {

        SequentialTransition seq_animation = new SequentialTransition(translate_animation(obj,time,y,x), translate_animation(obj,time,-1*y,x));
        seq_animation.setAutoReverse(true);
        seq_animation.setCycleCount(SequentialTransition.INDEFINITE);

        return seq_animation;
    }

    public TranslateTransition translate_animation(Node obj, int time, int y, int x) {

        TranslateTransition animation = new TranslateTransition();
        animation.setNode(obj);
        animation.setDuration(Duration.millis(time));
        animation.setByY(-1*y);
        animation.setByX(x);
        return animation;
    }

    @FXML
    void about_game(MouseEvent event) throws IOException {
        //Fade other components
        Black_pane.setVisible(true);
        Buttons_group.setDisable(true);
        Buttons_group.setVisible(false);
        translate_animation(About_page,500,550,0).play();
    }

    @FXML
    void exit(MouseEvent event) {

        //Fade other components
        Black_pane.setVisible(true);
        Buttons_group.setDisable(true);
        Buttons_group.setVisible(false);
        translate_animation(Exit_page,500,420,0).play();

    }

    @FXML
    void exit_yes_pressed(MouseEvent event)
    {
        System.exit(0);
    }

    @FXML
    void exit_no_pressed(MouseEvent event) {
        //change animation to fade animation
        Black_pane.setVisible(false);
        Buttons_group.setDisable(false);
        Buttons_group.setVisible(true);
        translate_animation(Exit_page,500,-420,0).play();

        //Un_Fade other components
    }


    @FXML
    void load_games(MouseEvent event) throws IOException {

        //Fade other components
        Black_pane.setVisible(true);
        Buttons_group.setDisable(true);
        Buttons_group.setVisible(false);
        translate_animation(Load_page,500,550,0).play();
    }

    public FadeTransition fade_animation(Node obj, int time,int fade_prop)
    {
        FadeTransition animation = new FadeTransition();
        animation.setNode(obj);
        animation.setDuration(Duration.millis(time));
        animation.setInterpolator(Interpolator.LINEAR);

        if(fade_prop > 0)
        {
            animation.setFromValue(0);
            animation.setToValue(1);
        }
        else
        {
            animation.setFromValue(1);
            animation.setToValue(0);
        }

        animation.setFromValue(0);
        animation.setToValue(1);
        return animation;

    }

    @FXML
    void start_game(MouseEvent event) throws IOException{
        Buttons_group.setDisable(true);
        Buttons_group.setVisible(false);
        Login_page.setDisable(false);
        Login_page.setVisible(true);
        System.out.println("in start game");
        fade_animation(Login_page,500,1).play();
    }


    @FXML
    void play_game(MouseEvent event) throws IOException {

        System.out.println("in play game");

        String name = name_field.getText();

        if(name.equals("Enter Username"))
            name = "Unknown";

        String email = email_field.getText();

        if(email.equals("Enter Email address"))
            email = "Unknown";

        Player player = new Player(name, email);

        int helmet_index = 0;
        this.game = new Game(player, helmet_index);

        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("Game_Screen.fxml")));
        Game_Controller game_controller = new Game_Controller(game);
        loader.setControllerFactory(controllerClass -> game_controller);
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene game_scene = new Scene(root);

        game_scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if( keyEvent.getCode() == KeyCode.W && !is_keypressed && !game_controller.getKey_listener())
                {
                    game_controller.move_hero();
                    is_keypressed = true;
                }
            }
        });

        game_scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode() == KeyCode.W)
                {
                    is_keypressed = false;
                }
            }
        });

        stage.setScene(game_scene);
        stage.show();

    }

    @FXML
    void stats(MouseEvent event) throws IOException {
        //Fade other components
        Black_pane.setVisible(true);
        Buttons_group.setDisable(true);
        Buttons_group.setVisible(false);
        //System.out.println(Stats_page.getLayoutY() + " " + Stats_page.getHeight() + " " +Stats_page.getTranslateY());
//        TranslateTransition animation = translate_animation(Stats_page,500,550,0);
//        animation.play();
        translate_animation(Stats_page,500,550,0).play();
    }


    @FXML
    void back_button(MouseEvent event) {

        ImageView button = (ImageView) (event.getSource());
        AnchorPane pane = (AnchorPane) button.getParent();
        translate_animation(pane,500,-550,0).play();
        Black_pane.setVisible(false);
        Buttons_group.setVisible(true);
        Buttons_group.setDisable(false);

    }

    @FXML
    void exit_button(MouseEvent event) {
        Buttons_group.setDisable(false);
        Buttons_group.setVisible(true);
        fade_animation(Login_page,500,-1).play();
        Login_page.setDisable(true);
        Login_page.setVisible(false);


    }



    public Game getGame() {
        return game;
    }

    public void show_menu(){}
    public void save_game(){}
    public void pause_menu(){}
    public void resume(){}
    public void restart(){}
    public void gameover_screen(){}
    public void settings(){}

}
