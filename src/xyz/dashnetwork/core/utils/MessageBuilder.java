package xyz.dashnetwork.core.utils;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.ArrayList;
import java.util.List;

public class MessageBuilder {

    private List<MessageComponent> messageComponents = new ArrayList<>();

    public MessageComponent append(String append) {
        MessageComponent messageComponent = new MessageComponent(append);
        messageComponents.add(messageComponent);

        return messageComponent;
    }

    public MessageComponent append(BaseComponent... append) {
        MessageComponent messageComponent = new MessageComponent(append);
        messageComponents.add(messageComponent);

        return messageComponent;
    }

    public boolean isEmpty() {
        return messageComponents.isEmpty();
    }

    public int getSize() {
        return messageComponents.size();
    }

    public BaseComponent[] build() {
        List<BaseComponent> baseComponents = new ArrayList<>();

        for (MessageComponent messageComponent : messageComponents)
            for (BaseComponent baseComponent : messageComponent.getBaseComponents())
                baseComponents.add(baseComponent);

        return baseComponents.toArray(new BaseComponent[baseComponents.size()]);
    }

    public class MessageComponent {

        private List<BaseComponent> components = new ArrayList<>();

        public MessageComponent(String text) {
            for (BaseComponent component : TextComponent.fromLegacyText(ColorUtils.translate(text)))
                components.add(component);
        }

        public MessageComponent(BaseComponent... baseComponents) {
            for (BaseComponent component : baseComponents)
                components.add(component);
        }

        public MessageComponent clickEvent(ClickEvent.Action action, String string) {
            for (BaseComponent component : components)
                component.setClickEvent(new ClickEvent(action, string));
            return this;
        }

        @SuppressWarnings("deprecation")
        public MessageComponent hoverEvent(HoverEvent.Action action, String string) {
            for (BaseComponent component : components)
                component.setHoverEvent(new HoverEvent(action, TextComponent.fromLegacyText(ColorUtils.translate(string))));
            return this;
        }

        @SuppressWarnings("deprecation")
        public MessageComponent hoverEvent(HoverEvent.Action action, BaseComponent... input) {
            for (BaseComponent component : components)
                component.setHoverEvent(new HoverEvent(action, input));
            return this;
        }

        public MessageComponent font(String font) {
            for (BaseComponent component : components)
                component.setFont(font);
            return this;
        }

        private List<BaseComponent> getBaseComponents() {
            return components;
        }

    }


}
