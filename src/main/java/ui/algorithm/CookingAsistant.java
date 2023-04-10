package ui.algorithm;

import ui.data.structure.LogicPremises;
import ui.format.output.Utils;

import java.util.*;

public class CookingAsistant {

    List<String> cookingLines;

    Set<LogicPremises> clauses;

    public CookingAsistant(List<String> cookingLines) {
        this.cookingLines = cookingLines;
        clauses = new HashSet<>();
    }


    public void execute() {
        Iterator<String> iterator = cookingLines.iterator();

        while (iterator.hasNext()) {
            String line = iterator.next();
            if (line.endsWith( "?" )) {
                StringBuilder sb = new StringBuilder();
                sb.append( "User’s command: " );
                sb.append( line + "\n");
                System.out.print( sb);
                this.executeQuery( line );
            } else if (line.endsWith( "+" )) {
                receiptQuery( line, true );
            } else if (line.endsWith( "-" )) {
                receiptQuery( line, false );
            } else {
                receiptInput( line );
            }
        }

    }

    public void receiptInput(String line) {
        List<String> premises = parseLine( line );
        LogicPremises logicPremises = new LogicPremises( premises, null );
        clauses.add( logicPremises );
    }

    public void receiptQuery(String line, boolean add) {
        StringBuilder sb = new StringBuilder();
        sb.append( "User’s command: " );
        sb.append( line + "\n");

        line = line.substring( 0, line.length() - 1 );
        List<String> premiseLines = parseLine( line );
        LogicPremises logicPremises = new LogicPremises( premiseLines, null );

        if (add) {
            clauses.add( logicPremises );
            for(String premise : premiseLines) {
                sb.append( "Added " + premise + "\n" );
            }
        } else {
            clauses.remove( logicPremises );
            for(String premise : premiseLines) {
                sb.append( "Removed " + premise + "\n" );
            }
        }
        System.out.println( sb.toString() );
    }

    public List<LogicPremises> prepareQueryPremis(String line) {
        line = line.substring( 0, line.length() - 1 ); //remove ? from end of line
        LogicPremises inputPremis = new LogicPremises( parseLine( line ), null );
        LinkedList<LogicPremises> outputPremises = new LinkedList<>();

        String negatedPremise = LogicPremises.negatePremise( inputPremis );
        String[] splitNegated = negatedPremise.split( "\\^" );
        for (String s : splitNegated) {
            List<String> tmp = new LinkedList<>();
            tmp.add( s.trim() );
            LogicPremises negated = new LogicPremises( tmp, null );
            outputPremises.add( negated );
        }
        return outputPremises;
    }

    public List<String> parseLine(String line) {
        List<String> premises = new ArrayList<>();
        String[] split = line.split( " V " );

        for (String s : split) {
            premises.add( s.trim() );
        }

        return premises;
    }

    public void executeQuery(String line) {
        List<LogicPremises> logicPremises = prepareQueryPremis( line );
        String testing = line.substring( 0, line.length() - 1 );
        Set<LogicPremises> SoS = new HashSet<>();

        SoS.addAll( logicPremises );
        clauses.addAll( SoS );
        ResolutionAlgorithm resolutionAlgorithm = new ResolutionAlgorithm( clauses, SoS);
        LogicPremises result = resolutionAlgorithm.algorithm();

        Utils.printResult( result, testing);

        clauses.removeAll( SoS );
    }


}
