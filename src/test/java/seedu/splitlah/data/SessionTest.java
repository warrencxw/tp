package seedu.splitlah.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.splitlah.command.Command;
import seedu.splitlah.exceptions.InvalidDataException;
import seedu.splitlah.parser.Parser;
import seedu.splitlah.ui.Message;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class SessionTest {

    Manager manager = new Manager();
    Session session;

    private static final int TEST_SESSION = 1;
    private static final int TEST_ACTIVITY_ONE = 1;
    private static final int TEST_ACTIVITY_TWO = 2;
    private static final String CREATE_TEST_SESSION_INPUT =
            "session /create /n Class outing /d 15-02-2022 /pl Alice Bob Charlie";
    private static final String CREATE_TEST_ACTIVITY_INPUT_ONE =
            "activity /create /sid 1 /n Lunch /p Alice /i Alice Bob Charlie /co 15";
    private static final String CREATE_TEST_ACTIVITY_INPUT_TWO =
            "activity /create /sid 1 /n Dinner /p Alice /i Alice Bob Charlie /co 30";

    /**
     * Creates a session that is stored and managed by the Manager object.
     */
    @BeforeEach
    void setUp() {
        Command createSessionCommand = Parser.getCommand(CREATE_TEST_SESSION_INPUT);
        createSessionCommand.run(manager);

        try {
            session = manager.getProfile().getSession(TEST_SESSION);
        } catch (InvalidDataException exception) {
            fail();
        }
    }

    // hasActivity()

    /**
     * Checks if false is returned when hasActivity method is run and no Activity objects exists in the Session object.
     */
    @Test
    void hasActivity_noActivityExists_false() {
        boolean hasActivity = session.hasActivity(TEST_ACTIVITY_ONE);
        assertFalse(hasActivity);
    }

    /**
     * Checks if false is returned when hasActivity method is run and no Activity object with an activity unique
     * identifier matching the provided identifier is found in the Session object.
     */
    @Test
    void hasActivity_activityWithSpecifiedIdDoesNotExist_false() {
        Command createActivityCommand = Parser.getCommand(CREATE_TEST_ACTIVITY_INPUT_ONE);
        createActivityCommand.run(manager);
        boolean hasActivity = session.hasActivity(TEST_ACTIVITY_TWO);
        assertFalse(hasActivity);
    }

    /**
     * Checks if true is returned when hasActivity method is run and Activity objects with activity unique identifiers
     * matching the provided identifiers are found in the Session object.
     */
    @Test
    void hasActivity_activityExists_true() {
        // Pretesting
        boolean hasActivityOne = session.hasActivity(TEST_ACTIVITY_ONE);
        boolean hasActivityTwo = session.hasActivity(TEST_ACTIVITY_TWO);
        assertFalse(hasActivityOne);
        assertFalse(hasActivityTwo);
        
        // 1 activity only
        Command createActivityCommandOne = Parser.getCommand(CREATE_TEST_ACTIVITY_INPUT_ONE);
        createActivityCommandOne.run(manager);
        hasActivityOne = session.hasActivity(TEST_ACTIVITY_ONE);
        assertTrue(hasActivityOne);
        assertFalse(hasActivityTwo);
        
        // 2 activities
        Command createActivityCommandTwo = Parser.getCommand(CREATE_TEST_ACTIVITY_INPUT_TWO);
        createActivityCommandTwo.run(manager);
        hasActivityTwo = session.hasActivity(TEST_ACTIVITY_TWO);
        assertTrue(hasActivityOne);
        assertTrue(hasActivityTwo);
    }
    
    // getActivity()

    /**
     * Checks if an InvalidDataException with the correct message is properly thrown when the getActivity method is
     * called and no Activity objects exists in the Session object.
     */
    @Test
    void getActivity_noActivityExists_InvalidDataExceptionThrown() {
        try {
            Activity activity = session.getActivity(TEST_ACTIVITY_ONE);
            fail();
        } catch (InvalidDataException exception) {
            assertEquals(Message.ERROR_SESSION_EMPTY_ACTIVITY_LIST, exception.getMessage());
        }
    }

    /**
     * Checks if an InvalidDataException with the correct message is properly thrown when the getActivity method is
     * called and no Activity objects with the specified activity unique identifier exists in the Session object.
     */
    @Test
    void getActivity_activityWithSpecifiedIdDoesNotExist_InvalidDataExceptionThrown() {
        Command createActivityCommand = Parser.getCommand(CREATE_TEST_ACTIVITY_INPUT_ONE);
        createActivityCommand.run(manager);

        try {
            Activity activity = session.getActivity(TEST_ACTIVITY_TWO);
            fail();
        } catch (InvalidDataException exception) {
            assertEquals(Message.ERROR_SESSION_ACTIVITY_ID_NOT_IN_LIST, exception.getMessage());
        }
    }

    /**
     * Checks if an Activity object is correctly returned when the getActivity method is
     * called and an Activity with the specified activity unique identifier exists in the Session object.
     */
    @Test
    void getActivity_activityExists_ActivityObjectReturned() {
        Command createActivityCommand = Parser.getCommand(CREATE_TEST_ACTIVITY_INPUT_ONE);
        createActivityCommand.run(manager);

        try {
            Activity activity = session.getActivity(TEST_ACTIVITY_ONE);
            assertEquals(TEST_ACTIVITY_ONE, activity.getActivityId());
        } catch (InvalidDataException exception) {
            fail();
        }
    }

    // removeActivity()

    /**
     * Checks if an InvalidDataException with the correct message is properly thrown when the removeActivity method is
     * called and no Activity objects exists in the Session object.
     */
    @Test
    void removeActivity_noActivityExists_InvalidDataExceptionThrown() {
        try {
            session.removeActivity(TEST_ACTIVITY_ONE);
            fail();
        } catch (InvalidDataException exception) {
            assertEquals(Message.ERROR_SESSION_EMPTY_ACTIVITY_LIST, exception.getMessage());
        }
    }

    /**
     * Checks if an InvalidDataException with the correct message is properly thrown when the removeActivity method is
     * called and no Activity objects with the specified activity unique identifier exists in the Session object.
     */
    @Test
    void removeActivity_activityWithSpecifiedIdDoesNotExist_InvalidDataExceptionThrown() {
        Command createActivityCommand = Parser.getCommand(CREATE_TEST_ACTIVITY_INPUT_ONE);
        createActivityCommand.run(manager);

        try {
            session.removeActivity(TEST_ACTIVITY_TWO);
            fail();
        } catch (InvalidDataException exception) {
            assertEquals(Message.ERROR_SESSION_ACTIVITY_ID_NOT_IN_LIST, exception.getMessage());
        }
    }

    /**
     * Checks if an Activity object and all ActivityCost objects related it are correctly removed from the
     * Session object when the removeActivity method is called and an Activity object with the specified
     * activity unique identifier exists in the Session object.
     */
    @Test
    void removeActivity_activityExists_activityAndActivityCostRemoved() {
        Command createActivityCommandOne = Parser.getCommand(CREATE_TEST_ACTIVITY_INPUT_ONE);
        Command createActivityCommandTwo = Parser.getCommand(CREATE_TEST_ACTIVITY_INPUT_TWO);
        createActivityCommandOne.run(manager);
        createActivityCommandTwo.run(manager);

        try {
            session.removeActivity(TEST_ACTIVITY_ONE);
        } catch (InvalidDataException exception) {
            fail();
        }

        // Check if Activity object still exists
        try {
            session.getActivity(TEST_ACTIVITY_ONE);
            fail();
        } catch (InvalidDataException exception) {
            assertEquals(Message.ERROR_SESSION_ACTIVITY_ID_NOT_IN_LIST, exception.getMessage());
        }

        // Check if ActivityCost objects still exists
        ArrayList<Person> personList = session.getPersonList();
        for (Person person : personList) {
            try {
                person.removeActivityCost(TEST_ACTIVITY_ONE);
            } catch (InvalidDataException exception) {
                String errorMessage = Message.ERROR_PERSON_ACTIVITY_NOT_FOUND + TEST_ACTIVITY_ONE;
                assertEquals(errorMessage, exception.getMessage());
            }
        }
    }

    // getPersonByName()

    /**
     * Checks if an InvalidDataException with the correct message is properly thrown when the getPersonByName method is
     * called and no Person objects with the specified name exists in the Session object.
     */
    @Test
    void getPersonByName_personWithSpecifiedNameDoesNotExist_InvalidDataExceptionThrown() {
        try {
            Person person = session.getPersonByName("David");
            fail();
        } catch (InvalidDataException exception) {
            assertEquals(Message.ERROR_SESSION_PERSON_NOT_IN_LIST, exception.getMessage());
        }
    }

    /**
     * Checks if a Person object is correctly returned when the getPersonByName method is
     * called and a Person object with the specified name exists in the Session object.
     */
    @Test
    void getPersonByName_personWithSpecifiedNameExists_PersonObjectReturned() {
        try {
            Person person = session.getPersonByName("Alice");
            assertEquals("Alice", person.getName());
        } catch (InvalidDataException exception) {
            fail();
        }
    }

    // getPersonListByName()

    /**
     * Checks if an InvalidDataException with the correct message is properly thrown 
     * when the getPersonListByName method is called with a String array object containing a single name
     * and no Person objects with the specified name exists in the Session object.
     */
    @Test
    void getPersonListByName_singleInvalidName_InvalidDataExceptionThrown() {
        try {
            String[] nameList = { "David" };
            ArrayList<Person> personList = session.getPersonListByName(nameList);
            fail();
        } catch (InvalidDataException exception) {
            assertEquals(Message.ERROR_SESSION_PERSON_NOT_IN_LIST, exception.getMessage());
        }
    }

    /**
     * Checks if an InvalidDataException with the correct message is properly thrown 
     * when the getPersonListByName method is called with a String array object containing multiple names
     * and no Person objects were found for one of the specified names in the Session object.
     */
    @Test
    void getPersonListByName_multipleNamesWithOneInvalidName_InvalidDataExceptionThrown() {
        try {
            String[] nameList = { "Alice", "Bob", "David" };
            ArrayList<Person> personList = session.getPersonListByName(nameList);
            fail();
        } catch (InvalidDataException exception) {
            assertEquals(Message.ERROR_SESSION_PERSON_NOT_IN_LIST, exception.getMessage());
        }
    }

    /**
     * Checks if an ArrayList object containing a Person object corresponding to the only specified name is returned 
     * when the getPersonListByName method is called with a String array object containing a single name
     * and a Person object with the only specified name exists in the Session object.
     */
    @Test
    void getPersonListByName_singleValidName_ArrayListReturned() {
        try {
            String[] nameList = { "Alice" };
            ArrayList<Person> personList = session.getPersonListByName(nameList);
            assertEquals("Alice", personList.get(0).getName());
        } catch (InvalidDataException exception) {
            fail();
        }
    }

    /**
     * Checks if an ArrayList object containing Person objects corresponding to the given names is returned 
     * when the getPersonListByName method is called with a String array object containing multiple names
     * and a Person object with a matching name was found in the Session object for each of the given names.
     */
    @Test
    void getPersonListByName_multipleValidNames_ArrayListReturned() {
        try {
            String[] nameList = { "Alice", "Bob", "Charlie" };
            ArrayList<Person> personList = session.getPersonListByName(nameList);
            assertEquals(3, personList.size());
            assertEquals("Alice", personList.get(0).getName());
            assertEquals("Bob", personList.get(1).getName());
            assertEquals("Charlie", personList.get(2).getName());
        } catch (InvalidDataException exception) {
            fail();
        }
    }
}
