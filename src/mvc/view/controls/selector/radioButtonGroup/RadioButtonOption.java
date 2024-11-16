package mvc.view.controls.selector.radioButtonGroup;

/**
 * Represents an option for a radio button, consisting of a label and an action command.
 *
 * <p>This record is used to encapsulate the data required to create a radio button in a
 * {@link RadioButtonGroup}. Each option has:
 * <ul>
 *   <li>A label, which is the text displayed on the radio button.</li>
 *   <li>An action command, which is used to identify the button's action in event handling.</li>
 * </ul>
 *
 * <p>Usage:
 * <pre>
 *     RadioButtonOption option = new RadioButtonOption("Label", "COMMAND");
 * </pre>
 *
 * @param label the text displayed on the radio button
 * @param actionCommand the string representing the command associated with the radio button
 * @see RadioButtonGroup
 * @see javax.swing.JRadioButton
 *
 * @author Sergio Vega García
 */
public record RadioButtonOption(String label, String actionCommand) {
}
