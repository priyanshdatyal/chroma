package com.soultalkproduction.chroma;

import android.annotation.TargetApi;
import android.os.Build;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;

@TargetApi(Build.VERSION_CODES.N)
class EnaDisa extends TileService {
    @Override
    public void onClick() {
        super.onClick();

        if (!AnotherReq.hasPermission(this)) {
            showDialog(AnotherReq.WarnDialog(this));
            return;
        }

        int oldState = getQsTile().getState();
        if (oldState == Tile.STATE_ACTIVE) {
            setState(Tile.STATE_INACTIVE);
        } else {
            setState(Tile.STATE_ACTIVE);
        }

        AnotherReq.toggleGrayscale(this, oldState == Tile.STATE_INACTIVE);
    }


    private void setState(int state) {
        Tile tile = getQsTile();
        tile.setState(state);
        tile.updateTile();
    }

    @Override
    public void onStartListening() {
        super.onStartListening();
        boolean grayscaleEnable = AnotherReq.isGrayscaleEnable(this);
        setState(grayscaleEnable ? Tile.STATE_ACTIVE : Tile.STATE_INACTIVE);
    }
}
