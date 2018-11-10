package test.controler;

import main.controler.Controler;

public class TestControlerState {
	// Note : UI interaction has been removed for these tests
	public static void main(String[] args) {
		// TODO
		// basic simulation of a navigation between states

		// final ModelInterface model ;
		Controler controler = new Controler();
		try {
			// Is controler in initState when first instanced
			if (initStateTest(controler)) {
				System.out.println("Test 1 PASSED");
			} else {
				System.out.println("test 1 FAILED controler state = " + controler.getCurrentState().stateToString());
			}
			controler.openPlan();
			// Is the plan loaded
			if (loadedPlanStateTest(controler)) {
				System.out.println("Test 2 PASSED");
			} else {
				System.out.println("test 2 FAILED controler state = " + controler.getCurrentState().stateToString());
			}
			// is the delivery loaded
			controler.openDeliveries();
			if (loadedDeliveryStateTest(controler)) {
				System.out.println("Test 3 PASSED");
			} else {
				System.out.println("test 3 FAILED controler state = " + controler.getCurrentState().stateToString());
			}
			// is the parameter window open
			controler.openParameters();
			if (parametersStateTest(controler)) {
				System.out.println("Test 4 PASSED");
			} else {
				System.out.println("test 4 FAILED controler state = " + controler.getCurrentState().stateToString());
			}
			// does the controler go back to loaded delivery upon confirmation
			controler.confirmParameters();
			if (loadedDeliveryStateTest(controler)) {
				System.out.println("Test 5 PASSED");
			} else {
				System.out.println("test 5 FAILED controler state = " + controler.getCurrentState().stateToString());
			}
			// controler calculate planning
			controler.calculatePlanning();
			if (planningStateTest(controler)) {
				System.out.println("Test 6 PASSED");
			} else {
				System.out.println("test 6 FAILED controler state = " + controler.getCurrentState().stateToString());
			}
			// does controler add delivery
			controler.addDelivery();
			if (addDeliveryStateTest(controler)) {
				System.out.println("Test  7 PASSED");
			} else {
				System.out.println("test 7 FAILED controler state = " + controler.getCurrentState().stateToString());
			}
			// controler go back upon confirmation
			controler.confirmNewDelivery();
			if (planningStateTest(controler)) {
				System.out.println("Test 8 PASSED");
			} else {
				System.out.println("test 8 FAILED controler state = " + controler.getCurrentState().stateToString());
			}
			// Tests to check that unacessible state arent accessed
			// TODO : check if syntax is correct
			controler = new Controler();
			// InitState check
			controler.addDelivery();
			if (initStateTest(controler)) {
				System.out.println("Test 9 PASSED");
			} else {
				System.out.println("test 9 FAILED controler state = " + controler.getCurrentState().stateToString());
			}
			controler.calculatePlanning();
			if (initStateTest(controler)) {
				System.out.println("Test 10 PASSED");
			} else {
				System.out.println("test 10 FAILED controler state = " + controler.getCurrentState().stateToString());
			}
			controler.cancelNewDelivery();
			if (initStateTest(controler)) {
				System.out.println("Test 11 PASSED");
			} else {
				System.out.println("test 11 FAILED controler state = " + controler.getCurrentState().stateToString());
			}
			controler.confirmNewDelivery();
			if (initStateTest(controler)) {
				System.out.println("Test 12 PASSED");
			} else {
				System.out.println("test 12 FAILED controler state = " + controler.getCurrentState().stateToString());
			}
			controler.confirmParameters();
			if (initStateTest(controler)) {
				System.out.println("Test 13 PASSED");
			} else {
				System.out.println("test 13 FAILED controler state = " + controler.getCurrentState().stateToString());
			}
			controler.openDeliveries();
			if (initStateTest(controler)) {
				System.out.println("Test 14 PASSED");
			} else {
				System.out.println("test 14 FAILED controler state = " + controler.getCurrentState().stateToString());
			}
			// LoadedPlanState tests
			controler.openPlan();

			controler.calculatePlanning();
			if (loadedPlanStateTest(controler)) {
				System.out.println("Test 15 PASSED");
			} else {
				System.out.println("test 15 FAILED controler state = " + controler.getCurrentState().stateToString());
			}
			controler.cancelNewDelivery();
			if (loadedPlanStateTest(controler)) {
				System.out.println("Test 16 PASSED");
			} else {
				System.out.println("test 16 FAILED controler state = " + controler.getCurrentState().stateToString());
			}
			controler.confirmNewDelivery();
			if (loadedPlanStateTest(controler)) {
				System.out.println("Test 17 PASSED");
			} else {
				System.out.println("test 17 FAILED controler state = " + controler.getCurrentState().stateToString());
			}
			controler.confirmParameters();
			if (loadedPlanStateTest(controler)) {
				System.out.println("Test 18 PASSED");
			} else {
				System.out.println("test 18 FAILED controler state = " + controler.getCurrentState().stateToString());
			}
			controler.addDelivery();
			if (loadedPlanStateTest(controler)) {
				System.out.println("Test 19 PASSED");
			} else {
				System.out.println("test 19 FAILED controler state = " + controler.getCurrentState().stateToString());
			}
			/*
			 * TODO : check if all tests are necessary //loadedDeliveriesState
			 * tests controler.openDeliveries(); controler.calculatePlanning();
			 * controler.cancelNewDelivery(); controler.confirmParameters();
			 * controler.confirmNewDelivery(); controler.openPlan();
			 * 
			 * //planningState tests
			 * 
			 * controler.calculatePlanning();
			 * 
			 * controler.openDeliveries(); controler.openPlan();
			 * controler.cancelNewDelivery(); controler
			 */
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	/**
	 * Test the controler initial state
	 */
	public static boolean initStateTest(Controler controler) {
		return (controler.getCurrentState().stateToString() == "initState");
	}

	/**
	 * Test if controler is in adddeliveryState
	 * 
	 * @param controler
	 * @return
	 */
	public static boolean addDeliveryStateTest(Controler controler) {
		return (controler.getCurrentState().stateToString() == "addDeliveryState");
	}

	/**
	 * test if controler is in loadedDeliveryState
	 * 
	 * @param controler
	 * @return
	 */
	public static boolean loadedDeliveryStateTest(Controler controler) {
		return (controler.getCurrentState().stateToString() == "loadedDeliveryState");
	}

	/**
	 * Test if controler is in loadedPlanState
	 * 
	 * @param controler
	 * @return
	 */
	public static boolean loadedPlanStateTest(Controler controler) {
		return (controler.getCurrentState().stateToString() == "loadedPlanState");
	}

	/**
	 * Test if controler is in ParametersState
	 * 
	 * @param controler
	 * @return
	 */
	public static boolean parametersStateTest(Controler controler) {
		return (controler.getCurrentState().stateToString() == "parametersState");
	}

	/**
	 * Test if controler is in planningState
	 * 
	 * @param controler
	 * @return
	 */
	public static boolean planningStateTest(Controler controler) {
		return (controler.getCurrentState().stateToString() == "planningState");
	}

}
