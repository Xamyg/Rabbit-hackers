import itumulator.simulator.Actor;
import itumulator.world.Location;
import itumulator.world.NonBlocking;
import itumulator.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Grass implements NonBlocking, Actor {
    int growthRate = 5;

    /** Has a random chance to 'grow' another tile of grass. Chance is equal to growthRate% **/
    @Override
    public void act(World world) {
        Random r = new Random();
        if (r.nextInt(100 / growthRate) == 0){
            grow(world);
        }

    }
    /** Chooses a random neighboring location that does not already have Grass on it, to add Grass to **/
    public void grow(World world) {
        List<Location> list = new ArrayList<>(world.getSurroundingTiles());
        list.removeIf(world::containsNonBlocking);
        if (list.toArray().length > 0){
            Random r = new Random();
            Location l = list.get(r.nextInt(list.toArray().length));
            world.setTile(l, new Grass());
        }

        }
    }

