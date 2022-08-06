package dev.mayuna.nostalgiamanager.modules.enhancedchat.data;

import dev.mayuna.pumpk1n.api.ParentedDataElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class EnhancedChatSettingsData extends ParentedDataElement {

    private @Setter @Getter boolean disabledPings;

}
