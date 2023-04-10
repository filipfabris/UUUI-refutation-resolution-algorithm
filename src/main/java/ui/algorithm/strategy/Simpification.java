package ui.algorithm.strategy;

import ui.data.structure.LogicPremises;

import java.util.Iterator;
import java.util.Set;

public class Simpification {

    public static void removeRedundant(Set<LogicPremises> clauses){

        Iterator<LogicPremises> iterator = clauses.iterator();
        while(iterator.hasNext()){
            LogicPremises clause1 = iterator.next();
            for(LogicPremises clause2: clauses){
                if(clause1.equals( clause2 )){
                    continue;
                }
                if(clause1.premises.containsAll( clause2.premises )){
                    iterator.remove();
                    break;
                }
            }

        }
    }

    public static void removeTautology(Set<LogicPremises> clauses){
        Iterator<LogicPremises> iterator = clauses.iterator();

        while(iterator.hasNext()){
            LogicPremises clause = iterator.next();
            for(String literal: clause.premises){
                if(literal.startsWith( "~" )){
                    String matchingLiteral = literal.substring( 1 );
                    if(clause.premises.contains( matchingLiteral )){
                        iterator.remove();
                        break;
                    }
                }else{
                    String matchingLiteral = "~" + literal;
                    if(clause.premises.contains( matchingLiteral )){
                        iterator.remove();
                        break;
                    }
                }
            }
        }

    }




}
