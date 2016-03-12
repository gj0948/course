package os.simulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import os.simulator.Process.Instruction;
import os.simulator.Process.Priority;

public class OperatingSystem {
    
    // strategy pattern
    private ProcessScheduler scheduler;
    private List<Process> processes;
    private int clock;
    private int numCpus;
    
    public OperatingSystem(List<Process> processes) {
        final int DATA_SIZE = 2000;
        
        this.processes = new ArrayList<>();
        
        Process[] processArray = new Process[processes.size() * DATA_SIZE];
        int index = 0;
        for (int i = 0; i < DATA_SIZE; i++) {
            for (Process process: processes)
                processArray[index++] = new Process(process);
        }
//        Arrays.sort(processArray);
        for (int i = 0; i < processArray.length; i++) this.processes.add(processArray[i]);
        
//        this.processes = processes;
        
        scheduler = new PriorityScheduler();
        clock = 0;
        numCpus = 4;
    }
    
    public void start() {
        long startTimeStamp = System.currentTimeMillis();
        while (!isTerminated()) {
//            System.out.println("run()");
            run();
        }
        System.out.println(System.currentTimeMillis() - startTimeStamp);
    }
    
    public void run() {
        int threadId = 0;
        List<Process> nextProcesses = scheduler.nextProcesses(processes, numCpus);
        for (Process process : nextProcesses) {
            process.run(++threadId);
            if (process.isTerminated()) processes.remove(process);
        }
    }
    
    private boolean isTerminated() {
        for (Process process: processes) if (!process.isTerminated()) return false;
        return true;
    }

    public static void main(String[] args) {
        List<Process> processes = readProcess("files\\process-info\\scenario_1\\");
//        for (int i = 0; i < processes.size(); i++) System.out.println(processes.get(i));
//        Iterator<Process> iterator = processes.iterator();
//        while (iterable.hasNext()) System.out.println(iterator.next());
//        for (Process process : processes) System.out.println(process);

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