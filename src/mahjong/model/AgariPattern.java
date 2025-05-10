package mahjong.model;

import java.util.List; //List を使って 複数の面子（Meld） を扱うためのインポート

//麻雀の「アガリ形」（あがりけい）＝アガれる牌の並び方の1通り を表現するためのクラス
public class AgariPattern {
	//フィールド変数
	private List<Meld> melds; //4つの面子（順子・刻子など）を Meld のリストで表現
    private Tile pair; //雀頭（ジャントウ） = 同じ牌2枚の対子
    private Tile winningTile; //アガリ牌。どの牌でアガったか（待ちを評価するために使う）

    //コンストラクタ
    public AgariPattern(List<Meld> melds, Tile pair, Tile winningTile) {
        this.melds = melds;
        this.pair = pair;
        this.winningTile = winningTile; //melds, pair, winningTile を外から受け取り、自身の変数に保存しています。
    }

    //ゲッター（各要素を取得）：他のクラスから AgariPattern の構成要素を取得したいときに使います。private フィールドを安全に外部に渡すための慣習です。
    public List<Meld> getMelds() {
        return melds;
    }

    public Tile getPair() {
        return pair;
    }

    public Tile getWinningTile() {
        return winningTile;
    }

    //他のロジック（符計算など）は Hand を使って動作するので、そこに渡すための変換用です。
    public Hand toHand() { //この AgariPattern を Hand オブジェクト（手牌全体の構成）に変換するためのメソッド
        return new Hand(melds, pair);
    }
}
