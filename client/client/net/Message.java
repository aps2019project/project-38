package client.net;

public enum Message {
    saveTheGame, quitTheApp, createAccount, showLeaderBoard, login, startTheGame, setAnActiveAccount, accountDeckIsValid,
    accountDeckIsNotValid, updateMessages, sendMessage, logOut,

    //////////////game info
    HeroPowerOfPlayer, WarriorOfACell, IndexofAvatar, PlayerUsername, SpecificCell, NextCard, HandCard, ActivePlayerIndex, isMyWarrior, isThereWarrior, itsWarrior, itsSpell,
    //////////////game actions
    put, cast, setCoolDown, setActivePlayer, buildPlayerHand, useCard, useSpecialPower, useCollectible, attack, comboAttack,
    move, endTurn, quitTheGame,
    //////////////
}