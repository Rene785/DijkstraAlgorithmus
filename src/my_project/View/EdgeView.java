package my_project.View;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;

public class EdgeView extends GraphicalObject {

    private double x1,x2,y1,y2;
    private int weigth;

    public EdgeView(double x1,double x2,double y1,double y2,int weigth){
       this.x1=x1;
       this.x2=x2;
       this.y1=y1;
       this.y2=y2;
       this.weigth=weigth;
    }

    @Override
    public void draw(DrawTool drawTool) {
        drawTool.drawLine(x1,y1,x2,y2);
        drawTool.drawText(x1+x2/2,y1+y2/2,"Gewicht :  "+weigth);
    }

    @Override
    public void update(double dt) {

    }
}
