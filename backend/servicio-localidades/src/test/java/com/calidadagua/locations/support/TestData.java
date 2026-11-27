package com.calidadagua.locations.support;

import com.calidadagua.locations.domain.model.*;

import java.util.UUID;

public final class TestData {

    private TestData() {
    }

    public static Department department() {

        Department department = new Department();

        department.setId(UUID.randomUUID());
        department.setName("Santa Cruz");

        return department;
    }

    public static Municipality municipality() {

        Municipality municipality = new Municipality();

        municipality.setId(UUID.randomUUID());
        municipality.setName("Samaipata");
        municipality.setDepartment(department());

        return municipality;
    }

    public static Community community() {

        Community community = new Community();

        community.setId(UUID.randomUUID());
        community.setName("Test Community");
        community.setMunicipality(municipality());

        return community;
    }

    public static WaterBody waterBody() {

        WaterBody waterBody = new WaterBody();

        waterBody.setId(UUID.randomUUID());
        waterBody.setName("Test River");
        waterBody.setType(WaterBodyType.RIVER);
        waterBody.setDescription("Monitoring site");
        waterBody.setLatitude(-18.18);
        waterBody.setLongitude(-63.87);
        waterBody.setCommunity(community());

        return waterBody;
    }
}