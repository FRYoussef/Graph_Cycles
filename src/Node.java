import java.util.ArrayList;
import java.util.List;

public class Node{
    private List<Node> adjacencyList;
    private String label;
    private int color;
    private int initTime;
    private int finishTime;

    Node(String label) {
        this.label = label;
        adjacencyList = new ArrayList<>();
        color = Graph.WHITE;
        initTime = -1;
        finishTime = -1;
    }

    public String getLabel() {
        return label;
    }

    public List<Node> getAdjacencyList() {
        return adjacencyList;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setInitTime(int initTime) {
        this.initTime = initTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    public void addNode(Node node){
        adjacencyList.add(node);
    }

    @Override
    public int hashCode() {
        return label.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(label + ": ");
        for(Node u : adjacencyList)
            sb.append(u.getLabel()).append(", ");
        return sb.toString();
    }
}