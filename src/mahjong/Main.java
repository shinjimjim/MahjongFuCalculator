package mahjong;

import java.util.List; //Javaの標準ライブラリから List（リスト型）を使うための宣言

import mahjong.logic.ScoreCalculator;
import mahjong.model.Hand;
import mahjong.model.Meld;
import mahjong.model.Tile; //ScoreCalculator, Hand, Meld, Tile クラスを使うためのimport文です。

public class Main { //テスト実行用のMainクラス
	public static void main(String[] args) {
        /*Tile tile1 = new Tile("man", 2); //「2萬（マンズの2）」の牌を作成。Tile クラスは suit（種類）と number（数）を持っています。*/
		/*Tile tile1 = new Tile("man", 1); // 老頭*/
		Tile tile1 = new Tile("man", 5); // 中張
        /*Meld meld1 = new Meld(Meld.Type.TRIPLE, tile1, false); //meld1 は暗刻（アンコウ）＝同じ牌3枚（例：2萬・2萬・2萬）
        													   //false は「鳴いていない＝暗刻」であることを示します。*/
		/*Meld meld1 = new Meld(Meld.Type.TRIPLE, tile1, false); // 暗刻 → 8符*/
		Meld meld1 = new Meld(Meld.Type.TRIPLE, tile1, false); // 暗刻 → 4符
        Meld meld2 = new Meld(Meld.Type.SEQUENCE, new Tile("pin", 3), true); //順子（シュンツ）＝3,4,5のような連続牌
        Meld meld3 = new Meld(Meld.Type.SEQUENCE, new Tile("sou", 4), true); //new Tile("pin", 3) なので「3筒」を始点とする順子（3筒, 4筒, 5筒）
        Meld meld4 = new Meld(Meld.Type.SEQUENCE, new Tile("man", 5), true); //true は「鳴いている＝明順」なので、副露した面子です。
        /*Tile pair = new Tile("honor", 1); //雀頭（2枚ペアの牌）を「東（1番の字牌）」で表現。*/
        /*Tile pair = new Tile("honor", 2); // 發（役牌）*/
        Tile pair = new Tile("honor", 5); // 南風 → 自風 or 場風に一致するなら役牌

        //手牌をまとめる。4つの面子（Meld）と1つの雀頭（Tile）を合わせて、1つの手牌 Hand を作成。
        Hand hand = new Hand(List.of(meld1, meld2, meld3, meld4), pair); //List.of(...) はJava 9以降で使える便利な書き方。
        //符の計算
        ScoreCalculator calc = new ScoreCalculator();
        
        int seatWind = 5;  // 南
        int roundWind = 5; // 南

        //第2引数 true は「ツモあがり」、第3引数 false は「門前ロンでない（副露あり）」を意味します。
        int fu = calc.calculateFu(hand, true, false, seatWind, roundWind);
        //結果の表示
        System.out.println("この手の符は: " + fu);
    }
}
