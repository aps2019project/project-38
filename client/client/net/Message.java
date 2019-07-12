package client.net;

public enum Message {
    saveTheGame, quitTheApp, createAccount, showLeaderBoard, login, startTheGame, setAnActiveAccount, accountDeckIsValid,
    accountDeckIsNotValid, updateRanking, updateMessages, sendMessage, logOut,showPopup,

    //////////////game info
    HeroPowerOfPlayer, WarriorOfACell, IndexofAvatar, PlayerUsername, ActivePlayerIndex, isMyWarrior, isThereWarrior, itsWarrior, itsSpell,
    //////////////game actions
    put, cast, setCoolDown, setActivePlayer, buildPlayerHand, useCard, useSpecialPower, useCollectible, attack, comboAttack,
    move, endTurn, quitTheGame, mana, graveYard,showCollectedCollectibleItems,kill,

    ////////ali:
    AuctionProposedPrice, StartNewAuction,

    AuctionResult, AuctionMaxProposedPriceUpdated,
}
