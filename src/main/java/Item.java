import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

//Static factory pattern. Class providing item configuration and tools to work with item.

class Item {

    private Date itemDate;
    private Double itemMoney;
    private String itemCurrency;
    private String itemName;

    Date getItemDate() {
        return itemDate;
    }

    Double getItemMoney() {
        return itemMoney;
    }
    String getItemCurrency() {
        return itemCurrency;
    }

    static Item createItem(String date, String money, String currency, String productName) {
        Date itemDate = parseDate(date);
        String itemCurrency = parseCurrency(currency);
        String itemName = productName.replaceAll("[-+.^:,“”\"]","");
        Double itemMoney;
        try {
            itemMoney = Double.parseDouble(money);
        } catch (NumberFormatException exp) {
            itemMoney = null;
            System.err.println("Price is incorrect: " + exp.getMessage());
        }
        if (itemDate != null && itemCurrency!= null && itemName != null && itemMoney != null)
            return new Item(itemDate, itemMoney, itemCurrency, itemName);
        return null;
    }


    private Item(Date date, Double money, String currency, String productName) {
        itemDate =date;
        itemMoney = money;
        itemCurrency = currency;
        itemName = productName;
    }
//Checking for validness of date.
    private static Date parseDate(String dateSample) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return formatter.parse(dateSample);
        } catch (ParseException e) {
            System.out.println("Error in parsing date");
            return null;
        }
    }

//Checking for validness of currency.
    private static String parseCurrency(String curr){
        Set<String> currentCurrencies = CurrencyChecker.currencyTypes();
        if (currentCurrencies.contains(curr.toUpperCase())) return curr.toUpperCase();
        System.out.println("Wrong currency");
        return null;
    }

    @Override
    public String toString(){
        return String.format("%s %s %s", itemName, itemMoney, itemCurrency);
    }
}