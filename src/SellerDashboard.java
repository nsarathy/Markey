import java.util.*;
import java.io.*;


public class SellerDashboard {
	/**
	 * TODO: A method to Display SellerStatistics.txt : Data will include a list of customers with the number of items that they have purchased and a list of products with the number of sales.
	 * TODO: Let seller sort items based on most sold to least sold
	 * TODO: Display stores and products and use Scanner to provide an interface for user to do any of the above
	 */


	//read the sellerStats
	//display for the specific seller user, all the customer usernames that have bought from them,
	//number of items each customer has bought from the seller, list of products the customer has bought,
	//number of sales for each product the seller has sold.

	//method used to display the interface the seller user can interact with and call the other method

	private String userName;

	public static void main (String[] args) {
		SellerDashboard sd = new SellerDashboard("TestUser");
		sd.main();
	}

	public SellerDashboard(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	//read the sellerstats file and return String list
	public List<String> readSellerStats() {
		List<String> fullList = new ArrayList<>();

		try {
			File f = new File("SellerStatistics.txt");
			FileReader fr = new FileReader(f);
			BufferedReader bfr = new BufferedReader(fr);

			if (f == null || !(f.exists())) {
				throw new FileNotFoundException();
			}

			String line = bfr.readLine();

			while (line != null) {
				fullList.add(line);
				line = bfr.readLine();
			}

			return fullList;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	//scan the array from method above and scan to see if seller username
	//matches and then make a new array which will only have the values if
	//seller name matches.

	public List<String> matchedList() {
		String sellerName = userName;
		List<String> fullList = readSellerStats();
		List<String> scannedList = new ArrayList<>();

		for (int i = 0; i < fullList.size(); i++) {
			if (fullList.get(i).contains(sellerName)) {
				scannedList.add(fullList.get(i));
			}
		}
		return scannedList;
	}


	//format without the seller name though because we don't need it anymore once
	//it's in the system.
	public List<String> onlyProducts() {
		List<String> scannedList = matchedList();
		List<String> productList = new ArrayList<>();
		String finalQuantity = "";

		for (int i = 0; i < scannedList.size(); i++) {
			List<String> collectedData = new ArrayList<>(Arrays.asList(scannedList.get(i).split(";")));
			productList.add(collectedData.get(1));
		}
		return productList;
	}

	public List<String> customerAndSales() {
		List<String> scannedList = matchedList();
		List<String> customer = new ArrayList<>();

		for (int i = 0; i < scannedList.size(); i++) {
			List<String> collectedData = new ArrayList<>(Arrays.asList(scannedList.get(i).split(";")));
			List<String> productData = new ArrayList<>(Arrays.asList(scannedList.get(i).split("___")));
			List<String> quantityData = new ArrayList<>(Arrays.asList(scannedList.get(i).split("_")));
			int update = 0;
			int finalConvert = 0;
			String send = "";

			customer.add((collectedData.get(2)));

			for (int q = 0; q < productData.size(); q++) {
				String strNum = quantityData.get(2 + update);
				int convert = Integer.parseInt(strNum);	
				finalConvert += convert;
				send = Integer.toString(finalConvert);
				update += 7;
			}

			customer.add(send);
		}
		return customer;
	}

	//this method will split the given string into the products and the customer
	//we then need to make a display showing the customer and their purchase
	public void displayCustomerList() {
		List<String> customerAndSales = customerAndSales();
		int count = 0;
		//this is for view the stores
		System.out.println("--------------------");
		for (int i = 0; i < customerAndSales.size(); i++) {
			count = i;
			if ((count ^ 1)== i + 1) {
				System.out.println("Customer: " + customerAndSales.get(i));
			} else {
				System.out.println("Items Purchases: " + customerAndSales.get(i));
				System.out.println("--------------------");
				System.out.println();
			}
		}

	}

	public void displaySortOptions(int givenSortID) {
		int sortID = givenSortID;

		System.out.println("Would you like to sort this List?");
		if (sortID == 0) {
			System.out.println("1: High to Low");
			System.out.println("2: Low to High");
			System.out.println("3: Exit");
			System.out.println();
			System.out.print("Enter Here: ");
		}

		if (sortID == 1) {
			System.out.println("1: Low to High");
			System.out.println("2: Revert List");
			System.out.println("3: Exit");
			System.out.println();
			System.out.print("Enter Here: ");
		}

		if (sortID == 2) {
			System.out.println("1: High to Low");
			System.out.println("2: Revert List");
			System.out.println("3: Exit");
			System.out.println();
			System.out.print("Enter Here: ");
		}
	}



	public void main() {
		Scanner input = new Scanner(System.in);
		boolean repeat = true;
		int sortID = 0;

		while (repeat) {
			int answer1 = 0;
			do {
				try {
					System.out.println("*SELLER DASHBOARD*");
					System.out.println();
					System.out.println("1. View Customer List");
					System.out.println("2. View Product Sales List");
					System.out.println("3. Exit Customer Dashboard");
					System.out.println();
					System.out.print("Enter Option Here: ");
					answer1 = input.nextInt();
					input.nextLine();
					System.out.println();

					if (answer1 == 1) {
						break;
					} else if (answer1 == 2) {
						break;
					} else if (answer1 == 3) {
						repeat = false;
						break;
					} else {
						System.out.println("Please enter valid choice!");
						System.out.println();
					}
				} catch (InputMismatchException e) {
					System.out.println();

					System.out.println("Please enter valid choice!");
					System.out.println();
					input.nextLine();
				}
			} while (answer1 != 1 || answer1 != 2 || answer1 != 3);

			if (answer1 == 1) {
				displayCustomerList();
				displaySortOptions(sortID);
				int wantedSort = input.nextInt();
				input.nextLine();
				System.out.println();
			}
		}
	}


}
