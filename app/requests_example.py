# Examples of 'requests' package + 'json' package
# http://docs.python-requests.org

import requests

cookies = {"cookies_are": "working"} # use s=requests.Session(); r=s.get(..) for persistance
params = {'key1': 'value1', 'key2': ['value2', 'value3']} # even a list
r = requests.get('https://example.com/getme', cookies=cookies, params=params)
print(r.url) # http://httpbin.org/get?key1=value1&key2=value2&key2=value3

payload = {'key1': 'value1', 'key2': 'value2'}
r = requests.post("http://example.com/post", data=payload) # or also json= , headers=

if(r.status_code == requests.codes.ok):
	print(r.text) # or r.json() for non-html
	print(r.headers)
	print(r.cookies['example_cookie_name'])



# Alternatively use json directly
import json

s = json.dumps(['foo', {'bar': ('baz', None, 1.0, 2)}])
print(s)    # '["foo", {"bar": ["baz", null, 1.0, 2]}]'
t = json.loads(s) # it's python object again

