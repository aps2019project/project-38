package model;


public interface Constant {
    interface GameConstants {
        int turnTime = 20000;
        int handSize = 5;
        int boardRow = 5;
        int boardColumn = 9;
        int collectingFlagMoodFlags = 7;
        int carryingFlagMoodWinScore = 6;
        int defaultPrise = 3;

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
