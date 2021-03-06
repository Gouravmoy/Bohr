package jobs.tasks;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.eclipse.e4.ui.model.application.ui.basic.MBasicFactory;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.swt.widgets.Display;

import common.Master;
import datagenerator.parts.SamplePart;

public class AddPartTask extends Task {
	String linkedClass;
	String partName;

	public AddPartTask(String linkedClass) {
		super();
		this.linkedClass = linkedClass;
	}

	@Override
	public void execute() throws BuildException {
		try {
			Display.getDefault().asyncExec(new Runnable() {
				@Override
				public void run() {
					MPart partDy = MBasicFactory.INSTANCE.createCompositePart();
					partDy.setContributionURI(linkedClass);
					MPart oldPart = SamplePart.partService.findPart("datagenerator.part.playground");
					MPartStack parent = (MPartStack) SamplePart.modelService.getContainer(oldPart);
					partDy.setElementId("datagenerator.part.playground");
					partDy.setContainerData("60");
					if (linkedClass.contains("DisplayTableValuesPart")) {
						partDy.setLabel(Master.INSTANCE.getCurrentGeneratedTable().getTableName());
						partDy.setCloseable(true);
					} else {
						partDy.setLabel("Table List");
						partDy.setCloseable(false);
					}
					parent.getChildren().add(partDy);
					parent.setSelectedElement(partDy);

				}
			});
		} catch (Exception exception) {
			log(exception.getMessage());
		}

	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

}
