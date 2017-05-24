import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.assertTrue;

/**
 * Test cases for Management System
 */
public class ManagementSystemTests {

    private ManagementSystem general = new ManagementSystem();

    @Test
    public void addItem_1() throws ParseException {
        List<String> lst = new ArrayList<>();
        Collections.addAll(lst, "add 2017-04-25 2 USD Jogurt".split(" "));
        general.parseInput(lst);
        Map<Date, List<ItemSpecification>> result = general.getDateMap();
        List<ItemSpecification> lstResult = new ArrayList<>();
        Map<Date, List<ItemSpecification>> expResult = new HashMap<>();
        lstResult.add(new ItemSpecification("2017-04-25", "2", "USD", "Jogurt"));
        expResult.put(new SimpleDateFormat("yyyy-MM-dd").parse("2017-04-25"), lstResult);
        assertTrue(result.toString().equals(expResult.toString()));
    }

    @Test
    public void addItem_2() throws ParseException {
        List<String> lst = new ArrayList<>();
        Collections.addAll(lst, "add 2017-04-25 2 USD Jogurt".split(" "));
        general.parseInput(lst);
        lst = new ArrayList<>();
        Collections.addAll(lst, "add 2017-04-25 3 EUR “French fries”".split(" "));
        general.parseInput(lst);
        Map<Date, List<ItemSpecification>> result = general.getDateMap();
        List<ItemSpecification> lstResult = new ArrayList<>();
        Map<Date, List<ItemSpecification>> expResult = new HashMap<>();
        lstResult.add(new ItemSpecification("2017-04-25", "2", "USD", "Jogurt"));
        lstResult.add(new ItemSpecification("2017-04-25", "3", "EUR", "“French fries”"));
        expResult.put(new SimpleDateFormat("yyyy-MM-dd").parse("2017-04-25"), lstResult);
        assertTrue(result.toString().equals(expResult.toString()));
    }

}


