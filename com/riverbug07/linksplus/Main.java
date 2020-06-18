/*     */ package com.riverbug07.linksplus;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.configuration.ConfigurationSection;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.plugin.PluginDescriptionFile;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
/*     */ 
/*     */ public class Main
/*     */   extends JavaPlugin
/*     */ {
/*     */   public final Logger logger;
/*     */   public static Main plugin;
/*  23 */   private FileConfiguration customConfig = null;
/*     */   
/*  25 */   private File customConfigFile = null;
/*     */   
/*     */   public void reloadCustomConfig() {
/*  28 */     if (this.customConfigFile == null)
/*  29 */       this.customConfigFile = new File(getDataFolder(), "config.yml"); 
/*  30 */     this.customConfig = (FileConfiguration)YamlConfiguration.loadConfiguration(this.customConfigFile);
/*     */   }
/*     */   
/*     */   public FileConfiguration getCustomConfig() {
/*  34 */     if (this.customConfig == null)
/*  35 */       reloadCustomConfig(); 
/*  36 */     return this.customConfig;
/*     */   }
/*     */   
/*     */   public void saveCustomConfig() {
/*  40 */     if (this.customConfig == null || this.customConfigFile == null)
/*     */       return; 
/*     */     try {
/*  43 */       getCustomConfig().save(this.customConfigFile);
/*  44 */     } catch (IOException ex) {
/*  45 */       getLogger().log(Level.SEVERE, "Could not save config to " + this.customConfigFile, ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Main() {
/*  50 */     this.logger = Logger.getLogger("Minecraft");
/*     */   }
/*     */   
/*     */   public void onDisable() {
/*  54 */     PluginDescriptionFile pdfFile = getDescription();
/*  55 */     this.logger.info(String.valueOf(String.valueOf(String.valueOf(pdfFile.getName()))) + " has been disabled!");
/*  56 */     saveConfig();
/*  57 */     reloadConfig();
/*     */   }
/*     */   
/*     */   public void onEnable() {
/*  61 */     PluginDescriptionFile pdfFile = getDescription();
/*  62 */     this.logger.info(String.valueOf(String.valueOf(String.valueOf(pdfFile.getName()))) + " version " + pdfFile.getVersion() + " by " + pdfFile.getAuthors() + " has been enabled!");
/*  63 */     getConfig().options().copyDefaults(true);
/*  64 */     saveConfig();
/*     */   }
/*     */   
/*     */   public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
/*  68 */     if (commandLabel.equalsIgnoreCase("link")) {
/*  69 */       if (sender instanceof Player) {
/*  70 */         Player player = (Player)sender;
/*  71 */         if (player.hasPermission("linksplus.use")) {
/*  72 */           if (args.length == 0) {
/*  73 */             player.sendMessage(ChatColor.RED + "Usage: /link <name> [player]");
/*  74 */             return true;
/*     */           } 
/*  76 */           if (args.length == 1) {
/*  77 */             if (args[0].equalsIgnoreCase("reload")) {
/*  78 */               reloadConfig();
/*  79 */               player.sendMessage(ChatColor.GREEN + "Links+ has been reloaded!");
/*  80 */               return true;
/*     */             } 
/*  82 */             if (!args[0].equalsIgnoreCase("reload")) {
/*  83 */               ConfigurationSection sec = getConfig().getConfigurationSection("links");
/*  84 */               for (String key : sec.getKeys(false)) {
/*  85 */                 if (key.equalsIgnoreCase(args[0])) {
/*  86 */                   player.sendMessage(ChatColor.DARK_AQUA + "Check out our " + ChatColor.MAGIC + "0" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + getConfig().getString("links." + key + ".name") + ChatColor.DARK_AQUA + ChatColor.MAGIC + "0" + ChatColor.DARK_AQUA + " at " + ChatColor.GOLD + ChatColor.BOLD + getConfig().getString("links." + key + ".link"));
/*  87 */                   return true;
/*     */                 } 
/*  89 */                 if (key.equalsIgnoreCase(null))
/*     */                   break; 
/*     */               } 
/*     */             } 
/*  93 */             player.sendMessage(ChatColor.RED + "Site name is invalid! ");
/*  94 */             return true;
/*     */           } 
/*  96 */           if (args.length == 2) {
/*  97 */             Player target = Bukkit.getPlayerExact(args[1]);
/*  98 */             if (target == null) {
/*  99 */               player.sendMessage(ChatColor.RED + args[1] + " is not online!");
/* 100 */               return true;
/*     */             } 
/* 102 */             ConfigurationSection sec = getConfig().getConfigurationSection("links");
/* 103 */             for (String key : sec.getKeys(false)) {
/* 104 */               if (key.equalsIgnoreCase(args[0])) {
/* 105 */                 target.sendMessage(ChatColor.DARK_AQUA + "Check out our " + ChatColor.MAGIC + "0" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + getConfig().getString("links." + key + ".name") + ChatColor.DARK_AQUA + ChatColor.MAGIC + "0" + ChatColor.DARK_AQUA + " at " + ChatColor.GOLD + ChatColor.BOLD + getConfig().getString("links." + key + ".link"));
/* 106 */                 return true;
/*     */               } 
/* 108 */               if (key.equalsIgnoreCase(null))
/*     */                 break; 
/*     */             } 
/* 111 */             player.sendMessage(ChatColor.RED + "Site name is invalid!");
/* 112 */             return true;
/*     */           } 
/* 114 */           player.sendMessage(ChatColor.RED + "Usage: /link <name> [player]");
/* 115 */           return true;
/*     */         } 
/*     */       } 
/* 118 */       if (args.length == 0) {
/* 119 */         sender.sendMessage("Usage: /link <name> [player]");
/* 120 */         return true;
/*     */       } 
/* 122 */       if (args.length == 1) {
/* 123 */         if (args[0].equalsIgnoreCase("reload")) {
/* 124 */           reloadConfig();
/* 125 */           sender.sendMessage("Links+ has been reloaded!");
/* 126 */           return true;
/*     */         } 
/* 128 */         if (!args[0].equalsIgnoreCase("reload")) {
/* 129 */           ConfigurationSection sec = getConfig().getConfigurationSection("links");
/* 130 */           for (String key : sec.getKeys(false)) {
/* 131 */             if (key.equalsIgnoreCase(args[0])) {
/* 132 */               sender.sendMessage("Check out our " + getConfig().getString("links." + key + ".name") + " at " + getConfig().getString("links." + key + ".link"));
/* 133 */               return true;
/*     */             } 
/* 135 */             if (key.equalsIgnoreCase(null))
/*     */               break; 
/*     */           } 
/*     */         } 
/* 139 */         sender.sendMessage("Site name is invalid!");
/* 140 */         return true;
/*     */       } 
/* 142 */       if (args.length == 2) {
/* 143 */         Player target = Bukkit.getPlayerExact(args[1]);
/* 144 */         if (target == null) {
/* 145 */           sender.sendMessage(String.valueOf(String.valueOf(args[1])) + " is not online!");
/* 146 */           return true;
/*     */         } 
/* 148 */         ConfigurationSection sec = getConfig().getConfigurationSection("links");
/* 149 */         for (String key : sec.getKeys(false)) {
/* 150 */           if (key.equalsIgnoreCase(args[0])) {
/* 151 */             target.sendMessage(ChatColor.DARK_AQUA + "Check out our " + ChatColor.MAGIC + "0" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + getConfig().getString("links." + key + ".name") + ChatColor.DARK_AQUA + ChatColor.MAGIC + "0" + ChatColor.DARK_AQUA + " at " + ChatColor.GOLD + ChatColor.BOLD + getConfig().getString("links." + key + ".link"));
/* 152 */             return true;
/*     */           } 
/* 154 */           if (key.equalsIgnoreCase(null))
/*     */             break; 
/*     */         } 
/* 157 */         sender.sendMessage("Site name is invalid!");
/* 158 */         return true;
/*     */       } 
/* 160 */       sender.sendMessage("Usage: /link <name> [player]");
/* 161 */       return true;
/*     */     } 
/* 163 */     sender.sendMessage(ChatColor.DARK_RED + "You do not have permission to use this command!");
/* 164 */     return true;
/*     */   }
/*     */ }
