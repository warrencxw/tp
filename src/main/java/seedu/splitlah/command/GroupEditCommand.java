package seedu.splitlah.command;

import seedu.splitlah.data.Manager;
import seedu.splitlah.data.PersonList;
import seedu.splitlah.data.Group;
import seedu.splitlah.exceptions.InvalidDataException;
import seedu.splitlah.ui.Message;
import seedu.splitlah.ui.TextUI;

import java.util.logging.Level;

/**
 * Represents a command object that edits a Group object.
 *
 * @author Tianle
 */
public class GroupEditCommand extends Command {

    private static final String COMMAND_SUCCESS = "The group was edited successfully.\n";

    private final String groupName;
    private final String[] involvedList;
    private final int groupId;

    /**
     * Initializes a GroupEditCommand object.
     *
     * @param involvedList  An array of String objects that represents the involved persons for the group.
     * @param groupName     A String object that represents the group name.
     * @param groupId       An integer that represents the group unique identifier for the group to be edited.
     */
    public GroupEditCommand(String[] involvedList, String groupName, int groupId) {
        assert groupId > 0 : Message.ASSERT_GROUPEDIT_GROUP_ID_INVALID;
        this.involvedList = involvedList;
        this.groupName = groupName;
        this.groupId = groupId;
    }

    /**
     * Runs the command with the group identifier as provided by the user input.
     *
     * @param manager A Manager object that manages the TextUI, Profile and Storage objects.
     */
    @Override
    public void run(Manager manager) {
        TextUI ui = manager.getUi();
        Group group;
        try {
            group = manager.getProfile().getGroup(groupId);
        } catch (InvalidDataException invalidDataException) {
            ui.printlnMessageWithDivider(invalidDataException.getMessage());
            Manager.getLogger().log(Level.FINEST, Message.LOGGER_PROFILE_GROUP_NOT_IN_LIST);
            return;
        }

        if (involvedList != null) {
            boolean hasDuplicates = PersonList.hasNameDuplicates(involvedList);
            if (hasDuplicates) {
                ui.printlnMessage(Message.ERROR_PERSONLIST_DUPLICATE_NAME_IN_GROUP);
                Manager.getLogger().log(Level.FINEST, Message.LOGGER_PERSONLIST_NAME_DUPLICATE_EXISTS_IN_EDITGROUP);
                return;
            }
            PersonList newPersonList = new PersonList();
            newPersonList.convertToPersonList(involvedList);
            group.setPersonList(newPersonList);
        }
        if (groupName != null) {
            group.setGroupName(groupName);
        }
        manager.saveProfile();
        ui.printlnMessageWithDivider(COMMAND_SUCCESS + "\n" + group);
    }
}