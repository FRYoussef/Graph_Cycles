import java.io.*;

public class IOFile {

    private static final String OUT_FILE = "dataSCC.txt";
    private BufferedReader br;

    public IOFile(String file) throws FileNotFoundException {
        br = new BufferedReader(new FileReader(new File(file)));
    }

    public String getGraph(){
        StringBuilder sb = new StringBuilder();
        try{
            String aux;
            while((aux = br.readLine()) != null)
                sb.append(aux).append(Graph.NODE_SEPARATOR);
        }catch (Exception e){ }
        return sb.toString();
    }

    public void writeData(int time, int vertex){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(OUT_FILE, true));
            bw.write(""+time);
            bw.write(" "+vertex);
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeReader() throws IOException {
        br.close();
    }
}
