import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test cases for Management System
 */
public class ManagementSystemTests {
    private ManagementSystem general;

    @Before
    public void initManagementSystem() {
        general = new ManagementSystem();
    }

    private void addBasicInput(){
        List<String> lst = new ArrayList<>();
        Collections.addAll(lst, "add 2017-04-25 2 USD Jogurt".split(" "));
        general.parseInput(lst);
        lst = new ArrayList<>();
        Collections.addAll(lst, "add 2017-04-27 4.75 EUR Beer".split(" "));
        general.parseInput(lst);
    }
    @Test
    public void addItem_1() throws ParseException {
        List<String> lst = new ArrayList<>();
        Collections.addAll(lst, "add 2017-04-25 2 USD Jogurt".split(" "));
        general.parseInput(lst);
        Map<Date, List<Item>> result = general.getDateMap();
        List<Item> lstResult = new ArrayList<>();
        Map<Date, List<Item>> expResult = new HashMap<>();
        lstResult.add(Item.createItem("2017-04-25", "2", "USD", "Jogurt"));
        expResult.put(new SimpleDateFormat("yyyy-MM-dd").parse("2017-04-25"), lstResult);
        assertTrue(result.toString().equals(expResult.toString()));
    }

    @Test
    public void addItem_2() throws ParseException {
        addBasicInput();
        Map<Date, List<Item>> result = general.getDateMap();
        List<Item> lstResult = new ArrayList<>();
        Map<Date, List<Item>> expResult = new HashMap<>();
        lstResult.add(Item.createItem("2017-04-25", "2", "USD", "Jogurt"));
        expResult.put(new SimpleDateFormat("yyyy-MM-dd").parse("2017-04-25"), lstResult);
        lstResult = new ArrayList<>();
        lstResult.add(Item.createItem("2017-04-27", "4.75", "EUR", "Beer"));
        expResult.put(new SimpleDateFormat("yyyy-MM-dd").parse("2017-04-27"), lstResult);
        assertTrue(result.toString().equals(expResult.toString()));
    }

    @Test
    public void listTest(){
        addBasicInput();
        String result = general.printItems();
        String expResult = "2017-04-25\n" +
                "Jogurt 2.0 USD\n" +
                "2017-04-27\n" +
                "Beer 4.75 EUR\n";
        assertTrue(result.equals(expResult));
    }

    @Test
    public void clearTest() throws ParseException {
        addBasicInput();
        general.clear(new SimpleDateFormat("yyyy-MM-dd").parse("2017-04-27"));
        String expResult = "2017-04-25\n" +
                "Jogurt 2.0 USD\n";
        assertTrue(general.printItems().equals(expResult));
    }

    @Test
    public void totalTest(){
        addBasicInput();
        Double result = general.total("USD");
        Double expResult = 2 + 4.75 * CurrencyChecker.getInstance().getBaseCurrencies().get("USD");
        assertEquals((Math.round(expResult * 100d) / 100d), result, 0.0001);

    }

}


