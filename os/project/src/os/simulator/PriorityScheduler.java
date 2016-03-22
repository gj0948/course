package os.simulator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import os.simulator.Process.Status;

public class PriorityScheduler implements ProcessScheduler {
    
    private Process[] processes;
    
    public PriorityScheduler(List<Process> processes) {
        setProcesses(processes);
    }

    @Override
    public void setProcesses(List<Process> processes) {
        this.processes = new Process[processes.size()];
        processes.toArray(this.processes);
        Arrays.sort(this.processes);
    }

    @Override
    public List<Process> nextProcesses(int threadCount) {
        for (int i = 0; i < this.processes.length; i++)
            if (!this.processes[i].isTerminated())
                this.processes[i].setStatus(Status.WAITING);
        List<Process> selectedProcesses = new ArrayList<>();
        for (int i = 0; i < processes.length && threadCount > 0; i++) {
            if (!processes[i].isTerminated()) {
                int requestThreadCount = processes[i].getRemaingInstructionCount();
                processes[i].setThreads(threadCount > requestThreadCount ? requestThreadCount : threadCount);
                threadCount -= requestThreadCount;
                selectedProcesses.add(processes[i]);
            }
        }
        return selectedProcesses;
    }

    @Override
    public boolean isTerminated() {
        if (processes == null) return true;
        for (int i = 0; i < processes.length; i++) if (!processes[i].isTerminated()) return false;
        return true;
    }

    @Override
    public boolean kill(int processId) {
        for (int i = 0; i < processes.length; i++)
            if (!processes[i].isTerminated() && processes[i].getProcessId() == processId) {
                processes[i].setStatus(Status.TERMINATED);
                return true;
            }
        return false;
    }

    @Override
    public List<Process> getProcesses() {
        Process[] processes = this.processes.clone();
        Arrays.sort(processes, new Comparator<Process>() {

            @Override
            public int compare(Process processLeft, Process processRight) {
                return processLeft.getProcessId() - processRight.getProcessId();
            }
        });
        return Arrays.asList(processes);
    }

    @Override
    public List<Process> getTerminatedProcesses() {
        List<Process> terminatedProcess = new ArrayList<>();
        for (int i = 0; i < processes.length; i++)
            if (processes[i].isTerminated()) terminatedProcess.add(processes[i]);
        Process[] processArray = new Process[terminatedProcess.size()];
        terminatedProcess.toArray(processArray);
        Arrays.sort(processArray, new Comparator<Process>() {

            @Override
            public int compare(Process processLeft, Process processRight) {
                return processLeft.getProcessId() - processRight.getProcessId();
            }
        });
        return Arrays.asList(processArray);
    }

    @Override
    public List<Process> getNonTerminatedProcesses() {
        List<Process> nonTerminatedProcess = new ArrayList<>();
        for (int i = 0; i < processes.length; i++)
            if (!processes[i].isTerminated()) nonTerminatedProcess.add(processes[i]);
        Process[] processArray = new Process[nonTerminatedProcess.size()];
        nonTerminatedProcess.toArray(processArray);
        Arrays.sort(processArray, new Comparator<Process>() {

            @Override
            public int compare(Process processLeft, Process processRight) {
                return processLeft.getProcessId() - processRight.getProcessId();
            }
        });
        return Arrays.asList(processArray);
    }
    
//    private boolean isSorted() {
//        for (int i = 1; i < processes.length; i++) {
//            if (larger(processes[i].getPriority(), processes[i - 1].getPriority())) return false;
//        }
//        return true;
//    }
//    
//    private boolean larger(Priority priority1, Priority priority2) {
//        return priority1.ordinal() > priority2.ordinal();
//    }
}