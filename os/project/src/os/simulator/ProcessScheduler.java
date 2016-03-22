package os.simulator;

import java.util.List;

public interface ProcessScheduler {
    
    public Process nextProcess(List<Process> processes);
    
    public List<Process> nextProcesses(List<Process> processes, int count);

}
