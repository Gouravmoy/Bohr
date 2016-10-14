package jobs.tasks;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import exceptions.DAOException;
import exceptions.ServiceException;
import views.tree.TreeView;

public class RefrehTreeTask extends Task {

	public RefrehTreeTask() {
		super();
	}

	@Override
	public void execute() throws BuildException {
		try {
			TreeView.queryAndRefresh();
		} catch (DAOException | ServiceException e1) {
			e1.printStackTrace();
		}
	}

}
