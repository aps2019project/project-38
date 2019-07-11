package server.net;

public enum Message {
    saveTheGame, quitTheApp, createAccount, showLeaderBoard, login, startTheGame, setAnActiveAccount, accountDeckIsValid,
    accountDeckIsNotValid, updateRanking, updateMessages, sendMessage, logOut,

    //////////////game actions:
    put, cast, setCoolDown, setActivePlayer, buildPlayerHand, useCard, useSpecialPower, useCollectible, attack, comboAttack,
    move, endTurn, quitTheGame, mana, graveYard,

    ////////////ali:
}
