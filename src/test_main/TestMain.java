package test_main;

import main.SimulateRoverMain;
import test_client_module.TestClientModuleMain;

public class TestMain {
		public static void main(String[] args) {
			SimulateRoverMain Chemin=new SimulateRoverMain();
			TestClientModuleMain Client=new TestClientModuleMain();
			
			Chemin.main(args);
			Client.main(args);
		}
}
