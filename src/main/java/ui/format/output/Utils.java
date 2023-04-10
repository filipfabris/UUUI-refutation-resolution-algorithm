package ui.format.output;

import ui.data.structure.LogicPremises;

import java.util.List;

public class Utils {

    public static void printResult(LogicPremises result, String testing) {

        if(result == null) {
            System.out.println("[CONCLUSION]: " + testing + " is unknown");
            System.out.println();
            return;
        }

        List<LogicPremises> path = LogicPremises.pathAsList( result );

        int count = 1;
        for (LogicPremises logicPremises : path) {
            StringBuilder sb = new StringBuilder();

            logicPremises.givenNumber = count;
            if (logicPremises.parent == null) {
                sb.append( logicPremises.givenNumber + " " );
                sb.append( logicPremises.premises + " " );
                System.out.println( sb );
            } else {
                sb.append( logicPremises.givenNumber + " " );
                sb.append( logicPremises.premises + " " );

                LogicPremises parent1 = logicPremises.parent.get( 0 );
                LogicPremises parent2 = logicPremises.parent.get( 1 );

                sb.append( "(" );
                sb.append( parent1.givenNumber + ", " );
                sb.append( parent2.givenNumber + ")" );

                System.out.println( sb );
            }
            count++;
        }

        System.out.println("[CONCLUSION]: " + testing + " is true");
        System.out.println();
    }


    public void removeComments(List<String> input){
        for (int i = 0; i < input.size(); i++) {
            String line = input.get( i );
            if (line.startsWith( "#" )) {
                input.remove( i );
                i--;
            }
        }
    }
}
