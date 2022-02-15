import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.program.GraphicsProgram;
import svu.csc213.Dialog;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Breakout extends GraphicsProgram {

    /*
    6) How can I tell that a brick has been hit?

    7) Power-ups in some bricks?
    8) Multiple levels?
    9) An animation for a broken brick?
     */


    private Ball ball;
    private Paddle paddle;
    private Brick brick;

    private int numBricksInRow, points, lives, col;
    public int row;
    private GLabel pointLabel, lifeLabel;

    private Color[] rowColors = {new Color(222,15,15), new Color(255,117,19),  new Color(222,194,15), new Color(113,255,19), new Color(49, 255, 193), new Color(19, 137, 255), new Color(86, 19, 255), new Color(113, 19, 255), new Color(176, 19, 255), new Color(224, 19, 255)};
    private Color[] dimmerColors = {new Color(155, 9, 9), new Color(190, 86, 15), new Color(157, 138, 11), new Color(60, 133, 10), new Color(36, 178, 135), new Color(15, 90, 169), new Color(66, 15, 183), new Color(72, 20, 155), new Color(120, 14, 169), new Color(149, 13, 168)};

    @Override
    public void init() {

        this.setSize(1920, 1000);

        numBricksInRow = getWidth() / (Brick.WIDTH + 5);

        for (row = 0; row < 10; row++) {
            for (col = 0; col < numBricksInRow; col++) {

                double brickX = 10 + col * Brick.WIDTH + 5;
                double brickY = Brick.HEIGHT + row * (Brick.HEIGHT +5);
                brick = new Brick(brickX, brickY, rowColors[row], row);
                add(brick);
                if(row == 9 || row == 8){
                    brick.hitsNeeded = 1;

                }else if(row == 7 || row == 6){
                    brick.hitsNeeded = 2;

                }else if(row == 5 || row == 4){
                    brick.hitsNeeded = 3;

                }else if(row == 3 || row == 2){
                    brick.hitsNeeded = 4;

                }else{
                    brick.hitsNeeded = 5;

                }

            }
        }
        points = 0;
        pointLabel = new GLabel("Points: " + points);
        add(pointLabel, 10, 20);

        lives = 3;
        lifeLabel = new GLabel("Lives: " + lives);
        add(lifeLabel, 10, 10);

        ball = new Ball(getWidth() / 2, 350, 10, this.getGCanvas());
        add(ball);

        paddle = new Paddle(230, 430, 50, 10);
        add(paddle);

    }

    @Override
    public void run(){
        addMouseListeners();
        waitForClick();
        gameLoop();

    }

    @Override
    public void mouseMoved(MouseEvent me){
        //Constrain the paddle to the edges of the window
        if ((me.getX() < getWidth() - paddle.getWidth()/2) && (me.getX() > paddle.getWidth()/2)) {
            paddle.setLocation(me.getX() - paddle.getWidth()/2, paddle.getY());
        }
    }

    private void gameLoop(){
        while(true){
            //move the ball
            ball.handleMove();

            //check if there were any collisions
            handleCollisions();

            //check if the ball was lost
            if(ball.lost){
                handleLoss();

                if(brick == null){
                    win();
                }
            }

            pause(5);
        }
    }



    private void handleCollisions() {

        //create a container
        GObject obj = null;

        //see if we hit something

        if (obj == null) {
            //check upper left corner
            obj = this.getElementAt(ball.getX(), ball.getY());
        }

        if (obj == null) {
            //check upper right corner
            obj = this.getElementAt(ball.getX() + ball.getWidth(), ball.getY());
        }

        if (obj == null) {
            //check lower left corner
            obj = this.getElementAt(ball.getX(), ball.getY() + ball.getHeight());
        }

        if (obj == null) {
            //check lower right corner
            obj = this.getElementAt(ball.getX() + ball.getWidth(), ball.getY() + ball.getHeight());
        }


        //if we hit something
        if(obj != null){

            //what did I hit??

            //if I hit the paddle
            if(obj instanceof Paddle){

                pointLabel.setLabel("Points: " + points);

                if(ball.getX() > (paddle.getX() + paddle.getWidth() * .8)){
                    //if I hit the right edge of the paddle
                    ball.bounceRight();

                } else if(ball.getX() < (paddle.getX() + paddle.getWidth()/5)){
                    //if I hit the left edge of the paddle
                    ball.bounceLeft();
                } else{
                    //I hit the middle of the paddle
                    ball.bounce();
                }

            }

            // if I hit a brick
            if(obj instanceof Brick) {

                ball.bounce();

                points += 1;
                pointLabel.setLabel("Points: " + points);

                if (((Brick) obj).hitsNeeded == 1) {

                    this.remove(obj);
                    points += 10;
                    pointLabel.setLabel("Points: " + points);

                } else if (((Brick) obj).hitsNeeded == 2) {

                    ((Brick) obj).hitsNeeded -= 1;

                    if (((Brick) obj).hitsNeeded == 0) {
                        this.remove(obj);
                        points += 20;
                        pointLabel.setLabel("Points: " + points);
                    }
                } else if (((Brick) obj).hitsNeeded == 3) {

                    ((Brick) obj).hitsNeeded -= 1;

                    if (((Brick) obj).hitsNeeded == 0) {
                        this.remove(obj);
                        points += 30;
                        pointLabel.setLabel("Points: " + points);
                        }
                    } else if (((Brick) obj).hitsNeeded == 4) {

                    ((Brick) obj).hitsNeeded -= 1;

                    if (((Brick) obj).hitsNeeded == 0) {
                        this.remove(obj);
                        points += 40;
                        pointLabel.setLabel("Points: " + points);
                        }
                    } else if (((Brick) obj).hitsNeeded == 5) {

                    ((Brick) obj).hitsNeeded -= 1;

                    if (((Brick) obj).hitsNeeded == 0) {
                        this.remove(obj);
                        points += 50;
                        pointLabel.setLabel("Points: " + points);
                    }
                }
                }
            }
        }

        //if we make it to the end, and it's still null, we hit nothing


    private void dimColor(){
        if(row == 2){
            brick.setFillColor(dimmerColors[7]);

        }else if(row == 3){
            brick.setFillColor(dimmerColors[6]);

        }else if(row == 4){
            brick.setFillColor(dimmerColors[5]);

        }else if(row == 5){
            brick.setFillColor(dimmerColors[4]);

        }else if(row ==6){
            brick.setFillColor(dimmerColors[3]);

        }else if(row == 7){
            brick.setFillColor(dimmerColors[2]);

        }else if(row == 8){
            brick.setFillColor(dimmerColors[1]);

        }else if(row == 9){
            brick.setFillColor(dimmerColors[0]);

        }
    }

    private void handleLoss () {
        if(ball.lost){
            //lose a life
            lives -= 1;
            lifeLabel.setLabel("Lives: " + lives);

            //check if they've lost all their lives
            if(lives == 0){
                lose();
            }
            reset();
        }

        ball.lost = false;
        reset();
    }

    private void win() {
        Dialog.showMessage("You win!");
    }

    private void lose() {
        Dialog.showMessage("You lost!");
        System.exit(0);
    }

    private void reset () {
        //put the ball back
        ball.setLocation(getWidth() / 2, 350);

        //put the paddle back
        paddle.setLocation(230, 430);

        //wait for click
        waitForClick();
    }

    public static void main (String[]args){
        new Breakout().start();
    }
}