package os.simulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import os.simulator.Process.Instruction;
import os.simulator.Process.Priority;

public class OperatingSystem {
    
    private static final String[] FILES = new String[] {
        "",
        "files\\process-info\\scenario_1\\",
        "files\\process-info\\scenario_2\\",
        "files\\process-info\\scenario_3\\",
    };
    
    private static final int MIN_CUP_NUM = 1;
    private static final int MAX_CUP_NUM = 8;
    
    // strategy pattern
    private ProcessScheduler scheduler;
    private int clock;
    private int numCpus;
    
    public enum Command {
        RUN,
        SHOW_PCT,
        SHOW_PIT,
        PS,
        HELP,
        KILL,
        SET_NUM_CPUS,
        SET_OS,
    }
    
    public OperatingSystem(List<Process> processes) {
        scheduler = new PriorityScheduler(processes);
        clock = 0;
        numCpus = 4;
    }
    
    public void start() {
//        for (int i = 0; i < 3; i++) {
//            System.out.println("run() " + clock);
//            run();
//        }
//        
        setOs(3);
        
        kill(1004);
        kill(1007);
        
        show_pct();
        while (!isTerminated()) {
            System.out.println("run() " + clock);
            run();
//            ps();
            show_pct();
        }
    }
    
    public void setOs(int index) {
        scheduler.setProcesses(readProcess(FILES[index]));
    }
    
    public boolean kill(int processId) {
        return scheduler.kill(processId);
    }
    
    public void ps() {
        for (Process process: scheduler.getNonTerminatedProcesses()) System.out.println(process);
    }
    
    public void show_pct() {
        for (Process process: scheduler.getProcesses()) System.out.println(process);
    }
    
    public void run() {
        clock++;
        List<Process> nextProcesses = scheduler.nextProcesses(numCpus);
        for (Process process : nextProcesses) {
            process.run();
        }
    }
    
    private boolean isTerminated() {
        return scheduler.isTerminated();
    }

    public static void main(String[] args) {
        List<Process> processes = readProcess(FILES[1]);
        new OperatingSystem(processes).start();
    }
    
    static List<Process> readProcess(String directory) {
        final File pct = new File(directory, "pct.txt");
        final File pit = new File(directory, "pit.txt");
        final List<Process> processes = new ArrayList<>();
        Scanner pctScanner = null;
        Scanner pitScanner = null;
        
        try {
            pctScanner = new Scanner(pct);
            pitScanner = new Scanner(pit);
            
            int id;
            int threadId;
            String status;
            int cpuTime;
            int priority;
            int counter;
            
            pctScanner.nextLine();
            pitScanner.nextLine();
            while (pctScanner.hasNextLine()) {
                
                id = pctScanner.nextInt();
                threadId = pctScanner.nextInt();
                status = pctScanner.next();
                cpuTime = pctScanner.nextInt();
                priority = pctScanner.nextInt();
                counter = pctScanner.nextInt();
                
                processes.add(new Process(
                        id,
                        threadId,
                        Priority.values()[priority - 1],
                        parseInstructions(pitScanner.nextLine())));
            }
            
        } catch (FileNotFoundException e) {
            for (StackTraceElement element: e.getStackTrace()) System.err.println(element);
//            e.printStackTrace();
        } finally {
            if (pctScanner != null) pctScanner.close();
            if (pitScanner != null) pitScanner.close();
        }
        
        return processes;
    }
    
    static List<Instruction> parseInstructions(String instructionRawData) {
        List<Instruction> instructions = new ArrayList<>();
        String[] instructionCharacters = instructionRawData.split("\t");
        for (String instructionName : instructionCharacters) {
            if (!instructionName.trim().equals("")) {
                instructions.add(Instruction.getInstructionByName(instructionName.trim()));
            }
        }
        return instructions;
    }
}
