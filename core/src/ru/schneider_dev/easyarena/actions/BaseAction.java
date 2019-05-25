package ru.schneider_dev.easyarena.actions;

import ru.schneider_dev.easyarena.Unit;

public abstract class BaseAction {
    public String getName() {
        return name;
    }

    String name;
    String textureName;

    public BaseAction(String name, String textureName) {
        this.name = name;
        this.textureName = textureName;
    }

    public String getTextureName() {
        return textureName;
    }

    public abstract boolean action(Unit me);

}
