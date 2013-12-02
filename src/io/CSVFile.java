package io;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

public class CSVFile {

	public static List<String[]> readCSV(File csv, int field_num)
			throws IOException {
		CSVReader reader = new CSVReader(new FileReader(csv));
		List<String[]> content = reader.readAll();
		reader.close();
		// Check the input fields
		for (int i = 0; i != content.size(); ++i) {
			if (content.get(i).length != field_num)
				throw new IOException("Wrong number of fields for "
						+ csv.getName() + "@ line " + i);
		}
		content.remove(0); // skip the first line
		return content;
	}

}
