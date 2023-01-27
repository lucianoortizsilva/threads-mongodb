import pandas as pd
from model.entity import Movie
from repository.save_move_thread import SaveMoveThread
import threading
import time
import logging

logging.basicConfig(format='%(levelname)s %(asctime)s:%(message)s', datefmt='%d/%m/%Y %H:%M:%S', level=logging.INFO)

# https://wellsr.com/python/python-multithreading-run-functions-in-parallel/

qtd_threads = 4
all_threads = []


def create_movie(row):
    m = Movie()
    m._id = row['id']
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
        print('read file - start')
        columns = ['id', 'type', 'title', 'director', 'cast', 'country', 'dtAdded', 'releaseYear', 'rating', 'duration',
                   'listedIn', 'description']
        df_1 = pd.read_csv('csv/data-01.csv', header=None, names=columns, delimiter=';')
        df_2 = pd.read_csv('csv/data-02.csv', header=None, names=columns, delimiter=';')
        df_3 = pd.read_csv('csv/data-03.csv', header=None, names=columns, delimiter=';')
        df_4 = pd.read_csv('csv/data-04.csv', header=None, names=columns, delimiter=';')
        union_dfs = pd.concat([df_1, df_2, df_3, df_4])
        print('read file - end')

        print('----------------')

        print('creating objects movies - start')
        movies = list()
        for index, row in union_dfs.iterrows():
            movies.append(create_movie(row))
        movies.sort(key=lambda m: m._id)
        print('creating objects movies - end')

        print('----------------')

        print('create threads object - start')
        try:
            count_tasks = 1
            first_index = 0
            quantity_index_by_thread = movies.__len__() / qtd_threads
            last_index = quantity_index_by_thread
            is_last = False
            for i in range(0, qtd_threads):
                if count_tasks == qtd_threads:
                    is_last = True
                if is_last:
                    diff = movies.__len__() - last_index
                    last_index = last_index + diff
                obj = SaveMoveThread(name='Thread name: ' + str(count_tasks))
                if first_index == last_index:
                    logging.warning('Threads Desejadas: %s - Threads Criadas: %s', str(qtd_threads), str(count_tasks-1))
                    break
                my_thread = threading.Thread(target=obj.run, args=(movies, int(first_index), int(last_index)))
                all_threads.append(my_thread)
                first_index = last_index
                last_index += quantity_index_by_thread
                count_tasks += 1
        except Exception as e:
            logging.error(e)
        print('create threads object - end')

        print('----------------')

        start = time.time()

        print('execute threads object - start')
        for j in all_threads:
            j.start()
        for j in all_threads:
            j.join()
        print('execute threads object - end')

        end = time.time() - start

        print('----------------')
        print('Time final results: ' + str(end) + ' seconds')
        print('----------------')

    except Exception as e:
        logging.error(exc_info=True)

