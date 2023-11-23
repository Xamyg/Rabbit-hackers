import itumulator.simulator.Actor;
import itumulator.world.Location;
import itumulator.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;


public class Person implements Actor {
    boolean isNight = false;
    @Override
    public void act(World world){
        isNight = world.isNight();
        if (!isNight) {
            Set<Location> neighbors = world.getEmptySurroundingTiles();
            List<Location> list = new ArrayList<>(neighbors);
            Random r = new Random();
            Location l = list.get(r.nextInt(list.toArray().length - 1));
            world.move(this, l);
        } else {

        }
    }

}
