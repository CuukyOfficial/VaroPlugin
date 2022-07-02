package de.varoplugin.varo.data;

import de.varoplugin.varo.game.VaroState;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Varo")
public interface VaroDataGame {

    void setState();

    @Column(name = "state")
    VaroState getState();

}
