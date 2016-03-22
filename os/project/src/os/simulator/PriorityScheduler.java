package os.simulator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import os.simulator.Process.Priority;

public class PriorityScheduler implements ProcessScheduler {

    @Override
    public Process nextProcess(List<Process> processes) {
        for (Process process : processes) {
            if (!process.isTerminated()) return process;
        }
        return null;
    }

//    @Override
//    public List<Process> nextProcesses(List<Process> processes, int count) {
//    // assert
//        assert(isSorted(processes));
//        List<Process> targetProcesses = new ArrayList<>();
//        for (int i = processes.size() - 1; i >= 0 && targetProcesses.size() < count; i--) {
//            if (!processes.get(i).isTerminated()) targetProcesses.add(processes.get(i));
//        }
//        return targetProcesses;
//    }
    
    private boolean isSorted(List<Process> processes) {
        for (int i = 1; i < processes.size(); i++) {
            if (larger(processes.get(i).getPriority(), processes.get(i - 1).getPriority())) return false;
        }
        return true;
    }
    
    private boolean larger(Priority priority1, Priority priority2) {
        return priority1.ordinal() > priority2.ordinal();
    }

    @Override
    public List<Process> nextProcesses(List<Process> processes, int count) {
        // assert
        List<Process> targetProcesses = new ArrayList<>();
        Iterator<Process> iterator = processes.iterator();
        while (iterator.hasNext()) {
            Process process = iterator.next();
            if (!process.isTerminated()) {
                if (targetProcesses.size() < count) targetProcesses.add(process);
                else {
                    for (int i = 0; i < targetProcesses.size(); i++) {
                        if (process.getPriority().ordinal() < targetProcesses.get(i).getPriority().ordinal()) {
                            targetProcesses.remove(i);
                            targetProcesses.add(process);
                        }
                    }
                }
            }
        }
        return targetProcesses;
    }
}
