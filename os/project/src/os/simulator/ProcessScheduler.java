
package os.simulator;

import java.util.List;

public interface ProcessScheduler {
    public void setProcesses(List<Process> processes);
    public List<Process> nextProcesses(int threadCount);
    public List<Process> getProcesses();
    public List<Process> getTerminatedProcesses();
    public List<Process> getNonTerminatedProcesses();
    public boolean isTerminated();
    public boolean kill(int processId);
}