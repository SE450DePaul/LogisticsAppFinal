package test.inventoryservice;

import logistics.inventoryservice.InventoryService;
import logistics.inventoryservice.dtos.FacilityWithItemDTO;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by uchennafokoye on 5/13/16.
 */

@RunWith(Parameterized.class)
public class InventoryServiceTest {

    private String itemId;
    private FacilityWithItemDTO expected;
    private Collection<FacilityWithItemDTO> expected_all;

    public InventoryServiceTest(String itemId, FacilityWithItemDTO expected, Collection<FacilityWithItemDTO> expected_all){
        this.itemId = itemId;
        this.expected = expected;
        this.expected_all = expected_all;
    }


    @Parameters
    public static Collection<Object[]> data()  {
        return Arrays.asList(new Object[][] {
                { null, null, null},
                {"ABC123", new FacilityWithItemDTO("Denver, CO", 80), null},
                {"RL123A", null, Arrays.asList(
                                new FacilityWithItemDTO("Denver, CO", 110),
                                new FacilityWithItemDTO("San Francisco, CA", 40),
                                new FacilityWithItemDTO("New York City, NY", 12),
                                new FacilityWithItemDTO("Norfolk, VA", 50),
                                new FacilityWithItemDTO("Austin, TX", 66))
                }
        });
    }

    static InventoryService instance = null;

    @BeforeClass
    public static void setup() {
        instance = InventoryService.getInstance();
    }

    @Test
    public void testGetFacilitiesWithItem() {
        Collection<FacilityWithItemDTO> actual = instance.getFacilityWithItemDTOs(itemId);
        if (itemId == null){
            assertEquals(expected, actual);
        }

        if (expected != null){
            boolean result = actual.contains(expected);
            assertTrue(result);
        }

        if (expected_all != null){
            boolean result = actual.containsAll(expected_all);
            assertTrue(result);
        }
    }


    @After
    public void teardown() {

    }
}
