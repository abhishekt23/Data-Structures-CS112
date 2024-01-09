package warehouse;

/*
 * Use this class to put it all together.
 */ 
public class Everything {
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

        int prodIdR;
        int prodIdD;
        int prodIdP;
        int amt;
        int currDay;
        int amtPur;

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
                prodIdR = StdIn.readInt();
                amt = StdIn.readInt();
                whouse.restockProduct(prodIdR, amt);
            }
            if(word.equals("delete")) {
                prodIdD = StdIn.readInt();
                whouse.deleteProduct(prodIdD);
            }
            if(word.equals("purchase")) {
                currDay = StdIn.readInt();
                prodIdP = StdIn.readInt();
                amtPur = StdIn.readInt();
                whouse.purchaseProduct(prodIdP, currDay, amtPur);
            }

            counter++;
        }

        StdOut.println(whouse);

	// Use this file to test all methods
    }
}
