package dan200.computer.api;

public abstract interface IComputerAccess
{
  public abstract int createNewSaveDir(String paramString);

  public abstract String mountSaveDir(String paramString1, String paramString2, int paramInt, boolean paramBoolean, long paramLong);

  public abstract String mountFixedDir(String paramString1, String paramString2, boolean paramBoolean, long paramLong);

  public abstract void unmount(String paramString);

  public abstract int getID();

  public abstract void queueEvent(String paramString);

  public abstract void queueEvent(String paramString, Object[] paramArrayOfObject);

  public abstract String getAttachmentSide();
}

/* Location:           C:\Users\mikeef\Documents\CC148.jar
 * Qualified Name:     dan200.computer.api.IComputerAccess
 * JD-Core Version:    0.6.2
 */