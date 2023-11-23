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

    Rabbit(World world){
        this.world = world;
        this.food = 50;
        this.vision_range = 2;
    }
    @Override
    public void act(World world) {
        isNight = world.isNight();
        age++;
        this.food -= max(1, (age / 20));
        if (food <= 0) {
            die();
        } else {
            if (!isNight) {
                if (world.containsNonBlocking(world.getLocation(this)) && food < 100) {
                    eat();
                } else {
                    randomMove();
                }
            } else {
                if (food >= 100){
                    reproduce();
                }
            }
        }
    }
        @Override
        public void eat(){
            world.delete(world.getNonBlocking(world.getLocation(this)));
            this.food += 25;
        }

        @Override
        void reproduce() {
            this.food -= 75;
            List<Location> list = new ArrayList<>(world.getEmptySurroundingTiles());
            Random r = new Random();
            Location l = list.get(r.nextInt(list.toArray().length - 1));
            world.setTile(l, new Rabbit(world));
        }
        void createHole() {

        }
        void die(){
            world.delete(this);
        }
        void seekFood(){


        }

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
