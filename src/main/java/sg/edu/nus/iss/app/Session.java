package sg.edu.nus.iss.app;

import java.io.Console;
import java.util.LinkedList;
import java.util.List;

public class Session {
    
    private static final String LIST = "list";
    private static final String ADD = "add";
    private static final String DELETE = "delete";
    private static final String USERS = "users";
    private static final String SAVE = "save";
    private static final String END = "end";
    private static final String LOGIN = "login";

    private Repository repository;
    private ShoppingCart currCart;
    private List<String> tempList = new LinkedList<>();

    public Session(Repository repo) {
        this.repository = repo;
    }

    public void start() {
        Console cons = System.console();
        boolean stop = false;
        currCart = new ShoppingCart("anon");
        while (!stop) {
            String input = cons.readLine("> ");
            String[] terms = input.trim().split(" ");
            switch(terms[0]){
                case LIST:
                    System.out.printf("Items in %s's" + " shopping cart\n"
                        , currCart.getUsername());
                    if (tempList.size() > 0) {
                        System.out.println("Please save before list");
                    } else {
                        currCart = repository.load(currCart.getUsername());
                        printList(currCart.getContents());
                    }
                    break;
                case ADD:
                    int before = currCart.getContents().size();
                    for (int i = 1; i < terms.length; i++) {
                        currCart.add(terms[i]);
                        System.out.printf("%s added to cart\n", terms[i]);
                    }
                    int addedCount = currCart.getContents().size() - before;
                    System.out.printf("Added %d item(s) to %s's cart\n"
                        , addedCount, currCart.getUsername());
                    break;
                case DELETE:
                    int index = Integer.parseInt(terms[1])-1;
                    String item = currCart.delete(index);
                    repository.save(currCart);
                    System.out.printf("Removed %s from %s's cart\n"
                        , item, currCart.getUsername());
                    break;
                case SAVE:
                    repository.save(currCart);
                    tempList.clear();
                    break;
                case LOGIN:
                    currCart = new ShoppingCart(terms[1]);
                    break;
                case USERS:
                    List<String> allCarts = repository.getShoppingCartsDbFiles();
                    this.printList(allCarts);
                    break;
                case END:
                    stop = true;
                    break;
                default:
                    System.err.printf("Unknown input: %s\n", terms[0]);
            }
            System.out.println("");
        }
        System.out.println("Thank you !\n");
    }

    public void printList(List<String> list) {
        if (list.size() < 1) {
            System.out.println("No record found!");
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            System.out.printf("%d. %s\n", (i+1), list.get(i));
        }
    }

}
