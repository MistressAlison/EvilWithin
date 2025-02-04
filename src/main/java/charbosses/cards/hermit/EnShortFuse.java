package charbosses.cards.hermit;

import charbosses.powers.bossmechanicpowers.HermitConcentrationPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import hermit.cards.GhostlyPresence;
import hermit.cards.Shortfuse;
import hermit.characters.hermit;

public class EnShortFuse extends AbstractHermitBossCard {
    public static final String ID = "downfall_Charboss:ShortFuse";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(Shortfuse.ID);

    public EnShortFuse() {
        super(ID, cardStrings.NAME, "hermitResources/images/cards/short_fuse.png", 3, cardStrings.DESCRIPTION, CardType.ATTACK, hermit.Enums.COLOR_YELLOW, CardRarity.UNCOMMON, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK);
        this.baseDamage = 18;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        addToBot(new DamageAction(p, new DamageInfo(m, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
    }

    public void updateCostToSpecific(int specific) {
        setCostForTurn(specific);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(4);
        }
    }


    @Override
    public AbstractCard makeCopy() {
        return new EnShortFuse();
    }
}
