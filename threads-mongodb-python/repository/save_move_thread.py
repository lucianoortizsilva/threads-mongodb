import threading
from threading import *
import pymongo
import logging


class SaveMoveThread(Thread):
    conn_str = "mongodb://localhost:27017/?retryWrites=true&w=majority"
    client = pymongo.MongoClient(conn_str, serverSelectionTimeoutMS=5000)
    db = client['my_db_python']
    collection_movies = db["Movies"]

    def __int__(self):
        pass

    def run(self, movies, first_index, last_index):
        logging.info('Current Thread: %s, Range Index: [%s-%s]', threading.currentThread().getName(), str(first_index), str(last_index - 1))
        try:
            movies_selecteds_by_index = [movies[i] for i in range(first_index, last_index)]
            for m in movies_selecteds_by_index:
                self.collection_movies.insert_one(m.__dict__)
        except Exception as e:
            print(e)
