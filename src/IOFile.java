import java.io.*;

public class IOFile {

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

    public void closeReader() throws IOException {
        br.close();
    }
}
