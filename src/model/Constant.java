package model;


public interface Constant {
    interface GameConstants {
        int turnTime = 20000;
        int handSize = 5;
        int primaryMana = 10000000;
    }

    interface BoardConstants {
        int row = 5;
        int column = 9;
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

        interface HolyBuff{
            int holyBuffReducedDamage = 1;
        }

        interface FiredCell{
            int firedCellDamage = 2;
        }

        interface WoundDeepener{
            int additionalDamage = 5;
        }
    }
}
