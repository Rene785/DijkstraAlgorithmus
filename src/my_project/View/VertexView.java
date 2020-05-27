package my_project.View;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.model.abitur.datenstrukturen.List;
import KAGO_framework.model.abitur.datenstrukturen.Vertex;
import KAGO_framework.view.DrawTool;

public class VertexView extends GraphicalObject {

    private boolean isMarked;
    private String name;
    private List<Vertex> vertexList;

    public VertexView(double x,double y,boolean isMarked,String name){
        this.x=x;
        this.y=y;
        this.isMarked=isMarked;
        this.name=name;
        width=40;

    }

    @Override
    public void draw(DrawTool drawTool) {
        drawTool.setCurrentColor(255,0,0,255);
        if(isMarked){
            drawTool.setCurrentColor(0,0,255,255);
        }
        drawTool.drawCircle(x,y,width);
    }

    @Override
    public void update(double dt) {

    }
}
