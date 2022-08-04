package dev.mayuna.nostalgiamanager.modules.enhancedchat.data;

import dev.mayuna.pumpk1n.api.DataElement;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class EnhancedChatSettingsData implements DataElement {

    private boolean disabledPings;

}
