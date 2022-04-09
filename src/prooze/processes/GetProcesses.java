package prooze.processes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class GetProcesses {

	public static LinkedList<String> getProcesses() throws IOException {
		String commandName = "powershell.exe -Command Get-Process | Where-Object {$_.mainWindowTitle} | Format-Table Name";

		Process powerShellProcessName = Runtime.getRuntime().exec(commandName);

		powerShellProcessName.getOutputStream().close();
		LinkedList<String> names = new LinkedList<String>();
		String line;
		BufferedReader brName = new BufferedReader(new InputStreamReader(powerShellProcessName.getInputStream()));
		brName.readLine();
		brName.readLine();
		brName.readLine();
		while((line = brName.readLine()) != null) {
			if(!line.isEmpty()) {
				names.add(line.trim());
			}
		}
		brName.close();

		return names;
	}

}
