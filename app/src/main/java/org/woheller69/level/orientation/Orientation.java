package org.woheller69.level.orientation;

/*
 *  This file is part of Level (an Android Bubble Level).
 *  <https://github.com/avianey/Level>
 *
 *  Copyright (C) 2014 Antoine Vianey
 *
 *  Level is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Level is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Level. If not, see <http://www.gnu.org/licenses/>
 */
public enum Orientation {

    LANDING(1, 0),
    TOP(1, 0),
    RIGHT(1, 90),
    BOTTOM(-1, 180),
    LEFT(-1, -90);

    private final int reverse;
    private final int rotation;

    Orientation(int reverse, int rotation) {
        this.reverse = reverse;
        this.rotation = rotation;
    }

    public int getReverse() {
        return reverse;
    }

    public int getRotation() {
        return rotation;
    }

    public boolean isLevel(float pitch, float roll, float balance, float sensibility) {
        if (sensibility < 0.2f) {
            //minimum sensibility for playing sound (play sound even if sensor sensibility is better)
            sensibility = 0.2f;
        }
        boolean b = Math.abs(pitch) <= sensibility
                || Math.abs(pitch) >= 180 - sensibility;
        switch (this) {
            case BOTTOM:
            case TOP:
                return balance <= sensibility
                        && balance >= -sensibility;
            case LANDING:
                return roll <= sensibility
                        && roll >= -sensibility
                        && b;
            case LEFT:
            case RIGHT:
                return b;
        }
        return false;
    }
}
