package com.owl.card.game.manager;

import java.util.ArrayList;
import java.util.List;

import com.owl.card.game.worker.GameWorker;

public class GameWorkerManager {
	private List<GameWorker> wrokers = new ArrayList<GameWorker>();

	public List<GameWorker> getWrokers() {
		return wrokers;
	}

	public void setWrokers(List<GameWorker> wrokers) {
		this.wrokers = wrokers;
	}

	public void init() {

	}
}
