package missionaries_cannibal_problem;

public class App {
	
	public static void main(String[] args) {
		State startState=new State(Constants.NUM_MISSIONARIES,Constants.NUM_CANNIBALS,Constants.FIRST_BANK);
		StateSpace stateSpace=new StateSpace(startState);
		stateSpace.generateStates();
		stateSpace.writeAllPaths();
	}
}