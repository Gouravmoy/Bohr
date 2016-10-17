package jobs.tasks;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.eclipse.e4.ui.model.application.ui.basic.MBasicFactory;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.swt.widgets.Display;

import datagenerator.parts.SamplePart;

public class AddPartTask extends Task {
	String linkedClass;

	public AddPartTask(String linkedClass) {
		super();
		this.linkedClass = linkedClass;
	}

	@Override
	public void execute() throws BuildException {
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				MPart partDy = MBasicFactory.INSTANCE.createCompositePart();
				partDy.setContributionURI(linkedClass);
				MPart oldPart = SamplePart.partService.findPart("datagenerator.part.playground");
				MPartStack parent = (MPartStack) SamplePart.modelService.getContainer(oldPart);
				partDy.setElementId("datagenerator.part.playground");
				partDy.setContainerData("60");
				partDy.setLabel("DATA RUNNER");
				partDy.setCloseable(true);
				parent.getChildren().add(partDy);
				parent.setSelectedElement(partDy);

			}
		});

	}

}
