
package datagenerator.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import dao.DatabaseDao;
import dao.impl.DatabaseDAOImpl;
import entity.Databasedetail;
import enums.DBType;
import exceptions.PersistException;
import job.FirstJob;

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
				FirstJob firstJob = new FirstJob("My Job");
				DatabaseDao databaseDao = new DatabaseDAOImpl();
				Databasedetail databasedetail = new Databasedetail();
				databasedetail.setDescription("jdbc:mysql://localhost:3306/sakila");
				databasedetail.setUsername("root");
				databasedetail.setPassword("root");
				databasedetail.setType(DBType.MYSQL);
				try {
					databaseDao.saveDatabse(databasedetail);
				} catch (PersistException W) {
					// TODO Auto-generated catch block
					W.printStackTrace();
				}
				firstJob.setDatabasedetail(databasedetail);
				System.out.println(firstJob.getResult());
				firstJob.schedule();

			}
		});
		btnClick.setBounds(114, 63, 75, 25);
		btnClick.setText("Click");

	}
}