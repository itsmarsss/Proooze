package prooze.processes;

public class ProcessInfo {
	private String name;
	private long start;
	private long duration;
	private boolean done;
	public ProcessInfo(String name, long start) {
		this.setName(name);
		this.setStart(start);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public long getStart() {
		return start;
	}
	public void setStart(long start) {
		this.start = start;
	}
	
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	public void addDuration(long duration) {
		this.duration+=duration;
	}
	public boolean isDone() {
		return done;
	}
	public void setDone(boolean done) {
		this.done = done;
	}
}
