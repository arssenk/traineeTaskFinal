import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ManagementSystem {
    private Map<Date, List<Item>> dateMap;
    private boolean condition;

    ManagementSystem() {
        this.dateMap = new HashMap<>();
    }

    public static void main(String[] args) {
        ManagementSystem general = new ManagementSystem();
        List<String> lst;
        general.condition = true;
        while (general.condition) {
            System.out.print("> ");
            Scanner scanner = new Scanner(System.in);
            lst = new ArrayList<>();
            Collections.addAll(lst, scanner.nextLine().split(" "));
            general.parseInput(lst);
        }
    }

    Map<Date, List<Item>> getDateMap() {
        return dateMap;
    }

    //Parsing input.
    void parseInput(List<String> input) {
        switch (input.get(0)) {
            case "add":
                Item item = parseInputIntoItem(input.subList(1, input.size()));
                if (item != null)
                    addItem(item);
                break;
            case "list":
                System.out.println(printItems());
                break;
            case "clear":
                try {
                    if (input.size() > 1) clear((new SimpleDateFormat("yyyy-MM-dd").parse(input.get(1))));
                    else System.out.println("No args in clear");
                } catch (ParseException e) {
                    System.out.println("You've inputted the wrong date");
                }
                break;
            case "total":
                if (input.size() == 2) total(input.get(1));
                else if (input.size() < 2) System.out.println("No currency found");
                else System.out.println("Wrong input. Can't recognize currency: " +
                            input.subList(1, input.size()).toString().replace("[", "").replace("]", ""));
                break;
            case "exit":
                System.out.println("Shutting down");
                condition = false;
                break;
            default:
                System.out.println("Input is not valid.");
        }

    }

    //Creating and returning item out of input. If input is not valid, returns null.
    private Item parseInputIntoItem(List<String> lst) {
        if (lst.size() >= 4) {
            Item newItem = Item.createItem(lst.get(0), lst.get(1),
                    lst.get(2), String.join(" ", lst.subList(3, lst.size())));
            if (newItem != null) {
                return newItem;
            } else return null;
        } else {
            System.out.println("Number of parameters mismatch.");
            return null;
        }
    }

    //Prints all items that was added by now.
    String printItems() {
        ArrayList<Date> dateList = new ArrayList<>(dateMap.keySet());
        Collections.sort(dateList);
        String result = "";
        for (Date date : dateList) {
            String value = "";
            for (Item item : dateMap.get(date)) value += item.toString() + "\n";
            result += new SimpleDateFormat("yyyy-MM-dd").format(date) + "\n" + value;
        }
        return result;
    }

    //Add items to main structure.
    private void addItem(Item item) {
        List<Item> myList;
        if (dateMap.get(item.getItemDate()) != null) {
            myList = dateMap.get(item.getItemDate());
            myList.add(item);
            dateMap.put(item.getItemDate(), myList);
        } else {
            myList = new ArrayList<>();
            myList.add(item);
            dateMap.put(item.getItemDate(), myList);
        }
        System.out.println(printItems());
    }

    //Clear all expenses that were made in particular date.
    void clear(Date date) {
        if (dateMap.containsKey(date)) {
            dateMap.remove(date);
            System.out.println(printItems());
        } else System.out.println("There is no such date in list.");
    }

    //Returns amount of money that were spent in particular currency. Return null if currency is not valid.
    Double total(String curr) {
        CurrencyChecker currencyTools = CurrencyChecker.getInstance();
        Map<String, Double> currentCurrency = currencyTools.getBaseCurrencies();
        Double total = 0.0;
        //Check for currency validness
        if (currentCurrency != null && currentCurrency.keySet().contains(curr.toUpperCase())) {
            for (Date itemDate : dateMap.keySet()) {
                for (Item itm : dateMap.get(itemDate)) {
                    total += itm.getItemMoney() * currentCurrency.get(curr.toUpperCase())
                            / currentCurrency.get(itm.getItemCurrency());
                }
            }
            System.out.printf("%.2f %s\n", total, curr.toUpperCase());
            return Math.round(total * 100d) / 100d;
        }
        else if (currentCurrency == null) System.out.println("Can't get currencies");
        else  System.out.println("Your currency is not valid.");
        return null;
    }
}

