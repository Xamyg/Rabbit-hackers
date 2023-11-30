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
        if (rabbit.burrowed && world.isTileEmpty(l)){
            world.setTile(l, rabbit);
            System.out.println("Burrowed = false");
            rabbit.burrowed = false;

        } else if (!rabbit.burrowed){
           world.remove(rabbit);
           rabbit.burrowed = true;
        }
    }
}
