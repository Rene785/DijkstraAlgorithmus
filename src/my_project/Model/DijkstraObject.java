package my_project.Model;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.model.abitur.datenstrukturen.Vertex;
import KAGO_framework.view.DrawTool;

/**
 * Ein Objekt, welches den Vertex mit seiner Entferung vom Start und dem Vorgänger zusammen zeichnet.
 */
public class DijkstraObject  {
    private double distanceToStart;
    private DijkstraObject previous;
    private Vertex thisOne;

    public DijkstraObject(Vertex thisOne,DijkstraObject previous){
        this.thisOne=thisOne;
        this.previous=previous;
        distanceToStart=Double.MAX_VALUE;
    }

    public double getDistanceToStart() {
        return distanceToStart;
    }

    public void setDistanceToStart(double distanceToStart) {
        this.distanceToStart = distanceToStart;
    }

    public DijkstraObject getPrevious() {
        return previous;
    }

    public void setPrevious(DijkstraObject previous) {
        this.previous = previous;
    }

    public Vertex getThisOne() {
        return thisOne;
    }

    public void setThisOne(Vertex thisOne) {
        this.thisOne = thisOne;
    }


}
