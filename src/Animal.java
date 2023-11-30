import itumulator.simulator.Actor;
import itumulator.world.Location;
import itumulator.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public abstract class Animal implements Actor {
    World world;
    boolean isNight = false;
    int vision_range;
    int age;
    int energy;

    String size;
    Location loc;
    Animal(World world, Location loc){
        this.world = world;
        this.loc = loc;
    }
    public void eat(){
        world.delete(world.getNonBlocking(loc));
        this.energy += 25;
    }

    void die(){
        world.delete(this);
        world.delete(world.getNonBlocking(loc));
        world.setTile(loc, new Carcass(size));
    }
    abstract void reproduce();

    void randomMove(){
        List<Location> list = new ArrayList<>(world.getEmptySurroundingTiles());
        if (list.toArray().length > 0) {
            Random r = new Random();
            Location l = list.get(r.nextInt(list.toArray().length));
            world.move(this, l);
        }
    }
    void moveTowards(Location location){
        if (location == null){
            randomMove();
        } else {
            int x = world.getLocation(this).getX();
            int y = world.getLocation(this).getY();
            if (location.getX() > x) {
                x++;
            } else if (location.getX() < x) {
                x--;
            }
            if (location.getY() > y) {
                y++;
            } else if (location.getY() < y) {
                y--;
            }
            Location l = new Location(x, y);
            if (world.isTileEmpty(l)) {
                world.move(this, l);
            }
        }
    }
    public Location search(Class c){
        for (int i = 1 ; i <= vision_range; i++){
            Set<Location> tiles = world.getSurroundingTiles(loc, i);
            for (Location tile : tiles){
                if (c.isInstance(world.getTile(tile))) {
                    return tile;
                } else if (world.containsNonBlocking(tile) && c.isInstance(world.getNonBlocking(tile))){
                    return tile;
                }
            }
        }
        return null;
    }
}
