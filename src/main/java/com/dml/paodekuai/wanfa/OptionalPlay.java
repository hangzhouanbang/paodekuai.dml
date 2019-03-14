package com.dml.paodekuai.wanfa;

/**
 * @Description: 跑的快可选玩法（不包括局数人数）
 */
public class OptionalPlay {
    private boolean bichu;//首出必须包含黑桃三
    private boolean biya; //能压牌必须压
    private boolean aBoom;//三个当做炸弹
    private boolean sandaique;//尾牌时三带可缺牌
    private boolean feijique;//尾牌时飞机可缺牌

    private boolean showShoupaiNum;//显示剩余手牌
    private boolean zhuaniao;//红桃10抓鸟玩法


    /**
     * 检查玩法，二人玩随机出牌
     */
    public boolean checkPlay (int playerNum) {
        if (playerNum == 2 && bichu == true) {
            return false;
        }
        return true;
    }

    public OptionalPlay() {
    }

    public OptionalPlay(boolean bichu, boolean biya, boolean aBoom, boolean sandaique, boolean feijique, boolean showShoupaiNum, boolean zhuaniao) {
        this.bichu = bichu;
        this.biya = biya;
        this.aBoom = aBoom;
        this.sandaique = sandaique;
        this.feijique = feijique;
        this.showShoupaiNum = showShoupaiNum;
        this.zhuaniao = zhuaniao;
    }

    public boolean isBichu() {
        return bichu;
    }

    public void setBichu(boolean bichu) {
        this.bichu = bichu;
    }

    public boolean isBiya() {
        return biya;
    }

    public void setBiya(boolean biya) {
        this.biya = biya;
    }

    public boolean isaBoom() {
        return aBoom;
    }

    public void setaBoom(boolean aBoom) {
        this.aBoom = aBoom;
    }

    public boolean isSandaique() {
        return sandaique;
    }

    public void setSandaique(boolean sandaique) {
        this.sandaique = sandaique;
    }

    public boolean isFeijique() {
        return feijique;
    }

    public void setFeijique(boolean feijique) {
        this.feijique = feijique;
    }

    public boolean isShowShoupaiNum() {
        return showShoupaiNum;
    }

    public void setShowShoupaiNum(boolean showShoupaiNum) {
        this.showShoupaiNum = showShoupaiNum;
    }

    public boolean isZhuaniao() {
        return zhuaniao;
    }

    public void setZhuaniao(boolean zhuaniao) {
        this.zhuaniao = zhuaniao;
    }
}
