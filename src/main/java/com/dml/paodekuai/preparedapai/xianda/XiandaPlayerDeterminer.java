package com.dml.paodekuai.preparedapai.xianda;

import com.dml.paodekuai.ju.Ju;
import com.dml.paodekuai.player.PlayerNotFoundException;

/**
 * 谁先打牌,并按打牌玩家分配位置
 */
public interface XiandaPlayerDeterminer {
	String determineXiandaPlayer(Ju ju) throws PlayerNotFoundException;
}
