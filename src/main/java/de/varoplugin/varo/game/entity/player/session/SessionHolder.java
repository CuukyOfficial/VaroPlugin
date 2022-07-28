package de.varoplugin.varo.game.entity.player.session;

import java.util.stream.Stream;

public interface SessionHolder {

    Session getCurrentSession();

    void endSession();

    void addSession(Session session);

    Stream<Session> getSessions();

}
