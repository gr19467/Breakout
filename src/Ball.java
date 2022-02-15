import acm.graphics.GCanvas;
import acm.graphics.GOval;

public class Ball extends GOval {

    private double deltaX = 1;
    private double deltaY = 1;
    private GCanvas screen;
    public boolean lost = false;

    public Ball(double x, double y, double size, GCanvas screen){
        super(x, y, size, size);
        setFilled(true);
        this.screen = screen;
    }

    public void handleMove(){
        move(deltaX, deltaY);
        //check if ball hits top
        if(getY() <= 0){
            deltaY *= -1;
        }

        //check if ball hits bottom
        if(getY() >= screen.getHeight() - getHeight()){
            lost = true;
            deltaX = 1;
            deltaY = 1;
        }

        //check if ball hits left
        if(getX() <= 0){
            deltaX *= -1;
        }

        //check if ball hits right
        if(getX() >= screen.getWidth() - getWidth()){
            deltaX *= -1;
        }

    }

    public void bounce(){
        //flip the sign of deltaY
        deltaY *= -1;
    }

    public void bounceLeft(){
        //flip the sign of deltaY
        deltaY *= -1;

        //make deltaX negative
        deltaX = -Math.abs(deltaX);
    }

    public void bounceRight(){
        //flip the sign of deltaY
        deltaY *= -1;

        //make deltaX positive
        deltaX = Math.abs(deltaX);
    }
}
