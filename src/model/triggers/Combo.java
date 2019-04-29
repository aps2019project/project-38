package model.triggers;
//
//import model.cards.warriors.Warrior;
//import model.conditions.IsAttacking;
//import model.effects.Dispelablity;
//import model.gamestate.Attack;
//import model.gamestate.GameState;
////this class is a failure.
//public class Combo extends Trigger{
//    {
//        conditions.add(new IsAttacking());
//    }
//    public Combo(Warrior warrior, int duration, Dispelablity dispelablity) {
//        super(warrior, duration, dispelablity);
//    }
//
//    @Override
//    void apply(GameState gameState) {
//
//    }
//
//    private long calculateComboDamage(Attack attack){
//        return attack.getAttacked().getCell().getGame().getEightAdjacents(getWarrior().getCell()).
//                stream().filter(cell -> cell.getWarrior()!=null).
//                filter(cell -> cell.getWarrior().getClass()==getWarrior().getClass()).count();
//    }
//}
