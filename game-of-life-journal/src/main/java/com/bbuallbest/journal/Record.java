package com.bbuallbest.journal;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.regex.Pattern;

public class Record {

    public static final String DATE_TIME_PATTERN = "yyyy-mm-dd hh-mm-ss";

    private static final int FIELD_AMOUNT = 4;
	private static final String WHITE_SPACE_PATTERN = "\\s+";
	private static final String CARRIAGE_RETURN_PATTERN = "[\\r\\n]";
	private static final String HARD_RECORD_PATTERN = "[0-9]{4}(-[0-9]{2}){2}\\s+[0-9]{2}(-[0-9]{2}){2}\\s+"
            + "(([.]\\s{4})|!!!\\s{2}|!{5})\\s+[^ \\s]+(\\s+[^ \\n]+)+";
	
	final private DateTime time;
	final private Importance importance;
	final private String source;
	final private String message;
	
	public Record(DateTime time, Importance importance, String source,
			String message) {
		
		validateInput(time, importance, source, message);
		
		this.time = time;
		this.importance = importance;
		this.source = source.replaceAll(WHITE_SPACE_PATTERN, "");
		this.message = message.replaceAll(CARRIAGE_RETURN_PATTERN, "");
	}
	
	public Record(String record) {
        if (record == null)
            throw new IllegalArgumentException("Input argument \"record\" is null.");

        String[] unparsedFields = record.split(WHITE_SPACE_PATTERN);

        if (!isMatch(record, HARD_RECORD_PATTERN) || unparsedFields.length < FIELD_AMOUNT + 1)
            throw new IllegalArgumentException("Invalid argument format.");

        time = parseTime(unparsedFields[0], unparsedFields[1]);
        importance = parseImportance(unparsedFields[2]);
        source = unparsedFields[3];
        message = buildMessageFromSource(unparsedFields, 4);
    }

    private String buildMessageFromSource(String[] sources, int start) {
        StringBuilder message = new StringBuilder(sources[start++]);
        while (start < sources.length) {
            message.append(" ");
            message.append(sources[start++]);
        }
        return message.toString();
    }

    private Importance parseImportance(String source) {
        for (Importance importance : Importance.values()) {
            if (importance.getView().trim().equals(source))
                return importance;
        }
        throw new IllegalArgumentException("Illegal importance argument \"" + source + "\".");
    }

    private DateTime parseTime(String dateSource, String timeSource) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(DATE_TIME_PATTERN);
        return formatter.parseDateTime(dateSource + " " + timeSource);
    }

    private boolean isMatch(String record, String pattern) {
        boolean b = Pattern.compile(pattern).matcher(record).matches();
        return b;
    }

    public DateTime getTime() {
		return time;
	}

	public Importance getImportance() {
		return importance;
	}

	public String getSource() {
		return source;
	}

	public String getMessage() {
		return message;
	}
	
	private void validateInput(DateTime time, Importance importance, String source,
			String message) {
		StringBuilder exceptionMessage = new StringBuilder("Input argument \"");
		
		if(time == null)
			exceptionMessage.append("dateTime");
		else if(importance == null)
			exceptionMessage.append("importance");
		else if(source == null)
			exceptionMessage.append("source");
		else if(message == null)
			exceptionMessage.append("message");
		else
			return;
		
		exceptionMessage.append("\" is null.");
		
		throw new IllegalArgumentException(exceptionMessage.toString());
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Record record = (Record) o;

        if (importance != record.importance) return false;
        if (!message.equals(record.message)) return false;
        if (!source.equals(record.source)) return false;
        if (!time.equals(record.time)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = time.hashCode();
        result = 31 * result + importance.hashCode();
        result = 31 * result + source.hashCode();
        result = 31 * result + message.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return DateTimeFormat.forPattern(DATE_TIME_PATTERN).print(time)
                + " " + importance.getView()
                + " " + source + " " + message;
    }
}
