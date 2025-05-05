package mahjong.util;

import java.util.Set; //Set は重複のない値の集合（コレクション）を表します。今回は「役牌の番号（1, 2, 3）」を管理するために使っています。

import mahjong.model.Tile;

public class MahjongUtils { //与えられた牌（Tile）が役牌（白・發・中）かどうかを判定する
	//staticメソッドを持ち、インスタンス化せずに使います。
	private static final Set<Integer> YAKU_HONOR_NUMBERS = Set.of(1, 2, 3); // 1: 白, 2: 發, 3: 中
	//定数として「役牌の番号」をセットで定義。Set.of(...) はJava 9以降の簡潔な書き方で、不変の集合を作れます。
    public static boolean isYakuHai(Tile tile) { //Tile オブジェクトを受け取って、「それが役牌かどうか」を調べる関数。
    	//static なのでインスタンス化せず MahjongUtils.isYakuHai(tile) として呼び出せます。
        return tile.getSuit().equals("honor") && YAKU_HONOR_NUMBERS.contains(tile.getNumber());
        //tile.getSuit() が "honor"（字牌）で、かつTile.getNumber() が 1, 2, 3 のいずれか（白・發・中）なら true を返す。
    }
}
