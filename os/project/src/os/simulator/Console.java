package os.simulator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Console {
    
    private Scanner scanner;
    
    public Console() {
        scanner = new Scanner(System.in);
    }
    
    public Command nextCommand() {
        if (scanner == null) return Command.UNKNOW_COMMAND;
        String line = scanner.nextLine();
        String[] items = line.split(" ");
        Queue<String> queue = new LinkedList<String>();
        for (int i = 0; i < items.length; i++) queue.add(items[i]);
        
        String commandString = queue.poll();
        if (commandString.equalsIgnoreCase("show")) commandString += "_" + queue.poll();
        CommandName instruction = CommandName.UNKNOWN;
        try {
            instruction = CommandName.valueOf(commandString.toUpperCase());
        }
        catch (Exception ex) {
            return Command.UNKNOW_COMMAND;
        }
        
        String parameterString;
        List<Integer> parameters = new ArrayList<>();
        while (!queue.isEmpty()) {
            try {
                parameterString = queue.poll();
                parameters.add(Integer.parseInt(parameterString));
            }
            catch (NumberFormatException ex) {
                break;
            }
        }
        
        return new Command(instruction, parameters);
    }
    
    public void close() {
        scanner.close();
        scanner = null;
    }
    
    public static class Command {
        public static final Command UNKNOW_COMMAND = new Command(CommandName.UNKNOWN, null);
        
        CommandName instruction;
        List<Integer> parameters;
        
        public Command(CommandName instruction, List<Integer> parameters) {
            this.instruction = instruction;
            this.parameters = parameters;
        }
        
        public CommandName getInstruction() {
            return instruction;
        }
        
        public List<Integer> getParameter() {
            return parameters;
        }
    }
    
    public enum CommandName {
        RUN,
        SHOW_PCT,
        SHOW_PIT,
        PS,
        HELP,
        KILL,
        SET_NUM_CPUS,
        SET_OS,
        EXIT,
        UNKNOWN,
    }

}
