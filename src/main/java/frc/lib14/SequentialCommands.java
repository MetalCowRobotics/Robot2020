package frc.lib14;

import java.util.ArrayList;

public class SequentialCommands implements MCRCommand {
	private ArrayList<MCRCommand> sequentialCommands = new ArrayList<MCRCommand>();
	
	public SequentialCommands(MCRCommand... commands) {
		for(MCRCommand command : commands) {
			sequentialCommands.add(command);
		}
	}

	public SequentialCommands(ArrayList<MCRCommand> commands) {
		sequentialCommands = commands;
	}
	
	@Override
	public void run() {
		for (MCRCommand command : sequentialCommands) {
			if (!command.isFinished()) {
				command.run();
				return;
			} else {
				// sequentialCommands.remove(command);
			}
		}
	}

	@Override
	public boolean isFinished() {
		for (MCRCommand command : sequentialCommands) {
			if (!command.isFinished()) {
				return false;
			}
		}
		return true;
	}

}
