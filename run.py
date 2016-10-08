# Run a test server.
from app import app

#host='0.0.0.0' only with debug disabled - security risk
app.run(port=8080, debug=True) # - don't use this one with sockets
