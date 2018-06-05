package FileAction.Exceptions;

@SuppressWarnings("serial")
public class DeleteFileException extends Exception
{
	public DeleteFileException()
	{
		super("Couldnt commit operation! File Couldnt delete.");
	}

}
