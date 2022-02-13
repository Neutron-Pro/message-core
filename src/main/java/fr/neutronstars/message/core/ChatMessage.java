package fr.neutronstars.message.core;

import fr.neutronstars.message.api.Message;
import fr.neutronstars.message.api.Sender;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChatMessage implements Message
{
    public static Message of(Sender sender)
    {
        return new ChatMessage(sender);
    }

    protected final List<BaseComponent> baseComponents = new ArrayList<>();
    protected final Sender sender;

    protected BaseComponent currentComponent;

    protected ChatMessage(Sender sender)
    {
        this.sender = sender;
    }

    public Message next(String text)
    {
        if (this.currentComponent != null) {
            this.baseComponents.add(this.currentComponent);
        }
        this.currentComponent = new TextComponent(text);
        return this;
    }

    public Message nextln()
    {
        return this.next("\n");
    }

    public Message nextln(String text)
    {
        return this.nextln().next(text);
    }

    public Message color(ChatColor color)
    {
        if (this.currentComponent == null) {
            throw new MessageException(
                "Can't set color on empty component. Use the next or nextln method before."
            );
        }
        this.currentComponent.setColor(color);
        return this;
    }

    public Message italic(boolean italic)
    {
        if (this.currentComponent == null) {
            throw new MessageException(
                "Can't set italic on empty component. Use the next or nextln method before."
            );
        }
        this.currentComponent.setItalic(italic);
        return this;
    }

    public Message bold(boolean bold)
    {
        if (this.currentComponent == null) {
            throw new MessageException(
                "Can't set bold on empty component. Use the next or nextln method before."
            );
        }
        this.currentComponent.setBold(bold);
        return this;
    }

    public Message obfuscated(boolean obfuscated)
    {
        if (this.currentComponent == null) {
            throw new MessageException(
                "Can't set obfuscated on empty component. Use the next or nextln method before."
            );
        }
        this.currentComponent.setObfuscated(obfuscated);
        return this;
    }

    public Message strikethrough(boolean strikethrough)
    {
        if (this.currentComponent == null) {
            throw new MessageException(
                "Can't set strikethrough on empty component. Use the next or nextln method before."
            );
        }
        this.currentComponent.setStrikethrough(strikethrough);
        return this;
    }

    public Message underlined(boolean underlined)
    {
        if (this.currentComponent == null) {
            throw new MessageException(
                "Can't set underline on empty component. Use the next or nextln method before."
            );
        }
        this.currentComponent.setUnderlined(underlined);
        return this;
    }

    public Message insertion(String insertion)
    {
        if (this.currentComponent == null) {
            throw new MessageException(
                "Can't set insertion on empty component. Use the next or nextln method before."
            );
        }
        this.currentComponent.setInsertion(insertion);
        return this;
    }

    public Message click(ClickEvent.Action action, String value)
    {
        if (this.currentComponent == null) {
            throw new MessageException(
                "Can't set click on empty component. Use the next or nextln method before."
            );
        }
        this.currentComponent.setClickEvent(new ClickEvent(action, value));
        return this;
    }

    public Message hover(String text)
    {
        return this.hover(HoverEvent.Action.SHOW_TEXT, new TextComponent(text));
    }

    public Message hover(HoverEvent.Action action, BaseComponent component)
    {
        if (this.currentComponent == null) {
            throw new MessageException(
                "Can't set hover on empty component. Use the next or nextln method before."
            );
        }
        this.currentComponent.setHoverEvent(new HoverEvent(action, new BaseComponent[]{ component }));
        return this;
    }

    public Message extra(String text)
    {
        return this.extra(new TextComponent(text));
    }

    public Message extra(BaseComponent baseComponent)
    {
        if (this.currentComponent == null) {
            throw new MessageException(
                "Can't add extra on empty component. Use the next or nextln method before."
            );
        }
        this.currentComponent.addExtra(baseComponent);
        return this;
    }

    public void send()
    {
        if (this.baseComponents.isEmpty() && this.currentComponent == null) {
            throw new MessageException(
                "Can't send on empty component. Use the next or nextln method before."
            );
        }
        BaseComponent[] baseComponents = this.baseComponents.toArray(
            new BaseComponent[this.baseComponents.size() + (this.currentComponent != null ? 1 : 0)]
        );
        if (this.currentComponent != null) {
            baseComponents[this.baseComponents.size()] = this.currentComponent;
        }
        this.sender.send(baseComponents);
    }

    @Override
    public Message clone()
    {
        ChatMessage message = new ChatMessage(this.sender);
        message.baseComponents.addAll(
            this.baseComponents.stream()
                .map(BaseComponent::duplicate)
                .collect(Collectors.toList())
        );
        if (this.currentComponent != null) {
            message.currentComponent = this.currentComponent.duplicate();
        }
        return message;
    }
}
