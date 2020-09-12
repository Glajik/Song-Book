# Press Shift+F10 to execute it or replace it with your code.
# Press Double Shift to search everywhere for classes, files, tool windows, actions, and settings.

from flask import Flask, render_template
from api import init_api

app = Flask(__name__)
init_api(app)


@app.route('/')
def hello():
    return render_template('hello.html')


if __name__ == '__main__':
    app.run(debug=True)
