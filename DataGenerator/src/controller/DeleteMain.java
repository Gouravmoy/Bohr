package controller;

import common.Master;
import dao.impl.test.CreateColumnPreClass;
import enums.Environment;

public class DeleteMain {

	public static void main(String[] args) {
		Master.INSTANCE.setEnvironment(Environment.TEST);
		CreateColumnPreClass.addColumnToDatabase();

	}

}
