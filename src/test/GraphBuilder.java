package test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class GraphBuilder {

    private final int MAX_EDGES;
    private final int MAX_VERTEX;
    private static final String DEFAULT_LABEL = "Node";
    private final String FILE;
    private static final String LINE_SEPARATOR = System.lineSeparator();

    private ArrayList<Node> alGraph;
    private Random r;

    public GraphBuilder(int v, int e, String file) {
        MAX_VERTEX = v;
        MAX_EDGES = e;
        FILE = file;
        alGraph = new ArrayList<>(MAX_VERTEX);
        r = new Random(System.currentTimeMillis());
        createGraph();
    }

    private void createGraph(){

        for (int i = 0; i < MAX_VERTEX; i++) {
            Node n = new Node(DEFAULT_LABEL+i);
            int numEdges = r.nextInt(MAX_EDGES);
            for (int j = 0; j < numEdges; j++) {
                int edge;
                do{
                    edge = r.nextInt(MAX_VERTEX);
                }while (n.adjacencyList.contains(DEFAULT_LABEL+edge));
                n.adjacencyList.add(DEFAULT_LABEL+edge);
            }
            alGraph.add(n);
        }
    }

    public void toFile(){
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(new File(FILE)));
            for (Node n : alGraph){
                StringBuilder sb = new StringBuilder();
                sb.append(n.label).append(": ");
                for (String s : n.adjacencyList)
                    sb.append(s).append(", ");

                //last concat
               sb.append(LINE_SEPARATOR);
                br.write(sb.toString());
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class Node{
        HashSet<String> adjacencyList;
        String label;

        public Node(String label) {
            this.adjacencyList = new HashSet<>(MAX_EDGES);
            this.label = label;
        }
    }
}
