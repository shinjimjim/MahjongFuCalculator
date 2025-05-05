package mahjong.model;

import java.util.List; //List は複数のデータ（ここでは面子）をまとめて扱えるコレクション型です。

public class Hand { //手牌クラス　　このクラスは麻雀の1人のプレイヤーのあがり形の手牌を表現するものです。あがりの形（4面子 + 1雀頭）を保持します。
	private List<Meld> melds; // 4面子　Meld は面子（シュンツ、コーツ、カンツ）のクラスです。
    private Tile pair;        // 雀頭　Tileは個々の牌を表すクラスです。

    //コンストラクタ
    //このクラスをnewするときにメンツと雀頭を受け取って、内部にセットします。
    public Hand(List<Meld> melds, Tile pair) {
        this.melds = melds; //this.melds = melds; のように this を使うことで、フィールドと引数を区別しています。
        this.pair = pair;
    }

    //ゲッター
    public List<Meld> getMelds() { return melds; } //getMelds()：この手牌が持っている4面子のリストを返します。
    public Tile getPair() { return pair; } //getPair()：この手牌の雀頭（1組の対子）を返します。
}
