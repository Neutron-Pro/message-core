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

/**
 * Build a message with special interactions in the player chat.
 */
public class ChatMessage implements Message
{
    /**
     * Create new instance of Message with a sender.
     *
     * @param sender The sender to whom the components will be sent.
     * @return new instance of message.
     */
    public static Message of(Sender sender)
    {
        return new ChatMessage(sender);
    }

    /**
     * List of components already created.
     */
    protected final List<BaseComponent> baseComponents = new ArrayList<>();

    /**
     * The sender to whom the components will be sent.
     */
    protected final Sender sender;

    /**
     * The current component.
     */
    protected BaseComponent currentComponent;

    /**
     * Constructor of ChatMessage.
     * @param sender The sender to whom the components will be sent.
     */
    protected ChatMessage(Sender sender)
    {
        this.sender = sender;
    }

    /**
     * Go to the next text which will have a different behavior than the previous one.
     *
     * @param text The next text of new component
     * @return this instance of message
     */
    @Override
    public Message next(String text)
    {
        if (this.currentComponent != null) {
            this.baseComponents.add(this.currentComponent);
        }
        this.currentComponent = new TextComponent(text);
        return this;
    }

    /**
     * Go to the next line of the chat.
     *
     * @return this instance of message
     */
    @Override
    public Message nextln()
    {
        return this.next("\n");
    }

    /**
     * Go to the next line of the chat and insert the next text which will have a different behavior than the previous one.
     *
     * @param text The next text of new component
     * @return this instance of message
     */
    @Override
    public Message nextln(String text)
    {
        return this.nextln().next(text);
    }

    /**
     * Put a color on the current text.
     * (You must first have called the {@link Message#next(String)} or  {@link Message#nextln(String)} method)
     *
     * @param color the new color of current text.
     * @return this instance of message
     */
    @Override
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

    /**
     * Add or remove italic on the current text.
     * (You must first have called the {@link Message#next(String)} or  {@link Message#nextln(String)} method)
     *
     * @param italic Add or remove italic
     * @return this instance of message
     */
    @Override
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

    /**
     * Add or remove bold on the current text.
     * (You must first have called the {@link Message#next(String)} or  {@link Message#nextln(String)} method)
     *
     * @param bold Add or remove bold
     * @return this instance of message
     */
    @Override
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

    /**
     * Add or remove obfuscated on the current text.
     * (You must first have called the {@link Message#next(String)} or  {@link Message#nextln(String)} method)
     *
     * @param obfuscated Add or remove obfuscated
     * @return this instance of message
     */
    @Override
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

    /**
     * Add or remove strikethrough on the current text.
     * (You must first have called the {@link Message#next(String)} or  {@link Message#nextln(String)} method)
     *
     * @param strikethrough Add or remove strikethrough
     * @return this instance of message
     */
    @Override
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

    /**
     * Add or remove underlined on the current text.
     * (You must first have called the {@link Message#next(String)} or  {@link Message#nextln(String)} method)
     *
     * @param underlined Add or remove underlined
     * @return this instance of message
     */
    @Override
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

    /**
     * The text to insert when this component is clicked while pressing the shift key
     *
     * @param insertion The text to insert
     * @return this instance of message
     */
    @Override
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

    /**
     * The action to be performed by clicking on the current text.
     *
     * @param action The action to be performed
     * @param value determines what the action should show or perform.
     * @return this instance of message
     */
    @Override
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

    /**
     * The text to be displayed when hovering over the current text.
     *
     * @param text The text to be displayed
     * @return this instance of message
     */
    @Override
    public Message hover(String text)
    {
        return this.hover(HoverEvent.Action.SHOW_TEXT, new TextComponent(text));
    }

    /**
     * The action to perform when hovering over the current text.
     *
     * @param action The action to perform
     * @param component determines what the action should show or perform.
     * @return this instance of message
     */
    @Override
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

    /**
     * Add a text with the same format and events as the current text.
     *
     * @param text the new text with the same format and events as the current text.
     * @return this instance of message
     */
    @Override
    public Message extra(String text)
    {
        return this.extra(new TextComponent(text));
    }

    /**
     * Add a component with the same format and events as the current text.
     *
     * @param baseComponent the new component with the same format and events as the current text.
     * @return this instance of message
     */
    @Override
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

    /**
     * Send the components to the sender
     */
    @Override
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

    /**
     * Create a new instance with the components of this instance.
     *
     * @return create a new instance of message
     */
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
