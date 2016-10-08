from flask import *
# Import the app object from the main app module
from app import app

@app.route('/')
def index():
	return "Hello"