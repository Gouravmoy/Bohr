package views.renderer;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import entity.Changelog;
import entity.Columnsdetail;
import entity.Constraintsdetail;
import entity.Databasedetail;
import entity.Datasamplemodel;
import entity.Patterndetail;
import entity.Relationsdetail;
import entity.Schemadetail;
import entity.Tabledetail;
import views.tree.TreeView;

public class TreeViewRenderer extends DefaultTreeCellRenderer {

	private static final long serialVersionUID = 1L;

	public TreeViewRenderer() {
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
			boolean leaf, int row, boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
		DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) value;
		ImageIcon columnsIcon = new ImageIcon(TreeView.class.getResource("/resources/images/column.png"));
		ImageIcon schemaIcon = new ImageIcon(TreeView.class.getResource("/resources/images/schema.png"));
		ImageIcon databaseIcon = new ImageIcon(TreeView.class.getResource("/resources/images/database_add.png"));
		ImageIcon tableIcon = new ImageIcon(TreeView.class.getResource("/resources/images/Table.png"));
		ImageIcon defaultIcon = new ImageIcon(TreeView.class.getResource("/resources/images/load.png"));
		ImageIcon homeIcon = new ImageIcon(TreeView.class.getResource("/resources/images/Home.png"));

		ImageIcon constraintsIcon = new ImageIcon(TreeView.class.getResource("/resources/images/Constraint.png"));
		ImageIcon changeLogIcon = new ImageIcon(TreeView.class.getResource("/resources/images/changeLog.png"));
		ImageIcon dataSampleIcon = new ImageIcon(TreeView.class.getResource("/resources/images/sample.png"));
		ImageIcon relationIcon = new ImageIcon(TreeView.class.getResource("/resources/images/relation.png"));
		ImageIcon patternIcon = new ImageIcon(TreeView.class.getResource("/resources/images/pattern.png"));

		if (tree.getModel().getRoot().equals(nodo)) {
			setIcon(homeIcon);
		} else if (nodo.getChildCount() > 0) {
			Object o = ((DefaultMutableTreeNode) value).getUserObject();
			if (o.getClass().equals(Tabledetail.class)) {
				setIcon(tableIcon);
			} else if (o.getClass().equals(Databasedetail.class)) {
				setIcon(databaseIcon);
			} else if (o.getClass().equals(Schemadetail.class)) {
				setIcon(schemaIcon);
			} else if (o.getClass().equals(Columnsdetail.class)) {
				setIcon(columnsIcon);
			} else if (o.getClass().equals(Constraintsdetail.class)) {
				setIcon(constraintsIcon);
			} else if (o.getClass().equals(Changelog.class)) {
				setIcon(changeLogIcon);
			} else if (o.getClass().equals(Datasamplemodel.class)) {
				setIcon(dataSampleIcon);
			} else if (o.getClass().equals(Relationsdetail.class)) {
				setIcon(relationIcon);
			} else if (o.getClass().equals(Patterndetail.class)) {
				setIcon(patternIcon);
			} else
				setIcon(defaultIcon);
		} else {
			if (nodo.isLeaf()) {
				Object o = ((DefaultMutableTreeNode) value).getUserObject();
				if (o.getClass().equals(Tabledetail.class)) {
					setIcon(tableIcon);
				} else if (o.getClass().equals(Databasedetail.class)) {
					setIcon(databaseIcon);
				} else if (o.getClass().equals(Schemadetail.class)) {
					setIcon(schemaIcon);
				} else if (o.getClass().equals(Columnsdetail.class)) {
					setIcon(columnsIcon);
				} else if (o.getClass().equals(Constraintsdetail.class)) {
					setIcon(constraintsIcon);
				} else if (o.getClass().equals(Changelog.class)) {
					setIcon(changeLogIcon);
				} else if (o.getClass().equals(Datasamplemodel.class)) {
					setIcon(dataSampleIcon);
				} else if (o.getClass().equals(Relationsdetail.class)) {
					setIcon(relationIcon);
				} else if (o.getClass().equals(Patterndetail.class)) {
					setIcon(patternIcon);
				} else
					setIcon(defaultIcon);
			}
		}
		return this;
	}

}
