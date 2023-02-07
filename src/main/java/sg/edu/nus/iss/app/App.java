package sg.edu.nus.iss.app;

public class App 
{
    public static String defaultDb = "db";
    public static void main( String[] args )
    {
        if (args.length > 0) {
            App.defaultDb = args[0];
            System.out.println(App.defaultDb);
        } else {
            String userDir = System.getProperty("user.dir");
            System.out.println(userDir);
            App.defaultDb = userDir + "/" + defaultDb;
        }
        Repository repo = new Repository(defaultDb);
        Session sess = new Session(repo);
        sess.start();
    }
}
