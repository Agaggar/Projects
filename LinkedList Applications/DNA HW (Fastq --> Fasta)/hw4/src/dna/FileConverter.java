package dna;

import java.io.*;
import java.util.*;

//This class reads and converts fastq records into fasta records

public class FileConverter {
	private File fastq;
	private File fasta;

	// Constructs the fileconverter
	public FileConverter(File fastq, File fasta) {
		this.fastq = fastq;
		this.fasta = fasta;
	}

	// Writes a fasta file consisting of conversion of all records from the fastq
	// with
	// sufficient quality and unique defline.
	//
	public void convert() throws IOException {
		// Build chain of readers.
		FileReader fr = new FileReader(fastq);
		BufferedReader br = new BufferedReader(fr);
		FastqReader fqr = new FastqReader(br);

		// Build chain of writers.
		FileWriter fw = new FileWriter(fasta);
		PrintWriter pw = new PrintWriter(fw);
		FastaWriter faw = new FastaWriter(pw);

		// Read, translate, write.
		boolean done = false; // Condition for while loop to continue
		while (!done) {

			// will attempt to create, read, and write records. if it encounters an
			// exception, will move to catch block
			try {
				FastqRecord fastqRec = fqr.readRecord();

				// Ensures the record isn't null.
				if (fastqRec == null) {
					done = true;
					break;
				}

				// Ensures quality check passes
				if (!fastqRec.qualityIsLow()) {
					FastaRecord fastaRec = new FastaRecord(fastqRec);
					faw.writeRecord(fastaRec);
				}

			}

			// Catches potential exceptions
			catch (RecordFormatException e) {
				e.getMessage();
			}

		}

		// Closes fr, br, fw, and pw
		pw.close();
		fw.close();
		br.close();
		fr.close();
	}

	// Main method
	public static void main(String[] args) {
		System.out.println("Starting");
		try {
			File fastq = new File("data/HW4.fastq");
			if (!fastq.exists()) {
				System.out.println("Can't find input file " + fastq.getAbsolutePath());
				System.exit(1);
			}
			File fasta = new File("data/HW4.fasta");
			FileConverter converter = new FileConverter(fastq, fasta);
			converter.convert();
		} catch (IOException x) {
			System.out.println(x.getMessage());
		}
		System.out.println("Done");
	}
}
