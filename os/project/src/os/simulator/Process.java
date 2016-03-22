package os.simulator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Process implements Comparable<Process> {
    
    private int id;
    private int currentThreadId;
    private Status status;
    private Priority priority;
    private int counter;
    private List<Instruction> instructions;
    
    public Process(int id, Priority priority, List<Instruction> instructions) {
        this.id = id;
        this.priority = priority;
        this.instructions = instructions;
        
        currentThreadId = -1;
        status = Status.READY;
        counter = 0;
    }
    
    public Process(Process process) {
        this.id = process.id;
        this.priority = process.priority;
        this.instructions = process.instructions;
        
        currentThreadId = -1;
        status = Status.READY;
        counter = 0;
    }
    
    public boolean isTerminated() {
        return counter >= instructions.size();
    }
    
    public Priority getPriority() {
        return priority;
    }
    
    public void run(int threadId) {
        counter++;
        currentThreadId = threadId;
//        System.out.println("Run process " + id + " thread " + currentThreadId + " instruction " + counter);
        if (isTerminated()) status = Status.TERMINATED;
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
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(Process.class.getSimpleName());
        sb.append("{")
            .append("id:").append(id)
            .append(", currentThreadId:").append(currentThreadId)
            .append(", status:").append(status)
            .append(", priority:").append(priority)
            .append(", counter:").append(counter)
            .append(", instructions:").append(instructions)
            .append("}");
        return sb.toString();
    }

    @Override
    public int compareTo(Process otherProcess) {
        return otherProcess.priority.ordinal() - priority.ordinal();
    }

}
