package hermit.powers;

import basemod.abstracts.cardbuilder.actionbuilder.PoisonActionBuilder;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.PoisonLoseHpAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.SwordBoomerang;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import hermit.HermitMod;
import hermit.patches.EnumPatch;
import hermit.relics.RyeStalk;
import hermit.relics.Spyglass;
import hermit.util.TextureLoader;

import static hermit.HermitMod.makePowerPath;

//Gain 1 dex for the turn for each card played.

public class Bruise extends AbstractPower implements CloneablePowerInterface, HealthBarRenderPower {
    public AbstractCreature source;

    public static final String POWER_ID = HermitMod.makeID("Bruise");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("bruise_p.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("bruise.png"));


    public Bruise(final AbstractCreature owner, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;

        priority = 4;

        type = PowerType.DEBUFF;

        if (this.amount <= 0) {
            this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.POWER_ID));
        }

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();

        this.isTurnBased = true;
    }

    public float atDamageReceive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL) {
            return damage +this.amount;
        } else {
            return damage;
        }
    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type == DamageInfo.DamageType.NORMAL) {
                this.flashWithoutSound();
                //this.addToTop(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
                //updateDescription();
        }

        return damageAmount;
    }

    public void atStartOfTurn() {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            if (AbstractDungeon.player.hasRelic(RyeStalk.ID)) {
                this.flashWithoutSound();
                this.addToBot(new PoisonLoseHpAction(this.owner, this.source, this.amount, EnumPatch.HERMIT_GHOSTFIRE));
            }
            if (!this.owner.hasPower(HorrorPower.POWER_ID))
            {
                this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
            }
        }
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public int getHealthBarAmount(){
        if (AbstractDungeon.player.hasRelic(RyeStalk.ID)) {
            return this.amount;
        }
        else
            return 0;
    }

    @Override
    public Color getColor()
    {
        return Color.CYAN;
    }

    @Override
    public AbstractPower makeCopy() {
        return new Bruise(owner, amount);
    }
}
