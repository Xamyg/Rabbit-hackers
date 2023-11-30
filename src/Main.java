import itumulator.executable.DisplayInformation;
import itumulator.executable.Program;
import itumulator.world.Location;
import itumulator.world.World;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        try {
            readFile(new File(".\\data\\t1-2cde.txt"));
        } catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }


    }

    public static Location randomLocation(int size){
        Random r = new Random();
        int x = r.nextInt(size);
        int y = r.nextInt(size);
        return new Location(x,y);
    }

    public static void readFile(File file) throws FileNotFoundException{
        Scanner s = new Scanner(file);

        int size = s.nextInt(); // størrelsen af vores 'map' (dette er altid kvadratisk)
        int delay = 300; // forsinkelsen mellem hver skridt af simulationen (i ms)
        int display_size = 900; // skærm opløsningen (i px)
        Program p = new Program(size, display_size, delay); // opret et nyt program
        World world = p.getWorld(); // hiv verdenen ud, som er der hvor vi skal tilføje ting!

        p.setDisplayInformation(Rabbit.class, new DisplayInformation(Color.red, "rabbit-large"));
        p.setDisplayInformation(Grass.class, new DisplayInformation(Color.green, "grass"));
        p.setDisplayInformation(Burrow.class, new DisplayInformation(Color.ORANGE, "hole-small"));
        p.setDisplayInformation(Carcass.class, new DisplayInformation(Color.ORANGE, "carcass-small"));

        while (s.hasNext()) {
            String objectType = s.next();
            System.out.println("Type: " + objectType);
            String objectAmountString = s.next();
            System.out.print("Amount: " + objectAmountString);

            int objectAmount;
            if (objectAmountString.contains("-")){
                ArrayList<String> minmax = new ArrayList<String>(Arrays.asList(objectAmountString.split("-")));
                int objectAmountMin = Integer.parseInt(minmax.get(0));
                int objectAmountMax = Integer.parseInt(minmax.get(1));
                Random r = new Random();
                objectAmount = r.nextInt(objectAmountMax - objectAmountMin + 1) + objectAmountMin;
            } else {
                objectAmount = Integer.parseInt(objectAmountString);

            } System.out.println(" (" + objectAmount + ")");



            for (int i = 0; i < objectAmount; i++) {
                Location l = randomLocation(size);
                if (objectType.equals("grass")){
                    while (world.containsNonBlocking(l)) {
                        l = randomLocation(size);
                    }
                    world.setTile(l, new Grass());
                } else if (objectType.equals("rabbit")) {
                    while (!world.isTileEmpty(l)) {
                        l = randomLocation(size);
                    }
                    world.setTile(l, new Rabbit(world, l));
                }

            }
        }
        p.show();

    }
}