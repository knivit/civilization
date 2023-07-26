package com.tsoft.civilization.unit.action.move;

import com.tsoft.civilization.util.dir.AbstractDir;
import lombok.Getter;

@Getter
class DirPassScore {

    private final int passScore;
    private final AbstractDir dir;
    private final int tileY;

    DirPassScore(int passScore, AbstractDir dir, int tileY) {
        this.passScore = passScore;
        this.dir = dir;
        this.tileY = tileY;
    }
}
