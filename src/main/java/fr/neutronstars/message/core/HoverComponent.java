package fr.neutronstars.message.core;

import fr.neutronstars.message.api.CloseableComponent;
import fr.neutronstars.message.api.MessageComponent;
import net.md_5.bungee.api.chat.HoverEvent;

public class HoverComponent extends AbstractComponent<CloseableComponent> implements CloseableComponent
{
    private final ChatMessage parentMessage;
    private final HoverEvent.Action action;

    protected HoverComponent(ChatMessage parentMessage, HoverEvent.Action action)
    {
        this.parentMessage = parentMessage;
        this.action = action;
    }

    @Override
    public MessageComponent close()
    {
        this.parentMessage.hover(action, this.build());
        return this.parentMessage;
    }
}
