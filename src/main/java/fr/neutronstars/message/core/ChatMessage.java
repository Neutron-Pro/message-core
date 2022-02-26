package fr.neutronstars.message.core;

import fr.neutronstars.message.api.CloseableComponent;
import fr.neutronstars.message.api.MessageComponent;
import fr.neutronstars.message.api.Sender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.stream.Collectors;

/**
 * Build a message with special interactions in the player chat.
 */
public class ChatMessage extends AbstractComponent<MessageComponent> implements MessageComponent
{
    /**
     * Create new instance of Message with a sender.
     *
     * @param sender The sender to whom the components will be sent.
     * @return new instance of message.
     */
    public static MessageComponent of(Sender sender)
    {
        return new ChatMessage(sender);
    }

    /**
     * The sender to whom the components will be sent.
     */
    protected final Sender sender;

    /**
     * Constructor of ChatMessage.
     * @param sender The sender to whom the components will be sent.
     */
    protected ChatMessage(Sender sender)
    {
        this.sender = sender;
    }

    /**
     * The text to insert when this component is clicked while pressing the shift key
     *
     * @param insertion The text to insert
     * @return this instance of message
     */
    @Override
    public MessageComponent insertion(String insertion)
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
    public MessageComponent click(ClickEvent.Action action, String value)
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
    public CloseableComponent hover(String text)
    {
        return this.hover(HoverEvent.Action.SHOW_TEXT, text);
    }

    /**
     * The action to perform when hovering over the current text.
     *
     * @param action The action to perform
     * @param text The text to be displayed
     * @return this instance of message
     */
    @Override
    public CloseableComponent hover(HoverEvent.Action action, String text)
    {
        if (this.currentComponent == null) {
            throw new MessageException(
                "Can't set hover on empty component. Use the next or nextln method before."
            );
        }
        return new HoverComponent(this, action).next(text);
    }

    /**
     * The action to perform when hovering over the current text.
     *
     * @param action         The action to perform
     * @param baseComponents determines what the action should show or perform.
     */
    protected void hover(HoverEvent.Action action, BaseComponent... baseComponents)
    {
        if (this.currentComponent == null) {
            throw new MessageException(
                "Can't set hover on empty component. Use the next or nextln method before."
            );
        }
        this.currentComponent.setHoverEvent(new HoverEvent(action, baseComponents));
    }

    /**
     * Add a text with the same format and events as the current text.
     *
     * @param text the new text with the same format and events as the current text.
     * @return this instance of message
     */
    @Override
    public MessageComponent extra(String text)
    {
        return this.extra(new TextComponent(text));
    }

    /**
     * Add a component with the same format and events as the current text.
     *
     * @param baseComponents the new component with the same format and events as the current text.
     * @return this instance of message
     */
    @Override
    public MessageComponent extra(BaseComponent... baseComponents)
    {
        if (this.currentComponent == null) {
            throw new MessageException(
                "Can't add extra on empty component. Use the next or nextln method before."
            );
        }
        for (BaseComponent baseComponent : baseComponents) {
            this.currentComponent.addExtra(baseComponent);
        }
        return this;
    }

    /**
     * Send the components to the sender
     */
    @Override
    public void send()
    {
        this.sender.send(this.build());
    }

    /**
     * Create a new instance with the components of this instance.
     *
     * @return create a new instance of message
     */
    @Override
    public MessageComponent clone()
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
