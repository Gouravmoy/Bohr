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
import javax.xml.validation.Schema;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wb.swt.SWTResourceManager;

import dao.DataSampleDao;
import dao.DatabaseDao;
import dao.ProjectDao;
import dao.impl.DataSampleDaoImpl;
import dao.impl.DatabaseDAOImpl;
import dao.impl.ProjectDAOImpl;
import entity.Changelog;
import entity.Columnsdetail;
import entity.Constraintsdetail;
import entity.Databasedetail;
import entity.Datasamplemodel;
import entity.Projectdetails;
import entity.Schemadetail;
import entity.Tabledetail;
import exceptions.ReadEntityException;
import views.listners.TreeSelectionListner;
import views.renderer.TreeViewRenderer;
import views.util.JTreeUtil;

public class TreeView extends DefaultTreeCellRenderer {

	private static final long serialVersionUID = 1L;
	Composite composite;
	private static JPanel panel_1;
	private static JScrollPane mainScrollPane;

	// Trees
	private static JTree databaseTree;
	private static JTree projectsTree;

	// Renderer
	static DefaultTreeCellRenderer databaseTreeRenderer;
	static DefaultTreeCellRenderer projectsTreeRenderer;

	// Top Nodes
	static DefaultMutableTreeNode projectsTreeTop;
	static DefaultMutableTreeNode repoTreeTop;

	// DAO

	DatabaseDao databaseDao;
	DataSampleDao dataSamepleDao;
	ProjectDao projectDao;

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
		panel_1.add(projectsTree);

		mainScrollPane.setViewportView(panel_1);
		panel.add(mainScrollPane);

	}

	private void createProjectsTree() throws ReadEntityException {
		DefaultMutableTreeNode category = null;
		DefaultMutableTreeNode schemaCategory = null;
		DefaultMutableTreeNode changeLogCategory = null;
		DefaultMutableTreeNode dataSampelModelCategory = null;
		projectDao = new ProjectDAOImpl();

		List<Projectdetails> projectdetails = projectDao.getAllProjectdetailsinDB();

		projectsTreeTop.removeAllChildren();

		for (Projectdetails projectdetail : projectdetails) {
			category = new DefaultMutableTreeNode(projectdetail);
			Schemadetail schemadetail = projectdetail.getSchemadetail();
			schemaCategory = new DefaultMutableTreeNode(schemadetail);
			addTableDetails(schemaCategory, schemadetail);
			projectsTreeTop.add(category);
		}

	}

	private void createDatabaseTree() throws ReadEntityException {
		DefaultMutableTreeNode databaseTreeTop = null;
		DefaultMutableTreeNode category = null;
		DefaultMutableTreeNode schemaCategory = null;
		DefaultMutableTreeNode changeLogCategory = null;
		DefaultMutableTreeNode dataSampelModelCategory = null;

		databaseDao = new DatabaseDAOImpl();
		dataSamepleDao = new DataSampleDaoImpl();
		List<Databasedetail> databaseList = new ArrayList<>();

		databaseList = databaseDao.getAllDatabaseinDB();

		repoTreeTop.removeAllChildren();
		databaseTreeTop = new DefaultMutableTreeNode("DATABASES");

		if (databaseList.isEmpty()) {
			category = new DefaultMutableTreeNode("...");
			databaseTreeTop.add(category);
		} else {
			for (Databasedetail database : databaseList) {
				category = new DefaultMutableTreeNode(database);
				for (Schemadetail schemadetail : database.getSchemadetails()) {
					schemaCategory = new DefaultMutableTreeNode(schemadetail);
					addTableDetails(schemaCategory, schemadetail);
					changeLogCategory = new DefaultMutableTreeNode("CHANGE LOGS");
					for (Changelog changelog : database.getChangelogs()) {
						changeLogCategory.add(new DefaultMutableTreeNode(changelog));
					}
					schemaCategory.add(changeLogCategory);
					category.add(schemaCategory);
				}
				databaseTreeTop.add(category);
			}
		}
		repoTreeTop.add(databaseTreeTop);
		List<Datasamplemodel> datasamplemodels = dataSamepleDao.getAllDatasamplemodelinDB();
		dataSampelModelCategory = new DefaultMutableTreeNode("DATA SAMPLE MODELS");
		for (Datasamplemodel datasamplemodel : datasamplemodels) {
			dataSampelModelCategory.add(new DefaultMutableTreeNode(datasamplemodel));
		}
		repoTreeTop.add(dataSampelModelCategory);
		refreshDatabaseTree();
	}

	private void addTableDetails(DefaultMutableTreeNode schemaCategory, Schemadetail schemadetail) {
		DefaultMutableTreeNode tableCategory;
		DefaultMutableTreeNode columnCategory;
		DefaultMutableTreeNode constraintsCategory;
		for (Tabledetail tabledetail : schemadetail.getTabledetails()) {
			tableCategory = new DefaultMutableTreeNode(tabledetail);
			for (Columnsdetail columnsdetail : tabledetail.getColumnsdetails()) {
				columnCategory = new DefaultMutableTreeNode(columnsdetail);
				for (Constraintsdetail constraintsdetail : columnsdetail.getConstraintsdetails1()) {
					constraintsCategory = new DefaultMutableTreeNode(constraintsdetail);
					columnCategory.add(constraintsCategory);
				}
				tableCategory.add(columnCategory);
			}
			schemaCategory.add(tableCategory);
		}
	}

	private void initilizeTrees(Frame frame) {
		TreeViewRenderer renderer = new TreeViewRenderer();
		repoTreeTop = new DefaultMutableTreeNode("REPOSITORY");
		databaseTree = new JTree(repoTreeTop);
		databaseTree.setAlignmentX(Component.LEFT_ALIGNMENT);
		databaseTree.setAlignmentY(Component.TOP_ALIGNMENT);
		databaseTree.setBounds(322, 252, 104, 16);
		databaseTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		databaseTree.setShowsRootHandles(true);
		databaseTree.addTreeSelectionListener(new TreeSelectionListner(databaseTree));
		databaseTree.setCellRenderer(renderer);

		projectsTreeTop = new DefaultMutableTreeNode("PROJECTS");
		projectsTree = new JTree(projectsTreeTop);
		projectsTree.setAlignmentX(Component.LEFT_ALIGNMENT);
		projectsTree.setAlignmentY(Component.TOP_ALIGNMENT);
		projectsTree.setBounds(322, 252, 104, 16);
		projectsTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		projectsTree.setShowsRootHandles(true);
		projectsTree.addTreeSelectionListener(new TreeSelectionListner(projectsTree));
		projectsTree.setCellRenderer(renderer);

	}

	private static void refreshDatabaseTree() {
		DefaultTreeModel model = (DefaultTreeModel) databaseTree.getModel();
		model.reload(repoTreeTop);
		JTreeUtil.colapse(databaseTree);
	}
}
