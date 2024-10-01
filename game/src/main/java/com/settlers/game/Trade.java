package com.settlers.game;

import java.util.HashMap;
import java.util.Map;

public record Trade(Player offeringPlayer,
                    Player receivingPlayer,
                    Map<Resource, Integer> offer,
                    Map<Resource, Integer> receive) {
    public Trade {
        assert offeringPlayer != null;
        assert receivingPlayer != null;
        assert offer != null;
        assert receive != null;
    }

    public static final class Builder {
        private final Map<Resource, Integer> offer;
        private final Map<Resource, Integer> receive;
        private Builder() {
            Map<Resource, Integer> offer = new HashMap<>();
            for (Resource resource : Resource.values()) {
                if (resource == Resource.NOTHING) continue;
                offer.put(resource, 0);
            }
            this.offer = offer;
            Map<Resource, Integer> receive = new HashMap<>();
            for (Resource resource : Resource.values()) {
                if (resource == Resource.NOTHING) continue;
                receive.put(resource, 0);
            }
            this.receive = receive;
        }

        public Trade build(Player offeringPlayer, Player receivingPlayer) {
            return new Trade(offeringPlayer, receivingPlayer, offer, receive);
        }

        public Builder offerBrick(int amount) {
            assert amount >= 0;
            offer.merge(Resource.BRICK, amount, Integer::sum);
            return this;
        }

        public Builder offerLumber(int amount) {
            assert amount >= 0;
            offer.merge(Resource.LUMBER, amount, Integer::sum);
            return this;
        }

        public Builder offerOre(int amount) {
            assert amount >= 0;
            offer.merge(Resource.ORE, amount, Integer::sum);
            return this;
        }

        public Builder offerGrain(int amount) {
            assert amount >= 0;
            offer.merge(Resource.GRAIN, amount, Integer::sum);
            return this;
        }

        public Builder offerWool(int amount) {
            assert amount >= 0;
            offer.merge(Resource.WOOL, amount, Integer::sum);
            return this;
        }

        public Builder receiveBrick(int amount) {
            assert amount >= 0;
            receive.merge(Resource.BRICK, amount, Integer::sum);
            return this;
        }

        public Builder receiveLumber(int amount) {
            assert amount >= 0;
            receive.merge(Resource.LUMBER, amount, Integer::sum);
            return this;
        }

        public Builder receiveOre(int amount) {
            assert amount >= 0;
            receive.merge(Resource.ORE, amount, Integer::sum);
            return this;
        }

        public Builder receiveGrain(int amount) {
            assert amount >= 0;
            receive.merge(Resource.GRAIN, amount, Integer::sum);
            return this;
        }

        public Builder receiveWool(int amount) {
            assert amount >= 0;
            receive.merge(Resource.WOOL, amount, Integer::sum);
            return this;
        }
    }
}
