package actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;


public class EnterKeyAction extends AbstractAction {
	private static final long serialVersionUID = 4101230456461445256L;
	private final JButton button;

    public EnterKeyAction(JButton button) {
        this.button = button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
   
    	button.doClick();
    }

}
