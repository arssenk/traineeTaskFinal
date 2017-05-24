import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class that describes Items specification. Comparable by date.
 */
public class ItemSpecification implements Comparable<ItemSpecification> {

    private Date date;
    private double money;
    private String currency;
    private String productName;


    ItemSpecification(String date, String money, String currency, String productName) {

        this.date = parseDate(date);
        this.currency = currency;
        this.productName = productName.replaceAll("[-+.^:,“”\"]","");
        try {
            this.money = Double.parseDouble(money);
        } catch (NumberFormatException exp) {
            System.err.println("Price is incorrect: " + exp.getMessage());
        }
    }

    private Date parseDate(String dateSample) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return formatter.parse(dateSample);
        } catch (ParseException e) {
            System.out.println("Error in parsing date");
            e.printStackTrace();
        }
        return null;
    }

    Date getDate() {
        return date;
    }

    public int compareTo(ItemSpecification o) {
        if (getDate() == null || o.getDate() == null)
            return 0;
        return getDate().compareTo(o.getDate());
    }
    @Override
    public String toString(){
        return String.format("%s %s %s", productName, new DecimalFormat("0.#").format(money), currency);
    }
}
