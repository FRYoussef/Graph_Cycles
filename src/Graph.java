import java.util.*;

public class Graph {

    private static final String LINE_SEPARATOR = System.lineSeparator();
    public static final String NODE_SEPARATOR = ";";
    private static final String SPLIT_NODE = ":";
    private static final String SPLIT_ADJACENCY_LIST = ",";
    private static final boolean SIMULATION = true;
    public static final int WHITE = 0;
    private static final int GRAY = 1;
    private static final int BLACK = 2;


    private Node[] nodes;
    private LinkedList<Node> topologicalSort;
    private boolean acyclic;
    private int time;

    /**
     * It creates a graph from a string with the node structure
     *
     * @param nodes a string with the node structure
     */
    public Graph(String nodes) {
        acyclic = true;
        time = 0;
        topologicalSort = new LinkedList<>();
        parseGraph(nodes);
    }

    public int getNumVertex(){
        return nodes.length;
    }

    public boolean isAcyclic() {
        return acyclic;
    }

    /**
     * It creates a graph transposed from the nodes of the original graph
     *
     * @param nodes an array which contains the nodes
     */
    private Graph(Node[] nodes){
        HashMap<String, Node> hmNodes = new HashMap<>(nodes.length);
        this.nodes = new Node[nodes.length];
        acyclic = false;
        time = 0;

        for(int i = 0; i < nodes.length; i++){
            if(!hmNodes.containsKey(nodes[i].getLabel()))
                hmNodes.put(nodes[i].getLabel(), new Node(nodes[i].getLabel()));
            Node uNew = hmNodes.get(nodes[i].getLabel());
            for(Node v : nodes[i].getAdjacencyList()){
                if(!hmNodes.containsKey(v.getLabel()))
                    hmNodes.put(v.getLabel(), new Node(v.getLabel()));
                hmNodes.get(v.getLabel()).addNode(uNew);
            }
            this.nodes[i] = uNew;
        }
        hmNodes = null;
    }

    /**
     * It parses a string to initialize the graph nodes
     *
     * @param nodes a string with the node structure
     */
    private void parseGraph(String nodes){

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

    /**
     * It finds the index of a node
     * @param val the label to find
     * @return -1 if has not find or "int" >= 0 if it is find
     */
    private int findNodeIndex(String val){
        for(int i = 0; i < nodes.length; i++)
            if(nodes[i].getLabel().equals(val))
                return i;
        return -1;
    }

    /**
     * The DFS algorithm, Cormen, second edition, chapter 22
     */
    public void dfs(){
        for(Node u : nodes)
            u.setColor(WHITE);
        time = 0;
        topologicalSort.clear();
        for (Node u : nodes)
            if(u.getColor() == WHITE)
                dfsVisit(u, false, null);
    }

    /**
     * The DFS algorithm for a Gt, where we creates a string with the SCC
     * @return a string with the SCC
     */
    public String dfsTrasposed(){
        StringBuilder sb = null;
        if(!SIMULATION) sb = new StringBuilder("Strongly Connected Components:" + LINE_SEPARATOR);
        time = 0;
        for (Node u: this.nodes){
            if(u.getColor() == WHITE){
                dfsVisit(u, true, sb);
                if(sb != null) sb.append(LINE_SEPARATOR);
            }
        }
        return sb == null ? null : sb.toString();
    }

    /**
     * The DFS algorithm, Cormen, second edition, chapter 22
     * @param u a vertex or node from the graph
     * @param transposedSearch a flag to know if we want to store the node´s label
     * @param sb where we store the node´s label
     */
    private void dfsVisit(Node u, boolean transposedSearch, StringBuilder sb) {
        u.setColor(GRAY);
        u.setInitTime(++time);
        if(transposedSearch && sb != null)
            sb.append(u.getLabel()).append(", ");
        for(Node v : u.getAdjacencyList())
        {

            if(v.getColor() == WHITE)
                dfsVisit(v, transposedSearch, sb);
            else if(acyclic && v.getColor() == GRAY) //if we visited a backward edge, it means the graph is cyclic
                acyclic = false;
        }
        u.setColor(BLACK);
        u.setFinishTime(++time);
        if(!transposedSearch && acyclic)
            topologicalSort.addFirst(u);
    }

    /**
     * It return a topological sort if the graph was acyclic
     * or the SCC, if was not
     * @return a string
     */
    public String getGraphElements(){
        if(acyclic)
        {
            if(SIMULATION) return null;
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
}
