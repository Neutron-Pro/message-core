package fr.neutronstars.message.core;

import fr.neutronstars.message.api.Component;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.ArrayList;
import java.util.List;

abstract class AbstractComponent<T extends Component<T>> implements Component<T>
{
    /**
     * List of components already created.
     */
    protected final List<BaseComponent> baseComponents = new ArrayList<>();

    protected BaseComponent currentComponent;

    /**
     * Go to the next text which will have a different behavior than the previous one.
     *
     * @param text The next text of new component
     * @return this instance of message
     */
    @Override
    public T next(String text)
    {
        if (this.currentComponent != null) {
            this.baseComponents.add(this.currentComponent);
        }
        this.currentComponent = new TextComponent(text);
        return (T) this;
    }

    /**
     * Go to the next line of the chat.
     *
     * @return this instance of message
     */
    @Override
    public T nextln()
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
    public T nextln(String text)
    {
        return this.nextln().next(text);
    }

    /**
     * Put a color on the current text.
     * (You must first have called the {@link AbstractComponent#next(String)} or  {@link AbstractComponent#nextln(String)} method)
     *
     * @param color the new color of current text.
     * @return this instance of message
     */
    @Override
    public T color(ChatColor color)
    {
        if (this.currentComponent == null) {
            throw new MessageException(
                "Can't set color on empty component. Use the next or nextln method before."
            );
        }
        this.currentComponent.setColor(color);
        return (T) this;
    }

    /**
     * Add or remove italic on the current text.
     * (You must first have called the {@link AbstractComponent#next(String)} or  {@link AbstractComponent#nextln(String)} method)
     *
     * @param italic Add or remove italic
     * @return this instance of message
     */
    @Override
    public T italic(boolean italic)
    {
        if (this.currentComponent == null) {
            throw new MessageException(
                "Can't set italic on empty component. Use the next or nextln method before."
            );
        }
        this.currentComponent.setItalic(italic);
        return (T) this;
    }

    /**
     * Add or remove bold on the current text.
     * (You must first have called the {@link AbstractComponent#next(String)} or  {@link AbstractComponent#nextln(String)} method)
     *
     * @param bold Add or remove bold
     * @return this instance of message
     */
    @Override
    public T bold(boolean bold)
    {
        if (this.currentComponent == null) {
            throw new MessageException(
                "Can't set bold on empty component. Use the next or nextln method before."
            );
        }
        this.currentComponent.setBold(bold);
        return (T) this;
    }

    /**
     * Add or remove obfuscated on the current text.
     * (You must first have called the {@link AbstractComponent#next(String)} or  {@link AbstractComponent#nextln(String)} method)
     *
     * @param obfuscated Add or remove obfuscated
     * @return this instance of message
     */
    @Override
    public T obfuscated(boolean obfuscated)
    {
        if (this.currentComponent == null) {
            throw new MessageException(
                "Can't set obfuscated on empty component. Use the next or nextln method before."
            );
        }
        this.currentComponent.setObfuscated(obfuscated);
        return (T) this;
    }

    /**
     * Add or remove strikethrough on the current text.
     * (You must first have called the {@link AbstractComponent#next(String)} or  {@link AbstractComponent#nextln(String)} method)
     *
     * @param strikethrough Add or remove strikethrough
     * @return this instance of message
     */
    @Override
    public T strikethrough(boolean strikethrough)
    {
        if (this.currentComponent == null) {
            throw new MessageException(
                "Can't set strikethrough on empty component. Use the next or nextln method before."
            );
        }
        this.currentComponent.setStrikethrough(strikethrough);
        return (T) this;
    }

    /**
     * Add or remove underlined on the current text.
     * (You must first have called the {@link AbstractComponent#next(String)} or  {@link AbstractComponent#nextln(String)} method)
     *
     * @param underlined Add or remove underlined
     * @return this instance of message
     */
    @Override
    public T underlined(boolean underlined)
    {
        if (this.currentComponent == null) {
            throw new MessageException(
                "Can't set underline on empty component. Use the next or nextln method before."
            );
        }
        this.currentComponent.setUnderlined(underlined);
        return (T) this;
    }

    /**
     * Build the list of components used for this message.
     *
     * @return the components array of this message.
     */
    public BaseComponent[] build()
    {
        if (this.baseComponents.isEmpty() && this.currentComponent == null) {
            throw new MessageException(
                "Can't build on empty component. Use the next or nextln method before."
            );
        }
        BaseComponent[] baseComponents = this.baseComponents.toArray(
            new BaseComponent[this.baseComponents.size() + (this.currentComponent != null ? 1 : 0)]
        );
        if (this.currentComponent != null) {
            baseComponents[this.baseComponents.size()] = this.currentComponent;
        }
        return baseComponents;
    }
}
