package FileAction.Exceptions;

@SuppressWarnings("serial")
public class RenameFileException extends Exception
{
	public RenameFileException()
	{
		super("Couldnt commit operation! File Couldnt renamed.");
	}
}
