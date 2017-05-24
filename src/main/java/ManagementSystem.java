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
            System.out.print("Enter your command: ");
            Scanner scanner = new Scanner(System.in);
            lst = new ArrayList<>();
            Collections.addAll(lst, scanner.nextLine().split(" "));
            general.parseInput(lst);
        }
    }

    public Map<Date, List<Item>> getDateMap() {
        return dateMap;
    }

    public void parseInput(List<String> input) {
        switch (input.get(0)) {
            case "add":
                if (parseInputIntoItem(input.subList(1, input.size())) != null) addItem(parseInputIntoItem(input.subList(1, input.size())));
                break;
            case "list":
                printItems();
                break;
            case "clear":
                try {
                    if (input.size() > 1) clear((new SimpleDateFormat("yyyy-MM-dd").parse(input.get(1))));
                    else System.out.println("No args in clear");
                } catch (ParseException e) {
                    System.out.println("You've inputted the wrong date");
                   // e.printStackTrace(); Не знаю чи треба виводити!
                }
                break;
            case "exit":
                System.out.println("Shutting down");
                condition = false;
                break;
            default:
                System.out.println("Input is not valid.");
        }

    }

    private Item parseInputIntoItem(List<String> lst) {
        if (lst.size() >= 4) {
            Item newItem = Item.createItem(lst.get(0), lst.get(1),
                    lst.get(2), String.join(" ", lst.subList(3, lst.size())));
            if (newItem!= null) {
                return newItem;
            }
            else return null;
        }
        else {
            System.out.println("Number of parameters mismatch.");
            return null;
        }
    }

    private void printItems() {
        ArrayList<Date> dateList = new ArrayList<>(dateMap.keySet());
        Collections.sort(dateList);
        for (Date date : dateList) {
            String value = "";
            for (Item item : dateMap.get(date)) value += item.toString() + "\n";
            System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(date) + "\n" + value);
        }
    }

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
        printItems();
    }

    void clear(Date date) {
        dateMap.remove(date);
        printItems();
    }
}

