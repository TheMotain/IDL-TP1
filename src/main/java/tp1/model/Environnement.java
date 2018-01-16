package tp1.model;

import java.util.Observable;

import tp1.Logger;

public class Environnement extends Observable {
	private Agent[][] environnement;
	
	private boolean torus;
	
	private int tick;
	
	public Environnement(int x, int y, boolean torus) {
		this.environnement = new Agent[x][y];
		this.torus = torus;
		this.tick = 0;
	}

	public void putAgent(Agent agent) {
		environnement[agent.getPosX()][agent.getPosY()] = agent;
	}

	public boolean isEmptyCellule(int x, int y) {
		if((x < 0 || y < 0 || x >= ConstantParams.getGridSizeX() || y >= ConstantParams.getGridSizeY()) && !torus) {
			return false;
		}
		else if (torus) {
			if(x < 0) {
				x = ConstantParams.getGridSizeX() - 1;
			}
			if(y < 0) {
				y = ConstantParams.getGridSizeY() - 1;
			}
			if(x >= ConstantParams.getGridSizeX()) {
				x = 0;
			}
			if(y >= ConstantParams.getGridSizeY()) {
				y = 0;
			}
		}
		return null == environnement[x][y];
	}
	
	public void applyTransition(Agent agent) {
		Logger.log(String.format("Apply transition agent %s : [%s,%s] -> [%s,%s]", agent.getId(),agent.getPosX(),agent.getNewPosY(),agent.getNewPosX(),agent.getNewPosY()));
		
		environnement[agent.getPosX()][agent.getPosY()] = null;
		agent.update();
		environnement[agent.getPosX()][agent.getPosY()] = agent;
		tick++;
		setChanged();
		notifyObservers();
		
		Logger.log(this);
	}
	
	public Agent[][] getEnvironnement() {
		return environnement;
	}
	
	public int getTick() {
		return tick;
	}

	@Override
	public String toString() {
		String output = "";
		int scale = String.valueOf(ConstantParams.getNumberOfParticles()).length();
		for(int i = 0; i < environnement.length; i++) {
			for(int j = 0; j < environnement[i].length; j++) {
				output += "|";
				if(environnement[i][j] != null) {
					output += environnement[i][j].getId();
				}
				for(int k = 0; k < (environnement[i][j] != null ? scale - String.valueOf(environnement[i][j].getId()).length() : scale); k++) {
					output += " ";
				}
			}
			output += "|\n";
		}
		return output;
	}
	
}
