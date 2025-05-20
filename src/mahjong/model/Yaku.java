package mahjong.model;

public class Yaku { //麻雀における 役（Yaku） を表すモデルクラス
	//フィールド（役の情報）
	private String name;       // 役の名前（例：立直、一発、混一色、四暗刻）
    private int han;           // 翻数（役満なら13翻相当）得点計算に使う
    private boolean isYakuman; // 役満かどうか（trueなら役満）13翻相当以上とみなす

    //コンストラクタ（初期化）
    public Yaku(String name, int han, boolean isYakuman) { //役名、翻数、役満かどうかを引数に受け取って、インスタンスを初期化します。
        this.name = name; //this.name のように this を使って区別しています。
        this.han = han;
        this.isYakuman = isYakuman;
    }

    //Getterメソッド（外部アクセス用）各フィールドの値を外部から取得するためのメソッド
    public String getName() {
        return name;
    }

    public int getHan() {
        return han;
    }

    public boolean isYakuman() {
        return isYakuman;
    }

    @Override
    public String toString() { //toString（役を文字列で表示）
    	//役満（isYakuman=true） → 四暗刻（役満）、通常役（isYakuman=false, han=3）→ 混一色：3翻
        return name + (isYakuman ? "（役満）" : "：" + han + "翻");
    }
}
