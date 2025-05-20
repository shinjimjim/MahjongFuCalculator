package mahjong.logic;

import java.util.List;

import mahjong.model.AgariPattern;
import mahjong.model.Hand;
import mahjong.model.Yaku;

public class AgariAnalyzer { //getBestPattern メソッドは、複数のあがりパターン（AgariPattern）から最も得点が高いものを選ぶための処理
    public static AgariPattern getBestPattern(List<AgariPattern> patterns, boolean isTsumo,
                                              boolean isDealer, List<Yaku> yakus, int seatWind, int roundWind) {
    	//List<AgariPattern> patterns: 複数のあがり形（候補）、boolean isTsumo: ツモあがりか（true）かロンあがりか（false）
    	//boolean isDealer: 親かどうか、int han: 翻数（リーチや役牌など）、int seatWind: 自風（東=1, 南=2 など）、int roundWind: 場風（東=1, 南=2 など）
        ScoreCalculator scoreCalc = new ScoreCalculator(); //符計算用のクラス、calculateFu(...) メソッドを使うためにインスタンス化しています。
        int maxPoint = 0; //現在の 最大点数 を記録する変数 maxPoint
        AgariPattern best = null; //最も得点が高かったパターン（best）を格納する変数

        for (AgariPattern pattern : patterns) { //すべてのあがり形を1つずつ確認します。
            Hand hand = pattern.toHand(); //AgariPattern を Hand に変換します（符計算は Hand 単位）。
            //符を計算する。第3引数の !isTsumo は「ロンならtrue、ツモならfalse」を意味します（ロンあがり判定）。自風と場風も渡して役牌の計算などに使用。
            int fu = scoreCalc.calculateFu(hand, isTsumo, !isTsumo, seatWind, roundWind, pattern.getWinningTile());
            //翻と符から点数を計算します。親かどうか、ツモかロンかも影響します。
            int point = PointCalculator.calculatePoints(yakus, fu, isDealer, isTsumo);
            //各パターンごとの計算結果（符・点数）を出力（デバッグ用）。
            System.out.println("Pattern with fu " + fu + " → " + point + " 点");

            if (point > maxPoint) { //このパターンがこれまでで最も高得点なら記録を更新。
                maxPoint = point;
                best = pattern;
            }
        }

        return best; //最終的に一番高い点数だったあがり形（AgariPattern）を返します。
    }
}