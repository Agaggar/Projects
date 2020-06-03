package dna;

import java.io.*;

// Reads lines from a BufferedReader and builds a FastqRecord.

public class FastqReader {
	private BufferedReader theBufferedReader;

	// Constructing FastqReader
	public FastqReader(BufferedReader theBufferedReader) {
		this.theBufferedReader = theBufferedReader;
	}

	// Returns next record in the file, or null if EOF (end-of-file).
	public FastqRecord readRecord() throws IOException, RecordFormatException {
		// theBufferedReader will read the 4 lines in a fastq record, and store the
		// strings that matter to be called later
		String defline = theBufferedReader.readLine();
		
		// this if segment ensures that the line that is read isn't the last line in the
			// file
			if (defline == null)
				return null;
		String sequence = theBufferedReader.readLine();
		theBufferedReader.readLine();
		String quality = theBufferedReader.readLine();
		
		// constructs a new FastqRecord.
		return new FastqRecord(defline, sequence, quality);
	}
}
