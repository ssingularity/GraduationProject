package com.sjtu.project.coreservice.domain.topology;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class TopologyTest {
    @Test(expected = IllegalArgumentException.class)
    public void testIllegalTopology() {
        Topology dataSource = createDataSourceTopology("", "test");
        dataSource.addInput(new Topology());
    }

    @Test
    public void testTopology() {
        Topology dataSourceA = createDataSourceTopology("", "DataSourceA");
        Topology dataSourceB = createDataSourceTopology("", "DataSourceB");
        Topology serviceA = createServiceTopology("", "ServiceA");
        serviceA.addInput(dataSourceA);
        serviceA.addInput(dataSourceB);
        assertTrue(serviceA.isAccessorOf(dataSourceA));
        assertTrue(serviceA.isAccessorOf(dataSourceB));
    }

    private Topology createDataSourceTopology(String id, String name) {
        Topology topology = new Topology();
        topology.setType(Type.DataSource);
        topology.setId(id);
        topology.setName(name);
        return topology;
    }

    private Topology createServiceTopology(String id, String name) {
        Topology topology = new Topology();
        topology.setType(Type.Service);
        topology.setId(id);
        topology.setName(name);
        return topology;
    }
}
