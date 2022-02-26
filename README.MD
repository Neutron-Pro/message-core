# Message Core using Message API with the BungeeCord Chat Components. [Minecraft 1.8+]

This core use the message api who allows to create special messages on Minecraft using the bungeecord-chat library of md_5.

# Implementation of core in your project with gradle

```gradle
repositories {
  mavenCentral()
  maven { url "https://jitpack.io" }
}

dependencies {
  implementation 'com.github.Neutron-Pro:message-core:1.1.0'
}
```

*Don't forget to add your Minecraft server version using the bungeecord-chat library of md_5. (ex: bukkit, spigot, paper) or your bungeecord library.*

# Documentation

## Create a new message with a Player of Spigot

```java
@EventHandler
public void on(PlayerJoinEvent event) 
{
    ChatMessage.of(event.getPlayer().spigot()::sendMessage)
        .next("Welcome on this server !")
        .send();
}
```

## Create a new message with a Collection of Player of Spigot

```java
final List<Player.Spigot> players = Bukkit.getOnlinePlayers()
    .stream()
    .map(Player::spigot)
    .collect(Collectors.toList());

ChatMessage.of((components) -> players.forEach(player -> player.sendMessage(components)))
    .next("Welcome on this server !")
    .send();
```

## Create a new message with a ProxiedPlayer of BungeeCord

```java
@EventHandler
public void onPostLogin(PostLoginEvent event)
{
    ChatMessage.of(event.getPlayer()::sendMessage)
        .next("Welcome on this server !")
        .send();
}
```

## Create a new message with a Collection of ProxiedPlayer of BungeeCord

```java
final List<ProxiedPlayer> players = ProxyServer.getInstance().getPlayers();

ChatMessage.of((components) -> players.forEach(player -> player.sendMessage(components)))
    .next("Welcome on this server !")
    .send();
```

## Add color on the message

```java
@EventHandler
public void on(PlayerJoinEvent event) 
{
    ChatMessage.of(event.getPlayer().spigot()::sendMessage)
        .next("Welcome on this server !")
        .color(ChatColor.GREEN)
        .send();
}
```

## Add style on the message

```java
@EventHandler
public void on(PlayerJoinEvent event) 
{
    ChatMessage.of(event.getPlayer().spigot()::sendMessage)
        .next("Welcome on this server !")
        .italic(true)
        .send();
}
```

```java
@EventHandler
public void on(PlayerJoinEvent event) 
{
    ChatMessage.of(event.getPlayer().spigot()::sendMessage)
        .next("Welcome on this server !")
        .bold(true)
        .send();
}
```

> You can also concatenate styles

```java
@EventHandler
public void on(PlayerJoinEvent event) 
{
    ChatMessage.of(event.getPlayer().spigot()::sendMessage)
        .next("Welcome on this server !")
        .italic(true)
        .bold(true)
        .underline(true)
        .send();
}
```

## Add hover on the message

```java
@EventHandler
public void on(PlayerJoinEvent event) 
{
    ChatMessage.of(event.getPlayer().spigot()::sendMessage)
        .next("Welcome on this server !")
        .hover("play.minecraft.net").close()
        .send();
}
```

## Add hover with color on the message

```java
@EventHandler
public void on(PlayerJoinEvent event) 
{
    ChatMessage.of(event.getPlayer().spigot()::sendMessage)
        .next("Welcome on this server !")
        .hover("play.minecraft.net")
            .color(ChatColor.GOLD)
            .close()
        .send();
}
```

## Add hover with color and more lines on the message

```java
@EventHandler
public void on(PlayerJoinEvent event) 
{
    ChatMessage.of(event.getPlayer().spigot()::sendMessage)
        .next("Welcome on this server !")
        .hover("play.minecraft.net")
            .color(ChatColor.GOLD)
            .nextln("https://minecraft.net/")
            .color(ChatColor.AQUA)
            .close()
        .send();
}
```

## Add click event on the message

```java
@EventHandler
public void on(PlayerJoinEvent event) 
{
    ChatMessage.of(event.getPlayer().spigot()::sendMessage)
        .next("Welcome on this server !")
        .click(ClickEvent.Action.OPEN_URL, "https://minecraft.net")
        .send();
}
```

```java
@EventHandler
public void on(PlayerJoinEvent event) 
{
    ChatMessage.of(event.getPlayer().spigot()::sendMessage)
        .next("Welcome on this server !")
        .hover("Click here to say hello to everyone !")
        .click(ClickEvent.Action.RUN_COMMAND, "/say hello !")
        .send();
}
```

## Send message with more components

```java
@EventHandler
public void on(PlayerJoinEvent event) 
{
    ChatMessage.of(event.getPlayer().spigot()::sendMessage)
        .next("Welcome on this server !")
        .nextln()
        .nextln("Choose a team")
        .color(ChatColor.GOLD)
        .nextln()
        .nextln(">  ")
        .next("[RED]")
        .color(ChatColor.RED)
        .hover("Join the red team")
            .color(ChatColor.RED)
            .close()
        .click(ClickEvent.Action.RUN_COMMAND, "/team join red")
        .next("  ")
        .next("[BLUE]")
        .color(ChatColor.BLUE)
        .hover("Join the blue team")
            .color(ChatColor.BLUE)
            .close()
        .click(ClickEvent.Action.RUN_COMMAND, "/team join blue")
        .send();
}
```

> Display
```
Welcome on this server !

Choose a team

>  [RED]  [BLUE]
```