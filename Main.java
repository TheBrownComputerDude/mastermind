import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.LinkedList;

public class Main extends Application {
    private Mastermind game;
    private LinkedList<Circle> holes = new LinkedList<>();
    private Circle peg = new Circle(14.8);
    private final int pegCX = 300;
    private final int pegCY = 380;
    private final double radius = 14.8;
    private Circle[] guess = new Circle[4];
    private int numGuesses = 0;
    private Label win = new Label("You Won!");
    private Label lost = new Label("You lost.");
    private LinkedList<Label> stamps = new LinkedList<>();
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Game");
        Pane layout = new Pane();
        Scene scene = new Scene(layout,400,600);
        stage.setScene(scene);
        drawBoard(layout);
        layout.setBackground(new Background(new BackgroundFill(Color.GREY,CornerRadii.EMPTY, Insets.EMPTY)));
        setUpPeg(layout);
        startGame();
        stage.setResizable(false);
        //game = new Mastermind();

        stage.show();
    }
    public static void main(String[] args){
        launch(args);
    }
    private void setUpPeg(Pane layout){
        layout.getChildren().add(peg);
        peg.setFill(Color.BLUE);
        peg.setTranslateX(pegCX);
        peg.setTranslateY(pegCY);
        peg.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                peg.setTranslateX(event.getSceneX());
                peg.setTranslateY(event.getSceneY());
            }
        });
        peg.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                check(layout);
                peg.setTranslateX(pegCX);
                peg.setTranslateY(pegCY);

            }
        });
    }
    private void startGame(){
        game = new Mastermind();
    }
    private void check(Pane layout){
        boolean done = true;

        for(Circle c: holes){
            if(c.getTranslateY() == (numGuesses * ((radius * 2) + 2) + radius + 10) && Math.abs(c.getTranslateX() - peg.getTranslateX()) < radius && Math.abs(c.getTranslateY() - peg.getTranslateY()) < radius ){
                c.setFill(peg.getFill());
                if((int)c.getTranslateX() == 29){
                    guess[0] = c;
                }else if((int)c.getTranslateX() == 61){
                    guess[1] = c;
                }else if((int)c.getTranslateX() == 93){
                    guess[2] = c;
                }else if((int)c.getTranslateX() == 124){
                    guess[3] = c;
                }

            }
        }
        for(Circle x : guess){
            if(x == null){
                done = false;
            }
        }
        if(done) sendGuess(layout);
    }
    private void resetGuess(){
        for(int i = 0; i < guess.length; i++){
            guess[i] = null;
        }
    }
    private void sendGuess(Pane layout){
        String toSend = "";
        for(Circle c: guess) {
            if (c.getFill().equals(Color.BLUE)) {
                toSend += "a";
            } else if (c.getFill().equals(Color.RED)) {
                toSend += "b";
            } else if (c.getFill().equals(Color.GREEN)) {
                toSend += "c";
            } else if (c.getFill().equals(Color.PURPLE)) {
                toSend += "d";
            } else if (c.getFill().equals(Color.ORANGE)) {
                toSend += "e";
            } else if (c.getFill().equals(Color.YELLOW)) {
                toSend += "f";
            }
        }
        resetGuess();
        int[] result = game.guess(toSend);
        Label l = new Label("Black: " + result[0] + " White: " + result[1]);
        l.setTranslateX(150);
        l.setTranslateY((numGuesses * ((radius * 2) + 2) + radius + 4));
        stamps.push(l);
        layout.getChildren().add(l);
        if(result[0] == 4) {
            updateWin();
            numGuesses++;
            return;
        }
        numGuesses++;
        if(numGuesses == 12) updateLoss();
    }
    private void updateLoss(){
        lost.toFront();
        lost.setVisible(true);
    }
    private void updateWin(){
        win.toFront();
        win.setVisible(true);

    }
    private void reset(Pane layout){
        numGuesses = 0;
        for(Circle c:holes){
            c.setFill(Color.BLACK);
        }
        int s = stamps.size();
        for(int i = 0; i < s; i++){
            layout.getChildren().remove(stamps.pop());
        }
        resetGuess();
        win.setVisible(false);
        lost.setVisible(false);
        startGame();
    }
    private void drawBoard(Pane layout){
        lost.setFont(new Font(40));
        lost.setTranslateX(200);
        lost.setTranslateY(200);
        win.setFont(new Font(40));
        win.setTranslateX(200);
        win.setTranslateY(300);
        win.setVisible(false);
        lost.setVisible(false);
        layout.getChildren().addAll(win,lost);
        Line l = new Line(10,10,10,400);
        Button blue = new Button("Blue");
        blue.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                peg.setFill(Color.BLUE);
            }
        });
        blue.setPrefWidth(133);
        blue.setPrefHeight(100);
        blue.setTranslateX(0);
        blue.setTranslateY(400);
        Button red = new Button("Red");
        red.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                peg.setFill(Color.RED);
            }
        });
        red.setPrefWidth(133);
        red.setPrefHeight(100);
        red.setTranslateX(134);
        red.setTranslateY(400);
        Button green = new Button("Green");
        green.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                peg.setFill(Color.GREEN);
            }
        });
        green.setPrefWidth(133);
        green.setPrefHeight(100);
        green.setTranslateX(268);
        green.setTranslateY(400);
        Button purple = new Button("Purple");
        purple.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                peg.setFill(Color.PURPLE);
            }
        });
        purple.setPrefWidth(133);
        purple.setPrefHeight(100);
        purple.setTranslateX(0);
        purple.setTranslateY(500);
        Button orange = new Button("Orange");
        orange.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                peg.setFill(Color.ORANGE);
            }
        });
        orange.setPrefWidth(133);
        orange.setPrefHeight(100);
        orange.setTranslateX(134);
        orange.setTranslateY(500);
        Button yellow = new Button("Yellow");
        yellow.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                peg.setFill(Color.YELLOW);
            }
        });
        yellow.setPrefWidth(133);
        yellow.setPrefHeight(100);
        yellow.setTranslateX(268);
        yellow.setTranslateY(500);
        int left = 10;
        int right = 145;
        int top = 10;
        int bottom = 390;
        Line l1 = new Line(left,top,right,top);
        Line l2 = new Line(left,top,left,bottom);
        Line l3 = new Line(left,bottom,right,bottom);
        Line l4 = new Line(right,bottom,right,top);

        for(int i = 0; i < 12; i++){
            for(int j = 0; j < 4; j++) {
                Circle c = new Circle(radius);
                c.setTranslateX(j * ((radius * 2) + 2) + radius + 15);
                c.setTranslateY(i * ((radius * 2) + 2) + radius + 10);
                holes.push(c);
                layout.getChildren().add(c);
            }
        }
        Button reset = new Button("Reset");
        reset.setTranslateX(350);
        reset.setTranslateY(10);
        reset.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                reset(layout);
            }
        });
        Button c = new Button("Computer");
        c.setTranslateX(325);
        c.setTranslateY(40);
        c.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                reset(layout);
                boolean won = false;
                Computer cp = new Computer(game);
                while(numGuesses < 12) {
                    String x = cp.doTurn();
                    for(int i = 0; i<x.length(); i++){
                        if(x.charAt(i) == 'a'){
                            holes.get(holes.size() - (numGuesses*4 + i)-1).setFill(Color.BLUE);
                        }else if(x.charAt(i) == 'b'){
                            holes.get(holes.size() - (numGuesses*4 + i)-1).setFill(Color.RED);
                        }else if(x.charAt(i) == 'c'){
                            holes.get(holes.size() - (numGuesses*4 + i)-1).setFill(Color.GREEN);
                        }else if(x.charAt(i) == 'd'){
                            holes.get(holes.size() - (numGuesses*4 + i)-1).setFill(Color.PURPLE);
                        }else if(x.charAt(i) == 'e'){
                            holes.get(holes.size() - (numGuesses*4 + i)-1).setFill(Color.ORANGE);
                        }else if(x.charAt(i) == 'f'){
                            holes.get(holes.size() - (numGuesses*4 + i)-1).setFill(Color.YELLOW);
                        }

                    }
                    Label l = new Label("Black: " + cp.getResult()[0] + " White: " + cp.getResult()[1]);
                    l.setTranslateX(150);
                    l.setTranslateY((numGuesses * ((radius * 2) + 2) + radius + 4));
                    stamps.push(l);
                    layout.getChildren().add(l);
                    if(cp.CpWin()){
                        won = true;
                        updateWin();
                        break;
                    }
                    numGuesses++;
                }
                if(!won){
                    updateLoss();
                }
            }
        });
        layout.getChildren().addAll(blue,red,green,purple,orange,yellow,l1,l2,l3,l4,reset,c);
    }
}
