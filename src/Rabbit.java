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

    Rabbit(World world, Location loc){
        super(world, loc);
        this.energy = 50;
        this.vision_range = 2;
        this.size = "small";
    }
    @Override
    public void act(World world) {
        if (world.getCurrentLocation() != null) {
            loc = world.getCurrentLocation();
        }
        isNight = world.isNight();
        age++;
        this.energy -= max(1, (age ));
        if (energy <= 0) {
            die();
        } else {
            if (!isNight) { // If it's daytime:
                if (burrowed) {
                    home.toggleHide(this);
                } else {
                    if (world.containsNonBlocking(loc) && world.getNonBlocking(loc) instanceof Grass && energy < 100) {
                        eat();
                    } else if (energy < 100) {
                        moveTowards(search(Grass.class));
                    } else {
                        randomMove();
                    }
                }
            } else if (isNight) { // If it's nighttime:
                if (home == null) {
                    Location l = search(Burrow.class);
                    if (world.containsNonBlocking(loc) && world.getNonBlocking(loc) instanceof Burrow) {
                        ((Burrow) world.getNonBlocking(loc)).toggleHide(this);
                    } else if (l == null) {
                        createBurrow();
                        home.toggleHide(this);
                    } else {
                        moveTowards(l);
                    }
                } else if (!burrowed) {

                    /** Checks if Rabbit is on the same tile as its home and if so hides in it **/

                    if (world.getLocation(home).getX() == loc.getX() && world.getLocation(home).getY() == loc.getY()) {
                        home.toggleHide(this);
                    } else {
                        /** Else move towards Rabbit's home **/
                        moveTowards(world.getLocation(home));
                    }
                } else if (energy >= 100) { /** If  it is burrowed and have enough energy: Reproduce **/
                    reproduce();
                }
            }
        }
    }
        /** Deletes Grass object to restore energy **/
        /** Creates a new Rabbit object at the cost of energy **/
        @Override
        void reproduce() {
            this.energy -= 75;
            List<Location> list = new ArrayList<>(world.getEmptySurroundingTiles(world.getLocation(home)));
            Random r = new Random();
            Location l = list.get(r.nextInt(list.toArray().length - 1));
            world.setTile(l, new Rabbit(world, l));
        }
        /** Creates new Burrow object at current location and sets it as home **/
        void createBurrow() {
            Location l = loc;
            if (world.containsNonBlocking(l)){
                world.delete(world.getNonBlocking(l));
            }
            home = new Burrow(l);
            world.setTile(l, home);
        }


    /** Updates sprite of the Rabbit to reflect age **/
        @Override
    public DisplayInformation getInformation() {
        if (age < 30){
            return new DisplayInformation(Color.red, "rabbit-small");
        }
        if (age >= 30){
            return new DisplayInformation(Color.red, "rabbit-large");
        }
        return null;
    }
}
