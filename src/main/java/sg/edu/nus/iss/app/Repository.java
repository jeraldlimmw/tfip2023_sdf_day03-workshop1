package sg.edu.nus.iss.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class Repository {
    
    private File repository;

    public Repository(String repo) {
        this.repository = new File(repo);
    }

    public List<String> getShoppingCartsDbFiles(){
        List<String> carts = new LinkedList<>();
        for (String n : repository.list()) {
            carts.add(n.replace(".cart", ""));
        } 
        return carts;
    }

    public void save(ShoppingCart cart) {
        String cartDbFileName = cart.getUsername() + ".cart";
        String saveLocation = repository.getPath() + File.separator + cartDbFileName;
        File saveFile = new File(saveLocation);
        OutputStream os = null;

        try {
            System.out.println(saveLocation);
            if(!saveFile.exists()) {
                try{
                    Path path = Paths.get(repository.getPath());
                    Files.createDirectory(path);
                } catch (FileAlreadyExistsException e) {
                    System.err.println("User's directory already exists");
                }
                saveFile.createNewFile();
            }

            os = new FileOutputStream(saveLocation);
            cart.save(os);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ShoppingCart load(String username) {
        String cartName = username + ".cart";
        ShoppingCart cart = new ShoppingCart(username);

        for (File cartFile : repository.listFiles()) {
            if (cartFile.getName().equals(cartName)) {
                InputStream is;
                try{
                    is = new FileInputStream(cartFile);
                    cart.load(is);
                    is.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return cart;
    }
}
