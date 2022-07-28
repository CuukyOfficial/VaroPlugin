package de.varoplugin.varo.data;

import de.varoplugin.varo.game.State;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Varo")
public interface VaroDataGame {

    boolean setState(State state);

    @Column(name = "state")
    State getState();

}
