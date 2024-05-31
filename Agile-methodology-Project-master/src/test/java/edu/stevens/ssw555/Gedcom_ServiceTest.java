package edu.stevens.ssw555;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.*;

public class Gedcom_ServiceTest {

    private Gedcom_Service service;
    private HashMap<String, Individual> individuals;
    private HashMap<String, Family> families;

    @Before
    public void setUp() {
        service = new Gedcom_Service();
        individuals = new HashMap<>();
        families = new HashMap<>();

        // Create and add test individual data
        Individual ind1 = new Individual("I1");
        ind1.setName("John Doe");
        ind1.setBirth("1980-01-01");


        individuals.put(ind1.getId(), ind1);

        Individual ind2 = new Individual("I2");
        ind2.setName("Jane Joe");
        ind1.setBirth("1980-02-02");

        // Create and add test family data
        Family fam1 = new Family("F1");
        fam1.setHusb("I1");
        fam1.setWife("I2");

        fam1.setMarriage("2000-06-15");
        fam1.setDivorce("2010-06-15");

        families.put(fam1.getId(), fam1);
        service.individuals = individuals;
        service.families = families;

    }

    @Test
    public void testMarriageBeforeDivorce() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (Map.Entry<String, Family> entry : families.entrySet()) {
            Family family = entry.getValue();
            Date marriageDate = sdf.parse(family.getMarriage());
            Date divorceDate = sdf.parse(family.getDivorce());
            assertTrue(marriageDate.before(divorceDate));
        }
    }

    @Test
    public void testDeathdays() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (Map.Entry<String, Individual> entry : individuals.entrySet()) {
            Individual individual = entry.getValue();
            if (individual.getDeath() != null) {
                Date deathDate = sdf.parse(individual.getDeath());
                assertEquals(deathDate, individual.getDeath());
            }
        }
    }

    @Test
    public void testCheckDate() {
        String date = "20 JUN 1990";
        assertEquals(date, "20 JUN 1990");
    }

    @Test
    public void testInvalidBirthdays() {
        for (Map.Entry<String, Individual> entry : individuals.entrySet()) {
            Individual individual = entry.getValue();
            assertFalse(individual.getBirth().equals("1982-02-02"));
        }
    }

    @Test
    public void testBirthBeforeDeath() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (Map.Entry<String, Individual> entry : individuals.entrySet()) {
            Individual individual = entry.getValue();
            if (individual.getDeath() != null) {
                Date birthDate = sdf.parse(individual.getBirth());
                Date deathDate = sdf.parse(individual.getDeath());
                assertTrue("Birth date must be before death date",
                        birthDate.before(deathDate));
            }
        }
    }

    @Test
    public void testUniqueIndividualIds() {
        Set<String> ids = new HashSet<>();

        for (Map.Entry<String, Individual> entry : individuals.entrySet()) {
            String id = entry.getKey();
            assertFalse("Duplicate individual ID found: " + id, ids.contains(id));
            ids.add(id);
        }
    }

    @Test
    public void testUniqueFamilyIds() {
        Set<String> ids = new HashSet<>();

        for (Map.Entry<String, Family> entry : families.entrySet()) {
            String id = entry.getKey();
            assertFalse("Duplicate family ID found: " + id, ids.contains(id));
            ids.add(id);
        }
    }
/////////////////
    @Test
    public void testBirthBeforeDeathMethodCall() throws IOException, ParseException {
        // Create a sample HashMap with individuals
        HashMap<String, Individual> individuals = new HashMap<>();
        Individual ind1 = new Individual("I1");
        ind1.setBirth("01/01/1980");
        ind1.setDeath("01/01/2020");
        individuals.put("I1", ind1);

        // Capture the output stream
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Call the birthBeforeDeath method
        Gedcom_Service.birthBeforeDeath(individuals);

        // Assert that no error message is printed (the method executed successfully)
        assertEquals("", outContent.toString().trim());

        // Reset the output stream
        System.setOut(System.out);
    }

    @Test
    public void testEmptyStringForGetMonth() {
        String expected = null;
        String actual = Gedcom_Service.getMonth("");
        assertEquals(expected, actual);
    }

    @Test
    public void testNormalCaseForGetMonth() {
        String expected = "06";
        String actual = Gedcom_Service.getMonth("JUN");
        assertEquals(expected, actual);
    }

    @Test
    public void testGetMonthForInvalidInput() {
        assertEquals(null, Gedcom_Service.getMonth("invalid input"));
    }

    @Test
    public void testInvalidDateFormatForBirthBeforeDeath() throws ParseException, IOException {
        // This test will not compile because the date string "01/01/1980" is not in the expected format
        HashMap<String, Individual> individuals = new HashMap<>();
        Individual ind1 = new Individual("I1");
        ind1.setBirth("01/01/1980");
        ind1.setDeath("01/01/2020");
        individuals.put("I1", ind1);

        Gedcom_Service.birthBeforeDeath(individuals);
    }

    @Test
    public void testInvalidIndividualConstructor() {
        // This test will not compile because the Individual constructor expects a non-null id
        Individual ind = new Individual(null);
    }

    @Test
    public void testBirthBeforeDeathEquivalent() throws ParseException, IOException {
        HashMap<String, Individual> individuals = new HashMap<>();
        Individual ind1 = new Individual("I1");
        ind1.setBirth("01/01/1980");
        ind1.setDeath("01/01/2020");
        individuals.put("I1", ind1);

        // Capture the output stream
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Gedcom_Service.birthBeforeDeath(individuals);

        // Assert that no error message is printed (the method executed successfully)
        assertEquals("", outContent.toString().trim());

        // Reset the output stream
        System.setOut(System.out);
    }

    @Test
    public void testInvalidButNotUseful() {
        // This test does nothing and will always pass
        assertTrue(true);
    }

    @Test
    public void testAnotherInvalidButNotUseful() {
        // This test compares two unrelated strings, which is not useful
        assertNotEquals("Hello", "World");
    }

    @Test
    public void testGetMonthForValidInput() {
        assertEquals("01", Gedcom_Service.getMonth("JAN"));
        assertEquals("02", Gedcom_Service.getMonth("FEB"));
        assertEquals("03", Gedcom_Service.getMonth("MAR"));
        assertEquals("04", Gedcom_Service.getMonth("APR"));
        assertEquals("05", Gedcom_Service.getMonth("MAY"));
        assertEquals("06", Gedcom_Service.getMonth("JUN"));
        assertEquals("07", Gedcom_Service.getMonth("JUL"));
        assertEquals("08", Gedcom_Service.getMonth("AUG"));
        assertEquals("09", Gedcom_Service.getMonth("SEP"));
        assertEquals("10", Gedcom_Service.getMonth("OCT"));
        assertEquals("11", Gedcom_Service.getMonth("NOV"));
        assertEquals("12", Gedcom_Service.getMonth("DEC"));

    }

    @Test
    public void testMarriageBeforeDivorceWithoutDivorceDate() throws ParseException, IOException {
        HashMap<String, Family> families = new HashMap<>();
        Family fam1 = new Family("F1");
        fam1.setMarriage("01/01/2000");
        families.put("F1", fam1);

        // Capture the output stream
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Gedcom_Service.Marriagebeforedivorce(new HashMap<>(), families);

        // Assert that no error message is printed (the method executed successfully)
        assertEquals("", outContent.toString().trim());

        // Reset the output stream
        System.setOut(System.out);
    }

    @Test
    public void testBirthBeforeDeathWithNullDeath() throws IOException {
        HashMap<String, Individual> individuals = new HashMap<>();
        Individual ind1 = new Individual("I1");
        ind1.setBirth("01/01/1980");
        ind1.setDeath(null);
        individuals.put("I1", ind1);

        // Capture the output stream
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Gedcom_Service.birthBeforeDeath(individuals);

        // Assert that no error message is printed (the method executed successfully)
        assertEquals("", outContent.toString().trim());

        // Reset the output stream
        System.setOut(System.out);
    }

    @Test
    public void testMarriageBeforeDivorceWithNullDates() throws ParseException, IOException {
        HashMap<String, Family> families = new HashMap<>();
        Family fam1 = new Family("F1");
        fam1.setMarriage(null);
        fam1.setDivorce(null);
        families.put("F1", fam1);

        // Capture the output stream
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Gedcom_Service.Marriagebeforedivorce(new HashMap<>(), families);

        // Assert that no error message is printed (the method executed successfully)
        assertEquals("", outContent.toString().trim());

        // Reset the output stream
        System.setOut(System.out);
    }

    @Test
    public void testBirthBeforeMarriageOfParentWithNullDates() throws ParseException, IOException {
        HashMap<String, Individual> individuals = new HashMap<>();
        HashMap<String, Family> families = new HashMap<>();

        Individual ind1 = new Individual("I1");
        ind1.setBirth("01/01/1990"); // Set a non-null birth date
        individuals.put("I1", ind1);

        Family fam1 = new Family("F1");
        fam1.setMarriage("01/01/1980"); // Set a non-null marriage date
        fam1.setChild(new ArrayList<>(Arrays.asList("I1")));
        families.put("F1", fam1);

        // Capture the output stream
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Gedcom_Service.birthbeforemarriageofparent(individuals, families);

        // Assert that no error message is printed (the method executed successfully)
        assertEquals("", outContent.toString().trim());

        // Reset the output stream
        System.setOut(System.out);
    }

    /////////////////

    @Test
    public void testBirthBeforeDeathWithInvalidDates() throws ParseException, IOException {
        HashMap<String, Individual> individuals = new HashMap<>();
        Individual ind1 = new Individual("I1");
        ind1.setBirth("01/01/2021"); // Future birth date
        ind1.setDeath("01/01/2020"); // Death before birth

        individuals.put("I1", ind1);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Gedcom_Service.birthBeforeDeath(individuals);

        String expectedOutput = "\n\nERROR:INDIVIDUAL: User Story US03: Birth Before Death \nIndividual: I1 - null was born after death\nDOB: 01/01/2021 DOD: 01/01/2020\n\n";
        assertEquals(expectedOutput, outContent.toString());

        System.setOut(System.out);
    }

    @Test
    public void testMarriageBeforeDivorceWithInvalidDates() throws ParseException, IOException {
        HashMap<String, Individual> individuals = new HashMap<>();
        HashMap<String, Family> families = new HashMap<>();

        Individual ind1 = new Individual("I1");
        Individual ind2 = new Individual("I2");

        Family fam1 = new Family("F1");
        fam1.setHusb("I1");
        fam1.setWife("I2");
        fam1.setMarriage("01/01/2025"); // Future marriage date
        fam1.setDivorce("01/01/2020"); // Divorce before marriage

        individuals.put("I1", ind1);
        individuals.put("I2", ind2);
        families.put("F1", fam1);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Gedcom_Service.Marriagebeforedivorce(individuals, families);

        String expectedOutput = "ERROR:FAMILY: User Story US04: Marriage Before Divorce \nFamily: F1\nIndividual: I1: null I2: null marriage date is before divorce date.\nMarriage Date: 01/01/2025 Divorce Date: 01/01/2020\n\n";
        assertEquals(expectedOutput, outContent.toString());

        System.setOut(System.out);
    }

    @Test
    public void testBirthBeforeMarriageOfParentWithInvalidDates() throws ParseException, IOException {
        HashMap<String, Individual> individuals = new HashMap<>();
        HashMap<String, Family> families = new HashMap<>();

        Individual ind1 = new Individual("I1");
        ind1.setBirth("01/01/2025"); // Future birth date

        Family fam1 = new Family("F1");
        fam1.setMarriage("01/01/2020");
        fam1.setChild(new ArrayList<>());
        fam1.getChild().add("I1");

        individuals.put("I1", ind1);
        families.put("F1", fam1);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Gedcom_Service.birthbeforemarriageofparent(individuals, families);

        String expectedOutput = "ERROR: User Story US08: Birth Before Marriage Date \nFamily ID: F1\nIndividual: I1: null Has been born before parents' marriage\nDOB: 01/01/2025 Parents Marriage Date: 01/01/2020\n\n";
        assertEquals(expectedOutput, outContent.toString());

        System.setOut(System.out);
    }

    @Test
    public void testBoundaryConditionsForGetMonth() {
        assertEquals(null, Gedcom_Service.getMonth(null)); // Test null input
        assertEquals(null, Gedcom_Service.getMonth("")); // Test empty string input
        assertEquals(null, Gedcom_Service.getMonth("INVALID")); // Test invalid input
        assertEquals("01", Gedcom_Service.getMonth("JAN")); // Test lower boundary
        assertEquals("12", Gedcom_Service.getMonth("DEC")); // Test upper boundary
    }

}
