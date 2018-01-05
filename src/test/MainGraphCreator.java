package test;

public class MainGraphCreator {


    public static void main(String[] args) {
        int v = 31000;
        int e = 5;
        String file = "graph_v"+v+"_e"+e+".txt";
        GraphBuilder gb = new GraphBuilder(v, e, file);
        gb.toFile();
    }
}
