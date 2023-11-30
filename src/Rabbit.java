import itumulator.executable.DisplayInformation;
import itumulator.executable.DynamicDisplayInformationProvider;
import itumulator.world.Location;
import itumulator.world.World;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.max;

public class Rabbit extends Animal implements DynamicDisplayInformationProvider {

    Burrow home = null;

    boolean burrowed = false;

    Rabbit(World world){
        this.world = world;
        this.energy = 50;
        this.vision_range = 2;
    }
    @Override
    public void act(World world) {
        isNight = world.isNight();
        System.out.println(isNight);
        age++;
        this.energy -= max(1, (age / 20));
        if (energy <= 0) {
            die();
        } else {
            if (!isNight) { // If it's daytime:
                if (burrowed){
                    home.toggleHide(this);
                } else {
                    if (world.containsNonBlocking(world.getLocation(this)) && world.getNonBlocking(world.getLocation(this)) instanceof Grass && energy < 100) {
                        eat();
                    } else if (energy < 100) {
                        moveTowards(search(Grass.class));
                    } else {
                        randomMove();
                    }
                }
            } else if (isNight) { // If it's nighttime:
                if (home == null){
                    Location l = search(Burrow.class);
                    if (l == null){
                        createBurrow();
                    } else {
                        moveTowards(l);
                        }
                    }

                } else if (!burrowed) {
                    if (world.getLocation(home) == world.getLocation(this)) {
                        home.toggleHide(this);
                    } else {
                        moveTowards(world.getLocation(home));
                    }
                } else if (energy >= 100){
                    reproduce();
                }
            }
        }

        /** Deletes Grass object to restore energy **/
        @Override
        public void eat(){
            world.delete(world.getNonBlocking(world.getLocation(this)));
            this.energy += 25;
        }

        /** Creates a new Rabbit object at the cost of energy **/
        @Override
        void reproduce() {
            this.energy -= 75;
            List<Location> list = new ArrayList<>(world.getEmptySurroundingTiles(world.getLocation(home)));
            Random r = new Random();
            Location l = list.get(r.nextInt(list.toArray().length - 1));
            world.setTile(l, new Rabbit(world));
        }
        /** Creates new Burrow object at current location and sets it as home **/
        void createBurrow() {
            Location l = world.getLocation(this);
            if (world.containsNonBlocking(l)){
                world.delete(world.getNonBlocking(l));
            }
            home = new Burrow(l);
            world.setTile(l, home);
        }
        void die(){
            world.delete(this);
        }

    /** Updates sprite of the Rabbit to reflect age and time of day **/
        @Override
    public DisplayInformation getInformation() {
        if (age < 30){
            if(isNight) return new DisplayInformation(Color.red, "rabbit-small-sleeping");
            return new DisplayInformation(Color.red, "rabbit-small");
        }
        if (age >= 30){
            if (isNight) return new DisplayInformation(Color.pink, "rabbit-sleeping");
            return new DisplayInformation(Color.red, "rabbit-large");
        }
        return null;
    }
}
