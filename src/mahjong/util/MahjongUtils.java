package mahjong.util;

import java.util.Set; //Set は重複のない値の集合（コレクション）を表します。今回は「役牌の番号（1, 2, 3）」を管理するために使っています。

import mahjong.model.Meld;
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
    
    public static boolean isChunchan(Tile tile) { //「この牌は中張牌（2～8）か？」を判定。中張牌は刻子や槓子で得られる符が少ない（例：暗刻→4符）
        return tile.getSuit().matches("man|pin|sou") && //萬子・筒子・索子のいずれかであり
               tile.getNumber() >= 2 && tile.getNumber() <= 8; //数字が2〜8である
    }

    public static boolean isRoutou(Tile tile) { //「この牌は老頭牌（1または9）か？」を判定。老頭牌の刻子や槓子は符が多い（例：暗刻→8符）
        return tile.getSuit().matches("man|pin|sou") && //萬・筒・索 のいずれか
               (tile.getNumber() == 1 || tile.getNumber() == 9); //数字が 1 または 9
    }

    public static boolean isHonor(Tile tile) { //「この牌は字牌（白發中・風牌）か？」を判定。字牌の刻子や槓子も符が多い（暗刻→8符）
        return tile.getSuit().equals("honor");
    }

    public static boolean isRoutouOrHonor(Tile tile) { //「この牌は老頭牌または字牌か？」を判定。刻子・槓子で +4符/+8符 に影響します。
        return isRoutou(tile) || isHonor(tile); //1または9の数牌 または 字牌
    }
    
    public static boolean isTankiWait(Tile winningTile, Tile pair) { //単騎待ちかどうか
    	//winningTile: あがった時の牌、pair: 雀頭（対子）として使っていた牌、equals() で両者が同じ牌なら「単騎待ち」と判定。
        return winningTile.equals(pair);
    }

    public static boolean isKanchanWait(Tile winningTile, Meld meld) { //カンチャン待ちかどうか
    	//meld.getTile()：順子の先頭牌（例：3-4-5 なら 3）を meld.getTile() で取得する設計になっています。
    	//つまり、順子の形は 「基準牌 + 連続2つ」 で管理している前提です。
        if (meld.getType() != Meld.Type.SEQUENCE) return false;
        int base = meld.getTile().getNumber(); // 順子の先頭

        return winningTile.getSuit().equals(meld.getTile().getSuit()) && //スート（man/pin/sou）も一致している必要あり
               winningTile.getNumber() == base + 1; //真ん中は base+1 → その数字とあがり牌の番号が一致するかを判定
    }

    public static boolean isPenchanWait(Tile winningTile, Meld meld) { //ペンチャン待ちかどうか
        if (meld.getType() != Meld.Type.SEQUENCE) return false;
        int base = meld.getTile().getNumber();

        return winningTile.getSuit().equals(meld.getTile().getSuit()) &&
               ((base == 1 && winningTile.getNumber() == 3) || //baseが1 → 3待ちならペンチャン、1-2-3 の 3待ち
                (base == 7 && winningTile.getNumber() == 7)); //baseが7 → 7待ちならペンチャン7-8-9 の 7待ち、特別な待ちとして+2符
    }
}
