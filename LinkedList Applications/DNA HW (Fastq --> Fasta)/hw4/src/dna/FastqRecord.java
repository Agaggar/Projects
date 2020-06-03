package dna;

// FastqRecord contains the defline, sequence, and quality string
// from a record in a fastq file.
// It ensures that the file has the correct Strings

public class FastqRecord implements DNARecord {
	private String defline;
	private String sequence;
	private String quality;

	// Constructor initializes type FastqRecord and ensures it takes suitable
	// strings.
	public FastqRecord(String defline, String sequence, String quality) throws RecordFormatException {
		// Check to ensure defline starts with char '@'. If it doesn't, throw Exception
		if (defline.charAt(0) != '@')
			throw new RecordFormatException(
					"Bad 1st char in defline in fastq record: saw " + defline.charAt(0) + ", expected @");

		this.defline = defline;
		this.sequence = sequence;
		this.quality = quality;

	}

	@Override
	public String getDefline() {
		return defline;
	}

	@Override
	public String getSequence() {
		return sequence;
	}

	// This equals() method checks for deep equality for all 3 instance variables.
	@Override
	public boolean equals(Object x) {
		FastqRecord that = (FastqRecord) x;

		/*
		 * return this.defline.equals(that.defline) && this.sequence.equals(
		 * 			that.sequence) && this,quality,equals(that.queality);
		 */
		if (this.defline.compareTo(that.defline) != 0)
			return false;
		if (this.sequence.compareTo(that.sequence) != 0)
			return false;
		if (this.quality.compareTo(that.quality) != 0)
			return false;

		return this.defline.compareTo(that.defline) == 0;
	}

	// ensures file contains at least one $ and #
	public boolean qualityIsLow() {
		if (this.quality.contains("$") && this.quality.contains("#"))
			return true;
		return false;
		// return quality.contains("$") && quality.contains("#");
			//cannot perform logical operators on strings or char
	}

	// HashCode method returns the sum of the hash codes of defline, sequence, and
	// quality.
	public int hashCode() {
		return defline.hashCode() + sequence.hashCode() + quality.hashCode();
	}
}
