package com.tsoft.civilization.tile.util;

import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.tile.base.Coast;
import com.tsoft.civilization.tile.base.Grassland;
import com.tsoft.civilization.tile.resource.AbstractResource;
import com.tsoft.civilization.tile.resource.Aluminium;
import com.tsoft.civilization.tile.resource.Iron;
import com.tsoft.civilization.tile.resource.Oil;
import com.tsoft.civilization.tile.resource.ResourceType;
import com.tsoft.civilization.tile.resource.Uranium;
import com.tsoft.civilization.world.generator.Climate;
import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.util.Point;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

@Slf4j
public class ResourceList {
    private class ResourceInfo {
        private Class<? extends AbstractResource> resourceClass;

        private int count;

        private ResourceType resourceType;

        public ResourceInfo(Class<? extends AbstractResource> resourceClass, int count) {
            this.resourceClass = resourceClass;
            this.count = count;

            AbstractResource resource = AbstractResource.newInstance(resourceClass);
            resourceType = resource.getResourceType();
        }

        public Class<? extends AbstractResource> getResourceClass() {
            return resourceClass;
        }

        public int getCount() {
            return count;
        }

        public ResourceType getResourceType() {
            return resourceType;
        }
    }

    private ArrayList<ResourceInfo> resourceInfoList = new ArrayList<>();

    private TilesMap tilesMap;

    private Climate climate;

    public ResourceList(TilesMap tilesMap, Climate climate) {
        this.tilesMap = tilesMap;
        this.climate = climate;
    }

    public void addToMap() {
        buildResourceList();

        addResourcesToMap();
    }

    private void buildResourceList() {
        resourceInfoList.clear();

        switch (climate) {
            case NORMAL: {
                // calculate the number of Resources depending on Map's size
                int count = (int)(Math.sqrt(tilesMap.getWidth() * tilesMap.getHeight()));

                resourceInfoList.add(new ResourceInfo(Aluminium.class, count / 14));
                resourceInfoList.add(new ResourceInfo(Iron.class, count / 16));
                resourceInfoList.add(new ResourceInfo(Oil.class, count / 12));
                resourceInfoList.add(new ResourceInfo(Uranium.class, count / 14));
                break;
            }

            default: {
                throw new IllegalArgumentException("Unknown Climate =" + climate.name());
            }
        }
    }

    // Add resources to the Map
    private void addResourcesToMap() {
        addResource(Coast.class, ResourceType.EARTH_SEA);
        addResource(Grassland.class, ResourceType.EARTH);
    }

    private void addResource(Class<? extends AbstractTile<?>> tileClass, ResourceType resourceType) {
        ArrayList<Point> tilePoints = tilesMap.getTileClassLocations(tileClass);
        if (tilePoints.size() == 0) {
            log.warn("There is no Tiles class = {}, so Resource with type = {} is skipped",  tileClass.getName(), resourceType.name());
            return;
        }

        for (ResourceInfo resourceInfo : resourceInfoList) {
            if (resourceInfo.getResourceType() != resourceType) {
                continue;
            }

            int count = 0;
            for (int n = 0 ; n < resourceInfo.getCount(); n ++) {
                AbstractResource resource = AbstractResource.newInstance(resourceInfo.getResourceClass());

                int index = (int)(Math.random() * tilePoints.size());
                Point point = tilePoints.get(index);
                AbstractTile<?> tile = tilesMap.getTile(point);
                if (tile.setResource(resource)) {
                    count ++;
                }
            }

            log.info("Added {} resources of {} type", count, resourceInfo.getResourceClass().getName());
        }
    }
}
