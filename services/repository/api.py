
from flask_restful import Resource, Api, abort, reqparse
from songs import one, all

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


def init_api(app):
    api = Api(app)
    api.add_resource(SongList, '/api/songs')
    api.add_resource(Song, '/api/songs/<int:song_id>')
