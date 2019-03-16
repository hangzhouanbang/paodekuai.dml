package com.dml.paodekuai.pai.dianshuzu;

import com.dml.puke.pai.DianShu;
import com.dml.puke.wanfa.dianshu.dianshuzu.DanGeZhadanDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.ZhadanDianShuZu;

/**
 * @Description: 可带一张牌的炸弹(33334)
 */
public class DaiPaiZhaDanDianShuZu extends DanGeZhadanDianShuZu {
    private DianShu zhadanDian;
    private DianShu daipaiDian;

    public DianShu getZhadanDian() {
        return zhadanDian;
    }

    public void setZhadanDian(DianShu zhadanDian) {
        this.zhadanDian = zhadanDian;
    }

    public DianShu getDaipaiDian() {
        return daipaiDian;
    }

    public void setDaipaiDian(DianShu daipaiDian) {
        this.daipaiDian = daipaiDian;
    }
}
