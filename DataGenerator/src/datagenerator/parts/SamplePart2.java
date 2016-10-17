
package datagenerator.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import dao.ProjectDao;
import dao.impl.ProjectDAOImpl;
import entity.Projectdetails;
import exceptions.ReadEntityException;
import job.GenerateDataJob;

public class SamplePart2 {
	@Inject
	public SamplePart2() {

	}

	@PostConstruct
	public void postConstruct(Composite parent) {
		parent.setLayout(null);

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setBounds(84, 165, 315, 123);

		Button btnClick = new Button(composite, SWT.NONE);
		btnClick.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ProjectDao dao = new ProjectDAOImpl();
				GenerateDataJob dataJob = new GenerateDataJob("Generate Data");
				Projectdetails projectdetails = null;
				try {
					projectdetails = dao.getProjectdetailsByid(1);
				} catch (ReadEntityException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dataJob.setProjectdetails(projectdetails);
				dataJob.schedule();
			}
		});
		btnClick.setBounds(114, 63, 75, 25);
		btnClick.setText("Click");

	}
}