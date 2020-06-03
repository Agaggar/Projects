package dna;

//FastaRecord class contains two specific strings, and ensures that this file is valid

public class FastaRecord implements DNARecord {
	private String defline;
	private String sequence;

	// Constructor initializes type FastqRecord and ensures it takes suitable
	// strings.
	public FastaRecord(String defline, String sequence) throws RecordFormatException {
		// Check to ensure defline starts with char '>'
		if (defline.charAt(0) != '>')
			throw new RecordFormatException(
				"Bad 1st char in defline in fasta record: saw "+defline.charAt(0)+", expected >");
		this.defline = defline; this.sequence = sequence; 
	}

	// Initializes defline and sequence from the input record.
	public FastaRecord(FastqRecord fastqRec) {
		// Changes fastq defline to a string valid for fasta defline
		String fastqRecDefline = ">" + fastqRec.getDefline().substring(1);
		this.defline = fastqRecDefline;
		this.sequence = fastqRec.getSequence();
	}

	@Override
	public String getDefline() {
		return this.defline;
	}

	@Override
	public String getSequence() {
		return this.sequence;
	}

	// This equals() method checks for deep equality for both fasta instance
	// variables.
	@Override
	public boolean equals(Object x) {
		FastaRecord that = (FastaRecord) x;

		if (this.defline.compareTo(that.defline) != 0)
			return false;
		if (this.sequence.compareTo(that.sequence) != 0)
			return false;
		return true;
	}

	// HashCode method returns the sum of the hash codes of defline, sequence, and
	// quality.
	public int hashCode() {
		return this.defline.hashCode() + this.sequence.hashCode();
	}
}
