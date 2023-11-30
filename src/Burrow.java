import itumulator.world.NonBlocking;
import itumulator.world.Location;
import itumulator.world.World;

public class Burrow implements NonBlocking {
    Location l;
    Burrow(Location l){
        this.l = l;
    }
    public void toggleHide(Rabbit rabbit){
        World world = rabbit.world;
        /** If this Rabbit doesn't already have a home set this burrow as its home **/
        if (rabbit.home == null){
            rabbit.home = this;
        }
        /** If already burrowed and the tile 'above' is empty, return to world and set burrowed to false **/
        if (rabbit.burrowed && world.isTileEmpty(l)){
            world.setTile(l, rabbit);
            rabbit.burrowed = false;

            /** If NOT already burrowed, remove from world and set burrowed to true **/
        } else if (!rabbit.burrowed){
           world.remove(rabbit);
           rabbit.burrowed = true;
        }
    }
}
