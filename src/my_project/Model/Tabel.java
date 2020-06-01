package my_project.Model;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;

public class Tabel extends GraphicalObject {

    private DijkstraObject dO;

    public Tabel(DijkstraObject dO,double x,double y) {
        this.x=x;
        this.y=y;
        this.dO = dO;
        width=200;
        height=50;
    }

    @Override
    public void draw(DrawTool drawTool) {
        drawTool.drawRectangle(x,y,width,height);
        drawTool.drawText(x+10,y*8/7,"Knoten: "+dO.getThisOne().getID());

        drawTool.drawRectangle(x,y+height,width,height);
        drawTool.drawText(x+10,y*8/7+height,"Abstand: "+dO.getDistanceToStart());

        drawTool.drawRectangle(x,y+2*height,width,height);
        drawTool.drawText(x+10,y*8/7+2*height,"Vorgänger : "+dO.getPrevious().getThisOne().getID());

        drawTool.drawRectangle(x,y+3*height,width,height);
        drawTool.drawText(x+10,y*8/7+3*height,"Besucht: "+dO.getThisOne().isMarked());
    }

    @Override
    public void update(double dt) {

    }


}
