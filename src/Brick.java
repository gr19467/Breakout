import acm.graphics.GRect;

import java.awt.*;

public class Brick extends GRect {

    public static final int WIDTH = 44;
    public static final int HEIGHT = 20;
    public int hitsNeeded;

    public Brick(double x, double y, Color color, int row){
        super(x, y, WIDTH, HEIGHT);
        this.setFilled(true);
        this.setFillColor(color);

        if(row == 9 || row == 8){
            hitsNeeded = 1;

        }else if(row == 7 || row == 6){
            hitsNeeded = 2;

        }else if(row == 5 || row == 4){
            hitsNeeded = 3;

        }else if(row == 3 || row == 2){
           hitsNeeded = 4;

        }else{
            hitsNeeded = 5;

        }

    }

}