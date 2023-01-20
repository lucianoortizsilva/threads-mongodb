package com.lucianoortizsilva.movies.data;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Extraction implements AutoCloseable {

	private String[] allCsv;

	@Getter
	private List<String> data;

	public Extraction(final String[] allCsv, final String splitBy) {
		Objects.nonNull(allCsv);
		Objects.nonNull(splitBy);
		this.data = new ArrayList<>();
		this.allCsv = allCsv;
		this.loadData();
	}

	private void loadData() {
		try {
			for (int i = 0; i < allCsv.length; i++) {
				final Path path = Path.of("src\\main\\resources\\csv", allCsv[i]);
				final List<String> dataOfFile = Files.readAllLines(path);
				for (final String lineOld : dataOfFile) {
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
					this.data.add(textByLine.toString());
				}

			}
		} catch (final Exception e) {
			var msg = String.format("Erro ao carregar arquivo: %s", allCsv.toString());
			throw new RuntimeException(msg, e);
		}
	}

	@Override
	public void close() throws Exception {
		log.info("Filename closed with success: {}", allCsv.toString());
	}

}