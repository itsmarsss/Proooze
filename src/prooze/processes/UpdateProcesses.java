package prooze.processes;

import java.awt.AWTException;
import java.awt.GridBagConstraints;
import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;

import prooze.Proze;
import prooze.components.ProzeListProfile;
import prooze.sections.CurrentStatusPage;

public class UpdateProcesses {
	public static void update() throws IOException, AWTException {
		JPanel processesPanel = CurrentStatusPage.getProcessesPanel();
		processesPanel.removeAll();
		LinkedList<String>processes = GetProcesses.getProcesses();
		LinkedList<String>blacklist = Proze.getBlackList();
		for(int i = 0; i < processes.size(); i++) {
			for(String black:blacklist) {
				if(processes.get(i).toLowerCase().contains(black.toLowerCase())) {
					processes.remove(i);
					i--;
				}
			}
		}
		LinkedList<ProcessInfo>currentRunning = Proze.getCurrentRunning();
		for(int i = 0; i < currentRunning.size(); i++) {
			for(String black:blacklist) {
				if(currentRunning.get(i).getName().toLowerCase().contains(black.toLowerCase())) {
					currentRunning.remove(i);
					i--;
				}
			}
		}
		for(String info : processes) {
			boolean matched = false;
			for(ProcessInfo runningInfo : currentRunning) {
				if(runningInfo.getName().equals(info)) {
					if(runningInfo.isDone()) {
						runningInfo.setStart(System.currentTimeMillis());
						runningInfo.setDone(false);
					}
					matched = true;
				}
			}
			if(!matched) {
				currentRunning.add(new ProcessInfo(info, System.currentTimeMillis()));
			}
		}
		for(ProcessInfo runningInfo : currentRunning) {
			if(!runningInfo.isDone()) {
				runningInfo.addDuration(System.currentTimeMillis()-runningInfo.getStart());
				runningInfo.setStart(System.currentTimeMillis());
			}
			boolean matched = false;
			for(String info : processes) {
				if(info.equals(runningInfo.getName())) {
					matched = true;
				}
			}
			if(!matched) {
				if(!runningInfo.isDone()) {
					runningInfo.setDone(true);
				}
			}
		}


		//					for(ProcessInfo runningInfo : currentRunning) {
		//						boolean matched = false;
		//						for(String info : processes) {
		//							if(info.equals(runningInfo.getName())) {
		//								matched = true;
		//							}
		//						}
		//						if(!matched) {
		//							if(!runningInfo.isDone()) {
		//								runningInfo.addDuration(System.currentTimeMillis()-runningInfo.getStart());
		//								runningInfo.setDone(true);
		//							}
		//						}
		//					}


		//for(ProcessInfo i:currentRunning) {
			//System.out.println(i.getName() + "-" + i.getStart() + "-" + i.getDuration());
		//}			

		//System.out.println();
		//System.out.println();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		LinkedList<ProcessInfo>tempList = new LinkedList<ProcessInfo>(currentRunning);
		while(!tempList.isEmpty()) {
			gbc.gridy++;
			ProcessInfo process = tempList.poll();
			long millis = process.getDuration();
			String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
					TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
					TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));

			ProzeListProfile processProfile = new ProzeListProfile(process.getName(), hms, processesPanel);
			processesPanel.add(processProfile, gbc);
		}

		processesPanel.repaint();
		processesPanel.revalidate();
	}
}
