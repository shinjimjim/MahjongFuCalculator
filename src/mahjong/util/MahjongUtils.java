package mahjong.util;

import java.util.Set; //Set は重複のない値の集合（コレクション）を表します。今回は「役牌の番号（1, 2, 3）」を管理するために使っています。

import mahjong.model.Tile;

public class MahjongUtils { //与えられた牌（Tile）が役牌（白・發・中）かどうかを判定する
	//staticメソッドを持ち、インスタンス化せずに使います。
	/*private static final Set<Integer> YAKU_HONOR_NUMBERS = Set.of(1, 2, 3); // 1: 白, 2: 發, 3: 中*/
	//定数として「役牌の番号」をセットで定義。Set.of(...) はJava 9以降の簡潔な書き方で、不変の集合を作れます。
	private static final Set<Integer> SAN_GEN = Set.of(1, 2, 3);         // 白發中
    private static final Set<Integer> WIND_NUMBERS = Set.of(4, 5, 6, 7); // 東南西北
    
    /*public static boolean isYakuHai(Tile tile) { //Tile オブジェクトを受け取って、「それが役牌かどうか」を調べる関数。
    	//static なのでインスタンス化せず MahjongUtils.isYakuHai(tile) として呼び出せます。
        return tile.getSuit().equals("honor") && YAKU_HONOR_NUMBERS.contains(tile.getNumber());
        //tile.getSuit() が "honor"（字牌）で、かつTile.getNumber() が 1, 2, 3 のいずれか（白・發・中）なら true を返す。
    }*/
    
    public static boolean isSanGen(Tile tile) { //「この牌は三元牌（白・發・中）ですか？」を判定
        return tile.getSuit().equals("honor") && SAN_GEN.contains(tile.getNumber());
    }

    public static boolean isWind(Tile tile) { //「この牌は風牌（東南西北）ですか？」を判定
        return tile.getSuit().equals("honor") && WIND_NUMBERS.contains(tile.getNumber());
    }

    public static boolean isYakuhai(Tile pair, int seatWind, int roundWind) { //その雀頭の牌（pair）が役牌（翻牌）かどうか？
    	//Tile pair：雀頭の牌、seatWind：プレイヤーの自風（東:4, 南:5, 西:6, 北:7）、roundWind：場の風（東場, 南場など）
    	//その雀頭が三元牌または自風または場風なら true（役牌扱い）
        if (pair.getSuit().equals("honor")) { //雀頭の牌が 字牌（honor） であるかどうかを確認
            int number = pair.getNumber(); //字牌の番号（1〜7）を取得。これを使って、白發中や東南西北と照合します
            return SAN_GEN.contains(number) || number == seatWind || number == roundWind;
            //白（1）, 發（2）, 中（3）であればOK、自風と同じ牌ならOK、場風と同じ牌ならOK
        }
        return false; //字牌でなかった場合は、そもそも役牌の可能性がないので false
    }
}
