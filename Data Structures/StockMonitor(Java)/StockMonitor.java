
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 * Object that keeps track of stocks and their prices on different days.
 * 
 * @author Patrick T Doyne
 *
 */
public class StockMonitor {  
	/**
	 * This method reads in a file and creates a list of its contents.
	 * @param filename is the file being read in.
	 * @return A list with all the prices from the file.
	 * @throws IOException in case file cannot be found or read.
	 */
	public ThreeTenDLList<Integer> fileToPriceList(String filename) throws IOException {

		Scanner scnr = new Scanner(new File(filename));
		ThreeTenDLList<Integer> list = new ThreeTenDLList<Integer>();
		Integer temp;

		if(!scnr.hasNext()) return list;
		while(scnr.hasNext()) {
			temp = scnr.nextInt();
			list.addLast(temp);
		}

		return list;
	}

	/**
	 * Does the calculation to update prices, spans, and days after a new one is dded.
	 * @param day is the new day being added to the monitor
	 * @param price is the price of the stock on this day
	 * @return the span of the new price.
	 */
	public int stepProcess(int day, int price) {

		StackItem step = new StackItem(day,price);
		StackItem temp = null;
		boolean newMax = false;

		if(day == 0)	{
			this.spanList.addLast(1);
			this.recordStack.push(step);
			this.priceSpanMap.put(price, 1);
			return 1;
		}
		while(!this.recordStack.isEmpty() && price>=this.recordStack.peek().getPrice()) {
			temp=this.recordStack.pop();

			if(temp.getPrice()==price && (day-temp.getDay()) > this.priceSpanMap.get(temp.getPrice())) {
				newMax=true;

			}
		}

		if(this.recordStack.isEmpty()) {
			this.recordStack.push(step);
			this.spanList.addLast(day+1);
			this.priceSpanMap.put(price, day+1);
		}
		else {

			this.spanList.addLast(day-this.recordStack.peek().getDay());
			this.recordStack.push(step);

			if(newMax) {
				this.priceSpanMap.put(price, day-this.recordStack.peek().getDay());
			}
			else {
				this.recordStack.pop();
				if(this.priceSpanMap.get(price)==null || this.priceSpanMap.get(price)<day-this.recordStack.peek().getDay()) {
					this.priceSpanMap.put(price, day-this.recordStack.peek().getDay());
				}
				this.recordStack.push(step);
			}
		}

		return this.priceSpanMap.get(price);


	}

	/**
	 * Finds the max span for a given price.
	 * @param price is the price a max span is being found for.
	 * @return the max span of the passed in price.
	 */
	public int reportMaxSpan(int price){

		return this.priceSpanMap.get(price);

	}
	/**
	 * Creates a String representation of the stock monitors span records.
	 * @return String representation of the stock monitors span records.
	 */
	public String spanRecordToString(){
		StringBuilder ans = new StringBuilder();
		ThreeTenDLList<Integer> usedPrices = new ThreeTenDLList<>();

		usedPrices.addFirst(-999);
		Iterator<Integer> it = this.priceList.iterator();
		Integer temp = null;

		while(it.hasNext()) {

			temp = it.next();
			if(usedPrices.remove(temp)==null) {
				ans.append(temp+":");
				ans.append(this.reportMaxSpan(temp)+" ");
			}
			usedPrices.addLast(temp);

		}

		return ans.toString().trim();

	}
	/**
	 * Test the object in this main if no args are passed in.
	 */
	public static void testMain() {

		StockMonitor x = new StockMonitor();
		x.priceList= new ThreeTenDLList<>();
		x.spanList= new ThreeTenDLList<>();
		x.recordStack = new ThreeTenStack<>();
		x.priceSpanMap = new ThreeTenHashMap<>();

		x.priceList.addLast(39);
		x.priceList.addLast(32);
		x.priceList.addLast(41);
		x.priceList.addLast(25);
		x.priceList.addLast(25);
		x.priceList.addLast(22);
		x.priceList.addLast(40);

		x.stepProcess(0, 39);
		x.stepProcess(1, 32);

		x.stepProcess(2, 41);
		x.stepProcess(3, 25);
		x.stepProcess(4, 25);
		x.stepProcess(5, 22);
		x.stepProcess(6, 40);

		System.out.println(x.priceSpanMap.toString());
		System.out.println(x.spanList.listToString());
		System.out.println(x.spanRecordToString());

		System.out.println("You need to put test code in testMain() to run StockMonitor with no parameters.");
	}

	/**
	 * Represents the object that is filled into our recordStack.	
	 * @author pdoyne
	 *
	 */
	private class StackItem{
		/**
		 * the day for this pair.
		 */
		private int day;
		/**
		 * the price for this pair.
		 */
		private int price;
		/**
		 * Constructor.
		 * @param day the day for this new stack item.
		 * @param price is the price for the new stack item.
		 */
		public StackItem(int day, int price){
			this.day = day;
			this.price = price;
		}
		/**
		 * Gets the day from this item.
		 * @return the day of this stackItem.
		 */
		public int getDay(){ return day;}
		/**
		 * Gets the price from this item.
		 * @return the price of this stackItem.
		 */
		public int getPrice(){return price;}
		/**
		 * Returns a string representation of the stackItem.
		 * @return a String representation of this stackItem.
		 */
		public String toString(){
			return "<" + day + "," + price + ">";
		}

	}

	/**
	 * List of prices.
	 */
	private ThreeTenDLList<Integer> priceList;
	/**
	 * List of pice spans.
	 */
	private ThreeTenDLList<Integer> spanList;

	/**
	 * Stack of current record.
	 */	
	private ThreeTenStack<StackItem> recordStack;

	/**
	 * Map of all pairs.
	 */
	private ThreeTenHashMap<Integer, Integer> priceSpanMap;

	/**
	 * Used for testing when there are args being passed in.
	 * @param args are whats passed in at the console.
	 */
	public static void main(String[] args) {
		//this is not a testing main method, so don't edit this
		//edit testMain() instead!

		if(args.length == 0) {
			testMain();
			return;
		}

		if (args.length == 1 || (args.length==2 && args[1].equals("-d"))){

			try {
				(new StockMonitor()).runProgram(args[0], 
						args.length==2 && args[1].equals("-d"));
			}
			catch(IOException e) {
				System.out.println(e.toString());
				e.printStackTrace();
			}

		}

		else {
			System.out.println("Usage: java StockMonitor [filename] [-d]");
		}

	}

	/**
	 * Runs the entire program.
	 * @param filename is the file being read in.
	 * @param debug determines to string representation.
	 * @throws IOException is used in case file has an issue.
	 */
	public void runProgram(String filename, boolean debug) throws IOException {

		//list of initialized from file
		priceList = fileToPriceList(filename);

		spanList = new ThreeTenDLList<>();

		recordStack = new ThreeTenStack<>();

		priceSpanMap = new ThreeTenHashMap<>();

		System.out.println("Prices: \t" + priceList.listToString());

		int day = 0;
		if(!debug) {
			for (int price: priceList){
				stepProcess(day, price);
				day ++;
			}
		}
		else {
			Scanner s = new Scanner(System.in);
			int span;
			for (int price: priceList){
				System.out.println("\n######### Step " + day + " ###############\n");
				System.out.println("----------Step Output----------");
				span = stepProcess(day, price);
				System.out.format("Day = %d, Price = %d, Span = %d\n", day, price, span);				
				System.out.println("--------------------------------");
				System.out.println("--Record Stack (bottom to top)--");
				System.out.println(recordStack);
				System.out.println("-------------Spans-------------");
				System.out.println(spanList.listToString());
				if(priceList.size() >0) {
					System.out.println("----------Prices Remaining----");
					System.out.println(priceList.listToString(day+1));
				}
				//System.out.println("Current max spans:\t"+ spanRecordToString());
				//uncomment for debugging needs
				System.out.println("\nPress Enter to Continue");
				s.nextLine();
				day++;
			}
			System.out.println("Prices:\t\t" + priceList.listToString());			

		}
		System.out.println("Spans:\t\t" + spanList.listToString());
		System.out.println("Max spans:\t"+ spanRecordToString());

	}


}


