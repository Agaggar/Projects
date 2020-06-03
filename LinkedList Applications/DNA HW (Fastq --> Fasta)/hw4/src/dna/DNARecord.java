package dna;

// FastqRecord and FastaRecord should implement this to get defline and sequence

public interface DNARecord {
	public String getDefline();

	public String getSequence();
}
