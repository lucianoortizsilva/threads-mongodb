package com.lucianoortizsilva.csv;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.lucianoortizsilva.model.Movie;
import com.lucianoortizsilva.model.Platform;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReadCSV implements AutoCloseable {

	private String filename;
	private Platform platform;

	@Getter
	private List<String> dataAll = new ArrayList<>();

	public ReadCSV(Platform platform, String filename, String splitBy) {
		this.filename = filename;
		this.platform = platform;
		this.load();
		this.transform();
	}

	private void load() {
		try {
			final Path path = Path.of("src\\main\\resources\\csv", filename);
			final List<String> data = Files.readAllLines(path);
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
				dataAll.add(textByLine.toString());
			}
			log.info("Filename loaded with success: {}", filename);
		} catch (final Exception e) {
			var msg = String.format("Erro ao carregar arquivo: %s", filename);
			throw new RuntimeException(msg, e);
		}
	}

	private void transform() {
		for (final String data : dataAll) {
			final var line = data.split(";");
			final Movie movie = Movie.builder()//
					.id(line[0])//
					.type(line[1])//
					.title(line[2])//
					.director(line[3])//
					.cast(line[4])//
					.country(line[5])//
					.dtAdded(line[6])//
					.releaseYear(line[7])//
					.rating(line[8])//
					.duration(line[9])//
					.listedIn(line[10])//
					.description(line[11])//
					.platform(platform).build();//
			System.out.println(movie);//
		}
	}

	@Override
	public void close() throws Exception {
		log.info("Filename closed with success: {}", filename);
	}

}