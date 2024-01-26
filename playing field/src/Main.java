import java.time.LocalDate;
import java.time.Month;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
/*        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        for (int i = 1; i <= 5; i++) {
            //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
            // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
            System.out.println(i + ". Software Construction");
        }

 */

        System.out.println("Ok now let's do thi:\n\n");
        GuyMan sayer = new GuyMan();
        sayer.says("I'm here to program, innit");

        LocalDate date = LocalDate.of(2023, Month.JANUARY,25);
        System.out.println(date);
        System.out.println(myStrProgram());
    }

    public static String myStrProgram() {
        return "get got";
    }
}

