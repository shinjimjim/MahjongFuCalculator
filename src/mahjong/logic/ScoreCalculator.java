package mahjong.logic;

import mahjong.model.Hand;
import mahjong.model.Meld; //他のパッケージから Hand（手牌）と Meld（面子）のクラスをインポートしています。
import mahjong.util.MahjongUtils;

public class ScoreCalculator { //符を計算するクラス
	public int calculateFu(Hand hand, boolean isTsumo, boolean isClosedRon, int seatWind, int roundWind) { //符の計算結果（int）を返すメソッド
		//Hand hand: プレイヤーの手牌（4面子+1雀頭）
		//boolean isTsumo: ツモあがりかどうか（true ならツモ）
		//boolean isClosedRon: 門前ロンかどうか（今は未使用ですが将来拡張用）
        int fu = 20; //符の初期値。麻雀の符計算は基本的に 20 符から始まります。

        /*// 暗刻・明刻などに応じて符を追加
        for (Meld meld : hand.getMelds()) { //手牌の中の面子（Meld）を1つずつ取り出して処理しています。
            if (meld.getType() == Meld.Type.TRIPLE) { //刻子（コーツ）＝同じ牌3枚の組み合わせ の場合
                if (meld.isOpen()) fu += 2; //副露（open）していたら 2符
                else fu += 4; //暗刻（non-open）なら 4符
            } else if (meld.getType() == Meld.Type.QUAD) { //槓子（カン）＝同じ牌4枚の組み合わせ の場合
                if (meld.isOpen()) fu += 8; //副露なら 8符
                else fu += 16; //暗槓なら 16符
            }
        }*/
        
        //「刻子（コーツ）」と「槓子（カンツ）」に加算される符を判断して加算する処理
        for (Meld meld : hand.getMelds()) { //プレイヤーの手牌に含まれる4つの面子（メンツ）を1つずつ取り出します。
        	//面子の構成牌が「老頭牌（1か9）」または「字牌（東南西北白發中）」かどうかを判定
            boolean isRoutouOrHonor = MahjongUtils.isRoutouOrHonor(meld.getTile());

            if (meld.getType() == Meld.Type.TRIPLE) { //面子が「刻子（3枚同じ牌）」かどうかの判定
                if (meld.isOpen()) { //面子が鳴いている（副露）＝オープンなら true。暗刻（自分だけで完成）＝クローズなら false
                    fu += isRoutouOrHonor ? 4 : 2;
                } else {
                    fu += isRoutouOrHonor ? 8 : 4;
                }
            } else if (meld.getType() == Meld.Type.QUAD) { //面子が「槓子（4枚同じ牌）」
                if (meld.isOpen()) {
                    fu += isRoutouOrHonor ? 16 : 8;
                } else {
                    fu += isRoutouOrHonor ? 32 : 16;
                }
            }
        }
        
        // 雀頭が役牌なら +2符
        /*if (MahjongUtils.isYakuHai(hand.getPair())) { //その牌が役牌（翻牌）かどうかをチェックするメソッド。
        	//プレイヤーの手牌（Hand）から雀頭（対子）の牌を取得。honor牌（字牌）であり、番号が 1（白）, 2（發）, 3（中）に該当すれば true を返す。
            fu += 2;
        }*/
        
        // 雀頭が役牌（白發中 or 自風 or 場風）なら +2符
        if (MahjongUtils.isYakuhai(hand.getPair(), seatWind, roundWind)) {
            fu += 2;
        }

        // ツモあがり：2符
        if (isTsumo) {
            fu += 2;
        }

        // 最後に10の倍数に切り上げて戻します（例：34符 → 40符）
        return roundUpToNearest10(fu);
    }

	//切り上げ処理：
    private int roundUpToNearest10(int fu) {
        return ((fu + 9) / 10) * 10; //9を足して10で割り、整数にしてから×10 で切り上げる。例：31 → 40, 40 → 40, 46 → 50
    }
}
