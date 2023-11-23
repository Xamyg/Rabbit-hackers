import itumulator.simulator.Actor;
import itumulator.world.Location;
import itumulator.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Animal implements Actor {
    World world;
    boolean isNight = false;
    int vision_range;
    int age;
    int food;
    abstract void eat();

    abstract void die();

    abstract void reproduce();

    void randomMove(){
        List<Location> list = new ArrayList<>(world.getEmptySurroundingTiles());
        Random r = new Random();
        Location l = list.get(r.nextInt(list.toArray().length - 1));
        world.move(this, l);
    }
}
