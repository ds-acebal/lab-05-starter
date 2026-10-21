
package es.uniovi.eii.ds.main;

import java.io.PrintWriter;
import java.util.Scanner;

import es.uniovi.eii.ds.editor.core.Editor;

/*
    Simulates user interaction with the editor through a simple console interface.
    Rather than implementing a graphical UI, this class translates user commands
    into the events that a real graphical UI would send to the editor.

    This class is not part of the editor's core logic and is outside the scope
    of this exercise. You are not expected to understand or modify its implementation;
    just leave it unchanged.
 */
public class Main {

    private Scanner in;
    private PrintWriter out;

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        boolean exit = false;
        in = new Scanner(System.in);
        out = new PrintWriter(System.out, true);
        Editor editor = new Editor(out);

        showHelp();

        do {
            Command command = askUser();
            try {
                switch (command.name()) {
                    
                    // Tools
                    case "selection", "rectangle", "circle", "triangle" ->
                        editor.toolButtonPressed(command.name());     
                        
                    // Other commands
                    case "draw" -> editor.draw();
                    case "undo" -> editor.undo();
                    case "redo" -> editor.redo();
                    case "help" -> showHelp();
                    case "exit" -> exit = true;
                    
                    // Mouse actions
                    case "press", "move", "release" -> {
                        
                        int x = Integer.parseInt(command.args()[0]);
                        int y = Integer.parseInt(command.args()[1]);

                        switch (command.name()) {
                            case "press" -> editor.mousePressed(x, y);
                            case "move" -> editor.mouseMoved(x, y);
                            case "release" -> editor.mouseReleased(x, y);
                        }
                    }

                    default -> out.println("Unknown action");
                }
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                out.println("Error: expected coordinates in the format: x, y");
            }
        } while (!exit);

        out.println("Bye!");
        in.close();
    }

    private record Command(String name, String[] args) {}

    // Ask the user and return a command with its arguments. For example:
    //  - help              { name: "help", args: [] }
    //  - press 10, 20      { name: "press", args: ["10", "20"] }
    //
    private Command askUser() {
        String userInput;
        do {
            out.print("> ");
            out.flush();
            userInput = in.nextLine().trim();
        } while (userInput.isEmpty());
        // Split the input into command name and arguments
        String[] parts = userInput.split("\\s+", 2);
        return new Command(parts[0], parts.length > 1 ? parts[1].split("[,\\s]+") : new String[0]);
    }

    private void showHelp() {
        out.println();
        out.println("Tools: selection - rectangle - circle - triangle");
        out.println("Mouse: press <x>,<y> - move <x>,<y> - release <x>,<y>");
        out.println("Other: draw - undo - redo - help - exit");
        out.println("-----------------------------------------------------");
    }
}
