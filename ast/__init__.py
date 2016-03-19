#!/usr/bin/env python3
# -*- coding: utf-8 -*-
#.--. .-. ... .... -. - ... .-.-.- .. -.

from flask import Flask, render_template, g, request, jsonify
# from flask.ext.mongoengine import MongoEngine
from flask import request
import json
from os.path import basename
from ast.audio.controller import Audio
from ast.image.controller import Image
app = Flask(__name__, template_folder='templates')
app.config.from_pyfile('config.py')

# db = MongoEngine(app)

def create_app():
	@app.route('/')
	def home():
		  return "f"
	@app.route('/api/audio', methods=['GET', 'POST'])
	def audio():
		try:
			if request.method =='POST':
				# return json.dumps({'method':'POST'})
				f_name= request.files['file']
			  
			   
				return json.dumps({'Status':200,"Message":"This audio is of Mr Mogambo"})
			elif request.method=='GET':
				return json.dumps({'Status':400})
			else:
				return json.dumps({'Status':404})
		except Exception:
			return json.dumps({'Status': 202})
	def image():
		pass

if __name__ == '__main__':
	create_app()
