package ui;

import ui.algorithm.CookingAsistant;
import ui.algorithm.ResolutionAlgorithm;
import ui.data.structure.LogicPremises;
import ui.format.output.Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Solution {


    public static void main(String[] args) throws IOException {

        long start = System.currentTimeMillis();

        boolean cooking = false;
        boolean resolution = false;

        String clausePath = null;
        String cookingCommands = null;

        if(args[0].equalsIgnoreCase( "cooking" )){
            clausePath = args[1];
            cookingCommands = args[2];
            cooking = true;
        }else if(args[0].equalsIgnoreCase( "resolution" )) {
            clausePath = args[1];
            resolution = true;
        }else {
            throw new IllegalArgumentException( "Wrong arguments" );
        }

//        clausePath = "test.txt";

        //Read premises
        List<String> input = Files.readAllLines( Path.of( clausePath ) );
        input.replaceAll(String::toUpperCase); //To upper case all
        if(resolution){
            Set<LogicPremises> SoS = new HashSet<>();
            Set<LogicPremises> clauses = LogicPremises.parseInput( input, SoS);
            String testing = input.get( input.size() - 1 );
            ResolutionAlgorithm resolutionAlgorithm = new ResolutionAlgorithm( clauses, SoS);
            LogicPremises result = resolutionAlgorithm.algorithm();
            Utils.printResult( result, testing);
        }else {
            //Read cooking commands
            List<String> cookingCommandLines = Files.readAllLines( Path.of( cookingCommands ));
            cookingCommandLines.replaceAll(String::toUpperCase); //To upper case all

            input.addAll( cookingCommandLines );
            CookingAsistant cookingAsistant = new CookingAsistant( input );
            cookingAsistant.execute();
        }

        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
//        System.out.println("Time elapsed: " + timeElapsed + " ms");
    }

}