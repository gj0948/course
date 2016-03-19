package os.simulator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Process implements Comparable<Process> {
    
    private int id;
    private Status status;
    private Priority priority;
    private int threadCount;
    private int threads;
    private int cpuTime;
    private int counter;
    private List<Instruction> instructions;
    
    public Process(int id, int threadCount, Priority priority, List<Instruction> instructions) {
        this.id = id;
        this.threadCount = threadCount;
        this.priority = priority;
        this.instructions = instructions;
        
        status = Status.READY;
        cpuTime = 0;
        counter = 0;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
    
    public boolean isTerminated() {
        return status == Status.TERMINATED;
    }
    
    public int getProcessId() {
        return id;
    }
    
    public int getRemaingInstructionCount() {
        return Math.min(instructions.size() - counter, threadCount);
    }
    
    public Priority getPriority() {
        return priority;
    }
    
    public void setThreads(int threads) {
        this.threads = threads;
    }
    
    public void run() {
//        System.out.println(this.toString());
        if (isTerminated()) {
            threads = 0;
            return;
        }
        cpuTime++;
        status = Status.RUNNING;
        for (int i = 1; i <= threads; i++) {
            System.out.println("Run process " + id + " thread " + i + " instruction " + ++counter);
        }
        threads = 0;
        if (counter >= instructions.size()) status = Status.TERMINATED;
//        else status = Status.WAITING;
    }

    public enum Status {
        RUNNING,
        WAITING,
        READY,
        TERMINATED,
        ;
    }
    
    public enum Priority {
        TOP,
        HIGH,
        MEDIUM,
        LOW,
        ;
    }
    
    public enum Instruction {
        INPUT,
        COMPUTE,
        OUTPUT,
        FINISH,
        ;
        
        private static Map<String, Instruction> INSTRUCTION_MAP;
        
        static {
            INSTRUCTION_MAP = new HashMap<>();
            INSTRUCTION_MAP.put("i", INPUT);
            INSTRUCTION_MAP.put("c", COMPUTE);
            INSTRUCTION_MAP.put("o", OUTPUT);
            INSTRUCTION_MAP.put("f", FINISH);
        }
        
        public static Instruction getInstructionByName(String name) {
            return INSTRUCTION_MAP.get(name);
        }
        
    }
    
//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder(Process.class.getSimpleName());
//        sb.append("{")
//            .append("id:").append(id)
//            .append(", status:").append(status)
//            .append(", priority:").append(priority)
//            .append(", counter:").append(counter)
//            .append(", instructions:").append(instructions)
//            .append("}");
//        return sb.toString();
//    }
    
    @Override
    public String toString() {
        return String.format("%8d%16s%8d%8s%8d", id, status.name().toLowerCase(), cpuTime, priority.name().toLowerCase(), counter);
    }

    @Override
    public int compareTo(Process otherProcess) {
        return priority.ordinal() - otherProcess.priority.ordinal();
    }

}
