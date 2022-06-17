package com.example.project_will_hero;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

public class Game_Controller implements Initializable {

    private final Game game;
    private final Group grp = new Group();

    @FXML
    private AnchorPane anchor_pane;
    @FXML
    private Label coins;
    @FXML
    private Text lose_coin;
    @FXML
    private AnchorPane lose_page;
    @FXML
    private Text lose_progress;
    @FXML
    private Text lose_score;
    @FXML
    private ProgressBar progress_bar;
    @FXML
    private Group rev_group;
    @FXML
    private Label score;
    @FXML
    private Text win_coin;
    @FXML
    private AnchorPane win_page;
    @FXML
    private AnchorPane pause_page;
    @FXML
    private Text win_progress;
    @FXML
    private Text win_score;
    @FXML
    private ImageView pause_button;

    private ImageView Heroimg;

    private Timeline gravity_timeline;
    private TranslateTransition island_animation;
    private TranslateTransition enemy_animation;
    private TranslateTransition tnt_animation;
    private TranslateTransition chest_animation;
    private TranslateTransition tree_animation;

    private Boolean revive = false;
    private final ArrayList<ImageView> trees = new ArrayList<>();
    private final ArrayList<ImageView> island_list = new ArrayList<>();
    private final ArrayList<ImageView> enemy_list = new ArrayList<>();
    private final ArrayList<ImageView> tnt_list = new ArrayList<>();
    private final ArrayList<ImageView> chest_list = new ArrayList<>();
    private final ArrayList<ImageView> weapon_list = new ArrayList<>();

    private int fall_value = 1;
    private int heigh_count = 0;
    private Boolean upward_bool = false;
    private int inside_node = 0;
    private int count_counter = 0;
    private Boolean key_listener = false;
    private int coins_val = 0;
    private Boolean init = false;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if(this.game == null) {
            System.out.println("game is null");
            return;
        }

        Heroimg = game.getHero().getSkin();
        System.out.println(" Hero set");
        Hero.jump(this,Heroimg);
        set_islands();
        System.out.println(" Island set");
        set_enemy();
        System.out.println(" Enemies set");
        set_tnt();
        System.out.println(" TNT set");
        set_trees();
        System.out.println(" Trees set");
        set_chest();

        check_enemy();
        check_tnt();

        for(int i=0;i<game.getPlayer().getScore();i++)
            set_layout();

        for(ImageView temp: island_list )
            grp.getChildren().add(temp);

        for(ImageView temp: trees )
            grp.getChildren().add(temp);

        for(ImageView temp: tnt_list )
            grp.getChildren().add(temp);

        for(ImageView temp: chest_list )
            grp.getChildren().add(temp);

        for(ImageView temp: enemy_list ) {
            grp.getChildren().add(temp);
            Enemy.jump(this,temp);
        }

        anchor_pane.getChildren().add(grp);
        anchor_pane.getChildren().add(Heroimg);

    }

    public void move_hero() {

        if (progress_bar.getProgress() == 1 ) {
            win_screen();
            key_listener = true;
            gravity_timeline.stop();
            return;
        }

        if (!game.getHero().Is_alive() || Heroimg.getLayoutY() >= 600) {
            key_listener = true;
            System.out.println("DED IN MOVE HERO");
            game.getHero().setIs_alive(false);

            lost_page();
            return;
        }

        count_counter++;
        inc_value(score, count_counter);
        progress_bar.setProgress((double) (Double.parseDouble(score.getText()) / 117));


        set_layout();

        if(!weapon_list.isEmpty() && !init)
        {
            init = true;
            ImageView weapon_temp = new ImageView();
            weapon_temp.setImage(weapon_list.get(0).getImage());
            weapon_temp.setLayoutY(Heroimg.getLayoutY());
            weapon_temp.setLayoutX(Heroimg.getLayoutX());
            weapon_temp.setFitHeight(70);
            weapon_temp.setFitWidth(50);
            anchor_pane.getChildren().add(weapon_temp);
            Timeline weapon_timeline = new Timeline(new KeyFrame(Duration.millis(0.8),event -> weapon_move(weapon_temp)));
            weapon_timeline.setCycleCount(300);
            weapon_timeline.setOnFinished(event -> {
                weapon_temp.setDisable(true);
                weapon_temp.setVisible(false);
                anchor_pane.getChildren().remove(weapon_temp);
                weapon_list.remove(0);
                init = false;
            });
            weapon_timeline.play();

        }
    }

    public void weapon_move(ImageView temp)
    {
        Iterator<ImageView> iter = enemy_list.iterator();
        while(iter.hasNext()) {
            ImageView enemy = iter.next();
            if (temp.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                game.getHero().getWeapon().collision(this, enemy, enemy);
                iter.remove();
            }
        }
        temp.setLayoutX(temp.getLayoutX()+1);
    }

    public void set_layout() {
        for (ImageView temp : island_list) {
            island_animation = translate_animation(temp, 300, (int) temp.getY(), (int) temp.getX() - 250);
            island_animation.play();
        }

        for (ImageView temp : enemy_list) {
            enemy_animation = translate_animation(temp, 300, (int) temp.getY(), (int) temp.getX() - 250);
            enemy_animation.play();
        }

        for (ImageView temp : tnt_list) {
            tnt_animation = translate_animation(temp, 300, (int) temp.getY(), (int) temp.getX() - 250);
            tnt_animation.play();
        }

        for (ImageView temp : trees) {
            tree_animation = translate_animation(temp, 300, (int) temp.getY(), (int) temp.getX() - 250);
            tree_animation.play();
        }

        for (ImageView temp : chest_list) {
            chest_animation = translate_animation(temp, 300, (int) temp.getY(), (int) temp.getX() - 250);
            chest_animation.play();
        }
    }


    private void win_screen() {

        System.out.println(coins.getText());
        System.out.println(score.getText());
        System.out.println(progress_bar.getProgress());

        win_coin.setText("Coins : " + coins.getText());
        win_score.setText("Score : " + score.getText());
        win_progress.setText("Progress : " + Math.round(progress_bar.getProgress()*100.0) + "%");

        anchor_pane.getChildren().remove(win_page);
        anchor_pane.getChildren().add(win_page);

        game.getPlayer().setScore(Integer.parseInt(score.getText()));
        game.getPlayer().setCoin(Integer.parseInt(coins.getText()));
        game.getPlayer().setProgress(Math.round(progress_bar.getProgress()*100.0));

        translate_animation(win_page,800,550,0).play();
    }


    public void lost_page() {

        inc_value(coins,coins_val);
        inc_value(score,count_counter);

        String coins_val = coins.getText();
        String score_val = score.getText();
        String progress_val = String.valueOf(Math.round(progress_bar.getProgress() * 100.0));

        pause_button.setDisable(true);
        pause_button.setVisible(false);

        System.out.println("coins_val"+coins_val);
        System.out.println("score_val"+score_val);
        System.out.println("progress_val"+progress_val);

        lose_coin.setText("Coins : " + coins_val);
        lose_score.setText("Score : " + score_val);
        lose_progress.setText("Progress : " + progress_val + "%");

        anchor_pane.getChildren().remove(lose_page);
        anchor_pane.getChildren().add(lose_page);

        game.getPlayer().setScore(Integer.parseInt(score.getText()));
        game.getPlayer().setCoin(Integer.parseInt(coins.getText()));
        game.getPlayer().setProgress(Math.round(progress_bar.getProgress() * 100.0));

        translate_animation(lose_page, 800, 550, 0).play();

        if(!revive && Integer.parseInt(coins.getText()) >= 50) {
            rev_group.setDisable(false);
            rev_group.setVisible(true);
        }
        else{
            rev_group.setDisable(true);
            rev_group.setVisible(false);
        }

    }

    @FXML
    void exit_page(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("Main_menu.fxml")));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene game_scene = new Scene(root);
        stage.setScene(game_scene);
        stage.show();
    }

    @FXML
    void resume_menu(MouseEvent event) {
        pause_button.setDisable(true);
        pause_button.setVisible(false);
        pause_page.setVisible(true);
        pause_page.setDisable(false);
        anchor_pane.getChildren().remove(pause_page);
        anchor_pane.getChildren().add(pause_page);

        fade_animation(pause_page,500,1).play();
    }

    @FXML
    void resume_menu_back(MouseEvent event) {
        pause_button.setDisable(false);
        pause_button.setVisible(true);
        pause_page.setVisible(false);
        pause_page.setDisable(true);
        fade_animation(pause_page,500,0).play();
    }

    @FXML
    void revive(MouseEvent event) {

        translate_animation(lose_page, 800, -550, 0).play();
        System.out.println("IN REVIVE");

        game.setHero(new Hero(game.getHelmet().get(0)));
        Heroimg = new ImageView();
        Heroimg = game.getHero().getSkin();
        Heroimg.setLayoutY(200);
        Heroimg.setLayoutX(500);
        anchor_pane.getChildren().add(Heroimg);
        game.getHero().setIs_alive(true);
        Hero.jump(this,Heroimg);

        key_listener = false;

        pause_button.setDisable(false);
        pause_button.setVisible(true);

        setCoins_val(getCoins_val() - 50);
        inc_value(getCoins(), getCoins_val());
        revive = true;

    }

    public void inc_value(Label obj,int val){
        KeyFrame inc_keyframe = new KeyFrame(Duration.millis(1), e -> set_label(obj,val));
        Timeline inc_timeline = new Timeline(inc_keyframe);
        inc_timeline.setCycleCount(Timeline.INDEFINITE);
        inc_timeline.play();
    }

    public void obj_die(Node obj,int time,int y,int x) {
        TranslateTransition animation = translate_animation(obj, time, y, x);
        animation.setOnFinished(event -> {
            obj.setVisible(false);
            obj.setDisable(true);
            anchor_pane.getChildren().remove(obj);
        });
        animation.play();
    }

    public void free_fall(Node obj) {

        if (progress_bar.getProgress() == 1 ) {
            win_screen();
            key_listener = true;
            gravity_timeline.stop();
            return;
        }


        if(!game.getHero().Is_alive() || obj.getLayoutY() >= 600 )
        {
            System.out.println("HERO DEAD in freefall");
            key_listener = true;
            game.getHero().setIs_alive(false);
            gravity_timeline.stop();
            lost_page();
            return;
        }

        Iterator<ImageView> iter;

        obj.setLayoutY(obj.getLayoutY() + fall_value);

        if(upward_bool)
            heigh_count ++;

        iter = chest_list.iterator();
        while(iter.hasNext()) {
            ImageView temp = iter.next();
            if (obj.getBoundsInParent().intersects(temp.getBoundsInParent())) {

                for (Chest c_temp : game.getChest_list()) {
                    if (c_temp.getSkin() == temp) {
                        c_temp.collision(this, temp, (ImageView) obj);
                        break;
                    }
                }
            }
        }

        iter = tnt_list.iterator();
        while(iter.hasNext()) {
            ImageView temp = iter.next();
            if (obj.getBoundsInParent().intersects(temp.getBoundsInParent())) {

                Iterator<TNT> t_iter = game.getTnt_list().iterator();
                while(t_iter.hasNext()){
                    TNT t_temp = t_iter.next();
                    if (t_temp.getSkin() == temp) {
                        t_temp.collision(this, temp, (ImageView) obj);
                        iter.remove();
                        t_iter.remove();
                        break;
                    }
                }

            }
        }

        iter = enemy_list.iterator();
        while(iter.hasNext()) {
            ImageView temp = iter.next();
            if (obj.getBoundsInParent().intersects(temp.getBoundsInParent())) {
                Iterator<Enemy> e_iter = game.getEnemy_list().iterator();
                while(e_iter.hasNext()){
                    Enemy e_temp = e_iter.next();
                    if (e_temp.getSkin() == temp) {
                        e_temp.collision(this, temp, (ImageView) obj);
                        iter.remove();
                        e_iter.remove();
                        break;
                    }
                }
            }
        }

        iter = island_list.iterator();
        while(iter.hasNext()) {
            ImageView temp = iter.next();
            if (obj.getBoundsInParent().intersects(temp.getBoundsInParent()) || heigh_count >= 150) {
                for (Island i_temp : game.getIsland_list()) {
                    if (i_temp.getSkin() == temp) {
                        i_temp.collision(this, temp, (ImageView) obj);
                        break;
                    }
                }

            } else {
                inside_node = 0;
            }
        }


    }

    public Boolean getKey_listener() {
        return key_listener;
    }

    public void setKey_listener(Boolean key_listener,int time) {

        this.key_listener = key_listener;
        KeyFrame inc_keyframe = new KeyFrame(Duration.millis(time));
        Timeline inc_timeline = new Timeline(inc_keyframe);
        inc_timeline.setCycleCount(1);
        inc_timeline.setOnFinished(event -> {
            this.key_listener = !key_listener;
        });
        inc_timeline.play();
    }

    public int getCoins_val() {
        return coins_val;
    }

    public void setCoins_val(int coins_val) {
        this.coins_val = coins_val;
    }

    public Label getCoins() {
        return coins;
    }

    public void setCoins(Label coins) {
        this.coins = coins;
    }

    public void set_weapon() {
        for(int i=0;i<game.getHero().getWeapon().getAmmo();i++)
            weapon_list.add(game.getHero().getWeapon().getSkin());

    }

//    public void serialize() throws IOException {
//        String filename = game.getPlayer().getName();
//        System.out.println(filename);
//        String filepath = "src/main/resources/Saved_games/" + filename;
//
//        new File("src/main/resources/Saved_games/").mkdirs();
//
//        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(Paths.get(filepath)))) {
//            nullify_imageview();
//            out.writeObject(game);
//        }
//    }
//
//    private void nullify_imageview() {
//        for(Island temp : game.getIsland_list())
//        {
//            temp.setSkin();
//        }
//        for(Chest temp : game.getChest_list())
//        {
//            temp.setSkin();
//        }
//        for(TNT temp : game.getTnt_list())
//        {
//            temp.setSkin();
//        }
//        for(Enemy temp : game.getEnemy_list())
//        {
//            temp.setSkin();
//        }
//
//        game.getHero().set_null_Skin();
//        game.getHero().getWeapon().set_null_Skin();
//    }

    private void check_enemy() {
        for(ImageView obj : enemy_list) {
            chest_list.removeIf(temp -> obj.getBoundsInParent().intersects(temp.getBoundsInParent()));
            tnt_list.removeIf(temp -> obj.getBoundsInParent().intersects(temp.getBoundsInParent()));
        }
    }

    private void check_tnt() {
        for(ImageView obj : tnt_list)
            chest_list.removeIf(temp -> obj.getBoundsInParent().intersects(temp.getBoundsInParent()));
    }

    private void set_chest() {
        for(Chest temp : game.getChest_list()) {
            chest_list.add(temp.getSkin());
        }
    }

    private void set_trees() {
        for( ImageView island : island_list) {
            ImageView tree = new ImageView();
            Coordinate island_cor = new Coordinate(island.getLayoutX(),island.getLayoutY());
            String path = "src/main/resources/Sprites/Tree1.png";
            File file = new File(path);
            tree.setImage(new Image(file.toURI().toString()));
            tree.setFitWidth(70);
            tree.setFitHeight(150);
            tree.setLayoutX(island.getLayoutX() + ThreadLocalRandom.current().nextDouble(0,(island.getFitWidth() - 2*tree.getFitWidth())));
            tree.setLayoutY(island_cor.getY_cor() - 150);
            trees.add(tree);
        }
    }

    private void set_tnt() {
        for(TNT temp : game.getTnt_list()) {
            tnt_list.add(temp.getSkin());
        }
    }

    private void set_enemy() {
        for(Enemy temp : game.getEnemy_list()) {
            enemy_list.add(temp.getSkin());
        }
    }

    private void set_islands() {
        for( Island temp : game.getIsland_list())
            island_list.add(temp.getSkin());
    }

    public FadeTransition fade_animation(Node obj, int time,int fade_prop) {
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

    public ScaleTransition scale_transition(Node obj, int time, int y, int x) {

        ScaleTransition animation = new ScaleTransition();
        animation.setNode(obj);
        animation.setDuration(Duration.millis(time));
        animation.setInterpolator(Interpolator.LINEAR);
        animation.setCycleCount(1);
        animation.setByX(x);
        animation.setByY(y);
        return animation;
    }

    public TranslateTransition get_island_animation() {
        return island_animation;
    }

    public TranslateTransition get_tree_animation() {
        return tree_animation;
    }

    public TranslateTransition get_enemy_animation() {
        return enemy_animation;
    }

    public TranslateTransition get_tnt_animation() {
        return tnt_animation;
    }

    public TranslateTransition get_chest_animation() {
        return chest_animation;
    }

    public Timeline get_grativy_timeline() {
        return gravity_timeline;
    }

    public void set_grativy_timeline(Timeline gravity) {
        gravity_timeline = gravity;
    }

    public int getHeigh_count() {
        return heigh_count;
    }

    public int getCount_counter() {
        return count_counter;
    }

    public int getFall_value() {
        return fall_value;
    }

    public Boolean getUpward_bool() {
        return upward_bool;
    }

    public void setFall_value(int fall_value) {
        this.fall_value = fall_value;
    }

    public void setHeigh_count(int heigh_count) {
        this.heigh_count = heigh_count;
    }

    public void setUpward_bool(Boolean upward_bool) {
        this.upward_bool = upward_bool;
    }

    public int getInside_node() {
        return inside_node;
    }

    public AnchorPane getAnchor_pane() {
        return anchor_pane;
    }

    public void setInside_node(int inside_node) {
        this.inside_node = inside_node;
    }

    public ArrayList<ImageView> getTnt_list() {
        return tnt_list;
    }

    public ArrayList<ImageView> getChest_list() {
        return chest_list;
    }

    public ArrayList<ImageView> getIsland_list() {
        return island_list;
    }

    public ArrayList<ImageView> getEnemy_list() {
        return enemy_list;
    }

    public Game getGame() {
        return game;
    }

    public Game_Controller(Game game) {
        this.game = game;
    }

    public void set_label(Label obj, int val) {
        obj.setText(String.valueOf(val));
    }

    public Group getGrp() {
        return grp;
    }

    public Label get_Score()
    {
        return score;
    }



}
