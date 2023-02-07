package sg.edu.nus.iss.app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.List;

public class ShoppingCart {
    
    private List<String> contents = new LinkedList<>();
    private String username;

    public ShoppingCart(String name) {
        this.username = name;
    }

    public List<String> getContents() {
        return contents;
    }

    public void setContents(List<String> contents) {
        this.contents = contents;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void add(String item) {
        if(contents.contains(item)) {
            return;
        }
        contents.add(item);
    }
    
    public String delete(int index) {
        if (index < contents.size()) {
            return contents.remove(index);
        }
        return "nothing to delete";
    }

    public void load(InputStream is) throws IOException {
        String item;
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        while ((item = br.readLine()) != null) {
            contents.add(item);
        }
        br.close();
        isr.close();
    }

    public void save(OutputStream os) throws IOException {
        OutputStreamWriter osw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(osw);
        for (String item : contents) {
            bw.write(item + "\n");
        }

        bw.flush(); // clear buffer because writing takes up memory
        osw.flush();
        bw.close();
        osw.close();
        
    }
}
