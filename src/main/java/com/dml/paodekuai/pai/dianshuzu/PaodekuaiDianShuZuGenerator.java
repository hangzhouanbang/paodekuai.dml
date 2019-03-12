package com.dml.paodekuai.pai.dianshuzu;

import com.alibaba.fastjson.JSON;
import com.dml.puke.pai.DianShu;
import com.dml.puke.wanfa.dianshu.dianshuzu.DianShuZu;
import com.sun.jmx.remote.internal.ArrayQueue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 通过点数数量数组生成相关的所有可能点数组:跑得快特有三带二、飞机
 */
public class PaodekuaiDianShuZuGenerator {
    public static List<SandaierDianShuZu> generateAllSandaierDianShuZu(int[] dianShuAmountArray, boolean sandaique) {
        List<SandaierDianShuZu> sanzhangList = new ArrayList<>();
        for (int i = 0; i < dianShuAmountArray.length; i++) {
            int[] tempDinashu = Arrays.copyOf(dianShuAmountArray, 13);
            int dianshuCount = dianShuAmountArray[i];
            if (dianshuCount >= 3) {
                SandaierDianShuZu sandaierDianShuZu = new SandaierDianShuZu();
                DianShu[] sanzhangDianShuArray = {DianShu.getDianShuByOrdinal(i)};
                sandaierDianShuZu.setSanzhangDianShuArray(sanzhangDianShuArray);

                //取出除去三张之外的所有余牌
                tempDinashu[i] = tempDinashu[i] - 3;
                List<DianShu> daipaiList = leftHand(tempDinashu);

                if (sandaique && daipaiList.size() < 2) {
                    DianShu[] dianShus = new DianShu[daipaiList.size()];
                    daipaiList.toArray(dianShus);
                    sandaierDianShuZu.setMissingType(true);
                    sanzhangList.add(sandaierDianShuZu);
                    continue;
                }

                //todo 有重复
                for (int a = 0; a < daipaiList.size() - 1; a++) {
                    for (int b = a + 1; b < daipaiList.size(); b++) {
                        DianShu[] daipaiDianshuzu = {daipaiList.get(a), daipaiList.get(b)};
                        sandaierDianShuZu.setDaipaiDianShuArray(daipaiDianshuzu);
                        sanzhangList.add(sandaierDianShuZu);
                    }
                }
            }
        }
        return sanzhangList;
    }

    public static List<FeijiDianShuZu> generateAllFeijiDianShuZu(int[] dianShuAmountArray, int length, boolean feijique) {
        List<FeijiDianShuZu> feijiDianShuZus = new ArrayList<>();
        for (int i = 0; i < dianShuAmountArray.length; i++) {
            int[] tempDinashu = Arrays.copyOf(dianShuAmountArray, 13);
            int danzhangLianXuCount = 0;
            int j = i;
            while (danzhangLianXuCount < length && j < 12 && dianShuAmountArray[j] >= 3) {// 3个或者3个以上的点数相连的3张牌，如555666777；2和大、小王无法参与
                danzhangLianXuCount++;
                j++;
                tempDinashu[i] = tempDinashu[i] - 3;
            }

            if (danzhangLianXuCount >= length) {
                //取出余牌
                List<DianShu> yupaiList = leftHand(tempDinashu);
                DianShu[] daipaiDianshu;

                //先取出缺牌情形的牌组(五连必缺牌)
                if (feijique && yupaiList.size() < length * 2) {
                    daipaiDianshu = new DianShu[yupaiList.size()];
                    yupaiList.toArray(daipaiDianshu);

                    FeijiDianShuZu feijiDianShuZu = new FeijiDianShuZu();
                    if (length == 2) {
                        DianShu[] sanzhangDianshuzu = {DianShu.getDianShuByOrdinal(i), DianShu.getDianShuByOrdinal(i + 1)};
                        feijiDianShuZu.setSanzhangDianShuArray(sanzhangDianshuzu);
                    } else if (length == 3) {
                        DianShu[] sanzhangDianshuzu = {DianShu.getDianShuByOrdinal(i), DianShu.getDianShuByOrdinal(i + 1),
                                DianShu.getDianShuByOrdinal(i + 2)};
                        feijiDianShuZu.setSanzhangDianShuArray(sanzhangDianshuzu);
                    } else if (length == 4) {
                        DianShu[] sanzhangDianshuzu = {DianShu.getDianShuByOrdinal(i), DianShu.getDianShuByOrdinal(i + 1),
                                DianShu.getDianShuByOrdinal(i + 2), DianShu.getDianShuByOrdinal(i + 3)};
                        feijiDianShuZu.setSanzhangDianShuArray(sanzhangDianshuzu);
                    } else if (length == 5) {
                        DianShu[] sanzhangDianshuzu = {DianShu.getDianShuByOrdinal(i), DianShu.getDianShuByOrdinal(i + 1),
                                DianShu.getDianShuByOrdinal(i + 2), DianShu.getDianShuByOrdinal(i + 3),
                                DianShu.getDianShuByOrdinal(i + 4)};
                        feijiDianShuZu.setSanzhangDianShuArray(sanzhangDianshuzu);
                    }
                    feijiDianShuZu.setDaipaiDianShuArray(daipaiDianshu);
                    feijiDianShuZus.add(feijiDianShuZu);
                    continue;
                }

                //不缺牌的2-4连的飞机
                if (yupaiList.size() >= length * 2) {
                    //三张
                    DianShu[] sanzhangDianshuzu = new DianShu[length];
                    for (int a = 0; a < length; a++) {
                        sanzhangDianshuzu[a] = DianShu.getDianShuByOrdinal(i + a);
                    }

                    //带牌
                    List<DianShu[]> daipaiList = new ArrayList<>();
                    quPai(daipaiList, new DianShu[length], yupaiList, length, 0, 0);

                    for (DianShu[] daipaiDianshuzu : daipaiList) {
                        FeijiDianShuZu feijiDianShuZu = new FeijiDianShuZu();
                        feijiDianShuZu.setSanzhangDianShuArray(sanzhangDianshuzu);
                        feijiDianShuZu.setDaipaiDianShuArray(daipaiDianshuzu);
                        feijiDianShuZus.add(feijiDianShuZu);
                    }
                }
            }
        }
        return feijiDianShuZus;
    }

    public static List<ABoomDianShuZu> generateAllABoomDianShuZu(int[] dianShuAmountArray) {
        List<ABoomDianShuZu> aBoomDianShuZus = new ArrayList<>();
        if (dianShuAmountArray[11] == 3) {
            ABoomDianShuZu aBoomDianShuZu = new ABoomDianShuZu();
            aBoomDianShuZu.setDianShu(DianShu.getDianShuByOrdinal(11));
            aBoomDianShuZus.add(aBoomDianShuZu);
        }
        return aBoomDianShuZus;
    }

    /**
     * 从余牌中取出带牌list
     *
     * @param diapaiList      所有飞机带的牌组
     * @param daipaiDianshuzu 飞机带牌的点数
     * @param yuPaiList       剩余手牌
     * @param length          取出的带牌点数数组长度
     * @param index           取牌起点下标
     * @param time            取牌次数
     */
    private static void quPai(List<DianShu[]> diapaiList, DianShu[] daipaiDianshuzu, List<DianShu> yuPaiList,
                              int length, int index, int time) {
        if (time < length) {  //2 < 3
            for (int i = index; i < yuPaiList.size(); i++) {
                daipaiDianshuzu[time] = yuPaiList.get(i);
                quPai(diapaiList, daipaiDianshuzu, yuPaiList, length, i + 1, time + 1);
            }
        } else {
            DianShu[] dianShus = daipaiDianshuzu.clone();
            diapaiList.add(dianShus);
        }
    }

//    void feijiTest() {
//
//        List<DianShu> yuPaiList = new ArrayList<>();
//        yuPaiList.add(DianShu.getDianShuByOrdinal(0));
//        yuPaiList.add(DianShu.getDianShuByOrdinal(1));
//        yuPaiList.add(DianShu.getDianShuByOrdinal(2));
//        yuPaiList.add(DianShu.getDianShuByOrdinal(3));
//        yuPaiList.add(DianShu.getDianShuByOrdinal(4));
//        yuPaiList.add(DianShu.getDianShuByOrdinal(5));
//        yuPaiList.add(DianShu.getDianShuByOrdinal(6));
//        yuPaiList.add(DianShu.getDianShuByOrdinal(7));
//        yuPaiList.add(DianShu.getDianShuByOrdinal(8));
//        yuPaiList.add(DianShu.getDianShuByOrdinal(9));
//        yuPaiList.add(DianShu.getDianShuByOrdinal(10));
//        yuPaiList.add(DianShu.getDianShuByOrdinal(11));
//        DianShu[] daipai = new DianShu[8];
//        List<DianShu[]> result = new ArrayList<>();
//        quPai(result,daipai, yuPaiList,8, 0 ,0);
//        System.out.println(JSON.toJSONString(result));
//        System.out.println(result.size());
//    }

    //取出余牌
    private static List<DianShu> leftHand(int[] tempDainshu) {
        List<DianShu> dianShus = new ArrayList<>();
        for (int i = 0; i < tempDainshu.length - 1; i++) {
            for (int j = 0; j < tempDainshu[i]; j++) {
                dianShus.add(DianShu.getDianShuByOrdinal(i));
            }
        }
        return dianShus;
    }
}
