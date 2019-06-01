package missionaries_cannibal_problem;

import java.util.ArrayList;
import java.util.List;

public class State {

	// number of missionaries on the left side of the river
	private int missionaries;

	// number of cannibals on the left side of the river
	private int cannibals;

	// boatStatus : bank of river where the boat is currently
	private int boatStatus;

	private State parentState;
	// possible states from the current state
	private List<State> nextPossibleStates;

	public State(int missionaries, int cannibals, int boatStatus) {
		super();
		this.missionaries = missionaries;
		this.cannibals = cannibals;
		this.boatStatus = boatStatus;
		this.nextPossibleStates = new ArrayList<State>();
		this.parentState = null;
	}

	public void expand() {

		if (this.getBoatStatus() == Constants.FIRST_BANK) {
			// cannibals or missionaries can only leave
			for (int m = 0; m <= Constants.BOAT_CAPACITY && m <= this.getM(); m++) {
				for (int c = 0; c + m <= Constants.BOAT_CAPACITY && c <= this.getC(); c++) {
					State newState = new State(this.getM() - m, this.getC() - c, this.getNextBoatStatus());
					newState.setParentState(this);
					if (m != 0 || c != 0)
						this.getNextPossibleStates().add(newState);
				}
			}
		} else if (this.getBoatStatus() == Constants.SECOND_BANK) {
			// cannibals or missionaries can only arrive
			for (int m = 0; m <= Constants.BOAT_CAPACITY && m + this.getM() <= Constants.NUM_MISSIONARIES; m++) {
				for (int c = 0; c + m <= Constants.BOAT_CAPACITY && c + this.getC() <= Constants.NUM_CANNIBALS; c++) {
					State newState = new State(this.getM() + m, this.getC() + c, this.getNextBoatStatus());
					newState.setParentState(this);
					if (m != 0 || c != 0)
						this.getNextPossibleStates().add(newState);
				}
			}
		}
	}

	public int getBoatStatus() {
		return boatStatus;
	}

	public int getC() {
		return cannibals;
	}

	public int getM() {
		return missionaries;
	}

	public int getNextBoatStatus() {
		return (boatStatus + 1) % Constants.NUM_RIVER_BANKS;
	}

	public List<State> getNextPossibleStates() {
		return nextPossibleStates;
	}

	public State getParentState() {
		return parentState;
	}

	public List<State> getPath() {
		List<State> path = new ArrayList<State>();
		getPath(this, path);
		return path;
	}

	private void getPath(State state, List<State> path) {
		if (state == null)
			return;
		getPath(state.getParentState(), path);
		path.add(state);
	}
	
	public boolean isGoalState() {
		return this.getC() == 0 && this.getM() == 0;
	}

	public void print() {
		System.out.print(this.getM() + " " + this.getC() + " " + this.getBoatStatus() + "---->");
	}

	public void printEvent(State nextState) {
		if(nextState==null)
			return;
		if (this.boatStatus == Constants.FIRST_BANK) {
			// only leave
			int cannibalsLeft = this.getC() - nextState.getC();
			int missionariesLeft = this.getM() - nextState.getM();
			System.out.printf("Send %d cannibals and %d missionaries to second bank\n", cannibalsLeft, missionariesLeft);

		} else {
			// only arrive
			int cannibalsArrived = nextState.getC() - this.getC();
			int missionariesArrived = nextState.getM() - this.getM();
			System.out.printf("Send %d cannibals and %d missionaries to first bank\n", cannibalsArrived,missionariesArrived);
			
		}
	}

	public boolean satisfiesConstraints() {
		if (this.getC() == this.getM() || this.getM() == 0 || this.getM() == Constants.NUM_MISSIONARIES)
			return true;
		return false;
	}

	public void setParentState(State parentState) {
		this.parentState = parentState;
	}

}