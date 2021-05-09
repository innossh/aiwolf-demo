package innossh.aiwolf.demo;

import org.aiwolf.sample.lib.AbstractRoleAssignPlayer;

public class DemoRoleAssignPlayer extends AbstractRoleAssignPlayer {

    public DemoRoleAssignPlayer() {
        setSeerPlayer(new DemoSeer());
    }

    @Override
    public String getName() {
        return "DemoAgent";
    }
}
