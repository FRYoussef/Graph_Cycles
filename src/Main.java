import java.util.Scanner;

public class Main {

    private static final int AVERAGE = 3;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce where the graph are founded: ");
        try {
            IOFile io = new IOFile(sc.nextLine());
            Graph graph = new Graph(io.getGraph());
            io.closeReader();
            int totalTime = 0;
            for (int i = 0; i < AVERAGE; i++)
                totalTime += executeGraph(graph);
            //System.out.println(graph.isAcyclic());
            io.writeData(totalTime/AVERAGE, graph.getNumVertex());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static long executeGraph(Graph graph){
        long initTime = System.currentTimeMillis();
        graph.dfs();
        String aux = graph.getGraphElements();
        if(aux != null) System.out.println(aux);
        return System.currentTimeMillis() - initTime;
    }

}
