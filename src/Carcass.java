import itumulator.executable.DisplayInformation;
import itumulator.simulator.Actor;
import itumulator.world.Location;
import itumulator.world.NonBlocking;
import itumulator.world.World;

import java.awt.*;
public class Carcass implements NonBlocking, Actor {
    int RotRate = 0;

    public Carcass(String size) {
        if (size.equals("Small") || size.equals("small")) {
            new DisplayInformation(Color.red, "carcass-small");
        }
        if (size.equals("Large")  || size.equals("large")) {
            new DisplayInformation(Color.pink, "carcass");
        }
    }

    @Override
    public void act(World world) {
        RotRate++;
        if ( RotRate == 45 ) {
            Location l = world.getLocation(this);
            world.delete(this);
            world.setTile(l, new Grass());
        }
    }
}
