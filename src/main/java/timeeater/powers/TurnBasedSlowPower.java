package timeeater.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static timeeater.TimeEaterMod.makeID;

public class TurnBasedSlowPower extends AbstractTimeEaterPower {
    public static final String ID = makeID(TurnBasedSlowPower.class.getSimpleName());
    public static final PowerStrings strs = CardCrawlGame.languagePack.getPowerStrings(ID);

    public TurnBasedSlowPower(AbstractCreature target, int amount) {
        super(ID,  PowerType.DEBUFF, true, target, amount);
        isTwoAmount = true;
        int resetAmt = 0;
        if (AbstractDungeon.player.hasPower(SlowDownPower.ID)) {
            resetAmt += AbstractDungeon.player.getPower(SlowDownPower.ID).amount;
        }
        amount2 = resetAmt;
    }

    @Override
    public void atEndOfRound() {
        int resetAmt = 0;
        if (AbstractDungeon.player.hasPower(SlowDownPower.ID)) {
            resetAmt += AbstractDungeon.player.getPower(SlowDownPower.ID).amount;
        }
        amount2 = resetAmt;
        updateDescription();
    }

    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        AbstractTimeEaterPower q = this;
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                q.amount2++;
            }
        });
    }

    public float atDamageReceive(float damage, DamageInfo.DamageType type) {
        return type == DamageInfo.DamageType.NORMAL ? damage * (1.0F + (float) this.amount * 0.1F) : damage;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + FontHelper.colorString(this.owner.name, "y") + DESCRIPTIONS[1];
        if (this.amount2 != 0) {
            this.description = this.description + DESCRIPTIONS[2] + this.amount2 * 10 + DESCRIPTIONS[3] + amount + (amount == 1 ? DESCRIPTIONS[4] : DESCRIPTIONS[5]);
        }
    }
}
