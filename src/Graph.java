import java.util.*;

public class Graph {

    private static final String LINE_SEPARATOR = System.lineSeparator();
    public static final String NODE_SEPARATOR = ";";
    private static final String SPLIT_NODE = ":";
    private static final String SPLIT_ADJACENCY_LIST = ",";
    private static final int WHITE = 0;
    private static final int GRAY = 1;
    private static final int BLACK = 2;


    private Node[] nodes;
    private LinkedList<Node> topologicalSort;
    private boolean acyclic;
    private int time;

    public Graph(String nodes) {
        acyclic = true;
        time = 0;
        topologicalSort = new LinkedList<>();

        nodes = nodes.replace(" ", "");
        String [] nodeList = nodes.split(NODE_SEPARATOR);
        this.nodes = new Node[nodeList.length];

        // It creates the vertex
        for(int i = 0; i < nodeList.length; i++)
            this.nodes[i] = new Node(nodeList[i].split(SPLIT_NODE)[0]);

        //It creates the edges
        for(int i = 0; i < nodeList.length; i++){
            String []aux = nodeList[i].split(SPLIT_NODE);
            if(aux.length > 1)
            {
                String []aList = aux[1].split(SPLIT_ADJACENCY_LIST);
                for(String val : aList){
                    int index = findNodeIndex(val);
                    if(index != -1)
                        this.nodes[i].addNode(this.nodes[index]);
                }
            }
        }
    }

    private Graph(Node[] nodes){
        HashMap<String, Node> hmNodes = new HashMap<>(nodes.length);
        this.nodes = new Node[nodes.length];
        acyclic = false;
        time = 0;

        for(int i = 0; i < nodes.length; i++){
            nodes[i].setColor(WHITE);
            if(!hmNodes.containsKey(nodes[i].getLabel()))
                hmNodes.put(nodes[i].getLabel(), new Node(nodes[i]));
            Node uNew = hmNodes.get(nodes[i].getLabel());
            for(Node v : nodes[i].getAdjacencyList()){
                if(!hmNodes.containsKey(v.getLabel()))
                    hmNodes.put(v.getLabel(), new Node(v));
                hmNodes.get(v.getLabel()).addNode(uNew);
            }
            this.nodes[i] = uNew;
        }
        hmNodes = null;
    }

    /**
     * It finds the index of a node
     * @param val the label to find
     * @return -1 if has not find or int >= 0 if it is find
     */
    private int findNodeIndex(String val){
        for(int i = 0; i < nodes.length; i++)
            if(nodes[i].getLabel().equals(val))
                return i;
        return -1;
    }

    public void dfs(){
        for(Node u : nodes)
            u.setColor(WHITE);
        time = 0;
        for (Node u : nodes)
            if(u.getColor() == WHITE)
                dfsVisit(u, false, null);
    }

    public String dfsTrasposed(){
        StringBuilder sb = new StringBuilder("Strongly Connected Components:" + LINE_SEPARATOR);
        time = 0;
        for (Node u: this.nodes){
            if(u.getColor() == WHITE){
                dfsVisit(u, true, sb);
                sb.append(LINE_SEPARATOR);
            }
        }
        return sb.toString();
    }

    private void dfsVisit(Node u, boolean transposedSearch, StringBuilder sb) {
        u.setColor(GRAY);
        u.setInitTime(++time);
        if(transposedSearch)
            sb.append(u.getLabel()).append(", ");
        for(Node v : u.getAdjacencyList())
        {

            if(v.getColor() == WHITE)
                dfsVisit(v, transposedSearch, sb);
            else if(acyclic && v.getColor() == GRAY)
                acyclic = false;
        }
        u.setColor(BLACK);
        u.setFinishTime(++time);
        if(!transposedSearch)
            topologicalSort.addFirst(u);
    }

    public String getGraphElements(){
        if(acyclic)
        {
            StringBuilder sb = new StringBuilder("Topological Sort:" + LINE_SEPARATOR);
            for(Node n : topologicalSort)
                sb.append(n.getLabel()).append(", ");
            return sb.toString();
        }
        else
        {
            Graph gT = new Graph(nodes);
            return gT.dfsTrasposed();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Node u: nodes)
            sb.append(u).append(LINE_SEPARATOR);
        return sb.toString();
    }

    private class Node{
        private List<Node> adjacencyList;
        private String label;
        private int color;
        private int initTime;
        private int finishTime;

        public Node(String label) {
            this.label = label;
            adjacencyList = new ArrayList<>();
            color = Graph.WHITE;
            initTime = -1;
            finishTime = -1;
        }

        public Node (Node node){
            label = node.getLabel();
            adjacencyList = new ArrayList<>();
            initTime = node.getInitTime();
            finishTime = node.getFinishTime();
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

        public int getInitTime() {
            return initTime;
        }

        public int getFinishTime() {
            return finishTime;
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
}
