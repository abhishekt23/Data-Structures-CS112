package warehouse;

public class Restock {
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
        int prodId;
        int amt;

        String word;
        int counter = 0;
        while(counter < limit) {

            word = StdIn.readString();

            if(word.equals("add")) {
                day = StdIn.readInt();
                id = StdIn.readInt();
                name = StdIn.readString();
                stock = StdIn.readInt();
                demand = StdIn.readInt();
                whouse.addProduct(id, name, stock, day, demand);
                
            }
            if(word.equals("restock")) {
                prodId = StdIn.readInt();
                amt = StdIn.readInt();
                whouse.restockProduct(prodId, amt);
            }
            counter++;
        }

        StdOut.println(whouse);


	// Uset his file to test restock
    }
}
