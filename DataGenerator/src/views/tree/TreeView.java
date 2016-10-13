package views.tree;

import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wb.swt.SWTResourceManager;

import dao.DatabaseDao;
import dao.impl.DatabaseDAOImpl;
import entity.Databasedetail;
import exceptions.ReadEntityException;
import views.listners.TreeSelectionListner;
import views.util.JTreeUtil;

public class TreeView {

	Composite composite;
	private static JPanel panel_1;
	private static JScrollPane mainScrollPane;

	// Trees
	private static JTree databaseTree;
	// private static JTree projectsTree;

	// Renderer
	static DefaultTreeCellRenderer databaseTreeRenderer;
	static DefaultTreeCellRenderer projectsTreeRenderer;

	// Top Nodes
	static DefaultMutableTreeNode databaseTreeTop;
	static DefaultMutableTreeNode projectsTreeTop;

	// DAO

	DatabaseDao databaseDao;

	@Inject
	public TreeView() {
	}

	@PostConstruct
	public void postConstruct(Composite parent) {
		composite = new Composite(parent, SWT.EMBEDDED);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite.setBounds(0, 0, 277, 465);

		Frame frame = SWT_AWT.new_Frame(composite);
		JPanel panel = new JPanel();
		panel.setBorder(null);
		panel.setBackground(Color.WHITE);
		panel.setBounds(12, 24, 247, 405);
		frame.add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		mainScrollPane = new JScrollPane();

		initilizeTrees(frame);

		try {
			createDatabaseTree();
			createProjectsTree();
		} catch (ReadEntityException e) {
			e.printStackTrace();
		}

		panel_1.add(databaseTree);
		// panel_1.add(projectsTree);

		mainScrollPane.setViewportView(panel_1);
		panel.add(mainScrollPane);

	}

	private void createProjectsTree() throws ReadEntityException {
	}

	private void createDatabaseTree() throws ReadEntityException {
		DefaultMutableTreeNode category = null;

		databaseDao = new DatabaseDAOImpl();
		List<Databasedetail> databaseList = new ArrayList<>();

		databaseList = databaseDao.getAllDatabaseinDB();
		databaseTreeTop.removeAllChildren();
		if (databaseList.isEmpty()) {
			category = new DefaultMutableTreeNode("...");
			databaseTreeTop.add(category);
		} else {
			for (Databasedetail database : databaseList) {
				category = new DefaultMutableTreeNode(database.getConnectionName());
				databaseTreeTop.add(category);
			}
		}
		refreshDatabaseTree();
	}

	private void initilizeTrees(Frame frame) {
		ImageIcon metadataImageIcon = new ImageIcon(TreeView.class.getResource("/resources/images/transform_flip.png"));
		databaseTreeRenderer = new DefaultTreeCellRenderer();
		databaseTreeRenderer.setIcon(new ImageIcon(TreeView.class.getResource("/resources/images/transform_flip.png")));
		databaseTreeRenderer.setLeafIcon(metadataImageIcon);
		databaseTreeTop = new DefaultMutableTreeNode("DATABASES");
		databaseTree = new JTree(databaseTreeTop);
		databaseTree.setAlignmentX(Component.LEFT_ALIGNMENT);
		databaseTree.setAlignmentY(Component.TOP_ALIGNMENT);
		databaseTree.setBounds(322, 252, 104, 16);
		databaseTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		databaseTree.setShowsRootHandles(true);
		databaseTree.addTreeSelectionListener(new TreeSelectionListner(databaseTree));
		databaseTree.setCellRenderer(databaseTreeRenderer);
	}

	private static void refreshDatabaseTree() {
		DefaultTreeModel model = (DefaultTreeModel) databaseTree.getModel();
		model.reload(databaseTreeTop);
		JTreeUtil.colapse(databaseTree);
	}
}
