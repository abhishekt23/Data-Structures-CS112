package warehouse;

/*
 * Use this class to test to addProduct method.
 */
public class AddProduct {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);
        
        Warehouse whouse = new Warehouse();
        int limit = StdIn.readInt();
        int day;
        int id;
        String name;
        int stock;
        int demand;

        int counter = 0;
        while(counter < limit) {
            day = StdIn.readInt();
            id = StdIn.readInt();
            name = StdIn.readString();
            stock = StdIn.readInt();
            demand = StdIn.readInt();
            whouse.addProduct(id, name, stock, day, demand);
            counter++;
        }

        StdOut.println(whouse);


	// Use this file to test addProduct
    }
}
