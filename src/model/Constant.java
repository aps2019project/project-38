package model;


public interface Constant {
    interface GameConstants {
        int turnTime = 20000;
        int handSize = 5;
        int boardRow = 5;
        int boardColumn = 9;
        int collectingFlagModeFlags = 7;
        int carryingFlagModeWinScore = 6;
        int defaultPrize = 1000;

        int winnerPriceForLevel1 = 500;
        int winnerPriceForLevel2 = 1000;
        int winnerPriceForLevel3 = 1500;

        static int getTurnMana(int turn) {
            int mana = (turn + 5) / 2;
            return mana > 9 ? 9 : mana;
        }
    }

    interface WarriorConstants {
        int maxMove = 2;
    }

    interface EffectsTriggersConstants{
        interface CellPoison{
            int poisonBuffDuration = 3;
        }

        interface PoisonBuff{
            int poisonBuffDamage = 1;
        }

        interface FiredCell{
            int firedCellDamage = 2;
        }

        interface WoundDeepener{
            int additionalDamage = 5;
        }
    }
}
