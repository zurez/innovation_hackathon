#!/usr/bin/env python3
# -*- coding: utf-8 -*-
#.--. .-. ... .... -. - ... .-.-.- .. -.

from flask import Flask, render_template, g, request, jsonify
# from flask.ext.mongoengine import MongoEngine
from flask import request
import tempfile
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
		  return ""
	@app.route('/api/audio', methods=['GET', 'POST'])
	def audio():
		try:
			if request.method =='POST':
				# return json.dumps({'method':'POST'})
				fil= request.files['file']
				fil.save("/home/zurez/Work/hackathon/ast/Uploads/"+fil.name)
				return json.dumps({'Status':200,"Message":"This audio is of Mr Mogambo"})
			elif request.method=='GET':
				return json.dumps({'Status':400})
			else:
				return json.dumps({'Status':404})
		except Exception as e:
			print (e)
			return json.dumps({'Status': 202})
	@app.route('/api/image',methods=['GET','POST'])
	def image():
		try:
			if request.method=='POST':
				fil= request.files['file']
				path="/home/zurez/Work/hackathon/ast/Uploads/"+fil.name
				fil.save(path)
				i = Image(path,"","gallery")
				response=i.recognize()
				attendance=""
				
				for x  in response['images']:
					name= i['candidates'][0].keys()[0]
					attendance=attendance+"#"+"name"
				with open("results.txt","a+") as f :
					f.write(attendance)
				return attendance


				
		except Exception as e:
			raise e

if __name__ == '__main__':
	create_app()
