package com.ranork.combathelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.live.bemmamin.gps.api.GPSAPI;

import me.clip.placeholderapi.PlaceholderAPI;
import net.Indyuce.mmocore.api.player.PlayerData;
import net.Indyuce.mmocore.api.player.social.Party;
import net.Indyuce.mmocore.manager.social.PartyManager;

public class CombatListener implements Listener {
	private static CombatHelper plugin;
		
	public CombatListener(CombatHelper pl) {
		plugin = pl;
	}
	
	Connection connection;
    String host, database, username, password;
    int port = 3306;
	public void openConnection() throws SQLException, ClassNotFoundException {
		host = "localhost";
	    database = "R_Island";
	    username = "admin";
	    password = "--";
		
	    if (connection != null && !connection.isClosed()) {
	        return;
    }
	    synchronized (this) {
	        if (connection != null && !connection.isClosed()) {
	            return;
	        }
	        Class.forName("com.mysql.jdbc.Driver");
	        connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database, this.username, this.password);
	    }
	}
	
	ScoreboardManager manager = Bukkit.getScoreboardManager();
    final Scoreboard board = manager.getNewScoreboard();
    @SuppressWarnings("deprecation")
	final Objective objective = board.registerNewObjective("test", "dummy");
    @EventHandler
    public void PlayerJoin(PlayerJoinEvent e) {
    	
        final Player p = e.getPlayer();
    	PlayerData pdata = PlayerData.get(p);
    	
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            public void run() {
            	
            	
            	if (!p.isOnline()) {
            		return;
            	}
            	
            	String hpline = "§4%mmocore_health% §7/ §4%mmocore_max_health%";
            	hpline = PlaceholderAPI.setPlaceholders(p, hpline);
            	String mpline = "§9%mmocore_mana% §7/ §9%mmocore_stat_max_mana%";
            	mpline = PlaceholderAPI.setPlaceholders(p, mpline);

            	String moneyline = "§6%lighteconomy_player_balance% Akat";
            	moneyline = PlaceholderAPI.setPlaceholders(p, moneyline);

            	String expline1 = "§7%mmocore_level_percent%";
            	expline1 = PlaceholderAPI.setPlaceholders(p, expline1);
            	String expline2 = "§6%mmocore_experience% §7/ §6%mmocore_next_level%";
            	expline2 = PlaceholderAPI.setPlaceholders(p, expline2);
            	String lvlline = "§6%mmocore_level%";
            	lvlline = PlaceholderAPI.setPlaceholders(p, lvlline);
            	
            	
            	
            	if (pdata.isInCombat()) {
            		
            		ScoreboardManager manager = Bukkit.getScoreboardManager();
                    final Scoreboard board = manager.getNewScoreboard();
					final Objective objective = board.registerNewObjective("test", "dummy", "§8- §c§l < §r§4SAVAÞ MODU §c§l> §8-");
                    objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                    objective.setDisplayName("§8- §c§l < §r§4SAVAÞ MODU §c§l> §8-");
                    Score score = objective.getScore("§0.");
                    score.setScore(20);
                    Score score1 = objective.getScore("§7HP:");
                    score1.setScore(19);
                    Score score2 = objective.getScore(hpline);
                    score2.setScore(18);
                    Score score3 = objective.getScore("§7Mana:");
                    score3.setScore(17);
                    Score score4 = objective.getScore(mpline);
                    score4.setScore(16);
                    Score score5 = objective.getScore("§7Tecrübe:");
                    score5.setScore(15);
                    Score score6 = objective.getScore(expline2 + " §8(§7%" + expline1 + "§8)");
                    score6.setScore(14);
                    
                    if (pdata.hasParty()) {

                        Party party = pdata.getParty();
                        int pSize = party.getMembers().count();
                        

                        if (pSize < 2) {
                        	PartyManager pman = new PartyManager();
                        	
                        	pman.unregisterParty(party);
                        }
                        
                        Score score7 = objective.getScore("§0..");
                        score7.setScore(13);
                        Score score8 = objective.getScore("§8§lGrup (" + String.valueOf(pSize) + "):");
                        score8.setScore(12);
                        
                        int nScore = 11;
                        String pOwner = party.getOwner().getPlayer().getName();
                        
                        for (int i = 0; i < pSize; i++) {
                        	String pMember = party.getMembers().get(i).getPlayer().getName();
                        	String memLv = String.valueOf(party.getMembers().get(i).getLevel());
                        	
                        	if (pMember.equalsIgnoreCase(pOwner)) {
                        		pMember = "§7§l" + pMember;
                        	}
                        	
                        	objective.getScore("§7" + pMember + " §7[" + memLv + "]").setScore(nScore);
                        	nScore--;
                        	
                        	
                        	Player member = party.getMembers().get(i).getPlayer();
                        	
                        	double curHP = member.getHealth();
                        	@SuppressWarnings("deprecation")
							double maxHp = member.getMaxHealth();
                        	
                        	double hpPer = (curHP / maxHp) * 100;
                        	hpPer = Math.round(hpPer * 100.0) / 100.0;
                        	
                        	objective.getScore("§4HP: §c%" + String.valueOf(hpPer) + "  §7" + String.valueOf(nScore)).setScore(nScore);
                        	nScore--;
                        	
                        }
                        
                        
                    }
                    
                    
                    
                    p.setScoreboard(board);
                    
            		
            		
            	}
            	else {
            		
            		ScoreboardManager manager = Bukkit.getScoreboardManager();
                    final Scoreboard board = manager.getNewScoreboard();
					final Objective objective = board.registerNewObjective("test", "dummy", ChatColor.DARK_GRAY + " -- " + ChatColor.GOLD + "Akatron Online" + ChatColor.DARK_GRAY + " -- ");        
                    objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                    objective.setDisplayName(ChatColor.DARK_GRAY + " -- " + ChatColor.GOLD + "Akatron Online" + ChatColor.DARK_GRAY + " -- ");
                    Score score = objective.getScore("§0.");
                    score.setScore(20); 
                    Score score1 = objective.getScore("§8§lBakiye:");
                    score1.setScore(19);
                    Score score2 = objective.getScore(moneyline);
                    score2.setScore(18);
                    Score score3 = objective.getScore("§0..");
                    score3.setScore(17);
                    Score score4 = objective.getScore("§8§lTecrübe: §6Lv" + lvlline);
                    score4.setScore(16);
                    Score score5 = objective.getScore(expline2 + " §8(§7%" + expline1 + "§8)");
                    score5.setScore(15);
                    
                    
                    int lastscore = 14;
                    if (pdata.hasParty()) {

                        Party party = pdata.getParty();
                        int pSize = party.getMembers().count();
                        
                        if (pSize < 2) {
                        	PartyManager pman = new PartyManager();
                        	
                        	pman.unregisterParty(party);
                        }
                        
                        Score score6 = objective.getScore("§0...");
                        score6.setScore(14);
                        Score score7 = objective.getScore("§8§lGrup (" + String.valueOf(pSize) + "):");
                        score7.setScore(13);
                        
                        int nScore = 12;
                        String pOwner = party.getOwner().getPlayer().getName();
                        
                        for (int i = 0; i < pSize; i++) {
                        	String pMember = party.getMembers().get(i).getPlayer().getName();
                        	String memLv = String.valueOf(party.getMembers().get(i).getLevel());
                        	
                        	if (pMember.equalsIgnoreCase(pOwner)) {
                        		pMember = "§7§l" + pMember;
                        	}
                        	
                        	objective.getScore("§7" + pMember + " §7[" + memLv + "]").setScore(nScore);
                        	nScore--;
                        	
                        }
                        
                        lastscore = nScore;
                        
                    }
                    

                    GPSAPI gps = new GPSAPI(plugin);
                    
                    if (gps.gpsIsActive(p)) {
                    	com.live.bemmamin.gps.playerdata.PlayerData pdata = com.live.bemmamin.gps.playerdata.PlayerData.getPlayerData(p);
                    	Location tloc = pdata.getTargetLocation();
                    	
                    	String locstr = tloc.getWorld().getName() + ", " + Math.round(tloc.getX()) + ", " +  Math.round(tloc.getY()) + ", " +  Math.round(tloc.getZ());
                    	
                    	objective.getScore("").setScore(lastscore);
                    	lastscore--;
                    	objective.getScore("§8§lYolGöster Aktif").setScore(lastscore);
                    	lastscore--;
                    	
                    }
                    

                    p.setScoreboard(board);
                    
            	}
                
            }
        },0, 20 * 1);
    }
 
 
}
