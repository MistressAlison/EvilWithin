package automaton.relics;

import automaton.AutomatonMod;
import automaton.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import sneckomod.SneckoMod;

import static champ.ChampMod.makeRelicOutlinePath;
import static champ.ChampMod.makeRelicPath;

public class MakeshiftBattery extends CustomRelic {

    public static final String ID = AutomatonMod.makeID("MakeshiftBattery");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("MakeshiftBattery.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("MakeshiftBattery.png"));

    public MakeshiftBattery() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public void onEquip() {
        ++AbstractDungeon.player.energy.energyMaster;
    }

    @Override
    public void onUnequip() {
        --AbstractDungeon.player.energy.energyMaster;
    }

    @Override
    public void atTurnStartPostDraw() {
        flash();
        addToBot(new MakeTempCardInDrawPileAction(SneckoMod.getRandomStatus(), 1, true, true));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}