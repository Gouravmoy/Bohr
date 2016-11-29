package views.listners;

import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Text;

public class TestFeildVerifyListner implements VerifyListener {

	public TestFeildVerifyListner() {
	}

	@Override
	public void verifyText(VerifyEvent e) {

		Text text = (Text) e.getSource();

		// get old text and create new text by using the
		// VerifyEvent.text
		final String oldS = text.getText();
		// String newS = oldS.substring(0, e.start) + e.text +
		// oldS.substring(e.end);
		String newS = oldS;
		if (newS == null) {
			e.doit = true;
		} else if (newS.length() == 0) {
			e.doit = true;
		} else {
			boolean isFloat = true;
			try {
				Float.parseFloat(newS);
			} catch (NumberFormatException ex) {
				throw new NumberFormatException();
			}

			System.out.println(newS);

			if (!isFloat)
				e.doit = false;
		}

	}
}
