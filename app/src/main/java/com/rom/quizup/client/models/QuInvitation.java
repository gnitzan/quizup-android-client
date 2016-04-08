package com.rom.quizup.client.models;

/**
 * The purpose of this class is to provide a layer of abstraction between the service generated
 * classes for an invitation and the application
 */
public class QuInvitation {
  private String invitationId;
  private String gameId;
  private InvitationStatus status = InvitationStatus.INITIALIZED;

  /**
   * Constructor
   *
   * @param gameId The game ID
   * @param invitationId The invitation ID
   * @param status The status of the invitation
   */
  public QuInvitation(String gameId, String invitationId, InvitationStatus status) {
    this.invitationId = invitationId;
    this.gameId = gameId;
    this.status = status;
  }

  /**
   * Get the invitation ID
   *
   * @return {@link Long}
   */
  public String getInvitationId() {
    return invitationId;
  }

  /**
   * Get the game ID
   *
   * @return {@link Long}
   */
  public String getGameId() {
    return gameId;
  }

  /**
   * Get the status
   *
   * @return {@link Integer}
   */
  public InvitationStatus getStatus() {
    return status;
  }
}
