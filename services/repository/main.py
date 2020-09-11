# Press Shift+F10 to execute it or replace it with your code.
# Press Double Shift to search everywhere for classes, files, tool windows, actions, and settings.

from flask import Flask
from flask_restful import Resource, Api, abort, reqparse
from songs import one, all



app = Flask(__name__)
api = Api(app)

parser = reqparse.RequestParser()
# parser.add_argument('id', type=int, help="Song id")


class SongList(Resource):
    def get(self):
        return all()


class Song(Resource):
    def get(self, song_id):
        try:
            return one(song_id)
        except Exception as e:
            abort(410, message=str(e), error=True)


api.add_resource(SongList, '/api/songs')
api.add_resource(Song, '/api/songs/<int:song_id>')


if __name__ == '__main__':
    app.run(debug=True)
