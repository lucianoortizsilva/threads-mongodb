import pandas as pd
import pymongo
from model.entity import Movie
import time

conn_str = "mongodb://localhost:27017/?retryWrites=true&w=majority"
client = pymongo.MongoClient(conn_str, serverSelectionTimeoutMS=5000)


def create_movie(row):
    m = Movie()
    m.id = row['id']
    m.type = row['type']
    m.title = row['title']
    m.description = row['description']
    m.director = row['director']
    m.cast = row['cast']
    m.country = row['country']
    m.dtAdded = row['dtAdded']
    m.releaseYear = row['releaseYear']
    m.rating = row['rating']
    m.duration = row['duration']
    m.listedIn = row['listedIn']
    m.description = row['description']
    return m


if __name__ == '__main__':

    try:
        db = client['my_db']
        collectionMovies = db["Movies"]

        columns = ['id', 'type', 'title', 'director', 'cast', 'country', 'dtAdded', 'releaseYear', 'rating', 'duration', 'listedIn', 'description']
        df = pd.read_csv('csv/data-00.csv', header=None, names=columns, delimiter=';')
        movies = list()

        for index, row in df.iterrows():
            movies.append(create_movie(row))

        start = time.time()
        for m in movies:
            collectionMovies.insert_one(m.__dict__)

        end = time.time() - start
        print(end)

    except Exception as e:
        print(e)
