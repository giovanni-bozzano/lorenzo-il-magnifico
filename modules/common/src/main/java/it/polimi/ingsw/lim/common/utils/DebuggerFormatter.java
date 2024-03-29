package it.polimi.ingsw.lim.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class DebuggerFormatter extends Formatter
{
	public static final String EXCEPTION_MESSAGE = "an exception was thrown";
	public static final String RMI_ERROR = "RMI connection closed remotely.";
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");

	private static String stackTraceToString(Throwable throwable)
	{
		StringBuilder stringBuilder = new StringBuilder();
		boolean isFirstLine = true;
		for (StackTraceElement element : throwable.getStackTrace()) {
			if (!isFirstLine) {
				stringBuilder.append('\n');
			} else {
				isFirstLine = false;
			}
			stringBuilder.append(element.toString());
		}
		return stringBuilder.toString();
	}

	@Override
	public String format(LogRecord logRecord)
	{
		if (logRecord.getLevel().intValue() >= Level.OFF.intValue()) {
			return "";
		}
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(DebuggerFormatter.DATE_FORMAT.format(new Date(logRecord.getMillis())));
		stringBuilder.append(" - ");
		stringBuilder.append('[');
		stringBuilder.append(logRecord.getLoggerName());
		stringBuilder.append('/');
		stringBuilder.append(logRecord.getLevel());
		stringBuilder.append("] - ");
		stringBuilder.append(this.formatMessage(logRecord));
		if (logRecord.getLevel().intValue() >= Level.WARNING.intValue() && logRecord.getThrown() != null) {
			stringBuilder.append('\n');
			stringBuilder.append(logRecord.getThrown());
			stringBuilder.append('\n');
			stringBuilder.append(DebuggerFormatter.stackTraceToString(logRecord.getThrown()));
		}
		stringBuilder.append('\n');
		return stringBuilder.toString();
	}
}
