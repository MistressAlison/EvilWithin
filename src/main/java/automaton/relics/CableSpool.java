package automaton.relics;

import automaton.AutomatonMod;
import automaton.actions.AddToFuncAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static automaton.AutomatonMod.*;

public class CableSpool extends CustomRelic {

    public static final String ID = AutomatonMod.makeID("CableSpool");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("CableSpool.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("CableSpool.png"));

    public CableSpool() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        this.grayscale = false;
    }

    @Override
    public void onVictory() {
        this.grayscale = false;
    }


    public void onUseCard(AbstractCard c, UseCardAction action) {
        if (!grayscale && c.hasTag(ENCODES)) {
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractCard c2 = c.makeCopy();
            addToBot(new AddToFuncAction(c2, null));
            grayscale = true;
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
