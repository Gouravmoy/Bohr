package views.tree;

import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.swing.BoxLayout;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.wb.swt.SWTResourceManager;

import ch.qos.logback.classic.Logger;
import controller.MainController;
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
import entity.Patterndetail;
import entity.PreDefinedModels;
import entity.Projectdetails;
import entity.Relationsdetail;
import entity.Schemadetail;
import entity.Tabledetail;
import exceptions.DAOException;
import exceptions.ReadEntityException;
import exceptions.ServiceException;
import service.ModelService;
import service.PatternService;
import service.RelationService;
import service.impl.ModelServiceImpl;
import service.impl.PatternServiceImpl;
import service.impl.RelationServiceImpl;
import views.dialog.DataModelDialog;
import views.dialog.RelationDialog;
import views.listners.MousePopupListner;
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

	// Deafult Nodes

	static DefaultMutableTreeNode relationsNodes = null;
	static DefaultMutableTreeNode patternsNodes = null;
	static DefaultMutableTreeNode dataModels = null;

	// DAO

	static DatabaseDao databaseDao;
	static DataSampleDao dataSamepleDao;
	static ProjectDao projectDao;

	// Service

	public static RelationService relationService;
	public static PatternService patternService;
	public static ModelService modelService;

	// Menu
	static JPopupMenu popup = new JPopupMenu();

	// Menu Items
	JMenuItem refressAll;
	JMenuItem edit;
	JMenuItem createRelations;
	JMenuItem createPatterns;
	JMenuItem createDataModel;

	// Logger
	static Logger logger = MainController.getLogger(TreeView.class);

	@Inject
	public TreeView() {
	}

	@PostConstruct
	public void postConstruct(Composite parent) {
		logger.error("Inside TreeView12345");
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

		assignMenuItems();
		popup.add(createRelations);
		// popup.add(createPatterns);
		 popup.add(createDataModel);
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

	private void assignMenuItems() {
		createRelations = new JMenuItem("Create a Relation");
		createRelations.setActionCommand("createRelation");
		createRelations.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				JTree currentSelectedTree = null;
				DefaultMutableTreeNode node = null;
				System.out.println("Here");
				Component selectedComponent = MousePopupListner.currentComponent;
				if (selectedComponent instanceof JTree) {
					currentSelectedTree = (JTree) selectedComponent;
					node = (DefaultMutableTreeNode) currentSelectedTree.getLastSelectedPathComponent();
				}
				if (node == null)
					return;
				openEditWizard(node);
			}

			private void openEditWizard(DefaultMutableTreeNode node) {
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						Dialog dialog = new RelationDialog(composite.getShell(), (Columnsdetail) node.getUserObject());
						dialog.open();
					}
				});

			}
		});
		createDataModel = new JMenuItem("Create a Data Model");
		createDataModel.setActionCommand("createDataModel");
		createDataModel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				JTree currentSelectedTree = null;
				DefaultMutableTreeNode node = null;
				System.out.println("Here");
				Component selectedComponent = MousePopupListner.currentComponent;
				if (selectedComponent instanceof JTree) {
					currentSelectedTree = (JTree) selectedComponent;
					node = (DefaultMutableTreeNode) currentSelectedTree.getLastSelectedPathComponent();
				}
				if (node == null)
					return;
				openEditWizard(node);
			}

			private void openEditWizard(DefaultMutableTreeNode node) {
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						Dialog dialog = new DataModelDialog(composite.getShell(), (Columnsdetail) node.getUserObject());
						dialog.open();
					}
				});
			}
		});

	}

	public static void queryAndRefresh() throws DAOException, ServiceException {
		createDatabaseTree();
		createProjectsTree();
		collapseAll();
	}

	private static void collapseAll() {
		JTreeUtil.colapse(databaseTree);
		JTreeUtil.colapse(projectsTree);
	}

	private static void createProjectsTree() throws ReadEntityException {
		DefaultMutableTreeNode category = null;
		DefaultMutableTreeNode schemaCategory = null;
		projectDao = new ProjectDAOImpl();

		List<Projectdetails> projectdetails = projectDao.getAllProjectdetailsinDB();

		projectsTreeTop.removeAllChildren();

		System.out.println("Inside Project");
		for (Projectdetails projectdetail : projectdetails) {
			relationsNodes = new DefaultMutableTreeNode("RELATIONS");
			patternsNodes = new DefaultMutableTreeNode("PATTERNS");
			dataModels = new DefaultMutableTreeNode("DATA MODELS");
			category = new DefaultMutableTreeNode(projectdetail);
			Schemadetail schemadetail = projectdetail.getSchemadetail();
			schemaCategory = new DefaultMutableTreeNode(schemadetail);
			addTableDetails(schemaCategory, schemadetail, true, projectdetail.getIdproject());
			category.add(schemaCategory);
			category.add(relationsNodes);
			category.add(patternsNodes);
			category.add(dataModels);
			projectsTreeTop.add(category);
			refreshProjectTree();
			logger.debug("Exit Tree");
		}

	}

	private static void createDatabaseTree() throws ReadEntityException {
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
					addTableDetails(schemaCategory, schemadetail, false, 0);
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

	private static void addTableDetails(DefaultMutableTreeNode schemaCategory, Schemadetail schemadetail,
			boolean addRelation, int Id) {
		DefaultMutableTreeNode tableCategory;
		DefaultMutableTreeNode columnCategory;
		DefaultMutableTreeNode constraintsCategory;
		relationService = new RelationServiceImpl();
		patternService = new PatternServiceImpl();
		modelService = new ModelServiceImpl();

		List<Tabledetail> tabledetails = new ArrayList<>();
		tabledetails.addAll(schemadetail.getTabledetails());
		sortList(tabledetails);
		// int count = 0;
		for (Tabledetail tabledetail : tabledetails) {
			// System.out.println(tabledetail.getTableName());
			tableCategory = new DefaultMutableTreeNode(tabledetail);
			for (Columnsdetail columnsdetail : tabledetail.getColumnsdetails()) {
				// System.out.println((count++)+"->"+tabledetail.getTableName()+"->"+columnsdetail.getName());
				columnCategory = new DefaultMutableTreeNode(columnsdetail);
				if (addRelation) {
					getRelationsAndPatterns(columnCategory, columnsdetail, Id);
					getModels(columnCategory, columnsdetail, Id);
				}
				for (Constraintsdetail constraintsdetail : columnsdetail.getConstraintsdetails1()) {
					constraintsCategory = new DefaultMutableTreeNode(constraintsdetail);
					columnCategory.add(constraintsCategory);
				}
				tableCategory.add(columnCategory);
			}
			schemaCategory.add(tableCategory);
		}
	}

	private static void getModels(DefaultMutableTreeNode columnCategory, Columnsdetail columnsdetail, int id) {
		// System.out.println("Inside getModels");
		if(columnsdetail.getName().equals("title")){
			System.out.println("Debug");
		}
		DefaultMutableTreeNode modelCategory;
		Datasamplemodel datasamplemodel = modelService
				.getDataSampleModelByColumnId(columnsdetail.getIdcolumnsdetails());
		PreDefinedModels preDefinedModels = modelService
				.getPreDefinedmodelsByColumnId(columnsdetail.getIdcolumnsdetails());
		modelCategory = new DefaultMutableTreeNode("DATA MODELS");
		if (datasamplemodel != null) {
			modelCategory.add(new DefaultMutableTreeNode(datasamplemodel));
			dataModels.add(new DefaultMutableTreeNode(datasamplemodel));
		}
		if (preDefinedModels != null) {
			modelCategory.add(new DefaultMutableTreeNode(preDefinedModels));
			dataModels.add(new DefaultMutableTreeNode(preDefinedModels));
		}
		columnCategory.add(modelCategory);
		// System.out.println("Exit getModels");
	}

	private static void getRelationsAndPatterns(DefaultMutableTreeNode columnCategory, Columnsdetail columnsdetail,
			int projectId) {
		DefaultMutableTreeNode relationCategory;
		DefaultMutableTreeNode patternCategory;
		Patterndetail patterndetail = null;
		Relationsdetail relationsdetail = relationService.getRelationForColumnId(columnsdetail.getIdcolumnsdetails(),
				projectId);

		if (columnsdetail.getPatterndetail() != null
				&& columnsdetail.getPatterndetail().getProjectdetail().getIdproject() == projectId) {
			patterndetail = columnsdetail.getPatterndetail();
		}
		relationCategory = new DefaultMutableTreeNode("RELATIONS");
		if (relationsdetail != null) {
			relationCategory.add(new DefaultMutableTreeNode(relationsdetail));
			relationsNodes.add(new DefaultMutableTreeNode(relationsdetail));
		}
		columnCategory.add(relationCategory);
		patternCategory = new DefaultMutableTreeNode("PATTERNS");
		if (patterndetail != null) {
			patternCategory.add(new DefaultMutableTreeNode(patterndetail));
			patternsNodes.add(new DefaultMutableTreeNode(patterndetail));
		}
		columnCategory.add(patternCategory);
	}

	private static void sortList(List<Tabledetail> tabledetails) {
		/*SortTableTask sortTableTask = new SortTableTask(tabledetails);
		sortTableTask.execute();
		tabledetails.clear();
		tabledetails.addAll(Master.INSTANCE.getSortedTableInLoadOrder());*/
		if (tabledetails.size() > 0) {
			Collections.sort(tabledetails, new Comparator<Tabledetail>() {
				@Override
				public int compare(final Tabledetail object1, final Tabledetail object2) {
					return object1.getTableName().compareTo(object2.getTableName());
				}
			});
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
		projectsTree.addMouseListener(new MousePopupListner(popup, panel_1));

	}

	private static void refreshDatabaseTree() {
		DefaultTreeModel model = (DefaultTreeModel) databaseTree.getModel();
		model.reload(repoTreeTop);
		JTreeUtil.colapse(databaseTree);
	}

	private static void refreshProjectTree() {
		DefaultTreeModel model = (DefaultTreeModel) projectsTree.getModel();
		model.reload(projectsTreeTop);
		JTreeUtil.colapse(projectsTree);
	}
}
