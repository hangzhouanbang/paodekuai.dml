package com.dml.paodekuai.player.action.da;

import java.util.Map;

import com.dml.puke.pai.PukePai;
import com.dml.paodekuai.player.action.da.solution.DaPaiDianShuSolution;

/**
 * 生成所有可打的牌的方案
 * 
 * @author Neo
 *
 */
public interface AllKedaPaiSolutionsGenerator {
	/**
	 * 一般情况的所有可打牌
	 */
	Map<String, DaPaiDianShuSolution> generateAllKedaPaiSolutions(Map<Integer, PukePai> allShoupai);

	/**
	 * 首次出牌时
	 */
	Map<String, DaPaiDianShuSolution> firstAllKedaPaiSolutions(Map<Integer, PukePai> allShoupai);
}
