package ru.schneider_dev.easyarena.specialFxs;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.schneider_dev.easyarena.Assets;

public class PoisonSpecialFx extends SpecialFX {


    public PoisonSpecialFx() {
        texture = Assets.getInstance().getAtlas().findRegion("poison");
        TextureRegion[][] tr = new TextureRegion(texture).split(164, 150);
        maxFrames = (tr[0].length) * (tr.length); // число картинок
        int counter = 0;
        regions = new TextureRegion[maxFrames];
        for (int i = 0; i < tr.length; i++) {
            for (int j = 0; j < tr[0].length; j++) {
                regions[counter] = tr[i][j];
                counter++;
            }
        }
    }


}
