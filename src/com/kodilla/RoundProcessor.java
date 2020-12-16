package com.kodilla;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import java.util.HashMap;
import static com.kodilla.boardViewer.pinsViewer;

public class RoundProcessor {
    private int currentRound;
    private int currentBall;
    private boolean ballPicked;
    private int score;
    private Integer[] currentGuess;
    private WinningSet winningSet;

    public RoundProcessor() {
        this.currentRound = 0;
        this.currentBall = 0;
        this.ballPicked = false;
        this.score = 0;
        this.currentGuess = new Integer[4];
        for(int i = 0;i<4;i++){
            this.currentGuess[i] = 9;
        }
        this.winningSet = new WinningSet();
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public int getCurrentBall() {
        return currentBall;
    }

    public boolean isBallPicked() {
        return ballPicked;
    }

    public int getScore() { return score; }

    public int getCurrentGuess(int index) {
        return currentGuess[index];
    }

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    public void setCurrentBall(int currentBall) {
        this.currentBall = currentBall;
    }

    public void setBallPicked(boolean ballPicked) {
        this.ballPicked = ballPicked;
    }

    public void setCurrentGuess(int col, int guess) {
        this.currentGuess[col] = guess;
    }

    public WinningSet getWinningSet() { return winningSet; }

    public void reset(){
        this.currentRound = 0;
        this.currentBall = 0;
        this.ballPicked = false;
        this.score = 0;
        this.currentGuess = new Integer[4];
        for(int i = 0;i<4;i++){
            this.currentGuess[i] = 9;
        }
        this.winningSet = new WinningSet();
    }

    public boolean nextRound(HashMap<gridCell, Pane> pinsMapIndividual){
        boolean result;
        result = false;
        boolean incompleteCheck;
        incompleteCheck = false;
        for(int i=0;i<4;i++){
            if(this.currentGuess[i]==9){
                incompleteCheck=true;
            }
        }
        if(incompleteCheck){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Mastermind");
            alert.setHeaderText("You must fill out all empty spaces in row no. "+(this.currentRound+1));
            alert.showAndWait();
        } else {
            int tempScore=0;
            Boolean[] WinSetCheck = new Boolean[4];
            Boolean[] CurrentSetCheck = new Boolean[4];
            for(int k = 0;k<4;k++){
                WinSetCheck[k] = true;
                CurrentSetCheck[k] = true;
            }
            for(int i=0;i<4;i++){
                    if(this.winningSet.getCurrentSet(i)==this.currentGuess[i]){
                            tempScore++;
                            pinsMapIndividual.get(new gridCell(7-this.currentRound, i)).getChildren().add(pinsViewer(0));
                            this.score = this.score +25;
                            WinSetCheck[i]=false;
                            CurrentSetCheck[i]=false;
                    }
            }
            for(int i=0;i<4;i++) {
                for (int j = 0; j < 4; j++) {
                    if (this.winningSet.getCurrentSet(i) == this.currentGuess[j] && WinSetCheck[i]&&CurrentSetCheck[j]) {
                        if (i != j) {
                            pinsMapIndividual.get(new gridCell(7 - this.currentRound, i)).getChildren().add(pinsViewer(1));
                            this.score = this.score +15;
                            WinSetCheck[i]=false;
                            CurrentSetCheck[j]=false;
                        }
                    }
                }
            }
            if(tempScore==4){
                if(this.currentRound!=7){
                    this.score = this.score + ((7-this.currentRound)*100) + 199;
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Mastermind");
                alert.setHeaderText("Success!! you have completed the challenge within "+(this.currentRound+1) + " rounds.\nYour score: "+this.score);
                alert.showAndWait();
                this.currentRound=8;
                result = true;

            }else{
                if(this.currentRound==7){
                    this.currentRound=8;
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Mastermind");
                    alert.setHeaderText("Game over. You haven't placed a correct guess within 8 rounds.\n");
                    alert.showAndWait();
                    result = true;
                } else {
                    this.currentRound++;
                    this.currentGuess = new Integer[4];
                    for(int i = 0;i<4;i++){
                        this.currentGuess[i] = 9;
                    }
                    this.ballPicked = false;
                }
            }
        }
        return result;
    }
}
