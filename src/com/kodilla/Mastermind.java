package com.kodilla;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.HashMap;

public class Mastermind extends Application {

    private Image imageback = new Image("file:resources/mboard.jpg");
    private Image wall = new Image("file:resources/pexels-pixabay-326311.jpg");

    public static void main(String[] args) {
        launch(args);
    }

    StackPane stackPane = new StackPane();
    RoundProcessor roundProcessor = new RoundProcessor();
    HashMap<gridCell,Pane> mainGridPaneMap = new HashMap<>();

    public ImageView pegsViewer(int index){
        String pegs[] = new String[6];
        pegs[0] = "ball-white.png";
        pegs[1] = "ball-purple.png";
        pegs[2] = "ball-yellow.png";
        pegs[3] = "ball-green.png";
        pegs[4] = "ball-red.png";
        pegs[5] = "ball-blue.png";

        ImageView result = new ImageView(new Image("file:resources/" + pegs[index]));
        result.setFitHeight(30);
        result.setFitWidth(30);
        result.addEventFilter(MouseEvent.MOUSE_CLICKED, gridPegsHandler);
        return result;
    }

    EventHandler<MouseEvent> gridPegsHandler = e -> {
        Node source = (Node) e.getSource();
        Integer colIndex = GridPane.getColumnIndex(source.getParent());
        Integer rowIndex = GridPane.getRowIndex(source.getParent());
        if((7-rowIndex)==roundProcessor.getCurrentRound()&&roundProcessor.isBallPicked()){
            mainGridPaneMap.get(new gridCell(7-roundProcessor.getCurrentRound(),colIndex)).getChildren().clear();
            mainGridPaneMap.get(new gridCell(7-roundProcessor.getCurrentRound(),colIndex)).getChildren().add(pegsViewer(roundProcessor.getCurrentBall()));
            roundProcessor.setBallPicked(false);
            roundProcessor.setCurrentGuess(colIndex,roundProcessor.getCurrentBall());
            stackPane.getScene().setCursor(Cursor.DEFAULT);
        }
    };

    EventHandler<MouseEvent> gridPegSelectionHandler = e -> {
        Node source = (Node)e.getSource();
        Integer colIndex3 = GridPane.getColumnIndex((Node)e.getTarget());
        Integer rowIndex3 = GridPane.getRowIndex((Node)e.getTarget());
        String balls[] = new String[6];
        balls[0] = "ball-white.png";
        balls[1] = "ball-purple.png";
        balls[2] = "ball-yellow.png";
        balls[3] = "ball-green.png";
        balls[4] = "ball-red.png";
        balls[5] = "ball-blue.png";

        Image image = new Image("file:resources/"+balls[colIndex3]);
        stackPane.getScene().setCursor(new ImageCursor(image));
        roundProcessor.setBallPicked(true);
        roundProcessor.setCurrentBall(colIndex3);
    };

    EventHandler<MouseEvent> mainGridHandler = e -> {
        Integer colIndex2 = GridPane.getColumnIndex((Node)e.getTarget());
        Integer rowIndex2 = GridPane.getRowIndex((Node)e.getTarget());
        if(colIndex2!=null&&colIndex2<4){
          if((7-rowIndex2)==roundProcessor.getCurrentRound()&&roundProcessor.isBallPicked()){
                mainGridPaneMap.get(new gridCell(7-roundProcessor.getCurrentRound(),colIndex2)).getChildren().add(pegsViewer(roundProcessor.getCurrentBall()));
                roundProcessor.setBallPicked(false);
                roundProcessor.setCurrentGuess(colIndex2,roundProcessor.getCurrentBall());
                stackPane.getScene().setCursor(Cursor.DEFAULT);
          }
        }
    };

    @Override
    public void start(Stage primaryStage) throws Exception {

        BackgroundSize backgroundSize = new BackgroundSize(400, 640, false, false, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(imageback, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        BackgroundImage wallpaper = new BackgroundImage(wall,BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize.DEFAULT);
        Background background_main = new Background(wallpaper);
        Background background = new Background(backgroundImage);

        ImageView logo = new ImageView(new Image("file:resources/logo_main.png"));
        logo.setPreserveRatio(true);
        logo.prefWidth(500);
        logo.setFitWidth(500);

        // grid generator
        boolean gridLinesVisible = false;
        double colSize = 33;
        double rowSize = 20;
        GridPane mainPegsGrid = new GridPane();
        mainPegsGrid.setPrefWidth(258);
        mainPegsGrid.setMaxWidth(258);
        mainPegsGrid.setAlignment(Pos.CENTER);
        mainPegsGrid.relocate(58,5);
        mainPegsGrid.setTranslateY(10);
        mainPegsGrid.setTranslateX(27);
        mainPegsGrid.setPadding(new Insets(0, 0, 0, 0));
        mainPegsGrid.setHgap(rowSize);
        mainPegsGrid.setVgap(colSize);
        mainPegsGrid.setAlignment(Pos.CENTER_LEFT);
        mainPegsGrid.setGridLinesVisible(gridLinesVisible);

        GridPane scoreGrid = new GridPane();
        scoreGrid.setPrefSize(250,40);
        scoreGrid.setHgap(rowSize);
        scoreGrid.setVgap(colSize);
        scoreGrid.setAlignment(Pos.CENTER_RIGHT);
        scoreGrid.setTranslateX(-50);
        scoreGrid.setTranslateY(-7);
        scoreGrid.setGridLinesVisible(gridLinesVisible);

        GridPane bottomSelectionGrid = new GridPane();
        bottomSelectionGrid.setPrefSize(300,30);
        bottomSelectionGrid.setTranslateX(57);
        bottomSelectionGrid.setTranslateY(42);
        bottomSelectionGrid.setHgap(23);
        bottomSelectionGrid.setVgap(colSize);
        bottomSelectionGrid.setGridLinesVisible(gridLinesVisible);

        GridPane pinsGrid = new GridPane();
        pinsGrid.setPrefSize(142,400);
        pinsGrid.setTranslateY(6);
        double colSizePins = 36;
        double rowSizePins = 4;
        pinsGrid.setHgap(rowSizePins);
        pinsGrid.setVgap(colSizePins);
        pinsGrid.setGridLinesVisible(gridLinesVisible);
        pinsGrid.setAlignment(Pos.TOP_RIGHT);

        ColumnConstraints colConst = new ColumnConstraints();
        colConst.setPrefWidth(colSize);
        RowConstraints rowConst = new RowConstraints();
        rowConst.setPrefHeight(rowSize);

        for (int i = 0; i < 8;i++){
            for (int j = 0; j < 4; j++){
                Pane temp_pane = new Pane();
                mainGridPaneMap.put(new gridCell(i,j),temp_pane);
                temp_pane.setPrefSize(30,30);
                temp_pane.addEventHandler(MouseEvent.MOUSE_CLICKED, mainGridHandler);
                mainPegsGrid.add(temp_pane,j,i);
            }
        }

        HashMap<gridCell,Pane> scorePegsMap = new HashMap<>();
        for (int i = 0; i < 1;i++){
            for (int j = 0; j < 4; j++){
                Pane temp_pane = new Pane();
                scorePegsMap.put(new gridCell(i,j),temp_pane);
                temp_pane.setPrefSize(30,30);
                temp_pane.addEventHandler(MouseEvent.MOUSE_CLICKED, mainGridHandler);
                scoreGrid.add(temp_pane,j,i);
            }
        }

        HashMap<gridCell,Pane> selectionPegsMap = new HashMap<>();
        for (int i = 0; i < 1;i++){
            for (int j = 0; j < 6; j++){
                Pane temp_pane = new Pane();
                selectionPegsMap.put(new gridCell(i,j),temp_pane);
                temp_pane.setPrefSize(30,30);
                temp_pane.setCursor(Cursor.HAND);
                temp_pane.addEventHandler(MouseEvent.MOUSE_CLICKED, gridPegSelectionHandler);
                bottomSelectionGrid.add(temp_pane,j,i);
            }
        }

        HashMap<gridCell,Pane> pinsRowMap = new HashMap<>();
        for (int i = 0; i < 8;i++){
            for (int j = 0; j < 1; j++){
                Pane temp_pane = new Pane();
                pinsRowMap.put(new gridCell(i,j),temp_pane);
                temp_pane.setPrefSize(60,60);
                pinsGrid.add(temp_pane,j,i);
            }
        }

        HashMap<gridCell,Pane> individualPinsMap = new HashMap<>();
        for(int i=0;i<8;i++) {
            GridPane grid2 = new GridPane();
            grid2.setGridLinesVisible(gridLinesVisible);
            grid2.setAlignment(Pos.CENTER);
            grid2.setPadding(new Insets(-0, 0, 0, 0));
            grid2.setHgap(8);
            grid2.setVgap(5);

            for(int j=0;j<2;j++){
                for(int k = 0;k<2;k++){
                    Pane temp_pane = new Pane();
                    temp_pane.setPrefSize(17,17);
                    individualPinsMap.put(new gridCell(i,((j*2)+k)),temp_pane);
                    grid2.add(temp_pane,j,k);
                }
            }
            grid2.setTranslateX(5);
            pinsRowMap.get(new gridCell(i, 0)).getChildren().add(grid2);
        }


        VBox mainVBox = new VBox();
        mainVBox.setPrefWidth(200);

        Button buttonNewGame = new Button("New Game");
        buttonNewGame.setCursor(Cursor.HAND);
        buttonNewGame.setAlignment(Pos.CENTER);
        buttonNewGame.setPrefSize(200,100);

        Button buttonExit = new Button("Exit");
        buttonExit.setCursor(Cursor.HAND);
        buttonExit.setAlignment(Pos.CENTER);
        buttonExit.setPrefSize(200,100);
        buttonExit.setTranslateY(20);
        buttonExit.setOnAction (e->{ primaryStage.close(); });

        mainVBox.getChildren().addAll(logo,buttonNewGame,buttonExit);
        mainVBox.setAlignment(Pos.CENTER);

        stackPane.getChildren().add(mainVBox);
        stackPane.setAlignment(Pos.CENTER);
        stackPane.setBackground(background_main);
        Scene scene = new Scene(stackPane, 400, 600);

        String fontStyle = "-fx-font: 35px Tahoma;";
        Label scoreLabel = new Label("0");
        scoreLabel.setStyle(fontStyle);
        scoreLabel.setTextFill(Color.web("#C0C0C0"));
        scoreLabel.setTranslateX(-30);
        scoreLabel.setTranslateY(25);

        VBox vboxMain = new VBox();
        vboxMain.setPrefSize(400,640);
        vboxMain.setMaxSize(400,640);
        vboxMain.setPadding(new Insets(0,0,0,0));

        HBox hBoxTop = new HBox();
        hBoxTop.setPrefHeight(80);
        hBoxTop.setPadding(new Insets(0,0,0,0));

        Pane scorePane = new Pane();
        scorePane.prefWidth(100);
        scorePane.prefHeight(50);
        scorePane.getChildren().add(scoreLabel);

        hBoxTop.getChildren().addAll(scorePane,scoreGrid);
        hBoxTop.setAlignment(Pos.CENTER_RIGHT);
        HBox hbox = new HBox();
        hbox.getChildren().addAll(pinsGrid,mainPegsGrid);

        vboxMain.getChildren().addAll(hBoxTop,hbox,bottomSelectionGrid);
        vboxMain.setAlignment(Pos.TOP_CENTER);
        hbox.setAlignment(Pos.TOP_LEFT);

        pinsGrid.setAlignment(Pos.TOP_RIGHT);

        String btn_style = "margin:0px;\n padding:0px;";

        Label roundLabel = new Label ("< Round: 1");
        String fontStyleRounds = "-fx-font: 35px Tahoma;\n -fx-font-weight:700;";
        roundLabel.setStyle(fontStyleRounds);
        roundLabel.setTextFill(Color.web("#f5e1da"));
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(3.0);
        dropShadow.setOffsetX(2.0);
        dropShadow.setOffsetY(2.0);
        dropShadow.setColor(Color.color(0.4, 0.5, 0.5));
        roundLabel.setEffect(dropShadow);
        roundLabel.setTranslateY(-73);
        roundLabel.setTranslateX(-29);

        Button checkButton = new Button("Next Round");
        checkButton.setStyle(btn_style);
        checkButton.setCursor(Cursor.HAND);
        checkButton.setPrefSize(400,50);
        checkButton.textAlignmentProperty().setValue(TextAlignment.CENTER);
        checkButton.setOnAction(e -> {
            if(roundProcessor.getCurrentRound()<8){
                boolean result = roundProcessor.nextRound(individualPinsMap);
                scoreLabel.setText(Integer.toString(roundProcessor.getScore()));

                if(result){
                    for(int i=0;i<4;i++){
                        scorePegsMap.get(new gridCell(0, i)).getChildren().add(pegsViewer(roundProcessor.getWinningSet().getCurrentSet(i)));
                    }
                }else{
                    roundLabel.setText("< Round: " + (roundProcessor.getCurrentRound()+1));
                    roundLabel.setTranslateY((-roundProcessor.getCurrentRound()*63)-73);
                }
            }
        });

        Button tutorialButton = new Button("Tutorial");
        tutorialButton.setStyle(btn_style);
        tutorialButton.setCursor(Cursor.HAND);
        tutorialButton.setPrefSize(133,50);
        tutorialButton.setOnAction(e-> {
            final Stage dialog = new Stage();
            String fontDialog = "-fx-font: 14px Tahoma;";
            Button nxt_btn = new Button("Next");
            nxt_btn.setPrefSize(70,40);
            nxt_btn.setTranslateY(-10);

            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(primaryStage);
            StackPane dialogPane = new StackPane();
            dialogPane.setAlignment(Pos.BOTTOM_CENTER);
            dialogPane.setMinSize(400,200);

            VBox dialogVbox = new VBox();
            dialogVbox.setMinSize(400,200);
            dialogVbox.setPadding(new Insets(10,10,10,10));
            Text dialogText = new Text("Mastermind is a puzzle game, in which player tries to guess the hidden code composed of coloured pegs. The challenge is to break the hidden code within 8 rounds. You need to guess correct colours as well as their order. There can be multiple pegs of the same colour in the code.\n");
            dialogText.setWrappingWidth(380);
            dialogText.setStyle(fontDialog);
            dialogVbox.getChildren().add(dialogText);
            dialogPane.getChildren().addAll(dialogVbox,nxt_btn);

            Scene dialogScene = new Scene(dialogPane, 400, 200);
            dialog.setScene(dialogScene);
            nxt_btn.setOnAction( x -> { dialog.close();});
            nxt_btn.setTextAlignment(TextAlignment.CENTER);

            dialog.showAndWait();
            dialogText.setText("In order to make a guess you must fill all four positions in a given row (which corresponds to the number of current round). After clicking Next round button the computer will give you feedback in form of black or white pins in a left-side column. Black pin means that one of the pegs from your guess is in the right colour AND placed in the correct slot. White pin means that one of the pegs is in the right colour BUT not in the correct slot.");
            dialogText.setWrappingWidth(380);
            dialogText.setStyle(fontDialog);
            dialogVbox.getChildren().clear();
            dialogVbox.getChildren().add(dialogText);
            dialog.showAndWait();
            dialogText.setText("Four white pins mean you guessed all colours correctly, but none of them is in the correct spot. Four black pins means the code is broken and you win!\n" +
                    "\n" +
                    "For more guidance, please visit:\n" +
                    "https://www.wikihow.com/Play-Mastermind\t");
            dialogVbox.getChildren().clear();
            dialogText.setWrappingWidth(380);
            dialogText.setStyle(fontDialog);
            dialogVbox.getChildren().add(dialogText);
            dialog.showAndWait();
        });

        Button resetButton = new Button("Reset");
        resetButton.setStyle(btn_style);
        resetButton.setCursor(Cursor.HAND);
        resetButton.setPrefSize(134,50);

        Button exitButton = new Button("Exit");
        exitButton.setStyle(btn_style);
        exitButton.setCursor(Cursor.HAND);
        exitButton.setPrefSize(133,50);
        exitButton.setOnAction(e-> {
            Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION);
            exitAlert.setTitle("Mastermind");
            exitAlert.setHeaderText("Do you really want to exit the game?\n" +
                    "Your progress will be lost.");
            exitAlert.showAndWait();
            if(exitAlert.getResult() == ButtonType.OK) {
                System.exit(0);
            }
        });

        VBox vboxBottom = new VBox();
        vboxBottom.setPrefWidth(400);
        HBox hboxBottom = new HBox();
        hboxBottom.setPrefWidth(400);

        hboxBottom.getChildren().addAll(tutorialButton,resetButton,exitButton);
        vboxBottom.getChildren().addAll(checkButton,hboxBottom);
        VBox vboxRight = new VBox();
        vboxRight.setAlignment(Pos.BOTTOM_LEFT);
        vboxRight.getChildren().add(roundLabel);

        BorderPane borderPane = new BorderPane();
        borderPane.setBottom(vboxBottom);
        borderPane.setRight(vboxRight);
        borderPane.setCenter(vboxMain);

        Pane mainPane = new Pane();
        mainPane.setPrefSize(400,640);
        mainPane.setMaxSize(400,640);
        mainPane.setBackground(background);
        mainPane.getChildren().add(borderPane);

        EventHandler<MouseEvent> newGameButtonHandler = event -> {
            logo.setFitWidth(300);
            logo.setTranslateY(-380);
            stackPane.getChildren().clear();
            stackPane.getChildren().addAll(logo,mainPane);
        };

        buttonNewGame.addEventFilter(MouseEvent.MOUSE_CLICKED, newGameButtonHandler);

        EventHandler<MouseEvent> resetHandler = event -> {
            Alert resetAlert = new Alert(Alert.AlertType.CONFIRMATION);
            resetAlert.setTitle("Mastermind");
            resetAlert.setHeaderText("Do you really want to reset the game?\n" +
                    "The board will be reset to Round 1");
            resetAlert.showAndWait();
            if(resetAlert.getResult() == ButtonType.OK) {
                stackPane.getScene().setCursor(Cursor.DEFAULT);
                for(int i = 0;i<8;i++){
                    for(int j = 0; j<4;j++){
                        mainGridPaneMap.get(new gridCell(i,j)).getChildren().clear();
                        individualPinsMap.get(new gridCell(i, j)).getChildren().clear();
                    }
                }
                for(int i=0;i<4;i++){
                    scorePegsMap.get(new gridCell(0, i)).getChildren().clear();
                }
                scoreLabel.setText("0");
                roundProcessor.reset();
                buttonNewGame.addEventFilter(MouseEvent.MOUSE_CLICKED, newGameButtonHandler);
                roundLabel.setText("< Round: 1");
                roundLabel.setTranslateY(-73);
                roundLabel.setTranslateX(-29);
            }
        };

        resetButton.addEventFilter(MouseEvent.MOUSE_CLICKED, resetHandler);
        stackPane.getScene().setCursor(Cursor.DEFAULT);

        primaryStage.setWidth(500);
        primaryStage.setHeight(800);
        primaryStage.setResizable(false);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setFullScreen(true);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Mastermind");
        primaryStage.show();
    }
}
