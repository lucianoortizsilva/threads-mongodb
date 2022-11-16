package com.lucianoortizsilva.csv;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReadCSV implements AutoCloseable {

	private String filename;

	@Getter
	private List<String> dataAll;

	public ReadCSV(final String filename, final String splitBy) {
		this.filename = filename;
		this.load();
	}

	private void load() {
		try {
			final Path path = Path.of("src\\main\\resources\\csv", filename);
			final List<String> data = Files.readAllLines(path);
			final List<String> lineNew = new ArrayList<>();
			for (final String lineOld : data) {
				final StringTokenizer stringTokenizer = new StringTokenizer(lineOld, "\"\"", true);
				final StringBuilder textByLine = new StringBuilder();
				while (stringTokenizer.hasMoreTokens()) {
					final String token = stringTokenizer.nextToken();
					if (token.equals("\"")) {
						if (stringTokenizer.hasMoreTokens()) {
							var newtWord = stringTokenizer.nextToken();
							textByLine.append(newtWord);
							if (stringTokenizer.hasMoreTokens()) {
								stringTokenizer.nextToken();
							} else {
								continue;
							}
						} else {
							continue;
						}
					} else {
						textByLine.append(token.replace(",", ";"));
					}
				}
				lineNew.add(textByLine.toString());
			}
			log.info("Filename loaded with success: {}", filename);
		} catch (final Exception e) {
			var msg = String.format("Erro ao carregar arquivo: %s", filename);
			throw new RuntimeException(msg, e);
		}
	}

	@Override
	public void close() throws Exception {
		log.info("Filename closed with success: {}", filename);
	}

}