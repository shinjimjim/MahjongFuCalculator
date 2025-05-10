package mahjong;

import java.util.List; //Javaの標準ライブラリから List（リスト型）を使うための宣言

import mahjong.logic.PointCalculator;
import mahjong.logic.ScoreCalculator;
import mahjong.model.AgariPattern;
import mahjong.model.Hand;
import mahjong.model.Meld;
import mahjong.model.Tile; //ScoreCalculator, Hand, Meld, Tile クラスを使うためのimport文です。
import mahjong.util.MahjongUtils.AgariAnalyzer;

public class Main { //テスト実行用のMainクラス
	public static void main(String[] args) {
        /*Tile tile1 = new Tile("man", 2); //「2萬（マンズの2）」の牌を作成。Tile クラスは suit（種類）と number（数）を持っています。*/
		/*Tile tile1 = new Tile("man", 1); // 老頭*/
		/*Tile tile1 = new Tile("man", 5); // 中張*/
        /*Meld meld1 = new Meld(Meld.Type.TRIPLE, tile1, false); //meld1 は暗刻（アンコウ）＝同じ牌3枚（例：2萬・2萬・2萬）
        													   //false は「鳴いていない＝暗刻」であることを示します。*/
		/*Meld meld1 = new Meld(Meld.Type.TRIPLE, tile1, false); // 暗刻 → 8符*/
		/*Meld meld1 = new Meld(Meld.Type.TRIPLE, tile1, false); // 暗刻 → 4符*/
        /*Meld meld2 = new Meld(Meld.Type.SEQUENCE, new Tile("pin", 3), true); //順子（シュンツ）＝3,4,5のような連続牌*/
        /*Meld meld3 = new Meld(Meld.Type.SEQUENCE, new Tile("sou", 4), true); //new Tile("pin", 3) なので「3筒」を始点とする順子（3筒, 4筒, 5筒）*/
        /*Meld meld4 = new Meld(Meld.Type.SEQUENCE, new Tile("man", 5), true); //true は「鳴いている＝明順」なので、副露した面子です。*/
        /*Tile pair = new Tile("honor", 1); //雀頭（2枚ペアの牌）を「東（1番の字牌）」で表現。*/
        /*Tile pair = new Tile("honor", 2); // 發（役牌）*/
        /*Tile pair = new Tile("honor", 5); // 南風 → 自風 or 場風に一致するなら役牌*/

        //手牌をまとめる。4つの面子（Meld）と1つの雀頭（Tile）を合わせて、1つの手牌 Hand を作成。
        /*Hand hand = new Hand(List.of(meld1, meld2, meld3, meld4), pair); //List.of(...) はJava 9以降で使える便利な書き方。*/
/*        
		//アガリ牌の指定
        Tile winTile = new Tile("man", 4); // カンチャン待ち：3-5 に対する4

        Hand hand = new Hand(List.of(
            new Meld(Meld.Type.SEQUENCE, new Tile("man", 3), true), //3-4-5 萬子、順子、明刻、アガリ牌がこの真ん中（4）
            new Meld(Meld.Type.SEQUENCE, new Tile("sou", 2), true), //2-3-4 索子、順子、明刻、通常の順子
            new Meld(Meld.Type.SEQUENCE, new Tile("pin", 6), true), //6-7-8 筒子、順子、明刻、通常の順子
            new Meld(Meld.Type.TRIPLE, new Tile("honor", 2), false) //發發發、暗刻	暗刻、字牌の暗刻 → +8符
        ), new Tile("honor", 2)); //發發、雀頭、三元牌（役牌）→ +2符
	
        //符の計算
        ScoreCalculator calc = new ScoreCalculator();
        
        /*int seatWind = 5;  // 南*/
        /*int roundWind = 5; // 南*/

        //第2引数 true は「ツモあがり」、第3引数 false は「門前ロンでない（副露あり）」を意味します。
        /*int fu = calc.calculateFu(hand, true, false, seatWind, roundWind);*/
        //seatWind→4、自風：東。roundWind→4、場風：東。winningTile→萬子の4、アガリ牌（カンチャン待ち）
        /*int fu = calc.calculateFu(hand, false, true, 4, 4, winTile);
        
        //結果の表示
        System.out.println("この手の符は: " + fu);
        
        int han = 3;
        boolean isDealer = false;
        boolean isTsumo = false;

        int point = PointCalculator.calculatePoints(han, fu, isDealer, isTsumo);
        System.out.println("得点: " + point + "点");
*/
		// 仮の2パターンのあがり形を作る（ペンチャン待ち vs タンキ待ち）
        Tile winningTile = new Tile("man", 3);

        // パターン1: ペンチャン待ち（1-2-3の3待ち）
        AgariPattern penchanPattern = new AgariPattern(
            List.of(
                new Meld(Meld.Type.SEQUENCE, new Tile("man", 1), true), //  1-2-3の順子。3萬が含まれていて、ペンチャン待ち（1-2待ちで3を引いた）。
                new Meld(Meld.Type.SEQUENCE, new Tile("sou", 2), true), // 2-3-4
                new Meld(Meld.Type.SEQUENCE, new Tile("pin", 6), true), // 6-7-8
                new Meld(Meld.Type.TRIPLE, new Tile("honor", 2), false) // 発×3（暗刻）
            ),
            new Tile("honor", 2), // 雀頭：発
            winningTile
        );

        // パターン2: タンキ待ち（雀頭の片割れで待つ）
        AgariPattern tankiPattern = new AgariPattern(
            List.of(
                new Meld(Meld.Type.SEQUENCE, new Tile("man", 3), true), // 3-4-5
                new Meld(Meld.Type.SEQUENCE, new Tile("sou", 2), true), // 2-3-4
                new Meld(Meld.Type.SEQUENCE, new Tile("pin", 6), true), // 6-7-8
                new Meld(Meld.Type.TRIPLE, new Tile("honor", 2), false) // 発×3（暗刻）
            ),
            new Tile("man", 3), // 雀頭：3m
            winningTile
        );

        // 翻・状況設定（仮）
        int han = 3;
        boolean isTsumo = false;
        boolean isDealer = false;
        int seatWind = 1;
        int roundWind = 1; //seatWind, roundWind: 自風と場風（1＝東）← 役牌判定に必要です。

        // 点数を比較して最良のアガリ形を選ぶ
        List<AgariPattern> patterns = List.of(penchanPattern, tankiPattern);
        //AgariAnalyzer.getBestPattern(...) で最も高得点のあがり形を選びます。
        AgariPattern best = AgariAnalyzer.getBestPattern(patterns, isTsumo, isDealer, han, seatWind, roundWind);

        System.out.println("--------- 最終結果 ---------");
        Hand bestHand = best.toHand();
        //符を計算: 面子の種類、雀頭、待ち方、ロン/ツモ、自風・場風などから符を出す。
        ScoreCalculator calc = new ScoreCalculator();
        int fu = calc.calculateFu(bestHand, isTsumo, !isTsumo, seatWind, roundWind, best.getWinningTile());
        //点数を計算: 翻と符から点数表に基づき、親/子、ツモ/ロンに応じて点を算出。
        int point = PointCalculator.calculatePoints(han, fu, isDealer, isTsumo);
        //最終的な最大点のあがり形と点数が出力されます。
        System.out.println("選ばれた待ち牌: " + best.getWinningTile());
        System.out.println("符: " + fu);
        System.out.println("翻: " + han);
        System.out.println("点数: " + point + "点");
    }
}
