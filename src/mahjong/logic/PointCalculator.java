package mahjong.logic;

import java.util.List;

import mahjong.model.Yaku;

public class PointCalculator {
	//han：翻数（役による得点）、fu：符数（副得点）、isDealer：親かどうか（true = 親）、isTsumo：ツモあがりかどうか
	public static int calculatePoints(List<Yaku> yakus, int fu, boolean isDealer, boolean isTsumo) {
		int totalHan = 0;
        int yakumanCount = 0;

        // 翻数と役満数の集計
        for (Yaku yaku : yakus) {
            if (yaku.isYakuman()) {
                yakumanCount += yaku.getHan() / 13; // ダブル役満以上に対応（26=ダブル, 39=トリプルなど）
            } else {
                totalHan += yaku.getHan();
            }
        }
		
        int basePoints;

        //翻数が高い場合（6翻以上）には「跳満」「倍満」「三倍満」「役満」の定額点が使われます（符に関係ない）。
        // --- 役満複合の処理 ---
        if (yakumanCount > 0) {
            basePoints = 8000 * yakumanCount;
        }
        // --- 通常の翻数による処理 ---
        else if (totalHan >= 13) {
            basePoints = 8000; // 役満
        } else if (totalHan >= 11) {
            basePoints = 6000; // 三倍満
        } else if (totalHan >= 8) {
            basePoints = 4000; // 倍満
        } else if (totalHan >= 6) {
            basePoints = 3000; // 跳満
        } else if (totalHan == 5 || (totalHan == 4 && fu >= 40) || (totalHan == 3 && fu >= 70)) {
            basePoints = 2000; // 満貫
        } else {
            basePoints = fu * (int) Math.pow(2, 2 + totalHan); //基本点=符×2の(2+翻)乗
            if (basePoints > 2000) basePoints = 2000; // 計算結果が 2000 を超える場合 → 満貫扱いにする（2000点固定）
        }

        //親か子か、ツモかロンかで最終点を計算
        if (isTsumo) {
            if (isDealer) {
                return roundUpToNearest100(basePoints * 2); //親がツモ：親は子2人から同額支払い
            } else {
                return roundUpToNearest100((basePoints) + (basePoints * 2)); //子がツモ：子は親と子から1:2の合計支払い
            }
        } else {
            if (isDealer) {
                return roundUpToNearest100(basePoints * 6); //親がロン：放銃者が全額負担
            } else {
                return roundUpToNearest100(basePoints * 4); //子がロン：放銃者が全額負担
            }
        }
    }

	//100点単位に切り上げ（麻雀の点数ルールによる）
    private static int roundUpToNearest100(int points) {
        return ((points + 99) / 100) * 100;
    }
}
