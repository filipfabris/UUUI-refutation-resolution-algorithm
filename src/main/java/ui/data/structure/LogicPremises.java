package ui.data.structure;

import java.util.*;

public class LogicPremises {
    public Set<String> premises;

    public List<LogicPremises> parent;

    public static int globalDepth = 0;
    public int depth;
    public int givenNumber = 0;

    private static Comparator premisComparator = (Comparator<String>) Comparator.naturalOrder();

    private static Comparator<LogicPremises> pathComparator = Comparator.comparingInt( o -> o.depth );

    public LogicPremises(List<String> premises, List<LogicPremises> parent) {
//        Collections.sort( premises, premisComparator );
        this.premises = new HashSet<>( premises );
        this.parent = parent;
        this.depth = globalDepth++;
    }

    public LogicPremises(List<LogicPremises> parent) {
        this.premises = new HashSet<>();
        this.parent = parent;
        this.depth = globalDepth++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LogicPremises that)) return false;
        return Objects.equals( premises, that.premises );
    }

    @Override
    public int hashCode() {
        return Objects.hash( premises );
    }

    public static String negatePremise(LogicPremises premise) {
        StringBuilder sb = new StringBuilder();
        for(String p : premise.premises) {
            if(p.startsWith("~")) {
                sb.append( p.substring( 1 ) );
                sb.append( "^" );
            }else {
                sb.append( "~" + p);
                sb.append( "^" );
            }
        }
        return sb.toString().trim();
    }

    public static Set<LogicPremises> parseInput(List<String> input, Set<LogicPremises> Sos) {
        Set<LogicPremises> clauses = new HashSet<>();
        Iterator<String> it = input.iterator();
        while (it.hasNext()) {
            String line = it.next();
            String[] split = line.split( " V " );

            List<String> tmp = new LinkedList<>();
            for(String s : split) {
                tmp.add( s.trim() );
            }
            LogicPremises premis = new LogicPremises( tmp, null);

            if (it.hasNext() == false) {
                String negatedPremise = LogicPremises.negatePremise( premis );
                String[] splitNegated = negatedPremise.split( "\\^" );
                for (String s : splitNegated) {
                    tmp = new LinkedList<>();
                    tmp.add( s.trim() );
                    LogicPremises negated = new LogicPremises( tmp, null);
                    clauses.add( negated);
                    Sos.add( negated );
                }

            } else {
                clauses.add( premis );
            }

        }
        return clauses;
    }

    public static LogicPremises parseLine(String line) {
        String[] split = line.split( " V " );

        List<String> tmp = new LinkedList<>();
        for(String s : split) {
            tmp.add( s.trim() );
        }
        return new LogicPremises( tmp, null);
    }

    //Returning path nodes as list
    public static List<LogicPremises> pathAsList(LogicPremises node) {
        Set<LogicPremises> pathList = new LinkedHashSet<>();
        pathRecursiveAsList(pathList, node);

        List<LogicPremises> output = new ArrayList<>( pathList );
        Collections.sort( output, pathComparator );
        return output;
    }

    private static void pathRecursiveAsList(Set<LogicPremises> pathNodes, LogicPremises premis) {
        if(premis.parent!=null ) {
            for (LogicPremises p : premis.parent){
                pathRecursiveAsList(pathNodes, p);
            }
        }
        pathNodes.add( premis );
    }

    @Override
    public String toString() {
        return premises.toString() + " depth: " + depth;
    }
}