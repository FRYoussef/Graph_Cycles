import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce the where the graph are founded: ");
        try {
            IOFile io = new IOFile(sc.nextLine());
            Graph graph = new Graph(io.getGraph());
            graph.dfs();
            System.out.println(graph.getGraphElements());
            io.closeReader();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
