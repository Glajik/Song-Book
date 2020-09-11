import os
from datetime import datetime, timezone
import pytz
from languages import LANG

path = os.path.dirname(__file__)

timezone = pytz.timezone("Europe/Kiev")
current_time = timezone.localize(datetime.now())


SONGS = [
    {
        'id': 1,
        'language_id': 'ru',
        'title': 'Воспой',
        'description': 'Слова/Музыка: Браян Крэйг,\nПеревод на русский М. Ляшенко',
        'filename': '1.txt',
        'created_at': current_time,
        'updated_at': current_time
    }, {
        'id': 2,
        'language_id': 'ru',
        'title': 'Дай мне увидеть Тебя!',
        'description': 'Слова/Музыка: Майкл Смит,\nРусский перевод: М. Ляшенко',
        'filename': '2.txt',
        'created_at': current_time,
        'updated_at': current_time
    }, {
        'id': 3,
        'language_id': 'ua',
        'title': 'Святий Боже',
        'description': 'Слова/Музыка: Майкл Смит\nПеревод на украинский: И. Ляшенко',
        'filename': '3.txt',
        'created_at': current_time,
        'updated_at': current_time
    },
]

def serialize(item, text):
    return {
        "id": item.get('id'),
        "title": item.get('title'),
        "description": item.get('description'),
        "text": text,
        "created_at": item.get('created_at').isoformat(),
        "updated_at": item.get('updated_at').isoformat(),
        "language": LANG.get(item.get('language_id')),
        "tags": ["all", "odessa-coc", "kyiv-coc", "favorites"],
        "resources": [
            {
                "title": "New video",
                "description": "Some video discription",
                "url": "https://www.youtube.com",
                "type": "VIDEO",
            }
        ],
        "user": {
            "id": "12345abcd"
        }
    }


get_filename = lambda item: f"{path}/songs/{item['filename']}"


def one(id):
    by_id = lambda item: item['id'] == id
    result = list(filter(by_id, SONGS))

    if len(result) == 0:
        raise Exception(f"No song with id: {id}")

    song = result[0]

    with open(get_filename(song)) as f:
        text = f.read()
        return serialize(song, text)


def all():
    ids = [item['id'] for item in SONGS]
    return list(map(one, ids))
