package innossh.aiwolf.demo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DemoRoleAssignPlayerTest {
    @Test
    void testGetName() {
        DemoRoleAssignPlayer demoAgent = new DemoRoleAssignPlayer();
        assertEquals(demoAgent.getName(), "DemoAgent");
    }
}
