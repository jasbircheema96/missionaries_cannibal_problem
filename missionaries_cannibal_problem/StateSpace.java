package missionaries_cannibal_problem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class StateSpace {
	private State startState;
	private List<State> goalStates;
	private boolean[][][] stateMap;

	StateSpace(State startState) {
		this.startState = startState;
		this.stateMap = new boolean[Constants.NUM_MISSIONARIES + 1][Constants.NUM_CANNIBALS
				+ 1][Constants.NUM_RIVER_BANKS];
		this.goalStates = new ArrayList<State>();
	}
	
	public List<State> getGoalStates() {
		return goalStates;
	}

	public State getStartState() {
		return startState;
	}

	public boolean[][][] getStateMap() {
		return stateMap;
	}

	private boolean isStateInMap(State s) {
		return this.stateMap[s.getM()][s.getC()][s.getBoatStatus()];
	}

	private void addStateToMap(State s) {
		this.stateMap[s.getM()][s.getC()][s.getBoatStatus()] = true;
	}

	public void generateStates() {
		// bfs used to generate states (and it should be optimal)
		Queue<State> q = new LinkedList<State>();
		q.add(startState);
		while (!q.isEmpty()) {
			State state = q.poll();
			state.expand();
			addStateToMap(state);
			for (State nextState : state.getNextPossibleStates()) {
				if (!isStateInMap(nextState) && nextState.satisfiesConstraints()) {
					if (nextState.isGoalState())
						this.goalStates.add(nextState);
					q.add(nextState);
				}
			}
		}
	}

	public void writeAllPaths() {
		Writer writer=new Writer();
		writer.write(this.getGoalStates().size()+" solutions exist for this problem \n");
		for (State goalState : this.getGoalStates()) {
			List<State> path = goalState.getPath();
			for (int i = 1; i < path.size(); i++) {
				State state = path.get(i - 1);
				state.writeEvent(path.get(i),writer);
			}
			writer.write("\n");
		}
		writer.close();
	}

	public void print() {
		printBFS(startState);
	}

	private void printBFS(State startState) {
		Queue<State> q = new LinkedList<State>();
		q.add(startState);
		int boatStatus = startState.getBoatStatus();
		while (!q.isEmpty()) {
			State state = q.poll();
			if (boatStatus != state.getBoatStatus()) {
				System.out.println();
				boatStatus = state.getBoatStatus();
			}
			state.print();
			for (State nextState : state.getNextPossibleStates()) {
				q.add(nextState);
			}
		}
	}
}
