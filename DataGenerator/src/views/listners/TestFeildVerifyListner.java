package views.listners;

import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Text;

public class TestFeildVerifyListner implements VerifyListener {

	public TestFeildVerifyListner() {
	}

	@Override
	public void verifyText(VerifyEvent event) {
		// Assume we allow it
		event.doit = true;

		String text = event.text;
		char[] chars = text.toCharArray();

		// Don't allow if text contains non-digit characters
		for (int i = 0; i < chars.length; i++) {
			if (!Character.isDigit(chars[i])) {
				event.doit = false;
				break;
			}
		}
	}
}
