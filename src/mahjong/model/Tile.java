package mahjong.model;

public class Tile { //牌クラス「1つの牌」　麻雀牌を表現する基本単位です。
	//フィールド（状態）
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

    //牌の分類（便利な判定メソッド）
    public boolean isHonor() { //字牌か？
        return "honor".equals(suit); //この牌が「東南西北白發中」などの字牌かどうか判定します。
    }

    public boolean isTerminal() { //老頭牌（1 or 9）か？
        return !isHonor() && (number == 1 || number == 9); //数牌の中で **1または9の端っこ（老頭牌）**かどうかを判定。
    }

    public boolean isSimple() { //中張牌（2〜8）か？
        return !isHonor() && number >= 2 && number <= 8; //「中張牌」と呼ばれる、2〜8の数牌かどうかを判定。
    }

    @Override
    public String toString() { //この牌を文字列にしたときの表現。new Tile("man", 3).toString() → "man3"
        return suit + number;
    }
    
    //equals と hashCode（比較やハッシュ）
    @Override
    public boolean equals(Object o) { //equals（同じ牌か？）2つのTileが同じ牌かどうかを判定できます。
        if (o instanceof Tile) {
            Tile t = (Tile) o;
            return suit.equals(t.suit) && number == t.number;
        }
        return false;
    }

    @Override
    public int hashCode() { //hashCode（ハッシュ値を生成）
        return suit.hashCode() * 10 + number; //HashMapやSetで使うときのために、**一意な数値（ハッシュ値）**を出しています。
    }
}
