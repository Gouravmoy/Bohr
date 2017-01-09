package views.listners;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import org.eclipse.swt.widgets.Display;

import common.Master;
import entity.Columnsdetail;
import views.util.MainUtil;
import views.util.PropViewer;

public class TreeSelectionListner implements TreeSelectionListener {

	JTree tree;
	MainUtil mainUtil;

	public TreeSelectionListner(JTree tree) {
		super();
		this.tree = tree;
		mainUtil = new MainUtil();
	}

	@Override
	public void valueChanged(TreeSelectionEvent event) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		if (node == null)
			return;
		if (node.getUserObject() instanceof Columnsdetail) {
			Columnsdetail nodeInfo = (Columnsdetail) node.getUserObject();
			Master.INSTANCE.setKeyValues(mainUtil.getColumnsdetail(nodeInfo));
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					PropViewer.viewer.setInput(Master.INSTANCE.getKeyValues());

				}
			});
		}
	}
}
