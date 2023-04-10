package ui.algorithm;

import ui.algorithm.strategy.Simpification;
import ui.data.structure.LogicPremises;
import ui.data.structure.Pair;

import java.util.*;

public class ResolutionAlgorithm {
    public Set<LogicPremises> clauses;
    public Set<Pair> resolvedPairs;
    public Set<LogicPremises> SoS;

    public ResolutionAlgorithm(Set<LogicPremises> clauses, Set<LogicPremises> SoS) {
        this.clauses = clauses;
        resolvedPairs = new HashSet<>();
        this.SoS = SoS;
    }

    public LogicPremises algorithm() {
        Set<LogicPremises> opens = new HashSet<>();
        Set<LogicPremises> tmp;

        Simpification.removeTautology( clauses );

        while (true) {
            tmp = new HashSet<>();
            while (true) {
                Pair selectedClauses = selectClauses();
                if (selectedClauses == null) {
                    break;
                }
                List<LogicPremises> resolvents = resolve(selectedClauses);
                for (LogicPremises resolvent : resolvents) {
                    if (this.checkForNil( resolvent) == true) {
                        return resolvent;
                    }
                    opens.addAll( resolvents );
                    SoS.addAll( resolvents ); //In depth search
                    this.resolutionStrategy(SoS);
                }
            }
            tmp.addAll( opens );
            tmp.addAll( clauses );
            this.resolutionStrategy(tmp);

            if (clauses.containsAll( tmp ) ){
                return null;
            }
            clauses = tmp;
        }
    }

    private void resolutionStrategy(Set<LogicPremises> toRemove) {
        Simpification.removeRedundant( toRemove );
        Simpification.removeTautology( toRemove );
    }

    private List<LogicPremises> resolve(Pair selectedClauses) {
        LogicPremises clause1 = selectedClauses.first;
        LogicPremises clause2 = selectedClauses.second;

        List<LogicPremises> resolvents = new ArrayList<>();

        if(clause1.premises.size() == 1 && clause2.premises.size() == 1){
            Iterator<String> iterator1 = clause1.premises.iterator();
            Iterator<String> iterator2 = clause2.premises.iterator();
            String literal1 = iterator1.next();
            String literal2 = iterator2.next();

            if(literal1.startsWith( "~" )){
                String literalToMatch = literal1.substring( 1 );
                if(literalToMatch.equalsIgnoreCase( literal2 )){
                    resolvents.add( new LogicPremises(Arrays.asList( "NIL" ), List.of(clause1, clause2)) );
                }
            }else{
                String literalToMatch = "~" + literal1;
                if(literalToMatch.equalsIgnoreCase( literal2 )){
                    resolvents.add( new LogicPremises(Arrays.asList( "NIL" ), List.of(clause1, clause2)) );
                }
            }

        }

        for (String literal : clause1.premises) {

            if (literal.startsWith( "~" )) {
                String atom = literal.substring( 1 );
                if (clause2.premises.contains( atom )) {
                    LogicPremises resolvent = new LogicPremises(List.of(clause1, clause2));
                    resolvent.premises.addAll( clause1.premises );
                    resolvent.premises.addAll( clause2.premises );
                    resolvent.premises.remove( literal );
                    resolvent.premises.remove( atom );
                    resolvents.add( resolvent );
                }
            } else {
                String atom = literal;
                if (clause2.premises.contains( "~" + atom )) {
                    LogicPremises resolvent = new LogicPremises(List.of(clause1, clause2));
                    resolvent.premises.addAll( clause1.premises );
                    resolvent.premises.addAll( clause2.premises );
                    resolvent.premises.remove( literal );
                    resolvent.premises.remove( "~" + atom );
                    resolvents.add( resolvent );
                }
            }
        }
        return resolvents;
    }

    public Pair selectClauses() {
        for (LogicPremises clause1 : SoS) {
            for (LogicPremises clause2 : clauses) {
                if (clause1 == clause2) {
                    continue;
                }

                if (resolvedPairs.contains( new Pair( clause1, clause2 ) )) {
                    continue;
                }

                if(this.canEliminate( clause1, clause2 ) == false){
                    continue;
                }

                Pair selectedClauses = new Pair();
                selectedClauses.first = clause1;
                selectedClauses.second = clause2;
                resolvedPairs.add( selectedClauses );
                return selectedClauses;
            }
        }
        return null;
    }

    public boolean canEliminate(LogicPremises clause1, LogicPremises clause2) {
        for (String literal : clause1.premises) {

            if(literal.matches( "V|v|\\^" )){
                continue;
            }

            if (literal.startsWith( "~" )) {
                String atom = literal.substring( 1 );
                if (clause2.premises.contains( atom )) {
                    return true;
                }
            } else {
                String atom = literal;
                if (clause2.premises.contains( "~" + atom )) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkForNil(LogicPremises resolvents) {
        if (resolvents.premises.contains( "NIL" )) {
            return true;
        }
        return false;
    }


}
