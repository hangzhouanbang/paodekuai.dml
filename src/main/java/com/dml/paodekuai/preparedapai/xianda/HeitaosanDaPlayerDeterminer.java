package com.dml.paodekuai.preparedapai.xianda;

import com.dml.paodekuai.player.PlayerNotFoundException;
import com.dml.puke.pai.PukePai;
import com.dml.puke.pai.PukePaiMian;
import com.dml.puke.pai.QiShouLiangPaiMark;
import com.dml.puke.wanfa.position.Position;
import com.dml.paodekuai.ju.Ju;
import com.dml.paodekuai.pan.Pan;
import com.dml.paodekuai.player.PaodekuaiPlayer;

import java.util.Collections;
import java.util.List;

/**
 * 三人选择黑桃三玩法时黑桃三先出
 */
public class HeitaosanDaPlayerDeterminer implements XiandaPlayerDeterminer {
	private boolean bichu;//三人玩首张出必须含黑桃三的牌型

	@Override
	public String determineXiandaPlayer(Ju ju) throws PlayerNotFoundException {
		Pan currentPan = ju.getCurrentPan();
		List<String> playerIdList = currentPan.sortedPlayerIdList();
		Collections.shuffle(playerIdList);
		String daplayerId = null;

		if (bichu == true) {
			for (PaodekuaiPlayer player : currentPan.getPaodekuaiPlayerIdMajiangPlayerMap().values()) {
				for (PukePai pukePai : player.getAllShoupai().values()) {
					//黑桃三设为先出牌，埋下亮牌标记
					if (pukePai.getPaiMian().equals(PukePaiMian.heitaosan)) {
						pukePai.setMark(new QiShouLiangPaiMark());
						daplayerId = player.getId();
						break;
					}
				}
			}
		} else {
			daplayerId = playerIdList.get(0);
		}

		//分配玩家位置
		currentPan.updatePlayerPosition(daplayerId, Position.dong);
		Position[] positions = Position.values();

		if (playerIdList.size() == 2) {		// 2人时，东西对坐
			for (String playerId : playerIdList) {
				if (!playerId.equals(daplayerId)) {
					currentPan.updatePlayerPosition(playerId, positions[2]);
				}
			}
		} else {
			int index = 1;
			for (String playerId : playerIdList) {
				if (!playerId.equals(daplayerId)) {
					currentPan.updatePlayerPosition(playerId, positions[index]);
					index++;
				}
			}
		}
		return daplayerId;
	}

	public HeitaosanDaPlayerDeterminer (){}

	public HeitaosanDaPlayerDeterminer(boolean bichu) {
		this.bichu = bichu;
	}

	public boolean isBichu() {
		return bichu;
	}

	public void setBichu(boolean bichu) {
		this.bichu = bichu;
	}
}
