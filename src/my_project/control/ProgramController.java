package my_project.control;

import KAGO_framework.control.ViewController;
import KAGO_framework.model.abitur.datenstrukturen.Edge;
import KAGO_framework.model.abitur.datenstrukturen.Graph;
import KAGO_framework.model.abitur.datenstrukturen.List;
import KAGO_framework.model.abitur.datenstrukturen.Vertex;
import my_project.Model.DijkstraObject;
import my_project.View.EdgeView;
import my_project.View.VertexView;

import java.awt.event.MouseEvent;

/**
 * Ein Objekt der Klasse ProgramController dient dazu das Programm zu steuern. Die updateProgram - Methode wird
 * mit jeder Frame im laufenden Programm aufgerufen.
 */
public class ProgramController {

    //Attribute
    private Graph graph;
    private List<DijkstraObject> list;
    private List<EdgeView> edgeViewList;
    private List<VertexView> vertexViewList;
    private List <Vertex> fastesPath;
    private double length;
    private Vertex end;
    private int amountOfUnmarkedVertices;

    // Referenzen
    private ViewController viewController;  // diese Referenz soll auf ein Objekt der Klasse viewController zeigen. Über dieses Objekt wird das Fenster gesteuert.

    /**
     * Konstruktor
     * Dieser legt das Objekt der Klasse ProgramController an, das den Programmfluss steuert.
     * Damit der ProgramController auf das Fenster zugreifen kann, benötigt er eine Referenz auf das Objekt
     * der Klasse viewController. Diese wird als Parameter übergeben.
     * @param ViewController das viewController-Objekt des Programms
     */
    public ProgramController(ViewController ViewController){
        this.viewController = ViewController;
    }

    /**
     * Diese Methode wird genau ein mal nach Programmstart aufgerufen. Achtung: funktioniert nicht im Szenario-Modus
     */
    public void startProgram() {
        viewController.createScene();
        viewController.showScene(0);
        graph=new Graph();
        length=0;
        fastesPath=new List<>();
        amountOfUnmarkedVertices =0;
        fillGraph();
        startAlgorithm(graph.getVertex("Dortmund"),graph.getVertex("Unna"));
        fastesPath.toFirst();
        System.out.println("Länge "+length);
        for(int i=1;fastesPath.hasAccess()&&i<6;i++){
            System.out.println("Die "+i+"te Sation ist "+fastesPath.getContent().getID());
            fastesPath.next();
        }
    }
    //TODO fülle den Graphen
    public void fillGraph(){
        graph.addVertex(new Vertex("Dortmund"));
        graph.addVertex(new Vertex("Berlin"));
        graph.addVertex(new Vertex("Moskau"));
        graph.addVertex(new Vertex("Paris"));
        graph.addVertex(new Vertex("Hong Kong"));
        graph.addVertex(new Vertex("Unna"));
        graph.addEdge(new Edge(graph.getVertex("Dormtund"),graph.getVertex("Paris"),20));
        graph.addEdge(new Edge(graph.getVertex("Unna"),graph.getVertex("Paris"),10));
        graph.addEdge(new Edge(graph.getVertex("Unna"),graph.getVertex("Hong Kong"),15));
        graph.addEdge(new Edge(graph.getVertex("Moskau"),graph.getVertex("Paris"),20));
        graph.addEdge(new Edge(graph.getVertex("Moskau"),graph.getVertex("Berlin"),30));
        graph.addEdge(new Edge(graph.getVertex("Berlin"),graph.getVertex("Unna"),20));
        graph.addEdge(new Edge(graph.getVertex("Dormtund"),graph.getVertex("Berlin"),25));
        graph.addEdge(new Edge(graph.getVertex("Hong Kong"),graph.getVertex("Paris"),10));
        graph.addEdge(new Edge(graph.getVertex("Hong Kong"),graph.getVertex("Dortmund"),10));
        graph.addEdge(new Edge(graph.getVertex("Unna"),graph.getVertex("Moskau"),10));

        /*List <Vertex> vertexList=graph.getVertices();
        vertexList.toFirst();
        while(vertexList.hasAccess()){
            vertexViewList.append(new VertexView(Math.random()*1300+50,Math.random()*900+40,vertexList.getContent().isMarked(),vertexList.getContent().getID()));
            vertexViewList.toLast();
            viewController.draw(vertexViewList.getContent());
            vertexList.next();
        }
        List <Edge> edgeList=graph.getEdges();

        edgeList.toFirst();
        while(edgeList.hasAccess()){
            Vertex[] vertexArray=edgeList.getContent().getVertices();
            edgeViewList.append(new EdgeView());
            viewController.draw(vertexViewList.getContent());
            edgeList.next();
        }*/
    }

    /**
     * Sorgt dafür, dass zunächst gewartet wird, damit der SoundController die
     * Initialisierung abschließen kann. Die Wartezeit ist fest und damit nicht ganz sauber
     * implementiert, aber dafür funktioniert das Programm auch bei falscher Java-Version
     * @param dt Zeit seit letzter Frame
     */
    public void updateProgram(double dt){

    }

    public void mouseClicked(MouseEvent e){}

    /**
     *
     * @param start der Startknoten
     * @param end der Endknoten
     */
    public void startAlgorithm(Vertex start,Vertex end){
        System.out.println("Start wurde aufgerufen");
        list=new List<>(); // eine enue Liste wird initialisiert
        this.end=end;
        List <Vertex> vertexList=graph.getVertices();
        vertexList.toFirst();
        //übertrage die Liste aller Knoten in eine neue Liste,
        // wo die Knotenobjekte mit anderen Daten(Abstand zum Start und Vorgänger) verwahrt werden
        while(vertexList.hasAccess()){
            if(vertexList.getContent().getID().equals(start.getID())){
                //Das Startobjekt muss an erster Stelle stehen
                list.toFirst();
                DijkstraObject tmp=new DijkstraObject(vertexList.getContent(),null);
                tmp.setPrevious(tmp);
                list.insert(tmp);
                list.toFirst();
                list.getContent().setDistanceToStart(0);
            }else{

                DijkstraObject tmp=new DijkstraObject(vertexList.getContent(),null);
                tmp.setPrevious(tmp);
                list.append(tmp);
            }
            amountOfUnmarkedVertices++;
            vertexList.next();
        }
        System.out.println("Die Liste mit Dijkstra-Objekten wurde gefüllt");

        stepInAlgorithm();

    }
    public void stepInAlgorithm(){
        System.out.println("Step wurde aufgerufen");
        //Das erste Objekt der Liste ist automatisch das kleinste Objekt
        list.toFirst();
        //erstelle List neigboer mit dem Objekt, welches die Nachbarknoten verwaltet
        List<Vertex> neigborhood=graph.getNeighbours(list.getContent().getThisOne());
        neigborhood.toFirst();
        DijkstraObject  firstDO=list.getContent();
        System.out.println("neigborhood wurde erstellt");

        while(neigborhood.hasAccess()) {
            //bringe Zeiger auf jew Objekt in Liste
            search(neigborhood.getContent());
            if(list.getContent()!=firstDO) {
                //prüfe, ob über das aktuel erste Objekt in der Liste die Entfernung zum Start geringer ist, als vorher
                double newDistance = graph.getEdge(list.getContent().getThisOne(), firstDO.getThisOne()).getWeight()
                        + firstDO.getDistanceToStart();
                if (list.getContent().getDistanceToStart() > newDistance) {
                    System.out.println("previosus wird verändert");

                    list.getContent().setDistanceToStart(newDistance);
                    list.getContent().setPrevious(list.getContent());

                    System.out.println("veränderte Werte werden in Liste angepasst wurde erstellt");
                    //Die Sortierschlüssel im Nachbarknoten wurden verändert,
                    // sodass es neu in die Liste eingefügt werden muss
                    DijkstraObject actual = list.getContent();
                    list.remove();
                    addToSortedList(actual);
                }
            }
            neigborhood.next();

        }
        System.out.println("Nachbarn wurden geprüft");

        list.toFirst();
        //markiere das betrachtete Objekt und füge es sortiert in die Liste neu ein
        list.getContent().getThisOne().setMark(true);
        amountOfUnmarkedVertices--;
        addToSortedList(list.getContent());
        list.remove();
        //Nun folge die Abbruchbedingungen
        list.toFirst();
        firstDO=list.getContent();
        boolean recursion=true;
        if(list.getContent().getThisOne()==end ){//Falls das neue aktuelle Objekt,
            // das Objekt am Ende ist, wurde das Ziel erreicht
            // und die anderen Wege sind länger und können daher nicht mehr kürzer werden.
            recursion=false;
            fastesPath=returnFastestPath(list.getContent());
            length=list.getContent().getDistanceToStart();
            System.out.println("end wurde über den kürzesten Pfad erreicht");
        }
        if(amountOfUnmarkedVertices==0){//falls in der Liste schon alle Objekte markiert waren
            //bringe den Zeiger auf end in der Liste
            search(end);
            if(list.hasAccess()){
                recursion=false;
                fastesPath=returnFastestPath(list.getContent());
                length= list.getContent().getDistanceToStart();
                System.out.println("nichts drin");

            }else{
                //es wurde kein Weg gefunden
                System.out.println("Kein Weg gefunden. Suche gefälligst einen zusammenhängenden Graphen");
            }
        }
        if(recursion){
            stepInAlgorithm();
        }

    }

    public void addToSortedList(DijkstraObject dO){
        list.toFirst();
        boolean search =true;
        while(list.hasAccess()&&search){
            if((dO.getThisOne().isMarked() && !list.getContent().getThisOne().isMarked())
                    &&dO.getDistanceToStart()>=list.getContent().getDistanceToStart()){
                list.next();
            }else{
                 list.insert(dO);
                 search=false;
            }
        }
    }
    //TODO mache daraus eine binäre Suche oder mache aus der liste einfach einen Suchbaum ...
    public void search(Vertex v){
        System.out.println("Suche begonnen");

        list.toFirst();
        boolean stop=true;
        while(list.hasAccess()&&stop){
            if(list.getContent().getThisOne().getID().equals(v.getID())){
                stop=false;
            }else{
                list.next();
            }
        }
        System.out.println("Suche abgeschlossen");

    }

    public List<Vertex> returnFastestPath(DijkstraObject actual){
        System.out.println("Rückgabe wurde begonnen-----------");

        List<Vertex> vertexList=new List<>();
        if(actual!=null){
            while(actual!=actual.getPrevious() && actual!=null){
                vertexList.insert(actual.getThisOne());
                actual=actual.getPrevious();
            }
            System.out.println("Rückgabe wurde beendet-----------");
            return vertexList;
        }
        System.out.println("Rückgabe wurde beendet-----------");

        return null;
    }

}
