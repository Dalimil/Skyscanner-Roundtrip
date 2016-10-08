from flask import Flask

# Define the WSGI application object
app = Flask(__name__)

# Configurations
app.config.from_object('config')

# Import modules

import main

# Register blueprint(s) - (but need to import first)
# app.register_blueprint(auth_module)
# ..
