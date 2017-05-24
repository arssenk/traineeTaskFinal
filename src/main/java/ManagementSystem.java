import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ManagementSystem {
    private Map<Date, List<ItemSpecification>> dateMap;

    ManagementSystem() {
        this.dateMap = new HashMap<>();
    }

    public static void main(String[] args) {
        ManagementSystem general = new ManagementSystem();
        List<String> lst;
        boolean condition = true;
        while (condition) {
            System.out.print("Enter your command: ");
            Scanner scanner = new Scanner(System.in);
            lst = new ArrayList<String>();
            Collections.addAll(lst, scanner.nextLine().split(" "));
            general.parseInput(lst);
        }
    }

    public Map<Date, List<ItemSpecification>> getDateMap() {
        return dateMap;
    }

    public void parseInput(List<String> input) {
        switch (input.get(0)) {
            case "add":
                addItem(parseInputIntoItem(input.subList(1, input.size())));
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
            default:
                System.out.println(String.format("Input is not defined: %s.", input.get(0)));
        }

    }

    private ItemSpecification parseInputIntoItem(List<String> lst) {
        if (lst.size() >= 3) return new ItemSpecification(lst.get(0), lst.get(1),
                lst.get(2), String.join(" ", lst.subList(3, lst.size())));
        else {
            System.out.println("Number of parameters mismatch in add func.");
            return null;
        }
    }

    private void printItems() {
        ArrayList<Date> dateList = new ArrayList<>(dateMap.keySet());
        Collections.sort(dateList);
        for (Date date : dateList) {
            String value = "";
            for (ItemSpecification item : dateMap.get(date)) value += item.toString() + "\n";
            System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(date) + "\n" + value);
        }
    }

    private void addItem(ItemSpecification item) {
        List<ItemSpecification> myList;
        if (dateMap.get(item.getDate()) != null) {
            myList = dateMap.get(item.getDate());
            myList.add(item);
            dateMap.put(item.getDate(), myList);
        } else {
            myList = new ArrayList<>();
            myList.add(item);
            dateMap.put(item.getDate(), myList);
        }
        printItems();
    }

    void clear(Date date) {
        dateMap.remove(date);
        printItems();
    }
}

