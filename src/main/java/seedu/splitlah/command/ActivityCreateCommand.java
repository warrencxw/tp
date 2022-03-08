package seedu.splitlah.command;

import seedu.splitlah.data.Manager;

import java.util.Arrays;

/**
 * Represents an ActivityCreateCommand which has a run method that creates an activity.
 */
public class ActivityCreateCommand extends Command {

    public static final String COMMAND_TEXT = "activity /create";
    private static final String COMMAND_FORMAT = "There are 3 different types of formats:\n"
            + "activity /create /sid <SESSIONID> /n <ACTIVITYNAME> /p <PAYER> /i <NAME1 NAME2…> /c <OVERALLCOST> "
            + "[<OPTIONAL ARGS>]\n"
            + "activity /create /sid <SESSIONID> /n <ACTIVITYNAME> /p <PAYER> /i <NAME1 NAME2…> /c <COST1 COST2…> "
            + "[<OPTIONAL ARGS>]\n"
            + "activity /create /sid <SESSIONID> /n <ACTIVITYNAME> /p <PAYER> /c <OVERALLCOST> [<OPTIONAL ARGS>]";

    private int sessionId;
    private String activityName;
    private double cost;
    private String payer;
    private String[] involvedList;
    private double[] costList;
    private int gst;
    private int serviceCharge;

    /**
     * Constructor to create a ActivityCreateCommand object.
     * 
     * @param sessionId Ths id of the session.
     * @param activityName The name of the activity.
     * @param cost The total cost of the activity.
     * @param payer The name of the person who paid for the activity.
     * @param involvedList The names of the persons who are involved in the activity.
     * @param costList The respective costs of each person involved in the activity.
     * @param gst The gst to be included for the cost of the activity.
     * @param serviceCharge The service charge to be included for the cost of the activity.
     */
    public ActivityCreateCommand(int sessionId, String activityName, double cost, String payer, String[] involvedList,
                                 double[] costList, int gst, int serviceCharge) {
        this.sessionId = sessionId;
        this.activityName = activityName;
        this.cost = cost;
        this.payer = payer;
        this.involvedList = involvedList;
        this.costList = costList;
        this.gst = gst;
        this.serviceCharge = serviceCharge;
    }

    @Override
    public void run(Manager manager) {

    }

    private static double updateCost(double cost, int gst, int serviceCharge) {
        cost *= (1 + gst / 100);
        cost *= (1 + serviceCharge / 100);
        return cost;
    }

    private static double[] distributeCostEvenly(double cost, int numberOfPeopleInvolved) {
        double dividedCost = cost / numberOfPeopleInvolved;
        double[] costList = new double[numberOfPeopleInvolved];
        Arrays.fill(costList, dividedCost);
        return costList;
    }

}
