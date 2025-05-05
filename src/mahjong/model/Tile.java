package mahjong.model;

public class Tile { //牌クラス「1つの牌」　麻雀牌を表現する基本単位です。
	private String suit; // 牌の種類（スート）"man：萬子（マンズ）", "pin：筒子（ピンズ）", "sou：索子（ソウズ）", "honor：字牌（東南西北白發中）"
    private int number;  // 牌の数字（番号）　数牌（萬・筒・索）は1~9 or 字牌は 1〜7（1:東, 2:南, ..., 7:中）1~7

    //コンストラクタ
    public Tile(String suit, int number) { //新しい牌を作るときに使います。
        this.suit = suit;
        this.number = number; //new Tile("man", 3) → 「3萬」という牌のインスタンスができます。
    }

    //ゲッター（外部から牌の情報を取得）
    public String getSuit() { return suit; } //getSuit()：この牌がどのスート（種類）か取得
    public int getNumber() { return number; } //getNumber()：この牌の数字（番号）を取得

    @Override
    public String toString() { //この牌を文字列にしたときの表現。new Tile("man", 3).toString() → "man3"
        return suit + number;
    }
}
